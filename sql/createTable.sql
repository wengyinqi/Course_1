drop table tc;
drop table sc;
drop table course;
drop table teacher;
drop table student;
drop table major;
drop table department;



CREATE TABLE department 
(
    Dnumber     VARCHAR(10),  -- 系号
    Dname       VARCHAR(20),  -- 系名
    Dphone      VARCHAR(11),
    Dintroduce  VARCHAR(200),
    PRIMARY KEY (Dnumber)
);

CREATE TABLE major 
(
    Mnumber     VARCHAR(10),  -- 专业号
    Mname       VARCHAR(50),  -- 专业名
    Mphone      VARCHAR(11),  -- 专业教务联系方式
    Mintroduce  VARCHAR(200), -- 专业介绍
    Dnumber     VARCHAR(10),  -- 专业所属的系号
    PRIMARY KEY (Mnumber),
    FOREIGN KEY (Dnumber) REFERENCES department(Dnumber)
);

CREATE TABLE student 
(
    Snumber     VARCHAR(13),  -- 学号
    Sname       VARCHAR(20),  -- 姓名
    Ssex        VARCHAR(10) ,  -- 性别
    Spassword   VARCHAR(20),  -- 密码
    Sage        NUMERIC(3, 0)   CHECK (Sage > 0),  -- 年龄
    Sphone      VARCHAR(11),  -- 电话
    Semail      VARCHAR(20),  -- 邮箱
    ShighSchool VARCHAR(20),  -- 高中毕业学校
    Sorigo      VARCHAR(20),  -- 籍贯
    Shobby      VARCHAR(100), -- 爱好
    Dnumber     VARCHAR(10),  -- 学生所属的系号
    Mnumber     VARCHAR(10),  -- 学生所属的专业号

    PRIMARY KEY (Snumber),
    FOREIGN KEY (Dnumber) REFERENCES department(Dnumber),
    FOREIGN KEY (Mnumber) REFERENCES major(Mnumber) 
);

CREATE TABLE teacher 
(
    Tnumber     VARCHAR(10),  -- 工号z
    Tname       VARCHAR(10),  -- 姓名
    Tsex        VARCHAR(2),   -- 性别
    Tpassword   VARCHAR(20),  -- 密码
    Tage        NUMERIC(3, 0)     CHECK(Tage > 0),  -- 年龄
    Tplace      VARCHAR(50), -- 办公地点
    Tresearch   VARCHAR(50), -- 研究方向
    Tphone      VARCHAR(11),  -- 联系方式
    Tpost       VARCHAR(10),  -- 职称
    Temail      VARCHAR(50),  -- 邮箱
    Dnumber     VARCHAR(10),  -- 老师所属的系号
    PRIMARY KEY (Tnumber),
    FOREIGN KEY (Dnumber) REFERENCES department(Dnumber)
);


CREATE TABLE course 
(
    Cnumber     VARCHAR(10),   -- 课程号
    Cname       VARCHAR(40),   -- 课程名   
    Corder      NUMERIC(2, 0), -- 课序号
    Ccredit     NUMERIC(2, 0)    CHECK (Ccredit > 0),   -- 学分  
    Cproperty   VARCHAR(10),    -- 课程属性: 选修/必修
    Ccapacity   NUMERIC(3, 0)    CHECK (Ccapacity > 0),   -- 课容量
    Ctime       VARCHAR(10),   -- 上课时间
    Cweek       VARCHAR(3) ,   -- 上课星期
    Csection    NUMERIC(4,0),   -- 上课节次
    Cschool     VARCHAR(5),    -- 校区
    Cfoor       VARCHAR(15),   -- 教学楼
    Croom       VARCHAR(15),   -- 教室
    Dnumber     VARCHAR(10),  -- 所属系号
    Mnumber     VARCHAR(10),  -- 所属的专业号
    PRIMARY KEY (Cnumber, Corder),
    FOREIGN KEY (Mnumber) REFERENCES major(Mnumber),
    FOREIGN KEY (Dnumber) REFERENCES department(Dnumber)
);

CREATE TABLE sc
(
    scnumber   INT(4) AUTO_INCREMENT PRIMARY KEY,  
    Snumber     VARCHAR(13),  -- 学号
    Cnumber     VARCHAR(10),  -- 课程号
    Corder      NUMERIC(2, 0), -- 课序号   
    FOREIGN KEY (Snumber) REFERENCES student(Snumber),
    FOREIGN KEY (Cnumber, Corder) REFERENCES course(Cnumber, Corder)
);


CREATE TABLE tc 
(
    tcnumber    INT(4) AUTO_INCREMENT PRIMARY KEY,  
    Tnumber     VARCHAR(10),  -- 工号
    Cnumber     VARCHAR(10),   -- 课程号
    Corder      NUMERIC(2, 0), -- 课序号
    FOREIGN KEY (Tnumber) REFERENCES teacher(Tnumber),
    FOREIGN KEY (Cnumber, Corder) REFERENCES course(Cnumber, Corder)
);




