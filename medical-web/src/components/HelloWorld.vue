<template>
  <div class="hello">
    <p>测试</p>
    <el-button @click="doInt">点击</el-button>
  </div>
</template>

<script>
import http from "@/utils/http";
export default {
  name: 'HelloWorld',
  methods:{
    doInt(){
      http.get("user-service/test",{
        params:{id:23}
      }).then(data =>{
        console.log(data)
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
