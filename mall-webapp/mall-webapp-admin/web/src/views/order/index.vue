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
                                        <el-button @click="() => {this.shipFormEdit = true}"
                                                   style="float: right; padding: 3px 0" type="text"
                                                   v-if="(selectRow.orderStatus === 11 || selectRow.orderStatus === 12) && !shipFormEdit">
                                            修改
                                        </el-button>
                                        <span v-else>
                                            <el-button @click="() => {this.shipFormEdit = false}"
                                                       style="float: right; padding: 3px 3px" type="text">
                                                取消
                                            </el-button>
                                            <el-button @click="editAddress"
                                                       style="float: right; padding: 3px 0" type="text">
                                                保存
                                            </el-button>
                                        </span>
                                    </div>
                                    <el-form :model="addressForm" :rules="addressRules" label-width="100px"
                                             ref="addressForm">
                                        <el-form-item label="地区选择" prop="regions">
                                            <el-cascader
                                                    :disabled="!(selectRow.orderStatus === 11 || selectRow.orderStatus === 12) || !shipFormEdit"
                                                    :options="addressOptions"
                                                    :props="{label: 'name', value: 'code'}"
                                                    size="small" v-model="addressForm.regions"></el-cascader>
                                        </el-form-item>
                                        <el-form-item label="联系人" prop="linkName">
                                            <el-input
                                                    :disabled="!(selectRow.orderStatus === 11 || selectRow.orderStatus === 12) || !shipFormEdit"
                                                    type="text"
                                                    v-model="addressForm.linkName"></el-input>
                                        </el-form-item>
                                        <el-form-item label="手机号" prop="mobile">
                                            <el-input
                                                    :disabled="!(selectRow.orderStatus === 11 || selectRow.orderStatus === 12) || !shipFormEdit"
                                                    type="text"
                                                    v-model="addressForm.mobile"></el-input>
                                        </el-form-item>
                                        <el-form-item label="详细地址" prop="address">
                                            <el-input
                                                    :disabled="!(selectRow.orderStatus === 11 || selectRow.orderStatus === 12) || !shipFormEdit"
                                                    type="textarea"
                                                    v-model="addressForm.address"></el-input>
                                        </el-form-item>
                                    </el-form>
                                </el-card>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24" style="margin-bottom: 20px;">
                                <el-table :data="orderGoodsList" border style="width: 100%;margin-top: 5px;">
                                    <el-table-column label="ID" prop="id" width="250"></el-table-column>
                                    <el-table-column label="商品名称" prop="goodsName" width="180"></el-table-column>
                                    <el-table-column label="商品总金额(￥)" prop="goodsTotalPrice"
                                                     width="180"></el-table-column>
                                    <el-table-column label="商品数量" prop="goodsCount" width="180"></el-table-column>
                                    <el-table-column label="快递公司名称" prop="logisticsCompanyName"></el-table-column>
                                    <el-table-column label="物流单号" prop="logisticsNo"></el-table-column>
                                    <el-table-column label="商品密钥" prop="goodsSecret"></el-table-column>
                                    <el-table-column fixed="right" label="操作" width="200">
                                        <template slot-scope="scope">
                                            <el-button @click="ship(scope.row)" size="small" type="text"
                                                       v-if="selectRow.orderStatus === 12 || selectRow.orderStatus === 13">
                                                发货
                                            </el-button>
                                            <el-button @click="lookLogistics(scope.row)" size="small" type="text"
                                                       v-if="scope.row.hasLogistics === 1
                                                       && (selectRow.orderStatus === 12 || selectRow.orderStatus === 13)">
                                                查看物流
                                            </el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-col>
                        </el-row>
                        <el-dialog :visible.sync="shipDialogVisible" title="发货" width="30%">
                            <el-form :model="shipForm" :rules="shipRules" label-width="100px" ref="shipForm">
                                <el-form-item label="快递公司" prop="logisticsCompanyId" v-if="shipForm.hasLogistics === 1">
                                    <el-select clearable placeholder="请选择" size="small"
                                               v-model="shipForm.logisticsCompanyId">
                                        <el-option :key="item.value" :label="item.label" :value="item.value"
                                                   v-for="item in logisticsOptions"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="运单号" prop="logisticsNo" v-if="shipForm.hasLogistics === 1">
                                    <el-input size="small" style="width: 200px;"
                                              v-model="shipForm.logisticsNo"></el-input>
                                </el-form-item>
                                <el-form-item label="商品密钥" prop="goodsSecret" v-if="shipForm.hasLogistics === 2">
                                    <el-input size="small" style="width: 200px;"
                                              v-model="shipForm.goodsSecret"></el-input>
                                </el-form-item>
                            </el-form>
                            <span class="dialog-footer" slot="footer">
                                <el-button @click="shipDialogVisible = false">取 消</el-button>
                                <el-button @click="saveShip" type="primary">发 货</el-button>
                            </span>
                        </el-dialog>
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