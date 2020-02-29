export let data = {
    data() {
        return {
            treeFilterText: '',
            menuList: [],
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
            this.get('/menu-list/menuTree', function (res) {
                this.menuList.splice(0);
                this.menuList = res.body;
                this.recursiveMenuTree(res.body);
            });
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.menuName.indexOf(value) !== -1;
        }
    },
    watch: {
        treeFilterText(val) {
            this.$refs.tree.filter(val);
        }
    },
}