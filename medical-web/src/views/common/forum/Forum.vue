<template>
  <div class="total">
    <el-row :gutter="10" class="header">
      <el-col :span="18"><el-input v-model="query" placeholder="请输入关键字" size="small" clearable></el-input></el-col>
      <el-col :span="4"><el-button type="primary" icon="el-icon-search" size="small" @click="search">搜索</el-button></el-col>
<!--      <el-col :span="2"><el-button type="success" icon="el-icon-position" size="small">发表</el-button></el-col>-->
    </el-row>

    <div class="float">
      <el-tooltip effect="dark" content="发表我的看法" placement="top-end" style="margin-bottom: 10px">
        <el-button type="success" icon="el-icon-position"
                   @click="publish()"
                   circle size="small"></el-button>
      </el-tooltip>
      <br/>
      <el-tooltip effect="dark" content="回到顶部" placement="bottom">
        <el-button icon="el-icon-caret-top" circle size="small" @click=""></el-button>
      </el-tooltip>
    </div>

    <div v-infinite-scroll="load" :infinite-scroll-disabled="isAll" class="main">
      <div class="item" v-for="item in forumDate" :key="item.id">
        <div class="text">
          <el-link class="title" @click="openArticle(item)">{{ item.title }}</el-link>
          <div class="info">{{item.outline}}</div>
        </div>
        <div class="icon">
          <i class="el-icon-view on" style="margin-right:15px">{{item.see}}</i>
          <i class="el-icon-star-off on" style="margin-right:15px">{{item.collection}}</i>
          <i class="el-icon-chat-dot-square on">{{item.collection}}</i>
        </div>
        <div class="author">
          <div class="time">{{ $moment(item.time).calendar()}}</div>
          <div>{{item.nickname}}</div>
        </div>
        <img class="image" :src="imagesUrl(item.avatarUrl)" >
        <el-divider class="hrs"></el-divider>
      </div>
    </div>

    <el-empty description="本界面空空如也" v-if="forumDate.length <= 0"></el-empty>
  </div>
</template>

<script>
export default {
  name: "Forum",
  data(){
    return{
      isAll:false,
      query:'',
      forumDate:[],
      pageNum: 1,
      pageSize: 10
    }
  },
  methods:{
    load(){
      this.http.get("/care-service/forum/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          title: this.query,
        }
      }).then(res => {
        if(res.data.total <= this.pageNum*this.pageSize){
          this.isAll = true;
        }else {
          this.pageNum = this.pageNum + 1;
        }
       res.data.records.forEach(record =>{
         this.forumDate.push(record)
       })
      })
    },
    publish(){
      this.$router.push({name:'发布帖子'})
    },
    openArticle(item){
      this.$router.push({name:'post', params:{id:item.id}})
    },
    search(){
      this.pageNum = 1
      if(this.forumDate.length <= 0){
        this.load()
      }else {
        this.forumDate = []
        this.isAll = false;
      }
    }
  }
}
</script>

<style scoped lang="scss">
.total {
  position: relative;
  width: 100%;
  .header{
    margin-bottom: 20px;
  }
  .float{
    position: fixed;
    right: 100px;
    top: 70vh;
  }
  .main {
    background-color: #ffffff;
    width: 70%;
    .item {
      margin-bottom: 50px;
      width: 100%;
      height: 50px;
      position: relative; //相对于本来位置移动
      padding: 0 20px;
      .image {
        position: absolute; //相对祖先移动
        display: block;
        top: 10px;
        right: 20px;
        height: 50px;
        width: 50px;
        object-fit: cover;
        border-radius: 30%;
      }
      .text {
        position: absolute; //相对祖先移动
        left: 10px;
        width: 100%;
        //display: inline-block;
        //line-height:50px;
        .title {
          font-size: 18px;
          font-weight: 600;
        }
        .info {
          color: #909399;
          margin-top: 10px;
          font-size: 12px;
          width: 50%;
          height: 60px;
          overflow: hidden;          /*溢出隐藏*/
          white-space: nowrap;      /*溢出不换行*/
          text-overflow: ellipsis;
        }
      }
      .author{
        position: absolute;
        right: 85px;
        font-size: 14px;
        color: #79bbff;
        top: 15px;
        .time {
          margin-bottom: 2px;
          color: #79bbff;
          font-size: 12px;
        }
      }
      .icon {
        left: 10px;
        position: absolute;
        font-size: 12px;
        top: 60px;
        .on {
          color: #06a7bd;
        }
      }
      .hrs{
        position: absolute;
        top: 60px;
      }
    }
  }


}

</style>