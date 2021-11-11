package com.spring.employees.service;

import java.util.List;
import java.util.Map;

import com.spring.employees.model.DocumentVO_jy;

public interface InterEmpService_jy {
	
	// 작성한 문서를 테이블에 insert 시켜주는 메소드
	int documentaddend(Map<String, String> paraMap);
	
	// 총 게시물 건수(totalCount)를 알아오는 메소드
	int getTotalCount(Map<String, String> paraMap);
	
	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것)
	List<DocumentVO_jy> documentListSearchWithPaging(Map<String, String> paraMap);
	
	

	
}
