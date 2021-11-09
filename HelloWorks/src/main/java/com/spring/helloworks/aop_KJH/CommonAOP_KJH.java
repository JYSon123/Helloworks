package com.spring.helloworks.aop_KJH;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.spring.helloworks.common_KJH.MyUtil;

@Aspect
@Component
public class CommonAOP_KJH {
	
	@Pointcut("execution(public * com.spring..*Controller*.requiredLogin_*(..))")	
	public void requiredLogin() {}
	
	@Before("requiredLogin()") // @After : 주업무(포인트컷) 이후, @Around : 주업무(포인트컷) 전후
	public void loginCheck(JoinPoint joinPoint) {
		
		HttpServletRequest request = (HttpServletRequest)joinPoint.getArgs()[0];
		
		HttpServletResponse response = (HttpServletResponse)joinPoint.getArgs()[1];
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginEmp") == null || (boolean)session.getAttribute("otpcheck") == false) {
			
			String message = "로그인 후 이용 가능합니다.";
			String loc = request.getContextPath() + "/login.hello2";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			// >>> 로그인 성공 후 로그인 하기전 페이지로 돌아가는 작업 만들기 <<< //
			// === 현재 페이지의 주소(URL)을 알아오기 ===
			String url = MyUtil.getCurrentURL(request);
			
			// 세션에 URL정보를 저장
			session.setAttribute("goBackURL", url);
			//////////////////////////////////////////////////
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg_KJH.jsp");
			
			try {
				
				dispatcher.forward(request, response);
				
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
			
		}
		
		// 로그인 되어 있다면 
		else {
			
			String validtime = (String)session.getAttribute("validtime");
			// 20211108092715
			
			String currenttime = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
			// 20211108092715
			
			if(Long.parseLong(currenttime)-Long.parseLong(validtime) >= 3000) {
				
				session.removeAttribute("loginEmp");
				session.removeAttribute("otpcheck");
				session.removeAttribute("validtime");
				
				String message = "30분 이상 페이지 이동이 없어 세션이 만료되었습니다. 다시 로그인 후 이용해주세요.";
				String loc = request.getContextPath() + "/login.hello2";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				// >>> 로그인 성공 후 로그인 하기전 페이지로 돌아가는 작업 만들기 <<< //
				// === 현재 페이지의 주소(URL)을 알아오기 ===
				String url = MyUtil.getCurrentURL(request);
				
				// 세션에 URL정보를 저장
				session.setAttribute("goBackURL", url);
				//////////////////////////////////////////////////
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg_KJH.jsp");
				
				try {
					
					dispatcher.forward(request, response);
					
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
				
			}
			
			else {				
				session.setAttribute("validtime", currenttime);				
			}
			
		}
		
	}
	
}
