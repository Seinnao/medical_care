<template>
  <div class="box">
    <div class="content">
      <div class="main">
        <h1>欢迎注册！</h1>
        <p>感谢您使用本平台</p>
        <el-form ref="loginForm" :model="dataForm"
                 @keyup.enter.native="register()"
                 label-width="0"
                 class="login_from">
          <el-form-item prop="username" class="register_item">
            <el-input
                class="login_input"
                placeholder="请输入账号"
                prefix-icon="el-icon-user"
                clearable
                v-model="dataForm.username">
            </el-input>
          </el-form-item>
          <el-form-item prop="password" class="register_item">
            <el-input
                placeholder="请输入密码"
                prefix-icon="el-icon-lock"
                type="password"
                clearable
                v-model="dataForm.password">
            </el-input>
          </el-form-item>

          <el-form-item prop="re_password" class="register_item">
            <el-input
                placeholder="再次输入密码"
                prefix-icon="el-icon-lock"
                type="password"
                clearable
                v-model="dataForm.rePassword">
            </el-input>
          </el-form-item>

          <el-form-item prop="tel" class="register_item">
            <el-input v-model="dataForm.tel"
                      prefix-icon="el-icon-key"
                      placeholder="请输入手机号"
                      clearable>
            </el-input>
          </el-form-item>

          <el-button type="text"
                     style="margin: 10px 0;height: 10px"
                     @click="toLogin"
          >返回登录</el-button>

          <el-form-item class="register_item">
            <el-button type="primary" class="login" @click="register()" style="margin-top:10px">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import {isTel} from "@/utils";
export default {
  name: "Register",
  data() {
    return {
      dataForm:{
        username: '',
        password: '',
        rePassword: '',
        tel: '',
      },
    }
  },
  methods: {
    register() {
      if(!this.dataForm.username){
        this.$message.error("用户名不能为空")
        return
      }

      if(!this.dataForm.password){
        this.$message.error("请输入密码")
        return
      }

      if(this.dataForm.password !== this.dataForm.rePassword){
        this.$message.error("两次密码不匹配")
        return
      }

      if(!isTel(this.dataForm.tel)){
        console.log(this.dataForm)
        this.$message.error("电话号码有误")
        return
      }

      this.http.post('/user-service/user/register',this.dataForm).then(res =>{
        if(res.data.code === 200){
          this.$message({
            message: '注册成功',
            type: 'success'
          })
          this.toLogin()
        }else {
          this.$message.error(res.data.msg)
        }
      })

    },
    toLogin(){
      this.$router.replace({name: 'login'});
    }
  },
  created() {

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

.box .content .main .register_item {
  font-size: 1vw;
  height: 2vw;
}

.box .content .main .login {
  width: 100%;
  height: 2.7vw;
  margin-top: 2vw;
}

</style>