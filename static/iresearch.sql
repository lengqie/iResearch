/*
 Navicat Premium Data Transfer

 Source Server         : mysql5.7
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : iresearch

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 10/02/2022 14:55:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES (1, '数学与计算机科学学院', NULL, NULL);
INSERT INTO `college` VALUES (2, '物理与电子信息学院', NULL, NULL);
INSERT INTO `college` VALUES (3, '政治与法律学院', NULL, NULL);
INSERT INTO `college` VALUES (4, '历史地理与旅游学院', NULL, NULL);
INSERT INTO `college` VALUES (5, '外国语学院', NULL, NULL);
INSERT INTO `college` VALUES (6, '化学与环境科学学院', NULL, NULL);
INSERT INTO `college` VALUES (7, '教育科学学院', NULL, NULL);
INSERT INTO `college` VALUES (8, '生命科学学院', NULL, NULL);
INSERT INTO `college` VALUES (9, '文学与新闻传播学院', NULL, NULL);
INSERT INTO `college` VALUES (10, '经济与管理学院', NULL, NULL);

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `file`(`file`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for funds
-- ----------------------------
DROP TABLE IF EXISTS `funds`;
CREATE TABLE `funds`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `funds` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `project_id`(`project_id`) USING BTREE,
  CONSTRAINT `funds_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of funds
-- ----------------------------

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `menber` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `project_id`(`project_id`) USING BTREE,
  CONSTRAINT `member_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member
-- ----------------------------

-- ----------------------------
-- Table structure for operation
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `operation` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `project_id`(`project_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `operation_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `operation_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of operation
-- ----------------------------
INSERT INTO `operation` VALUES (1, 1, 21, '创建成功', '2022-02-08 21:19:56', NULL);
INSERT INTO `operation` VALUES (2, 1, 1, '修改项目', '2022-02-09 00:12:25', NULL);
INSERT INTO `operation` VALUES (3, 1, 1, '修改项目', '2022-02-09 00:12:52', NULL);
INSERT INTO `operation` VALUES (4, 1, 1, '修改项目', '2022-02-09 00:17:05', NULL);
INSERT INTO `operation` VALUES (5, 1, 1, '修改项目', '2022-02-09 00:17:17', NULL);
INSERT INTO `operation` VALUES (6, 1, 1, '修改项目', '2022-02-09 00:26:54', NULL);
INSERT INTO `operation` VALUES (7, 1, 2, '修改项目', '2022-02-09 12:14:20', NULL);
INSERT INTO `operation` VALUES (8, 1, 1, '修改项目', '2022-02-09 12:14:34', NULL);
INSERT INTO `operation` VALUES (9, 1, 1, '申报中', '2022-02-09 19:46:06', NULL);
INSERT INTO `operation` VALUES (10, 1, 2, '申报中', '2022-02-09 19:47:36', NULL);
INSERT INTO `operation` VALUES (11, 1, 3, '申报中', '2022-02-09 19:47:38', NULL);
INSERT INTO `operation` VALUES (12, 1, 4, '申报中', '2022-02-09 19:47:38', NULL);
INSERT INTO `operation` VALUES (13, 1, 21, '申报中', '2022-02-09 19:47:39', NULL);
INSERT INTO `operation` VALUES (14, 1, 1, '删除', '2022-02-09 19:51:30', NULL);
INSERT INTO `operation` VALUES (15, 1, 2, '删除', '2022-02-09 19:51:36', NULL);
INSERT INTO `operation` VALUES (16, 1, 1, '申报中', '2022-02-09 20:03:26', NULL);
INSERT INTO `operation` VALUES (17, 1, 1, '删除', '2022-02-09 21:01:34', NULL);
INSERT INTO `operation` VALUES (18, 1, 7, '申报中', '2022-02-09 21:25:40', NULL);
INSERT INTO `operation` VALUES (19, 1, 8, '修改项目', '2022-02-09 21:27:08', NULL);
INSERT INTO `operation` VALUES (20, 1, 8, '申报中', '2022-02-09 21:27:16', NULL);
INSERT INTO `operation` VALUES (21, 1, 5, '申报失败', '2022-02-09 21:27:23', NULL);
INSERT INTO `operation` VALUES (22, 1, 11, '结课中', '2022-02-09 21:42:50', NULL);
INSERT INTO `operation` VALUES (23, 1, 11, '结课成功', '2022-02-09 21:42:54', NULL);
INSERT INTO `operation` VALUES (24, 1, 12, '结课失败', '2022-02-09 21:42:58', NULL);
INSERT INTO `operation` VALUES (25, 1, 12, '结课中', '2022-02-09 22:53:00', NULL);
INSERT INTO `operation` VALUES (26, 1, 12, '结课失败', '2022-02-09 22:53:05', NULL);
INSERT INTO `operation` VALUES (27, 1, 8, '申报失败', '2022-02-09 22:53:17', NULL);
INSERT INTO `operation` VALUES (28, 1, 13, '申报失败', '2022-02-09 22:53:20', NULL);
INSERT INTO `operation` VALUES (29, 1, 12, '结课中', '2022-02-09 23:07:08', NULL);
INSERT INTO `operation` VALUES (30, 1, 12, '结课成功', '2022-02-09 23:07:19', NULL);
INSERT INTO `operation` VALUES (31, 1, 1, '申报中', '2022-02-09 23:11:46', NULL);
INSERT INTO `operation` VALUES (32, 1, 1, '申报成功', '2022-02-09 23:11:57', NULL);

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_id` int(11) NOT NULL,
  `college_id` int(11) NULL DEFAULT NULL,
  `subject_id` int(11) NOT NULL,
  `in_charge` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `project_type` int(11) NOT NULL,
  `project_status` int(11) NOT NULL,
  `file_id` int(11) NULL DEFAULT NULL,
  `project_purpose` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `economic_analysis` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `existing_conditions` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `expected_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `viable_analysis` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  INDEX `file_id`(`file_id`) USING BTREE,
  INDEX `create_id`(`create_id`) USING BTREE,
  INDEX `college_id`(`college_id`) USING BTREE,
  INDEX `subject_id`(`subject_id`) USING BTREE,
  INDEX `project_type`(`project_type`) USING BTREE,
  INDEX `project_status`(`project_status`) USING BTREE,
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `project_ibfk_2` FOREIGN KEY (`create_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `project_ibfk_4` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `project_ibfk_5` FOREIGN KEY (`project_type`) REFERENCES `project_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `project_ibfk_6` FOREIGN KEY (`project_status`) REFERENCES `project_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (1, '测试项目第一个', 1, 1, 3, '我就是 负责人', 2, 2, NULL, 'test', 'test', 'test', 'test888', 'test', NULL, '2022-02-09 12:14:34');
INSERT INTO `project` VALUES (2, '测试项目2', 1, 1, 1, '我', 2, 0, NULL, 'test', 'test', 'test', '7777', 'test', NULL, '2022-02-09 12:14:20');
INSERT INTO `project` VALUES (3, '测试项目3', 1, 1, 2, '我', 1, 0, NULL, 'test', 'test', 'test', 'test', 'test', NULL, NULL);
INSERT INTO `project` VALUES (4, '测试项目4', 1, 1, 1, '我', 2, 0, NULL, 'test', 'test', 'test', 'test', 'test', NULL, NULL);
INSERT INTO `project` VALUES (5, '测试项目5', 1, 1, 4, '我', 2, -2, NULL, 'test', 'test', 'test', 'test', 'test', NULL, NULL);
INSERT INTO `project` VALUES (6, '测试项目6', 1, 1, 2, '我', 1, -1, NULL, 'test', 'test', 'test', 'test', 'test', NULL, NULL);
INSERT INTO `project` VALUES (7, '测试项目7', 1, 1, 1, '我', 2, 1, NULL, 'test', 'test', 'test', 'test', 'test', NULL, NULL);
INSERT INTO `project` VALUES (8, '测试项目8', 1, 1, 2, '我', 1, -2, NULL, 'test', 'test', 'test', 'test', 'test', NULL, '2022-02-09 21:27:08');
INSERT INTO `project` VALUES (9, '测试项目9', 1, 1, 1, '我', 2, 2, NULL, 'test', 'test', 'test', 'test', 'test', NULL, NULL);
INSERT INTO `project` VALUES (10, '测试项目10', 1, 1, 3, '我', 1, 4, NULL, 'test', 'test', 'test', 'test', 'test', NULL, NULL);
INSERT INTO `project` VALUES (11, '测试项目11', 1, 1, 1, '我', 1, 4, NULL, 'test', 'test', 'test', 'test', 'test', NULL, NULL);
INSERT INTO `project` VALUES (12, '测试项目12', 1, 1, 5, '我', 1, 4, NULL, 'test', 'test', 'test', 'test', 'test', NULL, NULL);
INSERT INTO `project` VALUES (13, '测试项目13', 2, 1, 1, '我', 2, -2, NULL, 'test', 'test', 'test', 'test', 'test', NULL, NULL);
INSERT INTO `project` VALUES (21, '111', 1, NULL, 3, '11', 1, 1, NULL, '11', '1', '11', '111', '111', '2022-02-08 21:19:56', NULL);

-- ----------------------------
-- Table structure for project_status
-- ----------------------------
DROP TABLE IF EXISTS `project_status`;
CREATE TABLE `project_status`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `project_status`(`project_status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of project_status
-- ----------------------------
INSERT INTO `project_status` VALUES (-4, '结课失败', NULL, NULL);
INSERT INTO `project_status` VALUES (-2, '申报失败', NULL, NULL);
INSERT INTO `project_status` VALUES (-1, '已删除', NULL, NULL);
INSERT INTO `project_status` VALUES (0, '创建成功', NULL, NULL);
INSERT INTO `project_status` VALUES (1, '申报中', NULL, NULL);
INSERT INTO `project_status` VALUES (2, '申报成功', NULL, NULL);
INSERT INTO `project_status` VALUES (3, '结课中', NULL, NULL);
INSERT INTO `project_status` VALUES (4, '结课成功', NULL, NULL);

-- ----------------------------
-- Table structure for project_type
-- ----------------------------
DROP TABLE IF EXISTS `project_type`;
CREATE TABLE `project_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of project_type
-- ----------------------------
INSERT INTO `project_type` VALUES (1, '研究', '2022-01-21 17:08:46', '2022-01-21 17:08:49');
INSERT INTO `project_type` VALUES (2, '设计', NULL, NULL);

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `college_id` int(11) NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  INDEX `college_id`(`college_id`) USING BTREE,
  CONSTRAINT `subject_ibfk_1` FOREIGN KEY (`college_id`) REFERENCES `college` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES (1, 1, '数学与应用数学', NULL, NULL);
INSERT INTO `subject` VALUES (2, 1, '计算机科学与技术', NULL, NULL);
INSERT INTO `subject` VALUES (3, 1, '经济统计学', NULL, NULL);
INSERT INTO `subject` VALUES (4, 1, '数据科学与大数据技术', NULL, NULL);
INSERT INTO `subject` VALUES (5, 2, '其他科目', NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `user_type` int(11) NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  INDEX `user_ibfk_1`(`user_type`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_type`) REFERENCES `user_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'user', 'user', '其实我真的是管理员', 2, '2022-01-20 18:47:01', '2022-02-08 20:07:43');
INSERT INTO `user` VALUES (2, 'test', 'user', '张三', 1, '2021-09-22 15:44:47', '2022-02-06 15:44:49');
INSERT INTO `user` VALUES (3, 'li4', 'user', '李四', 1, '2021-10-13 23:49:55', NULL);
INSERT INTO `user` VALUES (13, 'wang5', 'wang5', 'wang5', 1, '2022-02-08 13:52:29', NULL);

-- ----------------------------
-- Table structure for user_type
-- ----------------------------
DROP TABLE IF EXISTS `user_type`;
CREATE TABLE `user_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_type
-- ----------------------------
INSERT INTO `user_type` VALUES (1, 'user', NULL, NULL);
INSERT INTO `user_type` VALUES (2, 'admin', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
