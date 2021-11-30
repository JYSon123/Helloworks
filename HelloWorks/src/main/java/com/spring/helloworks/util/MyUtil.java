package com.spring.helloworks.util;

import javax.servlet.http.HttpServletRequest;

public class MyUtil {

	// *** ? 다음의 데이터까지 포함한 현재 URL 주소를 알려주는 메소드를 생성 *** //
	public static String getCurrentURL(HttpServletRequest request) {
		
		String currentURL = request.getRequestURL().toString();
		// System.out.println("~~~ 확인용 currentURL => " + currentURL);
		// ~~ 확인용 currentURL => http://localhost:9090/MyMVC/member/memberList.up
		// ? 중심으로 왼쪽만 나온다.(즉, 주소만 나옴) 참고로 오른쪽은 DATA이다.
		
		String queryString = request.getQueryString();
		// System.out.println("~~~ 확인용 queryString => " + queryString);
		// ~~~ 확인용 queryString => currentShowPageNo=5&sizePerPage=5&searchType=name&searchWord=%EC%9C%A0 (GET방식일 경우)
		// ? 중심으로 오른쪽만 나온다(즉, data만 나옴) ==> GET 방식일 경우
		// POST 방식일 경우는  ~~~ 확인용 queryString => null
		
		if(queryString != null) { // GET 방식일 경우
			currentURL += "?" + queryString;
		}
		
		
		// System.out.println("~~~ 확인용 currentURL => " + currentURL);
		// ~~~ 확인용 currentURL => http://localhost:9090/MyMVC/member/memberList.up?currentShowPageNo=5&sizePerPage=5&searchType=name&searchWord=%EC%9C%A0
		
		String ctxPath = request.getContextPath();
		//    /MyMVC
		
		int beginIndex = currentURL.indexOf(ctxPath) + ctxPath.length();
		// 	   27      =			21				 +		 6		
		
		currentURL = currentURL.substring(beginIndex);
		// /member/memberList.up?currentShowPageNo=5&sizePerPage=5&searchType=name&searchWord=%EC%9C%A0
		
		return currentURL;
	}

	
	// **** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 **** // 
    public static String secureCode(String str) {
       
       str = str.replaceAll("<", "&lt;");
       str = str.replaceAll(">", "&gt;");
       
       return str;
    }
	
	
	
	
}
