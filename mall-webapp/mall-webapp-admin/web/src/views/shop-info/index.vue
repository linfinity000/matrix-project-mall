<template>
    <div>
        <top-menu></top-menu>
        <el-container>
            <el-main>
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
                                <el-rate :colors="['#99A9BF', '#F7BA2A', '#FF9900']" :disabled="true"
                                         style="margin-top: 10px;"
                                         v-model="ruleForm.shopStar"></el-rate>
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