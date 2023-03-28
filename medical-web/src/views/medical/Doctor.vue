<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入查询名字" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 15px 0">
      <el-badge :value="applyNumber" style="margin-right:10px" :hidden="applyNumber === 0">
        <el-button type="primary" @click="openDoctor()">医生申请</el-button>
      </el-badge>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch">
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>
    <el-table :data="tableData" border stripe
              v-loading="loading"
              :header-cell-class-name="'headerBg'"
              @selection-change="handleSelectionChange">

      <el-table-column type="selection" align="center" width="55"></el-table-column>
      <el-table-column prop="name" align="center" label="名字" width="100"></el-table-column>
      <el-table-column prop="isOnline" align="center" label="在线状态" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isOnline" type="success" effect="plain">在线</el-tag>
          <el-tag v-else type="info" effect="plain">离线</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="头像" width="100">
        <template slot-scope="scope">
          <el-avatar size="small" :src="imagesUrl(scope.row.avatarUrl)"></el-avatar>
        </template>
      </el-table-column>
<!--      education-->
      <el-table-column prop="education" align="center" width="100" label="学历" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="introduce" align="center" label="自我描述" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" label="评分" width="200">
        <template slot-scope="scope">
          <el-rate v-model="scope.row.score" disabled show-score text-color="#ff9900" score-template="{value}">
          </el-rate>
        </template>
      </el-table-column>
      <!--      <el-table-column prop="address" align="center" label="其他"></el-table-column>-->
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

    <el-dialog title="医生信息" :visible.sync="dialogFormVisible" width="40%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="名字">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="自我描述">
          <el-input v-model="form.introduce" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="意向医生信息" :visible.sync="dialogFromDoctor" width="60%" >
      <el-table :data="tableDataTwo" border stripe
                v-loading="loadingTwo"
                :header-cell-class-name="'headerBg'">
        <el-table-column prop="name" align="center" label="名字" width="100"></el-table-column>
        <el-table-column align="center" label="头像" width="100">
          <template slot-scope="scope">
            <el-avatar size="small" :src="imagesUrl(scope.row.avatarUrl)"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column prop="education" align="center" width="100" label="学历" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="introduce" align="center" label="自我描述" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作"  width="200" align="center">
          <template slot-scope="scope">
            <el-button type="success" @click="agreeEdit(scope.row.id)">通过 <i class="el-icon-edit"></i></el-button>
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确定'
                cancel-button-text='我再想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定不通过该申请吗？"
                @confirm="fail(scope.row.id)"
            >
              <el-button type="danger" slot="reference" >不通过 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div style="padding: 10px 0">
        <el-pagination
            @size-change="handleSizeChangeTwo"
            @current-change="handleCurrentChangeTwo"
            :current-page="pageNumTwo"
            :page-sizes="[2, 5, 10, 15]"
            :page-size="pageSizeTwo"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalTwo">
        </el-pagination>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFromDoctor = false;load()">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Doctor",
  data() {
    return {
      tableData: [],
      tableDataTwo:[],
      total: 0,
      totalTwo: 0,
      pageNum: 1,
      pageNumTwo: 1,
      pageSize: 10,
      pageSizeTwo: 5,
      name: "",
      form: {},
      dialogFormVisible: false,
      dialogFromDoctor: false,
      multipleSelection: [],
      roles: [],
      loading: false,
      loadingTwo: false,
      applyNumber: 0
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.loading = true;
      this.http.get("/user-service/doctor/getDoctors", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
        this.loading = false;
      })

      this.http.get("/user-service/doctor/applyNumber").then(res => {
        this.applyNumber = res.data
      })
    },
    save() {
      this.http.post("/user-service/doctor", this.form).then(res => {
        if (res.code === 200) {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
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
      this.http.delete("/user-service/doctor/" + id).then(res => {
        if (res.code === 200) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    fail(id){
      this.http.delete("/user-service/doctor/fail/" + id).then(res => {
        if (res.code === 200) {
          this.$message.success("操作成功")
          this.loadTwo()
        } else {
          this.$message.error("操作失败")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.http.post("/user-service/doctor/del/batch", ids).then(res => {
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
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    handleSizeChangeTwo(pageSize) {
      this.pageSizeTwo = pageSize
      this.loadTwo()
    },
    handleCurrentChangeTwo(pageNum) {
      this.pageNumTwo = pageNum
      this.loadTwo()
    },
    agreeEdit(id){
      this.http.post("/user-service/doctor/agree", {id:id}).then(res => {
        if (res.code === 200) {
          this.$message.success("操作成功")
          this.loadTwo()
        } else {
          this.$message.error("操作失败")
        }
      })
    },
    loadTwo(){
      this.loadingTwo = true;
      this.http.get("/user-service/doctor/page", {
        params: {
          pageNum: this.pageNumTwo,
          pageSize: this.pageSizeTwo,
        }
      }).then(res => {
        this.tableDataTwo = res.data.records
        this.totalTwo = res.data.total
        this.loadingTwo = false;
      })
    },
    openDoctor(){
      this.dialogFromDoctor = true
      this.loadTwo()
    }
  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}
</style>
