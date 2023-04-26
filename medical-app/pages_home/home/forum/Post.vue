<template>
	<view>
		<uni-nav-bar dark fixed shadow background-color="#00aaff" status-bar title="资讯详情" left-icon="left"
			left-text="返回" @clickLeft="back">
		</uni-nav-bar>
		<!-- #ifdef MP-WEIXIN -->
		<towxml :nodes="content" />
		<!-- #endif -->
		<!-- #ifndef MP-WEIXIN -->
		<view class="u-content">
			<u-parse :html="content" :selectable="true" :tag-style="style" :show-with-animation="true"></u-parse>
		</view>
		<!-- #endif -->

		<view style="background-color: #FFFFFF;padding: 0rpx 30rpx 15rpx0rpx;margin: 25rpx 0 170rpx 0;">
			<view class="cu-bar justify-left bg-white">
				<view class="action border-title">
					<text class="text-lg text-bold text-blue">评论</text>
					<text class="bg-gradual-blue" style="width:3rem"></text>
				</view>
			</view>

			<!-- <view class="text-lg text-bold text-center margin-bottom-lg">暂无评论</view> -->

			<view class="cu-list menu-avatar comment solids-top">
				<view class="cu-item">
					<view class="cu-avatar round"
						style="background-image:url(http://1.85.32.5:49000/daxiang/common/20200918181604.png);"></view>
					<view class="content">
						<view class="text-grey">李小小</view>
						<view class="text-gray text-content text-df">
							这个真不错～
						</view>
						<view class="margin-top-sm flex justify-between">
							<view class="text-gray text-df">2023/3/14</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 底部评论 -->
		<view class="bottomBox solids-top">
			<view class="cu-bar input">
				<input v-model="comment_input" @confirm="send_btn" confirm-type="send" class="solid" maxlength="300"
					cursor-spacing="10" placeholder="说说你的看法.."></input>
				<view class="action">
					<view :class="!isCollection? 'text-grey cuIcon-favor':'cuIcon-favorfill text-red'" @click="praiseClick">
					</view>
				</view>
			</view>
		</view>
		<view class="safe-area-inset-bottom"></view>
	</view>

</template>

<script>
	// #ifdef MP-WEIXIN
	//import towxml from '../../static/towxml/towxml'
	// #endif
	export default {
		components:{
			// #ifdef MP-WEIXIN
			//towxml
			// #endif
		},
		data() {
			return {
				content: "",
				style: {
					h1: "font-size: 36rpx;margin:30rpx 0;border-bottom: thin solid #b6b6b6",
					h2: "font-size: 34rpx;margin:20rpx 0;border-bottom: thin solid #b6b6b6",
					h3: "font-size: 32rpx;margin:10rpx 0;border-bottom: thin solid #b6b6b6",
					h4: "font-size: 30rpx;margin:5rpx 0;",
					h5: "font-size: 28rpx;",
					p: "",
					span: "",
					table: "margin:20rpx 0;",
					img: "width: 100%;",
					'pre': "font-size: 20rpx;background-color: #1e1e1e;padding: 10rpx;color:#ffffff",
				},
				forum: {},
				comment_input: '',
				forumId: '',
				isCollection: false,
				collectionId: '',
				user: {}
			}
		},
		onLoad(option) {
			this.user = JSON.parse(uni.getStorageSync("user"));
			this.load(option.id)
		},
		mounted() {

		},
		methods: {
			search(data) {
				console.log(data)
			},
			load(id) {
				this.forumId = id;
				this.http.get(`/care-service/forum/getPost/${id}`).then(res => {
					// #ifdef MP-WEIXIN
					this.content = this.towxml(res.data.content, 'markdown')
					// #endif
					// #ifndef MP-WEIXIN
					this.content = this.md.render(res.data.content)
					// #endif
				})
				this.http.get(`/care-service/forum/collection/${this.user.id}/${id}`).then(res => {
					if (res.code === 200 && res.data) {
						this.isCollection = res.data.collection
						this.collectionId = res.data.id
					}
				})
			},
			send_btn() {

			},
			praiseClick() {
				this.isCollection = !this.isCollection;
				this.http.post('/care-service/forum/collection', {
					id: this.collectionId,
					collection: this.isCollection,
					userId: this.user.id,
					forumId: this.forumId
				}).then(res => {
					if (res.code === 200) {
						uni.showToast({
							title: `${this.isCollection ? '收藏成功' : '取消收藏'}`,
							duration: 2000
						});
					}
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	.u-content {
		padding: 30rpx;
		line-height: 1.8;
		background-color: #ffffff;
	}

	.h1 {}
</style>
