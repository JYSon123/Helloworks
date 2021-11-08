package com.spring.employees.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmpDAO_jy implements InterEmpDAO_jy {

	@Resource
	private SqlSessionTemplate sqlsession2;  
	
	
	

}
