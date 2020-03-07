export let data = {
    data() {
        return {
            activeName: 'list',
            orderList: [],
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
                address: ''
            },
            addressOptions: [],
            addressRules: {
                regions: [
                    {required: true, message: '请选择地区', trigger: 'blur'},
                ],
                address: [
                    {required: true, message: '请输入详细地址', trigger: 'blur'},
                    {min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur'}
                ]
            }
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
                address: row.address
            };
            this.showDetail = true;
            this.activeName = 'detail';
        },
        editAddress() {
            this.$confirm('确认修改么？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.post(this.addressForm, '/order/saveOrderAddress', function (res) {
                    this.loadTable(true);
                });
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