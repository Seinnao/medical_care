//let wsUri = `wss://www.jcoolish.top/api/chat-service/socket`;
//let wsUri = `wss://www.jcoolish.top/network/api/chat-service/socket`;
let wsUri = `ws://127.0.0.1/api/chat-service/socket`;

let socketOpen = false;

function createWebSocket(callback, nickname, role) {
	uni.connectSocket({
		url: `${wsUri}/${nickname}/${role}`
	});
	uni.onSocketOpen((res) => {
		console.log('WebSocket连接已打开！');
		//sendSock("哈哈哈哈")
		socketOpen = true
	})
	uni.onSocketMessage((res) => {
		console.log('收到服务器内容：' + res);
		let result = JSON.parse(res.data);
		callback(result)
	});
}

function sendSock(msg) {
	if (socketOpen) {
		uni.sendSocketMessage({
			data: msg
		});
	}
}

function closeSock() {
	console.log('WebSocket正在关闭',socketOpen);
	if (socketOpen) {
		uni.closeSocket(res =>{
			console.log('WebSocket开始关闭',socketOpen);
		})
		uni.onSocketClose((res) => {
			console.log('WebSocket 已关闭！');
		});
	}
}

const websocket={sendSock,createWebSocket,closeSock}

export default websocket;
