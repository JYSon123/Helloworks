package com.spring.employees.model;

import java.util.List;
import java.util.Map;

public interface InterEmpDAO_sj {

	// tbl_board테이블에서 groupno 컬럼의 최대값 알아오기
	int getGroupnoMax();
	
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

	// 글 1개 조회하기
	BoardVO getView(Map<String, String> paraMap);

	// 글조회수 1 증가하기
	void setAddReadCount(String seq);

	// 글 수정하기
	int boardEdit(BoardVO boardvo);

	// 글 삭제하기
	int boardDel(Map<String, String> paraMap);

	// 댓글쓰기(tbl_comment 테이블에 insert)
	int addComment(CommentVO_sj commentvo);

	// tbl_board 테이블에 commentCount 컬럼이 1증가(update)
	int updateCommentCount(String parentSeq);

	// 검색어 입력시 자동글 완성하기
	List<String> wordSearchShow(Map<String, String> paraMap);


	
	

	
}
