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
                                <el-button @click="append" size="small" type="primary">新增</el-button>
                                <el-button :disabled="selection.length !== 1" @click="edit" size="small" type="primary">
                                    编辑
                                </el-button>
                                <el-button :disabled="selection.length !== 1" @click="remove" size="small"
                                           type="danger">删除
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
                                    <el-table-column label="原子商品图片">
                                        <template slot-scope="scope">
                                            <img :src="scope.row.atomsGoodsImage"
                                                 height="100"
                                                 v-if="scope.row.atomsGoodsImage != null && scope.row.atomsGoodsImage.length > 0"
                                                 width="100"/>
                                        </template>
                                    </el-table-column>
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
                            <el-col :span="5">
                                <el-form :model="ruleForm" :rules="rules" label-width="120px" ref="ruleForm">
                                    <el-form-item label="原子商品名称" prop="atomsGoodsName">
                                        <el-input size="small" v-model="ruleForm.atomsGoodsName"></el-input>
                                    </el-form-item>
                                    <el-form-item label="品牌" prop="brandId">
                                        <el-select placeholder="请选择" size="small" v-model="ruleForm.brandId">
                                            <el-option :key="item.brandId" :label="item.brandName" :value="item.brandId"
                                                       v-for="item in brandOptions"></el-option>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item label="分类" prop="categoryId">
                                        <select-tree :options="categoryTree" v-model="ruleForm.categoryId"
                                                     width="200"></select-tree>
                                    </el-form-item>
                                    <el-form-item label="商品卖点">
                                        <el-input size="small" v-model="ruleForm.salePoints"></el-input>
                                    </el-form-item>
                                    <el-form-item label="原子商品图片">
                                        <file-upload :change="uploadImagesChange" :fileList="upload.images" :limit="1"
                                                     :tip="upload.tip"
                                                     list-type="picture"
                                                     style="width: 350px;" type="Image"></file-upload>
                                    </el-form-item>
                                    <el-form-item label="商品描述">
                                        <rich-editor style="width: 800px;" v-model="ruleForm.description"></rich-editor>
                                    </el-form-item>
                                    <el-form-item label="SKU属性配置" style="width: 800px;">
                                        <el-tag :closable="ruleForm.atomsGoodsId == null" :key="sku.skuName"
                                                @close="delSkuLabel(sku.skuName)"
                                                v-for="sku in ruleForm.skuList">
                                            {{sku.skuName}}
                                        </el-tag>
                                        <el-input @blur="newSkuLabel" @keyup.enter.native="newSkuLabel"
                                                  class="input-new-tag"
                                                  size="small"
                                                  v-if="showSkuInput && (ruleForm.atomsGoodsId == null)"
                                                  v-model="skuInputValue">
                                        </el-input>
                                        <el-button @click="() => {this.showSkuInput = true;}" class="button-new-tag"
                                                   size="small"
                                                   v-if="!showSkuInput && ruleForm.atomsGoodsId == null">+
                                        </el-button>
                                    </el-form-item>
                                    <el-form-item label="标签属性配置" style="width: 800px;">
                                        <el-tag :closable="ruleForm.atomsGoodsId == null" :key="attr.attrName"
                                                @close="delAttrLabel(attr.attrName)"
                                                v-for="attr in ruleForm.attrList">
                                            {{attr.attrName}}
                                        </el-tag>
                                        <el-input @blur="newAttrLabel" @keyup.enter.native="newAttrLabel"
                                                  class="input-new-tag" size="small"
                                                  v-if="showAttrInput && ruleForm.atomsGoodsId == null"
                                                  v-model="attrInputValue">
                                        </el-input>
                                        <el-button @click="() => {this.showAttrInput = true;}" class="button-new-tag"
                                                   size="small"
                                                   v-if="!showAttrInput && ruleForm.atomsGoodsId == null">+
                                        </el-button>
                                    </el-form-item>
                                    <el-form-item label="图片banner" style="width: 800px">
                                        <file-upload :change="uploadBannerImagesChange" :fileList="upload.bannerImages"
                                                     :limit="10" :tip="upload.tip" list-type="picture-card"
                                                     type="Image"></file-upload>
                                    </el-form-item>
                                    <el-form-item label="状态" prop="status">
                                        <el-select placeholder="请选择" size="small" v-model="ruleForm.status">
                                            <el-option :key="item.id" :label="item.name" :value="item.id"
                                                       v-for="item in statusOptions"></el-option>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button @click="save" size="small" type="primary">保存</el-button>
                                    </el-form-item>
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