export let data = {
    data() {
        return {
            activeName: 'list',
            atomsGoodsList: [],
            atomsGoodsCount: 0,
            showSkuInput: false,
            skuInputValue: '',
            showAttrInput: false,
            attrInputValue: '',
            selection: [],
            showDetail: false,
            queryForm: {
                atomsGoodsName: '',
                status: null,
                page: 1,
                pageSize: 20
            },
            ruleForm: {
                skuList: [],
                attrList: [],
                banners: [],
            },
            rules: {
                atomsGoodsName: [
                    {required: true, message: '商品名不能为空', trigger: 'blur'},
                    {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                ],
                brandId: [
                    {required: true, message: '品牌不能为空', trigger: 'blur'}
                ],
                categoryId: [
                    {required: true, message: '分类不能为空', trigger: 'blur'}
                ],
                status: [
                    {required: true, message: '状态不能为空', trigger: 'blur'}
                ]
            },
            upload: {
                images: [],
                bannerImages: [],
                tip: '只能上传jpg/png文件，且不超过500kb'
            },
            statusOptions: [{
                id: 1,
                name: '启用'
            }, {
                id: 0,
                name: '禁用'
            }],
            brandOptions: [],
            categoryTree: [],
        }
    },
    created() {
        this.loadTable();
    },
    methods: {
        loadBrand() {
            this.post({page: 1, pageSize: 100}, '/brand/listBrand', function (res) {
                this.brandOptions = res.body;
            })
        },
        loadCategory() {
            this.get('/category/categoryTree', function (res) {
                this.categoryTree = res.body;
                this.recursiveCategoryTree(this.categoryTree);
            })
        },
        recursiveCategoryTree(categoryList) {
            if (categoryList == null || categoryList.length <= 0) {
                return;
            }
            categoryList.forEach(item => {
                item.id = item.categoryId;
                item.name = item.categoryName;
                this.recursiveCategoryTree(item.children);
            });
        },
        loadTable() {
            this.loadBrand();
            this.loadCategory();
            this.activeName = 'list';
            this.showDetail = false;
            this.post(this.queryForm, '/atoms-goods/listAtomsGoods', function (res) {
                this.atomsGoodsList.splice(0);
                res.body.forEach(item => {
                    item['statusRemark'] = item.status === 1 ? '启用' : '禁用';
                    this.atomsGoodsList.push(item);
                });
            });
            this.post(this.queryForm, '/atoms-goods/countAtomsGoods', function (res) {
                this.atomsGoodsCount = res.body;
            });
        },
        uploadImagesChange(fileList) {
            if (fileList != null && fileList.length >= 1) {
                this.ruleForm.atomsGoodsImage = fileList[0].url;
            } else {
                this.ruleForm.atomsGoodsImage = null;
            }
        },
        uploadBannerImagesChange(fileList) {
            this.ruleForm.banners.splice(0);
            if (fileList != null && fileList.length >= 1) {
                fileList.forEach(item => {
                    this.ruleForm.banners.push({
                        imageUrl: item.url
                    });
                });
            }
        },
        append() {
            try {
                this.$refs.ruleForm.resetFields();
            } catch (e) {
            }
            this.ruleForm = {
                attrList: [],
                skuList: [],
                banners: [],
            };
            this.upload.images.splice(0);
            this.upload.bannerImages.splice(0);
            this.showDetail = true;
            this.activeName = 'detail';
        },
        edit() {
            try {
                this.$refs.ruleForm.resetFields();
            } catch (e) {
            }
            this.upload.images.splice(0);
            this.upload.bannerImages.splice(0);
            this.get('/atoms-goods/getAtomsGoods?atomsGoodsId=' + this.selection[0].atomsGoodsId, function (res) {
                this.ruleForm = res.body;
                if (this.ruleForm.atomsGoodsImage != null && this.ruleForm.atomsGoodsImage.length > 0) {
                    this.upload.images.push({
                        name: this.ruleForm.atomsGoodsImage.substring(this.ruleForm.atomsGoodsImage.lastIndexOf('/') + 1, this.ruleForm.atomsGoodsImage.length),
                        url: this.ruleForm.atomsGoodsImage
                    });
                }
                if (this.ruleForm.banners != null && this.ruleForm.banners.length > 0) {
                    this.ruleForm.banners.forEach(item => {
                        this.upload.bannerImages.push({
                            name: item.imageUrl.substring(item.imageUrl.lastIndexOf('/') + 1, item.imageUrl.length),
                            url: item.imageUrl
                        });
                    })
                }
                this.showDetail = true;
                this.activeName = 'detail';
            });
        },
        save() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.post(this.ruleForm, '/atoms-goods/saveAtomsGoods', function (res) {
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
                this.get('/atoms-goods/removeAtomsGoods?shopId=' + this.selection[0].atomsGoodsId, function (res) {
                    this.showMessage('success', '删除成功!');
                    this.loadTable();
                });
            });
        },
        handleShowSkuInput() {
            this.showSkuInput = true;
            this.$nextTick(_ => {
                this.$refs['skuInput'].$refs.input.focus();
            });
        },
        newSkuLabel() {
            this.showSkuInput = false;
            for (let i = 0; i < this.ruleForm.skuList.length; i++) {
                if (this.ruleForm.skuList[i].skuName === this.skuInputValue) {
                    this.skuInputValue = '';
                    return;
                }
            }
            if (this.skuInputValue != null && this.skuInputValue.length > 0) {
                this.ruleForm.skuList.push({
                    skuName: this.skuInputValue
                });
            }
            this.skuInputValue = '';
        },
        delSkuLabel(skuName) {
            let index = -1;
            for (let i = 0; i < this.ruleForm.skuList.length; i++) {
                if (this.ruleForm.skuList[i].skuName === skuName) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                this.ruleForm.skuList.splice(index, 1);
            }
        },
        handleShowAttrInput() {
            this.showAttrInput = true;
            this.$nextTick(_ => {
                this.$refs['attrInput'].$refs.input.focus();
            });
        },
        newAttrLabel() {
            this.showAttrInput = false;
            for (let i = 0; i < this.ruleForm.attrList.length; i++) {
                if (this.ruleForm.attrList[i].attrName === this.attrInputValue) {
                    this.attrInputValue = '';
                    return;
                }
            }
            if (this.attrInputValue != null && this.attrInputValue.length > 0) {
                this.ruleForm.attrList.push({
                    attrName: this.attrInputValue
                });
            }
            this.attrInputValue = '';
        },
        delAttrLabel(attrName) {
            let index = -1;
            for (let i = 0; i < this.ruleForm.attrList.length; i++) {
                if (this.ruleForm.attrList[i].attrName === attrName) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                this.ruleForm.attrList.splice(index, 1);
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