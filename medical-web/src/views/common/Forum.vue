<template>
  <div class="total">
    <el-row :gutter="10" class="header">
      <el-col :span="18"><el-input v-model="query" placeholder="请输入关键字" size="small"></el-input></el-col>
      <el-col :span="4"><el-button type="primary" icon="el-icon-search" size="small">搜索</el-button></el-col>
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
<!--        $emit('runBack')-->
        <el-button icon="el-icon-caret-top" circle size="small" @click=""></el-button>
      </el-tooltip>
    </div>
    <div v-infinite-scroll="load" :infinite-scroll-disabled="isAll" class="main">
      <div class="item" v-for="item in forumDate" :key="item.id">
        <div class="text">
          <el-link class="title" @click="openArticle(item)">{{ item.title }}</el-link>
          <!--        <span class="time">{{ $moment(item.time).calendar()}}</span>-->
          <div class="info">{{item.content}}</div>
        </div>
        <div class="icon">
          <i class="el-icon-view on" style="margin-right:15px">230</i>
          <i class="el-icon-star-off on" style="margin-right:15px">345</i>
          <i class="el-icon-chat-dot-square on">34</i>
        </div>
        <div class="author">
          <div class="time">{{ $moment(item.time).calendar()}}</div>
          <div>小明</div>
        </div>
        <img class="image" :src="item.imageUrl" >
        <el-divider class="hrs"></el-divider>
      </div>
    </div>

    <el-drawer
        title="发表文章"
        :visible.sync="drawer"
        direction="rtl"
        :before-close="handleClose">
      <span>我来啦!</span>

    </el-drawer>
  </div>
</template>

<script>
export default {
  name: "Forum",
  data(){
    return{
      id:2,
      isAll:false,
      drawer: false,
      query:'',
      forumDate:[{
        id:1,
        imageUrl: require("../../assets/test/R-C.jpg"),
        title:'震惊！真相竟然是这样',
        content:'俗话说得好，一张图片胜过千言万语，尤其是对于能够通过图像清晰有效地说明检查结果的病例报告而言。但是，如果没有正当理由，请避免使用图片 - 只有当他们具备关联时才这样做。例如，一个新鉴定的致病微生物的宏观和微观图像是必不可少的， 而你在文中其他地方已经清楚地解释了模型的图片可能是过度的。',
        time: new Date(),
      }],
    }
  },
  methods:{
    load(){
      this.id ++;
      if(this.id > 20){
        this.isAll = true;
      }
      this.forumDate.push({
        id: this.id,
        imageUrl: require("../../assets/test/R-C (3).jpg"),
        title: '震惊！真相竟然不是是这样',
        time: new Date(),
      })
    },
    handleClose(){
      this.drawer = false;
    },
    publish(){
      this.drawer = true;
    },
    openArticle(){

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