export let data = {
    data() {
        return {
            ruleForm: {},
            rules: {
                shopName: [
                    {required: true, message: '店铺名不能为空', trigger: 'blur'},
                    {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                ],
                shopStar: [
                    {required: true, message: '店铺星级不能为空', trigger: 'blur'},
                ],
                status: [
                    {required: true, message: '状态不能为空', trigger: 'blur'}
                ]
            },
            upload: {
                images: [],
                tip: '只能上传jpg/png文件，且不超过500kb'
            },
            statusOptions: [{
                id: 1,
                name: '启用'
            }, {
                id: 0,
                name: '停用'
            }]
        }
    },
    created() {
        this.loadShop();
    },
    methods: {
        loadShop() {
            this.get('/shop/getShop', function (res) {
                this.ruleForm = res.body;
                this.upload.images.splice(0);
                if (this.ruleForm.shopLogo != null && this.ruleForm.shopLogo.length > 0) {
                    this.upload.images.push({
                        name: this.ruleForm.shopLogo.substring(this.ruleForm.shopLogo.lastIndexOf('/') + 1, this.ruleForm.shopLogo.length),
                        url: this.ruleForm.shopLogo
                    });
                }
            });
        },
        uploadImagesChange(fileList) {
            if (fileList != null && fileList.length >= 1) {
                this.ruleForm.shopLogo = fileList[0].url;
            } else {
                this.ruleForm.shopLogo = null;
            }
        },
        save() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.post(this.ruleForm, '/shop/saveShop', function (res) {
                        this.showMessage("success", "保存成功");
                        this.loadShop();
                    });
                }
            });
        }
    }
}