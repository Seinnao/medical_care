<template>
  <div>
    <el-result title="" subTitle="抱歉，请求错误">
      <template slot="icon">
        <img src="@/assets/404/404.png"/>
      </template>
      <template slot="extra">
        <el-button type="primary" size="medium" @click="$router.back()">返回</el-button>
      </template>
    </el-result>
  </div>
</template>

<script>
export default {
  name: "404",
  methods:{

  }
}
</script>

<style scoped>

</style>