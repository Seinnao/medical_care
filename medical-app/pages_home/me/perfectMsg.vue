<template>
	<viwe>
		<uni-nav-bar dark fixed shadow background-color="#00aaff" status-bar title="完善信息" left-icon="left"
			left-text="返回" @clickLeft="back" />
		<view class="padding bg-white">
			<u-form :model="form" :rules="rules" ref="uForm">
				<u-form-item :leftIconStyle="{color: '#888', fontSize: '32rpx'}" left-icon="account" label-width="150"
					label="用户名:" prop="username">
					<u-input placeholder="请输入用户名" v-model="form.username" type="text" :disabled="true"></u-input>
				</u-form-item>
				<u-form-item label-width="150" label="昵称:" prop="nickname">
					<u-input placeholder="请输入昵称" v-model="form.nickname" type="text"></u-input>
				</u-form-item>
				<u-form-item label="头像:" prop="photo" label-width="130">
					<!-- <u-upload width="160" height="160" :auto-upload="false"></u-upload> -->
					<view class="cu-form-group" style="height: 140rpx;width: 500rpx;">
						<view class="grid col-4 grid-square flex-sub">
							<view class="bg-img" @tap="ChooseImage" :data-url="imagesUrl(form.avatarUrl)"
								v-if="form.avatarUrl">
								<image :src="imagesUrl(form.avatarUrl)" mode="aspectFill"></image>
							</view>
							<view class="solids" @tap="ChooseImage" v-if="!form.avatarUrl">
								<text class='cuIcon-cameraadd'></text>
							</view>
						</view>
					</view>
				</u-form-item>
				<u-form-item label-width="150" label="电话号码:" prop="phone">
					<u-input placeholder="请输入电话号码" v-model="form.phone" type="text"></u-input>
				</u-form-item>
				<u-form-item label-width="150" label="邮箱:" prop="email">
					<u-input placeholder="请输入邮箱" v-model="form.email" type="text"></u-input>
				</u-form-item>
				<u-form-item label-width="150" label="你的地址:" prop="address">
					<u-input placeholder="请输入地址" v-model="form.address" type="textarea"></u-input>
				</u-form-item>
			</u-form>
			<u-button @click="submit">提交</u-button>
		</view>
	</viwe>
</template>

<script>
	import {
		chooseImage
	} from '../../utils/index'
	export default {
		data() {
			return {
				// 基础表单数据
				user: {},
				form: {
					avatarUrl: '',
					username: '',
					nickname: '',
					email: '',
					phone: '',
					address: ''
				},
				imgList: [],
				rules: {
					nickname: [{
						required: true,
						message: '昵称不能为空',
						trigger: 'blur',
					}, {
						min: 2,
						max: 5,
						message: '昵称长度在2到5个字符',
						trigger: ['change', 'blur'],
					}, ],
					phone: [{
						required: true,
						message: '请输入手机号',
						trigger: ['change', 'blur'],
					}, {
						validator: (rule, value, callback) => {
							return this.$u.test.mobile(value);
						},
						message: '手机号码不正确',
						// 触发器可以同时用blur和change，二者之间用英文逗号隔开
						trigger: ['change', 'blur'],
					}],
					email: [{
						validator: (rule, value, callback) => {
							return this.$u.test.email(value);
						},
						message: '邮箱不正确',
						// 触发器可以同时用blur和change，二者之间用英文逗号隔开
						trigger: ['change', 'blur'],
					}]
				}
			}
		},
		onLoad() {
			this.user = JSON.parse(uni.getStorageSync("user"));
			this.getUser().then(data => {
				this.form = data
			})
		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		methods: {
			async getUser() {
				return (await this.http.get("user-service/user/username/" + this.user.username)).data
			},
			submit() {
				this.$refs.uForm.validate(valid => {
					if (valid) {
						console.log('验证通过');
						this.http.post("user-service/user", this.form).then(res => {
							if (res.code === 200) {
								uni.showToast({
									title: '保存成功',
									icon: 'success'
								})
								this.getUser().then(data => {
									uni.setStorageSync("user", JSON.stringify(data))
								})
							} else {
								uni.showToast({
									title: '保存失败',
									icon: 'error'
								})
							}
						})
					}
				})
			},
			ChooseImage() {
				chooseImage().then(res => {
					if (res.code === 200) {
						this.form.avatarUrl = res.url
						console.log(this.form.avatarUrl)
					}
				})
			},
		}
	}
</script>

<style>
</style>
