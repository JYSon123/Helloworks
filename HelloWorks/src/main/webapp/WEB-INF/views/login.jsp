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
		
		// === 로컬스토리지(localStorage)에 저장된 key 가 "saveid"인  userid 값을 불러와서 input 태그 userid에 넣어주기 === //
		
		var saveEmpid = localStorage.getItem('saveid');
		
		if(saveEmpid != null) {
			$("input#empid").val(saveEmpid);
			$("input:checkbox[id=saveid]").prop('checked', true);
			$("input#emppw").focus();
		}
		
		else
			$("input#empid").focus();
		
		/////////////////////////////////////////////////////////////////////////////////
		
		$("button#btnSubmit").click(function() {
			goLogin(); // 로그인 시도
		});
		
		$("input#emppw").bind("keyup", function(event) {
			if(event.keyCode == 13) { // 암호 입력란에서 엔터를 했을 경우 : [enter] ==> 13
				goLogin(); // 로그인 시도
			}
		});
				
	})// end of $(document).ready(function() {})------------------------------------------
	
	// Function Declaration
	function goLogin() {
		
		var empid = $("input#empid").val().trim();
		var emppw = $("input#emppw").val().trim();
		
		if(empid == ""){
			alert("아이디를 입력하세요.");
			$("input#empid").val("");
			$("input#empid").focus();
			return; // 햠수 종료
		}
		
		if(emppw == ""){
			alert("암호를 입력하세요.");
			$("input#emppw").val("");
			$("input#emppw").focus();
			return; // 함수 종료
		}
		
		if( $("input:checkbox[id=saveid]").prop("checked") ) { // [아이디저장]에 체크되어있으면							
			localStorage.setItem('saveid', $("input#empid").val());			
		}
			
		else {
			localStorage.removeItem('saveid');
		}
				
		var frm = document.loginFrm;
		
		frm.action = "<%= request.getContextPath()%>/loginEnd.hello2";
		frm.method = "POST";
		frm.submit();
		
	}
	
</script>

</head>

<body>
	
	<div class="container mx-auto pt-2 pb-2 mb-5" style="margin-top: 15%; border: solid 4px #2648A3; width: 50%; background-color: white; border-radius: 20px;">
		
		<img src="<%= ctxPath %>/resources/images/logo.jpg" style="width: 100%; height:100%" alt="###">
		
		<form name="loginFrm">
			
			<div class="container py-3">
				
				<p class="mx-auto text-center px-0" style="width: 70%;">					
					<input style="width: 100%;" type="text" name="empid" id="empid" class="form-control mx-auto"/>
				</p>
			
				<p class="mx-auto text-center px-0" style="width: 70%;">
					<input style="width: 100%;" type="password" name="emppw" id="emppw" class="form-control mx-auto"/>
				</p>
			
				<p class="mx-auto text-left px-1" style="width: 70%;">
					<input type="checkbox" id="saveid" name="saveid" /><label for="saveid" class="ml-1">아이디저장</label>
				</p>
				
				<p class="mx-auto text-center" style="width: 70%;">
					<button type="button" id="btnSubmit" class="w-100 btn" style="background-color: #0070C1; color: white;">LOGIN</button>
				</p>
				
			</div>
						
		</form>
		
	</div>
	
</body>
</html>