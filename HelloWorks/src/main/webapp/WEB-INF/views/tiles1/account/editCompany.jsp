<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	
	$(document).ready(function() {
		
		if($("input[name=mycompany_id]").val() != null && $("input[name=mycompany_id]").val() != "") {
			
			var id = $("input[name=mycompany_id]").val();
			

			$("button#btn_update").click(function() {
				
				$("input[name=mycompany_id]").prop("readonly", false);
				$("input[name=mycompany_comp]").prop("readonly", false);
				$("input[name=mycompany_name]").prop("readonly", false);
				$("input[name=mycompany_addr]").prop("readonly", false);
				$("input[name=mycompany_sort]").prop("readonly", false);
				
				$("div#updatediv").show();
				$("div#readdiv").hide();
				
			});
			
			$("button#btn_cancel").click(function() {
				
				location.reload();
				
			});
			
			$("input[name=mycompany_id]").keyup(function() {
				
				if(id != "" && id != $(this).val()) {
					$("button[name=idOK]").prop("disabled", false);
					$("button[name=idOK]").removeClass("btn-success");
					$("button[name=idOK]").addClass("btn-outline-danger");
					$("button[name=idOK]").html("<i class='fas fa-times-circle'></i>&nbsp;인증");
				}
				
				else {
					$("button[name=idOK]").prop("disabled", true);
					$("button[name=idOK]").removeClass("btn-outline-danger");
					$("button[name=idOK]").addClass("btn-success");
					$("button[name=idOK]").html("<i class='fas fa-check-circle'></i>&nbsp;완료");
				}
				
			});
			
		}
		
		$("button[name=idOK]").click(function() {

			var regExp = /^\d{3}-\d{2}-\d{5}$/; // 000-00-00000의 형식
			 
			var compid = regExp.test($("input[name=mycompany_id]").val());
			 
			if(!compid) {
				alert("형식에 맞지 않는 사업자 등록번호이거나, 존재하지 않는 사업자 등록번호입니다.");
				return;
			}
			 
			else {				
				$("input[name=mycompany_id]").prop("readonly", true);
				
				$(this).prop("disabled", true);
				$(this).removeClass("btn-outline-danger");
				$(this).addClass("btn-success");
				$(this).html("<i class='fas fa-check-circle'></i>&nbsp;완료");
			}
			
		});
		
		$("button#btn-reset").click(function() {
			
			$("input[name=mycompany_id]").prop("readonly", false);
			
			$("button[name=idOK]").prop("disabled", false);
			$("button[name=idOK]").removeClass("btn-success");
			$("button[name=idOK]").addClass("btn-outline-danger");
			$("button[name=idOK]").html("<i class='fas fa-times-circle'></i>&nbsp;인증");
			
		});
			
	})
	
	// Function Declaration
	
	function goRegister() {
		
		var frm = document.compFrm;
		
		if(frm.mycompany_id.value == "") {
			alert("사업자 등록번호를 입력하세요.");
			return;
		}
		
		if(!frm.idOK.disabled) {
			alert("사업자 등록번호를 인증해주세요.");
			return;
		}
		
		if(frm.mycompany_comp.value == "") {
			alert("업체명(상호)를 입력하세요.");
			return;
		}
		
		if(frm.mycompany_name.value == "") {
			alert("대표자명을 입력하세요.");
			return;
		}
		
		if(frm.mycompany_addr.value == "") {
			alert("사업장소재지를 입력하세요.");
			return;
		}
		
		if(frm.mycompany_sort.value == "") {
			alert("업종을 입력하세요.");
			return;
		}
		
		frm.action = "<%=request.getContextPath()%>/account/registerCompany.hello2";
		frm.method = "POST";
		frm.submit();
		
	}
	
	function goUpdate() {
		
		// ★ 여기부터 작업하면 된다 ★ 이거랑 삭제랑 만들면 된다!!!!!!!!!!!! ★
		var frm = document.compUpFrm;
		
		if(frm.mycompany_id.value == "") {
			alert("사업자 등록번호를 입력하세요.");
			return;
		}
		
		if(!frm.idOK.disabled) {
			alert("사업자 등록번호를 인증해주세요.");
			return;
		}
		
		if(frm.mycompany_comp.value == "") {
			alert("업체명(상호)를 입력하세요.");
			return;
		}
		
		if(frm.mycompany_name.value == "") {
			alert("대표자명을 입력하세요.");
			return;
		}
		
		if(frm.mycompany_addr.value == "") {
			alert("사업장소재지를 입력하세요.");
			return;
		}
		
		if(frm.mycompany_sort.value == "") {
			alert("업종을 입력하세요.");
			return;
		}
		
		frm.action = "<%=request.getContextPath()%>/account/updateCompany.hello2";
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
    	
    	<%-- 사업자 등록을 해야하는 경우 --%>
    	<c:if test="${empty sessionScope.comp}">
    		
    		<h4>사업자 정보 관리 - 사업자 정보 등록</h4>
    		
    		<hr>
    		
    		<form style="width: 80%;" name="compFrm">
    		
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">사업자등록번호</span></span>
					</div>
					<input type="text" class="form-control" name="mycompany_id" placeholder="반드시 -을 포함하여 입력하세요" autocomplete="off">
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
					<input type="text" class="form-control" name="mycompany_comp" autocomplete="off">
				</div>
				
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">대표자명</span></span>
					</div>
					<input type="text" class="form-control" name="mycompany_name" autocomplete="off">
				</div>
				
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">사업장 소재지</span></span>
					</div>
					<input type="text" class="form-control" name="mycompany_addr" autocomplete="off">
				</div>
				
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">업종</span></span>
					</div>
					<input type="text" class="form-control" name="mycompany_sort" placeholder="예:소매,도매,교육" autocomplete="off">
				</div>
				
				<div style="float: right;">
					<button type="button" class="btn btn-primary btn-sm" onclick="goRegister()">등록</button>
					<button type="reset" class="btn btn-danger btn-sm" id="btn-reset">취소</button>
				</div>
			</form>
    		
    	</c:if>
    	
    	<%-- 이미 사업자 정보를 등록해놨을 경우 --%>
    	<c:if test="${not empty sessionScope.comp}">
    		
    		<h4>사업자 정보 관리</h4>
    		
    		<hr>
    		
    		<form style="width: 80%;" name="compUpFrm">
    		
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">사업자등록번호</span></span>
					</div>
					<input type="text" class="form-control" name="mycompany_id" placeholder="반드시 -을 포함하여 입력하세요" value="${sessionScope.comp.mycompany_id}" readonly autocomplete="off">
					<div class="input-group-append">
						<button class="btn btn-success" type="button" name="idOK" disabled><i class='fas fa-check-circle'></i>&nbsp;완료</button>
						<%-- 버튼 클릭시 ajax로 사업자 정보를 조회하고 사업자 정보가 존재 할 경우 인증 버튼 비활성화, 디자인은 배경색 변경되게끔(removeClass와 addClass 활용) --%>
						<%-- 최종적으로 폼 전송할 때에 해당 버튼이 활성화되어있는지 점검 후 활성화 되어 있으면 인증부터 하라고 alert, 아니면 전송 --%>
					</div>
				</div>
				
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">업체명(상호)</span></span>
					</div>
					<input type="text" class="form-control" name="mycompany_comp" value="${sessionScope.comp.mycompany_comp}" readonly autocomplete="off">
				</div>
				
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">대표자명</span></span>
					</div>
					<input type="text" class="form-control" name="mycompany_name" value="${sessionScope.comp.mycompany_name}" readonly autocomplete="off">
				</div>
				
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">사업장 소재지</span></span>
					</div>
					<input type="text" class="form-control" name="mycompany_addr" value="${sessionScope.comp.mycompany_addr}" readonly autocomplete="off">
				</div>
				
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" style="background-color: #cce0ff; border: solid 1px #99c2ff;"><span style="width: 150px;">업종</span></span>
					</div>
					<input type="text" class="form-control" name="mycompany_sort" placeholder="예:소매,도매,교육" value="${sessionScope.comp.mycompany_sort}" readonly autocomplete="off">
				</div>
				
				<div style="float: right; display: none;" id="updatediv">
					<button type="button" class="btn btn-warning btn-sm" onclick="goUpdate()">저장</button>
					<button type="button" class="btn btn-secondary btn-sm" id="btn_cancel">취소</button>
				</div>
				
				<div style="float: right;" id="readdiv">
					<button type="button" class="btn btn-primary btn-sm" id="btn_update">수정</button>
					<button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#deleteModal" data-backdrop="static">삭제</button>
				</div>
				
			</form>
    		
    		<%-- 삭제모달 --%>
        	<div class="modal fade" id="deleteModal">
			  	<div class="modal-dialog modal-dialog-scrollable modal modal-dialog-centered">
			  
			    	<div class="modal-content">			      
			      	
			      		<!-- Modal header -->
			      		<div class="modal-header text-center">
			        		<h6 class="modal-title w-100 text-center">사업자 정보를 정말로 삭제하시겠습니까?</h6>
			      		</div>
			      	
			     		<!-- Modal body -->
			      		<div class="modal-body text-center">
	
				      		<form name="deleteComp" method="POST" action="<%=request.getContextPath()%>/account/deleteCompany.hello2">
				      			<input type="hidden" value="${sessionScope.comp.mycompany_id}" name="mycompany_id">				      		
				      			<button type="submit" class="btn btn-sm btn-danger deletethis mx-1">삭제</button>
				      			<button type="button" class="btn btn-sm btn-success thisclose mx-1" data-dismiss="modal">취소</button>
							</form>
												
						</div>
			     				      
			    	</div>
			  	</div>
			</div>
			
    	</c:if>
		    	
    </div>
    
  </div>
  

</div>
<!-- !!!!!PAGE CONTENT END!!!!!! -->