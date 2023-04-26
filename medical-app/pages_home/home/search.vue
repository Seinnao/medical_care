<template>
	<view>
		<uni-nav-bar dark fixed shadow background-color="#00aaff" status-bar title="药品查询" left-icon="left"
			left-text="返回" @clickLeft="back">
		</uni-nav-bar>
		<uni-search-bar class="uni-mt-10" radius="100" placeholder="药品名称" clearButton="always" cancelButton="none"
			@confirm="search" />
	</view>

</template>

<script>
	export default {
		data() {
			return {
				content: ""
			}
		},
		mounted() {
		},
		methods: {
			search(data) {
				console.log(data)
			}
		}
	}
</script>

<style scoped lang="scss">
</style>
