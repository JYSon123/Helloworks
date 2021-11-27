package com.spring.helloworks.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HelloWorksDAO_KJH implements InterHelloWorksDAO_KJH {

	@Resource
	private SqlSessionTemplate sqlsession2;
	
	// 직원정보 SELECT
	@Override
	public EmpVO_KJH getLoginEmp(Map<String, String> paraMap) {
		
		EmpVO_KJH loginEmp = sqlsession2.selectOne("jihee.getLoginEmp", paraMap);
		
		return loginEmp;
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 최초 OTPkey 생성 후 UPDATE
	@Override
	public int updateOtpKey(Map<String, String> paraMap) {
		
		int n = sqlsession2.update("jihee.updateOtpKey", paraMap);
		
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 로그인히스토리 INSERT
	@Override
	public int insertLoginHistory(Map<String, String> paraMap) {
		
		int n = sqlsession2.insert("jihee.insertLoginHistory", paraMap);
		
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 암호 UPDATE
	@Override
	public int updateEmppw(Map<String, String> paraMap) {
		
		int n = sqlsession2.update("jihee.updateEmppw", paraMap);
		
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 우리 회사 사업자 정보 SELECT
	@Override
	public MycompanyVO_KJH getMycomp() {
		
		MycompanyVO_KJH comp = sqlsession2.selectOne("jihee.getMycomp");
		
		return comp;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 우리 회사 정보 INSERT
	@Override
	public int registerCompany(MycompanyVO_KJH comp) {
		
		int n = sqlsession2.insert("jihee.registerCompany", comp);
		
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 우리 회사 정보 UPDATE
	@Override
	public int updateCompany(MycompanyVO_KJH comp) {
		
		int n = sqlsession2.update("jihee.updateCompany", comp);
		
		return n;
		
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	
	// 우리 회사 정보 DELETE
	@Override
	public int deleteCompany(String mycompany_id) {

		int n = sqlsession2.delete("jihee.deleteCompany", mycompany_id);
		
		return n;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////

	// 검색어에 해당하는 총 거래처 수
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		
		int n = sqlsession2.selectOne("jihee.getTotalCount", paraMap);
		
		return n;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 목록 SELECT
	@Override
	public List<CustomerVO_KJH> getCustomerList(Map<String, String> paraMap) {
		
		List<CustomerVO_KJH> cvoList = sqlsession2.selectList("jihee.getCustomerList", paraMap);
		
		return cvoList;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 신규 거래처 INSERT
	@Override
	public int insertCustomer(CustomerVO_KJH comp) {

		int n = sqlsession2.insert("jihee.insertCustomer", comp);
		
		return n;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 1개 SELECT
	@Override
	public CustomerVO_KJH selectCustomer(String customer_id) {
		
		CustomerVO_KJH customer = sqlsession2.selectOne("jihee.selectCustomer", customer_id);
		
		return customer;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 정보 UPDATE
	@Override
	public int updateCustomer(CustomerVO_KJH comp) {

		int n = sqlsession2.update("jihee.updateCustomer", comp);
		
		return n;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 DELETE
	@Override
	public int deleteCustomer(String customer_id) {

		int n = sqlsession2.delete("jihee.deleteCustomer", customer_id);
		
		return n;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 사업자등록번호 존재 여부 조회
	@Override
	public int verifyId(String compid) {
		
		int isExist = sqlsession2.selectOne("jihee.verifyId", compid);
		
		return isExist;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 목록 SELECT
	@Override
	public List<CustomerVO_KJH> getCustomerListNoPaging() {

		List<CustomerVO_KJH> cvoList = sqlsession2.selectList("jihee.getCustomerListNoPaging");
				
		return cvoList;
		
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	
	// 세금계산서 시퀀스 채번
	@Override
	public String getBillTaxSeq() {

		String billtax_seq = sqlsession2.selectOne("jihee.getBillTaxSeq");
		
		return billtax_seq;
		
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	
	// 세금계산서 INSERT
	@Override
	public int insertBillTax(BilltaxVO_KJH btvo) {
		
		int n = sqlsession2.insert("jihee.insertBillTax", btvo);
		
		return n;
		
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	
	// 세금계산서상세 INSERT
	@Override
	public int insertBillTaxDetail(BilltaxDetailVO_KJH dvo) {

		int n = sqlsession2.insert("jihee.insertBillTaxDetail", dvo);
		
		return n;
		
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	
	// 계산서 시퀀스 채번
	@Override
	public String getBillNoTaxSeq() {

		String billnotax_seq = sqlsession2.selectOne("jihee.getBillNoTaxSeq");
		
		return billnotax_seq;
				
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	
	// 계산서 INSERT
	@Override
	public int insertBillNoTax(BillnotaxVO_KJH bntvo) {

		int n = sqlsession2.insert("jihee.insertBillNoTax", bntvo);
		
		return n;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 계산서상세 INSERT
	@Override
	public int insertBillNoTaxDetail(BillnotaxDetailVO_KJH ndvo) {

		int n = sqlsession2.insert("jihee.insertBillNoTaxDetail", ndvo);
		
		return n;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래명세서 시퀀스 채번
	@Override
	public String getTransactionSeq() {

		String transaction_seq = sqlsession2.selectOne("jihee.getTransactionSeq");
		
		return transaction_seq;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래명세서 INSERT
	@Override
	public int insertTransaction(TransactionVO_KJH tvo) {

		int n = sqlsession2.insert("jihee.insertTransaction", tvo);
		
		return n;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	// 거래명세서상세 INSERT
	@Override
	public int insertTransactionDetail(TransactionDetailVO_KJH tdvo) {

		int n = sqlsession2.insert("jihee.insertTransactionDetail", tdvo);
		
		return n;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 총 작성문서 수 알아오기
	@Override
	public int getTotalDocument(Map<String, String> paraMap) {
		
		int n = sqlsession2.selectOne("jihee.getTotalDocument", paraMap);
		
		return n;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 작성문서 SELECT
	@Override
	public List<Map<String, String>> getDocumentList(Map<String, String> paraMap) {
		
		List<Map<String, String>> docList = sqlsession2.selectList("jihee.getDocumentList", paraMap);
		
		return docList;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 작성문서 상태 UPDATE
	@Override
	public int updateStatus(Map<String, String> paraMap) {
		
		int n = sqlsession2.update("jihee.updateStatus", paraMap);
		
		return n;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 메일 발송을 위한 거래처 이메일 SELECT
	@Override
	public CustomerVO_KJH getEmail(Map<String, String> paraMap) {
		
		CustomerVO_KJH cvoList = sqlsession2.selectOne("jihee.getEmail", paraMap);
		
		return cvoList;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
		
	@Override
	public BilltaxVO_KJH getBilltaxDoc(Map<String, String> paraMap) {
		BilltaxVO_KJH doc = sqlsession2.selectOne("jihee.getBilltaxDoc", paraMap);
		return doc;
	}

	@Override
	public List<BilltaxDetailVO_KJH> getDetailBilltaxList(Map<String, String> paraMap) {
		List<BilltaxDetailVO_KJH> detailList = sqlsession2.selectList("jihee.getDetailBilltaxList", paraMap);
		return detailList;
	}

	@Override
	public BillnotaxVO_KJH getBillnotaxDoc(Map<String, String> paraMap) {
		BillnotaxVO_KJH doc = sqlsession2.selectOne("jihee.getBillnotaxDoc", paraMap);
		return doc;
	}

	@Override
	public List<BillnotaxDetailVO_KJH> getDetailBillnotaxList(Map<String, String> paraMap) {
		List<BillnotaxDetailVO_KJH> detailList = sqlsession2.selectList("jihee.getDetailBillnotaxList", paraMap);
		return detailList;
	}

	@Override
	public TransactionVO_KJH getTransactionDoc(Map<String, String> paraMap) {
		TransactionVO_KJH doc = sqlsession2.selectOne("jihee.getTransactionDoc", paraMap);
		return doc;
	}

	@Override
	public List<TransactionDetailVO_KJH> getDetailTransactionList(Map<String, String> paraMap) {
		List<TransactionDetailVO_KJH> detailList = sqlsession2.selectList("jihee.getDetailTransactionList", paraMap);
		return detailList;
	}

	//////////////////////////////////////////////////////////////////////////////////
	
	// 문서 DELETE
	@Override
	public int deleteDoc(Map<String, String> paraMap) {
		
		int n = sqlsession2.delete("jihee.deleteDoc", paraMap);
		
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////
	
	// 매월 10일 오후12시에 문서 국세청으로 전송
	@Override
	public void updateStatusAlltax() {		
		sqlsession2.update("jihee.updateStatusAlltax");		
	}

	@Override
	public void updateStatusAllnotax() {		
		sqlsession2.update("jihee.updateStatusAllnotax");		
	}

	////////////////////////////////////////////////////////////////////
	
	@Override
	public List<Map<String, String>> getBilltaxEditList(String thisMonth) {
		List<Map<String, String>> billtaxEditList = sqlsession2.selectList("jihee.getBilltaxEditList", thisMonth);
		return billtaxEditList;
	}

	@Override
	public List<Map<String, String>> getBillnotaxEditList(String thisMonth) {
		List<Map<String, String>> billnotaxEditList = sqlsession2.selectList("jihee.getBillnotaxEditList", thisMonth);
		return billnotaxEditList;
	}

	@Override
	public List<Map<String, String>> getBilltaxStatusList(String thisMonth) {
		List<Map<String, String>> billtaxStatusList = sqlsession2.selectList("jihee.getBilltaxStatusList", thisMonth);
		return billtaxStatusList;
	}

	@Override
	public List<Map<String, String>> getBillnotaxStatusList(String thisMonth) {
		List<Map<String, String>> billnotaxStatusList = sqlsession2.selectList("jihee.getBillnotaxStatusList", thisMonth);
		return billnotaxStatusList;
	}

	@Override
	public List<Map<String, String>> getTransactionStatusList(String thisMonth) {
		List<Map<String, String>> transactionStatusList = sqlsession2.selectList("jihee.getTransactionStatusList", thisMonth);
		return transactionStatusList;
	}

	////////////////////////////////////////////////////////////////////////
	
	// 월별 매출 차트
	@Override
	public List<Map<String, String>> totalSalesOfMonth() {
		List<Map<String, String>> totalSalesOfMonth = sqlsession2.selectList("jihee.totalSalesOfMonth");
		return totalSalesOfMonth;
	}

	/////////////////////////////////////////////////////////////////////////
	
	// 월별 거래처별 거래건 수
	@Override
	public List<Map<String, String>> monthOfCustomerCnt(String month) {
		List<Map<String, String>> mapList = sqlsession2.selectList("jihee.monthOfCustomerCnt", month);
		return mapList;
	}

	/////////////////////////////////////////////////////////////////////////
	
	// 연간 매출 차트
	@Override
	public List<Map<String, String>> totalSalesOfYear() {
		List<Map<String, String>> totalSalesOfYearList = sqlsession2.selectList("jihee.totalSalesOfYear");
		return totalSalesOfYearList;
	}

	/////////////////////////////////////////////////////////////////////////
	
	// 거래처 wordcloud
	@Override
	public List<String> wordcloudOfCustomer() {
		List<String> wordcloudOfCustomerList = sqlsession2.selectList("jihee.wordcloudOfCustomer");
		return wordcloudOfCustomerList;
	}

	////////////////////////////////////////////////////////////////////////
	
	// 거래처 다중 DELETE
	@Override
	public int multiDelCustomer(Map<String, String[]> paraMap) {
		int n = sqlsession2.delete("jihee.multiDelCustomer", paraMap);
		return n;
	}
	
	
	
	
	
	

	
	
}
