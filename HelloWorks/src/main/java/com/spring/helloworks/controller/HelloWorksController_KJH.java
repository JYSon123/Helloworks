package com.spring.helloworks.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.helloworks.common_KJH.*;
import com.spring.helloworks.model.EmpVO_KJH;
import com.spring.helloworks.service.InterHelloWorksService_KJH;

@Component
@Controller
public class HelloWorksController_KJH {
	
	@Autowired
	private InterHelloWorksService_KJH service;
	
	@Autowired
	private GoogleOTP gotp;
	
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
					String validtime = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
					// 20211108092715
					
					session.setAttribute("validtime", validtime);
					
					// otp인증완료여부 저장
					session.setAttribute("otpcheck", otpcheck);
					
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
	
}
