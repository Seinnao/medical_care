<template>
  <div class="box">
    <div class="content">
      <div class="main">
        <h1>欢迎注册！</h1>
        <p>感谢您使用本平台</p>
        <el-form ref="loginForm" :model="dataForm"
                 @keyup.enter.native="login()"
                 label-width="0"
                 :rules="dataRule"
                 class="login_from">
          <el-form-item prop="userName" class="login_item">
            <el-input
                class="login_input"
                placeholder="请输入账号"
                prefix-icon="el-icon-user"
                clearable
                v-model="dataForm.userName">
            </el-input>
          </el-form-item>
          <el-form-item prop="password" class="login_item">
            <el-input
                class="login_input"
                placeholder="请输入密码"
                prefix-icon="el-icon-lock"
                type="password"
                clearable
                v-model="dataForm.password">
            </el-input>
          </el-form-item>

          <el-form-item>
            <el-input
                class="login_input"
                placeholder="再次输入密码"
                prefix-icon="el-icon-lock"
                type="password"
                clearable
                v-model="dataForm.password">
            </el-input>
          </el-form-item>

          <el-form-item prop="captcha" class="login_item">
            <el-input class="login_input"
                      v-model="dataForm.captcha"
                      prefix-icon="el-icon-key"
                      placeholder="请输入手机号"
                      clearable>
            </el-input>
          </el-form-item>

          <el-button type="text"
                     style="margin: 10px 0;height: 10px"
                     @click="toLogin"
          >返回登录</el-button>

          <el-form-item class="login_item">
            <el-button type="primary" class="login" @click="login()" style="margin-top:10px">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import {getUUID} from "@/utils";
import router from "@/router";
export default {
  name: "Register",
  data() {
    return {
      captchaPath: '',
      dataForm:{
        userName: '',
        password: '',
        captcha: '',
        uuid: '',
      },
      dataRule:{
        userName: [
          {required: true, message: '帐号不能为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'}
        ],
        captcha: [
          {required: true, message: '验证码不能为空', trigger: 'blur'}
        ]
      },
    }
  },
  methods: {
    getCaptcha() {
      console.log("获取验证码！");
      this.dataForm.uuid = getUUID();
      this.captchaPath = `${this.http.getBaseURL()}/user-service/captcha?uuid=${getUUID()}`
    },
    login() {
      this.$refs['loginForm'].validate((valid) => {
        if (valid) {
        }
      })
    },
    toLogin(){
      this.$router.replace({name: 'login'});
    }
  },
  created() {
    this.getCaptcha()
  }
}
</script>

<style scoped>
.box {
  margin: 0 auto;
  /*aspect-ratio: 192 / 108;*/
  background-color: #ffffff;
  background-image: url('~@/assets/login/background.jpg');
  background-repeat: no-repeat;
  background-size: 100%;
  height: 100vh;
  /*width: 100%;*/
  /*防止高度塌陷*/
  overflow: hidden;
}

.box .content {
  /*绝对定位*/
  position: relative;
  top: 18%;
  left: 55.84%;
  width: 34.64%;
  aspect-ratio: 598 / 605;
  background-color: rgba(255, 255, 255, 0.95);
  background-image: url('~@/assets/login/login-element.png');
  background-repeat: no-repeat;
  background-position: 300px 23px;
  background-size: 7vw;
  /*盒子阴影*/
  box-shadow: 1px 1px 20px 1px rgba(239, 105, 239, 0.91);
}

/*鼠标移动到上面触发*/
.box .content:hover {
  box-shadow: 1px 1px 40px 2px rgba(243, 108, 243, 0.95);
}

.box .content .main {
  /*绝对定位*/
  position: relative;
  top: 8%;
  left: 8%;
  width: 80%;
  height: 80%;
  /*background-color: rgba(76, 76, 84, 0.20);*/
  text-align: left;
}

.box .content .main h1 {
  padding-top: 3%;
  padding-bottom: 1%;
  font-weight: 400;
  font-size: 2.7vw;
  font-family: 'Microsoft YaHei', serif;
  color: #010103;
}

.box .content .main p {
  font-weight: 600;
  font-size: 1vw;
  color: #8480a2;
  margin-bottom: 9%;
}

.box .content .main .login_from {
  font-size: 1vw;
}

.box .content .main .login_item {
  font-size: 1vw;
  height: 2.7vw;
}

.box .content .main .login_input {
  font-size: 1vw;
  height: 3vw;
}

.box .content .main .login {
  width: 100%;
  height: 2.7vw;
  margin-top: 2vw;
}

</style>