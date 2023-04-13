//let bastUrl="https://www.jcoolish.top/network/api/"
let bastUrl="http://127.0.0.1/api/"


function isTimeout(res,resolve){
	if(res.statusCode === 401){
		uni.showToast({
			title: '登录超时',
			icon: 'error'
		});	
		uni.setStorageSync('token', null)
		uni.navigateTo({
			url: '/pages/login/index'
		});
	}else if(res.statusCode === 200){
		resolve(res.data)
	}
}


const request={}

request.get = function(url,data){
	
	let token = uni.getStorageSync('token');
	const header={}
	
	if(token){
		header['Authorization']='Bearer '+token
	}
	return new Promise((resolve, reject)=>{
		uni.request({
		    url: bastUrl+url, //仅为示例，并非真实接口地址。
		    data:data,
		    header:header,
		    success: (res) => {
				isTimeout(res,resolve)
		    },
			fail: (err) =>{
				reject(err)
			}
		});
	})
}

request.post = function(url,data){
	
	let token = uni.getStorageSync('token');
	const header={}
	header['content-type']='application/json; charset=utf-8'
	
	if(token){
		header['Authorization'] = 'Bearer '+token;
	}
	return new Promise((resolve, reject)=>{
		uni.request({
		    url: bastUrl+url, //仅为示例，并非真实接口地址。
		    data:data,
			method:'POST',
		    header:header,
		    success: (res) => {
				isTimeout(res,resolve)
		    },
			fail: (err) =>{
				reject(err)
			}
		});
	})
}

request.bastUrl = bastUrl;

export default request 