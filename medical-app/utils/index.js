import moment from 'moment'

moment.locale('zh-cn');

export function imagesUrl(url) {
	return `http://127.0.0.1/api/file-service/images/${url}`
}

export function calendar(time) {
	return moment(time).calendar(null, {
		sameDay: '[今天] hh:mm',
		nextDay: '[明天] hh:mm',
		nextWeek: 'dddd',
		lastDay: '[昨天] hh:mm',
		lastWeek: 'dddd',
		sameElse: 'YYYY-MM-DD'
	});
}
