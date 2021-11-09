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
	
	
	
}
