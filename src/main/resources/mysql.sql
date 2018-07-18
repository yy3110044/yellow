/*mysql建表文件*/
/*管理员表*/
DROP TABLE IF EXISTS `yellow_admin_user`;
CREATE TABLE `yellow_admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userName` varchar(128) NOT NULL COMMENT '用户名',
  `passWord` varchar(128) NOT NULL COMMENT '密码(md5加密过后的)',
  `lastLoginIp` varchar(128) COMMENT '最后登陆IP',
  `lastLoginTime` datetime COMMENT '最后登陆时间',
  `createTime` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*管理员登陆日志*/
DROP TABLE IF EXISTS `yellow_admin_user_login_log`;
CREATE TABLE `yellow_admin_user_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `adminUserId` int(11),
  `adminUserName` varchar(128),
  `loginIp` varchar(128),
  `loginTime` datetime,
  `userAgent` varchar(512),
  `createTime` datetime,
  PRIMARY KEY (`id`),
  KEY `adminUserId` (`adminUserId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*用户表*/
DROP TABLE IF EXISTS `yellow_user`;
CREATE TABLE `yellow_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userName` varchar(128) NOT NULL COMMENT '用户名',
  `passWord` varchar(128) NOT NULL COMMENT '密码(md5加密过后的)',
  `nickName` varchar(128) COMMENT '用户昵称',
  `phone` varchar(128) COMMENT '手机号码',
  `email` varchar(128) COMMENT '用户邮箱',
  `level` tinyint(11) COMMENT '用户vip级别，默认为0',
  `lastLoginIp` varchar(128) COMMENT '最后登陆IP',
  `lastLoginTime` datetime COMMENT '最后登陆时间',
  `lastLoginType` varchar(64) COMMENT '最后登陆类型：web、app',
  `createTime` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=100001 DEFAULT CHARSET=utf8;

/*用户登陆日志*/
DROP TABLE IF EXISTS `yellow_user_login_log`;
CREATE TABLE `yellow_user_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` int(11),
  `userName` varchar(128),
  `loginIp` varchar(128),
  `loginTime` datetime,
  `loginType` varchar(64),
  `userAgent` varchar(512),
  `createTime` datetime,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*影片表*/
DROP TABLE IF EXISTS `yellow_movie`;
CREATE TABLE `yellow_movie` (
  `id` char(64) NOT NULL COMMENT '主键id，UUID',
  `title` varchar(512) NOT NULL COMMENT '影片标题',
  `tags` varchar(512) COMMENT '标签',
  `imgUrl` varchar(512) COMMENT '缩略图链接',
  `externalLink` varchar(512) COMMENT '资源外部链接',
  `downloadStatus` enum('未下载', '准备中', '下载中', '已完成', '已取消') COMMENT '下载状态',
  `internalLink` varchar(512) COMMENT '资源内部链接',
  `filePath` varchar(256) COMMENT '文件本地路径',
  `createTime` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*观影日志表*/
DROP TABLE IF EXISTS `yellow_movie_watch_record`;
CREATE TABLE `yellow_movie_watch_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` int(11) COMMENT '用户id',
  `ip` varchar(128) COMMENT 'IP',
  `movieId` varchar(64) COMMENT '观看的movieID',
  `lastWatchTime` datetime COMMENT '最后一次观看时间',
  `createTime` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `ip` (`ip`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*用户级别权限表*/
DROP TABLE IF EXISTS `yellow_user_level_permission`;
CREATE TABLE `yellow_user_level_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `level` tinyint(11) NOT NULL COMMENT '级别，对应user表的level，多了一个 -1 代表未登陆的游客用户',
  `watchMovieCount` int(11) NOT NULL COMMENT '此级别每日能够观影的数量',
  `createTime` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `level` (`level`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*网站访问记录*/
DROP TABLE IF EXISTS `yellow_visit_log`;
CREATE TABLE `yellow_visit_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` int(11) COMMENT '用户id',
  `ip` varchar(128) COMMENT '访问ip',
  `userAgent` varchar(512) COMMENT 'userAgent',
  `requestUrl` varchar(512) COMMENT '访问的接口url',
  `params` varchar(512) COMMENT '参数',
  `createTime` datetime COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `ip` (`ip`),
  KEY `createTime` (`createTime`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

show tables;
