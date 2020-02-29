export let data = {
    data() {
        return {
            activeIndex: '',
            menuList: [{
                menuId: '1000',
                menuName: ''
            }],
            menuIdDict: {},
            menuUrlDict: {},
        }
    },
    created() {
        this.get('/menu-list/menuTree', function (res) {
            this.menuList.splice(0);
            this.menuIdDict = {};
            this.menuUrlDict = {};
            this.menuList = res.body;
            this.recursiveMenuTree(res.body);
            let menuUrl = location.hash.replace("#", "").replace("/", "");
            menuUrl = menuUrl.length === 0 ? 'index' : menuUrl;
            this.activeIndex = this.menuIdDict[menuUrl];
        });
    },
    methods: {
        recursiveMenuTree(menuList) {
            if (menuList == null || menuList.length <= 0) {
                return;
            }
            menuList.forEach(item => {
                this.menuIdDict[item.url] = item.menuId;
                this.menuUrlDict[item.menuId] = item.url;
                this.recursiveMenuTree(item.children);
            });
        },
        handleSelect(index) {
            let url = this.menuUrlDict[index];
            if (url != null && url.length > 0) {
                if (url.startsWith('http://') || url.startsWith('https://')) {
                    window.open(url);
                    return;
                }
                window.location.href = '#/' + url;
            }
        }
    }
}