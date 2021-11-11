package com.spring.employees.controller;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.poifs.filesystem.FileMagic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.employees.model.BoardVO;
import com.spring.employees.model.FileManager_sj;
import com.spring.employees.service.InterEmpService_sj;



@Controller
public class EmpController_sj {
	
	// === 의존객체 설정 === 
	@Autowired  
	private InterEmpService_sj service;
	
	// === 파일업로드 및 다운로드를 해주는 FileManager_sj클래스 의존객체 주입하기 === 
	@Autowired
	private FileManager_sj fileManager;
	
	
	// 보드 jsp 확인용 메소드
	@RequestMapping(value="/addtest.hello2")
    public String board(HttpServletRequest request) {
      
       return "board_sj/add.tiles1";   
    }
	
	
	// 보드 jsp 확인용 메소드
	@RequestMapping(value="/listtest.hello2")
    public String board2(HttpServletRequest request) {
      
       return "board_sj/list.tiles1";   
	 }
		
	
	
	// === 게시판 글쓰기 폼페이지 요청 === //
	@RequestMapping(value="/add.hello2")
	public ModelAndView requiredLogin_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
	    // getCurrentURL(request); // 로그인 또는 로그아웃을 했을 때 현재 보이던 페이지로 돌아가는 메소드 호출
		
		String fk_seq = request.getParameter("fk_seq");
		String groupno = request.getParameter("groupno");
		String depthno = request.getParameter("depthno");
		String subject = request.getParameter("subject");
		
		if(fk_seq == null) { // 원글번호가 null이라면(답변글이 아닌 원글작성이라면)
			fk_seq = "";     // 공백을 넣어 비교한 다음  원글 or 답변글 작성 폼이 나오도록 하자	
		}
		
		mav.addObject("fk_seq", fk_seq);
		mav.addObject("groupno", groupno);
		mav.addObject("depthno", depthno);
		mav.addObject("subject", subject);
		
		mav.setViewName("board_sj/add.tiles1");

		return mav;
	}
	
	
	// === 게시판 글쓰기 완료 요청 === //
	@RequestMapping(value="/addEnd.hello2", method = {RequestMethod.POST})
	public ModelAndView addEnd(Map<String, String> paraMap, ModelAndView mav, BoardVO boardvo, MultipartHttpServletRequest mrequest ) {
	
		// === 사용자의 글에 첨부파일이 있는 경우 시작 === //
		MultipartFile attach = boardvo.getAttach();
		
		if( !attach.isEmpty()) { // attach(첨부파일)가 비어있지 않다면(첨부파일이 있는 경우라면)
			
			HttpSession session = mrequest.getSession();
			String root = session.getServletContext().getRealPath("/"); // 경로를 알려주는 메소드
			
			
			String path = root + "resources" + File.separator + "files";
			                                 //File.seaparator는 운영체제에서 사용하는 폴더와 파일의 구분자
			
			// System.out.println("~~~ 확인용 path => " + path);
			// ~~~ 확인용 path => C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\HelloWorks\resources\files
			
			// 2. 파일첨부를 위한 변수의 설정 및 초기화한 후 파일 올리기
			String newFileName = ""; // WAS(톰캣)의 디스크에 저장될 파일명
			
			byte[] bytes = null;     // 첨부파일의 내용물을 담는 것
			
			long fileSize = 0;       // 첨부파일의 크기
			
			try {
				bytes = attach.getBytes(); // 첨부파일의 내용물을 읽어오는 것
				
				newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
				// 첨부되어진 파일을 업로드 하도록 하는 것이다.
				
				
			    // 3. BoardVO boardvo에 fileName값과 orgFilename값과 fileSize값을 넘겨주기
				
				boardvo.setFileName(newFileName);
				
				boardvo.setOrgFilename(attach.getOriginalFilename());
				
				fileSize = attach.getSize(); // 첨부파일의 크기
				boardvo.setFileSize(String.valueOf(fileSize));
				
				
			} catch(Exception e) {
				e.printStackTrace();
			}	
			
		}
		// === 사용자의 글에 첨부파일이 있는 경우 끝 === //
		
		
		int n = 0;
		
		if( attach.isEmpty()) { 
			// 첨부파일이 없는 경우라면
			n = service.add(boardvo);
		}
		
		else { 
			// 첨부파일이 있는 경우라면
			n = service.add_withFile(boardvo);
		}
		
		mav.setViewName("redirect:/list.hello2");
		// list.hello2페이지로 redirect(페이지이동)해라는 말이다.
		
		return mav;
	
	}
	

	// === 글목록 보기 페이지 요청 === //
	@RequestMapping(value="/list.hello2")
	public ModelAndView list(ModelAndView mav, HttpServletRequest request) {
		
		List<BoardVO> boardList = null;		
		boardList = service.boardListNoSearch();
		
		// 글조회수 증가는 새로고침 했을 때는 적용되지 않도록 해야 한다. session이용.
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes"); // 조회수 증가를 허락하겠다.
	
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"suject".equals(searchType) && !"name".equals(searchType)) ) { // 유저가 장난친 경우
			searchType = "";
		}
		
		if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty() ) { // 검색어 자체가 아예 없다면
			searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<>();
		
		mav.addObject("boardList", boardList);
		mav.setViewName("board_sj/list.tiles1");
		
		return mav;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
