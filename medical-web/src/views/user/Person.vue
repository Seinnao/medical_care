<template xmlns="">
  <el-card style="width: 500px;">
    <el-form label-width="80px" size="small">
      <el-upload
          class="avatar-uploader"
          action=""
          :show-file-list="false"
          :http-request="filesRequest"
          :on-success="handleAvatarSuccess"
      >
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>

      <el-form-item label="用户名">
        <el-input v-model="form.username" disabled autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "Person",
  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getUser().then(res => {
     // console.log(data)
      this.form = res
    })
  },
  methods: {
    async getUser() {
      return (await this.http.get("user-service/user/username/" + this.user.username)).data
    },
    save() {
      this.http.post("user-service/user", this.form).then(res => {
        if (res.code === 200) {
          this.$message.success("保存成功")

          // 触发父级更新User的方法
          this.$emit("refreshUser")

          // 更新浏览器存储的用户信息
          this.getUser().then(data => {
            data.token = JSON.parse(localStorage.getItem("user")).token
            localStorage.setItem("user", JSON.stringify(data))
          })

        } else {
          this.$message.error("保存失败")
        }
      })
    },
    filesRequest(data){
      const form = new FormData()
      form.append('file', data.file);
      //console.log('22222222222222',form)
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
      //console.log('111111111',res)
      this.form.avatarUrl = res
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
</style>
