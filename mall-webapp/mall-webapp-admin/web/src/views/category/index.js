export let data = {
    data() {
        return {
            treeFilterText: '',
            categoryList: [],
            showRuleForm: false,
            ruleForm: {
                categoryId: null,
                parentId: null,
                categoryName: null,
            },
            rules: {
                categoryName: [
                    {required: true, message: '请输入分类名称', trigger: 'blur'},
                    {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                ]
            }
        }
    },
    created() {
        this.loadTree();
    },
    methods: {
        recursiveCategoryTree(categoryList) {
            if (categoryList == null || categoryList.length <= 0) {
                return;
            }
            categoryList.forEach(item => {
                this.recursiveCategoryTree(item.children);
            });
        },
        loadTree() {
            this.showRuleForm = false;
            this.ruleForm = {
                categoryId: null,
                parentId: null,
                categoryName: null,
            };
            this.get('/category/categoryTree', function (res) {
                this.categoryList.splice(0);
                this.categoryList = res.body;
                this.recursiveCategoryTree(res.body);
            });
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.categoryName.indexOf(value) !== -1;
        },
        nodeClick(data) {
            this.showRuleForm = true;
            this.ruleForm = {
                categoryId: data.categoryId,
                parentId: data.parentId,
                categoryName: data.categoryName,
            }
        },
        append(data) {
            this.showRuleForm = true;
            this.ruleForm = {
                categoryId: null,
                parentId: data.categoryId,
                categoryName: null,
            }
        },
        appendRoot() {
            this.showRuleForm = true;
            this.ruleForm = {
                categoryId: null,
                parentId: 0,
                categoryName: null,
            }
        },
        remove(data) {
            this.$confirm('确认删除么？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.get('/category/removeTree?categoryId=' + data.categoryId, function (res) {
                    this.showMessage('success', '删除成功!');
                    this.loadTree();
                });
            });
        },
        save() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.post(this.ruleForm, '/category/saveTree', function (res) {
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