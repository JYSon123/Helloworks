select *
from tab;

purge recyclebin;

desc tbl_department

-- 직원테이블 생성---------------------------------
create table tbl_employee
(
  empno varchar2(200) not null  -- to_char(sysdate, ‘yyyy’) || to_char(empnoSeq.nextvals)
, empname varchar2(20) not null
, empid varchar2(20) not null
, emppw varchar2(30) not null
, email varchar2(30) not null -- 아이디@helloworks.com
, ranking number default 1-- 사원(1), 대리(2), 부장(3), 팀장(4),  사장(5)
, fk_deptnum varchar2(10) 
, empstatus number(1) default 1 -- (1: 재직, 0: 퇴사, 2:휴직) 
, empsalary number(20) not null  
, hiredate  date default sysdate
, constraint PK_tbl_member_empno primary key(empno)
, constraint FK_tbl_member_deptnum foreign key(fk_deptnum) REFERENCES tbl_deptpartment(deptnum)
, constraint CK_tbl_emp_rank check(ranking in (1,2,3,4,5))
, constraint CK_tbl_emp_empstatus check(empstatus in (0,1,2))
);

select * from tbl_department
alter table tbl_employee add constraint FK_tbl_member_deptnum foreign key(fk_deptnum) REFERENCES tbl_department(deptnum);

insert into tbl_employee (empno, empname, empid, emppw, email, ranking, fk_deptnum, empstatus, hiredate)
values (to_char(sysdate)||empnoSeq.nextval, '관리자', 'admin', 'qwer1234$','admin@naver.com', )
values ('10');


create sequence empnoSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
-----------------------------------------------------------------

-- 캘린더 카테고리 테이블 생성 ---------------------------------------
create table tbl_calCategory (
 cno    number not null         -- 카테고리번호  (1,2)
,cname  varchar2(15) not null   -- 카테고리명    (Personal/Share)
,constraint PK_tbl_calCategory_cno primary key(cno)
);

insert into tbl_calCategory(cno, cname) values (1, 'personal');
insert into tbl_calCategory(cno, cname) values  (2, 'share');
-----------------------------------------------------------------

-- 캘린더 테이블 생성 ---------------------------------------
create table tbl_calendar (
 calno    number not null           -- 캘린더번호
,fk_cno   number not null           -- 카테고리 번호
,calname  varchar2(50) not null     -- 캘린더 명
,color    varchar2(10) not null     -- 색상
,shareEmp varchar2(50)              -- 공유인원
,constraint PK_tbl_calCategory_calno primary key(calno)
,constraint FK_tbl_calCategory_cno foreign key(fk_cno) references tbl_calCategory(cno)
);

create sequence calnoSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
-----------------------------------------------------------------

-- 일정 테이블 생성 ---------------------------------------
create table tbl_schedule (
 sno        number not null         -- 일정일련번호
,fk_calno   number not null         -- 캘린더번호
,title      varchar2(200) not null  -- 일정 제목
,content    varchar2(2000)          -- 내용
,location   varchar2(200)           -- 장소
,startdate  varchar2(100) not null  -- 시작날짜
,enddate    varchar2(100) not null  -- 마감날짜
,status     number  default 0       -- 상태
,constraint PK_tbl_schedule_sno primary key(sno)
,constraint FK_tbl_schedule_calno foreign key(fk_calno) references tbl_calendar(calno)
);

create sequence snoSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
------------------------------------------------------------------

-- 캘린더 카테고리 테이블 조회
select * from tbl_calCategory;
desc tbl_calCategory;
-- 카테고리 테이블 조회
select * from tbl_calendar;
desc tbl_calendar;
-- 일정 테이블 조회
select * from tbl_schedule;
desc tbl_schedule;

select calname 
from tbl_calendar

select calname, color, fk_cno
from tbl_calendar
where shareemp like '%'|| 'test' ||'%'
        

