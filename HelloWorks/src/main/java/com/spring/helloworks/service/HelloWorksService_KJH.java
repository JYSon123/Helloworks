package com.spring.helloworks.service;

import java.util.HashMap;
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
	
	
	
	

	
	
}
