/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.32-log : Database - wechat
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wechat` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `wechat`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(200) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `appid` varchar(200) DEFAULT NULL,
  `appsecret` varchar(200) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  `accessToken` varchar(200) DEFAULT NULL,
  `ticket` varchar(200) DEFAULT NULL,
  `keywordid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `account_invitation_code` */

DROP TABLE IF EXISTS `account_invitation_code`;

CREATE TABLE `account_invitation_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '    用户邀请码',
  `account_id` int(11) NOT NULL,
  `code` varchar(6) CHARACTER SET utf8 NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `invitation_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `account_invitation_record` */

DROP TABLE IF EXISTS `account_invitation_record`;

CREATE TABLE `account_invitation_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '  邀请码记录表',
  `account_id` int(11) NOT NULL,
  `code` varchar(6) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `1` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `account_rel_info` */

DROP TABLE IF EXISTS `account_rel_info`;

CREATE TABLE `account_rel_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rel_name` varchar(125) DEFAULT NULL,
  `id_card` varchar(125) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `account_wechat_rel` */

DROP TABLE IF EXISTS `account_wechat_rel`;

CREATE TABLE `account_wechat_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `wechat_user_id` int(11) NOT NULL,
  `status` tinyint(2) DEFAULT '1',
  `bind_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account_id`,`wechat_user_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `accountinfo` */

DROP TABLE IF EXISTS `accountinfo`;

CREATE TABLE `accountinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `nickname` varchar(200) DEFAULT NULL,
  `img` varchar(300) DEFAULT NULL,
  `introduce` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `activity` */

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `enddate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `activityprize` */

DROP TABLE IF EXISTS `activityprize`;

CREATE TABLE `activityprize` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `activityid` int(11) DEFAULT NULL,
  `percentage` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `receiver` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `mem_id` varchar(255) DEFAULT NULL,
  `isdefault` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBB979BF4FE193FFF` (`mem_id`),
  CONSTRAINT `FKBB979BF4FE193FFF` FOREIGN KEY (`mem_id`) REFERENCES `member1` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `belong` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK586034FD88E281E` (`belong`),
  CONSTRAINT `FK586034FD88E281E` FOREIGN KEY (`belong`) REFERENCES `belong` (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `agent_article` */

DROP TABLE IF EXISTS `agent_article`;

CREATE TABLE `agent_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cover` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `author` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` text COLLATE utf8_unicode_ci,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `agent_bill_of_sales` */

DROP TABLE IF EXISTS `agent_bill_of_sales`;

CREATE TABLE `agent_bill_of_sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `agent_charge_order` */

DROP TABLE IF EXISTS `agent_charge_order`;

CREATE TABLE `agent_charge_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `agent_id` int(11) NOT NULL,
  `amount` decimal(9,2) NOT NULL,
  `order_number` varchar(50) NOT NULL,
  `status` tinyint(3) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `agent_deposit_record` */

DROP TABLE IF EXISTS `agent_deposit_record`;

CREATE TABLE `agent_deposit_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(9,2) DEFAULT NULL,
  `remark` varchar(225) DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `agent_life_record` */

DROP TABLE IF EXISTS `agent_life_record`;

CREATE TABLE `agent_life_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` smallint(6) NOT NULL,
  `agent_id` int(11) DEFAULT NULL,
  `life` bigint(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `agent_school` */

DROP TABLE IF EXISTS `agent_school`;

CREATE TABLE `agent_school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `a_id` int(11) DEFAULT NULL COMMENT '加盟商Id',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '学校名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `agent_user` */

DROP TABLE IF EXISTS `agent_user`;

CREATE TABLE `agent_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `passwd` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `album_alias` */

DROP TABLE IF EXISTS `album_alias`;

CREATE TABLE `album_alias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aliasName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `albumId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `all_msg` */

DROP TABLE IF EXISTS `all_msg`;

CREATE TABLE `all_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(125) DEFAULT NULL,
  `content` text,
  `status` tinyint(4) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `anchor` */

DROP TABLE IF EXISTS `anchor`;

CREATE TABLE `anchor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `logo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `summary` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `answer` */

DROP TABLE IF EXISTS `answer`;

CREATE TABLE `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answer` varchar(256) DEFAULT NULL,
  `insert_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `question_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `api_log` */

DROP TABLE IF EXISTS `api_log`;

CREATE TABLE `api_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_url` varchar(225) NOT NULL,
  `access_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `api_log_api_url` (`api_url`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `app` */

DROP TABLE IF EXISTS `app`;

CREATE TABLE `app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `insert_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `cover` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `app_fuc_show` */

DROP TABLE IF EXISTS `app_fuc_show`;

CREATE TABLE `app_fuc_show` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epal_id` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fuc_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_show` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `app_upgrade_config` */

DROP TABLE IF EXISTS `app_upgrade_config`;

CREATE TABLE `app_upgrade_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `environment` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `version` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `insert_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `appid_secret` */

DROP TABLE IF EXISTS `appid_secret`;

CREATE TABLE `appid_secret` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(30) DEFAULT NULL,
  `secret` varchar(50) DEFAULT NULL,
  `library_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_appid_secret` (`appid`,`secret`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `appindex` */

DROP TABLE IF EXISTS `appindex`;

CREATE TABLE `appindex` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `infoid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `appointnum` */

DROP TABLE IF EXISTS `appointnum`;

CREATE TABLE `appointnum` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `specific_num` int(11) DEFAULT '1',
  `mobile` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `apptime` */

DROP TABLE IF EXISTS `apptime`;

CREATE TABLE `apptime` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_no` varchar(225) DEFAULT NULL,
  `time` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `startdate` datetime DEFAULT NULL,
  `enddate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `area` */

DROP TABLE IF EXISTS `area`;

CREATE TABLE `area` (
  `id` varchar(15) NOT NULL,
  `code_a` varchar(15) NOT NULL,
  `name` varchar(20) NOT NULL,
  `code_c` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`code_a`),
  KEY `FK_Reference_9` (`code_c`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`code_c`) REFERENCES `city` (`code_c`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='该实体为行政区域划分等级';

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `source` varchar(200) DEFAULT NULL,
  `accountid` int(11) DEFAULT NULL,
  `parentid` int(11) DEFAULT NULL,
  `content` text,
  `releasedate` varchar(20) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `article_log` */

DROP TABLE IF EXISTS `article_log`;

CREATE TABLE `article_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(100) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `accountid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `audio_material_rel` */

DROP TABLE IF EXISTS `audio_material_rel`;

CREATE TABLE `audio_material_rel` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `audio_id` int(11) DEFAULT NULL,
  `material_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `audioinfo` */

DROP TABLE IF EXISTS `audioinfo`;

CREATE TABLE `audioinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `audioid` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `cover` varchar(100) DEFAULT NULL,
  `cn` varchar(100) DEFAULT NULL,
  `exp` varchar(100) DEFAULT NULL,
  `picrecog` varchar(100) DEFAULT NULL,
  `mediainfo` varchar(100) DEFAULT NULL,
  `src` varchar(100) DEFAULT NULL,
  `user_id` int(11) DEFAULT '1',
  `src2` varchar(200) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `audiinfo_name` (`name`) USING BTREE,
  KEY `audioid` (`audioid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `belong` */

DROP TABLE IF EXISTS `belong`;

CREATE TABLE `belong` (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `book_amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `bonein_user` */

DROP TABLE IF EXISTS `bonein_user`;

CREATE TABLE `bonein_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 NOT NULL,
  `gender` tinyint(2) DEFAULT '-1',
  `crecate_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `bonein_user_account` */

DROP TABLE IF EXISTS `bonein_user_account`;

CREATE TABLE `bonein_user_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) DEFAULT NULL,
  `password` varchar(125) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '1',
  `bonein_user_id` int(11) NOT NULL,
  `last_login` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `barcode` varchar(255) NOT NULL,
  `isexist` int(11) DEFAULT NULL,
  `cate_id` varchar(255) DEFAULT NULL,
  `belong` int(11) DEFAULT NULL,
  `url` varchar(300) DEFAULT NULL,
  `codeinfo` varchar(100) DEFAULT NULL,
  `copy_times` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`barcode`),
  KEY `FK2E3AE9D88E281E` (`belong`),
  KEY `FK2E3AE94E810AA9` (`cate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `book_category` */

DROP TABLE IF EXISTS `book_category`;

CREATE TABLE `book_category` (
  `cat_id` int(5) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(30) NOT NULL DEFAULT '',
  `cat_name` varchar(255) NOT NULL DEFAULT '',
  `keywords` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `parent_id` int(5) NOT NULL DEFAULT '0',
  `sort` int(1) unsigned NOT NULL DEFAULT '50',
  PRIMARY KEY (`cat_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `book_class_download_record` */

DROP TABLE IF EXISTS `book_class_download_record`;

CREATE TABLE `book_class_download_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grades_id` int(11) DEFAULT NULL,
  `class_room_ids` text,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `book_copy` */

DROP TABLE IF EXISTS `book_copy`;

CREATE TABLE `book_copy` (
  `barcode` varchar(255) NOT NULL,
  `isexist` int(11) DEFAULT NULL,
  `cate_id` varchar(255) DEFAULT NULL,
  `belong` int(11) DEFAULT NULL,
  `url` varchar(300) DEFAULT NULL,
  `codeinfo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`barcode`),
  KEY `FK2E3AE9D88E281E` (`belong`),
  KEY `FK2E3AE94E810AA9` (`cate_id`),
  CONSTRAINT `book_copy_ibfk_1` FOREIGN KEY (`cate_id`) REFERENCES `category_123` (`cateID`),
  CONSTRAINT `book_copy_ibfk_2` FOREIGN KEY (`belong`) REFERENCES `belong` (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `book_copy1` */

DROP TABLE IF EXISTS `book_copy1`;

CREATE TABLE `book_copy1` (
  `barcode` varchar(255) NOT NULL,
  `isexist` int(11) DEFAULT NULL,
  `cate_id` varchar(255) DEFAULT NULL,
  `belong` int(11) DEFAULT NULL,
  `url` varchar(300) DEFAULT NULL,
  `codeinfo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`barcode`),
  KEY `FK2E3AE9D88E281E` (`belong`),
  KEY `FK2E3AE94E810AA9` (`cate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `book_shop_category` */

DROP TABLE IF EXISTS `book_shop_category`;

CREATE TABLE `book_shop_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shopid` int(11) DEFAULT NULL,
  `categoryid` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `book_shop_user` */

DROP TABLE IF EXISTS `book_shop_user`;

CREATE TABLE `book_shop_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shopid` int(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `bookcard` */

DROP TABLE IF EXISTS `bookcard`;

CREATE TABLE `bookcard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) DEFAULT NULL,
  `card` varchar(100) DEFAULT NULL,
  `shopid` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `createdate` datetime NOT NULL,
  `year` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `bookcase` */

DROP TABLE IF EXISTS `bookcase`;

CREATE TABLE `bookcase` (
  `bcid` int(11) NOT NULL AUTO_INCREMENT,
  `mem_id` varchar(255) DEFAULT NULL,
  `cate_id` varchar(255) DEFAULT NULL,
  `isfinish` int(11) DEFAULT NULL,
  PRIMARY KEY (`bcid`),
  KEY `FK778319994E810AA9` (`cate_id`),
  KEY `FK77831999FE193FFF` (`mem_id`),
  CONSTRAINT `FK778319994E810AA9` FOREIGN KEY (`cate_id`) REFERENCES `category_123` (`cateID`),
  CONSTRAINT `FK77831999FE193FFF` FOREIGN KEY (`mem_id`) REFERENCES `member1` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `bookcollection` */

DROP TABLE IF EXISTS `bookcollection`;

CREATE TABLE `bookcollection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) NOT NULL,
  `cateid` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `bookexpress` */

DROP TABLE IF EXISTS `bookexpress`;

CREATE TABLE `bookexpress` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) DEFAULT NULL,
  `express` varchar(50) DEFAULT NULL,
  `expressnumber` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `bookindex` */

DROP TABLE IF EXISTS `bookindex`;

CREATE TABLE `bookindex` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `infoid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `bookkeyword` */

DROP TABLE IF EXISTS `bookkeyword`;

CREATE TABLE `bookkeyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `cateid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `booklabel` */

DROP TABLE IF EXISTS `booklabel`;

CREATE TABLE `booklabel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `labelId` int(11) DEFAULT NULL,
  `ios` int(11) DEFAULT NULL,
  `wechat` int(11) DEFAULT NULL,
  `android` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `bookorder` */

DROP TABLE IF EXISTS `bookorder`;

CREATE TABLE `bookorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ordernumber` varchar(50) DEFAULT NULL,
  `memberid` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `totalprice` double DEFAULT NULL,
  `freight` double DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `express` varchar(50) DEFAULT NULL,
  `expressnumber` varchar(50) DEFAULT NULL,
  `operatorid` int(11) DEFAULT NULL,
  `addressid` int(11) DEFAULT NULL,
  `deposit` double(11,0) DEFAULT NULL,
  `integral` int(11) DEFAULT NULL,
  `bookprice` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `bookorderinfo` */

DROP TABLE IF EXISTS `bookorderinfo`;

CREATE TABLE `bookorderinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `barcode` varchar(50) DEFAULT NULL,
  `orderid` int(11) DEFAULT NULL,
  `cateid` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `bookshop` */

DROP TABLE IF EXISTS `bookshop`;

CREATE TABLE `bookshop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `logo` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `account` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `memberCardPrice` float(10,2) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `contacts` varchar(500) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdate` datetime NOT NULL,
  `memberCardImage` varchar(255) DEFAULT NULL,
  `app_id` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `bookshop_banner` */

DROP TABLE IF EXISTS `bookshop_banner`;

CREATE TABLE `bookshop_banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `picture` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `product_id` int(11) DEFAULT '0',
  `type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `bookshop_order` */

DROP TABLE IF EXISTS `bookshop_order`;

CREATE TABLE `bookshop_order` (
  `oid` varchar(255) NOT NULL,
  `memid` varchar(255) DEFAULT NULL,
  `receiver` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `post` int(11) DEFAULT NULL,
  `ordertime` datetime DEFAULT NULL,
  `os_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `FK43E9A06E67474896` (`os_id`),
  KEY `FK43E9A06E448651EA` (`memid`),
  CONSTRAINT `FK43E9A06E448651EA` FOREIGN KEY (`memid`) REFERENCES `member1` (`username`) ON DELETE CASCADE,
  CONSTRAINT `FK43E9A06E67474896` FOREIGN KEY (`os_id`) REFERENCES `orderstatus` (`osid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `bookshop_orderdetail` */

DROP TABLE IF EXISTS `bookshop_orderdetail`;

CREATE TABLE `bookshop_orderdetail` (
  `odid` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(255) DEFAULT NULL,
  `cate_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`odid`),
  KEY `FK41892E3FF9221D5A` (`order_id`),
  KEY `FK41892E3F4E810AA9` (`cate_id`),
  CONSTRAINT `FK41892E3F4E810AA9` FOREIGN KEY (`cate_id`) REFERENCES `category_123` (`cateID`),
  CONSTRAINT `FK41892E3FF9221D5A` FOREIGN KEY (`order_id`) REFERENCES `bookshop_order` (`oid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `bookv` */

DROP TABLE IF EXISTS `bookv`;

CREATE TABLE `bookv` (
  `bookVId` varchar(20) NOT NULL,
  `bookVName` varchar(255) DEFAULT NULL,
  `bookId` varchar(20) DEFAULT NULL,
  `bookType` varchar(255) DEFAULT '',
  `album` varchar(255) DEFAULT NULL,
  `speaker` varchar(255) DEFAULT NULL,
  `savePath` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `downloadTimes` int(11) DEFAULT NULL,
  `recogKey` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `length` bigint(20) DEFAULT NULL,
  `language` tinyint(4) DEFAULT NULL,
  `shapecode_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bookVId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `bookvehicle` */

DROP TABLE IF EXISTS `bookvehicle`;

CREATE TABLE `bookvehicle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cateid` varchar(50) DEFAULT NULL,
  `memberid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `bulletin_question` */

DROP TABLE IF EXISTS `bulletin_question`;

CREATE TABLE `bulletin_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` text COLLATE utf8_unicode_ci,
  `question_script_content` text COLLATE utf8_unicode_ci,
  `member_id` int(11) DEFAULT '0',
  `student_id` int(11) NOT NULL,
  `reward_integral` int(11) DEFAULT '0',
  `is_close` int(2) DEFAULT '0',
  `status` int(2) DEFAULT '1',
  `b_reply` tinyint(2) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `bulletin_question_answer` */

DROP TABLE IF EXISTS `bulletin_question_answer`;

CREATE TABLE `bulletin_question_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT '0',
  `teacher_id` int(11) DEFAULT '0',
  `member_id` int(11) DEFAULT '0',
  `content` text COLLATE utf8_unicode_ci,
  `answer_script` text COLLATE utf8_unicode_ci,
  `is_adoption` tinyint(2) DEFAULT '0',
  `status` int(2) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `cachfolder` */

DROP TABLE IF EXISTS `cachfolder`;

CREATE TABLE `cachfolder` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `catalog` varchar(45) NOT NULL,
  `position` int(10) unsigned NOT NULL,
  `epalid` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `lastid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `care_feedback` */

DROP TABLE IF EXISTS `care_feedback`;

CREATE TABLE `care_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4,
  `picture` text COLLATE utf8_unicode_ci,
  `is_read` int(2) DEFAULT '0',
  `status` int(2) DEFAULT '1',
  `contact` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `care_sign_in` */

DROP TABLE IF EXISTS `care_sign_in`;

CREATE TABLE `care_sign_in` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `year` int(4) DEFAULT NULL,
  `week` tinyint(2) DEFAULT NULL,
  `day` tinyint(2) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `only_date` (`student_id`,`year`,`week`,`day`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `cateID` varchar(255) NOT NULL,
  `bname` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `publish` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `translator` varchar(255) DEFAULT NULL,
  `page` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `bquantity` int(11) DEFAULT NULL,
  `right_id` int(11) DEFAULT NULL,
  `series` varchar(255) DEFAULT NULL,
  `mp3` varchar(300) DEFAULT NULL,
  `testmp3` varchar(300) DEFAULT NULL,
  `catalog` varchar(2000) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `mp3type` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `book_cate_id` int(11) DEFAULT '0',
  PRIMARY KEY (`cateID`),
  KEY `FK302BCFEA24867B8` (`right_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `category_123` */

DROP TABLE IF EXISTS `category_123`;

CREATE TABLE `category_123` (
  `cateID` varchar(255) NOT NULL,
  `bname` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `publish` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `translator` varchar(255) DEFAULT NULL,
  `page` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `bquantity` int(11) DEFAULT NULL,
  `right_id` int(11) DEFAULT NULL,
  `series` varchar(255) DEFAULT NULL,
  `mp3` varchar(300) DEFAULT NULL,
  `testmp3` varchar(300) DEFAULT NULL,
  `catalog` varchar(2000) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `mp3type` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `book_cate_id` int(11) DEFAULT '0',
  PRIMARY KEY (`cateID`),
  KEY `FK302BCFEA24867B8` (`right_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `category_copy` */

DROP TABLE IF EXISTS `category_copy`;

CREATE TABLE `category_copy` (
  `cateID` varchar(255) NOT NULL,
  `bname` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `publish` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `translator` varchar(255) DEFAULT NULL,
  `page` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `bquantity` int(11) DEFAULT NULL,
  `right_id` int(11) DEFAULT NULL,
  `series` varchar(255) DEFAULT NULL,
  `mp3` varchar(300) DEFAULT NULL,
  `testmp3` varchar(300) DEFAULT NULL,
  `catalog` varchar(2000) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `mp3type` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `book_cate_id` int(11) DEFAULT '0',
  PRIMARY KEY (`cateID`),
  KEY `FK302BCFEA24867B8` (`right_id`),
  CONSTRAINT `category_copy_ibfk_1` FOREIGN KEY (`right_id`) REFERENCES `rightcategory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `categoryamount` */

DROP TABLE IF EXISTS `categoryamount`;

CREATE TABLE `categoryamount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `belong` int(11) DEFAULT NULL,
  `cate_id` varchar(255) DEFAULT NULL,
  `totality` int(11) DEFAULT NULL,
  `remain` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK752ED6D6D88E281E` (`belong`),
  KEY `FK752ED6D64E810AA9` (`cate_id`),
  CONSTRAINT `FK752ED6D64E810AA9` FOREIGN KEY (`cate_id`) REFERENCES `category_123` (`cateID`),
  CONSTRAINT `FK752ED6D6D88E281E` FOREIGN KEY (`belong`) REFERENCES `belong` (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `categorylabel` */

DROP TABLE IF EXISTS `categorylabel`;

CREATE TABLE `categorylabel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categoryid` varchar(20) DEFAULT NULL,
  `labelid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `categorylabel_copy` */

DROP TABLE IF EXISTS `categorylabel_copy`;

CREATE TABLE `categorylabel_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categoryid` varchar(20) DEFAULT NULL,
  `labelid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `chaozhi_order` */

DROP TABLE IF EXISTS `chaozhi_order`;

CREATE TABLE `chaozhi_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `mobile` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `open_id` varchar(28) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(3) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `chaozhi_study_friend` */

DROP TABLE IF EXISTS `chaozhi_study_friend`;

CREATE TABLE `chaozhi_study_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member1` int(11) NOT NULL,
  `member2` int(11) NOT NULL,
  `status` int(2) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `city` */

DROP TABLE IF EXISTS `city`;

CREATE TABLE `city` (
  `id` varchar(15) NOT NULL,
  `code_c` varchar(15) NOT NULL,
  `name` varchar(20) NOT NULL,
  `code_p` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`code_c`),
  KEY `FK_Reference_10` (`code_p`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`code_p`) REFERENCES `province` (`code_p`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='该实体为行政区域划分等级';

/*Table structure for table `class_course` */

DROP TABLE IF EXISTS `class_course`;

CREATE TABLE `class_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `do_title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `do_slot` int(11) DEFAULT NULL,
  `do_day` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  `class_grades_id` int(11) DEFAULT NULL,
  `cover` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `do_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `difficulty_level` int(3) DEFAULT '0',
  `live_dodate` datetime DEFAULT NULL COMMENT '直播课上课时间',
  `take_picture` tinyint(6) DEFAULT '0',
  `live_mode` tinyint(6) DEFAULT '0',
  `b_live` tinyint(3) DEFAULT '0',
  `take_time` tinyint(3) DEFAULT '0',
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `work_time` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `class_grades_id` (`class_grades_id`) USING BTREE,
  KEY `class_room_id` (`class_room_id`) USING BTREE,
  KEY `do_slot` (`do_slot`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_comment` */

DROP TABLE IF EXISTS `class_course_comment`;

CREATE TABLE `class_course_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_course_id` int(11) DEFAULT NULL,
  `teacher_sound` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `teacher_score` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `comment_teacher_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `insert_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `effective_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `union` (`class_course_id`,`student_id`) USING BTREE,
  KEY `class_course_comment_class_course_record_id` (`class_course_id`) USING BTREE,
  KEY `class_course_comment_student_id` (`student_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_copy1` */

DROP TABLE IF EXISTS `class_course_copy1`;

CREATE TABLE `class_course_copy1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `do_title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `do_slot` int(11) DEFAULT NULL,
  `do_day` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  `class_grades_id` int(11) DEFAULT NULL,
  `cover` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `do_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `class_grades_id` (`class_grades_id`) USING BTREE,
  KEY `class_room_id` (`class_room_id`) USING BTREE,
  KEY `do_slot` (`do_slot`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `class_course_error_word` */

DROP TABLE IF EXISTS `class_course_error_word`;

CREATE TABLE `class_course_error_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `class_course_record_id` int(11) DEFAULT NULL,
  `word` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_ranking_category` */

DROP TABLE IF EXISTS `class_course_ranking_category`;

CREATE TABLE `class_course_ranking_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `end_time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `class_grades_ids` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_ranking_list` */

DROP TABLE IF EXISTS `class_course_ranking_list`;

CREATE TABLE `class_course_ranking_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studentId` int(11) DEFAULT NULL,
  `studentName` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `epalId` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `totalTimeCost` int(11) DEFAULT NULL,
  `totalScore` int(11) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `perScore` int(11) DEFAULT NULL,
  `startTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `endTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `classGradesList` varchar(255) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `read_status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_record` */

DROP TABLE IF EXISTS `class_course_record`;

CREATE TABLE `class_course_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `class_grades_id` int(11) DEFAULT NULL,
  `class_course_id` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  `complete` int(11) DEFAULT '0',
  `score` varchar(255) COLLATE utf8_unicode_ci DEFAULT '0',
  `do_title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `used_time` int(11) DEFAULT '0',
  `do_sort` int(11) DEFAULT '-1' COMMENT '当前指令',
  `progress` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '指令进度',
  `complete_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`) USING BTREE,
  KEY `class_grades_id` (`class_grades_id`) USING BTREE,
  KEY `class_room_id` (`class_room_id`) USING BTREE,
  KEY `class_corse_id` (`class_course_id`) USING HASH,
  KEY `class_course_record_complete_time` (`complete_time`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_record_all_integral` */

DROP TABLE IF EXISTS `class_course_record_all_integral`;

CREATE TABLE `class_course_record_all_integral` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `all_integral` int(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_record_integral` */

DROP TABLE IF EXISTS `class_course_record_integral`;

CREATE TABLE `class_course_record_integral` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(255) DEFAULT NULL,
  `class_course_id` int(255) DEFAULT NULL,
  `integral` int(255) DEFAULT NULL,
  `used_time` int(11) DEFAULT '0',
  `carete_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_record_of_student` */

DROP TABLE IF EXISTS `class_course_record_of_student`;

CREATE TABLE `class_course_record_of_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_course_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `content` varchar(1024) CHARACTER SET utf8mb4 DEFAULT NULL,
  `images` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `insert_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_record_of_teacher` */

DROP TABLE IF EXISTS `class_course_record_of_teacher`;

CREATE TABLE `class_course_record_of_teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_course_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `images` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `insert_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_reply` */

DROP TABLE IF EXISTS `class_course_reply`;

CREATE TABLE `class_course_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grades_id` int(11) DEFAULT NULL,
  `record_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `reply_text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reply_voice` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `student_voice` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reply_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_schedule` */

DROP TABLE IF EXISTS `class_course_schedule`;

CREATE TABLE `class_course_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `class_course_id` int(11) DEFAULT NULL,
  `class_grades_id` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `last_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `do_day` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `class_course_schedule_union` (`student_id`,`class_grades_id`,`class_course_id`,`class_room_id`) USING BTREE,
  KEY `student_id` (`student_id`) USING BTREE,
  KEY `class_grades_id` (`class_grades_id`) USING BTREE,
  KEY `class_course_id` (`class_course_id`) USING BTREE,
  KEY `class_room_id` (`class_room_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_score` */

DROP TABLE IF EXISTS `class_course_score`;

CREATE TABLE `class_course_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_course_record_id` int(11) NOT NULL,
  `score` tinyint(4) DEFAULT NULL,
  `audio` varchar(225) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_score_record` */

DROP TABLE IF EXISTS `class_course_score_record`;

CREATE TABLE `class_course_score_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classCourseRecord_id` int(11) DEFAULT NULL,
  `score` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `audioUrl` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `insertDate` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `recordDate` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `accuracy` varchar(255) COLLATE utf8_unicode_ci DEFAULT '0',
  `integrity` varchar(255) COLLATE utf8_unicode_ci DEFAULT '0',
  `fluency` varchar(255) COLLATE utf8_unicode_ci DEFAULT '0',
  `timeCost` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `wordList` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_course_time_limit` */

DROP TABLE IF EXISTS `class_course_time_limit`;

CREATE TABLE `class_course_time_limit` (
  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `student_id` int(9) DEFAULT NULL COMMENT '学生id',
  `class_grades_id` int(9) DEFAULT NULL COMMENT '班级id',
  `class_course_id` int(9) DEFAULT NULL COMMENT '课程id',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_grade_code` */

DROP TABLE IF EXISTS `class_grade_code`;

CREATE TABLE `class_grade_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_grade_id` int(11) NOT NULL,
  `code` varchar(32) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_grades` */

DROP TABLE IF EXISTS `class_grades`;

CREATE TABLE `class_grades` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `class_grades_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '班级名称',
  `parent_id` int(11) DEFAULT NULL,
  `summary` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '简介',
  `cover` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '海报',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '班级创建时间',
  `grades_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '班级类型',
  `teacher_id` int(11) DEFAULT '0' COMMENT '所属老师ID',
  `status` int(11) DEFAULT '1' COMMENT '班级状态',
  `auditing_status` int(11) DEFAULT '1' COMMENT '审核状态',
  `price` int(11) DEFAULT '0' COMMENT '价格',
  `classOpenDate` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '开班时间',
  `joinStatus` int(11) DEFAULT '1' COMMENT '是否启用开班限制',
  `limit_count` int(5) DEFAULT NULL COMMENT '限制人数',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `show_in_eova` tinyint(4) DEFAULT '1' COMMENT '是否在后台管理系统中显示：1显示0不显示',
  `classroom_pack_id` int(11) DEFAULT NULL COMMENT '班级绑定课程包的id',
  `class_schedule_version` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '课程表版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_grades_rela` */

DROP TABLE IF EXISTS `class_grades_rela`;

CREATE TABLE `class_grades_rela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_grades_id` int(11) DEFAULT NULL,
  `class_student_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `gradesStatus` int(11) DEFAULT '1',
  `type` int(3) DEFAULT '0' COMMENT '关系类型：0:普通，1:重点',
  `progress` tinyint(4) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `class_grades_rela_studentId_union` (`class_grades_id`,`class_student_id`) USING BTREE,
  KEY `class_grades_rela_studentId` (`class_student_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_hourse` */

DROP TABLE IF EXISTS `class_hourse`;

CREATE TABLE `class_hourse` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `hourse_name` varchar(255) DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_live_booking` */

DROP TABLE IF EXISTS `class_live_booking`;

CREATE TABLE `class_live_booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'care直播课预约',
  `schedule_id` int(11) DEFAULT NULL COMMENT '课程表id(表class_course的ID)：Care传参，用于记录是在哪节课触发的预约',
  `student_id` int(11) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `appoint_time` varchar(200) DEFAULT NULL COMMENT '预约时间',
  `spare_time` varchar(200) DEFAULT NULL COMMENT '备选时间',
  `spare_time2` varchar(200) DEFAULT NULL COMMENT '备选时间2',
  `class_time` varchar(200) DEFAULT NULL COMMENT '确定时间',
  `teacher_name` varchar(30) DEFAULT NULL COMMENT '老师名字',
  `class_account` varchar(255) DEFAULT NULL COMMENT '登录账号',
  `class_password` varchar(255) DEFAULT NULL COMMENT '密码',
  `status` int(2) DEFAULT '1',
  `school_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_package_order` */

DROP TABLE IF EXISTS `class_package_order`;

CREATE TABLE `class_package_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `order_number` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `class_pack_id` int(11) DEFAULT '0',
  `class_grade_id` int(11) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_product_order` */

DROP TABLE IF EXISTS `class_product_order`;

CREATE TABLE `class_product_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `product_name` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL,
  `order_number` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `class_pack_id` int(11) DEFAULT '0',
  `class_grades_id` int(11) DEFAULT '0',
  `class_booking` int(11) DEFAULT '0',
  `price` decimal(5,2) DEFAULT '0.00',
  `pay_member_id` int(11) DEFAULT '0' COMMENT '他人支付的member_id',
  `status` int(2) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_qr_info` */

DROP TABLE IF EXISTS `class_qr_info`;

CREATE TABLE `class_qr_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(3) DEFAULT '1' COMMENT '状态：0=已删除，1=待添加，2=已添加',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` int(1) DEFAULT NULL,
  `partner_id` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `sort` int(5) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `text` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room` */

DROP TABLE IF EXISTS `class_room`;

CREATE TABLE `class_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT '0',
  `teacher_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `class_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cover` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `summary` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `sort` int(11) DEFAULT '0',
  `category_id` int(11) NOT NULL,
  `book_res_id` int(11) DEFAULT NULL,
  `group_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `class_room_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '课堂类型（默认为音频：audio,video）',
  `video_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '视频地址',
  `type` int(3) DEFAULT NULL COMMENT '主辅课标志：1=主课；2=辅课；3=主辅课皆可',
  `book_res_ids` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `difficulty_level` int(11) DEFAULT NULL,
  `original` int(11) DEFAULT '0' COMMENT '是否原创：0=原创，非0为原创课堂id',
  `member_account_id` int(11) DEFAULT '0',
  `credit` int(11) DEFAULT '0',
  `classroom_type` tinyint(6) DEFAULT '1' COMMENT '1=普通课,2=直播课',
  `live_status` tinyint(4) DEFAULT '0',
  `length` int(11) DEFAULT '0',
  `subject_id` int(11) DEFAULT '1',
  `b_visible` tinyint(1) DEFAULT '0',
  `alive_form` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `class_room_teacher_id` (`teacher_id`),
  KEY `class_room_class_name` (`class_name`),
  KEY `class_room_category` (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_audio_rel` */

DROP TABLE IF EXISTS `class_room_audio_rel`;

CREATE TABLE `class_room_audio_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `audio_id` int(11) DEFAULT NULL,
  `material_file_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `classroom_id` (`class_room_id`),
  KEY `audio_id` (`audio_id`) USING BTREE,
  KEY `material_file_id` (`material_file_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_category` */

DROP TABLE IF EXISTS `class_room_category`;

CREATE TABLE `class_room_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `summary` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cover` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_category_real` */

DROP TABLE IF EXISTS `class_room_category_real`;

CREATE TABLE `class_room_category_real` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) NOT NULL,
  `ceategory_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_comment` */

DROP TABLE IF EXISTS `class_room_comment`;

CREATE TABLE `class_room_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci DEFAULT '1',
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `insert_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '0',
  `score` int(11) DEFAULT '0',
  `image_urls` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `teacher_score` int(11) DEFAULT '0',
  `class_room_score` int(11) DEFAULT '0',
  `is_anonymous` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_grades_rela` */

DROP TABLE IF EXISTS `class_room_grades_rela`;

CREATE TABLE `class_room_grades_rela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `class_grades_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_label` */

DROP TABLE IF EXISTS `class_room_label`;

CREATE TABLE `class_room_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '标签名称',
  `sort_id` int(11) DEFAULT '0' COMMENT '排序ID',
  `group_id` int(11) DEFAULT '0' COMMENT '分组ID',
  `is_show` varchar(255) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '是否显示',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_label_rela` */

DROP TABLE IF EXISTS `class_room_label_rela`;

CREATE TABLE `class_room_label_rela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label_id` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `label` (`label_id`,`class_room_id`) USING BTREE,
  KEY `classroom_id` (`class_room_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_label_user` */

DROP TABLE IF EXISTS `class_room_label_user`;

CREATE TABLE `class_room_label_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_like` */

DROP TABLE IF EXISTS `class_room_like`;

CREATE TABLE `class_room_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `insert_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `class_room_order` */

DROP TABLE IF EXISTS `class_room_order`;

CREATE TABLE `class_room_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_pack_id` int(11) DEFAULT '0',
  `class_room_id` int(11) DEFAULT '0',
  `member_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `order_number` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `status` int(3) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_product_rel` */

DROP TABLE IF EXISTS `class_room_product_rel`;

CREATE TABLE `class_room_product_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_push` */

DROP TABLE IF EXISTS `class_room_push`;

CREATE TABLE `class_room_push` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `epal_id` longtext COLLATE utf8_unicode_ci,
  `class_room_script` text COLLATE utf8_unicode_ci,
  `cmd_do_status` int(11) DEFAULT NULL,
  `app_account` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `group_id` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_record` */

DROP TABLE IF EXISTS `class_room_record`;

CREATE TABLE `class_room_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `content` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `class_room_reply` */

DROP TABLE IF EXISTS `class_room_reply`;

CREATE TABLE `class_room_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `text` text,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_reply_pic` */

DROP TABLE IF EXISTS `class_room_reply_pic`;

CREATE TABLE `class_room_reply_pic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_reply_id` int(11) DEFAULT NULL,
  `pic` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_sign` */

DROP TABLE IF EXISTS `class_room_sign`;

CREATE TABLE `class_room_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `epal_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `pushId` varchar(255) COLLATE utf8_unicode_ci DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_statistics` */

DROP TABLE IF EXISTS `class_room_statistics`;

CREATE TABLE `class_room_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `class_number` int(11) DEFAULT NULL,
  `attendance_number` int(11) DEFAULT NULL,
  `complete_number` int(11) DEFAULT NULL,
  `update_time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `classRoomName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `teacherName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_temp` */

DROP TABLE IF EXISTS `class_room_temp`;

CREATE TABLE `class_room_temp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT NULL,
  `teacher_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `class_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cover` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `summary` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `sort` int(11) DEFAULT '0',
  `category_id` int(11) DEFAULT NULL,
  `book_res_id` int(11) DEFAULT NULL,
  `group_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_room_words_filter` */

DROP TABLE IF EXISTS `class_room_words_filter`;

CREATE TABLE `class_room_words_filter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `words` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `class_room_words` (`class_room_id`,`words`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_script_done` */

DROP TABLE IF EXISTS `class_script_done`;

CREATE TABLE `class_script_done` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `class_script_type_id` int(11) DEFAULT NULL,
  `class_script_content` text COLLATE utf8_unicode_ci,
  `reply` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `feedback` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `epal_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `student_id` int(11) DEFAULT '0',
  `class_course_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `class_script_done_epalId` (`epal_id`) USING HASH,
  KEY `class_room_id` (`class_room_id`) USING HASH,
  KEY `create_time` (`create_time`) USING HASH,
  KEY `student_id` (`student_id`) USING BTREE,
  KEY `class_course_id` (`class_course_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_script_group_to_type` */

DROP TABLE IF EXISTS `class_script_group_to_type`;

CREATE TABLE `class_script_group_to_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `insert_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_script_normal` */

DROP TABLE IF EXISTS `class_script_normal`;

CREATE TABLE `class_script_normal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `class_script_type_id` int(11) DEFAULT NULL,
  `class_script_content` text COLLATE utf8_unicode_ci,
  `sort` int(11) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `total_time` int(11) DEFAULT '0',
  `live_status` tinyint(4) DEFAULT '-1' COMMENT '直播指令开关：-1=关',
  PRIMARY KEY (`id`),
  KEY `class_room_id` (`class_room_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_script_normal_copy` */

DROP TABLE IF EXISTS `class_script_normal_copy`;

CREATE TABLE `class_script_normal_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `class_script_type_id` int(11) DEFAULT NULL,
  `class_script_content` text COLLATE utf8_unicode_ci,
  `sort` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `total_time` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `class_room_id` (`class_room_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_script_normal_copy1` */

DROP TABLE IF EXISTS `class_script_normal_copy1`;

CREATE TABLE `class_script_normal_copy1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `class_script_type_id` int(11) DEFAULT NULL,
  `class_script_content` text COLLATE utf8_unicode_ci,
  `sort` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `total_time` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `class_room_id` (`class_room_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_script_normal_temp` */

DROP TABLE IF EXISTS `class_script_normal_temp`;

CREATE TABLE `class_script_normal_temp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `class_script_type_id` int(11) DEFAULT NULL,
  `class_script_content` text COLLATE utf8_unicode_ci,
  `sort` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_script_normal_version` */

DROP TABLE IF EXISTS `class_script_normal_version`;

CREATE TABLE `class_script_normal_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `class_room_id` (`class_room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_script_type` */

DROP TABLE IF EXISTS `class_script_type`;

CREATE TABLE `class_script_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '1',
  `groupName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sort` tinyint(2) DEFAULT '1',
  `groupId` int(11) DEFAULT NULL,
  `des` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_script_type_group` */

DROP TABLE IF EXISTS `class_script_type_group`;

CREATE TABLE `class_script_type_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `group_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `sort_id` int(11) DEFAULT NULL,
  `insert_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_script_type_mutex` */

DROP TABLE IF EXISTS `class_script_type_mutex`;

CREATE TABLE `class_script_type_mutex` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id1` int(11) DEFAULT NULL,
  `type_id2` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_slot` */

DROP TABLE IF EXISTS `class_slot`;

CREATE TABLE `class_slot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `epal_id` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `do_time` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `do_slot` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `epalId` (`epal_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_student` */

DROP TABLE IF EXISTS `class_student`;

CREATE TABLE `class_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '学生名字',
  `epal_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '机器人账号',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `student_type` int(255) DEFAULT '1' COMMENT '学生类型',
  `sort_id` int(11) DEFAULT '2' COMMENT '排序ID',
  `integral` int(11) DEFAULT '0' COMMENT '积分',
  `contribution` int(11) DEFAULT '0' COMMENT '贡献值',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `member_id` int(11) DEFAULT '0',
  `degree_of_difficulty` float DEFAULT '1',
  `avatar` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(3) DEFAULT '1' COMMENT '删除状态',
  `lesson_integral` double(11,2) DEFAULT '0.00' COMMENT '课堂积分',
  `agent_id` int(11) DEFAULT '0',
  `sex` tinyint(2) DEFAULT '0',
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `class_student_epalId` (`epal_id`) USING HASH,
  KEY `class_student_id` (`id`) USING BTREE,
  KEY `agent_id` (`agent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_student_copy` */

DROP TABLE IF EXISTS `class_student_copy`;

CREATE TABLE `class_student_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `epal_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_student_rela` */

DROP TABLE IF EXISTS `class_student_rela`;

CREATE TABLE `class_student_rela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  `class_student_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `class_student_rela_class_student_id` (`class_student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_teacher` */

DROP TABLE IF EXISTS `class_teacher`;

CREATE TABLE `class_teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '老师名字',
  `member_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账号ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `level` int(11) DEFAULT '1' COMMENT '等级',
  `agent_id` int(11) DEFAULT '1' COMMENT '所属加盟商',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `rfid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dec` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cover` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `class_teacher_grades` */

DROP TABLE IF EXISTS `class_teacher_grades`;

CREATE TABLE `class_teacher_grades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_teacher_id` int(11) DEFAULT NULL,
  `class_grades_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `teacher_grades` (`class_teacher_id`,`class_grades_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `classroom_invite_record` */

DROP TABLE IF EXISTS `classroom_invite_record`;

CREATE TABLE `classroom_invite_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recommend_member_id` int(11) DEFAULT NULL COMMENT '推荐人id',
  `member_id` int(11) DEFAULT NULL COMMENT '受邀用户id',
  `class_room_id` int(11) DEFAULT NULL COMMENT '通过那节课邀请',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sole_recommend` (`recommend_member_id`,`member_id`,`class_room_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `classroom_package` */

DROP TABLE IF EXISTS `classroom_package`;

CREATE TABLE `classroom_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `origin` int(11) DEFAULT '0',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  `cover` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `summary` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '简介',
  `teacher_id` int(11) DEFAULT '0' COMMENT '0',
  `school_id` int(11) DEFAULT '0' COMMENT '0',
  `integral_support` int(3) DEFAULT '0' COMMENT '是否支持积分抵扣',
  `do_day_switch` tinyint(1) DEFAULT '0' COMMENT '课程表doday是否按课程包的设置，1为开启',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(3) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `classroom_package_grades` */

DROP TABLE IF EXISTS `classroom_package_grades`;

CREATE TABLE `classroom_package_grades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pack_id` int(11) NOT NULL,
  `grades_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `classroom_package_rel` */

DROP TABLE IF EXISTS `classroom_package_rel`;

CREATE TABLE `classroom_package_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_id` int(11) DEFAULT NULL,
  `classroom_id` int(11) DEFAULT NULL,
  `is_free` int(2) DEFAULT '0',
  `do_slot` smallint(6) DEFAULT NULL,
  `do_day` smallint(6) DEFAULT NULL,
  `credit` int(11) DEFAULT '0',
  `version` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `package_id` (`package_id`),
  KEY `classroom_id` (`classroom_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `classroom_subject` */

DROP TABLE IF EXISTS `classroom_subject`;

CREATE TABLE `classroom_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(125) NOT NULL,
  `status` tinyint(2) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `coin_gift` */

DROP TABLE IF EXISTS `coin_gift`;

CREATE TABLE `coin_gift` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL COMMENT '活动名称',
  `desc` varchar(255) DEFAULT NULL COMMENT '活动简介',
  `times` tinyint(4) DEFAULT NULL,
  `coin_count` int(11) NOT NULL DEFAULT '0' COMMENT '豆币',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `collection` */

DROP TABLE IF EXISTS `collection`;

CREATE TABLE `collection` (
  `mem_id` varchar(255) NOT NULL,
  `cate_id` varchar(255) NOT NULL,
  PRIMARY KEY (`mem_id`,`cate_id`),
  KEY `FK9835AE9E4E810AA9` (`cate_id`),
  KEY `FK9835AE9EFE193FFF` (`mem_id`),
  CONSTRAINT `FK9835AE9E4E810AA9` FOREIGN KEY (`cate_id`) REFERENCES `category_123` (`cateID`),
  CONSTRAINT `FK9835AE9EFE193FFF` FOREIGN KEY (`mem_id`) REFERENCES `member1` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  `memberid` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `star` int(11) DEFAULT NULL,
  `cateid` varchar(11) DEFAULT NULL,
  `fraction` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `comment1` */

DROP TABLE IF EXISTS `comment1`;

CREATE TABLE `comment1` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `imgpath` varchar(255) DEFAULT NULL,
  `mem_id` varchar(255) DEFAULT NULL,
  `cate_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cid`),
  KEY `FK38A5EE5F4E810AA9` (`cate_id`),
  KEY `FK38A5EE5FFE193FFF` (`mem_id`),
  CONSTRAINT `FK38A5EE5F4E810AA9` FOREIGN KEY (`cate_id`) REFERENCES `category_123` (`cateID`),
  CONSTRAINT `FK38A5EE5FFE193FFF` FOREIGN KEY (`mem_id`) REFERENCES `member1` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `commentimg` */

DROP TABLE IF EXISTS `commentimg`;

CREATE TABLE `commentimg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commentid` int(11) NOT NULL,
  `img` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `common_dict` */

DROP TABLE IF EXISTS `common_dict`;

CREATE TABLE `common_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` int(11) NOT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `createdate` datetime NOT NULL,
  `memberid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `coupons` */

DROP TABLE IF EXISTS `coupons`;

CREATE TABLE `coupons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `money` int(11) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `content` varchar(200) DEFAULT NULL,
  `endDate` datetime NOT NULL,
  `createDate` datetime NOT NULL,
  `url` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `couponsinfo` */

DROP TABLE IF EXISTS `couponsinfo`;

CREATE TABLE `couponsinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `couponsid` int(11) DEFAULT NULL,
  `memberid` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `url` varchar(300) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  `total_class` int(11) unsigned zerofill DEFAULT '00000000000',
  PRIMARY KEY (`id`),
  KEY `productid` (`productid`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `course_booklib` */

DROP TABLE IF EXISTS `course_booklib`;

CREATE TABLE `course_booklib` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  `bookname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bookcover` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bookisbn` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `course_copy` */

DROP TABLE IF EXISTS `course_copy`;

CREATE TABLE `course_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `url` varchar(300) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  `total_class` int(11) unsigned zerofill DEFAULT '00000000000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `course_period` */

DROP TABLE IF EXISTS `course_period`;

CREATE TABLE `course_period` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) DEFAULT NULL,
  `courseperiod_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `missionCmdList` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `course_plan` */

DROP TABLE IF EXISTS `course_plan`;

CREATE TABLE `course_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plan_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `epalid` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `memberid` int(11) DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `sort` int(11) DEFAULT NULL,
  `summary` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `course_plan_info` */

DROP TABLE IF EXISTS `course_plan_info`;

CREATE TABLE `course_plan_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plan_id` int(11) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  `courseid` int(11) DEFAULT NULL,
  `periodid` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `summary` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `course_schedule` */

DROP TABLE IF EXISTS `course_schedule`;

CREATE TABLE `course_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_no` varchar(225) DEFAULT NULL,
  `periodid` int(11) DEFAULT NULL,
  `courseid` int(11) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  `schedule` varchar(225) DEFAULT NULL,
  `cur_class` int(11) DEFAULT NULL,
  `cus_file` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `epal_id` (`epal_id`) USING HASH,
  KEY `productid` (`productid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `course_schedule_now` */

DROP TABLE IF EXISTS `course_schedule_now`;

CREATE TABLE `course_schedule_now` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `courseid` int(11) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  `schedule` varchar(225) DEFAULT NULL,
  `cur_class` int(11) DEFAULT NULL,
  `cus_file` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `course_word` */

DROP TABLE IF EXISTS `course_word`;

CREATE TABLE `course_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word_txt` varchar(255) DEFAULT NULL COMMENT '单词名称',
  `word_audio` varchar(255) DEFAULT NULL COMMENT '单词音频',
  `word_pic` varchar(255) DEFAULT NULL COMMENT '单词实物图片',
  `audioexplain` varchar(255) DEFAULT NULL COMMENT '单词解释音频',
  `audiorepeat` varchar(255) DEFAULT NULL COMMENT '跟读音频',
  `status` int(11) DEFAULT '0',
  `txtexplain` varchar(255) DEFAULT NULL COMMENT '中文解释',
  `createtime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `sound` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `course_word_label` */

DROP TABLE IF EXISTS `course_word_label`;

CREATE TABLE `course_word_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label_en` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `label_cn` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `group_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `sound` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `course_word_to_label` */

DROP TABLE IF EXISTS `course_word_to_label`;

CREATE TABLE `course_word_to_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word_id` int(11) DEFAULT NULL,
  `label_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `courseproject` */

DROP TABLE IF EXISTS `courseproject`;

CREATE TABLE `courseproject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectname` varchar(100) DEFAULT NULL,
  `epalId` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `memberid` int(11) DEFAULT NULL,
  `createdate` datetime NOT NULL,
  `sort` int(11) DEFAULT NULL,
  `system_plan` int(11) DEFAULT '-1',
  `plan_type` varchar(11) DEFAULT NULL,
  `recordcount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `courseproject_active` */

DROP TABLE IF EXISTS `courseproject_active`;

CREATE TABLE `courseproject_active` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qrcode_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qrcode_content` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `plan_id` int(11) DEFAULT NULL,
  `plan_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `active_count` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `courseproject_system` */

DROP TABLE IF EXISTS `courseproject_system`;

CREATE TABLE `courseproject_system` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lessonList` varchar(255) DEFAULT NULL,
  `plan_type` varchar(100) DEFAULT NULL,
  `projectname` varchar(100) DEFAULT NULL,
  `createdate` datetime NOT NULL,
  `sort` int(11) DEFAULT NULL,
  `active` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `courseprojectinfo` */

DROP TABLE IF EXISTS `courseprojectinfo`;

CREATE TABLE `courseprojectinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) NOT NULL,
  `courseid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `curriculumaudio` */

DROP TABLE IF EXISTS `curriculumaudio`;

CREATE TABLE `curriculumaudio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `curriculumid` int(11) DEFAULT NULL,
  `audioid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `daily_recommend_date` */

DROP TABLE IF EXISTS `daily_recommend_date`;

CREATE TABLE `daily_recommend_date` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `intro` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createTime` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `date_name_sort` (`dateName`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `daily_recommend_source` */

DROP TABLE IF EXISTS `daily_recommend_source`;

CREATE TABLE `daily_recommend_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sourceId` int(11) DEFAULT NULL,
  `tagId` int(11) DEFAULT NULL,
  `sortId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createTime` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tag_id` (`tagId`) USING BTREE,
  KEY `source_id` (`sourceId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `daily_recommend_tag` */

DROP TABLE IF EXISTS `daily_recommend_tag`;

CREATE TABLE `daily_recommend_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tagName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `intro` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dateId` int(11) DEFAULT NULL,
  `createTime` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sortId` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `dateId` (`dateId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `desk_capture` */

DROP TABLE IF EXISTS `desk_capture`;

CREATE TABLE `desk_capture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `grade_id` int(11) DEFAULT NULL,
  `pics` text,
  `type` tinyint(4) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device` */

DROP TABLE IF EXISTS `device`;

CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(225) DEFAULT NULL,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `sn` varchar(255) DEFAULT NULL,
  `epal_pwd` varchar(225) DEFAULT NULL,
  `nickname` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '机器人昵称',
  `device_type` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `device_used_type` varchar(255) DEFAULT '1' COMMENT '机器人用途（常规 1/电教室 2）',
  `version` varchar(255) DEFAULT '1',
  `status` int(11) DEFAULT '0',
  `is_free` varchar(255) DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  `color` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `epal_id` (`epal_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_activity` */

DROP TABLE IF EXISTS `device_activity`;

CREATE TABLE `device_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deviceNo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `epalId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `activityTime` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_agent_relation` */

DROP TABLE IF EXISTS `device_agent_relation`;

CREATE TABLE `device_agent_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `epal_id` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_category` */

DROP TABLE IF EXISTS `device_category`;

CREATE TABLE `device_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_category_to_device` */

DROP TABLE IF EXISTS `device_category_to_device`;

CREATE TABLE `device_category_to_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_collection` */

DROP TABLE IF EXISTS `device_collection`;

CREATE TABLE `device_collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(225) DEFAULT NULL,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `audio_id` varchar(225) DEFAULT NULL,
  `audio_name` varchar(225) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `res_from` varchar(225) DEFAULT NULL,
  `url` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_download_record` */

DROP TABLE IF EXISTS `device_download_record`;

CREATE TABLE `device_download_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epal_id` varchar(125) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL,
  `status` int(2) DEFAULT '0' COMMENT '1:下载成功',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `epal_bookId_student` (`epal_id`,`book_id`,`student_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_file` */

DROP TABLE IF EXISTS `device_file`;

CREATE TABLE `device_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `file_url` varchar(225) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `file_path` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_liber` */

DROP TABLE IF EXISTS `device_liber`;

CREATE TABLE `device_liber` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `file_name` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image_url` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `music_url` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_date` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_online_record` */

DROP TABLE IF EXISTS `device_online_record`;

CREATE TABLE `device_online_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epal_id` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `epal_id` (`epal_id`) USING HASH,
  KEY `time` (`time`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_online_record_count` */

DROP TABLE IF EXISTS `device_online_record_count`;

CREATE TABLE `device_online_record_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_count` int(11) DEFAULT NULL,
  `online_count` int(11) DEFAULT NULL,
  `insert_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_online_record_new` */

DROP TABLE IF EXISTS `device_online_record_new`;

CREATE TABLE `device_online_record_new` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epal_id` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `epal_id` (`epal_id`) USING HASH,
  KEY `time` (`time`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `device_playrecord` */

DROP TABLE IF EXISTS `device_playrecord`;

CREATE TABLE `device_playrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(225) DEFAULT NULL,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `audio_id` varchar(225) DEFAULT NULL,
  `audio_name` varchar(225) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `from` varchar(225) DEFAULT NULL,
  `lastid` varchar(225) DEFAULT NULL,
  `nextid` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_property` */

DROP TABLE IF EXISTS `device_property`;

CREATE TABLE `device_property` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(225) NOT NULL,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `property_key` varchar(225) DEFAULT NULL,
  `property_value` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_relation` */

DROP TABLE IF EXISTS `device_relation`;

CREATE TABLE `device_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(225) DEFAULT NULL,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `friend_id` varchar(225) DEFAULT NULL,
  `friend_name` varchar(225) DEFAULT NULL,
  `role` varchar(225) DEFAULT NULL,
  `isbind` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `test` (`epal_id`,`friend_id`),
  KEY `friend` (`friend_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_schedule` */

DROP TABLE IF EXISTS `device_schedule`;

CREATE TABLE `device_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `device_no` varchar(225) DEFAULT NULL,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `event_cn` varchar(255) DEFAULT NULL,
  `event` varchar(225) DEFAULT NULL,
  `note` varchar(225) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `period` varchar(20) DEFAULT NULL,
  `type` varchar(225) DEFAULT NULL,
  `do_time` varchar(56) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `is_def` int(11) DEFAULT NULL,
  `catalog_file` varchar(1000) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `sid` varchar(255) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_share` */

DROP TABLE IF EXISTS `device_share`;

CREATE TABLE `device_share` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(225) DEFAULT NULL,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `share_url` varchar(225) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `share_title` varchar(225) DEFAULT NULL,
  `file_type` varchar(225) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_shipment_record` */

DROP TABLE IF EXISTS `device_shipment_record`;

CREATE TABLE `device_shipment_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `insert_time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_sound_play_history` */

DROP TABLE IF EXISTS `device_sound_play_history`;

CREATE TABLE `device_sound_play_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epal_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sound_id` int(11) DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '0' COMMENT '0为未处理，1为已经处理',
  `playType` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `epalId` (`epal_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_sound_question` */

DROP TABLE IF EXISTS `device_sound_question`;

CREATE TABLE `device_sound_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sound_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `epal_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT '',
  `status` int(11) DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_sound_question_answer` */

DROP TABLE IF EXISTS `device_sound_question_answer`;

CREATE TABLE `device_sound_question_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sound_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `epal_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_story` */

DROP TABLE IF EXISTS `device_story`;

CREATE TABLE `device_story` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(225) DEFAULT NULL,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `story_id` varchar(225) DEFAULT NULL,
  `story_name` varchar(225) DEFAULT NULL,
  `author_id` varchar(225) DEFAULT NULL,
  `upload_time` timestamp NULL DEFAULT NULL,
  `public_time` timestamp NULL DEFAULT NULL,
  `url` varchar(225) DEFAULT NULL,
  `play_times` int(11) DEFAULT NULL,
  `positive_rate` float(11,0) DEFAULT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_student_school` */

DROP TABLE IF EXISTS `device_student_school`;

CREATE TABLE `device_student_school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fandouAccount` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `parentName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `parentMobile` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `epalId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `className` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `school` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `insertDate` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `addr` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_study` */

DROP TABLE IF EXISTS `device_study`;

CREATE TABLE `device_study` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(225) DEFAULT NULL,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `mission_id` varchar(225) DEFAULT NULL,
  `mission_name` varchar(225) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `isdone` int(11) DEFAULT NULL,
  `url` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_test` */

DROP TABLE IF EXISTS `device_test`;

CREATE TABLE `device_test` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(225) DEFAULT NULL,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  `led` int(2) DEFAULT '0',
  `usb_connect` int(2) DEFAULT '0',
  `burning_process` int(2) DEFAULT '0',
  `shake` int(2) DEFAULT '0',
  `camara` int(2) DEFAULT '0',
  `gesture_recognition` int(2) DEFAULT '0',
  `display` int(2) DEFAULT '0',
  `touch_key` int(2) DEFAULT '0',
  `rfid` int(2) DEFAULT '0',
  `rotation_function` int(2) DEFAULT '0',
  `wifi` int(2) DEFAULT '0',
  `back_result` text,
  `regist_men` varchar(225) DEFAULT NULL,
  `regist_time` timestamp NULL DEFAULT NULL,
  `review_men` varchar(225) DEFAULT NULL,
  `review_time` timestamp NULL DEFAULT NULL,
  `left_mic` int(2) DEFAULT '0',
  `right_mic` int(2) DEFAULT '0',
  `power` int(2) DEFAULT '0',
  `test_process` int(2) DEFAULT '0',
  `left_horn` int(2) DEFAULT '0',
  `right_horn` int(2) DEFAULT '0',
  `left_ear` int(2) DEFAULT '0',
  `right_ear` int(2) DEFAULT '0',
  `ear_mic` int(2) DEFAULT '0',
  `top_touch` int(2) DEFAULT '0',
  `left_touch` int(2) DEFAULT '0',
  `right_touch` int(2) DEFAULT '0',
  `foot_charge` int(2) DEFAULT NULL,
  `usb_charge` int(2) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `device_no` (`device_no`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_transport` */

DROP TABLE IF EXISTS `device_transport`;

CREATE TABLE `device_transport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_numbers` text NOT NULL,
  `bill_number` varchar(125) NOT NULL,
  `from1` int(11) NOT NULL,
  `to1` int(11) NOT NULL,
  `status` tinyint(3) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `device_transport_list` */

DROP TABLE IF EXISTS `device_transport_list`;

CREATE TABLE `device_transport_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_number` varchar(125) DEFAULT NULL,
  `list_id` int(11) DEFAULT '0',
  `status` tinyint(2) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `device_upload_file` */

DROP TABLE IF EXISTS `device_upload_file`;

CREATE TABLE `device_upload_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `music_url` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `file_path` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `file_name` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_id` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image_url` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `discovery` */

DROP TABLE IF EXISTS `discovery`;

CREATE TABLE `discovery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT '',
  `images` text,
  `is_open` int(2) DEFAULT '1',
  `agree_num` int(11) DEFAULT NULL,
  `comment_num` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `district` */

DROP TABLE IF EXISTS `district`;

CREATE TABLE `district` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentid` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `driver` */

DROP TABLE IF EXISTS `driver`;

CREATE TABLE `driver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '姓名',
  `id_card` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '身份证号',
  `address` varchar(225) COLLATE utf8_unicode_ci NOT NULL COMMENT '家庭住址',
  `motorcade` varchar(125) COLLATE utf8_unicode_ci NOT NULL COMMENT '车队信息',
  `telphone` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '电话号码',
  `join_date` date NOT NULL COMMENT '入职时间',
  `avatar` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '头像',
  `status` tinyint(3) DEFAULT '1' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

/*Table structure for table `driver_rel` */

DROP TABLE IF EXISTS `driver_rel`;

CREATE TABLE `driver_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `driver_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `driver_user` (`driver_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

/*Table structure for table `electrism` */

DROP TABLE IF EXISTS `electrism`;

CREATE TABLE `electrism` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `card` varchar(50) DEFAULT NULL,
  `bank` varchar(100) DEFAULT NULL,
  `hobbies` varchar(300) DEFAULT NULL,
  `area` varchar(50) DEFAULT NULL,
  `content` text,
  `headimg` varchar(50) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `paystatus` int(11) DEFAULT NULL,
  `wechat` varchar(100) NOT NULL,
  `idnumber` varchar(20) NOT NULL,
  `didstrict` varchar(2000) NOT NULL,
  `lng` varchar(50) DEFAULT NULL,
  `lat` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `electrismorder` */

DROP TABLE IF EXISTS `electrismorder`;

CREATE TABLE `electrismorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `electrismid` int(11) DEFAULT NULL,
  `ordernumber` varchar(20) DEFAULT NULL,
  `payment` double NOT NULL,
  `contacts` text,
  `mobile` varchar(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `electrismname` varchar(20) DEFAULT NULL,
  `serviceitem` varchar(50) DEFAULT NULL,
  `orderdate` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `electrismordertime` */

DROP TABLE IF EXISTS `electrismordertime`;

CREATE TABLE `electrismordertime` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` varchar(20) NOT NULL,
  `time` varchar(10) NOT NULL,
  `electrismid` int(11) NOT NULL,
  `orderid` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `enroll_info` */

DROP TABLE IF EXISTS `enroll_info`;

CREATE TABLE `enroll_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '编辑时间',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '报名用户名',
  `tel` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户电话',
  `class_id` int(11) DEFAULT NULL COMMENT '班级',
  `age` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL COMMENT '性别:1=男,0=女',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `eova_msg` */

DROP TABLE IF EXISTS `eova_msg`;

CREATE TABLE `eova_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oss_account_id` int(11) NOT NULL,
  `title` varchar(125) NOT NULL,
  `content` text NOT NULL,
  `from` int(11) DEFAULT NULL,
  `status` tinyint(3) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `eova_user` */

DROP TABLE IF EXISTS `eova_user`;

CREATE TABLE `eova_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` varchar(30) DEFAULT NULL COMMENT '帐号',
  `login_pwd` varchar(50) DEFAULT NULL COMMENT '密码',
  `rid` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `name` varchar(50) DEFAULT '' COMMENT '姓名',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `agent_id` int(11) DEFAULT '0' COMMENT '加盟商id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_login_id` (`login_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `eova_user_info` */

DROP TABLE IF EXISTS `eova_user_info`;

CREATE TABLE `eova_user_info` (
  `id` int(11) NOT NULL,
  `rid` int(11) DEFAULT '0' COMMENT '冗余角色ID',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `nickname` varchar(30) DEFAULT '' COMMENT '昵称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '联系手机',
  `province` int(11) DEFAULT NULL COMMENT '省',
  `city` int(11) DEFAULT NULL COMMENT '市',
  `region` int(11) DEFAULT NULL COMMENT '区',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `type` int(5) DEFAULT NULL COMMENT '用户类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详细信息';

/*Table structure for table `epalsystem` */

DROP TABLE IF EXISTS `epalsystem`;

CREATE TABLE `epalsystem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epalid` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `recommend` int(11) NOT NULL,
  `schedule` int(11) NOT NULL,
  `testing` int(11) NOT NULL,
  `distinguish` int(11) NOT NULL,
  `chat` int(11) DEFAULT '1',
  `intelligent_score` int(11) DEFAULT '0',
  `b_quiet` tinyint(2) DEFAULT '1',
  `period` varchar(123) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `eval_label` */

DROP TABLE IF EXISTS `eval_label`;

CREATE TABLE `eval_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL COMMENT '状态：1=正常,0=已失效',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '类型',
  `groups` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所属分组',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '标签名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `eval_label_relation` */

DROP TABLE IF EXISTS `eval_label_relation`;

CREATE TABLE `eval_label_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '编辑时间',
  `label_id` int(11) DEFAULT NULL COMMENT '标签ID',
  `relation_id` int(11) DEFAULT NULL COMMENT '关联ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `eval_option` */

DROP TABLE IF EXISTS `eval_option`;

CREATE TABLE `eval_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pic_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sound_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `eval_question` */

DROP TABLE IF EXISTS `eval_question`;

CREATE TABLE `eval_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT '1' COMMENT '状态',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '编辑时间',
  `text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '题目文本',
  `pic_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '题图网址',
  `sound_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '声音网址',
  `explains` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '题目解析（文字）',
  `explain_sound_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '题目解析（语音网址）',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `fraction` int(11) DEFAULT NULL COMMENT '分值',
  `source` int(11) DEFAULT NULL COMMENT '来源',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `eval_question_option` */

DROP TABLE IF EXISTS `eval_question_option`;

CREATE TABLE `eval_question_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `question_id` int(11) DEFAULT NULL,
  `option_id` int(11) DEFAULT NULL,
  `is_correct` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `eval_record` */

DROP TABLE IF EXISTS `eval_record`;

CREATE TABLE `eval_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `is_right` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `eval_template` */

DROP TABLE IF EXISTS `eval_template`;

CREATE TABLE `eval_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `grade_id` int(11) DEFAULT NULL,
  `sound` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_score` int(11) DEFAULT NULL,
  `end_score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `eval_wx_user` */

DROP TABLE IF EXISTS `eval_wx_user`;

CREATE TABLE `eval_wx_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `open_id` varchar(28) COLLATE utf8_unicode_ci DEFAULT NULL,
  `child_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `child_age` int(11) DEFAULT NULL,
  `child_grade` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `child_nick_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `parent_phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `memberId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `evaluation_template` */

DROP TABLE IF EXISTS `evaluation_template`;

CREATE TABLE `evaluation_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` tinyint(3) DEFAULT NULL,
  `score` tinyint(4) DEFAULT NULL,
  `text` varchar(125) DEFAULT NULL,
  `audio` varchar(225) DEFAULT NULL,
  `teacher_id` int(11) NOT NULL,
  `grade_id` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `exhibition_sign` */

DROP TABLE IF EXISTS `exhibition_sign`;

CREATE TABLE `exhibition_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NOT NULL,
  `uname` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `mobile` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sign_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_id` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `fandou_agent` */

DROP TABLE IF EXISTS `fandou_agent`;

CREATE TABLE `fandou_agent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oss_account_id` int(11) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `top_agent_id` int(11) NOT NULL,
  `contact` varchar(20) DEFAULT NULL COMMENT '联系人',
  `tel` varchar(32) DEFAULT NULL COMMENT '电话',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `fandou_group` */

DROP TABLE IF EXISTS `fandou_group`;

CREATE TABLE `fandou_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `fandou_group_member` */

DROP TABLE IF EXISTS `fandou_group_member`;

CREATE TABLE `fandou_group_member` (
  `id` int(11) NOT NULL,
  `group_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `j_u` (`group_id`,`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `fandou_library_member` */

DROP TABLE IF EXISTS `fandou_library_member`;

CREATE TABLE `fandou_library_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` int(2) DEFAULT NULL,
  `miniapp_openid` varchar(125) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mobile` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `province` varchar(125) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(125) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_tmee` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_login` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `fandou_shop` */

DROP TABLE IF EXISTS `fandou_shop`;

CREATE TABLE `fandou_shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(125) NOT NULL,
  `carete_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `fbms_user` */

DROP TABLE IF EXISTS `fbms_user`;

CREATE TABLE `fbms_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_login` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `filecach` */

DROP TABLE IF EXISTS `filecach`;

CREATE TABLE `filecach` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_no` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `cach_data` longtext COMMENT '缓存数据\r\n',
  `insertdate` varchar(255) DEFAULT NULL,
  `updatedate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `filecach_epal_id` (`epal_id`) USING BTREE,
  KEY `filecach_path` (`path`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `folder_agent` */

DROP TABLE IF EXISTS `folder_agent`;

CREATE TABLE `folder_agent` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `folder_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `folder_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `file_link` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdate` timestamp NULL DEFAULT NULL,
  `higher_up` int(11) DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `food_ingredients` */

DROP TABLE IF EXISTS `food_ingredients`;

CREATE TABLE `food_ingredients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '食物名称',
  `area` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '产地',
  `edible` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '可食用部分',
  `energy` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '能量',
  `water` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '水分',
  `protein` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '蛋白质',
  `fat` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '脂肪',
  `dietary_fiber` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '膳食纤维',
  `carbohydrate` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '碳水化物',
  `retinol_equivalent` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '视黄醇当量',
  `vb1` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '硫胺素',
  `vb2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '核黄素',
  `vpp` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '尼克酸',
  `ve` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '维生素E',
  `na` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '钠含量',
  `ca` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '钙含量',
  `fe` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '铁含量',
  `category` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '类别',
  `vc` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '抗坏血酸',
  `label` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '一级分类',
  `cholesterol` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '胆固醇',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `food_menu` */

DROP TABLE IF EXISTS `food_menu`;

CREATE TABLE `food_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `schoolId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `food_menu_class` */

DROP TABLE IF EXISTS `food_menu_class`;

CREATE TABLE `food_menu_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `menu_id` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `food_menu_ingredients_rela` */

DROP TABLE IF EXISTS `food_menu_ingredients_rela`;

CREATE TABLE `food_menu_ingredients_rela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `menu_id` int(11) DEFAULT NULL,
  `ingredients_id` int(11) DEFAULT NULL,
  `content` int(5) DEFAULT NULL COMMENT '用量(克)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `free_lesson_invite_record` */

DROP TABLE IF EXISTS `free_lesson_invite_record`;

CREATE TABLE `free_lesson_invite_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL COMMENT '课程商品id',
  `inviter` int(11) DEFAULT NULL COMMENT '邀请人的memberid',
  `invitee` int(11) DEFAULT NULL COMMENT '被邀请人的memberid',
  `status` tinyint(2) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_product_inviter_invitee` (`product_id`,`inviter`,`invitee`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `friends` */

DROP TABLE IF EXISTS `friends`;

CREATE TABLE `friends` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) NOT NULL,
  `friend_id` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `friend_type` varchar(45) NOT NULL,
  `note_name` varchar(45) DEFAULT NULL,
  `group_id` varchar(45) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `friend_id` (`friend_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `gamebattle` */

DROP TABLE IF EXISTS `gamebattle`;

CREATE TABLE `gamebattle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sponsor` int(11) NOT NULL,
  `recipient` int(11) NOT NULL,
  `resuit` int(11) NOT NULL,
  `integar` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `createdate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `gameintegar` */

DROP TABLE IF EXISTS `gameintegar`;

CREATE TABLE `gameintegar` (
  `id` int(11) NOT NULL DEFAULT '0',
  `memberid` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `integar` int(11) NOT NULL,
  `createdate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ganme` */

DROP TABLE IF EXISTS `ganme`;

CREATE TABLE `ganme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `lever` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ganmeinfo` */

DROP TABLE IF EXISTS `ganmeinfo`;

CREATE TABLE `ganmeinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ganmeid` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `resourcesid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `good_video` */

DROP TABLE IF EXISTS `good_video`;

CREATE TABLE `good_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `school` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `insert_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `grade_code` */

DROP TABLE IF EXISTS `grade_code`;

CREATE TABLE `grade_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `grade_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_grade` (`code`,`grade_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `grade_gift` */

DROP TABLE IF EXISTS `grade_gift`;

CREATE TABLE `grade_gift` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grade_ids` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `grade_study_day` */

DROP TABLE IF EXISTS `grade_study_day`;

CREATE TABLE `grade_study_day` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `grade_id` int(11) DEFAULT NULL,
  `day_of_week` int(4) DEFAULT NULL,
  `is_open` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `grade_study_day_by_day` */

DROP TABLE IF EXISTS `grade_study_day_by_day`;

CREATE TABLE `grade_study_day_by_day` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `grade_id` int(11) DEFAULT NULL,
  `day_str` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '上课安排，字符串，用-分割',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `grade_study_rule` */

DROP TABLE IF EXISTS `grade_study_rule`;

CREATE TABLE `grade_study_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grade_id` int(11) NOT NULL,
  `rule` text NOT NULL,
  `status` tinyint(2) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `grades_temporary_class` */

DROP TABLE IF EXISTS `grades_temporary_class`;

CREATE TABLE `grades_temporary_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_grades_id` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  `class_course_id` int(11) DEFAULT '0',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `grades_id` (`class_grades_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `group_chat` */

DROP TABLE IF EXISTS `group_chat`;

CREATE TABLE `group_chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_grade_id` int(11) DEFAULT '0' COMMENT '凡豆班级id',
  `easemob_group_id` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '环信群组id',
  `teacher_id` int(11) DEFAULT NULL COMMENT '老师id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `group_info` */

DROP TABLE IF EXISTS `group_info`;

CREATE TABLE `group_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) NOT NULL,
  `group_id` varchar(45) NOT NULL,
  `note_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `history_schedules` */

DROP TABLE IF EXISTS `history_schedules`;

CREATE TABLE `history_schedules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `do_time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `picture` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `period` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `epalId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `scheduleId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `exe_time` bigint(255) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `hot_keys` */

DROP TABLE IF EXISTS `hot_keys`;

CREATE TABLE `hot_keys` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keys` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `key_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `score` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `hotkey_group` */

DROP TABLE IF EXISTS `hotkey_group`;

CREATE TABLE `hotkey_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `group_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `integral` */

DROP TABLE IF EXISTS `integral`;

CREATE TABLE `integral` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remark` varchar(500) DEFAULT NULL,
  `typeid` int(11) DEFAULT NULL,
  `memberid` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `fraction` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `membertype` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `integral_copy` */

DROP TABLE IF EXISTS `integral_copy`;

CREATE TABLE `integral_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remark` varchar(500) DEFAULT NULL,
  `typeid` int(11) DEFAULT NULL,
  `memberid` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `fraction` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `membertype` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `integral_level_info` */

DROP TABLE IF EXISTS `integral_level_info`;

CREATE TABLE `integral_level_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level_name` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `level` int(11) NOT NULL,
  `integral_start` int(11) NOT NULL,
  `integral_end` int(11) NOT NULL,
  `level_pic_url` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `level_url` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `integral_wallet` */

DROP TABLE IF EXISTS `integral_wallet`;

CREATE TABLE `integral_wallet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) DEFAULT NULL COMMENT '帐号id',
  `integral` int(11) DEFAULT '0' COMMENT '积分',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `integral_wallet_record` */

DROP TABLE IF EXISTS `integral_wallet_record`;

CREATE TABLE `integral_wallet_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `integral_wallet_id` int(11) DEFAULT NULL COMMENT '积分钱包id',
  `detail` int(11) DEFAULT NULL COMMENT '明细',
  `description` varchar(125) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `integraltype` */

DROP TABLE IF EXISTS `integraltype`;

CREATE TABLE `integraltype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `interface` */

DROP TABLE IF EXISTS `interface`;

CREATE TABLE `interface` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `method` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `parameter` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `invitation_code` */

DROP TABLE IF EXISTS `invitation_code`;

CREATE TABLE `invitation_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL COMMENT '课堂id',
  `product_id` int(11) DEFAULT '0' COMMENT '邀请码绑定的商品，可能是包或班',
  `random_code` varchar(6) NOT NULL COMMENT '邀请码',
  `invalid_time` timestamp NULL DEFAULT NULL COMMENT '失效时间',
  `member_id` int(11) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `recommend_member_id` int(11) DEFAULT '0' COMMENT '邀请人memberid',
  `status` tinyint(2) DEFAULT '0',
  `first_send_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '第一次发送短信的时间',
  PRIMARY KEY (`id`,`random_code`),
  UNIQUE KEY `invitation_code_random_code` (`random_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `invite_msg` */

DROP TABLE IF EXISTS `invite_msg`;

CREATE TABLE `invite_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `msg` varchar(225) DEFAULT NULL,
  `type` tinyint(4) DEFAULT '1',
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `invite_reg_record` */

DROP TABLE IF EXISTS `invite_reg_record`;

CREATE TABLE `invite_reg_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inviter_student_id` int(11) DEFAULT NULL,
  `account` varchar(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `ip_table` */

DROP TABLE IF EXISTS `ip_table`;

CREATE TABLE `ip_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `IP` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `province` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `operator` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `insert_time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `area` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `is_outdate` */

DROP TABLE IF EXISTS `is_outdate`;

CREATE TABLE `is_outdate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_outdate` int(1) NOT NULL COMMENT '是否需要更新版本',
  `environment` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '软件版本',
  `version` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `keyword` */

DROP TABLE IF EXISTS `keyword`;

CREATE TABLE `keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `contenttype` int(11) DEFAULT NULL,
  `materialid` int(11) DEFAULT NULL,
  `accountid` int(11) DEFAULT NULL,
  `matchingrules` int(11) DEFAULT NULL,
  `content` text,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `label` */

DROP TABLE IF EXISTS `label`;

CREATE TABLE `label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `leftcategory` */

DROP TABLE IF EXISTS `leftcategory`;

CREATE TABLE `leftcategory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_ad_rel` */

DROP TABLE IF EXISTS `lesson_ad_rel`;

CREATE TABLE `lesson_ad_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) NOT NULL,
  `lesson_ads` int(11) NOT NULL,
  `lesson_share_rule_id` int(11) NOT NULL,
  `class_grade_id` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_ads` */

DROP TABLE IF EXISTS `lesson_ads`;

CREATE TABLE `lesson_ads` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pics` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_content` */

DROP TABLE IF EXISTS `lesson_content`;

CREATE TABLE `lesson_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT '-1',
  `teacher_id` int(11) DEFAULT '-1',
  `content_script` text COLLATE utf8_unicode_ci,
  `type` tinyint(2) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_feedback` */

DROP TABLE IF EXISTS `lesson_feedback`;

CREATE TABLE `lesson_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT '0',
  `class_course_id` int(11) DEFAULT '0',
  `class_script_id` int(11) DEFAULT '0',
  `bulletin_question_id` int(11) DEFAULT '0',
  `student_id` int(11) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_integral_record` */

DROP TABLE IF EXISTS `lesson_integral_record`;

CREATE TABLE `lesson_integral_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `integral` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_product_image` */

DROP TABLE IF EXISTS `lesson_product_image`;

CREATE TABLE `lesson_product_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `img1` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `img2` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `img3` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_product_order` */

DROP TABLE IF EXISTS `lesson_product_order`;

CREATE TABLE `lesson_product_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `product_name` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL,
  `total_price` decimal(5,2) DEFAULT NULL,
  `openid` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `student_id` int(11) DEFAULT '0',
  `inviter` int(11) DEFAULT '0',
  `status` tinyint(2) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_record` */

DROP TABLE IF EXISTS `lesson_record`;

CREATE TABLE `lesson_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_course_id` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `class_script_id` int(11) DEFAULT NULL,
  `class_script_content` text COLLATE utf8_unicode_ci,
  `lesson_reply_content` text COLLATE utf8_unicode_ci,
  `type` tinyint(2) DEFAULT '1' COMMENT '记录类型：1=上课记录,2=阅读记录',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_reply_comment` */

DROP TABLE IF EXISTS `lesson_reply_comment`;

CREATE TABLE `lesson_reply_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `script_id` int(11) NOT NULL,
  `reply_id` int(11) NOT NULL,
  `content` text,
  `teacher_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_reply_like_app` */

DROP TABLE IF EXISTS `lesson_reply_like_app`;

CREATE TABLE `lesson_reply_like_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `reply_id` int(11) DEFAULT NULL COMMENT 'lesson_script_reply_id',
  `status` tinyint(2) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `student_reply` (`student_id`,`reply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_script_reply` */

DROP TABLE IF EXISTS `lesson_script_reply`;

CREATE TABLE `lesson_script_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `class_course_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `class_script_id` int(11) DEFAULT NULL,
  `class_script_content` text COLLATE utf8_unicode_ci,
  `title` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` text COLLATE utf8_unicode_ci,
  `reply_script` text CHARACTER SET utf8mb4,
  `time` int(3) DEFAULT '1',
  `time_sn` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL,
  `score` tinyint(3) DEFAULT '0',
  `b_public` tinyint(2) DEFAULT '0',
  `group_id` int(11) DEFAULT '0' COMMENT '学习记录分享group_id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `time_sn` (`time_sn`) USING HASH,
  KEY `class_course_id` (`class_course_id`) USING HASH,
  KEY `student_id` (`student_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_script_reply_commont` */

DROP TABLE IF EXISTS `lesson_script_reply_commont`;

CREATE TABLE `lesson_script_reply_commont` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL COMMENT 'lessonn_script_reply_group_id',
  `comment` varchar(240) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(3) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_script_reply_group` */

DROP TABLE IF EXISTS `lesson_script_reply_group`;

CREATE TABLE `lesson_script_reply_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL COMMENT '学生id',
  `like` int(11) DEFAULT '0' COMMENT '点赞数',
  `class_room_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `upadte_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_script_reply_group_rel` */

DROP TABLE IF EXISTS `lesson_script_reply_group_rel`;

CREATE TABLE `lesson_script_reply_group_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lesson_script_reply_group_id` int(11) DEFAULT NULL,
  `lesson_script_reply_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_script_reply_like` */

DROP TABLE IF EXISTS `lesson_script_reply_like`;

CREATE TABLE `lesson_script_reply_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL COMMENT 'lessonn_script_reply_group_id',
  `member_id` int(11) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_group` (`group_id`,`member_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_script_reply_score` */

DROP TABLE IF EXISTS `lesson_script_reply_score`;

CREATE TABLE `lesson_script_reply_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time_sn` varchar(30) NOT NULL,
  `score` tinyint(4) DEFAULT NULL,
  `audio` varchar(225) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `lesson_script_time` */

DROP TABLE IF EXISTS `lesson_script_time`;

CREATE TABLE `lesson_script_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `time_sn` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_class_room_id_time` (`class_room_id`,`time_sn`) USING BTREE,
  KEY `time_sn` (`time_sn`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `lesson_share_rule` */

DROP TABLE IF EXISTS `lesson_share_rule`;

CREATE TABLE `lesson_share_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(60) NOT NULL,
  `description` varchar(60) NOT NULL,
  `h5_title` varchar(125) NOT NULL,
  `h5_ads01_pic` varchar(125) NOT NULL,
  `h5_ads02_pic` varchar(125) NOT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `library` */

DROP TABLE IF EXISTS `library`;

CREATE TABLE `library` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isbn` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `book_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cover_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `book_name_key` (`book_name`) USING HASH,
  KEY `isbn_key` (`isbn`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `library_product` */

DROP TABLE IF EXISTS `library_product`;

CREATE TABLE `library_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mall_product_id` int(11) DEFAULT NULL,
  `library_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `life_convert_record` */

DROP TABLE IF EXISTS `life_convert_record`;

CREATE TABLE `life_convert_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `agent_id` int(11) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `status` tinyint(4) DEFAULT '0',
  `remark` text COMMENT '备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `live_lesson` */

DROP TABLE IF EXISTS `live_lesson`;

CREATE TABLE `live_lesson` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `live_lesson_id` int(225) DEFAULT NULL,
  `lesson_name` varchar(126) COLLATE utf8_unicode_ci DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  `live_lesson_grade` int(11) DEFAULT '0' COMMENT '直播绑定班级',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `live_lesson_grade` */

DROP TABLE IF EXISTS `live_lesson_grade`;

CREATE TABLE `live_lesson_grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grade_id` int(11) DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `live_lesson_rel` */

DROP TABLE IF EXISTS `live_lesson_rel`;

CREATE TABLE `live_lesson_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `live_lesson_id` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `live_script_status` */

DROP TABLE IF EXISTS `live_script_status`;

CREATE TABLE `live_script_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL,
  `class_script_id` int(11) NOT NULL,
  `live_status` smallint(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `1` (`course_id`,`class_script_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `login_log` */

DROP TABLE IF EXISTS `login_log`;

CREATE TABLE `login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(125) DEFAULT NULL,
  `ip` varchar(125) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mall_order_status` */

DROP TABLE IF EXISTS `mall_order_status`;

CREATE TABLE `mall_order_status` (
  `id` smallint(3) NOT NULL,
  `status` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `mall_product2` */

DROP TABLE IF EXISTS `mall_product2`;

CREATE TABLE `mall_product2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(125) NOT NULL,
  `desc` varchar(125) DEFAULT NULL COMMENT '描述',
  `pics` text,
  `video` varchar(225) DEFAULT NULL,
  `content` text,
  `price_range` varchar(125) DEFAULT NULL,
  `b_show` tinyint(4) DEFAULT '0',
  `class_room_package_id` int(11) DEFAULT '0',
  `class_grade_id` int(11) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mall_product_banner` */

DROP TABLE IF EXISTS `mall_product_banner`;

CREATE TABLE `mall_product_banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mall_product_id` int(11) DEFAULT NULL,
  `banner_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `mall_product_category` */

DROP TABLE IF EXISTS `mall_product_category`;

CREATE TABLE `mall_product_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(125) NOT NULL,
  `status` tinyint(4) DEFAULT '1',
  `show_home` tinyint(4) DEFAULT '0' COMMENT '是否在首页展示',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updata_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mall_product_category_rel` */

DROP TABLE IF EXISTS `mall_product_category_rel`;

CREATE TABLE `mall_product_category_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mall_product_category` int(11) DEFAULT NULL,
  `mall_product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mall_product_miniapp` */

DROP TABLE IF EXISTS `mall_product_miniapp`;

CREATE TABLE `mall_product_miniapp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL,
  `infromation` text COLLATE utf8_unicode_ci,
  `status` int(3) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mall_product_order` */

DROP TABLE IF EXISTS `mall_product_order`;

CREATE TABLE `mall_product_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(20) NOT NULL,
  `product_spec_id` int(11) NOT NULL,
  `account_id` int(11) DEFAULT '0',
  `student_id` int(11) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mall_product_picture` */

DROP TABLE IF EXISTS `mall_product_picture`;

CREATE TABLE `mall_product_picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mall_produuct_id` int(11) DEFAULT NULL,
  `pic_url` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mall_product_rebate_code` */

DROP TABLE IF EXISTS `mall_product_rebate_code`;

CREATE TABLE `mall_product_rebate_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '折扣码',
  `rebate` double DEFAULT NULL COMMENT '折扣数',
  `status` int(2) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `mall_product_specification` */

DROP TABLE IF EXISTS `mall_product_specification`;

CREATE TABLE `mall_product_specification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mall_product_id` int(11) NOT NULL,
  `name` varchar(125) DEFAULT NULL,
  `count` int(11) DEFAULT '0',
  `original_price` decimal(6,2) DEFAULT '0.00' COMMENT '原价',
  `price` decimal(6,2) DEFAULT '0.00',
  `original_coin` int(11) DEFAULT '0',
  `coin` int(11) DEFAULT '0' COMMENT '原价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mallbanner` */

DROP TABLE IF EXISTS `mallbanner`;

CREATE TABLE `mallbanner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `banner` varchar(200) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `url` varchar(300) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `urltype` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `banner1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `malllabel` */

DROP TABLE IF EXISTS `malllabel`;

CREATE TABLE `malllabel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `labelId` int(11) DEFAULT NULL,
  `ios` int(11) DEFAULT NULL,
  `wechat` int(11) DEFAULT NULL,
  `android` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mallorder` */

DROP TABLE IF EXISTS `mallorder`;

CREATE TABLE `mallorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ordernumber` varchar(50) DEFAULT NULL,
  `memberId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `totalprice` double DEFAULT NULL,
  `freight` double DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `express` varchar(50) DEFAULT NULL,
  `expressnumber` varchar(50) DEFAULT NULL,
  `operatorid` int(11) DEFAULT NULL,
  `couponsmoney` double DEFAULT NULL,
  `addressid` int(11) DEFAULT NULL,
  `statusblank` int(11) DEFAULT NULL,
  `integral` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mallorder_copy` */

DROP TABLE IF EXISTS `mallorder_copy`;

CREATE TABLE `mallorder_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ordernumber` varchar(50) DEFAULT NULL,
  `memberId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `totalprice` double DEFAULT NULL,
  `freight` double DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `express` varchar(50) DEFAULT NULL,
  `expressnumber` varchar(50) DEFAULT NULL,
  `operatorid` int(11) DEFAULT NULL,
  `couponsmoney` double DEFAULT NULL,
  `addressid` int(11) DEFAULT NULL,
  `statusblank` int(11) DEFAULT NULL,
  `integral` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mallorderproduct` */

DROP TABLE IF EXISTS `mallorderproduct`;

CREATE TABLE `mallorderproduct` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` int(11) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `specifications` varchar(200) DEFAULT NULL,
  `productName` varchar(50) DEFAULT NULL,
  `productImg` varchar(200) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `commentstatus` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mallorderservice` */

DROP TABLE IF EXISTS `mallorderservice`;

CREATE TABLE `mallorderservice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` int(11) NOT NULL,
  `ordernumber` varchar(50) DEFAULT NULL,
  `memberId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `payment` double DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `express` varchar(50) DEFAULT NULL,
  `expressnumber` varchar(50) DEFAULT NULL,
  `operatorid` int(11) DEFAULT NULL,
  `servicetype` varchar(220) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mallproduct` */

DROP TABLE IF EXISTS `mallproduct`;

CREATE TABLE `mallproduct` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `content` text,
  `accountid` int(11) DEFAULT NULL,
  `logo1` varchar(200) DEFAULT NULL,
  `logo2` varchar(200) DEFAULT NULL,
  `logo3` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `price` varchar(100) DEFAULT '0',
  `mp3` varchar(1000) DEFAULT NULL,
  `mp3type` int(11) DEFAULT NULL,
  `cat_id` int(11) DEFAULT '0',
  `class_room_package` int(11) DEFAULT '0' COMMENT '课堂包id',
  `class_grade_id` int(11) DEFAULT '0' COMMENT '班级id',
  `class_booking` int(11) DEFAULT '0' COMMENT '预约次数',
  PRIMARY KEY (`id`),
  KEY `cat_id` (`cat_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mallproduct_copy` */

DROP TABLE IF EXISTS `mallproduct_copy`;

CREATE TABLE `mallproduct_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `content` text,
  `accountid` int(11) DEFAULT NULL,
  `logo1` varchar(200) DEFAULT NULL,
  `logo2` varchar(200) DEFAULT NULL,
  `logo3` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `price` varchar(100) DEFAULT NULL,
  `mp3` varchar(1000) DEFAULT NULL,
  `mp3type` int(11) DEFAULT NULL,
  `cat_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mallproduct_copy1` */

DROP TABLE IF EXISTS `mallproduct_copy1`;

CREATE TABLE `mallproduct_copy1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `content` text,
  `accountid` int(11) DEFAULT NULL,
  `logo1` varchar(200) DEFAULT NULL,
  `logo2` varchar(200) DEFAULT NULL,
  `logo3` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `price` varchar(100) DEFAULT NULL,
  `mp3` varchar(1000) DEFAULT NULL,
  `mp3type` int(11) DEFAULT NULL,
  `cat_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mallproductkeyword` */

DROP TABLE IF EXISTS `mallproductkeyword`;

CREATE TABLE `mallproductkeyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mallproductlog` */

DROP TABLE IF EXISTS `mallproductlog`;

CREATE TABLE `mallproductlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) NOT NULL,
  `memberid` int(11) DEFAULT NULL,
  `createdate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mallspecifications` */

DROP TABLE IF EXISTS `mallspecifications`;

CREATE TABLE `mallspecifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `count` int(11) NOT NULL,
  `price` double NOT NULL,
  `integral` int(11) DEFAULT '0',
  `productid` int(11) NOT NULL,
  `commission` float(2,2) DEFAULT '0.00' COMMENT '佣金（百分比）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `material` */

DROP TABLE IF EXISTS `material`;

CREATE TABLE `material` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `contenttype` int(11) DEFAULT NULL,
  `logo` varchar(100) DEFAULT NULL,
  `summary` varchar(1000) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `accountid` int(11) DEFAULT NULL,
  `parentid` int(11) DEFAULT NULL,
  `content` text,
  `createdate` datetime DEFAULT NULL,
  `logostatus` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `material_file` */

DROP TABLE IF EXISTS `material_file`;

CREATE TABLE `material_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isdir` int(2) DEFAULT NULL,
  `belong` int(11) DEFAULT NULL,
  `path_pid` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `audio_length` int(11) DEFAULT NULL,
  `file_size` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `audioinfo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `audioinfo_id` (`audioinfo_id`) USING BTREE,
  KEY `belong` (`belong`) USING HASH,
  KEY `file_name` (`name`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `material_file_type` */

DROP TABLE IF EXISTS `material_file_type`;

CREATE TABLE `material_file_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `material_path` */

DROP TABLE IF EXISTS `material_path`;

CREATE TABLE `material_path` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pid` int(11) DEFAULT '0',
  `status` int(1) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT '-1',
  `member_id` int(11) DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `material_path_file_rel` */

DROP TABLE IF EXISTS `material_path_file_rel`;

CREATE TABLE `material_path_file_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `material_file_id` int(11) DEFAULT NULL,
  `material_path_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mediainfo_words` */

DROP TABLE IF EXISTS `mediainfo_words`;

CREATE TABLE `mediainfo_words` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `audio_id` int(11) DEFAULT NULL,
  `words` text,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `audio_id` (`audio_id`) USING HASH,
  FULLTEXT KEY `words` (`words`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `mem_book` */

DROP TABLE IF EXISTS `mem_book`;

CREATE TABLE `mem_book` (
  `b_barcode` varchar(255) NOT NULL,
  `m_barcode` varchar(255) NOT NULL,
  `borrDate` datetime NOT NULL,
  `backDate` datetime DEFAULT NULL,
  `ifBack` int(11) NOT NULL DEFAULT '0',
  `isRenew` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`b_barcode`,`m_barcode`,`borrDate`),
  KEY `FKD91EAB13D82792E8` (`m_barcode`),
  KEY `FKD91EAB13FE73662C` (`b_barcode`),
  CONSTRAINT `FKD91EAB13D82792E8` FOREIGN KEY (`m_barcode`) REFERENCES `member1` (`username`),
  CONSTRAINT `FKD91EAB13FE73662C` FOREIGN KEY (`b_barcode`) REFERENCES `book` (`barcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `openid` varchar(100) DEFAULT NULL,
  `accountid` int(11) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `headimgurl` varchar(300) DEFAULT NULL,
  `createdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mobile` varchar(20) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `tempname` varchar(200) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `miniapp_openid` varchar(50) DEFAULT NULL,
  `unionid` varchar(40) DEFAULT NULL,
  `status` int(2) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `mobile` (`mobile`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `member1` */

DROP TABLE IF EXISTS `member1`;

CREATE TABLE `member1` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `barcode` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `borrowed` int(11) NOT NULL,
  `borrowed_amount` int(11) unsigned zerofill NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `ismem` int(11) unsigned zerofill NOT NULL,
  `belong` int(11) DEFAULT NULL,
  `memtype_id` int(11) DEFAULT NULL,
  `purchase_time` datetime DEFAULT NULL,
  `deposit` int(11) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `FKBFC28A9AD88E281E` (`belong`),
  KEY `FKBFC28A9A429C075F` (`memtype_id`),
  CONSTRAINT `FKBFC28A9A429C075F` FOREIGN KEY (`memtype_id`) REFERENCES `membertype` (`memtypeid`),
  CONSTRAINT `FKBFC28A9AD88E281E` FOREIGN KEY (`belong`) REFERENCES `belong` (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `member_address` */

DROP TABLE IF EXISTS `member_address`;

CREATE TABLE `member_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) DEFAULT '0',
  `username` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `area` varchar(100) DEFAULT NULL,
  `address` varchar(256) DEFAULT NULL,
  `lng` varchar(50) DEFAULT NULL,
  `lat` varchar(50) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '1',
  `student_id` int(11) DEFAULT '0',
  `default` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `member_copy` */

DROP TABLE IF EXISTS `member_copy`;

CREATE TABLE `member_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `openid` varchar(100) DEFAULT NULL,
  `accountid` int(11) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `headimgurl` varchar(300) DEFAULT NULL,
  `createdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mobile` varchar(20) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `tempname` varchar(200) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `miniapp_openid` varchar(50) DEFAULT NULL,
  `unionid` varchar(40) DEFAULT NULL,
  `status` int(2) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `mobile` (`mobile`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `member_student_rela` */

DROP TABLE IF EXISTS `member_student_rela`;

CREATE TABLE `member_student_rela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `member_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `member_user_agent` */

DROP TABLE IF EXISTS `member_user_agent`;

CREATE TABLE `member_user_agent` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `member_id` int(255) DEFAULT NULL,
  `user_id` int(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `memberaccount` */

DROP TABLE IF EXISTS `memberaccount`;

CREATE TABLE `memberaccount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `password` varchar(100) DEFAULT NULL,
  `memberid` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `createdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `nickname` varchar(500) DEFAULT NULL,
  `last_login` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `account` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `memberbook` */

DROP TABLE IF EXISTS `memberbook`;

CREATE TABLE `memberbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) DEFAULT NULL,
  `enddate` datetime DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `memberbookinfo` */

DROP TABLE IF EXISTS `memberbookinfo`;

CREATE TABLE `memberbookinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) DEFAULT NULL,
  `cateid` varchar(11) DEFAULT NULL,
  `bookexpressid` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `barcode` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `membercard` */

DROP TABLE IF EXISTS `membercard`;

CREATE TABLE `membercard` (
  `id` varchar(100) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `createdate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `memberchildren` */

DROP TABLE IF EXISTS `memberchildren`;

CREATE TABLE `memberchildren` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) NOT NULL,
  `age` varchar(50) NOT NULL,
  `sex` int(11) NOT NULL,
  `interest` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `membercollection` */

DROP TABLE IF EXISTS `membercollection`;

CREATE TABLE `membercollection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  `memberid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `membergame` */

DROP TABLE IF EXISTS `membergame`;

CREATE TABLE `membergame` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) NOT NULL,
  `lever` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `memberkeyword` */

DROP TABLE IF EXISTS `memberkeyword`;

CREATE TABLE `memberkeyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) DEFAULT NULL,
  `keyword` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `membermp3` */

DROP TABLE IF EXISTS `membermp3`;

CREATE TABLE `membermp3` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  `memberid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `memberpayment` */

DROP TABLE IF EXISTS `memberpayment`;

CREATE TABLE `memberpayment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createdate` datetime NOT NULL,
  `ordernumber` varchar(20) NOT NULL,
  `payment` double NOT NULL,
  `memberid` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `membertype` */

DROP TABLE IF EXISTS `membertype`;

CREATE TABLE `membertype` (
  `memtypeid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `picpath` varchar(255) DEFAULT NULL,
  `lendable` int(11) DEFAULT NULL,
  `forfree` int(11) DEFAULT NULL,
  PRIMARY KEY (`memtypeid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `accountid` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `to_user` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `from_user` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_date` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_date` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `miniapp_member` */

DROP TABLE IF EXISTS `miniapp_member`;

CREATE TABLE `miniapp_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` int(2) DEFAULT NULL,
  `miniapp_openid` varchar(125) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mobile` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `country` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `province` varchar(125) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(125) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `language` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_login` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_openid` (`miniapp_openid`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `miniapp_template_form` */

DROP TABLE IF EXISTS `miniapp_template_form`;

CREATE TABLE `miniapp_template_form` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `miniapp_openid` varchar(56) CHARACTER SET utf8mb4 DEFAULT NULL,
  `form_id` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_miniapp_openid` (`miniapp_openid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `modular` */

DROP TABLE IF EXISTS `modular`;

CREATE TABLE `modular` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `url` varchar(100) NOT NULL,
  `sort` int(11) NOT NULL,
  `parentid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `moment` */

DROP TABLE IF EXISTS `moment`;

CREATE TABLE `moment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT '0',
  `class_course_id` int(11) DEFAULT '0',
  `text` varchar(350) CHARACTER SET utf8mb4 DEFAULT NULL,
  `likes` int(11) DEFAULT '0',
  `media_content` text,
  `b_public` tinyint(1) DEFAULT '1',
  `status` tinyint(4) DEFAULT '1',
  `m_type` int(2) DEFAULT '1' COMMENT '1是少年派，2是凡豆伴',
  `group_id` int(11) DEFAULT '0' COMMENT '学习记录分享group_id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `action` tinyint(4) DEFAULT '0' COMMENT '是否打卡 值 0:不是 ,1:是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `myliber` */

DROP TABLE IF EXISTS `myliber`;

CREATE TABLE `myliber` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `epal_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `book_name` varchar(128) NOT NULL,
  `book_image` varchar(128) NOT NULL DEFAULT 'http://www.fandoutech.com.cn',
  `status` varchar(255) DEFAULT '0' COMMENT '状态',
  `source` varchar(45) NOT NULL DEFAULT 'admin' COMMENT '来源',
  `remarks` varchar(45) NOT NULL DEFAULT 'no' COMMENT '备注',
  `sort` int(10) unsigned NOT NULL DEFAULT '1',
  `createdate` varchar(45) NOT NULL,
  `updatedate` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `online_class_comment` */

DROP TABLE IF EXISTS `online_class_comment`;

CREATE TABLE `online_class_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacherId` int(11) DEFAULT NULL,
  `modelId` int(11) DEFAULT NULL,
  `content` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `studentIds` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `commentSound` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gradesId` int(11) DEFAULT NULL,
  `startDate` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `endDate` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `insertDate` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(255) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `online_class_comment_model` */

DROP TABLE IF EXISTS `online_class_comment_model`;

CREATE TABLE `online_class_comment_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT NULL,
  `model_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `model_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(255) DEFAULT NULL,
  `insert_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `edit_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `teacher_id` (`teacher_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `online_class_delete_student_record` */

DROP TABLE IF EXISTS `online_class_delete_student_record`;

CREATE TABLE `online_class_delete_student_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `grades_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `insert_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `edit_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `order_student_roompack` */

DROP TABLE IF EXISTS `order_student_roompack`;

CREATE TABLE `order_student_roompack` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `orderstatus` */

DROP TABLE IF EXISTS `orderstatus`;

CREATE TABLE `orderstatus` (
  `osid` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`osid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `oss_agent_school` */

DROP TABLE IF EXISTS `oss_agent_school`;

CREATE TABLE `oss_agent_school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '学校名称',
  `oss_user_id` int(11) DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  `credit` int(11) DEFAULT '0',
  `contact` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系人',
  `tel` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '电话',
  `cover` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` text COLLATE utf8_unicode_ci,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `oss_company` */

DROP TABLE IF EXISTS `oss_company`;

CREATE TABLE `oss_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oss_account_id` int(11) NOT NULL,
  `name` varchar(123) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `oss_role_detail` */

DROP TABLE IF EXISTS `oss_role_detail`;

CREATE TABLE `oss_role_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oss_account_id` int(11) NOT NULL COMMENT '账号id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `name` varchar(30) DEFAULT NULL COMMENT '用户名',
  `contact` varchar(30) DEFAULT NULL COMMENT '联系人',
  `tel` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `pid` int(11) DEFAULT NULL COMMENT '上级id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ACID` (`oss_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `oss_transfer_account` */

DROP TABLE IF EXISTS `oss_transfer_account`;

CREATE TABLE `oss_transfer_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oss_account_id` int(11) NOT NULL,
  `recipient_name` varchar(20) NOT NULL,
  `alipay_account` varchar(125) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `oss_transfer_order` */

DROP TABLE IF EXISTS `oss_transfer_order`;

CREATE TABLE `oss_transfer_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(64) NOT NULL,
  `amount` varchar(25) NOT NULL,
  `oss_account_id` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `oss_user` */

DROP TABLE IF EXISTS `oss_user`;

CREATE TABLE `oss_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` varchar(30) NOT NULL COMMENT '账户',
  `login_pwd` varchar(50) NOT NULL COMMENT '密码',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `name` varchar(30) NOT NULL COMMENT '用户名',
  `pid` int(11) DEFAULT NULL COMMENT '上级id',
  `contact` varchar(20) DEFAULT NULL COMMENT '联系人',
  `tel` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_login` timestamp NULL DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `oss_wallet` */

DROP TABLE IF EXISTS `oss_wallet`;

CREATE TABLE `oss_wallet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oss_account_id` int(11) NOT NULL,
  `amount` int(11) DEFAULT NULL COMMENT '金额，单位：分',
  `remark` varchar(125) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `pc_manage_menu` */

DROP TABLE IF EXISTS `pc_manage_menu`;

CREATE TABLE `pc_manage_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `fun` varchar(50) DEFAULT NULL,
  `grade` int(5) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `pc_manage_menu_role` */

DROP TABLE IF EXISTS `pc_manage_menu_role`;

CREATE TABLE `pc_manage_menu_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL,
  `menuid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `pc_manage_role` */

DROP TABLE IF EXISTS `pc_manage_role`;

CREATE TABLE `pc_manage_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `play_history` */

DROP TABLE IF EXISTS `play_history`;

CREATE TABLE `play_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `source_id` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL,
  `source_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `source_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `source` text COLLATE utf8_unicode_ci,
  `epal_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `play_time` datetime DEFAULT NULL,
  `stop_time` datetime DEFAULT NULL,
  `complete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `popularize` */

DROP TABLE IF EXISTS `popularize`;

CREATE TABLE `popularize` (
  `popid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`popid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `product_category` */

DROP TABLE IF EXISTS `product_category`;

CREATE TABLE `product_category` (
  `cat_id` int(11) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(30) NOT NULL DEFAULT '',
  `cat_name` varchar(255) NOT NULL DEFAULT '',
  `keywords` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `parent_id` smallint(5) NOT NULL DEFAULT '0',
  `sort` tinyint(1) unsigned NOT NULL DEFAULT '50',
  PRIMARY KEY (`cat_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `product_category_copy` */

DROP TABLE IF EXISTS `product_category_copy`;

CREATE TABLE `product_category_copy` (
  `cat_id` smallint(5) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(30) NOT NULL DEFAULT '',
  `cat_name` varchar(255) NOT NULL DEFAULT '',
  `keywords` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `parent_id` smallint(5) NOT NULL DEFAULT '0',
  `sort` tinyint(1) unsigned NOT NULL DEFAULT '50',
  PRIMARY KEY (`cat_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `product_category_show` */

DROP TABLE IF EXISTS `product_category_show`;

CREATE TABLE `product_category_show` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL,
  `is_show` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `product_pay` */

DROP TABLE IF EXISTS `product_pay`;

CREATE TABLE `product_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `grade` varchar(125) DEFAULT NULL COMMENT '年级',
  `orderNumber` varchar(255) DEFAULT NULL,
  `openid` varchar(50) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `product_spec` varchar(255) DEFAULT NULL,
  `status` tinyint(2) DEFAULT NULL COMMENT '默认0为未支付  1已支付',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `productlabel` */

DROP TABLE IF EXISTS `productlabel`;

CREATE TABLE `productlabel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  `labelid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_id_label` (`productid`,`labelid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `province` */

DROP TABLE IF EXISTS `province`;

CREATE TABLE `province` (
  `id` varchar(15) NOT NULL,
  `code_p` varchar(15) NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`code_p`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='该实体为行政区域划分等级';

/*Table structure for table `public_room_fid_to_student` */

DROP TABLE IF EXISTS `public_room_fid_to_student`;

CREATE TABLE `public_room_fid_to_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_fid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `insert_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL COMMENT '学校ID',
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `purchase_member_trade` */

DROP TABLE IF EXISTS `purchase_member_trade`;

CREATE TABLE `purchase_member_trade` (
  `pmoid` varchar(20) NOT NULL,
  `mem_id` varchar(255) DEFAULT NULL,
  `memtype_id` int(11) DEFAULT NULL,
  `purchase_time` datetime DEFAULT NULL,
  PRIMARY KEY (`pmoid`),
  KEY `FK318B51FDFE193FFF` (`mem_id`),
  KEY `FK318B51FD429C075F` (`memtype_id`),
  CONSTRAINT `FK318B51FD429C075F` FOREIGN KEY (`memtype_id`) REFERENCES `membertype` (`memtypeid`),
  CONSTRAINT `FK318B51FDFE193FFF` FOREIGN KEY (`mem_id`) REFERENCES `member1` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `push_history` */

DROP TABLE IF EXISTS `push_history`;

CREATE TABLE `push_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `source_id` int(11) DEFAULT NULL,
  `source_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `source_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `source` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `user_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `epal_id` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `push_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qidi_user` */

DROP TABLE IF EXISTS `qidi_user`;

CREATE TABLE `qidi_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company` varchar(60) NOT NULL,
  `industry` varchar(60) NOT NULL,
  `name` varchar(20) NOT NULL,
  `wechat` varchar(60) DEFAULT NULL,
  `phone` varchar(30) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qiniu_compression_job` */

DROP TABLE IF EXISTS `qiniu_compression_job`;

CREATE TABLE `qiniu_compression_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` varchar(15) COLLATE utf8_unicode_ci NOT NULL COMMENT '任务id',
  `compression_id` varchar(125) COLLATE utf8_unicode_ci NOT NULL COMMENT '七牛压缩任务id',
  `status` tinyint(2) DEFAULT '0' COMMENT '压缩状态:1=已完成,2=未完成',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qiniu_file_log` */

DROP TABLE IF EXISTS `qiniu_file_log`;

CREATE TABLE `qiniu_file_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_url` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `access_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `qiniu_file_log_file_url` (`file_url`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(256) DEFAULT NULL,
  `question_type` varchar(56) DEFAULT NULL,
  `insert_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_app_accout` */

DROP TABLE IF EXISTS `qy_app_accout`;

CREATE TABLE `qy_app_accout` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_app_menu` */

DROP TABLE IF EXISTS `qy_app_menu`;

CREATE TABLE `qy_app_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_app_role` */

DROP TABLE IF EXISTS `qy_app_role`;

CREATE TABLE `qy_app_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_app_role_menu` */

DROP TABLE IF EXISTS `qy_app_role_menu`;

CREATE TABLE `qy_app_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `qy_class` */

DROP TABLE IF EXISTS `qy_class`;

CREATE TABLE `qy_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_class_to_student` */

DROP TABLE IF EXISTS `qy_class_to_student`;

CREATE TABLE `qy_class_to_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `status` smallint(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_device` */

DROP TABLE IF EXISTS `qy_device`;

CREATE TABLE `qy_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `belong` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_device_class` */

DROP TABLE IF EXISTS `qy_device_class`;

CREATE TABLE `qy_device_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_punch_action_rule` */

DROP TABLE IF EXISTS `qy_punch_action_rule`;

CREATE TABLE `qy_punch_action_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_id` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `qy_punch_action_type` */

DROP TABLE IF EXISTS `qy_punch_action_type`;

CREATE TABLE `qy_punch_action_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `msg` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `group` tinyint(4) DEFAULT NULL COMMENT '可删除',
  `group_id` int(11) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_punch_action_type_group` */

DROP TABLE IF EXISTS `qy_punch_action_type_group`;

CREATE TABLE `qy_punch_action_type_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `school_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_punch_record` */

DROP TABLE IF EXISTS `qy_punch_record`;

CREATE TABLE `qy_punch_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `punch_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `punch_image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `send_status` int(11) DEFAULT '0',
  `action` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `temperature` float(4,1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_student` */

DROP TABLE IF EXISTS `qy_student`;

CREATE TABLE `qy_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tel` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `photo` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(2) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_student_card` */

DROP TABLE IF EXISTS `qy_student_card`;

CREATE TABLE `qy_student_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_type` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `student_id` int(11) DEFAULT '0',
  `contacts` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `qy_user` */

DROP TABLE IF EXISTS `qy_user`;

CREATE TABLE `qy_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `rand_comment` */

DROP TABLE IF EXISTS `rand_comment`;

CREATE TABLE `rand_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `moment_id` int(11) DEFAULT NULL,
  `student_id` int(11) NOT NULL,
  `teacher_id` int(11) DEFAULT '0',
  `content` varchar(1000) CHARACTER SET utf8mb4 DEFAULT NULL,
  `createDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `audio_url` varchar(255) DEFAULT NULL,
  `audio_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `rand_comment_like` */

DROP TABLE IF EXISTS `rand_comment_like`;

CREATE TABLE `rand_comment_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rand_comment_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `comment_student` (`rand_comment_id`,`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `readbookrecord` */

DROP TABLE IF EXISTS `readbookrecord`;

CREATE TABLE `readbookrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookvname` varchar(255) DEFAULT NULL,
  `bookvid` varchar(255) DEFAULT NULL,
  `mem_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7F07EA90FE193FFF` (`mem_id`),
  CONSTRAINT `FK7F07EA90FE193FFF` FOREIGN KEY (`mem_id`) REFERENCES `member1` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `recommend` */

DROP TABLE IF EXISTS `recommend`;

CREATE TABLE `recommend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `replacement` */

DROP TABLE IF EXISTS `replacement`;

CREATE TABLE `replacement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_epal_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `current_epal_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(52) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `replies` */

DROP TABLE IF EXISTS `replies`;

CREATE TABLE `replies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(255) DEFAULT NULL,
  `discovery_id` int(11) DEFAULT NULL,
  `to_user` varchar(255) DEFAULT NULL,
  `agree` int(1) DEFAULT '0',
  `content` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `report_day_active` */

DROP TABLE IF EXISTS `report_day_active`;

CREATE TABLE `report_day_active` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `real_count` int(11) DEFAULT NULL,
  `show_count` int(11) DEFAULT NULL,
  `real_active_count` int(11) DEFAULT NULL,
  `show_active_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `resource_folder` */

DROP TABLE IF EXISTS `resource_folder`;

CREATE TABLE `resource_folder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `resource_folder_rel` */

DROP TABLE IF EXISTS `resource_folder_rel`;

CREATE TABLE `resource_folder_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `material_file_id` int(11) NOT NULL,
  `resource_folder_id` int(11) NOT NULL,
  `status` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `rfid` */

DROP TABLE IF EXISTS `rfid`;

CREATE TABLE `rfid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `card_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `rfid_associate` */

DROP TABLE IF EXISTS `rfid_associate`;

CREATE TABLE `rfid_associate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `rfid_student` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `student_rfid` (`student_id`,`rfid_student`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `rightcategory` */

DROP TABLE IF EXISTS `rightcategory`;

CREATE TABLE `rightcategory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `left_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB614A31A855A4818` (`left_id`),
  CONSTRAINT `FKB614A31A855A4818` FOREIGN KEY (`left_id`) REFERENCES `leftcategory` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `robot_expression` */

DROP TABLE IF EXISTS `robot_expression`;

CREATE TABLE `robot_expression` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `actionCmd` text COLLATE utf8_unicode_ci,
  `insert_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `robot_hourse` */

DROP TABLE IF EXISTS `robot_hourse`;

CREATE TABLE `robot_hourse` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_epal_id` varchar(30) DEFAULT NULL,
  `epal_id` varchar(70) DEFAULT NULL,
  `hourse_id` int(11) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `color` varchar(20) DEFAULT NULL,
  `row` int(3) DEFAULT '0',
  `col` int(3) DEFAULT '0',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `epal_hourse` (`epal_id`,`hourse_id`) USING BTREE,
  UNIQUE KEY `epal_id` (`epal_id`) USING BTREE,
  KEY `epal` (`stu_epal_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `robot_state` */

DROP TABLE IF EXISTS `robot_state`;

CREATE TABLE `robot_state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epal_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `action` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `insert_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `epal_id` (`epal_id`) USING BTREE,
  KEY `insert_time` (`insert_time`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `modularids` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `rolemodular` */

DROP TABLE IF EXISTS `rolemodular`;

CREATE TABLE `rolemodular` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) NOT NULL,
  `modularid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `room_package` */

DROP TABLE IF EXISTS `room_package`;

CREATE TABLE `room_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cover` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `summary` text COLLATE utf8_unicode_ci,
  `flag` int(11) DEFAULT NULL COMMENT '标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `room_package_rela` */

DROP TABLE IF EXISTS `room_package_rela`;

CREATE TABLE `room_package_rela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `package_id` int(11) DEFAULT NULL,
  `room_tree_id` int(11) DEFAULT NULL,
  `sort` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `room_package_specification` */

DROP TABLE IF EXISTS `room_package_specification`;

CREATE TABLE `room_package_specification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `count` int(11) NOT NULL,
  `price` double NOT NULL,
  `room_package_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `room_rela` */

DROP TABLE IF EXISTS `room_rela`;

CREATE TABLE `room_rela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `main_id` int(11) DEFAULT NULL COMMENT '主课roomID',
  `sub_id` int(11) DEFAULT NULL COMMENT '辅课roomId',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `room_tree` */

DROP TABLE IF EXISTS `room_tree`;

CREATE TABLE `room_tree` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pid` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL COMMENT '课程名',
  `agent_id` int(11) DEFAULT NULL COMMENT '加盟商',
  `type` int(8) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `scenario_english_video` */

DROP TABLE IF EXISTS `scenario_english_video`;

CREATE TABLE `scenario_english_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `v_title` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `v_url` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `v_pic` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `student_name` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `v_like` int(11) DEFAULT '0',
  `group_name` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `school` */

DROP TABLE IF EXISTS `school`;

CREATE TABLE `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `schoolName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `school_class` */

DROP TABLE IF EXISTS `school_class`;

CREATE TABLE `school_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `schoolId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `school_class_to_student` */

DROP TABLE IF EXISTS `school_class_to_student`;

CREATE TABLE `school_class_to_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classId` int(11) DEFAULT NULL,
  `studentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `school_credit_record` */

DROP TABLE IF EXISTS `school_credit_record`;

CREATE TABLE `school_credit_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `school_id` int(11) DEFAULT NULL,
  `action` smallint(6) DEFAULT NULL,
  `amount` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `school_teacher_rela` */

DROP TABLE IF EXISTS `school_teacher_rela`;

CREATE TABLE `school_teacher_rela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `school_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL COMMENT '老师账号的memberId',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `serviceitems` */

DROP TABLE IF EXISTS `serviceitems`;

CREATE TABLE `serviceitems` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `electrismid` int(11) DEFAULT NULL,
  `servicename` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `shapecode` */

DROP TABLE IF EXISTS `shapecode`;

CREATE TABLE `shapecode` (
  `shapecode` varchar(255) NOT NULL,
  PRIMARY KEY (`shapecode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `share_get_lesson` */

DROP TABLE IF EXISTS `share_get_lesson`;

CREATE TABLE `share_get_lesson` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inviter` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u` (`inviter`,`class_room_id`,`product_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `shipment_record` */

DROP TABLE IF EXISTS `shipment_record`;

CREATE TABLE `shipment_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '设备流水号',
  `user_id` int(11) DEFAULT NULL COMMENT '操作者ID',
  `agent_id` int(11) DEFAULT NULL COMMENT '加盟商ID',
  `bill_of_sales_id` int(11) DEFAULT NULL COMMENT '出货单ID',
  `create_time` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `shop_commodity` */

DROP TABLE IF EXISTS `shop_commodity`;

CREATE TABLE `shop_commodity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(125) NOT NULL,
  `description` varchar(125) DEFAULT NULL,
  `content` text,
  `status` tinyint(4) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `shop_commodity_specification` */

DROP TABLE IF EXISTS `shop_commodity_specification`;

CREATE TABLE `shop_commodity_specification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commodity_id` int(11) NOT NULL,
  `name` varchar(125) NOT NULL,
  `price` decimal(5,2) NOT NULL,
  `status` tinyint(4) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `shoporder` */

DROP TABLE IF EXISTS `shoporder`;

CREATE TABLE `shoporder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ordernumber` varchar(50) DEFAULT NULL,
  `memberid` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `shopid` int(11) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `type` int(3) DEFAULT '1' COMMENT '订单类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `shoporderinfo` */

DROP TABLE IF EXISTS `shoporderinfo`;

CREATE TABLE `shoporderinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `barcode` varchar(50) DEFAULT NULL,
  `orderid` int(11) DEFAULT NULL,
  `cateid` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `shoppingcart` */

DROP TABLE IF EXISTS `shoppingcart`;

CREATE TABLE `shoppingcart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  `specifications` varchar(200) DEFAULT NULL,
  `productname` varchar(50) DEFAULT NULL,
  `productimg` varchar(200) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `memberid` int(11) DEFAULT NULL,
  `specifications_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `sms_ingore_number` */

DROP TABLE IF EXISTS `sms_ingore_number`;

CREATE TABLE `sms_ingore_number` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `smscode` */

DROP TABLE IF EXISTS `smscode`;

CREATE TABLE `smscode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `snp_mall_banner` */

DROP TABLE IF EXISTS `snp_mall_banner`;

CREATE TABLE `snp_mall_banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pic` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '商城首页轮播图',
  `link` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片跳转地址',
  `status` int(11) DEFAULT '0' COMMENT '默认0未启用 ,  1启用',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `sound_alias` */

DROP TABLE IF EXISTS `sound_alias`;

CREATE TABLE `sound_alias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aliasName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `soundId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `soundId` (`soundId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `sound_best` */

DROP TABLE IF EXISTS `sound_best`;

CREATE TABLE `sound_best` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sound_id` varchar(11) DEFAULT NULL,
  `image` varchar(256) DEFAULT NULL,
  `sound_url` varchar(256) DEFAULT NULL,
  `sound_name` varchar(256) DEFAULT NULL,
  `serier_name` varchar(256) DEFAULT NULL,
  `isbn` varchar(128) DEFAULT NULL,
  `insert_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `sound_collection` */

DROP TABLE IF EXISTS `sound_collection`;

CREATE TABLE `sound_collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sound_id` varchar(56) DEFAULT NULL COMMENT '音频ID',
  `image` varchar(256) DEFAULT NULL COMMENT '音频地址',
  `sound_url` varchar(256) DEFAULT NULL COMMENT '音频名称',
  `sound_name` varchar(128) DEFAULT NULL COMMENT 'isbn编码',
  `serier_name` varchar(256) DEFAULT NULL COMMENT '系列名称',
  `isbn` varchar(128) DEFAULT NULL,
  `user_id` varchar(128) DEFAULT NULL,
  `insert_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(2) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `sound_recommend_group` */

DROP TABLE IF EXISTS `sound_recommend_group`;

CREATE TABLE `sound_recommend_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tagEN` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `channelId` int(11) DEFAULT NULL,
  `tagCN` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `sound_recommend_group_to_sound` */

DROP TABLE IF EXISTS `sound_recommend_group_to_sound`;

CREATE TABLE `sound_recommend_group_to_sound` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupId` int(11) DEFAULT NULL,
  `soundId` int(11) DEFAULT NULL,
  `albumId` int(11) DEFAULT NULL,
  `sortId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id` (`groupId`) USING BTREE,
  KEY `sound_id` (`soundId`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `sound_search` */

DROP TABLE IF EXISTS `sound_search`;

CREATE TABLE `sound_search` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `soundName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `insertDate` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `userName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `page` varchar(255) COLLATE utf8_unicode_ci DEFAULT '1',
  `ISBN` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remoteIP` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sound_search_soundName` (`soundName`) USING BTREE,
  KEY `sound_search_insertDate` (`insertDate`) USING BTREE,
  KEY `sound_search_userName` (`userName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `sound_tag` */

DROP TABLE IF EXISTS `sound_tag`;

CREATE TABLE `sound_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tagName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `sortId` int(11) DEFAULT '1',
  `channelId` int(11) DEFAULT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `intro` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `sound_tag_group` */

DROP TABLE IF EXISTS `sound_tag_group`;

CREATE TABLE `sound_tag_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `sortId` int(11) DEFAULT '1',
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `intro` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `sound_tag_source` */

DROP TABLE IF EXISTS `sound_tag_source`;

CREATE TABLE `sound_tag_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tagId` int(11) DEFAULT NULL,
  `sourceId` int(11) DEFAULT NULL,
  `sourceType` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sortId` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sound_tag_source` (`tagId`,`sourceId`,`sourceType`) USING HASH,
  KEY `sound_tag_source_sourceId` (`sourceId`) USING BTREE,
  KEY `sound_tag_source_tagId` (`tagId`) USING BTREE,
  KEY `sound_tag_source_sortId` (`sortId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `special` */

DROP TABLE IF EXISTS `special`;

CREATE TABLE `special` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `logo` varchar(200) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `cat_id` int(11) DEFAULT '0',
  `topic_type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `specialinfo` */

DROP TABLE IF EXISTS `specialinfo`;

CREATE TABLE `specialinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categoryid` varchar(20) DEFAULT NULL,
  `specialid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `spread_relation` */

DROP TABLE IF EXISTS `spread_relation`;

CREATE TABLE `spread_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `r_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `std_diy_study_day` */

DROP TABLE IF EXISTS `std_diy_study_day`;

CREATE TABLE `std_diy_study_day` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `std_id` int(11) DEFAULT NULL COMMENT '学生ID',
  `grade_id` int(11) DEFAULT NULL COMMENT '班级Id',
  `rule` text COLLATE utf8_unicode_ci COMMENT '上课规则',
  `rule_type` int(255) DEFAULT '1' COMMENT '规则类型',
  `week` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '周几上课规则',
  `self_study_week` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '自习课上课规则',
  `is_teacher_default` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `class_a` (`std_id`,`grade_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `std_to_agent` */

DROP TABLE IF EXISTS `std_to_agent`;

CREATE TABLE `std_to_agent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `s_id` int(11) DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `stroe_product` */

DROP TABLE IF EXISTS `stroe_product`;

CREATE TABLE `stroe_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(125) NOT NULL,
  `price_interval` varchar(125) DEFAULT NULL,
  `content` text,
  `classroom_pack_id` int(11) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `student_lesson_quality` */

DROP TABLE IF EXISTS `student_lesson_quality`;

CREATE TABLE `student_lesson_quality` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `quality_script` text COLLATE utf8_unicode_ci,
  `class_room_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `student_video_show` */

DROP TABLE IF EXISTS `student_video_show`;

CREATE TABLE `student_video_show` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `v_title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `v_url` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `v_pic` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `student_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `is_show` int(11) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `study_coin_card` */

DROP TABLE IF EXISTS `study_coin_card`;

CREATE TABLE `study_coin_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL,
  `number` varchar(30) NOT NULL,
  `code` varchar(20) NOT NULL,
  `status` tinyint(4) DEFAULT '0',
  `account` varchar(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `study_coin_card_category` */

DROP TABLE IF EXISTS `study_coin_card_category`;

CREATE TABLE `study_coin_card_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `object` varchar(125) NOT NULL,
  `prefix` varchar(10) NOT NULL,
  `coin_count` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `expire_time` date NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `study_coin_order` */

DROP TABLE IF EXISTS `study_coin_order`;

CREATE TABLE `study_coin_order` (
  `int` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(32) DEFAULT NULL,
  `count` int(11) DEFAULT '0' COMMENT '废除',
  `total_price` tinyint(4) DEFAULT '0' COMMENT '废除',
  `coin_pack_id` int(11) NOT NULL,
  `price` decimal(6,2) DEFAULT NULL,
  `account` int(11) DEFAULT NULL,
  `status` smallint(6) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`int`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `study_coin_order2` */

DROP TABLE IF EXISTS `study_coin_order2`;

CREATE TABLE `study_coin_order2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(20) NOT NULL,
  `study_coin_pack_id` int(11) NOT NULL,
  `total_price` decimal(6,2) DEFAULT NULL,
  `account_id` int(11) NOT NULL,
  `status` tinyint(4) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `study_coin_pack` */

DROP TABLE IF EXISTS `study_coin_pack`;

CREATE TABLE `study_coin_pack` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apple_id` varchar(20) NOT NULL,
  `name` varchar(125) NOT NULL,
  `count` int(11) NOT NULL,
  `total_price` decimal(6,2) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `study_coin_record` */

DROP TABLE IF EXISTS `study_coin_record`;

CREATE TABLE `study_coin_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` smallint(6) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `remark` varchar(125) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `study_photo` */

DROP TABLE IF EXISTS `study_photo`;

CREATE TABLE `study_photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) DEFAULT NULL COMMENT '课程表id',
  `student_id` int(11) DEFAULT NULL COMMENT '学生id',
  `pic_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '学习照片地址',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `study_recorder` */

DROP TABLE IF EXISTS `study_recorder`;

CREATE TABLE `study_recorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `studentId` int(11) DEFAULT NULL COMMENT '学生id',
  `content` text COLLATE utf8_unicode_ci COMMENT '内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '类型',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `sys_dict` */

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文本',
  `pid` int(11) DEFAULT NULL COMMENT '父节点',
  `code` int(5) DEFAULT NULL COMMENT 'code',
  `str_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '字符串类型的Code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `system_dictionary` */

DROP TABLE IF EXISTS `system_dictionary`;

CREATE TABLE `system_dictionary` (
  `value` varchar(16) DEFAULT NULL,
  `detail` varchar(125) DEFAULT NULL,
  `object` varchar(32) DEFAULT NULL,
  UNIQUE KEY `index1` (`value`,`object`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_class` */

DROP TABLE IF EXISTS `t_class`;

CREATE TABLE `t_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_grade` */

DROP TABLE IF EXISTS `t_grade`;

CREATE TABLE `t_grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_school` */

DROP TABLE IF EXISTS `t_school`;

CREATE TABLE `t_school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code_s` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `code_a` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `table` */

DROP TABLE IF EXISTS `table`;

CREATE TABLE `table` (
  `id` int(11) NOT NULL,
  `bane` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tabletemplate` */

DROP TABLE IF EXISTS `tabletemplate`;

CREATE TABLE `tabletemplate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `groups` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `teacher_collection_class_room` */

DROP TABLE IF EXISTS `teacher_collection_class_room`;

CREATE TABLE `teacher_collection_class_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT NULL,
  `class_room_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `teacher_important_class_grades` */

DROP TABLE IF EXISTS `teacher_important_class_grades`;

CREATE TABLE `teacher_important_class_grades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_grades_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `teacher_question` */

DROP TABLE IF EXISTS `teacher_question`;

CREATE TABLE `teacher_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `b_reply` tinyint(2) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `tempaa` */

DROP TABLE IF EXISTS `tempaa`;

CREATE TABLE `tempaa` (
  `mobile` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `temporary_login_code` */

DROP TABLE IF EXISTS `temporary_login_code`;

CREATE TABLE `temporary_login_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) DEFAULT NULL,
  `code` varchar(12) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '-1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `text_filter` */

DROP TABLE IF EXISTS `text_filter`;

CREATE TABLE `text_filter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(125) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `thumb_up` */

DROP TABLE IF EXISTS `thumb_up`;

CREATE TABLE `thumb_up` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `moment_id` int(11) DEFAULT '0',
  `status` tinyint(4) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `moment_user` (`user_id`,`moment_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `tixian_bili` */

DROP TABLE IF EXISTS `tixian_bili`;

CREATE TABLE `tixian_bili` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `juese` varchar(125) DEFAULT NULL COMMENT '角色',
  `bili` varchar(125) DEFAULT NULL COMMENT '提现比例(%)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `top_agent` */

DROP TABLE IF EXISTS `top_agent`;

CREATE TABLE `top_agent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oss_account_id` int(11) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `company_id` int(11) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `train_answer_item` */

DROP TABLE IF EXISTS `train_answer_item`;

CREATE TABLE `train_answer_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` tinyint(2) DEFAULT NULL COMMENT '类型：1-文字；2-音频；3-图片',
  `content` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '内容',
  `priority` tinyint(4) DEFAULT '2' COMMENT '优先级，0-5，越大优先级越高',
  `loop_count` tinyint(4) DEFAULT '0' COMMENT '循环次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `train_answer_page` */

DROP TABLE IF EXISTS `train_answer_page`;

CREATE TABLE `train_answer_page` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answer_id` int(11) DEFAULT NULL,
  `content` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `train_knowledge` */

DROP TABLE IF EXISTS `train_knowledge`;

CREATE TABLE `train_knowledge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `member_id` int(11) DEFAULT NULL,
  `epal_id` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `train_knowledge_answer_rela` */

DROP TABLE IF EXISTS `train_knowledge_answer_rela`;

CREATE TABLE `train_knowledge_answer_rela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `knowledge_id` int(11) DEFAULT NULL,
  `answer_item_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `train_knowledge_question_rela` */

DROP TABLE IF EXISTS `train_knowledge_question_rela`;

CREATE TABLE `train_knowledge_question_rela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `question_item_id` int(11) DEFAULT NULL,
  `knowledge_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `train_question_item` */

DROP TABLE IF EXISTS `train_question_item`;

CREATE TABLE `train_question_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `transaction_records` */

DROP TABLE IF EXISTS `transaction_records`;

CREATE TABLE `transaction_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `wallet_id` int(11) DEFAULT NULL COMMENT '钱包ID',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `for` int(11) DEFAULT NULL COMMENT '用途',
  `amount` int(20) DEFAULT NULL COMMENT '金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `trial_user` */

DROP TABLE IF EXISTS `trial_user`;

CREATE TABLE `trial_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company` varchar(60) NOT NULL,
  `industry` varchar(60) NOT NULL,
  `name` varchar(20) NOT NULL,
  `wechat` varchar(60) DEFAULT NULL,
  `phone` varchar(30) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `sex` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  `createdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastdate` datetime DEFAULT NULL,
  `operatorid` int(11) DEFAULT NULL,
  `mark` varchar(255) DEFAULT NULL,
  `releid` int(11) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `user_answer_history` */

DROP TABLE IF EXISTS `user_answer_history`;

CREATE TABLE `user_answer_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(128) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `insert_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `answer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `user_channel` */

DROP TABLE IF EXISTS `user_channel`;

CREATE TABLE `user_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户ID',
  `channel_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '频道ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `user_recommend_record` */

DROP TABLE IF EXISTS `user_recommend_record`;

CREATE TABLE `user_recommend_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recommend_from_memberid` int(11) DEFAULT '76',
  `recommend_to_memberid` int(11) DEFAULT NULL,
  `type` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `user_sign` */

DROP TABLE IF EXISTS `user_sign`;

CREATE TABLE `user_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NOT NULL,
  `uname` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `ugrade` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uage` int(3) DEFAULT NULL,
  `mobile` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sign_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_id` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `user_to_class_grades` */

DROP TABLE IF EXISTS `user_to_class_grades`;

CREATE TABLE `user_to_class_grades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `class_grades_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `useraddress` */

DROP TABLE IF EXISTS `useraddress`;

CREATE TABLE `useraddress` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `area` varchar(100) DEFAULT NULL,
  `lng` varchar(50) DEFAULT NULL,
  `lat` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_access` */

DROP TABLE IF EXISTS `video_access`;

CREATE TABLE `video_access` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` int(11) NOT NULL,
  `week` int(11) NOT NULL,
  `access_num` int(11) NOT NULL,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_activity` */

DROP TABLE IF EXISTS `video_activity`;

CREATE TABLE `video_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cover` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `scroll_text` text COLLATE utf8_unicode_ci,
  `rule` text COLLATE utf8_unicode_ci,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_collection` */

DROP TABLE IF EXISTS `video_collection`;

CREATE TABLE `video_collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `v_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `status` int(2) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `video_vid_memberid` (`v_id`,`member_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_comment` */

DROP TABLE IF EXISTS `video_comment`;

CREATE TABLE `video_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vid` int(11) NOT NULL,
  `commenter` varchar(225) COLLATE utf8mb4_bin NOT NULL,
  `openid` varchar(225) COLLATE utf8mb4_bin NOT NULL,
  `content` varchar(225) COLLATE utf8mb4_bin NOT NULL,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sort` int(3) NOT NULL,
  `member_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_comment_miniapp` */

DROP TABLE IF EXISTS `video_comment_miniapp`;

CREATE TABLE `video_comment_miniapp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `video_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `video_competition` */

DROP TABLE IF EXISTS `video_competition`;

CREATE TABLE `video_competition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `video_info_id` int(11) DEFAULT NULL,
  `video_activity_id` int(11) DEFAULT NULL,
  `acoutn_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `verify_status` int(11) DEFAULT NULL COMMENT '审核状态',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_info` */

DROP TABLE IF EXISTS `video_info`;

CREATE TABLE `video_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `v_title` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `v_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `s_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_icelandic_ci DEFAULT NULL,
  `localfile_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `share_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `epal_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `acount_id` varchar(30) CHARACTER SET utf8 NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `admission` int(5) DEFAULT '0' COMMENT '录取状态',
  `vote` int(11) DEFAULT '0' COMMENT '投票数',
  `access_num` int(11) DEFAULT '0' COMMENT '资源访问次数',
  `video_score_id` int(11) DEFAULT NULL COMMENT '专家评分id',
  `v_like` int(11) DEFAULT '0' COMMENT '点赞数',
  `v_reward` double DEFAULT '0' COMMENT '视频打赏',
  `learn_time` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '学龄',
  `comment_status` int(3) DEFAULT '-1' COMMENT '邀请状态',
  `video_description` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '视频描述',
  `is_quote` int(2) DEFAULT '0' COMMENT '是否推荐',
  `status` int(2) DEFAULT '0',
  `learn_day` int(11) DEFAULT '1' COMMENT '自第一次上课的时间到视频上传时间的天数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `video_info_v_url` (`v_url`),
  KEY `video_info_account_id` (`acount_id`),
  KEY `video_info_share_url` (`share_url`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_invite_message` */

DROP TABLE IF EXISTS `video_invite_message`;

CREATE TABLE `video_invite_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vid` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  `message` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_like` */

DROP TABLE IF EXISTS `video_like`;

CREATE TABLE `video_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `vid` int(11) DEFAULT NULL,
  `add_date` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `member_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_like_recoad` */

DROP TABLE IF EXISTS `video_like_recoad`;

CREATE TABLE `video_like_recoad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `v_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `status` int(2) DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `video_vid_memberid` (`v_id`,`member_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_member_vote_count` */

DROP TABLE IF EXISTS `video_member_vote_count`;

CREATE TABLE `video_member_vote_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `count` int(3) DEFAULT '0',
  `day` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_pay_record` */

DROP TABLE IF EXISTS `video_pay_record`;

CREATE TABLE `video_pay_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vid` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `price` double NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_score` */

DROP TABLE IF EXISTS `video_score`;

CREATE TABLE `video_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `video_info_id` int(11) DEFAULT NULL,
  `expert1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `expert2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '专家2打分',
  `expert3` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '专家3打分',
  `expert4` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '专家4打分',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `video_score_video_info_id` (`video_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `video_vote` */

DROP TABLE IF EXISTS `video_vote`;

CREATE TABLE `video_vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `vid` int(11) DEFAULT NULL,
  `vote_date` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `wallet` */

DROP TABLE IF EXISTS `wallet`;

CREATE TABLE `wallet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT NULL,
  `type` int(3) DEFAULT NULL COMMENT '钱包类型',
  `balance` int(11) DEFAULT NULL COMMENT '余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `wechat_api` */

DROP TABLE IF EXISTS `wechat_api`;

CREATE TABLE `wechat_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_url` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `method` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `paramter` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `module` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `package` varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `wechat_lesson_comment` */

DROP TABLE IF EXISTS `wechat_lesson_comment`;

CREATE TABLE `wechat_lesson_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_room_id` int(11) DEFAULT NULL,
  `openid` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` varchar(125) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `wechat_user` */

DROP TABLE IF EXISTS `wechat_user`;

CREATE TABLE `wechat_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sex` tinyint(2) DEFAULT '0',
  `openid` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `headimgurl` varchar(225) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `province` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `country` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `unionid` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `subscribe_time` timestamp NULL DEFAULT NULL,
  `b_subscribe` tinyint(4) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_login` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid` (`openid`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `well_lesson` */

DROP TABLE IF EXISTS `well_lesson`;

CREATE TABLE `well_lesson` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bespeak_id` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '上课ID',
  `class_time` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '上课时间',
  `teacher_id` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '外教名称',
  `school_name` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '学校名称',
  `class_name` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '班级名称',
  `chinese_teacher_name` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '中教名称',
  `package_name` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '套餐名称',
  `book_name` varchar(125) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '课本名称',
  `book_id` int(11) DEFAULT NULL COMMENT '课本ID',
  `state_school_id` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '学校ID',
  `status` tinyint(2) DEFAULT '0' COMMENT '上课状态 --- 0:未上,1:已上',
  `lesson_from` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '直播课来源',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `works_eval` */

DROP TABLE IF EXISTS `works_eval`;

CREATE TABLE `works_eval` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `creat_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT NULL COMMENT '评分人员id',
  `work_id` int(11) DEFAULT NULL COMMENT '作品ID',
  `competition_id` int(11) DEFAULT NULL COMMENT '比赛ID',
  `score` int(3) DEFAULT NULL COMMENT '得分',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `type` int(3) DEFAULT NULL COMMENT '类型，备用字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `xmly_album` */

DROP TABLE IF EXISTS `xmly_album`;

CREATE TABLE `xmly_album` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `album_id` int(11) DEFAULT NULL,
  `channel_id` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `intro` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sort_id` int(11) DEFAULT NULL,
  `updateTime` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `xmly_album_name` (`name`) USING BTREE,
  KEY `xmly_album_chanelId` (`channel_id`) USING HASH,
  KEY `xmly_album_updateTime` (`updateTime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `xmly_album_sound` */

DROP TABLE IF EXISTS `xmly_album_sound`;

CREATE TABLE `xmly_album_sound` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `albumId` int(11) DEFAULT NULL,
  `soundId` int(11) DEFAULT NULL,
  `sortId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `xmly_album_sound_albumId` (`albumId`) USING HASH,
  KEY `soundId` (`soundId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `xmly_channel` */

DROP TABLE IF EXISTS `xmly_channel`;

CREATE TABLE `xmly_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fans` int(11) DEFAULT NULL,
  `intro` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `channel_id` int(11) DEFAULT NULL,
  `status` int(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `lastUpdateTime` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nextUpdateTime` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `updateCycle` int(11) DEFAULT '12',
  PRIMARY KEY (`id`),
  KEY `channel_union` (`channel_id`) USING HASH,
  KEY `xmly_channel_name` (`name`) USING HASH,
  KEY `xmly_channel_nextUpdateTime` (`nextUpdateTime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `xmly_channel_album` */

DROP TABLE IF EXISTS `xmly_channel_album`;

CREATE TABLE `xmly_channel_album` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channelId` int(11) DEFAULT NULL,
  `albumId` int(11) DEFAULT NULL,
  `sortId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `xmly_channel_album_channelId` (`channelId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `xmly_sound` */

DROP TABLE IF EXISTS `xmly_sound`;

CREATE TABLE `xmly_sound` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `soundId` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `intro` varchar(1024) DEFAULT NULL,
  `playTimes` int(11) DEFAULT NULL,
  `playUrl` varchar(255) DEFAULT NULL,
  `updateTime` varchar(255) DEFAULT NULL,
  `albumName` varchar(255) DEFAULT NULL,
  `channelId` int(11) DEFAULT NULL,
  `albumId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sortId` int(11) DEFAULT NULL,
  `cate_id` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `fd_playCount` int(11) DEFAULT NULL,
  `process` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `xmly_sound_name` (`name`) USING HASH,
  KEY `xmly_sound_channeId` (`channelId`) USING HASH,
  KEY `xmly_sound_albumId` (`albumId`) USING BTREE,
  KEY `xmly_sound_soundId` (`soundId`) USING BTREE,
  KEY `xmly_sound_score` (`score`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/*Table structure for table `zhubo_alias` */

DROP TABLE IF EXISTS `zhubo_alias`;

CREATE TABLE `zhubo_alias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aliasName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zhuboId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

/* Function  structure for function  `getParLst` */

/*!50003 DROP FUNCTION IF EXISTS `getParLst` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`wechat_user`@`%` FUNCTION `getParLst`(rootId INT) RETURNS text CHARSET utf8mb4
BEGIN

	DECLARE sTemp TEXT;

    DECLARE sTempChd TEXT;



    SET sTemp = rootId;

    SET sTempChd =cast(rootId as CHAR);



    WHILE sTempChd is not null DO

    	SET sTemp = concat(sTemp,',',sTempChd);

        SELECT group_concat(id) INTO sTempChd FROM  material_path where FIND_IN_SET(pid,sTempChd)>0;

   	END WHILE;

    RETURN sTemp; 

END */$$
DELIMITER ;

/* Function  structure for function  `getParLst1` */

/*!50003 DROP FUNCTION IF EXISTS `getParLst1` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`wechat_user`@`%` FUNCTION `getParLst1`(code1 INT) RETURNS text CHARSET utf8mb4
BEGIN

	DECLARE sTemp TEXT;

    DECLARE sTempChd TEXT;



    SET sTemp = code1;

    SET sTempChd =cast(code1 as CHAR);



    WHILE sTempChd is not null DO

    	SET sTemp = concat(sTemp,',',sTempChd);

        SELECT group_concat(invite_code) INTO sTempChd FROM  v_invite_record where FIND_IN_SET(`code`,sTempChd)>0;

   	END WHILE;

    RETURN sTemp; 

END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_changeRobot` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_changeRobot` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`wechat_user`@`%` PROCEDURE `sp_changeRobot`(`newEpalId` varchar(50),`oldEpalId` varchar(50))
BEGIN

	set @newEpalId = newEpalId;
	SET @oldEpalId = oldEpalId;

  set @epal_temp = concat(@newEpalId, @oldEpalId);



	
	
	CALL wechat.sp_clearData(@newEpalId);
	

	
	update class_student set epal_id= @newEpalId where epal_id= @oldEpalId;
	UPDATE history_schedules SET epalId = @newEpalId WHERE epalId = @oldEpalId;
	
	
	CALL wechat.sp_justClearData(@oldEpalId);
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_clearData` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_clearData` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`wechat_user`@`%` PROCEDURE `sp_clearData`(IN epalId  varchar(50))
BEGIN
SET @epalId = epalId;
CALL wechat.sp_justClearRobotData(@epalId);

set @dt = now();
set @epal_old = concat(epalId, concat('-',date_format(@dt, '%Y%m%d')));

update class_student set epal_id= @epal_old where epal_id= @epalId;
UPDATE history_schedules SET epalId = @epal_old WHERE epalId = @epalId;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_justClearRobotData` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_justClearRobotData` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`wechat_user`@`%` PROCEDURE `sp_justClearRobotData`(IN `epalId` varchar(50))
BEGIN

SET @epalId = epalId;

delete from friends where friend_id= @epalId;

delete from courseproject where system_plan=-1 and epalId= @epalId;
delete from course_schedule where epal_id= @epalId;
delete from course_schedule_now where epal_id= @epalId and course_schedule_now.project_id not in (select id from courseproject where epalId= @epalId);
update course_schedule_now set cur_class=1 where epal_id= @epalId;

delete from device_schedule where is_def=0 and epal_id= @epalId;

delete from device_relation where epal_id= @epalId;
update device set nickname= @epalId where epal_id= @epalId;

END */$$
DELIMITER ;

/*Table structure for table `account_student_relation` */

DROP TABLE IF EXISTS `account_student_relation`;

/*!50001 DROP VIEW IF EXISTS `account_student_relation` */;
/*!50001 DROP TABLE IF EXISTS `account_student_relation` */;

/*!50001 CREATE TABLE  `account_student_relation`(
 `id` int(1) ,
 `account` int(1) ,
 `name` int(1) 
)*/;

/*Table structure for table `agent_class_room` */

DROP TABLE IF EXISTS `agent_class_room`;

/*!50001 DROP VIEW IF EXISTS `agent_class_room` */;
/*!50001 DROP TABLE IF EXISTS `agent_class_room` */;

/*!50001 CREATE TABLE  `agent_class_room`(
 `id` int(1) ,
 `teacher_id` int(1) ,
 `teacher_name` int(1) ,
 `class_name` int(1) ,
 `cover` int(1) ,
 `summary` int(1) ,
 `status` int(1) ,
 `sort` int(1) ,
 `category_id` int(1) ,
 `book_res_id` int(1) ,
 `group_id` int(1) ,
 `create_time` int(1) ,
 `class_room_type` int(1) ,
 `video_url` int(1) ,
 `type` int(1) ,
 `book_res_ids` int(1) ,
 `difficulty_level` int(1) ,
 `original` int(1) ,
 `member_account_id` int(1) ,
 `agent_id` int(1) 
)*/;

/*Table structure for table `class_course_record_eova_template` */

DROP TABLE IF EXISTS `class_course_record_eova_template`;

/*!50001 DROP VIEW IF EXISTS `class_course_record_eova_template` */;
/*!50001 DROP TABLE IF EXISTS `class_course_record_eova_template` */;

/*!50001 CREATE TABLE  `class_course_record_eova_template`(
 `class_grades_id` int(1) ,
 `class_course_id` int(1) ,
 `do_title` int(1) ,
 `do_slot` int(1) ,
 `student_id` int(1) ,
 `complete` int(1) ,
 `score` int(1) ,
 `used_time` int(1) ,
 `complete_time` int(1) 
)*/;

/*Table structure for table `fandou_user_role_rel` */

DROP TABLE IF EXISTS `fandou_user_role_rel`;

/*!50001 DROP VIEW IF EXISTS `fandou_user_role_rel` */;
/*!50001 DROP TABLE IF EXISTS `fandou_user_role_rel` */;

/*!50001 CREATE TABLE  `fandou_user_role_rel`(
 `school_id` int(11) ,
 `agent_id` int(11) ,
 `top_id` int(11) ,
 `school_name` varchar(64) 
)*/;

/*Table structure for table `school_grades` */

DROP TABLE IF EXISTS `school_grades`;

/*!50001 DROP VIEW IF EXISTS `school_grades` */;
/*!50001 DROP TABLE IF EXISTS `school_grades` */;

/*!50001 CREATE TABLE  `school_grades`(
 `school_id` int(11) ,
 `teacher_id` int(11) ,
 `class_grades_id` int(11) ,
 `class_grades_name` varchar(255) 
)*/;

/*Table structure for table `school_student` */

DROP TABLE IF EXISTS `school_student`;

/*!50001 DROP VIEW IF EXISTS `school_student` */;
/*!50001 DROP TABLE IF EXISTS `school_student` */;

/*!50001 CREATE TABLE  `school_student`(
 `id` int(11) ,
 `name` varchar(255) ,
 `epal_id` varchar(255) ,
 `create_time` timestamp ,
 `student_type` int(255) ,
 `sort_id` int(11) ,
 `integral` int(11) ,
 `contribution` int(11) ,
 `remark` varchar(255) ,
 `member_id` int(11) ,
 `degree_of_difficulty` float ,
 `avatar` varchar(255) ,
 `status` int(11) ,
 `lesson_integral` double(11,2) ,
 `agent_id` int(11) ,
 `sex` tinyint(4) 
)*/;

/*Table structure for table `student_account` */

DROP TABLE IF EXISTS `student_account`;

/*!50001 DROP VIEW IF EXISTS `student_account` */;
/*!50001 DROP TABLE IF EXISTS `student_account` */;

/*!50001 CREATE TABLE  `student_account`(
 `id` int(11) ,
 `account` varchar(100) 
)*/;

/*Table structure for table `student_grades` */

DROP TABLE IF EXISTS `student_grades`;

/*!50001 DROP VIEW IF EXISTS `student_grades` */;
/*!50001 DROP TABLE IF EXISTS `student_grades` */;

/*!50001 CREATE TABLE  `student_grades`(
 `id` int(11) ,
 `name` varchar(255) ,
 `epal_id` varchar(255) ,
 `create_time` timestamp ,
 `student_type` int(255) ,
 `sort_id` int(11) ,
 `class_grades_name` varchar(255) ,
 `agent_id` int(11) 
)*/;

/*Table structure for table `student_grades_new` */

DROP TABLE IF EXISTS `student_grades_new`;

/*!50001 DROP VIEW IF EXISTS `student_grades_new` */;
/*!50001 DROP TABLE IF EXISTS `student_grades_new` */;

/*!50001 CREATE TABLE  `student_grades_new`(
 `id` int(11) ,
 `name` varchar(255) ,
 `epal_id` varchar(255) ,
 `create_time` timestamp ,
 `student_type` int(255) ,
 `sort_id` int(11) ,
 `class_grades_id` int(11) ,
 `class_grades_name` varchar(255) ,
 `agent_id` int(11) 
)*/;

/*Table structure for table `studentwithclassid` */

DROP TABLE IF EXISTS `studentwithclassid`;

/*!50001 DROP VIEW IF EXISTS `studentwithclassid` */;
/*!50001 DROP TABLE IF EXISTS `studentwithclassid` */;

/*!50001 CREATE TABLE  `studentwithclassid`(
 `class_grades_id` int(11) ,
 `id` int(11) ,
 `name` varchar(255) ,
 `epal_id` varchar(255) ,
 `create_time` timestamp ,
 `student_type` int(255) ,
 `sort_id` int(11) ,
 `remark` varchar(255) ,
 `fid` varchar(255) 
)*/;

/*Table structure for table `success_order` */

DROP TABLE IF EXISTS `success_order`;

/*!50001 DROP VIEW IF EXISTS `success_order` */;
/*!50001 DROP TABLE IF EXISTS `success_order` */;

/*!50001 CREATE TABLE  `success_order`(
 `nickname` varchar(100) ,
 `headimgurl` varchar(300) ,
 `openid` varchar(100) ,
 `ordernumber` varchar(50) ,
 `memberId` int(11) ,
 `status` int(11) ,
 `createdate` datetime ,
 `totalprice` double ,
 `productid` int(11) ,
 `productName` varchar(50) ,
 `price` double ,
 `count` int(11) ,
 `mobile` varchar(20) 
)*/;

/*Table structure for table `teacher_grade_product` */

DROP TABLE IF EXISTS `teacher_grade_product`;

/*!50001 DROP VIEW IF EXISTS `teacher_grade_product` */;
/*!50001 DROP TABLE IF EXISTS `teacher_grade_product` */;

/*!50001 CREATE TABLE  `teacher_grade_product`(
 `teacher_id` int(11) ,
 `id` int(11) ,
 `name` varchar(200) ,
 `createdate` datetime ,
 `content` text ,
 `accountid` int(11) ,
 `logo1` varchar(200) ,
 `logo2` varchar(200) ,
 `logo3` varchar(200) ,
 `status` int(11) ,
 `price` varchar(100) ,
 `mp3` varchar(1000) ,
 `mp3type` int(11) ,
 `cat_id` int(11) ,
 `class_room_package` int(11) ,
 `class_grade_id` int(11) ,
 `labelid` int(11) 
)*/;

/*Table structure for table `teacher_grade_student` */

DROP TABLE IF EXISTS `teacher_grade_student`;

/*!50001 DROP VIEW IF EXISTS `teacher_grade_student` */;
/*!50001 DROP TABLE IF EXISTS `teacher_grade_student` */;

/*!50001 CREATE TABLE  `teacher_grade_student`(
 `teacher_id` int(11) ,
 `teacher_agent_id` int(11) ,
 `class_grades_id` int(11) ,
 `class_grades_name` varchar(255) ,
 `student_id` int(11) ,
 `name` varchar(255) ,
 `epal_id` varchar(255) ,
 `create_time` timestamp ,
 `sex` tinyint(2) 
)*/;

/*Table structure for table `v_agent` */

DROP TABLE IF EXISTS `v_agent`;

/*!50001 DROP VIEW IF EXISTS `v_agent` */;
/*!50001 DROP TABLE IF EXISTS `v_agent` */;

/*!50001 CREATE TABLE  `v_agent`(
 `epal_id` varchar(255) ,
 `user_id` int(11) 
)*/;

/*Table structure for table `v_agent_grades_rela` */

DROP TABLE IF EXISTS `v_agent_grades_rela`;

/*!50001 DROP VIEW IF EXISTS `v_agent_grades_rela` */;
/*!50001 DROP TABLE IF EXISTS `v_agent_grades_rela` */;

/*!50001 CREATE TABLE  `v_agent_grades_rela`(
 `id` int(11) ,
 `class_grades_name` varchar(255) ,
 `agent_id` int(11) 
)*/;

/*Table structure for table `v_agent_life` */

DROP TABLE IF EXISTS `v_agent_life`;

/*!50001 DROP VIEW IF EXISTS `v_agent_life` */;
/*!50001 DROP TABLE IF EXISTS `v_agent_life` */;

/*!50001 CREATE TABLE  `v_agent_life`(
 `agent_id` int(11) ,
 `life` decimal(41,0) ,
 `create_time` timestamp 
)*/;

/*Table structure for table `v_class_room` */

DROP TABLE IF EXISTS `v_class_room`;

/*!50001 DROP VIEW IF EXISTS `v_class_room` */;
/*!50001 DROP TABLE IF EXISTS `v_class_room` */;

/*!50001 CREATE TABLE  `v_class_room`(
 `class_name` varchar(255) ,
 `status` int(11) ,
 `id` int(11) ,
 `teacher_id` int(11) ,
 `teacher_name` varchar(255) ,
 `create_time` timestamp 
)*/;

/*Table structure for table `v_class_script_normal` */

DROP TABLE IF EXISTS `v_class_script_normal`;

/*!50001 DROP VIEW IF EXISTS `v_class_script_normal` */;
/*!50001 DROP TABLE IF EXISTS `v_class_script_normal` */;

/*!50001 CREATE TABLE  `v_class_script_normal`(
 `id` int(11) ,
 `class_room_id` int(11) ,
 `class_script_type_id` int(11) ,
 `class_script_content` text ,
 `sort` int(11) ,
 `create_time` timestamp ,
 `total_time` int(11) ,
 `live_status` tinyint(4) ,
 `live_status1` int(6) 
)*/;

/*Table structure for table `v_classroom_package` */

DROP TABLE IF EXISTS `v_classroom_package`;

/*!50001 DROP VIEW IF EXISTS `v_classroom_package` */;
/*!50001 DROP TABLE IF EXISTS `v_classroom_package` */;

/*!50001 CREATE TABLE  `v_classroom_package`(
 `id` int(11) ,
 `origin` int(11) ,
 `name` varchar(255) ,
 `price` double ,
 `cover` varchar(255) ,
 `summary` varchar(255) ,
 `teacher_id` int(11) ,
 `school_id` int(11) ,
 `integral_support` int(3) ,
 `create_time` timestamp ,
 `status` int(3) ,
 `total_credit` decimal(32,0) 
)*/;

/*Table structure for table `v_course_study_count` */

DROP TABLE IF EXISTS `v_course_study_count`;

/*!50001 DROP VIEW IF EXISTS `v_course_study_count` */;
/*!50001 DROP TABLE IF EXISTS `v_course_study_count` */;

/*!50001 CREATE TABLE  `v_course_study_count`(
 `classCourseRecord_id` int(11) ,
 `count` bigint(21) 
)*/;

/*Table structure for table `v_device_agent_relation` */

DROP TABLE IF EXISTS `v_device_agent_relation`;

/*!50001 DROP VIEW IF EXISTS `v_device_agent_relation` */;
/*!50001 DROP TABLE IF EXISTS `v_device_agent_relation` */;

/*!50001 CREATE TABLE  `v_device_agent_relation`(
 `device_no` varchar(225) ,
 `epal_id` varchar(225) ,
 `hourse_id` int(11) ,
 `row` int(3) ,
 `col` int(3) ,
 `agent_id` int(11) ,
 `school_id` bigint(11) 
)*/;

/*Table structure for table `v_fandou_role_relation` */

DROP TABLE IF EXISTS `v_fandou_role_relation`;

/*!50001 DROP VIEW IF EXISTS `v_fandou_role_relation` */;
/*!50001 DROP TABLE IF EXISTS `v_fandou_role_relation` */;

/*!50001 CREATE TABLE  `v_fandou_role_relation`(
 `teacher_id` int(11) ,
 `school_id` int(11) ,
 `agent_id` int(11) ,
 `top_agent_id` int(11) 
)*/;

/*Table structure for table `v_grade` */

DROP TABLE IF EXISTS `v_grade`;

/*!50001 DROP VIEW IF EXISTS `v_grade` */;
/*!50001 DROP TABLE IF EXISTS `v_grade` */;

/*!50001 CREATE TABLE  `v_grade`(
 `id` int(11) ,
 `class_grades_name` varchar(255) ,
 `parent_id` int(11) ,
 `summary` varchar(255) ,
 `cover` varchar(255) ,
 `sort` int(11) ,
 `create_time` timestamp ,
 `grades_type` varchar(255) ,
 `teacher_id` int(11) ,
 `status` int(11) ,
 `auditing_status` int(11) ,
 `price` int(11) ,
 `classOpenDate` varchar(255) ,
 `joinStatus` int(11) ,
 `limit_count` int(11) ,
 `agent_id` int(11) 
)*/;

/*Table structure for table `v_has_video` */

DROP TABLE IF EXISTS `v_has_video`;

/*!50001 DROP VIEW IF EXISTS `v_has_video` */;
/*!50001 DROP TABLE IF EXISTS `v_has_video` */;

/*!50001 CREATE TABLE  `v_has_video`(
 `agent_id` int(11) ,
 `g_name` varchar(255) ,
 `g_id` int(11) ,
 `s_name` varchar(255) ,
 `s_id` int(11) ,
 `v.id` int(11) ,
 `v_title` varchar(255) 
)*/;

/*Table structure for table `v_invite_record` */

DROP TABLE IF EXISTS `v_invite_record`;

/*!50001 DROP VIEW IF EXISTS `v_invite_record` */;
/*!50001 DROP TABLE IF EXISTS `v_invite_record` */;

/*!50001 CREATE TABLE  `v_invite_record`(
 `id` int(11) ,
 `account_id` int(11) ,
 `code` varchar(6) ,
 `create_time` timestamp ,
 `update_time` timestamp ,
 `invite_code` varchar(6) 
)*/;

/*Table structure for table `v_live_lesson_grade` */

DROP TABLE IF EXISTS `v_live_lesson_grade`;

/*!50001 DROP VIEW IF EXISTS `v_live_lesson_grade` */;
/*!50001 DROP TABLE IF EXISTS `v_live_lesson_grade` */;

/*!50001 CREATE TABLE  `v_live_lesson_grade`(
 `id` int(11) ,
 `class_grades_name` varchar(255) ,
 `agent_id` int(11) 
)*/;

/*Table structure for table `v_member_to_teacherinfo` */

DROP TABLE IF EXISTS `v_member_to_teacherinfo`;

/*!50001 DROP VIEW IF EXISTS `v_member_to_teacherinfo` */;
/*!50001 DROP TABLE IF EXISTS `v_member_to_teacherinfo` */;

/*!50001 CREATE TABLE  `v_member_to_teacherinfo`(
 `member_id` varchar(255) ,
 `info` varchar(358) 
)*/;

/*Table structure for table `v_oss_agent_school` */

DROP TABLE IF EXISTS `v_oss_agent_school`;

/*!50001 DROP VIEW IF EXISTS `v_oss_agent_school` */;
/*!50001 DROP TABLE IF EXISTS `v_oss_agent_school` */;

/*!50001 CREATE TABLE  `v_oss_agent_school`(
 `id` int(11) ,
 `name` varchar(64) ,
 `oss_user_id` int(11) ,
 `agent_id` int(11) ,
 `credit` decimal(41,0) ,
 `create_time` timestamp ,
 `update_time` timestamp 
)*/;

/*Table structure for table `v_qy_class` */

DROP TABLE IF EXISTS `v_qy_class`;

/*!50001 DROP VIEW IF EXISTS `v_qy_class` */;
/*!50001 DROP TABLE IF EXISTS `v_qy_class` */;

/*!50001 CREATE TABLE  `v_qy_class`(
 `id` int(11) ,
 `class_name` varchar(255) ,
 `member_id` int(11) ,
 `agent_id` int(11) ,
 `school_id` int(11) ,
 `school_name` varchar(255) ,
 `teacher_id` int(11) ,
 `teacher_name` varchar(255) ,
 `teacher_rfid` varchar(255) 
)*/;

/*Table structure for table `v_qy_device` */

DROP TABLE IF EXISTS `v_qy_device`;

/*!50001 DROP VIEW IF EXISTS `v_qy_device` */;
/*!50001 DROP TABLE IF EXISTS `v_qy_device` */;

/*!50001 CREATE TABLE  `v_qy_device`(
 `id` int(11) ,
 `device_id` varchar(255) ,
 `device_name` varchar(255) ,
 `remark` varchar(255) ,
 `member_id` int(11) ,
 `belong` int(11) ,
 `name` varchar(255) ,
 `agent_id` int(11) ,
 `class` int(11) 
)*/;

/*Table structure for table `v_qy_punch_card_group` */

DROP TABLE IF EXISTS `v_qy_punch_card_group`;

/*!50001 DROP VIEW IF EXISTS `v_qy_punch_card_group` */;
/*!50001 DROP TABLE IF EXISTS `v_qy_punch_card_group` */;

/*!50001 CREATE TABLE  `v_qy_punch_card_group`(
 `group_id` int(11) ,
 `group_name` varchar(20) ,
 `school_id` int(11) ,
 `school_name` varchar(255) ,
 `agent_id` int(11) 
)*/;

/*Table structure for table `v_qy_punch_card_record` */

DROP TABLE IF EXISTS `v_qy_punch_card_record`;

/*!50001 DROP VIEW IF EXISTS `v_qy_punch_card_record` */;
/*!50001 DROP TABLE IF EXISTS `v_qy_punch_card_record` */;

/*!50001 CREATE TABLE  `v_qy_punch_card_record`(
 `id` int(11) ,
 `card_id` varchar(255) ,
 `device_id` varchar(255) ,
 `punch_time` datetime ,
 `punch_image` varchar(255) ,
 `send_status` int(11) ,
 `action` varchar(255) ,
 `class_id` int(11) 
)*/;

/*Table structure for table `v_qy_punch_card_type` */

DROP TABLE IF EXISTS `v_qy_punch_card_type`;

/*!50001 DROP VIEW IF EXISTS `v_qy_punch_card_type` */;
/*!50001 DROP TABLE IF EXISTS `v_qy_punch_card_type` */;

/*!50001 CREATE TABLE  `v_qy_punch_card_type`(
 `id` int(11) ,
 `name` varchar(10) ,
 `msg` varchar(100) ,
 `group_id` int(11) ,
 `group_name` varchar(20) ,
 `school_id` int(11) ,
 `school_name` varchar(255) ,
 `agent_id` int(11) 
)*/;

/*Table structure for table `v_qy_punch_record` */

DROP TABLE IF EXISTS `v_qy_punch_record`;

/*!50001 DROP VIEW IF EXISTS `v_qy_punch_record` */;
/*!50001 DROP TABLE IF EXISTS `v_qy_punch_record` */;

/*!50001 CREATE TABLE  `v_qy_punch_record`(
 `id` int(11) ,
 `card_id` varchar(255) ,
 `device_id` varchar(255) ,
 `punch_time` datetime ,
 `punch_image` varchar(255) ,
 `send_status` int(11) ,
 `action` varchar(255) ,
 `type_id` int(11) ,
 `type_name` varchar(10) ,
 `group_id` int(11) ,
 `group_name` varchar(20) ,
 `device_name` varchar(255) ,
 `agent_id` int(11) ,
 `name` varchar(255) 
)*/;

/*Table structure for table `v_qy_rfid` */

DROP TABLE IF EXISTS `v_qy_rfid`;

/*!50001 DROP VIEW IF EXISTS `v_qy_rfid` */;
/*!50001 DROP TABLE IF EXISTS `v_qy_rfid` */;

/*!50001 CREATE TABLE  `v_qy_rfid`(
 `student_id` int(11) ,
 `status` int(11) ,
 `name` varchar(255) ,
 `class_name` varchar(255) ,
 `id` varchar(255) ,
 `agent_id` int(11) ,
 `member_id` int(11) 
)*/;

/*Table structure for table `v_qy_statistics_class` */

DROP TABLE IF EXISTS `v_qy_statistics_class`;

/*!50001 DROP VIEW IF EXISTS `v_qy_statistics_class` */;
/*!50001 DROP TABLE IF EXISTS `v_qy_statistics_class` */;

/*!50001 CREATE TABLE  `v_qy_statistics_class`(
 `class_name` varchar(255) ,
 `class_id` int(11) ,
 `member_id` int(11) ,
 `student_count` bigint(21) 
)*/;

/*Table structure for table `v_qy_statistics_school` */

DROP TABLE IF EXISTS `v_qy_statistics_school`;

/*!50001 DROP VIEW IF EXISTS `v_qy_statistics_school` */;
/*!50001 DROP TABLE IF EXISTS `v_qy_statistics_school` */;

/*!50001 CREATE TABLE  `v_qy_statistics_school`(
 `school_id` int(11) ,
 `school_name` varchar(255) ,
 `agent_id` int(11) ,
 `student_count` decimal(42,0) 
)*/;

/*Table structure for table `v_qy_student` */

DROP TABLE IF EXISTS `v_qy_student`;

/*!50001 DROP VIEW IF EXISTS `v_qy_student` */;
/*!50001 DROP TABLE IF EXISTS `v_qy_student` */;

/*!50001 CREATE TABLE  `v_qy_student`(
 `card_id` varchar(255) ,
 `contacts` varchar(255) ,
 `status` int(11) ,
 `card_id_id` int(11) ,
 `name` varchar(255) ,
 `id` int(11) ,
 `avatar` varchar(255) ,
 `class_id` int(11) ,
 `agent_id` int(11) ,
 `class_name` varchar(255) 
)*/;

/*Table structure for table `v_qy_teacher` */

DROP TABLE IF EXISTS `v_qy_teacher`;

/*!50001 DROP VIEW IF EXISTS `v_qy_teacher` */;
/*!50001 DROP TABLE IF EXISTS `v_qy_teacher` */;

/*!50001 CREATE TABLE  `v_qy_teacher`(
 `id` int(11) ,
 `rfid` varchar(255) ,
 `member_id` varchar(255) ,
 `agent_id` int(11) ,
 `account` varchar(100) ,
 `name` varchar(255) 
)*/;

/*Table structure for table `v_rfid` */

DROP TABLE IF EXISTS `v_rfid`;

/*!50001 DROP VIEW IF EXISTS `v_rfid` */;
/*!50001 DROP TABLE IF EXISTS `v_rfid` */;

/*!50001 CREATE TABLE  `v_rfid`(
 `id` int(11) ,
 `card_fid` varchar(255) ,
 `student_id` int(11) ,
 `insert_date` varchar(255) ,
 `update_date` datetime ,
 `update_by` varchar(255) ,
 `remark` varchar(255) ,
 `agent_id` int(11) ,
 `school_id` int(11) ,
 `name` varchar(255) 
)*/;

/*Table structure for table `v_rfid_all` */

DROP TABLE IF EXISTS `v_rfid_all`;

/*!50001 DROP VIEW IF EXISTS `v_rfid_all` */;
/*!50001 DROP TABLE IF EXISTS `v_rfid_all` */;

/*!50001 CREATE TABLE  `v_rfid_all`(
 `card_id` varchar(255) ,
 `student_id` varchar(11) 
)*/;

/*Table structure for table `v_room_tree_id_with_r_name` */

DROP TABLE IF EXISTS `v_room_tree_id_with_r_name`;

/*!50001 DROP VIEW IF EXISTS `v_room_tree_id_with_r_name` */;
/*!50001 DROP TABLE IF EXISTS `v_room_tree_id_with_r_name` */;

/*!50001 CREATE TABLE  `v_room_tree_id_with_r_name`(
 `id` int(11) ,
 `name` text 
)*/;

/*Table structure for table `v_room_with_t_name` */

DROP TABLE IF EXISTS `v_room_with_t_name`;

/*!50001 DROP VIEW IF EXISTS `v_room_with_t_name` */;
/*!50001 DROP TABLE IF EXISTS `v_room_with_t_name` */;

/*!50001 CREATE TABLE  `v_room_with_t_name`(
 `ID` int(11) ,
 `CN` text 
)*/;

/*Table structure for table `v_school_grade_rel` */

DROP TABLE IF EXISTS `v_school_grade_rel`;

/*!50001 DROP VIEW IF EXISTS `v_school_grade_rel` */;
/*!50001 DROP TABLE IF EXISTS `v_school_grade_rel` */;

/*!50001 CREATE TABLE  `v_school_grade_rel`(
 `school_id` int(11) ,
 `class_grade_id` int(11) 
)*/;

/*Table structure for table `v_school_student` */

DROP TABLE IF EXISTS `v_school_student`;

/*!50001 DROP VIEW IF EXISTS `v_school_student` */;
/*!50001 DROP TABLE IF EXISTS `v_school_student` */;

/*!50001 CREATE TABLE  `v_school_student`(
 `id` int(11) ,
 `name` varchar(255) ,
 `epal_id` varchar(255) ,
 `create_time` timestamp ,
 `student_type` int(255) ,
 `sort_id` int(11) ,
 `integral` int(11) ,
 `contribution` int(11) ,
 `remark` varchar(255) ,
 `member_id` int(11) ,
 `degree_of_difficulty` float ,
 `avatar` varchar(255) ,
 `status` int(11) ,
 `lesson_integral` double(11,2) ,
 `agent_id` int(11) ,
 `sex` tinyint(4) 
)*/;

/*Table structure for table `v_school_student_account` */

DROP TABLE IF EXISTS `v_school_student_account`;

/*!50001 DROP VIEW IF EXISTS `v_school_student_account` */;
/*!50001 DROP TABLE IF EXISTS `v_school_student_account` */;

/*!50001 CREATE TABLE  `v_school_student_account`(
 `id` int(11) ,
 `name` varchar(255) ,
 `epal_id` varchar(255) ,
 `create_time` timestamp ,
 `student_type` int(255) ,
 `sort_id` int(11) ,
 `integral` int(11) ,
 `contribution` int(11) ,
 `remark` varchar(255) ,
 `member_id` int(11) ,
 `degree_of_difficulty` float ,
 `avatar` varchar(255) ,
 `status` int(11) ,
 `lesson_integral` double(11,2) ,
 `agent_id` int(11) ,
 `sex` tinyint(4) ,
 `account` varchar(100) 
)*/;

/*Table structure for table `v_std_for_agent` */

DROP TABLE IF EXISTS `v_std_for_agent`;

/*!50001 DROP VIEW IF EXISTS `v_std_for_agent` */;
/*!50001 DROP TABLE IF EXISTS `v_std_for_agent` */;

/*!50001 CREATE TABLE  `v_std_for_agent`(
 `a_id` int(11) ,
 `s_id` int(11) 
)*/;

/*Table structure for table `v_std_has_video` */

DROP TABLE IF EXISTS `v_std_has_video`;

/*!50001 DROP VIEW IF EXISTS `v_std_has_video` */;
/*!50001 DROP TABLE IF EXISTS `v_std_has_video` */;

/*!50001 CREATE TABLE  `v_std_has_video`(
 `s_id` int(11) ,
 `name` varchar(255) ,
 `v_id` int(11) ,
 `pic_url` varchar(255) ,
 `v_title` varchar(255) ,
 `v_url` varchar(255) 
)*/;

/*Table structure for table `v_student` */

DROP TABLE IF EXISTS `v_student`;

/*!50001 DROP VIEW IF EXISTS `v_student` */;
/*!50001 DROP TABLE IF EXISTS `v_student` */;

/*!50001 CREATE TABLE  `v_student`(
 `g_id` int(11) ,
 `g_name` varchar(255) ,
 `s_id` int(11) ,
 `s_name` varchar(255) ,
 `epal_id` varchar(255) ,
 `agent_id` int(11) 
)*/;

/*Table structure for table `v_student_account` */

DROP TABLE IF EXISTS `v_student_account`;

/*!50001 DROP VIEW IF EXISTS `v_student_account` */;
/*!50001 DROP TABLE IF EXISTS `v_student_account` */;

/*!50001 CREATE TABLE  `v_student_account`(
 `id` int(11) ,
 `account_id` int(11) 
)*/;

/*Table structure for table `v_student_grade_rel` */

DROP TABLE IF EXISTS `v_student_grade_rel`;

/*!50001 DROP VIEW IF EXISTS `v_student_grade_rel` */;
/*!50001 DROP TABLE IF EXISTS `v_student_grade_rel` */;

/*!50001 CREATE TABLE  `v_student_grade_rel`(
 `class_grades_id` int(11) ,
 `class_grades_name` varchar(255) ,
 `summary` varchar(255) ,
 `grades_type` varchar(255) ,
 `join_date` timestamp ,
 `gradesStatus` int(11) ,
 `class_student_id` int(11) ,
 `class_student_name` varchar(255) 
)*/;

/*Table structure for table `v_teacher_account` */

DROP TABLE IF EXISTS `v_teacher_account`;

/*!50001 DROP VIEW IF EXISTS `v_teacher_account` */;
/*!50001 DROP TABLE IF EXISTS `v_teacher_account` */;

/*!50001 CREATE TABLE  `v_teacher_account`(
 `id` int(11) ,
 `name` varchar(255) ,
 `account` varchar(100) ,
 `agent_id` int(11) ,
 `avatar` varchar(255) ,
 `accountId` int(11) 
)*/;

/*Table structure for table `v_teacher_product` */

DROP TABLE IF EXISTS `v_teacher_product`;

/*!50001 DROP VIEW IF EXISTS `v_teacher_product` */;
/*!50001 DROP TABLE IF EXISTS `v_teacher_product` */;

/*!50001 CREATE TABLE  `v_teacher_product`(
 `teacher_id` int(11) ,
 `id` int(11) ,
 `name` varchar(200) ,
 `createdate` datetime ,
 `content` text ,
 `logo1` varchar(200) ,
 `price` varchar(100) ,
 `class_room_package` int(11) ,
 `class_grade_id` int(11) ,
 `status` int(11) 
)*/;

/*Table structure for table `v_user_grade` */

DROP TABLE IF EXISTS `v_user_grade`;

/*!50001 DROP VIEW IF EXISTS `v_user_grade` */;
/*!50001 DROP TABLE IF EXISTS `v_user_grade` */;

/*!50001 CREATE TABLE  `v_user_grade`(
 `user_id` int(11) ,
 `grade_id` int(11) 
)*/;

/*Table structure for table `v_user_student` */

DROP TABLE IF EXISTS `v_user_student`;

/*!50001 DROP VIEW IF EXISTS `v_user_student` */;
/*!50001 DROP TABLE IF EXISTS `v_user_student` */;

/*!50001 CREATE TABLE  `v_user_student`(
 `user_id` int(11) ,
 `student_id` int(11) 
)*/;

/*Table structure for table `v_user_student_more` */

DROP TABLE IF EXISTS `v_user_student_more`;

/*!50001 DROP VIEW IF EXISTS `v_user_student_more` */;
/*!50001 DROP TABLE IF EXISTS `v_user_student_more` */;

/*!50001 CREATE TABLE  `v_user_student_more`(
 `user_id` int(11) ,
 `student_id` int(11) ,
 `epal_id` varchar(255) ,
 `name` varchar(255) ,
 `remark` varchar(255) ,
 `nameRemark` varchar(512) 
)*/;

/*Table structure for table `v_video_info_score` */

DROP TABLE IF EXISTS `v_video_info_score`;

/*!50001 DROP VIEW IF EXISTS `v_video_info_score` */;
/*!50001 DROP TABLE IF EXISTS `v_video_info_score` */;

/*!50001 CREATE TABLE  `v_video_info_score`(
 `id` int(11) ,
 `video_info_id` int(11) ,
 `video_activity_id` int(11) ,
 `verify_status` int(11) ,
 `edit_time` timestamp ,
 `creat_time` timestamp ,
 `vid` int(11) ,
 `v_title` varchar(255) ,
 `v_url` varchar(255) ,
 `share_url` varchar(255) ,
 `acount_id` varchar(30) ,
 `create_time` datetime ,
 `admission` int(5) ,
 `vote` int(11) ,
 `access_num` int(11) ,
 `sid` int(11) ,
 `expert1` varchar(255) ,
 `expert2` varchar(255) ,
 `expert3` varchar(255) ,
 `expert4` varchar(255) 
)*/;

/*View structure for view account_student_relation */

/*!50001 DROP TABLE IF EXISTS `account_student_relation` */;
/*!50001 DROP VIEW IF EXISTS `account_student_relation` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `account_student_relation` AS select 1 AS `id`,1 AS `account`,1 AS `name` */;

/*View structure for view agent_class_room */

/*!50001 DROP TABLE IF EXISTS `agent_class_room` */;
/*!50001 DROP VIEW IF EXISTS `agent_class_room` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `agent_class_room` AS select 1 AS `id`,1 AS `teacher_id`,1 AS `teacher_name`,1 AS `class_name`,1 AS `cover`,1 AS `summary`,1 AS `status`,1 AS `sort`,1 AS `category_id`,1 AS `book_res_id`,1 AS `group_id`,1 AS `create_time`,1 AS `class_room_type`,1 AS `video_url`,1 AS `type`,1 AS `book_res_ids`,1 AS `difficulty_level`,1 AS `original`,1 AS `member_account_id`,1 AS `agent_id` */;

/*View structure for view class_course_record_eova_template */

/*!50001 DROP TABLE IF EXISTS `class_course_record_eova_template` */;
/*!50001 DROP VIEW IF EXISTS `class_course_record_eova_template` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `class_course_record_eova_template` AS select 1 AS `class_grades_id`,1 AS `class_course_id`,1 AS `do_title`,1 AS `do_slot`,1 AS `student_id`,1 AS `complete`,1 AS `score`,1 AS `used_time`,1 AS `complete_time` */;

/*View structure for view fandou_user_role_rel */

/*!50001 DROP TABLE IF EXISTS `fandou_user_role_rel` */;
/*!50001 DROP VIEW IF EXISTS `fandou_user_role_rel` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `fandou_user_role_rel` AS select `oas`.`id` AS `school_id`,`fg`.`id` AS `agent_id`,`ft`.`id` AS `top_id`,`oas`.`name` AS `school_name` from ((`oss_agent_school` `oas` join `fandou_agent` `fg`) join `top_agent` `ft`) where ((`oas`.`agent_id` = `fg`.`id`) and (`fg`.`top_agent_id` = `ft`.`id`)) */;

/*View structure for view school_grades */

/*!50001 DROP TABLE IF EXISTS `school_grades` */;
/*!50001 DROP VIEW IF EXISTS `school_grades` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `school_grades` AS select `a`.`id` AS `school_id`,`b`.`id` AS `teacher_id`,`c`.`id` AS `class_grades_id`,`c`.`class_grades_name` AS `class_grades_name` from ((`oss_agent_school` `a` join `class_teacher` `b`) join `class_grades` `c`) where ((`a`.`id` = `b`.`agent_id`) and (`b`.`id` = `c`.`teacher_id`)) */;

/*View structure for view school_student */

/*!50001 DROP TABLE IF EXISTS `school_student` */;
/*!50001 DROP VIEW IF EXISTS `school_student` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `school_student` AS (select `c`.`id` AS `id`,`c`.`name` AS `name`,`c`.`epal_id` AS `epal_id`,`c`.`create_time` AS `create_time`,`c`.`student_type` AS `student_type`,`c`.`sort_id` AS `sort_id`,`c`.`integral` AS `integral`,`c`.`contribution` AS `contribution`,`c`.`remark` AS `remark`,`c`.`member_id` AS `member_id`,`c`.`degree_of_difficulty` AS `degree_of_difficulty`,`c`.`avatar` AS `avatar`,`c`.`status` AS `status`,`c`.`lesson_integral` AS `lesson_integral`,`ct`.`agent_id` AS `agent_id`,`c`.`sex` AS `sex` from (((`class_teacher` `ct` join `class_grades` `a`) join `class_grades_rela` `b`) join `class_student` `c`) where ((`ct`.`id` = `a`.`teacher_id`) and (`a`.`id` = `b`.`class_grades_id`) and (`b`.`class_student_id` = `c`.`id`) and (`a`.`teacher_id` > 0)) group by `ct`.`id`,`c`.`id`) union all (select `class_student`.`id` AS `id`,`class_student`.`name` AS `name`,`class_student`.`epal_id` AS `epal_id`,`class_student`.`create_time` AS `create_time`,`class_student`.`student_type` AS `student_type`,`class_student`.`sort_id` AS `sort_id`,`class_student`.`integral` AS `integral`,`class_student`.`contribution` AS `contribution`,`class_student`.`remark` AS `remark`,`class_student`.`member_id` AS `member_id`,`class_student`.`degree_of_difficulty` AS `degree_of_difficulty`,`class_student`.`avatar` AS `avatar`,`class_student`.`status` AS `status`,`class_student`.`lesson_integral` AS `lesson_integral`,`class_student`.`agent_id` AS `agent_id`,`class_student`.`sex` AS `sex` from `class_student` where (1 = 1)) */;

/*View structure for view student_account */

/*!50001 DROP TABLE IF EXISTS `student_account` */;
/*!50001 DROP VIEW IF EXISTS `student_account` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `student_account` AS select `c`.`id` AS `id`,`a`.`account` AS `account` from ((`memberaccount` `a` join `device_relation` `b`) join `class_student` `c`) where ((`a`.`account` = `b`.`friend_id`) and (`b`.`epal_id` = `c`.`epal_id`)) */;

/*View structure for view student_grades */

/*!50001 DROP TABLE IF EXISTS `student_grades` */;
/*!50001 DROP VIEW IF EXISTS `student_grades` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `student_grades` AS (select `a`.`id` AS `id`,`a`.`name` AS `name`,`a`.`epal_id` AS `epal_id`,`a`.`create_time` AS `create_time`,`a`.`student_type` AS `student_type`,`a`.`sort_id` AS `sort_id`,`b`.`class_grades_name` AS `class_grades_name`,`e`.`id` AS `agent_id` from ((((`class_student` `a` join `class_grades` `b`) join `class_grades_rela` `c`) join `v_agent` `d`) join `user` `e`) where ((`a`.`id` = `c`.`class_student_id`) and (`b`.`id` = `c`.`class_grades_id`) and (`a`.`epal_id` = `d`.`epal_id`) and (`e`.`id` = `d`.`user_id`))) */;

/*View structure for view student_grades_new */

/*!50001 DROP TABLE IF EXISTS `student_grades_new` */;
/*!50001 DROP VIEW IF EXISTS `student_grades_new` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `student_grades_new` AS (select `a`.`id` AS `id`,`a`.`name` AS `name`,`a`.`epal_id` AS `epal_id`,`a`.`create_time` AS `create_time`,`a`.`student_type` AS `student_type`,`a`.`sort_id` AS `sort_id`,`b`.`id` AS `class_grades_id`,`b`.`class_grades_name` AS `class_grades_name`,`e`.`id` AS `agent_id` from ((((`class_student` `a` join `class_grades` `b`) join `class_grades_rela` `c`) join `v_agent` `d`) join `user` `e`) where ((`a`.`id` = `c`.`class_student_id`) and (`b`.`id` = `c`.`class_grades_id`) and (`a`.`epal_id` = `d`.`epal_id`) and (`e`.`id` = `d`.`user_id`))) */;

/*View structure for view studentwithclassid */

/*!50001 DROP TABLE IF EXISTS `studentwithclassid` */;
/*!50001 DROP VIEW IF EXISTS `studentwithclassid` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `studentwithclassid` AS select `gr`.`class_grades_id` AS `class_grades_id`,`class_student`.`id` AS `id`,`class_student`.`name` AS `name`,`class_student`.`epal_id` AS `epal_id`,`class_student`.`create_time` AS `create_time`,`class_student`.`student_type` AS `student_type`,`class_student`.`sort_id` AS `sort_id`,`class_student`.`remark` AS `remark`,`fid`.`card_fid` AS `fid` from ((`class_student` join `class_grades_rela` `gr`) left join `public_room_fid_to_student` `fid` on((`class_student`.`id` = `fid`.`student_id`))) where (`class_student`.`id` = `gr`.`class_student_id`) */;

/*View structure for view success_order */

/*!50001 DROP TABLE IF EXISTS `success_order` */;
/*!50001 DROP VIEW IF EXISTS `success_order` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `success_order` AS select `c`.`nickname` AS `nickname`,`c`.`headimgurl` AS `headimgurl`,`c`.`openid` AS `openid`,`a`.`ordernumber` AS `ordernumber`,`a`.`memberId` AS `memberId`,`a`.`status` AS `status`,`a`.`createdate` AS `createdate`,`a`.`totalprice` AS `totalprice`,`b`.`id` AS `productid`,`b`.`productName` AS `productName`,`b`.`price` AS `price`,`b`.`count` AS `count`,`c`.`mobile` AS `mobile` from ((`mallorder` `a` join `mallorderproduct` `b`) join `member` `c`) where ((`a`.`id` = `b`.`orderid`) and (`a`.`status` = 1) and (`b`.`productid` = 3275) and (`a`.`memberId` = `c`.`id`)) */;

/*View structure for view teacher_grade_product */

/*!50001 DROP TABLE IF EXISTS `teacher_grade_product` */;
/*!50001 DROP VIEW IF EXISTS `teacher_grade_product` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `teacher_grade_product` AS select `a`.`id` AS `teacher_id`,`c`.`id` AS `id`,`c`.`name` AS `name`,`c`.`createdate` AS `createdate`,`c`.`content` AS `content`,`c`.`accountid` AS `accountid`,`c`.`logo1` AS `logo1`,`c`.`logo2` AS `logo2`,`c`.`logo3` AS `logo3`,`c`.`status` AS `status`,`c`.`price` AS `price`,`c`.`mp3` AS `mp3`,`c`.`mp3type` AS `mp3type`,`c`.`cat_id` AS `cat_id`,`c`.`class_room_package` AS `class_room_package`,`c`.`class_grade_id` AS `class_grade_id`,`d`.`labelid` AS `labelid` from (((`class_teacher` `a` join `class_grades` `b`) join `mallproduct` `c`) join `productlabel` `d`) where ((`a`.`id` = `b`.`teacher_id`) and (`b`.`id` = `c`.`class_grade_id`) and (`c`.`id` = `d`.`productid`)) */;

/*View structure for view teacher_grade_student */

/*!50001 DROP TABLE IF EXISTS `teacher_grade_student` */;
/*!50001 DROP VIEW IF EXISTS `teacher_grade_student` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `teacher_grade_student` AS select `c`.`id` AS `teacher_id`,`c`.`agent_id` AS `teacher_agent_id`,`a`.`id` AS `class_grades_id`,`a`.`class_grades_name` AS `class_grades_name`,`d`.`id` AS `student_id`,`d`.`name` AS `name`,`d`.`epal_id` AS `epal_id`,`d`.`create_time` AS `create_time`,`d`.`sex` AS `sex` from (((`class_grades` `a` join `class_grades_rela` `b`) join `class_teacher` `c`) join `class_student` `d`) where ((`c`.`id` = `a`.`teacher_id`) and (`a`.`id` = `b`.`class_grades_id`) and (`b`.`class_student_id` = `d`.`id`)) */;

/*View structure for view v_agent */

/*!50001 DROP TABLE IF EXISTS `v_agent` */;
/*!50001 DROP VIEW IF EXISTS `v_agent` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_agent` AS (select `a`.`epal_id` AS `epal_id`,`d`.`user_id` AS `user_id` from ((`class_student` `a` join `class_grades_rela` `b`) join `user_to_class_grades` `d`) where ((`a`.`id` = `b`.`class_student_id`) and (`b`.`gradesStatus` = 1) and (`b`.`class_grades_id` = `d`.`class_grades_id`)) group by `a`.`epal_id`) */;

/*View structure for view v_agent_grades_rela */

/*!50001 DROP TABLE IF EXISTS `v_agent_grades_rela` */;
/*!50001 DROP VIEW IF EXISTS `v_agent_grades_rela` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_agent_grades_rela` AS select `g`.`id` AS `id`,`g`.`class_grades_name` AS `class_grades_name`,`t`.`agent_id` AS `agent_id` from (`class_grades` `g` join `class_teacher` `t` on((`g`.`teacher_id` = `t`.`id`))) where (`g`.`grades_type` in ('virtualClass','eleClass')) union all select `c`.`id` AS `id`,`c`.`class_grades_name` AS `class_grades_name`,`uc`.`user_id` AS `agent_id` from (`class_grades` `c` join `user_to_class_grades` `uc`) where (`c`.`id` = `uc`.`class_grades_id`) */;

/*View structure for view v_agent_life */

/*!50001 DROP TABLE IF EXISTS `v_agent_life` */;
/*!50001 DROP VIEW IF EXISTS `v_agent_life` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_agent_life` AS select `agent_life_record`.`agent_id` AS `agent_id`,sum(`agent_life_record`.`life`) AS `life`,`agent_life_record`.`create_time` AS `create_time` from `agent_life_record` group by `agent_life_record`.`agent_id` */;

/*View structure for view v_class_room */

/*!50001 DROP TABLE IF EXISTS `v_class_room` */;
/*!50001 DROP VIEW IF EXISTS `v_class_room` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_class_room` AS select `cr`.`class_name` AS `class_name`,`cr`.`status` AS `status`,`cr`.`id` AS `id`,`cr`.`teacher_id` AS `teacher_id`,`ct`.`name` AS `teacher_name`,`cr`.`create_time` AS `create_time` from (`class_room` `cr` join `class_teacher` `ct`) where (`cr`.`teacher_id` = `ct`.`id`) */;

/*View structure for view v_class_script_normal */

/*!50001 DROP TABLE IF EXISTS `v_class_script_normal` */;
/*!50001 DROP VIEW IF EXISTS `v_class_script_normal` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_class_script_normal` AS select `a`.`id` AS `id`,`a`.`class_room_id` AS `class_room_id`,`a`.`class_script_type_id` AS `class_script_type_id`,`a`.`class_script_content` AS `class_script_content`,`a`.`sort` AS `sort`,`a`.`create_time` AS `create_time`,`a`.`total_time` AS `total_time`,`a`.`live_status` AS `live_status`,ifnull(`b`.`live_status`,0) AS `live_status1` from (`class_script_normal` `a` left join `live_script_status` `b` on((`a`.`id` = `b`.`class_script_id`))) */;

/*View structure for view v_classroom_package */

/*!50001 DROP TABLE IF EXISTS `v_classroom_package` */;
/*!50001 DROP VIEW IF EXISTS `v_classroom_package` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_classroom_package` AS select `a`.`id` AS `id`,`a`.`origin` AS `origin`,`a`.`name` AS `name`,`a`.`price` AS `price`,`a`.`cover` AS `cover`,`a`.`summary` AS `summary`,`a`.`teacher_id` AS `teacher_id`,`a`.`school_id` AS `school_id`,`a`.`integral_support` AS `integral_support`,`a`.`create_time` AS `create_time`,`a`.`status` AS `status`,ifnull(sum(`c`.`credit`),0) AS `total_credit` from (`classroom_package` `a` left join (`classroom_package_rel` `b` join `class_room` `c` on((`b`.`classroom_id` = `c`.`id`))) on((`a`.`id` = `b`.`package_id`))) where (1 = 1) group by `a`.`id` */;

/*View structure for view v_course_study_count */

/*!50001 DROP TABLE IF EXISTS `v_course_study_count` */;
/*!50001 DROP VIEW IF EXISTS `v_course_study_count` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_course_study_count` AS select `class_course_score_record`.`classCourseRecord_id` AS `classCourseRecord_id`,count(0) AS `count` from `class_course_score_record` group by `class_course_score_record`.`classCourseRecord_id` */;

/*View structure for view v_device_agent_relation */

/*!50001 DROP TABLE IF EXISTS `v_device_agent_relation` */;
/*!50001 DROP VIEW IF EXISTS `v_device_agent_relation` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_device_agent_relation` AS select `a`.`device_no` AS `device_no`,`a`.`epal_id` AS `epal_id`,`b`.`hourse_id` AS `hourse_id`,`b`.`row` AS `row`,`b`.`col` AS `col`,`a`.`agent_id` AS `agent_id`,(select `class_hourse`.`user_id` from `class_hourse` where (`class_hourse`.`Id` = `b`.`hourse_id`)) AS `school_id` from (`device_agent_relation` `a` left join `robot_hourse` `b` on((convert(`a`.`epal_id` using utf8mb4) = `b`.`epal_id`))) */;

/*View structure for view v_fandou_role_relation */

/*!50001 DROP TABLE IF EXISTS `v_fandou_role_relation` */;
/*!50001 DROP VIEW IF EXISTS `v_fandou_role_relation` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_fandou_role_relation` AS select `a`.`id` AS `teacher_id`,`b`.`id` AS `school_id`,`c`.`id` AS `agent_id`,`d`.`id` AS `top_agent_id` from (((`class_teacher` `a` left join `oss_agent_school` `b` on((`a`.`agent_id` = `b`.`id`))) left join `fandou_agent` `c` on((`b`.`agent_id` = `c`.`id`))) left join `top_agent` `d` on((`c`.`top_agent_id` = `d`.`id`))) */;

/*View structure for view v_grade */

/*!50001 DROP TABLE IF EXISTS `v_grade` */;
/*!50001 DROP VIEW IF EXISTS `v_grade` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_grade` AS select `c`.`id` AS `id`,`c`.`class_grades_name` AS `class_grades_name`,`c`.`parent_id` AS `parent_id`,`c`.`summary` AS `summary`,`c`.`cover` AS `cover`,`c`.`sort` AS `sort`,`c`.`create_time` AS `create_time`,`c`.`grades_type` AS `grades_type`,`c`.`teacher_id` AS `teacher_id`,`c`.`status` AS `status`,`c`.`auditing_status` AS `auditing_status`,`c`.`price` AS `price`,`c`.`classOpenDate` AS `classOpenDate`,`c`.`joinStatus` AS `joinStatus`,`c`.`limit_count` AS `limit_count`,`t`.`agent_id` AS `agent_id` from (`class_grades` `c` join `class_teacher` `t`) where (`t`.`id` = `c`.`teacher_id`) union select `c`.`id` AS `id`,`c`.`class_grades_name` AS `class_grades_name`,`c`.`parent_id` AS `parent_id`,`c`.`summary` AS `summary`,`c`.`cover` AS `cover`,`c`.`sort` AS `sort`,`c`.`create_time` AS `create_time`,`c`.`grades_type` AS `grades_type`,`c`.`teacher_id` AS `teacher_id`,`c`.`status` AS `status`,`c`.`auditing_status` AS `auditing_status`,`c`.`price` AS `price`,`c`.`classOpenDate` AS `classOpenDate`,`c`.`joinStatus` AS `joinStatus`,`c`.`limit_count` AS `limit_count`,`uc`.`user_id` AS `agent_id` from (`class_grades` `c` join `user_to_class_grades` `uc`) where (`c`.`id` = `uc`.`class_grades_id`) */;

/*View structure for view v_has_video */

/*!50001 DROP TABLE IF EXISTS `v_has_video` */;
/*!50001 DROP VIEW IF EXISTS `v_has_video` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_has_video` AS select `s`.`agent_id` AS `agent_id`,`s`.`g_name` AS `g_name`,`s`.`g_id` AS `g_id`,`s`.`s_name` AS `s_name`,`s`.`s_id` AS `s_id`,`v`.`id` AS `v.id`,`v`.`v_title` AS `v_title` from (`v_student` `s` left join `video_info` `v` on((`s`.`epal_id` = `v`.`epal_id`))) */;

/*View structure for view v_invite_record */

/*!50001 DROP TABLE IF EXISTS `v_invite_record` */;
/*!50001 DROP VIEW IF EXISTS `v_invite_record` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_invite_record` AS select `a`.`id` AS `id`,`a`.`account_id` AS `account_id`,`a`.`code` AS `code`,`a`.`create_time` AS `create_time`,`a`.`update_time` AS `update_time`,`b`.`code` AS `invite_code` from (`account_invitation_record` `a` left join `account_invitation_code` `b` on((`a`.`account_id` = `b`.`account_id`))) */;

/*View structure for view v_live_lesson_grade */

/*!50001 DROP TABLE IF EXISTS `v_live_lesson_grade` */;
/*!50001 DROP VIEW IF EXISTS `v_live_lesson_grade` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_live_lesson_grade` AS select `live_lesson_grade`.`id` AS `id`,`class_grades`.`class_grades_name` AS `class_grades_name`,`live_lesson_grade`.`agent_id` AS `agent_id` from (`live_lesson_grade` join `class_grades`) where (`live_lesson_grade`.`grade_id` = `class_grades`.`id`) */;

/*View structure for view v_member_to_teacherinfo */

/*!50001 DROP TABLE IF EXISTS `v_member_to_teacherinfo` */;
/*!50001 DROP VIEW IF EXISTS `v_member_to_teacherinfo` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_member_to_teacherinfo` AS select `t`.`member_id` AS `member_id`,concat(`t`.`name`,'-(',`m`.`account`,')') AS `info` from (`class_teacher` `t` join `memberaccount` `m` on((`t`.`member_id` = `m`.`memberid`))) */;

/*View structure for view v_oss_agent_school */

/*!50001 DROP TABLE IF EXISTS `v_oss_agent_school` */;
/*!50001 DROP VIEW IF EXISTS `v_oss_agent_school` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_oss_agent_school` AS select `a`.`id` AS `id`,`a`.`name` AS `name`,`a`.`oss_user_id` AS `oss_user_id`,`a`.`agent_id` AS `agent_id`,ifnull(sum(`b`.`amount`),0) AS `credit`,`a`.`create_time` AS `create_time`,`a`.`update_time` AS `update_time` from (`oss_agent_school` `a` left join `school_credit_record` `b` on((`a`.`id` = `b`.`school_id`))) group by `a`.`id` */;

/*View structure for view v_qy_class */

/*!50001 DROP TABLE IF EXISTS `v_qy_class` */;
/*!50001 DROP VIEW IF EXISTS `v_qy_class` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_qy_class` AS select `c`.`id` AS `id`,`c`.`class_name` AS `class_name`,`c`.`member_id` AS `member_id`,`s`.`a_id` AS `agent_id`,`s`.`id` AS `school_id`,`s`.`name` AS `school_name`,`t`.`id` AS `teacher_id`,`t`.`name` AS `teacher_name`,`t`.`rfid` AS `teacher_rfid` from (((`qy_class` `c` join `school_teacher_rela` `tr`) join `agent_school` `s`) join `class_teacher` `t`) where ((`c`.`member_id` = `tr`.`member_id`) and (`s`.`id` = `tr`.`school_id`) and (`t`.`member_id` = `c`.`member_id`)) */;

/*View structure for view v_qy_device */

/*!50001 DROP TABLE IF EXISTS `v_qy_device` */;
/*!50001 DROP VIEW IF EXISTS `v_qy_device` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_qy_device` AS select `d`.`id` AS `id`,`d`.`device_id` AS `device_id`,`d`.`device_name` AS `device_name`,`d`.`remark` AS `remark`,`d`.`member_id` AS `member_id`,`d`.`belong` AS `belong`,`s`.`name` AS `name`,`s`.`a_id` AS `agent_id`,`d`.`class_id` AS `class` from (`qy_device` `d` left join `agent_school` `s` on((`d`.`belong` = `s`.`id`))) */;

/*View structure for view v_qy_punch_card_group */

/*!50001 DROP TABLE IF EXISTS `v_qy_punch_card_group` */;
/*!50001 DROP VIEW IF EXISTS `v_qy_punch_card_group` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_qy_punch_card_group` AS select `g`.`id` AS `group_id`,`g`.`name` AS `group_name`,`a`.`id` AS `school_id`,`a`.`name` AS `school_name`,`a`.`a_id` AS `agent_id` from (`qy_punch_action_type_group` `g` join `agent_school` `a`) where (`g`.`school_id` = `a`.`id`) */;

/*View structure for view v_qy_punch_card_record */

/*!50001 DROP TABLE IF EXISTS `v_qy_punch_card_record` */;
/*!50001 DROP VIEW IF EXISTS `v_qy_punch_card_record` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_qy_punch_card_record` AS select `r`.`id` AS `id`,`r`.`card_id` AS `card_id`,`r`.`device_id` AS `device_id`,`r`.`punch_time` AS `punch_time`,`r`.`punch_image` AS `punch_image`,`r`.`send_status` AS `send_status`,`r`.`action` AS `action`,`s`.`class_id` AS `class_id` from ((`qy_punch_record` `r` join `qy_student_card` `c` on((`r`.`card_id` = `c`.`card_id`))) join `qy_class_to_student` `s` on((`c`.`student_id` = `s`.`student_id`))) */;

/*View structure for view v_qy_punch_card_type */

/*!50001 DROP TABLE IF EXISTS `v_qy_punch_card_type` */;
/*!50001 DROP VIEW IF EXISTS `v_qy_punch_card_type` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_qy_punch_card_type` AS select `type`.`id` AS `id`,`type`.`name` AS `name`,`type`.`msg` AS `msg`,`g`.`group_id` AS `group_id`,`g`.`group_name` AS `group_name`,`g`.`school_id` AS `school_id`,`g`.`school_name` AS `school_name`,`g`.`agent_id` AS `agent_id` from (`qy_punch_action_type` `type` left join `v_qy_punch_card_group` `g` on((`type`.`group_id` = `g`.`group_id`))) */;

/*View structure for view v_qy_punch_record */

/*!50001 DROP TABLE IF EXISTS `v_qy_punch_record` */;
/*!50001 DROP VIEW IF EXISTS `v_qy_punch_record` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_qy_punch_record` AS select `pr`.`id` AS `id`,`pr`.`card_id` AS `card_id`,`pr`.`device_id` AS `device_id`,`pr`.`punch_time` AS `punch_time`,`pr`.`punch_image` AS `punch_image`,`pr`.`send_status` AS `send_status`,`pr`.`action` AS `action`,`pr`.`type` AS `type_id`,`t`.`name` AS `type_name`,`t`.`group_id` AS `group_id`,`t`.`group_name` AS `group_name`,`d`.`device_name` AS `device_name`,`d`.`agent_id` AS `agent_id`,`s`.`name` AS `name` from (((`qy_punch_record` `pr` left join `v_qy_device` `d` on((`pr`.`device_id` = `d`.`device_id`))) left join `v_qy_punch_card_type` `t` on((`pr`.`type` = `t`.`id`))) left join `v_qy_student` `s` on((`pr`.`card_id` = `s`.`card_id`))) */;

/*View structure for view v_qy_rfid */

/*!50001 DROP TABLE IF EXISTS `v_qy_rfid` */;
/*!50001 DROP VIEW IF EXISTS `v_qy_rfid` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_qy_rfid` AS select `sc`.`student_id` AS `student_id`,`sc`.`status` AS `status`,`s`.`name` AS `name`,`vc`.`class_name` AS `class_name`,`sc`.`card_id` AS `id`,`vc`.`agent_id` AS `agent_id`,`vc`.`member_id` AS `member_id` from (((`qy_student_card` `sc` left join `qy_class_to_student` `c` on((`sc`.`student_id` = `c`.`student_id`))) left join `class_student` `s` on((`sc`.`student_id` = `s`.`id`))) left join `v_qy_class` `vc` on((`c`.`class_id` = `vc`.`id`))) */;

/*View structure for view v_qy_statistics_class */

/*!50001 DROP TABLE IF EXISTS `v_qy_statistics_class` */;
/*!50001 DROP VIEW IF EXISTS `v_qy_statistics_class` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_qy_statistics_class` AS select `c`.`class_name` AS `class_name`,`c`.`id` AS `class_id`,`c`.`member_id` AS `member_id`,count(0) AS `student_count` from (`qy_class` `c` left join (`qy_class_to_student` `cs` join `qy_student_card` `sc` on(((`cs`.`student_id` = `sc`.`student_id`) and (`sc`.`status` <> -(1)) and (`sc`.`status` <> -(1))))) on((`cs`.`class_id` = `c`.`id`))) group by `c`.`class_name`,`cs`.`class_id` */;

/*View structure for view v_qy_statistics_school */

/*!50001 DROP TABLE IF EXISTS `v_qy_statistics_school` */;
/*!50001 DROP VIEW IF EXISTS `v_qy_statistics_school` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_qy_statistics_school` AS select `s`.`id` AS `school_id`,`s`.`name` AS `school_name`,`s`.`a_id` AS `agent_id`,sum(`c`.`student_count`) AS `student_count` from ((`agent_school` `s` join `v_qy_statistics_class` `c`) join `school_teacher_rela` `rela`) where ((`s`.`id` = `rela`.`school_id`) and (`rela`.`member_id` = `c`.`member_id`)) group by `s`.`id`,`s`.`name` */;

/*View structure for view v_qy_student */

/*!50001 DROP TABLE IF EXISTS `v_qy_student` */;
/*!50001 DROP VIEW IF EXISTS `v_qy_student` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_qy_student` AS select `c`.`card_id` AS `card_id`,`c`.`contacts` AS `contacts`,`c`.`status` AS `status`,`c`.`id` AS `card_id_id`,`s`.`name` AS `name`,`s`.`id` AS `id`,`s`.`avatar` AS `avatar`,`cs`.`class_id` AS `class_id`,`vc`.`agent_id` AS `agent_id`,`vc`.`class_name` AS `class_name` from (((`qy_student_card` `c` left join `class_student` `s` on((`c`.`student_id` = `s`.`id`))) left join `qy_class_to_student` `cs` on((`cs`.`student_id` = `s`.`id`))) left join `v_qy_class` `vc` on((`vc`.`id` = `cs`.`class_id`))) */;

/*View structure for view v_qy_teacher */

/*!50001 DROP TABLE IF EXISTS `v_qy_teacher` */;
/*!50001 DROP VIEW IF EXISTS `v_qy_teacher` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_qy_teacher` AS select `t`.`id` AS `id`,`t`.`rfid` AS `rfid`,`t`.`member_id` AS `member_id`,`t`.`agent_id` AS `agent_id`,`m`.`account` AS `account`,`t`.`name` AS `name` from ((`class_teacher` `t` join `school_teacher_rela` `tr` on((`t`.`member_id` = `tr`.`member_id`))) left join `memberaccount` `m` on((`t`.`member_id` = `m`.`memberid`))) order by `tr`.`id` desc */;

/*View structure for view v_rfid */

/*!50001 DROP TABLE IF EXISTS `v_rfid` */;
/*!50001 DROP VIEW IF EXISTS `v_rfid` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_rfid` AS select `rfid`.`id` AS `id`,`rfid`.`card_fid` AS `card_fid`,`rfid`.`student_id` AS `student_id`,`rfid`.`insert_date` AS `insert_date`,`rfid`.`update_date` AS `update_date`,`rfid`.`update_by` AS `update_by`,`rfid`.`remark` AS `remark`,`rfid`.`agent_id` AS `agent_id`,`rfid`.`school_id` AS `school_id`,`s`.`name` AS `name` from (`public_room_fid_to_student` `rfid` left join `class_student` `s` on((`rfid`.`student_id` = `s`.`id`))) */;

/*View structure for view v_rfid_all */

/*!50001 DROP TABLE IF EXISTS `v_rfid_all` */;
/*!50001 DROP VIEW IF EXISTS `v_rfid_all` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_rfid_all` AS select `public_room_fid_to_student`.`card_fid` AS `card_id`,`public_room_fid_to_student`.`student_id` AS `student_id` from `public_room_fid_to_student` union select `qy_student_card`.`card_id` AS `card_id`,`qy_student_card`.`student_id` AS `student_id` from `qy_student_card` union select `rfid`.`card_id` AS `card_id`,'' AS `student_id` from `rfid` */;

/*View structure for view v_room_tree_id_with_r_name */

/*!50001 DROP TABLE IF EXISTS `v_room_tree_id_with_r_name` */;
/*!50001 DROP VIEW IF EXISTS `v_room_tree_id_with_r_name` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_room_tree_id_with_r_name` AS select `room_tree`.`id` AS `id`,concat(ifnull(`class_room`.`class_name`,`class_room`.`id`),' : ',ifnull(`class_room`.`teacher_name`,'_')) AS `name` from (`room_tree` join `class_room`) where ((`room_tree`.`pid` = 705) and (`class_room`.`id` = `room_tree`.`room_id`)) */;

/*View structure for view v_room_with_t_name */

/*!50001 DROP TABLE IF EXISTS `v_room_with_t_name` */;
/*!50001 DROP VIEW IF EXISTS `v_room_with_t_name` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_room_with_t_name` AS select `class_room`.`id` AS `ID`,concat(ifnull(`class_room`.`class_name`,`class_room`.`id`),' : ',ifnull(`class_room`.`teacher_name`,'_')) AS `CN` from `class_room` */;

/*View structure for view v_school_grade_rel */

/*!50001 DROP TABLE IF EXISTS `v_school_grade_rel` */;
/*!50001 DROP VIEW IF EXISTS `v_school_grade_rel` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_school_grade_rel` AS select `a`.`id` AS `school_id`,`b`.`id` AS `class_grade_id` from ((`oss_agent_school` `a` join `class_grades` `b`) join `class_teacher` `c`) where ((`a`.`id` = `c`.`agent_id`) and (`c`.`id` = `b`.`teacher_id`)) */;

/*View structure for view v_school_student */

/*!50001 DROP TABLE IF EXISTS `v_school_student` */;
/*!50001 DROP VIEW IF EXISTS `v_school_student` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_school_student` AS select `c`.`id` AS `id`,`c`.`name` AS `name`,`c`.`epal_id` AS `epal_id`,`c`.`create_time` AS `create_time`,`c`.`student_type` AS `student_type`,`c`.`sort_id` AS `sort_id`,`c`.`integral` AS `integral`,`c`.`contribution` AS `contribution`,`c`.`remark` AS `remark`,`c`.`member_id` AS `member_id`,`c`.`degree_of_difficulty` AS `degree_of_difficulty`,`c`.`avatar` AS `avatar`,`c`.`status` AS `status`,`c`.`lesson_integral` AS `lesson_integral`,`ct`.`agent_id` AS `agent_id`,`c`.`sex` AS `sex` from (((`class_teacher` `ct` join `class_grades` `a` on((`ct`.`id` = `a`.`teacher_id`))) join `class_grades_rela` `b` on(((`a`.`id` = `b`.`class_grades_id`) and (`b`.`gradesStatus` > 0)))) join `class_student` `c` on((`b`.`class_student_id` = `c`.`id`))) where (1 = 1) group by `c`.`id` union (select `class_student`.`id` AS `id`,`class_student`.`name` AS `name`,`class_student`.`epal_id` AS `epal_id`,`class_student`.`create_time` AS `create_time`,`class_student`.`student_type` AS `student_type`,`class_student`.`sort_id` AS `sort_id`,`class_student`.`integral` AS `integral`,`class_student`.`contribution` AS `contribution`,`class_student`.`remark` AS `remark`,`class_student`.`member_id` AS `member_id`,`class_student`.`degree_of_difficulty` AS `degree_of_difficulty`,`class_student`.`avatar` AS `avatar`,`class_student`.`status` AS `status`,`class_student`.`lesson_integral` AS `lesson_integral`,`class_student`.`agent_id` AS `agent_id`,`class_student`.`sex` AS `sex` from `class_student` where (`class_student`.`agent_id` > 0)) */;

/*View structure for view v_school_student_account */

/*!50001 DROP TABLE IF EXISTS `v_school_student_account` */;
/*!50001 DROP VIEW IF EXISTS `v_school_student_account` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_school_student_account` AS select `a`.`id` AS `id`,`a`.`name` AS `name`,`a`.`epal_id` AS `epal_id`,`a`.`create_time` AS `create_time`,`a`.`student_type` AS `student_type`,`a`.`sort_id` AS `sort_id`,`a`.`integral` AS `integral`,`a`.`contribution` AS `contribution`,`a`.`remark` AS `remark`,`a`.`member_id` AS `member_id`,`a`.`degree_of_difficulty` AS `degree_of_difficulty`,`a`.`avatar` AS `avatar`,`a`.`status` AS `status`,`a`.`lesson_integral` AS `lesson_integral`,`a`.`agent_id` AS `agent_id`,`a`.`sex` AS `sex`,`b`.`account` AS `account` from (`school_student` `a` join `student_account` `b`) where (`a`.`id` = `b`.`id`) group by `a`.`id`,`a`.`agent_id` */;

/*View structure for view v_std_for_agent */

/*!50001 DROP TABLE IF EXISTS `v_std_for_agent` */;
/*!50001 DROP VIEW IF EXISTS `v_std_for_agent` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_std_for_agent` AS (select `g`.`agent_id` AS `a_id`,`gr`.`class_student_id` AS `s_id` from (`v_grade` `g` join `class_grades_rela` `gr`) where (`g`.`id` = `gr`.`class_grades_id`)) union (select `fid`.`agent_id` AS `a_id`,`fid`.`student_id` AS `s_id` from `public_room_fid_to_student` `fid`) */;

/*View structure for view v_std_has_video */

/*!50001 DROP TABLE IF EXISTS `v_std_has_video` */;
/*!50001 DROP VIEW IF EXISTS `v_std_has_video` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_std_has_video` AS select `s`.`id` AS `s_id`,`s`.`name` AS `name`,`i`.`id` AS `v_id`,`i`.`pic_url` AS `pic_url`,`i`.`v_title` AS `v_title`,`i`.`v_url` AS `v_url` from (`class_student` `s` left join `video_info` `i` on((`s`.`epal_id` = `i`.`epal_id`))) */;

/*View structure for view v_student */

/*!50001 DROP TABLE IF EXISTS `v_student` */;
/*!50001 DROP VIEW IF EXISTS `v_student` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_student` AS select `g`.`id` AS `g_id`,`g`.`class_grades_name` AS `g_name`,`s`.`id` AS `s_id`,`s`.`name` AS `s_name`,`s`.`epal_id` AS `epal_id`,`g`.`agent_id` AS `agent_id` from ((`v_grade` `g` join `class_grades_rela` `gr`) join `class_student` `s`) where ((`g`.`id` = `gr`.`class_grades_id`) and (`gr`.`class_student_id` = `s`.`id`)) */;

/*View structure for view v_student_account */

/*!50001 DROP TABLE IF EXISTS `v_student_account` */;
/*!50001 DROP VIEW IF EXISTS `v_student_account` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_student_account` AS select `a`.`id` AS `id`,`c`.`id` AS `account_id` from ((`class_student` `a` join `device_relation` `b`) join `memberaccount` `c`) where ((`b`.`isbind` = 1) and (`a`.`epal_id` = `b`.`epal_id`) and (`b`.`friend_id` = `c`.`account`)) */;

/*View structure for view v_student_grade_rel */

/*!50001 DROP TABLE IF EXISTS `v_student_grade_rel` */;
/*!50001 DROP VIEW IF EXISTS `v_student_grade_rel` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_student_grade_rel` AS select `a`.`id` AS `class_grades_id`,`a`.`class_grades_name` AS `class_grades_name`,`a`.`summary` AS `summary`,`a`.`grades_type` AS `grades_type`,`b`.`create_time` AS `join_date`,`b`.`gradesStatus` AS `gradesStatus`,`c`.`id` AS `class_student_id`,`c`.`name` AS `class_student_name` from ((`class_grades` `a` join `class_grades_rela` `b`) join `class_student` `c`) where ((`a`.`id` = `b`.`class_grades_id`) and (`b`.`class_student_id` = `c`.`id`)) */;

/*View structure for view v_teacher_account */

/*!50001 DROP TABLE IF EXISTS `v_teacher_account` */;
/*!50001 DROP VIEW IF EXISTS `v_teacher_account` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_teacher_account` AS select `a`.`id` AS `id`,ifnull(`a`.`name`,'') AS `name`,`b`.`account` AS `account`,`a`.`agent_id` AS `agent_id`,`a`.`avatar` AS `avatar`,`b`.`id` AS `accountId` from (`class_teacher` `a` join `memberaccount` `b`) where (`a`.`member_id` = `b`.`memberid`) */;

/*View structure for view v_teacher_product */

/*!50001 DROP TABLE IF EXISTS `v_teacher_product` */;
/*!50001 DROP VIEW IF EXISTS `v_teacher_product` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_teacher_product` AS select `a`.`teacher_id` AS `teacher_id`,`b`.`id` AS `id`,`b`.`name` AS `name`,`b`.`createdate` AS `createdate`,`b`.`content` AS `content`,`b`.`logo1` AS `logo1`,`b`.`price` AS `price`,`b`.`class_room_package` AS `class_room_package`,`b`.`class_grade_id` AS `class_grade_id`,`b`.`status` AS `status` from (`classroom_package` `a` join `mallproduct` `b`) where (`a`.`id` = `b`.`class_room_package`) union select `a`.`teacher_id` AS `teacher_id`,`b`.`id` AS `id`,`b`.`name` AS `name`,`b`.`createdate` AS `createdate`,`b`.`content` AS `content`,`b`.`logo1` AS `logo1`,`b`.`price` AS `price`,`b`.`class_room_package` AS `class_room_package`,`b`.`class_grade_id` AS `class_grade_id`,`b`.`status` AS `status` from (`class_grades` `a` join `mallproduct` `b`) where (`a`.`id` = `b`.`class_grade_id`) */;

/*View structure for view v_user_grade */

/*!50001 DROP TABLE IF EXISTS `v_user_grade` */;
/*!50001 DROP VIEW IF EXISTS `v_user_grade` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_user_grade` AS select `ug`.`user_id` AS `user_id`,`ug`.`class_grades_id` AS `grade_id` from `user_to_class_grades` `ug` union select `t`.`agent_id` AS `user_id`,`g`.`id` AS `grade_id` from (`class_teacher` `t` left join `class_grades` `g` on((`t`.`id` = `g`.`teacher_id`))) */;

/*View structure for view v_user_student */

/*!50001 DROP TABLE IF EXISTS `v_user_student` */;
/*!50001 DROP VIEW IF EXISTS `v_user_student` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_user_student` AS select `g`.`user_id` AS `user_id`,`gr`.`class_student_id` AS `student_id` from (`v_user_grade` `g` left join `class_grades_rela` `gr` on((`g`.`grade_id` = `gr`.`class_grades_id`))) union select `fid`.`agent_id` AS `user_id`,`fid`.`student_id` AS `student_id` from `public_room_fid_to_student` `fid` */;

/*View structure for view v_user_student_more */

/*!50001 DROP TABLE IF EXISTS `v_user_student_more` */;
/*!50001 DROP VIEW IF EXISTS `v_user_student_more` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_user_student_more` AS select `vs`.`user_id` AS `user_id`,`vs`.`student_id` AS `student_id`,`s`.`epal_id` AS `epal_id`,`s`.`name` AS `name`,`s`.`remark` AS `remark`,concat(`s`.`name`,'(',ifnull(`s`.`remark`,' '),')') AS `nameRemark` from (`v_user_student` `vs` join `class_student` `s`) where ((`vs`.`student_id` = `s`.`id`) and (length(`s`.`epal_id`) < 16)) */;

/*View structure for view v_video_info_score */

/*!50001 DROP TABLE IF EXISTS `v_video_info_score` */;
/*!50001 DROP VIEW IF EXISTS `v_video_info_score` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`wechat_user`@`%` SQL SECURITY DEFINER VIEW `v_video_info_score` AS (select `a`.`id` AS `id`,`a`.`video_info_id` AS `video_info_id`,`a`.`video_activity_id` AS `video_activity_id`,`a`.`verify_status` AS `verify_status`,`a`.`edit_time` AS `edit_time`,`a`.`creat_time` AS `creat_time`,`b`.`id` AS `vid`,`b`.`v_title` AS `v_title`,`b`.`v_url` AS `v_url`,`b`.`share_url` AS `share_url`,`b`.`acount_id` AS `acount_id`,`b`.`create_time` AS `create_time`,`b`.`admission` AS `admission`,`b`.`vote` AS `vote`,`b`.`access_num` AS `access_num`,`c`.`id` AS `sid`,`c`.`expert1` AS `expert1`,`c`.`expert2` AS `expert2`,`c`.`expert3` AS `expert3`,`c`.`expert4` AS `expert4` from ((`video_competition` `a` join `video_info` `b`) join `video_score` `c`) where ((`a`.`video_info_id` = `b`.`id`) and (`b`.`id` = `c`.`video_info_id`))) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
