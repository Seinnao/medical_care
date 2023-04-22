<template>
  <div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="warning" @click="reset">刷新 <i class="el-icon-refresh"></i></el-button>
    </div>
    <el-row :gutter="10" v-if="list.length > 0">
     <el-col :span="6" v-for="item in list" :key="item.id" style="margin-bottom: 10px">
       <el-card class="box-card" body-style="padding:8px">
         <el-image :src="imagesUrl(item.url)" class="swiper_images"
                   :preview-src-list="[imagesUrl(item.url)]" fit="cover"/>
         <el-row :gutter="10" type="flex" class="row-bg" justify="space-between">
           <el-col :span="18"><span class="swiper_name">大小：{{ item.size }} kb</span></el-col>
           <el-col :span="6"><el-button type="text" class="button" @click="del(item.id)">删除图片</el-button></el-col>
         </el-row>
       </el-card>
     </el-col>
    </el-row>
    <el-empty v-else description="没有图片哦"></el-empty>

    <el-dialog title="上传轮播图" :visible.sync="dialogFormVisible" width="30%">
      <el-upload
          class="images-uploader"
          action=""
          :show-file-list="false"
          :http-request="filesRequest"
          :on-success="handleImagesSuccess">
        <i v-if="!url" class="el-icon-plus images-uploader-icon"></i>
        <img v-else :src="imagesUrl(url)" class="images">
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">退 出</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {compress} from "@/utils";

export default {
  name: "SwiperImages",
  data(){
    return{
      url:'',
      list:[],
      dialogFormVisible: false,
    }
  },
  created() {
    this.load()
    this.url = ''
  },
  methods:{
    load() {
      this.http.get("file-service/file/swiperImages").then(res => {
        this.list = res.data
      })
    },
    handleAdd(){
      this.url = ''
      this.dialogFormVisible = true
    },
    reset(){
      this.load()
    },
    filesRequest(data){
      compress(data.file,{width:800}).then(rst =>{
        const form = new FormData()
        form.append('file', rst);
        this.http({
          url: 'file-service/file/upload/swiper',
          method:'post',
          data: form,
          headers: { 'Content-Type': 'multipart/form-data' }
        }).then((res) => {
          if (res.code === 200) {
            this.url = res.url
            this.$forceUpdate() //强制渲染组件
            this.$message.success("上传成功")
            this.load()
          } else {
            this.$message.error(res.msg)
          }
        })
      })
    },
    del(id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.http.delete("file-service/file/" + id).then(res => {
          if (res.code === 200) {
            this.$message.success("删除成功")
            this.load()
          } else {
            this.$message.error("删除失败")
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleImagesSuccess(res){
      this.form.imagesUrl = res
    }
  }

}
</script>

<style lang="scss">
.images-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  width: 220px;
  height: 120px;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.images-uploader .el-upload:hover {
  border-color: #409EFF;
}
</style>

<style scoped>
.images-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.images-uploader-icon {
  font-size: 20px;
  color: #8c939d;
  width: 220px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.images {
  width: 220px;
  height: 120px;
  object-fit: cover;
  display: block;
}
.swiper_images{
  width: 100%;
  height: 100px;
}
.swiper_name{
  font-size:12px;
  color: #E6A23C;
}
</style>