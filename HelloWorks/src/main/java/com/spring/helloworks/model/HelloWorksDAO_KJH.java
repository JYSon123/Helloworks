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
	
	
	
	
	
	

	
	
}
