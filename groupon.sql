
-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `tool`;
CREATE TABLE `tool` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `type` tinyint(4) DEFAULT NULL COMMENT '工具类型 ',
  `cate` tinyint(4) DEFAULT NULL COMMENT '工具分类',
  `container_type` tinyint(4) DEFAULT NULL COMMENT '容器类型',
  `container_addr` varchar(200) DEFAULT NULL COMMENT '容器地址',
  `website` varchar(200) DEFAULT NULL COMMENT '主页',
  `help` varchar(200) DEFAULT NULL COMMENT '使用帮助',
  `create_time` timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `tool_input_and_output`;
CREATE TABLE `tool_input_and_output` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `tool_id` bigint(20) NOT NULL COMMENT '工具ID',
  `cate` tinyint(4) NOT NULL COMMENT '类别 tool_input_and_output_cate  16::输入，17：输出',
  `type` tinyint(4) DEFAULT NULL COMMENT '输入输出类型：0：文件',
  `min_num` tinyint(4) DEFAULT 1 COMMENT '最小个数',
  `max_num` tinyint(4) DEFAULT NULL COMMENT '最大个数',
  `file_format` tinyint(4) DEFAULT NULL COMMENT '文件格式',
  `file_split_symbol` tinyint(4) DEFAULT NULL COMMENT '文件分割符',
  `desc` varchar(200) DEFAULT NULL COMMENT '描述',
  `prefix` varchar(20) DEFAULT NULL COMMENT '前缀 例如:-a',
  `prefix_split_symbol` tinyint(4) DEFAULT NULL COMMENT '前缀分隔符 例如：-a后面空格就是分隔符',
  `is_must` tinyint(4) DEFAULT 0 COMMENT '是否必选项 :1:必选 0：非必选',
  `create_time` timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `tool_parameter`;
CREATE TABLE `tool_parameter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `tool_id` bigint(20) NOT NULL COMMENT '工具ID',
  `type` tinyint(4) DEFAULT 0 COMMENT '参数类型：0：字符串',
  `default_value` varchar(100) DEFAULT NULL COMMENT '参数默认值',
  `desc` varchar(200) DEFAULT NULL COMMENT '描述',
  `prefix` varchar(20) DEFAULT NULL COMMENT '前缀 例如-t',
  `prefix_split_symbol` varchar(200) DEFAULT NULL COMMENT '前缀分隔符 例如：-t后面空格就是分隔符',
  `is_use_quotation_marks` tinyint(4) DEFAULT 0 COMMENT '是否使用引号 :1:是 0：否',
  `is_must` tinyint(4) DEFAULT 0 COMMENT '是否必选项 :1:必选 0：非必选',
  `create_time` timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tool_template`;
CREATE TABLE `tool_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tool_id` bigint(20) NOT NULL COMMENT '工具ID',
  `content` varchar(1000) DEFAULT NULL COMMENT '模板内容',
  `create_time` timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



CREATE TABLE `flow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT '名称',
  `desc` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '工作流基础表';

CREATE TABLE `flow_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `flow_id` bigint(20) NOT NULL COMMENT '所属工作流ID',
  `busi_id` bigint(20) NOT NULL COMMENT '业务ID，此处是toolId',
  `create_time` timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT '工作流节点表';

CREATE TABLE `flow_line` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `flow_id` bigint(20) NOT NULL COMMENT '所属工作流ID',
  `from_id` bigint(20) NOT NULL COMMENT '业务ID',
  `to_id` bigint(20) NOT NULL COMMENT '业务ID',
  `create_time` timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT '工作流链接表';

CREATE TABLE `flow_node_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `flow_id` bigint(20) NOT NULL COMMENT '所属工作流ID',
  `tool_id` bigint(20) NOT NULL COMMENT '工具ID',
  `busi_id` bigint(20) NOT NULL COMMENT '业务ID',
   `value` varchar(200) DEFAULT NULL COMMENT '用户输入的值',
  `create_time` timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT '工作流配置表';

CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar (100) NOT NULL COMMENT '名称',
  `status` tinyint(20) Default NULL COMMENT '状态',
  `create_time` timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT '任务表';

CREATE TABLE `task_run` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NOT NULL COMMENT 'task ID',
  `flow_id` bigint(20) NOT NULL COMMENT '所属工作流ID',
  `tool_id` bigint(20) NOT NULL COMMENT '工具ID',
  `busi_type` tinyint(4) NOT NULL COMMENT '输入，输出，见：tool_input_and_output_cate。 参数：18',
  `busi_id` bigint(20) NOT NULL COMMENT '业务ID',
   `value` varchar(200) DEFAULT NULL COMMENT '用户输入的值',
  `create_time` timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL,
  `update_time` timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT '任务运行表';



DROP TABLE IF EXISTS `dic`;
CREATE TABLE `dic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL COMMENT '编码',
  `name` varchar(255) NOT NULL COMMENT '显示的名称',
  `value` varchar(255) NOT NULL COMMENT '名称对应的实际值',
  `group` varchar(128) DEFAULT NULL COMMENT '组名,规则：表名_字段名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` int(18) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` int(18) DEFAULT NULL,
  `is_deleted` int(2) DEFAULT '0' COMMENT '0 : 未逻辑删除 1:逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_CODE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




INSERT INTO `dic` VALUES (null,'1', '公共工具','', 'tool_cate', null, null, null, null, 0);
INSERT INTO `dic` VALUES (null,'2', '私有工具','', 'tool_cate', null, null, null, null, 0);
INSERT INTO `dic` VALUES (null,'3', '索引工具','', 'tool_type', null, null, null, null, 0);
INSERT INTO `dic` VALUES (null,'4', '质控工具','', 'tool_type', null, null, null, null, 0);
INSERT INTO `dic` VALUES (null,'5', '比对工具','', 'tool_type', null, null, null, null, 0);
INSERT INTO `dic` VALUES (null,'6', '其它','', 'tool_type', null, null, null, null, 0);
INSERT INTO `dic` VALUES (null,'7', '文件','', 'tool_input_and_output_type', null, null, null, null, 0);

INSERT INTO `dic` VALUES (null,'8', 'fasta','', 'tool_input_and_output_file_format', null, null, null, null, 0);
INSERT INTO `dic` VALUES (null,'9', 'fa', '','tool_input_and_output_file_format', null, null, null, null, 0);



INSERT INTO `dic` VALUES (null,'10', '空格',' ', 'tool_input_and_output_prefix_split_symbol', null, null, null, null, 0);
INSERT INTO `dic` VALUES (null,'11', '空', '','tool_input_and_output_file_format', null, null, null, null, 0);
INSERT INTO `dic` VALUES (null,'12', 'tgz','', 'tool_input_and_output_file_format', null, null, null, null, 0);


INSERT INTO `dic` VALUES (null,'13', '字符串', '','tool_parameter_type', null, null, null, null, 0);
INSERT INTO `dic` VALUES (null,'14', '整型','', 'tool_parameter_type', null, null, null, null, 0);
INSERT INTO `dic` VALUES (null,'15', '浮点型', '','tool_parameter_type', null, null, null, null, 0);


INSERT INTO `dic` VALUES (null,'16', '输入','', 'tool_input_and_output_cate', null, null, null, null, 0);
INSERT INTO `dic` VALUES (null,'17', '输出','', 'tool_input_and_output_cate', null, null, null, null, 0);