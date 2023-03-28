<template xmlns="">
  <div style="width:100%;height: 100%">
    <el-steps :active="active"
              :space="600"
              align-center
              finish-status="success">
<!--       style="position: relative;left: 50%"-->
      <el-step title="发送请求"></el-step>
      <el-step title="等待审核"></el-step>
      <el-step :title="partStatus()" :status="partStatusStyle()"></el-step>
    </el-steps>
    <div class="first" v-if="active === 0" align="center">
      <el-card style="width: 500px;">
        <el-form label-width="80px" size="small">
          <span style="font-size:14px;color: #606266;">你的照片</span>
          <el-upload
              class="avatar-uploader"
              action=""
              :show-file-list="false"
              :http-request="filesRequest"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="form.avatarUrl" :src="imagesUrl(form.avatarUrl)" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>

          <el-form-item label="真实姓名">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="学历">
            <el-input v-model="form.education" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="自我描述">
            <el-input type="textarea" v-model="form.introduce" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="save" style="margin-left: -80px">发送申请</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
    <div v-else-if="active === 1" align="center">
      <el-row>
        <el-col>
          <el-result icon="info" title="请耐心等待管理员审核" subTitle="管理员需要审核大量请求、麻烦您耐心等待">
          </el-result>
        </el-col>
      </el-row>
    </div>
    <div v-else-if="active === 2" align="center">
      <el-row>
        <el-col>
          <el-result icon="info" title="管理员正在审核中" subTitle="大概半小时就会有结果，请您耐心等待哦！">
          </el-result>
        </el-col>
      </el-row>
    </div>
    <div v-else-if="active === 3" align="center">
      <el-row>
        <el-col>
          <el-result icon="success" title="审核通过" subTitle="恭喜您成功入住本平台！">
          </el-result>
        </el-col>
      </el-row>
    </div>
    <div v-else-if="active === 4" align="center">
      <el-row>
        <el-col>
          <el-result icon="error" title="审核不通过" subTitle="您的信息可能有点问题，请您重新提交!">
            <template slot="extra">
              <el-button type="primary" size="medium" @click="reApply()">重新申请</el-button>
            </template>
          </el-result>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import user from "@/views/user/User";

export default {
  name: "BecomeDoctor",
  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      active:0,
    }
  },
  created() {
    this.getState().then(res => {
      this.active = res.state
    })
    this.form.userId = this.user.id;
  },
  methods: {
    async getState(){
      return (await this.http.get("user-service/doctor/userId/" + this.user.id)).data
    },
    save() {
      this.http.post("user-service/doctor", this.form).then(res => {
        if (res.code === 200) {
          this.$message.success("提交成功")
          this.active = 1;
        } else {
          this.$message.error("提交失败")
        }
      })
    },
    filesRequest(data){
      const form = new FormData()
      form.append('file', data.file);
      this.http({
        url: 'file-service/file/upload',
        method:'post',
        data: form,
        headers: { 'Content-Type': 'multipart/form-data' }
      }).then((res) => {
        if (res.code === 200) {
          this.form.avatarUrl = res.url
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res
    },
    partStatus(){
      switch (this.active){
        case 0:
        case 1:
        case 3:return '审核通过'
        case 2:return '正在审核'
        case 4:return '审核不通过'
      }
    },
    partStatusStyle(){
      if(this.active === 4)
        return 'error'
    },
    reApply(){
      this.http.get("user-service/doctor/reApply/" + this.user.id).then(res =>{
        if(res.code === 200){
          this.active = 0
        }else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
.first{
  position: relative;
  top:50%;
  transform:translateY(-58%);
}
</style>
