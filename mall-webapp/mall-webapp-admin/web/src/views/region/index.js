export let data = {
    data() {
        return {
            treeFilterText: '',
            regionList: [],
            showRuleForm: false,
            ruleForm: {
                code: null,
                parentCode: null,
                name: null,
            },
            rules: {
                code: [
                    {required: true, message: '请输入地区code', trigger: 'blur'},
                    {type: 'number', message: 'code必须为数字值'}
                ],
                name: [
                    {required: true, message: '请输入地区名称', trigger: 'blur'},
                    {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                ]
            }
        }
    },
    created() {
        this.loadTree(0);
    },
    methods: {
        loadTree() {
            this.showRuleForm = false;
            this.ruleForm = {
                code: null,
                parentCode: null,
                name: null,
            };
            this.get('/region/regionTree', function (res) {
                this.regionList.splice(0);
                this.regionList = res.body;
            });
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        append(data) {
            this.showRuleForm = true;
            this.ruleForm = {
                code: null,
                parentCode: data.code,
                name: null
            }
        },
        appendRoot() {
            this.showRuleForm = true;
            this.ruleForm = {
                code: null,
                parentCode: 0,
                name: null,
            }
        },
        remove(data) {
            console.log(data);
            this.$confirm('确认删除么？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.get('/region/removeRegion?code=' + data.code, function (res) {
                    this.showMessage('success', '删除成功!');
                    this.loadTree();
                });
            });
        },
        save() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.post(this.ruleForm, '/region/saveRegion', function (res) {
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