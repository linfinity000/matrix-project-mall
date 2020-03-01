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
                                    <el-form-item label="店铺名">
                                        <el-input clearable size="small" v-model="queryForm.shopName"></el-input>
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
                                <el-button :disabled="selection.length !== 1" @click="bindShop" size="small"
                                           type="primary">
                                    绑定店铺
                                </el-button>
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
                                <el-table :data="shopList" @selection-change="handleSelectionChange" border
                                          style="width: 100%;margin-top: 5px;">
                                    <el-table-column type="selection" width="55"></el-table-column>
                                    <el-table-column label="店铺ID" prop="shopId" width="250"></el-table-column>
                                    <el-table-column label="店铺名称" prop="shopName" width="180"></el-table-column>
                                    <el-table-column label="店铺Logo" width="180">
                                        <template slot-scope="scope">
                                            <img :src="scope.row.shopLogo"
                                                 height="100"
                                                 v-if="scope.row.shopLogo != null && scope.row.shopLogo.length > 0"
                                                 width="100"/>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="创建时间" prop="createTime"></el-table-column>
                                    <el-table-column label="更新时间" prop="updateTime"></el-table-column>
                                    <el-table-column label="状态" prop="statusRemark"></el-table-column>
                                </el-table>
                                <el-pagination :page-count="queryForm.pageSize"
                                               :page-sizes="[20, 50, 100]" :total="shopCount"
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
                                <el-form :model="ruleForm" :rules="rules" label-width="100px" ref="ruleForm">
                                    <el-form-item label="店铺名称" prop="shopName">
                                        <el-input size="small" v-model="ruleForm.shopName"></el-input>
                                    </el-form-item>
                                    <el-form-item label="店铺Logo">
                                        <file-upload :change="uploadImagesChange" :fileList="upload.images" :limit="1"
                                                     :tip="upload.tip"
                                                     list-type="picture"
                                                     style="width: 350px;" type="Image"></file-upload>
                                    </el-form-item>
                                    <el-form-item label="店铺描述">
                                        <rich-editor style="width: 800px;" v-model="ruleForm.shopDesc"></rich-editor>
                                    </el-form-item>
                                    <el-form-item label="店铺星级" prop="shopStar">
                                        <el-rate :colors="['#99A9BF', '#F7BA2A', '#FF9900']" style="margin-top: 10px;"
                                                 v-model="ruleForm.shopStar"></el-rate>
                                    </el-form-item>
                                    <el-form-item label="状态" prop="status">
                                        <el-select :disabled="ruleForm.isDefault === 1" placeholder="请选择" size="small"
                                                   v-model="ruleForm.status">
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
</style>