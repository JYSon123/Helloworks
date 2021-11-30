package com.spring.chat.model;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDAO_HJE implements InterChatDAO_HJE {

	@Resource
	private SqlSessionTemplate sqlsession2;
	
	// 채팅에 접속한 직원의 부서명 구하기
	@Override
	public String getDeptName(String empid) {
		String deptname = sqlsession2.selectOne("hje.getDeptName",empid); 
		return deptname;
	}

}
