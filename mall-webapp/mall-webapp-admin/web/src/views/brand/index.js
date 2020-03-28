export let data = {
    data() {
        return {
            activeName: 'list',
            brandList: [],
            brandCount: 0,
            selection: [],
            showDetail: false,
            queryForm: {
                brandName: '',
                page: 1,
                pageSize: 20
            },
            ruleForm: {},
            upload: {
                images: [],
                tip: '只能上传jpg/png文件，且不超过500kb'
            },
            rules: {
                brandName: [
                    {required: true, message: '品牌名不能为空', trigger: 'blur'},
                    {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                ]
            }
        }
    },
    created() {
        this.loadTable();
    },
    methods: {
        loadTable() {
            this.activeName = 'list';
            this.showDetail = false;
            this.post(this.queryForm, '/brand/listBrand', function (res) {
                this.brandList.splice(0);
                res.body.forEach(item => {
                    this.brandList.push(item);
                });
            });
            this.post(this.queryForm, '/brand/countBrand', function (res) {
                this.brandCount = res.body;
            });
        },
        append() {
            try {
                this.$refs.ruleForm.resetFields();
            } catch (e) {
            }
            this.ruleForm = {
                brandId: null,
                brandName: '',
                brandUrl: '',
                brandLogo: ''
            };
            this.upload.images.splice(0);
            this.showDetail = true;
            this.activeName = 'detail';
        },
        detail(row) {
            try {
                this.$refs.ruleForm.resetFields();
            } catch (e) {
            }
            this.ruleForm = {
                brandId: row.brandId,
                brandName: row.brandName,
                brandUrl: row.brandUrl,
                brandLogo: row.brandLogo
            };
            this.upload.images.splice(0);
            if (this.ruleForm.brandLogo != null && this.ruleForm.brandLogo.length > 0) {
                this.upload.images.push({
                    name: this.ruleForm.brandLogo.substring(this.ruleForm.brandLogo.lastIndexOf('/') + 1, this.ruleForm.brandLogo.length),
                    url: this.ruleForm.brandLogo
                });
            }
            this.showDetail = true;
            this.activeName = 'detail';
        },
        save() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.post(this.ruleForm, '/brand/saveBrand', function (res) {
                        this.showMessage("success", "保存成功");
                        this.loadTable();
                    });
                }
            });
        },
        remove() {
            this.$confirm('确认删除么？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.get('/brand/removeBrand?brandId=' + this.selection[0].brandId, function (res) {
                    this.showMessage('success', '删除成功!');
                    this.loadTable();
                });
            });
        },
        uploadImagesChange(fileList) {
            if (fileList != null && fileList.length >= 1) {
                this.ruleForm.brandLogo = fileList[0].url;
            } else {
                this.ruleForm.brandLogo = null;
            }
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