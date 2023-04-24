import App from './App'
import http from './utils/http'
import uView from 'uview-ui';
import Vue from 'vue'
import cuCustom from './colorui/components/cu-custom.vue'
import {imagesUrl,calendar,back} from "./utils/index"

// #ifdef MP-WEIXIN
import towxml from './static/towxml/towxml'
Vue.component('towxml',towxml)
// #endif

// Markdown解析器
// #ifdef APP
import MarkdownIt from 'markdown-it';
let md = new MarkdownIt();
Vue.prototype.md=md
// #endif

Vue.use(uView);
Vue.component('cu-custom',cuCustom)


Vue.config.productionTip = false
Vue.prototype.http=http
Vue.prototype.imagesUrl = imagesUrl
Vue.prototype.$calendar = calendar; //时间处理
Vue.prototype.back = back  //回退
Vue.prototype.towxml = require('./static/towxml/index.js')

App.mpType = 'app'
const app = new Vue({
    ...App
})
app.$mount()
 