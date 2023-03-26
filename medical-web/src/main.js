import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/gloable.css'
import http from "@/utils/http";
import {imagesUrl} from "@/utils";
import moment from 'moment' //时间处理插件

Vue.config.productionTip = false
Vue.use(ElementUI, { size: "mini" })

moment.locale('zh-cn');
Vue.prototype.$moment = moment; //时间处理
//$moment(time).calendar() 处理方法获得日历时间

Vue.prototype.http=http
Vue.prototype.imagesUrl = imagesUrl

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
