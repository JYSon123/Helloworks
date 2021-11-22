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
		left: 75px;
		cursor: default;
		background-color: white;
		color: #0070C1;
	}
	#extraArea {
		margin: 15px 0;
	}
	span.plusEmp {
		
		background-color: #00518c;
		color: white;
		border-radius: 5px;
		padding: 6px;
		margin: 10px 2px;
		
	}
	

</style>

<script type="text/javascript">
	
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
	  						},
	  				        open: function(event, ui) {
	  				            $(this).autocomplete("widget").css({
	  				                "width": "700px"
	  				            });
	  				        }
	  						
	  					});
  					}
  					
  				},
  				error: function(request, status, error){
  		            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
  		        }
  				
  				
  			});
  			
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
		}
		$("input#s_shareEmp").val("");
	}
	
	
</script>

</head>

<body>
	
	<form name="shareFrm">
		<table id= "tblCalendar" class="w-90 mx-auto">
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
						<input type="text" id="s_shareEmp" name="shareEmp" class="form-control form-control-color" />
						<div id="extraArea" style=""></div>
						<input type="hidden" id="loginuserid" name="loginuserid" value="${sessionScope.loginuser.empid }" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>