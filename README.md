# AI学习助手与错题分析系统

> JavaEE 期末大作业 — DM. 39C

## 一、项目介绍

本项目是一个基于 **SSM + Vue** 前后端分离架构的 AI 学习助手与错题分析系统，主要面向学生用户，提供题库管理、错题记录、错题统计分析和个性化学习报告等功能，帮助学生高效复习、查漏补缺。

### 核心功能模块

| 模块 | 功能说明 |
|------|----------|
| 用户登录/注册 | 用户身份认证，区分学生/管理员角色 |
| 题库管理 | 题目录入、分类查询、学科筛选、关键词搜索、详情查看、删除 |
| 错题本 | 错题记录、按学科/掌握状态筛选、标记掌握、移除错题 |
| 错题分析 | 错题概览统计、学科分布饼图、题型分布柱状图、薄弱知识点 TOP5 |
| 学习报告 | 自动生成个性化学习建议，指出薄弱学科和知识点 |

## 二、技术栈

### 后端
- **框架**: Spring + SpringMVC + MyBatis (SSM)
- **JDK**: 1.8
- **构建工具**: Maven
- **数据库**: MySQL 8.0
- **连接池**: Druid
- **JSON处理**: Jackson
- **日志**: Logback

### 前端
- **框架**: Vue 2.x
- **UI组件库**: Element UI 2.x
- **图表库**: ECharts 5.x
- **HTTP请求**: Axios
- **路由**: Vue Router 3.x

## 三、目录结构

```
dm-project/
├── sql/                        # 数据库脚本
│   └── init.sql                # 建库建表 + 测试数据
├── backend/                    # SSM后端项目
│   ├── src/main/
│   │   ├── java/com/dm/
│   │   │   ├── entity/         # 实体类
│   │   │   │   ├── User.java
│   │   │   │   ├── Question.java
│   │   │   │   └── WrongQuestion.java
│   │   │   ├── mapper/         # MyBatis Mapper接口
│   │   │   │   ├── UserMapper.java
│   │   │   │   ├── QuestionMapper.java
│   │   │   │   └── WrongQuestionMapper.java
│   │   │   ├── service/        # Service层
│   │   │   │   ├── UserService.java
│   │   │   │   ├── QuestionService.java
│   │   │   │   ├── WrongQuestionService.java
│   │   │   │   ├── AnalysisService.java
│   │   │   │   └── impl/       # Service实现类
│   │   │   ├── controller/     # Controller层
│   │   │   │   ├── UserController.java
│   │   │   │   ├── QuestionController.java
│   │   │   │   ├── WrongQuestionController.java
│   │   │   │   └── AnalysisController.java
│   │   │   └── dto/            # 数据传输对象
│   │   │       ├── Result.java
│   │   │       └── WrongStatDTO.java
│   │   ├── resources/
│   │   │   ├── spring/         # Spring配置文件
│   │   │   │   ├── applicationContext.xml
│   │   │   │   └── spring-mvc.xml
│   │   │   ├── mybatis/        # MyBatis映射文件
│   │   │   │   ├── UserMapper.xml
│   │   │   │   ├── QuestionMapper.xml
│   │   │   │   └── WrongQuestionMapper.xml
│   │   │   └── jdbc.properties # 数据库配置
│   │   └── webapp/WEB-INF/
│   │       └── web.xml         # Web应用配置
│   └── pom.xml                 # Maven依赖配置
├── frontend/                   # Vue前端项目
│   ├── src/
│   │   ├── views/              # 页面组件
│   │   │   ├── Login.vue       # 登录注册页
│   │   │   ├── Layout.vue      # 主布局
│   │   │   ├── QuestionBank.vue # 题库管理
│   │   │   ├── WrongBook.vue   # 错题本
│   │   │   └── Analysis.vue    # 错题分析
│   │   ├── router/             # 路由配置
│   │   ├── App.vue
│   │   └── main.js
│   ├── public/
│   │   └── index.html
│   ├── package.json
│   └── vue.config.js
└── README.md                   # 项目说明文档
```

## 四、环境要求

| 软件 | 版本要求 |
|------|----------|
| JDK | 1.8+ |
| Maven | 3.6+ |
| MySQL | 5.7+ / 8.0 |
| Node.js | 14+ |
| Tomcat | 8.5+ / 9.0 |

## 五、部署步骤

### 1. 数据库初始化

```sql
-- 登录MySQL后执行
source E:/dm-project/sql/init.sql;
```

或者手动复制 `init.sql` 文件内容到MySQL中执行。

### 2. 后端部署

1. 修改数据库配置：编辑 `backend/src/main/resources/jdbc.properties`，改成你自己的MySQL账号密码
2. 进入backend目录，打包：
```bash
cd E:\dm-project\backend
mvn clean package
```
3. 将生成的 `target/dm-study.war` 部署到 Tomcat 的 webapps 目录下
4. 启动 Tomcat，后端默认运行在 `http://localhost:8081/dm-study/api/`

> **注意**：如果Tomcat端口不是8081，请同时修改前端 `vue.config.js` 中的 `target` 地址。

### 3. 前端部署

```bash
cd E:\dm-project\frontend
npm install
npm run serve
```

启动后访问 `http://localhost:8080` 即可。

## 六、测试账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| student | 123456 | 学生 |
| admin | 123456 | 管理员 |

## 七、API 接口说明

### 用户模块
| 接口 | 方法 | 说明 |
|------|------|------|
| /api/user/login | POST | 登录 |
| /api/user/register | POST | 注册 |
| /api/user/info/{id} | GET | 获取用户信息 |

### 题目模块
| 接口 | 方法 | 说明 |
|------|------|------|
| /api/question/list | GET | 查询题目列表 |
| /api/question/{id} | GET | 查询题目详情 |
| /api/question | POST | 新增题目 |
| /api/question/{id} | PUT | 更新题目 |
| /api/question/{id} | DELETE | 删除题目 |
| /api/question/subjects | GET | 获取所有学科 |

### 错题模块
| 接口 | 方法 | 说明 |
|------|------|------|
| /api/wrong/list | GET | 查询错题列表 |
| /api/wrong/{id} | GET | 查询错题详情 |
| /api/wrong | POST | 添加错题 |
| /api/wrong/{id} | PUT | 更新错题 |
| /api/wrong/{id} | DELETE | 删除错题 |

### 分析模块
| 接口 | 方法 | 说明 |
|------|------|------|
| /api/analysis/overview | GET | 错题概览统计 |
| /api/analysis/bySubject | GET | 按学科统计 |
| /api/analysis/byKnowledge | GET | 按知识点统计 |
| /api/analysis/byType | GET | 按题型统计 |
| /api/analysis/report | GET | 生成学习报告 |

## 八、系统功能演示

### 1. 登录页
- 支持登录/注册切换
- 渐变紫色背景，卡片式设计

### 2. 题库管理
- 左侧菜单栏切换
- 支持按学科、题型、关键词搜索
- 弹窗式题目详情查看
- 弹窗式新增题目表单

### 3. 错题本
- 按学科和掌握状态筛选
- 红色标注错误答案，绿色标注正确答案
- 一键标记已掌握
- 错题详情弹窗展示完整解析

### 4. 错题分析
- 顶部四个数据卡片：总数、已掌握、未掌握、掌握率
- ECharts 饼图：学科错题分布
- ECharts 柱状图：题型错题分布
- 薄弱知识点 TOP5 排行榜
- 自动生成个性化学习建议
