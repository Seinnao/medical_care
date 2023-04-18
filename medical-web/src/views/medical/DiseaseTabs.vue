<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="病名管理" name="disease" lazy>
      <transition name="el-fade-in-linear">
        <Disease ref="disease"/>
      </transition>
    </el-tab-pane>
    <el-tab-pane label="病症管理" name="symptom" lazy>
      <transition name="el-fade-in-linear">
        <Symptom ref="symptom"/>
      </transition>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
import Disease from "@/views/medical/disease/Disease";
import Symptom from "@/views/medical/disease/Symptom";
export default {
  name:"DiseaseTabs",
  components: {Symptom, Disease},
  data() {
    return {
      activeName: 'disease'
    };
  },
  methods: {
    handleClick(tab, event) {
      this.$nextTick(()=>{
        this.$refs[tab.name].init()
      })
    }
  }
};
</script>