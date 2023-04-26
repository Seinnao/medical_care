<template>
	<view>
		<uni-nav-bar dark fixed shadow background-color="#00aaff" status-bar title="医疗服务">
		</uni-nav-bar>
		<uni-search-bar class="uni-mt-10" radius="100" placeholder="你有什么问题可以问我" clearButton="always" cancelButton="none"
			@confirm="search" />

		<view v-for="(item,index) in dataList" :key="item.data.id">
			<view v-if="item.type === 'drugs'">
				<view class="cu-card case no-card margin-bottom-sm drugs">
					<view class="cu-item shadow padding-sm">
						<image :src="imagesUrl(item.data.imagesUrl)" class="images images_border" mode="aspectFit"></image>
						<view class="title_all title text-cut text-bold text-blue margin-bottom-sm">{{item.data.name}}</view>
						<uni-tag class="tag text spacing-magin" :text="item.data.label" :type="item.data.label === '非处方药'? 'success':'warning'" />
						<view class="introduce">
							<!-- text-xs -->
							<view class="flex p-xs mb-sm spacing">
								<text class="text text-grey">介绍：{{item.data.introduce }}</text>
							</view>
							<view class="flex justify-start align-center spacing">
								<text class="text text-grey">可以治疗</text>
								<u-tag v-for="symptom in item.data.symptoms" :key="symptom.id"
								class="margin-left-sm"
								:text="symptom.name" mode="light" type="success" style="padding: 2rpx;"/>
							</view>
						</view>
					</view>
				</view>
			</view>
			<view v-if="item.type === 'symptom'">
				<view class="cu-card case no-card margin-bottom-sm symptom">
					<view class="cu-item shadow padding-sm">
						<view class="flex title_all text-cut text-bold text-blue">{{item.data.name}}</view>
						<view class="flex spacing">
							<text class="text text-grey">介绍：{{ item.data.introduce }}</text>
						</view>
						<view class="flex spacing">
							<uni-tag class="tag text spacing-magin" text="可用药品" type="primary" />
						</view>
						<view class="grid spacing col-5">
							<view class="text-center" v-for="drugs in item.data.drugs" :key="drugs.id" @tap="openDrug(drugs.id)">
								<image :src="imagesUrl(drugs.imagesUrl)" class="drugs-images images_border" mode="aspectFit"></image>
								<!-- <text class="text">{{drugs.name}}</text> -->
							</view>
						</view>
					</view>
				</view>
			</view>
			<view v-if="item.type === 'disease'">
				<view class="cu-card case no-card margin-bottom-sm disease">
					<view class="cu-item shadow padding-sm">
						<view class="flex title_all text-cut text-bold text-blue">{{item.data.name}}</view>
						<view class="flex spacing">
							<text class="text text-grey">介绍：{{ item.data.introduce }}</text>
						</view>
						<view class="flex justify-start align-center spacing">
							<text class="text text-grey">症状：</text>
							<u-tag v-for="symptom in item.data.symptom" :key="symptom.id"
							class="margin-left-sm"
							:text="symptom.name" mode="light" type="success" style="padding: 2rpx;"/>
						</view>
						<view class="flex spacing">
							<uni-tag class="tag text spacing-magin" text="参考药品" type="primary" />
						</view>
						<view class="grid spacing col-5">
							<view class="text-center" v-for="drugs in item.data.drugs" :key="drugs.id" @tap="openDrug(drugs.id)">
								<image :src="imagesUrl(drugs.imagesUrl)" class="disease-images images_border" mode="aspectFit"></image>
								<!-- <text class="text">{{drugs.name}}</text> -->
							</view>
						</view>
					</view>
				</view>
			</view>
			<view v-if="item.type === 'forum'">
				<view class="cu-card case no-card margin-bottom-sm my-card">
					<view class="cu-item shadow" @tap="openPost(item.data.id)">
						<view class="padding forum" style="padding-bottom: 0;">
							<view class="title">
								<view class="text-cut text-bold text-blue">{{item.data.title}}</view>
							</view>
							<view class="text-content padding-top-sm text-grey">{{item.data.outline}}</view>
						</view>
						<view class="cu-list menu-avatar">
							<view class="cu-item">
								<view class="cu-avatar round lg"
									:style="'background-image:url('+imagesUrl(item.data.avatarUrl)+');'">
								</view>
								<view class="content flex-sub">
									<view class="text-grey">{{item.data.nickname}}</view>
									<view class="text-gray text-sm flex justify-between">
										{{$calendar(item.data.time,false)}}
										<view class="text-gray text-sm">
											<text class="cuIcon-attentionfill margin-lr-xs"></text> {{item.data.see}}
											<text class="cuIcon-favorfill margin-lr-xs"></text>
											{{ item.data.collection}}
											<text class="cuIcon-messagefill margin-lr-xs"></text>
											{{ item.data.commentSum}}
										</view>
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>

</template>

<script>
	export default {
		data() {
			return {
				content: "",
				dataList: [],
				sear: true,
				loading: false
			}
		},
		mounted() {

		},
		methods: {
			search(data) {
				console.log(data)
				this.loading = true
				this.http.post('care-service/ai/search', {
					data: data.value
				}).then(res => {
					if (res.code === 200) {
						this.dataList = res.data;
						this.sear = this.dataList.length <= 0;
					}
					this.loading = false
				})
			},
			openPost(id) {
				//console.log("hhhhhh")
				uni.navigateTo({
					url: "/pages/home/forum/Post?id=" + id
				});
			},
			openDrug(id){
				uni.navigateTo({
					url: `/pages_home/home/drugsItem?id=${id}`,
					animationType: 'slide-in-right',
					animationDuration: 500,
				});
			}
		}
	}
</script>

<style scoped lang="scss">
	.forum {
		font-size: 26rpx;

		.title {
			font-size: 32rpx;
		}
	}

	.spacing{
		padding-top: 18rpx;
	}
	
	.spacing-bottom{
		padding-bottom: 18rpx;
	}

	.spacing-magin{
		magin-top: 18rpx;
	}


	.my-card {
		opacity: 1;
	}

	.my-card:hover {
		opacity: 0.8;
	}

	.title_all {
		top: 30rpx;
		font-size: 32rpx;
	}

	.images_border{
		border-style: solid;
		border-width: thin;
		border-color: #e3e8ec;
		border-radius: 15%;
	}

	.text{
		font-size: 28rpx;
	}

	.drugs {
		position: relative;
		width: 100%;
		height: 400rpx;
		
		.images {
			width: 300rpx;
			height: 230rpx;
		}

		.title {
			position: absolute;
			left: 340rpx;
			top: 20rpx;
		}
		
		.tag{
			position: absolute;
			left: 340rpx;
			top: 85rpx;
		}
		
		.introduce{
			position: absolute;
			left: 340rpx;
			top: 120rpx;
		}

	}

	.symptom {
		.drugs-images{
			width: 120rpx;
			height: 80rpx;
		}
	}

	.disease {
		.disease-images{
			width: 120rpx;
			height: 80rpx;
		}
	}
</style>
