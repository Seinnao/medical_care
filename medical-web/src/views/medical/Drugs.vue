<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入药品名称" suffix-icon="el-icon-search" v-model="name" clearable></el-input>
      <el-select style="width: 200px" v-model="label" placeholder="请选择药品标签" class="ml-5" clearable>
        <el-option label="处方药" value="处方药"></el-option>
        <el-option label="非处方药" value="非处方药"></el-option>
      </el-select>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData" border stripe
              v-loading="loading"
              :header-cell-class-name="'headerBg'"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center" width="55"></el-table-column>

      <el-table-column prop="name" align="center" label="药品名" width="140"></el-table-column>
      <el-table-column align="center" label="图片">
        <template v-slot="scope">
          <el-image :src="imagesUrl(scope.row.imagesUrl)" fit="cover" style="width:80px;height: 80px">
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="label" align="center" label="标签" width="120">
        <template v-slot="{row}">
          <el-tag :type="row.label==='处方药'? '':'success'" effect="plain">{{ row.label }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="introduce" align="center" label="描述"></el-table-column>
      <el-table-column label="操作"  width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 8, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="药品信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="药品名字">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="form.label" autocomplete="off">
            <el-option label="处方药" value="处方药"></el-option>
            <el-option label="非处方药" value="非处方药"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图片">
          <el-upload
              class="drugs-uploader"
              action=""
              :show-file-list="false"
              :http-request="filesRequest"
              :on-success="handleImagesSuccess"
          >
            <i v-if="!form.imagesUrl" class="el-icon-plus images-uploader-icon"></i>
            <img v-else :src="imagesUrl(form.imagesUrl)" class="images">
          </el-upload>
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="form.introduce" autocomplete="off" :rows="2"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {compress} from "@/utils";

export default {
  name: "Drugs",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      label: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      roles: [],
      loading: false
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.loading = true;
      this.form.imagesUrl = ''
      this.http.get("/care-service/drugs/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          label: this.label,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
        this.loading = false;
      })
    },
    save() {
      this.http.post("/care-service/drugs", this.form).then(res => {
        if (res.code === 200) {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    del(id) {
      this.http.delete("/care-service/drugs/" + id).then(res => {
        if (res.code === 200) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.http.post("/care-service/drugs/del/batch", ids).then(res => {
        if (res.code === 200) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset() {
      this.name = ""
      this.label = ""
      this.load()
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
    filesRequest(data){
      compress(data.file,{width:500}).then(rst =>{
        const form = new FormData()
        form.append('file', rst);
        this.http({
          url: 'file-service/file/upload/drugs',
          method:'post',
          data: form,
          headers: { 'Content-Type': 'multipart/form-data' }
        }).then((res) => {
          if (res.code === 200) {
            console.log(res)
            this.form.imagesUrl = res.url
            this.$forceUpdate() //强制渲染组件
          } else {
            this.$message.error(res.msg)
          }
        })
      })
    },
    handleImagesSuccess(res){
      //console.log("哈哈哈哈")
      this.form.imagesUrl = res
    }
  }
}
</script>


<style lang="scss">
.headerBg {
  background: #eee!important;
}
.drugs-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  width: 100px;
  height: 100px;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.drugs-uploader .el-upload:hover {
  border-color: #409EFF;
}

</style>

<style scoped>
.drugs-uploader {
  padding-bottom: 10px;
}
.images-uploader-icon {
  font-size: 20px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.images {
  width: 100px;
  height: 100px;
  object-fit: cover;
  display: block;
}
</style>