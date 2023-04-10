<template>
  <div class="content">
    <el-row :gutter="10" style="margin-bottom: 30px">
     <el-col :span="18"><el-input v-model="query" placeholder="您有什么问题可以问我!"></el-input></el-col>
      <el-col :span="6"><el-button type="primary" icon="el-icon-search">搜索</el-button></el-col>
    </el-row>
    <el-row :gutter="10">
      <el-divider></el-divider>
     <el-col :span="22">
       <div style="background-color: #b9b9b9">
         <h1>标题</h1>
       </div>
     </el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="24"><el-skeleton /></el-col>
    </el-row>
  </div>
</template>

<script>
import { sendSock, createWebSocket, closeSock} from '@/utils/webSocket'
export default {
  name: "Home",
  created(){
    this.init()
  },
  data(){
    return{
      query:''
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
    send(){
      let obj = {
        from: '小明',
        to: '小花',
        type: 'text',
        content: '你好呀！'
      }
      sendSock(JSON.stringify(obj))
    }
  },
}
</script>

<style scoped>
.content{
}
</style>