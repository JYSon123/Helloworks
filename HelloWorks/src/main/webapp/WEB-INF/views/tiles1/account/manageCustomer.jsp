<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
	
	tr.cvo:hover {
		background-color: #e6f4ff;
		cursor: pointer;
	}
	
</style>

<script type="text/javascript">
	
	$(document).ready(function() {
		
		$(".thisclose").click(function() {
			$(this).parent().parent().find("input").val("");
			$(this).parent().parent().find("textarea").val("");
		});
		
		$("#excelFile").on('change',function(){
			
			if(window.FileReader){			
					            
				var fileArr = $(this).val().toLowerCase().split(".");
				
				if(fileArr[1] != "xlsx" && fileArr[1] != "xls") {
			   		alert("엑셀파일만 등록 가능합니다. (xlsx, xls)");
			   		$(this).val("");
			   		return;
				}
				
	        }
			
		});
		
		if("${searchType}" != null && "${searchType}" != "") {
			$("#searchType").val("${searchType}");
		}
		
		$("td.click").click(function() {
			
			if($(this).parent().find(".customer_comp").text() != "") {
				$("#customer_comp").text($(this).parent().find(".customer_comp").text());
			}
			
			else {
				$("#customer_comp").text($(this).parent().find(".customer_name").text());
			}
						
			$("#customer_id").text($(this).parent().find(".customer_id").text());
			$("input[name=customer_id]").val($(this).parent().find(".customer_id").text());
			$("input[name=customer_email]").val($(this).parent().find(".customer_email").text());
			
			if($("input[name=customer_email]").val() == "") {
				$("button.sendemail").hide();
			}
			
			else {
				$("button.sendemail").show();
			}
			
			$("button#btn_customerModal").trigger("click");
			
		});
		
		// 전체선택 클릭 시
       	$("input#selectAll").click(function() {       		
       		var bool = $(this).prop("checked");       		
       		$("input:checkbox[name=selectcvo]").prop("checked", bool);       		
       	});
       	
       	// 체크박스 클릭 시
       	$("input:checkbox[name=selectcvo]").click(function(){
			
			var bool = $(this).prop("checked");
			
			if(bool) { // 클릭한 체크박스가 체크가 되어진 상태이라면
				 
				var flag = false;
			
				$("input:checkbox[name=selectcvo]").each(function(index, item){
										
					var bChecked = $(item).prop("checked");
					
					if(!bChecked) {
						flag = true;
						return false;
					}
					
				});// end of each-----------------------------------
				
				if(!flag)	    			
	    			$("input:checkbox[id=selectAll]").prop("checked", true);
								
			}
			
			else // 클릭한 체크박스가 체크가 해제 되어진 상태이라면				
				$("input:checkbox[id=selectAll]").prop("checked", false);
						
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
	
	// 다중선택삭제
	function multiDelete() {
		
		var checkCnt = 0;
		var selectId = "";
		
		$(".selectcvo").each(function(index, item) {
			
			if($(this).prop("checked") == true) {				
					
				if(checkCnt == 0) 
					selectId += $(this).parent().parent().find("p.customer_id").text().trim();
				
				else
					selectId += "," + $(this).parent().parent().find("p.customer_id").text().trim();
				
				checkCnt++;
			
			}
			
		});
		
		if(checkCnt == 0) {
			alert("삭제할 거래처를 선택해주세요.");
			return;
		}
				
		else {
			var frm = document.multiFrm;
			
			frm.customer_id.value = selectId;
			frm.action = "<%=request.getContextPath()%>/account/multiDel.hello2";
			
			$("span#multiCnt").text(checkCnt);
			
			$("button#btn_multiDelModal").trigger("click");
			
		}
		
	}
	
	function multiMail() {
		
		var checkCnt = 0;
		var selectEmail = "";
		var selectName = "";
		
		$(".selectcvo").each(function(index, item) {
			
			if($(this).prop("checked") == true) {				
				
				if($(this).parent().parent().find("p.customer_email").text().trim() != "") {

					if(checkCnt == 0) {
						
						selectEmail += $(this).parent().parent().find("p.customer_email").text().trim();
						
						if($(this).parent().parent().find("p.customer_comp").text().trim() != "")
							selectName += $(this).parent().parent().find("p.customer_comp").text().trim();
						
						else
							selectName += $(this).parent().parent().find("p.customer_name").text().trim();
						
					}
										
					else {
						
						selectEmail += "," + $(this).parent().parent().find("p.customer_email").text().trim();
						
						if($(this).parent().parent().find("p.customer_comp").text().trim() != "")
							selectName += "," + $(this).parent().parent().find("p.customer_comp").text().trim();
						
						else
							selectName += "," + $(this).parent().parent().find("p.customer_name").text().trim();
					
					}
										
					checkCnt++;
				
				}
				
			}
			
		});
		
		if(checkCnt == 0) {
			alert("이메일을 전송할 거래처를 선택해주세요.");
			return;
		}
				
		else {
			var frm = document.multiEmailFrm;
			
			frm.rec_company.value = selectEmail;
			frm.rec_name.value = selectName;
			frm.method = "POST";
			frm.action = "<%=request.getContextPath()%>/account/multiMail.hello2";
						
			$("button#btn_multiEmailModal").trigger("click");
			
		}
		
	}
	
	function sendMultiEmail() {
		
		var frm = document.multiEmailFrm;
		
		if(frm.content.value.trim() == "") {
			alert("메일 내용을 입력하세요.");
			return;
		}
		
		frm.submit();
		
	}
	
	function checkFile() {
		
		var frm = document.customerRegFrm;
		
		if(frm.excelFile.value.trim() == "") {
			alert("파일을 등록해주세요.");
			return;
		}
		
		frm.method = "POST";
		frm.action = "<%=request.getContextPath()%>/account/regCustomerExcel.hello2";
		frm.submit();
		
	}
	
	function formPopup() {
		
		location.href='<%=request.getContextPath()%>/account/excelFormDownload.hello2';
		var win = window.open("<%=request.getContextPath()%>/account/explainImg.hello2");
		
	}
	
</script>

<jsp:include page="template_KJH.jsp"/>

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 300px;">
    
    <div class="container" style="margin-top: 70px;">
    	<%-- 등록된 거래처가 존재하지 않는  경우 --%>
    	<c:if test="${searchType eq '' and (empty cvoList or cvoList == '')}">
    		
    		<h4>거래처 등록/관리</h4>
    		
    		<hr>
    		
    		<div class="text-center mx-auto px-auto" style="width: 80%;">
    			<p>현재 등록된 거래처가 존재하지 않습니다.<br><br>신규 거래처를 등록하세요.</p>
    			<button type="button" class="btn btn-success" onclick="location.href='<%=request.getContextPath()%>/account/registerCustomer.hello2'">거래처 등록</button>    		
    		</div>
    		
    	</c:if>
    	
    	<%-- 등록된 거래처가 존재하는 경우 --%>
    	<c:if test="${(searchType ne '' and (empty cvoList or cvoList == '')) or (not empty cvoList and cvoList != '')}">
    		
    		<h4>거래처 등록/관리</h4>
    		
    		<hr>
    		
    		<div class="mx-auto px-auto" style="width: 100%;">
    			
    			<form name="multiFrm" method="POST">
    				<input type="hidden" name="customer_id"/>
    			</form>
    			
    			<p class="w-100 my-3 text-right">
    				<span style="float: left;"><button type="button" style="border: solid 1px gray; background-color: #ebf0fa; border-radius: 5px; font-size: 10pt; padding: 3px 8px;" onclick="formPopup();">엑셀양식다운</button></span>
    				<button type="button" class="btn btn-info btn-sm" id="btn_customerRegModal" data-toggle="modal" data-target="#customerRegModal">거래처등록(엑셀)</button>
    				<button type="button" class="btn btn-success btn-sm" onclick="location.href='<%=request.getContextPath()%>/account/registerCustomer.hello2'">거래처 등록</button>
    				<button type="button" class="btn btn-danger btn-sm" onclick="multiDelete()">삭제</button>
    				<button type="button" class="btn btn-primary btn-sm" onclick="multiMail()">이메일전송</button>
    			</p>
    			
    			<table class="w-100" style="font-size: 10pt; font-weight: normal;">
    				
    				<thead style="background-color: #ebf0fa; height: 50px; color: gray;">
			
						<tr>
							
							<th style="width: 15%; text-align: center; font-weight: normal; border-right: solid 1px white;">사업자등록번호</th>
							<th style="width: 13%; text-align: center; font-weight: normal; border-right: solid 1px white;">업체명(상호)</th>
							<th style="width: 10%; text-align: center; font-weight: normal; border-right: solid 1px white;">대표자명</th>
							<th style="text-align: center; font-weight: normal; border-right: solid 1px white;">사업장소재지</th>
							<th style="width: 18%; text-align: center; font-weight: normal; border-right: solid 1px white;">이메일</th>
							<th style="width: 5%; text-align: center; font-weight: normal; font-size: 8pt;"><label for="selectAll">전체선택</label><input type="checkbox" name="selectAll" id="selectAll"/></th>							
						</tr>
			
					</thead>
					
					<tbody>
						
						<c:forEach var="cvo" items="${cvoList}">
							
							<tr valign="middle" class="cvo" style="border: solid 1px #e6e6e6; border-top: none;">
								
								<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 customer_id">${cvo.customer_id}</p></td>
								<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 customer_comp">${cvo.customer_comp}</p></td>
								<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 customer_name">${cvo.customer_name}</p></td>
								<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 customer_addr">${cvo.customer_addr}</p></td>
								<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 customer_email">${cvo.customer_email}</p></td>
								<td style="text-align: center;"><input type="checkbox" name="selectcvo" class="selectcvo"/></td>
							</tr>
							
						</c:forEach>
						
					</tbody>
    				
    			</table>
    			
    			<nav class="nav-light">
					<ul class="pagination justify-content-center" style="margin:20px 0;">
						${pageBar}
					</ul>
				</nav>
				
				<form name="searchForm" action="<%=request.getContextPath()%>/account/manageCustomer.hello2" method="GET">
								
					<div class="row w-100 mx-0 my-2 px-auto justify-content-center input-group">
									
						<div class="input-group-prepend" style="">
							<select name="searchType" id="searchType" style="height: 29px; background-color: #ebf0fa; border-radius: 5px 0 0 5px; text-align: center; font-size: 10pt;">
								<option value="customer_id">사업자등록번호</option>
								<option value="customer_comp" selected="selected">업체명(상호)</option>
								<option value="customer_name">대표자명</option>
							</select>
						</div>								            	            
						<input type="text" name="searchWord" style="font-size: 10pt;" value="${paraMap.searchWord}">
						&ensp; 
						<button type="button" onclick="search()" style="border: solid 1px gray; background-color: #ebf0fa; border-radius: 5px; font-size: 10pt; padding: 3px 8px;">조회</button>						    
											
					</div>
					
				</form>
    			
    			<button type="button" id="btn_customerModal" data-toggle="modal" data-target="#customerModal" style="display: none;"></button>
    			<button type="button" id="btn_deleteModal" data-toggle="modal" data-target="#deleteModal" style="display: none;" data-backdrop="static"></button>
    			<button type="button" id="btn_emailModal" data-toggle="modal" data-target="#emailModal" style="display: none;" data-backdrop="static"></button>
    			<button type="button" id="btn_multiDelModal" data-toggle="modal" data-target="#multiDelModal" style="display: none;" data-backdrop="static"></button>
    			<button type="button" id="btn_multiEmailModal" data-toggle="modal" data-target="#multiEmaillModal" style="display: none;" data-backdrop="static"></button>
    			
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
				      				<button type="button" class="btn btn-sm btn-success deleteOK mx-1" onclick="sendEmail()">전송</button>
				      				<button type="button" class="btn btn-sm btn-danger thisclose mx-1" data-dismiss="modal">취소</button>
								</form>
																				
							</div>
			     				      
			    		</div>
			    
			  		</div>
			  		
				</div>
				<%--=========================================--%>
				
				<%-- 다중삭제모달 --%>
        		<div class="modal fade" id="multiDelModal">
			  		
			  		<div class="modal-dialog modal-dialog-scrollable modal modal-dialog-centered">
			  
			    		<div class="modal-content">			      
			      	
			      			<!-- Modal header -->
			      			<div class="modal-header text-center px-auto">
			        			<h6 class="modal-title mx-auto text-center">정말로 <span id="multiCnt"></span>개의 거래처를 삭제하시겠습니까?</h6>
			      			</div>
			      	
			     			<!-- Modal body -->
			      			<div class="modal-body text-center px-auto">			      		
				      			<button type="button" class="btn btn-sm btn-danger deleteOK mx-1" onclick="document.multiFrm.submit();">삭제</button>
				      			<button type="button" class="btn btn-sm btn-success thisclose mx-1" data-dismiss="modal">취소</button>
							</div>
			     				      
			    		</div>
			    
			  		</div>
			  		
				</div>
				
				<%--=========================================--%>
				
				<%-- 다중 이메일모달 --%>
        		<div class="modal fade" id="multiEmaillModal">
			  		
			  		<div class="modal-dialog modal-dialog-scrollable modal modal-dialog-centered">
			  
			    		<div class="modal-content">			      
			      	
			      			<!-- Modal header -->
			      			<div class="modal-header text-center px-auto">
			        			<h6 class="modal-title mx-auto text-center">이메일 작성</h6>
			      			</div>
			      	
			     			<!-- Modal body -->
			      			<div class="modal-body text-center px-auto w-90">
			      												
				      			<form name="multiEmailFrm">
				      				<input type="hidden" name="rec_company"/>
				      				<input type="hidden" name="rec_name"/>
				      				<textarea name="content" rows="20" cols="50" placeholder="내용을 입력하세요."></textarea>
				      				<br><br>    		
				      				<button type="button" class="btn btn-sm btn-success deleteOK mx-1" onclick="sendMultiEmail()">전송</button>
				      				<button type="button" class="btn btn-sm btn-danger thisclose mx-1" data-dismiss="modal">취소</button>
								</form>
																				
							</div>
			     				      
			    		</div>
			    
			  		</div>
			  		
				</div>
				
				<%--=========================================--%>
    			
		    	<%-- 거래처엑셀등록모달 --%>
		    	<div class="modal fade" id="customerRegModal">
					  
					  <div class="modal-dialog modal-dialog-scrollable modal modal-dialog-centered">
					  
						<div class="modal-content">			      
					      	
					      	<!-- Modal header -->
					      <div class="modal-header">
					      		<h6 class="modal-title">거래처등록(엑셀)</h6>			        	
					        	<button type="button" class="close thisclose" data-dismiss="modal">&times;</button>
					      </div>
					      	
					     <!-- Modal body -->
					      <div class="modal-body">
								
						      	<form name="customerRegFrm" enctype="multipart/form-data">
						      		<input type="file" name="excelFile" id="excelFile"/>
						      		<br>
						      		<span style="color: red; font-size: 8pt;">.xlsx 또는 .xls 파일만 등록가능합니다</span>      		
						      		<br>
						      		<p class="text-right">
						      			<button type="button" class="btn btn-sm btn-success" onclick="checkFile();">등록</button>
						      			<button type="button" class="btn btn-sm btn-danger thisclose mx-1" data-dismiss="modal">취소</button>
									</p>
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