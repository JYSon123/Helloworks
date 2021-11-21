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
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
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

	
	
	
	

	
	
}
