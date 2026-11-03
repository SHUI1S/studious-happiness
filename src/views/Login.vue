<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="title">AI学习助手与错题分析</h2>
      <el-tabs v-model="activeTab" type="card">
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" label-width="60px">
            <el-form-item label="用户名">
              <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" @keyup.enter.native="handleLogin"></el-input>
            </el-form-item>
            <el-button type="primary" style="width: 100%;" @click="handleLogin">登 录</el-button>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <el-form :model="registerForm" label-width="60px">
            <el-form-item label="用户名">
              <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="registerForm.nickname" placeholder="请输入昵称"></el-input>
            </el-form-item>
            <el-button type="success" style="width: 100%;" @click="handleRegister">注 册</el-button>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <div class="tip">测试账号：student / 123456</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      activeTab: 'login',
      loginForm: { username: 'student', password: '123456' },
      registerForm: { username: '', password: '', nickname: '' }
    }
  },
  methods: {
    handleLogin() {
      this.$http.post('/api/user/login', this.loginForm).then(res => {
        const data = res.data
        if (data.code === 200) {
          localStorage.setItem('user', JSON.stringify(data.data.user))
          this.$message.success('登录成功')
          this.$router.push('/')
        } else {
          this.$message.error(data.message)
        }
      })
    },
    handleRegister() {
      this.$http.post('/api/user/register', this.registerForm).then(res => {
        const data = res.data
        if (data.code === 200) {
          this.$message.success('注册成功，请登录')
          this.activeTab = 'login'
        } else {
          this.$message.error(data.message)
        }
      })
    }
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}
.login-box {
  width: 400px;
  background: #fff;
  border-radius: 10px;
  padding: 30px;
  box-shadow: 0 10px 40px rgba(0,0,0,0.2);
}
.title {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
  font-size: 20px;
}
.tip {
  margin-top: 15px;
  text-align: center;
  color: #999;
  font-size: 12px;
}
</style>
