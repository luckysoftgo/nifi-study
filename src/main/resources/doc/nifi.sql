DROP DATABASE if exists test_database;
CREATE DATABASE test_database DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

DROP TABLE IF EXISTS test_nifi_branch;
CREATE TABLE test_nifi_branch (
   branch_id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长的主键',
   id varchar(500) NOT NULL DEFAULT '' COMMENT '主键',
   uuid varchar(500) NOT NULL DEFAULT '' COMMENT 'uuid',
   disabled tinyint(1) DEFAULT '0' COMMENT '删除标志,1删除,0正常使用',
   companyId varchar(50) DEFAULT '' COMMENT '工业库企业Id',
   saveUser varchar(20) DEFAULT '' COMMENT '创建者',
   saveTime varchar(50) DEFAULT '' COMMENT '创建时间',
   logo text,
   regStatus varchar(200) DEFAULT '',
   estiblishTime varchar(200) DEFAULT '',
   regCapital varchar(200) DEFAULT '',
   pencertileScore varchar(200) DEFAULT '',
   type varchar(200) DEFAULT '',
   legalPersonName varchar(200) DEFAULT '',
   toco varchar(200) DEFAULT '',
   legalPersonId varchar(200) DEFAULT '',
   name varchar(200) DEFAULT '',
   alias varchar(200) DEFAULT '',
   category varchar(200) DEFAULT '',
   personType varchar(200) DEFAULT '',
   base varchar(200) DEFAULT '',
   PRIMARY KEY (branch_id)
 ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='天眼查企业分支结构信息';


DROP TABLE IF EXISTS test_nifi_teacher;
CREATE TABLE test_nifi_teacher (
   id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长的主键',
   name varchar(200) DEFAULT '' COMMENT '名称',
   age int(10) DEFAULT 0 COMMENT '年龄',
   gender varchar(10) DEFAULT '' COMMENT '性别',

   major varchar(200) DEFAULT '' COMMENT '专业',
   workyear int(10) DEFAULT 0 COMMENT '工作年限',
   PRIMARY KEY (id)
 ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='教师信息';

 DROP TABLE IF EXISTS test_nifi_student;
 CREATE TABLE test_nifi_student (
   id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长的主键',
   name varchar(200) DEFAULT '' COMMENT '名称',
   age int(10) DEFAULT 0 COMMENT '年龄',
   gender varchar(10) DEFAULT '' COMMENT '性别',

   grade varchar(200) DEFAULT '' COMMENT '年级',
   clasz varchar(200) DEFAULT '' COMMENT '所学课程',
   PRIMARY KEY (id)
 ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='学生信息';

 DROP TABLE IF EXISTS test_nifi_score;
 CREATE TABLE test_nifi_score (
   id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长的主键',
   course_name varchar(200) DEFAULT '' COMMENT '课程名称',
   teacher varchar(100) DEFAULT '' COMMENT '任课老师',
   student varchar(200) DEFAULT '' COMMENT '学生姓名',
   course_score int(10) DEFAULT 0 COMMENT '课程得分',
   PRIMARY KEY (id)
 ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='得分信息';

 DROP TABLE IF EXISTS test_nifi_logistics;
 CREATE TABLE test_nifi_logistics (
    lid int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长的主键',
    id varchar(200) DEFAULT '' COMMENT 'id',
    encrypt varchar(200) DEFAULT '' COMMENT '加密标识',
    up_date varchar(200) DEFAULT '' COMMENT '时间',
    lon varchar(200) DEFAULT '' COMMENT '经度',
    create_by varchar(200) DEFAULT '' COMMENT '创建人',
    create_dt varchar(200) DEFAULT '' COMMENT '创建时间',
    lat varchar(200) DEFAULT '' COMMENT '纬度',
    vec1 varchar(200) DEFAULT '' COMMENT 'GPS速度',
    vec2 varchar(200) DEFAULT '' COMMENT '行驶记录速度',
    vec3 varchar(200) DEFAULT '' COMMENT '车辆当前总里程数',
    direction varchar(200) DEFAULT '' COMMENT '方向',
    altitude varchar(200) DEFAULT '' COMMENT '海拔高度',
    state varchar(200) DEFAULT '' COMMENT '车辆状态',
    alarm varchar(200) DEFAULT '' COMMENT '报警状态',
    vehicleno varchar(200) DEFAULT '' COMMENT '车牌号码',
    vehiclecolor varchar(200) DEFAULT '' COMMENT '车身颜色',

   PRIMARY KEY (lid)
 ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='车辆轨迹信息';


-- nifi PutSql中的写法...
-- insert into test_nifi_teacher(name,age,gender,major,workyear) values ('${name}',${age},'${gender}','${major}',${workyear});
-- insert into test_nifi_student(name,age,gender,grade,clasz) values ('${name}',${age},'${gender}','${grade}','${clasz}');
-- insert into test_nifi_score(course_name,teacher,student,course_score) values ('${courseName}','${teacher}','${student}',${courseScore});
-- insert into test_nifi_logistics(id,encrypt,up_date,lon,create_by,create_dt,lat,vec1,vec2,vec3,direction,altitude,state,alarm,vehicleno,vehiclecolor) values ('${id}','${encrypt}','${up_date}','${lon}','${create_by}','${create_dt}','${lat}','${vec1}','${vec2}','${vec3}','${direction}','${altitude}','${state}','${alarm}','${vehicleno}','${vehiclecolor}');
-- insert into test_nifi_branch(id,regStatus,estiblishTime,regCapital,pencertileScore,type,legalPersonName,toco,legalPersonId,name,logo,alias,category,personType,base) values ('${id}','${regStatus}','${estiblishTime}','${regCapital}','${pencertileScore}','${type}','${legalPersonName}','${toco}','${legalPersonId}','${name}','${logo}','${alias}','${category}','${personType}','${base}');
