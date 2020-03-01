<template>
    <div>
        <file-upload :change="uploadChange" class="avatar-uploader" style="display: none;" type="Image"></file-upload>
        <quill-editor :options="editorOption" @blur="onEditorBlur()" @change="onEditorChange()"
                      @focus="onEditorFocus()" class="editor" ref="myQuillEditor"
                      style="position:relative;z-index: 20;" v-model="content">
        </quill-editor>
    </div>
</template>
<script>
    import {data} from './index.js';

    export default data;
</script>
<style type="text/css">
    .ql-editor {
        height: 500px;
        overflow: auto;
    }

    .ql-snow .ql-picker {
        height: auto;
    }
</style>