package com.spring.helloworks.common;

import javax.servlet.http.HttpServletRequest;

public class MyUtil_HJE {

	// *** ? 다음의 데이터까지 포함한 현재 URL 주소를 알려주는 메소드를 생성 *** //
	public static String getCurrentURL(HttpServletRequest request) {

		String currentURL = request.getRequestURL().toString();
		// System.out.println("~~~ 확인용 currentURL => " + currentURL);
		// ~~~ 확인용 currentURL => http://localhost:9090/MyMVC/member/memberList.up
		
		String queryString = request.getQueryString();
		// System.out.println("~~~ 확인용 queryString => " + queryString);
		// ~~~ 확인용 currentURL => currentShowPageNo=5&sizePerPage=5&searchType=name&searchWord=%EC%9C%A0
		// ~~~ 확인용 currentURL => null (POST방식일 경우)
		
		if(queryString != null) {
			currentURL += "?" + queryString ;
		}
		
		// System.out.println("~~~ 확인용 currentURL => " + currentURL);
		// ~~~ 확인용 currentURL => http://localhost:9090/MyMVC/member/memberList.up?currentShowPageNo=5&sizePerPage=5&searchType=name&searchWord=%EC%9C%A0

		String ctxPath = request.getContextPath();
		//     /MYMVC
		
		int beginIndex = currentURL.indexOf(ctxPath)+ctxPath.length();
		// 	27 		   =  21(/MYMVC가 시작하는 인덱스 번호) + 		6
		
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
