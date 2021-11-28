package com.spring.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.chat.model.InterChatDAO_HJE;

@Service
public class ChatService_HJE implements InterChatService_HJE {

	@Autowired
	private InterChatDAO_HJE dao;

	// 채팅에 접속한 직원의 부서명 구하기
	@Override
	public String getDeptName(String empid) {
		String deptname = dao.getDeptName(empid);
		return deptname;
	}
	
	
}
