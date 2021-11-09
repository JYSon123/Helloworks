<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
		
		// 저장 버튼
		$("button#btnWrite").click(function(){
			
			// 글제목 유효성 검사
			var subjectVal = $("input#subject").val().trim();
			if(subjectVal == ""){
				alert("글제목을 입력하세요!");
				return;
			}
			
			// 글내용 유효성 검사
			var contentVal  = $("textarea#content").val().trim();
			if(contentVal == ""){
				alert("글내용을 입력하세요!");
				return;
			}
			
			// 글내용 유효성 검사
			var pwVal  = $("input#pw").val().trim();
			if(pwVal == ""){
				alert("글암호를 입력하세요!");
				return;
			}
			
			
			/* if(!b_flagpwCheckClik){ // 암호확인을 클릭하지 않았다면
				alert("암호확인을 클릭하세요!");
				return; // 종료
			} */
			
			// 폼(form)을 전송(submit);
		    var frm = document.addFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath%>/addEnd.hello2";
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
	
	<button type="button" class="btn" id="btn1" style="background-color:#0070C0; margin-left:14px; font-size:21px; width: 240px; height:63px; color:white">
				<b>글쓰기</b></button>
	<br>
  </div>
  <div class="w3-bar-block" style="background-color:#f5f5f5; height: 100%">
	<div style="margin-left:42px; font-size: 16pt; color:#595959">
	  <br>
	  <a href="#메일" class="w3-bar-item w3-button">자유게시판</a>
  	</div>
  </div>  
</nav>
<!-- 좌측 고정 상세메뉴 끝 -->


<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin-top:30px; font-size: 15pt">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 800px;">
    <div style="display: flex;">
	<div style="margin:70px 0 0 280px; padding-left: 3%;">

		<h4 style="margin-bottom: 30px; color:#0070C0"><b>자유게시판 글쓰기</b></h4>

	<form name="addFrm" enctype="multipart/form-data">
	
		<div style="margin: 20px 0 ">
			<button type="button" class="btn btn-lg mr-3 " id="btnWrite" style="background-color: white; color:#0070C0; font-size: 18pt">저장</button>
			<button type="button" class="btn btn-lg" onclick="javascript:history.back()" style="background-color: white; color:#0070C0; font-size: 18pt">취소</button> 
		</div>
		
		<table style="width: 1300px; margin-left:6px" class="table">
			<tr>
				<th>제목</th>
				<td colspan="3">
						<input type="text" name="subject" id="subject" size="90" /> 	
				</td>		
			</tr>
			<tr>
				<th>성명</th>
				<td style="width: 40%">
					<input type="hidden" name="fk_empno" value="${sessionScope.loginuser.empno}" />
					<input type="text" name="name" value="${sessionScope.loginuser.name}" readonly /> 
				</td>
				
				<th>파일첨부</th>
				<td>
					<input type="file" name="attach" /> 
				</td>
			</tr>
			
			<tr>
				<th style="vertical-align: middle; color:#595959">내용</th>
				<td colspan="3"> 
					<textarea style="width: 100%; height: 612px;" name="content" id="content" ></textarea> 
				</td>
			</tr>
			
			
			<tr>
				<th>글암호</th>
				<td colspan="3">
					<input type="password" name="pw" id="pw" /> 
					<button type="button" class="btn btn-light" id="pwCheck" style=" font-size:16px; width: 90px; height:38px">
					암호확인</button>
					<span class="error" style="color:#0070C0; font-size:17px">암호는 숫자 네자리로 입력해주세요.</span>
					
				</td>
			</tr>
		</table>
	</form>

</div>	
</div>
</div>
  

<!-- !!!!!PAGE CONTENT END!!!!!! -->
</div>

