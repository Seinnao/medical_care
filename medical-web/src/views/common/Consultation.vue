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
               <el-button style="float: right; padding: 3px 0" type="text" @click="toInquiry(item)">向它咨询</el-button>
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
           <p style="font-size: 12px;color: #909399;"> {{item.introduce}} </p>
         </div>
       </el-card>
     </el-col>
    </el-row>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[8, 16, 24, 40]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "Consultation",
  data() {
    return {
      value: 3.8,
      total: 0,
      pageNum: 1,
      pageSize: 16,
      loading: false,
      doctors: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.loading = true;
      this.http.get("/user-service/doctor/getDoctors", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.doctors = res.data.records
        this.total = res.data.total
        this.loading = false;
      })
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    toInquiry(data){
      this.$router.push({
        name:"chat",
        params:{
          id:data.id,
          name:data.name,
          avatarUrl:data.avatarUrl
        }
      })
    }
  }
}
</script>

<style scoped>

</style>