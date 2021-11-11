package com.spring.employees.service;

import java.util.List;

import com.spring.employees.model.BoardVO;

public interface InterEmpService_sj {

	// 파일첨부가 없는 글쓰기
	int add(BoardVO boardvo);

	// 파일첨부가 있는 글쓰기
	int add_withFile(BoardVO boardvo);

	// 페이징 처리 x 검색어 x 전체 글목록 보여주기(수정 예정)
	List<BoardVO> boardListNoSearch();

}
