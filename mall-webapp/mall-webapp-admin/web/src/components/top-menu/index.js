export let data = {
    data() {
        return {
            activeIndex: '',
            menuList: [],
            menuIdDict: {},
            menuUrlDict: {},
            avatarSpan: ''
        }
    },
    created() {
        this.get('/menu/menuTree', function (res) {
            this.menuList.splice(0);
            this.menuIdDict = {};
            this.menuUrlDict = {};
            this.menuList = res.body;
            this.recursiveMenuTree(res.body);
            let menuUrl = location.hash.replace("#", "").replace("/", "");
            menuUrl = menuUrl.length === 0 ? 'index' : menuUrl;
            this.activeIndex = this.menuIdDict[menuUrl];
        });
        this.get('/admin-user/getUser', function (res) {
            let username = res.body.username;
            this.avatarSpan = username != null ? username.substring(0, 1).toUpperCase() : "";
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
        },
        userInfo() {
            location.href = '#/OperationUserInfo';
        },
        exit() {
            this.get('/admin-user/exit', function (res) {
                location.reload();
            });
        }
    }
}