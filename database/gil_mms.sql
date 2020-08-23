/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : gil_mms

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_account
-- ----------------------------
DROP TABLE IF EXISTS `tb_account`;
CREATE TABLE `tb_account` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '账户主键',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账户email',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `identity` int NOT NULL COMMENT '身份类别',
  `permission` int NOT NULL COMMENT '权限类别',
  `identity_num` varchar(100) NOT NULL COMMENT '号 根据身份类别判断是学号还是教工号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`) USING BTREE COMMENT '账户索引'
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='账户表';

-- ----------------------------
-- Table structure for tb_clockin
-- ----------------------------
DROP TABLE IF EXISTS `tb_clockin`;
CREATE TABLE `tb_clockin` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date` date NOT NULL COMMENT '打卡记录创建日期',
  `time` time NOT NULL COMMENT '打卡记录创建日期',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '打卡者姓名',
  `emotion_type` int NOT NULL COMMENT '打卡心情类型',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `uid` int NOT NULL COMMENT '打卡者的账户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2668 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='打卡表';

-- ----------------------------
-- Table structure for tb_diary
-- ----------------------------
DROP TABLE IF EXISTS `tb_diary`;
CREATE TABLE `tb_diary` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(10000) DEFAULT NULL,
  `uid` int NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2656 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='日报表';

-- ----------------------------
-- Table structure for tb_student
-- ----------------------------
DROP TABLE IF EXISTS `tb_student`;
CREATE TABLE `tb_student` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '学生记录id',
  `stu_num` varchar(100) NOT NULL COMMENT '学号',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `email` varchar(255) NOT NULL COMMENT 'email',
  `clockin_num` int NOT NULL COMMENT '本月打卡数，被事件每月清空',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `entry_year` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '入学年份',
  `college` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '院系',
  `is_graduate` int DEFAULT NULL COMMENT '是否毕业0否1是',
  `level` int DEFAULT NULL COMMENT '学历1实习生，2本科，3硕士，4博士',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号码',
  `uid` int DEFAULT NULL COMMENT '学生关联的用户id',
  `address` varchar(255) DEFAULT NULL COMMENT '学生住址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_stu_num` (`stu_num`) USING BTREE COMMENT '学号索引'
) ENGINE=InnoDB AUTO_INCREMENT=390 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生表';

-- ----------------------------
-- Table structure for tb_teacher
-- ----------------------------
DROP TABLE IF EXISTS `tb_teacher`;
CREATE TABLE `tb_teacher` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tch_num` varchar(100) NOT NULL COMMENT '教工号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'email',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号码',
  `office` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '办公室',
  `level` int DEFAULT NULL COMMENT '职级',
  `team_id` int DEFAULT NULL COMMENT '所在组号',
  `uid` int DEFAULT NULL COMMENT '账户id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_tch_num` (`tch_num`) USING BTREE COMMENT '教工号索引'
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='教师表';

-- ----------------------------
-- Table structure for tb_team
-- ----------------------------
DROP TABLE IF EXISTS `tb_team`;
CREATE TABLE `tb_team` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '组别主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组名',
  `linkman` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '负责人姓名',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号码',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='组别表';

SET FOREIGN_KEY_CHECKS = 1;
