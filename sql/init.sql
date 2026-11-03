-- =============================================
-- AI学习助手与错题分析系统 数据库初始化脚本
-- 数据库: dm_study
-- =============================================

CREATE DATABASE IF NOT EXISTS dm_study DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE dm_study;

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) COMMENT '昵称',
  `role` TINYINT DEFAULT 1 COMMENT '角色: 1-学生 2-管理员',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- 题库表
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '题目ID',
  `subject` VARCHAR(50) NOT NULL COMMENT '学科: 数学/英语/Java/计算机网络等',
  `type` VARCHAR(20) NOT NULL COMMENT '题型: 单选/多选/判断/简答',
  `content` TEXT NOT NULL COMMENT '题目内容',
  `option_a` VARCHAR(500) COMMENT '选项A',
  `option_b` VARCHAR(500) COMMENT '选项B',
  `option_c` VARCHAR(500) COMMENT '选项C',
  `option_d` VARCHAR(500) COMMENT '选项D',
  `answer` VARCHAR(200) NOT NULL COMMENT '正确答案',
  `analysis` TEXT COMMENT '答案解析',
  `knowledge_point` VARCHAR(100) COMMENT '知识点',
  `difficulty` TINYINT DEFAULT 3 COMMENT '难度: 1-简单 2-中等 3-困难',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题库表';

-- ----------------------------
-- 错题表
-- ----------------------------
DROP TABLE IF EXISTS `wrong_question`;
CREATE TABLE `wrong_question` (
  `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '错题ID',
  `user_id` INT NOT NULL COMMENT '用户ID',
  `question_id` INT NOT NULL COMMENT '题目ID',
  `user_answer` VARCHAR(200) COMMENT '用户答案',
  `wrong_reason` VARCHAR(500) COMMENT '错误原因',
  `mastered` TINYINT DEFAULT 0 COMMENT '是否已掌握: 0-未掌握 1-已掌握',
  `wrong_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '做错时间',
  FOREIGN KEY (user_id) REFERENCES `user`(id),
  FOREIGN KEY (question_id) REFERENCES question(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='错题表';

-- =============================================
-- 测试数据
-- =============================================

-- 用户数据
INSERT INTO `user` (username, password, nickname, role) VALUES
('admin', '123456', '管理员', 2),
('student', '123456', '小明', 1);

-- 题库数据
INSERT INTO `question` (subject, type, content, option_a, option_b, option_c, option_d, answer, analysis, knowledge_point, difficulty) VALUES
('Java', '单选', '下列哪个不是Java的基本数据类型？', 'int', 'String', 'double', 'char', 'B', 'String是引用数据类型，不是基本数据类型。Java的基本数据类型有8种：byte、short、int、long、float、double、char、boolean。', '基本数据类型', 2),
('Java', '单选', 'Java中，以下哪个关键字用于继承？', 'implements', 'extends', 'override', 'super', 'B', 'Java中类使用extends关键字实现继承，接口使用implements关键字实现。', '类继承', 1),
('Java', '单选', 'String s = new String("abc"); 创建了几个对象？', '1个', '2个', '3个', '4个', 'B', '如果字符串常量池中没有"abc"，则创建2个对象（常量池一个，堆中一个）；如果已有则创建1个。通常面试题答案为2个。', 'String对象', 3),
('计算机网络', '单选', 'HTTP协议默认端口号是？', '21', '23', '80', '443', 'C', 'HTTP默认端口80，HTTPS默认端口443，FTP默认21，Telnet默认23。', '网络端口', 1),
('计算机网络', '单选', 'OSI参考模型共分为几层？', '5层', '6层', '7层', '8层', 'C', 'OSI七层模型：物理层、数据链路层、网络层、传输层、会话层、表示层、应用层。', 'OSI模型', 2),
('数据结构', '单选', '栈的特点是？', '先进先出', '后进先出', '只能插入不能删除', '随机存取', 'B', '栈是后进先出(LIFO)的线性表，队列是先进先出(FIFO)。', '栈结构', 1),
('数据结构', '单选', '二分查找的时间复杂度是？', 'O(n)', 'O(n²)', 'O(log n)', 'O(1)', 'C', '二分查找每次将查找范围缩小一半，时间复杂度为O(log n)。', '查找算法', 2),
('数学', '单选', '函数f(x)=x²的导数是？', 'x', '2x', 'x²', '2', 'B', '根据求导公式，x^n的导数为n*x^(n-1)，所以x²的导数为2x。', '导数', 2),
('英语', '单选', '"She ___ to school every day." 填入正确的动词形式', 'go', 'goes', 'going', 'went', 'B', '第三人称单数一般现在时，动词要加es，go的第三人称单数是goes。', '一般现在时', 1),
('Java', '多选', '下列哪些是Java的集合类？', 'ArrayList', 'HashMap', 'String', 'HashSet', 'ABD', 'String是字符串类，不属于集合框架。ArrayList是List实现，HashMap是Map实现，HashSet是Set实现。', '集合框架', 2);

-- 错题数据
INSERT INTO `wrong_question` (user_id, question_id, user_answer, wrong_reason, mastered) VALUES
(2, 1, 'D', '混淆了基本数据类型和引用数据类型', 0),
(2, 3, 'A', '不了解String对象创建机制', 0),
(2, 5, 'B', '记错了OSI模型层数', 1),
(2, 7, 'A', '时间复杂度计算不熟练', 0),
(2, 10, 'ABC', '漏选了HashSet', 1);
