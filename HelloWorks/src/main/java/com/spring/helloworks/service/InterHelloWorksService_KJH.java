package com.spring.helloworks.service;

import java.util.*;

import com.spring.helloworks.model.EmpVO_KJH;

public interface InterHelloWorksService_KJH {

	// 직원정보 SELECT
	EmpVO_KJH getLoginEmp(Map<String, String> paraMap);

	// 로그인히스토리 INSERT
	int insertLoginHistory(Map<String, String> paraMap);

	// 암호 UPDATE
	int updateEmppw(Map<String, String> paraMap);

	
	
}
