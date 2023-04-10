import axios from 'axios'
import router from "@/router";
import { Message } from 'element-ui';

let baseURL = '/api'

const http = axios.create({
    baseURL,
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json; charset=utf-8'
    }
})

// 请求拦截
http.interceptors.request.use( config =>{
    let token = localStorage.getItem('token')
    if(token){
        config.headers['Authorization'] = 'Bearer '+token;// 请求头带上token
    }
    return config;
    },error =>{
    return Promise.reject(error)
})

//响应拦截
http.interceptors.response.use( response =>{

    let res = response.data;
    // 如果是返回的文件
    if (response.config.responseType === 'blob') {
        return res
    }
    // 兼容服务端返回的字符串数据
    if (typeof res === 'string') {
        res = res ? JSON.parse(res) : res
    }

    return res;
    },
    error =>{
       // console.log("错误拦截",error)
        if(error.response.status === 401){
            Message.error("您的登录过期了，请重新登录")
            localStorage.setItem("token","")
            router.push("/login")
        }
        if(error.response.status === 403){
            Message.error("您的没有权限访问该资源")
        }
        return Promise.reject(error)
})

http.getBaseURL = function () {
    return baseURL;
}

export default http;