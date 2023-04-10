<!-- 蓝色简洁登录页面 -->
<template>
	<view class="t-login">
		<!-- 标题 -->
		<view class="t-b">欢迎使用医疗服务！</view>
		<view class="t-b2">为个人医疗提供智能服务的平台</view>
		<!-- 页面装饰图片 -->
		<image class="img-a" src="../../static/login/2.png"></image>
		<image class="img-b" src="../../static/login/3.png"></image>
		<form class="cl">
			<view class="t-a">
				<image src="@/static/login/nick-name.png"></image>
				<view class="line"></view>
				<input type="none" name="username" placeholder="请输入账号" maxlength="11" v-model="username" />
			</view>
			<view class="t-a">
				<image src="@/static/login/password.png"></image>
				<view class="line"></view>
				<input type="password" name="password" maxlength="16" placeholder="请输入密码" v-model="password" />
				<!-- <view v-if="showText" class="t-c" @tap="getCode()">发送短信</view>
				<view v-else class="t-c" style="background-color: #A7A7A7;">重新发送({{ second }})</view> -->
			</view>
			<button @tap="login()" style="margin-bottom: 20rpx;">登 录</button>
			<view class="new-account" @click="toRegister">注册新账号</view>
		</form>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				username: '', //手机号码
				password: '' //密码
			};
		},
		onLoad() {
			let token = uni.getStorageSync('token');
			if (token) {
				this.http.get('user-service/user/isToken').then(res => {
					if (res.code === 200) {
						console.log("自动登录。。。")
						uni.switchTab({
							url: '/pages/index/index'
						});
					}
				})
			}
		},
		methods: {
			//当前登录按钮操作
			login() {
				let that = this;
				if (!that.username) {
					uni.showToast({
						title: '请输入账号',
						icon: 'none'
					});
					return;
				}
				if (!that.password) {
					uni.showToast({
						title: '请输入密码',
						icon: 'none'
					});
					return;
				}
				//清除状态
				uni.setStorageSync('token', "")

				this.http.post('user-service/user/app-login', {
					username: this.username,
					password: this.password,
				}).then(res => {
					console.log(res)
					if (res.code === 200) {
						let user = res.data;
						uni.setStorageSync('token', res.data.token)
						uni.setStorageSync('user', JSON.stringify({
							id: user.id,
							username: user.username,
							nickname: user.nickname,
							avatarUrl: user.avatarUrl,
							role: user.role,
							doctorName: user.doctorName
						}))
						uni.showToast({
							title: '登录成功',
							icon: 'success',
							complete: () => {
								setTimeout(() => {
									uni.switchTab({
										url: '/pages/index/index'
									});
								}, 500);
							}
						});
					} else {
						uni.showToast({
							title: res.data.msg,
							icon: 'error'
						});
					}
				})
			},
			toRegister() {
				uni.navigateTo({
					url: '/pages/login/register',
					animationType: 'slide-in-left',
					animationDuration: 500,
				});
			}

		}
	};
</script>
<style>
	.img-a {
		position: absolute;
		filter: alpha(opacity=20);
		width: 100%;
		top: -190rpx;
		right: 0;
	}

	.img-b {
		position: absolute;
		filter: alpha(opacity=20);
		width: 50%;
		bottom: 0;
		left: -130rpx;
		/* margin-bottom: -200rpx; */
	}

	.t-login {
		width: 650rpx;
		margin: 0 auto;
		font-size: 28rpx;
		color: #000;
	}

	.t-login button {
		font-size: 28rpx;
		background: #5677fc;
		color: #fff;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 50rpx;
		box-shadow: 0 5px 7px 0 rgba(86, 119, 252, 0.2);
	}

	.t-login input {
		padding: 0 20rpx 0 120rpx;
		height: 90rpx;
		line-height: 90rpx;
		margin-bottom: 50rpx;
		background: #f8f7fc;
		border: 1px solid #e9e9e9;
		font-size: 28rpx;
		border-radius: 50rpx;
	}

	.t-login .t-a {
		position: relative;
	}

	.t-login .t-a image {
		width: 40rpx;
		height: 40rpx;
		position: absolute;
		left: 40rpx;
		top: 28rpx;
		/* border-right: 2rpx solid #dedede; */
		margin-right: 20rpx;
	}

	.t-login .t-a .line {
		width: 2rpx;
		height: 40rpx;
		background-color: #dedede;
		position: absolute;
		top: 28rpx;
		left: 98rpx;
	}

	.t-login .t-b {
		text-align: left;
		font-size: 46rpx;
		color: #000;
		padding: 300rpx 0 30rpx 0;
		font-weight: bold;
	}

	.t-login .t-b2 {
		text-align: left;
		font-size: 32rpx;
		color: #aaaaaa;
		padding: 0rpx 0 120rpx 0;
	}

	.t-login .t-c {
		position: absolute;
		right: 22rpx;
		top: 22rpx;
		background: #5677fc;
		color: #fff;
		font-size: 24rpx;
		border-radius: 50rpx;
		height: 50rpx;
		line-height: 50rpx;
		padding: 0 25rpx;
	}

	.t-login .t-d {
		text-align: center;
		color: #999;
		margin: 80rpx 0;
	}

	.t-login .t-e {
		text-align: center;
		width: 250rpx;
		margin: 80rpx auto 0;
	}

	.t-login .t-g {
		float: left;
		width: 50%;
	}

	.t-login .t-e image {
		width: 50rpx;
		height: 50rpx;
	}

	.t-login .t-f {
		text-align: center;
		margin: 200rpx 0 0 0;
		color: #666;
	}

	.t-login .t-f text {
		margin-left: 20rpx;
		color: #aaaaaa;
		font-size: 27rpx;
	}

	.t-login .uni-input-placeholder {
		color: #000;
	}

	.cl {
		zoom: 1;
	}

	.cl:after {
		clear: both;
		display: block;
		visibility: hidden;
		height: 0;
		content: '\20';
	}

	.cl .new-account {
		text-align: right;
		font-size: 20rpx;
		padding-bottom: 20rpx;
	}

	.cl .new-account:hover {
		color: #099bd9;
	}
</style>
