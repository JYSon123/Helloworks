<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>

   
<style type="text/css">
	body,h1,h2,h3,h4,h5,h6 {font-family: Verdana, sans-serif;}
	
	form{
		font-family: Verdana, sans-serif;
	
	}
	
	th{
		color:#595959;
		width: 18%;
		font-weight: normal;
	}
	
	table,tr,td{
		border:none;
	}
	
</style>

<script type="text/javascript">

	$(document).ready(function(){

		$("span.error").hide();
		
		// 글암호 유효성 검사
		 $("button#pwCheck").click(function(){
			 
			// b_flagpwCheckClick = true; // 암호확인을 클릭했는지 안 했는지 알아보는 용도
			
			var regExp = /^\d{4}$/i;			
			
			var pwVal = $("input#pw").val().trim();	
			
			var bool = regExp.test(pwVal);
			
			if(!bool){
				$("span.error").show();
			}
			else{
				$("span.error").hide();
			}		
		 }); 
		
		
		// 글삭제 완료 버튼
		$("button#btnDel").click(function(){
			
			// 글암호 유효성 검사
			var pwVal = $("input#pw").val().trim();
			if(pwVal == ""){
				alert("글암호를 입력하세요!!");
				return;
			}
			
			// 폼(form)을 전송(submit)
			var frm = document.delFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath%>/boardDelEnd.hello2";
			frm.submit();	
		});		
		
	}); // end of $(document).ready(function(){})---------------

</script>

<!-- 좌측 고정 상세메뉴 시작 -->
<nav class="w3-sidebar w3-collapse w3-white " style="margin-top:20px; z-index:0; width:300px;background-color:#f5f5f5; overflow: hidden" id="mySidebar"><br>
  <div class="w3-container" style="background-color:#f5f5f5; margin-top:10px" >
    <a href="#" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu">
      <i class="fa fa-remove"></i>
    </a>
	<br><br>
	
	<button type="button" onclick="location.href='<%= ctxPath %>/boardAdd.hello2'" class="btn" id="btn1" style="background-color:#0070C0; margin-left:14px; font-size:21px; width: 240px; height:63px; color:white">
				<b>글쓰기</b></button>
	<br>
  </div>
  <div class="w3-bar-block" style="background-color:#f5f5f5; height: 100%">
	<div style="margin-left:42px; font-size: 16pt; color:#595959">
	  <br>
	  <a href="<%= ctxPath %>/list.hello2" class="w3-bar-item w3-button">사내공지</a>
  	</div>
  </div>  
</nav>
<!-- 좌측 고정 상세메뉴 끝 -->


<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin-top:30px; font-size: 15pt">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 800px;">
    <div style="display: flex;">
	<div style="margin:100px 0 0 300px; color:#595959; padding-left: 3%;">
	
	<form name="delFrm" enctype="multipart/form-data">
		<h2><i class="far fa-trash-alt"></i>&nbsp;<b>글삭제 요청</b></h2>
		<span style="font-size:17px">작성시 등록한 암호를 입력해주세요.</span>

		
			<button type="button" class="btn btn-lg mr-3 " id="btnDel" style="background-color: white; color:#0070C0; font-size: 18pt; margin-left:210px">삭제</button>
			<button type="button" class="btn btn-lg" onclick="javascript:history.back()" style="background-color: white; color:#0070C0; font-size: 18pt">취소</button> 
	
	

		<table style="width: 900px; margin-top:30px" class="table table-borderd">
			<tr>
			
				<td><br>글암호</td>
				<td>
				<br>
					<input type="hidden" name="seq" value="${requestScope.seq}"></input>
					<input type="password" name="pw" id="pw" /> 
					<button type="button" class="btn btn-light" id="pwCheck" style=" font-size:16px; width: 90px; height:38px">
					암호확인</button>
					<span class="error" style="color:#0070C0; font-size:17px">암호는 숫자 네자리로 입력해주세요.</span>
				</td>
				<td></td>
			</tr>
		</table>
		
		
	</form>

</div>	
</div>
</div>
  

<!-- !!!!!PAGE CONTENT END!!!!!! -->
</div>

