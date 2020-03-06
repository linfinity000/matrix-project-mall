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
                                    <el-form-item label="快递名称">
                                        <el-input clearable size="small" v-model="queryForm.logisticsName"></el-input>
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
                                <el-table :data="logisticsList" @selection-change="handleSelectionChange" border
                                          style="width: 100%;margin-top: 5px;">
                                    <el-table-column type="selection" width="55"></el-table-column>
                                    <el-table-column label="快递ID" prop="logisticsId" width="250"></el-table-column>
                                    <el-table-column label="快递名称" prop="logisticsName" width="180"></el-table-column>
                                    <el-table-column label="创建时间" prop="createTime"></el-table-column>
                                    <el-table-column label="更新时间" prop="updateTime"></el-table-column>
                                </el-table>
                                <el-pagination :page-count="queryForm.pageSize"
                                               :page-sizes="[20, 50, 100]" :total="logisticsCount"
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
                                    <el-form-item label="快递名称" prop="logisticsName">
                                        <el-input size="small" v-model="ruleForm.logisticsName"></el-input>
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