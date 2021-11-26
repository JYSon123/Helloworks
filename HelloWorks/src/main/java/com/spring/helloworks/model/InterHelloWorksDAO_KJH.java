package com.spring.helloworks.model;

import java.util.*;

public interface InterHelloWorksDAO_KJH {

	// 직원정보 SELECT
	EmpVO_KJH getLoginEmp(Map<String, String> paraMap);

	// 최초 OTPkey 생성 후 UPDATE
	int updateOtpKey(Map<String, String> paraMap);

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
	int insertBillTax(BilltaxVO_KJH btvo);

	// 세금계산서상세 INSERT
	int insertBillTaxDetail(BilltaxDetailVO_KJH dvo);

	// 계산서 시퀀스 채번
	String getBillNoTaxSeq();

	// 계산서 INSERT
	int insertBillNoTax(BillnotaxVO_KJH bntvo);

	// 계산서상세 INSERT
	int insertBillNoTaxDetail(BillnotaxDetailVO_KJH ndvo);

	// 거래명세서 시퀀스 채번
	String getTransactionSeq();

	// 거래명세서 INSERT
	int insertTransaction(TransactionVO_KJH tvo);

	// 거래명세서상세 INSERT
	int insertTransactionDetail(TransactionDetailVO_KJH tdvo);

	// 총 작성문서 수 알아오기
	int getTotalDocument(Map<String, String> paraMap);

	// 작성문서 SELECT
	List<Map<String, String>> getDocumentList(Map<String, String> paraMap);

	// 작성문서 상태 UPDATE
	int updateStatus(Map<String, String> paraMap);

	// 메일 발송을 위한 거래처 이메일 SELECT
	CustomerVO_KJH getEmail(Map<String, String> paraMap);

	BilltaxVO_KJH getBilltaxDoc(Map<String, String> paraMap);

	List<BilltaxDetailVO_KJH> getDetailBilltaxList(Map<String, String> paraMap);

	BillnotaxVO_KJH getBillnotaxDoc(Map<String, String> paraMap);

	List<BillnotaxDetailVO_KJH> getDetailBillnotaxList(Map<String, String> paraMap);

	TransactionVO_KJH getTransactionDoc(Map<String, String> paraMap);

	List<TransactionDetailVO_KJH> getDetailTransactionList(Map<String, String> paraMap);

	// 문서 DELETE
	int deleteDoc(Map<String, String> paraMap);

	// 매월 10일 오후12시에 문서 국세청으로 전송
	void updateStatusAlltax();
	void updateStatusAllnotax();

	// 작성문서 상태별 count
	List<Map<String, String>> getBilltaxEditList(String thisMonth);
	List<Map<String, String>> getBillnotaxEditList(String thisMonth);
	List<Map<String, String>> getBilltaxStatusList(String thisMonth);
	List<Map<String, String>> getBillnotaxStatusList(String thisMonth);
	List<Map<String, String>> getTransactionStatusList(String thisMonth);

	// 월별 매출 차트
	List<Map<String, String>> totalSalesOfMonth();

	// 월별 거래처별 거래건 수
	List<Map<String, String>> monthOfCustomerCnt(String month);

	// 연간 매출 차트
	List<Map<String, String>> totalSalesOfYear();

	// 거래처 wordcloud
	List<String> wordcloudOfCustomer();
	
	

	
	
	
}
