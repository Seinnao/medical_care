<template>
  <div class="content">
    <el-row :gutter="10" style="margin-bottom: 30px">
     <el-col :span="18"><el-input v-model="query" placeholder="您有什么问题可以问我!" size="small"></el-input></el-col>
      <el-col :span="6"><el-button type="primary" icon="el-icon-search" size="small">搜索</el-button></el-col>
    </el-row>
    <el-empty description="请搜索" v-if="sear"></el-empty>
  </div>
</template>

<script>
export default {
  name: "Home",
  created(){
    this.init()
  },
  data(){
    return{
      query:'',
      sear:true
    }
  },
  methods:{
    init(){
      //createWebSocket(this.callback,"小明")
    },
    search() {
      // 在这里执行搜索操作
      console.log('搜索关键词：', this.query);
    },
  },
}
</script>

<style scoped>
.content{
}
</style>