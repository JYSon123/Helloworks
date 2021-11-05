----- final -----

show user;

create user FINALORAUSER3 identified by cclass;
-- User FINALORAUSER3이(가) 생성되었습니다.

grant connect, resource, create view, unlimited tablespace to FINALORAUSER3;
-- Grant을(를) 성공했습니다.

show user;
-- USER이(가) "FINALORAUSER3"입니다.



create table tbl_test
(
userid   varchar2(20)          not null    -- 사용자ID
,name    varchar2(20)          not null    -- 글쓴이 
);


insert into tbl_test(userid, name) values('leess', '이순신');  
commit;

insert into tbl_test(userid, name) values('eomjh', '엄정화');  
commit;

insert into tbl_test(userid, name) values('test', '연결완료!!');  
commit;

select *
from tbl_employee;



[직원테이블] TBL_EMPLOYEE

create table tbl_employee
(
  empno varchar2(200) not null  -- to_char(sysdate, ‘yyyy’) || to_char(empnoSeq.nextval)
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
-- , constraint FK_tbl_member_deptnum foreign key(fk_deptnum) REFERENCES tbl_dept(deptnum)
, constraint CK_tbl_emp_rank check(ranking in (1,2,3,4,5))
, constraint CK_tbl_emp_empstatus check(empstatus in (0,1,2))
);



create sequence empnoSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


부서번호
10: 인사팀
20: 회계팀
30: 총무팀
40: 마케팅팀
50: 영업팀
60: 대표 



[부서테이블] tbl_department
(
  deptnum varchar2 not null -- 부서번호 
, deptname varchar2(10) not null -- 부서명   
, fk_empno number not null -- 부장 사원번호 
constraint FK_tbl_emp_empno foreign key(fk_empno) references tbl_employee(empno) on delete set null 
);


select *
from tbl_department;
