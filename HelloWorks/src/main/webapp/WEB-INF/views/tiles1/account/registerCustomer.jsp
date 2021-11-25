<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	
	$(document).ready(function() {
		
		$("button[name=idOK]").click(function() {

			var regExp = /^\d{3}-\d{2}-\d{5}$/; // 000-00-00000의 형식
			 
			var compid = regExp.test($("input[name=customer_id]").val());
			 
			if(!compid) {
				alert("형식에 맞지 않는 사업자 등록번호이거나, 존재하지 않는 사업자 등록번호입니다.");
				return;
			}
			 
			else {
				
				$.ajax({url:"<%=request.getContextPath()%>/account/verifyId.hello2",
						type:"POST",
						data:{"compid":$("input[name=customer_id]").val()},
						dataType:"JSON",
						success:function(json) {
														
							if(json.isExist == 0) {

								$("input[name=customer_id]").prop("readonly", true);
								
								$("button[name=idOK]").prop("disabled", true);
								$("button[name=idOK]").removeClass("btn-outline-danger");
								$("button[name=idOK]").addClass("btn-success");
								$("button[name=idOK]").html("<i class='fas fa-check-circle'></i>&nbsp;완료");
								
							}
							
							else {
								alert("이미 등록된 사업자 등록번호입니다.");
								return;
							}
							
						},
						error: function(request, status, error){
							alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
						}
				});
				
			}
			
		});
		
		$("button#btn-reset").click(function() {
			
			$("input[name=customer_id]").prop("readonly", false);
			
			$("button[name=idOK]").prop("disabled", false);
			$("button[name=idOK]").removeClass("btn-success");
			$("button[name=idOK]").addClass("btn-outline-danger");
			$("button[name=idOK]").html("<i class='fas fa-times-circle'></i>&nbsp;인증");
			
		});
			
	})
	
	// Function Declaration
	
	function goRegister() {
		
		var frm = document.compFrm;
		
		if(frm.customer_id.value == "") {
			alert("사업자 등록번호를 입력하세요.");
			return;
		}
		
		if(!frm.idOK.disabled) {
			alert("사업자 등록번호를 인증해주세요.");
			return;
		}
		
		if(frm.customer_comp.value == "" && frm.customer_name.value == "") {
			alert("업체명(상호) 또는 대표자명 중 하나를 반드시 입력하세요.");
			return;
		}
		
		if(frm.customer_email.value != "") {
			
			var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
			
			var bool = regExp.test(frm.customer_email.value);
			
			if(!bool) {
				alert("이메일 형식이 올바르지 않습니다.");
				frm.customer_email.focus();				
				return;
			}
			
		}
		
		frm.action = "<%=request.getContextPath()%>/account/insertCustomer.hello2";
		frm.method = "POST";
		frm.submit();
		
	}
	
</script>

<jsp:include page="template_KJH.jsp"/>

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 300px;">
    
    <div class="container" style="margin-top: 70px;">
    	    		
   		<h4>거래처 등록/관리 - 신규 거래처 등록</h4>
   		
   		<hr>
   		
   		<form style="width: 80%;" name="compFrm">
   		
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">사업자등록번호</span></span>
				</div>
				<input type="text" class="form-control" name="customer_id" placeholder="반드시 -을 포함하여 입력하세요" autocomplete="off">
				<div class="input-group-append">
					<button class="btn btn-outline-danger" type="button" name="idOK"><i class="fas fa-times-circle"></i>&nbsp;인증</button>
					<%-- 버튼 클릭시 ajax로 사업자 정보를 조회하고 사업자 정보가 존재 할 경우 인증 버튼 비활성화, 디자인은 배경색 변경되게끔(removeClass와 addClass 활용) --%>
					<%-- 최종적으로 폼 전송할 때에 해당 버튼이 활성화되어있는지 점검 후 활성화 되어 있으면 인증부터 하라고 alert, 아니면 전송 --%>
				</div>
			</div>
			
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">업체명(상호)</span></span>
				</div>
				<input type="text" class="form-control" name="customer_comp" autocomplete="off">
			</div>
			
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">대표자명</span></span>
				</div>
				<input type="text" class="form-control" name="customer_name" autocomplete="off">
			</div>
			
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">사업장 소재지</span></span>
				</div>
				<input type="text" class="form-control" name="customer_addr" autocomplete="off">
			</div>
			
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">이메일</span></span>
				</div>
				<input type="text" class="form-control" name="customer_email" autocomplete="off" placeholder="예: abc@gmail.com">
			</div>
						
			<div style="float: right;">
				<button type="button" class="btn btn-primary btn-sm" onclick="goRegister()">등록</button>
				<button type="reset" class="btn btn-danger btn-sm" id="btn-reset">취소</button>
			</div>
			
		</form>
    		
    </div>
    
  </div>
  

</div>
<!-- !!!!!PAGE CONTENT END!!!!!! -->