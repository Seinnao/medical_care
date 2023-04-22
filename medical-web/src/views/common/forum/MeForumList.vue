<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" clearable placeholder="请输入标题" suffix-icon="el-icon-search" v-model="title"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
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
      <el-table-column prop="title" align="center" label="主题" width="140" show-overflow-tooltip></el-table-column>
      <el-table-column prop="outline" align="center" label="概要" show-overflow-tooltip></el-table-column>
      <el-table-column prop="nickname" align="center" label="发帖昵称" width="100"></el-table-column>
      <el-table-column align="center" label="时间" width="160">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ $moment(scope.row.time).calendar() }}</span>
        </template>
      </el-table-column>
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
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
export default {
  name: "MeForumList",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      title: "",
      nickname: "",
      form: {},
      multipleSelection: [],
      roles: [],
      loading: false,
    }
  },
  created() {
    let user = JSON.parse(localStorage.getItem("user"));
    this.nickname = user.nickname;
    this.load()
  },
  methods: {
    load() {
      this.loading = true;
      this.http.get("/care-service/forum/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          title: this.title,
          nickname: this.nickname
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
        this.loading = false;
      })
    },
    del(id) {
      this.http.delete("/care-service/forum/" + id).then(res => {
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
      let ids = this.multipleSelection.map(v => v.id)
      this.http.post("/care-service/forum/del/batch", ids).then(res => {
        if (res.code === 200) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset() {
      this.username = ""
      this.email = ""
      this.address = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    see(data){
      this.$router.push({
        name:'post',
        params: {id:data.id}
      })
    },
    handleEdit(data){
      //console.log(data,"2")
      this.$router.push({
        name:'编辑帖子',
        params: {id:data.id}
      })
    }
  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}
</style>
