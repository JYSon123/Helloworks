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
, fk_deptnum varchar2(10) -- 부서번호
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
, fk_empno number not null -- 관리자 사원번호 
constraint FK_tbl_emp_empno foreign key(fk_empno) references tbl_employee(empno) on delete set null 
);


select *
from tbl_department;

------------------ 게시판 만들기-------------------

-- *** 게시판 테이블 (댓글, 답변글, 파일첨부 기능) *** --
create table tbl_board
(seq           number                not null    -- 글번호
,fk_empno      varchar2(200)         not null    -- 사원번호
,name          varchar2(20)          not null    -- 글쓴이 
,subject       Nvarchar2(200)        not null    -- 글제목
,content       Nvarchar2(2000)       not null    -- 글내용   -- clob (최대 4GB까지 허용) 
,pw            varchar2(20)          not null    -- 글암호
,readCount     number default 0      not null    -- 글조회수
,regDate       date default sysdate  not null    -- 글쓴시간
,status        number(1) default 1   not null    -- 글삭제여부   1:사용가능한 글,  0:삭제된 글
,commentCount  number default 0      not null    -- 댓글의 개수

,groupno       number                not null    -- 답변글쓰기에 있어서 그룹번호 
                                                 -- 원글(부모글)과 답변글은 동일한 groupno 를 가진다.
                                                 -- 답변글이 아닌 원글(부모글)인 경우 groupno 의 값은 groupno 컬럼의 최대값(max)+1 로 한다.

,fk_seq         number default 0      not null   -- fk_seq 컬럼은 절대로 foreign key가 아니다.!!!!!!
                                                 -- fk_seq 컬럼은 자신의 글(답변글)에 있어서 
                                                 -- 원글(부모글)이 누구인지에 대한 정보값이다.
                                                 -- 답변글쓰기에 있어서 답변글이라면 fk_seq 컬럼의 값은 
                                                 -- 원글(부모글)의 seq 컬럼의 값을 가지게 되며,
                                                 -- 답변글이 아닌 원글일 경우 0 을 가지도록 한다.

,depthno        number default 0       not null  -- 답변글쓰기에 있어서 답변글 이라면
                                                 -- 원글(부모글)의 depthno + 1 을 가지게 되며,
                                                 -- 답변글이 아닌 원글일 경우 0 을 가지도록 한다.

,fileName       varchar2(255)                    -- WAS(톰캣)에 저장될 파일명(2021110809271535243254235235234.png)                                       
,orgFilename    varchar2(255)                    -- 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
,fileSize       number                           -- 파일크기  

,constraint PK_tbl_board_seq primary key(seq)
,constraint FK_tbl_board_fk_empno foreign key(fk_empno) references tbl_employee(empno)
,constraint CK_tbl_board_status check( status in(0,1) )
);
-- Table TBL_BOARD이(가) 생성되었습니다.

create sequence boardSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
-- Sequence BOARDSEQ이(가) 생성되었습니다.

select *
from tbl_board;


----- **** 댓글 테이블 생성 **** -----
create table tbl_comment
(seq           number               not null   -- 댓글번호
,fk_empno      varchar2(200)        not null  -- 사원번호
,name          varchar2(20)         not null   -- 성명
,content       varchar2(1000)       not null   -- 댓글내용
,regDate       date default sysdate not null   -- 작성일자
,parentSeq     number               not null   -- 원게시물 글번호
,status        number(1) default 1  not null   -- 댓글삭제여부
                                               -- 0 : 삭제된 글, 1 : 사용가능한 글
                                               -- 댓글은 원글이 삭제되면 자동적으로 삭제되어야 한다.
,constraint PK_tbl_comment_seq primary key(seq)
,constraint FK_tbl_comment_fk_empno foreign key(fk_empno) references tbl_employee(empno)
,constraint FK_tbl_comment_parentSeq foreign key(parentSeq) references tbl_board(seq) on delete cascade
,constraint CK_tbl_comment_status check( status in(1,0) ) 
);
-- Table TBL_COMMENT이(가) 생성되었습니다.

create sequence commentSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

select *
from tbl_employee;



insert into tbl_board(seq, fk_empno, name, subject, content, pw, readCount, regDate, status, groupno, fk_seq, depthno)
values(boardSeq.nextval, '202111081004', '사용자', '테스트글입니다', '안녕하세요? 테스트입니다.', '1234', default, default, default, 0, default, default);
-- 1 행 이(가) 삽입되었습니다.
-- 커밋 완료.
select *
from tbl_board;

commit;

select *
from tbl_employee;

select nvl(max(groupno), 0)
		from tbl_board


select *
from tbl_board;
<th style="width: 70px;">글번호</th>
			<th style="width: 200px;">제목</th>
			<th style="width: 130px;">성명</th>
			<th style="width: 100px;">날짜</th>
			<th style="width: 70px;">조회수</th>
            
select seq, fk_empno, name, subject, readcount, to_char(regDate, 'yyyy-mm-dd hh24:mi:ss') as regDate,
    commentCount
from tbl_board
where status = 1
order by seq desc;

select *
from tbl_board;



begin
    for i in 1..20 loop
        insert into tbl_board(seq, fk_empno, name, subject, content, pw, readCount, regDate, status, groupno)
        values(boardSeq.nextval, '202111081004', '테스트', '테스트글입니다'||i, '안녕하세요'|| i ||' 테스트글입니다.', '1234', default, default, default, i);
    end loop;
end;
commit;
rollback;

select *
from tbl_board;

select count(*)
from tbl_board
where status = 1
and lower('글제목') like '%'||lower('테스트')||'%';

select *
from tbl_board;

select *
from tbl_comment;


select empname, empid,email, empno, hiredate, ranking, fk_deptnum, empstatus
from tbl_employee


update tbl_employee set ranking = '1'
                      , fk_deptnum = '10'
                      , empstatus = '1'
where empno = '202111081004';

rollback;
commit;

select empname, empid,email, empno, hiredate, ranking, fk_deptnum, empstatus
from tbl_employee
where empno = '202111081004';

select *
from tbl_employee;

select empname, empid,email, noticeemail, empno, hiredate, ranking, fk_deptnum, empstatus
from tbl_employee
where empno =  '202111081004';


select *
from tbl_board;

update tbl_board set fk_empno = '10'
where seq = '34';


