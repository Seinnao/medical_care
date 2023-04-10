import App from './App'
import http from './utils/http'
import uView from 'uview-ui';
import Vue from 'vue'
import cuCustom from './colorui/components/cu-custom.vue'
import {imagesUrl,calendar} from "./utils/index"

Vue.use(uView);
Vue.component('cu-custom',cuCustom)

Vue.config.productionTip = false
Vue.prototype.http=http
Vue.prototype.imagesUrl = imagesUrl
Vue.prototype.$calendar = calendar; //时间处理

App.mpType = 'app'
const app = new Vue({
    ...App
})
app.$mount()
 