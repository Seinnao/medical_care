<template>
  <div>
    <mavon-editor :codeStyle="codeStyle"
                  :toolbars="toolbars"
                  v-model="content"
                  @imgAdd="imgAdd"
                  :style="mak_class"
                  :ishljs="true"
                  ref="md" @change="change"/>
  </div>
</template>

<script>
import {compress} from "@/utils";
export default {
  name: "Markdown",
  props: {   //父节点传的值
    mak_class: String,
    content:{
      type:String,
      default:'这里是markdown编辑的内容',
    },
  },
  data() {
    return {
      page_article:undefined,
      html_content:undefined,
      md_content:undefined,
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        navigation: true, // 导航目录
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        subfield: true, // 单双栏模式
        preview: true // 预览
      },
      codeStyle:'monokai-sublime',//主题
      test_html:undefined
    };
  },
  methods:{
    imgAdd(pos,file){
      compress(file,{width:300}).then(rst =>{
        const form = new FormData()
        form.append('file', rst);
        this.http({
          url: 'file-service/file/upload/avatar',
          method:'post',
          data: form,
          headers: { 'Content-Type': 'multipart/form-data' }
        }).then((res) => {
          if (res.code === 200) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
            })
            this.$refs.md.$img2Url(pos,this.imagesUrl(res.url))
          } else {
            this.$message.error(res.msg)
          }
        })
      })
    },
    change(value, render){
      console.log(this.mak_class)
      this.test_html = render;
      this.$emit('change',this.test_html,this.content)
    }
  }
}
</script>

<style scoped>

</style>