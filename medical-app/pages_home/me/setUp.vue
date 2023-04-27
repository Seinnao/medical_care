<template>
	<view>
		<uni-nav-bar dark fixed shadow background-color="#00aaff" status-bar title="设置" left-icon="left" left-text="返回"
			@clickLeft="back" />
		<view class="cu-list menu sm-border margin-top">
			<view class="cu-item">
				<button class="cu-btn content" open-type="contact" @click="exit()">
					<text class="cuIcon-backwardfill text-olive"></text>
					<text class="text-grey">退出系统</text>
				</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {
			exit() {
				uni.setStorageSync("user", null)
				uni.setStorageSync("token", null)
				setTimeout(() => {
					uni.reLaunch({
						url: '/pages/login/index',
					});
				}, 500);
			}
		}
	}
</script>

<style>

</style>
