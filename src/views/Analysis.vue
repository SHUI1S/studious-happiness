<template>
  <div>
    <!-- 概览卡片 -->
    <el-row :gutter="15" style="margin-bottom: 15px;">
      <el-col :span="6">
        <el-card shadow="hover">
          <div style="text-align: center;">
            <div style="font-size: 32px; color: #409EFF; font-weight: bold;">{{ overview.total }}</div>
            <div style="color: #999; margin-top: 5px;">错题总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div style="text-align: center;">
            <div style="font-size: 32px; color: #67C23A; font-weight: bold;">{{ overview.mastered }}</div>
            <div style="color: #999; margin-top: 5px;">已掌握</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div style="text-align: center;">
            <div style="font-size: 32px; color: #F56C6C; font-weight: bold;">{{ overview.notMastered }}</div>
            <div style="color: #999; margin-top: 5px;">未掌握</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div style="text-align: center;">
            <div style="font-size: 32px; color: #E6A23C; font-weight: bold;">{{ overview.masterRate }}%</div>
            <div style="color: #999; margin-top: 5px;">掌握率</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="15" style="margin-bottom: 15px;">
      <el-col :span="12">
        <el-card shadow="never">
          <div slot="header">按学科错题分布</div>
          <div ref="subjectChart" style="height: 300px;"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="never">
          <div slot="header">按题型错题分布</div>
          <div ref="typeChart" style="height: 300px;"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="15" style="margin-bottom: 15px;">
      <el-col :span="12">
        <el-card shadow="never">
          <div slot="header">薄弱知识点 TOP5</div>
          <el-table :data="topKnowledge" border size="small">
            <el-table-column type="index" label="排名" width="60"></el-table-column>
            <el-table-column prop="name" label="知识点"></el-table-column>
            <el-table-column prop="count" label="错题数" width="80"></el-table-column>
            <el-table-column prop="percentage" label="占比" width="100">
              <template slot-scope="scope">
                <el-progress :percentage="scope.row.percentage"></el-progress>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="never">
          <div slot="header">📋 学习建议</div>
          <el-timeline>
            <el-timeline-item v-for="(s, index) in suggestions" :key="index" :timestamp="'建议' + (index + 1)" placement="top">
              {{ s }}
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'Analysis',
  data() {
    return {
      userId: JSON.parse(localStorage.getItem('user') || '{}').id || 2,
      overview: { total: 0, mastered: 0, notMastered: 0, masterRate: 0 },
      bySubject: [],
      byType: [],
      topKnowledge: [],
      suggestions: []
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    loadData() {
      // 加载概览
      this.$http.get('/api/analysis/overview', { params: { userId: this.userId } }).then(res => {
        if (res.data.code === 200) {
          this.overview = res.data.data
        }
      })

      // 加载学科统计
      this.$http.get('/api/analysis/bySubject', { params: { userId: this.userId } }).then(res => {
        if (res.data.code === 200) {
          this.bySubject = res.data.data
          this.$nextTick(() => this.renderSubjectChart())
        }
      })

      // 加载题型统计
      this.$http.get('/api/analysis/byType', { params: { userId: this.userId } }).then(res => {
        if (res.data.code === 200) {
          this.byType = res.data.data
          this.$nextTick(() => this.renderTypeChart())
        }
      })

      // 加载完整报告（含学习建议）
      this.$http.get('/api/analysis/report', { params: { userId: this.userId } }).then(res => {
        if (res.data.code === 200) {
          this.topKnowledge = (res.data.data.byKnowledge || []).slice(0, 5)
          this.suggestions = res.data.data.suggestions || []
        }
      })
    },
    renderSubjectChart() {
      const chart = echarts.init(this.$refs.subjectChart)
      chart.setOption({
        tooltip: { trigger: 'item' },
        legend: { orient: 'vertical', left: 'left' },
        series: [{
          type: 'pie',
          radius: '60%',
          center: ['60%', '50%'],
          data: this.bySubject.map(item => ({ name: item.name, value: item.count })),
          emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' } }
        }]
      })
    },
    renderTypeChart() {
      const chart = echarts.init(this.$refs.typeChart)
      chart.setOption({
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'category', data: this.byType.map(i => i.name) },
        yAxis: { type: 'value' },
        series: [{
          type: 'bar',
          data: this.byType.map(i => i.count),
          itemStyle: { color: '#67C23A' },
          barWidth: '40%'
        }]
      })
    }
  }
}
</script>
