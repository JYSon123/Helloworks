<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%> 

<style type="text/css">
h1 {
		font-family: 'Lato', sans-serif;
		position: relative;
		display: block;
		margin: 0 0 0 10px;
		font-size: 55pt;
		line-height: 1;
		transform: translateY(6rem);
		animation: up 500ms linear forwards;
		z-index: 1;
		text-shadow: 0px 1px 1px white;
		color: white;
		
		&::before,
		&::after {
			position: absolute;
			content: '';
			width: 0px;
			height: 1px;
			left: 0;
			background-color: white;
			z-index: -1;
		}
		
	&::before {
		top: 1.4rem;
		animation: draw 500ms linear 1s forwards;
	}
	
	&::after {
		bottom: 0.4rem;
		animation: draw 500ms linear 1s forwards;
	}
	
	}

</style>

<!-- 메인 이미지 화면 시작-->
<div id="body">
	<header class="w3-display-container w3-content w3-wide" style="max-width:1600px; margin-top:90px" id="home">
		<div id="main">
		      <img src="<%= ctxPath %>/resources/images/finalmain.jpg" style="width: 100%; height:100%" alt="###"> 
		      <div class=" d-none d-md-block w3-display-right" id="title-main" style="text-align:center; margin-right:460px"> 
			    <h1>helloworks is</h1><h1 class="mt-4">here to help you.</h1>
				<button type="button" class="btn btn-outline-light" id="btn1" style="margin-top:30px; margin-right:40px; border-radius: 60px; font-size:19px; width: 170px; height:50px; font-family: 맑은 고딕">
				Menu</button>
			 </div> 
		</div>		      
	</header>
</div>
<!-- 메인 이미지 화면 끝-->


<!-- 전체 메뉴 시작-->
<div class="w3-content w3-padding" id="callMenu" style="max-width:1600px; margin-bottom: 60px; font-family: Verdana, sans-serif;">
 <div class="w3-content w3-padding"  style="max-width:1600px; margin-top:80px">
    <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16" style="color:#0070C0"><b>전체메뉴</b></h3>

	<!-- 메뉴 아이콘 컨테이너 -->
	<div class="w3-container w3-padding-64 w3-center" id="team">	
	
	<div class="w3-row"><br>
		<div class="w3-quarter">
			<button type="button" class="btn btn-outline-dark" style="border-radius: 70px; width: 100px; height: 100px" >
			  <i class="far fa-envelope" style="font-size: 34pt"></i></button>
			  <p class="p">메일</p>
		</div>
		
		<div class="w3-quarter">
		 <button type="button" onclick="location.href='<%= ctxPath %>/list.hello2'" class="btn btn-outline-dark" style="border-radius: 70px; width: 100px; height: 100px" >
			  <i class="far fa-clipboard" style="font-size: 34pt"></i></button>
			  <p class="p">게시판</p>
		</div>
		
		<div class="w3-quarter">
		  <button type="button" onclick="location.href='<%= ctxPath %>/chat/multichat.hello2'" class="btn btn-outline-dark" style="border-radius: 70px; width: 100px; height: 100px" >
			  <i class="far fa-comment" style="font-size: 34pt"></i></button>
			  <p class="p">채팅</p>
		</div>
		
		<div class="w3-quarter">
		 <button type="button" onclick="location.href='<%= ctxPath %>/account/home.hello2'" class="btn btn-outline-dark" style="border-radius: 70px; width: 100px; height: 100px" >
			  <i class="fas fa-calculator" style="font-size: 34pt"></i></button>
			  <p class="p">전자세금계산서</p>
		</div>
		<br>	
	</div>
	
	<div class="w3-row"><br>
		<div class="w3-quarter">
		<button type="button" onclick="location.href='<%= ctxPath %>/schedule.hello2'" class="btn btn-outline-dark" style="border-radius: 70px; width: 100px; height: 100px" >
			  <i class="far fa-check-square" style="font-size: 34pt"></i></button>
			  <p class="p">일정</p>
		</div>
		
		<div class="w3-quarter">
		<button type="button" class="btn btn-outline-dark" style="border-radius: 70px; width: 100px; height: 100px" >
			  <i class="far fa-address-book" style="font-size: 34pt"></i></button>
			  <p class="p">주소록</p>
		</div>
		
		<div class="w3-quarter">
		<button type="button" class="btn btn-outline-dark" style="border-radius: 70px; width: 100px; height: 100px" >
			  <i class="fas fa-chart-pie" style="font-size: 34pt"></i></button>
			  <p class="p">예약</p>
		</div>
		
		<div class="w3-quarter">
		<button type="button" onclick="location.href='<%= ctxPath %>/emp/viewAttend.hello2'" class="btn btn-outline-dark" style="border-radius: 70px; width: 100px; height: 100px" >
			  <i class="fas fa-users" style="font-size: 34pt"></i></button>
			  <p class="p">인사</p>
		</div>
		<br>
	</div>
	
	<div class="w3-row"><br>
		<div class="w3-quarter">
		<button type="button" onclick="location.href='<%= ctxPath %>/documentMain.hello2'" class="btn btn-outline-dark" style="border-radius: 70px; width: 100px; height: 100px" >
			  <i class="fas fa-clipboard-check" style="font-size: 34pt"></i></button>
			  <p class="p">전자결재</p>
		</div>
		
		<div class="w3-quarter">
		<button type="button" class="btn btn-outline-dark" style="border-radius: 70px; width: 100px; height: 100px" >
			  <i class="fas fa-cog" style="font-size: 34pt"></i></button>
			  <p class="p">오피스 관리</p>
		</div>
		
		<div class="w3-quarter">
		<button type="button" class="btn btn-outline-dark" style="border-radius: 70px; width: 100px; height: 100px" >
			  <i class="fas fa-sticky-note" style="font-size: 34pt"></i></button>
			  <p class="p">회계지원</p>
		</div>
		
		<div class="w3-quarter"> <!-- 칸을 맞추기 위한 것(메뉴 추가 가능) -->
		  
		</div>
		<br>
	</div>
	
	
	</div>	 
 </div> 
</div>
<!-- 전체메뉴 끝 -->	
