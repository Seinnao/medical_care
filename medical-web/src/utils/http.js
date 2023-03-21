import axios from 'axios'

let baseURL = '/api'

const http = axios.create({
    baseURL,
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json; charset=utf-8'
    }
})

//请求拦截
http.interceptors.request.use( config =>{
    config.headers['token'] = localStorage.getItem('token');// 请求头带上token
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
    // 当权限验证不通过的时候给出提示
    if (res.code === 401 ) {
        router.push("/login")
    }
    return res;
    },error =>{
    return Promise.reject(error)
})

http.getBaseURL = function () {
    return baseURL;
}

export default http;