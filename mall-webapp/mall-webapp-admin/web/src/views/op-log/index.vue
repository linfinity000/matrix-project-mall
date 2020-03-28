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
                                    <el-form-item label="操作名称">
                                        <el-input clearable size="small" v-model="queryForm.name"></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-form>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-button @click="loadTable" size="small" type="primary">刷新</el-button>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-table :data="logList" border style="width: 100%;margin-top: 5px;">
                                    <el-table-column label="ID" prop="id"></el-table-column>
                                    <el-table-column label="名称" prop="name"></el-table-column>
                                    <el-table-column label="URI" prop="uri"></el-table-column>
                                    <el-table-column label="请求参数" prop="request"></el-table-column>
                                    <el-table-column label="返回参数" prop="response"></el-table-column>
                                    <el-table-column label="用户ID" prop="userId"></el-table-column>
                                    <el-table-column label="创建时间" prop="createTime"></el-table-column>
                                </el-table>
                                <el-pagination :page-count="queryForm.pageSize"
                                               :page-sizes="[20, 50, 100]" :total="logCount"
                                               @current-change="handleCurrentChange"
                                               @size-change="handleSizeChange" background
                                               layout="total, prev, pager, next, sizes"
                                               style="text-align: right;margin-top: 20px;"></el-pagination>
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