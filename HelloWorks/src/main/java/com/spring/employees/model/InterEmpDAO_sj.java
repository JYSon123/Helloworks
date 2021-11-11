package com.spring.employees.model;

import java.util.List;

public interface InterEmpDAO_sj {

	// tbl_board테이블에서 groupno 컬럼의 최대값 알아오기
	int getGroupnoMax();
	
	// 파일첨부가 없는 글쓰기
	int add(BoardVO boardvo);

	// 파일첨부가 있는 글쓰기
	int add_withFile(BoardVO boardvo);

	// 페이징 처리 x 검색어 x 전체 글목록 보여주기(수정 예정)
	List<BoardVO> boardListNoSearch();
	
	

	
}
