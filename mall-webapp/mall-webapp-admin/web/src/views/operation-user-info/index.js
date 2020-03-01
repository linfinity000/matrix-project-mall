export let data = {
    data() {
        return {
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
            }],
            shopOptions: []
        }
    },
    created() {
        this.loadUser();
        this.loadShop();
    },
    methods: {
        loadShop() {
            this.shopOptions.splice(0);
            this.get('/shop/listValidShop', function (res) {
                res.body.forEach(item => {
                    this.shopOptions.push({
                        shopId: item.shopId,
                        shopName: item.shopName
                    });
                })
            });
        },
        loadUser() {
            this.get('/admin-user/getUser', function (res) {
                this.ruleForm = {
                    userId: res.body.userId,
                    username: res.body.username,
                    password: null,
                    isDefault: res.body.isDefault,
                    shopId: res.body.shopId,
                    status: res.body.status,
                };
            });
        },
        save() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.post(this.ruleForm, '/admin-user/saveUser', function (res) {
                        this.showMessage("success", "保存成功");
                        this.loadUser();
                    });
                }
            });
        },
    }
}