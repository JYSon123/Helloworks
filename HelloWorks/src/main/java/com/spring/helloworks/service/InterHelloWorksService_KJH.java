package com.spring.helloworks.service;

import java.util.*;

import com.spring.helloworks.model.CustomerVO_KJH;
import com.spring.helloworks.model.EmpVO_KJH;
import com.spring.helloworks.model.MycompanyVO_KJH;

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

	
	
}
