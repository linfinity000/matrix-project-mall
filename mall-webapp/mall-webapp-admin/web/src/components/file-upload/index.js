export let data = {
    props: {
        tip: {
            type: String,
            default: ''
        },
        fileList: {
            type: Array,
            default: () => []
        },
        listType: {
            type: String,
            default: 'text'
        },
        limit: {
            type: Number
        },
        type: {
            type: String,
            default: 'File'
        },
        change: {}
    },
    data() {
        return {
            upload: {
                action: {
                    'File': '/api/file/uploadFile',
                    'Image': '/api/file/uploadImage'
                }
            }
        };
    },
    methods: {
        uploadSuccess(res) {
            if (!res['isSuccess']) {
                this.showMessage('error', res['msg']);
                this.fileList.splice(0);
            } else {
                let fileList = res.body;
                if (fileList != null && fileList.length > 0) {
                    fileList.forEach(item => {
                        this.fileList.push({name: item.substring(item.lastIndexOf('/') + 1, item.length), url: item});
                    });
                }
            }
        },
        uploadRemove(file) {
            let removeIndex = null;
            this.fileList.forEach((item, index) => {
                if (item.url === file.url) {
                    removeIndex = index;
                }
            });
            this.fileList.splice(removeIndex, 1);
        },
        uploadExceed() {
            this.showMessage('error', '文件超过个数限制');
        }
    },
    watch: {
        fileList(fileList) {
            this.fileList = fileList;
            if (this.change != null) {
                this.change.call(this.change, fileList);
            }
        }
    }
};