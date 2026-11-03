// AI学习助手 - Mock后端服务器
// 用于演示，真实项目请用SSM后端

const http = require('http');
const url = require('url');

// 模拟数据
let users = [
  { id: 1, username: 'admin', password: '123456', nickname: '管理员', role: 2 },
  { id: 2, username: 'student', password: '123456', nickname: '小明', role: 1 }
];

let questions = [
  { id: 1, subject: 'Java', type: '单选', content: '下列哪个不是Java的基本数据类型？', optionA: 'int', optionB: 'String', optionC: 'double', optionD: 'char', answer: 'B', analysis: 'String是引用数据类型，不是基本数据类型。', knowledgePoint: '基本数据类型', difficulty: 2 },
  { id: 2, subject: 'Java', type: '单选', content: 'Java中，以下哪个关键字用于继承？', optionA: 'implements', optionB: 'extends', optionC: 'override', optionD: 'super', answer: 'B', analysis: 'Java中类使用extends关键字实现继承。', knowledgePoint: '类继承', difficulty: 1 },
  { id: 3, subject: 'Java', type: '单选', content: 'String s = new String("abc"); 创建了几个对象？', optionA: '1个', optionB: '2个', optionC: '3个', optionD: '4个', answer: 'B', analysis: '如果常量池没有"abc"，则创建2个对象。', knowledgePoint: 'String对象', difficulty: 3 },
  { id: 4, subject: '计算机网络', type: '单选', content: 'HTTP协议默认端口号是？', optionA: '21', optionB: '23', optionC: '80', optionD: '443', answer: 'C', analysis: 'HTTP默认端口80，HTTPS默认443。', knowledgePoint: '网络端口', difficulty: 1 },
  { id: 5, subject: '计算机网络', type: '单选', content: 'OSI参考模型共分为几层？', optionA: '5层', optionB: '6层', optionC: '7层', optionD: '8层', answer: 'C', analysis: 'OSI七层模型。', knowledgePoint: 'OSI模型', difficulty: 2 },
  { id: 6, subject: '数据结构', type: '单选', content: '栈的特点是？', optionA: '先进先出', optionB: '后进先出', optionC: '只能插入不能删除', optionD: '随机存取', answer: 'B', analysis: '栈是后进先出(LIFO)。', knowledgePoint: '栈结构', difficulty: 1 },
  { id: 7, subject: '数据结构', type: '单选', content: '二分查找的时间复杂度是？', optionA: 'O(n)', optionB: 'O(n²)', optionC: 'O(log n)', optionD: 'O(1)', answer: 'C', analysis: '二分查找时间复杂度O(log n)。', knowledgePoint: '查找算法', difficulty: 2 },
  { id: 8, subject: '数学', type: '单选', content: '函数f(x)=x²的导数是？', optionA: 'x', optionB: '2x', optionC: 'x²', optionD: '2', answer: 'B', analysis: 'x^n的导数为n*x^(n-1)。', knowledgePoint: '导数', difficulty: 2 },
  { id: 9, subject: '英语', type: '单选', content: '"She ___ to school every day." 填入正确的动词形式', optionA: 'go', optionB: 'goes', optionC: 'going', optionD: 'went', answer: 'B', analysis: '第三人称单数一般现在时加es。', knowledgePoint: '一般现在时', difficulty: 1 },
  { id: 10, subject: 'Java', type: '多选', content: '下列哪些是Java的集合类？', optionA: 'ArrayList', optionB: 'HashMap', optionC: 'String', optionD: 'HashSet', answer: 'ABD', analysis: 'String不是集合类。', knowledgePoint: '集合框架', difficulty: 2 }
];

let wrongQuestions = [
  { id: 1, userId: 2, questionId: 1, userAnswer: 'D', wrongReason: '混淆了基本数据类型和引用数据类型', mastered: 0, wrongTime: '2026-11-01 10:00:00' },
  { id: 2, userId: 2, questionId: 3, userAnswer: 'A', wrongReason: '不了解String对象创建机制', mastered: 0, wrongTime: '2026-11-01 14:30:00' },
  { id: 3, userId: 2, questionId: 5, userAnswer: 'B', wrongReason: '记错了OSI模型层数', mastered: 1, wrongTime: '2026-10-30 09:15:00' },
  { id: 4, userId: 2, questionId: 7, userAnswer: 'A', wrongReason: '时间复杂度计算不熟练', mastered: 0, wrongTime: '2026-10-29 16:45:00' },
  { id: 5, userId: 2, questionId: 10, userAnswer: 'ABC', wrongReason: '漏选了HashSet', mastered: 1, wrongTime: '2026-10-28 11:20:00' }
];

let nextWrongId = 6;

// 工具函数
function sendRes(res, code, message, data) {
  const resData = { code, message, data };
  res.writeHead(200, { 'Content-Type': 'application/json;charset=utf-8', 'Access-Control-Allow-Origin': '*' });
  res.end(JSON.stringify(resData));
}

function getBody(req) {
  return new Promise((resolve) => {
    let body = '';
    req.on('data', chunk => body += chunk);
    req.on('end', () => {
      resolve(body ? JSON.parse(body) : {});
    });
  });
}

// 创建服务器
const server = http.createServer(async (req, res) => {
  const parsedUrl = url.parse(req.url, true);
  const pathname = parsedUrl.pathname;
  const query = parsedUrl.query;

  // 处理OPTIONS预检请求
  if (req.method === 'OPTIONS') {
    res.writeHead(200, {
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
      'Access-Control-Allow-Headers': 'Content-Type'
    });
    res.end();
    return;
  }

  console.log(`${req.method} ${pathname}`);

  try {
    // ========== 用户模块 ==========
    if (pathname === '/api/user/login' && req.method === 'POST') {
      const body = await getBody(req);
      const user = users.find(u => u.username === body.username && u.password === body.password);
      if (user) {
        const { password, ...userInfo } = user;
        sendRes(res, 200, '登录成功', { user: userInfo });
      } else {
        sendRes(res, 500, '用户名或密码错误', null);
      }
      return;
    }

    if (pathname === '/api/user/register' && req.method === 'POST') {
      const body = await getBody(req);
      sendRes(res, 200, '注册成功', { id: 99, username: body.username, nickname: body.nickname });
      return;
    }

    // ========== 题目模块 ==========
    if (pathname === '/api/question/list' && req.method === 'GET') {
      let list = [...questions];
      if (query.subject) list = list.filter(q => q.subject === query.subject);
      if (query.type) list = list.filter(q => q.type === query.type);
      if (query.keyword) list = list.filter(q => q.content.includes(query.keyword));
      sendRes(res, 200, '成功', list);
      return;
    }

    if (pathname === '/api/question/subjects' && req.method === 'GET') {
      const subjects = [...new Set(questions.map(q => q.subject))];
      sendRes(res, 200, '成功', subjects);
      return;
    }

    if (pathname.match(/^\/api\/question\/\d+$/) && req.method === 'GET') {
      const id = parseInt(pathname.split('/').pop());
      const question = questions.find(q => q.id === id);
      sendRes(res, 200, '成功', question);
      return;
    }

    if (pathname === '/api/question' && req.method === 'POST') {
      const body = await getBody(req);
      body.id = questions.length + 1;
      questions.push(body);
      sendRes(res, 200, '添加成功', null);
      return;
    }

    if (pathname.match(/^\/api\/question\/\d+$/) && req.method === 'DELETE') {
      const id = parseInt(pathname.split('/').pop());
      questions = questions.filter(q => q.id !== id);
      sendRes(res, 200, '删除成功', null);
      return;
    }

    // ========== 错题模块 ==========
    if (pathname === '/api/wrong/list' && req.method === 'GET') {
      let list = wrongQuestions.filter(w => w.userId == query.userId);
      if (query.subject) {
        list = list.filter(w => {
          const q = questions.find(q => q.id === w.questionId);
          return q && q.subject === query.subject;
        });
      }
      if (query.mastered !== undefined && query.mastered !== '') {
        list = list.filter(w => w.mastered == query.mastered);
      }
      // 关联题目信息
      const result = list.map(w => ({
        ...w,
        question: questions.find(q => q.id === w.questionId)
      }));
      sendRes(res, 200, '成功', result);
      return;
    }

    if (pathname.match(/^\/api\/wrong\/\d+$/) && req.method === 'PUT') {
      const id = parseInt(pathname.split('/').pop());
      const body = await getBody(req);
      const idx = wrongQuestions.findIndex(w => w.id === id);
      if (idx >= 0) {
        wrongQuestions[idx] = { ...wrongQuestions[idx], ...body };
      }
      sendRes(res, 200, '更新成功', null);
      return;
    }

    if (pathname.match(/^\/api\/wrong\/\d+$/) && req.method === 'DELETE') {
      const id = parseInt(pathname.split('/').pop());
      wrongQuestions = wrongQuestions.filter(w => w.id !== id);
      sendRes(res, 200, '删除成功', null);
      return;
    }

    // ========== 分析模块 ==========
    if (pathname === '/api/analysis/overview' && req.method === 'GET') {
      const userId = parseInt(query.userId);
      const list = wrongQuestions.filter(w => w.userId === userId);
      const total = list.length;
      const mastered = list.filter(w => w.mastered === 1).length;
      const rate = total === 0 ? 0 : Math.round(mastered / total * 1000) / 10;
      sendRes(res, 200, '成功', { total, mastered, notMastered: total - mastered, masterRate: rate });
      return;
    }

    if (pathname === '/api/analysis/bySubject' && req.method === 'GET') {
      const userId = parseInt(query.userId);
      const list = wrongQuestions.filter(w => w.userId === userId);
      const map = {};
      list.forEach(w => {
        const q = questions.find(q => q.id === w.questionId);
        if (q) {
          map[q.subject] = (map[q.subject] || 0) + 1;
        }
      });
      const result = Object.keys(map).map(name => ({
        name,
        count: map[name],
        percentage: Math.round(map[name] / list.length * 1000) / 10
      })).sort((a, b) => b.count - a.count);
      sendRes(res, 200, '成功', result);
      return;
    }

    if (pathname === '/api/analysis/byKnowledge' && req.method === 'GET') {
      const userId = parseInt(query.userId);
      const list = wrongQuestions.filter(w => w.userId === userId);
      const map = {};
      list.forEach(w => {
        const q = questions.find(q => q.id === w.questionId);
        if (q && q.knowledgePoint) {
          map[q.knowledgePoint] = (map[q.knowledgePoint] || 0) + 1;
        }
      });
      const result = Object.keys(map).map(name => ({
        name,
        count: map[name],
        percentage: Math.round(map[name] / list.length * 1000) / 10
      })).sort((a, b) => b.count - a.count);
      sendRes(res, 200, '成功', result);
      return;
    }

    if (pathname === '/api/analysis/byType' && req.method === 'GET') {
      const userId = parseInt(query.userId);
      const list = wrongQuestions.filter(w => w.userId === userId);
      const map = {};
      list.forEach(w => {
        const q = questions.find(q => q.id === w.questionId);
        if (q) {
          map[q.type] = (map[q.type] || 0) + 1;
        }
      });
      const result = Object.keys(map).map(name => ({
        name,
        count: map[name],
        percentage: Math.round(map[name] / list.length * 1000) / 10
      })).sort((a, b) => b.count - a.count);
      sendRes(res, 200, '成功', result);
      return;
    }

    if (pathname === '/api/analysis/report' && req.method === 'GET') {
      const userId = parseInt(query.userId);
      const list = wrongQuestions.filter(w => w.userId === userId);
      const total = list.length;
      const mastered = list.filter(w => w.mastered === 1).length;
      const rate = total === 0 ? 0 : Math.round(mastered / total * 1000) / 10;

      // 按学科统计
      const subjectMap = {};
      list.forEach(w => {
        const q = questions.find(q => q.id === w.questionId);
        if (q) subjectMap[q.subject] = (subjectMap[q.subject] || 0) + 1;
      });
      const bySubject = Object.keys(subjectMap).map(name => ({
        name, count: subjectMap[name],
        percentage: Math.round(subjectMap[name] / total * 1000) / 10
      })).sort((a, b) => b.count - a.count);

      // 按知识点统计
      const knowledgeMap = {};
      list.forEach(w => {
        const q = questions.find(q => q.id === w.questionId);
        if (q && q.knowledgePoint) knowledgeMap[q.knowledgePoint] = (knowledgeMap[q.knowledgePoint] || 0) + 1;
      });
      const byKnowledge = Object.keys(knowledgeMap).map(name => ({
        name, count: knowledgeMap[name],
        percentage: Math.round(knowledgeMap[name] / total * 1000) / 10
      })).sort((a, b) => b.count - a.count);

      // 学习建议
      const suggestions = [];
      if (rate < 30) {
        suggestions.push('错题掌握率较低，建议优先复习近期做错的题目，重新梳理知识点。');
      } else if (rate < 60) {
        suggestions.push('错题掌握率中等，建议针对高频错题进行专项练习，巩固薄弱环节。');
      } else {
        suggestions.push('错题掌握率较好，建议继续保持，定期回顾已掌握的题目防止遗忘。');
      }
      if (bySubject.length > 0) {
        suggestions.push(`【${bySubject[0].name}】是当前错题最多的学科，建议增加该学科的练习量。`);
      }
      if (byKnowledge.length > 0) {
        suggestions.push(`【${byKnowledge[0].name}】知识点错误率最高，建议重点复习该知识点相关内容。`);
      }

      sendRes(res, 200, '成功', {
        overview: { total, mastered, notMastered: total - mastered, masterRate: rate },
        bySubject,
        byKnowledge,
        suggestions
      });
      return;
    }

    // 404
    sendRes(res, 404, '接口不存在', null);

  } catch (err) {
    console.error(err);
    sendRes(res, 500, '服务器内部错误: ' + err.message, null);
  }
});

const PORT = 8081;
server.listen(PORT, () => {
  console.log(`Mock后端服务器已启动: http://localhost:${PORT}/`);
});
