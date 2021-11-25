package com.spring.employees.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.employees.model.BoardVO;
import com.spring.employees.model.CommentVO_sj;
import com.spring.employees.model.FileManager_sj;
import com.spring.employees.service.InterEmpService_sj;
import com.spring.helloworks.common_KJH.MyUtil;
import com.spring.helloworks.model.EmpVO_KJH;



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
		
		// 글조회수 증가는 새로고침 했을 때는 적용되지 않도록 해야 한다. session이용.
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes"); // 조회수 증가를 허락하겠다.
	
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"subject".equals(searchType) && !"name".equals(searchType)) ) { // 유저가 장난친 경우
			searchType = "";
		}
		
		if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty() ) { // 검색어 자체가 아예 없다면
			searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<>();	
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		// 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
		// 총 게시물 건수(totalCount)는 검색조건이 있을 때와 없을 때로 나뉘어진다.
		int totalCount = 0;		   // 총 게시물 건수
		int sizePerPage = 10;	   // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0; // 현재 보여주는 페이지번호로서 초기치로는 1페이지로 설정
		int totalPage = 0;         // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)
		
		int startRno = 0;		   // 시작 행번호
		int endRno = 0;            // 끝 행번호
		
		// 총 게시물 건수(totalCount)
		totalCount = service.getTotalCount(paraMap);
		
		// 만약에 총 게시물 건수(totalCount)가 127개라면
		// 총 페이지수(totalPage)는 13개가 되어야 한다.
		
		totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
		
		
		if(str_currentShowPageNo == null) {
			// 게시판에 보여지는 초기화면
			currentShowPageNo = 1;
		}
		else {
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
					currentShowPageNo = 1;
				}
			}catch(NumberFormatException e) {
				currentShowPageNo = 1;
			}
		}
		
		startRno = ((currentShowPageNo - 1) * sizePerPage) + 1;
		endRno = startRno + sizePerPage - 1;
		
		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));
		
		boardList = service.boardListSearchWithPaging(paraMap);
		// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것)
		
		// 아래는 검색대상 컬럼과 검색어를 유지키시기 위한 것이다.
		if(!"".equals(searchType) && !"".equals(searchWord)) {
			mav.addObject("paraMap", paraMap);
		}
		
		// === 페이지바 만들기 === //
		int blockSize = 10;
		// blockSize는 1개블럭당 보여지는 페이지번호의 개수이다.
		
		int loop = 1;
		// loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수까지만 증가하는 용도
		
		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
		
		
		String pageBar = "<ul style='list-style:none;'>";
		String url = "list.hello2";
		
		// === [맨처음][이전] 만들기 === 
		if(pageNo != 1) {
			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
        	pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>"; 
		}
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
			
			if(pageNo == currentShowPageNo) {
				pageBar += "<li style='display:inline-block; width:30px; font-size:13pt; border:solid 1px gray; padding:2px 4px;'>"+pageNo+"</li>";
			}
			else {
				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
			}
			
			loop ++;
			pageNo++;
		} // end of while---------------------------
		
		
		// === [다음][마지막] 만들기 ===
		if(pageNo <= totalPage) {
			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
        	pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>"; 
		}
		
		
		pageBar += "</ul>";
		
		mav.addObject("pageBar", pageBar);
		
		// 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후
		// 사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해
		// 현재 페이지 주소를 뷰단으로 넘겨준다.
		
		String gobackURL = MyUtil.getCurrentURL(request);
		
	    mav.addObject("gobackURL", gobackURL);
		
		// === 페이징 처리를 한 검색어가 있는 전체 글목록 보여주기 끝 === //
		
		mav.addObject("boardList", boardList);
		mav.setViewName("board_sj/list.tiles1");
		
		return mav;
		
	}
	
	
	// === 글1개를 보여주는 페이지 요청 === //
	@RequestMapping(value="/view.hello2")
	public ModelAndView view(ModelAndView mav, HttpServletRequest request) {
		
		
		// 조회하고자 하는 글번호 받아오기
		String seq = request.getParameter("seq");
		
		// 글목록에서 검색되어진 글
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		if(searchType == null) {
			searchType = "";
		}
		
		if(searchWord == null) {
			searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		mav.addObject("searchType", searchType);
		mav.addObject("searchWord", searchWord);
		
		// 페이징 처리가 되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후에
		// 사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해
		// 현재 페이지 주소를 뷰단으로 넘겨준다.
		String gobackURL = request.getParameter("gobackURL");
		
		if(gobackURL != null && gobackURL.contains(" ")) {
			gobackURL = gobackURL.replaceAll(" ", "&");
			// 이전글제목, 다음글제목을 클릭했을 때 돌아갈 페이지 주소를 올바르게 만들어주기 위함
		}
		
		mav.addObject("gobackURL", gobackURL);
		/////////////////////////////////////////////////////////
		
		
		try {
			Integer.parseInt(seq);
			
			HttpSession session = request.getSession();
			EmpVO_KJH loginEmp = (EmpVO_KJH) session.getAttribute("loginEmp");
			
			String login_empno = null;
			
			if(loginEmp != null) {
				login_empno = loginEmp.getEmpno();
			}
			
			BoardVO boardvo = null;
			
			if("yes".equals(session.getAttribute("readCountPermission"))) {
				// 글목록보기를 클릭한 다음에 특정글을 조회해온 경우
				
				boardvo = service.getView(paraMap, login_empno);
				// 글조회수 증가와 함께 글 한개를 조회해주는 것
				
				session.removeAttribute("readCountPermission");
				// session에 저장된 readCountPermission를 삭제한다.
			}
			else {
				// 웹브라우저에서 새로고침을 클릭한 경우
				
				boardvo = service.getViewWithNoAddCount(paraMap);
				// 조회수 증가가 없는 글 한개 조회
			}
			mav.addObject("boardvo", boardvo);
		} catch(NumberFormatException e) {
			
		}
		
		mav.setViewName("board_sj/view.tiles1");

		return mav;
		
	}
	
	
	// === 글수정 페이지 요청 === //
	@RequestMapping(value="/boardEdit.hello2")
	public ModelAndView requiredLogin_boardEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			
		// 글 수정해야 할 글번호 가져오기
		String seq = request.getParameter("seq");
		
		// 수정할 글 한개 내용 가져오기
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);
		
		paraMap.put("searchType", "");
		paraMap.put("searchWord", "");
		
		BoardVO boardvo = service.getViewWithNoAddCount(paraMap);
		// 조회수 증가 없는 글 조회
		
		HttpSession session = request.getSession();
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		
		if(!loginEmp.getEmpno().equals(boardvo.getFk_empno())) {
			String message = "다른 사용자의 글은 수정이 불가합니다.";
			String loc = "javascript:history.back()";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			mav.setViewName("msg_KJH");
		}
		else {
			// 자신의 글을 수정할 경우
			// 가져온 한개의 글을 수정할 폼이 있는 view단으로 보내준다.
			mav.addObject("boardvo", boardvo);
			mav.setViewName("board_sj/boardEdit.tiles1");
		}
		
		return mav;		
	}
	
	
	// === 글수정 페이지 완료하기  === //
	@RequestMapping(value="boardEditEnd.hello2", method = {RequestMethod.POST})
	public ModelAndView boardEditEnd(ModelAndView mav, BoardVO boardvo, HttpServletRequest request) {
		
		/*
		  글을 수정하려면 원본글의 글암호와 수정시 입력해준 암호가 일치할 때만
		  글 수정이 가능하도록 해야 한다.
		 */
		int n = service.boardEdit(boardvo); // 성공하면 1값이 들어온다.
		// n이 1이라면 정상적으로 변경됨
		// n이 0이라면 글암호가 틀린 경우
		
		if(n == 0) { // 글암호가 틀린 경우
			mav.addObject("message", "암호가 일치하지 않아 글 수정이 불가합니다.");	
		}
		else { // 글암호 일치, 수정 성공
			mav.addObject("message", "성공적으로 수정되었습니다.");
		}
		
		mav.addObject("loc", request.getContextPath()+"/view.hello2?seq="+boardvo.getSeq());
		
		mav.setViewName("msg_KJH");
		
		return mav;
	}
	
	
	// === 글삭제 페이지 요청 ===  // 
	@RequestMapping(value="/boardDel.hello2")
	public ModelAndView requiredLogin_boardDel(HttpServletRequest request, HttpServletResponse responser, ModelAndView mav) {
		
		String seq = request.getParameter("seq");
		
		// 삭제해야 할 글 한개 내용 가져오기
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);
		
		paraMap.put("searchType", "");
		paraMap.put("searchWord", "");
		
		BoardVO boardvo = service.getViewWithNoAddCount(paraMap);
		// 조회수 증가 없는 글 한개 조회하기
		
		HttpSession session  = request.getSession();
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		
		// 나중에 인사팀은 삭제가 가능하도록 수정해야 함
		if(!loginEmp.getEmpno().equals(boardvo.getFk_empno())) {
			String message = "다른 사용자의 글 삭제는 불가합니다.";
			String loc = "javascript:history.back()";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			mav.setViewName("msg_KJH");	
		}
		else {
			// 자신의 글을 삭제할 경우
			// 글작성시 입력해준 글암호와 일치하는지 여부를 알아오도록 암호를 입력받아주는 del.jsp페이지를 띄운다.
			mav.addObject("seq", seq); // 글번호를 넘겨주자.
			mav.setViewName("board_sj/boardDel.tiles1");
		}
	
		return mav;
	}
	
	
	// === 글삭제 페이지 완료하기 === //
	@RequestMapping(value="/boardDelEnd.hello2", method = {RequestMethod.POST})
	public ModelAndView boardDelEnd(ModelAndView mav, HttpServletRequest request) {
		
		/*
		 	글을 삭제하려면 원본글의 글암호와 삭제시 입력해준 암호가 일치할 때만
		 	글 삭제가 가능하도록 해야 한다.
		 */
		String seq = request.getParameter("seq");
		String pw = request.getParameter("pw");
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);
		paraMap.put("pw", pw);
		
		// === 파일첨부가 된 글이라면 삭제시 첨부파일을 먼저 삭제해야 한다 시작 === //
		paraMap.put("searchType", "");
		paraMap.put("searchWord", "");
		
		
		BoardVO boardvo = service.getViewWithNoAddCount(paraMap);
		String fileName = boardvo.getFileName(); // 삭제할 파일 이름을 가져와야 한다
		
		if(fileName != null && !"".equals(fileName)) { // DB에 파일이 있다면
			
			HttpSession session = request.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";
			
			paraMap.put("path", path);         // 삭제해야 할 파일이 저장된 경로
			paraMap.put("fileName", fileName); // 삭제해야 할 파일명		
		}
		// === 파일첨부가 된 글이라면 삭제시 첨부파일을 먼저 삭제해야 한다 끝 === //
		
		int n = service.boardDel(paraMap); // paraMap을 service로 보낸다
		// n이 1이라면  정상적으로 삭제됨
		// n이 0이라면 글 삭제에 필요한 글암호가 틀린 경우
		
		
		if(n == 0) {
			mav.addObject("message", "암호가 일치하지 않아 글 삭제가 불가합니다.");
			
			// 글삭제 실패시 글 한개를 보여주면서 "검색된결과목록보기" 버튼 클릭시 올바르게 가기 위해서 gobackURL=/list.hello2 를 추가해준다.
			mav.addObject("loc", request.getContextPath()+"/view.hello2?seq="+seq+"&gobackURL=/list.hello2");// == request.setAttribute("loc", loc); 		
		}
		else { // 글삭제 성공
			mav.addObject("message", "글 삭제를 성공적으로 완료했습니다.");
			mav.addObject("loc", request.getContextPath()+"/list.hello2");
		}
		
		mav.setViewName("msg_KJH");
		
		return mav;
	}
	
	
	
	// === 첨부파일 다운로드 받기 === //
	@RequestMapping(value="/boarddownload.hello2")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		
		String seq = request.getParameter("seq");
		// 첨부파일이 있는 글번호
		
		// fileName과 orgFilename 값도 DB에서 가져와야 한다.
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);
		paraMap.put("searchType", "");
		paraMap.put("searchWord", "");
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		
		try {
			Integer.parseInt(seq);
			
			BoardVO boardvo = service.getViewWithNoAddCount(paraMap);
			
			if(boardvo == null || (boardvo != null && boardvo.getFileName() == null)) {
				out = response.getWriter();
				// 웹브라우저상에 메시지를 쓰기 위한 객체 생성
				
				out.println("<script type='text/javascript'>alert('존재하지 않는 글 번호이거나 첨부파일이 없으므로 파일 다운로드가 불가합니다.'); history.back(); </script>");
				
				return; // 종료
			}
			else {
				String fileName = boardvo.getFileName();
				// WAS(톰캣)디스크에 저장된 파일명이다.
				
				String orgFileName = boardvo.getOrgFilename();
				// 원본 파일명 : 다운로드시 보여줄 파일명
				
				// 첨부파일이 저장되어 있는 WAS의 디스크 경로명을 알아와야만 다운로드 해줄 수 있다.
				HttpSession session = request.getSession();
				String root = session.getServletContext().getRealPath("/");
				
				String path = root + "resources" + File.separator + "files";
				
				
				boolean flag = fileManager.doFileDownload(fileName, orgFileName, path, response);
				// flag값이 true로 받아오면 다운로드 성공
				// flag값이 flase로 받아오면 다운로드 실패
				
				if(flag == false) {
					// 다운로드가 실패할 경우 메시지를 띄워준다.
					
					out = response.getWriter();
					// 웹브라우저상에 메시지를 쓰기 위한 객체 생성
					
					out.println("<script type='text/javascript'>alert('파일다운로드가 실패되었습니다!!'); history.back(); </script>");
				}
			}			
		} catch(NumberFormatException e) {
			
			try {
				out = response.getWriter();
				// 웹브라우저상에 메시지를 쓰기 위한 객체 생성
				
				 out.println("<script type='text/javascript'>alert('존재하지 않는 글 번호이므로 파일 다운로드가 불가합니다.'); history.back(); </script>");		
			} catch(IOException e1) {
				
			}	
		} catch(IOException e2) {
			
		}

	}
	
	
	// === 댓글쓰기(Ajax) === //
	@ResponseBody
	@RequestMapping(value="/addComment.hello2", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String addComment(CommentVO_sj commentvo) {
		
		int n = 0;
		
		try {			
			n = service.addComment(commentvo);
		} catch(Throwable e) {
			e.printStackTrace(); // 실패이면 콘솔에 찍어라
		}
		
		// 댓글쓰기(insert) 및 원게시물(tbl_board 테이블)에 댓글의 개수 증가(update 1씩 증가)하기 
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("n", n);
		
		return jsonObj.toString(); // @ResponseBody를 넣어주면 return값이 웹페이지에 그대로 찍혀서 나온다.
	}
	
	
	
	// === 검색어 입력시 자동글 완성하기 === //
	@ResponseBody
	@RequestMapping(value="/wordSearchShow.hello2", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String wordSearchShow(HttpServletRequest request) {
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		List<String> wordList = service.wordSearchShow(paraMap);
		
		JSONArray jsonArr = new JSONArray();
		
		if(wordList != null) {
			for(String word : wordList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("word", word);
				
				jsonArr.put(jsonObj);	
			}	
		}	
		return jsonArr.toString();
	}
	
	
	
	
	
	
}
