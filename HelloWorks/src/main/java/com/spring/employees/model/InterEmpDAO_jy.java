package com.spring.employees.model;

import java.util.List;
import java.util.Map;

public interface InterEmpDAO_jy {
	
	// 작성한 문서를 테이블에 insert 시켜주는 메소드
	int documentaddend(Map<String, String> paraMap);
	
	// 파일이 있는 경우 기안하기를 완료해주기
	int documentaddend_file(Map<String, String> paraMap);
	
	// 총 게시물 건수(totalCount)를 알아오는 메소드
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것)
	List<DocumentVO_jy> documentListSearchWithPaging(Map<String, String> paraMap);
	
	// 문서 하나를 자세히 보기
	DocumentVO_jy viewDocument(Map<String, String> paraMap);
	
	// 캘린더에 연차를 표시해주는 메소드
	List<BreakCalendarVO_jy> viewBreak(Map<String, String> paraMap);
	
	// 문서의 result를 바꿔주는 함수
	int changeResult(Map<String, String> paraMap);
	
	// 캘린더에 새롭게 승인된 연차를 표시해주는 메소드
	int insertCalendar(Map<String, String> paraMap2);
	
	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것, 로그인한 사람것)
	List<DocumentVO_jy> myDocumentListSearchWithPaging(Map<String, String> paraMap);
	
	// 기안문서를 삭제해주는 메소드
	int delDocumentEnd(Map<String, String> paraMap);
	

	
	

	
	
}
