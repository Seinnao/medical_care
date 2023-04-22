<template>
  <div>
    <el-row style="margin-top: 20px">
      <el-col :span="16" :offset="4">
        <el-card class="box-card" shadow="never" :body-style="{ padding: 0 }">
          <div slot="header" class="clearfix">
            <h1>{{post.title}}</h1>
            <div class="header_time"><i class="el-icon-time"></i> {{$moment(post.time).calendar()}}</div>
            <div class="avatar" >
              <el-avatar :size="50" fit="cover">
                <img :src="imagesUrl(post.avatarUrl)"/>
              </el-avatar>
            </div>
          </div>
          <mavon-editor class="md" :value="post.content"
                        :subfield="false"
                        :defaultOpen="'preview'"
                        :toolbarsFlag="false"
                        :editable="false"
                        :scrollStyle="true"
                        :ishljs="true"/>
          <div class="foot">
            <el-button type="text" icon="el-icon-star-off" size="medium">收藏</el-button>
            <el-button type="text" icon="el-icon-chat-dot-square" size="medium" @click="comment">评论</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-drawer
        title="评论"
        :visible.sync="drawer"
        direction="rtl"
        :before-close="handleClose">
      <el-input v-model="form.outline" type="textarea" maxlength="300" show-word-limit></el-input>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: "Post",
  data(){
    return{
      post:{
        avatarUrl:''
      },
      drawer: false,
      form:{}
    }
  },
  created(){
    this.init()
  },
  methods:{
    init(){
      this.http.get(`/care-service/forum/getPost/${this.$route.params.id}`).then(res =>{
        if(res.code === 200){
          this.post = res.data
          this.$forceUpdate() //强制渲染组件
        }
      })
    },
    handleClose(){
      this.drawer = false;
    },
    comment(){
      this.drawer = true;
    }
  }
}
</script>

<style lang="scss">
.clearfix{
  position: relative;
  h1{
    font-weight: 500
  }
  .header_time{
    margin-top: 10px;
    font-size:12px
  }
  .avatar{
    position: absolute;
    right: 10px;
    top: 10px;
  }
}
.md{
  margin: 0;
  font-size: 14px;
  color: #606266;
  h1{
    font-size: 22px;
  }
  h2{
    font-size: 20px;
  }
  h3{
    font-size: 18px;
  }
  h4{
    font-size: 16px;
  }
  h5{
    font-size: 14px;
  }
  img{
    clear: both;
    display: block;
    margin: auto
  }
}
.foot{
  margin: 20px 0;
  height: 30px
}
</style>