<!-- 蓝色简洁登录页面 -->
<template>
	<view class="t-login">
		<!-- 标题 -->
		<view class="t-b">{{ title }}</view>
		<!-- 页面装饰图片 -->
		<image class="img-a" src="../../static/login/2.png"></image>
		<image class="img-b" src="../../static/login/3.png"></image>
		<form class="cl">
			<view class="t-a">
				<image src="@/static/login/nick-name.png"></image>
				<view class="line"></view>
				<input type="text" name="username" placeholder="请输入账号" maxlength="11" v-model="username" />
			</view>
			<view class="t-a">
				<image src="@/static/login/sj.png"></image>
				<view class="line"></view>
				<input type="number" name="phone" maxlength="11" placeholder="请输人手机号" v-model="phone" />
			</view>
			<view class="t-a">
				<image src="@/static/login/password.png"></image>
				<view class="line"></view>
				<input type="password" name="password" placeholder="请输入密码" maxlength="20" v-model="password" />
			</view>
			<view class="t-a">
				<image src="@/static/login/confirm-password.png"></image>
				<view class="line"></view>
				<input type="password" name="confirmPassword" placeholder="确认密码" maxlength="20"
					v-model="confirmPassword" />
			</view>
			<button @tap="login()" style="margin-bottom: 20rpx;">注 册</button>
			<view class="new-account" @click="toLogin">返回登录</view>
		</form>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				title: '注册！', //填写logo或者app名称，也可以用：欢迎回来，看您需求
				username: '',
				phone: '', //手机号码
				password: '', //密码
				confirmPassword: ''
			};
		},
		onLoad() {

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

				if (!that.phone) {
					uni.showToast({
						title: '请输入手机号',
						icon: 'none'
					});
					return;
				}
				if (!/^[1][3,4,5,7,8,9][0-9]{9}$/.test(that.phone)) {
					uni.showToast({
						title: '请输入正确手机号',
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

				if (!that.confirmPassword) {
					uni.showToast({
						title: '请确认密码',
						icon: 'none'
					});
					return;
				}

				if (that.password !== that.confirmPassword) {
					uni.showToast({
						title: '两次密码不一致',
						icon: 'none'
					});
					return;
				}

				this.http.post('/user-service/user/register', {
					username: that.username,
					tel: that.phone,
					password: that.password
				}).then(res => {
					if (res.code === 200) {
						uni.showToast({
							title: '注册成功',
							icon: 'success',
							complete: () => {
								setTimeout(() => {
									that.toLogin();
								}, 500)
							}
						});
					} else {
						uni.showToast({
							title: res.msg,
							icon: 'none'
						});
					}
				})
			},
			toLogin() {
				uni.navigateBack({
					url: '/pages/login/index',
					//animationType: 'fade - in',
					animationDuration: 500,
					// slide - in -right 右侧
					// slide - in -left 左侧
					// slide - in -top 顶部
					// slide - in -bottom 底部
					// pop - in 新窗体从左侧进入， 且老窗体被挤压而出
					// fade - in 新窗体从透明到不透明逐渐显示
					// zoom - out 新窗体从小到大缩放显示
					// zoom - fade - out 新窗体从小到大逐渐放大并且从透明到不透明逐渐显示
					// none 无动画
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
		padding: 200rpx 0 140rpx 30rpx;
		font-weight: bold;
	}

	.t-login .t-b2 {
		text-align: left;
		font-size: 32rpx;
		color: #aaaaaa;
		padding: 0rpx 0 100rpx 0;
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
		padding-top: 20rpx;
		padding-bottom: 20rpx;
	}

	.cl .new-account:hover {
		color: #099bd9;
	}
</style>
