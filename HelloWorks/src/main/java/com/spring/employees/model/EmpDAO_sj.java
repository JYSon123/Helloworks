package com.spring.employees.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.spring.helloworks.model.EmpVO_KJH;

@Repository
public class EmpDAO_sj implements InterEmpDAO_sj {

	@Resource
	private SqlSessionTemplate sqlsession2;

	
	// === tbl_board테이블에서 groupno 컬럼의 최대값 알아오기 === //
	@Override
	public int getGroupnoMax() {
		int max = sqlsession2.selectOne("yoosj.getGroupnoMax");
		return max;
	}


	// === 파일첨부가 없는 글쓰기 === //
	@Override
	public int add(BoardVO boardvo) {
		int n = sqlsession2.insert("yoosj.add", boardvo);
		return n;
	}


	// === 파일첨부가 있는 글쓰기 === //
	@Override
	public int add_withFile(BoardVO boardvo) {
		int n = sqlsession2.insert("yoosj.add_withFile", boardvo);
		return n;
	}


	// === 페이징 처리 x 검색어 x 전체 글목록 보여주기(수정 예정) === //
	@Override
	public List<BoardVO> boardListNoSearch() {
		List<BoardVO> boardList = sqlsession2.selectList("yoosj.boardListNoSearch");	
		return boardList;
	}


	// 총 게시물 건수(totalCount)구하기 - 검색이 있을 때와 없을 때로 나뉜다.
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = sqlsession2.selectOne("yoosj.getTotalCount", paraMap);		
		return n;
	}


	// 페이징 처리한 글목록 가져오기(검색이 있든지 없든지 모두 포함된 것)
	@Override
	public List<BoardVO> boardListSearchWithPaging(Map<String, String> paraMap) {
		List<BoardVO> boardList = sqlsession2.selectList("yoosj.boardListSearchWithPaging", paraMap);
		return boardList;
	}


	// 글 1개 조회하기
	@Override
	public BoardVO getView(Map<String, String> paraMap) {
		BoardVO boardvo = sqlsession2.selectOne("yoosj.getView", paraMap);
		return boardvo;
	}


	// 조회수 1 증가하기
	@Override
	public void setAddReadCount(String seq) {
		sqlsession2.update("yoosj.setAddReadCount", seq);	
	}

	// 글 수정하기
	@Override
	public int boardEdit(BoardVO boardvo) {
		int n = sqlsession2.update("yoosj.boardEdit", boardvo);
		return n;
	}


	// 글 삭제하기
	@Override
	public int boardDel(Map<String, String> paraMap) {
		int n = sqlsession2.delete("yoosj.boardDel", paraMap);
		return n;
	}

	
	// 댓글 쓰기(tbl_comment 테이블에 insert)
	@Override
	public int addComment(CommentVO_sj commentvo) {
		int n = sqlsession2.insert("yoosj.addComment", commentvo);
		return n;
	}


	// === tbl_board테이블에 commentCount 컬럼의 값 1증가(update) === //
	@Override
	public int updateCommentCount(String parentSeq) {
		int n = sqlsession2.update("yoosj.updateCommentCount", parentSeq);
		return n;
	}


	// 검색어 입력시 자동글 완성하기
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = sqlsession2.selectList("yoosj.wordSearchShow", paraMap);
		return wordList;
	}


	// 원게시물에 딸린 댓글들을 조회해오는 것
	@Override
	public List<CommentVO_sj> getCommentList(String parentSeq) {
		List<CommentVO_sj> commentList = sqlsession2.selectList("yoosj.getCommentList", parentSeq);
		return commentList;
	}


	// 회원 한 명의 정보 불러오기
	@Override
	public EmpVO_sj getViewEmpOne(Map<String, String> paraMap) {
		EmpVO_sj empvo = sqlsession2.selectOne("yoosj.getViewEmpOne", paraMap);
		return empvo;
	}


	// 회원 한 명의 정보 수정하기
	@Override
	public int empUpdate(EmpVO_sj emp) {
		int n = sqlsession2.update("yoosj.empUpdate", emp);
		return n;
	}  
	

}
