package com.spring.helloworks.common_KJH;

import javax.servlet.http.HttpServletRequest;

public class MyUtil {

	// *** ? 다음의 데이터까지 포함한 현재 URL 주소를 알려주는 메소드를 생성 *** //
	public static String getCurrentURL(HttpServletRequest request) {
		
		String currentURL = request.getRequestURL().toString(); // StringBuffer --> String
	//	System.out.println("## 확인용 currentURL : " + currentURL);
		// ## 확인용 currentURL : http://localhost:9090/MyMVC/member/memberList.up
		// '?' 앞까지만 가져온다.
		// '?'앞은 주소, 뒤는 DATA이다.
		
		String queryString = request.getQueryString();
	//	System.out.println("@@ 확인용 queryString(GET) : " + queryString);
		// @@ 확인용 queryString(GET) : currentShowPageNo=5&sizePerPage=5&searchType=name&searchWord=%EC%9C%A0
		// '?' 뒤의 DATA를 가져온다.
		// 'GET'방식 일 경우에만 나온다.
		// 'POST'방식 일 경우에는 null이 나온다.
		// @@ 확인용 queryString(POST) : null
		
		if(queryString != null) // 'GET'방식 일 경우
			currentURL += "?" + queryString;
				
	//	System.out.println("&& 확인용 currentURL : " + currentURL);
		// && 확인용 currentURL : http://localhost:9090/MyMVC/member/memberList.up?currentShowPageNo=5&sizePerPage=5&searchType=name&searchWord=%EC%9C%A0
		
		String ctxPath = request.getContextPath(); // /MyMVC
		
		int beginIndex = currentURL.indexOf(ctxPath) + ctxPath.length(); // 27 = 21 + 6
		
		currentURL = currentURL.substring(beginIndex);
	//	System.out.println("** 확인용 최종 currentURL : " + currentURL);
		// ** 확인용 최종 currentURL : /member/memberList.up?currentShowPageNo=5&sizePerPage=5&searchType=name&searchWord=%EC%9C%A0
		
		return currentURL;
		
	}
	
	// **** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 **** // 
    public static String secureCode(String str) {
       
       str = str.replaceAll("<", "&lt;");
       str = str.replaceAll(">", "&gt;");
       
       return str;
    }
	
}
