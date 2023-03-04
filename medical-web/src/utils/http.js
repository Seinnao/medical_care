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
    return response;
    },error =>{
    return Promise.reject(error)
})

http.getBaseURL = function () {
    return baseURL;
}

export default http;