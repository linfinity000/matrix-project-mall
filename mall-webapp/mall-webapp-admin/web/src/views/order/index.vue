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
                                    <el-form-item label="订单号">
                                        <el-input clearable size="small" v-model="queryForm.orderId"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="3">
                                    <el-form-item label="订单状态">
                                        <el-select clearable placeholder="请选择" size="small"
                                                   v-model="queryForm.orderStatus">
                                            <el-option :key="item.code" :label="item.name" :value="item.code"
                                                       v-for="item in orderStatusOptions"></el-option>
                                        </el-select>
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
                                <el-table :data="orderList" @row-dblclick="detail"
                                          border style="width: 100%;margin-top: 5px;">
                                    <el-table-column label="订单号" prop="orderId" width="250"></el-table-column>
                                    <el-table-column label="店铺名称" prop="shopName" width="180"></el-table-column>
                                    <el-table-column label="订单金额(￥)" prop="orderPrice" width="180"></el-table-column>
                                    <el-table-column label="商品数量" prop="orderGoodsCount" width="180"></el-table-column>
                                    <el-table-column label="订单备注" prop="remark"></el-table-column>
                                    <el-table-column label="创建时间" prop="createTime"></el-table-column>
                                    <el-table-column label="更新时间" prop="updateTime"></el-table-column>
                                    <el-table-column label="订单状态" prop="orderStatusRemark"></el-table-column>
                                </el-table>
                                <el-pagination :page-count="queryForm.pageSize"
                                               :page-sizes="[20, 50, 100]" :total="orderCount"
                                               @current-change="handleCurrentChange"
                                               @size-change="handleSizeChange" background
                                               layout="total, prev, pager, next, sizes"
                                               style="text-align: right;margin-top: 20px;"></el-pagination>
                            </el-col>
                        </el-row>
                    </el-tab-pane>
                    <el-tab-pane label="详情" name="detail" v-if="showDetail">
                        <el-row v-if="selectRow.hasLogistics === 1">
                            <el-col :span="5" style="margin-bottom: 20px;">
                                <el-card class="box-card">
                                    <div class="clearfix" slot="header">
                                        <span>收货地址</span>
                                        <el-button @click="editAddress" style="float: right; padding: 3px 0"
                                                   type="text">修改
                                        </el-button>
                                    </div>
                                    <el-form :model="addressForm" :rules="addressRules" label-width="100px"
                                             ref="addressForm">
                                        <el-form-item label="地区选择" prop="regions">
                                            <el-cascader :options="addressOptions"
                                                         :props="{label: 'name', value: 'code'}"
                                                         size="small" v-model="addressForm.regions"></el-cascader>
                                        </el-form-item>
                                        <el-form-item label="详细地址" prop="address">
                                            <el-input type="textarea" v-model="addressForm.address"></el-input>
                                        </el-form-item>
                                    </el-form>
                                </el-card>
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