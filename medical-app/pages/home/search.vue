<template>
	<view>
		<uni-nav-bar dark fixed shadow background-color="#00aaff" status-bar title="药品查询" left-icon="left"
			left-text="返回" @clickLeft="back">
		</uni-nav-bar>
		<uni-search-bar class="uni-mt-10" radius="100" placeholder="药品名称" clearButton="always" cancelButton="none"
			@confirm="search" />
		<!-- #ifdef MP-WEIXIN -->
		<towxml :nodes="content" />
		<!-- #endif -->
		<!-- #ifdef APP-PLUS -->
		<view class="u-content">
			<u-parse :html="content" :selectable="true" :tag-style="style" :show-with-animation="true"></u-parse>
		</view>
		<!-- #endif -->
	</view>

</template>

<script>
	export default {
		data() {
			return {
				content: "<h1>滚滚滚<h1/>",
				style:{
					h1:"",
					h2:"",
					h3:"",
					h4:"",
					h5:"",
					p:"",
					span:"",
					
				}
			}
		},
		mounted() {
			this.http.get(`/care-service/forum/getPost/1649700020898504706`).then(res => {
				// #ifdef MP-WEIXIN
				this.content = this.towxml(res.data.content, 'markdown')
				// #endif
				// #ifdef APP
				this.content = this.md.render(res.data.content)
				// #endif
			})
		},
		methods: {
			search(data) {
				console.log(data)
			}
		}
	}
</script>

<style scoped lang="scss">
.u-content {
	margin: 20rpx;
	line-height: 1.8;
}
</style>
