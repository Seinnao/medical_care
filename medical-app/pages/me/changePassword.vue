<template>
	<view>
		<uni-nav-bar dark fixed shadow background-color="#00aaff" status-bar title="修改密码" left-icon="left"
			left-text="返回" @clickLeft="back">
		</uni-nav-bar>
		<view class="padding">
			<u-form :model="form" :rules="rules" ref="uForm">
				<u-form-item label="旧密码" prop="password" label-width="150">
					<u-input type="password" v-model="form.password" placeholder="请输入旧密码" />
				</u-form-item>
				<u-form-item label="新密码" prop="newPassword" label-width="150">
					<u-input type="password" v-model="form.newPassword" placeholder="请输入新密码" />
				</u-form-item>
				<u-form-item label="确认密码" label-width="150" prop="confirmPassword">
					<u-input type="password" v-model="form.confirmPassword" placeholder="请确认密码"></u-input>
				</u-form-item>
			</u-form>
			<u-button @click="submit">提交</u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 基础表单数据
				user:{},
				form: {
					newPassword: '',
					password: '',
					confirmPassword: '',
				},
				//校验规则
				rules: {
					password: [{
						required: true,
						message: '旧密码不能为空',
						trigger: 'blur',
					}],
					newPassword: [{
							required: true,
							message: '新密码不能为空',
							trigger: 'blur',
						},
						{
							validator: (rule, value, callback) => {
								return this.form.password !== value;
							},
							message: '新密码不能和旧密码一样',
							// 触发器可以同时用blur和change，二者之间用英文逗号隔开
							trigger: ['change', 'blur'],
						}
					],
					confirmPassword: [{
							required: true,
							message: '请确认密码',
							trigger: 'blur',
						},
						{
							validator: (rule, value, callback) => {
								return this.form.newPassword === value;
							},
							message: '两次输入的密码不相等',
							// 触发器可以同时用blur和change，二者之间用英文逗号隔开
							trigger: ['change', 'blur'],
						}
					]
				}
			}
		},
		onLoad() {
			this.user = JSON.parse(uni.getStorageSync("user"));
			this.form.username = this.user.username
		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		methods: {
			submit() {
				this.$refs.uForm.validate(valid => {
					if (valid) {
						console.log('验证通过');
						this.http.post("user-service/user/password", this.form).then(res => {
							if (res.code === 200) {
								uni.showToast({
									title: "修改成功",
									icon: 'success',
									complete: () => {
										uni.setStorageSync("user",null)
										uni.setStorageSync("token",null)
										setTimeout(() => {
											uni.reLaunch({
												url: '/pages/login/index',
											});
										}, 500);
									}
								});
							} else {
								uni.showToast({
									title: res.msg,
									icon: 'error'
								});
							}
						})
					}
				});
			},
		}
	}
</script>

<style>
</style>
