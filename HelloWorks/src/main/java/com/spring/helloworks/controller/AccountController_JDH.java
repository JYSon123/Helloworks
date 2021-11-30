package com.spring.helloworks.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.employees.model.DepartmentVO_ce;
import com.spring.helloworks.model.EmpVO_KJH;
import com.spring.helloworks.model_JDH.AccountingVO_JDH;
import com.spring.helloworks.model_JDH.CardVO_JDH;
import com.spring.helloworks.service_JDH.InterAccountService_JDH;
import com.spring.helloworks.util.FileManager;
import com.spring.helloworks.util.MyUtil;

@Controller
public class AccountController_JDH {

	@Autowired
	private InterAccountService_JDH service;
	
	@Autowired // Type에 따라 알아서 Bean 을 주입해준다.
	private FileManager fileManager;
	
	@RequestMapping(value = "/empAccountList.hello2") // requiredLogin_
	public ModelAndView requiredLogin_list_empAccountList(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ) {
		getCurrentURL(request);
		
		List<AccountingVO_JDH> accountingList = null;
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"empname".equals(searchType) && !"accountempid".equals(searchType))) {         
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
		
		totalCount = service.getAccountCount(paraMap);
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
		// System.out.println(String.valueOf(startRno) + "," + String.valueOf(endRno));
	    accountingList = service.accountingListWithPaging(paraMap);
	    // System.out.println("어카운트사이즈 컨트롤러" + accountingList.size());
	    if(!"".equals(searchType) && !"".equals(searchWord)) {
		       mav.addObject("paraMap", paraMap);
		    }
		      
		    int blockSize = 10;
		    
		    
		    
		    int loop = 1;
		    
		    int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
			
		    String pageBar = "<ul style='list-style: none;' class='px-0'>";
		      
		    String url = "empAccountList.hello2";
			
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
		    
		    mav.addObject("accountingList", accountingList);
		      
		    mav.setViewName("accounting/empAccount.tiles1");
		    
		    List<Map<String,String>> empList = service.empList();
		    
		    mav.addObject("empList",empList);
		    
		    
		return mav;
		
	}
	
	// 계좌 삭제
	@RequestMapping(value = "/deleteAccount.hello2") // requiredLogin_
	public ModelAndView requiredLogin_list_deleteAccount(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ) {
		
		String sAccountseq = request.getParameter("sAccountseq");
		
		Map<String,Object> paraMap = new HashMap<>();
		
		if(sAccountseq != null && !"".equals(sAccountseq)) {
			String[] arrAccountseq = sAccountseq.split(","); 
			
			paraMap.put("arrAccountseq", arrAccountseq);
			
			request.setAttribute("sAccountseq", sAccountseq);
			
		}
		
		int n = service.deleteAccount(paraMap);
		// System.out.println(n);
		
		mav.addObject("sAccountseq", sAccountseq);
		mav.setViewName("msg_KJH");
		
		mav.addObject("message", "계좌 삭제 성공");
  	  	mav.addObject("loc", request.getContextPath()+"/empAccountList.hello2");
		
		return mav;		
	}
	
	// 계좌 등록
	@RequestMapping(value = "/addAccount.hello2") // requiredLogin_
	public ModelAndView requiredLogin_list_addAccount(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ) {
		
		List<EmpVO_KJH> empList = null;
		
		String accountempid = request.getParameter("accountempid");
		String accountbank = request.getParameter("accountbank");
		String accountnumber = request.getParameter("accountnumber");
		String empid = request.getParameter("empid");
		String empname = request.getParameter("empname");
		String deptname = request.getParameter("deptname");
		
		
		Map<String,String> paraMap = new HashMap<>();
		
		paraMap.put("empid", empid);
		paraMap.put("empname", empname);
		paraMap.put("deptname", deptname);
		
		paraMap.put("accountempid", accountempid);
		paraMap.put("accountbank", accountbank);
		paraMap.put("accountnumber", accountnumber);
		
		int n = service.addAccount(paraMap);
		
		mav.addObject("empList", empList);
		
		mav.setViewName("msg_KJH");		  
		mav.addObject("message", "계좌 등록 성공"); 
		mav.addObject("loc", request.getContextPath()+"/empAccountList.hello2");
		 
		return mav;

	}
	
	// 계좌 수정
	@RequestMapping(value = "/editAccount.hello2") // requiredLogin_
	public ModelAndView requiredLogin_list_editAccount(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ) {
		
		List<EmpVO_KJH> empList = null;
		
		String accountempid = request.getParameter("editaccountempid");
		String accountbank = request.getParameter("editaccountbank");
		String accountnumber = request.getParameter("editaccountnumber");
		
		Map<String,String> paraMap = new HashMap<>();
		
		
		paraMap.put("accountempid", accountempid);
		paraMap.put("accountbank", accountbank);
		paraMap.put("accountnumber", accountnumber);
		
		int n = service.editAccount(paraMap);
		
		mav.addObject("empList", empList);
		
		if(n != 0) {
			mav.setViewName("msg_KJH");		  
			mav.addObject("message", "계좌 수정 성공"); 
			mav.addObject("loc", request.getContextPath()+"/empAccountList.hello2");
			
		}
		
		else {
			mav.setViewName("msg_KJH");		  
			mav.addObject("message", "계좌 수정 실패"); 
			mav.addObject("loc", request.getContextPath()+"/empAccountList.hello2");
			
		}
		return mav;
	}	
	
	// 카드 리스트
	@RequestMapping(value = "/empCardList.hello2") // requiredLogin_
	public ModelAndView requiredLogin_list_empCard(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ) {
		
		getCurrentURL(request);
		
		List<CardVO_JDH> cardList = null;
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null || (!"empname".equals(searchType) && !"cardempid".equals(searchType))) {         
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
		
		totalCount = service.getCardCount(paraMap);
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
		// System.out.println(String.valueOf(startRno) + "," + String.valueOf(endRno));
	    cardList = service.cardList(paraMap);
	    // System.out.println("카드 컨트롤러" + cardList.size());
	    if(!"".equals(searchType) && !"".equals(searchWord)) {
		       mav.addObject("paraMap", paraMap);
		    }
		      
		    int blockSize = 10;
		    
		    
		    
		    int loop = 1;
		    
		    int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
			
		    String pageBar = "<ul style='list-style: none;' class='px-0'>";
		      
		    String url = "empCard.hello2";
			
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
		    
		    mav.addObject("cardList", cardList);
		      
		    mav.setViewName("accounting/empCard.tiles1");
		    
		    List<Map<String,String>> empCardList = service.empCardList();
		    
		    mav.addObject("empCardList",empCardList);
		
		return mav;
	}
	
	// 카드 등록
	@RequestMapping(value = "/addCard.hello2") // requiredLogin_
	public ModelAndView requiredLogin_list_addCard(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ) {
		
		List<EmpVO_KJH> empCardList = null;
		
		String cardempid = request.getParameter("cardempid");
		String cardcompany = request.getParameter("cardcompany");
		String cardnumber = request.getParameter("cardnumber");
		String empid = request.getParameter("empid");
		String empname = request.getParameter("empname");
		String deptname = request.getParameter("deptname");
		
		
		Map<String,String> paraMap = new HashMap<>();
		
		paraMap.put("empid", empid);
		paraMap.put("empname", empname);
		paraMap.put("deptname", deptname);
		
		paraMap.put("cardempid", cardempid);
		paraMap.put("cardcompany", cardcompany);
		paraMap.put("cardnumber", cardnumber);
		
		int n = service.addCard(paraMap);
		
		mav.addObject("empCardList", empCardList);
		
		mav.setViewName("msg_KJH");		  
		mav.addObject("message", "카드 등록 성공"); 
		mav.addObject("loc", request.getContextPath()+"/empCardList.hello2");
		 
		return mav;

	}
	
	// 카드 수정
	@RequestMapping(value = "/editCard.hello2") // requiredLogin_
	public ModelAndView requiredLogin_list_editCard(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ) {
		
		List<EmpVO_KJH> empCardList = null;
		
		String cardempid = request.getParameter("editcardempid");
		String cardcompany = request.getParameter("editcardcompany");
		String cardnumber = request.getParameter("editcardnumber");
		
		Map<String,String> paraMap = new HashMap<>();
		
		
		paraMap.put("cardempid", cardempid);
		paraMap.put("cardcompany", cardcompany);
		paraMap.put("cardnumber", cardnumber);
		
		int n = service.editCard(paraMap);
		
		mav.addObject("empCardList", empCardList);
		
		if(n != 0) {
			mav.setViewName("msg_KJH");		  
			mav.addObject("message", "카드 수정 성공"); 
			mav.addObject("loc", request.getContextPath()+"/empCardList.hello2");
			
		}
		
		else {
			mav.setViewName("msg_KJH");		  
			mav.addObject("message", "카드 수정 실패"); 
			mav.addObject("loc", request.getContextPath()+"/empCardList.hello2");
			
		}
		return mav;
	}	
	
	// 계좌 삭제
	@RequestMapping(value = "/deleteCard.hello2") // requiredLogin_
	public ModelAndView requiredLogin_list_deleteCard(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ) {
		
		String sCardseq = request.getParameter("sCardseq");
		
		Map<String,Object> paraMap = new HashMap<>();
		
		if(sCardseq != null && !"".equals(sCardseq)) {
			String[] arrCardseq = sCardseq.split(","); 
			
			paraMap.put("arrCardseq", arrCardseq);
			
			request.setAttribute("sCardseq", sCardseq);
			
		}
		
		int n = service.deleteCard(paraMap);
		// System.out.println(n);
		
		mav.addObject("sCardseq", sCardseq);
		mav.setViewName("msg_KJH");
		
		mav.addObject("message", "법인카드 삭제 성공");
  	  	mav.addObject("loc", request.getContextPath()+"/empCardList.hello2");
		
		return mav;		
				
	}
	
	

	
	// 로그인, 로그아웃시 페이지 유지
	private void getCurrentURL(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("goBackURL", MyUtil.getCurrentURL(request) );
		
	}
	
	
}
