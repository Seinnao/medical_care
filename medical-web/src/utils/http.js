import axios from 'axios'

let baseUrl = '/api'

const request = axios.create({
    baseUrl,
    timeout: 5000
})



export default request;