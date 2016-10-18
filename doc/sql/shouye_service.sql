-- ----------------------------
-- Table structure for tb_user_login
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_login`;
CREATE TABLE `tb_user_login` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `login_username` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '登录名',
  `password` VARCHAR(50) NOT NULL DEFAULT '密码',
  `status` INT(2) NOT NULL DEFAULT 0 COMMENT '账号状态 0:正常 1:封号 2:冻结',
  `unlock_time` BIGINT DEFAULT -1 COMMENT '解冻时间，默认为-1',
  `last_login_ip` VARCHAR(25) DEFAULT '' COMMENT '最后登录IP',
  `create_time` BIGINT COMMENT '创建时间',
    PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  AUTO_INCREMENT = 2
  COMMENT '用户登录信息表';

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `mobile` VARCHAR(15) NOT NULL DEFAULT '' COMMENT '手机号',
  `gender` int(2) NOT NULL DEFAULT 1 COMMENT '性别 1:男 2:女',
  `age` INT(2) DEFAULT 1 COMMENT '年龄',
  `birthday` BIGINT DEFAULT 0 COMMENT '出生年月',
  `age_interval_id` INT(11) DEFAULT 0 COMMENT '年龄区间段ID',
  `province_id` INT(11) DEFAULT 0 COMMENT '省份ID',
  `city_id` INT(11) DEFAULT 0 COMMENT '城市ID',
  `province_city` VARCHAR(20) DEFAULT '' COMMENT '省份城市名字',
  `school` VARCHAR(50) DEFAULT '' COMMENT '学校名字，多个名字之间用“|”分割',
  `career` VARCHAR(10) DEFAULT '' COMMENT '事业状态,多个状态用"|"分割',
  `domain` VARCHAR(50) DEFAULT '' COMMENT '行业领域 多个领域用“|”分割',
  `professional`VARCHAR(50) DEFAULT '' COMMENT '专业 多个专业用“|”分割',
  `product_service` VARCHAR(50) DEFAULT '' COMMENT '产品服务 多个产品服务用“|”分割',
  `organization`  VARCHAR(50) DEFAULT '' COMMENT '单位 多个单位服务用“|”分割',

  `groups` VARCHAR(50) DEFAULT '' COMMENT '分组 多个分组用“|”分割',
  `targat` VARCHAR(50) DEFAULT ''COMMENT  '标记 多个标记用“|”分割',
  `vip_end_time` BIGINT DEFAULT -1 COMMENT '会员到期 -1:永久，其他:会员到期时间',
  `vip_type` INT(2) DEFAULT 1 COMMENT '会员类型1:普通会员 2:干事',
  `join_type` INT(2) DEFAULT 1 COMMENT '1:主动加入2:邀请加入3:',
  `create_time` BIGINT COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  AUTO_INCREMENT = 2
  COMMENT '用户详细信息表';


-- ----------------------------
-- Table structure for tb_user_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_group`;
CREATE TABLE `tb_user_group` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL COMMENT '用户ID',
  `group_id` INT(11) NOT NULL COMMENT '组织id',
  `status` INT(2) DEFAULT 0 COMMENT '状态 0:正常 1:退出 2:黑名单',
  `create_time` BIGINT COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  AUTO_INCREMENT = 2
  COMMENT '用户组织关系表';


-- ----------------------------
-- Table structure for tb_user_friend
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_friend`;
CREATE TABLE `tb_user_friend` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL COMMENT '用户ID',
  `friend_id` INT(11) NOT NULL COMMENT '好友id',
  `type` INT(2) NOT NULL DEFAULT 0 COMMENT '关系类型 0:结识 1:熟人',
  `status` INT(2) DEFAULT 0 COMMENT '状态 0:正常 1:删除 2:黑名单',
  `create_time` BIGINT COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  AUTO_INCREMENT = 2
  COMMENT '用户组织关系表';

-- ----------------------------
-- Table structure for tb_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_group`;
CREATE TABLE `tb_group` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name`VARCHAR(50) NOT NULL COMMENT '组织名称',
  `avater` VARCHAR(200) DEFAULT '' COMMENT '头像',
  `idcard` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '组织ID',
  `password` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '组织密码',
  `type` INT(2) DEFAULT 0 COMMENT '组织类型ID',
  `province` INT(11) DEFAULT 0 COMMENT '省份ID',
  `city` INT(11) DEFAULT 0 COMMENT '城市ID',
  `targat` VARCHAR(100) DEFAULT '' COMMENT '关键字,多个关键字用"|"分割',
  `code` VARCHAR(50) DEFAULT ''COMMENT '组织代码证号',
  `code_image` VARCHAR(200) DEFAULT '' COMMENT '组织代码证号扫描件',
  `license` VARCHAR(100) DEFAULT '' COMMENT '营业执照注册号',
  `license_image` VARCHAR(200) DEFAULT ''COMMENT '营业执照扫描件',
  `artificial_person_name` VARCHAR(50) DEFAULT ''COMMENT '运营者身份证姓名',
  `artificial_person_idcard` VARCHAR(50) DEFAULT ''COMMENT '运营者身份证号码',
  `artificial_person_mobile` VARCHAR(50) DEFAULT ''COMMENT '运营者电话',

  `cost` INT DEFAULT 0 COMMENT '入会费',
  `activity_count` INT DEFAULT 0 COMMENT '活动举办数量',
  `member_count` INT DEFAULT 0 COMMENT '会员数',
  `admin_count` INT DEFAULT 0 COMMENT '干事数量',

  `status` INT(2) DEFAULT 0 COMMENT '状态 0:正常 1:封号',
  `freeze_time` BIGINT DEFAULT -1 COMMENT '封号时长 正常则为-1',
  `why1` VARCHAR(500) DEFAULT '' COMMENT '封号原因, 正常为空',
  `create_time` BIGINT COMMENT '创建时间',
  `approval_admin_id` INT(11) DEFAULT 0 COMMENT '审核人ID',
  `create_admin_id` INT(11) DEFAULT 0 COMMENT '创建人ID',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  AUTO_INCREMENT = 2
  COMMENT '组织表';

-- ----------------------------
-- Table structure for tb_group_bank
-- ----------------------------
DROP TABLE IF EXISTS `tb_group_bank`;
CREATE TABLE `tb_group_bank` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `group_id` INT(11) NOT NULL COMMENT '组织ID',
  `bank_user_name` VARCHAR(50) DEFAULT '' COMMENT '银行开户名',
  `bank_accout` VARCHAR(50) DEFAULT '' COMMENT '银行账号',
  `bank_id` INT(11) DEFAULT 0 COMMENT '银行',
  `bank_name` VARCHAR(50) DEFAULT '' COMMENT '银行名字',
  `provice` INT(11) DEFAULT 0 COMMENT '省份ID',
  `city` INT(11) DEFAULT 0 COMMENT '城市ID',
  `branch_name` VARCHAR(50) DEFAULT '' COMMENT '开户行支行名称',
  `create_time` BIGINT COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  AUTO_INCREMENT = 2
  COMMENT '组织扩展信息表';

-- ----------------------------
-- Table structure for tb_group_extend
-- ----------------------------
DROP TABLE IF EXISTS `tb_group_extend`;
CREATE TABLE `tb_group_extend` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `group_id` INT(11) NOT NULL COMMENT '组织ID',
  `title` VARCHAR(50) DEFAULT '' COMMENT '标题',
  `target` VARCHAR(2000) DEFAULT '' COMMENT '内容',
  `create_time` BIGINT COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  AUTO_INCREMENT = 2
  COMMENT '组织扩展信息表';


-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `type` INT(2) NOT NULL DEFAULT 0 COMMENT '类型 0:通知 1:消息',
  `from_id` INT(11) NOT NULL DEFAULT 0 COMMENT '信息指向者',
  `to_id` INT(11) NOT NULL DEFAULT 0 COMMENT '信息被指向者',
  `title` VARCHAR(50) DEFAULT '' COMMENT '标题/标签',
  `content` VARCHAR(500) DEFAULT '' COMMENT '通知内容',
  `replaces` VARCHAR(500) DEFAULT '' COMMENT '内容替换内容',
  `action1` VARCHAR(100) DEFAULT '' COMMENT '事件1，格式: action|/path1/method1',
  `action2` VARCHAR(100) DEFAULT '' COMMENT '事件2，格式: action2|/path2/method2',
  `action3` VARCHAR(100) DEFAULT '' COMMENT '事件3，格式: action2|/path2/method2',
  `action4` VARCHAR(100) DEFAULT '' COMMENT '事件4，格式: action2|/path2/method2',
  `action5` VARCHAR(100) DEFAULT '' COMMENT '事件5，格式: action2|/path2/method2',
  `send_time` BIGINT DEFAULT -1 COMMENT '定时发送时间',
  `create_time` BIGINT COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  AUTO_INCREMENT = 2
  COMMENT '消息表';

-- ----------------------------
-- Table structure for tb_user_approval
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_approval`;
CREATE TABLE `tb_user_approval` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL COMMENT '申请用户ID',
  `introduce_user_id` INT(11) NOT NULL COMMENT '引荐人ID',
  `validate_info` VARCHAR(200) NOT NULL COMMENT '验证消息',
  `status` INT(2) NOT NULL DEFAULT 0 COMMENT '0:未处理1:已批准2：已忽略3:亿拉黑4:等待对方验证5:同意加入',
  `create_time` BIGINT COMMENT '创建时间',
  `admin_id` INT(11) DEFAULT 0 COMMENT '操作人ID',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  AUTO_INCREMENT = 2
  COMMENT '审批表';

-- ----------------------------
-- Table structure for tb_dealflow
-- ----------------------------
DROP TABLE IF EXISTS `tb_dealflow`;
CREATE TABLE `tb_dealflow` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `dno` VARCHAR(32) NOT NULL COMMENT '订单编号',
  `group_id` INT(11) NOT NULL COMMENT '所属组织',
  `type` INT(2) NOT NULL DEFAULT 0 COMMENT '交易类型 0:加入费 1:续费 2:报名费',
  `user_id` INT(11) NOT NULL COMMENT '用户ID',
  `amount` DOUBLE(10,2) DEFAULT 0 COMMENT '金额',
  `status` INT(2) DEFAULT 0 COMMENT '交易状态 0:成功 1:失败',
  `create_time` BIGINT COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  AUTO_INCREMENT = 2
  COMMENT '交易流水表';

-- ----------------------------
-- Table structure for tb_report
-- ----------------------------
DROP TABLE IF EXISTS `tb_report`;
CREATE TABLE `tb_report` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `rno` VARCHAR(32) NOT NULL COMMENT '时间ID',
  `type` INT(2) NOT NULL COMMENT '类型',
  `object_id` INT(11) NOT NULL COMMENT '对象ID',
  `object_name` VARCHAR(50) DEFAULT '' COMMENT '对象名称',
  `why1` VARCHAR(50) DEFAULT '' COMMENT '举报原因',
  `status` INT(2) DEFAULT 0 COMMENT '处理状态 1:忽略 2:冻结 3:封号 4:暂停 5:停刊',
  `admin_id` INT(11) NOT NULL COMMENT '处理人ID',
  `create_time` BIGINT COMMENT '创建时间',
  `update_time` BIGINT COMMENT '处理时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  AUTO_INCREMENT = 2
  COMMENT '举报表';

-- ----------------------------
-- Table structure for tb_feekback
-- ----------------------------
DROP TABLE IF EXISTS `tb_feekback`;
CREATE TABLE `tb_feekback` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `rno` VARCHAR(32) NOT NULL COMMENT '时间ID',
  `user_id` INT(11) NOT NULL COMMENT '反馈人ID',
  `content` VARCHAR(500) NOT NULL COMMENT '反馈内容',
  `reply` VARCHAR(500) DEFAULT '' COMMENT '回复',
  `delegate` INT(11) DEFAULT 0 COMMENT '委派人',
  `admin_id` INT(11) NOT NULL COMMENT '处理人ID',
  `status` INT(2) DEFAULT 0 COMMENT '处理状态 0:未处理 1:已解决 2:忽略 3:屏蔽',
  `create_time` BIGINT COMMENT '创建时间',
  `update_time` BIGINT COMMENT '处理时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  AUTO_INCREMENT = 2
  COMMENT '意见反馈表';