package com.spring.schedule.model;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDAO_HJE implements InterScheduleDAO_HJE {
	
	@Resource
	private SqlSessionTemplate sqlsession2;
	
	
	// DB연결 테스트용 (이순신, 엄정화 select)
	@Override
	public List<String> getName() {
		
		List<String> nameList =  sqlsession2.selectList("sonjy.getName");
		
		return nameList;
	}

	
	
}
