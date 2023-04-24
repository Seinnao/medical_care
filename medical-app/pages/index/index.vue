<template>
	<view class="content">
		<uni-nav-bar dark :fixed="true" shadow background-color="#00aaff" status-bar title="首页" leftIcon="search" @clickLeft="search()"/>
		<swiper class="card-swiper" :class="dotStyle?'square-dot':'round-dot'" :indicator-dots="true" :circular="true"
			:autoplay="true" interval="5000" duration="500" @change="cardSwiper" indicator-color="#8799a3"
			indicator-active-color="#0081ff">
			<swiper-item v-for="(item,index) in swiperList" :key="index" :class="cardCur==index?'cur':''">
				<view class="swiper-item">
					<image :src="imagesUrl(item.url)" mode="aspectFill" v-if="true || item.type=='image'"></image>
<!-- 					<video :src="item.url" autoplay loop muted :show-play-btn="false" :controls="false"
						objectFit="cover" v-if="item.type=='video'"></video> -->
				</view>
			</swiper-item>
		</swiper>
		
		<!-- 导航栏 -->
		<view class="cu-list grid solids-bottom col-4 no-border">
			<view class="cu-item" v-for="(item, index) in categories" :key="index"
				:style="[{ animation: 'show ' + ((index + 1) * 0.2 + 1) + 's 1' }]" @click="goCategorieslist"
				:data-mid="item.mid">
				<view :class="['cuIcon-' + item.cuIcon, 'text-' + item.color]">
				</view>
				<text>{{ item.name }}</text>
			</view>
		</view>
		
		<view class="cu-bar bg-white">
			<view class="action">
				<text class="cuIcon-title text-orange"></text> Gif动画
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: 'Hello',
				keyword: '',
				cardCur: 0,
				swiperList: [],
				dotStyle: false,
				towerStart: 0,
				direction: '',
				categories: [{
						cuIcon: 'hotfill',
						color: 'red',
						mid: '1',
						name: '学习技术'
					},
					{
						cuIcon: 'colorlens',
						color: 'orange',
						mid: '2',
						name: '需求定制'
					},
					{
						cuIcon: 'baby',
						color: 'yellow',
						mid: '3',
						name: '药品查找'
					},
					{
						cuIcon: 'message',
						color: 'blue',
						mid: '4',
						name: '资讯医生'
					}
				],
			}
		},
		onLoad() {
			this.http.get("file-service/file/swiperImages").then(res => {
			     this.swiperList = res.data
				 this.TowerSwiper('swiperList');
				 // 初始化towerSwiper 传已有的数组名即可
			})
		},
		methods: {
			goCategorieslist(e){
				console.log(e.currentTarget.dataset.mid)
				if (e.currentTarget.dataset.mid == 1 || e.currentTarget.dataset.mid == 2) {
					
				} else if (e.currentTarget.dataset.mid == 3) {
					uni.navigateTo({
						url: '/pages/home/drugs',
						animationType: 'slide-in-left',
						animationDuration: 500,
					});
				} else if (e.currentTarget.dataset.mid == 4) {
					uni.navigateTo({
						url: '/pages/home/consultation',
						animationType: 'slide-in-left',
						animationDuration: 500,
					});
				}
			},
			search(){
				uni.navigateTo({
					url: '/pages/home/search',
					animationType: 'slide-in-left',
					animationDuration: 500,
				});
			},
			DotStyle(e) {
				this.dotStyle = e.detail.value
			},
			// cardSwiper
			cardSwiper(e) {
				this.cardCur = e.detail.current
			},
			// towerSwiper
			// 初始化towerSwiper
			TowerSwiper(name) {
				let list = this[name];
				for (let i = 0; i < list.length; i++) {
					list[i].zIndex = parseInt(list.length / 2) + 1 - Math.abs(i - parseInt(list.length / 2))
					list[i].mLeft = i - parseInt(list.length / 2)
				}
				this.swiperList = list
			},

			// towerSwiper触摸开始
			TowerStart(e) {
				this.towerStart = e.touches[0].pageX
			},

			// towerSwiper计算方向
			TowerMove(e) {
				this.direction = e.touches[0].pageX - this.towerStart > 0 ? 'right' : 'left'
			},

			// towerSwiper计算滚动
			TowerEnd(e) {
				let direction = this.direction;
				let list = this.swiperList;
				if (direction == 'right') {
					let mLeft = list[0].mLeft;
					let zIndex = list[0].zIndex;
					for (let i = 1; i < this.swiperList.length; i++) {
						this.swiperList[i - 1].mLeft = this.swiperList[i].mLeft
						this.swiperList[i - 1].zIndex = this.swiperList[i].zIndex
					}
					this.swiperList[list.length - 1].mLeft = mLeft;
					this.swiperList[list.length - 1].zIndex = zIndex;
				} else {
					let mLeft = list[list.length - 1].mLeft;
					let zIndex = list[list.length - 1].zIndex;
					for (let i = this.swiperList.length - 1; i > 0; i--) {
						this.swiperList[i].mLeft = this.swiperList[i - 1].mLeft
						this.swiperList[i].zIndex = this.swiperList[i - 1].zIndex
					}
					this.swiperList[0].mLeft = mLeft;
					this.swiperList[0].zIndex = zIndex;
				}
				this.direction = ""
				this.swiperList = this.swiperList
			},
		}
	}
</script>

<style>
	.tower-swiper .tower-item {
		transform: scale(calc(0.5 + var(--index) / 10));
		margin-left: calc(var(--left) * 100upx - 150upx);
		z-index: var(--index);
	}

	.content {
		background-color: #ffffff;
	}
</style>
