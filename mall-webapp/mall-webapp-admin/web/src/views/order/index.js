export let data = {
    data() {
        return {
            activeName: 'list',
            orderList: [],
            orderGoodsList: [],
            orderCount: 0,
            showDetail: false,
            queryForm: {
                orderId: '',
                orderStatus: null,
                page: 1,
                pageSize: 20
            },
            selectRow: null,
            orderStatusOptions: [],
            orderStatusDict: {},
            addressForm: {
                orderId: '',
                regions: [],
                address: '',
                linkName: '',
                mobile: '',
            },
            addressOptions: [],
            addressRules: {
                regions: [
                    {required: true, message: '请选择地区', trigger: 'blur'},
                ],
                linkName: [
                    {required: true, message: '请输入联系人', trigger: 'blur'},
                    {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                ],
                mobile: [
                    {required: true, message: '请输入手机号', trigger: 'blur'},
                    {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                ],
                address: [
                    {required: true, message: '请输入详细地址', trigger: 'blur'},
                    {min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur'}
                ]
            },
            logisticsOptions: [],
            shipForm: {
                orderGoodsId: null,
                logisticsCompanyId: null,
                logisticsNo: '',
                goodsSecret: '',
                hasLogistics: null,
            },
            shipFormEdit: false,
            shipRules: {
                logisticsCompanyId: [
                    {required: true, message: '请选择快递公司', trigger: 'blur'},
                ],
                logisticsNo: [
                    {required: true, message: '请输入运单号', trigger: 'blur'},
                    {min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur'}
                ],
                goodsSecret: [
                    {required: true, message: '请输入商品密钥', trigger: 'blur'},
                    {min: 1, max: 1000, message: '长度在 1 到 1000 个字符', trigger: 'blur'}
                ]
            },
            shipDialogVisible: false
        }
    },
    created() {
        this.orderStatusOptions.splice(0);
        this.get('/order/orderStatus', function (res) {
            res.body.forEach(item => {
                this.orderStatusOptions.push({
                    code: item.code,
                    name: item.name
                });
                this.orderStatusDict[item.code] = item.name;
            });
        });
        this.logisticsOptions.splice(0);
        this.post({logisticsName: '', page: 1, pageSize: 10000}, '/logistics/listLogistics', function (res) {
            res.body.forEach(item => {
                this.logisticsOptions.push({
                    label: item.logisticsName,
                    value: item.logisticsId
                });
            });
        });
        this.addressOptions.splice(0);
        this.get('/region/regionTree', function (res) {
            this.addressOptions = res.body;
        });
        this.loadTable();
    },
    methods: {
        loadTable(showDetail) {
            if (!showDetail) {
                this.activeName = 'list';
                this.showDetail = false;
            }
            this.post(this.queryForm, '/order/listOrder', function (res) {
                this.orderList.splice(0);
                res.body.forEach(item => {
                    item['orderStatusRemark'] = this.orderStatusDict[item.orderStatus];
                    this.orderList.push(item);
                });
            });
            this.post(this.queryForm, '/order/countOrder', function (res) {
                this.orderCount = res.body;
            });
        },
        detail(row) {
            this.selectRow = row;
            this.addressForm = {
                orderId: row.orderId,
                regions: [row.provinceCode, row.cityCode, row.areaCode],
                address: row.address,
                linkName: row.linkName,
                mobile: row.mobile
            };
            this.shipFormEdit = false;
            this.loadOrderGoodsList();
        },
        loadOrderGoodsList() {
            this.orderGoodsList.splice(0);
            this.get('/order/listOrderGoods?orderId=' + this.selectRow.orderId, function (res) {
                this.orderGoodsList = res.body;
                this.showDetail = true;
                this.activeName = 'detail';
            });
        },
        editAddress() {
            this.$refs.addressForm.validate((valid) => {
                if (valid) {
                    this.$confirm('确认修改么？', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.shipFormEdit = false;
                        this.post(this.addressForm, '/order/saveOrderAddress', function (res) {
                            this.loadTable(true);
                        });
                    });
                }
            });
        },
        lookLogistics(row) {
            if (row.logisticsNo == null || row.logisticsNo === '') {
                this.showMessage('error', '暂无物流单号');
                return;
            }
        },
        ship(row) {
            this.shipForm = {
                orderGoodsId: row.id,
                logisticsCompanyId: row.logisticsCompanyId,
                logisticsNo: row.logisticsNo,
                goodsSecret: row.goodsSecret,
                hasLogistics: row.hasLogistics
            };
            this.shipDialogVisible = true;
        },
        saveShip() {
            this.$refs.shipForm.validate((valid) => {
                if (valid) {
                    this.post(this.shipForm, '/order/saveShip', function (res) {
                        this.shipDialogVisible = false;
                        this.loadOrderGoodsList();
                        this.loadTable(true);
                    });
                }
            });
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