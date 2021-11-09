set hidden param parseThreshold = 150000;

show user; -- USER이(가) "FINALORAUSER3"입니다.

select * from tab;

desc tbl_employee;

create table tbl_department
(deptnum varchar2(10) not null -- 부서번호
,deptname varchar2(10) not null -- 부서명
,fk_empno varchar2(200) -- 부장 사원번호
,constraint FK_tbl_emp_empno foreign key(fk_empno) references tbl_employee(empno) on delete set null);

insert into tbl_department(deptnum, deptname)
values('10', '인사');

insert into tbl_department(deptnum, deptname)
values('20', '회계');

insert into tbl_department(deptnum, deptname)
values('30', '총무');

insert into tbl_department(deptnum, deptname)
values('40', '마케팅');

insert into tbl_department(deptnum, deptname)
values('50', '영업');

insert into tbl_department(deptnum, deptname)
values('00', '대표');

commit;

select * from tbl_department;

alter table tbl_department add constraint PK_tbl_department_deptnum primary key(deptnum);

select * from tbl_employee;

desc tbl_employee;

--------------------------------------------------------------------------------

-- OTP 개인키(비밀키) 컬럼 추가
alter table tbl_employee add otpkey varchar2(1000);

-- 암호화를 위해 컬럼 데이터 타입 변경
alter table tbl_employee modify emppw varchar2(500);

-- 아이디 후보키로 사용
alter table tbl_employee add constraint UQ_tbl_employee_empid unique(empid);

insert into tbl_employee(empno, empname, empid, emppw, email, empsalary)
values('202111081004','테스트','test','qwer1234$','test@helloworks.com',1004);


update tbl_employee set emppw = '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382' where empid='test';

commit;

select empno, empname, empid, emppw, email, ranking, fk_deptnum
         , empstatus, empsalary, hiredate, otpkey, lastlogingap
from
(
    select empno, empname, empid, emppw, email, ranking, fk_deptnum
         , empstatus, empsalary, to_char(hiredate, 'yyyy-mm-dd') AS hiredate, otpkey
    from tbl_employee
    where empstatus != 0 and empid = 'test' and emppw = '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382'
) E
cross join
(
    select trunc(months_between(sysdate, max(logindate))) AS lastlogingap
    from tbl_loginhistory
    where fk_empid = 'test'
) H;
---------------------------------------------------------------------

--->> 로그인 히스토리(IP추적, 최종활동일자 등) <<---
create table tbl_loginhistory
(fk_empid   varchar2(20) not null           -- 직원아이디
,logindate   date default sysdate not null  -- 로그인일자
,clientip    varchar2(20) not null          -- 접속IP
,constraint FK_tbl_loginhistory foreign key(fk_empid) 
                                references tbl_employee(empid) on delete cascade
);

select * from tbl_loginhistory;

delete from tbl_loginhistory;

commit;

drop table tbl_loginhistory;

update tbl_employee set otpkey = null where empid='test';

commit;