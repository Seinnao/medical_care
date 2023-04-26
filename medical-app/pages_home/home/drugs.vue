<template>
	<view>
		<uni-nav-bar dark fixed shadow background-color="#00aaff" status-bar title="药品查询" left-icon="left"
			left-text="返回" @clickLeft="back">
		</uni-nav-bar>
		<uni-search-bar class="uni-mt-10" radius="100" placeholder="药品名称" clearButton="always"
		cancelButton="none" @confirm="search"/>
			
		<u-waterfall v-model="flowList" ref="uWaterfall">
			<template v-slot:left="{leftList}">
				<view class="demo-warter" v-for="(item, index) in leftList" :key="item.id" @click="seeDruges(item)">
					<u-lazy-load threshold="-450" border-radius="10" :image="item.imagesUrl" :index="index"></u-lazy-load>
					<view class="demo-title">
						{{item.name}}
					</view>
					<view class="demo-tag">
						<view class="demo-tag-owner">
							标签：
						</view>
						<view class="demo-tag-text">
							{{item.label}}
						</view>
					</view>
					<!-- <u-icon name="close-circle-fill" color="#fa3534" size="34" class="u-close" @click="remove(item.id)"></u-icon> -->
				</view>
			</template>
			<template v-slot:right="{rightList}">
				<view class="demo-warter" v-for="(item, index) in rightList" :key="item.id" @click="seeDruges(item)">
					<u-lazy-load threshold="-450" border-radius="10" :image="item.imagesUrl" :index="index"></u-lazy-load>
					<view class="demo-title">
						{{item.name}}
					</view>
					<view class="demo-tag">
						<view class="demo-tag-owner">
							标签：
						</view>
						<view class="demo-tag-text">
							{{item.label}}
						</view>
					</view>
				</view>
			</template>
		</u-waterfall>
		<u-loadmore bg-color="rgb(240, 240, 240)" :status="loadStatus" @loadmore="addRandomData"></u-loadmore>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				loadStatus: 'loadmore',
				flowList: [],
				name:'',
				all: false,
				total: 0,
				pageNum: 1
			};
		},
		onLoad(option) {
			this.load()
		},
		onReachBottom() {
			if(!this.all){
				this.load()
			}
		},
		methods: {
			load(){
				this.loadStatus = 'loading';
				console.log(this.flowList)
				this.http.get("/care-service/drugs/page", {pageNum: this.pageNum,pageSize: 4,name: this.name}).then(res => {
					this.total = res.data.total
					res.data.records.forEach(item =>{
						item.imagesUrl = this.imagesUrl(item.imagesUrl)
						this.flowList.push(item)
					})
					if(this.total <= this.pageNum * 4){
						this.all = true
						this.loadStatus = 'nomore'
					}else{
						this.loadStatus = 'loadmore';
					}
					this.pageNum = this.pageNum + 1;
				})
			},
			addRandomData(){
				if(!this.all){
					this.load()
				}
			},
			seeDruges(data){
				uni.navigateTo({
					url: `/pages_home/home/drugsItem?id=${data.id}`,
					animationType: 'slide-in-right',
					animationDuration: 500,
				});
			},
			search(data){
				this.name = data.value;
				this.pageNum = 1;
				this.flowList = []
				this.$refs.uWaterfall.clear()
				this.all = false;
				this.load()
			}
		}
	}
</script>


<style>
	/* page不能写带scope的style标签中，否则无效 */
	page {
		background-color: rgb(240, 240, 240);
	}
</style>


<style lang="scss" scoped>
	.demo-warter {
		border-radius: 8px;
		margin: 10px 5px;
		background-color: #ffffff;
		padding: 8px;
		position: relative;
	}

	.u-close {
		position: absolute;
		top: 32rpx;
		right: 32rpx;
	}

	.demo-image {
		width: 100%;
		border-radius: 4px;
	}

	.demo-title {
		font-size: 30rpx;
		margin-top: 5px;
		color: $u-main-color;
	}

	.demo-tag {
		display: flex;
		margin-top: 5px;
	}

	.demo-tag-owner {
		color: #416c76;
		display: flex;
		padding: 4rpx 0;
		font-size: 22rpx;
		line-height: 1;
	}

	.demo-tag-text {
		border: 1px solid $u-type-primary;
		color: $u-type-primary;
		margin-left: 10px;
		border-radius: 50rpx;
		line-height: 1;
		padding: 4rpx 14rpx;
		display: flex;
		align-items: center;
		border-radius: 50rpx;
		font-size: 20rpx;
	}
	
</style>