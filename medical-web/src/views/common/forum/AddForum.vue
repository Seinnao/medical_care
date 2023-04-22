<template>
  <div style="background-color: #ffffff">
    <el-button type="primary" @click="goBack" icon="el-icon-arrow-left" class="m-10">返回</el-button>
    <el-form label-position="left" label-width="80px" :model="form">
      <el-form-item label="标题">
        <el-input v-model="form.title" maxlength="20" show-word-limit></el-input>
      </el-form-item>
      <el-form-item label="概要">
        <el-input v-model="form.outline" type="textarea" maxlength="40" show-word-limit></el-input>
      </el-form-item>
      <el-form-item label="内容">
        <Markdown @change="change" mak_class="height:80px" :content="form.content"></Markdown>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">提交</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Markdown from "@/components/commom/Markdown";
export default {
  name: "AddForum",
  components: {
    Markdown
  },
  data(){
    return{
      form:{}
    }
  },
  created() {
    this.init()
  },
  methods:{
    init(){
      this.form = {}
      let user = JSON.parse(localStorage.getItem("user"));
      this.form.userId = user.id
      this.form.content = '这里是markdown编辑的内容'
    },
    change(data,content){
      this.form.content = content
    },
    goBack(){
      this.$router.back();
    },
    submit(){
      this.http.post("/care-service/forum", this.form).then(res => {
        if (res.code === 200) {
          this.$message.success("发布成功")
          this.dialogFormVisible = false
          this.goBack();
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reset(){
      this.init()
    }
  }

}
</script>

<style scoped>
</style>