<template>
    <div>
        <top-menu></top-menu>
        <el-container>
            <el-main>
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
                                <el-select :disabled="true" clearable placeholder="请选择" size="small"
                                           v-model="ruleForm.shopId">
                                    <el-option :key="item.shopId" :label="item.shopName" :value="item.shopId"
                                               v-for="item in shopOptions"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="状态" prop="status">
                                <el-select :disabled="true" placeholder="请选择" size="small"
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