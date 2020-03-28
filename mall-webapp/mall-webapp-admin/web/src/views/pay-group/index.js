export let data = {
    data() {
        return {
            activeName: 'list',
            orderList: [],
            orderCount: 0,
            selection: [],
            queryForm: {
                orderId: '',
                status: null,
                page: 1,
                pageSize: 20
            },
            selectRow: null,
            orderStatusOptions: [{
                code: 0,
                name: '未支付'
            }, {
                code: 1,
                name: '已支付'
            }],
        }
    },
    created() {
        this.loadTable();
    },
    methods: {
        loadTable() {
            this.post(this.queryForm, '/order-pay-group/listPayGroup', function (res) {
                this.orderList.splice(0);
                res.body.forEach(item => {
                    item['statusRemark'] = item.status === 0 ? '未支付' : '已支付';
                    this.orderList.push(item);
                });
            });
            this.post(this.queryForm, '/order-pay-group/countPayGroup', function (res) {
                this.orderCount = res.body;
            });
        },
        checkResult() {
            let payGroup = this.selection[0];
            if (payGroup.status === 1) {
                this.showMessage('info', '订单已支付');
                return;
            }
            this.get('/order-pay-group/checkOrderStatus?payGroupId=' + payGroup.id, function (res) {
                this.showMessage('success', '查询成功');
                this.loadTable();
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