export let data = {
    data() {
        return {
            activeName: 'list',
            logisticsList: [],
            logisticsCount: 0,
            selection: [],
            showDetail: false,
            queryForm: {
                logisticsName: '',
                page: 1,
                pageSize: 20
            },
            ruleForm: {},
            rules: {
                logisticsName: [
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
            this.post(this.queryForm, '/logistics/listLogistics', function (res) {
                this.logisticsList.splice(0);
                res.body.forEach(item => {
                    this.logisticsList.push(item);
                });
            });
            this.post(this.queryForm, '/logistics/countLogistics', function (res) {
                this.logisticsCount = res.body;
            });
        },
        append() {
            try {
                this.$refs.ruleForm.resetFields();
            } catch (e) {
            }
            this.ruleForm = {
                logisticsId: null,
                logisticsName: ''
            };
            this.showDetail = true;
            this.activeName = 'detail';
        },
        edit() {
            try {
                this.$refs.ruleForm.resetFields();
            } catch (e) {
            }
            this.ruleForm = {
                logisticsId: this.selection[0].logisticsId,
                logisticsName: this.selection[0].logisticsName
            };
            this.showDetail = true;
            this.activeName = 'detail';
        },
        save() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.post(this.ruleForm, '/logistics/saveLogistics', function (res) {
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
                this.get('/logistics/removeLogistics?logisticsId=' + this.selection[0].logisticsId, function (res) {
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