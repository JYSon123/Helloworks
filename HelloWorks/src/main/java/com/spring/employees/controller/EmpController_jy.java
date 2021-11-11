package com.spring.employees.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.employees.model.DocumentVO_jy;
import com.spring.employees.model.FileManager_sj;
import com.spring.employees.service.InterEmpService_jy;
import com.spring.helloworks.model.EmpVO_KJH;
import com.sun.xml.internal.txw2.Document;

@Controller
public class EmpController_jy {
	
	
	@Autowired  // 의존객체 설정
	private InterEmpService_jy service;
	
	// === 파일업로드 및 다운로드를 해주는 FileManager_sj클래스 의존객체 주입하기 === 
	@Autowired
	private FileManager_sj fileManager;
	
	
	// documentMain.jsp 확인용 메소드
	@RequestMapping(value="/documentMain.hello2")
    public String documentMain(HttpServletRequest request) {
      
	
       return "document/documentMain.tiles1";   
    }
	
	
	
	// 기안하기 폼페이지 요청
	@RequestMapping(value="/write.hello2")
 // public ModelAndView requiredLogin_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {	
		
	//	getCurrentURL(request); // 로그인 또는 로그아웃을 했을 때 현재 보이던 그 페이지로 그대로 돌아가기 위한 메소드 호출
		
		mav.setViewName("document/add.tiles1");

		return mav;
	}
	
	
	// 기안하기 완료하기
	@RequestMapping(value="/addEndjy.hello2", method= {RequestMethod.POST})
	public ModelAndView documentaddend(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		int documentaddend = 0;
		
		String fk_empno =  request.getParameter("fk_empno");
		String fk_deptnum = request.getParameter("fk_deptnum");
		String name = request.getParameter("empname");
		String status = request.getParameter("documentKind"); // 왜 이름을 바꾸면 안될까? 하루종일 붙들고있었네...
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		
		Map<String,String> paraMap = new HashMap<>();
	    paraMap.put("fk_empno", fk_empno);
	    paraMap.put("fk_deptnum", fk_deptnum);
	    paraMap.put("name", name);
	    paraMap.put("status", status);
	    paraMap.put("subject", subject);
	    paraMap.put("content", content);
		
	    // 작성한 문서를 테이블에 insert 시켜주는 메소드
	    documentaddend = service.documentaddend(paraMap);
	    
			   
		mav.setViewName("document/documentMain.tiles1");
	
		return mav;
	
	}
	
	
	// 기안한 문서 보여주기
	@RequestMapping(value="/documentlist.hello2")
    public ModelAndView documentlist(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
       
   //  getCurrentURL(request); // 로그인 또는 로그아웃을 했을 때 현재 보이던 그 페이지로 그대로 돌아가기 위한 메소드 생성 ===  
       
		List<DocumentVO_jy> documentList = null;
		
       // === #114. 페이징 처리를 한 검색어가 있는 전체 문서목록 보여주기 시작 === // 
        String searchType = request.getParameter("searchType");
        String searchWord = request.getParameter("searchWord");
        String str_currentShowPageNo = request.getParameter("currentShowPageNo");
        
        /*
        if(searchType == null || (!"subject".equals(searchType) && !"name".equals(searchType)) ) {  // 유저가 장난을 쳐왔다면
           searchType = "";
        }
        
        if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty() ) {  // 검색어 자체가 아예 없다면
           searchWord = "";
        }
        */
        
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
        int sizePerPage = 3;      // 한 페이지당 보여줄 게시물 건수
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
        /*
             currentShowPageNo      startRno     endRno
            --------------------------------------------
                 1 page        ===>    1           10
                 2 page        ===>    11          20
                 3 page        ===>    21          30
                 4 page        ===>    31          40
                 ......                ...         ...
         */
        
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
        /*
                         1  2  3  4  5  6  7  8  9 10 [다음][마지막]  -- 1개블럭
           [맨처음][이전]  11 12 13 14 15 16 17 18 19 20 [다음][마지막]  -- 1개블럭
           [맨처음][이전]  21 22 23
        */
        
        int loop = 1;
        /*
           loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수[ 지금은 10개(== blockSize) ] 까지만 증가하는 용도이다.
        */
        
        int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
        // *** !! 공식이다. !! *** //
        
    /*
        1  2  3  4  5  6  7  8  9  10  -- 첫번째 블럭의 페이지번호 시작값(pageNo)은 1 이다.
        11 12 13 14 15 16 17 18 19 20  -- 두번째 블럭의 페이지번호 시작값(pageNo)은 11 이다.
        21 22 23 24 25 26 27 28 29 30  -- 세번째 블럭의 페이지번호 시작값(pageNo)은 21 이다.
        
        currentShowPageNo         pageNo
       ----------------------------------
             1                      1 = ((1 - 1)/10) * 10 + 1
             2                      1 = ((2 - 1)/10) * 10 + 1
             3                      1 = ((3 - 1)/10) * 10 + 1
             4                      1
             5                      1
             6                      1
             7                      1 
             8                      1
             9                      1
             10                     1 = ((10 - 1)/10) * 10 + 1
            
             11                    11 = ((11 - 1)/10) * 10 + 1
             12                    11 = ((12 - 1)/10) * 10 + 1
             13                    11 = ((13 - 1)/10) * 10 + 1
             14                    11
             15                    11
             16                    11
             17                    11
             18                    11 
             19                    11 
             20                    11 = ((20 - 1)/10) * 10 + 1
             
             21                    21 = ((21 - 1)/10) * 10 + 1
             22                    21 = ((22 - 1)/10) * 10 + 1
             23                    21 = ((23 - 1)/10) * 10 + 1
             ..                    ..
             29                    21
             30                    21 = ((30 - 1)/10) * 10 + 1
    */  
        
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
	
	
	
	////////////////////////////////////////////////////////////////////
	// 로그인한 유저의 소속부서를 확인하는 메소드
	   private boolean checkDepartment(HttpServletRequest request, String deptnum) {
	      
	      boolean checkDepartment = false;
	      
	      HttpSession session = request.getSession();
	      
	      EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
	      
	      if(loginEmp != null && deptnum.equals(loginEmp.getFk_deptnum())) {
	         checkDepartment = true;
	      }
	      
	      return checkDepartment;
	      
	   }
	////////////////////////////////////////////////////////////////////////
	
}
