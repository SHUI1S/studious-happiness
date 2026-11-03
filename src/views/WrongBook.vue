<template>
  <div>
    <!-- 搜索栏 -->
    <el-card shadow="never" style="margin-bottom: 15px;">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="学科">
          <el-select v-model="searchForm.subject" placeholder="全部" clearable>
            <el-option v-for="s in subjects" :key="s" :label="s" :value="s"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="掌握状态">
          <el-select v-model="searchForm.mastered" placeholder="全部" clearable>
            <el-option label="未掌握" :value="0"></el-option>
            <el-option label="已掌握" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="loadData">查询</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 错题列表 -->
    <el-card shadow="never">
      <el-table :data="list" border stripe>
        <el-table-column prop="id" label="ID" width="60"></el-table-column>
        <el-table-column label="学科" width="100">
          <template slot-scope="scope">{{ scope.row.question.subject }}</template>
        </el-table-column>
        <el-table-column label="题型" width="80">
          <template slot-scope="scope">{{ scope.row.question.type }}</template>
        </el-table-column>
        <el-table-column label="题目内容" show-overflow-tooltip>
          <template slot-scope="scope">{{ scope.row.question.content }}</template>
        </el-table-column>
        <el-table-column label="你的答案" width="100">
          <template slot-scope="scope">
            <span style="color: #F56C6C;">{{ scope.row.userAnswer }}</span>
          </template>
        </el-table-column>
        <el-table-column label="正确答案" width="100">
          <template slot-scope="scope">
            <span style="color: #67C23A;">{{ scope.row.question.answer }}</span>
          </template>
        </el-table-column>
        <el-table-column label="掌握状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.mastered === 1 ? 'success' : 'danger'">
              {{ scope.row.mastered === 1 ? '已掌握' : '未掌握' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button size="mini" @click="viewDetail(scope.row)">详情</el-button>
            <el-button size="mini" type="warning" @click="toggleMastered(scope.row)" v-if="scope.row.mastered === 0">标记掌握</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">移除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 错题详情弹窗 -->
    <el-dialog title="错题详情" :visible.sync="detailVisible" width="650px">
      <div v-if="currentWrong">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="学科">{{ currentWrong.question.subject }}</el-descriptions-item>
          <el-descriptions-item label="题型">{{ currentWrong.question.type }}</el-descriptions-item>
          <el-descriptions-item label="题目">{{ currentWrong.question.content }}</el-descriptions-item>
          <el-descriptions-item label="选项A" v-if="currentWrong.question.optionA">{{ currentWrong.question.optionA }}</el-descriptions-item>
          <el-descriptions-item label="选项B" v-if="currentWrong.question.optionB">{{ currentWrong.question.optionB }}</el-descriptions-item>
          <el-descriptions-item label="选项C" v-if="currentWrong.question.optionC">{{ currentWrong.question.optionC }}</el-descriptions-item>
          <el-descriptions-item label="选项D" v-if="currentWrong.question.optionD">{{ currentWrong.question.optionD }}</el-descriptions-item>
          <el-descriptions-item label="你的答案">
            <span style="color: #F56C6C;">{{ currentWrong.userAnswer }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="正确答案">
            <span style="color: #67C23A;">{{ currentWrong.question.answer }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="答案解析">{{ currentWrong.question.analysis }}</el-descriptions-item>
          <el-descriptions-item label="错误原因">{{ currentWrong.wrongReason }}</el-descriptions-item>
          <el-descriptions-item label="知识点">{{ currentWrong.question.knowledgePoint }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'WrongBook',
  data() {
    return {
      userId: JSON.parse(localStorage.getItem('user') || '{}').id || 2,
      searchForm: { subject: '', mastered: '' },
      subjects: [],
      list: [],
      detailVisible: false,
      currentWrong: null
    }
  },
  created() {
    this.loadSubjects()
    this.loadData()
  },
  methods: {
    loadSubjects() {
      this.$http.get('/api/question/subjects').then(res => {
        if (res.data.code === 200) {
          this.subjects = res.data.data
        }
      })
    },
    loadData() {
      const params = { userId: this.userId }
      if (this.searchForm.subject) params.subject = this.searchForm.subject
      if (this.searchForm.mastered !== '') params.mastered = this.searchForm.mastered
      this.$http.get('/api/wrong/list', { params }).then(res => {
        if (res.data.code === 200) {
          this.list = res.data.data
        }
      })
    },
    viewDetail(row) {
      this.currentWrong = row
      this.detailVisible = true
    },
    toggleMastered(row) {
      this.$http.put('/api/wrong/' + row.id, { mastered: 1 }).then(res => {
        if (res.data.code === 200) {
          this.$message.success('已标记为掌握')
          this.loadData()
        }
      })
    },
    handleDelete(row) {
      this.$confirm('确定从错题本移除此题？', '提示', { type: 'warning' }).then(() => {
        this.$http.delete('/api/wrong/' + row.id).then(res => {
          if (res.data.code === 200) {
            this.$message.success('移除成功')
            this.loadData()
          }
        })
      })
    }
  }
}
</script>
