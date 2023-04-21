<template>
	<view>
		<uni-nav-bar dark fixed shadow background-color="#00aaff" status-bar title="药品详情" left-icon="left"
			left-text="返回" @clickLeft="back">
		</uni-nav-bar>
		
		<image :src="imagesUrl(drugs.imagesUrl)" mode="aspectFit"></image>
		<uni-section :title="drugs.name" type="line" style="text-align: left;">
			<uni-notice-bar :text="'标签:     '+drugs.label" />
		</uni-section>
		<uni-card is-full :is-shadow="false" class="details">
			<uni-title type="h3" title="详情"></uni-title>
			<text style="font-size: 30rpx;">{{drugs.introduce}}</text>
		</uni-card>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				drugs:{}
			};
		},
		onLoad(option) {
			this.drugs.id = option.id
			this.load()
		},
		methods: {
			load(){
				this.http.get('/care-service/drugs/findById/'+this.drugs.id).then(res =>{
					this.drugs = res.data
					//console.log(this.drugs)
				})
			},
		}
	}
</script>

<style>
	.uni-container {
		text-align: center;
	}
	.details {
		text-align: left;
	}
</style>