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

insert into tbl_employee(empno, empname, empid, emppw, email, ranking, fk_deptnum, empsalary)
values('202111101005','나회계','account','9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382','account@helloworks.com', 3, '20',1004);

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

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

-- 세금계산서
create table tbl_billtax
(billtax_seq    number          not null
,customer_id    varchar2(200)   not null
,customer_comp  varchar2(1000)
,customer_name  varchar2(200)
,customer_addr  varchar2(1000)
,regdate        date default sysdate not null
,totalprice     number           not null
,taxprice       number           not null
,status         number default 0 not null -- 0:발급전, 1:승인완료, 2:발급/국세청전송완료
,edit           number default 0 not null -- 0:수정X, 1:수정세금계산서
,empid       varchar2(20)        not null -- 작성한 직원아이디(발급 담당자)
,empname        varchar2(20)     not null
,constraint PK_tbl_billtax primary key(billtax_seq)
,constraint CK_tbl_billtax_status check(status in(0,1,2))
);

alter table tbl_billtax drop column customer_email;

alter table tbl_billtax add mycompany_id varchar2(200) not null;
alter table tbl_billtax add mycompany_comp  varchar2(1000);
alter table tbl_billtax add mycompany_name  varchar2(200);
alter table tbl_billtax add mycompany_addr  varchar2(1000);

alter table tbl_billtax add payment varchar2(20); -- 영수, 청구
alter table tbl_billtax add constraint CK_tbl_billtax_payment check (payment in('영수','청구'));

select * from tbl_billtax;
select * from tbl_billtax_detail;

-- 세금계산서상세
create table tbl_billtax_detail
(billtax_detail_seq     number      not null
,fk_billtax_seq         number      not null
,selldate               date        not null
,sellprod               varchar2(500) not null
,sellamount             number
,selloneprice           number      not null
,selltotalprice         number      not null
,selltax                number      not null
,constraint PK_tbl_billtax_detail primary key(billtax_detail_seq)
,constraint FK_fk_billtax_seq foreign key(fk_billtax_seq) references tbl_billtax(billtax_seq) on delete cascade
);

alter table tbl_billtax_detail modify sellamount default 1;

-- 세금계산서시퀀스
create sequence billtax_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

-- 세금계산서상세시퀀스
create sequence billtax_detail_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

------------------------------------------

-- 계산서
create table tbl_billnotax
(billnotax_seq    number          not null
,customer_id    varchar2(200)   not null
,customer_comp  varchar2(1000)
,customer_name  varchar2(200)
,customer_addr  varchar2(1000)
,customer_email varchar2(1000) -- 거래처 이메일(메일발송용도)
,regdate        date default sysdate not null
,totalprice     number           not null
,status         number default 0 not null -- 0:발급전, 1:승인완료, 2:발급/국세청전송
,edit           number default 0 not null -- 0:수정X, 1:수정계산서
,empid       varchar2(20)        not null -- 작성한 직원아이디(발급 담당자)
,empname        varchar2(20)     not null
,constraint PK_tbl_billnotax primary key(billnotax_seq)
,constraint CK_tbl_billnotax_status check(status in(0,1,2))
);


alter table tbl_billnotax drop column customer_email;

select * from tbl_billnotax;
select * from tbl_billnotax_detail;

alter table tbl_billnotax add mycompany_id varchar2(200) not null;
alter table tbl_billnotax add mycompany_comp  varchar2(1000);
alter table tbl_billnotax add mycompany_name  varchar2(200);
alter table tbl_billnotax add mycompany_addr  varchar2(1000);

alter table tbl_billnotax add payment varchar2(20); -- 영수, 청구
alter table tbl_billnotax add constraint CK_tbl_billnotax_payment check (payment in('영수','청구'));


-- 계산서 상세
create table tbl_billnotax_detail
(billnotax_detail_seq     number      not null
,fk_billnotax_seq         number      not null
,selldate               date        not null
,sellprod               varchar2(500) not null
,sellamount             number default 1
,selloneprice           number      not null
,selltotalprice         number      not null
,constraint PK_tbl_billnotax_detail primary key(billnotax_detail_seq)
,constraint FK_fk_billnotax_seq foreign key(fk_billnotax_seq) references tbl_billnotax(billnotax_seq) on delete cascade
);

alter table tbl_billnotax_detail modify sellamount default 1;

desc tbl_billnotax_detail;

-- 계산서 시퀀스
create sequence billnotax_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

select billnotax_seq.nextval
from dual

-- 계산서상세 시퀀스
create sequence billnotax_detail_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

------------------------------------------

-- 거래처
create table tbl_customer
(customer_id    varchar2(200)   not null
,customer_comp  varchar2(1000)
,customer_name  varchar2(200)
,customer_addr  varchar2(1000)
,customer_email varchar2(1000) -- 거래처 이메일(메일발송용도)
,constraint PK_tbl_customer primary key(customer_id)
);

alter table tbl_customer add customer_regdate date default sysdate not null;

------------------------------------------

-- 우리 회사 사업자등록정보
create table tbl_mycompany
(mycompany_id    varchar2(200)  not null
,mycompany_comp  varchar2(1000) not null
,mycompany_name  varchar2(200)  not null
,mycompany_addr  varchar2(1000) not null
,mycompany_sort  varchar2(300)  not null
,constraint PK_tbl_mycompany primary key(mycompany_id)
);


------------------------------------------

-- 국세청(세금계산서)
create table tbl_hometax_tax
(hometax_tax_seq  number           not null
,fk_billtax_seq   number           not null
,status           number default 1 not null -- 0:전송실패, 1:전송중, 2:전송완료
,constraint PK_tbl_hometax_tax primary key(hometax_tax_seq)
,constraint CK_tbl_hometax_tax_status check(status in(0,1,2))
);

-- 국세청(세금계산서) 시퀀스
create sequence hometax_tax_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

--------------------------------------------

-- 국세청(계산서)
create table tbl_hometax_notax
(hometax_notax_seq  number           not null
,fk_billnotax_seq   number           not null
,status           number default 1 not null -- 0:전송실패, 1:전송중, 2:전송완료
,constraint PK_tbl_hometax_notax primary key(hometax_notax_seq)
,constraint CK_tbl_hometax_notax_status check(status in(0,1,2))
);

-- 국세청(계산서) 시퀀스
create sequence hometax_notax_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

--------------------------------------------

-- 문서수신함(우리회사가 구매자인~) 테이블 만들어야 함


-- 거래명세서테이블
create table tbl_transaction
(transaction_seq    number          not null
,customer_id    varchar2(200)   not null
,customer_comp  varchar2(1000)
,customer_name  varchar2(200)
,customer_addr  varchar2(1000)
,regdate        date default sysdate not null
,totalprice     number           not null
,billtax_yn     varchar2(20) default '발행' not null
,empid       varchar2(20)        not null -- 작성한 직원아이디(발급 담당자)
,empname        varchar2(20)     not null
,mycompany_id varchar2(200) not null
,mycompany_comp  varchar2(1000)
,mycompany_name  varchar2(200)
,mycompany_addr  varchar2(1000)
,constraint PK_tbl_transaction primary key(transaction_seq)
,constraint CK_tbl_transaction_billtax_yn check(billtax_yn in('발행','미발행'))
);

alter table tbl_transaction add status number default 0 not null; -- 0:발급전, 1:승인완료
alter table tbl_transaction add constraint CK_tbl_transaction_status check(status in(0,1));

alter table tbl_transaction rename column transactiondate to regdate;

-- 거래명세서 상세
create table tbl_transaction_detail
(transaction_detail_seq     number      not null
,fk_transaction_seq         number      not null
,selldate               date        not null
,sellprod               varchar2(500) not null
,sellamount             number default 1
,selloneprice           number      not null
,selltotalprice         number      not null
,constraint PK_tbl_transaction_detail primary key(transaction_detail_seq)
,constraint FK_fk_transaction_seq foreign key(fk_transaction_seq) references tbl_transaction(transaction_seq) on delete cascade
);


drop table tbl_transaction_detail purge;
drop table tbl_transaction purge;

create sequence transaction_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

create sequence transaction_detail_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

select * from tbl_transaction where to_char(regdate, 'yyyymmdd') between '20211111' and '20211120';
select * from tbl_transaction_detail;

-----------------------------------------------------

select regdate, customer_id, customer_comp, customer_name, empname, totalprice, status
from
(
    select row_number() over(order by regdate desc) AS rno
         , to_char(regdate, 'yyyy-mm-dd') AS regdate
         , customer_id, customer_comp, customer_name, empname, totalprice, status
    from tbl_billnotax
    where 1 = 1
          and to_char(regdate, 'yyyymmdd') between '20211111' and '20211120'
          and customer_id like '%' || '-' || '%'
) V
where rno between 1 and 10
order by rno desc
