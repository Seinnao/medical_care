<template>
	<view>
		<uni-nav-bar dark fixed shadow background-color="#00aaff" status-bar title="资讯医生" left-icon="left"
			left-text="返回" @clickLeft="back">
		</uni-nav-bar>

		<view class="cu-card dynamic no-card margin-sm" v-for="item in doctors" :key="item.id">
			<view class="cu-item shadow">
				<view class="cu-list menu-avatar">
					<view class="cu-item">
						<view class="cu-avatar round lg"
							:style="'background-image:url('+imagesUrl(item.avatarUrl)+');'">
						</view>
						<view class="content flex-sub">
							<view class="text-black text-bold">{{item.name}}</view>
							<view class="flex align-center">
								<uni-rate allow-half :size="12" :value="item.score" />
								<text class="padding-left-xs">{{item.score}}</text>
							</view>
						</view>
						<view class='cu-tag round bg-yellow' v-if="item.isOnline">在线</view>
						<view class='cu-tag round bg-grey' v-else>离线</view>
						<view class="action">
							<button class="cu-btn round sm line-blue" @click="toInquiry(item)">资询</button>
						</view>
					</view>
				</view>
				<view class="padding">
					{{item.introduce}}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {},
				loadStatus: 'loadmore',
				pageNum: 1,
				doctors: []
			};
		},
		onLoad(option) {
			this.load()
		},
		methods: {
			load() {
				this.user = JSON.parse(uni.getStorageSync("user"));
				this.http.get("/user-service/doctor/getDoctors", {
					pageNum: this.pageNum,
					pageSize: 8,
				}).then(res => {
					this.doctors = res.data.records
				})
			},
			toInquiry(data) {
				let user = JSON.parse(uni.getStorageSync("user"));
				this.http.post("/user-service/chat-people", {
					nickname: user.nickname,
					otherParty: data.name,
					myUrl: user.avatarUrl,
					toUrl: data.avatarUrl,
					time: new Date()
				}).then(res => {
					if (res.code === 200) {	
						const item={
							name:data.name,
							avatarUrl:data.avatarUrl
						}
						uni.navigateTo({
							url: '/pages_chat/chat/chat?data='+encodeURIComponent(JSON.stringify(item)),
							animationType: 'slide-in-right',
							animationDuration: 500,
						});
					} else {
						
					}
				})
			}
		}
	}
</script>


<style>
</style>
