let websock = null;
let global_callback = null;
let isConnect = false; //连接标识 避免重复连接
let rec; //断线重连后，延迟5秒重新创建WebSocket连接  rec用来存储延迟请求的代码

let wsUri = `ws://${location.host}/api/chat-service/socket`;

function createWebSocket(callback,username) {
    if (websock == null || typeof websock !== WebSocket) {
        initWebSocket(callback,username);
    }
}

function initWebSocket(callback,username) {
    global_callback = callback;
    // 初始化websocket
    websock = new WebSocket(wsUri+'/'+username);
    websock.onmessage = function (e) {
        websocketonmessage(e);
    };
    websock.onclose = function (e) {
        websocketclose(e);
    };
    websock.onopen = function () {
        websocketOpen();
    };

    // 连接发生错误的回调方法
    websock.onerror = function () {
        console.log("WebSocket连接发生错误");
    };
}

//定义重连函数
let reConnect = () => {
    console.log("尝试重新连接");
    if (isConnect) return; //如果已经连上就不在重连了
    rec && clearTimeout(rec);
    rec = setTimeout(function () { // 延迟5秒重连  避免过多次过频繁请求重连
        initWebSocket();
    }, 5000);
};

// 实际调用的方法
function sendSock(agentData) {
    if (websock.readyState === websock.OPEN) {
        // 若是ws开启状态
        websocketsend(agentData);
    } else if (websock.readyState === websock.CONNECTING) {
        // 若是 正在开启状态，则等待1s后重新调用
        setTimeout(function () {
            sendSock(agentData);
        }, 1000);
    } else {
        // 若未开启 ，则等待1s后重新调用
        setTimeout(function () {
            sendSock(agentData);
        }, 1000);
    }
}

function closeSock() {
    console.log('关闭了')
    websock.close();
}

// 数据接收
function websocketonmessage(msg) {
    // 收到信息为Blob类型时
    let result = null;
    if (msg.data instanceof Blob) {
        const reader = new FileReader();
        reader.readAsText(msg.data, "UTF-8");
        reader.onload = (e) => {
            result = JSON.parse(reader.result);
            //console.log("websocket收到", result);
            global_callback(result);
        };
    } else {
        result = JSON.parse(msg.data);
        //console.log("websocket收到", result);
        global_callback(result);
    }
}

// 数据发送
function websocketsend(agentData) {
    // console.log("发送数据：" + agentData);
    websock.send(agentData);
}

// 关闭
function websocketclose(e) {
    console.log("connection closed (" + e.code + ")");
}

function websocketOpen(e) {
    console.log("连接打开");
    isConnect = true
    //heartCheck.start(); //发送心跳 看个人项目需求
}

export { sendSock, createWebSocket, closeSock};