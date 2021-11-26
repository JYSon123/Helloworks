<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.net.InetAddress"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- ======= #27. tile1 중 header 페이지 만들기 (#26.번은 없다 샘이 장난침.) ======= --%>
<%
   String ctxPath = request.getContextPath();

   // localhost는 각각 자기 ip를 말하므로 모두 다 localhost를 갖고 있어 웹 채팅에서 사용할 수 없다.
   
   // === #172. (웹채팅관련3) === 
   // === 서버 IP 주소 알아오기(사용중인 IP주소가 유동IP 이라면 IP주소를 알아와야 한다.) ===
   InetAddress inet = InetAddress.getLocalHost();  
   String serverIP = inet.getHostAddress();
   
 // System.out.println("serverIP : " + serverIP);
 // serverIP : 211.238.142.72
   
   // String serverIP = "211.238.142.72"; 만약에 사용중인 IP주소가 고정IP 이라면 IP주소를 직접입력해주면 된다.
   
   // === 서버 포트번호 알아오기   ===
   int portnumber = request.getServerPort();
 // System.out.println("portnumber : " + portnumber);
 // portnumber : 9090
   
   String serverName = "http://"+serverIP+":"+portnumber; 
 // System.out.println("serverName : " + serverName);
 // serverName : http://211.238.142.72:9090 
%>

<!-- 좌측 사이드바 시작 -->
<nav class="w3-sidebar w3-bar-block w3-card w3-top w3-xlarge w3-animate-left w3-light" style="display:none; z-index:3; max-width:370px; overflow:hidden" id="mySidebar">
  <div class="container" style="background-color:#f5f5f5; height:90px">
	  <a href="javascript:void(0)" onclick="w3_close()"class="w3-button " style="border-radius: 70px; width:60px; height:60px; margin:20px 0 18px 5px"><i class="fas fa-angle-left" style="width:30px; color:gray; font-size:20pt; margin-top:8px"></i></a>
	  <a class="navbar-brand" href="<%= ctxPath %>/index.hello2" style="margin-left: 8px; margin-bottom: 10px"><img src="<%= ctxPath %>/resources/images/logo2.jpg" alt="HELLOWORKS_logo" width="165" height="54"/></a>
  </div>
  
  <div class="container" style="margin:30px 0 0 75px; font-size:20px; color:#595959; width:320px">
      <span style="color:#0070C0" class="ml-3"><b>전체메뉴</b></span><br><br>
	  <a href="#메일" onclick="#" class="w3-bar-item w3-button"><i class="far fa-envelope"></i>&emsp;메일</a>
	  <a href="<%= ctxPath %>/list.hello2" onclick="#" class="w3-bar-item w3-button"><i class="far fa-clipboard"></i>&emsp;게시판</a>
	  <a href="#채팅" onclick="#" class="w3-bar-item w3-button"><i class="far fa-comment"></i>&emsp;채팅</a>
	  <a href="#전자세금" onclick="#" class="w3-bar-item w3-button"><i class="fas fa-calculator"></i>&emsp;전자세금계산서</a>
	  <a href="#일정" onclick="#" class="w3-bar-item w3-button"><i class="far fa-check-square"></i>&emsp;일정</a>
	  <a href="#주소록" onclick="#" class="w3-bar-item w3-button"><i class="far fa-address-book"></i>&emsp;주소록</a>
	  <a href="#예약" onclick="#" class="w3-bar-item w3-button"><i class="fas fa-chart-pie"></i>&emsp;예약</a>
	  <a href="<%= ctxPath %>/emp/viewAttend.hello2" onclick="#" class="w3-bar-item w3-button"><i class="fas fa-users"></i>&emsp;인사</a>
	  <a href="<%= ctxPath %>/documentMain.hello2" onclick="#" class="w3-bar-item w3-button"><i class="fas fa-clipboard-check"></i>&emsp;전자결재</a>
	  <a href="#관리" onclick="#" class="w3-bar-item w3-button"><i class="fas fa-cog"></i>&emsp;오피스관리</a>
	  <a href="#회계지원" onclick="#" class="w3-bar-item w3-button"><i class="fas fa-sticky-note"></i>&emsp;회계지원</a>
 </div>
</nav>
<!-- 좌측 사이드바 끝-->


<!-- 상단 고정 네비게이션 시작 -->
<div class="w3-top" style="border-bottom: 1px solid #e6e6e6; z-index:2;">
  <div class="w3-bar w3-white w3-padding">
    <a class="navbar-brand w3-left mt-2" href="<%= ctxPath %>/index.hello2" >
    	<img src="<%= ctxPath %>/resources/images/logo.jpg" alt="HELLOWORKS_logo" width="153" height="43"/>
    </a>  
    <div class="w3-button w3-padding-16 w3-left mt-2" style="border-radius: 70px; width:60px; height:60px;"  onclick="w3_open()">
	  	<i class="fas fa-angle-right" style="color:gray; font-size:20pt;"></i>
	</div>

    <div class="w3-right w3-hide-small">
		  <button type="button" class="btn btn-light dropdown-toggle" id="myinfo"  data-toggle="dropdown" aria-haspopup="true"  aria-expanded="false" style="border-radius: 70px; width: 60px; height: 60px; margin-top: 11px" >
			  <i class="fa fa-user" style="font-size: 18pt; color:gray; background-color:#f5f5f5"></i>
		  </button>
		
		  <div class="dropdown-menu" aria-labelledby="myinfo" style=" margin-left:1100px; margin-top:8px;  width: 400px; height:200px">
		    <table style="margin:5px 0 0 5px"> 
		     	<tr>
		     		<td>
		     			<button type="button" class="btn btn-light"style="border-radius: 70px; width: 70px; height: 70px;" >
			  			<i class="fa fa-user" style="font-size: 18pt; color:gray; background-color:#f5f5f5"></i>
						</button>
					</td> 
					
					<!-- 로그인했을 경우 나타낼 것--> 
		     	  	<td style="font-size: 24pt; width:90px; margin-top:10px">
		     			&nbsp;<span><b>${sessionScope.loginEmp.empname}</b></span>
		     		</td>
		     		<td></td>
		     	</tr>
	     		<tr style="font-size: 13pt">			     			
	     			<td style="width:80px"></td> 	
	     			<td>${sessionScope.loginEmp.email}</td>    
	     			<td></td>
	     		 </tr>
	     		 <tr>
	     			<td></td><td></td>
	     		 	<td>
	     		 		<button type="button" class="btn btn-light" id="btnMy" style=" background-color:#f5f5f5; margin-top:19px; margin-left:10px; border-radius: 50px; font-size:16px; width: 110px; height:53px">
	           				<b>로그아웃</b>
	           			</button>
	           		</td>
	     		 </tr>
	        </table>     	
	  	</div>
	</div>
  </div>
</div>
<!-- 상단 고정 네비게이션 끝 --> 
  