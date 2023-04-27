<template>
	<view>
		<uni-nav-bar dark fixed shadow background-color="#00aaff" status-bar title="我的收藏" left-icon="left" left-text="返回"
			@clickLeft="back" />
		<view class="cu-card case no-card margin-bottom-sm my-card"
			v-for="(item) in forumDate" :key="item.id">
			<view class="cu-item shadow" @tap="openPost(item.id)">
				<view class="padding forum" style="padding-bottom: 0;">
					<view class="title">
						<view class="text-cut text-bold" style="color: #6b6b6b;">{{item.title}}</view>
					</view>
					<view class="text-content padding-top-sm text-grey">{{item.outline}}</view>
				</view>
				<view class="cu-list menu-avatar">
					<view class="cu-item">
						<view class="cu-avatar round lg"
							:style="'background-image:url('+imagesUrl(item.avatarUrl)+');'">
						</view>
						<view class="content flex-sub">
							<view class="text-grey">{{item.nickname}}</view>
							<view class="text-gray text-sm flex justify-between">
								{{$calendar(item.time,false)}}
								<view class="text-gray text-sm">
									<text class="cuIcon-attentionfill margin-lr-xs"></text> {{item.see}}
									<text class="cuIcon-favorfill margin-lr-xs"></text> {{ item.collection}}
									<text class="cuIcon-messagefill margin-lr-xs"></text> {{ item.commentSum}}
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="cu-load load-modal" v-if="loading">
			<view class="cuIcon-emojifill text-orange"></view>
			<!-- <image src="/static/logo.png" mode="aspectFit"></image> -->
			<view class="gray-text">加载中...</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				forumDate: [],
				user:{},
				loading: false,
			}
		},
		onLoad(option) {
			this.user = JSON.parse(uni.getStorageSync("user"));
			this.load()
		},
		methods: {
			load(){
				this.loading = true;
				//#/myCollection/{userId}
				this.http.get(`/care-service/forum/myCollection/${this.user.id}`).then(res => {
					this.forumDate = res.data;
					this.loading = false;
				})
			},
			openPost(id){
				//console.log("hhhhhh")
				uni.navigateTo({
					url: "/pages_home/home/forum/Post?id="+id
				});
			}
		}
	}
</script>

<style lang="scss">
	.forum {
		font-size: 26rpx;

		.title {
			font-size: 32rpx;
		}
	}
	.my-card{
		opacity: 1;
	}
	.my-card:hover{
		opacity: 0.8;
	}
</style>

