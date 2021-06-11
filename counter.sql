/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : counter

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2021-06-11 22:51:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mycal`
-- ----------------------------
DROP TABLE IF EXISTS `mycal`;
CREATE TABLE `mycal` (
  `history` varchar(100) COLLATE utf8_bin NOT NULL,
  `id` int(32) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of mycal
-- ----------------------------
INSERT INTO `mycal` VALUES ('8+9=17.0', '1');
INSERT INTO `mycal` VALUES ('3+6=9.0', '2');
INSERT INTO `mycal` VALUES ('5+93=98.0', '3');
