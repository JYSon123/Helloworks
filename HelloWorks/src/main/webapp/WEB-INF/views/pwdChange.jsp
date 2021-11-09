<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
   String ctxPath = request.getContextPath(); // /board
%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[HelloWorks]MYPAGE</title>

<!-- Required meta tags -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> 

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/bootstrap-4.6.0-dist/css/bootstrap.min.css" > 

<!-- Font Awesome 5 Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<!-- 직접 만든 CSS 1 -->
<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/style1.css" />

<!-- Optional JavaScript -->
<script type="text/javascript" src="<%= ctxPath%>/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/bootstrap-4.6.0-dist/js/bootstrap.bundle.min.js" ></script> 
<script type="text/javascript" src="<%= ctxPath%>/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script> 

<%--  ===== 스피너를 사용하기 위해  jquery-ui 사용하기 ===== --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/jquery-ui-1.11.4.custom/jquery-ui.css" />
<script type="text/javascript" src="<%= ctxPath%>/resources/jquery-ui-1.11.4.custom/jquery-ui.js"></script>

<%-- *** ajax로 파일을 업로드(다운로드)할때 가장 널리 사용하는 방법 ==> ajaxForm *** --%>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/jquery.form.min.js"></script>

<style type="text/css">
	
	body {background-color: #3158D9;}
	
</style>

<script type="text/javascript">
	
	$(document).ready(function() {
		
		$("#btnSubmit").click(function() {			
			savePwd();			
		});
		
		$("input#newpw_check").bind("keyup", function(event) {
			if(event.keyCode == 13) { // 암호확인 입력란에서 엔터를 했을 경우 : [enter] ==> 13
				savePwd();
			}
		});
				
	})// end of $(document).ready(function() {})------------------------------------------
	
	// Function Declaration
	function savePwd() {
		
		var frm = document.pwdFrm;
		
		var currentpwVal = frm.currentpw.value;
		
		if(currentpwVal.trim() == "") {
			alert("현재 암호를 입력하세요.");
			frm.currentpw.value = "";
			return;
		}
		
		var newpwVal = frm.newpw.value;
		
		if(newpwVal.trim() == "") {
			alert("새 암호를 입력하세요.");
			frm.newpw.value = "";
			return;
		}
		
		if(newpwVal != $("#newpw_check").val()) {
			alert("새 암호와 암호확인이 일치하지 않습니다.");
			$("#newpw_check").val("");
			return;
		}
		
		frm.action = "<%= ctxPath%>/pwdChangeEnd.hello2";
		frm.method = "POST";
		frm.submit();
		
	}
	
</script>

</head>

<body>
	
	<div class="container mx-auto pt-2 pb-2" style="margin-top: 20%; border: solid 4px #2648A3; width: 50%; background-color: white; border-radius: 20px;">
		
		<img src="<%= ctxPath %>/resources/images/logo.jpg" style="width: 100%; height:100%" alt="###">
					
		<form name="pwdFrm" class="py-3">
			
			<div class="mb-3 mx-auto text-center" style="width: 70%;">
				<small class="mx-auto">장기 미접속자이므로 보안을 위해 암호를 변경해주세요.</small>
			</div>
			
			<div class="container">
				
				<p class="mx-auto text-center px-0" style="width: 70%;">					
					<input style="width: 100%;" type="password" name="currentpw" id="currentpw" class="form-control mx-auto" placeholder="현재 암호를 입력하세요"/>
				</p>
			
				<p class="mx-auto text-center px-0" style="width: 70%;">
					<input style="width: 100%;" type="password" name="newpw" id="newpw" class="form-control mx-auto" placeholder="새 암호를 입력하세요"/>
				</p>
			
				<p class="mx-auto text-center px-0" style="width: 70%;">
					<input style="width: 100%;" type="password" id="newpw_check" class="form-control mx-auto" placeholder="새 암호를 다시 한번 입력하세요"/>
				</p>
				
				<p class="mx-auto text-center" style="width: 70%;">
					<button type="button" id="btnSubmit" class="w-100 btn" style="background-color: #0070C1; color: white;">암호변경</button>
				</p>
				
			</div>
						
		</form>
				
	</div>
	
</body>
</html>