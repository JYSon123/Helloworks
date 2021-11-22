package com.spring.helloworks.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.helloworks.common_KJH.GoogleOTP;
import com.spring.helloworks.model.*;

// 트랜잭션 처리 담당, DB담당
@Service
public class HelloWorksService_KJH implements InterHelloWorksService_KJH {

	@Autowired
	private InterHelloWorksDAO_KJH dao; // 의존객체 주입
		
	@Autowired
	private GoogleOTP gotp;
	
	// 직원정보 SELECT
	@Override
	public EmpVO_KJH getLoginEmp(Map<String, String> paraMap) {
		
		EmpVO_KJH loginEmp = dao.getLoginEmp(paraMap);
		
		if(loginEmp != null && (loginEmp.getLastlogingap() == null || Integer.parseInt(loginEmp.getLastlogingap()) >= 3)) {
			
			loginEmp.setRequiredPwdChange(true);
			
		}
		
		if(loginEmp != null && loginEmp.getLastlogingap() == null) {
			
			loginEmp.setRequiredGetOTPkey(true);
			
			HashMap<String, String> map = gotp.generate(loginEmp.getEmpid(), "helloworks.com");
			
			paraMap.put("otpKey", map.get("encodedKey"));
			
			int n = dao.updateOtpKey(paraMap);
			
			if(n != 0) {
				
				loginEmp.setOtpkey(map.get("encodedKey"));
				
				loginEmp.setOtpurl(map.get("url"));
				
			}
		}
				
		return loginEmp;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 로그인히스토리 INSERT
	@Override
	public int insertLoginHistory(Map<String, String> paraMap) {
		
		int n = dao.insertLoginHistory(paraMap);
		
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 암호 UPDATE
	@Override
	public int updateEmppw(Map<String, String> paraMap) {
		
		int n = dao.updateEmppw(paraMap);
		
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 우리 회사 사업자 정보 SELECT
	@Override
	public MycompanyVO_KJH getMycomp() {
		
		MycompanyVO_KJH comp = dao.getMycomp();
		
		return comp;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 우리 회사 정보 INSERT
	@Override
	public int registerCompany(MycompanyVO_KJH comp) {
		
		int n = dao.registerCompany(comp);
				
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 우리 회사 정보 UPDATE
	@Override
	public int updateCompany(MycompanyVO_KJH comp) {
		
		int n = dao.updateCompany(comp);
		
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 우리 회사 정보 DELETE
	@Override
	public int deleteCompany(String mycompany_id) {

		int n = dao.deleteCompany(mycompany_id);
		
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 검색어에 해당하는 총 거래처 수
	@Override
	public int getTotalCount(Map<String, String> paraMap) {

		int n = dao.getTotalCount(paraMap);		
		
		return n;
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 목록 SELECT
	@Override
	public List<CustomerVO_KJH> getCustomerList(Map<String, String> paraMap) {
		
		List<CustomerVO_KJH> cvoList = dao.getCustomerList(paraMap);
		
		return cvoList;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 신규 거래처 INSERT
	@Override
	public int insertCustomer(CustomerVO_KJH comp) {

		int n = dao.insertCustomer(comp);
				
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 1개 SELECT
	@Override
	public CustomerVO_KJH selectCustomer(String customer_id) {
		
		CustomerVO_KJH customer = dao.selectCustomer(customer_id);
		
		return customer;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 정보 UPDATE
	@Override
	public int updateCustomer(CustomerVO_KJH comp) {

		int n = dao.updateCustomer(comp);
				
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 DELETE
	@Override
	public int deleteCustomer(String customer_id) {
		
		int n = dao.deleteCustomer(customer_id);
		
		return n;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 사업자등록번호 존재 여부 조회
	@Override
	public int verifyId(String compid) {
		
		int isExist = dao.verifyId(compid);
		
		return isExist;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 목록 SELECT
	@Override
	public List<CustomerVO_KJH> getCustomerListNoPaging() {
		
		List<CustomerVO_KJH> cvoList = dao.getCustomerListNoPaging();
				
		return cvoList;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	
	// 세금계산서 시퀀스 채번
	@Override
	public String getBillTaxSeq() {
		
		String billtax_seq = dao.getBillTaxSeq();
		
		return billtax_seq;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	
	// 세금계산서 INSERT
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int insertBillTax(Map<String, Object> paraMap) {
		
		int n = 0, m = 0;
		
		BilltaxVO_KJH btvo = (BilltaxVO_KJH)paraMap.get("btvo");
		
		// 세금계산서
		n = dao.insertBillTax(btvo);
		
		// 세금계산서 상세
		if(n != 0) {
			
			BilltaxDetailVO_KJH dvo = (BilltaxDetailVO_KJH)paraMap.get("dvo");
			
			String[] arrSelldate = dvo.getSelldate().split(",");
			String[] arrSellprod = dvo.getSellprod().split(",");
			String[] arrSellamount = dvo.getSellamount().split(",");
			String[] arrSelloneprice = dvo.getSelloneprice().split(",");
			String[] arrSelltotalprice = dvo.getSelltotalprice().split(",");
			String[] arrSelltax = dvo.getSelltax().split(",");
			
			for(int i=0; i<arrSelldate.length; i++) {
				
				dvo.setSelldate(arrSelldate[i]);
				dvo.setSellprod(arrSellprod[i]);
				dvo.setSellamount(arrSellamount[i]);
				dvo.setSelloneprice(arrSelloneprice[i]);
				dvo.setSelltotalprice(arrSelltotalprice[i]);
				dvo.setSelltax(arrSelltax[i]);
				
				m = dao.insertBillTaxDetail(dvo);
				
			}
			
		}
		
		return n*m;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////

	// 계산서 시퀀스 채번
	@Override
	public String getBillNoTaxSeq() {
		
		String billnotax_seq = dao.getBillNoTaxSeq();
		
		return billnotax_seq;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	
	// 계산서 INSERT
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int insertBillNoTax(Map<String, Object> paraMap) {

		int n = 0, m = 0;
		
		BillnotaxVO_KJH bntvo = (BillnotaxVO_KJH)paraMap.get("bntvo");
		
		// 계산서
		n = dao.insertBillNoTax(bntvo);
		
		// 계산서 상세
		if(n != 0) {
			
			BillnotaxDetailVO_KJH ndvo = (BillnotaxDetailVO_KJH)paraMap.get("ndvo");
			
			String[] arrSelldate = ndvo.getSelldate().split(",");
			String[] arrSellprod = ndvo.getSellprod().split(",");
			String[] arrSellamount = ndvo.getSellamount().split(",");
			String[] arrSelloneprice = ndvo.getSelloneprice().split(",");
			String[] arrSelltotalprice = ndvo.getSelltotalprice().split(",");
			
			for(int i=0; i<arrSelldate.length; i++) {
				
				ndvo.setSelldate(arrSelldate[i]);
				ndvo.setSellprod(arrSellprod[i]);
				ndvo.setSellamount(arrSellamount[i]);
				ndvo.setSelloneprice(arrSelloneprice[i]);
				ndvo.setSelltotalprice(arrSelltotalprice[i]);
				
				m = dao.insertBillNoTaxDetail(ndvo);
				
			}
			
		}
		
		return n*m;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	// 거래명세서 시퀀스 채번
	@Override
	public String getTransactionSeq() {

		String transaction_seq = dao.getTransactionSeq();
		
		return transaction_seq;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////
	
	// 거래명세서 INSERT
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int insertTransaction(Map<String, Object> paraMap) {

		int n = 0, m = 0;
		
		TransactionVO_KJH tvo = (TransactionVO_KJH)paraMap.get("tvo");
		
		// 거래명세서
		n = dao.insertTransaction(tvo);
		
		// 거래명세서 상세
		if(n != 0) {
			
			TransactionDetailVO_KJH tdvo = (TransactionDetailVO_KJH)paraMap.get("tdvo");
			
			String[] arrSelldate = tdvo.getSelldate().split(",");
			String[] arrSellprod = tdvo.getSellprod().split(",");
			String[] arrSellamount = tdvo.getSellamount().split(",");
			String[] arrSelloneprice = tdvo.getSelloneprice().split(",");
			String[] arrSelltotalprice = tdvo.getSelltotalprice().split(",");
			
			for(int i=0; i<arrSelldate.length; i++) {
				
				tdvo.setSelldate(arrSelldate[i]);
				tdvo.setSellprod(arrSellprod[i]);
				tdvo.setSellamount(arrSellamount[i]);
				tdvo.setSelloneprice(arrSelloneprice[i]);
				tdvo.setSelltotalprice(arrSelltotalprice[i]);
				
				m = dao.insertTransactionDetail(tdvo);
				
			}
			
		}
		
		return n*m;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	
	// 총 작성문서 수 알아오기
	@Override
	public int getTotalDocument(Map<String, String> paraMap) {
		
		int n = dao.getTotalDocument(paraMap);
		
		return n;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	
	// 작성문서 SELECT
	@Override
	public List<Map<String, String>> getDocumentList(Map<String, String> paraMap) {
		
		List<Map<String, String>> docList = dao.getDocumentList(paraMap);
				
		return docList;
		
	}

	
	
	
	

	
	
}
