package com.spring.employees.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.mail.Session;
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

import com.spring.employees.model.BreakCalendarVO_jy;
import com.spring.employees.model.DocumentVO_jy;
import com.spring.employees.model.FileManager_jy;
import com.spring.employees.service.InterEmpService_jy;
import com.spring.helloworks.common_KJH.Sha256;
import com.spring.helloworks.model.EmpVO_KJH;

@Controller
public class EmpController_jy {
	
	
	@Autowired  // 의존객체 설정
	private InterEmpService_jy service;
	
	// === 파일업로드 및 다운로드를 해주는 FileManager_sj클래스 의존객체 주입하기 === 
	@Autowired
	private FileManager_jy fileManager;

	
	// 메인 페이지를 보여주는 메소드
	@RequestMapping(value="/documentMain.hello2")
    public ModelAndView requiredLogin_documentMain(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		HttpSession session = request.getSession();
		 
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		
		if(loginEmp == null) {
			mav.addObject("message", "먼저 로그인을 해주세요!");
			
			mav.addObject("loc", request.getContextPath()+"/login.hello2");

			mav.setViewName("msg_JY");
			
			return mav;
		}
		
		
		String empid = loginEmp.getEmpid();
		
	    if(!checkDepartment(request,"10") || !empid.equals("admin")) { // 인사팀이나 관리자가 아닌 경우
	    	
	    	mav.addObject("loc", request.getContextPath()+"/myDocumentlist.hello2");

			mav.setViewName("pageReturn_JY");

			return mav;
		   
	    }
		
	    else { // 인사팀이나 관리자일 경우
	    	mav.addObject("loc", request.getContextPath()+"/documentlist.hello2");

			mav.setViewName("pageReturn_JY");

			return mav;
	    	
	    }
	    
	    
	    
    }
	
	
	
	// 기안하기 폼페이지 요청
	@RequestMapping(value="/write.hello2")
 // public ModelAndView requiredLogin_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {	
		
	//	getCurrentURL(request); // 로그인 또는 로그아웃을 했을 때 현재 보이던 그 페이지로 그대로 돌아가기 위한 메소드 호출
		
		HttpSession session = request.getSession();
		 
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		
		if(loginEmp == null) {
			mav.addObject("message", "먼저 로그인을 해주세요!");
			
			mav.addObject("loc", request.getContextPath()+"/login.hello2");

			mav.setViewName("msg_JY");
			
			return mav;
		}
		
		
		mav.setViewName("document/add.tiles1");

		return mav;
	}
	
	
	// 기안하기 완료하기
	@RequestMapping(value="/addEndjy.hello2", method= {RequestMethod.POST})
	public ModelAndView documentaddend(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, DocumentVO_jy dovo, MultipartHttpServletRequest mrequest) {
		
		String newFileName = "";
		// WAS(톰캣)의 디스크에 저장될 파일명
		
		byte[] bytes = null;
		// 첨부파일의 내용물을 담는 것
		
		long fileSize = 0;
		// 첨부파일의 크기 

		String orgFilename  = "";
		
		MultipartFile attach = dovo.getAttach();
    	
    	if( !attach.isEmpty() ) {
    		// attch(첨부파일)가 비어있지 않으면(즉, 첨부파일이 있는 경우라면)
    		
    		/*
    		 	1. 사용자가 보낸 첨부파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다.
    		 	>>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
    		 		우리는 was의 wabapp/resources/files 라는 폴더는 지정해준다.
    		 		조심할 것은 Package Explorer 에서  files 라는 폴더를 만드는 것이 아니다. 
    		*/
    		// was의 webapp의 절대경로를 알아와야한다.
    		HttpSession session = mrequest.getSession();
    		String root = session.getServletContext().getRealPath("/");
    		
    		// System.out.println("~~~~ 확인용 webapp의 절대경로 => " + root);
    		// ~~~~ 확인용 webapp의 절대경로 => C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\HelloWorks\
    		
    		String path = root + "resources" + File.separator + "files";
    		/* File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다.
		              운영체제가 Windows 이라면 File.separator 는  "\" 이고,
		              운영체제가 UNIX, Linux 이라면  File.separator 는 "/" 이다. 
    		 */
    		
    		// path 가 첨부파일이 저장될 WAS(톰캣)의 폴더가 된다.
    		// System.out.println("~~~~ 확인용 path => " + path);
    		// ~~~~ 확인용 path => C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\HelloWorks\resources\files
    		

    			
    			try {
					bytes = attach.getBytes();
					// 첨부파일의 내용물을 읽어오는 것
					
					newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
					// 첨부되어진 파일을 업로드 하도록 하는 것이다. 
		            // attach.getOriginalFilename() 은 첨부파일의 파일명(예: 강아지.png)이다.
					
					// System.out.println(">>> 확인용 newFileName => " + newFileName);
					// >>> 확인용 newFileName => 202111081126401738292352399700.jpg
					
				/*
					 3. BoardVO boardvo 에 filename 값과 orgFilename 값과 fileSize 값을 넣어주기  
				*/
					
					dovo.setFileName(newFileName);
					// WAS(톰캣)에 저장될 파일명(202111081126401738292352399700.jpg)
					
					dovo.setOrgFilename(attach.getOriginalFilename());
					// 게시판 페이지에서 첨부된 파일(강아지.png)를 보여줄때 사용.
					// 또한 사용자가 파일을 다운로드 할때 사용되어지는 파일명으로 사용.
					
					orgFilename  = dovo.getOrgFilename();
					
					fileSize = attach.getSize(); // 첨부파일의 크기(단위는 byte임)
					
					dovo.setFileSize(String.valueOf(fileSize));
					
    			} catch (Exception e) {
					e.printStackTrace();
				}
		
    	}
    	
    	
		int documentaddend = 0;
		
		String fk_empno =  request.getParameter("fk_empno");
		String fk_deptnum = request.getParameter("fk_deptnum");
		String name = request.getParameter("empname");
		String status = request.getParameter("documentKind"); // 왜 이름을 바꾸면 안될까? 하루종일 붙들고있었네...
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		// 문서의 종류가 연차일때만 사용하게 된다.
		String breakkind = null;
		String breakstart = null;
		String breakend = null;
		
		
		// 문서의 종류가 연차일 경우
		if(status.equals("1")) {
			breakkind = request.getParameter("breakkind");
			breakstart = request.getParameter("breakstart");
			breakend = request.getParameter("breakend");
		}
		

		
		Map<String,String> paraMap = new HashMap<>();
	    paraMap.put("fk_empno", fk_empno);
	    paraMap.put("fk_deptnum", fk_deptnum);
	    paraMap.put("name", name);
	    paraMap.put("status", status);
	    paraMap.put("subject", subject);
	    paraMap.put("content", content);

		// 문서의 종류가 연차일 경우
		if(status.equals("1")) {
			paraMap.put("breakkind", breakkind);
			paraMap.put("breakstart", breakstart);
			paraMap.put("breakend", breakend);
		}
	    
		
		if( !attach.isEmpty() ) {
			paraMap.put("fileName", newFileName);
			paraMap.put("orgFilename", orgFilename);
			paraMap.put("fileSize", String.valueOf(fileSize));
			
			// 파일이 있는 경우 기안하기를 완료해주기
			documentaddend = service.documentaddend_file(paraMap);
			
			mav.addObject("loc", request.getContextPath()+"/myDocumentlist.hello2");

			mav.setViewName("pageReturn_JY");
			
			return mav;
		}
		

	    // 작성한 문서를 테이블에 insert 시켜주는 메소드
	    documentaddend = service.documentaddend(paraMap);
	    
	    mav.addObject("loc", request.getContextPath()+"/myDocumentlist.hello2");

		mav.setViewName("pageReturn_JY");

		return mav;

	}
	

	// 파일을 다운로드 하기
    @RequestMapping(value="/download_document.hello2")
    public void download(HttpServletRequest request, HttpServletResponse response) {
    	
    	String doument_seq = request.getParameter("doument_seq");
    	// 첨부파일이 있는 글번호 
    	
    	/*
    	 	첨부파일이 있는 글번호에서 
    	 	202111081246371743090024821700.jpg 와 같은
    	 	이러한 fileName 값을 DB 에서 가져와야 한다.
    	  	또한 orgFilename 값도 DB 에서 가져와야 한다.
    	*/
    
    	Map<String, String> paraMap = new HashMap<>();
    	paraMap.put("doument_seq", doument_seq);
    	/*
    	paraMap.put("searchType", "");
    	paraMap.put("searchWord", "");
    	*/
    	
    	
    	response.setContentType("text/html; charset=UTF-8");
    	PrintWriter out = null;
    	
    	try {
    		Integer.parseInt(doument_seq);
    		
	    	DocumentVO_jy dovo = service.viewDocument(paraMap);
	    	
	    	if(dovo == null || (dovo != null && dovo.getFileName() == null)) {
		    	out = response.getWriter();
		    	// 웹브라우저상에 메시지를 쓰기 위한 객체 생성
		    	out.println("<script type='text/javascript'> alert('존재하지 않는 글번호 이거나 첨부파일이 없으므로 파일 다운로드가 불가합니다!!'); history.back();</script>");
		    	
		    	return;  // 종료
	    	}
	    	
	    	else {
	    		String fileName = dovo.getFileName();
	    		// 202111081246371743090024821700.jpg 이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다.
	    		
	    		String orgFilename = dovo.getOrgFilename();
	    		// berkelekle심플V넥02.jp  다운로드시 보여줄 파일명
	    		
	    		// 첨부파일이 저장되어 있는 WAS(톰캣)의 디스크 경로명을 알아와야만 다운로드를 해줄수 있다. 
	            // 이 경로는 우리가 파일첨부를 위해서 /addEnd.action 에서 설정해두었던 경로와 똑같아야 한다.
	    		// was의 webapp의 절대경로를 알아와야한다.
	    		HttpSession session = request.getSession();
	    		String root = session.getServletContext().getRealPath("/");
	    		
	    	//	System.out.println("~~~~ 확인용 webapp의 절대경로 => " + root);
	    		// ~~~~ 확인용 webapp의 절대경로 => C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\
	    		
	    		String path = root + "resources" + File.separator + "files";
	    		/* File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다.
			              운영체제가 Windows 이라면 File.separator 는  "\" 이고,
			              운영체제가 UNIX, Linux 이라면  File.separator 는 "/" 이다. 
	    		 */
	    		
	    		// path 가 첨부파일이 저장될 WAS(톰캣)의 폴더가 된다.
	    //		System.out.println("~~~~ 확인용 path => " + path);
	    		// ~~~~ 확인용 path => C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\files
	    		
	    		// **** file 다운로드 하기 **** //
	    		boolean flag = fileManager.doFileDownload(fileName, orgFilename, path, response);
	    		// flag 값이 ture 로 받아오면 다운로드 성공을 말하고,
	    		// flag 값이 flase 로 받아오면 다운로드 실패를 말한다.
	    		
	    		if(flag == false) {
	    			// 다운로드가 실패할 경우 메시지를 띄워준다.
	    			
	    			out = response.getWriter();
			    	// 웹브라우저상에 메시지를 쓰기 위한 객체 생성
			    	
	    			out.println("<script type='text/javascript'> alert('파일 다운로드가 실패되었습니다!!'); history.back();</script>");
	    			
	    		}
	    		
	    	}
	    	
    	} catch(NumberFormatException e) {
    		
    		try {
	    		out = response.getWriter();
		    	// 웹브라우저상에 메시지를 쓰기 위한 객체 생성
		    	
	    		out.println("<script type='text/javascript'> alert('존재하지 않는 글번호 이므로 파일 다운로드가 불가합니다!!'); history.back();</script>");
    		
    		} catch(IOException e1) {
    			
    		}
    		
    	} catch(IOException e2) {
	
    	}
    	
    }
	
	

	// 기안한 문서 리스트 보여주기 (관리자용)
	@RequestMapping(value="/documentlist.hello2")
    public ModelAndView documentlist(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
       
		HttpSession session = request.getSession();
		 
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		 
		if(loginEmp == null) {
			mav.addObject("message", "먼저 로그인을 해주세요!");
			
			mav.addObject("loc", request.getContextPath()+"/login.hello2");

			mav.setViewName("msg_JY");
			
			return mav;
		}
		
		String empid = loginEmp.getEmpid();
		
		
		
		
		
		if(checkDepartment(request,"30")) {
			   //  getCurrentURL(request); // 로그인 또는 로그아웃을 했을 때 현재 보이던 그 페이지로 그대로 돌아가기 위한 메소드 생성 ===  
	        
						List<DocumentVO_jy> documentList = null;
						
				       // === #114. 페이징 처리를 한 검색어가 있는 전체 문서목록 보여주기 시작 === // 
				        String searchType = request.getParameter("searchType");
				        String searchWord = request.getParameter("searchWord");
				        String str_currentShowPageNo = request.getParameter("currentShowPageNo");
				        
				        
				        if(searchType == null) {
				        	searchType = "";
				        }
				        if(searchWord == null) {
				        	searchWord = "";
				        }
				        
				        
				        
				        Map<String,String> paraMap = new HashMap<>();
				        paraMap.put("searchType", searchType);
				        paraMap.put("searchWord", searchWord);
				        
				        
				        // 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
				        // 총 게시물 건수(totalCount)는 검색조건이 있을 때와 없을 때로 나뉘어진다.
				        int totalCount = 0;        // 총 게시물 건수
				        int sizePerPage = 10;      // 한 페이지당 보여줄 게시물 건수
				        int currentShowPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
				        int totalPage = 0;         // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바 )
				        
				        int startRno = 0;          // 시작 행번호
				        int endRno = 0;            // 끝 행번호
				        
				        // 총 게시물 건수(totalCount)
				        totalCount = service.getTotalCount(paraMap);
				       //  System.out.println("~~~ 확인용 totalCount=> "+ totalCount);
				        
				        // 만약에 총 게시물 건수(totalCount)가 127개이라면
				        // 총 페이지수(totalPage)는 13개 되어야 한다.
				        
				        totalPage = (int) Math.ceil((double)totalCount/sizePerPage); // (double)127/10 ==> 12.7 ==> Math.ceil(12.7) ==> 13.0 ==> (int)13.0 ==> 13
				        
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
				        
				        // **** 가져올 게시글의 범위를 구한다.(공식임!!!) **** 

				        
				        startRno = ( ( currentShowPageNo -1 ) * sizePerPage ) +1;
				        endRno = startRno + sizePerPage - 1;
				        
				        paraMap.put("startRno", String.valueOf(startRno));
				        paraMap.put("endRno", String.valueOf(endRno));
				        
				        documentList = service.documentListSearchWithPaging(paraMap);
				        // 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것)
				        
				        // 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
				        if(!"".equals(searchType) && !"".equals(searchWord)) {
				           mav.addObject("paraMap",paraMap);
				        }
				        mav.addObject("paraMap",paraMap);
				        
				        // === #121. 페이지바 만들기 === //
				        int blockSize = 10;
				        // blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 개수 이다.
				        
				        int loop = 1;
				        /*
				           loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수[ 지금은 10개(== blockSize) ] 까지만 증가하는 용도이다.
				        */
				        
				        int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
				        // *** !! 공식이다. !! *** //

				        
				        String pageBar = "<ul style='list-style:none;'>"; 
				        String url = "documentlist.hello2";
				        
				        // === [맨처음][이전] 만들기 === 
				        if(pageNo != 1) {     
				           pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
				           pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";     
				        }        
				        
				        while( !(loop > blockSize || pageNo > totalPage) ) {
				           
				           if(pageNo == currentShowPageNo) {
				              pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
				           }
				           else {
				              pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
				           }
				           
				           loop++;
				           pageNo++;
				           
				        }// end of while------------------------
				        
				              
				        // === [다음][마지막] 만들기 === 
				        if(pageNo <= totalPage) {     
				           pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
				           pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";     
				        }
				        
				        
				        pageBar += "</ul>";
				        
				        mav.addObject("pageBar", pageBar);
				       
				       
				       // === #123. 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후 
				        //            사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해
				        //           현재 페이지 주소를 뷰단으로 넘겨준다. === //
				   //     String gobackURL = MyUtil.getCurrentURL(request);
				     // System.out.println("~~~~ 확인용 gobackURL => " + gobackURL);
				        // ~~~~ 확인용 gobackURL => /list.action?searchType=subject&searchWord=java
				        // ~~~~ 확인용 gobackURL => /list.action?searchType=subject&searchWord=%EC%9E%85%EB%8B%88%EB%8B%A4&currentShowPageNo=15
				        
				      //  mav.addObject("gobackURL", gobackURL);
				        
				        // === 페이징 처리를 한 검색어가 있는 전체 글목록 보여주기 끝 === //
				        /////////////////////////////////////////////////////////////
				        
				        mav.addObject("documentList", documentList);
				        mav.setViewName("document/list2.tiles1");
				       // /WEB-INF/views/tiles1/board/list.jsp 파일을 생성한다.
				       
				       return mav;
				    	
			
			
		}
		
	    else if (empid.equals("admin")){ // 인사팀이나 관리자일 경우
	    //  getCurrentURL(request); // 로그인 또는 로그아웃을 했을 때 현재 보이던 그 페이지로 그대로 돌아가기 위한 메소드 생성 ===  
	        
			List<DocumentVO_jy> documentList = null;
			
	       // === #114. 페이징 처리를 한 검색어가 있는 전체 문서목록 보여주기 시작 === // 
	        String searchType = request.getParameter("searchType");
	        String searchWord = request.getParameter("searchWord");
	        String str_currentShowPageNo = request.getParameter("currentShowPageNo");
	        
	        
	        if(searchType == null) {
	        	searchType = "";
	        }
	        if(searchWord == null) {
	        	searchWord = "";
	        }
	        
	        
	        
	        Map<String,String> paraMap = new HashMap<>();
	        paraMap.put("searchType", searchType);
	        paraMap.put("searchWord", searchWord);
	        
	        
	        // 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
	        // 총 게시물 건수(totalCount)는 검색조건이 있을 때와 없을 때로 나뉘어진다.
	        int totalCount = 0;        // 총 게시물 건수
	        int sizePerPage = 10;      // 한 페이지당 보여줄 게시물 건수
	        int currentShowPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
	        int totalPage = 0;         // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바 )
	        
	        int startRno = 0;          // 시작 행번호
	        int endRno = 0;            // 끝 행번호
	        
	        // 총 게시물 건수(totalCount)
	        totalCount = service.getTotalCount(paraMap);
	       //  System.out.println("~~~ 확인용 totalCount=> "+ totalCount);
	        
	        // 만약에 총 게시물 건수(totalCount)가 127개이라면
	        // 총 페이지수(totalPage)는 13개 되어야 한다.
	        
	        totalPage = (int) Math.ceil((double)totalCount/sizePerPage); // (double)127/10 ==> 12.7 ==> Math.ceil(12.7) ==> 13.0 ==> (int)13.0 ==> 13
	        
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
	        
	        // **** 가져올 게시글의 범위를 구한다.(공식임!!!) **** 

	        
	        startRno = ( ( currentShowPageNo -1 ) * sizePerPage ) +1;
	        endRno = startRno + sizePerPage - 1;
	        
	        paraMap.put("startRno", String.valueOf(startRno));
	        paraMap.put("endRno", String.valueOf(endRno));
	        
	        documentList = service.documentListSearchWithPaging(paraMap);
	        // 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것)
	        
	        // 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
	        if(!"".equals(searchType) && !"".equals(searchWord)) {
	           mav.addObject("paraMap",paraMap);
	        }
	        mav.addObject("paraMap",paraMap);
	        
	        // === #121. 페이지바 만들기 === //
	        int blockSize = 10;
	        // blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 개수 이다.
	        
	        int loop = 1;
	        /*
	           loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수[ 지금은 10개(== blockSize) ] 까지만 증가하는 용도이다.
	        */
	        
	        int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
	        // *** !! 공식이다. !! *** //

	        
	        String pageBar = "<ul style='list-style:none;'>"; 
	        String url = "documentlist.hello2";
	        
	        // === [맨처음][이전] 만들기 === 
	        if(pageNo != 1) {     
	           pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
	           pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";     
	        }        
	        
	        while( !(loop > blockSize || pageNo > totalPage) ) {
	           
	           if(pageNo == currentShowPageNo) {
	              pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
	           }
	           else {
	              pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
	           }
	           
	           loop++;
	           pageNo++;
	           
	        }// end of while------------------------
	        
	              
	        // === [다음][마지막] 만들기 === 
	        if(pageNo <= totalPage) {     
	           pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
	           pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";     
	        }
	        
	        
	        pageBar += "</ul>";
	        
	        mav.addObject("pageBar", pageBar);
	       
	       
	       // === #123. 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후 
	        //            사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해
	        //           현재 페이지 주소를 뷰단으로 넘겨준다. === //
	   //     String gobackURL = MyUtil.getCurrentURL(request);
	     // System.out.println("~~~~ 확인용 gobackURL => " + gobackURL);
	        // ~~~~ 확인용 gobackURL => /list.action?searchType=subject&searchWord=java
	        // ~~~~ 확인용 gobackURL => /list.action?searchType=subject&searchWord=%EC%9E%85%EB%8B%88%EB%8B%A4&currentShowPageNo=15
	        
	      //  mav.addObject("gobackURL", gobackURL);
	        
	        // === 페이징 처리를 한 검색어가 있는 전체 글목록 보여주기 끝 === //
	        /////////////////////////////////////////////////////////////
	        
	        mav.addObject("documentList", documentList);
	        mav.setViewName("document/list2.tiles1");
	       // /WEB-INF/views/tiles1/board/list.jsp 파일을 생성한다.
	       
	       return mav;
	    	
	    }
		
	    else { // 인사팀이나 관리자가 아닌 경우
	    	
			mav.addObject("message", "접근 권한이 없습니다! (총무팀 전용)");
			
			mav.addObject("loc", request.getContextPath()+"/myDocumentlist.hello2");

			mav.setViewName("msg_JY");
			
			return mav;
	    }
   
       
    }
	
	    // 나의 기안한 문서 리스트 보여주기 (평사원용)
		@RequestMapping(value="/myDocumentlist.hello2")
	    public ModelAndView myDocumentlist(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	       
	   //  getCurrentURL(request); // 로그인 또는 로그아웃을 했을 때 현재 보이던 그 페이지로 그대로 돌아가기 위한 메소드 생성 ===  
			
			HttpSession session = request.getSession();
			 
			EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
			 
			if(loginEmp == null) {
				mav.addObject("message", "먼저 로그인을 해주세요!");
				
				mav.addObject("loc", request.getContextPath()+"/login.hello2");

				mav.setViewName("msg_JY");
				
				return mav;
			}
			
			
			String fk_empno = loginEmp.getEmpno();
			
			List<DocumentVO_jy> documentList = null;
			
	       // === #114. 페이징 처리를 한 검색어가 있는 전체 문서목록 보여주기 시작 === // 
	        String searchType = request.getParameter("searchType");
	        String searchWord = request.getParameter("searchWord");
	        String str_currentShowPageNo = request.getParameter("currentShowPageNo");
	        
	        
	        if(searchType == null) {
	        	searchType = "";
	        }
	        if(searchWord == null) {
	        	searchWord = "";
	        }
	        
	        
	        
	        Map<String,String> paraMap = new HashMap<>();
	        paraMap.put("searchType", searchType);
	        paraMap.put("searchWord", searchWord);
	        paraMap.put("fk_empno", fk_empno);
	        
	        // 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
	        // 총 게시물 건수(totalCount)는 검색조건이 있을 때와 없을 때로 나뉘어진다.
	        int totalCount = 0;        // 총 게시물 건수
	        int sizePerPage = 10;      // 한 페이지당 보여줄 게시물 건수
	        int currentShowPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
	        int totalPage = 0;         // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바 )
	        
	        int startRno = 0;          // 시작 행번호
	        int endRno = 0;            // 끝 행번호
	        
	        // 총 게시물 건수(totalCount) 일반사용자
	        totalCount = service.getMyTotalCount(paraMap);
	       //  System.out.println("~~~ 확인용 totalCount=> "+ totalCount);
	        
	        // 만약에 총 게시물 건수(totalCount)가 127개이라면
	        // 총 페이지수(totalPage)는 13개 되어야 한다.
	        
	        totalPage = (int) Math.ceil((double)totalCount/sizePerPage); // (double)127/10 ==> 12.7 ==> Math.ceil(12.7) ==> 13.0 ==> (int)13.0 ==> 13
	        
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
	        
	        // **** 가져올 게시글의 범위를 구한다.(공식임!!!) **** 
	        startRno = ( ( currentShowPageNo -1 ) * sizePerPage ) +1;
	        endRno = startRno + sizePerPage - 1;
	        
	        paraMap.put("startRno", String.valueOf(startRno));
	        paraMap.put("endRno", String.valueOf(endRno));
	        
	        
	        documentList = service.myDocumentListSearchWithPaging(paraMap);
	        // 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것, 로그인한 사람것)
	        
	        // 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
	        if(!"".equals(searchType) && !"".equals(searchWord)) {
	           mav.addObject("paraMap",paraMap);
	        }
	        mav.addObject("paraMap",paraMap);
	        
	        // === #121. 페이지바 만들기 === //
	        int blockSize = 10;
	        // blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 개수 이다.
	        
	        int loop = 1;
	        /*
	           loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수[ 지금은 10개(== blockSize) ] 까지만 증가하는 용도이다.
	        */
	        
	        int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
	        // *** !! 공식이다. !! *** //

	        
	        String pageBar = "<ul style='list-style:none;'>"; 
	        String url = "myDocumentlist.hello2";
	        
	        // === [맨처음][이전] 만들기 === 
	        if(pageNo != 1) {     
	           pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
	           pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";     
	        }        
	        
	        while( !(loop > blockSize || pageNo > totalPage) ) {
	           
	           if(pageNo == currentShowPageNo) {
	              pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
	           }
	           else {
	              pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
	           }
	           
	           loop++;
	           pageNo++;
	           
	        }// end of while------------------------
	        
	              
	        // === [다음][마지막] 만들기 === 
	        if(pageNo <= totalPage) {     
	           pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
	           pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";     
	        }
	        
	        
	        pageBar += "</ul>";
	        
	        mav.addObject("pageBar", pageBar);
	       
	       
	       // === #123. 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후 
	        //            사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해
	        //           현재 페이지 주소를 뷰단으로 넘겨준다. === //
	   //     String gobackURL = MyUtil.getCurrentURL(request);
	     // System.out.println("~~~~ 확인용 gobackURL => " + gobackURL);
	        // ~~~~ 확인용 gobackURL => /list.action?searchType=subject&searchWord=java
	        // ~~~~ 확인용 gobackURL => /list.action?searchType=subject&searchWord=%EC%9E%85%EB%8B%88%EB%8B%A4&currentShowPageNo=15
	        
	      //  mav.addObject("gobackURL", gobackURL);
	        
	        // === 페이징 처리를 한 검색어가 있는 전체 글목록 보여주기 끝 === //
	        /////////////////////////////////////////////////////////////
	        
	        mav.addObject("documentList", documentList);
	        mav.setViewName("document/myList.tiles1");
	       // /WEB-INF/views/tiles1/board/list.jsp 파일을 생성한다.
	       
	       return mav;
	       
	    }
	
		
	
	
	// 문서 하나를 자세히 보기(관리자, 경지)
	@RequestMapping(value="/viewDocument.hello2")
	public ModelAndView viewDocument(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		String doument_seq = request.getParameter("doument_seq");
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		DocumentVO_jy documentvo = null;
		
		Map<String, String> paraMap = new HashMap<>();
        paraMap.put("doument_seq", doument_seq);
        paraMap.put("searchType", searchType);
        paraMap.put("searchWord", searchWord);
       
       
        mav.addObject("searchType",searchType); // request에 담아주는 것이다.
        mav.addObject("searchWord",searchWord);
		
	    
        documentvo =  service.viewDocument(paraMap);
        
        
        String gobackURL = request.getParameter("gobackURL");
		
        if(gobackURL != null && gobackURL.contains(" ") ) { // 이 문자열속에 공백이 포함 되었다면 contains()
     	   gobackURL = gobackURL.replaceAll(" ", "&"); 
     	   // 이전글제목, 다음글제목을 클릭했을때 돌아갈 페이지 주소를 올바르게 만들어주기 위해서 한 것임.
     	   //   System.out.println("~~~~~~ 확인용 gobackURL => " + gobackURL);
           // ~~~~~~ 확인용 gobackURL => /list.action?searchType=subject&searchWord=%EC%9E%85%EB%8B%88%EB%8B%A4&currentShowPageNo=15
        }
        
        mav.addObject("documentvo", documentvo);
        
        mav.addObject("gobackURL", gobackURL);

	    mav.setViewName("document/view.tiles1");
		
		return mav;
	}
	
	

	// 문서 하나를 자세히 보기(일반 사용자용)
	@RequestMapping(value="/viewMyDocument.hello2")
	public ModelAndView viewMyDocument(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		String doument_seq = request.getParameter("doument_seq");
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		DocumentVO_jy documentvo = null;
		
		Map<String, String> paraMap = new HashMap<>();
        paraMap.put("doument_seq", doument_seq);
        paraMap.put("searchType", searchType);
        paraMap.put("searchWord", searchWord);
       
       
        mav.addObject("searchType",searchType); // request에 담아주는 것이다.
        mav.addObject("searchWord",searchWord);
		
	    
        documentvo =  service.viewDocument(paraMap);
        
        
        String gobackURL = request.getParameter("gobackURL");
		
        if(gobackURL != null && gobackURL.contains(" ") ) { // 이 문자열속에 공백이 포함 되었다면 contains()
     	   gobackURL = gobackURL.replaceAll(" ", "&"); 
     	   // 이전글제목, 다음글제목을 클릭했을때 돌아갈 페이지 주소를 올바르게 만들어주기 위해서 한 것임.
     	   //   System.out.println("~~~~~~ 확인용 gobackURL => " + gobackURL);
           // ~~~~~~ 확인용 gobackURL => /list.action?searchType=subject&searchWord=%EC%9E%85%EB%8B%88%EB%8B%A4&currentShowPageNo=15
        }
        
        mav.addObject("documentvo", documentvo);
        
        mav.addObject("gobackURL", gobackURL);

	    mav.setViewName("document/myView.tiles1");
		
		return mav;
	}
	
	// 기안한 문서 결재하기
	@RequestMapping(value="/changeResult.hello2")
	public ModelAndView changeResult(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		String doument_seq = request.getParameter("doument_seq");
		String result = request.getParameter("change");  // 1 승인, 2 반려
		String status = request.getParameter("status1"); // 문서종류 (1:연차) 처리 따로 해줘야함! 
		String title = request.getParameter("breakkind"); // 종일, 반차(오전), 반차(오후)
		String start1 = request.getParameter("breakstart");
		String end1 = request.getParameter("breakend");
		
		if(title.equals("1")) {
			title = "종일";
		}
		else if(title.equals("2")) {
			title = "반차(오전)";
		}
		else {
			title = "반차(오후)";
		}


		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("doument_seq", doument_seq);
		paraMap.put("result", result);
		
		HttpSession session = request.getSession();
		 
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		 
		String fk_empno = loginEmp.getEmpno();
		
		// 문서의 result를 바꿔주는 함수
		int n = service.changeResult(paraMap);
		
		// 문서의 상태가 승인이고, 연차라면
		if(n == 1 && status.equals("1") && result.equals("1")) {
			
			Map<String, String> paraMap2 = new HashMap<>();
			
			paraMap2.put("fk_empno", fk_empno);
			paraMap2.put("title", title);
			paraMap2.put("start1", start1);
			paraMap2.put("end1", end1);
	
			// 캘린더에 새롭게 승인된 연차를 표시해주는 메소드
			int m = service.insertCalendar(paraMap2);
			
		}
		

		mav.addObject("message", "결재가 완료되었습니다!");
		
		mav.addObject("loc", request.getContextPath()+"/documentlist.hello2");

		mav.setViewName("msg_JY");
		
		return mav;
	}
	
	
	// 기안문서 삭제하기 (대기상태에서만 가능)
	@RequestMapping(value="/delDocumentEnd.hello2")
	public ModelAndView delDocumentEnd(HttpServletRequest request, ModelAndView mav) {
		
		String doument_seq = request.getParameter("doument_seq");
		
		Map<String,String> paraMap = new HashMap<>();
		
		paraMap.put("doument_seq",doument_seq);
		
		// 기안문서를 삭제해주는 메소드
		int n = service.delDocumentEnd(paraMap);
		
		mav.addObject("loc", request.getContextPath()+"/documentMain.hello2");

		mav.setViewName("pageReturn_JY");

		return mav;
		
	}
	
	
	
	// 연차캘린더 보여주기
	@RequestMapping(value="/viewBreak.hello2")
	public ModelAndView viewBreak(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {	
		
	//	getCurrentURL(request); // 로그인 또는 로그아웃을 했을 때 현재 보이던 그 페이지로 그대로 돌아가기 위한 메소드 호출
		
		HttpSession session = request.getSession();
		 
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		 
		if(loginEmp == null) {
			mav.addObject("message", "먼저 로그인을 해주세요!");
			
			mav.addObject("loc", request.getContextPath()+"/login.hello2");

			mav.setViewName("msg_JY");
			
			return mav;
		}
		
		mav.setViewName("break/breakMain.tiles1");

		return mav;
	}
	
	// 연차캘린더 ajax로 값 받아오기
	@ResponseBody
	@RequestMapping(value="/viewBreak2.hello2", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String viewBreak2(HttpServletRequest request, HttpServletResponse response) {
		 
		 List<BreakCalendarVO_jy> breakCalendarList = null;
		 
		 HttpSession session = request.getSession();
		 
		 EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		 
		 String fk_empno = loginEmp.getEmpno();
		 
	//	 String fk_empno = "202111081004";
		 
		 Map<String, String> paraMap = new HashMap<>();
		 paraMap.put("fk_empno", fk_empno);
		 
		 breakCalendarList =  service.viewBreak(paraMap); // 캘린더에 연차를 표시해주는 메소드
		 
		 JSONArray jsonArr = new JSONArray();
		 
		 if(breakCalendarList != null) {
	          for(BreakCalendarVO_jy breakvo : breakCalendarList) {
	             JSONObject jsonObj = new JSONObject();
	             jsonObj.put("title", breakvo.getTitle());
	             jsonObj.put("start", breakvo.getStart1());
	             jsonObj.put("end", breakvo.getEnd1());
	   
	             jsonArr.put(jsonObj);
	          }
		 }      
		 
		 return jsonArr.toString();
		
	}
	
	
    // 사원등록 페이지 보여주기
	@RequestMapping(value="/register.hello2")
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {	
		
		   HttpSession session = request.getSession();
	      
	       EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		
		   String empid = loginEmp.getEmpid();

		   
		   if(!checkDepartment(request,"10") ) { // 인사팀이 아닌 경우
		    	
				mav.addObject("message", "접근 권한이 없습니다!(인사팀전용)");
				
				mav.addObject("loc", "javascript:history.go(-1)");

				mav.setViewName("msg_JY");
				
				return mav;
			   
		    }
		   
		   else if (empid.equals("admin")) {  // 관리자일 경우
		    	
		    	mav.setViewName("document/register.tiles1");
		    	
				return mav;
		    	
		    }
			
		    else { // 인사팀일 경우
		    	
		    	mav.setViewName("document/register.tiles1");
		    	
				return mav;
		    	
		    }
		   
		  
		   
		   

	}
	

	    // id 중복확인 버튼을 눌렀을 경우
		@ResponseBody
		@RequestMapping(value="/idDuplicateCheck.hello2", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
		public String idDuplicateCheck(HttpServletRequest request, HttpServletResponse response) {	
			
			String empid = request.getParameter("empid");
			  
			int n = service.idDuplicateCheck(empid); // ID를 중복확인 해주는 메소드
			
			JSONObject jsonObj = new JSONObject();
		    jsonObj.put("n", n); 

		    return jsonObj.toString();
		
		}
		
		// 회원등록을 해주는 메소드
		@RequestMapping(value="/registerEnd.hello2", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
		public ModelAndView registerEnd(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {	
			
			 HttpSession session = request.getSession();
		      
		     EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
			
			
			if(loginEmp == null) {
				
				mav.addObject("message", "먼저 로그인을 해주세요!");
				
				mav.addObject("loc", request.getContextPath()+"/login.hello2");

				mav.setViewName("msg_JY");
				
				return mav;
			}
			
			else {
			
			
				String empname = request.getParameter("empname");
				String empid = request.getParameter("empid");
				String emppw = request.getParameter("emppw");
				String ranking = request.getParameter("ranking");
				String fk_deptnum = request.getParameter("fk_deptnum");
				String empsalary = request.getParameter("empsalary");
				String hiredate = request.getParameter("hiredate");
				String noticeemail = request.getParameter("noticeemail");
				
				
				
				Map<String, String> paraMap = new HashMap<> ();
				
				paraMap.put("empname", empname);
				paraMap.put("empid", empid);
				paraMap.put("emppw", Sha256.encrypt(emppw));
				paraMap.put("ranking", ranking);
				paraMap.put("fk_deptnum", fk_deptnum);
				paraMap.put("empsalary", empsalary);
				paraMap.put("hiredate", hiredate);
				paraMap.put("noticeemail", noticeemail);
				
				// 회원등록을 해주는 메소드
				int n = service.registerEnd(paraMap);
				
				if(n == 1) {
					mav.addObject("message", "사용자 등록이 완료되었습니다!");
					
					mav.addObject("loc", request.getContextPath()+"/register.hello2");
	
					mav.setViewName("msg_JY");
					
					return mav;
				}
				else {
					mav.addObject("message", "사용자 등록이 실패했습니다");
					
					mav.addObject("loc", request.getContextPath()+"/register.hello2");
	
					mav.setViewName("msg_JY");
					
					return mav;
				}
		
			
			}
		
		}
		
		

	
	
	
	
	////////////////////////////////////////////////////////////////////
	// 로그인한 유저의 소속부서를 확인하는 메소드
	   private boolean checkDepartment(HttpServletRequest request, String deptnum) {
	      
	      boolean checkDepartment = false;
	      
	      HttpSession session = request.getSession();
	      
	      EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
	      
	      if(loginEmp != null && (deptnum.equals(loginEmp.getFk_deptnum()) || "00".equals(loginEmp.getFk_deptnum()))) {
	         checkDepartment = true;
	      }
	      
	      return checkDepartment;
	      
	   }
	////////////////////////////////////////////////////////////////////////
	
}
