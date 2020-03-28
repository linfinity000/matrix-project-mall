export let data = {
    data() {
        return {
            activeName: 'list',
            logList: [],
            logCount: 0,
            queryForm: {
                name: '',
                page: 1,
                pageSize: 20
            }
        }
    },
    created() {
        this.loadTable();
    },
    methods: {
        loadTable() {
            this.activeName = 'list';
            this.post(this.queryForm, '/log/listOpLog', function (res) {
                this.logList.splice(0);
                res.body.forEach(item => {
                    this.logList.push(item);
                });
            });
            this.post(this.queryForm, '/log/countOpLog', function (res) {
                this.logCount = res.body;
            });
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