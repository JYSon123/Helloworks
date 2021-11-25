package com.spring.employees.service;

import java.util.List;
import java.util.Map;

import com.spring.employees.model.BoardVO;
import com.spring.employees.model.CommentVO_sj;

public interface InterEmpService_sj {

	// 파일첨부가 없는 글쓰기
	int add(BoardVO boardvo);

	// 파일첨부가 있는 글쓰기
	int add_withFile(BoardVO boardvo);

	// 페이징 처리 x 검색어 x 전체 글목록 보여주기(수정 예정)
	List<BoardVO> boardListNoSearch();

	// 총 게시물 건수(totalCount)구하기 - 검색이 있을 때와 없을 때로 나뉜다.
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 글목록 가져오기(검색이 있든지 없든지 모두 포함된 것)
	List<BoardVO> boardListSearchWithPaging(Map<String, String> paraMap);

	// 검색어 입력시 자동글 완성하기
	List<String> wordSearchShow(Map<String, String> paraMap);
		
	// 글조회수 증가와 함께 글 한개 조회하기
	BoardVO getView(Map<String, String> paraMap, String login_empno);

	// 글조회수 증가 없는 글 한개 조회하기
	BoardVO getViewWithNoAddCount(Map<String, String> paraMap);

	// 글 수정하기
	int boardEdit(BoardVO boardvo);

	// 글 삭제하기
	int boardDel(Map<String, String> paraMap);

	// 댓글쓰기(transaction처리)
	int addComment(CommentVO_sj commentvo) throws Throwable;

	
}
