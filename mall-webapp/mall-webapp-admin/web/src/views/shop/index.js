export let data = {
    data() {
        return {
            activeName: 'list',
            userList: [],
            userCount: 0,
            selection: [],
            showDetail: false,
            queryForm: {
                username: '',
                status: null,
                page: 1,
                pageSize: 20
            },
            ruleForm: {},
            rules: {
                username: [
                    {required: true, message: '用户名不能为空', trigger: 'blur'},
                    {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '密码不能为空', trigger: 'blur'},
                    {min: 6, max: 32, message: '长度在 6 到 32 个字符', trigger: 'blur'}
                ],
                status: [
                    {required: true, message: '状态不能为空', trigger: 'blur'}
                ]
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
            this.post(this.queryForm, '/admin-user/listUser', function (res) {
                this.userList.splice(0);
                res.body.forEach(item => {
                    item['userGrantRemark'] = item.shopId != null && item.shopId.length > 0 ? '运营用户' : '管理员用户';
                    item['statusRemark'] = item.status === 1 ? '启用' : '停用';
                    this.userList.push(item);
                });
            });
            this.post(this.queryForm, '/admin-user/countUser', function (res) {
                this.userCount = res.body;
            });
        },
        append() {
            try {
                this.$refs.ruleForm.resetFields();
            } catch (e) {
            }
            this.ruleForm = {
                userId: null,
                username: '',
                password: null,
                isDefault: 0,
                shopId: null,
                status: null,
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
                userId: this.selection[0].userId,
                username: this.selection[0].username,
                password: null,
                isDefault: this.selection[0].isDefault,
                shopId: this.selection[0].shopId,
                status: this.selection[0].status,
            };
            this.showDetail = true;
            this.activeName = 'detail';
        },
        save() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.post(this.ruleForm, '/admin-user/saveUser', function (res) {
                        this.showMessage("success", "保存成功");
                        this.loadTable();
                    });
                }
            });
        },
        remove() {
            let user = this.selection[0];
            if (user.isDefault === 1) {
                this.showMessage('error', '默认用户不允许删除');
                return;
            }
            this.$confirm('确认删除么？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.get('/admin-user/removeUser?userId=' + user.userId, function (res) {
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