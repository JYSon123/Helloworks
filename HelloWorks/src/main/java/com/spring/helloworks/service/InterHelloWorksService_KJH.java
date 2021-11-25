package com.spring.helloworks.service;

import java.util.*;

import com.spring.helloworks.model.BillnotaxDetailVO_KJH;
import com.spring.helloworks.model.BillnotaxVO_KJH;
import com.spring.helloworks.model.BilltaxDetailVO_KJH;
import com.spring.helloworks.model.BilltaxVO_KJH;
import com.spring.helloworks.model.CustomerVO_KJH;
import com.spring.helloworks.model.EmpVO_KJH;
import com.spring.helloworks.model.MycompanyVO_KJH;
import com.spring.helloworks.model.TransactionDetailVO_KJH;
import com.spring.helloworks.model.TransactionVO_KJH;

public interface InterHelloWorksService_KJH {

	// 직원정보 SELECT
	EmpVO_KJH getLoginEmp(Map<String, String> paraMap);

	// 로그인히스토리 INSERT
	int insertLoginHistory(Map<String, String> paraMap);

	// 암호 UPDATE
	int updateEmppw(Map<String, String> paraMap);

	// 우리 회사 사업자 정보 SELECT
	MycompanyVO_KJH getMycomp();

	// 우리 회사 정보 INSERT
	int registerCompany(MycompanyVO_KJH comp);

	// 우리 회사 정보 UPDATE
	int updateCompany(MycompanyVO_KJH comp);

	// 우리 회사 정보 DELETE
	int deleteCompany(String mycompany_id);

	// 검색어에 해당하는 총 거래처 수
	int getTotalCount(Map<String, String> paraMap);
	
	// 거래처 목록 SELECT
	List<CustomerVO_KJH> getCustomerList(Map<String, String> paraMap);

	// 신규 거래처 INSERT
	int insertCustomer(CustomerVO_KJH comp);

	// 거래처 1개 SELECT
	CustomerVO_KJH selectCustomer(String customer_id);

	// 거래처 정보 UPDATE
	int updateCustomer(CustomerVO_KJH comp);

	// 거래처 DELETE
	int deleteCustomer(String customer_id);

	// 사업자등록번호 존재 여부 조회
	int verifyId(String compid);

	// 거래처 목록 SELECT
	List<CustomerVO_KJH> getCustomerListNoPaging();

	// 세금계산서 시퀀스 채번
	String getBillTaxSeq();

	// 세금계산서 INSERT
	int insertBillTax(Map<String, Object> paraMap);

	// 계산서 시퀀스 채번
	String getBillNoTaxSeq();

	// 계산서 INSERT
	int insertBillNoTax(Map<String, Object> paraMap);

	// 거래명세서 시퀀스 채번
	String getTransactionSeq();

	// 거래명세서 INSERT
	int insertTransaction(Map<String, Object> paraMap);

	// 총 작성문서 수 알아오기
	int getTotalDocument(Map<String, String> paraMap);

	// 작성문서 SELECT
	List<Map<String, String>> getDocumentList(Map<String, String> paraMap);

	// 작성문서 상태 UPDATE
	int updateStatus(Map<String, String> paraMap);

	// 메일 발송을 위한 거래처 이메일 SELECT
	CustomerVO_KJH getEmail(Map<String, String> paraMap);

	// 세금계산서정보 SELECT
	BilltaxVO_KJH getBilltaxDoc(Map<String, String> paraMap);

	// 세금계산서상세정보 SELECT
	List<BilltaxDetailVO_KJH> getDetailBilltaxList(Map<String, String> paraMap);

	// 계산서정보 SELECT
	BillnotaxVO_KJH getBillnotaxDoc(Map<String, String> paraMap);

	// 계산서상세정보 SELECT
	List<BillnotaxDetailVO_KJH> getDetailBillnotaxList(Map<String, String> paraMap);

	// 거래명세서정보 SELECT
	TransactionVO_KJH getTransactionDoc(Map<String, String> paraMap);

	// 거래명세서상세정보 SELECT
	List<TransactionDetailVO_KJH> getDetailTransactionList(Map<String, String> paraMap);

	// 문서 DELETE
	int deleteDoc(Map<String, String> paraMap);

	// 매월 10일 오후12시에 문서 국세청으로 전송
	void submitDoc() throws Exception;

	// 세금계산서 UPDATE
	int updateBillTax(Map<String, Object> paraMap);

	// 계산서 UPDATE
	int updateBillNoTax(Map<String, Object> paraMap);

	// 거래명세서 UPDATE
	int updateTransaction(Map<String, Object> paraMap);

	
	
}
