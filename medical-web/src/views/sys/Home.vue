<template>
  <div>主页
    <div>{{data}}</div>
    <el-input v-model="text"></el-input>
    <el-button @click="send">发送</el-button>
  </div>
</template>

<script>
import { sendSock, createWebSocket, closeSock ,setParam} from '@/utils/webSocket'
import {timeFormate} from "@/utils";

export default {
  name: "Home",
  created(){
    this.init()
  },
  data(){
    return{
      text:'',
      data:''
    }
  },
  methods:{
    init(){
      setParam('小明')
      createWebSocket(this.callback)
    },
    callback(data){
      this.data=data
    },
    send(){
      let obj = {
        from: '小明',
        to: '小花',
        type: 'text',
        content: '你好呀！',
        time: new Date()
      }
      sendSock(JSON.stringify(obj))
    }
  },
  beforeDestroy(){
    closeSock();
  }
}
</script>

<style scoped>

</style>