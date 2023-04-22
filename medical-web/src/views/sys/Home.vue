<template>
  <div class="content">
    <el-row :gutter="10" style="margin-bottom: 30px">
     <el-col :span="18"><el-input v-model="query" placeholder="您有什么问题可以问我!" size="small"></el-input></el-col>
      <el-col :span="6"><el-button type="primary" icon="el-icon-search" size="small" @click="search">搜索</el-button></el-col>
    </el-row>
    <el-empty description="请搜索" v-if="sear"></el-empty>
    <div v-for="(item,index) in dataList" :key="index">
      <div v-if="item.type === 'drugs'" class="drugs">
        <el-card shadow="hover">
          <el-image :src="imagesUrl(item.data.imagesUrl)" class="images" lazy fit="contain"/>
          <span class="title">{{item.data.name}}</span>
          <el-tag v-if="item.data.label === '非处方药'" type="success" effect="dark" class="label">{{ item.data.label }}</el-tag>
          <el-tag v-else type="danger" effect="dark" class="label">{{ item.data.label }}</el-tag>
          <div class="introduce">介绍：{{item.data.introduce }}</div>
          <div class="symptoms">可以治疗
            <el-tag type="warning" class="tag" v-if="item.data.symptoms.length <= 0">
              无
            </el-tag>
            <el-tag type="warning" v-for="(symptom,index) in item.data.symptoms" :key="index" class="tag">
              {{symptom.name}}
            </el-tag>
          </div>
        </el-card>
      </div>
      <div v-if="item.type === 'symptom'" class="symptom">
        <el-card shadow="hover">
          <span class="syName">{{item.data.name}}</span>


        </el-card>
      </div>
      <div v-if="item.type === 'disease'" class="disease">
        {{item.data.name}}
      </div>
      <div v-if="item.type === 'forum'" class="forum">
        {{item.data.title}}
      </div>
    </div>
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
      query:'999感冒灵颗和头疼',
      dataList:[],
      sear:true
    }
  },
  methods:{
    init(){
    },
    search() {
      this.http.post('care-service/ai/search',{
        data:this.query
      }).then(res =>{
        if(res.code === 200){
          this.dataList = res.data;
          this.sear = this.dataList.length <= 0;
        }
      })
    },
  },
}
</script>

<style scoped lang="scss">
.content{
  .drugs{
    position: relative;
    width: 70%;
    .images{
      border-style: solid;
      border-width: thin;
      border-color: #e3e8ec;
      border-radius: 12px;
      width:200px;
      height: 130px;
    }
    .title{
      position: absolute;
      font-size: 23px;
      left: 240px;
      top: 22px;
    }
    .label{
      position: absolute;
      left: 240px;
      top: 60px;
    }
    .introduce{
      position: absolute;
      font-size: 12px;
      left: 240px;
      top: 90px;
    }
    .symptoms{
      position: absolute;
      font-size: 12px;
      left: 240px;
      top: 120px;
      .tag{
        margin-left: 5px;
      }
    }
  }
  .symptom{
    position: relative;
    width: 70%;
    height: 150px;
    .el-card{
      height: 100%;
    }
    .syName{
      position: absolute;
      font-size: 23px;
      left: 20px;
      top: 22px;
    }
  }
  .disease{
    position: relative;
    width: 70%;
  }
  .forum{
    position: relative;
    width: 70%;
  }
}

</style>