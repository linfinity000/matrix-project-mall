export let data = {
    data() {
        return {
            treeFilterText: '',
            menuList: [],
            isReadOnly: false,
            showRuleForm: false,
            ruleForm: {
                menuId: null,
                parentId: null,
                menuName: null,
                url: null,
                orderBy: 0,
            },
            rules: {
                menuName: [
                    {required: true, message: '请输入菜单名称', trigger: 'blur'},
                    {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                ],
                url: [
                    {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur'}
                ]
            }
        }
    },
    created() {
        this.loadTree();
    },
    methods: {
        recursiveMenuTree(menuList) {
            if (menuList == null || menuList.length <= 0) {
                return;
            }
            menuList.forEach(item => {
                this.recursiveMenuTree(item.children);
            });
        },
        loadTree() {
            this.showRuleForm = false;
            this.ruleForm = {
                menuId: null,
                parentId: null,
                menuName: null,
                url: null,
                orderBy: 0,
            };
            this.get('/menu-list/menuTree', function (res) {
                this.menuList.splice(0);
                this.menuList = res.body;
                this.recursiveMenuTree(res.body);
            });
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.menuName.indexOf(value) !== -1;
        },
        nodeClick(data) {
            this.showRuleForm = true;
            if (data.isDefault === 1) {
                this.isReadOnly = true;
            }
            this.ruleForm = {
                menuId: data.menuId,
                parentId: data.parentId,
                menuName: data.menuName,
                url: data.url,
                orderBy: data.orderBy
            }
        },
        append(data) {
            this.showRuleForm = true;
            this.isReadOnly = false;
            this.ruleForm = {
                menuId: null,
                parentId: data.menuId,
                menuName: null,
                url: null,
                orderBy: 0
            }
        },
        appendRoot() {
            this.showRuleForm = true;
            this.isReadOnly = false;
            this.ruleForm = {
                menuId: null,
                parentId: 0,
                menuName: null,
                url: null,
                orderBy: 0
            }
        },
        remove(data) {
            this.$confirm('确认删除么？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.get('/menu-list/removeTree?menuId=' + data.menuId, function (res) {
                    this.showMessage('success', '删除成功!');
                    this.loadTree();
                });
            });
        },
        save() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.post(this.ruleForm, '/menu-list/saveTree', function (res) {
                        this.showMessage('success', '保存成功!');
                        this.loadTree();
                    });
                }
            });
        }
    },
    watch: {
        treeFilterText(val) {
            this.$refs.tree.filter(val);
        }
    },
}