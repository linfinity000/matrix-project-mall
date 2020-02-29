export default {
    install(Vue, options) {
        Vue.prototype.showMessage = function (type, msg) {
            this.$message({
                showClose: true,
                message: msg,
                type: type
            });
        };
        Vue.prototype.get = function (url, callback) {
            const loading = this.$loading({
                lock: true, text: '加载中',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            this.$http.get('/api' + url).then(function (res) {
                loading.close();
                if (res.body['isSuccess']) {
                    callback.call(this, res.body);
                } else {
                    if (res.body['resultCode'] === -1000) {
                        window.location.href = '#/Login'
                    } else {
                        this.showMessage('error', res.body['msg']);
                    }
                }
            }, function (error) {
                loading.close();
                this.showMessage('error', error.statusText);
            });
        };
        Vue.prototype.post = function (params, url, callback) {
            const loading = this.$loading({
                lock: true, text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            this.$http.post('/api' + url, params, {emulateJSON: false}).then(function (res) {
                loading.close();
                if (res.body['isSuccess']) {
                    callback.call(this, res.body);
                } else {
                    if (res.body['resultCode'] === -1000) {
                        window.location.href = '#/Login'
                    } else {
                        this.showMessage('error', res.body['msg']);
                    }
                }
            }, function (error) {
                loading.close();
                this.showMessage('error', error.statusText);
            });
        };
        Vue.prototype.storeAccessToken = function (accessToken) {
            sessionStorage.setItem('Access-Token', accessToken);
        };
        Vue.http.interceptors.push((request, next) => {
            let accessToken = sessionStorage.getItem('Access-Token')
            if (accessToken != null && accessToken !== '') {
                request.headers.set('Access-Token', accessToken);
            }
            next((response) => {
                return response
            })
        });
    }
};