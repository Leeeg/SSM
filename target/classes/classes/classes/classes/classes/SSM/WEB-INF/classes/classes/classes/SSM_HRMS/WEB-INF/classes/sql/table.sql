-- tbl_emp表：
DROP TABLE IF EXISTS `tbl_emp`;
CREATE TABLE `tbl_emp`(
	`emp_id` int(11) UNSIGNED NOT NULL auto_increment,
	`emp_name` VARCHAR(22) NOT NULL DEFAULT '',
  `emp_email` VARCHAR(256) NOT NULL DEFAULT '',
  `gender` CHAR(2) NOT NULL DEFAULT '',
   `department_id` int(11) NOT NULL DEFAULT 0,
	 PRIMARY KEY(`emp_id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;


-- tbl_dept表：
DROP TABLE IF EXISTS `tbl_dept`;
CREATE TABLE `tbl_dept`(
	`dept_id` int(11) NOT NULL DEFAULT 0,
	`dept_name` VARCHAR(255) NOT NULL DEFAULT '',
  `dept_leader` VARCHAR(255) NOT NULL DEFAULT ''

) ENGINE=InnoDB DEFAULT CHARSET=utf8;