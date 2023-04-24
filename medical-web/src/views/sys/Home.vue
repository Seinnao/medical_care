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
          <el-image :src="imagesUrl(item.data.imagesUrl)" class="images images_border" lazy fit="contain"/>
          <span class="title title_all">{{item.data.name}}</span>
          <el-tag v-if="item.data.label === '非处方药'" type="success" effect="dark" class="label">{{ item.data.label }}</el-tag>
          <el-tag v-else type="danger" effect="dark" class="label">{{ item.data.label }}</el-tag>
          <div class="introduce text">介绍：{{item.data.introduce }}</div>
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
          <span class="title_all syName">{{item.data.name}}</span>
          <div class="text sy_introduce">介绍：{{item.data.introduce }}</div>
          <el-tag effect="dark" class="isUse">可用药品</el-tag>
          <el-row :gutter="10" class="sy_drugs">
            <el-col :span="8" v-for="(drugs,index) in item.data.drugs" :key="index" class="sy_drug">
              <el-image :src="imagesUrl(drugs.imagesUrl)" class="drugs_images images_border" lazy fit="contain"/>
              <el-tag type="success" class="sy_drugs_name">{{drugs.name}}</el-tag>
            </el-col>
          </el-row>
        </el-card>
      </div>
      <div v-if="item.type === 'disease'" class="disease">
        <el-card shadow="hover">
          <div class="title_all" style="left: 20px">{{item.data.name}}</div>
          <div class="text dis_introduce">介绍：{{item.data.introduce }}</div>
          <div class="isUse text">症状:
            <el-tag type="warning" class="tag" v-if="item.data.symptom.length <= 0">
              无
            </el-tag>
            <el-tag type="warning" v-for="(sym,index) in item.data.symptom" :key="index" class="tag">
              {{sym.name}}
            </el-tag>
          </div>
          <el-tag effect="dark" class="isGin">参考药品</el-tag>
          <el-row :gutter="2" class="dis_drugs">
            <el-col :span="4" v-for="(drugs,index) in item.data.drugs" :key="index" class="sy_drug">
              <el-image :src="imagesUrl(drugs.imagesUrl)" class="drugs_images images_border" lazy fit="contain"/>
              <el-tag type="success" class="sy_drugs_name">{{drugs.name}}</el-tag>
            </el-col>
          </el-row>
        </el-card>
      </div>
      <div v-if="item.type === 'forum'" class="forum">
        <el-card class="box-card">
          <el-link class="title_all" @click="openArticle(item.data)">{{item.data.title}}</el-link>
          <div class="text outline">简介：{{item.data.outline}}</div>
          <el-avatar size="large" :src="imagesUrl(item.data.avatarUrl)" class="avatar"></el-avatar>
          <div class="time">{{ $moment(item.data.time).calendar()}}</div>
          <div class="icon">
            <i class="el-icon-view on" style="margin-right:15px"> {{item.data.see}}</i>
            <i class="el-icon-star-off on" style="margin-right:15px"> {{ item.data.collection}}</i>
            <i class="el-icon-chat-dot-square on"> {{ item.data.commentSum}}</i>
          </div>
        </el-card>
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
      query:'感冒999感冒灵颗和头疼',
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
    openArticle(item){
      const { href } = this.$router.resolve({
        path: `/post`,
        query: {
          id: item.id
        }
      });
      window.open(href, '_blank');
    },
  },
}
</script>

<style scoped lang="scss">
.content{
  .text{
    font-size: 12px;
    color: #606266;
  }
  .images_border{
    border-style: solid;
    border-width: thin;
    border-color: #e3e8ec;
    border-radius: 15%;
  }
  .title_all{
    position: absolute;
    font-size: 23px;
    left: 20px;
    top: 22px;
  }
  .drugs{
    position: relative;
    width: 70%;
    .images{
      width:200px;
      height: 130px;
    }
    .title{
      left: 240px;
    }
    .label{
      position: absolute;
      left: 240px;
      top: 60px;
    }
    .introduce{
      position: absolute;
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
    height: 196px;
    .el-card{
      height: 100%;
    }
    .syName{
      left: 20px;
    }
    .sy_introduce{
      position: absolute;
      top: 60px;
    }
    .isUse{
      position: absolute;
      top: 84px;
    }
    .sy_drugs{
      position: absolute;
      top: 110px;
      .sy_drug{
        display: inline;
        text-align: center;
        .drugs_images{
          width: 80px;
          height: 48px;
        }
        .sy_drugs_name{
        }
      }
    }
  }
  .disease{
    position: relative;
    width: 70%;
    height: 220px;
    .el-card{
      height: 100%;
    }
    .dis_introduce{
      position: absolute;
      top: 60px;
    }
    .isUse{
      position: absolute;
      top: 84px;
      .tag{
        margin-left: 5px;
      }
    }
    .isGin{
      position: absolute;
      top: 110px;
    }
    .dis_drugs{
      position: absolute;
      top: 136px;
      .sy_drug{
        display: inline;
        text-align: center;
        .drugs_images{
          width: 80px;
          height: 48px;
        }
      }
    }
  }
  .forum{
    position: relative;
    width: 70%;
    height: 110px;
    .el-card{
      height: 100%;
    }
    .outline{
      position: absolute;
      top: 60px;
    }
    .avatar{
      position: absolute;
      width: 40px;
      height: 40px;
      top: 20px;
      right: 20px;
    }
    .time{
      position: absolute;
      color: #06a7bd;
      font-size: 12px;
      top: 70px;
      right: 20px;
    }
    .icon{
      position: absolute;
      font-size: 12px;
      top: 86px;
      .on {
        color: #06a7bd;
        //margin-left:6px
      }
    }
  }
}

</style>