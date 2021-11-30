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

commit;

select to_char(sysdate, 'yyyymmdd') || 100 || to_char(empnoSeq.nextval)
from dual;

[직원테이블] TBL_EMPLOYEE

create table tbl_employee
(
  empno varchar2(200) not null     -- 사원번호   to_char(sysdate, ‘yyyymmdd’) || to_char(empnoSeq.nextval)
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

noticeemail

 delete from tbl_employee
 where empno = '202111241009';

update tbl_employee set fk_deptnum = 10
where empid = 'test';
commit;


INSERT INTO tbl_employee(empno, empname, empid, emppw, email, ranking, fk_deptnum, empstatus, empsalary, hiredate)
values(to_char(sysdate, 'yyyymmdd') || '100' || to_char(empnoSeq.nextval),'나영업', 'sales', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382',  ||'@helloworks.com', '2', '50', '1', '2004', to_char(sysdate, 'yyyy/mm/dd')  );

commit;

select to_char(sysdate, 'yyyy/mm/dd')
from dual;



create sequence empnoSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


부서번호
10: 인사팀(경영지원팀)
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

select *
from tbl_document


[기안(문서) 테이블] tbl_document
create table tbl_document
( 
  doument_seq   number      not null           -- 문서번호    to_char(sysdate, ‘yyyy’) || to_char(documentSeq.nextval)
, fk_empno      varchar2(200) not null                 -- 사원번호
, fk_deptnum    varchar2(10)                         -- 부서번호
, name          varchar2(20)          not null    -- 글쓴이 
, subject       Nvarchar2(200)        not null    -- 문서제목
, content       Nvarchar2(2000)       not null    -- 문서내용   -- clob (최대 4GB까지 허용) 
, regDate       date default sysdate  not null    -- 문서기안시간
, status        number(1)    not null             -- 문서종류   1:연차,  2:지출결의서, 3:품의서, 4:업무협조요청  
, result         number default 0                  -- 결과, 0대기, 1승인, 2 반려
, fileName      varchar2(255)                    -- WAS(톰캣)에 저장될 파일명(2021110809271535243254235235234.png)                                       
, orgFilename   varchar2(255)                    -- 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
, fileSize      number                           -- 파일크기  

, breakstart   date      -- 연차시작날짜
, breakend     date      -- 연차 끝나는 날짜
, breakkind    number    -- 연차 종류(반차 등등)

,constraint PK_tbl_document_seq primary key(doument_seq)
,constraint FK_tbl_document_fk_empno foreign key(fk_empno) references tbl_employee(empno)
,constraint FK_tbl_document_fk_deptnum foreign key(fk_deptnum) references tbl_department(deptnum)
,constraint CK_tbl_document_status check(status in(1,2,3,4))
);
-- Table TBL_DOCUMENT이(가) 생성되었습니다.


update tbl_document set result = '0'
	      where doument_seq = '2021111718'
commit;

rollback;

    



alter table tbl_document add breakstart date;

alter table tbl_document add breakend date;

alter table tbl_document add breakkind number;

commit;


alter table tbl_document drop CK_tbl_document_status check;


alter table tbl_document -- DDL문 이기 때문에 자동으로 commit 이 된다.
          drop constraint CK_tbl_document_result;


create sequence documentSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
-- Sequence DOCUMENTSEQ이(가) 생성되었습니다.



rollback;

commit;



------------XXX 못함 --------------
[휴가, 연차 테이블] tbl_break

create table tbl_break
( 
, fk_empno      varchar2(200)         not null    -- 사원번호
, fk_deptnum    varchar2(10)          not null    -- 부서번호
, useDate       varchar2(20)                      -- 연차사용날짜
, subject       varchar2(20)          not null    -- 연차유형 1:연차 2:반차 3:포상휴가 4:경조사 5:기타
, content       Nvarchar2(2000)                   -- 연차사용사유   -- clob (최대 4GB까지 허용) 
, fileName      varchar2(255)                     -- WAS(톰캣)에 저장될 파일명(2021110809271535243254235235234.png)                                       
, orgFilename   varchar2(255)                     -- 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
, fileSize      number                            -- 파일크기   ===== 여기까지가 연차 기안 폼 =====
, regDate       date default sysdate  not null    -- 연차기안시간 (생성, 사용, 기안 모두 다 )
, preason varchar2(255)-- 연차발생 사유
, mreason varchar2(255)-- 연차차감 사유
, breakCount not null -- 연차개수 (남은연차 count용), 사용이면 -1, 생성이면 1 식으로..?
, result        number default 0                  -- 결과, 0발생, 1사용, 2기안중 (select 용으로)

,constraint FK_tbl_document_fk_empno foreign key(fk_empno) references tbl_employee(empno)
,constraint FK_tbl_document_fk_deptnum foreign key(fk_deptnum) references tbl_department(deptnum)
);




select *
from TBL_DOCUMENT;


SELECT * 
FROM ALL_TAB_COLUMNS
WHERE TABLE_NAME = 'TBL_DOCUMENT'


select count(*)
from TBL_DOCUMENT
where  status = 1 and result = 0;
      
select count(*)
	    from TBL_DOCUMENT


select doument_seq, fk_empno, fk_deptnum, name, subject, content, to_char(regDate, 'yyyy-mm-dd hh24:mi:ss' ), status, result, filename
from TBL_DOCUMENT


-- select 태그를 건들지 않았을 경우 (맨 처음 보이는 페이지)
select doument_seq, fk_empno, fk_deptnum, name, subject, content, regDate, status, result, filename
from 
(
    select row_number() over(order by doument_seq desc) AS rno, doument_seq, fk_empno, fk_deptnum, name, subject, content, to_char(regDate, 'yyyy-mm-dd hh24:mi:ss' ) AS regDate, status, result, filename
    from TBL_DOCUMENT
)V

where rno between 1 and 5



-- select 태그를 변경을 한 경우 (둘 다 조건이 있을 경우)
select doument_seq, fk_empno, fk_deptnum, name, subject, content, regDate, status, result, filename
from 
(
    select row_number() over(order by doument_seq desc) AS rno, doument_seq, fk_empno, fk_deptnum, name, subject, content, to_char(regDate, 'yyyy-mm-dd hh24:mi:ss' ) AS regDate, status, result, filename
    from TBL_DOCUMENT
    where status = '1' and result = '0'
)V
where rno between 1 and 5



-- select 태그를 변경을 한 경우 (문서 종류만 선택을 한 경우)
select doument_seq, fk_empno, fk_deptnum, name, subject, content, regDate, status, result, filename
from 
(
    select row_number() over(order by doument_seq desc) AS rno, doument_seq, fk_empno, fk_deptnum, name, subject, content, to_char(regDate, 'yyyy-mm-dd hh24:mi:ss' ) AS regDate, status, result, filename
    from TBL_DOCUMENT
    where status = '3'
)V
where rno between 1 and 5



-- select 태그를 변경을 한 경우 (결과만 선택을 한 경우)
select doument_seq, fk_empno, fk_deptnum, name, subject, content, regDate, status, result, filename
from 
(
    select row_number() over(order by doument_seq desc) AS rno, doument_seq, fk_empno, fk_deptnum, name, subject, content, to_char(regDate, 'yyyy-mm-dd hh24:mi:ss' ) AS regDate, status, result, filename
    from TBL_DOCUMENT
    where result = '0'
)V
where rno between 1 and 5

select *
from tbl_document


update TBL_DOCUMENT set result = '1'
where doument_seq = 2021111013;

update TBL_DOCUMENT set result = '2'
where doument_seq = 202111095;

commit;


select doument_seq, status, result, fk_deptnum, name, subject, content, regDate, filename
from tbl_document
where doument_seq = 202111095;


select *
from tbl_breakcalendar

create table tbl_breakcalendar(
  fk_empno      varchar2(200)   not null  -- 사원번호
, title         varchar2(50)    not null  -- 연차종류 (연차,반차,포상휴가)
, start1        date            not null  -- 시작날짜
, end1          date            not null  -- 끝나는날짜
,constraint FK_tbl_breakcalendar_fk_empno foreign key(fk_empno) references tbl_employee(empno)
);

INSERT INTO tbl_breakcalendar values('202111081004','반차',to_date('2021-11-22','YYYY/MM/DD'), to_date('2021-11-23','YYYY/MM/DD'));

commit;
rollback;

INSERT INTO tbl_breakcalendar values('202111081004','연차',to_date('2021/11/29','YYYY/MM/DD'), to_date('2021/11/30','YYYY/MM/DD'));
commit;

INSERT INTO tbl_breakcalendar values('202111081004','포상휴가',to_date('2021/12/10','YYYY/MM/DD'), to_date('2021/12/15','YYYY/MM/DD'));
commit;

INSERT INTO tbl_breakcalendar values('202111081004','테스트용',to_date('2021/11/23','YYYY/MM/DD'), to_date('2021/11/23','YYYY/MM/DD'));


INSERT INTO tbl_breakcalendar values('202111081004','테스트용2',to_date('2021/11/23','YYYY/MM/DD'), to_date('2021/11/25','YYYY/MM/DD'));
commit;


INSERT INTO tbl_breakcalendar values('202111081004','날짜 테스트중2222',to_date('2021-12-10','YYYY/MM/DD') , (to_date('2021-12-15','YYYY/MM/DD')+ (INTERVAL '1' DAY)) );

    delete from tbl_breakcalendar
    where title = '테스트용2';
    commit;
    
    
select *
from tbl_breakcalendar


rollback;

select *
from tbl_breakcalendar
rollback;


select SYSDATE + (INTERVAL '1' DAY)        --1일 더하기
from dual


select  title, to_char(start1,'YYYY-MM-DD') AS start1 ,  to_char(end1,'YYYY-MM-DD') AS end1
from tbl_breakcalendar
where fk_empno = '202111081004';


select doument_seq, status, result, fk_deptnum, name, subject, content, to_char(regDate, 'yyyy-mm-dd hh24:mi:ss' ) AS regDate, filename, to_char(breakstart,'YYYY-MM-DD') AS breakstart, to_char(breakend,'YYYY-MM-DD') AS breakend,breakkind 
from tbl_document
where doument_seq = '2021111617'



	     select doument_seq, fk_empno, fk_deptnum, name, subject, content, regDate, status, result, filename
			from 
			(
			    select row_number() over(order by doument_seq desc) AS rno, doument_seq, fk_empno, fk_deptnum, name, subject, content, to_char(regDate, 'yyyy-mm-dd hh24:mi:ss' ) AS regDate, status, result, filename
			    from TBL_DOCUMENT
			    where status = ${searchType} and fk_empno = ${fk_empno}
			) V
	  	 where rno between #{startRno} and #{endRno}


select doument_seq, fk_empno, fk_deptnum, name, subject, content, regDate, status, result, filename
			from 
			(
			    select row_number() over(order by doument_seq desc) AS rno, doument_seq, fk_empno, fk_deptnum, name, subject, content, to_char(regDate, 'yyyy-mm-dd hh24:mi:ss' ) AS regDate, status, result, filename
			    from TBL_DOCUMENT
                where status = '1' and result = '2' and fK_empno = '202111081004'
			) V
where rno between 1 and 2