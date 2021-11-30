package com.spring.helloworks.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.*;

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

import com.spring.helloworks.model.EmpVO_KJH;
import com.spring.helloworks.model.MailVO_JDH;
import com.spring.helloworks.util.FileManager;
import com.spring.helloworks.util.MyUtil;
import com.spring.mail.service.InterMailService_JDH;

@Controller
public class MailController_JDH {

	@Autowired
	private InterMailService_JDH service;
	
	@Autowired // Type에 따라 알아서 Bean 을 주입해준다.
	private FileManager fileManager;
	
	// 받은 메일리스트 보기
	@RequestMapping(value = "/mailList.hello2")
	public ModelAndView requiredLogin_list(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ) {
		getCurrentURL(request);
		
		List<MailVO_JDH> mailList = null;
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"mailsubject".equals(searchType) && !"sendid".equals(searchType))) {         
	         searchType = "";         
		}
      
		if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty()) {         
			searchWord = "";         
		}
		
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		int totalCount = 0;
		int sizePerPage = 10;
		int currentShowPageNo = 0;
		int totalPage = 0;
		
		int startRno = 0;
		int endRno = 0;
		
		totalCount = service.getTotalCount(paraMap);
		totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
		
		if(str_currentShowPageNo == null) { // 글목록보기 초기화면
	         currentShowPageNo = 1;
	    }
	      
	    else {
	         
	       try {
	          currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
	          
	          if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
	             currentShowPageNo = 1; // 숫자는 숫자인데 1보다 작은 숫자를 입력했거나 totalPage보다 클 경우
	          }
	           
	       } catch (NumberFormatException e) {
	          currentShowPageNo = 1; // str_currentShowPageNo가 ""거나 문자로 장난쳤을 경우 1페이지로!
	       }
	         
	    }
		
		startRno = ((currentShowPageNo - 1) * sizePerPage) + 1;
	    endRno = startRno + sizePerPage - 1;
	     
	    paraMap.put("startRno", String.valueOf(startRno));
	    paraMap.put("endRno", String.valueOf(endRno));
	    
	    ///////////////////////////////////////////////////////
	    // 로그인한 직원의 아이디 값 찾아내서 맵에 넣어주기 (where 절에 사용해야 함!!!)
	    
	    // 세션은 이미 위에서 불러왔음~
	    EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
	    paraMap.put("loginEmpid", loginEmp.getEmpid());
	    
	    
	    mailList = service.mailListSearchWithPaging(paraMap);
	    // 페이징 처리한 글목록 가져오기(검색이 있든지, 없든지 모두 다 포함한 것)
	    
	    // 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.(검색을 했을 경우)
	    if(!"".equals(searchType) && !"".equals(searchWord)) {
	       mav.addObject("paraMap", paraMap);
	    }
	      
	    int blockSize = 10;
	    
	    
	    int loop = 1;
	    
	    int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
		
	    String pageBar = "<ul style='list-style: none;' class='px-0'>";
	      
	    String url = "mailList.hello2";
		
	    if(pageNo != 1) {
	         pageBar += "<li style='display: inline-block; width: 70px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=1'>[맨처음]</a></li>";         
	         pageBar += "<li style='display: inline-block; width: 50px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + (pageNo - 1) + "'>[이전]</a></li>";
	    }
	                  
	    while(!(loop > blockSize || pageNo > totalPage)) {
	         
	       if(pageNo == currentShowPageNo) 
	          pageBar += "<li style='display: inline-block; width: 30px; font-size: 12pt; border: solid 1px blue; color: navy; padding: 2px 4px;'>" + pageNo + "</li>";
	       else
	          pageBar += "<li style='display: inline-block; width: 30px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + pageNo + "'>" + pageNo + "</a></li>";
	       
	       loop++;
	       pageNo++;
	    }// end of while-------------------------
	    
	    if(pageNo <= totalPage) {         
	         pageBar += "<li style='display: inline-block; width: 50px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + (pageNo) + "'>[다음]</a></li>";
	         pageBar += "<li style='display: inline-block; width: 70px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + totalPage + "'>[마지막]</a></li>";         
	    }
	      
	    pageBar += "</ul>";
	    
	    mav.addObject("pageBar", pageBar);
	    
	    String gobackURL = MyUtil.getCurrentURL(request);
	    
	    mav.addObject("gobackURL", gobackURL);
	    
	    mav.addObject("mailList", mailList);
	      
	    mav.setViewName("mail/mailList.tiles1");

		return mav;
	}
	
	// 메일보내기
	@RequestMapping(value = "/send.hello2") // requiredLogin_ 붙여주기
	public ModelAndView requiredLogin_send(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		getCurrentURL(request);
		
		String fk_mailseq = request.getParameter("fk_mailseq");
		String mailsubject = request.getParameter("mailsubject");
		
		String empid = request.getParameter("sendid");
		
		if(empid != null && !"".equals(empid) ) {
			Map<String, String> empMap = service.searchEmp(empid);
			mav.addObject("empMap", empMap);
		}
		
		if(fk_mailseq == null || "".equals(fk_mailseq)) {
			fk_mailseq = "";
		}
		
		mav.addObject("fk_mailseq", fk_mailseq);
		mav.addObject("mailsubject", mailsubject);
		
		mav.setViewName("mail/send.tiles1");
		
		return mav;
	}
	
	// 메일 받는사람 자동완성하기 
	@ResponseBody //웹페이지에 내용이 그대로 찍혀서 나온다
	@RequestMapping(value="/recidSearchShow.hello2", method={RequestMethod.GET}, produces="text/plain;charset=UTF-8")
	public String recidSearchShow(HttpServletRequest request) {
		
		String searchWord = request.getParameter("searchWord");
		
	//	System.out.println(">> 확인용 : " + searchWord);
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchWord", searchWord);
		
		List<Map<String,String>> recidList = service.recidSearchShow(paraMap);
		
		JSONArray jsonArr = new JSONArray();
		
		if(recidList != null) {
			for(Map<String,String> recMap : recidList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("empname", recMap.get("empname"));
				
				jsonObj.put("empid", recMap.get("empid"));
				jsonObj.put("fk_deptnum", recMap.get("fk_deptnum"));
				jsonObj.put("deptname", recMap.get("deptname"));
				
				jsonArr.put(jsonObj);
			}
		}
		
		return jsonArr.toString();
	}
	
	// 메일보내기 완료 요청
	@RequestMapping(value="/sendEnd.hello2", method = {RequestMethod.POST})
	public ModelAndView sendEnd(Map<String, String>paraMap, ModelAndView mav, MailVO_JDH mailvo, MultipartHttpServletRequest mrequest) {
		
		MultipartFile attach = mailvo.getAttach();
		
		if(!attach.isEmpty()) {
		
			HttpSession session = mrequest.getSession();
			String root = session.getServletContext().getRealPath("/");
			
			String path = root + "resources" + File.separator + "files";
			
			String newfilename = "";
			byte[] bytes = null;
			long mailfilesize = 0;
			
			try {
				bytes = attach.getBytes();
				newfilename = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
				
				mailvo.setMailfilename(newfilename);
				mailvo.setMailorgfilename(attach.getOriginalFilename());
				mailfilesize = attach.getSize();
				mailvo.setMailfilesize(String.valueOf(mailfilesize));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		int n = 0;
		
		String recidList = mailvo.getRecid();
	//	System.out.println(recidList);
		String[] recidArr = recidList.split(",");
		
		for( String recid : recidArr) {
		//	System.out.println(recid);
		//	System.out.println(recidList);
		//	System.out.println(recidArr);
			mailvo.setRecid(recid);
						
			if(attach.isEmpty()) { // 파일첨부가 없으면
				n = service.send(mailvo); // 파일첨부 없는 메일 쓰기
			}
			else {
				n = service.send_withFile(mailvo);
			}
			
		}
		
		String message = "";
		
		if(n == 0)
			message = "메일 전송이 실패하였습니다.";
		
		else
			message = "메일 전송이 성공하였습니다.";
		
		mav.addObject("message", message);
		mav.addObject("loc", mrequest.getContextPath() + "/mailList.hello2");
		mav.setViewName("msg_KJH");
		
		return mav;
	}
	// 사진업로드
	@RequestMapping(value="/image/multiplePhotoUpload.hello2", method= {RequestMethod.POST})
	public void multiplePhotoUpload(HttpServletRequest request, HttpServletResponse response) {
	   
	   /*
	      1. 사용자가 보낸 파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다.
	      >>>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
	             우리는 WAS 의 webapp/resources/photo_upload 라는 폴더로 지정해준다.
	   */
	      
	      // WAS 의 webapp 의 절대경로를 알아와야 한다. 
	      HttpSession session = request.getSession();
	      String root = session.getServletContext().getRealPath("/"); 
	      String path = root + "resources"+File.separator+"photo_upload";
	      // path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다. 
	         
	      // System.out.println(">>>> 확인용 path ==> " + path); 
	      // >>>> 확인용 path ==> C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\photo_upload    
	         
	      File dir = new File(path);
	      if(!dir.exists())
	          dir.mkdirs();
	      
	      String strURL = "";
	      
	      try {
		      String filename = request.getHeader("file-name"); // 파일명을 받는다 - 일반 원본파일명
	          // 네이버 스마트에디터를 사용한 파일업로드시 싱글파일업로드와는 다르게 멀티파일업로드는 파일명이 header 속에 담겨져 넘어오게 되어있다. 
		   
		      /*
		          [참고]
		          HttpServletRequest의 getHeader() 메소드를 통해 클라이언트 사용자의 정보를 알아올 수 있다. 
		
				  request.getHeader("referer");           // 접속 경로(이전 URL)
				  request.getHeader("user-agent");        // 클라이언트 사용자의 시스템 정보
				  request.getHeader("User-Agent");        // 클라이언트 브라우저 정보 
				  request.getHeader("X-Forwarded-For");   // 클라이언트 ip 주소 
				  request.getHeader("host");              // Host 네임  예: 로컬 환경일 경우 ==> localhost:9090    
		      */
		      
		      InputStream is = request.getInputStream(); // is 는 네이버 스마트 에디터를 사용하여 사진첨부하기 된 이미지 파일임.
	          /*
			               요청 헤더의 content-type이 application/json 이거나 multipart/form-data 형식일 때,
			       name(이름) 없이 값만 전달될 때 이 값은 요청 헤더가 아닌 바디를 통해 전달된다. 
			               이러한 형태의 값을 'payload body'라고 하는데 요청 바디에 직접 쓰여진다 하여 'request body post data'라고도 한다.
			
			               서블릿에서 payload body 의 값을 읽어오려면 request.getParameter()를 사용하는 것이 아니라 
			       request.getInputStream() (2진파일) 또는 request.getReader()를 사용하여 body를 직접 읽는 방식으로 가져온다.    
	          */
	      
		      String newFilename = fileManager.doFileUpload(is, filename, path);
		      
		      String ctxPath = request.getContextPath(); 
              
              strURL += "&bNewLine=true&sFileName="+newFilename; 
              strURL += "&sWidth=";
              strURL += "&sFileURL="+ctxPath+"/resources/photo_upload/"+newFilename;
		      
              // === 웹브라우저상에 사진 이미지를 쓰기 === //
              PrintWriter out = response.getWriter();
              out.print(strURL);
              
	      } catch(Exception e) {
	    	  e.printStackTrace();
	      }
	}
	
	// === #62. 글 1개를 보여주는 페이지 요청 === //
	@RequestMapping(value = "/getView.hello2")
	public ModelAndView getView(ModelAndView mav, HttpServletRequest request) {
		
		getCurrentURL(request); // 로그인 또는 로그아웃을 했을 때 현재 보이던 그 페이지로 그대로 돌아가기 위한 메소드 호출
		
		// 조회하고자 하는 글번호 받아오기
		String mailseq = request.getParameter("mailseq");
		
		// 글목록에서 검색되어진 글내용일 경우 이전글제목, 다음글제목은 검색되어진 결과물내의 이전글과 다음글이 나오도록 하기 위한 것이다.  
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		if(searchType == null) {
			searchType = "";
		}
		
		if(searchWord == null) {
			searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("mailseq", mailseq);
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		mav.addObject("searchType", searchType); // request에 담아주는것
		mav.addObject("searchWord", searchWord); // request에 담아주는것
		
		
		
		String gobackURL = request.getParameter("gobackURL");
		
		if(gobackURL != null && gobackURL.contains(" ")) {
			gobackURL = gobackURL.replaceAll(" ", "&");
			
		}
		
		mav.addObject("gobackURL", gobackURL);
		
		////////////////////////////////////////////////////////////
		
		try {
			Integer.parseInt(mailseq);
			
			HttpSession session = request.getSession();
			EmpVO_KJH loginuser = (EmpVO_KJH)session.getAttribute("loginEmp");
			
			String login_userid = null;
			
			if(loginuser != null) {
				login_userid = loginuser.getEmpid();
			}
			
			// System.out.println(login_userid);
			MailVO_JDH mailvo = null;
			
			mailvo = service.getViewMail(paraMap); 
			
			if(mailvo != null) {
				// System.out.println(mailvo.getRecid() + "," + mailvo.getSendid());
				if( !mailvo.getRecid().equals(login_userid) && !mailvo.getSendid().equals(login_userid) ) {
					
					String message = "본인의 메일이 아닙니다.";
					String loc = "javascript:history.back()";
					
					mav.addObject("message", message);
					mav.addObject("loc", loc);
					
					mav.setViewName("msg_KJH");
				}
				
				else {
					
					if("0".equals(mailvo.getMailreadstatus())) {
						
						int n = 0;
						
						n = service.updateReadStatus(mailvo);
						// 여기서부터 readStatus를 수정하는거 만들기~~~~*~)*)*~)*~)~*)~*)~*)~*)*~*)~*)~*)~*)*
						// 서비스단으로 다시 seq전송해서 해당 글의 읽음상태를 '1'(읽음)으로 update해준다.
					}
					
					mav.setViewName("mail/viewMail.tiles1"); // view단에 보내주기
					
				}
				
			}
			
			mav.addObject("mailvo", mailvo); // request에 담는것
		
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		
		
		return mav;
	}
	
	// 첨부파일 다운로드 받기
	@RequestMapping(value="/download.hello2")
	public void requiredLogin_download(HttpServletRequest request, HttpServletResponse response) {
		
		String mailseq = request.getParameter("mailseq");
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("mailseq", mailseq);
		
		response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = null;
	    
	    try {
	         Integer.parseInt(mailseq);
	         
	         MailVO_JDH mailvo = service.getViewMail(paraMap);
	         
	         if(mailvo == null || (mailvo != null && mailvo.getMailfilename() == null )) {
	            out = response.getWriter();
	            // 웹브라우저상에 메시지를 쓰기 위한 객체생성 
	            
	            out.println("<script type='text/javascript'> alert('존재하지 않는 글번호 이거나 첨부파일이 없으므로 파일 다운로드가 불가합니다!!'); history.back(); </script>");
	            
	            return; // 종료
	         }
	         
	         else {
	            String mailfilename = mailvo.getMailfilename();
	            // 202111081246501138232227609900.jpg  이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다. 
	            
	            String mailorgfilename = mailvo.getMailorgfilename();
	            // berkelekle심플V넥02.jpg  다운로드시 보여줄 파일명 
	            
	             
	             // 첨부파일이 저장되어 있는 WAS(톰캣)의 디스크 경로명을 알아와야만 다운로드를 해줄수 있다. 
	             // 이 경로는 우리가 파일첨부를 위해서 /addEnd.action 에서 설정해두었던 경로와 똑같아야 한다.
	            // WAS의 webapp 의 절대경로를 알아와야 한다.
	          HttpSession session = request.getSession();
	          String root = session.getServletContext().getRealPath("/");
	          
	       //   System.out.println("~~~~ 확인용 webapp 의 절대경로 => " + root);
	          // ~~~~ 확인용 webapp 의 절대경로 => C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\ 
	          
	          String path = root + "resources" + File.separator + "files";
	          /* File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다.
	                운영체제가 Windows 이라면 File.separator 는  "\" 이고,
	                운영체제가 UNIX, Linux 이라면  File.separator 는 "/" 이다. 
	          */
	          
	          // path 가 첨부파일이 저장될 WAS(톰캣)의 폴더가 된다.
	       //   System.out.println("~~~~ 확인용 path => " + path);
	          // ~~~~ 확인용 path => C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\files
	            
	            // **** file 다운로드 하기 **** //
	          boolean flag = fileManager.doFileDownload(mailfilename, mailorgfilename, path, response);
	          // flag 값이 true 로 받아오면 다운로드 성공을 말하고,
	          // flag 값이 false 로 받아오면 다운로드 실패를 말한다.
	          
	          if(flag == false) {
	             // 다운로드가 실패할 경우 메시지를 띄워준다.
	             
	             out = response.getWriter();
	                  // 웹브라우저상에 메시지를 쓰기 위한 객체생성 
	                  
	               out.println("<script type='text/javascript'> alert('파일 다운로드가 실패되었습니다!!'); history.back(); </script>"); 
	          }
	          
	         }
	      } catch(NumberFormatException e) {
	         
	         try {
	            out = response.getWriter();
	            // 웹브라우저상에 메시지를 쓰기 위한 객체생성 
	               
	            out.println("<script type='text/javascript'> alert('존재하지 않는 글번호 이므로 파일 다운로드가 불가합니다!!'); history.back(); </script>");
	         } catch(IOException e1) {
	            
	         }
	         
	      } catch(IOException e2) {
	         
	      }
	}
	
	// 보낸 메일리스트 보기
	@RequestMapping(value = "/sendmailList.hello2")
	public ModelAndView requiredLogin_sendmailList(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ) {
		getCurrentURL(request);
		
		List<MailVO_JDH> sendmailList = null;
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"mailsubject".equals(searchType) && !"recid".equals(searchType))) {         
	         searchType = "";         
		}
      
		if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty()) {         
			searchWord = "";         
		}
		
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		int totalCount = 0;
		int sizePerPage = 10;
		int currentShowPageNo = 0;
		int totalPage = 0;
		
		int startRno = 0;
		int endRno = 0;
		
		totalCount = service.getTotalCount(paraMap);
		totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
		
		if(str_currentShowPageNo == null) { // 글목록보기 초기화면
	         currentShowPageNo = 1;
	    }
	      
	    else {
	         
	       try {
	          currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
	          
	          if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
	             currentShowPageNo = 1; // 숫자는 숫자인데 1보다 작은 숫자를 입력했거나 totalPage보다 클 경우
	          }
	           
	       } catch (NumberFormatException e) {
	          currentShowPageNo = 1; // str_currentShowPageNo가 ""거나 문자로 장난쳤을 경우 1페이지로!
	       }
	         
	    }
		
		startRno = ((currentShowPageNo - 1) * sizePerPage) + 1;
	    endRno = startRno + sizePerPage - 1;
	     
	    paraMap.put("startRno", String.valueOf(startRno));
	    paraMap.put("endRno", String.valueOf(endRno));
	    
	    ///////////////////////////////////////////////////////
	    // 로그인한 직원의 아이디 값 찾아내서 맵에 넣어주기 (where 절에 사용해야 함!!!)
	    
	    // 세션은 이미 위에서 불러왔음~
	    EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
	    paraMap.put("loginEmpid", loginEmp.getEmpid());
	    
	    
	    sendmailList = service.sendmailListSearchWithPaging(paraMap);
	    // 페이징 처리한 글목록 가져오기(검색이 있든지, 없든지 모두 다 포함한 것)
	    
	    // 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.(검색을 했을 경우)
	    if(!"".equals(searchType) && !"".equals(searchWord)) {
	       mav.addObject("paraMap", paraMap);
	    }
	      
	    int blockSize = 10;
	    
	    
	    int loop = 1;
	    
	    int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
		
	    String pageBar = "<ul style='list-style: none;' class='px-0'>";
	      
	    String url = "mailList.hello2";
		
	    if(pageNo != 1) {
	         pageBar += "<li style='display: inline-block; width: 70px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=1'>[맨처음]</a></li>";         
	         pageBar += "<li style='display: inline-block; width: 50px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + (pageNo - 1) + "'>[이전]</a></li>";
	    }
	                  
	    while(!(loop > blockSize || pageNo > totalPage)) {
	         
	       if(pageNo == currentShowPageNo) 
	          pageBar += "<li style='display: inline-block; width: 30px; font-size: 12pt; border: solid 1px blue; color: navy; padding: 2px 4px;'>" + pageNo + "</li>";
	       else
	          pageBar += "<li style='display: inline-block; width: 30px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + pageNo + "'>" + pageNo + "</a></li>";
	       
	       loop++;
	       pageNo++;
	    }// end of while-------------------------
	    
	    if(pageNo <= totalPage) {         
	         pageBar += "<li style='display: inline-block; width: 50px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + (pageNo) + "'>[다음]</a></li>";
	         pageBar += "<li style='display: inline-block; width: 70px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + totalPage + "'>[마지막]</a></li>";         
	    }
	      
	    pageBar += "</ul>";
	    
	    mav.addObject("pageBar", pageBar);
	    
	    String gobackURL = MyUtil.getCurrentURL(request);
	    
	    mav.addObject("gobackURL", gobackURL);
	    
	    mav.addObject("sendmailList", sendmailList);
	      
	    mav.setViewName("mail/sendmailList.tiles1");

		return mav;
	}
	
	
	// 휴지통 리스트 보기
	@RequestMapping(value = "/trashmailList.hello2")
	public ModelAndView requiredLogin_trashmailList(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ) {
		getCurrentURL(request);
		
		List<MailVO_JDH> trashmailList = null;
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"mailsubject".equals(searchType) && !"recid".equals(searchType))) {         
	         searchType = "";         
		}
      
		if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty()) {         
			searchWord = "";         
		}
		
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		int totalCount = 0;
		int sizePerPage = 10;
		int currentShowPageNo = 0;
		int totalPage = 0;
		
		int startRno = 0;
		int endRno = 0;
		
		totalCount = service.getTotalCount(paraMap);
		totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
		
		if(str_currentShowPageNo == null) { // 글목록보기 초기화면
	         currentShowPageNo = 1;
	    }
	      
	    else {
	         
	       try {
	          currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
	          
	          if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
	             currentShowPageNo = 1; // 숫자는 숫자인데 1보다 작은 숫자를 입력했거나 totalPage보다 클 경우
	          }
	           
	       } catch (NumberFormatException e) {
	          currentShowPageNo = 1; // str_currentShowPageNo가 ""거나 문자로 장난쳤을 경우 1페이지로!
	       }
	         
	    }
		
		startRno = ((currentShowPageNo - 1) * sizePerPage) + 1;
	    endRno = startRno + sizePerPage - 1;
	     
	    paraMap.put("startRno", String.valueOf(startRno));
	    paraMap.put("endRno", String.valueOf(endRno));
	    
	    ///////////////////////////////////////////////////////
	    // 로그인한 직원의 아이디 값 찾아내서 맵에 넣어주기 (where 절에 사용해야 함!!!)
	    
	    // 세션은 이미 위에서 불러왔음~
	    EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
	    paraMap.put("loginEmpid", loginEmp.getEmpid());
	    
	    
	    trashmailList = service.trashmailListSearchWithPaging(paraMap);
	    // 페이징 처리한 글목록 가져오기(검색이 있든지, 없든지 모두 다 포함한 것)
	    
	    // 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.(검색을 했을 경우)
	    if(!"".equals(searchType) && !"".equals(searchWord)) {
	       mav.addObject("paraMap", paraMap);
	    }
	      
	    int blockSize = 10;
	    
	    
	    int loop = 1;
	    
	    int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
		
	    String pageBar = "<ul style='list-style: none;' class='px-0'>";
	      
	    String url = "trashmailList.hello2";
		
	    if(pageNo != 1) {
	         pageBar += "<li style='display: inline-block; width: 70px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=1'>[맨처음]</a></li>";         
	         pageBar += "<li style='display: inline-block; width: 50px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + (pageNo - 1) + "'>[이전]</a></li>";
	    }
	                  
	    while(!(loop > blockSize || pageNo > totalPage)) {
	         
	       if(pageNo == currentShowPageNo) 
	          pageBar += "<li style='display: inline-block; width: 30px; font-size: 12pt; border: solid 1px blue; color: navy; padding: 2px 4px;'>" + pageNo + "</li>";
	       else
	          pageBar += "<li style='display: inline-block; width: 30px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + pageNo + "'>" + pageNo + "</a></li>";
	       
	       loop++;
	       pageNo++;
	    }// end of while-------------------------
	    
	    if(pageNo <= totalPage) {         
	         pageBar += "<li style='display: inline-block; width: 50px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + (pageNo) + "'>[다음]</a></li>";
	         pageBar += "<li style='display: inline-block; width: 70px; font-size: 12pt;'><a href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + totalPage + "'>[마지막]</a></li>";         
	    }
	      
	    pageBar += "</ul>";
	    
	    mav.addObject("pageBar", pageBar);
	    
	    String gobackURL = MyUtil.getCurrentURL(request);
	    
	    mav.addObject("gobackURL", gobackURL);
	    
	    mav.addObject("trashmailList", trashmailList);
	      
	    mav.setViewName("mail/trashmailList.tiles1");

		return mav;
	}
	
	
	
	// 메일 삭제
	@RequestMapping(value="/delete.hello2")
	public ModelAndView requiredLogin_delete(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
      
		
		// 삭제해야 할 글번호 가져오기 
		String sMailseq = request.getParameter("sMailseq");
		
		// System.out.println("~~~~~확인용 sMailseq : " + sMailseq);
      
		// 삭제해야할 글1개 내용 가져와서 로그인한 사람이 쓴 글이라면 글삭제가 가능하지만
		// 다른 사람이 쓴 글은 삭제가 불가하도록 해야 한다.
		Map<String,Object> paraMap = new HashMap<>();
		
		if(sMailseq != null && !"".equals(sMailseq)) {
			String[] arrMailseq = sMailseq.split(","); 
			
			paraMap.put("arrMailseq", arrMailseq);
			
			request.setAttribute("sMailseq", sMailseq);
			
		}
		
		int n = service.delete(paraMap);
		// System.out.println(n);
		
		mav.addObject("sMailseq", sMailseq);
		mav.setViewName("msg_KJH");
		
		mav.addObject("message", "메일 삭제 성공");
  	  	mav.addObject("loc", request.getContextPath()+"/mailList.hello2");
		
		return mav;
	}
	
	// 메일 복구
	@RequestMapping(value="/revive.hello2")
	public ModelAndView requiredLogin_revive(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
      
		
		// 복구해야 할 글번호 가져오기 
		String sMailseq = request.getParameter("sMailseq");
		
		// System.out.println("~~~~~확인용 sMailseq : " + sMailseq);
      
		Map<String,Object> paraMap = new HashMap<>();
		
		if(sMailseq != null && !"".equals(sMailseq)) {
			String[] arrMailseq = sMailseq.split(","); 
			
			paraMap.put("arrMailseq", arrMailseq);
			
			request.setAttribute("sMailseq", sMailseq);
			
		}
		
		int n = service.revive(paraMap);
		// System.out.println(n);
		
		mav.addObject("sMailseq", sMailseq);
		mav.setViewName("msg_KJH");
		
		mav.addObject("message", "메일 복구 성공");
  	  	mav.addObject("loc", request.getContextPath()+"/mailList.hello2");
		
		return mav;
	}
	
	
	// 휴지통 메일삭제
	@RequestMapping(value="/trashdelete.hello2", method={RequestMethod.POST})
	public ModelAndView delEnd(ModelAndView mav, HttpServletRequest request) {
   	  
		// 삭제해야 할 글번호 가져오기 
		String sMailseq = request.getParameter("sMailseq");
		
		// System.out.println("~~~~~확인용 sMailseq : " + sMailseq);
      
		Map<String,Object> paraMap = new HashMap<>();
		
		if(sMailseq != null && !"".equals(sMailseq)) {
			String[] arrMailseq = sMailseq.split(","); 
			
			paraMap.put("arrMailseq", arrMailseq);
			
			request.setAttribute("sMailseq", sMailseq);
			
		}
		
		int n = service.trashdelete(paraMap);
		// System.out.println(n);
		
		mav.addObject("sMailseq", sMailseq);
		mav.setViewName("msg_KJH");
		
		mav.addObject("message", "휴지통 메일 삭제 성공");
  	  	mav.addObject("loc", request.getContextPath()+"/mailList.hello2");
		
		return mav;
	
	}
	
	// 보낸 메일삭제
	@RequestMapping(value="/deleteSendMail.hello2", method={RequestMethod.POST})
	public ModelAndView deleteSendMail(ModelAndView mav, HttpServletRequest request) {
   	  
		// 삭제해야 할 글번호 가져오기 
		String sMailseq = request.getParameter("sMailseq");
		
		// System.out.println("~~~~~확인용 sMailseq : " + sMailseq);
      
		Map<String,Object> paraMap = new HashMap<>();
		
		if(sMailseq != null && !"".equals(sMailseq)) {
			String[] arrMailseq = sMailseq.split(","); 
			
			paraMap.put("arrMailseq", arrMailseq);
			
			request.setAttribute("sMailseq", sMailseq);
			
		}
		
		int n = service.deleteSendMail(paraMap);
		// System.out.println(n);
		
		mav.addObject("sMailseq", sMailseq);
		mav.setViewName("msg_KJH");
		
		mav.addObject("message", "보낸 메일 삭제 성공");
  	  	mav.addObject("loc", request.getContextPath()+"/mailList.hello2");
		
		return mav;
	
	}

	
	
	
	// 로그인, 로그아웃시 페이지 유지
	private void getCurrentURL(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("goBackURL", MyUtil.getCurrentURL(request) );
		
	}
	
}
