<template>
  <el-menu style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(48, 65, 86)"
           text-color="#fff"
           unique-opened
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           :default-active="$route.path.replace('/','')"
           router>
<!--     unique-opened
:default-openeds="opens"
-->
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="@/assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px;">
      <b style="color: white; margin-left: 1px" v-show="logoTextShow">医疗服务平台</b>
    </div>
    <div v-for="item in menus" :key="item.id" class="aside">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{ item.name }}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id + ''">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
          <div v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path">
              <i :class="subItem.icon"></i>
              <span slot="title">{{ subItem.name }}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  data() {
    return {
      menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
      //opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : []
    }
  },
  methods:{
    getOpen(){

    }
  }
}
</script>

<style lang="scss" scoped>
.el-menu-item.is-active {
  background-color: rgb(38, 52, 69) !important;
}

.el-menu-item:hover {
  background-color: rgb(38, 52, 69) !important;
}

.el-submenu__title:hover {
  background-color: rgb(38, 52, 69) !important;
}

/*解决收缩菜单文字不消失问题*/
.el-menu--collapse span {
  visibility: hidden;
}

//目前不起作用
.aside{
  overflow: auto;
  &::-webkit-scrollbar {
    display: none;
  }
}

</style>