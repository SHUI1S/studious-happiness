<template>
  <el-container style="height: 100vh;">
    <el-aside width="200px" style="background-color: #304156;">
      <div class="logo">📚 学习助手</div>
      <el-menu :default-active="activeMenu" router background-color="#304156" text-color="#fff" active-text-color="#409EFF">
        <el-menu-item index="/question">
          <i class="el-icon-document"></i>
          <span slot="title">题库管理</span>
        </el-menu-item>
        <el-menu-item index="/wrong">
          <i class="el-icon-edit-outline"></i>
          <span slot="title">错题本</span>
        </el-menu-item>
        <el-menu-item index="/analysis">
          <i class="el-icon-data-analysis"></i>
          <span slot="title">错题分析</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="background: #fff; border-bottom: 1px solid #eee; display: flex; justify-content: space-between; align-items: center;">
        <span style="font-size: 16px; font-weight: bold;">{{ currentPageName }}</span>
        <el-dropdown @command="handleCommand">
          <span style="cursor: pointer;">
            {{ userInfo.nickname }} <i class="el-icon-arrow-down"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-main style="background: #f5f5f5;">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'Layout',
  data() {
    return {
      userInfo: JSON.parse(localStorage.getItem('user') || '{}')
    }
  },
  computed: {
    activeMenu() {
      return this.$route.path
    },
    currentPageName() {
      return this.$route.name || '首页'
    }
  },
  methods: {
    handleCommand(cmd) {
      if (cmd === 'logout') {
        localStorage.removeItem('user')
        this.$router.push('/login')
      }
    }
  }
}
</script>

<style scoped>
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  background: #2b3a4d;
}
.el-menu {
  border-right: none;
}
</style>
