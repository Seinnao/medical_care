<template>
	<viwe>
		<uni-nav-bar dark :fixed="true" shadow background-color="#00aaff" status-bar title="交流问诊" leftIcon="chatboxes-filled"
		left-text="去资讯" @clickLeft="realTime()"/>
		
		<view class="cu-bar bg-white margin-top-xs">
			<view class="action sub-title">
				<text class="text-xl text-bold text-blue text-shadow">医疗资讯</text>
				<text class="text-ABC text-blue">Medical</text>
			</view>
		</view>
		
		<view class="cu-list menu-avatar">
			<view class="cu-item" @click="openChat(item)" v-for="(item) in chatList" :key="item.id">
				<view class="cu-avatar round lg"
					:style="'background-image:url('+imagesUrl(item.avatarUrl)+');'">
				</view>
				<view class="content basis-xl">
					<view class="text-grey">{{item.otherParty}}</view>
					<view class="text-gray text-sm flex">
						<view class="text-cut">
							<view v-if="item.content"> {{item.content}} </view>
							<text v-else class="cuIcon-infofill text-olive margin-right-xs">对方未回消息</text>
						</view>
					</view>
				</view>
				<view class="action">
					<view class="text-grey text-xs" v-if="item.time">{{$calendar(item.time)}}</view>
					<view class="cu-tag round bg-red sm" v-if="item.unreadMsg !== 0">{{item.unreadMsg}}</view>
				</view>
			</view>
		</view>
	</viwe>
</template>

<script>
	export default {
		data() {
			return {
				chatList: [],
				user: {}
			};
		},
		onLoad(option) {
			console.log("onLoad---chatList")
			this.load()
		},
		onShow() {
			console.log("show---chatList")
			this.load()
		},
		onPullDownRefresh(){
			console.log("36373")
			this.load()
		},
		methods: {
			openChat(data) {
				console.log("打开聊天")
				
				const item={
					name:data.otherParty,
					avatarUrl:data.avatarUrl
				}
				
				uni.navigateTo({
					url: '/pages_chat/chat/chat?data='+encodeURIComponent(JSON.stringify(item)),
					animationType: 'slide-in-right',
					animationDuration: 500,
				});
			},
			load(){
				this.user = JSON.parse(uni.getStorageSync("user"));
				let name = this.user.nickname;
				if(this.user.role === 'ROLE_DOCTOR'){
					name = this.user.doctorName
				}
				this.http.get("/user-service/chat-people/" + name).then(res => {
					this.chatList = res.data
					uni.stopPullDownRefresh();
				})
			},
			realTime(){
				uni.navigateTo({
					url: '/pages_home/home/consultation',
					animationType: 'slide-in-left',
					animationDuration: 500,
				});
			}
		}
	}
</script>

<style lang="scss">
</style>
