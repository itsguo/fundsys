create database funddb;
use funddb;
create table FUNDSYS_USERS(
   user_no    char(6) primary key,
   user_pwd   char(6) not null,
   user_issuperadmin char(1) not null
);
insert FUNDSYS_USERS values("admin","123","-");

create table CLIENT(
 CLIENT_ID CHAR(18) primary key,
 CLIENT_NAME VARCHAR (30) not null,
 CLIENT_PWD VARCHAR(16) NOT NULL,
 SEX CHAR(1),
 PHONE VARCHAR (20),
 ADDRESS VARCHAR (20),
 EMAIL VARCHAR (50),
 BALANCE double,
 TOTAL_EARNINGS double,
 ACTIVE tinyint(1) DEFAULT '1'
);

create table FUND_TYPE(
  FUND_TYPE_ID INT primary key auto_increment,
  FUND_TYPE_NAME VARCHAR(10) not null,
  FUND_TYPE_DESCRIBE VARCHAR(50) not null
);

create table FUND(
   FUND_NO int  primary key auto_increment,
   FUND_NAME VARCHAR(40) unique,
   DESCRIPTION VARCHAR(100),
   STATUS CHAR(3) not null,
   CREATED_DATE DATETIME not null,
   RATE double not null,
   FUND_TYPE_ID int not null,
   constraint FK_FUND_TYPE_ID FOREIGN KEY(FUND_TYPE_ID) references FUND_TYPE(FUND_TYPE_ID) 
)AUTO_INCREMENT=100;

create table RATE_RECORD(
   RNO int primary key auto_increment,
   FUND_NO int not null,
   RATE double not null,
   constraint FK_RATE_RECORD FOREIGN KEY(FUND_NO) references FUND(FUND_NO) 
);


create table CILENT_TRANSINFO(
   TRANS_ID INT primary key auto_increment,
   TRANS_TYPE CHAR(2) not null,
   TRANS_PRICE double not null,
   CLIENT_ID CHAR(18) not null,
   TARGET_ID CHAR(18) not null,
   CREATE_DATE DATETIME not null,
   constraint FK_CLIENT_ID FOREIGN KEY(CLIENT_ID) references CLIENT(CLIENT_ID)
)AUTO_INCREMENT=10000;

create table FUND_TRANSINFO(
   TRANS_ID INT primary key auto_increment,
   TRANS_TYPE CHAR(2) not null,
   CLIENT_ID CHAR(18) not null,
   FUND_NO INT not null,
   CREATE_DATE DATETIME not null,
   AMOUNT  double not null,
   constraint FK_CLIENT_ID2 FOREIGN KEY(CLIENT_ID) references CLIENT(CLIENT_ID),
   constraint FK_FUND_NO FOREIGN KEY(FUND_NO) references FUND(FUND_NO)
)AUTO_INCREMENT=10000;

create table FUND_HOLDING(
   HID INT primary key auto_increment,
   CLIENT_ID CHAR(18) not null,
   FUND_NO INT not null,
   AMOUNT  double not null,
   YESTODAY_EARN double not null,
   constraint FK_CLIENT_ID3 FOREIGN KEY(CLIENT_ID) references CLIENT(CLIENT_ID),
   constraint FK_FUND_NO2 FOREIGN KEY(FUND_NO) references FUND(FUND_NO)
);

create table feature(
    feature_id int primary key auto_increment,
    feature_title varchar(5),
    feature_describe varchar(20),
    feature_info varchar(20),
    feature_info2 varchar(20)
);

create table client_picture(
    picture_id int primary key auto_increment,
    picture_oldname varchar(255) not null,
    picture_newname varchar(255) not null,
    CLIENT_ID CHAR(18) not null
);
