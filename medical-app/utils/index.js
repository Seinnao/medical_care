import moment from 'moment'
import http from './http'


moment.locale('zh-cn');

export function imagesUrl(url) {
	return `${http.bastUrl}file-service/images/${url}`
}

export function calendar(time) {
	return moment(time).calendar(null, {
		sameDay: '[今天] hh:mm',
		nextDay: '[明天] hh:mm',
		nextWeek: 'dddd',
		lastDay: '[昨天] hh:mm',
		lastWeek: 'dddd',
		sameElse: 'YYYY/MM/DD'
	});
}

export function back() {
	uni.navigateBack({
		delta: 1
	})
}

export function chooseImage() {
	let token = uni.getStorageSync('token');
	const header = {}
	header['content-type'] = 'application/json; charset=utf-8'

	if (token) {
		header['Authorization'] = 'Bearer ' + token;
	}
	return new Promise((resolve, reject) => {
		//chooseImage
		uni.chooseImage({
			count: 1,
			sizeType: ['compressed'], //压缩图的方法
			//sourceType: ['album'], //从相册选择
			success: (res) => {
				console.log(res)
				uni.compressImage({
					src: res.tempFilePaths[0],
					quality: 80,
					width: '300px', //app设置
					compressedWidth: 300, //小程序
					success: res => {
						uni.uploadFile({
							url: http.bastUrl + "file-service/file/upload/avatar", //仅为示例，非真实的接口地址
							filePath: res.tempFilePath,
							header,
							name: 'file',
							success: (uploadFileRes) => {
								resolve(JSON.parse(uploadFileRes.data));
							}
						});
					}
				})
			}
		});
	})
}
