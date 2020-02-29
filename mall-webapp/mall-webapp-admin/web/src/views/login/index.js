export let data = {
    data() {
        return {
            username: '',
            password: ''
        }
    },
    created() {
    },
    methods: {
        loginClick() {
            if (this.username.length <= 0) {
                this.showMessage('error', '用户名不允许为空');
                return;
            }
            if (this.password.length <= 0) {
                this.showMessage('error', '密码不允许为空');
                return;
            }
            this.post({username: this.username, password: this.password}, '/admin-user/login', function (res) {
                this.storeAccessToken(res.body);
                window.location.href = '#/';
            });
        }
    }
}