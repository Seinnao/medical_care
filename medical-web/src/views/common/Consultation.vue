<template>
  <div>
    <el-row :gutter="10">
     <el-col :span="6" v-for="item in doctors" :key="item.id" style="margin-top: 10px">
       <el-card>
         <div class="clearfix" slot="header">
           <el-row :gutter="10" type="flex" align="middle">
            <el-col :span="6">
              <el-avatar :size="50" :src="imagesUrl(item.avatarUrl)"></el-avatar>
            </el-col>
             <el-col :span="12">
               <h1>{{ item.name }}</h1>
             </el-col>
             <el-col :span="6" style="top: 0">
               <el-button :disabled="!item.isOnline" style="float: right; padding: 3px 0" type="text">向它咨询</el-button>
             </el-col>
           </el-row>
         </div>
         <el-row :gutter="10" style="margin-bottom: 10px">
           <el-col :span="8" v-if="item.isOnline"><el-tag type="success" effect="dark">在线</el-tag></el-col>
           <el-col :span="8" v-else><el-tag type="info" effect="dark">离线</el-tag></el-col>
           <el-col :span="16"><el-rate v-model="value" disabled show-score text-color="#ff9900" score-template="{value}"></el-rate></el-col>
         </el-row>
         <div style="height: 30px;
         display: -webkit-box;
         -webkit-line-clamp: 2;
         -webkit-box-orient: vertical;
          overflow: hidden;">
           <p style="font-size: 12px;color: #909399;"> {{item.describe}} </p>
         </div>
       </el-card>
     </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Consultation",
  data() {
    return {
      value: 3.8,
      doctors: [{
        id: '9888888888845',
        name: "李小华",
        score: 3.7,
        isOnline: true,
        avatarUrl: "group1/M00/00/00/wKhYg2QcM2KAbkwWAAH6x2Bh11k242.jpg",
        nickname: "",
        describe: "该医生是一位。。。。。。。"
      }, {
        id: '9888888888843',
        name: "李雯",
        score: 3.9,
        isOnline: false,
        avatarUrl: "group1/M00/00/00/wKhYg2QcM2KAbkwWAAH6x2Bh11k242.jpg",
        nickname: "",
        describe: "该医生是一位。。。。。。。"
      },]
    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>