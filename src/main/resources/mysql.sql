/*mysql建表文件*/
/*管理员表*/
DROP TABLE IF EXISTS `yellow_admin_user`;
CREATE TABLE `yellow_admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userName` varchar(128) NOT NULL COMMENT '用户名',
  `passWord` varchar(128) NOT NULL COMMENT '密码(md5加密过后的)',
  `createTime` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*用户表*/
DROP TABLE IF EXISTS `yellow_user`;
CREATE TABLE `yellow_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userName` varchar(128) NOT NULL COMMENT '用户名',
  `passWord` varchar(128) NOT NULL COMMENT '密码(md5加密过后的)',
  `nickName` varchar(128) COMMENT '用户昵称',
  `email` varchar(128) COMMENT '用户邮箱',
  `createTime` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=100001 DEFAULT CHARSET=utf8;

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
