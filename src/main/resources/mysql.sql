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
  `email` varchar(128) COMMENT '用户邮箱',
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
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `title` varchar(512) NOT NULL COMMENT '影片标题',
  `tags` varchar(512) COMMENT '标签',
  `externalLink` varchar(512) COMMENT '资源外部链接',
  `internalLink` varchar(512) COMMENT '资源内部链接',
  `filePath` varchar(256) COMMENT '文件本地路径',
  `createTime` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

show tables;
