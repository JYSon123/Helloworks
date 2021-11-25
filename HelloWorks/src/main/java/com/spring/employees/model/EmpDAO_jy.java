package com.spring.employees.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class EmpDAO_jy implements InterEmpDAO_jy {

	@Resource
	private SqlSessionTemplate sqlsession2;

	
	// 작성한 문서를 테이블에 insert 시켜주는 메소드
	@Override
	public int documentaddend(Map<String, String> paraMap) {
		int documentaddend = sqlsession2.insert("sonjy.addEnd", paraMap);
		return documentaddend;
	}
	
	@Override
	public int documentaddend_file(Map<String, String> paraMap) {
		int documentaddend_file = sqlsession2.insert("sonjy.addEndFile", paraMap);
		return documentaddend_file;
	}  
	
	
	// 총 게시물 건수(totalCount)를 알아오는 메소드
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = sqlsession2.selectOne("sonjy.getTotalCount",paraMap);
		return n;
	}

	
	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것)
	@Override
	public List<DocumentVO_jy> documentListSearchWithPaging(Map<String, String> paraMap) {
		List<DocumentVO_jy> documentList = sqlsession2.selectList("sonjy.documentListSearchWithPaging" , paraMap);
		return documentList;
	}

	// 문서 하나를 자세히 보기
	@Override
	public DocumentVO_jy viewDocument(Map<String, String> paraMap) {
		
		DocumentVO_jy documentvo = sqlsession2.selectOne("sonjy.viewDocument", paraMap);
		
		return documentvo;
	}

	
	// 캘린더에 연차를 표시해주는 메소드
	@Override
	public List<BreakCalendarVO_jy> viewBreak(Map<String, String> paraMap) {
		
		List<BreakCalendarVO_jy> breakCalendarList = sqlsession2.selectList("sonjy.viewBreak", paraMap);
		
		return breakCalendarList;
	}

	
	// 문서의 result를 바꿔주는 함수
	@Override
	public int changeResult(Map<String, String> paraMap) {
		
		int n = sqlsession2.update("sonjy.changeResult", paraMap);
		
		return n;
	}
	
	
	// 캘린더에 새롭게 승인된 연차를 표시해주는 메소드
	@Override
	public int insertCalendar(Map<String, String> paraMap2) {
		
		int m = sqlsession2.insert("sonjy.insertCalendar", paraMap2);
		
		return m;
	}
	
	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것, 로그인한 사람것)
	@Override
	public List<DocumentVO_jy> myDocumentListSearchWithPaging(Map<String, String> paraMap) {
		List<DocumentVO_jy> documentList = sqlsession2.selectList("sonjy.myDocumentListSearchWithPaging" , paraMap);
		return documentList;
	}
	
	// 기안문서를 삭제해주는 메소드
	@Override
	public int delDocumentEnd(Map<String, String> paraMap) {
		
		int n = sqlsession2.delete("sonjy.delDocumentEnd" , paraMap);
		
		return n;
	}
	
	// 총 게시물 건수(totalCount) 일반사용자
	@Override
	public int getMyTotalCount(Map<String, String> paraMap) {
		int n = sqlsession2.selectOne("sonjy.getMyTotalCount",paraMap);
		return n;
	}
	
	// ID를 중복확인 해주는 메소드
	@Override
	public int idDuplicateCheck(String empid) {
		int n = sqlsession2.selectOne("sonjy.idDuplicateCheck", empid);
		return n;
	}
	
	// 회원등록을 해주는 메소드
	@Override
	public int registerEnd(Map<String, String> paraMap) {
		int n = sqlsession2.insert("sonjy.registerEnd", paraMap);
		return n;
	}


}
