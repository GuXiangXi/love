--用户表
drop table if exists loveUser;
create table loveUser(
	id int primary key auto_increment,
	code int,
	name varchar(200),
	password  varchar(200),
	role varchar(1) --0普通人 1管理员
);

--活动表
drop table if exists loveTitle;
create table loveTitle(
	id int primary key auto_increment,
	titleTime date,
	name varchar(100),
	peopleName varchar(200),
	status varchar(1), --0进行中 1筹备中 2已结束
	isShow varchar(1) --0在移动端 展示  1不展示
);

--点赞表
drop table if exists loveJob;
create table loveJob(
	id int primary key auto_increment,
	titleId int,
	loveId int,
	loveName  varchar(100),
	love varchar(1)
);


--参数表
drop table if exists pram;
create table pram(
	id int primary key auto_increment,
	code varchar(100),
	name varchar(200),
	typeName varchar(100) 
);
