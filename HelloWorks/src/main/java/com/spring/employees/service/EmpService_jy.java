package com.spring.employees.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.employees.model.BreakCalendarVO_jy;
import com.spring.employees.model.DocumentVO_jy;
import com.spring.employees.model.InterEmpDAO_jy;

@Service
public class EmpService_jy implements InterEmpService_jy {

	@Autowired // 의존객체 주입
	private InterEmpDAO_jy dao;
	
	
	// 작성한 문서를 테이블에 insert 시켜주는 메소드
	@Override
	public int documentaddend(Map<String, String> paraMap) {
		
		int documentaddend = dao.documentaddend(paraMap);
		
		return documentaddend;
	}

	
	// 총 게시물 건수(totalCount)를 알아오는 메소드
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = dao.getTotalCount(paraMap);
		return n;
	}
	
	
	// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것)
	@Override
	public List<DocumentVO_jy> documentListSearchWithPaging(Map<String, String> paraMap) {
		
		List<DocumentVO_jy> documentList = dao.documentListSearchWithPaging(paraMap);
		
		return documentList;

	}

	// 문서 하나를 자세히 보기
	@Override
	public DocumentVO_jy viewDocument(Map<String, String> paraMap) {
		
		DocumentVO_jy documentvo = dao.viewDocument(paraMap);
		
		return documentvo;
	}

	
	// 캘린더에 연차를 표시해주는 메소드
	@Override
	public List<BreakCalendarVO_jy> viewBreak(Map<String, String> paraMap) {
		
		List<BreakCalendarVO_jy> breakCalendarList = dao.viewBreak(paraMap);
		
		return breakCalendarList;
	}

	

	
}
