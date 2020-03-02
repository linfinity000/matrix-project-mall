<template>
    <div>
        <top-menu></top-menu>
        <el-container>
            <el-main>
                <el-tabs type="card" v-model="activeName">
                    <el-tab-pane label="列表" name="list">
                        <el-row>
                            <el-form label-position='center' label-width="80px">
                                <el-col :span="3">
                                    <el-form-item label="商品名称">
                                        <el-input clearable size="small" v-model="queryForm.atomsGoodsName"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="3">
                                    <el-form-item label="状态">
                                        <el-select clearable placeholder="请选择" size="small" v-model="queryForm.status">
                                            <el-option :key="item.id" :label="item.name" :value="item.id"
                                                       v-for="item in statusOptions"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                            </el-form>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-button @click="loadTable" size="small" type="primary">刷新</el-button>
                                <el-button :disabled="selection.length !== 1" @click="edit" size="small" type="primary">
                                    编辑
                                </el-button>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-table :data="atomsGoodsList" @selection-change="handleSelectionChange" border
                                          style="width: 100%;margin-top: 5px;">
                                    <el-table-column type="selection" width="55"></el-table-column>
                                    <el-table-column label="原子商品ID" prop="atomsGoodsId"></el-table-column>
                                    <el-table-column label="原子商品名称" prop="atomsGoodsName"></el-table-column>
                                    <el-table-column label="商品卖点" prop="salePoints"></el-table-column>
                                    <el-table-column label="品牌名称" prop="brandName"></el-table-column>
                                    <el-table-column label="分类名称" prop="categoryName"></el-table-column>
                                    <el-table-column label="店铺名称" prop="shopName"></el-table-column>
                                    <el-table-column label="创建时间" prop="createTime"></el-table-column>
                                    <el-table-column label="更新时间" prop="updateTime"></el-table-column>
                                    <el-table-column label="状态" prop="statusRemark"></el-table-column>
                                </el-table>
                                <el-pagination :page-count="queryForm.pageSize"
                                               :page-sizes="[20, 50, 100]" :total="atomsGoodsCount"
                                               @current-change="handleCurrentChange"
                                               @size-change="handleSizeChange" background
                                               layout="total, prev, pager, next, sizes"
                                               style="text-align: right;margin-top: 20px;"></el-pagination>
                            </el-col>
                        </el-row>
                    </el-tab-pane>
                    <el-tab-pane label="详情" name="detail" v-if="showDetail">
                        <el-row>
                            <el-col :span="24">
                                <el-form :model="ruleForm" :rules="rules" label-width="120px" ref="ruleForm">
                                    <div v-if="skuLabels != null && skuLabels.length > 0">
                                        <el-form-item :key="item.labelId" :label="item.labelName"
                                                      v-for="item in skuLabels">
                                            <el-tag :key="tag.value" :type="tag.type"
                                                    @click="handleSkuClick(item.labelId, tag)"
                                                    style="cursor: pointer;" v-for="tag in item.skuValues">
                                                {{tag.value}}
                                            </el-tag>
                                            <el-input :ref="'saveTagNewValue' + item.labelId"
                                                      @blur="handleNewValueConfirm(item.labelId)"
                                                      @keyup.enter.native="handleNewValueConfirm(item.labelId)"
                                                      class="input-new-tag"
                                                      size="small" v-if="newValue.shows[item.labelId]"
                                                      v-model="newValue.values[item.labelId]">
                                            </el-input>
                                            <el-button @click="showNewValue(item.labelId)" class="button-new-tag"
                                                       size="small" v-else>+
                                            </el-button>
                                        </el-form-item>
                                    </div>
                                    <div v-if="showRuleForm">
                                        <el-divider content-position="left">商品信息</el-divider>
                                        <el-form-item label="商品名称" v-if="ruleForm.goodsName != null">
                                            {{ruleForm.goodsName}}
                                        </el-form-item>
                                        <el-form-item label="原价">
                                            <el-input-number :max="100000" :min="0.01" :precision="2"
                                                             :step="0.1" size="small" style="width: 180px;"
                                                             v-model="ruleForm.originPrice"></el-input-number>
                                        </el-form-item>
                                        <el-form-item label="销售价" prop="salePrice">
                                            <el-input-number :max="100000" :min="0.01" :precision="2"
                                                             :step="0.1" size="small" style="width: 180px;"
                                                             v-model="ruleForm.salePrice"></el-input-number>
                                        </el-form-item>
                                        <el-form-item label="库存" prop="stock">
                                            <el-input-number :max="99999" :precision="0" :step="1"
                                                             size="small" style="width: 180px;"
                                                             v-model="ruleForm.stock"></el-input-number>
                                        </el-form-item>
                                        <el-form-item label="商品图片">
                                            <file-upload :change="uploadImagesChange" :fileList="upload.images"
                                                         :limit="1"
                                                         :tip="upload.tip"
                                                         list-type="picture"
                                                         style="width: 350px;" type="Image"></file-upload>
                                        </el-form-item>
                                        <el-form-item label="状态" prop="status">
                                            <el-select placeholder="请选择" size="small" v-model="ruleForm.status">
                                                <el-option :key="item.id" :label="item.name" :value="item.id"
                                                           v-for="item in statusOptions"></el-option>
                                            </el-select>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button @click="save" size="small" type="primary">保存</el-button>
                                            <el-button @click="remove" size="small" type="danger"
                                                       v-if="ruleForm.goodsId != null">删除商品
                                            </el-button>
                                        </el-form-item>
                                    </div>
                                </el-form>
                            </el-col>
                        </el-row>
                    </el-tab-pane>
                </el-tabs>

            </el-main>
        </el-container>
    </div>
</template>
<script type="text/javascript">
    import {data} from './index.js';

    export default data;
</script>
<style scoped type="text/css">
    .el-main {
        padding-left: 2px;
        padding-right: 2px;
    }

    .el-tag + .el-tag {
        margin-left: 10px;
    }

    .button-new-tag {
        margin-left: 10px;
        height: 32px;
        line-height: 30px;
        padding-top: 0;
        padding-bottom: 0;
    }

    .input-new-tag {
        width: 90px;
        margin-left: 10px;
        vertical-align: bottom;
    }
</style>