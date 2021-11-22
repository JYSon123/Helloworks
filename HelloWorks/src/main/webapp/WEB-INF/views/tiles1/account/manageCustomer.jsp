<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
	
	tr.cvo:hover {
		background-color: #e6f4ff;
	}
	
</style>

<script type="text/javascript">
	
	$(document).ready(function() {
		
		// 해야할 일 : 거래처 수정, 삭제, 이메일전송 만들기(tr클릭시 모달을 이용하자!) 
		
		$("tr.cvo").click(function() {
			
			if($(this).find(".customer_comp").text() != "") {
				$("#customer_comp").text($(this).find(".customer_comp").text());
			}
			
			else {
				$("#customer_comp").text($(this).find(".customer_name").text());
			}
						
			$("#customer_id").text($(this).find(".customer_id").text());
			$("input[name=customer_id]").val($(this).find(".customer_id").text());
			$("input[name=customer_email]").val($(this).find(".customer_email").text());
			
			if($("input[name=customer_email]").val() == "") {
				$("button.sendemail").hide();
			}
			
			else {
				$("button.sendemail").show();
			}
			
			$("button#btn_customerModal").trigger("click");
			
		});
			
	})
	
	// Function Declaration
	
	// 거래처 수정
	function goUpdate() {
		
		var frm = document.customer_action;
		
		frm.action = "<%=request.getContextPath()%>/account/modifyCustomer.hello2";
		frm.method = "POST";
		frm.submit();
		
	}
	
	// 거래처 삭제
	function goDelete() {
		
		var customer_comp = $("#customer_comp").text();
		
		$("button.thisclose").trigger("click");
		
		$("span.what").text(customer_comp);
		
		$("button#btn_deleteModal").trigger("click");
		
	}
	
	function deleteOK() {
		
		var frm = document.customer_action;
		
		frm.action = "<%=request.getContextPath()%>/account/deleteCustomer.hello2";
		frm.method = "POST";
		frm.submit();		
		
	}
	
	// 이메일전송 클릭 시
	function goEmail() {
		
		var customer_email = $("input[name=customer_email]").val();
		var customer_comp = $("#customer_comp").text()
		
		$("button.thisclose").trigger("click");
		
		$("input[name=rec_company]").val(customer_email);
		$("#rec_comp").text(customer_comp);
		$("input[name=rec_name]").val(customer_comp);
		
		$("button#btn_emailModal").trigger("click");
				
	}
	
	// 이메일 전송
	function sendEmail() {
				
		var frm = document.emailFrm;
		
		if(frm.content.value.trim() == "") {
			alert("메일 내용을 입력하세요.");
			return;
		}
		
		frm.action = "<%=request.getContextPath()%>/account/sendEmail.hello2";
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
    	
    	<%-- 등록된 거래처가 존재하지 않는  경우 --%>
    	<c:if test="${empty cvoList or cvoList == ''}">
    		
    		<h4>거래처 등록/관리</h4>
    		
    		<hr>
    		
    		<div class="text-center mx-auto px-auto" style="width: 80%;">
    			<p>현재 등록된 거래처가 존재하지 않습니다.<br><br>신규 거래처를 등록하세요.</p>
    			<button type="button" class="btn btn-success" onclick="location.href='<%=request.getContextPath()%>/account/registerCustomer.hello2'">거래처 등록</button>    		
    		</div>
    		
    	</c:if>
    	
    	<%-- 등록된 거래처가 존재하는 경우 --%>
    	<c:if test="${not empty cvoList and cvoList != ''}">
    		
    		<h4>거래처 등록/관리</h4>
    		
    		<hr>
    		
    		<div class="mx-auto px-auto" style="width: 100%;">
    			
    			<p class="text-right w-100">
    				<button type="button" class="btn btn-success" onclick="location.href='<%=request.getContextPath()%>/account/registerCustomer.hello2'">거래처 등록</button>
    			</p>
    			
    			<table class="w-100" style="font-size: 10pt; font-weight: normal;">
    				
    				<thead style="background-color: #0070C1; color: white; height: 50px;">
				
						<tr>
							
							<th style="width: 15%; text-align: center;">사업자등록번호</th>
							<th style="width: 13%; text-align: center;">업체명(상호)</th>
							<th style="width: 10%; text-align: center;">대표자명</th>
							<th style="text-align: center;">사업장소재지</th>
							<th style="width: 18%; text-align: center;">이메일</th>
							
						</tr>
				
					</thead>
					
					<tbody>
						
						<c:forEach var="cvo" items="${cvoList}">
							
							<tr valign="middle" class="cvo">
								
								<td style="text-align: center;"><p class="my-2 customer_id">${cvo.customer_id}</p></td>
								<td style="text-align: center;"><p class="my-2 customer_comp">${cvo.customer_comp}</p></td>
								<td style="text-align: center;"><p class="my-2 customer_name">${cvo.customer_name}</p></td>
								<td style="text-align: center;"><p class="my-2 customer_addr">${cvo.customer_addr}</p></td>
								<td style="text-align: center;"><p class="my-2 customer_email">${cvo.customer_email}</p></td>
								
							</tr>
							
						</c:forEach>
						
					</tbody>
    				
    			</table>
    			
    			<button type="button" id="btn_customerModal" data-toggle="modal" data-target="#customerModal" style="display: none;"></button>
    			<button type="button" id="btn_deleteModal" data-toggle="modal" data-target="#deleteModal" style="display: none;" data-backdrop="static"></button>
    			<button type="button" id="btn_emailModal" data-toggle="modal" data-target="#emailModal" style="display: none;" data-backdrop="static"></button>
    			
    			<%--=========================================--%>
    			
		    	<%-- 모달 --%>
		    	<div class="modal fade" id="customerModal">
					  
					  <div class="modal-dialog modal-dialog-scrollable modal modal-dialog-centered">
					  
						<div class="modal-content">			      
					      	
					      	<!-- Modal header -->
					      <div class="modal-header">			        	
					        	<button type="button" class="close thisclose" data-dismiss="modal">&times;</button>
					      </div>
					      	
					     <!-- Modal body -->
					      <div class="modal-body text-center px-auto">
								
								<h4 class="modal-title mx-auto" id="customer_comp"></h4>
								<h6 class="modal-title mx-auto" id="customer_id"></h6>
								
								<hr>
								
						      	<form name="customer_action">
						      		<input type="hidden" name="customer_id"/>
						      		<input type="hidden" name="customer_email"/>      		
						      		<button type="button" class="btn btn-sm btn-success update mx-1" onclick="goUpdate()">수정</button>
						      		<button type="button" class="btn btn-sm btn-danger delete mx-1" onclick="goDelete()">삭제</button>
						      		<button type="button" class="btn btn-sm btn-primary sendemail mx-1" onclick="goEmail()">메일전송</button>
								</form>
														
							</div>
					     				      
						</div>
					    
					  </div>
					  
				</div>
				
				<%--=========================================--%>
				
				<%-- 삭제모달 --%>
        		<div class="modal fade" id="deleteModal">
			  		
			  		<div class="modal-dialog modal-dialog-scrollable modal modal-dialog-centered">
			  
			    		<div class="modal-content">			      
			      	
			      			<!-- Modal header -->
			      			<div class="modal-header text-center px-auto">
			        			<h6 class="modal-title mx-auto text-center"><span class="what" style="font-weight: bold;"></span>을(를) 삭제하시겠습니까?</h6>
			      			</div>
			      	
			     			<!-- Modal body -->
			      			<div class="modal-body text-center px-auto">
	
				      			<form name="deleteCompany">			      		
				      				<button type="button" class="btn btn-sm btn-danger deleteOK mx-1" onclick="deleteOK()">삭제</button>
				      				<button type="button" class="btn btn-sm btn-success thisclose mx-1" data-dismiss="modal">취소</button>
								</form>
												
							</div>
			     				      
			    		</div>
			    
			  		</div>
			  		
				</div>
				
				<%--=========================================--%>
				
				<%-- 이메일모달 --%>
        		<div class="modal fade" id="emailModal">
			  		
			  		<div class="modal-dialog modal-dialog-scrollable modal modal-dialog-centered">
			  
			    		<div class="modal-content">			      
			      	
			      			<!-- Modal header -->
			      			<div class="modal-header text-center px-auto">
			        			<h6 class="modal-title mx-auto text-center">이메일 작성</h6>
			      			</div>
			      	
			     			<!-- Modal body -->
			      			<div class="modal-body text-center px-auto w-90">
			      							      				
								<p style="text-align: left;">TO.&nbsp;<span id="rec_comp"></span></p>
																
				      			<form name="emailFrm">
				      				<input type="hidden" name="rec_company"/>
				      				<input type="hidden" name="rec_name"/>
				      				<textarea name="content" rows="20" cols="50" placeholder="내용을 입력하세요."></textarea>
				      				<br><br>    		
				      				<button type="button" class="btn btn-sm btn-danger deleteOK mx-1" onclick="sendEmail()">전송</button>
				      				<button type="button" class="btn btn-sm btn-success thisclose mx-1" data-dismiss="modal">취소</button>
								</form>
																				
							</div>
			     				      
			    		</div>
			    
			  		</div>
			  		
				</div>
				
				<%--=========================================--%>			
    			
    		</div>
    		
    	</c:if>
    			    	
    </div>
    
  </div>
  

</div>
<!-- !!!!!PAGE CONTENT END!!!!!! -->