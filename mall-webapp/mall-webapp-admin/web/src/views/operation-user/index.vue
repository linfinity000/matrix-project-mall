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
                                    <el-form-item label="用户名">
                                        <el-input clearable size="small" v-model="queryForm.username"></el-input>
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
                                <el-table :data="userList" @selection-change="handleSelectionChange" border
                                          style="width: 100%;margin-top: 5px;">
                                    <el-table-column type="selection" width="55"></el-table-column>
                                    <el-table-column label="用户ID" prop="userId" width="250"></el-table-column>
                                    <el-table-column label="用户名" prop="username" width="180"></el-table-column>
                                    <el-table-column label="用户类型" prop="userGrantRemark" width="180"></el-table-column>
                                    <el-table-column label="创建时间" prop="createTime"></el-table-column>
                                    <el-table-column label="更新时间" prop="updateTime"></el-table-column>
                                    <el-table-column label="状态" prop="statusRemark"></el-table-column>
                                </el-table>
                                <el-pagination :page-count="queryForm.pageSize"
                                               :page-sizes="[20, 50, 100]" :total="userCount"
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
                                    <el-form-item label="用户名" prop="username">
                                        <el-input :disabled="ruleForm.userId != null" size="small"
                                                  v-model="ruleForm.username"></el-input>
                                    </el-form-item>
                                    <el-form-item :prop="ruleForm.userId == null ? 'password' : 'noPassword'"
                                                  label="密码">
                                        <el-input size="small" type="password" v-model="ruleForm.password"></el-input>
                                    </el-form-item>
                                    <el-form-item label="所属店铺" v-if="ruleForm.isDefault !== 1">
                                        <el-select clearable placeholder="请选择" size="small" v-model="ruleForm.shopId">
                                            <el-option :key="item.shopId" :label="item.shopName" :value="item.shopId"
                                                       v-for="item in shopOptions"></el-option>
                                        </el-select>
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