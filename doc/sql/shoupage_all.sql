/*
SQLyog Professional v12.08 (64 bit)
MySQL - 5.6.11 : Database - leoman_shoupage
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`leoman_shoupage` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `leoman_shoupage`;

/*Table structure for table `tb_dealflow` */

DROP TABLE IF EXISTS `tb_dealflow`;

CREATE TABLE `tb_dealflow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dno` varchar(32) NOT NULL COMMENT '订单编号',
  `group_id` int(11) NOT NULL COMMENT '所属组织',
  `type` int(2) NOT NULL DEFAULT '0' COMMENT '交易类型 0:加入费 1:续费 2:报名费',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `amount` double(10,2) DEFAULT '0.00' COMMENT '金额',
  `status` int(2) DEFAULT '0' COMMENT '交易状态 0:成功 1:失败',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易流水表';

/*Data for the table `tb_dealflow` */

/*Table structure for table `tb_feekback` */

DROP TABLE IF EXISTS `tb_feekback`;

CREATE TABLE `tb_feekback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rno` varchar(32) NOT NULL COMMENT '时间ID',
  `user_id` int(11) NOT NULL COMMENT '反馈人ID',
  `content` varchar(500) NOT NULL COMMENT '反馈内容',
  `reply` varchar(500) DEFAULT '' COMMENT '回复',
  `delegate` int(11) DEFAULT '0' COMMENT '委派人',
  `admin_id` int(11) NOT NULL COMMENT '处理人ID',
  `status` int(2) DEFAULT '0' COMMENT '处理状态 0:未处理 1:已解决 2:忽略 3:屏蔽',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意见反馈表';

/*Data for the table `tb_feekback` */

/*Table structure for table `tb_group` */

DROP TABLE IF EXISTS `tb_group`;

CREATE TABLE `tb_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '组织名称',
  `avater` varchar(200) DEFAULT '' COMMENT '头像',
  `type` int(2) DEFAULT '0' COMMENT '组织类型ID',
  `province` int(11) DEFAULT '0' COMMENT '省份ID',
  `city` int(11) DEFAULT '0' COMMENT '城市ID',
  `targat` varchar(100) DEFAULT '' COMMENT '关键字,多个关键字用"|"分割',
  `activity_count` int(11) DEFAULT '0' COMMENT '活动举办数量',
  `member_count` int(11) DEFAULT '0' COMMENT '会员数',
  `admin_count` int(11) DEFAULT '0' COMMENT '干事数量',
  `status` int(2) DEFAULT '0' COMMENT '状态 0:正常 1:封号',
  `title1` varchar(50) DEFAULT NULL COMMENT '标签1',
  `content1` longtext COMMENT '内容1',
  `is_open1` int(2) DEFAULT '1' COMMENT '是否公开 1:公开 2:不公开',
  `title2` varchar(50) DEFAULT NULL COMMENT '标签2',
  `content2` longtext COMMENT '内容2',
  `is_open2` int(2) DEFAULT '1' COMMENT '是否公开 1:公开 2:不公开',
  `title3` varchar(50) DEFAULT NULL COMMENT '标签3',
  `content3` longtext COMMENT '内容3',
  `is_open3` int(2) DEFAULT '1' COMMENT '是否公开 1:公开 2:不公开',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='组织表';

/*Data for the table `tb_group` */

insert  into `tb_group`(`id`,`name`,`avater`,`type`,`province`,`city`,`targat`,`activity_count`,`member_count`,`admin_count`,`status`,`title1`,`content1`,`is_open1`,`title2`,`content2`,`is_open2`,`title3`,`content3`,`is_open3`,`create_time`) values (9,'武汉六脉神掌科技有限公司',NULL,3,1,1,'app|开发',0,0,0,0,'基本介绍','基本介绍内容',1,NULL,'组织简介内容',1,'领域行业','领域行业内容',1,1477471163595),(15,'试一哈',NULL,3,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1477645105215);

/*Table structure for table `tb_group_approval` */

DROP TABLE IF EXISTS `tb_group_approval`;

CREATE TABLE `tb_group_approval` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` int(2) DEFAULT '0' COMMENT '状态 0:未处理 1:批准 2:拒绝 3:拉黑 4:同意加入',
  `paied` int(2) DEFAULT '1' COMMENT '是否支付 1:未支付 2:已支付',
  `validate_info` varchar(100) DEFAULT '' COMMENT '验证信息',
  `through_time` bigint(20) DEFAULT NULL COMMENT '批准通过的时间',
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `tb_group_approval_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `tb_group` (`id`),
  CONSTRAINT `tb_group_approval_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tb_user_login` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_group_approval` */

insert  into `tb_group_approval`(`id`,`group_id`,`user_id`,`status`,`paied`,`validate_info`,`through_time`,`create_time`) values (2,9,5,0,1,'申请加入',NULL,1477556612211);

/*Table structure for table `tb_group_bank` */

DROP TABLE IF EXISTS `tb_group_bank`;

CREATE TABLE `tb_group_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL COMMENT '组织ID',
  `bank_user_name` varchar(50) DEFAULT '' COMMENT '银行开户名',
  `bank_accout` varchar(50) DEFAULT '' COMMENT '银行账号',
  `bank_id` int(11) DEFAULT '0' COMMENT '银行',
  `bank_name` varchar(50) DEFAULT '' COMMENT '银行名字',
  `province` int(11) DEFAULT '0' COMMENT '省份ID',
  `city` int(11) DEFAULT '0' COMMENT '城市ID',
  `branch_name` varchar(50) DEFAULT '' COMMENT '开户行支行名称',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `tb_group_bank_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `tb_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='组织银行表';

/*Data for the table `tb_group_bank` */

insert  into `tb_group_bank`(`id`,`group_id`,`bank_user_name`,`bank_accout`,`bank_id`,`bank_name`,`province`,`city`,`branch_name`,`create_time`) values (6,9,'张三','6225 6655 6665 8888',1,'中国工商银行',1,1,NULL,1477471163630),(7,15,'坑钱银行','6222023202026007954',1,NULL,1,1,'坑钱银行大大大',1477645105314);

/*Table structure for table `tb_group_extend` */

DROP TABLE IF EXISTS `tb_group_extend`;

CREATE TABLE `tb_group_extend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL COMMENT '组织ID',
  `idcard` varchar(32) DEFAULT NULL COMMENT '组织编号',
  `password` varchar(50) DEFAULT '' COMMENT '组织密码',
  `code` varchar(50) DEFAULT '' COMMENT '组织代码证号',
  `code_image` varchar(200) DEFAULT '' COMMENT '组织代码证号扫描件',
  `license` varchar(100) DEFAULT '' COMMENT '营业执照注册号',
  `license_image` varchar(200) DEFAULT '' COMMENT '营业执照扫描件',
  `artificial_person_name` varchar(50) DEFAULT '' COMMENT '运营者身份证姓名',
  `artificial_person_idcard` varchar(50) DEFAULT '' COMMENT '运营者身份证号码',
  `artificial_person_mobile` varchar(50) DEFAULT '' COMMENT '运营者电话',
  `cost` int(11) DEFAULT '0' COMMENT '入会费',
  `freeze_time` bigint(20) DEFAULT NULL COMMENT '封号时长 正常则为-1',
  `why1` varchar(500) DEFAULT '' COMMENT '封号原因, 正常为空',
  `approval_admin_id` int(11) DEFAULT NULL COMMENT '审核人ID',
  `create_admin_id` int(11) DEFAULT NULL COMMENT '创建者ID',
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `tb_group_extend_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `tb_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='组织扩展信息表';

/*Data for the table `tb_group_extend` */

insert  into `tb_group_extend`(`id`,`group_id`,`idcard`,`password`,`code`,`code_image`,`license`,`license_image`,`artificial_person_name`,`artificial_person_idcard`,`artificial_person_mobile`,`cost`,`freeze_time`,`why1`,`approval_admin_id`,`create_admin_id`) values (21,9,'7d1e22c893084f818b5204ce12edfa9a','123456','ZUZHI20161026',NULL,'350500400032802-2/3',NULL,'张三','420822198909166666','13476107753',120,-1,NULL,1,22),(22,15,'9515b19d6b374992a97d3a96a94a1061','123','wqe123',NULL,'asd123',NULL,'叶松','420106198602280053','18625412365',0,-1,NULL,NULL,NULL);

/*Table structure for table `tb_message` */

DROP TABLE IF EXISTS `tb_message`;

CREATE TABLE `tb_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(2) NOT NULL DEFAULT '0' COMMENT '类型 0:普通消息 1.事件消息',
  `from_id` int(11) NOT NULL DEFAULT '0' COMMENT '消息发送者',
  `from_type` int(2) DEFAULT '0' COMMENT '消息发送者类型，0:系统 1:个人 2:组织',
  `to_id` int(11) NOT NULL DEFAULT '0' COMMENT '消息接收者',
  `to_type` int(2) DEFAULT '1' COMMENT '消息接收者类型，1:个人 2:组织',
  `title` varchar(50) DEFAULT '' COMMENT '标题/标签',
  `content` varchar(500) DEFAULT '' COMMENT '通知内容',
  `replaces` varchar(500) DEFAULT '' COMMENT '内容替换内容',
  `action1` varchar(100) DEFAULT '' COMMENT '事件1，格式: action|/path1/method1',
  `action2` varchar(100) DEFAULT '' COMMENT '事件2，格式: action2|/path2/method2',
  `action3` varchar(100) DEFAULT '' COMMENT '事件3，格式: action2|/path2/method2',
  `action4` varchar(100) DEFAULT '' COMMENT '事件4，格式: action2|/path2/method2',
  `action5` varchar(100) DEFAULT '' COMMENT '事件5，格式: action2|/path2/method2',
  `send_time` bigint(20) DEFAULT '-1' COMMENT '定时发送时间',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';

/*Data for the table `tb_message` */

/*Table structure for table `tb_order` */

DROP TABLE IF EXISTS `tb_order`;

CREATE TABLE `tb_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `order_no` varchar(40) NOT NULL COMMENT '订单号',
  `amount` double(11,2) DEFAULT '0.00' COMMENT '金额',
  `status` int(2) DEFAULT '1' COMMENT '状态 1:成功 2:失败',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_order` */

/*Table structure for table `tb_report` */

DROP TABLE IF EXISTS `tb_report`;

CREATE TABLE `tb_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rno` varchar(32) NOT NULL COMMENT '时间ID',
  `type` int(2) NOT NULL COMMENT '类型',
  `object_id` int(11) NOT NULL COMMENT '对象ID',
  `object_name` varchar(50) DEFAULT '' COMMENT '对象名称',
  `why1` varchar(50) DEFAULT '' COMMENT '举报原因',
  `status` int(2) DEFAULT '0' COMMENT '处理状态 1:忽略 2:冻结 3:封号 4:暂停 5:停刊',
  `admin_id` int(11) NOT NULL COMMENT '处理人ID',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='举报表';

/*Data for the table `tb_report` */

/*Table structure for table `tb_tfw_tzgg` */

DROP TABLE IF EXISTS `tb_tfw_tzgg`;

CREATE TABLE `tb_tfw_tzgg` (
  `F_IT_XL` int(11) NOT NULL AUTO_INCREMENT,
  `F_VC_BT` varchar(255) DEFAULT NULL,
  `F_IT_LX` int(11) DEFAULT NULL,
  `F_TX_NR` text,
  `F_DT_FBSJ` datetime DEFAULT NULL,
  `F_DT_CJSJ` datetime DEFAULT NULL,
  `F_IT_CJR` int(11) DEFAULT NULL,
  `F_IT_TP` int(11) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`F_IT_XL`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_tfw_tzgg` */

insert  into `tb_tfw_tzgg`(`F_IT_XL`,`F_VC_BT`,`F_IT_LX`,`F_TX_NR`,`F_DT_FBSJ`,`F_DT_CJSJ`,`F_IT_CJR`,`F_IT_TP`,`VERSION`) values (1,'testdd12222233333331',10,'d12121211','2016-06-09 12:00:00',NULL,NULL,NULL,1);

/*Table structure for table `tb_user_approval` */

DROP TABLE IF EXISTS `tb_user_approval`;

CREATE TABLE `tb_user_approval` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_user_id` int(11) NOT NULL COMMENT '申请用户ID',
  `to_user_id` int(11) DEFAULT NULL COMMENT '接收用户ID',
  `introduce_user_id` int(11) NOT NULL COMMENT '引荐人ID',
  `group_id` int(11) NOT NULL COMMENT '组织ID',
  `validate_info` varchar(200) NOT NULL COMMENT '验证消息',
  `type` int(2) DEFAULT '1' COMMENT '1:结识 2:熟人',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0:未处理 1:已批准 2:已忽略3:已拉黑',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `admin_id` int(11) DEFAULT '0' COMMENT '操作人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审批表';

/*Data for the table `tb_user_approval` */

/*Table structure for table `tb_user_friend` */

DROP TABLE IF EXISTS `tb_user_friend`;

CREATE TABLE `tb_user_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `friend_id` int(11) NOT NULL COMMENT '好友id',
  `type` int(2) NOT NULL DEFAULT '0' COMMENT '关系类型 0:结识 1:熟人',
  `status` int(2) DEFAULT '0' COMMENT '状态 0:正常 1:删除 2:黑名单',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组织关系表';

/*Data for the table `tb_user_friend` */

/*Table structure for table `tb_user_group` */

DROP TABLE IF EXISTS `tb_user_group`;

CREATE TABLE `tb_user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `group_id` int(11) NOT NULL COMMENT '组织id',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户组织关系表';

/*Data for the table `tb_user_group` */

/*Table structure for table `tb_user_info` */

DROP TABLE IF EXISTS `tb_user_info`;

CREATE TABLE `tb_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID，关联到user_login表中的ID',
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `mobile` varchar(15) NOT NULL DEFAULT '' COMMENT '手机号',
  `gender` int(2) NOT NULL DEFAULT '1' COMMENT '性别 1:男 2:女',
  `age` varchar(10) DEFAULT '1' COMMENT '年龄段',
  `birthday` bigint(20) DEFAULT '0' COMMENT '出生年月',
  `age_interval_id` int(11) DEFAULT '0' COMMENT '年龄区间段ID',
  `province_id` int(11) DEFAULT '0' COMMENT '省份ID',
  `city_id` int(11) DEFAULT '0' COMMENT '城市ID',
  `province_city` varchar(20) DEFAULT '' COMMENT '省份城市名字',
  `school` varchar(50) DEFAULT '' COMMENT '学校名字，多个名字之间用“|”分割',
  `career` varchar(10) DEFAULT '' COMMENT '事业状态,多个状态用"|"分割',
  `domain` varchar(50) DEFAULT '' COMMENT '行业领域 多个领域用“|”分割',
  `professional` varchar(50) DEFAULT '' COMMENT '专业 多个专业用“|”分割',
  `product_type` int(2) DEFAULT '0' COMMENT '产品服务类型 1:产品 2:服务',
  `product_service_name` varchar(50) DEFAULT '' COMMENT '产品服务名字',
  `org_type` int(2) DEFAULT NULL COMMENT '单位类型 1:公司 2:学校 3:团体 4：其他',
  `organization` varchar(50) DEFAULT '' COMMENT '单位名称',
  `zy` varchar(50) DEFAULT '' COMMENT '专业',
  `sc` varchar(50) DEFAULT '' COMMENT '擅长',
  `zl` varchar(50) DEFAULT '' COMMENT '资历',
  `zy2` varchar(50) DEFAULT '' COMMENT '资源',
  `org_is_open` int(2) DEFAULT NULL COMMENT '单位是否公开 1:是 2:否',
  `key_word` varchar(200) DEFAULT NULL COMMENT '个人关键字，多个状态用"|"分割',
  `groups` varchar(50) DEFAULT '' COMMENT '分组 多个分组用“|”分割',
  `targat` varchar(50) DEFAULT '' COMMENT '标记 多个标记用“|”分割',
  `vip_end_time` bigint(20) DEFAULT '-1' COMMENT '会员到期 -1:永久，其他:会员到期时间',
  `vip_type` int(2) DEFAULT '1' COMMENT '会员类型1:普通会员 2:干事',
  `join_type` int(2) DEFAULT '1' COMMENT '1:主动加入2:邀请加入3:',
  `desc` longtext COMMENT '用户描述',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户详细信息表';

/*Data for the table `tb_user_info` */

insert  into `tb_user_info`(`id`,`user_id`,`username`,`mobile`,`gender`,`age`,`birthday`,`age_interval_id`,`province_id`,`city_id`,`province_city`,`school`,`career`,`domain`,`professional`,`product_type`,`product_service_name`,`org_type`,`organization`,`zy`,`sc`,`zl`,`zy2`,`org_is_open`,`key_word`,`groups`,`targat`,`vip_end_time`,`vip_type`,`join_type`,`desc`,`create_time`) values (4,5,'yesong','13476107753',1,'18',0,46,1,1,'湖北-武汉','武汉大学|清华大学','66,67','1,104,74,IT互联网|1,105,74,金融','1,124,118,软件开发|1,125,118,金融',1,'产品服务名称',NULL,'武汉六脉神掌','软件','Java','一般','供应商上下游',1,'科技|Java',NULL,NULL,NULL,1,NULL,NULL,NULL);

/*Table structure for table `tb_user_login` */

DROP TABLE IF EXISTS `tb_user_login`;

CREATE TABLE `tb_user_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_username` varchar(50) NOT NULL DEFAULT '' COMMENT '登录名',
  `password` varchar(50) NOT NULL DEFAULT '',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '账号状态 0:正常 1:封号 2:冻结',
  `unlock_time` bigint(20) DEFAULT '-1' COMMENT '解冻时间，默认为-1',
  `last_login_ip` varchar(25) DEFAULT '' COMMENT '最后登录IP',
  `last_login_time` bigint(20) DEFAULT NULL COMMENT '最后登录时间',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户登录信息表';

/*Data for the table `tb_user_login` */

insert  into `tb_user_login`(`id`,`login_username`,`password`,`status`,`unlock_time`,`last_login_ip`,`last_login_time`,`create_time`) values (5,'13476107753','',0,-1,'4.16.1.158',1477386487542,NULL);

/*Table structure for table `tb_userinfo_career` */

DROP TABLE IF EXISTS `tb_userinfo_career`;

CREATE TABLE `tb_userinfo_career` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '关联用户ID',
  `career_id` int(11) DEFAULT NULL COMMENT '事业状态ID',
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_userinfo_career` */

/*Table structure for table `tb_userinfo_domain` */

DROP TABLE IF EXISTS `tb_userinfo_domain`;

CREATE TABLE `tb_userinfo_domain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '关联用户ID',
  `domain_id` int(11) DEFAULT NULL COMMENT '行业ID',
  `p_id` int(11) DEFAULT NULL COMMENT '行业父ID，主要用于选择“其他”时，绑定父级',
  `name` varchar(20) DEFAULT NULL COMMENT '行业名字',
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='用户领域关联表';

/*Data for the table `tb_userinfo_domain` */

insert  into `tb_userinfo_domain`(`id`,`user_id`,`domain_id`,`p_id`,`name`,`create_time`) values (5,1,104,74,'IT互联网',NULL),(6,1,105,74,'金融',NULL);

/*Table structure for table `tb_userinfo_professional` */

DROP TABLE IF EXISTS `tb_userinfo_professional`;

CREATE TABLE `tb_userinfo_professional` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '关联用户ID',
  `pro_id` int(11) DEFAULT NULL COMMENT '职业ID',
  `pro_name` varchar(50) DEFAULT NULL COMMENT '职业名称',
  `p_id` int(11) DEFAULT NULL COMMENT '行业父ID，主要用于选择“其他”时，绑定父级',
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='用户职业（专业）关联表';

/*Data for the table `tb_userinfo_professional` */

insert  into `tb_userinfo_professional`(`id`,`user_id`,`pro_id`,`pro_name`,`p_id`,`create_time`) values (5,1,124,'软件开发',118,NULL),(6,1,125,'金融',118,NULL);

/*Table structure for table `tfw_attach` */

DROP TABLE IF EXISTS `tfw_attach`;

CREATE TABLE `tfw_attach` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `URL` text,
  `STATUS` int(11) DEFAULT NULL,
  `CREATER` int(11) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8;

/*Data for the table `tfw_attach` */

insert  into `tfw_attach`(`ID`,`CODE`,`NAME`,`URL`,`STATUS`,`CREATER`,`CREATETIME`) values (290,NULL,'Desert.jpg','/upload/20160222/20160222085146_506.jpg',1,1,'2015-08-02 08:51:46'),(291,NULL,'Chrysanthemum.jpg','/upload\\20160319\\1458372187438.jpg',1,1,'2016-09-19 15:23:07'),(292,NULL,'11.png','/upload\\20160415\\1460705916912.png',1,1,'2016-10-12 15:38:36'),(293,NULL,'11.png','/upload\\20160415\\1460706087306.png',1,1,'2016-10-15 15:41:27'),(294,NULL,'14ce36d3d539b600ac7ff8a1ea50352ac65cb777.jpg','/upload\\20160612\\1465711014299.jpg',2,1,'2016-10-20 13:56:54');

/*Table structure for table `tfw_dept` */

DROP TABLE IF EXISTS `tfw_dept`;

CREATE TABLE `tfw_dept` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NUM` int(11) DEFAULT NULL,
  `PID` int(11) DEFAULT NULL,
  `SIMPLENAME` varchar(45) DEFAULT NULL,
  `FULLNAME` varchar(255) DEFAULT NULL,
  `TIPS` varchar(255) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `tfw_dept` */

insert  into `tfw_dept`(`ID`,`NUM`,`PID`,`SIMPLENAME`,`FULLNAME`,`TIPS`,`VERSION`) values (1,0,0,'Tonbu','Tonbu',NULL,1),(2,1,1,'江苏同步','江苏同步信息技术有限公司',NULL,1),(3,1,2,'技服部','技术服务部',NULL,1),(4,2,2,'客服部','客户服务部',NULL,1),(5,3,2,'销售部','销售部',NULL,1),(6,4,2,'综合管理部','综合管理部',NULL,1),(7,2,1,'常州同步','常州同步软件技术有限公司',NULL,1),(8,1,7,'产品部','产品部',NULL,1),(9,2,7,'研发部','研发部',NULL,1),(10,3,7,'项目部','项目部',NULL,1),(11,4,7,'运维部','运维部',NULL,1),(12,5,7,'销售部','销售部',NULL,1),(13,6,7,'行政部','行政部',NULL,1);

/*Table structure for table `tfw_dict` */

DROP TABLE IF EXISTS `tfw_dict`;

CREATE TABLE `tfw_dict` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `NUM` int(11) DEFAULT NULL,
  `PID` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `TIPS` varchar(255) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8;

/*Data for the table `tfw_dict` */

insert  into `tfw_dict`(`ID`,`CODE`,`NUM`,`PID`,`NAME`,`TIPS`,`VERSION`) values (1,'101',0,0,'性别',NULL,0),(2,'101',1,1,'男',NULL,1),(3,'101',2,1,'女',NULL,0),(5,'901',0,0,'账号状态',NULL,0),(6,'901',1,5,'启用',NULL,0),(7,'901',2,5,'冻结',NULL,0),(8,'901',3,5,'待审核',NULL,0),(9,'901',4,5,'审核拒绝',NULL,0),(10,'901',5,5,'已删除',NULL,0),(11,'902',0,0,'状态',NULL,0),(12,'902',1,11,'启用',NULL,0),(13,'902',2,11,'禁用',NULL,0),(14,'102',0,0,'公告类型',NULL,0),(15,'102',10,14,'通知公告',NULL,0),(16,'102',9,14,'发布计划',NULL,0),(17,'903',0,0,'审核状态',NULL,0),(18,'903',1,17,'待审核',NULL,0),(19,'903',2,17,'审核拒绝',NULL,0),(20,'903',3,17,'审核通过',NULL,0),(41,'102',6,16,'测试',NULL,0),(44,'102',1,14,'发布测试',NULL,0),(45,'102',2,16,'测试222',NULL,1),(46,'904',0,0,'年龄段','年龄段',0),(47,'904',1,46,'18+','',NULL),(48,'904',2,46,'20+','',NULL),(49,'904',3,46,'25+','',NULL),(50,'904',4,46,'30+','',NULL),(51,'904',5,46,'35+','',NULL),(52,'904',6,46,'40+','',NULL),(53,'904',7,46,'45+','',NULL),(54,'904',8,46,'50+','',NULL),(55,'904',9,46,'55+','',NULL),(56,'904',10,46,'60+','',NULL),(58,'904',11,46,'65+','',NULL),(59,'904',12,46,'70+','',NULL),(60,'904',13,46,'75+','',NULL),(61,'904',14,46,'80+','',NULL),(62,'904',15,46,'85+','',NULL),(63,'904',16,46,'90+','',NULL),(64,'904',17,46,'95+','',NULL),(65,'904',18,46,'100+','',NULL),(66,'905',0,0,'事业状态','事业状态',NULL),(67,'905',1,66,'退休/半退休','',NULL),(68,'905',2,66,'阶段性成功','',NULL),(69,'905',3,66,'积极筹划','',NULL),(70,'905',4,66,'修炼绝技','',NULL),(71,'905',5,66,'埋头苦干','',NULL),(72,'905',6,66,'创业开拓','',NULL),(73,'906',0,0,'行业领域','行业领域',NULL),(74,'906',1,73,'信息业','',NULL),(75,'906',2,73,'金融业','',NULL),(76,'906',3,73,'服务业','',NULL),(77,'906',4,73,'工业','',NULL),(78,'906',5,73,'农业','',NULL),(79,'906',6,73,'政府','',NULL),(80,'906',7,73,'军队','',NULL),(81,'906',8,73,'学生','',NULL),(82,'907',0,0,'单位类型','单位类型',NULL),(83,'907',1,82,'公司','',NULL),(84,'907',2,82,'学校','',NULL),(85,'907',3,82,'团体','',NULL),(86,'907',4,82,'机构','',NULL),(87,'907',5,82,'政府','',NULL),(88,'907',6,82,'军队','',NULL),(89,'907',7,82,'医院','',NULL),(90,'908',0,0,'组织类型','组织类型',NULL),(91,'908',1,90,'行业协会','',NULL),(92,'908',2,90,'校友会','',NULL),(93,'908',3,90,'兴趣组织','',NULL),(94,'908',4,90,'公益机构','',NULL),(95,'908',5,90,'文化沙龙','',NULL),(96,'909',0,0,'举报类型','举报类型',NULL),(97,'909',1,96,'广告','',NULL),(98,'909',2,96,'色情','',NULL),(99,'909',3,96,'政治敏感','政治敏感',NULL),(100,'909',4,96,'欺诈','欺诈',NULL),(101,'909',5,96,'身份作假','身份作假',NULL),(102,'909',6,96,'违法','其他',NULL),(103,'909',7,96,'其他','其他',NULL),(104,'906',9,74,'IT互联网','IT互联网',NULL),(105,'906',10,74,'软件','软件',NULL),(106,'906',11,74,'电子','电子',NULL),(107,'906',12,74,'通讯','通讯',NULL),(108,'906',13,75,'银行','银行',NULL),(109,'906',14,75,'证券','证券',NULL),(110,'906',15,75,'保险','保险',NULL),(111,'906',16,76,'运输业','运输业',NULL),(112,'906',17,76,'邮电通讯业','邮电通讯业',NULL),(113,'906',18,76,'餐饮业','餐饮业',NULL),(114,'906',19,77,'钢铁','钢铁',NULL),(115,'906',20,77,'有色冶金','有色冶金',NULL),(116,'906',21,77,'石油加工','石油加工',NULL),(117,'910',0,0,'职业','职业',NULL),(118,'910',1,117,'技术类','技术类',NULL),(119,'910',2,117,'管理类','管理类',NULL),(120,'910',3,117,'销售类','销售类',NULL),(121,'910',4,117,'事物类','事物类',NULL),(122,'910',5,117,'艺术类','艺术类',NULL),(123,'910',6,117,'研究类','研究类',NULL),(124,'910',7,118,'软件开发','软件开发',NULL),(125,'910',8,118,'设备装配','设备装配',NULL),(126,'910',9,118,'设备操作','设备操作',NULL),(127,'910',10,118,'硬件设计','硬件设计',NULL),(128,'910',11,119,'工商管理','工商管理',NULL),(129,'910',12,119,'财务管理','财务管理',NULL),(130,'910',13,119,'人力资源','人力资源',NULL),(131,'910',14,120,'快消品','快消品',NULL),(132,'910',15,120,'医药代理','医药代理',NULL),(133,'910',16,120,'房地产销售','房地产销售',NULL),(134,'910',17,121,'行政办公','行政办公',NULL),(135,'910',18,121,'文员','文员',NULL),(136,'910',19,121,'资料管理','资料管理',NULL),(137,'910',20,122,'舞蹈','舞蹈',NULL),(138,'910',21,122,'美术','美术',NULL),(139,'910',22,122,'体育','体育',NULL),(140,'910',23,123,'数学','数学',NULL),(141,'910',24,123,'物理','物理',NULL),(142,'910',25,123,'化学','化学',NULL),(143,'910',26,123,'力学','力学',NULL),(144,'906',22,78,'人工林','人工林',NULL),(145,'906',23,78,'农业水利','农业水利',NULL),(146,'906',24,78,'海洋捕捞','海洋捕捞',NULL),(147,'906',25,79,'军事','军事',NULL),(148,'906',26,79,'政治','政治',NULL),(149,'906',27,79,'人文社科','人文社科',NULL),(150,'906',28,80,'核工业','核工业',NULL),(151,'906',29,80,'航空工业','航空工业',NULL),(152,'906',30,80,'航天工业','航天工业',NULL),(153,'906',31,80,'船舶工业','船舶工业',NULL),(154,'906',32,80,'兵器工业','兵器工业',NULL),(155,'906',33,81,'大学生','大学生',NULL),(156,'906',34,81,'研究生','研究生',NULL),(157,'906',35,81,'高中生','高中生',NULL);

/*Table structure for table `tfw_generate` */

DROP TABLE IF EXISTS `tfw_generate`;

CREATE TABLE `tfw_generate` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  `REALPATH` varchar(255) DEFAULT NULL,
  `PACKAGENAME` varchar(255) DEFAULT NULL,
  `MODELNAME` varchar(255) DEFAULT NULL,
  `TABLENAME` varchar(255) DEFAULT NULL,
  `PKNAME` varchar(255) DEFAULT NULL,
  `TIPS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `tfw_generate` */

insert  into `tfw_generate`(`ID`,`NAME`,`REALPATH`,`PACKAGENAME`,`MODELNAME`,`TABLENAME`,`PKNAME`,`TIPS`) values (1,'测试','E:\\Workspaces\\git\\bladepro','com.smallchill.test','Blog','tb_tfw_tzgg','f_it_xl',NULL),(2,'用户登录','E:\\Workspaces\\git\\bladepro','com.smallchill.test','UserLogin','tb_user_login','id',NULL),(3,'用户详细模块','E:\\Workspaces\\git\\bladepro','com.smallchill.test','UserInfo','tb_user_info','id',NULL),(4,'用户-领域关系模块','E:\\Workspaces\\git\\bladepro','userdomain','UserDomain','tb_userinfo_domain','id',NULL),(9,'用户-专业关系模块','E:\\Workspaces\\git\\bladepro','userprofessional','userprofessional','tb_userinfo_professional','ID',NULL),(10,'组织基础信息','E:\\Workspaces\\git\\bladepro','group','Group','tb_group','id',NULL),(11,'组织银行信息表','E:\\Workspaces\\git\\bladepro','groupbank','GroupBank','tb_group_bank','id',NULL),(12,'组织页签','E:\\Workspaces\\git\\bladepro','grouptag','GroupTag','tb_group_extend','id',NULL),(13,'加入组织审核','E:\\Workspaces\\git\\bladepro','groupapproval','GroupApproval','tb_group_approval','id',NULL),(14,'消息','E:\\Workspaces\\git\\bladepro','message','Message','tb_message','id',NULL),(15,'组织-会员','E:\\Workspaces\\git\\bladepro','usergroup','UserGourp','tb_user_group','id',NULL),(16,'用户申请审核','E:\\Workspaces\\git\\bladepro','usraapproval','UserApproval','tb_user_approval','id',NULL);

/*Table structure for table `tfw_group` */

DROP TABLE IF EXISTS `tfw_group`;

CREATE TABLE `tfw_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groups` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8 COMMENT='测试用\r\n\r\n角色分组';

/*Data for the table `tfw_group` */

insert  into `tfw_group`(`id`,`groups`) values (148,'胆大妄为'),(159,'订单'),(164,'七味都气丸'),(180,'得得得得'),(181,'大大大'),(184,'请求'),(186,'dd ');

/*Table structure for table `tfw_login_log` */

DROP TABLE IF EXISTS `tfw_login_log`;

CREATE TABLE `tfw_login_log` (
  `ID` int(65) NOT NULL AUTO_INCREMENT,
  `LOGNAME` varchar(255) DEFAULT NULL,
  `USERID` varchar(255) DEFAULT NULL,
  `CLASSNAME` varchar(255) DEFAULT NULL,
  `METHOD` text,
  `CREATETIME` datetime DEFAULT NULL,
  `SUCCEED` varchar(255) DEFAULT NULL,
  `MESSAGE` text,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

/*Data for the table `tfw_login_log` */

insert  into `tfw_login_log`(`ID`,`LOGNAME`,`USERID`,`CLASSNAME`,`METHOD`,`CREATETIME`,`SUCCEED`,`MESSAGE`) values (69,'12','2',NULL,'12','2016-02-01 00:00:00','0','12');

/*Table structure for table `tfw_menu` */

DROP TABLE IF EXISTS `tfw_menu`;

CREATE TABLE `tfw_menu` (
  `ID` int(65) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `PCODE` varchar(255) DEFAULT NULL,
  `ALIAS` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ICON` varchar(255) DEFAULT NULL,
  `URL` varchar(255) DEFAULT NULL,
  `NUM` int(65) DEFAULT NULL,
  `LEVELS` int(65) DEFAULT NULL,
  `SOURCE` text,
  `PATH` varchar(255) DEFAULT NULL,
  `TIPS` varchar(255) DEFAULT NULL,
  `STATUS` int(65) DEFAULT NULL,
  `ISOPEN` varchar(255) DEFAULT NULL,
  `ISTEMPLATE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8;

/*Data for the table `tfw_menu` */

insert  into `tfw_menu`(`ID`,`CODE`,`PCODE`,`ALIAS`,`NAME`,`ICON`,`URL`,`NUM`,`LEVELS`,`SOURCE`,`PATH`,`TIPS`,`STATUS`,`ISOPEN`,`ISTEMPLATE`,`VERSION`) values (1,'system','0',NULL,'系统管理','fa-cog',NULL,9,1,NULL,NULL,NULL,1,'1','0',3),(2,'role','system',NULL,'角色管理','fa-key','/role/',2,2,NULL,NULL,NULL,1,'0',NULL,1),(3,'role_add','role','addex','角色新增','btn btn-xs btn-white | fa fa-floppy-o bigger-120','/role/add',1,3,NULL,'role_add.html','800*340',1,'0',NULL,2),(4,'role_edit','role','edit','修改','btn btn-xs btn-white | fa fa-pencil  bigger-120','/role/edit',2,3,NULL,'role_edit.html','800*340',1,'0','0',1),(5,'role_remove','role','remove','删除','btn btn-xs btn-white | fa fa-times  bigger-120','/role/remove',3,3,NULL,NULL,NULL,1,'0',NULL,1),(6,'role_view','role','view','查看','btn btn-xs btn-white | fa fa-eye bigger-120','/role/view',4,3,NULL,'role_view.html','800*340',1,NULL,NULL,1),(7,'role_authority','role','authority','权限配置','btn btn-xs btn-white | fa fa-wrench  bigger-120','/role/authority',5,3,NULL,'role_authority.html','350*500',1,'0',NULL,2),(8,'user','system',NULL,'用户管理','fa-user','/user/',1,2,NULL,NULL,NULL,1,NULL,NULL,0),(9,'user_add','user','add','新增','btn btn-xs btn-white | fa fa-floppy-o bigger-120','/user/add',1,3,NULL,'user_add.html','800*430',1,NULL,NULL,0),(10,'user_edit','user','edit','修改','btn btn-xs btn-white | fa fa-pencil  bigger-120','/user/edit',2,3,NULL,'user_edit.html','800*430',1,NULL,NULL,0),(11,'user_del','user','remove','删除','btn btn-xs btn-white | fa fa fa-times bigger-120','/user/del',3,3,NULL,NULL,NULL,1,NULL,NULL,0),(12,'user_view','user','view','查看','btn btn-xs btn-white | fa fa-eye bigger-120','/user/view',4,3,NULL,'user_view.html','800*390',1,NULL,NULL,0),(13,'user_audit','user','audit','审核','btn btn-xs btn-white | fa fa-user  bigger-120','{\"status\":\"3\"}',5,3,NULL,NULL,NULL,1,NULL,NULL,0),(14,'user_audit_ok','user_audit','ok','通过','btn btn-xs btn-white | fa fa-check  bigger-120','/user/auditOk',1,4,NULL,NULL,NULL,1,NULL,NULL,0),(15,'user_audit_refuse','user_audit','refuse','拒绝','btn btn-xs btn-white | fa fa-times  bigger-120','/user/auditRefuse',2,4,NULL,NULL,NULL,1,NULL,NULL,0),(16,'user_audit_back','user_audit','back','返回','btn btn-xs btn-white | fa fa-undo  bigger-120',NULL,3,4,NULL,NULL,NULL,1,NULL,NULL,0),(17,'user_reset','user','reset','重置密码','btn btn-xs btn-white | fa fa-key  bigger-120','/user/reset',6,3,NULL,NULL,NULL,1,NULL,NULL,0),(18,'user_ban','user','frozen','冻结','btn btn-xs btn-white | fa fa-ban  bigger-120','/user/ban',7,3,NULL,NULL,NULL,1,NULL,NULL,0),(19,'user_recycle','user','recycle','回收站','btn btn-xs btn-white | fa fa-recycle  bigger-120','{\"status\":\"5\"}',8,3,NULL,NULL,NULL,1,NULL,NULL,0),(20,'user_recycle_restore','user_recycle','restore','还原','btn btn-xs btn-white | fa fa-refresh  bigger-120','/user/restore',1,4,NULL,NULL,NULL,1,NULL,NULL,0),(21,'user_recycle_remove','user_recycle','remove','彻底删除','btn btn-xs btn-white  btn-danger | fa fa fa-times bigger-120','/user/remove',2,4,NULL,NULL,NULL,1,NULL,NULL,0),(22,'user_recycle_back','user_recycle','back','返回','btn btn-xs btn-white | fa fa-undo  bigger-120',NULL,3,4,NULL,NULL,NULL,1,NULL,NULL,0),(23,'user_roleAssign','user','assign','角色分配','btn btn-xs btn-white | fa fa-users bigger-120','/user/roleAssign',9,3,NULL,'user_roleAssign.html','350*500',1,NULL,NULL,0),(24,'user_extrole','user','agent','权限代理','btn btn-xs btn-white | fa fa-wrench  bigger-120','/user/extrole',10,3,NULL,'user_extrole.html',NULL,1,NULL,NULL,0),(25,'menu','system',NULL,'菜单管理','fa-tasks','/menu/',3,2,NULL,NULL,NULL,1,NULL,NULL,0),(26,'menu_add','menu','addex','菜单新增','btn btn-xs btn-white | fa fa-floppy-o bigger-120','/menu/add',1,3,NULL,'menu_add.html','800*430',1,'0','0',1),(27,'menu_edit','menu','edit','修改','btn btn-xs btn-white | fa fa-pencil  bigger-120','/menu/edit',2,3,NULL,'menu_edit.html','800*430',1,'0','0',1),(28,'menu_del','menu','remove','删除','btn btn-xs btn-white | fa fa-times  bigger-120','/menu/del',3,3,NULL,NULL,NULL,1,'0',NULL,1),(29,'menu_view','menu','view','查看','btn btn-xs btn-white | fa fa-eye bigger-120','/menu/view',4,3,NULL,'menu_view.html','800*430',1,'0','0',1),(30,'menu_recycle','menu','recycle','回收站','btn btn-xs btn-white | fa fa-recycle  bigger-120','{\"status\":\"2\"}',5,3,NULL,NULL,NULL,1,NULL,NULL,0),(31,'menu_recycle_restore','menu_recycle','restore','还原','btn btn-xs btn-white | fa fa-refresh  bigger-120','/menu/restore',1,4,NULL,NULL,NULL,1,NULL,NULL,0),(32,'menu_recycle_remove','menu_recycle','remove','彻底删除','btn btn-xs btn-white  btn-danger | fa fa fa-times bigger-120','/menu/remove',2,4,NULL,NULL,NULL,1,'0',NULL,1),(33,'menu_recycle_back','menu_recycle','back','返回','btn btn-xs btn-white | fa fa-undo  bigger-120',NULL,3,4,NULL,NULL,NULL,1,NULL,NULL,0),(34,'dict','system',NULL,'字典管理','fa fa-book','/dict/',4,2,NULL,NULL,NULL,1,NULL,NULL,0),(35,'dict_add','dict','addex','字典新增','btn btn-xs btn-white | fa fa-floppy-o bigger-120','/dict/add',1,3,NULL,'dict_add.html','800*390',1,NULL,NULL,0),(36,'dict_edit','dict','edit','修改','btn btn-xs btn-white | fa fa-pencil  bigger-120','/dict/edit',2,3,NULL,'dict_edit.html','800*390',1,NULL,NULL,0),(37,'dict_remove','dict','remove','删除','btn btn-xs btn-white | fa fa-times  bigger-120','/dict/remove',3,3,NULL,NULL,NULL,1,NULL,NULL,0),(38,'dict_view','dict','view','查看','btn btn-xs btn-white | fa fa-eye bigger-120','/dict/view',4,3,NULL,'dict_view.html','800*390',1,NULL,NULL,0),(39,'dept','system',NULL,'部门管理','fa fa-users','/dept/',5,2,NULL,NULL,NULL,1,NULL,NULL,0),(40,'dept_add','dept','addex','部门新增','btn btn-xs btn-white | fa fa-floppy-o bigger-120','/dept/add',1,3,NULL,'dept_add.html','800*340',1,NULL,NULL,0),(41,'dept_edit','dept','edit','修改','btn btn-xs btn-white | fa fa-pencil  bigger-120','/dept/edit',2,3,NULL,'dept_edit.html','800*340',1,NULL,NULL,0),(42,'dept_remove','dept','remove','删除','btn btn-xs btn-white | fa fa-times  bigger-120','/dept/remove',3,3,NULL,NULL,NULL,1,NULL,NULL,0),(43,'dept_view','dept','view','查看','btn btn-xs btn-white | fa fa-eye bigger-120','/dept/view',4,3,NULL,'dept_view.html','800*340',1,'0','0',0),(44,'attach','system',NULL,'附件管理','fa fa-paperclip','/attach/',6,2,NULL,'attach.html',NULL,1,'0','0',0),(45,'attach_add','attach','add','新增','btn btn-xs btn-white | fa fa-floppy-o bigger-120','/attach/add',1,3,NULL,'attach_add.html','800*450',1,'0','0',0),(46,'attach_edit','attach','edit','修改','btn btn-xs btn-white | fa fa-pencil  bigger-120','/attach/edit',2,3,NULL,'attach_edit.html','800*290',1,'0',NULL,0),(47,'attach_remove','attach','remove','删除','btn btn-xs btn-white | fa fa-times  bigger-120','/attach/remove',3,3,NULL,NULL,NULL,1,NULL,NULL,0),(48,'attach_view','attach','view','查看','btn btn-xs btn-white | fa fa-eye bigger-120','/attach/view',4,3,NULL,'attach_view.html','800*450',1,'0','0',1),(49,'attach_download','attach','download','下载','btn btn-xs btn-white | fa fa-paperclip bigger-120','/attach/download',5,3,NULL,NULL,NULL,1,NULL,NULL,0),(56,'parameter','system',NULL,'参数化管理','fa-tags','/parameter/',9,2,NULL,'parameter.html',NULL,1,'0','1',0),(57,'parameter_add','parameter','add','新增','btn btn-xs btn-white | fa fa-floppy-o bigger-120','/parameter/add',1,3,NULL,'parameter_add.html',NULL,1,'0','0',0),(58,'parameter_edit','parameter','edit','修改','btn btn-xs btn-white | fa fa-pencil  bigger-120','/parameter/edit',2,3,NULL,'parameter_edit.html',NULL,1,'0','0',0),(59,'parameter_del','parameter','remove','删除','btn btn-xs btn-white | fa fa-times  bigger-120','/parameter/del',3,3,NULL,NULL,NULL,1,'0','0',1),(60,'parameter_view','parameter','view','查看','btn btn-xs btn-white | fa fa-eye bigger-120','/parameter/view',4,3,NULL,'parameter_view.html',NULL,1,'0','0',0),(61,'parameter_recycle','parameter','recycle','回收站','btn btn-xs btn-white | fa fa-recycle  bigger-120','{\"status\":\"5\"}',5,3,NULL,'parameter_recycle.html',NULL,1,'0','0',0),(62,'parameter_recycle_restore','parameter_recycle','restore','还原','btn btn-xs btn-white | fa fa-refresh  bigger-120','/parameter/restore',1,4,NULL,NULL,NULL,1,'0','0',0),(63,'parameter_recycle_remove','parameter_recycle','remove','彻底删除','btn btn-xs btn-white  btn-danger | fa fa fa-times bigger-120','/parameter/remove',2,4,NULL,NULL,NULL,1,'0','0',1),(64,'parameter_recycle_back','parameter_recycle','back','返回','btn btn-xs btn-white | fa fa-undo  bigger-120',NULL,3,4,NULL,NULL,NULL,1,'0','0',0),(65,'druid','system',NULL,'连接池监视','fa-arrows-v','/druid',10,2,NULL,NULL,NULL,1,'0',NULL,1),(81,'log','system',NULL,'日志管理','fa-tasks',NULL,11,2,NULL,NULL,NULL,1,'0','0',1),(82,'olog','log',NULL,'操作日志','fa-database','/olog/',1,3,NULL,'olog.html',NULL,1,'0','0',0),(83,'llog','log',NULL,'登录日志','fa-sign-in','/llog/',2,3,NULL,'llog.html',NULL,1,'0','1',0),(84,'olog_add','olog','add','新增','btn btn-xs btn-white | fa fa-floppy-o bigger-120','/olog/add',1,4,NULL,'olog_add.html',NULL,1,'0','0',0),(85,'olog_edit','olog','edit','修改','btn btn-xs btn-white | fa fa-pencil  bigger-120','/olog/edit',2,4,NULL,'olog_edit.html',NULL,1,'0','0',0),(86,'olog_remove','olog','remove','删除','btn btn-xs btn-white | fa fa-times  bigger-120','/olog/remove',3,4,NULL,NULL,NULL,1,'0','0',0),(87,'olog_view','olog','view','查看','btn btn-xs btn-white | fa fa-eye bigger-120','/olog/view',4,4,NULL,'olog_view.html',NULL,1,'0','0',0),(88,'llog_add','llog','add','新增','btn btn-xs btn-white | fa fa-floppy-o bigger-120','/llog/add',1,4,NULL,'llog_add.html',NULL,1,'0','0',0),(89,'llog_edit','llog','edit','修改','btn btn-xs btn-white | fa fa-pencil  bigger-120','/llog/edit',2,4,NULL,'llog_edit.html',NULL,1,'0','0',0),(90,'llog_remove','llog','remove','删除','btn btn-xs btn-white | fa fa-times  bigger-120','/llog/remove',3,4,NULL,NULL,NULL,1,'0','0',0),(91,'llog_view','llog','view','查看','btn btn-xs btn-white | fa fa-eye bigger-120','/llog/view',4,4,NULL,'llog_view.html',NULL,1,'0','0',0),(92,'office','0',NULL,'工作台','fa fa-desktop',NULL,1,1,NULL,NULL,NULL,1,'0','0',0),(93,'notice','office',NULL,'通知公告','fa fa-bell','/notice/',1,2,NULL,NULL,NULL,1,'0','0',0),(94,'notice_add','notice','add','新增','btn btn-xs btn-white | fa fa-floppy-o bigger-120','/notice/add',1,3,NULL,NULL,'800*500',1,'0','0',1),(95,'notice_edit','notice','edit','修改','btn btn-xs btn-white | fa fa-pencil  bigger-120','/notice/edit',2,3,NULL,NULL,'800*500',1,'0','0',0),(96,'notice_remove','notice','remove','删除','btn btn-xs btn-white | fa fa-times  bigger-120','/notice/remove',3,3,NULL,NULL,NULL,1,'0','0',0),(97,'notice_view','notice','view','查看','btn btn-xs btn-white | fa fa-eye bigger-120','/notice/view',4,3,NULL,NULL,'800*500',1,'0','0',0),(98,'online','system','','在线开发','fa-rocket',NULL,12,2,NULL,NULL,'800*520',1,'0',NULL,1),(99,'generate','online',NULL,'代码生成','fa-gavel','/generate/',1,3,NULL,NULL,'800*520',1,'0',NULL,1),(100,'generate_add','generate','add','新增','btn btn-xs btn-white | fa fa-floppy-o bigger-120','/generate/add',1,4,NULL,NULL,'800*420',1,'0',NULL,3),(101,'generate_edit','generate','edit','修改','btn btn-xs btn-white | fa fa-pencil  bigger-120','/generate/edit',2,4,NULL,NULL,'800*420',1,'0',NULL,3),(102,'generate_remove','generate','remove','删除','btn btn-xs btn-white | fa fa-times  bigger-120','/generate/remove',3,4,NULL,NULL,'800*520',1,'0',NULL,NULL),(103,'generate_view','generate','view','查看','btn btn-xs btn-white | fa fa-eye bigger-120','/generate/view',4,4,NULL,NULL,'800*420',1,'0',NULL,3),(104,'generate_gencode','generate','gencode','代码生成','btn btn-xs btn-white | fa fa-gavel bigger-120','/generate/gencode',5,4,NULL,NULL,'800*520',1,'0',NULL,1),(105,'role_demo','role','demo','分组','btn btn-xs btn-white|fa fa-floppy-o bigger-120','/role/demo',6,3,NULL,NULL,'350*500',1,'0',NULL,4),(107,'group','system','','组织管理','fa-key','/group/',2,2,NULL,NULL,'',1,'0',NULL,1),(108,'group_add','group','add','新增','btn btn-xs btn-white | fa fa-floppy-o bigger-120','/group/add',1,3,NULL,NULL,'1000*800',1,'0',NULL,2);

/*Table structure for table `tfw_operation_log` */

DROP TABLE IF EXISTS `tfw_operation_log`;

CREATE TABLE `tfw_operation_log` (
  `ID` int(65) NOT NULL AUTO_INCREMENT,
  `LOGNAME` varchar(255) DEFAULT NULL,
  `USERID` varchar(255) DEFAULT NULL,
  `CLASSNAME` varchar(255) DEFAULT NULL,
  `METHOD` text,
  `CREATETIME` datetime DEFAULT NULL,
  `SUCCEED` varchar(255) DEFAULT NULL,
  `MESSAGE` text,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=589 DEFAULT CHARSET=utf8;

/*Data for the table `tfw_operation_log` */

insert  into `tfw_operation_log`(`ID`,`LOGNAME`,`USERID`,`CLASSNAME`,`METHOD`,`CREATETIME`,`SUCCEED`,`MESSAGE`) values (216,'异常日志','1',NULL,'com.sun.proxy.$Proxy27 cannot be cast to org.springframework.web.multipart.MultipartHttpServletRequest','2016-02-17 16:26:13','0',NULL),(217,'异常日志','1',NULL,'write javaBean error','2016-02-17 16:43:12','0',NULL),(218,'异常日志','1',NULL,'-1','2016-02-17 16:57:21','0',NULL),(219,'异常日志','1',NULL,'\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: ORA-01722: 无效数字\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: insert into tb_tfw_tzgg (f_dt_fbsj,f_vc_bt,f_it_xl) values (?,?,?)\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-01722: 无效数字\n\n; SQL []; ORA-01722: 无效数字\n; nested exception is java.sql.SQLSyntaxErrorException: ORA-01722: 无效数字\n','2016-02-18 10:51:46','0',NULL),(220,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'f_it_xl\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting null for parameter #3 with JdbcType OTHER . Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. Cause: java.sql.SQLException: 无效的列类型: 1111','2016-02-18 10:58:01','0',NULL),(221,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select * from TMSP_ATTACH where id = ?\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00942: 表或视图不存在\n','2016-02-18 15:10:05','0',NULL),(231,'异常日志','1',NULL,'文件不存在!','2016-02-19 08:48:49','0',NULL),(232,'异常日志','1',NULL,'文件不存在!','2016-02-19 08:49:02','0',NULL),(233,'异常日志','1',NULL,'文件不存在!','2016-02-19 08:49:39','0',NULL),(234,'异常日志','1',NULL,'文件不存在!','2016-02-19 08:50:22','0',NULL),(235,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'code\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-19 13:29:00','0',NULL),(236,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'code\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-19 13:29:52','0',NULL),(237,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'code\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-19 13:33:12','0',NULL),(238,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT count(*) FROM tfw_menu m LEFT JOIN (SELECT num, name FROM tfw_dict WHERE code = 902) d ON m.status = d.num WHERE 1 = 1 AND (name LIKE CONCAT(CONCAT(\'%\', ?), \'%\')) AND (code LIKE CONCAT(CONCAT(\'%\', ?), \'%\'))\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n','2016-02-19 13:35:17','0',NULL),(239,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'name\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting null for parameter #1 with JdbcType OTHER . Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. Cause: java.sql.SQLException: 无效的列类型: 1111','2016-02-19 13:36:31','0',NULL),(240,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'name\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting null for parameter #1 with JdbcType OTHER . Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. Cause: java.sql.SQLException: 无效的列类型: 1111','2016-02-19 13:37:11','0',NULL),(241,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'name\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting null for parameter #1 with JdbcType OTHER . Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. Cause: java.sql.SQLException: 无效的列类型: 1111','2016-02-19 13:40:07','0',NULL),(242,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT count(*) FROM tfw_menu m LEFT JOIN (SELECT num, name FROM tfw_dict WHERE code = 902) d ON m.status = d.num WHERE 1 = 1 AND (name LIKE CONCAT(CONCAT(\'%\', ?), \'%\')) AND (code LIKE CONCAT(CONCAT(\'%\', ?), \'%\'))\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n','2016-02-19 13:42:08','0',NULL),(243,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT count(*) FROM tfw_menu m LEFT JOIN (SELECT num, name FROM tfw_dict WHERE code = 902) d ON m.status = d.num WHERE 1 = 1 AND (name LIKE \'%新增%\') AND (code LIKE \'%user%\')\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n','2016-02-19 13:48:24','0',NULL),(244,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT count(*) FROM (SELECT m.*, d.name AS DIC_STATUS FROM tfw_menu m LEFT JOIN (SELECT num, name FROM tfw_dict WHERE code = 902) d ON m.status = d.num WHERE 1 = 1 AND (name LIKE CONCAT(CONCAT(\'%\', ?), \'%\')) AND (code LIKE CONCAT(CONCAT(\'%\', ?), \'%\'))) blade_statement\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n','2016-02-19 13:50:40','0',NULL),(245,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT count(*) FROM (SELECT m.*, d.name AS DIC_STATUS FROM tfw_menu m LEFT JOIN (SELECT num, name FROM tfw_dict WHERE code = 902) d ON m.status = d.num WHERE 1 = 1 AND (name LIKE CONCAT(CONCAT(\'%\', ?), \'%\'))) blade_statement\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00918: 未明确定义列\n','2016-02-19 13:54:35','0',NULL),(246,'异常日志','1',NULL,'Index: 0, Size: 0','2016-02-19 14:00:39','0',NULL),(247,'异常日志','1',NULL,'Index: 0, Size: 0','2016-02-19 14:00:41','0',NULL),(248,'异常日志','1',NULL,'Index: 0, Size: 0','2016-02-19 14:01:13','0',NULL),(249,'异常日志','1',NULL,'Index: 0, Size: 0','2016-02-19 14:04:21','0',NULL),(250,'异常日志','1',NULL,'Index: 0, Size: 0','2016-02-19 14:04:58','0',NULL),(251,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"R\".\"ID\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select * from ( select tmp_page.*, rownum row_id from ( SELECT * FROM (SELECT r.*, d.simpleName DEPTNAME, (SELECT name FROM tfw_role WHERE id = r.pId) PNAME FROM tfw_role r LEFT JOIN tfw_dept d ON r.deptId = d.id) blade_statement WHERE 1 = 1 order by r.id asc ) tmp_page where rownum <= ? ) where row_id > ?\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"R\".\"ID\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"R\".\"ID\": 标识符无效\n','2016-02-19 14:08:19','0',NULL),(252,'异常日志','21',NULL,'java.sql.SQLIntegrityConstraintViolationException: ORA-00001: 违反唯一约束条件 (TFRAMEWORK.SYS_C0041927)\n','2016-02-19 14:10:58','0',NULL),(253,'异常日志','21',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00907: 缺失右括号\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select count(*) from (select * from (select            bg.*,          nr.F_TX_BGNR,          u1.name DIC_F_IT_CJR,          u2.name DIC_F_IT_XGR                from           tb_tfw_gzbg bg           left join tb_tfw_gzbgnr nr on bg.f_it_xl=nr.f_it_bgxl           left join tfw_user u1 on bg.f_it_cjr=u1.id           left join tfw_user u2 on bg.f_it_xgr=u2.id) blade_statement where 1=1  where 1=1 and bg.f_it_cjr = ?) tmp_count\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00907: 缺失右括号\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00907: 缺失右括号\n','2016-02-19 14:14:47','0',NULL),(254,'异常日志','21',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00907: 缺失右括号\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select count(*) from (select * from (select            bg.*,          nr.F_TX_BGNR,          u1.name DIC_F_IT_CJR,          u2.name DIC_F_IT_XGR                from           tb_tfw_gzbg bg           left join tb_tfw_gzbgnr nr on bg.f_it_xl=nr.f_it_bgxl           left join tfw_user u1 on bg.f_it_cjr=u1.id           left join tfw_user u2 on bg.f_it_xgr=u2.id) blade_statement where 1=1  where 1=1 and bg.f_it_cjr = ?) tmp_count\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00907: 缺失右括号\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00907: 缺失右括号\n','2016-02-19 14:15:23','0',NULL),(255,'异常日志','21',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00907: 缺失右括号\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select count(*) from (select * from (select            bg.*,          nr.F_TX_BGNR,          u1.name DIC_F_IT_CJR,          u2.name DIC_F_IT_XGR                from           tb_tfw_gzbg bg           left join tb_tfw_gzbgnr nr on bg.f_it_xl=nr.f_it_bgxl           left join tfw_user u1 on bg.f_it_cjr=u1.id           left join tfw_user u2 on bg.f_it_xgr=u2.id) blade_statement where 1=1 and bg.f_it_cjr = ? where 1=1 ) tmp_count\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00907: 缺失右括号\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00907: 缺失右括号\n','2016-02-19 14:17:19','0',NULL),(256,'异常日志','21',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"BG\".\"F_IT_CJR\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT count(*) FROM (SELECT bg.*, nr.F_TX_BGNR, u1.name DIC_F_IT_CJR, u2.name DIC_F_IT_XGR FROM tb_tfw_gzbg bg LEFT JOIN tb_tfw_gzbgnr nr ON bg.f_it_xl = nr.f_it_bgxl LEFT JOIN tfw_user u1 ON bg.f_it_cjr = u1.id LEFT JOIN tfw_user u2 ON bg.f_it_xgr = u2.id) blade_statement WHERE 1 = 1 AND bg.f_it_cjr = ?\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"BG\".\"F_IT_CJR\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"BG\".\"F_IT_CJR\": 标识符无效\n','2016-02-19 14:18:34','0',NULL),(257,'异常日志','21',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"BG\".\"F_IT_XL\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select * from ( select tmp_page.*, rownum row_id from ( SELECT * FROM (SELECT bg.*, nr.F_TX_BGNR, u1.name DIC_F_IT_CJR, u2.name DIC_F_IT_XGR FROM tb_tfw_gzbg bg LEFT JOIN tb_tfw_gzbgnr nr ON bg.f_it_xl = nr.f_it_bgxl LEFT JOIN tfw_user u1 ON bg.f_it_cjr = u1.id LEFT JOIN tfw_user u2 ON bg.f_it_xgr = u2.id) blade_statement WHERE 1 = 1 AND f_it_cjr = ? order by bg.F_IT_XL desc ) tmp_page where rownum <= ? ) where row_id > ?\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"BG\".\"F_IT_XL\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"BG\".\"F_IT_XL\": 标识符无效\n','2016-02-19 14:20:09','0',NULL),(258,'异常日志','1',NULL,'java.sql.SQLSyntaxErrorException: ORA-00911: 无效字符\n','2016-02-20 14:03:39','0',NULL),(261,'异常日志','1',NULL,'Required String parameter \'type\' is not present','2016-02-23 14:27:19','0',NULL),(262,'异常日志','1',NULL,'Mapped Statements collection does not contain value for 0','2016-02-23 14:27:42','0',NULL),(263,'异常日志','1',NULL,'Mapped Statements collection does not contain value for 0','2016-02-23 14:28:13','0',NULL),(264,'异常日志','1',NULL,'Mapped Statements collection does not contain value for 0','2016-02-23 14:28:33','0',NULL),(265,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLException: 要执行的 SQL 语句不得为空白或空值\r\n### The error may involve SELECT.0\r\n### The error occurred while executing a query\r\n### SQL: \r\n### Cause: java.sql.SQLException: 要执行的 SQL 语句不得为空白或空值\n; uncategorized SQLException for SQL []; SQL state [99999]; error code [17104]; 要执行的 SQL 语句不得为空白或空值; nested exception is java.sql.SQLException: 要执行的 SQL 语句不得为空白或空值','2016-02-23 14:30:08','0',NULL),(266,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLException: 要执行的 SQL 语句不得为空白或空值\r\n### The error may involve SELECT.0\r\n### The error occurred while executing a query\r\n### SQL: \r\n### Cause: java.sql.SQLException: 要执行的 SQL 语句不得为空白或空值\n; uncategorized SQLException for SQL []; SQL state [99999]; error code [17104]; 要执行的 SQL 语句不得为空白或空值; nested exception is java.sql.SQLException: 要执行的 SQL 语句不得为空白或空值','2016-02-23 14:30:35','0',NULL),(267,'异常日志','1',NULL,'Mapped Statements collection does not contain value for ','2016-02-23 15:27:09','0',NULL),(268,'异常日志','1',NULL,'Mapped Statements collection does not contain value for ','2016-02-23 15:27:25','0',NULL),(269,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=diy\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n','2016-02-23 16:10:49','0',NULL),(270,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=diy\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n','2016-02-23 16:10:50','0',NULL),(271,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=diy\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n','2016-02-23 16:13:59','0',NULL),(272,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=diy\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n','2016-02-23 16:14:03','0',NULL),(273,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=diy\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY\": 标识符无效\n','2016-02-23 16:14:06','0',NULL),(274,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=diy_notice\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n','2016-02-23 16:19:43','0',NULL),(275,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=diy_notice\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n','2016-02-23 16:19:44','0',NULL),(276,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=diy_notice\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n','2016-02-23 16:21:50','0',NULL),(277,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=diy_notice\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n','2016-02-23 16:21:52','0',NULL),(278,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=diy_notice\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n','2016-02-23 16:23:14','0',NULL),(279,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=diy_notice\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n','2016-02-23 16:23:33','0',NULL),(280,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=diy_notice\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"DIY_NOTICE\": 标识符无效\n','2016-02-23 16:24:34','0',NULL),(281,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX_equal\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 16:47:44','0',NULL),(282,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX_equal\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 16:48:25','0',NULL),(283,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX_equal\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 16:49:58','0',NULL),(284,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX_equ\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 16:52:15','0',NULL),(285,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX_equal\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 16:55:44','0',NULL),(286,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX_2nd\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 16:57:31','0',NULL),(287,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_VC_BT_2nd\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 16:59:17','0',NULL),(288,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_VC_BT\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #2 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #2 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 16:59:38','0',NULL),(289,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_VC_BT\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #2 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #2 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 17:00:02','0',NULL),(290,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_VC_BT_2nd\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 17:00:20','0',NULL),(291,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_VC_BT_2nd\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 17:02:13','0',NULL),(292,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_VC_BT\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #2 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #2 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 17:03:19','0',NULL),(293,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_VC_BT\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #2 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #2 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 17:03:56','0',NULL),(294,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_VC_BT\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #2 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #2 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 17:06:42','0',NULL),(295,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_VC_BT_2nd\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 17:07:36','0',NULL),(296,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_VC_BT_2nd\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 17:07:50','0',NULL),(297,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX_2nd\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting null for parameter #1 with JdbcType OTHER . Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 17:11:19','0',NULL),(298,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 17:15:33','0',NULL),(299,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX_2nd\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-23 17:15:35','0',NULL),(300,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-24 08:38:32','0',NULL),(301,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX_2nd\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-24 08:38:40','0',NULL),(302,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX_2nd\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-24 08:38:43','0',NULL),(303,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX_2nd\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-24 08:41:40','0',NULL),(304,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX_2nd\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-24 08:41:48','0',NULL),(305,'异常日志','1',NULL,'Expected one result (or null) to be returned by selectOne(), but found: 2','2016-02-24 08:41:55','0',NULL),(306,'异常日志','1',NULL,'nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property=\'F_IT_LX\', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId=\'null\', jdbcTypeName=\'null\', expression=\'null\'}. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: org.apache.ibatis.type.TypeException: Error setting non null for parameter #1 with JdbcType null . Try setting a different JdbcType for this parameter or a different configuration property. Cause: java.sql.SQLException: 无效的列索引','2016-02-24 08:43:14','0',NULL),(307,'异常日志','1',NULL,'Expected one result (or null) to be returned by selectOne(), but found: 2','2016-02-24 08:45:51','0',NULL),(308,'异常日志','1',NULL,'Expected one result (or null) to be returned by selectOne(), but found: 2','2016-02-24 08:47:36','0',NULL),(309,'异常日志','1',NULL,'Expected one result (or null) to be returned by selectOne(), but found: 2','2016-02-24 08:53:09','0',NULL),(310,'异常日志','1',NULL,'Expected one result (or null) to be returned by selectOne(), but found: 2','2016-02-24 08:53:52','0',NULL),(311,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"USERID\": 标识符无效\n\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select * from tfw_user where userid = 1\r\n### Cause: java.sql.SQLSyntaxErrorException: ORA-00904: \"USERID\": 标识符无效\n\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: ORA-00904: \"USERID\": 标识符无效\n','2016-02-24 09:57:54','0',NULL),(312,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Table \'tframework.tfw_user\' doesn\'t exist','2016-02-24 14:08:54','0',NULL),(313,'异常日志','1',NULL,'java.lang.IllegalAccessException: Class org.beetl.sql.core.mapping.BeanProcessor can not access a member of class com.tonbusoft.core.toolbox.Maps with modifiers \"private\"','2016-02-24 16:43:38','0',NULL),(314,'异常日志','1',NULL,'SQL Script Error:>>04:43:48:变量未定义(VAR_NOT_DEFINED):jds 位于1行 资源:auto._gen_select * from tfw_me','2016-02-24 16:43:48','0',NULL),(315,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'\'1\',\'2\',\'3\',\'4\',\'5\',\'6\',\'7\',\'8\',\'9\',\'10\' \nlimit 0 , 5\' at line 1','2016-02-24 16:44:46','0',NULL),(316,'异常日志','1',NULL,'java.sql.SQLSyntaxErrorException: ORA-00907: 缺失右括号\n','2016-02-24 16:55:09','0',NULL),(317,'异常日志','1',NULL,'java.lang.InstantiationException','2016-02-25 13:16:46','0',NULL),(318,'异常日志','1',NULL,'java.lang.InstantiationException','2016-02-25 13:17:33','0',NULL),(319,'异常日志','1',NULL,'java.lang.InstantiationException','2016-02-25 13:18:37','0',NULL),(320,'异常日志','1',NULL,'java.lang.IllegalAccessException: Class org.beetl.sql.core.mapping.BeanProcessor can not access a member of class com.tonbusoft.core.toolbox.Maps with modifiers \"private\"','2016-02-25 13:30:23','0',NULL),(321,'异常日志','1',NULL,'在 /beetlsql/mysql/userMapper.sql 和 /beetlsql/mysql/userMapper.md 和 /beetlsql/userMapper.sql 和 /beetlsql/userMapper.md 和  未找到[id=userMapper.list]相关的SQL','2016-03-19 11:17:04','0',NULL),(322,'异常日志','1',NULL,'在 /beetlsql/mysql/userMapper.sql 和 /beetlsql/mysql/userMapper.md 和 /beetlsql/userMapper.sql 和 /beetlsql/userMapper.md 和  未找到[id=userMapper.list]相关的SQL','2016-03-19 11:23:52','0',NULL),(324,'异常日志','1',NULL,'在 /beetlsql/mysql/noticeMapper.sql 和 /beetlsql/mysql/noticeMapper.md 和 /beetlsql/noticeMapper.sql 和 /beetlsql/noticeMapper.md 和  未找到[id=noticeMapper.list]相关的SQL','2016-03-19 14:43:28','0',NULL),(325,'异常日志','1',NULL,'在 /beetlsql/mysql/noticeMapper.sql 和 /beetlsql/mysql/noticeMapper.md 和 /beetlsql/noticeMapper.sql 和 /beetlsql/noticeMapper.md 和  未找到[id=noticeMapper.list]相关的SQL','2016-03-19 15:23:03','0',NULL),(326,'异常日志','1',NULL,'在 /beetlsql/mysql/reportMapper.sql 和 /beetlsql/mysql/reportMapper.md 和 /beetlsql/reportMapper.sql 和 /beetlsql/reportMapper.md 和  未找到[id=reportMapper.list]相关的SQL','2016-03-21 09:11:45','0',NULL),(327,'异常日志','1',NULL,'在 /beetlsql/mysql/reportMapper.sql 和 /beetlsql/mysql/reportMapper.md 和 /beetlsql/reportMapper.sql 和 /beetlsql/reportMapper.md 和  未找到[id=reportMapper.list]相关的SQL','2016-03-21 09:16:29','0',NULL),(328,'异常日志','1',NULL,'SQL Script Error:>>09:17:12:调用方法抛出了异常(NATIVE_CALL_EXCEPTION):use 位于2行 资源:auto._gen_select * from (selec','2016-03-21 09:17:12','0',NULL),(329,'异常日志','1',NULL,'SQL Script Error:>>09:18:49:调用方法抛出了异常(NATIVE_CALL_EXCEPTION):use 位于2行 资源:auto._gen_select * from (selec','2016-03-21 09:18:49','0',NULL),(330,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'from\r\ntb_tfw_gzbg bg\r\nleft join tb_tfw_gzbgnr nr on bg.f_it_xl=nr.f_it_bgxl\r\nlef\' at line 3','2016-03-21 09:19:11','0',NULL),(331,'异常日志','1',NULL,'SQL Script Error:>>09:19:54:调用方法抛出了异常(NATIVE_CALL_EXCEPTION):use 位于2行 资源:auto._gen_select * from (selec','2016-03-21 09:19:54','0',NULL),(332,'异常日志','1',NULL,'SQL Script Error:>>09:20:01:调用方法抛出了异常(NATIVE_CALL_EXCEPTION):use 位于2行 资源:auto._gen_select * from (selec','2016-03-21 09:20:01','0',NULL),(333,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'from\r\ntb_tfw_gzbg bg\r\nleft join tb_tfw_gzbgnr nr on bg.f_it_xl=nr.f_it_bgxl\r\nlef\' at line 3','2016-03-21 09:20:49','0',NULL),(334,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'from\r\ntb_tfw_gzbg bg\r\nleft join tb_tfw_gzbgnr nr on bg.f_it_xl=nr.f_it_bgxl\r\nlef\' at line 3','2016-03-21 09:21:40','0',NULL),(335,'异常日志','1',NULL,'SQL Script Error:>>09:22:12:调用方法抛出了异常(NATIVE_CALL_EXCEPTION):use 位于2行 资源:auto._gen_select * from (selec','2016-03-21 09:22:12','0',NULL),(336,'异常日志','1',NULL,'SQL Script Error:>>09:22:21:调用方法抛出了异常(NATIVE_CALL_EXCEPTION):use 位于2行 资源:auto._gen_select * from (selec','2016-03-21 09:22:21','0',NULL),(337,'异常日志','1',NULL,'SQL Script Error:>>09:23:25:调用方法抛出了异常(NATIVE_CALL_EXCEPTION):use 位于2行 资源:auto._gen_select * from (selec','2016-03-21 09:23:25','0',NULL),(338,'异常日志','1',NULL,'java.util.ArrayList cannot be cast to java.util.Map','2016-03-21 09:24:49','0',NULL),(339,'异常日志','1',NULL,'在 /beetlsql/mysql/noticeMapper.sql 和 /beetlsql/mysql/noticeMapper.md 和 /beetlsql/noticeMapper.sql 和 /beetlsql/noticeMapper.md 和  未找到[id=noticeMapper.list]相关的SQL','2016-03-21 09:30:26','0',NULL),(340,'异常日志','1',NULL,'在 /beetlsql/mysql/newsMapper.sql 和 /beetlsql/mysql/newsMapper.md 和 /beetlsql/newsMapper.sql 和 /beetlsql/newsMapper.md 和  未找到[id=newsMapper.data]相关的SQL','2016-03-21 09:35:03','0',NULL),(341,'异常日志','1',NULL,'在 /beetlsql/mysql/noticeMapper.sql 和 /beetlsql/mysql/noticeMapper.md 和 /beetlsql/noticeMapper.sql 和 /beetlsql/noticeMapper.md 和  未找到[id=noticeMapper.list]相关的SQL','2016-03-21 10:57:21','0',NULL),(342,'异常日志','1',NULL,'在 /beetlsql/mysql/noticeMapper.sql 和 /beetlsql/mysql/noticeMapper.md 和 /beetlsql/noticeMapper.sql 和 /beetlsql/noticeMapper.md 和  未找到[id=noticeMapper.list]相关的SQL','2016-03-21 10:57:45','0',NULL),(343,'异常日志','1',NULL,'在 /beetlsql/mysql/notice.sql 和 /beetlsql/mysql/notice.md 和 /beetlsql/notice.sql 和 /beetlsql/notice.md 和  未找到[id=notice.list]相关的SQL','2016-03-21 10:58:14','0',NULL),(344,'异常日志','1',NULL,'删除失败！','2016-03-30 13:35:12','0',NULL),(345,'异常日志','1',NULL,'删除失败！','2016-03-30 13:35:58','0',NULL),(346,'异常日志','1',NULL,'未取到ID的值,无法修改!','2016-03-31 08:56:32','0',NULL),(347,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-01 11:23:21','0',NULL),(348,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-01 11:30:07','0',NULL),(349,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-01 12:06:47','0',NULL),(350,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-01 12:08:32','0',NULL),(351,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-01 12:19:34','0',NULL),(352,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-05 08:40:25','0',NULL),(353,'异常日志','1',NULL,'数据库中此数据不存在，可能数据已经被删除，请刷新数据后在操作','2016-04-05 08:40:33','0',NULL),(354,'异常日志','1',NULL,'com.mysql.jdbc.MysqlDataTruncation: Data truncation: Data too long for column \'F_CR_FBZT\' at row 1','2016-04-07 14:08:08','0',NULL),(355,'异常日志','1',NULL,'Inject [height] error!','2016-04-07 16:27:32','0',NULL),(356,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'dept\' in \'where clause\'\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=dept\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'dept\' in \'where clause\'\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'dept\' in \'where clause\'','2016-04-09 10:07:35','0',NULL),(357,'异常日志','1',NULL,'Mapped Statements collection does not contain value for 0','2016-04-09 10:08:46','0',NULL),(358,'异常日志','1',NULL,'Mapped Statements collection does not contain value for 0','2016-04-09 10:09:22','0',NULL),(359,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'dept\' in \'where clause\'\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=dept\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'dept\' in \'where clause\'\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'dept\' in \'where clause\'','2016-04-09 10:11:16','0',NULL),(360,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'dept\' in \'where clause\'\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=dept\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'dept\' in \'where clause\'\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'dept\' in \'where clause\'','2016-04-09 10:11:28','0',NULL),(361,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'dept\' in \'where clause\'\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select NUM as \"num\",ID as \"id\",PID as \"pId\",NAME as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_DICT where code=dept\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'dept\' in \'where clause\'\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'dept\' in \'where clause\'','2016-04-09 10:11:58','0',NULL),(362,'异常日志','1',NULL,'Mapped Statements collection does not contain value for 0','2016-04-09 10:12:36','0',NULL),(363,'异常日志','1',NULL,'Required String parameter \'where\' is not present','2016-04-09 10:37:24','0',NULL),(364,'异常日志','1',NULL,'Required String parameter \'where\' is not present','2016-04-09 10:37:27','0',NULL),(365,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-09 10:37:53','0',NULL),(366,'异常日志','1',NULL,'Required String parameter \'where\' is not present','2016-04-09 10:37:59','0',NULL),(367,'异常日志','1',NULL,'Required String parameter \'where\' is not present','2016-04-09 10:38:04','0',NULL),(368,'异常日志','1',NULL,'数据库中此数据不存在，可能数据已经被删除，请刷新数据后在操作','2016-04-09 10:38:18','0',NULL),(369,'异常日志','1',NULL,'Required String parameter \'where\' is not present','2016-04-09 11:00:13','0',NULL),(370,'异常日志','1',NULL,'Required String parameter \'where\' is not present','2016-04-09 11:30:54','0',NULL),(371,'异常日志','1',NULL,'Required String parameter \'where\' is not present','2016-04-09 11:31:16','0',NULL),(372,'异常日志','1',NULL,'Required String parameter \'where\' is not present','2016-04-09 11:31:34','0',NULL),(373,'异常日志','1',NULL,'Required String parameter \'where\' is not present','2016-04-09 11:31:46','0',NULL),(374,'异常日志','1',NULL,'Required String parameter \'where\' is not present','2016-04-09 13:42:14','0',NULL),(375,'异常日志','1',NULL,'Required String parameter \'where\' is not present','2016-04-09 13:43:06','0',NULL),(376,'异常日志','1',NULL,'Required String parameter \'where\' is not present','2016-04-09 13:43:20','0',NULL),(377,'异常日志','1',NULL,'syntax error, expect {, actual int','2016-04-09 13:46:01','0',NULL),(378,'异常日志','1',NULL,'syntax error, expect {, actual int','2016-04-09 13:46:15','0',NULL),(379,'异常日志','1',NULL,'syntax error, expect {, actual int','2016-04-09 13:46:25','0',NULL),(380,'异常日志','1',NULL,'syntax error, expect {, actual EOF','2016-04-09 13:48:11','0',NULL),(381,'异常日志','1',NULL,'syntax error, expect {, actual EOF','2016-04-09 13:48:26','0',NULL),(382,'异常日志','1',NULL,'删除失败！','2016-04-11 10:11:36','0',NULL),(383,'异常日志','1',NULL,'删除失败！','2016-04-11 10:11:48','0',NULL),(384,'异常日志','1',NULL,'Required String parameter \'inter\' is not present','2016-04-12 09:24:15','0',NULL),(385,'异常日志','1',NULL,'Required String parameter \'inter\' is not present','2016-04-12 09:24:20','0',NULL),(386,'异常日志','1',NULL,'Required String parameter \'inter\' is not present','2016-04-12 09:24:59','0',NULL),(387,'异常日志','1',NULL,'Required String parameter \'inter\' is not present','2016-04-12 09:25:05','0',NULL),(388,'异常日志','1',NULL,'Required String parameter \'inter\' is not present','2016-04-12 09:27:57','0',NULL),(389,'异常日志','1',NULL,'Required String parameter \'inter\' is not present','2016-04-12 09:28:12','0',NULL),(390,'异常日志','1',NULL,'Required String parameter \'intercept\' is not present','2016-04-12 09:30:47','0',NULL),(391,'异常日志','1',NULL,'Instance class [10] error!','2016-04-12 09:32:45','0',NULL),(392,'异常日志','1',NULL,'Instance class [] error!','2016-04-12 09:34:03','0',NULL),(393,'异常日志','1',NULL,'Instance class [] error!','2016-04-12 09:34:36','0',NULL),(394,'异常日志','1',NULL,'Instance class [] error!','2016-04-12 09:35:52','0',NULL),(395,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-12 16:43:40','0',NULL),(396,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-12 16:44:01','0',NULL),(397,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-12 16:44:11','0',NULL),(398,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-12 16:45:01','0',NULL),(399,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-12 16:49:38','0',NULL),(400,'异常日志','1',NULL,'表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑','2016-04-12 16:50:08','0',NULL),(401,'异常日志','1',NULL,'已经有XXXX......','2016-05-07 15:31:01','0',NULL),(402,'异常日志','1',NULL,'Could not find acceptable representation','2016-05-10 09:35:31','0',NULL),(403,'异常日志','1',NULL,'Could not find acceptable representation','2016-05-10 09:37:13','0',NULL),(404,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'\'1\'\' at line 1','2016-05-16 14:24:36','0',NULL),(405,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'where id in (1,2,3)) tmp_count\' at line 1\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select count(*) from (select * from (select r.*,d.simpleName DEPTNAME,(select name from tfw_role where id=r.pId) PNAME from tfw_role r left join tfw_dept d on r.deptId=d.id) blade_statement where 1=1 where id in (1,2,3)) tmp_count\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'where id in (1,2,3)) tmp_count\' at line 1\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'where id in (1,2,3)) tmp_count\' at line 1','2016-06-03 08:45:03','0',NULL),(406,'异常日志','1',NULL,'\r\n### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'where r.id in (1,2,3)) tmp_count\' at line 1\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select count(*) from (select * from (select r.*,d.simpleName DEPTNAME,(select name from tfw_role where id=r.pId) PNAME from tfw_role r left join tfw_dept d on r.deptId=d.id) blade_statement where 1=1 where r.id in (1,2,3)) tmp_count\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'where r.id in (1,2,3)) tmp_count\' at line 1\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'where r.id in (1,2,3)) tmp_count\' at line 1','2016-06-03 08:46:18','0',NULL),(407,'异常日志','21',NULL,'\r\n### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')) tmp_count\' at line 1\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select count(*) from (select * from (select d.*,(select simpleName from tfw_dept  where id=d.pId) PNAME from tfw_dept d) blade_statement where 1=1 and id in (11,)) tmp_count\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')) tmp_count\' at line 1\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')) tmp_count\' at line 1','2016-06-03 09:00:55','0',NULL),(408,'异常日志','21',NULL,'\r\n### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')) tmp_count\' at line 1\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select count(*) from (select * from (select d.*,(select simpleName from tfw_dept  where id=d.pId) PNAME from tfw_dept d) blade_statement where 1=1 and id in (11,)) tmp_count\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')) tmp_count\' at line 1\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')) tmp_count\' at line 1','2016-06-03 09:01:16','0',NULL),(409,'异常日志','21',NULL,'\r\n### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')) tmp_count\' at line 1\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select count(*) from (select * from (select d.*,(select simpleName from tfw_dept  where id=d.pId) PNAME from tfw_dept d) blade_statement where 1=1 and id in (11,)) tmp_count\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')) tmp_count\' at line 1\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')) tmp_count\' at line 1','2016-06-03 09:01:23','0',NULL),(410,'异常日志','21',NULL,'\r\n### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')) tmp_count\' at line 1\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select count(*) from (select * from (select d.*,(select simpleName from tfw_dept  where id=d.pId) PNAME from tfw_dept d) blade_statement where 1=1 and id in (11,)) tmp_count\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')) tmp_count\' at line 1\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \')) tmp_count\' at line 1','2016-06-03 09:02:00','0',NULL),(411,'异常日志','22',NULL,'当前用户无权操作!','2016-06-03 09:34:11','0',NULL),(412,'异常日志','22',NULL,'\r\n### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'and id in (5)\' at line 1\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select id \"id\",pId \"pId\",name as \"name\",(case when (pId=0 or pId is null) then \'true\' else \'false\' end) \"open\" from  TFW_ROLE order by pId,num asc and id in (5)\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'and id in (5)\' at line 1\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'and id in (5)\' at line 1','2016-06-03 09:48:07','0',NULL),(413,'异常日志','1',NULL,'For input string: \"administrator\"','2016-06-03 15:04:25','0',NULL),(414,'异常日志','1',NULL,'For input string: \"administrator\"','2016-06-03 15:05:02','0',NULL),(415,'异常日志','1',NULL,'For input string: \"administrator\"','2016-06-03 15:05:45','0',NULL),(416,'异常日志','1',NULL,'For input string: \"administrator\"','2016-06-03 15:07:23','0',NULL),(417,'异常日志','1',NULL,'table \"tb_yw_blog\" not exist','2016-06-12 16:54:39','0',NULL),(418,'异常日志','1',NULL,'table \"tb_yw_tzgg\" not exist','2016-06-12 16:55:12','0',NULL),(419,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'id\' in \'where clause\'','2016-06-18 09:54:45','0',NULL),(420,'异常日志','1',NULL,'syntax error, position at 0, name PID','2016-10-20 10:26:08','0',NULL),(421,'异常日志','1',NULL,'syntax error, position at 0, name PID','2016-10-20 10:26:10','0',NULL),(422,'异常日志','1',NULL,'syntax error, position at 0, name PID','2016-10-20 10:26:12','0',NULL),(423,'异常日志','1',NULL,'syntax error, position at 0, name PID','2016-10-20 10:26:16','0',NULL),(424,'异常日志','1',NULL,'syntax error, position at 0, name PID','2016-10-20 10:26:56','0',NULL),(425,'异常日志','1',NULL,'syntax error, position at 0, name DEPTID','2016-10-20 10:27:25','0',NULL),(426,'异常日志','1',NULL,'syntax error, position at 0, name DEPTID','2016-10-20 10:27:49','0',NULL),(427,'异常日志','1',NULL,'syntax error, position at 0, name DEPTID','2016-10-20 10:28:54','0',NULL),(428,'异常日志','1',NULL,'not close json text, token : {','2016-10-20 10:29:54','0',NULL),(429,'异常日志','1',NULL,'syntax error, position at 0, name PID','2016-10-20 10:30:01','0',NULL),(430,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22DEPTID%22:%223%22%7B%22PID%22:%222%22%7D','2016-10-20 10:30:44','0',NULL),(431,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22DEPTID%22:%223%22%7B%22PID%22:%224%22%7D','2016-10-20 10:30:47','0',NULL),(432,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7B%22DEPTID%22:%2210%22%7D','2016-10-20 10:30:48','0',NULL),(433,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7D','2016-10-20 10:30:51','0',NULL),(434,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7D','2016-10-20 10:30:55','0',NULL),(435,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22DEPTID%22:%222%22%7D','2016-10-20 10:30:58','0',NULL),(436,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22DEPTID%22:%226%22%7D','2016-10-20 10:30:59','0',NULL),(437,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22DEPTID%22:%2210%22%7D','2016-10-20 10:30:59','0',NULL),(438,'异常日志','1',NULL,'not close json text, token : {','2016-10-20 10:31:30','0',NULL),(439,'异常日志','1',NULL,'syntax error, position at 0, name DEPTID','2016-10-20 10:33:33','0',NULL),(440,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7B%22DEPTID%22:%229%22%7D','2016-10-20 10:35:07','0',NULL),(441,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7B%22DEPTID%22:%2210%22%7D','2016-10-20 10:35:25','0',NULL),(442,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22DEPTID%22:%2210%22%7B%22PID%22:%225%22%7D','2016-10-20 10:35:33','0',NULL),(443,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%226%22%7B%22DEPTID%22:%225%22%7D','2016-10-20 10:38:54','0',NULL),(444,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%225%22%7B%22DEPTID%22:%228%22%7D','2016-10-20 10:39:43','0',NULL),(445,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%222%22%7B%22DEPTID%22:%225%22%7D','2016-10-20 10:41:32','0',NULL),(446,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22PID%22:%224%22,%22DEPTID%22:%2211%22%7D','2016-10-20 10:48:48','0',NULL),(447,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID%22:%2210%22%7D','2016-10-20 10:51:30','0',NULL),(448,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7D','2016-10-20 10:51:34','0',NULL),(449,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID%22:%229%22%7D','2016-10-20 10:51:36','0',NULL),(450,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID%22:%224%22%7D','2016-10-20 10:51:37','0',NULL),(451,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22DEPTID%22:%224%22%7D','2016-10-20 10:51:40','0',NULL),(452,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7D','2016-10-20 10:51:46','0',NULL),(453,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%226%22%7D','2016-10-20 10:51:48','0',NULL),(454,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%222%22%7D','2016-10-20 10:51:56','0',NULL),(455,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID%22:%224%22%7D','2016-10-20 10:53:23','0',NULL),(456,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID%22:%2210%22%7D','2016-10-20 10:53:27','0',NULL),(457,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7D','2016-10-20 10:53:30','0',NULL),(458,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%225%22%7D','2016-10-20 10:53:36','0',NULL),(459,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7D','2016-10-20 10:53:37','0',NULL),(460,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID%22:%2210%22%7D','2016-10-20 10:53:47','0',NULL),(461,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID%22:%2210%22%7D','2016-10-20 10:56:08','0',NULL),(462,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID%22:%224%22%7D','2016-10-20 10:57:37','0',NULL),(463,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID%22:%2210%22%7D','2016-10-20 10:57:39','0',NULL),(464,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID%22:%221%22%7D','2016-10-20 10:57:40','0',NULL),(465,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7D','2016-10-20 10:57:41','0',NULL),(466,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7D','2016-10-20 10:57:43','0',NULL),(467,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%222%22%7D','2016-10-20 10:57:45','0',NULL),(468,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%225%22%7D','2016-10-20 10:57:46','0',NULL),(469,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%226%22%7D','2016-10-20 10:57:52','0',NULL),(470,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7D','2016-10-20 10:57:55','0',NULL),(471,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%225%22%7D','2016-10-20 10:58:01','0',NULL),(472,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%225%22%7D','2016-10-20 10:58:08','0',NULL),(473,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22%7D','2016-10-20 10:58:12','0',NULL),(474,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID%22:%223%22%7D','2016-10-20 10:58:23','0',NULL),(475,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID_2nd%22:%2210%22%7D','2016-10-20 11:33:47','0',NULL),(476,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %257B%2522PID%2522:%25224%2522,%2522DEPTID_2nd%2522:%252210%2522,%2522SEX_3nd%2522:%25222%2522%257D','2016-10-20 11:34:25','0',NULL),(477,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %257B%2522PID%2522:%25224%2522,%2522DEPTID_2nd%2522:%252210%2522%257D','2016-10-20 11:34:33','0',NULL),(478,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %257B%2522PID%2522:%25224%2522%257D','2016-10-20 11:34:35','0',NULL),(479,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID_2nd%22:%224%22,%22DEPTID_3nd%22:%2210%22%7D','2016-10-20 11:35:23','0',NULL),(480,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %257B%2522PID_4nd%2522:%25224%2522,%2522DEPTID_5nd%2522:%252210%2522,%2522SEX_6nd%2522:%25222%2522%257D','2016-10-20 11:35:27','0',NULL),(481,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %257B%2522PID_7nd%2522:%25224%2522,%2522DEPTID_8nd%2522:%252210%2522%257D','2016-10-20 11:35:28','0',NULL),(482,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %257B%2522PID_9nd%2522:%25224%2522%257D','2016-10-20 11:35:30','0',NULL),(483,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%222%22,%22DEPTID_2nd%22:%2210%22%7D','2016-10-20 11:41:03','0',NULL),(484,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID_2nd%22:%2210%22%7D','2016-10-20 11:41:26','0',NULL),(485,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID_2nd%22:%2210%22%7D','2016-10-20 11:44:24','0',NULL),(486,'异常日志','1',NULL,'syntax error, expect {, actual error, pos 1, json : %7B%22PID%22:%224%22,%22DEPTID_2nd%22:%2210%22%7D','2016-10-20 11:48:07','0',NULL),(487,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX_3nd\' in \'where clause\'','2016-10-20 11:54:17','0',NULL),(488,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX_3nd\' in \'where clause\'','2016-10-20 11:54:18','0',NULL),(489,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'TIPS_3nd\' in \'where clause\'','2016-10-20 14:30:33','0',NULL),(490,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'TIPS_3nd\' in \'where clause\'','2016-10-20 14:30:41','0',NULL),(491,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'TIPS_3nd\' in \'where clause\'','2016-10-20 14:31:02','0',NULL),(492,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'TIPS_3nd\' in \'where clause\'','2016-10-20 14:31:04','0',NULL),(493,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'PID_3nd\' in \'where clause\'','2016-10-20 14:31:10','0',NULL),(494,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'PID_3nd\' in \'where clause\'','2016-10-20 14:31:11','0',NULL),(495,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'PID_3nd\' in \'where clause\'','2016-10-20 14:31:12','0',NULL),(496,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'PID_3nd\' in \'where clause\'','2016-10-20 14:31:12','0',NULL),(497,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'PID_3nd\' in \'where clause\'','2016-10-20 14:31:12','0',NULL),(498,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'PID_3nd\' in \'where clause\'','2016-10-20 14:31:24','0',NULL),(499,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'PID_3td\' in \'where clause\'','2016-10-20 14:32:17','0',NULL),(500,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'PID_3td\' in \'where clause\'','2016-10-20 14:32:19','0',NULL),(501,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'PID_3td\' in \'where clause\'','2016-10-20 14:32:19','0',NULL),(502,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'PID_3td\' in \'where clause\'','2016-10-20 14:32:19','0',NULL),(503,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'PID_3td\' in \'where clause\'','2016-10-20 14:32:19','0',NULL),(504,'异常日志','1',NULL,'org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is com.alibaba.druid.pool.GetConnectionTimeoutException: wait millis 60000, active 0, maxActive 100','2016-10-20 15:30:07','0',NULL),(505,'异常日志','1',NULL,'org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is com.alibaba.druid.pool.GetConnectionTimeoutException: wait millis 60000, active 0, maxActive 100','2016-10-20 15:30:45','0',NULL),(506,'异常日志','1',NULL,'org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is com.alibaba.druid.pool.GetConnectionTimeoutException: wait millis 60000, active 0, maxActive 100','2016-10-20 15:30:50','0',NULL),(507,'异常日志','1',NULL,'org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is com.alibaba.druid.pool.GetConnectionTimeoutException: wait millis 60000, active 0, maxActive 100','2016-10-20 15:30:35','0',NULL),(508,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-21 14:19:51','0',NULL),(509,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-21 14:19:52','0',NULL),(510,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-21 15:05:40','0',NULL),(511,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-21 15:05:41','0',NULL),(512,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-21 16:58:46','0',NULL),(513,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 14:43:10','0',NULL),(514,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 14:43:11','0',NULL),(515,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:34:46','0',NULL),(516,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:34:59','0',NULL),(517,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:37:14','0',NULL),(518,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:37:15','0',NULL),(519,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:37:17','0',NULL),(520,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:37:18','0',NULL),(521,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:37:18','0',NULL),(522,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:37:20','0',NULL),(523,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:37:24','0',NULL),(524,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:37:25','0',NULL),(525,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:37:26','0',NULL),(526,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:37:27','0',NULL),(527,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:38:23','0',NULL),(528,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:39:17','0',NULL),(529,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:39:19','0',NULL),(530,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:39:20','0',NULL),(531,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:39:21','0',NULL),(532,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:39:24','0',NULL),(533,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:44:18','0',NULL),(534,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:44:20','0',NULL),(535,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:44:21','0',NULL),(536,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:44:24','0',NULL),(537,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:45:17','0',NULL),(538,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:45:44','0',NULL),(539,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:48:24','0',NULL),(540,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:49:16','0',NULL),(541,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:52:28','0',NULL),(542,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'undefined\' in \'where clause\'','2016-10-24 15:53:01','0',NULL),(543,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:42','0',NULL),(544,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:42','0',NULL),(545,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:43','0',NULL),(546,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:46','0',NULL),(547,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:46','0',NULL),(548,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:47','0',NULL),(549,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:47','0',NULL),(550,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:48','0',NULL),(551,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:48','0',NULL),(552,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:48','0',NULL),(553,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:48','0',NULL),(554,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:48','0',NULL),(555,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:19:49','0',NULL),(556,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:30:02','0',NULL),(557,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:30:13','0',NULL),(558,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:30:14','0',NULL),(559,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:34:03','0',NULL),(560,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:38:26','0',NULL),(561,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:57:55','0',NULL),(562,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:57:58','0',NULL),(563,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:58:00','0',NULL),(564,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:58:03','0',NULL),(565,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:58:05','0',NULL),(566,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:58:07','0',NULL),(567,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-24 17:58:07','0',NULL),(568,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-25 11:05:48','0',NULL),(569,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-25 11:05:50','0',NULL),(570,'异常日志','1',NULL,'Missing URI template variable \'id\' for method parameter of type String','2016-10-25 14:20:31','0',NULL),(571,'异常日志','1',NULL,'Missing URI template variable \'id\' for method parameter of type String','2016-10-25 14:20:48','0',NULL),(572,'异常日志','1',NULL,'table \"demo\" not exist','2016-10-25 16:18:28','0',NULL),(573,'异常日志','1',NULL,'table \"demo\" not exist','2016-10-25 16:21:37','0',NULL),(574,'异常日志','1',NULL,'table \"demo\" not exist','2016-10-25 16:21:43','0',NULL),(575,'异常日志','1',NULL,'table \"tfw_demo\" not exist','2016-10-25 16:25:44','0',NULL),(576,'异常日志','1',NULL,'table \"tfw_demo\" not exist','2016-10-25 16:28:19','0',NULL),(577,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-26 17:50:20','0',NULL),(578,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'SEX\' in \'where clause\'','2016-10-26 17:50:22','0',NULL),(579,'异常日志','1',NULL,'table \"tfw_group\" not exist','2016-10-28 11:28:37','0',NULL),(580,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \';) blade_statement where 1=1  order by id asc\r\n  \r\nlimit 0 , 10\' at line 22','2016-10-28 11:33:12','0',NULL),(581,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \';) blade_statement where 1=1  order by id asc\r\n  \r\nlimit 0 , 10\' at line 22','2016-10-28 11:33:39','0',NULL),(582,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'PID\' in \'where clause\'','2016-10-28 11:36:01','0',NULL),(583,'异常日志','1',NULL,'table \"tfw_group\" not exist','2016-10-28 11:37:52','0',NULL),(584,'异常日志','1',NULL,'table \"tfw_group\" not exist','2016-10-28 11:42:10','0',NULL),(585,'异常日志','1',NULL,'A JSONArray text must start with \'[\' at character 1','2016-10-28 15:12:13','0',NULL),(586,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Column \'group_id\' cannot be null','2016-10-28 15:50:14','0',NULL),(587,'异常日志','1',NULL,'com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Column \'name\' cannot be null','2016-10-28 16:35:31','0',NULL),(588,'异常日志','1',NULL,'org.springframework.validation.BeanPropertyBindingResult: 3 errors\nField error in object \'groupVo\' on field \'banckProvince\': rejected value [坑钱银行]; codes [typeMismatch.groupVo.banckProvince,typeMismatch.banckProvince,typeMismatch.java.lang.Integer,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [groupVo.banckProvince,banckProvince]; arguments []; default message [banckProvince]]; default message [Failed to convert property value of type [java.lang.String] to required type [java.lang.Integer] for property \'banckProvince\'; nested exception is java.lang.NumberFormatException: For input string: \"坑钱银行\"]\nField error in object \'groupVo\' on field \'bankCity\': rejected value [坑钱银行]; codes [typeMismatch.groupVo.bankCity,typeMismatch.bankCity,typeMismatch.java.lang.Integer,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [groupVo.bankCity,bankCity]; arguments []; default message [bankCity]]; default message [Failed to convert property value of type [java.lang.String] to required type [java.lang.Integer] for property \'bankCity\'; nested exception is java.lang.NumberFormatException: For input string: \"坑钱银行\"]\nField error in object \'groupVo\' on field \'bankId\': rejected value [坑钱银行]; codes [typeMismatch.groupVo.bankId,typeMismatch.bankId,typeMismatch.java.lang.Integer,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [groupVo.bankId,bankId]; arguments []; default message [bankId]]; default message [Failed to convert property value of type [java.lang.String] to required type [java.lang.Integer] for property \'bankId\'; nested exception is java.lang.NumberFormatException: For input string: \"坑钱银行\"]','2016-10-28 16:57:34','0',NULL);

/*Table structure for table `tfw_parameter` */

DROP TABLE IF EXISTS `tfw_parameter`;

CREATE TABLE `tfw_parameter` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `NUM` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PARA` text,
  `TIPS` varchar(255) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tfw_parameter` */

insert  into `tfw_parameter`(`ID`,`CODE`,`NUM`,`NAME`,`PARA`,`TIPS`,`STATUS`,`VERSION`) values (1,'101',100,'是否开启记录日志','2','1:是  2:否',1,7);

/*Table structure for table `tfw_relation` */

DROP TABLE IF EXISTS `tfw_relation`;

CREATE TABLE `tfw_relation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENUID` int(11) DEFAULT NULL,
  `ROLEID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2125 DEFAULT CHARSET=utf8;

/*Data for the table `tfw_relation` */

insert  into `tfw_relation`(`ID`,`MENUID`,`ROLEID`) values (821,73,2),(822,74,2),(823,75,2),(824,76,2),(825,77,2),(826,78,2),(827,79,2),(828,80,2),(829,81,2),(830,82,2),(831,83,2),(832,84,2),(833,85,2),(834,86,2),(835,87,2),(836,88,2),(837,89,2),(838,90,2),(839,91,2),(840,92,2),(841,93,2),(842,94,2),(843,95,2),(844,96,2),(845,97,2),(846,98,2),(847,99,2),(848,100,2),(849,101,2),(850,102,2),(851,1,2),(852,8,2),(853,9,2),(854,10,2),(855,11,2),(856,12,2),(857,13,2),(858,14,2),(859,15,2),(860,16,2),(861,17,2),(862,18,2),(863,19,2),(864,20,2),(865,21,2),(866,22,2),(867,23,2),(868,24,2),(869,2,2),(870,3,2),(871,4,2),(872,5,2),(873,6,2),(874,7,2),(875,25,2),(876,26,2),(877,27,2),(878,28,2),(879,29,2),(880,30,2),(881,31,2),(882,32,2),(883,33,2),(884,34,2),(885,35,2),(886,36,2),(887,37,2),(888,38,2),(889,39,2),(890,40,2),(891,41,2),(892,42,2),(893,43,2),(894,44,2),(895,45,2),(896,46,2),(897,47,2),(898,48,2),(899,49,2),(1244,1,3),(1245,62,3),(1246,64,3),(1247,72,3),(1248,73,3),(1249,74,3),(1250,75,3),(1251,76,3),(1252,77,3),(1253,78,3),(1254,79,3),(1255,80,3),(1384,72,6),(1385,73,6),(1386,74,6),(1387,75,6),(1388,76,6),(1389,77,6),(1390,78,6),(1391,79,6),(1392,80,6),(1393,81,6),(1394,82,6),(1395,84,6),(1396,85,6),(1397,86,6),(1398,87,6),(1399,83,6),(1400,88,6),(1401,89,6),(1402,90,6),(1403,91,6),(1524,1,25),(1525,62,25),(1526,64,25),(1527,72,25),(1528,73,25),(1529,74,25),(1530,75,25),(1531,76,25),(1532,77,25),(1533,78,25),(1534,79,25),(1535,80,25),(1668,81,5),(1669,82,5),(1670,84,5),(1671,85,5),(1672,86,5),(1673,87,5),(1980,1,4),(1981,2,4),(1982,3,4),(1983,4,4),(1984,5,4),(1985,6,4),(1986,7,4),(1987,81,4),(1988,82,4),(1989,84,4),(1990,85,4),(1991,86,4),(1992,87,4),(1993,83,4),(1994,88,4),(1995,89,4),(1996,90,4),(1997,91,4),(1998,92,1),(1999,93,1),(2000,94,1),(2001,95,1),(2002,96,1),(2003,97,1),(2004,1,1),(2005,8,1),(2006,9,1),(2007,10,1),(2008,11,1),(2009,12,1),(2010,13,1),(2011,14,1),(2012,15,1),(2013,16,1),(2014,17,1),(2015,18,1),(2016,19,1),(2017,20,1),(2018,21,1),(2019,22,1),(2020,23,1),(2021,24,1),(2022,2,1),(2023,3,1),(2024,4,1),(2025,5,1),(2026,6,1),(2027,7,1),(2028,105,1),(2029,107,1),(2030,108,1),(2031,25,1),(2032,26,1),(2033,27,1),(2034,28,1),(2035,29,1),(2036,30,1),(2037,31,1),(2038,32,1),(2039,33,1),(2040,34,1),(2041,35,1),(2042,36,1),(2043,37,1),(2044,38,1),(2045,39,1),(2046,40,1),(2047,41,1),(2048,42,1),(2049,43,1),(2050,44,1),(2051,45,1),(2052,46,1),(2053,47,1),(2054,48,1),(2055,49,1),(2056,56,1),(2057,57,1),(2058,58,1),(2059,59,1),(2060,60,1),(2061,61,1),(2062,62,1),(2063,63,1),(2064,64,1),(2065,65,1),(2066,81,1),(2067,82,1),(2068,84,1),(2069,85,1),(2070,86,1),(2071,87,1),(2072,83,1),(2073,88,1),(2074,89,1),(2075,90,1),(2076,91,1),(2077,98,1),(2078,99,1),(2079,100,1),(2080,101,1),(2081,102,1),(2082,103,1),(2083,104,1);

/*Table structure for table `tfw_role` */

DROP TABLE IF EXISTS `tfw_role`;

CREATE TABLE `tfw_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NUM` int(11) DEFAULT NULL,
  `PID` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DEPTID` int(11) DEFAULT NULL,
  `TIPS` varchar(255) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `tfw_role` */

insert  into `tfw_role`(`ID`,`NUM`,`PID`,`NAME`,`DEPTID`,`TIPS`,`VERSION`) values (1,1,NULL,'超级管理员',1,'administrator',1),(2,1,1,'管理员',7,'admin',3),(3,2,1,'管理员1',10,'admin',2),(4,2,NULL,'测试',10,'test',0),(5,1,4,'测试1',3,'test',1),(6,2,4,'测试2',10,'test',0);

/*Table structure for table `tfw_role_ext` */

DROP TABLE IF EXISTS `tfw_role_ext`;

CREATE TABLE `tfw_role_ext` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERID` varchar(255) DEFAULT NULL,
  `ROLEIN` text,
  `ROLEOUT` text,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8;

/*Data for the table `tfw_role_ext` */

insert  into `tfw_role_ext`(`ID`,`USERID`,`ROLEIN`,`ROLEOUT`) values (27,'66','1,44,49','45'),(47,'2','0','8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24'),(48,'63','0','0'),(49,'72','0','0'),(50,'74','0','0'),(67,'1','0','0'),(87,'168','92,103,104,105,106,107','109,110,111,112,113,114,115,116,117,118,119,120,121,122'),(107,'189','108,109,110,111,112,113,114,115,116,117,118,119,120,121,122','0'),(127,'21','92,98,99,100,101,102,1,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,39,40,41,42,43','0');

/*Table structure for table `tfw_role_group` */

DROP TABLE IF EXISTS `tfw_role_group`;

CREATE TABLE `tfw_role_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL,
  `groupid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='测试用\r\n\r\n角色分组功能';

/*Data for the table `tfw_role_group` */

insert  into `tfw_role_group`(`id`,`roleid`,`groupid`) values (1,1,147),(4,2,148),(5,2,149),(7,4,147),(8,2,147),(10,3,147),(13,3,148),(15,1,151),(19,1,164);

/*Table structure for table `tfw_user` */

DROP TABLE IF EXISTS `tfw_user`;

CREATE TABLE `tfw_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT` varchar(45) DEFAULT NULL,
  `PASSWORD` varchar(45) DEFAULT NULL,
  `SALT` varchar(45) DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `BIRTHDAY` datetime DEFAULT NULL,
  `SEX` int(11) DEFAULT NULL,
  `EMAIL` varchar(45) DEFAULT NULL,
  `PHONE` varchar(45) DEFAULT NULL,
  `ROLEID` varchar(255) DEFAULT NULL,
  `DEPTID` int(11) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `tfw_user` */

insert  into `tfw_user`(`ID`,`ACCOUNT`,`PASSWORD`,`SALT`,`NAME`,`BIRTHDAY`,`SEX`,`EMAIL`,`PHONE`,`ROLEID`,`DEPTID`,`STATUS`,`CREATETIME`,`VERSION`) values (1,'admin','4779e4a9903dfb08f9f877791c516a73','admin','管理员','2015-09-08 00:00:00',1,'admin@tonbusoft.com.cn','222333','1',9,1,'2016-01-29 08:49:53',24),(21,'test001','e334680512284cac2f57701abe03af96','r4i90','test','2016-02-19 14:00:13',1,NULL,NULL,'5',11,1,'2016-02-19 14:00:19',22),(22,'123123','653f21c93acdd4f03c95876824f440a7','048wh','213123','2016-05-03 00:00:00',1,'123','1232','4',1,1,'2016-05-17 18:50:15',2);

/* Function  structure for function  `queryChildren` */

/*!50003 DROP FUNCTION IF EXISTS `queryChildren` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `queryChildren`(rootid VARCHAR(500),tabname VARCHAR(500)) RETURNS varchar(4000) CHARSET utf8
BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);
DECLARE icount INTEGER;
DECLARE tname VARCHAR(500);
SET sTemp = '$';
SET sTempChd = rootid;
set tname=tabname;
if INSTR(tname,'tfw_dept')>0 then
select count(1) into icount from tfw_dept where id=sTempChd;
if icount>0 then
WHILE sTempChd is not NULL DO
if sTempChd <> rootid then 
SET sTemp = CONCAT(sTemp,',',sTempChd);
end if;
SELECT group_concat(id) INTO sTempChd FROM tfw_dept where FIND_IN_SET(pid,sTempChd)>0;
END WHILE;
RETURN SUBSTRING(sTemp,3);
ELSE
RETURN null;
end if;
end if;
if INSTR(tname,'tfw_role')>0 then
select count(1) into icount from tfw_role where id=sTempChd;
if icount>0 then
WHILE sTempChd is not NULL DO
if sTempChd <> rootid then 
SET sTemp = CONCAT(sTemp,',',sTempChd);
end if;
SELECT group_concat(id) INTO sTempChd FROM tfw_role where FIND_IN_SET(pid,sTempChd)>0;
END WHILE;
RETURN SUBSTRING(sTemp,3);
ELSE
RETURN null;
end if;
end if;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;