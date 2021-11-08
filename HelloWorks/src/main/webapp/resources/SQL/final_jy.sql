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
  empno varchar2(200) not null     -- 사원번호   to_char(sysdate, ‘yyyy’) || to_char(empnoSeq.nextval)
, empname varchar2(20) not null    -- 사원이름
, empid varchar2(20) not null      -- ID
, emppw varchar2(30) not null      -- PW
, email varchar2(30) not null      -- 아이디@helloworks.com
, ranking number default 1         -- 사원(1), 대리(2), 부장(3), 팀장(4),  사장(5)
, fk_deptnum varchar2(10)          -- 부서번호
, empstatus number(1) default 1    -- (1: 재직, 0: 퇴사, 2:휴직) 
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
, fk_empno number not null -- 관리자 사원번호 
constraint FK_tbl_emp_empno foreign key(fk_empno) references tbl_employee(empno) on delete set null 
);


select *
from tbl_department;



[기안(문서) 테이블] tbl_document
create table tbl_document
( 
  doument_seq      number      not null           -- 문서번호    to_char(sysdate, ‘yyyy’) || to_char(empnoSeq.nextval)
, fk_empno varchar2(200) not null                 -- 사원번호
, fk_deptnum varchar2(10)                         -- 부서번호
, name          varchar2(20)          not null    -- 글쓴이 
, subject       Nvarchar2(200)        not null    -- 문서제목
, content       Nvarchar2(2000)       not null    -- 문서내용   -- clob (최대 4GB까지 허용) 
, regDate       date default sysdate  not null    -- 문서기안시간
, status        number(1)    not null             -- 문서종류   1:연차,  2:지출결의서, 3:품의서, 4:업무협조요청                                             
, fileName       varchar2(255)                    -- WAS(톰캣)에 저장될 파일명(2021110809271535243254235235234.png)                                       
, orgFilename    varchar2(255)                    -- 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
, fileSize       number                           -- 파일크기  
,constraint PK_tbl_document_seq primary key(doument_seq)
,constraint FK_tbl_document_fk_empno foreign key(fk_empno) references tbl_employee(empno)
,constraint FK_tbl_document_fk_deptnum foreign key(fk_deptnum) references tbl_department(deptnum)
,constraint CK_tbl_document_status check(status in(1,2,3,4))
);
-- Table TBL_DOCUMENT이(가) 생성되었습니다.


create sequence documentSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
-- Sequence DOCUMENTSEQ이(가) 생성되었습니다.



select *
from TBL_DOCUMENT;



