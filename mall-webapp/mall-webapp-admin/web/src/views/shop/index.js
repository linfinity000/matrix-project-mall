export let data = {
    data() {
        return {
            activeName: 'list',
            shopList: [],
            shopCount: 0,
            selection: [],
            showDetail: false,
            queryForm: {
                shopName: '',
                status: null,
                page: 1,
                pageSize: 20
            },
            ruleForm: {},
            rules: {
                shopName: [
                    {required: true, message: '店铺名不能为空', trigger: 'blur'},
                    {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
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
        this.loadTable();
    },
    methods: {
        loadTable() {
            this.activeName = 'list';
            this.showDetail = false;
            this.post(this.queryForm, '/shop/listShop', function (res) {
                this.shopList.splice(0);
                res.body.forEach(item => {
                    item['statusRemark'] = item.status === 1 ? '启用' : '停用';
                    this.shopList.push(item);
                });
            });
            this.post(this.queryForm, '/shop/countShop', function (res) {
                this.shopCount = res.body;
            });
        },
        uploadImagesChange(fileList) {
            if (fileList != null && fileList.length >= 1) {
                this.ruleForm.shopLogo = fileList[0].url;
            } else {
                this.ruleForm.shopLogo = null;
            }
        },
        append() {
            try {
                this.$refs.ruleForm.resetFields();
            } catch (e) {
            }
            this.ruleForm = {
                shopId: null,
                shopName: '',
                shopLogo: null,
                shopDesc: null,
                isDefault: 0,
                shopStar: 5,
                status: null,
            };
            this.upload.images.splice(0);
            this.showDetail = true;
            this.activeName = 'detail';
        },
        edit() {
            try {
                this.$refs.ruleForm.resetFields();
            } catch (e) {
            }
            this.ruleForm = {
                shopId: this.selection[0].shopId,
                shopName: this.selection[0].shopName,
                shopLogo: this.selection[0].shopLogo,
                shopDesc: this.selection[0].shopDesc,
                isDefault: this.selection[0].isDefault,
                shopStar: this.selection[0].shopStar,
                status: this.selection[0].status,
            };
            this.upload.images.splice(0);
            if (this.ruleForm.shopLogo != null && this.ruleForm.shopLogo.length > 0) {
                this.upload.images.push({
                    name: this.ruleForm.shopLogo.substring(this.ruleForm.shopLogo.lastIndexOf('/') + 1, this.ruleForm.shopLogo.length),
                    url: this.ruleForm.shopLogo
                });
            }
            this.showDetail = true;
            this.activeName = 'detail';
        },
        save() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.post(this.ruleForm, '/shop/saveShop', function (res) {
                        this.showMessage("success", "保存成功");
                        this.loadTable();
                    });
                }
            });
        },
        remove() {
            let shop = this.selection[0];
            if (shop.isDefault === 1) {
                this.showMessage('error', '默认店铺不允许删除');
                return;
            }
            this.$confirm('确认删除么？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.get('/shop/removeShop?shopId=' + shop.shopId, function (res) {
                    this.showMessage('success', '删除成功!');
                    this.loadTable();
                });
            });
        },
        handleSelectionChange(val) {
            this.selection = val;
        },
        handleSizeChange(val) {
            this.queryForm.pageSize = val;
            this.loadTable();
        },
        handleCurrentChange(val) {
            this.queryForm.page = val;
            this.loadTable();
        }
    }
}