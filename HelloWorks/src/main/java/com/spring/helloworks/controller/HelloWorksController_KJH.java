package com.spring.helloworks.controller;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spring.helloworks.common_KJH.*;
import com.spring.helloworks.model.*;
import com.spring.helloworks.service.InterHelloWorksService_KJH;

@Component
@Controller
public class HelloWorksController_KJH {
	
	@Autowired
	private InterHelloWorksService_KJH service;
	
	@Autowired
	private GoogleOTP gotp;
	
	@Autowired
	private GoogleMail gmail;
	
	@Autowired
	private ExcelMaker excel;
	
	@RequestMapping(value="/login.hello2")
	public ModelAndView login(ModelAndView mav, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		
		if(loginEmp == null) {
			mav.setViewName("login");
		}
		
		else {
			mav.setViewName("redirect:/index.hello2");
		}
		
		return mav;
		
	}
	
	// 로그인처리
	@RequestMapping(value="/loginEnd.hello2", method= {RequestMethod.POST})
	public ModelAndView loginEnd(ModelAndView mav, HttpServletRequest request) {
		
		String empid = request.getParameter("empid");
		String emppw = request.getParameter("emppw");
		
		Map<String, String> paraMap = new HashMap<> ();
		
		paraMap.put("empid", empid);
		paraMap.put("emppw", Sha256.encrypt(emppw));
		
		EmpVO_KJH loginEmp = service.getLoginEmp(paraMap);
		
		if(loginEmp == null) {
			
			String message = "아이디 또는 암호가 일치하지 않습니다.";
			String loc = request.getContextPath() + "/login.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");
			
		}
		
		else {
			
			if(loginEmp.getEmpstatus() == 2) {
				
				String message = loginEmp.getEmpname() + "님은 현재 휴직상태입니다. 복직 후 로그인 가능합니다.";
				String loc = request.getContextPath() + "/login.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
			
			else {
				
				HttpSession session = request.getSession();
				
				session.setAttribute("loginEmp", loginEmp);
				
				if(loginEmp.isRequiredGetOTPkey()) 					
					mav.setViewName("redirect:/otpRegister.hello2");
				
				else 					
					mav.setViewName("redirect:/otp.hello2");					
				
			}
			
		}
		
		return mav;
		
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	
	// otp 등록 페이지
	@RequestMapping(value="/otpRegister.hello2")
	public ModelAndView otpRegister(ModelAndView mav, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		
		if(loginEmp == null || (loginEmp != null && loginEmp.getOtpkey() == null)) {
			
			String message = "로그인 후 접근 가능합니다.";
			String loc = request.getContextPath() + "/login.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");
			
		}
		
		else if(loginEmp != null && loginEmp.isRequiredGetOTPkey() == true)
			mav.setViewName("otpRegister");
		
		else
			mav.setViewName("redirect:/index.hello2");
		
		return mav;
	
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
		
	// otp 인증 페이지
	@RequestMapping(value="/otp.hello2")
	public ModelAndView otp(ModelAndView mav, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		
		if(loginEmp == null) {
			
			String message = "로그인 후 접근 가능합니다.";
			String loc = request.getContextPath() + "/login.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");
			
		}
		
		else {
			
			loginEmp.setRequiredGetOTPkey(false);
			
			mav.setViewName("otp");
			
		}
		
		return mav;
	
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	// otp 인증번호 확인
	@RequestMapping(value="/otpEnd.hello2", method= {RequestMethod.POST})
	public ModelAndView otpEnd(ModelAndView mav, HttpServletRequest request) {
		
		String usercode = request.getParameter("usercode");
		
		HttpSession session = request.getSession();
		
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		
		if(loginEmp == null || (loginEmp != null && loginEmp.getOtpkey() == null)) {
			
			String message = "로그인 후 접근 가능합니다.";
			String loc = request.getContextPath() + "/login.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");
			
		}
		
		else {
			
			boolean otpcheck = gotp.checkCode(usercode, loginEmp.getOtpkey());
			
			// OTP 인증 실패
			if(!otpcheck) {
				
				String message = "인증번호가 일치하지 않거나, 만료된 인증번호입니다.";
				String loc = request.getContextPath() + "/otp.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
			
			// OTP 인증 성공 ==> 최종 로그인(세션에 시간 + otp인증완료여부 저장, loginHistory에 insert 진행)
			else {
				
				String fk_empid = loginEmp.getEmpid();
				String clientip = request.getRemoteAddr();
				
				Map<String, String> paraMap = new HashMap<> ();
				
				paraMap.put("fk_empid", fk_empid);
				paraMap.put("clientip", clientip);
				
				int n = service.insertLoginHistory(paraMap);
				
				if(n == 0) {
					
					String message = "로그인에 실패하였습니다.";
					String loc = request.getContextPath() + "/login.hello2";
					
					mav.addObject("message", message);
					mav.addObject("loc", loc);
					
					mav.setViewName("msg_KJH");
					
				}
				
				else {
					
					// 세션만료를 위한 로그인 시간 저장
					Calendar cal = Calendar.getInstance();
					
					cal.add(Calendar.MINUTE, 30);
					
					String validtime = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", cal);
					// 20211108092715
					
					session.setAttribute("validtime", validtime);
					
					// otp인증완료여부 저장
					session.setAttribute("otpcheck", otpcheck);
					
					// +++ 사업자정보 불러와서 세션에 저장하기 +++ //
					MycompanyVO_KJH comp = service.getMycomp();
					
					if(comp != null && !"".equals(comp.getMycompany_id())) {
						
						session.setAttribute("comp", comp);
						
					}
					
					// 로그인 검사 할 때마다 goBackURL을 세션에 저장, validtime 확인 후 갱신
					String goBackURL = (String)session.getAttribute("goBackURL");
					
					if(goBackURL != null && !loginEmp.isRequiredPwdChange()) {
						
						mav.setViewName("redirect:" + goBackURL);
						
						session.removeAttribute("goBackURL");
						
					}
					
					// 암호변경이 필요할 경우 암호변경페이지로 이동시켜주기
					else if(loginEmp.isRequiredPwdChange()) {
						mav.setViewName("redirect:/pwdChange.hello2");						
					}
					
					else {
						mav.setViewName("redirect:/index.hello2");						
					}
					
				}
				
			}
		
		}

		return mav;
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	// 암호변경페이지
	@RequestMapping(value="/pwdChange.hello2")
	public ModelAndView requiredLogin_pwdChange(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		HttpSession session = request.getSession();
		
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		
		if(loginEmp.isRequiredPwdChange() == true) {
			mav.setViewName("pwdChange");
		}
		
		else {
			mav.setViewName("redirect:/index.hello2");	
		}		
		
		return mav;
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	// 암호변경완료
	@RequestMapping(value="/pwdChangeEnd.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_pwdChangeEnd(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		HttpSession session = request.getSession();
		
		EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
		
		String currentpw = request.getParameter("currentpw");
				
		if(loginEmp.getEmppw().equals(Sha256.encrypt(currentpw))) {
			
			String empid = loginEmp.getEmpid();
			
			String newpw = request.getParameter("newpw");
			
			newpw = Sha256.encrypt(newpw);
			
			Map<String, String> paraMap = new HashMap<> ();
			
			paraMap.put("empid", empid);
			paraMap.put("newpw", newpw);
			
			int n = service.updateEmppw(paraMap);
			
			if(n == 0) {
				
				String message = "암호변경에 실패하였습니다.";
				String loc = request.getContextPath() + "/pwdChange.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
			
			else {
				
				loginEmp.setEmppw(newpw);
				loginEmp.setRequiredPwdChange(false);
				
				String message = "암호가 변경되었습니다.";
				String loc = request.getContextPath() + "/index.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");					
							
			}
			
		}
		
		else {
			
			String message = "현재 암호가 올바르지 않습니다.";
			String loc = request.getContextPath() + "/pwdChange.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");
			
		}
		
		return mav;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/home.hello2")
	public ModelAndView requiredLogin_accountHome(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment) {
			
			String thisMonth = request.getParameter("thisMonth");
			System.out.println("1번" + thisMonth);
			if(thisMonth == null || "".equals(thisMonth)) {
				System.out.println("널임");
				Calendar cal = Calendar.getInstance();
				
				thisMonth = String.format("%1$tY-%1$tm", cal);
				
			}
			
			String[] arrMonth = thisMonth.split("-");
			
			if(arrMonth.length > 2) {
				System.out.println("길다");
				Calendar cal = Calendar.getInstance();
				
				thisMonth = String.format("%1$tY-%1$tm", cal);
				
			}
			
			try {
				
				Integer.parseInt(arrMonth[0] + arrMonth[1]);
				
			} catch (NumberFormatException e) {
				System.out.println("숫자아냐");
				Calendar cal = Calendar.getInstance();
				
				thisMonth = String.format("%1$tY-%1$tm", cal);
				
			}
			System.out.println(thisMonth);
			arrMonth = thisMonth.split("-");
			
			// 세금계산서 수정여부
			List<Map<String, String>> billtaxEditList = service.getBilltaxEditList(thisMonth);
			
			// 계산서 수정여부
			List<Map<String, String>> billnotaxEditList = service.getBillnotaxEditList(thisMonth);
			
			// 세금계산서 전송상태
			List<Map<String, String>> billtaxStatusList = service.getBilltaxStatusList(thisMonth);
			
			// 계산서 전송상태
			List<Map<String, String>> billnotaxStatusList = service.getBillnotaxStatusList(thisMonth);
			
			// 거래명세서 전송상태
			List<Map<String, String>> transactionStatusList = service.getTransactionStatusList(thisMonth);
						
			mav.addObject("billtaxEditList", billtaxEditList);
			mav.addObject("billnotaxEditList", billnotaxEditList);
			mav.addObject("billtaxStatusList", billtaxStatusList);
			mav.addObject("billnotaxStatusList", billnotaxStatusList);
			mav.addObject("transactionStatusList", transactionStatusList);
			mav.addObject("showMonth", arrMonth[1]);
			mav.addObject("thisMonth", thisMonth);
			
			mav.setViewName("account/home.tiles1");
			
		}
		
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/editCompany.hello2")
	public ModelAndView requiredLogin_editCompany(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{
			
			HttpSession session = request.getSession();
			
			EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
			
			if(loginEmp.getRanking() >= 3) { // 부장이상이라면
				mav.setViewName("account/editCompany.tiles1");
			}
			
			else { // 부장보다 낮은 직급이라면
				String message = "부장 이상의 직급만 접근 가능합니다.";
				String loc = request.getContextPath() + "/account/home.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
			}			
			
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/registerCompany.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_registerCompany(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, MycompanyVO_KJH comp) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{
			
			HttpSession session = request.getSession();
			
			EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
			
			if(loginEmp.getRanking() >= 3) { // 부장이상이라면
				
				int n = service.registerCompany(comp);
				
				if(n != 0) {
					
					// +++ 사업자정보 세션에 저장하기 +++ //
					session.setAttribute("comp", comp);
					
					String message = "사업자 정보 등록이 완료되었습니다.";
					String loc = request.getContextPath() + "/account/editCompany.hello2";
					
					mav.addObject("message", message);
					mav.addObject("loc", loc);
					
					mav.setViewName("msg_KJH");
					
				}
				
				else {
					
					String message = "사업자 정보 등록에 실패하였습니다.";
					String loc = request.getContextPath() + "/account/editCompany.hello2";
					
					mav.addObject("message", message);
					mav.addObject("loc", loc);
					
					mav.setViewName("msg_KJH");
					
				}
				
			}
			
			else { // 부장보다 낮은 직급이라면
				String message = "부장 이상의 직급만 접근 가능합니다.";
				String loc = request.getContextPath() + "/account/home.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
			}			
			
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
		
	@RequestMapping(value="/account/updateCompany.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_updateCompany(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, MycompanyVO_KJH comp) {
	
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{
		
			HttpSession session = request.getSession();
			
			EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
			
			if(loginEmp.getRanking() >= 3) { // 부장이상이라면
			
				int n = service.updateCompany(comp);
				
				if(n != 0) {
				
					// +++ 사업자정보 세션에 저장하기 +++ //
					session.setAttribute("comp", comp);
					
					String message = "사업자 정보 수정이 완료되었습니다.";
					String loc = request.getContextPath() + "/account/editCompany.hello2";
					
					mav.addObject("message", message);
					mav.addObject("loc", loc);
					
					mav.setViewName("msg_KJH");
				
				}
				
				else {
				
					String message = "사업자 정보 수정에 실패하였습니다.";
					String loc = request.getContextPath() + "/account/editCompany.hello2";
					
					mav.addObject("message", message);
					mav.addObject("loc", loc);
					
					mav.setViewName("msg_KJH");
				
				}
			
			}
			
			else { // 부장보다 낮은 직급이라면
				String message = "부장 이상의 직급만 접근 가능합니다.";
				String loc = request.getContextPath() + "/account/home.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
			}			
		
		}
		
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
	
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
		
	@RequestMapping(value="/account/deleteCompany.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_deleteCompany(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{
		
			HttpSession session = request.getSession();
			
			EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
			
			if(loginEmp.getRanking() >= 3) { // 부장이상이라면
				
				String mycompany_id = request.getParameter("mycompany_id");
				
				int n = service.deleteCompany(mycompany_id);
				
				if(n != 0) {
				
					// +++ 사업자정보 세션 삭제하기 +++ //
					session.removeAttribute("comp");
					
					String message = "사업자 정보 삭제가 완료되었습니다.";
					String loc = request.getContextPath() + "/account/editCompany.hello2";
					
					mav.addObject("message", message);
					mav.addObject("loc", loc);
					
					mav.setViewName("msg_KJH");
				
				}
				
				else {
				
					String message = "사업자 정보 삭제에 실패하였습니다.";
					String loc = request.getContextPath() + "/account/editCompany.hello2";
					
					mav.addObject("message", message);
					mav.addObject("loc", loc);
					
					mav.setViewName("msg_KJH");
				
				}
			
			}
			
			else { // 부장보다 낮은 직급이라면
				String message = "부장 이상의 직급만 접근 가능합니다.";
				String loc = request.getContextPath() + "/account/home.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
			}			
		
		}
		
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
	
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/manageCustomer.hello2")
	public ModelAndView requiredLogin_manageCustomer(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{
			
			String searchType = request.getParameter("searchType");
			String searchWord = request.getParameter("searchWord");
			String str_currentShowPageNo = request.getParameter("currentShowPageNo");
						
			if(searchType == null || (!"customer_id".equals(searchType) && !"customer_comp".equals(searchType) && !"customer_name".equals(searchType))) {			
				searchType = "";			
			}
			
			if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty()) {			
				searchWord = "";			
			}
			
			Map<String, String> paraMap = new HashMap<> ();

			paraMap.put("searchType", searchType);
			paraMap.put("searchWord", searchWord);
			
			int totalCount = 0; 		// 총 거래처 수		
			int sizePerPage = 10; 		// 한 페이지당 보여줄 거래처 건수
			int currentShowPageNo = 0; 	// 현재 보여주는 페이지 번호로, 초기치는 1페이지로 설정함	
			int totalPage = 0; 			// 총 페이지 수(웹브러우저상에서 보여줄 총 페이지 갯수, 페이지바)(totalCount/sizePerPage 올림)
			
			int startRno = 0; 			// 시작 행번호
			int endRno = 0; 			// 끝  행번호
			
			totalCount = service.getTotalCount(paraMap);
			
			totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
			
			if(str_currentShowPageNo == null) { // 거래처관리 초기화면
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
			
			List<CustomerVO_KJH> cvoList = service.getCustomerList(paraMap);
			
			if(!"".equals(searchType) && !"".equals(searchWord)) {
				mav.addObject("paraMap", paraMap);
			}
			
			int blockSize = 10;
			
			int loop = 1;
			
			int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
			
			String pageBar = "";
			
			String url = request.getContextPath() + "/account/manageCustomer.hello2";
			
			if(pageNo != 1) {
				pageBar += "<li class='page-item'>"
			 			+ "<a class='page-link' href='" + url + "?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=1'>[맨처음]</a>"
			 			+ "</li>";
		
				pageBar += "<li class='page-item'>"
			 			+ "<a class='page-link' href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + (pageNo - 1) + "'>[이전]</a>"
			 			+ "</li>";				
			}
							
			while(!(loop > blockSize || pageNo > totalPage)) {
				
				if(pageNo == currentShowPageNo) 
					pageBar += "<li class='page-item active'>"
					     	+ "<a class='page-link' href='#'>" + pageNo + "</a>"
					     	+ "</li>";
				else
					pageBar += "<li class='page-item'>"
			 				+ "<a class='page-link' href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + pageNo + "'>" + pageNo + "</a>"
			 				+ "</li>";
				
				loop++;
				pageNo++;
				
			}// end of while-------------------------
			
			// +++ [다음][마지막] 만들기 +++
			
			if(pageNo <= totalPage) {
				pageBar += "<li class='page-item'>"
				 		+ "<a class='page-link' href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + (pageNo) + "'>[다음]</a>"
				 		+ "</li>";
			
				pageBar += "<li class='page-item'>"
			 			+ "<a class='page-link' href='" + url +"?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + totalPage + "'>[마지막]</a>"
			 			+ "</li>";			
			}
						
			mav.addObject("pageBar", pageBar);
			
			mav.addObject("cvoList", cvoList);
			
			mav.setViewName("account/manageCustomer.tiles1");
						
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/registerCustomer.hello2")
	public ModelAndView requiredLogin_registerCustomer(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{						
			mav.setViewName("account/registerCustomer.tiles1");						
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	@ResponseBody
	@RequestMapping(value="/account/verifyId.hello2", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String requiredLogin_verifyId(HttpServletRequest request, HttpServletResponse response) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{			
			
			String compid = request.getParameter("compid");
						
			int isExist = service.verifyId(compid);
			
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("isExist", isExist);
			
			return jsonObj.toString();
						
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			return "msg_KJH";			
		}
				
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/insertCustomer.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_insertCustomer(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, CustomerVO_KJH comp) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{			
				
			int n = service.insertCustomer(comp);
			
			if(n != 0) {
								
				String message = "거래처 등록이 완료되었습니다.";
				String loc = request.getContextPath() + "/account/manageCustomer.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
			
			else {
				
				String message = "거래처 등록에 실패하였습니다.";
				String loc = request.getContextPath() + "/account/manageCustomer.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
						
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 수정 페이지 요청
	@RequestMapping(value="/account/modifyCustomer.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_modifyCustomer(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{			
			
			String customer_id = request.getParameter("customer_id");
			
			CustomerVO_KJH customer = service.selectCustomer(customer_id);
			
			if(customer != null && customer.getCustomer_id() != "") {
								
				mav.addObject("customer", customer);
				
				mav.setViewName("account/modifyCustomer.tiles1");
				
			}
			
			else {
				
				String message = "해당 거래처가 존재하지 않습니다.";
				String loc = request.getContextPath() + "/account/manageCustomer.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
						
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 정보 UPDATE
	@RequestMapping(value="/account/updateCustomer.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_updateCustomer(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, CustomerVO_KJH comp) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{			
			
			int n = service.updateCustomer(comp);
			
			if(n != 0) {
								
				String message = "거래처 정보 수정이 완료되었습니다.";
				String loc = request.getContextPath() + "/account/manageCustomer.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
			
			else {
				
				String message = "거래처 정보 수정에 실패하였습니다.";
				String loc = request.getContextPath() + "/account/manageCustomer.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
						
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처 DELETE
	@RequestMapping(value="/account/deleteCustomer.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_deleteCustomer(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{			
			
			String customer_id = request.getParameter("customer_id");
			
			int n = service.deleteCustomer(customer_id);
			
			if(n != 0) {
								
				String message = "거래처 삭제가 완료되었습니다.";
				String loc = request.getContextPath() + "/account/manageCustomer.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
			
			else {
				
				String message = "거래처 삭제에 실패하였습니다.";
				String loc = request.getContextPath() + "/account/manageCustomer.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
						
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	// 거래처에 메일발송
	@RequestMapping(value="/account/sendEmail.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_sendEmail(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{			
			
			String rec_company = request.getParameter("rec_company");
			String rec_name = request.getParameter("rec_name");
			
			String content = request.getParameter("content");
			
			content = content.replaceAll("\r\n", "<br>");
			        	
        	StringBuilder sb = new StringBuilder();
        	        	
        	sb.append("<div style='width: 70%; padding: 0px auto; border: solid 4px #003399; border-radius: 20px; word-break: break-all;'>");
        	
        	sb.append("<div style='text-align: center; background-color: white; border-radius: 20px 20px 0 0; width: 100%; padding: 40px auto;'><br><br><img src='http://127.0.0.1:9090/helloworks/resources/images/maillogo.png' style='margin 10px auto; width: 60%;'><br><br><br></div>");
        	
        	sb.append("<div style='width: 100%; background-color: #e6eeff; border-radius: 0 0 20px 20px; padding: 20px 0;'>");
        	
        	sb.append("<p style='width: 100%; text-align: center;'>" + content + "</p>");
        	        	
        	sb.append("<br><br><br><br><p style='width: 100%; text-align: center; color: #002b80; font-size: 15pt;'><strong>helloworks</strong>와 함께해주셔서 감사합니다.</p>");
        	
        	sb.append("</div>");
        	
        	sb.append("</div>");
			
        	String emailContents = sb.toString();
        	
        	try {
				
        		gmail.sendmail_customer(rec_company, rec_name, emailContents);
								
				String message = "메일이 전송되었습니다.";
				String loc = request.getContextPath() + "/account/manageCustomer.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");				
				
			} catch (Exception e) {
				
				String message = "메일 전송이 실패하였습니다.";
				String loc = request.getContextPath() + "/account/manageCustomer.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
        							
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/writeBillTax.hello2")
	public ModelAndView requiredLogin_writeBillTax_requiredComp(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{
			
			if("POST".equalsIgnoreCase(request.getMethod())) {
				String editRegdate = request.getParameter("editRegdate");
				String editSeq = request.getParameter("editSeq");
				
				mav.addObject("editRegdate", editRegdate);
				mav.addObject("editSeq", editSeq);
				
			}
			
			mav.setViewName("account/writeBillTax.tiles1");				
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	@ResponseBody
	@RequestMapping(value="/account/searchCustomer.hello2", produces="text/plain;charset=UTF-8")
	public String requiredLogin_searchCustomer(HttpServletRequest request, HttpServletResponse response) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{			
			
			List<CustomerVO_KJH> cvoList = service.getCustomerListNoPaging();
			
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("cvoList", cvoList);
			
			return jsonObj.toString();
						
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			return "msg_KJH";			
		}
				
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/insertBillTax.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_insertBillTax_requiredComp(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, BilltaxVO_KJH btvo) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{						
			
			// 세금계산서 시퀀스 채번
			String billtax_seq = service.getBillTaxSeq();
			
			btvo.setBilltax_seq(billtax_seq);
			
			HttpSession session = request.getSession();
			
			MycompanyVO_KJH comp = (MycompanyVO_KJH)session.getAttribute("comp");
			
			EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
			
			btvo.setMycompany_id(comp.getMycompany_id());
			btvo.setMycompany_comp(comp.getMycompany_comp());
			btvo.setMycompany_name(comp.getMycompany_name());
			btvo.setMycompany_addr(comp.getMycompany_addr());
			
			btvo.setEmpid(loginEmp.getEmpid());
			btvo.setEmpname(loginEmp.getEmpname());
			
			// 세금계산서 상세 객체 생성
			BilltaxDetailVO_KJH dvo = new BilltaxDetailVO_KJH();
			
			dvo.setFk_billtax_seq(billtax_seq);
			dvo.setSelldate(request.getParameter("selldate"));
			dvo.setSellprod(request.getParameter("sellprod"));
			dvo.setSellamount(request.getParameter("sellamount"));
			dvo.setSelloneprice(request.getParameter("selloneprice"));
			dvo.setSelltotalprice(request.getParameter("selltotalprice"));
			dvo.setSelltax(request.getParameter("selltax"));
			
			Map<String, Object> paraMap = new HashMap<> ();
			
			paraMap.put("btvo", btvo);
			paraMap.put("dvo", dvo);
			
			int result = service.insertBillTax(paraMap);
			
			if(result != 0) {
				String message = "전자세금계산서 작성이 완료되었습니다.";
				String loc = request.getContextPath() + "/account/listBill.hello2?tabName=tbl_billtax";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
			else {
				String message = "전자세금계산서 작성에 실패하였습니다.";
				String loc = request.getContextPath() + "/account/writeBillTax.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}	

	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/updateBillTax.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_updateBillTax_requiredComp(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, BilltaxVO_KJH btvo) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{						
						
			String billtax_seq = btvo.getBilltax_seq();
			
			HttpSession session = request.getSession();
			
			MycompanyVO_KJH comp = (MycompanyVO_KJH)session.getAttribute("comp");
			
			EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
			
			btvo.setMycompany_id(comp.getMycompany_id());
			btvo.setMycompany_comp(comp.getMycompany_comp());
			btvo.setMycompany_name(comp.getMycompany_name());
			btvo.setMycompany_addr(comp.getMycompany_addr());
			
			btvo.setEmpid(loginEmp.getEmpid());
			btvo.setEmpname(loginEmp.getEmpname());
			
			// 세금계산서 상세 객체 생성
			BilltaxDetailVO_KJH dvo = new BilltaxDetailVO_KJH();
			
			dvo.setFk_billtax_seq(billtax_seq);
			dvo.setSelldate(request.getParameter("selldate"));
			dvo.setSellprod(request.getParameter("sellprod"));
			dvo.setSellamount(request.getParameter("sellamount"));
			dvo.setSelloneprice(request.getParameter("selloneprice"));
			dvo.setSelltotalprice(request.getParameter("selltotalprice"));
			dvo.setSelltax(request.getParameter("selltax"));
			
			Map<String, Object> paraMap = new HashMap<> ();
			
			paraMap.put("btvo", btvo);
			paraMap.put("dvo", dvo);
			
			int result = service.updateBillTax(paraMap);
			
			if(result != 0) {
				String message = "전자세금계산서 수정이 완료되었습니다.";
				String loc = request.getContextPath() + "/account/viewBill.hello2?tabName=tbl_billtax&seq=" + billtax_seq;
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
			else {
				String message = "전자세금계산서 수정에 실패하였습니다.";
				String loc = request.getContextPath() + "/account/viewBill.hello2?tabName=tbl_billtax&seq=" + billtax_seq;
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/writeBillNotax.hello2")
	public ModelAndView requiredLogin_writeBillNotax_requiredComp(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{		
			
			if("POST".equalsIgnoreCase(request.getMethod())) {
				String editRegdate = request.getParameter("editRegdate");
				String editSeq = request.getParameter("editSeq");
				
				mav.addObject("editRegdate", editRegdate);
				mav.addObject("editSeq", editSeq);
				
			}
			
			mav.setViewName("account/writeBillNotax.tiles1");				
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/insertBillNoTax.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_insertBillNoTax_requiredComp(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, BillnotaxVO_KJH bntvo) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{						
			
			// 계산서 시퀀스 채번
			String billnotax_seq = service.getBillNoTaxSeq();
			
			bntvo.setBillnotax_seq(billnotax_seq);
			
			HttpSession session = request.getSession();
			
			MycompanyVO_KJH comp = (MycompanyVO_KJH)session.getAttribute("comp");
			
			EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
			
			bntvo.setMycompany_id(comp.getMycompany_id());
			bntvo.setMycompany_comp(comp.getMycompany_comp());
			bntvo.setMycompany_name(comp.getMycompany_name());
			bntvo.setMycompany_addr(comp.getMycompany_addr());
			
			bntvo.setEmpid(loginEmp.getEmpid());
			bntvo.setEmpname(loginEmp.getEmpname());
			
			// 계산서 상세 객체 생성
			BillnotaxDetailVO_KJH ndvo = new BillnotaxDetailVO_KJH();
			
			ndvo.setFk_billnotax_seq(billnotax_seq);
			ndvo.setSelldate(request.getParameter("selldate"));
			ndvo.setSellprod(request.getParameter("sellprod"));
			ndvo.setSellamount(request.getParameter("sellamount"));
			ndvo.setSelloneprice(request.getParameter("selloneprice"));
			ndvo.setSelltotalprice(request.getParameter("selltotalprice"));
			
			Map<String, Object> paraMap = new HashMap<> ();
			
			paraMap.put("bntvo", bntvo);
			paraMap.put("ndvo", ndvo);
			
			int result = service.insertBillNoTax(paraMap);
			
			if(result != 0) {
				String message = "전자계산서 작성이 완료되었습니다.";
				String loc = request.getContextPath() + "/account/listBill.hello2?tabName=tbl_billnotax";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
			else {
				String message = "전자계산서 작성에 실패하였습니다.";
				String loc = request.getContextPath() + "/account/writeBillNotax.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}	

	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/updateBillNoTax.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_updateBillNoTax_requiredComp(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, BillnotaxVO_KJH bntvo) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{						
						
			String billnotax_seq = bntvo.getBillnotax_seq();
						
			HttpSession session = request.getSession();
			
			MycompanyVO_KJH comp = (MycompanyVO_KJH)session.getAttribute("comp");
			
			EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
			
			bntvo.setMycompany_id(comp.getMycompany_id());
			bntvo.setMycompany_comp(comp.getMycompany_comp());
			bntvo.setMycompany_name(comp.getMycompany_name());
			bntvo.setMycompany_addr(comp.getMycompany_addr());
			
			bntvo.setEmpid(loginEmp.getEmpid());
			bntvo.setEmpname(loginEmp.getEmpname());
			
			// 계산서 상세 객체 생성
			BillnotaxDetailVO_KJH ndvo = new BillnotaxDetailVO_KJH();
			
			ndvo.setFk_billnotax_seq(billnotax_seq);
			ndvo.setSelldate(request.getParameter("selldate"));
			ndvo.setSellprod(request.getParameter("sellprod"));
			ndvo.setSellamount(request.getParameter("sellamount"));
			ndvo.setSelloneprice(request.getParameter("selloneprice"));
			ndvo.setSelltotalprice(request.getParameter("selltotalprice"));
			
			Map<String, Object> paraMap = new HashMap<> ();
			
			paraMap.put("bntvo", bntvo);
			paraMap.put("ndvo", ndvo);
			
			int result = service.updateBillNoTax(paraMap);
			
			if(result != 0) {
				String message = "전자계산서 수정이 완료되었습니다.";
				String loc = request.getContextPath() + "/account/viewBill.hello2?tabName=tbl_billnotax&seq=" + billnotax_seq;
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
			else {
				String message = "전자계산서 수정에 실패하였습니다.";
				String loc = request.getContextPath() + "/account/viewBill.hello2?tabName=tbl_billnotax&seq=" + billnotax_seq;
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}	

	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/writeTransaction.hello2")
	public ModelAndView requiredLogin_writeTransaction_requiredComp(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{						
			mav.setViewName("account/writeTransaction.tiles1");				
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/insertTransaction.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_insertTransaction_requiredComp(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, TransactionVO_KJH tvo) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{						
			
			// 거래명세서 시퀀스 채번
			String transaction_seq = service.getTransactionSeq();
			
			tvo.setTransaction_seq(transaction_seq);
			
			HttpSession session = request.getSession();
			
			MycompanyVO_KJH comp = (MycompanyVO_KJH)session.getAttribute("comp");
			
			EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
			
			tvo.setMycompany_id(comp.getMycompany_id());
			tvo.setMycompany_comp(comp.getMycompany_comp());
			tvo.setMycompany_name(comp.getMycompany_name());
			tvo.setMycompany_addr(comp.getMycompany_addr());
			
			tvo.setEmpid(loginEmp.getEmpid());
			tvo.setEmpname(loginEmp.getEmpname());
			
			// 거레명세서 상세 객체 생성
			TransactionDetailVO_KJH tdvo = new TransactionDetailVO_KJH();
			
			tdvo.setFk_transaction_seq(transaction_seq);
			tdvo.setSelldate(request.getParameter("selldate"));
			tdvo.setSellprod(request.getParameter("sellprod"));
			tdvo.setSellamount(request.getParameter("sellamount"));
			tdvo.setSelloneprice(request.getParameter("selloneprice"));
			tdvo.setSelltotalprice(request.getParameter("selltotalprice"));
			
			Map<String, Object> paraMap = new HashMap<> ();
			
			paraMap.put("tvo", tvo);
			paraMap.put("tdvo", tdvo);
			
			int result = service.insertTransaction(paraMap);
			
			if(result != 0) {
				String message = "거래명세서 작성이 완료되었습니다.";
				String loc = request.getContextPath() + "/account/listBill.hello2?tabName=tbl_transaction";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
			else {
				String message = "거래명세서 작성에 실패하였습니다.";
				String loc = request.getContextPath() + "/account/writeTransaction.hello2";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}	


	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/updateTransaction.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_updateTransaction_requiredComp(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, TransactionVO_KJH tvo) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{						
			
			String transaction_seq = request.getParameter("transaction_seq");
			
			HttpSession session = request.getSession();
			
			MycompanyVO_KJH comp = (MycompanyVO_KJH)session.getAttribute("comp");
			
			EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
			
			tvo.setMycompany_id(comp.getMycompany_id());
			tvo.setMycompany_comp(comp.getMycompany_comp());
			tvo.setMycompany_name(comp.getMycompany_name());
			tvo.setMycompany_addr(comp.getMycompany_addr());
			
			tvo.setEmpid(loginEmp.getEmpid());
			tvo.setEmpname(loginEmp.getEmpname());
			
			// 거레명세서 상세 객체 생성
			TransactionDetailVO_KJH tdvo = new TransactionDetailVO_KJH();
			
			tdvo.setFk_transaction_seq(transaction_seq);
			tdvo.setSelldate(request.getParameter("selldate"));
			tdvo.setSellprod(request.getParameter("sellprod"));
			tdvo.setSellamount(request.getParameter("sellamount"));
			tdvo.setSelloneprice(request.getParameter("selloneprice"));
			tdvo.setSelltotalprice(request.getParameter("selltotalprice"));
			
			Map<String, Object> paraMap = new HashMap<> ();
			
			paraMap.put("tvo", tvo);
			paraMap.put("tdvo", tdvo);
			
			int result = service.updateTransaction(paraMap);
			
			if(result != 0) {
				String message = "거래명세서 수정이 완료되었습니다.";
				String loc = request.getContextPath() + "/account/viewBill.hello2?tabName=tbl_transaction&seq=" + transaction_seq;
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
			else {
				String message = "거래명세서 수정에 실패하였습니다.";
				String loc = request.getContextPath() + "/account/viewBill.hello2?tabName=tbl_transaction&seq=" + transaction_seq;
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}	

	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/listBill.hello2")
	public ModelAndView requiredLogin_listBill(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{
			
			Map<String, String> paraMap = new HashMap<> ();
			
			// 탭기능(default 세금계산서로 설정, 파라미터로 사용하자!)
			String tabName = request.getParameter("tabName");
			
			if(!"tbl_billtax".equalsIgnoreCase(tabName) 
					&& !"tbl_billnotax".equalsIgnoreCase(tabName) 
					&& !"tbl_transaction".equalsIgnoreCase(tabName)) {
				
				tabName = "tbl_billtax";
				
			}
			
			paraMap.put("tabName", tabName);
									
			// 기간검색기능
			String startDate = request.getParameter("startDate"); // 2021-11-20
			String lastDate = request.getParameter("lastDate");
			
			if(startDate != null && lastDate != null && !"".equals(startDate) && !"".equals(lastDate)) {
				
				String[] startDateArr = startDate.split("-");
				
				startDate = "";
				
				for(String piece : startDateArr) {
					startDate += piece; // 20211120
				}
				
				String[] lastDateArr = lastDate.split("-");
				
				lastDate = "";
				
				for(String piece : lastDateArr) {
					lastDate += piece;
				}
				
				try {
					Integer.parseInt(startDate);
					Integer.parseInt(lastDate);
					
					if(startDate.length() == 8 && lastDate.length() == 8) {
						paraMap.put("startDate", startDate);
						paraMap.put("lastDate", lastDate);
					}					
										
				} catch (NumberFormatException e) {
					
				}
				
			}
			
			// 검색기능
			String searchType = request.getParameter("searchType");
			String searchWord = request.getParameter("searchWord");
			String str_currentShowPageNo = request.getParameter("currentShowPageNo");
						
			if(searchType == null || (!"customer_id".equals(searchType) && !"customer_comp".equals(searchType) && !"customer_name".equals(searchType))) {			
				searchType = "";
			}
			
			if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty()) {			
				searchWord = "";			
			}
			
			paraMap.put("searchType", searchType);
			paraMap.put("searchWord", searchWord);
			
			// 페이징처리
			int totalCount = 0; 		// 총 작성문서 수	
			int sizePerPage = 10; 		// 한 페이지당 보여줄 작성문서 건수
			int currentShowPageNo = 0; 	// 현재 보여주는 페이지 번호로, 초기치는 1페이지로 설정함	
			int totalPage = 0; 			// 총 페이지 수(웹브러우저상에서 보여줄 총 페이지 갯수, 페이지바)(totalCount/sizePerPage 올림)
			
			int startRno = 0; 			// 시작 행번호
			int endRno = 0; 			// 끝  행번호
			
			totalCount = service.getTotalDocument(paraMap);
			
			totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
			
			if(str_currentShowPageNo == null) // 작성완료문서 초기화면
				currentShowPageNo = 1;
			
			
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
			
			// 시퀀스명
			if("tbl_billtax".equalsIgnoreCase(tabName))
				paraMap.put("seq", "billtax_seq");
			
			else if("tbl_billnotax".equalsIgnoreCase(tabName))
				paraMap.put("seq", "billnotax_seq");
			
			else
				paraMap.put("seq", "transaction_seq");
			
			List<Map<String, String>> docList = service.getDocumentList(paraMap);
			
			int blockSize = 10;
			
			int loop = 1;
			
			int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
			
			String pageBar = "";
			
			String url = request.getContextPath() + "/account/listBill.hello2?tabName=" + tabName + "&startDate=" + startDate + "&lastDate=" + lastDate + "&searchType=" + searchType + "&searchWord=" + searchWord;
			
			if(pageNo != 1) {
				pageBar += "<li class='page-item'>"
			 			+ "<a class='page-link' href='" + url + "&currentShowPageNo=1'>[맨처음]</a>"
			 			+ "</li>";
		
				pageBar += "<li class='page-item'>"
			 			+ "<a class='page-link' href='" + url + "&currentShowPageNo=" + (pageNo - 1) + "'>[이전]</a>"
			 			+ "</li>";				
			}
							
			while(!(loop > blockSize || pageNo > totalPage)) {
				
				if(pageNo == currentShowPageNo) 
					pageBar += "<li class='page-item active'>"
					     	+ "<a class='page-link' href='#'>" + pageNo + "</a>"
					     	+ "</li>";
				else
					pageBar += "<li class='page-item'>"
			 				+ "<a class='page-link' href='" + url + "&currentShowPageNo=" + pageNo + "'>" + pageNo + "</a>"
			 				+ "</li>";
				
				loop++;
				pageNo++;
				
			}// end of while-------------------------
			
			// +++ [다음][마지막] 만들기 +++
			
			if(pageNo <= totalPage) {
				pageBar += "<li class='page-item'>"
				 		+ "<a class='page-link' href='" + url + "&currentShowPageNo=" + (pageNo) + "'>[다음]</a>"
				 		+ "</li>";
			
				pageBar += "<li class='page-item'>"
			 			+ "<a class='page-link' href='" + url + "&currentShowPageNo=" + totalPage + "'>[마지막]</a>"
			 			+ "</li>";			
			}			

			mav.addObject("paraMap", paraMap);
			
			mav.addObject("pageBar", pageBar);
			
			mav.addObject("docList", docList);
			
			mav.setViewName("account/listBill.tiles1");	
			
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/updateStatus.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_updateStatus(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{						
			
			String[] seqArr = request.getParameter("seq").split(",");
			
			String status = request.getParameter("state");
			String tabName = request.getParameter("tabName2");
			String colName = "";
			
			if("tbl_billtax".equalsIgnoreCase(tabName))
				colName = "billtax_seq";
			
			else if("tbl_billnotax".equalsIgnoreCase(tabName))
				colName = "billnotax_seq";
			
			else
				colName = "transaction_seq";
			
			Map<String, String> paraMap = new HashMap<> ();
			
			paraMap.put("status", status);
			paraMap.put("tabName", tabName);
			paraMap.put("colName", colName);
			
			int n = 0;
			
			for(String seq : seqArr) {
				paraMap.put("seq", seq);
				n = service.updateStatus(paraMap);				
			}
						
			if(n != 0) {
				
				// 고객들에게 승인요청 메일 발송하기
				if("1".equals(status)) {
					
					for(String seq : seqArr) {
						
						paraMap.put("seq", seq);
						
						CustomerVO_KJH cvo = service.getEmail(paraMap);
						
						if(cvo != null && !"empty".equalsIgnoreCase(cvo.getCustomer_email())) {
							
							String reciever = (!"".equals(cvo.getCustomer_comp()))?cvo.getCustomer_comp():cvo.getCustomer_name();
							
							String emailContents = "[" + reciever + "]님께 문서 승인 요청 드립니다.\n첨부파일을 확인하신 후 10일 이전까지 승인 부탁드립니다.\n\n\nhelloworks와 함께해주셔서 감사합니다.";
														
							String[] seqArr2 = {seq};
							
							SXSSFWorkbook workbook = null;
							
							if("tbl_billtax".equalsIgnoreCase(tabName))
								workbook = excel.excelOfBilltax(seqArr2);
							
							else if("tbl_billnotax".equalsIgnoreCase(tabName))
								workbook = excel.excelOfBillnotax(seqArr2);
							
							else
								workbook = excel.excelOfTransaction(seqArr2);
														
							FileManager fm = new FileManager();
							
							Map<String, Object> excelMap = new HashMap<> ();
							
							excelMap.put("locale", Locale.KOREA);
							excelMap.put("workbookName", reciever + "_승인요청문서_helloworks");
							excelMap.put("header", request.getHeader("User-Agent"));
							
							HttpSession session = request.getSession();
							String root = session.getServletContext().getRealPath("/"); 
							String path = root + "resources" + File.separator +"excelStorage";
							
							excelMap.put("path", path);
							excelMap.put("workbook", workbook);
							
							try {
								File file = fm.excelUpload(excelMap);								
								gmail.sendmail_customer_withAttach(cvo.getCustomer_email(), reciever, emailContents, file);
							} catch (Exception e) {}
				        								
						}
						
					}// end of for------------------------------------
					
				}
				
				String message = "정상적으로 처리되었습니다.";
				String loc = "";				

				if(request.getParameter("loc") != null && !"".equals(request.getParameter("loc")))
					loc = request.getParameter("loc");
				
				else
					loc = request.getContextPath() + "/account/listBill.hello2?tabName=" + tabName;
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
			
			else {
				String message = "오류발생으로 인해 처리에 실패하였습니다.";
				String loc = "";				

				if(request.getParameter("loc") != null && !"".equals(request.getParameter("loc")))
					loc = request.getParameter("loc");
				
				else
					loc = request.getContextPath() + "/account/listBill.hello2?tabName=" + tabName;
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}	
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/deleteDoc.hello2", method= {RequestMethod.POST})
	public ModelAndView requiredLogin_deleteDoc(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{
			
			String[] seqArr = request.getParameter("seqes").split(",");			
			String tabName = request.getParameter("tabName3");
			String colName = "";
			
			if("tbl_billtax".equalsIgnoreCase(tabName))
				colName = "billtax_seq";
			
			else if("tbl_billnotax".equalsIgnoreCase(tabName))
				colName = "billnotax_seq";
			
			else
				colName = "transaction_seq";
			
			Map<String, String> paraMap = new HashMap<> ();
			
			paraMap.put("tabName", tabName);
			paraMap.put("colName", colName);
						
			int n = 0;
						
			for(String seq : seqArr) {
				
				paraMap.put("seq", seq);
				
				n = service.deleteDoc(paraMap);
								
			}
						
			if(n != 0) {
				
				String message = "정상적으로 처리되었습니다.";
				String loc = request.getContextPath() + "/account/listBill.hello2?tabName=" + tabName;
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");
				
			}
			
			else {
				String message = "문서 삭제에 실패하였습니다.";
				String loc = request.getContextPath() + "/account/listBill.hello2?tabName=" + tabName;
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/docExcelDownload.hello2", method= {RequestMethod.POST})
	public String requiredLogin_docExcelDownload(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{						
			
			String seqes = request.getParameter("seq");
			String[] seqArr = seqes.split(",");
			
			String tabName = request.getParameter("tabName2");
			String colName = "";
			
			if("tbl_billtax".equalsIgnoreCase(tabName))
				colName = "billtax_seq";
			
			else if("tbl_billnotax".equalsIgnoreCase(tabName))
				colName = "billnotax_seq";
			
			else
				colName = "transaction_seq";
			
			String fk_colName = "fk_" + colName;
			
			Map<String, String> paraMap = new HashMap<> ();
			
			paraMap.put("tabName", tabName);
			paraMap.put("colName", colName);
			paraMap.put("fk_colName", fk_colName);
			
			SXSSFWorkbook workbook = null;
			
			if("tbl_billtax".equalsIgnoreCase(tabName))				
				workbook = excel.excelOfBilltax(seqArr);				
							
			else if("tbl_billnotax".equalsIgnoreCase(tabName))
				workbook = excel.excelOfBillnotax(seqArr);
			
			else
				workbook = excel.excelOfTransaction(seqArr);
			
			model.addAttribute("locale", Locale.KOREA);
			model.addAttribute("workbook", workbook);
	        model.addAttribute("workbookName", "helloWorksDocument");
			
	        return "excelDownloadView";
	        
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			return "msg_KJH";	
		}
				
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/viewBill.hello2")
	public ModelAndView requiredLogin_viewBill(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{
			
			Map<String, String> paraMap = new HashMap<> ();
			
			String tabName = request.getParameter("tabName");
			
			if(!"tbl_billtax".equalsIgnoreCase(tabName) 
					&& !"tbl_billnotax".equalsIgnoreCase(tabName) 
					&& !"tbl_transaction".equalsIgnoreCase(tabName)) {
				
				tabName = "tbl_billtax";
				
			}
			
			try {
				
				Integer.parseInt(request.getParameter("seq"));
				
				paraMap.put("seq", request.getParameter("seq"));
				
				mav.addObject("seq", request.getParameter("seq"));
				
				if("tbl_billtax".equalsIgnoreCase(tabName)) {
					
					BilltaxVO_KJH doc = service.getBilltaxDoc(paraMap);
					
					if(doc != null && !"".equals(doc.getBilltax_seq())) {
						List<BilltaxDetailVO_KJH> detailList = service.getDetailBilltaxList(paraMap);
						
						mav.addObject("doc", doc);
						mav.addObject("detailList", detailList);
						mav.addObject("size", detailList.size());
						mav.addObject("total", doc.getTotalprice() + doc.getTaxprice());
						
						mav.setViewName("account/viewBilltax.tiles1");	
					}
					
				}
				
				else if("tbl_billnotax".equalsIgnoreCase(tabName)) {
					BillnotaxVO_KJH doc = service.getBillnotaxDoc(paraMap);
					
					if(doc != null && !"".equals(doc.getBillnotax_seq())) {
						List<BillnotaxDetailVO_KJH> detailList = service.getDetailBillnotaxList(paraMap);
						
						mav.addObject("doc", doc);
						mav.addObject("detailList", detailList);
						mav.addObject("size", detailList.size());
												
						mav.setViewName("account/viewBillNotax.tiles1");
					}
					
				}
				
				else {
					TransactionVO_KJH doc = service.getTransactionDoc(paraMap);
					
					if(doc != null && !"".equals(doc.getTransaction_seq())) {
						List<TransactionDetailVO_KJH> detailList = service.getDetailTransactionList(paraMap);
						
						mav.addObject("doc", doc);
						mav.addObject("detailList", detailList);
						mav.addObject("size", detailList.size());
						
						mav.setViewName("account/viewTransaction.tiles1");
					}
					
				}
				
				if(mav.getViewName() == null || "".equals(mav.getViewName())) {
					
					String message = "존재하지 않는 문서입니다.";
					String loc = "javascript:history.back()";
					
					mav.addObject("message", message);
					mav.addObject("loc", loc);
					
					mav.setViewName("msg_KJH");	
					
				}
								
			} catch (NumberFormatException e) {
				String message = "존재하지 않는 문서입니다.";
				String loc = "javascript:history.back()";
				
				mav.addObject("message", message);
				mav.addObject("loc", loc);
				
				mav.setViewName("msg_KJH");	
			}
			
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/editBilltax.hello2")
	public ModelAndView requiredLogin_editBilltax_requiredComp(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{
			
			mav.setViewName("account/editBilltax.tiles1");
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/account/editBillnotax.hello2")
	public ModelAndView requiredLogin_editBillnotax_requiredComp(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{
			mav.addObject("seq", request.getParameter("seq"));
			mav.addObject("original_regdate", request.getParameter("regdate"));
			mav.setViewName("account/editBillnotax.tiles1");
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg_KJH");	
		}
		
		return mav;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@ResponseBody
	@RequestMapping(value="/account/totalSalesOfMonth.hello2", produces="text/plain;charset=UTF-8")
	public String requiredLogin_totalSalesOfMonth(HttpServletRequest request, HttpServletResponse response) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{			
			
			List<Map<String, String>> totalSalesOfMonthList = service.totalSalesOfMonth();
			
			Gson gson = new Gson();
			
			JsonArray gsonArr = new JsonArray();
			
			for(Map<String, String> map : totalSalesOfMonthList) {
				
				JsonObject gsonObj = new JsonObject();
				
				gsonObj.addProperty("sales", map.get("sales"));
				gsonObj.addProperty("month", map.get("month"));
				
				gsonArr.add(gsonObj);
				
			}
			
			return gson.toJson(gsonArr);
						
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			return "msg_KJH";			
		}
				
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@ResponseBody
	@RequestMapping(value="/account/totalSalesOfYear.hello2", produces="text/plain;charset=UTF-8")
	public String requiredLogin_totalSalesOfYear(HttpServletRequest request, HttpServletResponse response) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{			
			
			List<Map<String, String>> totalSalesOfYearList = service.totalSalesOfYear();
			
			Gson gson = new Gson();
			
			JsonArray gsonArr = new JsonArray();
			
			for(Map<String, String> map : totalSalesOfYearList) {
				
				JsonObject gsonObj = new JsonObject();
				
				gsonObj.addProperty("sales", map.get("sales"));
				gsonObj.addProperty("year", map.get("year"));
				
				gsonArr.add(gsonObj);
				
			}
			
			return gson.toJson(gsonArr);
						
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			return "msg_KJH";			
		}
				
	}
		
	///////////////////////////////////////////////////////////////////////////////////////
	
	@ResponseBody
	@RequestMapping(value="/account/wordcloudOfCustomer.hello2", produces="text/plain;charset=UTF-8")
	public String requiredLogin_wordcloudOfCustomer(HttpServletRequest request, HttpServletResponse response) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{			
			
			List<String> wordcloudOfCustomerList = service.wordcloudOfCustomer();
			
			StringBuilder sb = new StringBuilder();
			
			for(int i=0; i<wordcloudOfCustomerList.size(); i++) {
				
				if(i == 0)
					sb.append(wordcloudOfCustomerList.get(i));
				
				else
					sb.append("," + wordcloudOfCustomerList.get(i));
				
			}
			
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("customer", sb.toString());
			
			return jsonObj.toString();
						
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			return "msg_KJH";			
		}
				
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	@ResponseBody
	@RequestMapping(value="/account/monthOfCustomerCnt.hello2", produces="text/plain;charset=UTF-8", method= {RequestMethod.POST})
	public String requiredLogin_monthOfCustomerCnt(HttpServletRequest request, HttpServletResponse response) {
		
		boolean checkDepartment = checkDepartment(request, "20");
		
		if(checkDepartment)	{			
			
			String month = request.getParameter("month");
			
			List<Map<String, String>> mapList = service.monthOfCustomerCnt(month);
			
			Gson gson = new Gson();
			
			JsonArray gsonArr = new JsonArray();
			
			for(Map<String, String> map : mapList) {
				
				JsonObject gsonObj = new JsonObject();
				
				gsonObj.addProperty("customer", map.get("customer"));
				gsonObj.addProperty("cnt", map.get("cnt"));
				
				gsonArr.add(gsonObj);
				
			}
			
			return gson.toJson(gsonArr);
						
		}
					
		else {
			String message = "해당 부서 소속의 직원만 접근 가능합니다.";
			String loc = request.getContextPath() + "/index.hello2";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			return "msg_KJH";			
		}
				
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
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
	
}
