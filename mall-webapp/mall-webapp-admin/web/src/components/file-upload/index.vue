<template>
    <div>
        <el-upload :action="upload.action[type]" :file-list="fileList" :limit="limit" :list-type="listType"
                   :on-exceed="uploadExceed" :on-remove="uploadRemove" :on-success="uploadSuccess">
            <el-button size="small" type="primary">点击上传</el-button>
            <div class="el-upload__tip" slot="tip" style="margin-top:0px;">({{tip}})</div>
        </el-upload>
    </div>
</template>
<script>
    import {data} from './index.js';

    export default data;
</script>
<style type="text/css">
    .el-upload-list__item {
        transition: none;
    }
</style>