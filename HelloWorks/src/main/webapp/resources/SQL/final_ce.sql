------***** finalproject helloworks *****--------


show user;
--USER이(가) "FINALORAUSER3"입니다.


-- 전체테이블 보기 
select * from tab;

-- 사원테이블 보기
select * from tbl_employee

select count(*) from tbl_test

commit;

-- 파이널프로젝트용 출퇴근관리 테이블
create table tbl_attendance
(nowdate            varchar2(30)    not null    -- 일자(데이터타입을 sysdate로 해야하나?)
,fk_empno           varchar2(20)    not null    -- 사원번호(외래키)
,intime             varchar2(20)    not null    -- 출근시간(insert시 항상 들어와야하므로 not null 걸어야하나?)
,outtime            varchar2(20)                -- 퇴근시간(값이 무조건들어가는건 아님 default로 null해줘야하나?)
,totaltime          number                      -- 총 근무시간 이거 null때문에 String처리해야ㅏ나
,status_latein      number          default 0   -- 지각유무(0: 지각안함 1: 지각함)
,status_earlyout    number          default 0   -- 조기퇴근유무(0: 조기퇴근안함 1:조기퇴근함)     

,constraint     PK_tbl_attendance primary key(nowdate, fk_empno)    --복합프라이머리키(일자,사원번호)
,constraint     FK_tbl_attendance foreign key(fk_empno) references tbl_employee(empno) --외래키(사원번호)
)  

-- 출퇴근기록에 출근 값 넣기
insert into tbl_attendance(nowdate, fk_empno,intime, outtime, totaltime, status_latein, status_earlyout)
values( to_char(sysdate,'yyyy-mm-dd'),'202111081004', to_char(sysdate,'hh24:mi:ss'), null, null, default, default );

-- 출퇴근기록에 퇴근 값 넣기
update tbl_attendance set outtime = to_char(sysdate,'hh24:mi:ss'), status_earlyout = 0
where nowdate = to_char(sysdate,'yyyy-mm-dd') and fk_empno = 202111081004;

select * from tbl_attendance

select count(*)
from tbl_attendance
		

-- 당일기록이 존재하는지 아닌지 확인해오기1 (존재시:1 존재하지않으면:0) => 바로 사원번호 없이 아이디로 우회해서 받아오는 방법
select count(*)
from
(
    select A.fk_empno, empid, nowdate, intime, outtime, totaltime, status_latein, status_earlyout
    from tbl_attendance A
    join tbl_employee E
    on A.fk_empno = E.empno
)T
where empid = 'test' and nowdate = to_char(sysdate,'yyyy-mm-dd')

-- 당일기록이 존재하는지 아닌지 확인해오기2 (존재시:1 존재하지않으면:0) => 바로 사원번호로 받아오는 방법 
select count(*)
from tbl_attendance
where fk_empno = '202111081004' and nowdate = to_char(sysdate,'yyyy-mm-dd')

select *
from tbl_attendance

-- 출근컬럼값알아오기1(사원번호로 바로)
select intime
from tbl_attendance
where fk_empno = '202111081004' and nowdate = to_char(sysdate,'yyyy-mm-dd')

-- 출근컬럼값알아오기2(아이디로 우회해서)
select intime
from
(
    select A.fk_empno, empid, nowdate, intime, outtime, totaltime, status_latein, status_earlyout
    from tbl_attendance A
    join tbl_employee E
    on A.fk_empno = E.empno
)T
where empid = 'test' and nowdate = to_char(sysdate,'yyyy-mm-dd')



-- 퇴근컬럼값알아오기1(사원번호로 바로)
select outtime
from tbl_attendance
where fk_empno = '202111081004' and nowdate = to_char(sysdate,'yyyy-mm-dd')

-- 퇴근컬럼값알아오기2(아이디로 우회해서)
select outtime
from
(
    select A.fk_empno, empid, nowdate, intime, outtime, totaltime, status_latein, status_earlyout
    from tbl_attendance A
    join tbl_employee E
    on A.fk_empno = E.empno
)T
where empid = 'test' and nowdate = to_char(sysdate,'yyyy-mm-dd')



--근태관리 횟수 계산하기
select
from tbl_attendance
where empid = 'test' and 



----------11월 9일-------------
select * from tbl_employee

-- 사원아이디로 직원테이블에서레코드 가져오기
select empno, empname, empid, emppw, email, ranking, fk_deptnum, empstatus, empsalary, hiredate, otpkey
from tbl_employee
where empid = 'test'


-- 사원번호로 출퇴근 테이블에서 레코드 가져오기
select nowdate, fk_empno, intime, outtime, totaltime, status_latein, status_earlyout
from tbl_attendance
where fk_empno = '202111081004'


-- 지각확인하는 식( 지각하면 1, 정시출근 0, 조기출근 -1 => 정시출근, 조기출근은 같이 정상출근으로 간주)
select sign( to_date( '15:46:43', 'hh24:mi:ss' ) - to_date( '09:00:00', 'hh24:mi:ss') ) as status_latein
from dual


-- 출퇴근기록에 출근 값 넣기
insert into tbl_attendance(nowdate, fk_empno,intime, outtime, totaltime, status_latein, status_earlyout)
values( '2021-11-06','202111081004', ' 16:03:57', null, null, default, default );

update tbl_attendance set status_latein = '1'
where nowdate = '2021-11-06' and fk_empno = 202111081004;

commit;


select * from tbl_attendance


-- 파이널프로젝트용 야근 테이블
create table tbl_nightshift
(nowdate            varchar2(30)    not null    -- 일자(데이터타입을 sysdate로 해야하나?)
,fk_empno           varchar2(20)    not null    -- 사원번호(외래키)
,outtime         varchar2(20)       not null    -- 퇴근시간(값이 무조건들어가는건 아님 default로 null해줘야하나?)
,addtime            number          not null    -- 추가 근무시간 이거 null때문에 String처리해야ㅏ나
 
,constraint     PK_tbl_nightshift primary key(nowdate, fk_empno)    --복합프라이머리키(일자,사원번호)
,constraint     FK_tbl_nightshift foreign key(fk_empno) references tbl_employee(empno) --외래키(사원번호)
)
--Table TBL_NIGHTSHIFT이(가) 생성되었습니다.

drop table tbl_nightshift purge;
--Table TBL_NIGHTSHIFT이(가) 삭제되었습니다.

select * from tbl_nightshift

-- 야간근무테이블 입력(아직사용x)
insert into tbl_nightshift(nowdate, fk_empno, outtime, addtime)
values( to_char(sysdate,'yyyy-mm-dd'),'202111081004', to_char(sysdate,'hh24:mi:ss'), 5.5 );


-- 퇴근시간 update에 총 근무시간 계산해서 넣기 
update tbl_attendance set totaltime = round( ( to_date( outtime, 'hh24:mi:ss' ) - to_date( intime, 'hh24:mi:ss') ) * 24, 1)
where nowdate = '2021-11-09' and fk_empno = 202111081004;

select * from tbl_attendance


-- 테스트용 레코드 넣기( 다른달, 퇴근 미체크)

insert into tbl_attendance(nowdate, fk_empno,intime, outtime, totaltime, status_latein, status_earlyout)
values( '2021-11-05','202111081004', '08:30:00', '18:00:00', 9.5, 0, 0 );

insert into tbl_attendance(nowdate, fk_empno,intime, outtime, totaltime, status_latein, status_earlyout)
values( '2021-10-01','202111081004', '08:30:00', '18:00:00', 9.5, 0, 0 );

insert into tbl_attendance(nowdate, fk_empno,intime, outtime, totaltime, status_latein, status_earlyout)
values( '2021-10-02','202111081004', '08:30:00', '18:00:00', 9.5, 0, 0 );

insert into tbl_attendance(nowdate, fk_empno,intime, outtime, totaltime, status_latein, status_earlyout)
values( '2021-10-03','202111081004', '08:30:00', null, null, default, default );

commit;


--11월 10일 근태관리 (service에서 paraMap으로 받아오기)

update tbl_attendance set intime = '08:30:00'
where nowdate = '2021-11-10' and fk_empno = 202111081004;

update tbl_attendance set intime = '08:30:00', outtime = null, totaltime = null, status_earlyout= 0
where nowdate = '2021-11-10' and fk_empno = 202111081004;

commit;
select * from tbl_attendance



-- 해당월의 레코드 가져오기 yyyy-mm-dd 11월 7일빼기 
select * from tbl_attendance
where to_char(sysdate,'yyyy-mm') = substr(nowdate, 1,7) and fk_empno = 202111081004;

-- 해당월의 레코드 갯수 가져오기 yyyy-mm-dd (결근에 사용)
select count(*) from tbl_attendance
where to_char(sysdate,'yyyy-mm') = substr(nowdate, 1,7) and fk_empno = 202111081004;

-- 해당월의 지각 횟수 가져오기 
select sum(status_latein),sum(status_earlyout) from tbl_attendance
where to_char(sysdate,'yyyy-mm') = substr(nowdate, 1,7) and fk_empno = 202111081004;

-- 해당월의 조기퇴근 횟수 가져오기 
select sum(status_earlyout) from tbl_attendance
where to_char(sysdate,'yyyy-mm') = substr(nowdate, 1,7) and fk_empno = 202111081004;

-- 해당월의 퇴근미체크 횟수 가져오기 
select count(*) from tbl_attendance
where to_char(sysdate,'yyyy-mm') = substr(nowdate, 1,7) and fk_empno = 202111081004 and outtime is null

-- 해당월의 평일갯수( 공휴일 계산안함,,)
SELECT COUNT(1) CNT
   FROM (SELECT TO_CHAR(SDT + LEVEL - 1, 'YYYYMMDD') DT
              , TO_CHAR(SDT + LEVEL - 1, 'D') D
           FROM (SELECT TO_DATE( to_char(trunc(sysdate,'MM'),'yyyymmdd'), 'YYYYMMDD') SDT
                      , TO_DATE( to_char(last_day(sysdate),'yyyymmdd'), 'YYYYMMDD') EDT
                   FROM DUAL)
         CONNECT BY LEVEL <= EDT - SDT + 1
         ) A
WHERE A.D NOT IN ('1', '7');
  
  
select last_day(sysdate) from dual
select trunc(sysdate,'MM') from dual



-- 해당년도의 레코드 가져오기 yyyy-mm-dd 11월 7일빼기 
select * from tbl_attendance
where to_char(sysdate,'yyyy') = substr(nowdate, 1,4) and fk_empno = 202111081004;

-- 해당년도의 레코드 갯수 가져오기 yyyy-mm-dd (결근에 사용)
select count(*) from tbl_attendance
where to_char(sysdate,'yyyy') = substr(nowdate, 1,4) and fk_empno = 202111081004;

-- 해당년도의 지각 횟수 가져오기 
select sum(status_latein),sum(status_earlyout) from tbl_attendance
where to_char(sysdate,'yyyy') = substr(nowdate, 1,4) and fk_empno = 202111081004;

-- 해당년도의 조기퇴근 횟수 가져오기 
select sum(status_earlyout) from tbl_attendance
where to_char(sysdate,'yyyy') = substr(nowdate, 1,4) and fk_empno = 202111081004;

-- 해당년도의 퇴근미체크 횟수 가져오기 
select count(*) from tbl_attendance
where to_char(sysdate,'yyyy') = substr(nowdate, 1,4) and fk_empno = 202111081004 and outtime is null

-- 해당년도의 평일갯수( 공휴일 계산안함,,)
SELECT COUNT(1) CNT
   FROM (SELECT TO_CHAR(SDT + LEVEL - 1, 'YYYYMMDD') DT
              , TO_CHAR(SDT + LEVEL - 1, 'D') D
           FROM (SELECT TO_DATE( '20'||substr(sysdate, 1,2) || '0131', 'YYYYMMDD') SDT
                      , TO_DATE( '20'||substr(sysdate, 1,2) || '1231', 'YYYYMMDD') EDT
                   FROM DUAL)
         CONNECT BY LEVEL <= EDT - SDT + 1
         ) A
WHERE A.D NOT IN ('1', '7');

-- 해당년도 첫날, 마지막날 만들기 
select substr(sysdate, 1,2) || '1231' from dual

select substr(sysdate, 1,2) || '0131' from dual

-- 입사일이 올해이면 이번 입사일 아니면 올해처음 


-- 11월 11일 
select * from tbl_attendance
update tbl_attendance set intime = '08:30:00', outtime = null, totaltime = null, status_earlyout= 0
where nowdate = '2021-11-10' and fk_empno = 202111081004;

delete from tbl_attendance
where nowdate = '2021-11-15' and fk_empno = 202111101005;

commit;


-- 입사일자 가져오기 
select * from tbl_employee


select empno, empname, empid, emppw, email, ranking, fk_deptnum, empstatus, empsalary, to_char( hiredate,'yy/mm/dd' ) as hiredate , otpkey
from tbl_employee
where empid = 'test'


-- 입사일자로 날짜 만들기
select TO_DATE( '20' || replace('21/11/29','/','') ,'yyyymmdd') from dual

select '20' || replace('21/11/29','/','') from dual


SELECT COUNT(1) CNT
	   	FROM (SELECT TO_CHAR(SDT + LEVEL - 1, 'YYYYMMDD') DT
	              , TO_CHAR(SDT + LEVEL - 1, 'D') D
	           FROM (SELECT TO_DATE( '20' || replace('21/11/29','/','') , 'YYYYMMDD') SDT
	                      , TO_DATE( '20' || substr(sysdate, 1,2) || '1231' , 'YYYYMMDD') EDT
	                   FROM DUAL)
	         CONNECT BY LEVEL <![CDATA[<=]]>  EDT - SDT + 1
	         ) A
		WHERE A.D NOT IN ('1', '7')
        
SELECT COUNT(1) CNT
	   	FROM (SELECT TO_CHAR(SDT + LEVEL - 1, 'YYYYMMDD') DT
	              , TO_CHAR(SDT + LEVEL - 1, 'D') D
	           FROM (SELECT TO_DATE( '20' || replace('21/11/08','/','') , 'YYYYMMDD') SDT
	                      , TO_DATE( '20' || substr(sysdate, 1,2) || '1231' , 'YYYYMMDD') EDT
	                   FROM DUAL)
	         CONNECT BY LEVEL <=  EDT - SDT + 1
	         ) A
WHERE A.D NOT IN ('1', '7')

select count(*) from tbl_attendance
select * from tbl_attendance

select '20' || replace( sysdate ,'/','') from dual


-- 캘린더에 표시할 출퇴근시간, 총근무시간 가져오기(월별) sysdate대신 캘린더 해당 월 가져와야함 

select nowdate, intime, nvl( outtime , '퇴근미체크') as outtime , nvl( totaltime , 0 ) as totaltime  
from tbl_attendance
where fk_empno = '202111081004' and to_char(sysdate,'yyyy-mm') = substr(nowdate, 1,7)


select nowdate, intime, outtime, totaltime  
from tbl_attendance
where fk_empno = '202111081004' and to_char(sysdate,'yyyy-mm') = substr(nowdate, 1,7)


-- 캘린더 db연동할 테이블 만들기
-- title,start
update tbl_attendance set outtime= '23:05:00'
where nowdate = '2021-11-09' and fk_empno = 202111081004;

commit;


-- 11월 15일
select * from tbl_attendance

-- 월별 근무시간( 근무일수, 총 근무시간, 평균근무시간) 근무시간, 평균근무시간 분: 소수점에서 따로 떼서 * 60분으로 보정해줘야함 0.5시간 => 0.5 * 60분 = 30분
select count(nowdate) as 근무일수, sum(nvl(totaltime, 0)) as 근무시간 , round( sum(nvl(totaltime, 0)) / count(nowdate),1 ) as 평균근무시간
from tbl_attendance
where to_char(sysdate,'yyyy-mm') = substr(nowdate, 1,7) and fk_empno = 202111081004; 
--count null값 못받아옴,

-- 월별 부서별 출퇴근시간 조회
select nowdate, intime, nvl( outtime , '퇴근미체크') as outtime , nvl( totaltime , 0 ) as totaltime  
from tbl_attendance
where fk_empno = '202111081004' 

select * from tbl_employee


-- 부서번호가 10인 사원들의 출퇴근시간 조회해오기(부서로만 조회)
select *
from
(
    select fk_empno, fk_deptnum, nowdate, intime, nvl( outtime , '퇴근미체크') as outtime , nvl( totaltime , 0 ) as totaltime
    from tbl_attendance A
    join tbl_employee E
    on e.empno = a.fk_empno
) T
where fk_deptnum = 10 and fk_empno = '202111081004'

-- 사원번호로만 조회해오기 (근데 굳이 이렇게 쓸 필요가 잇을까? 위에꺼 쓰면되지,,)
select *
from
(
    select fk_empno, fk_deptnum, nowdate, intime, nvl( outtime , '퇴근미체크') as outtime , nvl( totaltime , 0 ) as totaltime
    from tbl_attendance A
    join tbl_employee E
    on e.empno = a.fk_empno
) T
where fk_empno = '202111081004'


-- 아직 출퇴근기록이 존재하지 않는 경우('202111101005') null값으로 떨어지므로 보정해줘야함 
select nvl( sum(status_latein), 0) as status_lateincnt from tbl_attendance
where to_char(sysdate,'yyyy') = substr(nowdate, 1,4) and fk_empno = '202111101005'

select count(*) from tbl_attendance
where to_char(sysdate,'yyyy') = substr(nowdate, 1,4) and fk_empno = '202111101005' and outtime is null

select nowdate, intime, outtime, totaltime
from tbl_attendance
where fk_empno = '202111101005'

-- 부서테이블보기
select * from tbl_department
select * from tbl_employee
select * from tbl_attendance

-- 부서정보 가져오기 null값 어떻게 처리할 지 아직 모르겠음
select deptnum, deptname, nvl( fk_empno, '0') as fk_empno
from tbl_department



-- 부서 근무관리: 부서별, 날짜별 
-- 부서번호가 10인 사원들의 출퇴근시간 조회해오기(부서로만 조회)
select  empname, deptnum,fk_empno, nowdate, intime, outtime, status_latein, status_earlyout
from
(
    select fk_empno, empname, fk_deptnum as deptnum , nowdate, intime, nvl( outtime , '퇴근미체크') as outtime , nvl( totaltime , 0 ) as totaltime, status_latein, status_earlyout
    from tbl_attendance A
    join tbl_employee E
    on e.empno = a.fk_empno
) T
where deptnum = 10


-- 부서별 근태통계(월별 지각, 조기퇴근, 결근, 총근무시간 총근무 일수)

-- 해당월의 레코드 갯수 가져오기 yyyy-mm-dd (결근, 근무일수에 사용)
select count(*) 
from
(
    select fk_empno, empname, fk_deptnum as deptnum , nowdate, intime, nvl( outtime , '퇴근미체크') as outtime , nvl( totaltime , 0 ) as totaltime, status_latein, status_earlyout
    from tbl_attendance A
    join tbl_employee E
    on e.empno = a.fk_empno
) T
where to_char(sysdate,'yyyy-mm') = substr(nowdate, 1,7) and deptnum = 10;

-- 해당월의 지각 횟수, 조기 퇴근 횟수, 총 근무시간, 근무날짜 가져오기 (부서에 해당하는 사원의 정보임) 컬럼들 vo에 생성해야함
select nvl(sum(status_latein),0) as sumLate , nvl(sum(status_earlyout), 0) as sumEarly , nvl(sum(totaltime),0) as sumTotal  ,count(empname) as sumworkday
from
(
    select fk_empno, empname, fk_deptnum as deptnum , nowdate, intime, nvl( outtime , '퇴근미체크') as outtime , nvl( totaltime , 0 ) as totaltime, status_latein, status_earlyout
    from tbl_attendance A
    join tbl_employee E
    on e.empno = a.fk_empno
) T
where '2021-11' = substr(nowdate, 1,7) and fk_empno = '202111081004'

select sum(status_latein) as sumLate , nvl(sum(status_earlyout), 0 ) as sumEarly , nvl(sum(totaltime),0) as sumTotal  ,count(empname) as sumworkday
from
(
    select fk_empno, empname, fk_deptnum as deptnum , nowdate, intime, nvl( outtime , '퇴근미체크') as outtime , nvl( totaltime , 0 ) as totaltime, status_latein, status_earlyout
    from tbl_attendance A
    join tbl_employee E
    on e.empno = a.fk_empno
) T
where '2021-08' = substr(nowdate, 1,7) and fk_empno = '202111101005'



-- 해당월의 조기퇴근 횟수 가져오기 
select nvl( sum(status_earlyout) , 0)
from
(
    select fk_empno, empname, fk_deptnum as deptnum , nowdate, intime, nvl( outtime , '퇴근미체크') as outtime , nvl( totaltime , 0 ) as totaltime, status_latein, status_earlyout
    from tbl_attendance A
    join tbl_employee E
    on e.empno = a.fk_empno
) T
where to_char(sysdate,'yyyy-mm') = substr(nowdate, 1,7) and  fk_empno = '202111101005'

-- 해당월의 퇴근미체크 횟수 가져오기 
select count(*) 
from
(
    select fk_empno, empname, fk_deptnum as deptnum , nowdate, intime, outtime , nvl( totaltime , 0 ) as totaltime, status_latein, status_earlyout
    from tbl_attendance A
    join tbl_employee E
    on e.empno = a.fk_empno
) T
where to_char(sysdate,'yyyy-mm') = substr(nowdate, 1,7) and fk_empno = '202111101005' and outtime is null


-- 해당월의 평일갯수( 공휴일 계산안함,,)
SELECT COUNT(1) CNT
   FROM (SELECT TO_CHAR(SDT + LEVEL - 1, 'YYYYMMDD') DT
              , TO_CHAR(SDT + LEVEL - 1, 'D') D
           FROM (SELECT TO_DATE( to_char(trunc(sysdate,'MM'),'yyyymmdd'), 'YYYYMMDD') SDT
                      , TO_DATE( to_char(last_day(sysdate),'yyyymmdd'), 'YYYYMMDD') EDT
                   FROM DUAL)
         CONNECT BY LEVEL <= EDT - SDT + 1
         ) A
WHERE A.D NOT IN ('1', '7');


-- 부서별 사원정보 가져오기 
select empno, empname, empid, ranking, fk_deptnum, empstatus, hiredate
from tbl_employee
where fk_deptnum = 10

select * from tbl_employee


-- 해당월의 평일갯수
SELECT COUNT(1) CNT
   FROM (SELECT TO_CHAR(SDT + LEVEL - 1, 'YYYYMMDD') DT
              , TO_CHAR(SDT + LEVEL - 1, 'D') D
           FROM (SELECT TO_DATE( to_char(trunc(sysdate,'MM'),'yyyymmdd'), 'YYYYMMDD') SDT
                      , TO_DATE( to_char(last_day(sysdate),'yyyymmdd'), 'YYYYMMDD') EDT
                   FROM DUAL)
         CONNECT BY LEVEL <= EDT - SDT + 1
         ) A
WHERE A.D NOT IN ('1', '7');

-- 입사일 고려했을때 평일 갯수
SELECT COUNT(1) CNT
   FROM (SELECT TO_CHAR(SDT + LEVEL - 1, 'YYYYMMDD') DT
              , TO_CHAR(SDT + LEVEL - 1, 'D') D
           FROM (SELECT TO_DATE( '20' || replace('21/11/08','/','') , 'YYYYMMDD') SDT
                      , TO_DATE( to_char(last_day(sysdate),'yyyymmdd'), 'YYYYMMDD') EDT
                   FROM DUAL)
         CONNECT BY LEVEL <= EDT - SDT + 1
         ) A
WHERE A.D NOT IN ('1', '7');


select trunc(sysdate,'MM')
from dual

select hiredate 
from tbl_employee

select * from tbl_attendance
select * from tbl_department


-- 11월 19일 부서별, 날짜별 계산 yyyymmdd형태로 들어옴 
SELECT COUNT(1) CNT
   FROM (SELECT TO_CHAR(SDT + LEVEL - 1, 'YYYYMMDD') DT
              , TO_CHAR(SDT + LEVEL - 1, 'D') D
           FROM (SELECT TO_DATE(  '20211101', 'YYYYMMDD') SDT
                      , TO_DATE( to_char( last_day( to_date( '20211101', 'YYYYMMDD') ),'yyyymmdd'), 'YYYYMMDD') EDT
                   FROM DUAL)
         CONNECT BY LEVEL <= EDT - SDT + 1
         ) A
WHERE A.D NOT IN ('1', '7')




select to_char (last_day ( to_date( '20211101', 'YYYYMMDD') ),'yyyymmdd') from dual

select TO_DATE( to_char (last_day ( to_date( '20211101', 'YYYYMMDD') ),'yyyymmdd'), 'YYYYMMDD') from dual
