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
	
	
	
	
	
	

	
	
}
