<template>
  <el-container style="min-height: 100vh">

    <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%);">
      <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" style="padding-bottom: 20px" />
    </el-aside>

    <el-container>

      <el-header style="border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" @asideCollapse="collapse" :user="user" />
      </el-header>

      <el-main style="background-color: rgb(252,252,252);">
        <!--        表示当前页面的子路由会在 <router-view /> 里面展示-->
        <router-view @refreshUser="getUser"/>
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/sys/Aside";
import Header from "@/components/sys/Header";
export default {
  name: "Manage",
  components: {
    Aside,
    Header
  },
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      user: {}
    }
  },
  created() {
    // 从后台获取最新的User数据
    this.getUser()
  },
  methods: {
    collapse() {  // 点击收缩按钮触发
      console.log("5555555555555")
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {  // 收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {   // 展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    getUser() {
      console.log("refreshUser==")
      this.user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
      if (this.username) {
        // 从后台获取User数据
        this.http.get("user-service/user/username/" + this.username).then(res => {
          // 重新赋值后台的最新User数据
          this.user = res.data
        })
      }
    },
  },
}
</script>

<style>
.el-main {
  height: calc(100vh - 60px); /*设置主体 main 高度 calc计算*/
}
</style>