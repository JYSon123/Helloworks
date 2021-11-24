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
d

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
,notice     varchar2(100)           -- 알림(문자,메일)
,constraint PK_tbl_schedule_sno primary key(sno)
,constraint FK_tbl_schedule_calno foreign key(fk_calno) references tbl_calendar(calno) on delete cascade;
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

select *
from user_constraints
where table_name = 'TBL_SCHEDULE'

select calname 
from tbl_calendar

update tbl_calendar set calname= '공유캘린더' , color= '#ffefcc', shareEmp='test,hje0121'
		where calno= 2
        rollback;

select calname, color, fk_cno
from tbl_calendar
where shareemp like '%'|| 'test' ||'%'
        
select TITLE, STARTDATE, ENDDATE, COLOR
from tbl_calendar C JOIN tbl_schedule S
ON C.CALNO = S.FK_CALNO
where SHAREEMP like '%'|| 'hje0121' ||'%'

update tbl_calendar set calname='정은캘린더' , color='#284B91'
where calno='1'

rollback;

delete from tbl_calendar
		where calno= #{calno}

delete from tbl_calendar
where calno=4 and fk_cno = 2

rollback;

select to_date(substr(startDate,0,10),'yyyy-mm-dd') as startDate, to_date(substr(endDate,0,10),'yyyy-mm-dd') as endDate
from tbl_schedule
where startDate <= sysdate;


select title, startdate, enddate, color
    from tbl_calendar C JOIN tbl_schedule S
    ON C.CALNO = S.FK_CALNO
    where SHAREEMP like '%'|| 'test' ||'%'
and (to_date(substr(startDate,0,10),'yyyy-mm-dd') between '2021-11-18' and '2021-11-19'
or to_date(substr(endDate,0,10),'yyyy-mm-dd') between '2021-11-18' and '2021-11-19')

		
			and title like '%'|| '캘린더' ||'%'
            
            select empname, empid, deptname
		from tbl_employee E JOIN tbl_department D
		on E.fk_deptnum = D.deptnum
		where empname = '테스트'
        

select title, startDate, endDate, calName, shareEmp, color, sno, content, location, status
from        
(
    select row_number() over (order by sno desc) AS rno,
           title, startDate, endDate, calName, shareEmp, color, sno, content, location, status
    from tbl_calendar C JOIN tbl_schedule S
    ON C.CALNO = S.FK_CALNO
    where SHAREEMP like '%'|| 'test' ||'%'
    
    and lower(title) like '%'|| lower('') ||'%' 
)
where rno between 1 and 5

--and to_date(substr(startDate,0,10),'yyyy-mm-dd') between '2021-11-01' and '2021-11-23'
--or to_date(substr(endDate,0,10),'yyyy-mm-dd') between '2021-11-01' and '2021-11-23'
order by enddate desc

select * from tbl_schedule

insert into tbl_schedule (sno, fk_calno, title, startdate, enddate, status)
values (snoSeq.nextval, 1,'페이징처리테스트16','2021-11-22T18:30:00','2021-11-22T22:00:00',0);
insert into tbl_schedule (sno, fk_calno, title, startdate, enddate, status)
values (snoSeq.nextval, 1,'페이징처리테스트17','2021-11-22T18:30:00','2021-11-22T22:00:00',0);
insert into tbl_schedule (sno, fk_calno, title, startdate, enddate, status)
values (snoSeq.nextval, 1,'페이징처리테스트18','2021-11-22T18:30:00','2021-11-22T22:00:00',0);
insert into tbl_schedule (sno, fk_calno, title, startdate, enddate, status)
values (snoSeq.nextval, 1,'페이징처리테스트19','2021-11-22T18:30:00','2021-11-22T22:00:00',0);
insert into tbl_schedule (sno, fk_calno, title, startdate, enddate, status)
values (snoSeq.nextval, 1,'페이징처리테스트20','2021-11-22T18:30:00','2021-11-22T22:00:00',0);
insert into tbl_schedule (sno, fk_calno, title, startdate, enddate, status)
values (snoSeq.nextval, 1,'페이징처리테스트21','2021-11-22T18:30:00','2021-11-22T22:00:00',0);
       
       commit
       
       
select title, content, location, startdate, enddate, shareemp
from tbl_schedule S JOIN tbl_calendar C
ON S.FK_CALNO = c.CALNO
where notice like '%' || 'email' and substr(startdate,1,10) = to_char(sysdate+1, 'yyyy-mm-dd')

select * from tbl_employee

update tbl_employee set email='test@helloworks.com'
where empid='test'
commit
up

select email
from tbl_employee
where empid in (test, ceo)

select count(*)
from tbl_calendar
where calname='공유 캘린더2' and shareemp like '%'|| 'test' || '%'

