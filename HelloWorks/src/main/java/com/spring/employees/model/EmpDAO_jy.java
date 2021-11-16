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
	
	
	

}
