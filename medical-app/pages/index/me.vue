<template>
	<view>
		<view class="flex bg-img shadow-blur justify-center my-header align-center">
			<!-- <view class="bg-grey padding-lg margin-xs radius">ColorUi</view> -->
			<view>
				<view class="cu-avatar xl round my-avatar"
					:style="'background-image:url('+imagesUrl(user.avatarUrl)+');'"></view>
				<view class="text-center text-white text-xl text-bold padding-sm text-shadow">{{user.nickname}}</view>
			</view>
		</view>
		<view class="cu-list menu sm-border margin-top">
			<view class="cu-item">
				<button class="cu-btn content" @click="perfectMsg()">
					<image src="/static/me/item/perfect_msg.png" class="png"></image>
					<text class="text-grey">完善信息</text>
				</button>
			</view>
			<view class="cu-item">
				<button class="cu-btn content" @click="changePass()">
					<image src="/static/me/item/change_password.png" class="png"></image>
					<!-- <text class="cuIcon-btn text-olive"></text> -->
					<text class="text-grey">修改密码</text>
				</button>
			</view>
			<view class="cu-item">
				<button class="cu-btn content" @click="setUp">
					<image src="/static/me/item/set_up.png" class="png"></image>
					<text class="text-grey">设置</text>
				</button>
			</view>
			<view class="cu-item">
				<button class="cu-btn content" @click="aboutMe()">
					<image src="/static/me/item/about_author.png" class="png"></image>
					<text class="text-grey">关于作者</text>
				</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {},
			}
		},
		onLoad(option) {
			this.user = JSON.parse(uni.getStorageSync("user"));
		},
		onShow() {
			//console.log("show---chatList")
			this.user = JSON.parse(uni.getStorageSync("user"));
		},
		methods: {
			aboutMe(){
				uni.navigateTo({
					url: '/pages_home/me/aboutMe',
					animationType: 'slide-in-left',
					animationDuration: 500,
				});
			},
			changePass(){
				uni.navigateTo({
					url: '/pages_home/me/changePassword',
					animationType: 'slide-in-left',
					animationDuration: 500,
				});
			},
			perfectMsg(){
				uni.navigateTo({
					url: '/pages_home/me/perfectMsg',
					animationType: 'slide-in-left',
					animationDuration: 500,
				});
			},
			setUp(){
				uni.navigateTo({
					url: '/pages_home/me/setUp',
					animationType: 'slide-in-left',
					animationDuration: 500,
				});
			}
		}
	}
</script>

<style lang="scss">
	.my-header {
		height: 400rpx;
		background-image: url('../../static/me/me_back.jpg');
	
	.my-avatar {
			margin-top: 90rpx;
		}
	}
</style>
