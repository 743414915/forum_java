/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : forum

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2023-05-18 11:51:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for email_code
-- ----------------------------
DROP TABLE IF EXISTS `email_code`;
CREATE TABLE `email_code` (
  `email` varchar(150) NOT NULL COMMENT '邮箱',
  `code` varchar(5) NOT NULL COMMENT '编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '0:未使用  1:已使用',
  PRIMARY KEY (`email`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮箱验证码';

-- ----------------------------
-- Records of email_code
-- ----------------------------
INSERT INTO `email_code` VALUES ('743414915@qq.com', '0BAV6', '2023-03-30 22:56:13', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', '0oGCQ', '2023-03-28 22:36:20', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', '1rVBF', '2023-03-28 21:46:09', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'B9tjF', '2023-03-28 21:48:34', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'Bce3D', '2023-04-05 19:21:23', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'BZ0x4', '2023-03-28 21:42:50', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'fDTrF', '2023-04-05 19:05:31', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'Ftpb4', '2023-04-05 14:08:33', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'gFD09', '2023-04-05 14:24:18', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'gOjF7', '2023-04-05 18:43:38', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'hXmmt', '2023-04-05 14:16:02', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'iR5s3', '2023-03-28 22:31:08', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'j0I7g', '2023-04-05 19:04:24', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'kcQyt', '2023-04-05 13:18:14', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'lu9PU', '2023-03-28 22:35:04', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'ouQI1', '2023-04-03 12:07:58', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'QfI1o', '2023-04-14 12:39:32', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'qXjYR', '2023-03-28 21:45:48', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'sKTUa', '2023-04-05 19:11:24', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'ugHqh', '2023-04-05 18:49:55', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'uQuRe', '2023-03-28 21:46:01', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'urG2b', '2023-03-28 22:19:34', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'VlnDT', '2023-03-28 21:39:44', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'YpxR6', '2023-03-28 21:42:59', '1');
INSERT INTO `email_code` VALUES ('743414915@qq.com', 'YR7js', '2023-03-28 22:37:48', '1');
INSERT INTO `email_code` VALUES ('tes2t@qq.com', '9vCSS', '2023-04-05 14:43:56', '1');
INSERT INTO `email_code` VALUES ('test004@qq.com', '4fjWu', '2023-03-28 21:22:56', '1');
INSERT INTO `email_code` VALUES ('test004@qq.com', 'Lw8n3', '2023-03-28 21:23:07', '1');
INSERT INTO `email_code` VALUES ('test004@qq.com', 'sLQrY', '2023-03-28 21:21:54', '1');
INSERT INTO `email_code` VALUES ('test02@qq.com', '02758', '2023-01-16 09:38:44', '1');
INSERT INTO `email_code` VALUES ('test@qq.com', '08531', '2023-01-15 17:45:44', '1');

-- ----------------------------
-- Table structure for forum_article
-- ----------------------------
DROP TABLE IF EXISTS `forum_article`;
CREATE TABLE `forum_article` (
  `article_id` varchar(15) NOT NULL COMMENT '文章ID',
  `board_id` int(11) DEFAULT NULL COMMENT '板块ID',
  `board_name` varchar(50) DEFAULT NULL COMMENT '板块名称',
  `p_board_id` int(11) DEFAULT NULL COMMENT '父级板块ID',
  `p_board_name` varchar(50) DEFAULT NULL COMMENT '父板块名称',
  `user_id` varchar(15) NOT NULL COMMENT '用户ID',
  `nick_name` varchar(20) NOT NULL COMMENT '昵称',
  `user_ip_address` varchar(100) DEFAULT NULL COMMENT '最后登录ip地址',
  `title` varchar(150) NOT NULL COMMENT '标题',
  `cover` varchar(100) DEFAULT NULL COMMENT '封面',
  `content` text COMMENT '内容',
  `markdown_content` text COMMENT 'markdown内容',
  `editor_type` tinyint(4) NOT NULL COMMENT '0:富文本编辑器 1:markdown编辑器',
  `summary` varchar(200) DEFAULT NULL COMMENT '摘要',
  `post_time` datetime NOT NULL COMMENT '发布时间',
  `last_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `read_count` int(11) DEFAULT '0' COMMENT '阅读数量',
  `good_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `top_type` tinyint(4) DEFAULT '0' COMMENT '0未置顶  1:已置顶',
  `attachment_type` tinyint(4) DEFAULT NULL COMMENT '0:没有附件  1:有附件',
  `status` tinyint(4) DEFAULT NULL COMMENT '-1已删除 0:待审核  1:已审核 ',
  PRIMARY KEY (`article_id`),
  KEY `idx_board_id` (`board_id`),
  KEY `idx_pboard_id` (`p_board_id`),
  KEY `idx_post_time` (`post_time`),
  KEY `idx_top_type` (`top_type`),
  KEY `idx_title` (`title`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章信息';

-- ----------------------------
-- Records of forum_article
-- ----------------------------
INSERT INTO `forum_article` VALUES ('001', '1', '动画讨论', '0', '动漫', 'user001', '小明', '192.168.0.1', '最爱的动画', '/covers/001.jpg', '这是一部非常好看的动画……', '这是一部非常好看的动画……', '0', '本文讨论的是最爱的动画，欢迎大家加入讨论', '2022-01-01 12:00:00', '2023-04-25 18:11:08', '142', '50', '20', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('002', '1', '动画讨论', '0', '动漫', 'user002', '小红', '192.168.0.2', '推荐一部新番', '/covers/002.jpg', '最近发现了一部非常有趣的动画……', '最近发现了一部非常有趣的动画……', '1', '来看看最新推荐的这部新番吧', '2022-02-01 14:30:00', '2023-04-08 12:10:44', '80', '30', '10', '0', '1', '1');
INSERT INTO `forum_article` VALUES ('003', '2', '漫画讨论', '0', '动漫', 'user003', '小华', '192.168.0.3', '推荐一部经典漫画', '/covers/003.jpg', '这是一部非常经典的漫画……', '这是一部非常经典的漫画……', '0', '分享一下自己最爱的经典漫画，欢迎大家一起交流', '2022-03-05 10:00:00', '2023-04-26 10:45:58', '151', '70', '30', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('004', '2', '漫画讨论', '0', '动漫', 'user004', '小李', '192.168.0.4', '最新一期漫画更新了', '/covers/004.jpg', '最新一期漫画更新了，快来看看吧……', '最新一期漫画更新了，快来看看吧……', '1', '最新一期漫画已经更新了，赶快来看看吧', '2022-03-20 15:00:00', '2023-04-25 18:11:00', '121', '40', '15', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('1', '1', '番剧讨论区', '0', '', 'u001', '小A', '192.168.0.1', '【讨论】《JOJO的奇妙冒险》第六部分的期待值', '/images/jojo.jpg', 'JOJO第六部分快要开始啦！你们都有什么期待的呢？', 'JOJO第六部分快要开始啦！你们都有什么期待的呢？', '0', '本文讨论《JOJO的奇妙冒险》第六部分', '2022-03-01 12:00:00', '2022-03-01 12:00:00', '100', '20', '10', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('2', '1', '番剧讨论区', '0', '', 'u002', '小B', '192.168.0.2', '【讨论】动画《创神英雄传》是否能成为2022年的爆款？', '/images/csyxz.jpg', '创神英雄传已经开播啦！大家觉得它是否有成为2022年爆款的潜力呢？', '创神英雄传已经开播啦！大家觉得它是否有成为2022年爆款的潜力呢？', '0', '本文讨论动画《创神英雄传》', '2022-03-03 10:00:00', '2023-04-09 19:02:04', '82', '10', '5', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('3', '2', '轻小说讨论区', '0', '', 'u003', '小C', '192.168.0.3', '【推荐】轻小说《关于我转生变成史莱姆这件事》', '/images/slime.jpg', '分享一下我最近看的一部轻小说《关于我转生变成史莱姆这件事》', '分享一下我最近看的一部轻小说《关于我转生变成史莱姆这件事》', '0', '推荐一下轻小说《关于我转生变成史莱姆这件事》', '2022-03-05 15:00:00', '2022-03-05 15:00:00', '120', '50', '30', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('44sORgDq2dntlpq', '10004', '大学', '10000', '校园动漫', '1890524956', '测试账号', '', '测试频次校验5', null, '<p>测试频次校验5</p>', '测试频次校验5', '1', null, '2023-04-19 15:26:25', '2023-04-25 18:12:31', '1', '0', '0', '0', '0', '-1');
INSERT INTO `forum_article` VALUES ('4tnuxcU9XUbP52v', '10005', '青年', '10001', '青少年动漫', '1890524956', '测试账号', '', '测试频次校验4', null, '<p>测试频次校验4</p>', '测试频次校验4', '1', null, '2023-04-19 15:25:38', '2023-04-25 18:12:48', '3', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('6MN92fHH5M3ryGR', '10003', '中小学', '10000', '校园动漫', '1890524956', '测试账号', '', '测试频次校验6', null, '<p>测试频次校验6</p>', '测试频次校验6', '1', null, '2023-04-19 15:26:50', '2023-04-25 18:12:25', '1', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('7bf9V6PgZPWzxjD', '10005', '青年', '10001', '青少年动漫', '1890524956', '测试账号', '', '测试校验频次', null, '<p>测试校验频次测试校验频次测试校验频次测试校验频次</p>', '测试校验频次测试校验频次测试校验频次测试校验频次', '1', null, '2023-04-19 15:22:32', '2023-04-25 18:12:48', '0', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('7QdXp0FkOIyAAsG', '1', '国漫', '10003', null, '10001', 'MangaFan', '192.168.1.100', '西游记', 'https://picsum.photos/id/12/640/480', '《西游记》是中国古代长篇小说，全名《大唐西游记》。作者是明代小说家吴承恩。小说讲述了唐僧师徒四人西天取经的故事，被誉为中国四大名著之一。', null, '0', '《西游记》讲述的是唐僧师徒四人在取经路上经历的一系列历险，同时还描写了各种奇异的人物形象，是中国古代小说的经典之作。', '2022-11-05 10:12:00', '2023-04-24 17:35:58', '1168', '543', '0', '0', '1', '1');
INSERT INTO `forum_article` VALUES ('7QdXp0FkOIyJAsG', '0', '', '10002', '恋爱动漫', '7437465925', '测试账号02', '未知', '同样是光头造型，把刘学义茅子俊边程放一起，差别就出来了', null, '<p data-v-md-line=\"1\">《少年歌行》剧版上线，豆瓣开分7.3，相信有数不清的书粉、漫粉慕名而来。圈里有这么一句大家都认同的老话，是说一部优秀的作品，原著是天，漫改是地，剧版则是毁天又灭地。那到底这部剧拍出来精髓还剩了多少？</p>\r\n', '《少年歌行》剧版上线，豆瓣开分7.3，相信有数不清的书粉、漫粉慕名而来。圈里有这么一句大家都认同的老话，是说一部优秀的作品，原著是天，漫改是地，剧版则是毁天又灭地。那到底这部剧拍出来精髓还剩了多少？', '1', '《少年歌行》剧版上线，豆瓣开分7.3，相信有数不清的书粉、漫粉慕名而来。圈里有这么一句大家都认同的老话，是说一部优秀的作品，原著是天，漫改是地，剧版则是毁天又灭地。那到底这部剧拍出来精髓还剩了多少？', '2023-01-16 10:01:14', '2023-04-25 17:59:17', '3', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('7QdXp0FkOIyJAsS', '0', '日漫', '10003', '动漫', '10001', 'MangaFan', '192.168.1.100', '火影忍者', 'https://picsum.photos/id/11/640/480', '火影忍者（Naruto），是日本漫画家岸本齐史创作的少年漫画作品。讲述的是一个忍者少年——漩涡鸣人，以及身边的小伙伴们不断奋斗、不断成长的故事。', null, '0', '火影忍者的情节围绕着寻找忍者的路途展开，鸣人等人不断面临各种各样的危险。', '2022-10-23 09:32:00', '2023-04-24 17:36:10', '2048', '123', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('ART00000001', '10003', '中小学', '10000', '校园动漫', 'USR00000001', '小明', '192.168.1.1', '《进击的巨人》第四季动画评测', 'https://example.com/cover/attack-on-titan.jpg', '最近看了进击的巨人第四季，感觉剧情非常精彩，画风也很优美。', '最近看了进击的巨人第四季，感觉剧情非常精彩，画风也很优美。', '1', '关于进击的巨人第四季动画的评测', '2023-04-08 12:00:00', '2023-04-25 18:12:25', '120', '50', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('ART00000002', '10003', '中小学', '10000', '校园动漫', 'USR00000002', '小红', '192.168.1.2', '《Re：从零开始的异世界生活》第二季观后感', 'https://example.com/cover/re-zero.jpg', '看完Re：从零开始的异世界生活第二季，觉得故事情节很精彩，人物形象也非常鲜明，特别喜欢里姆。', '看完Re：从零开始的异世界生活第二季，觉得故事情节很精彩，人物形象也非常鲜明，特别喜欢里姆。', '1', '关于Re：从零开始的异世界生活第二季的观后感', '2023-04-08 13:00:00', '2023-04-25 18:12:25', '81', '40', '0', '0', '1', '1');
INSERT INTO `forum_article` VALUES ('D3fj3tiHMCpDubm', '10003', '中小学', '10000', '校园动漫', '1890524956', '测试账号', '未知', 'Class 与 Style 绑定', null, '<p data-v-md-line=\"1\">数据绑定的一个常见需求场景是操纵元素的 CSS class 列表和内联样式。因为 class 和 style 都是 attribute，我们可以和其他 attribute 一样使用 v-bind 将它们和动态的字符串绑定。但是，在处理比较复杂的绑定时，通过拼接生成字符串是麻烦且易出错的。因此，Vue 专门为 class 和 style 的 v-bind 用法提供了特殊的功能增强。除了字符串外，表达式的值也可以是对象或数组。</p>\r\n', '数据绑定的一个常见需求场景是操纵元素的 CSS class 列表和内联样式。因为 class 和 style 都是 attribute，我们可以和其他 attribute 一样使用 v-bind 将它们和动态的字符串绑定。但是，在处理比较复杂的绑定时，通过拼接生成字符串是麻烦且易出错的。因此，Vue 专门为 class 和 style 的 v-bind 用法提供了特殊的功能增强。除了字符串外，表达式的值也可以是对象或数组。', '1', null, '2023-01-16 09:35:14', '2023-04-25 18:12:25', '1', '0', '0', '0', '0', '-1');
INSERT INTO `forum_article` VALUES ('f9xkyY6K6mZZ41G', '10003', '中小学', '10000', '校园动漫', '7437465925', '测试账号02', '未知', '条件渲染', null, '<p data-v-md-line=\"1\">条件渲染</p>\r\n<h1 data-v-md-heading=\"v-if\" data-v-md-line=\"2\">v-if</h1>\r\n<p data-v-md-line=\"3\">指令用于条件性地渲染一块内容。这块内容只会在指令的表达式返回真值时才被渲染。</p>\r\n<div data-v-md-line=\"4\"><div class=\"v-md-pre-wrapper v-md-pre-wrapper-js extra-class\"><pre class=\"v-md-hljs-js\"><code>&lt;h1 v-<span class=\"hljs-keyword\">if</span>=<span class=\"hljs-string\">&quot;awesome&quot;</span>&gt;Vue is awesome!&lt;/h1&gt;\r\n</code></pre>\r\n</div></div><h1 data-v-md-heading=\"v-else\" data-v-md-line=\"7\">v-else</h1>\r\n<p data-v-md-line=\"8\">你也可以使用 v-else 为 v-if 添加一个“else 区块”。</p>\r\n<div data-v-md-line=\"9\"><div class=\"v-md-pre-wrapper v-md-pre-wrapper-js extra-class\"><pre class=\"v-md-hljs-js\"><code>&lt;button @click=<span class=\"hljs-string\">&quot;awesome = !awesome&quot;</span>&gt;Toggle&lt;/button&gt;\r\n\r\n<span class=\"xml\"><span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">h1</span> <span class=\"hljs-attr\">v-if</span>=<span class=\"hljs-string\">&quot;awesome&quot;</span>&gt;</span>Vue is awesome!<span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">h1</span>&gt;</span></span>\r\n<span class=\"xml\"><span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">h1</span> <span class=\"hljs-attr\">v-else</span>&gt;</span>Oh no ?<span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">h1</span>&gt;</span></span>\r\n</code></pre>\r\n</div></div>', '条件渲染\r\n# v-if \r\n指令用于条件性地渲染一块内容。这块内容只会在指令的表达式返回真值时才被渲染。\r\n```js\r\n<h1 v-if=\"awesome\">Vue is awesome!</h1>\r\n```\r\n# v-else\r\n你也可以使用 v-else 为 v-if 添加一个“else 区块”。\r\n```js\r\n<button @click=\"awesome = !awesome\">Toggle</button>\r\n\r\n<h1 v-if=\"awesome\">Vue is awesome!</h1>\r\n<h1 v-else>Oh no ?</h1>\r\n```', '1', '令用于条件性地渲染一块内容。这块内容只会在指令的表达式返回真值时才被渲染。', '2023-01-16 09:55:37', '2023-04-26 10:45:44', '2', '10', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('foP3hhNAJTVCQYz', '0', '', '10002', '恋爱动漫', '7437465925', '测试账号02', '未知', '新射雕英雄传：梅超风路透，孟子义散发超美，洪七公南帝路透！', '202301/qkq0V8xvwQ8pQ4m.png', '<p data-v-md-line=\"1\">新版的射雕英雄传《金庸武侠世界》，目前还在拍摄中。一共有5个单元，请来了很多大家熟悉的演员。不过在服化道这块，似乎不是很有质感的样子。最近看到了孟子义，何润东，明道等人的造型，一起来看看吧。（本文为溪风影视汇聚独家首发，严禁抄袭洗稿！）</p>\r\n<p data-v-md-line=\"3\">孟子义版梅超风路透<br>\r\n这个新版的女演员颜值都在线，尤其是陈都灵的冯蘅，还有孟子义的梅超风。</p>\r\n<p data-v-md-line=\"6\">可惜周一围的黄药师，实在是不值得两大美女吃醋啊。<br>\r\n<img src=\"/api/file/getImage/202301/SQqeK0H5vMbrJgGa6y0FG247MIplyP.png\" alt=\"图片\"></p>\r\n', '新版的射雕英雄传《金庸武侠世界》，目前还在拍摄中。一共有5个单元，请来了很多大家熟悉的演员。不过在服化道这块，似乎不是很有质感的样子。最近看到了孟子义，何润东，明道等人的造型，一起来看看吧。（本文为溪风影视汇聚独家首发，严禁抄袭洗稿！）\r\n\r\n孟子义版梅超风路透\r\n这个新版的女演员颜值都在线，尤其是陈都灵的冯蘅，还有孟子义的梅超风。\r\n\r\n可惜周一围的黄药师，实在是不值得两大美女吃醋啊。\r\n![图片](/api/file/getImage/202301/SQqeK0H5vMbrJgGa6y0FG247MIplyP.png)', '1', '新版的射雕英雄传《金庸武侠世界》，目前还在拍摄中。一共有5个单元，请来了很多大家熟悉的演员', '2023-01-16 09:58:54', '2023-04-25 17:59:17', '123', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('gWiZMhCluNCfYRR', '0', '', '10002', '恋爱动漫', '7437465925', '测试账号02', '未知', '外媒镜头里的国内女星，简直堪比照妖镜：刘亦菲美的太不真实', '202301/2lR1QAS78RUrdX4.png', '<p data-v-md-line=\"1\">拍照五分钟，P图两小时，想要青春永驻、容颜不老，P图和美颜技术必须要好，普通人拍照尚且需要遵守这样的原则，在堪称“颜值即正义”的娱乐圈更不用说了。现在国内许多高颜值的明星很难碰到有不修图的。<br>\r\n<img src=\"/api/file/getImage/202301/ns0ysKmwKIiyHdHJt7UOSM9XYZZbJ2.png\" alt=\"图片\"></p>\r\n', '拍照五分钟，P图两小时，想要青春永驻、容颜不老，P图和美颜技术必须要好，普通人拍照尚且需要遵守这样的原则，在堪称“颜值即正义”的娱乐圈更不用说了。现在国内许多高颜值的明星很难碰到有不修图的。\r\n![图片](/api/file/getImage/202301/ns0ysKmwKIiyHdHJt7UOSM9XYZZbJ2.png)', '1', '拍照五分钟，P图两小时，想要青春永驻、容颜不老，P图和美颜技术必须要好', '2023-01-16 09:58:13', '2023-04-25 17:59:17', '23', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('HBNzH4CgFsitvtf', '0', '', '10002', '恋爱动漫', '7437465925', '测试账号02', '未知', '赵樱子的演技，好吗？吴镇宇的不鼓掌就是最好的回答', null, '<p data-v-md-line=\"1\">赵樱子身穿乞丐服，化着乞丐的妆容，扮演起了娇俏可人的黄蓉。</p>\r\n<p data-v-md-line=\"3\">这样一个经典的IP，这样一个经典的人物，赵樱子能够拿捏得住吗？</p>\r\n<p data-v-md-line=\"5\">从鼓掌就可以看出来了。</p>\r\n<p data-v-md-line=\"7\">佘诗曼、尔冬升、惠英红、许魏洲等人都给予了热烈的掌声。来放慢速度细品一下，尔冬升和佘诗曼是发自内心的，惠英红则是有一些无奈，并不是发自内心的鼓掌。</p>\r\n<p data-v-md-line=\"9\">尔冬升夸赵樱子哭戏好，让他颠覆了对她之前的看法。在尔冬升看来，一个好的演员只需要专注于自己的演技就好，把戏演好就可以了，不要去制造太多的新闻。这样容易本末倒置。</p>\r\n', '赵樱子身穿乞丐服，化着乞丐的妆容，扮演起了娇俏可人的黄蓉。\r\n\r\n这样一个经典的IP，这样一个经典的人物，赵樱子能够拿捏得住吗？\r\n\r\n从鼓掌就可以看出来了。\r\n\r\n佘诗曼、尔冬升、惠英红、许魏洲等人都给予了热烈的掌声。来放慢速度细品一下，尔冬升和佘诗曼是发自内心的，惠英红则是有一些无奈，并不是发自内心的鼓掌。\r\n\r\n尔冬升夸赵樱子哭戏好，让他颠覆了对她之前的看法。在尔冬升看来，一个好的演员只需要专注于自己的演技就好，把戏演好就可以了，不要去制造太多的新闻。这样容易本末倒置。', '1', '赵樱子身穿乞丐服，化着乞丐的妆容，扮演起了娇俏可人的黄蓉。', '2023-01-16 10:02:53', '2023-04-25 17:59:17', '4', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('kFmzzykJHesxjuv', '0', '', '10002', '恋爱动漫', '7437465925', '测试账号02', '未知', '金庸笔下，若把射雕三男主vs天龙三兄弟，哪个更厉害？', '202301/kOL99fdJztTWTa1.png', '<p data-v-md-line=\"1\">前言：金庸先生一生写了多篇武侠小说，而最有影响力的莫过于《天龙八部》与“射雕三部曲”共四部小说。这四部小说不断被翻拍为影视作品，活跃在大众视野之下。相信不少人第一次接触金庸先生的小说便是通过观看这四部小说改编而成的影视作品吧<br>\r\n<img src=\"/api/file/getImage/202301/QkduGtSxvqY2fhoUA5T0geVmU3TSpv.png\" alt=\"图片\"></p>\r\n', '前言：金庸先生一生写了多篇武侠小说，而最有影响力的莫过于《天龙八部》与“射雕三部曲”共四部小说。这四部小说不断被翻拍为影视作品，活跃在大众视野之下。相信不少人第一次接触金庸先生的小说便是通过观看这四部小说改编而成的影视作品吧\r\n![图片](/api/file/getImage/202301/QkduGtSxvqY2fhoUA5T0geVmU3TSpv.png)', '1', '金庸先生一生写了多篇武侠小说，而最有影响力的莫过于《天龙八部》与“射雕三部曲”共四部小说。这四部小说不断被翻拍为影视作品，活跃在大众视野之下', '2023-01-16 10:09:28', '2023-04-25 17:59:17', '52', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('NaU1ktjwN7h0EHf', '10003', '中小学', '10000', '校园动漫', '1890524956', '测试账号', '未知', '创建一个应用', '202301/iqlAZcksrj7NaDd.png', '<p data-v-md-line=\"1\">应用实例<br>\r\n每个 Vue 应用都是通过 createApp 函数创建一个新的 应用实例</p>\r\n<div data-v-md-line=\"3\"><div class=\"v-md-pre-wrapper v-md-pre-wrapper-js extra-class\"><pre class=\"v-md-hljs-js\"><code><span class=\"hljs-keyword\">import</span> { createApp } <span class=\"hljs-keyword\">from</span> <span class=\"hljs-string\">&#x27;vue&#x27;</span>\r\n\r\n<span class=\"hljs-keyword\">const</span> app = createApp({\r\n  <span class=\"hljs-comment\">/* 根组件选项 */</span>\r\n})\r\n</code></pre>\r\n</div></div>', '应用实例\r\n每个 Vue 应用都是通过 createApp 函数创建一个新的 应用实例\r\n```js\r\nimport { createApp } from \'vue\'\r\n\r\nconst app = createApp({\r\n  /* 根组件选项 */\r\n})\r\n```', '1', '', '2023-01-16 09:31:13', '2023-04-25 18:12:25', '35', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('NsIoyZeJs5vps7M', '10005', '青年', '10001', '青少年动漫', '1890524956', '测试账号', '', '测试频次校验3', null, '<p>测试频次校验2</p>', '测试频次校验2', '1', null, '2023-04-19 15:25:01', '2023-04-25 18:12:48', '1', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('o08GxoUuP76riyq', '10005', '青年', '10001', '青少年动漫', '1890524956', '测试账号', '', '测试频次校验2', null, '<p>测试频次校验2测试频次校验2</p>', '测试频次校验2测试频次校验2', '1', null, '2023-04-19 15:24:35', '2023-04-25 18:12:48', '1', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('OCvZQr4PQte1KGm', '10005', '青年', '10001', '青少年动漫', '1890524956', '测试账号', '', '测试校验频次1', null, '<p>测试校验频次测试校验频次测试校验频次测试校验频次</p>', '测试校验频次测试校验频次测试校验频次测试校验频次', '1', null, '2023-04-19 15:23:24', '2023-04-25 18:12:48', '0', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('odFjtwnRa5pVvD8', '10003', '中小学', '10000', '校园动漫', '1890524956', '测试账号', '未知', '计算属性', null, '<p data-v-md-line=\"1\">基础示例#<br>\r\n模板中的表达式虽然方便，但也只能用来做简单的操作。如果在模板中写太多逻辑，会让模板变得臃肿，难以维护。比如说，我们有这样一个包含嵌套数组的对象：</p>\r\n', '基础示例#\r\n模板中的表达式虽然方便，但也只能用来做简单的操作。如果在模板中写太多逻辑，会让模板变得臃肿，难以维护。比如说，我们有这样一个包含嵌套数组的对象：', '1', null, '2023-01-16 09:34:52', '2023-04-25 18:12:25', '4', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('QbanyzWzq3XV5KU', '0', '', '10002', '恋爱动漫', '7437465925', '测试账号02', '未知', '华为二公主姚安娜赢麻了！带资进组做配角，演技自然收获一致好评', '202301/nxUQw81IuTtUYCU.png', '<p data-v-md-line=\"1\">由刘亦菲和李现合作出演的电视剧《去有风的地方》正在热播中，据悉这是神仙姐姐刘亦菲首部现代剧，自从刘亦菲主演的《梦华录》成为现象级爆款后，很多观众都开始期待起这部双顶流影视剧了<br>\r\n<img src=\"/api/file/getImage/202301/MI545z3GH9q5E0lwP3SM2rqqhMJkVL.png\" alt=\"图片\"></p>\r\n', '由刘亦菲和李现合作出演的电视剧《去有风的地方》正在热播中，据悉这是神仙姐姐刘亦菲首部现代剧，自从刘亦菲主演的《梦华录》成为现象级爆款后，很多观众都开始期待起这部双顶流影视剧了\r\n![图片](/api/file/getImage/202301/MI545z3GH9q5E0lwP3SM2rqqhMJkVL.png)', '1', '由刘亦菲和李现合作出演的电视剧《去有风的地方》正在热播中，据悉这是神仙姐姐刘亦菲首部现代剧，自从刘亦菲主演的', '2023-01-16 09:57:32', '2023-04-25 17:59:17', '7', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('Qyj3cTZFfhO3wZM', '0', '', '10002', '恋爱动漫', '7437465925', '测试账号02', '未知', '在拍戏的过程中我们真的很爱对方”，张彬彬为什么这样说', '202301/d7eGKIvOtsBUFUo.png', '<p data-v-md-line=\"1\">在拍摄《司藤》时，发生了一件让张彬彬十分不理解的事，那就是大甜甜都能凭着那几十套的旗袍火，为啥自己同样换了十几套衣服，就没人在意呢？<br>\r\n<img src=\"/api/file/getImage/202301/e3FkzpUaSbjPkI0xX47Y4LbYmd8t4B.png\" alt=\"图片\"></p>\r\n', '在拍摄《司藤》时，发生了一件让张彬彬十分不理解的事，那就是大甜甜都能凭着那几十套的旗袍火，为啥自己同样换了十几套衣服，就没人在意呢？\r\n![图片](/api/file/getImage/202301/e3FkzpUaSbjPkI0xX47Y4LbYmd8t4B.png)', '1', null, '2023-01-16 10:05:02', '2023-04-25 17:59:17', '10', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('rRnX4Oz6vHihElY', '10003', '中小学', '10000', '校园动漫', '1890524956', '测试账号', '未知', '响应式基础', null, '<p data-v-md-line=\"1\">声明响应式状态#<br>\r\n选用选项式 API 时，会用 data 选项来声明组件的响应式状态。此选项的值应为返回一个对象的函数。Vue 将在创建新组件实例的时候调用此函数，并将函数返回的对象用响应式系统进行包装。此对象的所有顶层属性都会被代理到组件实例 (即方法和生命周期钩子中的 this) 上</p>\r\n', '声明响应式状态#\r\n选用选项式 API 时，会用 data 选项来声明组件的响应式状态。此选项的值应为返回一个对象的函数。Vue 将在创建新组件实例的时候调用此函数，并将函数返回的对象用响应式系统进行包装。此对象的所有顶层属性都会被代理到组件实例 (即方法和生命周期钩子中的 this) 上', '1', null, '2023-01-16 09:34:34', '2023-04-25 18:12:25', '3', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('RtiXj832TFL4nhW', '10003', '中小学', '10000', '校园动漫', '1890524956', '测试账号', '', '第一个帖子，带图，带附件', '202301/8Hyca1SDrUWhBRy.jpeg', '<p>第一个帖子，下面是图片<br><img src=\"/api/file/getImage/202301/F9TerZiONdqrZBk5o3NDqeRJuIlTaP.jpeg\" alt=\"图片\" data-href=\"\" style=\"\"/><br><br>testsdasdssdas</p>', '第一个帖子，下面是图片\n![图片](/api/file/getImage/202301/F9TerZiONdqrZBk5o3NDqeRJuIlTaP.jpeg)\ntestsdasdssdas', '1', '第一个帖子，带图，带附件，这里是摘要', '2023-01-15 18:01:23', '2023-04-27 09:46:52', '640', '3', '33', '1', '1', '1');
INSERT INTO `forum_article` VALUES ('sgXFWoQx4Fn3BsN', '0', '', '10002', '恋爱动漫', '7437465925', '测试账号02', '未知', '连续9天拿下收视冠军！久违的谭松韵一出手，就是国剧天花板！', '202301/OazOPuaQnfmpJcF.png', '<p data-v-md-line=\"1\">20岁，她在《甄嬛传》中演豆蔻少女“淳贵人”，少女的天真烂漫被她完美诠释，从而收获首波关注。<br>\r\n<img src=\"/api/file/getImage/202301/Xr6prSbCVgrMJNmFrVphOogQRRIaSQ.png\" alt=\"图片\"><br>\r\n再出现，她已经是《耿耿于怀》中的“耿耿”。这一年，她即将奔三，但还是搭配19岁的刘昊然，演起青春校园剧。15年，《旋风少女》爆了，风靡一整个暑假，无人不知无人不上头。而谭松韵饰演的“范晓萤”，人气比主角还高。这部剧中，“范晓萤”一角的定位依然是十七八的小女孩。<br>\r\n<img src=\"/api/file/getImage/202301/S2UKIwJH7RfKMh0nRkV2Z7GF9adh2I.png\" alt=\"图片\"></p>\r\n', '20岁，她在《甄嬛传》中演豆蔻少女“淳贵人”，少女的天真烂漫被她完美诠释，从而收获首波关注。\r\n![图片](/api/file/getImage/202301/Xr6prSbCVgrMJNmFrVphOogQRRIaSQ.png)\r\n再出现，她已经是《耿耿于怀》中的“耿耿”。这一年，她即将奔三，但还是搭配19岁的刘昊然，演起青春校园剧。15年，《旋风少女》爆了，风靡一整个暑假，无人不知无人不上头。而谭松韵饰演的“范晓萤”，人气比主角还高。这部剧中，“范晓萤”一角的定位依然是十七八的小女孩。\r\n![图片](/api/file/getImage/202301/S2UKIwJH7RfKMh0nRkV2Z7GF9adh2I.png)', '1', '20岁，她在《甄嬛传》中演豆蔻少女“淳贵人”，少女的天真烂漫被她完美诠释，从而收获首波关注。', '2023-01-16 09:59:37', '2023-04-25 17:59:17', '42', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('TBe46hXdObCBghT', '10004', '大学', '10000', '校园动漫', '1890524956', '测试账号', '', '测试审核文章', null, '<p>测试审核文章测试审核文章测试审核文章测试审核文章</p>', '', '0', null, '2023-04-19 10:09:42', '2023-04-25 18:12:31', '2', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('U2zRa4cFPwLF4rR', '0', '', '10002', '恋爱动漫', '7437465925', '测试账号02', '未知', '38岁凤姐现状：异国他乡漂泊十年，孤苦无依，网友“自作自受”', '202301/M5Apq2fXSfqQ9Lu.png', '<p data-v-md-line=\"1\">在贴吧和博客刚刚诞生的那几年，一位凹着“S型”造型的美女，横空出世，她就是来自陕西的芙蓉姐姐。<br>\r\n<img src=\"/api/file/getImage/202301/RxjFhBwN6XgR8Yy5V8po7UvkKoyK2W.png\" alt=\"图片\"><br>\r\n她以极其自恋的照片和无比自信的文字，在未名和水木的BBS上成为人尽皆知的红人。</p>\r\n<p data-v-md-line=\"5\">2005年，有好事的网友将她的文字和照片转载到彼时火热的猫扑和天涯上，让她成为了全网皆知的红人。</p>\r\n<p data-v-md-line=\"7\">无数人调侃她的长相，嘲讽她的舞姿，也有无数人坐等着她的博客更新。</p>\r\n<p data-v-md-line=\"9\">一时间，她的新闻和热搜霸榜各大门户网站的娱乐版块。</p>\r\n<p data-v-md-line=\"11\">她以”扮丑”和“低俗审美”的方式成为了许多无聊看客茶余饭后的谈资，也成了互联网上的“明星”。</p>\r\n<p data-v-md-line=\"13\">4年后，来自重庆的罗玉凤试图以同样的方式完成从“素人”到“明星”的转变。<br>\r\n<img src=\"/api/file/getImage/202301/1RyNjXxTr2F5Kb7KnwPdWCbxiLQvu2.png\" alt=\"图片\"></p>\r\n', '在贴吧和博客刚刚诞生的那几年，一位凹着“S型”造型的美女，横空出世，她就是来自陕西的芙蓉姐姐。\r\n![图片](/api/file/getImage/202301/RxjFhBwN6XgR8Yy5V8po7UvkKoyK2W.png)\r\n她以极其自恋的照片和无比自信的文字，在未名和水木的BBS上成为人尽皆知的红人。\r\n\r\n2005年，有好事的网友将她的文字和照片转载到彼时火热的猫扑和天涯上，让她成为了全网皆知的红人。\r\n\r\n无数人调侃她的长相，嘲讽她的舞姿，也有无数人坐等着她的博客更新。\r\n\r\n一时间，她的新闻和热搜霸榜各大门户网站的娱乐版块。\r\n\r\n她以”扮丑”和“低俗审美”的方式成为了许多无聊看客茶余饭后的谈资，也成了互联网上的“明星”。\r\n\r\n4年后，来自重庆的罗玉凤试图以同样的方式完成从“素人”到“明星”的转变。\r\n![图片](/api/file/getImage/202301/1RyNjXxTr2F5Kb7KnwPdWCbxiLQvu2.png)', '1', '在贴吧和博客刚刚诞生的那几年，一位凹着“S型”造型的美女，横空出世，她就是来自陕西的芙蓉姐姐。', '2023-01-16 10:00:18', '2023-04-25 17:59:17', '45', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('uT4FJLoDXSJhNYo', '10003', '中小学', '10000', '校园动漫', '1890524956', '测试账号', '未知', '模板语法', null, '<p data-v-md-line=\"1\">Vue 使用一种基于 HTML 的模板语法，使我们能够声明式地将其组件实例的数据绑定到呈现的 DOM 上。所有的 Vue 模板都是语法层面合法的 HTML，可以被符合规范的浏览器和 HTML 解析器解析。</p>\r\n<p data-v-md-line=\"3\">在底层机制中，Vue 会将模板编译成高度优化的 JavaScript 代码。结合响应式系统，当应用状态变更时，Vue 能够智能地推导出需要重新渲染的组件的最少数量，并应用最少的 DOM 操作。</p>\r\n<p data-v-md-line=\"5\">如果你对虚拟 DOM 的概念比较熟悉，并且偏好直接使用 JavaScript，你也可以结合可选的 JSX 支持直接手写渲染函数而不采用模板。但请注意，这将不会享受到和模板同等级别的编译时优化。</p>\r\n', 'Vue 使用一种基于 HTML 的模板语法，使我们能够声明式地将其组件实例的数据绑定到呈现的 DOM 上。所有的 Vue 模板都是语法层面合法的 HTML，可以被符合规范的浏览器和 HTML 解析器解析。\r\n\r\n在底层机制中，Vue 会将模板编译成高度优化的 JavaScript 代码。结合响应式系统，当应用状态变更时，Vue 能够智能地推导出需要重新渲染的组件的最少数量，并应用最少的 DOM 操作。\r\n\r\n如果你对虚拟 DOM 的概念比较熟悉，并且偏好直接使用 JavaScript，你也可以结合可选的 JSX 支持直接手写渲染函数而不采用模板。但请注意，这将不会享受到和模板同等级别的编译时优化。', '1', null, '2023-01-16 09:34:14', '2023-04-25 18:12:25', '1', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('V4a8RUmYw6X9R0y', '0', '', '10002', '恋爱动漫', '7437465925', '测试账号02', '未知', '投资仅850万，翻拍战狼，一上映就夺冠，于荣光带来一部生猛新片', '202301/8nsyR6wYBFi2wBL.png', '<p data-v-md-line=\"1\">近些年的内地电影市场，线下院线的发展有所停滞，但是网大电影似乎迎来了一波发展高潮，众多明星都开始转型拍摄网大电影。<br>\r\n<img src=\"/api/file/getImage/202301/NFpVgHDDeMLO7tyycZmd5Wxm7wSuuS.png\" alt=\"图片\"></p>\r\n', '近些年的内地电影市场，线下院线的发展有所停滞，但是网大电影似乎迎来了一波发展高潮，众多明星都开始转型拍摄网大电影。\r\n![图片](/api/file/getImage/202301/NFpVgHDDeMLO7tyycZmd5Wxm7wSuuS.png)', '1', '近些年的内地电影市场，线下院线的发展有所停滞，但是网大电影似乎迎来了一波发展高潮，众多明星都开始转型拍摄网大电影', '2023-01-16 10:01:43', '2023-04-25 17:59:17', '58', '0', '0', '0', '0', '1');
INSERT INTO `forum_article` VALUES ('ykYQG0hvUYbVoZ8', '10003', '中小学', '10000', '校园动漫', '1890524956', '测试账号', '', '测试发帖', '2023-04/Y08gUMOQHTBdzb5.jpeg', '<h1>这是一级标题</h1><p>内容</p><h2>这是二级标题</h2><p>内容</p><h3>这是三级标题</h3><p>还是内容<br><strong>加粗</strong><br><code>13点06分</code></p><h1>这是一级标题2</h1><p>内容</p><h2>这是二级标题2</h2><p>内容</p><h3>这是三级标题2</h3>', '# 这是一级标题\n内容\n## 这是二级标题\n内容\n###  这是三级标题\n还是内容\n**加粗**\n`13点06分`\n# 这是一级标题2\n内容\n## 这是二级标题2\n内容\n###  这是三级标题2', '1', '这是一个测试摘要', '2023-04-17 13:07:51', '2023-04-25 18:13:00', '95', '2', '5', '0', '1', '1');

-- ----------------------------
-- Table structure for forum_article_attachment
-- ----------------------------
DROP TABLE IF EXISTS `forum_article_attachment`;
CREATE TABLE `forum_article_attachment` (
  `file_id` varchar(15) NOT NULL COMMENT '文件ID',
  `article_id` varchar(15) NOT NULL COMMENT '文章ID',
  `user_id` varchar(15) DEFAULT NULL COMMENT '用户id',
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `file_name` varchar(200) DEFAULT NULL COMMENT '文件名称',
  `download_count` int(11) DEFAULT NULL COMMENT '下载次数',
  `file_path` varchar(100) DEFAULT NULL COMMENT '文件路径',
  `file_type` tinyint(4) DEFAULT NULL COMMENT '文件类型',
  `integral` int(11) DEFAULT NULL COMMENT '下载所需积分',
  PRIMARY KEY (`file_id`),
  KEY `idx_article_id` (`article_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件信息';

-- ----------------------------
-- Records of forum_article_attachment
-- ----------------------------
INSERT INTO `forum_article_attachment` VALUES ('014569155409431', 'RtiXj832TFL4nhW', '1890524956', '425672', '帅照.zip', '15', '202301/TADTC77LNjeO7Bs.zip', '0', '0');
INSERT INTO `forum_article_attachment` VALUES ('216261252545777', 'ykYQG0hvUYbVoZ8', '1890524956', '677188', '实习材料.zip', '1', '2023-04/SWziWTes3f7IVTj.zip', '0', '10');

-- ----------------------------
-- Table structure for forum_article_attachment_download
-- ----------------------------
DROP TABLE IF EXISTS `forum_article_attachment_download`;
CREATE TABLE `forum_article_attachment_download` (
  `file_id` varchar(15) NOT NULL COMMENT '文件ID',
  `user_id` varchar(15) NOT NULL COMMENT '用户id',
  `article_id` varchar(15) NOT NULL COMMENT '文章ID',
  `download_count` int(11) DEFAULT '1' COMMENT '文件下载次数',
  PRIMARY KEY (`file_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户附件下载';

-- ----------------------------
-- Records of forum_article_attachment_download
-- ----------------------------
INSERT INTO `forum_article_attachment_download` VALUES ('014569155409431', '1544156683', 'RtiXj832TFL4nhW', '15');
INSERT INTO `forum_article_attachment_download` VALUES ('216261252545777', '1890524956', 'ykYQG0hvUYbVoZ8', '1');

-- ----------------------------
-- Table structure for forum_board
-- ----------------------------
DROP TABLE IF EXISTS `forum_board`;
CREATE TABLE `forum_board` (
  `board_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '板块ID',
  `p_board_id` int(11) DEFAULT NULL COMMENT '父级板块ID',
  `board_name` varchar(50) DEFAULT NULL COMMENT '板块名',
  `cover` varchar(50) DEFAULT NULL COMMENT '封面',
  `board_desc` varchar(150) DEFAULT NULL COMMENT '描述',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `post_type` tinyint(1) DEFAULT '1' COMMENT '0:只允许管理员发帖 1:任何人可以发帖',
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10019 DEFAULT CHARSET=utf8mb4 COMMENT='文章板块信息';

-- ----------------------------
-- Records of forum_board
-- ----------------------------
INSERT INTO `forum_board` VALUES ('10000', '0', '校园动漫', null, '前端', '3', '1');
INSERT INTO `forum_board` VALUES ('10001', '0', '青少年动漫', null, '后端', '4', '1');
INSERT INTO `forum_board` VALUES ('10002', '0', '恋爱动漫', null, '摸鱼', '5', '1');
INSERT INTO `forum_board` VALUES ('10003', '10000', '中小学', null, '中小学', '1', '1');
INSERT INTO `forum_board` VALUES ('10004', '10000', '大学', null, '大学', '2', '1');
INSERT INTO `forum_board` VALUES ('10005', '10001', '青年', null, '青年', '1', '1');
INSERT INTO `forum_board` VALUES ('10006', '0', '社区管理', null, '社区管理', '6', '0');
INSERT INTO `forum_board` VALUES ('10007', '10006', '规章制度', null, '规章制度', '1', '0');
INSERT INTO `forum_board` VALUES ('10008', '1', '国漫', null, '国漫', '5', '1');
INSERT INTO `forum_board` VALUES ('10009', '0', '热血动漫', '2023-04/3KK4L4RFd8FxsUN.jpeg', '测试', '1', '1');
INSERT INTO `forum_board` VALUES ('10010', '0', '言情动漫', '2023-04/V7lpf6nKl1GD0jx.jpg', '测试测试测试测试测试测试测试测试11', '2', '1');
INSERT INTO `forum_board` VALUES ('10015', '10009', '国漫', '2023-04/DnvvpuwfxnVDoep.jpg', '测试测试测试测试测试', '1', '1');
INSERT INTO `forum_board` VALUES ('10016', '10009', '日漫', '2023-04/xV6FtR78dk3jIr9.jpeg', null, '2', '1');
INSERT INTO `forum_board` VALUES ('10018', '10001', '少年', null, '少年', '2', '1');

-- ----------------------------
-- Table structure for forum_comment
-- ----------------------------
DROP TABLE IF EXISTS `forum_comment`;
CREATE TABLE `forum_comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `p_comment_id` int(11) DEFAULT NULL COMMENT '父级评论ID',
  `article_id` varchar(15) NOT NULL COMMENT '文章ID',
  `content` varchar(800) DEFAULT NULL COMMENT '回复内容',
  `img_path` varchar(150) DEFAULT NULL COMMENT '图片',
  `user_id` varchar(15) NOT NULL COMMENT '用户ID',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `user_ip_address` varchar(100) DEFAULT NULL COMMENT '用户ip地址',
  `reply_user_id` varchar(15) DEFAULT NULL COMMENT '回复人ID',
  `reply_nick_name` varchar(20) DEFAULT NULL COMMENT '回复人昵称',
  `top_type` tinyint(4) DEFAULT '0' COMMENT '0:未置顶  1:置顶',
  `post_time` datetime DEFAULT NULL COMMENT '发布时间',
  `good_count` int(11) DEFAULT '0' COMMENT 'good数量',
  `status` tinyint(4) DEFAULT NULL COMMENT '0:待审核  1:已审核',
  PRIMARY KEY (`comment_id`),
  KEY `idx_article_id` (`article_id`),
  KEY `idx_post_time` (`post_time`),
  KEY `idx_top` (`top_type`),
  KEY `idx_p_id` (`p_comment_id`),
  KEY `idx_status` (`status`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10077 DEFAULT CHARSET=utf8mb4 COMMENT='评论';

-- ----------------------------
-- Records of forum_comment
-- ----------------------------
INSERT INTO `forum_comment` VALUES ('10000', '0', 'RtiXj832TFL4nhW', '自己做沙发', null, '1890524956', '测试账号', '未知', null, null, '1', '2023-01-15 18:01:35', '2', '1');
INSERT INTO `forum_comment` VALUES ('10001', '0', 'RtiXj832TFL4nhW', '带图的评论', '202301/G6f2JlVazmYMLYP.jpeg', '1890524956', '测试账号', '未知', null, null, '0', '2023-01-16 09:26:07', '0', '1');
INSERT INTO `forum_comment` VALUES ('10002', '0', 'RtiXj832TFL4nhW', '我来个评论', null, '7437465925', '测试账号02', '未知', null, null, '0', '2023-01-16 09:54:04', '0', '1');
INSERT INTO `forum_comment` VALUES ('10003', '0', 'RtiXj832TFL4nhW', '啦啦啦啦啦啦啦啦啦啦啦啦', null, '7437465925', '测试账号02', '未知', null, null, '0', '2023-01-16 14:40:53', '0', '1');
INSERT INTO `forum_comment` VALUES ('10004', '0', 'RtiXj832TFL4nhW', '测试评论', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-14 16:53:44', '0', '1');
INSERT INTO `forum_comment` VALUES ('10005', '0', 'RtiXj832TFL4nhW', '测试二级', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-14 16:59:47', '0', '1');
INSERT INTO `forum_comment` VALUES ('10006', '0', 'RtiXj832TFL4nhW', '测试二级', null, '1890524956', '测试账号', '', 'null', null, '0', '2023-04-14 17:00:43', '0', '1');
INSERT INTO `forum_comment` VALUES ('10007', '10000', 'RtiXj832TFL4nhW', '测试二级', null, '1890524956', '测试账号', '', 'null', '测试账号', '0', '2023-04-14 17:02:22', '2', '1');
INSERT INTO `forum_comment` VALUES ('10008', '10000', 'RtiXj832TFL4nhW', '测试二级二级二级', null, '1890524956', '测试账号', '', 'null', '测试账号', '0', '2023-04-14 17:02:56', '1', '1');
INSERT INTO `forum_comment` VALUES ('10009', '10000', 'RtiXj832TFL4nhW', '二级bug', null, '1890524956', '测试账号', '', 'null', '测试账号', '0', '2023-04-14 17:04:03', '2', '1');
INSERT INTO `forum_comment` VALUES ('10010', '10000', 'RtiXj832TFL4nhW', '二级bug', null, '1890524956', '测试账号', '', '1890524956', '测试账号', '0', '2023-04-14 17:07:41', '2', '1');
INSERT INTO `forum_comment` VALUES ('10011', '10000', 'RtiXj832TFL4nhW', '二级bug', null, '1890524956', '测试账号', '', '1890524956', '测试账号', '0', '2023-04-14 19:58:17', '1', '1');
INSERT INTO `forum_comment` VALUES ('10012', '10000', 'RtiXj832TFL4nhW', '这是一个二级评论', null, '1890524956', '测试账号', '', '1890524956', '测试账号', '0', '2023-04-15 11:20:16', '0', '1');
INSERT INTO `forum_comment` VALUES ('10013', '10000', 'RtiXj832TFL4nhW', '测试测试', null, '7514349893', '宠', '', '1890524956', null, '0', '2023-04-15 12:14:27', '0', '-1');
INSERT INTO `forum_comment` VALUES ('10014', '10000', 'RtiXj832TFL4nhW', '还是测试', null, '7437465925', '测试账号02', '', '1890524956', null, '0', '2023-04-15 12:15:10', '1', '1');
INSERT INTO `forum_comment` VALUES ('10015', '10002', 'RtiXj832TFL4nhW', '111111111111111111', null, '1890524956', '测试账号', '', '7437465925', null, '0', '2023-04-15 13:21:02', '0', '1');
INSERT INTO `forum_comment` VALUES ('10016', '10003', 'RtiXj832TFL4nhW', 'zzzzzzzzzzzzzzzzzz', null, '1890524956', '测试账号', '', '7437465925', null, '0', '2023-04-15 13:21:20', '0', '1');
INSERT INTO `forum_comment` VALUES ('10017', '10002', 'RtiXj832TFL4nhW', '0000000000000', null, '1890524956', '测试账号', '', '1890524956', null, '0', '2023-04-15 13:22:24', '0', '1');
INSERT INTO `forum_comment` VALUES ('10018', '0', 'RtiXj832TFL4nhW', '00000', null, '1890524956', '测试账号', '', 'null', null, '0', '2023-04-15 13:22:39', '0', '1');
INSERT INTO `forum_comment` VALUES ('10019', '0', 'RtiXj832TFL4nhW', '0', null, '1890524956', '测试账号', '', 'null', null, '0', '2023-04-15 13:23:23', '0', '1');
INSERT INTO `forum_comment` VALUES ('10020', '10003', 'RtiXj832TFL4nhW', '0', null, '1890524956', '测试账号', '', '1890524956', null, '0', '2023-04-15 13:26:28', '0', '1');
INSERT INTO `forum_comment` VALUES ('10021', '10002', 'RtiXj832TFL4nhW', '0', null, '1890524956', '测试账号', '', '1890524956', null, '0', '2023-04-15 13:26:43', '0', '1');
INSERT INTO `forum_comment` VALUES ('10022', '10002', 'RtiXj832TFL4nhW', '0', null, '1890524956', '测试账号', '', '1890524956', null, '0', '2023-04-15 13:27:26', '0', '1');
INSERT INTO `forum_comment` VALUES ('10023', '10001', 'RtiXj832TFL4nhW', '0', null, '1890524956', '测试账号', '', '1890524956', null, '0', '2023-04-15 13:29:02', '0', '1');
INSERT INTO `forum_comment` VALUES ('10024', '10003', 'RtiXj832TFL4nhW', '111', null, '1890524956', '测试账号', '', '1890524956', null, '0', '2023-04-15 13:29:20', '0', '1');
INSERT INTO `forum_comment` VALUES ('10025', '10001', 'RtiXj832TFL4nhW', '3232', null, '1890524956', '测试账号', '', '1890524956', null, '0', '2023-04-15 13:29:28', '0', '1');
INSERT INTO `forum_comment` VALUES ('10026', '10001', 'RtiXj832TFL4nhW', '11', null, '1890524956', '测试账号', '', '1890524956', null, '0', '2023-04-15 13:33:47', '0', '1');
INSERT INTO `forum_comment` VALUES ('10027', '10001', 'RtiXj832TFL4nhW', '1234', null, '1890524956', '测试账号', '', '1890524956', '测试账号', '0', '2023-04-15 15:36:36', '0', '1');
INSERT INTO `forum_comment` VALUES ('10028', '10002', 'RtiXj832TFL4nhW', '231', null, '1890524956', '测试账号', '', '1890524956', '测试账号', '0', '2023-04-15 15:36:48', '0', '1');
INSERT INTO `forum_comment` VALUES ('10029', '10003', 'RtiXj832TFL4nhW', '1123465132', null, '1890524956', '测试账号', '', '7437465925', '测试账号02', '0', '2023-04-15 15:42:39', '0', '1');
INSERT INTO `forum_comment` VALUES ('10030', '10000', 'RtiXj832TFL4nhW', '1', null, '1890524956', '测试账号', '', '7437465925', '测试账号02', '0', '2023-04-15 15:45:18', '0', '1');
INSERT INTO `forum_comment` VALUES ('10031', '10000', 'RtiXj832TFL4nhW', '745123', null, '1890524956', '测试账号', '', '7514349893', '宠', '0', '2023-04-15 15:45:25', '0', '1');
INSERT INTO `forum_comment` VALUES ('10032', '0', 'RtiXj832TFL4nhW', '带图片', '2023-04/eZ971zm8MbK0hRh.jpg', '1890524956', '测试账号', '', '', null, '0', '2023-04-15 16:49:38', '0', '1');
INSERT INTO `forum_comment` VALUES ('10033', '0', 'RtiXj832TFL4nhW', '带图片', '2023-04/8MiNgr5Z74nQxFn.jpg', '1890524956', '测试账号', '', '', null, '0', '2023-04-15 17:01:45', '0', '1');
INSERT INTO `forum_comment` VALUES ('10034', '0', 'RtiXj832TFL4nhW', '不带图', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-15 20:08:36', '0', '1');
INSERT INTO `forum_comment` VALUES ('10035', '0', 'RtiXj832TFL4nhW', '86kb', '2023-04/MVrnI5rTI8YEKn7.jpg', '1890524956', '测试账号', '', '', null, '0', '2023-04-15 20:08:47', '0', '1');
INSERT INTO `forum_comment` VALUES ('10036', '0', 'RtiXj832TFL4nhW', '1MB', '2023-04/8d7rG8ZOGmDH0yo.jpeg', '1890524956', '测试账号', '', '', null, '0', '2023-04-15 20:13:50', '0', '1');
INSERT INTO `forum_comment` VALUES ('10037', '0', 'RtiXj832TFL4nhW', ' ', '2023-04/yB42BGRbMfEoTSz.jpeg', '1890524956', '测试账号', '', '', null, '0', '2023-04-15 20:14:06', '0', '1');
INSERT INTO `forum_comment` VALUES ('10038', '0', 'RtiXj832TFL4nhW', ' ', '2023-04/YBm0zbksBMZO3fP.jpeg', '1890524956', '测试账号', '', '', null, '0', '2023-04-15 20:15:31', '0', '1');
INSERT INTO `forum_comment` VALUES ('10039', '0', 'RtiXj832TFL4nhW', '发完清图', '2023-04/MTcgN0kEwM4I3an.jpg', '1890524956', '测试账号', '', '', null, '0', '2023-04-15 20:16:03', '0', '1');
INSERT INTO `forum_comment` VALUES ('10040', '0', 'RtiXj832TFL4nhW', '更新评论', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-15 20:18:07', '0', '1');
INSERT INTO `forum_comment` VALUES ('10041', '0', 'RtiXj832TFL4nhW', '更新评论数', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-15 20:18:16', '0', '1');
INSERT INTO `forum_comment` VALUES ('10042', '0', 'RtiXj832TFL4nhW', '更新评论数', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-15 20:22:12', '0', '1');
INSERT INTO `forum_comment` VALUES ('10043', '10000', 'RtiXj832TFL4nhW', '更新评论数', null, '1890524956', '测试账号', '', '1890524956', '测试账号', '0', '2023-04-15 20:22:23', '0', '1');
INSERT INTO `forum_comment` VALUES ('10044', '0', 'RtiXj832TFL4nhW', '打算大苏打实打实大苏打', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-15 20:34:34', '0', '1');
INSERT INTO `forum_comment` VALUES ('10045', '0', 'RtiXj832TFL4nhW', 'sdasdassd', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-15 20:34:43', '0', '1');
INSERT INTO `forum_comment` VALUES ('10046', '0', 'RtiXj832TFL4nhW', '541230', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-15 20:56:52', '0', '1');
INSERT INTO `forum_comment` VALUES ('10047', '0', 'RtiXj832TFL4nhW', '4651', null, '1890524956', '测试账号', '未知', '', null, '0', '2023-04-15 22:08:40', '0', '1');
INSERT INTO `forum_comment` VALUES ('10048', '10000', 'RtiXj832TFL4nhW', '12122134556', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:08:58', '0', '1');
INSERT INTO `forum_comment` VALUES ('10049', '10000', 'RtiXj832TFL4nhW', '78944456', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:09:31', '0', '1');
INSERT INTO `forum_comment` VALUES ('10050', '10000', 'RtiXj832TFL4nhW', '4585', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:10:31', '0', '1');
INSERT INTO `forum_comment` VALUES ('10051', '0', 'RtiXj832TFL4nhW', 'xinSAdaasd啊实打实的', null, '1890524956', '测试账号', '未知', '', null, '0', '2023-04-15 22:12:47', '0', '1');
INSERT INTO `forum_comment` VALUES ('10052', '10000', 'RtiXj832TFL4nhW', '大苏打萨达阿迪斯', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:13:00', '0', '1');
INSERT INTO `forum_comment` VALUES ('10053', '10000', 'RtiXj832TFL4nhW', '7412啊实打实大苏打大啊大苏打飒飒大阿松大', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:13:58', '0', '1');
INSERT INTO `forum_comment` VALUES ('10054', '0', 'RtiXj832TFL4nhW', '在上面，', null, '1890524956', '测试账号', '未知', '', null, '0', '2023-04-15 22:15:31', '0', '1');
INSERT INTO `forum_comment` VALUES ('10055', '10000', 'RtiXj832TFL4nhW', '出来', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:15:38', '0', '1');
INSERT INTO `forum_comment` VALUES ('10056', '10000', 'RtiXj832TFL4nhW', '出来', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:15:53', '0', '1');
INSERT INTO `forum_comment` VALUES ('10057', '10001', 'RtiXj832TFL4nhW', '出来', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:16:18', '0', '1');
INSERT INTO `forum_comment` VALUES ('10058', '10001', 'RtiXj832TFL4nhW', '4', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:17:05', '0', '1');
INSERT INTO `forum_comment` VALUES ('10059', '10001', 'RtiXj832TFL4nhW', '给爷出来', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:17:45', '0', '1');
INSERT INTO `forum_comment` VALUES ('10060', '10001', 'RtiXj832TFL4nhW', 'loading', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:18:46', '0', '1');
INSERT INTO `forum_comment` VALUES ('10061', '10001', 'RtiXj832TFL4nhW', 'loading', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:18:54', '0', '1');
INSERT INTO `forum_comment` VALUES ('10062', '10002', 'RtiXj832TFL4nhW', '不要loading', null, '1890524956', '测试账号', '未知', '1890524956', '测试账号', '0', '2023-04-15 22:20:04', '0', '1');
INSERT INTO `forum_comment` VALUES ('10063', '0', 'ykYQG0hvUYbVoZ8', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-17 13:08:18', '1', '1');
INSERT INTO `forum_comment` VALUES ('10064', '0', 'ykYQG0hvUYbVoZ8', '测试发言', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-17 13:08:21', '1', '1');
INSERT INTO `forum_comment` VALUES ('10065', '10064', 'ykYQG0hvUYbVoZ8', '发言', null, '1890524956', '测试账号', '', '1890524956', '测试账号', '0', '2023-04-17 13:08:28', '1', '1');
INSERT INTO `forum_comment` VALUES ('10066', '10063', 'ykYQG0hvUYbVoZ8', '发言', null, '1890524956', '测试账号', '', '1890524956', '测试账号', '0', '2023-04-17 13:08:34', '1', '1');
INSERT INTO `forum_comment` VALUES ('10067', '0', 'ykYQG0hvUYbVoZ8', '最上面', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-17 13:08:41', '0', '1');
INSERT INTO `forum_comment` VALUES ('10068', '0', 'RtiXj832TFL4nhW', '评论', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-18 20:02:44', '0', '1');
INSERT INTO `forum_comment` VALUES ('10069', '0', 'RtiXj832TFL4nhW', '还是评论', null, '1890524956', '测试账号', '', '', null, '0', '2023-04-18 20:04:50', '0', '1');
INSERT INTO `forum_comment` VALUES ('10070', '0', 'RtiXj832TFL4nhW', '测试评论哈哈哈啊哈哈哈哈', null, '7514349893', '宠', '', '', null, '0', '2023-04-18 20:13:55', '0', '-1');
INSERT INTO `forum_comment` VALUES ('10071', '0', 'RtiXj832TFL4nhW', '再次测试评论哈哈哈啊哈哈哈哈', null, '7514349893', '宠', '', '', null, '0', '2023-04-18 20:14:57', '0', '-1');
INSERT INTO `forum_comment` VALUES ('10072', '0', 'ykYQG0hvUYbVoZ8', '测试发言', null, '7514349893', '宠', '', '', null, '0', '2023-04-18 20:16:25', '0', '-1');
INSERT INTO `forum_comment` VALUES ('10073', '0', 'ykYQG0hvUYbVoZ8', '发帖测试', null, '7514349893', '宠', '', '', null, '0', '2023-04-18 20:18:29', '0', '-1');
INSERT INTO `forum_comment` VALUES ('10074', '0', 'ykYQG0hvUYbVoZ8', '测试', null, '7514349893', '宠', '', '', null, '0', '2023-04-18 20:20:24', '0', '-1');
INSERT INTO `forum_comment` VALUES ('10075', '0', 'ykYQG0hvUYbVoZ8', '测试', null, '7514349893', '宠', '', '', null, '0', '2023-04-18 20:20:31', '0', '-1');
INSERT INTO `forum_comment` VALUES ('10076', '0', 'ykYQG0hvUYbVoZ8', 'ceshi', null, '7514349893', '宠', '', '', null, '0', '2023-04-18 20:21:49', '0', '-1');

-- ----------------------------
-- Table structure for like_record
-- ----------------------------
DROP TABLE IF EXISTS `like_record`;
CREATE TABLE `like_record` (
  `op_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `op_type` tinyint(4) DEFAULT NULL COMMENT '操作类型0:文章点赞 1:评论点赞',
  `object_id` varchar(15) NOT NULL COMMENT '主体ID',
  `user_id` varchar(15) NOT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '发布时间',
  `author_user_id` varchar(15) DEFAULT NULL COMMENT '主体作者ID',
  PRIMARY KEY (`op_id`),
  UNIQUE KEY `idx_key` (`object_id`,`user_id`,`op_type`) USING BTREE,
  KEY `idx_user_id` (`user_id`,`op_type`)
) ENGINE=InnoDB AUTO_INCREMENT=10049 DEFAULT CHARSET=utf8mb4 COMMENT='点赞记录';

-- ----------------------------
-- Records of like_record
-- ----------------------------
INSERT INTO `like_record` VALUES ('10000', '0', 'RtiXj832TFL4nhW', '7437465925', '2023-01-16 09:53:50', '1890524956');
INSERT INTO `like_record` VALUES ('10015', '0', 'RtiXj832TFL4nhW', '1544156683', '2023-04-10 13:31:45', '1890524956');
INSERT INTO `like_record` VALUES ('10021', '1', '10000', '1890524956', '2023-04-15 16:05:55', '1890524956');
INSERT INTO `like_record` VALUES ('10022', '1', '10007', '1890524956', '2023-04-15 16:05:56', '1890524956');
INSERT INTO `like_record` VALUES ('10023', '1', '10009', '1890524956', '2023-04-15 16:08:45', '1890524956');
INSERT INTO `like_record` VALUES ('10025', '1', '10010', '1890524956', '2023-04-15 16:08:47', '1890524956');
INSERT INTO `like_record` VALUES ('10026', '0', 'ykYQG0hvUYbVoZ8', '1890524956', '2023-04-17 13:08:54', '1890524956');
INSERT INTO `like_record` VALUES ('10029', '0', 'ykYQG0hvUYbVoZ8', '7514349893', '2023-04-18 20:24:39', '1890524956');
INSERT INTO `like_record` VALUES ('10030', '1', '10066', '1890524956', '2023-04-18 20:30:14', '1890524956');
INSERT INTO `like_record` VALUES ('10033', '1', '10063', '1890524956', '2023-04-18 20:31:34', '1890524956');
INSERT INTO `like_record` VALUES ('10034', '1', '10064', '1890524956', '2023-04-18 20:32:46', '1890524956');
INSERT INTO `like_record` VALUES ('10035', '1', '10014', '7514349893', '2023-04-18 20:34:29', '7437465925');
INSERT INTO `like_record` VALUES ('10037', '1', '10010', '7514349893', '2023-04-18 20:35:43', '1890524956');
INSERT INTO `like_record` VALUES ('10038', '1', '10009', '7514349893', '2023-04-18 20:35:49', '1890524956');
INSERT INTO `like_record` VALUES ('10039', '1', '10000', '7514349893', '2023-04-18 20:35:50', '1890524956');
INSERT INTO `like_record` VALUES ('10040', '1', '10007', '7514349893', '2023-04-18 20:35:51', '1890524956');
INSERT INTO `like_record` VALUES ('10041', '1', '10008', '7514349893', '2023-04-18 20:35:52', '1890524956');
INSERT INTO `like_record` VALUES ('10042', '1', '10011', '7514349893', '2023-04-18 20:35:54', '1890524956');
INSERT INTO `like_record` VALUES ('10045', '1', '10063', '7514349893', '2023-04-18 20:36:23', '1890524956');
INSERT INTO `like_record` VALUES ('10046', '1', '10065', '7514349893', '2023-04-18 20:36:25', '1890524956');
INSERT INTO `like_record` VALUES ('10048', '0', 'RtiXj832TFL4nhW', '7514349893', '2023-04-27 09:46:52', '1890524956');

-- ----------------------------
-- Table structure for sys_setting
-- ----------------------------
DROP TABLE IF EXISTS `sys_setting`;
CREATE TABLE `sys_setting` (
  `code` varchar(10) NOT NULL COMMENT '编号',
  `json_content` varchar(500) DEFAULT NULL COMMENT '设置信息',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统设置信息';

-- ----------------------------
-- Records of sys_setting
-- ----------------------------
INSERT INTO `sys_setting` VALUES ('audit', '{\"commentAudit\":false,\"postAudit\":false}');
INSERT INTO `sys_setting` VALUES ('comment', '{\"commentDayCountThreshold\":50,\"commentIntegral\":1,\"commentOpen\":true}');
INSERT INTO `sys_setting` VALUES ('email', '{\"emailContent\":\"你好，您的邮箱验证码是：%s，15分钟有效\",\"emailTitle\":\"邮箱验证码--Forum\"}');
INSERT INTO `sys_setting` VALUES ('like', '{\"likeDayCountThreshold\":50}');
INSERT INTO `sys_setting` VALUES ('post', '{\"attachmentSize\":1,\"dayImageUploadCount\":50,\"postDayCountThreshold\":50,\"postIntegral\":1}');
INSERT INTO `sys_setting` VALUES ('register', '{\"registerWelcomInfo\":\"社区欢迎你，以后的日子里，一起讨论喜欢的动漫吧\"}');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` varchar(15) NOT NULL COMMENT '用户ID',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `email` varchar(150) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `sex` tinyint(1) DEFAULT NULL COMMENT '0:女 1:男',
  `person_description` varchar(200) DEFAULT NULL COMMENT '个人描述',
  `join_time` datetime DEFAULT NULL COMMENT '加入时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(15) DEFAULT NULL COMMENT '最后登录IP',
  `last_login_ip_address` varchar(100) DEFAULT NULL COMMENT '最后登录ip地址',
  `total_integral` int(11) DEFAULT NULL COMMENT '积分',
  `current_integral` int(11) DEFAULT NULL COMMENT '当前积分',
  `status` tinyint(4) DEFAULT NULL COMMENT '0:禁用 1:正常',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `key_email` (`email`),
  UNIQUE KEY `key_nick_name` (`nick_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1890524956', '测试账号', 'test@qq.com', '47ec2dd791e31e2ef2076caf64ed9b3d', '0', '我只是一个测试账号而已', '2023-01-15 17:45:46', '2023-04-27 09:49:14', '127.0.0.1', '', '80', '80', '1');
INSERT INTO `user_info` VALUES ('7437465925', '测试账号02', 'test02@qq.com', '47ec2dd791e31e2ef2076caf64ed9b3d', '0', '我是测试账号02', '2023-01-16 09:52:31', '2023-04-15 12:15:02', '127.0.0.1', '', '19', '19', '1');
INSERT INTO `user_info` VALUES ('7514349893', '宠', '743414915@qq.com', 'dbfdbf1ada35f439cca8bbf54c56754c', null, null, '2023-04-14 12:44:38', '2023-04-27 09:11:18', '127.0.0.1', '未知', '16', '13', '1');

-- ----------------------------
-- Table structure for user_integral_record
-- ----------------------------
DROP TABLE IF EXISTS `user_integral_record`;
CREATE TABLE `user_integral_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` varchar(15) DEFAULT NULL COMMENT '用户ID',
  `oper_type` tinyint(4) DEFAULT NULL COMMENT '操作类型',
  `integral` int(11) DEFAULT NULL COMMENT '积分',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10118 DEFAULT CHARSET=utf8mb4 COMMENT='用户积分记录表';

-- ----------------------------
-- Records of user_integral_record
-- ----------------------------
INSERT INTO `user_integral_record` VALUES ('10000', '1890524956', '1', '5', '2023-01-15 17:45:46');
INSERT INTO `user_integral_record` VALUES ('10004', '1890524956', '4', '1', '2023-01-15 18:01:23');
INSERT INTO `user_integral_record` VALUES ('10005', '1890524956', '4', '1', '2023-01-15 18:01:35');
INSERT INTO `user_integral_record` VALUES ('10006', '1890524956', '4', '1', '2023-01-16 09:26:07');
INSERT INTO `user_integral_record` VALUES ('10007', '1890524956', '4', '1', '2023-01-16 09:31:13');
INSERT INTO `user_integral_record` VALUES ('10008', '1890524956', '4', '1', '2023-01-16 09:34:14');
INSERT INTO `user_integral_record` VALUES ('10009', '1890524956', '4', '1', '2023-01-16 09:34:34');
INSERT INTO `user_integral_record` VALUES ('10010', '1890524956', '4', '1', '2023-01-16 09:34:52');
INSERT INTO `user_integral_record` VALUES ('10011', '1890524956', '4', '1', '2023-01-16 09:35:14');
INSERT INTO `user_integral_record` VALUES ('10012', '7437465925', '1', '5', '2023-01-16 09:52:31');
INSERT INTO `user_integral_record` VALUES ('10013', '7437465925', '4', '1', '2023-01-16 09:54:04');
INSERT INTO `user_integral_record` VALUES ('10014', '7437465925', '4', '1', '2023-01-16 09:55:37');
INSERT INTO `user_integral_record` VALUES ('10015', '7437465925', '4', '1', '2023-01-16 09:57:32');
INSERT INTO `user_integral_record` VALUES ('10016', '7437465925', '4', '1', '2023-01-16 09:58:13');
INSERT INTO `user_integral_record` VALUES ('10017', '7437465925', '4', '1', '2023-01-16 09:58:54');
INSERT INTO `user_integral_record` VALUES ('10018', '7437465925', '4', '1', '2023-01-16 09:59:37');
INSERT INTO `user_integral_record` VALUES ('10019', '7437465925', '4', '1', '2023-01-16 10:00:19');
INSERT INTO `user_integral_record` VALUES ('10020', '7437465925', '4', '1', '2023-01-16 10:01:14');
INSERT INTO `user_integral_record` VALUES ('10021', '7437465925', '4', '1', '2023-01-16 10:01:43');
INSERT INTO `user_integral_record` VALUES ('10022', '7437465925', '4', '1', '2023-01-16 10:02:53');
INSERT INTO `user_integral_record` VALUES ('10023', '7437465925', '4', '1', '2023-01-16 10:05:02');
INSERT INTO `user_integral_record` VALUES ('10024', '7437465925', '4', '1', '2023-01-16 10:09:28');
INSERT INTO `user_integral_record` VALUES ('10025', '7437465925', '4', '1', '2023-01-16 14:43:28');
INSERT INTO `user_integral_record` VALUES ('10029', '8328764800', '1', '5', '2023-03-30 23:05:12');
INSERT INTO `user_integral_record` VALUES ('10030', '2240403798', '1', '5', '2023-04-05 18:48:14');
INSERT INTO `user_integral_record` VALUES ('10031', '5997415494', '1', '5', '2023-04-05 18:50:26');
INSERT INTO `user_integral_record` VALUES ('10032', '1544156683', '1', '5', '2023-04-05 19:06:12');
INSERT INTO `user_integral_record` VALUES ('10033', '7514349893', '1', '5', '2023-04-14 12:44:38');
INSERT INTO `user_integral_record` VALUES ('10034', '1890524956', '4', '1', '2023-04-14 16:53:44');
INSERT INTO `user_integral_record` VALUES ('10035', '1890524956', '4', '1', '2023-04-14 16:59:47');
INSERT INTO `user_integral_record` VALUES ('10036', '1890524956', '4', '1', '2023-04-14 17:00:43');
INSERT INTO `user_integral_record` VALUES ('10037', '1890524956', '4', '1', '2023-04-14 17:02:22');
INSERT INTO `user_integral_record` VALUES ('10038', '1890524956', '4', '1', '2023-04-14 17:02:56');
INSERT INTO `user_integral_record` VALUES ('10039', '1890524956', '4', '1', '2023-04-14 17:04:03');
INSERT INTO `user_integral_record` VALUES ('10040', '1890524956', '4', '1', '2023-04-14 17:07:41');
INSERT INTO `user_integral_record` VALUES ('10041', '1890524956', '4', '1', '2023-04-14 19:58:17');
INSERT INTO `user_integral_record` VALUES ('10042', '1890524956', '4', '1', '2023-04-15 11:20:16');
INSERT INTO `user_integral_record` VALUES ('10043', '7514349893', '4', '1', '2023-04-15 12:14:27');
INSERT INTO `user_integral_record` VALUES ('10044', '7437465925', '4', '1', '2023-04-15 12:15:10');
INSERT INTO `user_integral_record` VALUES ('10045', '1890524956', '4', '1', '2023-04-15 13:21:02');
INSERT INTO `user_integral_record` VALUES ('10046', '1890524956', '4', '1', '2023-04-15 13:21:20');
INSERT INTO `user_integral_record` VALUES ('10047', '1890524956', '4', '1', '2023-04-15 13:22:24');
INSERT INTO `user_integral_record` VALUES ('10048', '1890524956', '4', '1', '2023-04-15 13:22:39');
INSERT INTO `user_integral_record` VALUES ('10049', '1890524956', '4', '1', '2023-04-15 13:23:23');
INSERT INTO `user_integral_record` VALUES ('10050', '1890524956', '4', '1', '2023-04-15 13:26:28');
INSERT INTO `user_integral_record` VALUES ('10051', '1890524956', '4', '1', '2023-04-15 13:26:43');
INSERT INTO `user_integral_record` VALUES ('10052', '1890524956', '4', '1', '2023-04-15 13:27:26');
INSERT INTO `user_integral_record` VALUES ('10053', '1890524956', '4', '1', '2023-04-15 13:29:02');
INSERT INTO `user_integral_record` VALUES ('10054', '1890524956', '4', '1', '2023-04-15 13:29:20');
INSERT INTO `user_integral_record` VALUES ('10055', '1890524956', '4', '1', '2023-04-15 13:29:28');
INSERT INTO `user_integral_record` VALUES ('10056', '1890524956', '4', '1', '2023-04-15 13:33:47');
INSERT INTO `user_integral_record` VALUES ('10057', '1890524956', '4', '1', '2023-04-15 15:36:36');
INSERT INTO `user_integral_record` VALUES ('10058', '1890524956', '4', '1', '2023-04-15 15:36:48');
INSERT INTO `user_integral_record` VALUES ('10059', '1890524956', '4', '1', '2023-04-15 15:42:39');
INSERT INTO `user_integral_record` VALUES ('10060', '1890524956', '4', '1', '2023-04-15 15:45:18');
INSERT INTO `user_integral_record` VALUES ('10061', '1890524956', '4', '1', '2023-04-15 15:45:25');
INSERT INTO `user_integral_record` VALUES ('10062', '1890524956', '4', '1', '2023-04-15 16:49:38');
INSERT INTO `user_integral_record` VALUES ('10063', '1890524956', '4', '1', '2023-04-15 17:01:45');
INSERT INTO `user_integral_record` VALUES ('10064', '1890524956', '4', '1', '2023-04-15 20:08:36');
INSERT INTO `user_integral_record` VALUES ('10065', '1890524956', '4', '1', '2023-04-15 20:08:47');
INSERT INTO `user_integral_record` VALUES ('10066', '1890524956', '4', '1', '2023-04-15 20:13:51');
INSERT INTO `user_integral_record` VALUES ('10067', '1890524956', '4', '1', '2023-04-15 20:14:06');
INSERT INTO `user_integral_record` VALUES ('10068', '1890524956', '4', '1', '2023-04-15 20:15:32');
INSERT INTO `user_integral_record` VALUES ('10069', '1890524956', '4', '1', '2023-04-15 20:16:03');
INSERT INTO `user_integral_record` VALUES ('10070', '1890524956', '4', '1', '2023-04-15 20:18:07');
INSERT INTO `user_integral_record` VALUES ('10071', '1890524956', '4', '1', '2023-04-15 20:18:16');
INSERT INTO `user_integral_record` VALUES ('10072', '1890524956', '4', '1', '2023-04-15 20:22:12');
INSERT INTO `user_integral_record` VALUES ('10073', '1890524956', '4', '1', '2023-04-15 20:22:23');
INSERT INTO `user_integral_record` VALUES ('10074', '1890524956', '4', '1', '2023-04-15 20:34:34');
INSERT INTO `user_integral_record` VALUES ('10075', '1890524956', '4', '1', '2023-04-15 20:34:43');
INSERT INTO `user_integral_record` VALUES ('10076', '1890524956', '4', '1', '2023-04-15 20:56:52');
INSERT INTO `user_integral_record` VALUES ('10077', '1890524956', '4', '1', '2023-04-15 22:08:40');
INSERT INTO `user_integral_record` VALUES ('10078', '1890524956', '4', '1', '2023-04-15 22:08:58');
INSERT INTO `user_integral_record` VALUES ('10079', '1890524956', '4', '1', '2023-04-15 22:09:31');
INSERT INTO `user_integral_record` VALUES ('10080', '1890524956', '4', '1', '2023-04-15 22:10:31');
INSERT INTO `user_integral_record` VALUES ('10081', '1890524956', '4', '1', '2023-04-15 22:12:47');
INSERT INTO `user_integral_record` VALUES ('10082', '1890524956', '4', '1', '2023-04-15 22:13:00');
INSERT INTO `user_integral_record` VALUES ('10083', '1890524956', '4', '1', '2023-04-15 22:13:58');
INSERT INTO `user_integral_record` VALUES ('10084', '1890524956', '4', '1', '2023-04-15 22:15:31');
INSERT INTO `user_integral_record` VALUES ('10085', '1890524956', '4', '1', '2023-04-15 22:15:38');
INSERT INTO `user_integral_record` VALUES ('10086', '1890524956', '4', '1', '2023-04-15 22:15:53');
INSERT INTO `user_integral_record` VALUES ('10087', '1890524956', '4', '1', '2023-04-15 22:16:18');
INSERT INTO `user_integral_record` VALUES ('10088', '1890524956', '4', '1', '2023-04-15 22:17:05');
INSERT INTO `user_integral_record` VALUES ('10089', '1890524956', '4', '1', '2023-04-15 22:17:45');
INSERT INTO `user_integral_record` VALUES ('10090', '1890524956', '4', '1', '2023-04-15 22:18:46');
INSERT INTO `user_integral_record` VALUES ('10091', '1890524956', '4', '1', '2023-04-15 22:18:54');
INSERT INTO `user_integral_record` VALUES ('10092', '1890524956', '4', '1', '2023-04-15 22:20:04');
INSERT INTO `user_integral_record` VALUES ('10093', '1890524956', '4', '1', '2023-04-17 13:08:18');
INSERT INTO `user_integral_record` VALUES ('10094', '1890524956', '4', '1', '2023-04-17 13:08:21');
INSERT INTO `user_integral_record` VALUES ('10095', '1890524956', '4', '1', '2023-04-17 13:08:28');
INSERT INTO `user_integral_record` VALUES ('10096', '1890524956', '4', '1', '2023-04-17 13:08:34');
INSERT INTO `user_integral_record` VALUES ('10097', '1890524956', '4', '1', '2023-04-17 13:08:41');
INSERT INTO `user_integral_record` VALUES ('10098', '1890524956', '4', '1', '2023-04-18 20:02:44');
INSERT INTO `user_integral_record` VALUES ('10099', '1890524956', '4', '1', '2023-04-18 20:04:50');
INSERT INTO `user_integral_record` VALUES ('10100', '7514349893', '4', '1', '2023-04-18 20:13:55');
INSERT INTO `user_integral_record` VALUES ('10101', '7514349893', '4', '1', '2023-04-18 20:14:57');
INSERT INTO `user_integral_record` VALUES ('10102', '7514349893', '4', '1', '2023-04-18 20:16:25');
INSERT INTO `user_integral_record` VALUES ('10103', '7514349893', '4', '1', '2023-04-18 20:18:29');
INSERT INTO `user_integral_record` VALUES ('10104', '7514349893', '4', '1', '2023-04-18 20:20:24');
INSERT INTO `user_integral_record` VALUES ('10105', '7514349893', '4', '1', '2023-04-18 20:20:31');
INSERT INTO `user_integral_record` VALUES ('10106', '7514349893', '4', '1', '2023-04-18 20:21:49');
INSERT INTO `user_integral_record` VALUES ('10107', '7514349893', '8', '-1', '2023-04-24 20:37:05');
INSERT INTO `user_integral_record` VALUES ('10108', '7514349893', '8', '-1', '2023-04-24 20:38:31');
INSERT INTO `user_integral_record` VALUES ('10109', '7514349893', '8', '-1', '2023-04-24 20:38:32');
INSERT INTO `user_integral_record` VALUES ('10112', '1890524956', '4', '1', '2023-04-24 20:43:41');
INSERT INTO `user_integral_record` VALUES ('10113', '1890524956', '4', '1', '2023-04-24 20:45:11');
INSERT INTO `user_integral_record` VALUES ('10114', '1890524956', '4', '1', '2023-04-24 20:49:24');
INSERT INTO `user_integral_record` VALUES ('10115', '7514349893', '6', '1', '2023-04-25 09:31:44');
INSERT INTO `user_integral_record` VALUES ('10116', '7514349893', '6', '1', '2023-04-25 09:35:11');
INSERT INTO `user_integral_record` VALUES ('10117', '7514349893', '6', '1', '2023-04-25 09:35:52');

-- ----------------------------
-- Table structure for user_message
-- ----------------------------
DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `received_user_id` varchar(15) DEFAULT NULL COMMENT '接收人用户ID',
  `article_id` varchar(15) DEFAULT NULL COMMENT '文章ID',
  `article_title` varchar(150) DEFAULT NULL COMMENT '文章标题',
  `comment_id` int(11) DEFAULT NULL COMMENT '评论ID',
  `send_user_id` varchar(15) DEFAULT NULL COMMENT '发送人用户ID',
  `send_nick_name` varchar(20) DEFAULT NULL COMMENT '发送人昵称',
  `message_type` tinyint(4) DEFAULT NULL COMMENT '0:系统消息 1:评论 2:文章点赞  3:评论点赞 4:附件下载',
  `message_content` varchar(1000) DEFAULT NULL COMMENT '消息内容',
  `status` tinyint(4) DEFAULT NULL COMMENT '1:未读 2:已读',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`message_id`),
  UNIQUE KEY `idx_key` (`article_id`,`comment_id`,`send_user_id`,`message_type`) USING BTREE,
  KEY `idx_received_user_id` (`received_user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_type` (`message_type`)
) ENGINE=InnoDB AUTO_INCREMENT=10115 DEFAULT CHARSET=utf8mb4 COMMENT='用户消息';

-- ----------------------------
-- Records of user_message
-- ----------------------------
INSERT INTO `user_message` VALUES ('10000', '1890524956', null, null, null, null, null, '0', '社区欢迎你，以后的日子里，有老罗陪伴你一起学编程', '2', '2023-01-15 17:45:46');
INSERT INTO `user_message` VALUES ('10001', '7437465925', null, null, null, null, null, '0', '社区欢迎你，以后的日子里，有老罗陪伴你一起学编程', '2', '2023-01-16 09:52:31');
INSERT INTO `user_message` VALUES ('10002', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '0', '7437465925', '测试账号02', '2', null, '2', '2023-01-16 09:53:50');
INSERT INTO `user_message` VALUES ('10003', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10002', '7437465925', '测试账号02', '1', '我来个评论', '2', '2023-01-16 09:54:04');
INSERT INTO `user_message` VALUES ('10004', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10003', '7437465925', '测试账号02', '1', '啦啦啦啦啦啦啦啦啦啦啦啦', '2', '2023-01-16 14:43:28');
INSERT INTO `user_message` VALUES ('10006', '8328764800', null, null, null, null, null, '0', '社区欢迎你，以后的日子里，有老罗陪伴你一起学编程', '1', '2023-03-30 23:05:12');
INSERT INTO `user_message` VALUES ('10007', '2240403798', null, null, null, null, null, '0', '社区欢迎你，以后的日子里，有老罗陪伴你一起学编程', '1', '2023-04-05 18:48:14');
INSERT INTO `user_message` VALUES ('10008', '5997415494', null, null, null, null, null, '0', '社区欢迎你，以后的日子里，有老罗陪伴你一起学编程', '1', '2023-04-05 18:50:26');
INSERT INTO `user_message` VALUES ('10009', '1544156683', null, null, null, null, null, '0', '社区欢迎你，以后的日子里，有老罗陪伴你一起学编程', '1', '2023-04-05 19:06:12');
INSERT INTO `user_message` VALUES ('10032', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '0', '1544156683', '宠', '4', null, '2', '2023-04-10 21:00:39');
INSERT INTO `user_message` VALUES ('10033', '7514349893', null, null, null, null, null, '0', '社区欢迎你，以后的日子里，有老罗陪伴你一起学编程', '2', '2023-04-14 12:44:38');
INSERT INTO `user_message` VALUES ('10034', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10004', '1890524956', '测试账号', '1', null, '2', '2023-04-14 16:53:44');
INSERT INTO `user_message` VALUES ('10035', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10005', '1890524956', '测试账号', '1', null, '2', '2023-04-14 16:59:47');
INSERT INTO `user_message` VALUES ('10036', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10006', '1890524956', '测试账号', '1', null, '2', '2023-04-14 17:00:43');
INSERT INTO `user_message` VALUES ('10037', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10007', '1890524956', '测试账号', '1', null, '2', '2023-04-14 17:02:22');
INSERT INTO `user_message` VALUES ('10038', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10008', '1890524956', '测试账号', '1', null, '2', '2023-04-14 17:02:56');
INSERT INTO `user_message` VALUES ('10039', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10009', '1890524956', '测试账号', '1', null, '2', '2023-04-14 17:04:03');
INSERT INTO `user_message` VALUES ('10040', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10010', '1890524956', '测试账号', '1', null, '2', '2023-04-14 17:07:41');
INSERT INTO `user_message` VALUES ('10041', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10011', '1890524956', '测试账号', '1', null, '2', '2023-04-14 19:58:17');
INSERT INTO `user_message` VALUES ('10042', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10012', '1890524956', '测试账号', '1', null, '2', '2023-04-15 11:20:16');
INSERT INTO `user_message` VALUES ('10043', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10017', '1890524956', '测试账号', '1', null, '2', '2023-04-15 13:22:24');
INSERT INTO `user_message` VALUES ('10044', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10018', '1890524956', '测试账号', '1', null, '2', '2023-04-15 13:22:39');
INSERT INTO `user_message` VALUES ('10045', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10019', '1890524956', '测试账号', '1', null, '2', '2023-04-15 13:23:23');
INSERT INTO `user_message` VALUES ('10046', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10020', '1890524956', '测试账号', '1', null, '2', '2023-04-15 13:26:28');
INSERT INTO `user_message` VALUES ('10047', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10021', '1890524956', '测试账号', '1', null, '2', '2023-04-15 13:26:43');
INSERT INTO `user_message` VALUES ('10048', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10022', '1890524956', '测试账号', '1', null, '2', '2023-04-15 13:27:26');
INSERT INTO `user_message` VALUES ('10049', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10023', '1890524956', '测试账号', '1', null, '2', '2023-04-15 13:29:03');
INSERT INTO `user_message` VALUES ('10050', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10024', '1890524956', '测试账号', '1', null, '2', '2023-04-15 13:29:20');
INSERT INTO `user_message` VALUES ('10051', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10025', '1890524956', '测试账号', '1', null, '2', '2023-04-15 13:29:28');
INSERT INTO `user_message` VALUES ('10052', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10026', '1890524956', '测试账号', '1', null, '2', '2023-04-15 13:33:47');
INSERT INTO `user_message` VALUES ('10053', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10027', '1890524956', '测试账号', '1', null, '2', '2023-04-15 15:36:36');
INSERT INTO `user_message` VALUES ('10054', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10028', '1890524956', '测试账号', '1', null, '2', '2023-04-15 15:36:48');
INSERT INTO `user_message` VALUES ('10055', '1890524956', '10000', '第一个帖子，带图，带附件', '10000', '7514349893', '宠', '3', '自己做沙发', '2', '2023-04-15 15:55:35');
INSERT INTO `user_message` VALUES ('10056', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10032', '1890524956', '测试账号', '1', null, '2', '2023-04-15 16:49:38');
INSERT INTO `user_message` VALUES ('10057', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10033', '1890524956', '测试账号', '1', null, '2', '2023-04-15 17:01:45');
INSERT INTO `user_message` VALUES ('10058', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10034', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:08:36');
INSERT INTO `user_message` VALUES ('10059', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10035', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:08:47');
INSERT INTO `user_message` VALUES ('10060', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10036', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:13:51');
INSERT INTO `user_message` VALUES ('10061', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10037', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:14:06');
INSERT INTO `user_message` VALUES ('10062', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10038', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:15:32');
INSERT INTO `user_message` VALUES ('10063', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10039', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:16:03');
INSERT INTO `user_message` VALUES ('10064', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10040', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:18:07');
INSERT INTO `user_message` VALUES ('10065', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10041', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:18:16');
INSERT INTO `user_message` VALUES ('10066', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10042', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:22:12');
INSERT INTO `user_message` VALUES ('10067', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10043', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:22:23');
INSERT INTO `user_message` VALUES ('10068', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10044', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:34:34');
INSERT INTO `user_message` VALUES ('10069', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10045', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:34:43');
INSERT INTO `user_message` VALUES ('10070', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10046', '1890524956', '测试账号', '1', null, '2', '2023-04-15 20:56:52');
INSERT INTO `user_message` VALUES ('10071', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10047', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:08:40');
INSERT INTO `user_message` VALUES ('10072', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10048', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:08:58');
INSERT INTO `user_message` VALUES ('10073', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10049', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:09:31');
INSERT INTO `user_message` VALUES ('10074', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10050', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:10:31');
INSERT INTO `user_message` VALUES ('10075', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10051', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:12:47');
INSERT INTO `user_message` VALUES ('10076', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10052', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:13:00');
INSERT INTO `user_message` VALUES ('10077', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10053', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:13:58');
INSERT INTO `user_message` VALUES ('10078', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10054', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:15:31');
INSERT INTO `user_message` VALUES ('10079', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10055', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:15:38');
INSERT INTO `user_message` VALUES ('10080', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10056', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:15:53');
INSERT INTO `user_message` VALUES ('10081', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10057', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:16:18');
INSERT INTO `user_message` VALUES ('10082', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10058', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:17:05');
INSERT INTO `user_message` VALUES ('10083', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10059', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:17:45');
INSERT INTO `user_message` VALUES ('10084', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10060', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:18:46');
INSERT INTO `user_message` VALUES ('10085', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10061', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:18:54');
INSERT INTO `user_message` VALUES ('10086', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10062', '1890524956', '测试账号', '1', null, '2', '2023-04-15 22:20:04');
INSERT INTO `user_message` VALUES ('10087', '1890524956', 'ykYQG0hvUYbVoZ8', '测试发帖', '10063', '1890524956', '测试账号', '1', null, '2', '2023-04-17 13:08:18');
INSERT INTO `user_message` VALUES ('10088', '1890524956', 'ykYQG0hvUYbVoZ8', '测试发帖', '10064', '1890524956', '测试账号', '1', null, '2', '2023-04-17 13:08:21');
INSERT INTO `user_message` VALUES ('10089', '1890524956', 'ykYQG0hvUYbVoZ8', '测试发帖', '10065', '1890524956', '测试账号', '1', null, '2', '2023-04-17 13:08:28');
INSERT INTO `user_message` VALUES ('10090', '1890524956', 'ykYQG0hvUYbVoZ8', '测试发帖', '10066', '1890524956', '测试账号', '1', null, '2', '2023-04-17 13:08:34');
INSERT INTO `user_message` VALUES ('10091', '1890524956', 'ykYQG0hvUYbVoZ8', '测试发帖', '10067', '1890524956', '测试账号', '1', null, '2', '2023-04-17 13:08:41');
INSERT INTO `user_message` VALUES ('10092', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10068', '1890524956', '测试账号', '1', null, '2', '2023-04-18 20:03:17');
INSERT INTO `user_message` VALUES ('10093', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '10069', '1890524956', '测试账号', '1', '还是评论', '2', '2023-04-18 20:04:50');
INSERT INTO `user_message` VALUES ('10094', '1890524956', 'ykYQG0hvUYbVoZ8', '测试发帖', '10076', '7514349893', '宠', '1', 'ceshi', '2', '2023-04-18 20:21:49');
INSERT INTO `user_message` VALUES ('10095', '1890524956', 'ykYQG0hvUYbVoZ8', '测试发帖', '0', '7514349893', '宠', '2', null, '2', '2023-04-18 20:22:12');
INSERT INTO `user_message` VALUES ('10096', '1890524956', '10063', '测试发帖', '10063', '7514349893', '宠', '3', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈', '2', '2023-04-18 20:22:16');
INSERT INTO `user_message` VALUES ('10097', '7437465925', '10014', '第一个帖子，带图，带附件', '10014', '7514349893', '宠', '3', '还是测试', '1', '2023-04-18 20:34:29');
INSERT INTO `user_message` VALUES ('10098', '1890524956', '10011', '第一个帖子，带图，带附件', '10011', '7514349893', '宠', '3', '二级bug', '2', '2023-04-18 20:34:42');
INSERT INTO `user_message` VALUES ('10099', '1890524956', '10010', '第一个帖子，带图，带附件', '10010', '7514349893', '宠', '3', '二级bug', '2', '2023-04-18 20:35:43');
INSERT INTO `user_message` VALUES ('10100', '1890524956', '10009', '第一个帖子，带图，带附件', '10009', '7514349893', '宠', '3', '二级bug', '2', '2023-04-18 20:35:49');
INSERT INTO `user_message` VALUES ('10101', '1890524956', '10007', '第一个帖子，带图，带附件', '10007', '7514349893', '宠', '3', '测试二级', '2', '2023-04-18 20:35:51');
INSERT INTO `user_message` VALUES ('10102', '1890524956', '10008', '第一个帖子，带图，带附件', '10008', '7514349893', '宠', '3', '测试二级二级二级', '2', '2023-04-18 20:35:52');
INSERT INTO `user_message` VALUES ('10103', '1890524956', '10066', '测试发帖', '10066', '7514349893', '宠', '3', '发言', '2', '2023-04-18 20:36:22');
INSERT INTO `user_message` VALUES ('10104', '1890524956', '10065', '测试发帖', '10065', '7514349893', '宠', '3', '发言', '2', '2023-04-18 20:36:25');
INSERT INTO `user_message` VALUES ('10105', '1890524956', null, null, null, null, null, '0', '文章【测试频次校验5】被管理员删除', '2', '2023-04-24 16:27:27');
INSERT INTO `user_message` VALUES ('10106', '7514349893', null, null, null, null, null, '0', '评论【ceshi】已被管理员删除', '2', '2023-04-24 20:37:05');
INSERT INTO `user_message` VALUES ('10107', '7514349893', null, null, null, null, null, '0', '评论【测试】已被管理员删除', '2', '2023-04-24 20:38:31');
INSERT INTO `user_message` VALUES ('10108', '7514349893', null, null, null, null, null, '0', '评论【测试】已被管理员删除', '2', '2023-04-24 20:38:32');
INSERT INTO `user_message` VALUES ('10109', '7514349893', null, null, null, '', null, '0', '测试系统消息', '2', '2023-04-25 09:31:44');
INSERT INTO `user_message` VALUES ('10110', '7514349893', null, null, null, '', null, '0', '加积分', '2', '2023-04-25 09:35:11');
INSERT INTO `user_message` VALUES ('10111', '7514349893', null, null, null, '', null, '0', '发送积分', '2', '2023-04-25 09:35:52');
INSERT INTO `user_message` VALUES ('10112', '7514349893', null, null, null, '', null, '0', '系统消息', '2', '2023-04-25 11:04:44');
INSERT INTO `user_message` VALUES ('10113', '7514349893', null, null, null, null, null, '0', '测试系统消息', '2', '2023-04-25 11:16:51');
INSERT INTO `user_message` VALUES ('10114', '1890524956', 'RtiXj832TFL4nhW', '第一个帖子，带图，带附件', '0', '7514349893', '宠', '2', null, '2', '2023-04-26 10:48:06');
