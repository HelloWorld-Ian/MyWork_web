# MyWork_web
网络计划图web项目

### 运行环境
idea+MySQL数据库+tomcat服务器

### Mysql 数据库配置
User —— root

密码  —— 自行设置

数据库名 —— myproject

表格创建：

1. 
CREATE TABLE `basic_workdata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `time` varchar(10) DEFAULT NULL,
  `pre_work` varchar(50) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `work_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

2.
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) DEFAULT NULL,
  `project_name` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `time_type` varchar(15) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
)

3.
 CREATE TABLE `user_password` (
  `password_id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(50) DEFAULT NULL,
  UNIQUE KEY `password_id` (`password_id`)
)

4.
  CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
)


### 启动
数据库配置完成后，启动tomcat服务器，从login.html进入网页
