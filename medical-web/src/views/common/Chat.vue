<template>
  <div class="chat-content">
    <div class="chat-text" ref="chatText">
      <!-- recordContent 聊天记录数组-->
      <div v-for="(item,indexc) in recordContent" :key="indexc">
        <!-- 对方 -->
        <div class="word" v-if="!item.fromMe">
          <img src="../../assets/login/login-element.png">
          <div class="info">
            <p class="time">{{item.nickName}}  {{item.time}}</p>
            <div class="info-content">{{item.text}}</div>
          </div>
        </div>
        <!-- 我的 -->
        <div class="word-my" v-else>
          <div class="info">
            <p class="time">{{item.nickName}}  {{item.time}}</p>
            <div class="info-content">{{item.text}}</div>
          </div>
          <img src="../../assets/logo.png">
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
</template>

<script>
export default {
  name: "Chat",
  data() {
    return {
      friendName: 'John',
      recordContent: [],
      newMessage: ''
    }
  },
  methods: {
    sendMessage() {
      if (this.newMessage) {
        this.recordContent.push({
          text: this.newMessage,
          fromMe: true,
          time: new Date().toLocaleTimeString(),
          nickName:'小明'
        })

        this.recordContent.push({
          text: this.newMessage,
          fromMe: false,
          nickName:'小化',
          time: new Date().toLocaleTimeString()
        })

        //滚动到最后
        this.$refs.chatText.scrollTop = this.$refs.chatText.scrollHeight

        this.newMessage = ''
      }
    }
  }
}
</script>

<style lang="scss">
.chat-content{
  position: relative;
  width: 100%;
  height: 100%;
  padding: 20px;
  background-color: #F2F6FC;
  .chat-text{
    height: 100%;
    overflow: auto;
    &::-webkit-scrollbar {
      display: none;
    }
    .word{
      display: flex;
      margin-bottom: 20px;
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

</style>