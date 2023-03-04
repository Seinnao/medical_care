const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false, //关闭自动监测
  devServer: {
    port: 8029,
    // 后端请求转发，此配置仅开发环境有效
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8030',  // 后台接口域名
        ws: false,        //如果要代理 websockets，配置这个参数
        secure: false,  // 如果是https接口，需要配置这个参数
        changeOrigin: true,  //是否跨域
        pathRewrite:{    //让请求不带'/api'
          '^/api': '/'
        }
      }
    },
  },
})
