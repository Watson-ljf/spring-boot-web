/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.34-log : Database - watson
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`watson` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `watson`;

/*Table structure for table `sys_dictionary` */

DROP TABLE IF EXISTS `sys_dictionary`;

CREATE TABLE `sys_dictionary` (
  `dict_code` varchar(8) NOT NULL COMMENT '字典主键',
  `dict_name` varchar(50) NOT NULL COMMENT '字典名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典';

/*Data for the table `sys_dictionary` */

insert  into `sys_dictionary`(`dict_code`,`dict_name`,`description`) values ('1','性别',NULL);

/*Table structure for table `sys_dictionarydata` */

DROP TABLE IF EXISTS `sys_dictionarydata`;

CREATE TABLE `sys_dictionarydata` (
  `dictdata_code` varchar(8) NOT NULL COMMENT '字典项主键',
  `dict_code` varchar(8) NOT NULL COMMENT '字典主键',
  `dictdata_name` varchar(50) NOT NULL COMMENT '字典项值',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `sort_number` int(11) NOT NULL COMMENT '排序',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`dictdata_code`),
  KEY `FK_Reference_36` (`dict_code`),
  CONSTRAINT `FK_Reference_36` FOREIGN KEY (`dict_code`) REFERENCES `sys_dictionary` (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='字典项';

/*Data for the table `sys_dictionarydata` */

insert  into `sys_dictionarydata`(`dictdata_code`,`dict_code`,`dictdata_name`,`is_delete`,`sort_number`,`description`) values ('D3J2tkwu','1','男',0,0,NULL),('fSkvu3XK','1','女',0,1,NULL);

/*Table structure for table `sys_login_record` */

DROP TABLE IF EXISTS `sys_login_record`;

CREATE TABLE `sys_login_record` (
  `id` varchar(8) NOT NULL COMMENT '主键',
  `user_id` varchar(8) NOT NULL COMMENT '用户id',
  `os_name` varchar(200) DEFAULT NULL COMMENT '操作系统',
  `device` varchar(200) DEFAULT NULL COMMENT '设备名',
  `browser_type` varchar(200) DEFAULT NULL COMMENT '浏览器类型',
  `ip_address` varchar(200) DEFAULT NULL COMMENT 'ip地址',
  `create_time` datetime NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_login_record_user` (`user_id`),
  CONSTRAINT `FK_sys_login_record_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_login_record` */

insert  into `sys_login_record`(`id`,`user_id`,`os_name`,`device`,`browser_type`,`ip_address`,`create_time`) values ('e18vTY9T','Erk7B4aH','Android Mobile','MI 6','Chrome Mobile','220.249.67.55','2018-02-18 09:29:51'),('Gz0w1Fp3','Erk7B4aH','Windows 7','Windows 7','Chrome','14.155.89.210','2018-02-22 13:44:58'),('IOIIGOUU','Erk7B4aH','Windows 7','Windows 7','Chrome','14.155.89.210','2018-02-22 13:42:23'),('LoQW0WfA','Erk7B4aH','Windows 10','Windows 10','Chrome','192.168.2.145','2018-02-14 20:38:08'),('p2R5ptFB','Erk7B4aH','Windows 10','Windows 10','Firefox','223.64.149.65','2018-02-22 23:17:06'),('phYwan05','GzV5Qh3u','Android Mobile','MI 6','Chrome Mobile','192.168.1.153','2018-02-23 12:37:24'),('TFnG9mLi','GzV5Qh3u','Android Mobile','MI 6','Chrome Mobile','192.168.1.153','2018-02-23 12:37:49');

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `permission_id` varchar(8) NOT NULL COMMENT '主键id',
  `parent_id` varchar(8) DEFAULT NULL COMMENT '上级权限',
  `permission_name` varchar(20) NOT NULL COMMENT '权限名',
  `permission_value` varchar(20) DEFAULT NULL COMMENT '权限值',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除：0否,1是',
  `permission_icon` varchar(20) DEFAULT NULL COMMENT '图标',
  `order_number` int(11) NOT NULL DEFAULT '0' COMMENT '菜单排序编号',
  `permission_type` int(11) NOT NULL DEFAULT '0' COMMENT '权限类型：0菜单，1按钮',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`permission_id`,`parent_id`,`permission_name`,`permission_value`,`create_time`,`update_time`,`is_delete`,`permission_icon`,`order_number`,`permission_type`) values ('5zLoLbDq','X0K27xEi','角色管理','system/role','2017-04-15 12:47:52','2018-02-23 09:35:37',0,NULL,2,0),('6ow0AXFF','X0K27xEi','用户管理','system/user','2017-04-08 13:54:23','2018-02-14 19:37:18',0,NULL,1,0),('SQMnxrNU','X0K27xEi','权限管理','system/permission','2017-04-14 13:18:02','2018-02-23 09:35:42',0,NULL,3,0),('w9F4aXyx','X0K27xEi','登录日志','system/loginRecord','2017-07-24 13:41:13','2018-02-23 09:35:50',0,NULL,4,0),('X0K27xEi','0','系统管理',NULL,'2017-04-08 13:42:46','2018-02-23 09:35:30',0,'&#xe716;',0,0);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` varchar(8) NOT NULL,
  `role_name` varchar(100) NOT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`role_name`,`comments`,`create_time`,`update_time`,`is_delete`) values ('admin','管理员','系统管理员','2018-02-23 08:31:22','2018-02-23 11:18:26',0),('user','普通用户','系统普通用户','2018-02-23 08:32:11','2018-02-23 11:19:08',0);

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` varchar(8) NOT NULL,
  `role_id` varchar(8) NOT NULL,
  `permission_id` varchar(8) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_role_permission_pm` (`permission_id`),
  KEY `FK_sys_role_permission_role` (`role_id`),
  CONSTRAINT `FK_sys_role_permission_pm` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`permission_id`),
  CONSTRAINT `FK_sys_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`id`,`role_id`,`permission_id`) values ('4WXNS7Y8','user','5zLoLbDq'),('DFTJjVuq','admin','5zLoLbDq'),('FcVIAl6N','user','X0K27xEi'),('KCeWOT7v','admin','6ow0AXFF'),('pRg5fL5h','user','6ow0AXFF'),('qKfFl99Q','admin','SQMnxrNU'),('rfzhAgh1','user','SQMnxrNU'),('rxTpMfLd','user','w9F4aXyx'),('SOj7EIHC','admin','w9F4aXyx'),('YUiRUpGt','admin','X0K27xEi');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` varchar(8) NOT NULL COMMENT '主键id',
  `user_account` varchar(20) NOT NULL COMMENT '账号',
  `user_password` varchar(32) NOT NULL COMMENT '密码',
  `user_nickname` varchar(20) NOT NULL COMMENT '用户名',
  `mobile_phone` varchar(12) DEFAULT NULL COMMENT '手机号',
  `sex` varchar(1) NOT NULL DEFAULT '男' COMMENT '性别',
  `user_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态，0正常，1冻结',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `role_id` varchar(8) NOT NULL COMMENT '角色ID',
  `token` varchar(200) DEFAULT NULL COMMENT '设备id',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_account` (`user_account`),
  KEY `FK_sys_user_role` (`role_id`),
  CONSTRAINT `FK_sys_user_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`user_account`,`user_password`,`user_nickname`,`mobile_phone`,`sex`,`user_status`,`create_time`,`update_time`,`role_id`,`token`) values ('Erk7B4aH','admin','ba3f6006c8ee8dd080b8d29825dd44a4','管理员','13125062807','女',0,'2017-08-14 14:12:36','2018-02-23 11:01:45','admin','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJHelY1UWgzdSIsImlhdCI6MTUxODYxMDMzNSwiZXhwIjoxNTIxMjAyMzM1fQ.AS8SdYzkF-mOXvn316wCqQiNsHSdnW6Lkcg48pCrsS0'),('GzV5Qh3u','watson','59e577fe762108b7d783fe514f2b493f','演示账号','13125062807','男',0,'2018-02-13 21:42:35','2018-02-23 11:13:06','user','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFcms3QjRhSCIsImlhdCI6MTUxOTM1NTUyOSwiZXhwIjoxNTIxOTQ3NTI5fQ.BQB4QPAMcH-OZhXZNiZV714AAJmWeS_8YpoC40xQ8LQ');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
