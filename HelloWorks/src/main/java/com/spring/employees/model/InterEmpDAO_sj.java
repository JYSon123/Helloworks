package com.spring.employees.model;

public interface InterEmpDAO_sj {

	// tbl_board테이블에서 groupno 컬럼의 최대값 알아오기
	int getGroupnoMax();
	
	// 파일첨부가 없는 글쓰기
	int add(BoardVO boardvo);

	// 파일첨부가 있는 글쓰기
	int add_withFile(BoardVO boardvo);
	
	

	
}
