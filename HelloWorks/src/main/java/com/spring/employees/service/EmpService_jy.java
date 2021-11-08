package com.spring.employees.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.employees.model.InterEmpDAO_jy;

@Service
public class EmpService_jy implements InterEmpService_jy {

	@Autowired // 의존객체 주입
	private InterEmpDAO_jy dao;

	

	
}
