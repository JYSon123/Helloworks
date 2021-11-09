package com.spring.employees.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.employees.model.BoardVO;
import com.spring.employees.model.InterEmpDAO_sj;

@Service
public class EmpService_sj implements InterEmpService_sj {

	@Autowired // 의존객체 주입
	private InterEmpDAO_sj dao;

	// === 파일첨부가 없는 글쓰기 === //
	@Override
	public int add(BoardVO boardvo) {
		
		// === 원글쓰기인지 답변글쓰기인지 구분하기 시작 === 
		if("".equals(boardvo.getFk_seq()) ) {
			// 원글쓰기인 경우
			// groupno 컬럼의 값은 groupno 컬럼의 최대값(max)+1로 해야 한다.
			int groupno = dao.getGroupnoMax() + 1;
			boardvo.setGroupno(String.valueOf(groupno));
		}
		// === 원글쓰기인지 답변글쓰기인지 구분하기 끝 === 
		
		int n = dao.add(boardvo);
		
		return 0;
	}

	
	// === 파일첨부가 있는 글쓰기 === //
	@Override
	public int add_withFile(BoardVO boardvo) {
		
		// 원글쓰기라면 tbl_board테이블의 groupno 컬럼의 값은 max+1로 해서 insert해야 하고
		// 답변글쓰기라면 넘겨받은 값 (boardvo)을 그대로 insert
		
		// === 원글쓰기인지 답변글쓰기인지 구분하기 시작 ===
		if("".equals(boardvo.getFk_seq()) ) {
			// 원글쓰기인 경우
			int groupno = dao.getGroupnoMax() + 1;
			boardvo.setGroupno(String.valueOf(groupno));
		}
		// === 원글쓰기인지 답변글쓰기인지 구분하기 끝 ===
		
		int n = dao.add_withFile(boardvo); // 첨부파일이 있는 경우
		return n;
	}
	
}
