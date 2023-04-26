<template>
	<view class="bg-white">
		<uni-nav-bar dark fixed shadow background-color="#00aaff" status-bar title="药品详情" left-icon="left"
			left-text="返回" @clickLeft="back">
		</uni-nav-bar>
		
		<view class="cu-bar bg-white margin-top-xs">
			<view class="action sub-title">
				<text class="text-xl text-bold text-blue text-shadow">{{drugs.name}}</text>
				<text class="text-ABC text-blue">drugs</text>
			</view>
		</view>

		<view class="flex justify-center">
			<image :src="imagesUrl(drugs.imagesUrl)" mode="aspectFit"></image>
		</view>
		
		<uni-section :title="drugs.name" type="line" style="text-align: left;">
			<uni-notice-bar :text="'标签:     '+drugs.label" />
		</uni-section>
		<uni-card is-full :is-shadow="false" class="details">
			<view class="flex">
				<view>介绍:</view>
				<text>{{drugs.introduce}}</text>
			</view>
			<view class="flex padding-top-sm">
				<view>可以治疗：</view>
				<u-tag v-for="symptom in drugs.symptoms" :key="symptom.id"
				class="margin-left-sm"
				:text="symptom.name" mode="light" type="success" style="padding: 2rpx;"/>
			</view>
		</uni-card>
		
		<view class="cu-load load-modal" v-if="loadModal">
			<view class="cuIcon-emojifill text-orange"></view>
			<image src="/static/logo.png" mode="aspectFit"></image>
			<!-- <view class="gray-text">加载中...</view> -->
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				drugs: {},
				loadModal:false,
			};
		},
		onLoad(option) {
			this.drugs.id = option.id
			this.load()
		},
		methods: {
			load() {
				this.loadModal = true
				this.http.get('/care-service/drugs/findById/' + this.drugs.id).then(res => {
					this.drugs = res.data
					this.loadModal = false
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
