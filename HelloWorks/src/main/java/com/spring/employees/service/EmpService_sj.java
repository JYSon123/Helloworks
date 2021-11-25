package com.spring.employees.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.employees.model.BoardVO;
import com.spring.employees.model.CommentVO_sj;
import com.spring.employees.model.EmpVO_sj;
import com.spring.employees.model.FileManager_sj;
import com.spring.employees.model.InterEmpDAO_sj;
import com.spring.helloworks.model.EmpVO_KJH;

@Service
public class EmpService_sj implements InterEmpService_sj {

	@Autowired // 의존객체 주입
	private InterEmpDAO_sj dao;
	
	@Autowired // 의존객체 주입
	private FileManager_sj fileManager;

	// === 파일첨부가 없는 글쓰기 === //
	@Override
	public int add(BoardVO boardvo) {
		
		// === 원글쓰기인지 답변글쓰기인지 구분하기 시작 === 
		if("".equals(boardvo.getFk_seq()) ) {
			// 원글쓰기인 경우
			// groupno 컬럼의 값은 groupno 컬럼의 최대값(max)+1로 해야 한다.
			int groupno = dao.getGroupnoMax() + 1;
			boardvo.setGroupno(String.valueOf(groupno));
		}
		// === 원글쓰기인지 답변글쓰기인지 구분하기 끝 === 
		
		int n = dao.add(boardvo);
		
		return n;
	}

	
	// === 파일첨부가 있는 글쓰기 === //
	@Override
	public int add_withFile(BoardVO boardvo) {
		
		// 원글쓰기라면 tbl_board테이블의 groupno 컬럼의 값은 max+1로 해서 insert해야 하고
		// 답변글쓰기라면 넘겨받은 값 (boardvo)을 그대로 insert
		
		// === 원글쓰기인지 답변글쓰기인지 구분하기 시작 ===
		if("".equals(boardvo.getFk_seq()) ) {
			// 원글쓰기인 경우
			int groupno = dao.getGroupnoMax() + 1;
			boardvo.setGroupno(String.valueOf(groupno));
		}
		// === 원글쓰기인지 답변글쓰기인지 구분하기 끝 ===
		
		int n = dao.add_withFile(boardvo); // 첨부파일이 있는 경우
		return n;
	}


	// === 페이징 처리 x 검색어 x 전체 글목록 보여주기(수정 예정) === //
	@Override
	public List<BoardVO> boardListNoSearch() {
		
		List<BoardVO> boardList = dao.boardListNoSearch();
		
		return boardList;
	}


	// 총 게시물 건수(totalCount)구하기 - 검색이 있을 때와 없을 때로 나뉜다.
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = dao.getTotalCount(paraMap);	
		return n;
	}


	// 페이징 처리한 글목록 가져오기(검색이 있든지 없든지 모두 포함된 것)
	@Override
	public List<BoardVO> boardListSearchWithPaging(Map<String, String> paraMap) {
		List<BoardVO> boardList = dao.boardListSearchWithPaging(paraMap);
		return boardList;
	}


	// 글조회수 증가와 함께 글 한개 조회하기
	@Override
	public BoardVO getView(Map<String, String> paraMap, String login_empno) {
		BoardVO  boardvo = dao.getView(paraMap);
		
		if(login_empno != null &&
		   boardvo != null &&
		   !login_empno.equals(boardvo.getFk_empno())) {
			
			dao.setAddReadCount(boardvo.getSeq()); // 글조회수 1개 증가하기
			boardvo = dao.getView(paraMap);
		}
		return boardvo;
	}


	// 글조회수 증가 없는 글 한개 조회하기
	@Override
	public BoardVO getViewWithNoAddCount(Map<String, String> paraMap) {
		BoardVO  boardvo = dao.getView(paraMap);
		return boardvo;
	}


	// 글 수정하기
	@Override
	public int boardEdit(BoardVO boardvo) {
		int n = dao.boardEdit(boardvo);
		return n;
	}


	// 글 삭제하기
	@Override
	public int boardDel(Map<String, String> paraMap) {
		int n = dao.boardDel(paraMap); 
		
		// 파일첨부가 된 글이라면 DB에서 글 삭제가 성공된 후 첨부파일을 삭제해야 한다.
		if(n==1) { // 글이 성공적으로 삭제가 되었다면
			String fileName = paraMap.get("fileName");
			String path = paraMap.get("path");
			
			if(fileName != null && !"".equals(fileName)) { // DB에 파일이 있다면
				
				try {
					fileManager.doFileDelete(fileName, path);	
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		}
		////////////////////////////////////////////////////////////
		
		return n;
	}


	// 댓글쓰기(transaction처리)
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int addComment(CommentVO_sj commentvo) throws Throwable {
		
		int n=0, m=0;
		// 댓글쓰기(tbl_comment 테이블에 insert)
		n = dao.addComment(commentvo);
		
		
		if(n==1) {
			// tbl_board테이블에 commentCount 컬럼이 1증가(update)
		    m = dao.updateCommentCount(commentvo.getParentSeq());
		}
		
		return m;
	}

	
	// 검색어 입력시 자동글 완성하기
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = dao.wordSearchShow(paraMap);
		return wordList;
	}


	// 원게시물에 딸린 댓글들을 조회해오는 것
	@Override
	public List<CommentVO_sj> getCommentList(String parentSeq) {
		List<CommentVO_sj> commentList = dao.getCommentList(parentSeq);
		return commentList;
	}


	// 회원 한 명의 정보 불러오기
	@Override
	public EmpVO_sj getViewEmpOne(Map<String, String> paraMap) {
		EmpVO_sj empvo = dao.getViewEmpOne(paraMap);
		return empvo;
	}


	// 회원 한 명의 정보 수정하기
	@Override
	public int empUpdate(EmpVO_sj emp) {
		int n = dao.empUpdate(emp);
		return n;
	}
	

	
	
	
}
