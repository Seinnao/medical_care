<template>
	<view>
		<uni-nav-bar dark :fixed="true" shadow background-color="#007AFF" status-bar left-icon="left" left-text="返回"
					title="聊天" @clickLeft="back" />
		<view class="content" @touchstart="hideDrawer">
			<scroll-view class="msg-list" :class="popupLayerClass" scroll-y="true"
				:scroll-with-animation="scrollAnimation" :scroll-top="scrollTop" :scroll-into-view="scrollToView"
				@scrolltoupper="loadHistory" upper-threshold="50">
				<!-- 加载历史数据waitingUI -->
				<view class="loading">
					<view class="spinner">
						<view class="rect1"></view>
						<view class="rect2"></view>
						<view class="rect3"></view>
						<view class="rect4"></view>
						<view class="rect5"></view>
					</view>
				</view>
				<view class="row" v-for="(row,index) in msgList" :key="index" :id="'msg'+row.msg.id">
					<!-- 系统消息 -->
					<block v-if="row.type=='system'">
						<view class="system">
							<!-- 文字消息 -->
							<view v-if="row.msg.type=='text'" class="text">
								{{row.msg.content.text}}
							</view>
							<!-- 领取红包消息 -->
							<view v-if="row.msg.type=='redEnvelope'" class="red-envelope">
								<image src="/static/chat/red-envelope-chat.png"></image>
								{{row.msg.content.text}}
							</view>
						</view>
					</block>
					<!-- 用户消息 -->
					<block v-if="row.type=='user'">
						<!-- 自己发出的消息 -->
						<view class="my" v-if="row.msg.userinfo.uid==myuid">
							<!-- 左-消息 -->
							<view class="left">
								<!-- 文字消息 -->
								<view v-if="row.msg.type=='text'" class="bubble">
									<rich-text :nodes="row.msg.content.text"></rich-text>
								</view>
								<!-- 语言消息 -->
								<view v-if="row.msg.type=='voice'" class="bubble voice" @tap="playVoice(row.msg)"
									:class="playMsgid == row.msg.id?'play':''">
									<view class="length">{{row.msg.content.length}}</view>
									<view class="icon my-voice"></view>
								</view>
								<!-- 图片消息 -->
								<view v-if="row.msg.type=='img'" class="bubble img" @tap="showPic(row.msg)">
									<image :src="row.msg.content.url"
										:style="{'width': row.msg.content.w+'px','height': row.msg.content.h+'px'}">
									</image>
								</view>
							</view>
							<!-- 右-头像 -->
							<view class="right">
								<image :src="row.msg.userinfo.face"></image>
							</view>
						</view>
						<!-- 别人发出的消息 -->
						<view class="other" v-if="row.msg.userinfo.uid!=myuid">
							<!-- 左-头像 -->
							<view class="left">
								<image :src="row.msg.userinfo.face"></image>
							</view>
							<!-- 右-用户名称-时间-消息 -->
							<view class="right">
								<view class="username">
									<view class="name">{{row.msg.userinfo.username}}</view>
									<view class="time">{{row.msg.time}}</view>
								</view>
								<!-- 文字消息 -->
								<view v-if="row.msg.type=='text'" class="bubble">
									<rich-text :nodes="row.msg.content.text"></rich-text>
								</view>
								<!-- 语音消息 -->
								<view v-if="row.msg.type=='voice'" class="bubble voice" @tap="playVoice(row.msg)"
									:class="playMsgid == row.msg.id?'play':''">
									<view class="icon other-voice"></view>
									<view class="length">{{row.msg.content.length}}</view>
								</view>
								<!-- 图片消息 -->
								<view v-if="row.msg.type=='img'" class="bubble img" @tap="showPic(row.msg)">
									<image :src="row.msg.content.url"
										:style="{'width': row.msg.content.w+'px','height': row.msg.content.h+'px'}">
									</image>
								</view>
							</view>
						</view>
					</block>
				</view>
			</scroll-view>
		</view>
		<!-- 抽屉栏 -->
		<view class="popup-layer" :class="popupLayerClass" @touchmove.stop.prevent="discard">
			<!-- 更多功能 相册-拍照-红包 -->
			<view class="more-layer" :class="{hidden:hideMore}">
				<view class="list">
					<view class="box" @tap="chooseImage">
						<view class="icon tupian2"></view>
					</view>
					<view class="box" @tap="camera">
						<view class="icon paizhao"></view>
					</view>
				</view>
			</view>
		</view>
		<!-- 底部输入栏 -->
		<view class="input-box cu-bar tabbar" :class="popupLayerClass" @touchmove.stop.prevent="discard">
			<!-- H5下不能录音，输入栏布局改动一下 -->
			<!-- #ifndef H5 -->
			<view class="voice">
				<view class="icon" :class="isVoice?'jianpan':'yuyin'" @tap="switchVoice"></view>
			</view>
			<!-- #endif -->
			<!-- #ifdef H5 -->
			<view class="more" @tap="showMore">
				<view class="icon add"></view>
			</view>
			<!-- #endif -->
			<view class="textbox">
				<view class="voice-mode" :class="[isVoice?'':'hidden',recording?'recording':'']"
					@touchstart="voiceBegin" @touchmove.stop.prevent="voiceIng" @touchend="voiceEnd"
					@touchcancel="voiceCancel">{{voiceTis}}</view>
				<view class="text-mode" :class="isVoice?'hidden':''">
					<view class="box">
						<textarea auto-height="true" v-model="textMsg" @focus="textareaFocus" />
					</view>
				</view>
			</view>
			<!-- #ifndef H5 -->
			<view class="more" @tap="showMore">
				<view class="icon add"></view>
			</view>
			<!-- #endif -->
			<view class="send" :class="isVoice?'hidden':''" @tap="sendText">
				<view class="btn">发送</view>
			</view>
		</view>
		<!-- 录音UI效果 -->
		<view class="record" :class="recording?'':'hidden'">
			<view class="ing" :class="willStop?'hidden':''">
				<view class="icon luyin2"></view>
			</view>
			<view class="cancel" :class="willStop?'':'hidden'">
				<view class="icon chehui"></view>
			</view>
			<view class="tis" :class="willStop?'change':''">{{recordTis}}</view>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				//文字消息
				textMsg: '',
				//消息列表
				isHistoryLoading: false,
				scrollAnimation: false,
				scrollTop: 0,
				scrollToView: '',
				msgList: [],
				msgImgList: [],
				myuid: 0,

				//录音相关参数
				// #ifndef H5
				//H5不能录音
				RECORDER: uni.getRecorderManager(),
				// #endif
				isVoice: false,
				voiceTis: '按住 说话',
				recordTis: "手指上滑 取消发送",
				recording: false,
				willStop: false,
				initPoint: {
					identifier: 0,
					Y: 0
				},
				recordTimer: null,
				recordLength: 0,

				//播放语音相关参数
				AUDIO: uni.createInnerAudioContext(),
				playMsgid: null,
				VoiceTimer: null,
				// 抽屉参数
				popupLayerClass: '',
				// more参数
				hideMore: true,
			};
		},
		onLoad(option) {
			const data = JSON.parse(decodeURIComponent(option.data));
			
			console.log(data)
			this.getMsgList();
			//语音自然播放结束
			this.AUDIO.onEnded((res) => {
				this.playMsgid = null;
			});
			// #ifndef H5
			//录音开始事件
			this.RECORDER.onStart((e) => {
				this.recordBegin(e);
			})
			//录音结束事件
			this.RECORDER.onStop((e) => {
				this.recordEnd(e);
			})
			// #endif
		},
		onShow() {
			this.scrollTop = 9999999;

			//模板借由本地缓存实现发红包效果，实际应用中请不要使用此方法。
			//
			uni.getStorage({
				key: 'redEnvelopeData',
				success: (res) => {
					console.log(res.data);
					let nowDate = new Date();
					let lastid = this.msgList[this.msgList.length - 1].msg.id;
					lastid++;
					let row = {
						type: "user",
						msg: {
							id: lastid,
							type: "redEnvelope",
							time: nowDate.getHours() + ":" + nowDate.getMinutes(),
							userinfo: {
								uid: 0,
								username: "大黑哥",
								face: "https://zhoukaiwen.com/img/kevinLogo.png"
							},
							content: {
								blessing: res.data.blessing,
								rid: Math.floor(Math.random() * 1000 + 1),
								isReceived: false
							}
						}
					};
					this.screenMsg(row);
					uni.removeStorage({
						key: 'redEnvelopeData'
					});
				}
			});
		},
		methods: {
			// 接受消息(筛选处理)
			screenMsg(msg) {
				//从长连接处转发给这个方法，进行筛选处理
				if (msg.type == 'system') {
					// 系统消息
					switch (msg.msg.type) {
						case 'text':
							this.addSystemTextMsg(msg);
							break;
						case 'redEnvelope':
							this.addSystemRedEnvelopeMsg(msg);
							break;
					}
				} else if (msg.type == 'user') {
					// 用户消息
					switch (msg.msg.type) {
						case 'text':
							this.addTextMsg(msg);
							break;
						case 'voice':
							this.addVoiceMsg(msg);
							break;
						case 'img':
							this.addImgMsg(msg);
							break;
						case 'redEnvelope':
							this.addRedEnvelopeMsg(msg);
							break;
					}
					console.log('用户消息');
					//非自己的消息震动
					if (msg.msg.userinfo.uid != this.myuid) {
						console.log('振动');
						uni.vibrateLong();
					}
				}
				this.$nextTick(function() {
					// 滚动到底
					this.scrollToView = 'msg' + msg.msg.id
				});
			},


			back(){
				uni.navigateBack({
					delta: 1
				});
			},

			//触发滑动到顶部(加载历史信息记录)
			loadHistory(e) {
				if (this.isHistoryLoading) {
					return;
				}
				this.isHistoryLoading = true; //参数作为进入请求标识，防止重复请求
				this.scrollAnimation = false; //关闭滑动动画
				let Viewid = this.msgList[0].msg.id; //记住第一个信息ID
				//本地模拟请求历史记录效果
				setTimeout(() => {
					// 消息列表
					let list = [{
							type: "user",
							msg: {
								id: 1,
								type: "text",
								time: "12:56",
								userinfo: {
									uid: 0,
									username: "大黑哥",
									face: "https://zhoukaiwen.com/img/kevinLogo.png"
								},
								content: {
									text: "web前端开发该怎么学习？"
								}
							}
						},
						{
							type: "user",
							msg: {
								id: 2,
								type: "text",
								time: "12:57",
								userinfo: {
									uid: 1,
									username: "售后客服008",
									face: "https://zhoukaiwen.com/img/qdpz/face/face_2.jpg"
								},
								content: {
									text: "按照基本路线，从html、css、js三大基础开始，然后ajax、vue进阶学习，最后学习小程序、node、react。"
								}
							}
						},
						{
							type: "user",
							msg: {
								id: 3,
								type: "voice",
								time: "12:59",
								userinfo: {
									uid: 1,
									username: "售后客服008",
									face: "https://zhoukaiwen.com/img/qdpz/face/face_2.jpg"
								},
								content: {
									url: "/static/voice/1.mp3",
									length: "00:06"
								}
							}
						},
						{
							type: "user",
							msg: {
								id: 4,
								type: "voice",
								time: "13:05",
								userinfo: {
									uid: 0,
									username: "大黑哥",
									face: "https://zhoukaiwen.com/img/kevinLogo.png"
								},
								content: {
									url: "/static/voice/2.mp3",
									length: "00:06"
								}
							}
						},
					]
					// 获取消息中的图片,并处理显示尺寸
					for (let i = 0; i < list.length; i++) {
						if (list[i].type == 'user' && list[i].msg.type == "img") {
							list[i].msg.content = this.setPicSize(list[i].msg.content);
							this.msgImgList.unshift(list[i].msg.content.url);
						}
						list[i].msg.id = Math.floor(Math.random() * 1000 + 1);
						this.msgList.unshift(list[i]);
					}

					//这段代码很重要，不然每次加载历史数据都会跳到顶部
					this.$nextTick(function() {
						this.scrollToView = 'msg' + Viewid; //跳转上次的第一行信息位置
						this.$nextTick(function() {
							this.scrollAnimation = true; //恢复滚动动画
						});

					});
					this.isHistoryLoading = false;

				}, 1000)
			},
			// 加载初始页面消息
			getMsgList() {
				// 消息列表
				let list = [{
						type: "system",
						msg: {
							id: 0,
							type: "text",
							content: {
								text: "欢迎进入Kevin聊天室"
							}
						}
					},
					{
						type: "user",
						msg: {
							id: 1,
							type: "text",
							time: "12:56",
							userinfo: {
								uid: 0,
								username: "大黑哥",
								face: "https://zhoukaiwen.com/img/kevinLogo.png"
							},
							content: {
								text: "web前端开发该怎么学习？"
							}
						}
					},
					{
						type: "user",
						msg: {
							id: 2,
							type: "text",
							time: "12:57",
							userinfo: {
								uid: 1,
								username: "售后客服008",
								face: "https://zhoukaiwen.com/img/qdpz/face/face_2.jpg"
							},
							content: {
								text: "按照基本路线，从html、css、js三大基础开始，然后ajax、vue进阶学习，最后学习小程序、node、react。"
							}
						}
					},
					{
						type: "user",
						msg: {
							id: 3,
							type: "voice",
							time: "12:59",
							userinfo: {
								uid: 1,
								username: "售后客服008",
								face: "https://zhoukaiwen.com/img/qdpz/face/face_2.jpg"
							},
							content: {
								url: "/static/voice/1.mp3",
								length: "00:06"
							}
						}
					},
					{
						type: "user",
						msg: {
							id: 4,
							type: "voice",
							time: "13:05",
							userinfo: {
								uid: 0,
								username: "大黑哥",
								face: "https://zhoukaiwen.com/img/kevinLogo.png"
							},
							content: {
								url: "/static/voice/2.mp3",
								length: "00:06"
							}
						}
					},
					{
						type: "user",
						msg: {
							id: 5,
							type: "img",
							time: "13:05",
							userinfo: {
								uid: 0,
								username: "大黑哥",
								face: "https://zhoukaiwen.com/img/kevinLogo.png"
							},
							content: {
								url: "https://zhoukaiwen.com/img/Design/logo/psketch3.png",
								w: 200,
								h: 200
							}
						}
					},
					{
						type: "user",
						msg: {
							id: 6,
							type: "img",
							time: "12:59",
							userinfo: {
								uid: 1,
								username: "售后客服008",
								face: "https://zhoukaiwen.com/img/qdpz/face/face_2.jpg"
							},
							content: {
								url: "https://zhoukaiwen.com/img/Design/pc/ybss_jt.png",
								w: 1920,
								h: 1080
							}
						}
					},
					{
						type: "system",
						msg: {
							id: 7,
							type: "text",
							content: {
								text: "欢迎进入Kevin聊天室"
							}
						}
					},
					{
						type: "system",
						msg: {
							id: 9,
							type: "redEnvelope",
							content: {
								text: "售后客服008领取了你的红包"
							}
						}
					}
				]
				// 获取消息中的图片,并处理显示尺寸
				for (let i = 0; i < list.length; i++) {
					if (list[i].type == 'user' && list[i].msg.type == "img") {
						list[i].msg.content = this.setPicSize(list[i].msg.content);
						this.msgImgList.push(list[i].msg.content.url);
					}
				}
				this.msgList = list;
				// 滚动到底部
				this.$nextTick(function() {
					//进入页面滚动到底部
					this.scrollTop = 9999;
					this.$nextTick(function() {
						this.scrollAnimation = true;
					});
				});
			},
			//处理图片尺寸，如果不处理宽高，新进入页面加载图片时候会闪
			setPicSize(content) {
				// 让图片最长边等于设置的最大长度，短边等比例缩小，图片控件真实改变，区别于aspectFit方式。
				let maxW = uni.upx2px(350); //350是定义消息图片最大宽度
				let maxH = uni.upx2px(350); //350是定义消息图片最大高度
				if (content.w > maxW || content.h > maxH) {
					let scale = content.w / content.h;
					content.w = scale > 1 ? maxW : maxH * scale;
					content.h = scale > 1 ? maxW / scale : maxH;
				}
				return content;
			},

			//更多功能(点击+弹出) 
			showMore() {
				this.isVoice = false;
				this.hideEmoji = true;
				if (this.hideMore) {
					this.hideMore = false;
					this.openDrawer();
				} else {
					this.hideDrawer();
				}
			},
			// 打开抽屉
			openDrawer() {
				this.popupLayerClass = 'showLayer';
			},
			// 隐藏抽屉
			hideDrawer() {
				this.popupLayerClass = '';
				setTimeout(() => {
					this.hideMore = true;
					this.hideEmoji = true;
				}, 150);
			},
			// 选择图片发送
			chooseImage() {
				this.getImage('album');
			},
			//拍照发送
			camera() {
				this.getImage('camera');
			},
			//选照片 or 拍照
			getImage(type) {
				this.hideDrawer();
				uni.chooseImage({
					sourceType: [type],
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					success: (res) => {
						for (let i = 0; i < res.tempFilePaths.length; i++) {
							uni.getImageInfo({
								src: res.tempFilePaths[i],
								success: (image) => {
									console.log(image.width);
									console.log(image.height);
									let msg = {
										url: res.tempFilePaths[i],
										w: image.width,
										h: image.height
									};
									this.sendMsg(msg, 'img');
								}
							});
						}
					}
				});
			},

			//获取焦点，如果不是选表情ing,则关闭抽屉
			textareaFocus() {
				if (this.popupLayerClass == 'showLayer' && this.hideMore == false) {
					this.hideDrawer();
				}
			},
			// 发送文字消息
			sendText() {
				this.hideDrawer(); //隐藏抽屉
				if (!this.textMsg) {
					return;
				}
				this.sendMsg(this.textMsg, 'text');
				this.textMsg = ''; //清空输入框
			},
			// 发送消息
			sendMsg(content, type) {
				//实际应用中，此处应该提交长连接，模板仅做本地处理。
				var nowDate = new Date();
				let lastid = this.msgList[this.msgList.length - 1].msg.id;
				lastid++;
				let msg = {
					type: 'user',
					msg: {
						id: lastid,
						time: nowDate.getHours() + ":" + nowDate.getMinutes(),
						type: type,
						userinfo: {
							uid: 0,
							username: "大黑哥",
							face: "https://zhoukaiwen.com/img/kevinLogo.png"
						},
						content: content
					}
				}
				// 发送消息
				this.screenMsg(msg);
				// 定时器模拟对方回复,三秒
				setTimeout(() => {
					lastid = this.msgList[this.msgList.length - 1].msg.id;
					lastid++;
					msg = {
						type: 'user',
						msg: {
							id: lastid,
							time: nowDate.getHours() + ":" + nowDate.getMinutes(),
							type: type,
							userinfo: {
								uid: 1,
								username: "售后客服008",
								face: "https://zhoukaiwen.com/img/qdpz/face/face_2.jpg"
							},
							content: content
						}
					}
					// 本地模拟发送消息
					this.screenMsg(msg);
				}, 3000)
			},

			// 添加文字消息到列表
			addTextMsg(msg) {
				this.msgList.push(msg);
			},
			// 添加语音消息到列表
			addVoiceMsg(msg) {
				this.msgList.push(msg);
			},
			// 添加图片消息到列表
			addImgMsg(msg) {
				msg.msg.content = this.setPicSize(msg.msg.content);
				this.msgImgList.push(msg.msg.content.url);
				this.msgList.push(msg);
			},
			addRedEnvelopeMsg(msg) {
				this.msgList.push(msg);
			},
			// 添加系统文字消息到列表
			addSystemTextMsg(msg) {
				this.msgList.push(msg);
			},
			// 添加系统红包消息到列表
			addSystemRedEnvelopeMsg(msg) {
				this.msgList.push(msg);
			},
			// 打开红包
			openRedEnvelope(msg, index) {
				let rid = msg.content.rid;
				uni.showLoading({
					title: '加载中...'
				});
				console.log("index: " + index);
				//模拟请求服务器效果
				setTimeout(() => {
					//加载数据
					if (rid == 0) {
						this.redenvelopeData = {
							rid: 0, //红包ID
							from: "大黑哥",
							face: "https://zhoukaiwen.com/img/qdpz/face/face.jpg",
							blessing: "恭喜发财，大吉大利",
							money: "已领完"
						}
					} else {
						this.redenvelopeData = {
							rid: 1, //红包ID
							from: "售后客服008",
							face: "https://zhoukaiwen.com/img/qdpz/face/face_2.jpg",
							blessing: "恭喜发财",
							money: "0.01"
						}
						if (!msg.content.isReceived) {
							// {type:"system",msg:{id:8,type:"redEnvelope",content:{text:"你领取了售后客服008的红包"}}},
							this.sendSystemMsg({
								text: "你领取了" + (msg.userinfo.uid == this.myuid ? "自己" : msg.userinfo
									.username) + "的红包"
							}, 'redEnvelope');
							console.log("this.msgList[index]: " + JSON.stringify(this.msgList[index]));
							this.msgList[index].msg.content.isReceived = true;
						}
					}
					uni.hideLoading();
					this.windowsState = 'show';

				}, 200)

			},
			// 关闭红包弹窗
			closeRedEnvelope() {
				this.windowsState = 'hide';
				setTimeout(() => {
					this.windowsState = '';
				}, 200)
			},
			sendSystemMsg(content, type) {
				let lastid = this.msgList[this.msgList.length - 1].msg.id;
				lastid++;
				let row = {
					type: "system",
					msg: {
						id: lastid,
						type: type,
						content: content
					}
				};
				this.screenMsg(row)
			},
			//领取详情
			toDetails(rid) {
				uni.navigateTo({
					url: 'details/details?rid=' + rid
				})
			},
			// 预览图片
			showPic(msg) {
				uni.previewImage({
					indicator: "none",
					current: msg.content.url,
					urls: this.msgImgList
				});
			},
			// 播放语音
			playVoice(msg) {
				this.playMsgid = msg.id;
				this.AUDIO.src = msg.content.url;
				this.$nextTick(function() {
					this.AUDIO.play();
				});
			},
			// 录音开始
			voiceBegin(e) {
				if (e.touches.length > 1) {
					return;
				}
				this.initPoint.Y = e.touches[0].clientY;
				this.initPoint.identifier = e.touches[0].identifier;
				this.RECORDER.start({
					format: "mp3"
				}); //录音开始,
			},
			//录音开始UI效果
			recordBegin(e) {
				this.recording = true;
				this.voiceTis = '松开 结束';
				this.recordLength = 0;
				this.recordTimer = setInterval(() => {
					this.recordLength++;
				}, 1000)
			},
			// 录音被打断
			voiceCancel() {
				this.recording = false;
				this.voiceTis = '按住 说话';
				this.recordTis = '手指上滑 取消发送'
				this.willStop = true; //不发送录音
				this.RECORDER.stop(); //录音结束
			},
			// 录音中(判断是否触发上滑取消发送)
			voiceIng(e) {
				if (!this.recording) {
					return;
				}
				let touche = e.touches[0];
				//上滑一个导航栏的高度触发上滑取消发送
				if (this.initPoint.Y - touche.clientY >= uni.upx2px(100)) {
					this.willStop = true;
					this.recordTis = '松开手指 取消发送'
				} else {
					this.willStop = false;
					this.recordTis = '手指上滑 取消发送'
				}
			},
			// 结束录音
			voiceEnd(e) {
				if (!this.recording) {
					return;
				}
				this.recording = false;
				this.voiceTis = '按住 说话';
				this.recordTis = '手指上滑 取消发送'
				this.RECORDER.stop(); //录音结束
			},
			//录音结束(回调文件)
			recordEnd(e) {
				clearInterval(this.recordTimer);
				if (!this.willStop) {
					console.log("e: " + JSON.stringify(e));
					let msg = {
						length: 0,
						url: e.tempFilePath
					}
					let min = parseInt(this.recordLength / 60);
					let sec = this.recordLength % 60;
					min = min < 10 ? '0' + min : min;
					sec = sec < 10 ? '0' + sec : sec;
					msg.length = min + ':' + sec;
					this.sendMsg(msg, 'voice');
				} else {
					console.log('取消发送录音');
				}
				this.willStop = false;
			},
			// 切换语音/文字输入
			switchVoice() {
				this.hideDrawer();
				this.isVoice = this.isVoice ? false : true;
			},
			discard() {
				return;
			}
		}
	}
</script>
<style lang="scss">
	@import "css/style.scss";

	.content .showLayer {
		transform: translate3d(0, -43vw, 0);
	}
</style>
