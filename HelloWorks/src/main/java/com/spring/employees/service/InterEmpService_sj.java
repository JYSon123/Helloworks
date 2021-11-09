package com.spring.employees.service;

import com.spring.employees.model.BoardVO;

public interface InterEmpService_sj {

	// 파일첨부가 없는 글쓰기
	int add(BoardVO boardvo);

	// 파일첨부가 있는 글쓰기
	int add_withFile(BoardVO boardvo);

}
