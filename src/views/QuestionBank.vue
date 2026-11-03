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
        <el-form-item label="题型">
          <el-select v-model="searchForm.type" placeholder="全部" clearable>
            <el-option label="单选" value="单选"></el-option>
            <el-option label="多选" value="多选"></el-option>
            <el-option label="判断" value="判断"></el-option>
            <el-option label="简答" value="简答"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="searchForm.keyword" placeholder="搜索题目内容"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="loadData">查询</el-button>
          <el-button type="success" icon="el-icon-plus" @click="openAddDialog">添加题目</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 题目列表 -->
    <el-card shadow="never">
      <el-table :data="list" border stripe>
        <el-table-column prop="id" label="ID" width="60"></el-table-column>
        <el-table-column prop="subject" label="学科" width="100"></el-table-column>
        <el-table-column prop="type" label="题型" width="80"></el-table-column>
        <el-table-column prop="content" label="题目内容" show-overflow-tooltip></el-table-column>
        <el-table-column prop="knowledgePoint" label="知识点" width="120" show-overflow-tooltip></el-table-column>
        <el-table-column prop="difficulty" label="难度" width="80">
          <template slot-scope="scope">
            <el-tag :type="scope.row.difficulty === 1 ? 'success' : scope.row.difficulty === 2 ? 'warning' : 'danger'">
              {{ scope.row.difficulty === 1 ? '简单' : scope.row.difficulty === 2 ? '中等' : '困难' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button size="mini" @click="viewDetail(scope.row)">详情</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 题目详情弹窗 -->
    <el-dialog title="题目详情" :visible.sync="detailVisible" width="600px">
      <div v-if="currentQuestion">
        <p><strong>学科：</strong>{{ currentQuestion.subject }}</p>
        <p><strong>题型：</strong>{{ currentQuestion.type }}</p>
        <p><strong>题目：</strong>{{ currentQuestion.content }}</p>
        <p v-if="currentQuestion.optionA"><strong>A.</strong> {{ currentQuestion.optionA }}</p>
        <p v-if="currentQuestion.optionB"><strong>B.</strong> {{ currentQuestion.optionB }}</p>
        <p v-if="currentQuestion.optionC"><strong>C.</strong> {{ currentQuestion.optionC }}</p>
        <p v-if="currentQuestion.optionD"><strong>D.</strong> {{ currentQuestion.optionD }}</p>
        <p style="color: #67C23A;"><strong>答案：</strong>{{ currentQuestion.answer }}</p>
        <p><strong>解析：</strong>{{ currentQuestion.analysis }}</p>
        <p><strong>知识点：</strong>{{ currentQuestion.knowledgePoint }}</p>
      </div>
    </el-dialog>

    <!-- 添加题目弹窗 -->
    <el-dialog title="添加题目" :visible.sync="addVisible" width="600px">
      <el-form :model="addForm" label-width="80px">
        <el-form-item label="学科">
          <el-input v-model="addForm.subject" placeholder="如：Java、数学、英语"></el-input>
        </el-form-item>
        <el-form-item label="题型">
          <el-select v-model="addForm.type" placeholder="请选择题型">
            <el-option label="单选" value="单选"></el-option>
            <el-option label="多选" value="多选"></el-option>
            <el-option label="判断" value="判断"></el-option>
            <el-option label="简答" value="简答"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="题目内容">
          <el-input type="textarea" v-model="addForm.content" :rows="3"></el-input>
        </el-form-item>
        <el-form-item label="选项A" v-if="addForm.type === '单选' || addForm.type === '多选'">
          <el-input v-model="addForm.optionA"></el-input>
        </el-form-item>
        <el-form-item label="选项B" v-if="addForm.type === '单选' || addForm.type === '多选'">
          <el-input v-model="addForm.optionB"></el-input>
        </el-form-item>
        <el-form-item label="选项C" v-if="addForm.type === '单选' || addForm.type === '多选'">
          <el-input v-model="addForm.optionC"></el-input>
        </el-form-item>
        <el-form-item label="选项D" v-if="addForm.type === '单选' || addForm.type === '多选'">
          <el-input v-model="addForm.optionD"></el-input>
        </el-form-item>
        <el-form-item label="正确答案">
          <el-input v-model="addForm.answer"></el-input>
        </el-form-item>
        <el-form-item label="答案解析">
          <el-input type="textarea" v-model="addForm.analysis"></el-input>
        </el-form-item>
        <el-form-item label="知识点">
          <el-input v-model="addForm.knowledgePoint"></el-input>
        </el-form-item>
        <el-form-item label="难度">
          <el-select v-model="addForm.difficulty">
            <el-option label="简单" :value="1"></el-option>
            <el-option label="中等" :value="2"></el-option>
            <el-option label="困难" :value="3"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAdd">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'QuestionBank',
  data() {
    return {
      searchForm: { subject: '', type: '', keyword: '' },
      subjects: [],
      list: [],
      detailVisible: false,
      currentQuestion: null,
      addVisible: false,
      addForm: {
        subject: '', type: '单选', content: '', optionA: '', optionB: '',
        optionC: '', optionD: '', answer: '', analysis: '', knowledgePoint: '', difficulty: 2
      }
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
      this.$http.get('/api/question/list', { params: this.searchForm }).then(res => {
        if (res.data.code === 200) {
          this.list = res.data.data
        }
      })
    },
    viewDetail(row) {
      this.currentQuestion = row
      this.detailVisible = true
    },
    openAddDialog() {
      this.addVisible = true
    },
    handleAdd() {
      this.$http.post('/api/question', this.addForm).then(res => {
        if (res.data.code === 200) {
          this.$message.success('添加成功')
          this.addVisible = false
          this.loadData()
          this.loadSubjects()
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    handleDelete(row) {
      this.$confirm('确定删除该题目？', '提示', { type: 'warning' }).then(() => {
        this.$http.delete('/api/question/' + row.id).then(res => {
          if (res.data.code === 200) {
            this.$message.success('删除成功')
            this.loadData()
          }
        })
      })
    }
  }
}
</script>
