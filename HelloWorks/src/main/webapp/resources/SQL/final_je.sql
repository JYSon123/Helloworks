select *
from tab;

desc tbl_department

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
