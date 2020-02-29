module.exports = {
    publicPath: process.env.NODE_ENV === 'development' ? '/' : '/html',
    outputDir: '../src/main/webapp/html',
    assetsDir: 'static',
    indexPath: 'index.html',
    productionSourceMap: false,
    devServer: {
        host: '0.0.0.0',
        port: 8010,
        proxy: {
            '/api': {
                target: 'http://localhost:8081',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': '/'
                }
            }
        }
    }
}