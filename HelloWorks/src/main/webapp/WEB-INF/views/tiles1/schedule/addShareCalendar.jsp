<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%	String ctxPath = request.getContextPath(); %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일정 추가 모달창</title>

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
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


<%-- *** ajax로 파일을 업로드할때 가장 널리 사용하는 방법 ==> ajaxForm *** --%>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/jquery.form.min.js"></script>

<style type="text/css">
	
	#title {
		font-weight: bold;
		vertical-align: top; 
		padding-top: 7px;
	} 
	table {
		border-collapse: separate;
  		border-spacing: 0 10px;
	}
	.ui-autocomplete {
		position: absolute;
		top: 150px;
		left: 90px;
		cursor: default;
		background-color: #fff;
		color: #0070C1;
		width: 320px;
		z-index: 30;
	}
	.ui-menu .ui-menu-item a.ui-state-hover,
	.ui-menu .ui-menu-item a.ui-state-active {
        color: #fff;
		background-color: #0070C1;
}
	#extraArea {
		margin: 15px 0;
	}
	span.plusEmp {
		
		background-color: #00518c;
		color: white;
		border-radius: 5px;
		padding: 6px;
		margin-top: 60px;
		
	}
	#title {
		font-weight: bold;
		vertical-align: top; 
		padding-top: 7px;
		font-size: 14px;
	} 
	table {
		border-collapse: separate;
  		border-spacing: 0 10px;
	}
	.star {
		color: #b4c5e4;
		font-weight: bold;
		font-size: 13pt;
	}
	

</style>

<script type="text/javascript">
	
	var allEmpid = "";
	var flagcalNameDuplicate = true;
	$(document).ready(function(){
		
		// 공유대상입력
  		$("input#s_shareEmp").keyup(function(){
  			
  			var shareEmp = $(this).val();
  			
  			$.ajax({
  				url:"<%=ctxPath%>/searchShareEmp.hello2",
  				data:{"employee":shareEmp},
  				dataType: "JSON",
  				success: function(json) {
  					var list = [];
  					
  					if(json.length>0) {
  						$.each(json, function(index, item){
	  						
	  						var inputEmp = item.empname;
	  						if(!shareEmp.includes(inputEmp)) {
	  							list.push(inputEmp+"("+item.empid+"/"+item.deptname+")")
	  							
	  						}
  						});
  					
	  					$("input#s_shareEmp").autocomplete({
	  						source:list,
	  						select: function(event, ui){ 
	  							addShareEmp(ui.item.value);
	  							return false;
	  						},
	  						focus: function(event, ui){ 
	  							return false;
	  						}
	  						
	  					});
  					}
  					
  				},
  				error: function(request, status, error){
  		            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
  		        }
  				
  				
  			});
  			
  		});
		
		
		// 공유 캘린더 엔터 입력 시
  		$("input#s_calname").bind("keyup", function(event) {
			if(event.keyCode == 13) { 
				addShare();
			}
		});
		
		
		
	});
	
	
	// Function Declaration 
	function addShareEmp(value) {
		var shareEmp = $("span.plusEmp").text();
		var $div = $("div#extraArea");
		var $span = $("<span class='plusEmp'>").text(value+" ");
		if(shareEmp.includes(value)) {
			alert("이미 추가한 사원입니다.");
		}
		else {
			shareEmp += value+",";
			$div.append($span);
			$div.append("<br><br>");
			
			// 아이디를 input태그 value에 저장
			var empid = value.substring(value.indexOf("(")+1,value.indexOf("/"))
			
			allEmpid += ","+empid;
		}
// 		console.log("allEmpid : "+allEmpid);
		$("input#s_shareEmp").val("");
	}
	
	// 공유 캘린더 추가하기
	function addShare() {
		
		$("input#allShareEmp").val(allEmpid);
		
		// 캘린더 이름 유효성 검사
		if( $("input#s_calname").val().trim() == "" ) {
			
			alert("캘린더 이름을 입력해주세요!");
			return;
		}
		
		// 공유인원 유효성 검사
		if( $("input#allShareEmp").val().trim() == "" ) {
			
			alert("캘린더를 공유할 인원을 입력해주세요!");
			return;
		}

		window.parent.calnameDuplicateCheck();
		
		if(flagcalNameDuplicate == false) { 
			var frm = document.shareFrm;
			
			frm.action ="<%=ctxPath%>/addCalendar.hello2";
			frm.method = "POST";
			frm.submit();
			
			// 부모창의  closeModal 함수 호출해서 모달창 닫기
			window.parent.closeModal();
		}
		else {
			alert("이미 존재하는 캘린더 명입니다. 다시 입력해주세요.");
			$("input#s_calname").val("");
			$("input#s_calname").focus();
		}
		
		
	}
	
	
</script>

</head>

<body>
	
	<form name="shareFrm" class="container">
		<table id= "tblCalendar" class="w-80 mx-auto">
			<tbody>
				<tr>
					<td style="width: 20%;" id="title">캘린더 이름&nbsp;</td>
					<td style="width: 80%; text-align: left;">
						<input type="text" id="s_calname" name="calname" class="form-control" />
					</td>
				</tr>
				<tr>
					<td style="width: 20%;" id="title">색상&nbsp;</td>
					<td style="width: 80%;">
						<input type="color" id="s_color" name="color" class="form-control form-control-color col-2" value="#e0f2ff" />
					</td>
				</tr>
				<tr>
					<td style="width: 20%;" id="title">공유대상&nbsp;</td>
					<td style="width: 80%;">
						<input type="text" id="s_shareEmp" class="form-control form-control-color" />
						<input type="hidden" id="allShareEmp" name="shareEmp" />
						
						<div id="extraArea"></div>
						<input type="hidden" id="loginuserid" name="loginuserid" value="${sessionScope.loginuser.empid }" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<hr>
	<div style="float:right;">
		<button type="button" class="btn myclose" data-dismiss="modal" style="background-color: #c10000; color: #fff;">Close</button>
		<button type="button" class="btn" style="background-color: #0070C1; color: #fff;" onclick="addShare()" >Add</button>
	</div>
	
	
</body>
</html>