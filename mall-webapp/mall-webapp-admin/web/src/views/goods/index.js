export let data = {
    data() {
        return {
            activeName: 'list',
            atomsGoodsList: [],
            atomsGoodsCount: 0,
            selection: [],
            showDetail: false,
            queryForm: {
                atomsGoodsName: '',
                status: null,
                page: 1,
                pageSize: 20
            },
            skuLabels: [],
            attrLabels: [],
            skuSelect: [],
            newValue: {
                shows: {},
                values: {}
            },
            showRuleForm: false,
            ruleForm: {},
            rules: {
                salePrice: [
                    {required: true, message: '销售价不能为空', trigger: 'blur'}
                ],
                stock: [
                    {required: true, message: '库存不能为空', trigger: 'blur'}
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
                name: '禁用'
            }]
        }
    },
    created() {
        this.loadTable();
    },
    methods: {
        loadSkuLabel(atomsGoodsId) {
            this.skuLabels.splice(0);
            this.get('/goods/skuLabels?atomsGoodsId=' + atomsGoodsId, function (res) {
                if (res.body != null && res.body.length > 0) {
                    res.body.forEach(item => {
                        let skuValues = [];
                        if (item.skuValues != null && item.skuValues.length > 0) {
                            item.skuValues.forEach(item => {
                                skuValues.push({
                                    value: item,
                                    type: 'info'
                                });
                            });
                        }
                        this.skuLabels.push({
                            labelId: item.labelId,
                            labelName: item.labelName,
                            skuValues: skuValues
                        });
                    });
                    this.showRuleForm = false;
                } else {
                    try {
                        this.$refs.ruleForm.resetFields();
                    } catch (e) {
                    }
                    this.showRuleForm = true;
                    this.loadSingleGoods();
                }
            });
        },
        loadAttrLabel(atomsGoodsId, goodsId) {
            this.attrLabels.splice(0);
            goodsId = goodsId != null ? goodsId : '';
            this.get('/goods/attrLabels?atomsGoodsId=' + atomsGoodsId + '&goodsId=' + goodsId, function (res) {
                this.attrLabels = res.body;
            });
        },
        loadSingleGoods() {
            this.loadAttrLabel(this.selection[0].atomsGoodsId);
            this.skuSelect.splice(0);
            this.get('/goods/getGoodsByAtomsGoodsId?atomsGoodsId=' + this.selection[0].atomsGoodsId, function (res) {
                this.ruleForm = res.body;
                this.loadAttrLabel(this.selection[0].atomsGoodsId, res.body.goodsId);
            });
        },
        loadMultiGoods() {
            this.skuSelect.splice(0);
            for (let i = 0; i < this.skuLabels.length; i++) {
                let label = this.skuLabels[i];
                for (let j = 0; j < label.skuValues.length; j++) {
                    let value = label.skuValues[j];
                    if (value.type === 'success') {
                        this.skuSelect.push({labelId: label.labelId, skuValue: value.value});
                    }
                }
            }
            if (this.skuSelect.length === this.skuLabels.length && this.skuLabels.length > 0) {
                this.upload.images.splice(0);
                this.post(JSON.stringify(this.skuSelect), '/goods/getGoods', function (res) {
                    this.showRuleForm = true;
                    try {
                        this.$refs.ruleForm.resetFields();
                    } catch (e) {
                    }
                    this.ruleForm = res.body;
                    this.ruleForm.atomsGoodsId = this.ruleForm.atomsGoodsId != null ? this.ruleForm.atomsGoodsId : this.selection[0].atomsGoodsId;
                    if (this.ruleForm.imageUrl != null && this.ruleForm.imageUrl.length > 0) {
                        this.upload.images.push({
                            name: this.ruleForm.imageUrl.substring(this.ruleForm.imageUrl.lastIndexOf('/') + 1, this.ruleForm.imageUrl.length),
                            url: this.ruleForm.imageUrl
                        });
                    }
                    this.loadAttrLabel(this.selection[0].atomsGoodsId, res.body.goodsId);
                });
            }
        },
        loadTable() {
            this.activeName = 'list';
            this.showDetail = false;
            this.post(this.queryForm, '/atoms-goods/listAtomsGoods', function (res) {
                this.atomsGoodsList.splice(0);
                res.body.forEach(item => {
                    item['statusRemark'] = item.status === 1 ? '上架' : '下架';
                    this.atomsGoodsList.push(item);
                });
            });
            this.post(this.queryForm, '/atoms-goods/countAtomsGoods', function (res) {
                this.atomsGoodsCount = res.body;
            });
        },
        uploadImagesChange(fileList) {
            if (fileList != null && fileList.length >= 1) {
                this.ruleForm.imageUrl = fileList[0].url;
            } else {
                this.ruleForm.imageUrl = null;
            }
        },
        showNewValue(id) {
            this.newValue.shows[id] = true;
            this.newValue = JSON.parse(JSON.stringify(this.newValue));
            this.$nextTick(_ => {
                this.$refs['saveTagNewValue' + id][0].$refs.input.focus();
            });
        },
        handleSkuClick(id, value) {
            this.skuLabels.forEach(item => {
                if (item.labelId === id) {
                    item.skuValues.forEach(_ => {
                        _.type = 'info';
                    });
                    value.type = 'success';
                }
            });
            this.loadMultiGoods();
        },
        handleNewValueConfirm(id) {
            let value = this.newValue.values[id];
            if (value) {
                this.skuLabels.forEach(item => {
                    if (item.labelId === id) {
                        let isExist = false;
                        for (let i = 0; i < item.skuValues.length; i++) {
                            if (value === item.skuValues[i].value) {
                                isExist = true;
                                break;
                            }
                        }
                        if (!isExist) {
                            item.skuValues.push({type: 'info', value: value});
                        }
                    }
                });
            }
            this.newValue.shows[id] = false;
            this.newValue.values[id] = '';
        },
        edit() {
            this.loadSkuLabel(this.selection[0].atomsGoodsId);
            this.showDetail = true;
            this.activeName = 'detail';
        },
        save() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.ruleForm.skuLabels = this.skuSelect;
                    this.ruleForm.attrLabels = this.attrLabels;
                    this.post(this.ruleForm, '/goods/saveGoods', function (res) {
                        this.showMessage("success", "保存成功");
                        if (this.skuLabels.length > 0) {
                            this.loadMultiGoods();
                        } else {
                            this.loadSingleGoods();
                        }
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
                this.get('/goods/removeGoods?goodsId=' + this.ruleForm.goodsId, function (res) {
                    this.showMessage('success', '删除成功!');
                    if (this.skuLabels.length > 0) {
                        this.loadSkuLabel(this.selection[0].atomsGoodsId);
                    } else {
                        this.loadTable();
                    }
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