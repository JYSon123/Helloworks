<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
   String ctxPath = request.getContextPath(); // /board
%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LOGIN</title>

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
		
		$("input#usercode").bind("keyup", function(event) {
			if(event.keyCode == 13) {
				otpSubmit();
			}
		});
		
		$("#btnSubmit").click(function() {			
			otpSubmit();			
		});
				
	})// end of $(document).ready(function() {})------------------------------------------
	
	// Function Declaration
	function otpSubmit() {
		
		var frm = document.otpFrm;
		
		frm.usercode.value = frm.usercode.value.trim();
		
		var usercodeVal = frm.usercode.value;
		
		if(usercodeVal == "" || usercodeVal.length != 6) {
			
			alert("OTP 인증앱에 표시된 인증번호 6자리를 입력하세요.");
			
			frm.usercode.value = "";
			
			return;
			
		}
		
		frm.action = "<%= ctxPath%>/otpEnd.hello2";
		frm.method = "POST";
		frm.submit();
		
	}
	
</script>

</head>

<body>
	
	<div class="container mx-auto pt-2 pb-2 mb-5" style="margin-top: 15%; border: solid 4px #2648A3; width: 50%; background-color: white; border-radius: 20px;">
		
		<img src="<%= ctxPath %>/resources/images/logo.jpg" style="width: 100%; height:100%" alt="###">
					
		<div class="container py-3">
			
			<div class="mb-3 mx-auto text-center" style="width: 70%;">
				<small class="mx-auto">OTP 인증앱에 표시된 인증번호 6자리를 입력하세요.</small>
			</div>
			
			<form name="otpFrm">
				<div class="input-group mb-3 mx-auto text-center" style="width: 70%;">
					<input id="usercode" name="usercode" type="text" class="form-control" placeholder="OTP 인증번호를 입력하세요" autocomplete="off">
					<input type="text" style="display: none;"/>
					<div class="input-group-append">
						<button id="btnSubmit" class="btn" type="button" style="background-color: #0070C1; color: white;">OTP 확인</button>
					</div>
				</div>
			</form>
		</div>
				
	</div>
	
</body>
</html>