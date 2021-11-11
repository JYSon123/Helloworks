package com.spring.employees.model;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmpDAO_sj implements InterEmpDAO_sj {

	@Resource
	private SqlSessionTemplate sqlsession2;

	
	// === tbl_board테이블에서 groupno 컬럼의 최대값 알아오기 === //
	@Override
	public int getGroupnoMax() {
		int max = sqlsession2.selectOne("yoosj.getGroupnoMax");
		return max;
	}


	// === 파일첨부가 없는 글쓰기 === //
	@Override
	public int add(BoardVO boardvo) {
		int n = sqlsession2.insert("yoosj.add", boardvo);
		return n;
	}


	// === 파일첨부가 있는 글쓰기 === //
	@Override
	public int add_withFile(BoardVO boardvo) {
		int n = sqlsession2.insert("yoosj.add_withFile", boardvo);
		return n;
	}


	// === 페이징 처리 x 검색어 x 전체 글목록 보여주기(수정 예정) === //
	@Override
	public List<BoardVO> boardListNoSearch() {
		List<BoardVO> boardList = sqlsession2.selectList("yoosj.boardListNoSearch");
		
		return boardList;
	}  
	
	
	

}
