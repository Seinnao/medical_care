<template>
  <el-container style="height: 100%">
    <el-header height="40px" style="padding: 0 10px 0 0;">
      <div style="background-color: #409EFF;height: 100%">
        <h4 style="color: #fff;line-height:40px;text-align: center;">{{ to.nickname }}</h4>
      </div>
    </el-header>
    <el-container style="height: calc(100% - 40px)">
      <el-aside width="120px" class="aside-left">
<!--        <div class="seize_a_seat"></div>-->
        <div :class="selectId !== item.id ? 'incoming_letter':'incoming_letter_active'"
             @click="switchChat(item)"
             v-for="item in chatList" :key="item.id">
          <el-row align="middle" type="flex">
            <el-col :span="12">
              <el-badge :value="item.unreadMsg" :hidden="item.unreadMsg === 0">
                <el-avatar :src="imagesUrl(item.avatarUrl)"></el-avatar>
              </el-badge>
            </el-col>
            <el-col :span="12">
              <p class="info_msg">{{item.otherParty}}</p>
            </el-col>
          </el-row>
        </div>
      </el-aside>
      <el-main style="padding: 0 10px 0 0;height: calc(100% - 40px)">
        <div class="chat-content" v-if="selectId !== 0">
          <div class="chat-text" ref="chatText">
            <!-- recordContent 聊天记录数组-->
            <div v-for="(item,index) in recordContent" :key="index">
              <!-- 对方 -->
              <div class="word" v-if="item.come === to.nickname">
                <img :src="imagesUrl(to.avatarUrl)">
                <div class="info">
                  <p class="time">{{to.nickname}}  {{$moment(item.time).calendar()}}</p>
                  <div class="info-content">{{item.content}}</div>
                </div>
              </div>
              <!-- 我的 -->
              <div class="word-my" v-else>
                <div class="info">
                  <p class="time">{{user.nickname}}  {{$moment(item.time).calendar()}}</p>
                  <div class="info-content">{{item.content}}</div>
                </div>
                <img :src="imagesUrl(user.avatarUrl)">
              </div>
            </div>
            <div class="text-bottom"></div>
          </div>

          <div class="chat-input">
            <el-row :gutter="10">
              <el-col :span="21">
                <el-input
                    size="medium"
                    placeholder="请输入内容"
                    v-model="newMessage">
                </el-input>
              </el-col>
              <el-col :span="1">
                <el-button size="medium"
                           @click="sendMessage"
                           type="primary"
                           icon="el-icon-s-promotion">发送</el-button>
              </el-col>
            </el-row>
          </div>

        </div>
        <el-empty v-else description=""></el-empty>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { sendSock, createWebSocket, closeSock} from "@/utils/webSocket"
export default {
  name: "Chat",
  data() {
    return {
      to: {},
      user:{},
      recordContent: [],
      newMessage: '',
      selectId: 0,
      chatList:[]
    }
  },
  created() {
    this.init();
  },
  updated(){//数据更新时调用
    //滚动到最后
    this.$refs.chatText.scrollTop = this.$refs.chatText.scrollHeight
  },
  methods: {
    init(){
      this.user = JSON.parse(localStorage.getItem("user"));

      if(this.user.role === 'ROLE_DOCTOR'){
        this.user.nickname = this.user.doctorName
        this.http.get("/user-service/doctor/getDoctor/"+this.user.doctorName).then(res =>{
          this.user.avatarUrl = res.data.avatarUrl
        })
      }

      createWebSocket(this.callback, this.user.nickname,this.user.role)

      if(this.$route.params.id){
        this.selectId = this.$route.params.id
        this.to.nickname = this.$route.params.name
        this.to.avatarUrl = this.$route.params.avatarUrl
      }else {
        this.selectId = 0
        this.to.nickname = ""
        this.to.avatarUrl = ""
      }
      this.load()
      if(this.selectId !== 0){
        this.loadMsg();
      }
    },
    load(){
      let name = this.user.nickname;
      this.http.get("/user-service/chat-people/"+name).then(res => {
        this.chatList = res.data
      })
    },
    loadMsg(){
      this.http.get(`/chat-service/chat-message/history/${this.user.nickname}/${this.to.nickname}`).then(res => {
        this.recordContent = res.data
        this.recordContent.reverse()
      })
    },
    callback(data) {
      if(data.come === this.to.nickname) {
        this.recordContent.push(data)
        //滚动到最后
        this.$refs.chatText.scrollTop = this.$refs.chatText.scrollHeight
        //修改状态
      }else {
        this.load();
      }
    },
    switchChat(item){
      this.selectId = item.id;
      this.to.nickname = item.otherParty;
      this.to.avatarUrl = item.avatarUrl;
      item.unreadMsg = 0;
      this.loadMsg();
    },
    sendMessage() {
      if (this.newMessage) {
        let message = {
          come: this.user.nickname,
          reach: this.to.nickname,
          content: this.newMessage,
          time: new Date(),
          type:'text'
        }
        this.recordContent.push(message)
        this.$refs.chatText.scrollTop = this.$refs.chatText.scrollHeight
        sendSock(JSON.stringify(message))
        this.newMessage = ''
      }
    },
  },
  beforeDestroy(){
    closeSock()
  }
}
</script>

<style lang="scss">
.chat-content{
  position: relative;
  width: 100%;
  height: 100%;
  padding: 0;
  background-color: #F2F6FC;
  .chat-text{
    height: 100%;
    padding: 20px 0 0 0;
    overflow: auto;
    &::-webkit-scrollbar {
      display: none;
    }
    .word{
      display: flex;
      margin-bottom: 20px;
      margin-left: 20px;
      img{
        width: 40px;
        height: 40px;
        border-radius: 50%;
      }
      .info{
        margin-left: 10px;
        .time{
          font-size: 12px;
          color: rgba(51,51,51,0.8);
          height: 20px;
          line-height: 20px;
          margin: -5px 0 0;
        }
        .info-content{
          padding: 10px;
          font-size: 14px;
          background: #ffffff;
          border-radius: 8px;
          position: relative;
          margin-top: 8px;
        }
        //小三角形
        .info-content::before{
          position: absolute;
          left: -8px;
          top: 8px;
          content: '';
          border-right: 10px solid #ffffff;
          border-top: 8px solid transparent;
          border-bottom: 8px solid transparent;
        }
      }
    }
    .word-my{
      display: flex;
      justify-content:flex-end;
      margin-bottom: 20px;
      margin-right: 20px;
      img{
        width: 40px;
        height: 40px;
        border-radius: 50%;
      }
      .info{
        width: 90%;
        margin-left: 10px;
        text-align: right;
        .time{
          font-size: 12px;
          color: rgba(51,51,51,0.8);
          height: 20px;
          line-height: 20px;
          margin: -5px 10px 0 0;
        }
        .info-content{
          max-width: 70%;
          padding: 10px;
          font-size: 14px;
          float: right;
          margin-right: 10px;
          position: relative;
          margin-top: 8px;
          background: #79bbff;
          border-radius: 8px;
          text-align: left;
        }
        //小三角形
        .info-content::after{
          position: absolute;
          right: -8px;
          top: 8px;
          content: '';
          border-left: 10px solid #79bbff;
          border-top: 8px solid transparent;
          border-bottom: 8px solid transparent;
        }
      }
    }

    .text-bottom{
      width: 100%;
      //background-color: #010103;
      height: 40%;
    }
  }

  .chat-input{
    position: absolute;
    bottom: 0;
    right: 0;
    width: 100%;
  }
}
.aside-left{
  padding:0;
  overflow-x: hidden;
  overflow-y: auto;
  &::-webkit-scrollbar {
    display: none;
  }
  background-color: #409EFF;
  .seize_a_seat{
    background-color:#409EFF;
    height: 40px
  }
  .incoming_letter{
    padding: 10px;
    .info_msg{
      font-size:16px;
      font-weight:600;
      color: #ffffff
    }
  }
  .incoming_letter:hover{
    background-color: #79bbff;
  }
  .incoming_letter_active{
    background-color: #79bbff;
    padding: 10px;
    .info_msg{
      font-size:16px;
      font-weight:600;
      color: #ffffff
    }
  }
}
</style>