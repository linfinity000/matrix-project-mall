const toolbarOptions = [
    ["bold", "italic", "underline", "strike"], // 加粗 斜体 下划线 删除线
    ["blockquote", "code-block"], // 引用  代码块
    [{header: 1}, {header: 2}], // 1、2 级标题
    [{list: "ordered"}, {list: "bullet"}], // 有序、无序列表
    [{script: "sub"}, {script: "super"}], // 上标/下标
    [{indent: "-1"}, {indent: "+1"}], // 缩进
    [{size: ["small", false, "large", "huge"]}], // 字体大小
    [{header: [1, 2, 3, 4, 5, 6, false]}], // 标题
    [{color: []}, {background: []}], // 字体颜色、字体背景颜色
    [{font: []}], // 字体种类
    [{align: []}], // 对齐方式
    ["clean"], // 清除文本格式
    ["link", "image"] // 链接、图片
];
export let data = {
    props: {
        value: {
            type: String,
            default: null
        }
    },
    data() {
        return {
            quillUpdateImg: false,
            content: '',
            editorOption: {
                placeholder: '',
                theme: 'snow',
                modules: {
                    toolbar: {
                        container: toolbarOptions,
                        handlers: {
                            'image': function (value) {
                                if (value) {
                                    document.querySelector('.avatar-uploader input').click()
                                } else {
                                    this.quill.format('image', false);
                                }
                            }
                        }
                    }
                }
            }
        };
    },
    methods: {
        onEditorBlur() {
        },
        onEditorFocus() {
        },
        onEditorChange() {
            this.$emit("input", this.content);
        },
        uploadChange(fileList) {
            let quill = this.$refs['myQuillEditor'].quill;
            if (fileList != null && fileList.length > 0) {
                let length = quill.getSelection().index;
                quill.insertEmbed(length, "image", fileList[0].url);
                quill.setSelection(length + 1);
            } else {
                this.$message.error("图片插入失败");
            }
            this.quillUpdateImg = false;
        }
    },
    watch: {
        'value': {
            handler(val) {
                this.content = val;
            },
            deep: true,
            immediate: true,
        }
    }
};