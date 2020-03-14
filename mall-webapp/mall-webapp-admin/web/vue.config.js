const CompressionWebpackPlugin = require('compression-webpack-plugin');
const productionGzipExtensions = ['js', 'css'];
const isProduction = process.env.NODE_ENV === 'production';

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
                    '^/api': '/api'
                }
            }
        }
    },
    // 配置webpack
    configureWebpack: config => {
        if (isProduction) {
            // 开启gzip压缩
            config.plugins.push(new CompressionWebpackPlugin({
                algorithm: 'gzip',
                test: /\.js$|\.html$|\.json$|\.css/,
                threshold: 10240,
                minRatio: 0.8
            }))
        }
    }
}