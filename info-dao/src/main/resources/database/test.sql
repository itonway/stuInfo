/*
Navicat MySQL Data Transfer

Source Server         : myDatabase
Source Server Version : 50517
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50517
File Encoding         : 65001

Date: 2020-03-01 21:22:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_info_class
-- ----------------------------
DROP TABLE IF EXISTS `t_info_class`;
CREATE TABLE `t_info_class` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `class_no` varchar(20) DEFAULT NULL COMMENT '班级编号',
  `class_name` varchar(20) DEFAULT NULL COMMENT '班级名称',
  `class_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `creater_id` bigint(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Records of t_info_class
-- ----------------------------
INSERT INTO `t_info_class` VALUES ('76', '1001', '计算机科学与技术（一班）', '计算机科学与技术（一班）', '2020-03-01 21:18:58', '1', null, null);

-- ----------------------------
-- Table structure for t_info_course
-- ----------------------------
DROP TABLE IF EXISTS `t_info_course`;
CREATE TABLE `t_info_course` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `teacher_id` bigint(10) DEFAULT NULL COMMENT '任课老师ID',
  `course_no` varchar(20) DEFAULT NULL COMMENT '课程编号',
  `course_name` varchar(50) DEFAULT NULL COMMENT '课程名称',
  `course_time` int(3) DEFAULT NULL COMMENT '学时',
  `course_credit` int(3) DEFAULT NULL COMMENT '学分',
  `max_stu_num` bigint(3) DEFAULT NULL COMMENT '最大学生人数',
  `course_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `creater_id` bigint(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='课程信息';

-- ----------------------------
-- Records of t_info_course
-- ----------------------------
INSERT INTO `t_info_course` VALUES ('6', '41', 'KC10001', '计算机科学与技术', null, null, null, '语文语文语文', '2019-07-17 11:40:55', '1', '2020-03-01 21:19:30', '1');

-- ----------------------------
-- Table structure for t_info_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_info_dept`;
CREATE TABLE `t_info_dept` (
  `id` bigint(10) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '部门名字',
  `code` varchar(50) DEFAULT NULL COMMENT '部门code',
  `parent_code` varchar(50) DEFAULT NULL COMMENT '父节点',
  `level` varchar(50) DEFAULT NULL COMMENT '部门级别',
  `status` varchar(1) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  `creater_id` bigint(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_info_dept
-- ----------------------------

-- ----------------------------
-- Table structure for t_info_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_info_dict`;
CREATE TABLE `t_info_dict` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '字典名称',
  `dict_name` varchar(20) DEFAULT NULL COMMENT '字典名称',
  `dict_code` varchar(10) DEFAULT NULL COMMENT '字典code',
  `dict_status` char(1) DEFAULT NULL COMMENT '1可用0作废',
  `create_time` datetime DEFAULT NULL,
  `creater_id` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_info_dict
-- ----------------------------
INSERT INTO `t_info_dict` VALUES ('1', '性别', '1001', '1', null, null);

-- ----------------------------
-- Table structure for t_info_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `t_info_dict_item`;
CREATE TABLE `t_info_dict_item` (
  `id` bigint(10) NOT NULL,
  `dict_code` varchar(10) DEFAULT NULL COMMENT 't_info_dict 表dict_code',
  `dict_item_code` varchar(10) DEFAULT NULL COMMENT '字典明细code',
  `dict_item_name` varchar(10) DEFAULT NULL COMMENT '字典明细name',
  `dict_item_status` char(1) DEFAULT NULL COMMENT '1可用0作废',
  `create_time` datetime DEFAULT NULL,
  `creater_id` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_info_dict_item
-- ----------------------------
INSERT INTO `t_info_dict_item` VALUES ('1', '1001', '1', '男', '1', null, null);
INSERT INTO `t_info_dict_item` VALUES ('2', '1001', '2', '女', '1', null, null);

-- ----------------------------
-- Table structure for t_info_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_info_menu`;
CREATE TABLE `t_info_menu` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限控制',
  `p_id` bigint(10) DEFAULT NULL COMMENT '父id',
  `url` varchar(255) DEFAULT NULL,
  `ramark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT NULL COMMENT '1可用0不可用',
  `type` varchar(10) DEFAULT NULL COMMENT '1菜单0按钮',
  `icon` varchar(20) DEFAULT NULL COMMENT '图标',
  `create_time` datetime DEFAULT NULL,
  `creater_id` bigint(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_info_menu
-- ----------------------------
INSERT INTO `t_info_menu` VALUES ('1', '系统管理', 'sys:show', '0', null, '系统管理', '1', '1', null, '2019-01-31 18:00:46', '1', '2019-01-31 18:01:03', null);
INSERT INTO `t_info_menu` VALUES ('2', '用户管理', 'user:show', '1', '/user/jumpUserList', '用户管理', '1', '1', null, '2019-01-31 18:01:00', '1', '2019-01-31 18:01:03', null);
INSERT INTO `t_info_menu` VALUES ('3', '角色管理', 'role:show', '1', '/role/jumpRoleList', '角色管理', '1', '1', null, '2019-01-31 18:01:03', '1', '2019-01-31 18:01:03', null);
INSERT INTO `t_info_menu` VALUES ('4', '菜单管理', 'menu:show', '1', '/menu/jumpMenuList', '菜单管理', '1', '1', null, '2019-07-16 18:44:44', '1', '2019-07-16 18:44:52', null);
INSERT INTO `t_info_menu` VALUES ('5', '学生信息管理', null, '0', null, '学生信息管理', '1', '1', null, '2019-07-16 18:49:29', '1', null, null);
INSERT INTO `t_info_menu` VALUES ('6', '学生管理', null, '5', '/stu/jumpStuList', '学生管理', '1', '1', null, '2019-07-16 18:49:32', '1', null, null);
INSERT INTO `t_info_menu` VALUES ('7', '班级管理', null, '5', '/class/jumpClassList', '班级管理', '1', '1', null, '2019-07-16 18:49:36', '1', null, null);
INSERT INTO `t_info_menu` VALUES ('8', '课程管理', null, '5', '/course/jumpCourseList', '课程管理', '1', '1', null, '2019-07-16 18:49:39', '1', null, null);
INSERT INTO `t_info_menu` VALUES ('9', '教师管理', null, '5', '/teacher/jumpTeacherList', '教师管理', '1', '1', null, '2019-07-16 18:49:43', '1', null, null);
INSERT INTO `t_info_menu` VALUES ('10', '成绩管理', null, '5', '/score/jumpScoreList', '成绩管理', '1', '1', null, '2019-07-17 17:44:34', '1', null, null);

-- ----------------------------
-- Table structure for t_info_role
-- ----------------------------
DROP TABLE IF EXISTS `t_info_role`;
CREATE TABLE `t_info_role` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `status` char(1) DEFAULT NULL COMMENT '状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `creater_id` bigint(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_info_role
-- ----------------------------
INSERT INTO `t_info_role` VALUES ('1', '系统管理员', null, '系统管理员', '2019-07-18 18:35:13', '1', '2020-03-01 21:10:58', '59');

-- ----------------------------
-- Table structure for t_info_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_info_role_menu`;
CREATE TABLE `t_info_role_menu` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(10) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(10) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_info_role_menu
-- ----------------------------
INSERT INTO `t_info_role_menu` VALUES ('281', '1', '1');
INSERT INTO `t_info_role_menu` VALUES ('282', '1', '2');
INSERT INTO `t_info_role_menu` VALUES ('283', '1', '3');
INSERT INTO `t_info_role_menu` VALUES ('284', '1', '4');
INSERT INTO `t_info_role_menu` VALUES ('285', '1', '5');
INSERT INTO `t_info_role_menu` VALUES ('286', '1', '6');
INSERT INTO `t_info_role_menu` VALUES ('287', '1', '7');
INSERT INTO `t_info_role_menu` VALUES ('288', '1', '8');
INSERT INTO `t_info_role_menu` VALUES ('289', '1', '9');
INSERT INTO `t_info_role_menu` VALUES ('290', '1', '10');

-- ----------------------------
-- Table structure for t_info_score
-- ----------------------------
DROP TABLE IF EXISTS `t_info_score`;
CREATE TABLE `t_info_score` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `stu_id` bigint(10) NOT NULL COMMENT '学生编号',
  `course_id` bigint(10) NOT NULL COMMENT '课程编号',
  `score` decimal(10,0) DEFAULT NULL COMMENT '分数',
  `score_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `creater_id` bigint(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='成绩表';

-- ----------------------------
-- Records of t_info_score
-- ----------------------------
INSERT INTO `t_info_score` VALUES ('32', '189', '6', '121', 'itontheway', '2020-03-01 21:19:45', '1', null, null);

-- ----------------------------
-- Table structure for t_info_stu
-- ----------------------------
DROP TABLE IF EXISTS `t_info_stu`;
CREATE TABLE `t_info_stu` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `class_id` bigint(10) DEFAULT NULL COMMENT '班级id',
  `class_no` varchar(20) DEFAULT NULL COMMENT '班级编号',
  `stu_no` varchar(20) DEFAULT NULL COMMENT '学号',
  `stu_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `stu_birth_date` date DEFAULT NULL COMMENT '出生日期',
  `stu_age` int(3) DEFAULT NULL COMMENT '年龄',
  `stu_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `stu_sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `stu_email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `stu_mobile` varchar(12) DEFAULT NULL COMMENT '联系方式',
  `stu_address` varchar(255) DEFAULT NULL COMMENT '家庭住址',
  `stu_start_date` date DEFAULT NULL COMMENT '入学日期',
  `stu_end_date` date DEFAULT NULL COMMENT '毕业日期',
  `stu_status` varchar(2) DEFAULT NULL COMMENT '状态1启用2禁用',
  `stu_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creater_id` bigint(10) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `updater_id` bigint(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8 COMMENT='学生信息表';

-- ----------------------------
-- Records of t_info_stu
-- ----------------------------
INSERT INTO `t_info_stu` VALUES ('189', '76', null, '1001', 'itontheway', '2020-03-01', '27', '410421199009898762', '1', 'itontheway@163.com', '13781087765', '河南郑州', '2020-03-01', '2020-03-01', null, '河南郑州', '2020-03-01 21:18:26', '1', '2020-03-01 21:19:13', '1');

-- ----------------------------
-- Table structure for t_info_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_info_teacher`;
CREATE TABLE `t_info_teacher` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `teacher_no` varchar(20) DEFAULT NULL COMMENT '编号',
  `teacher_name` varchar(20) DEFAULT NULL COMMENT '教师名称',
  `teacher_password` varchar(20) DEFAULT NULL COMMENT '密码',
  `teacher_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `teacher_sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `teacher_birth_date` date DEFAULT NULL COMMENT '出生日期',
  `teacher_mobile` varchar(12) DEFAULT NULL COMMENT '手机',
  `teacher_email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `teacher_address` varchar(255) DEFAULT NULL COMMENT '地址',
  `start_job_date` date DEFAULT NULL COMMENT '入职日期',
  `end_job_date` date DEFAULT NULL COMMENT '离职日期',
  `teacher_status` varchar(2) DEFAULT NULL COMMENT '状态',
  `teacher_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `creater_id` bigint(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='教师表';

-- ----------------------------
-- Records of t_info_teacher
-- ----------------------------
INSERT INTO `t_info_teacher` VALUES ('41', 'JS10001', '在路上老师', '', '410421199005185521', '1', '1998-08-13', '13781079332', 'ontheway@163.com', '河南省宝丰县肖旗乡', '2019-08-02', '2029-08-12', null, '老师好', '2019-07-17 14:44:22', '1', '2020-03-01 21:19:24', '1');

-- ----------------------------
-- Table structure for t_info_user
-- ----------------------------
DROP TABLE IF EXISTS `t_info_user`;
CREATE TABLE `t_info_user` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `login_name` varchar(10) DEFAULT NULL COMMENT '登录名',
  `age` varchar(3) DEFAULT NULL COMMENT '年龄',
  `sex` char(1) DEFAULT NULL COMMENT '性别1男2女',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(12) DEFAULT NULL COMMENT '手机号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creater_id` bigint(10) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `updater_id` bigint(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_info_user
-- ----------------------------
INSERT INTO `t_info_user` VALUES ('1', '管理员', 'e0b141de1c8091be350d3fc80de66528', 'admin', null, '1', 'admin@163.com', '13221079318', '2020-03-01 21:10:30', '1', '2020-03-01 21:10:48', '59');

-- ----------------------------
-- Table structure for t_info_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_info_user_role`;
CREATE TABLE `t_info_user_role` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(10) NOT NULL COMMENT '用户ID',
  `role_id` bigint(10) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_info_user_role
-- ----------------------------
INSERT INTO `t_info_user_role` VALUES ('143', '1', '1');
