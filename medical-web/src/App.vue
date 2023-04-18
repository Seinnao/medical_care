<template>
  <div id="app">
    <router-view/>
    <div class="bottom">
      <span>©2023 whz </span>
      <a href="https://beian.miit.gov.cn">湘ICP备2023007034号</a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
}
</script>

<style lang="scss" scoped>
.bottom{
  font-size: 14px;
  opacity: 0.6;
  color: #8f8f8f;
  position: fixed;
  bottom: 20px;
  left:50%;
  transform: translateX(-50%);
  a{
    color: #8f8f8f;
  }
}
</style>
