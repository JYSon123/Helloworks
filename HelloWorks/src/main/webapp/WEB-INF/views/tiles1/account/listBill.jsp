<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">
	
	tr.doc:hover {
		background-color: #e6f4ff;		
	}
	
	td.click:hover {
		cursor: pointer;
	}
	
	.tab:hover {
		background-color: #ebf0fa;
	}
	
	.tblue {
		color: #b30000;
	}
	
</style>

<script type="text/javascript">
	
	$(document).ready(function() {
		
		var activateTab = "${paraMap.tabName}";
		
		$("#" + activateTab).addClass('active');
				
		if("${paraMap.searchType}" != null && "${paraMap.searchType}" != "") {
			$("#searchType").val("${paraMap.searchType}");
		}
				
		if("${paraMap.startDate}" != null && "${paraMap.startDate}" != ""
				&& "${paraMap.lastDate}" != null && "${paraMap.lastDate}" != "") {
		
			var startDate = "${paraMap.startDate}".substring(0,4) + "-" + "${paraMap.startDate}".substring(4,6) + "-" + "${paraMap.startDate}".substring(6);
			var lastDate = "${paraMap.lastDate}".substring(0,4) + "-" + "${paraMap.lastDate}".substring(4,6) + "-" + "${paraMap.lastDate}".substring(6);
			
			$("#startDate").val(startDate);
			$("#lastDate").val(lastDate);
			
		}
				
       	//모든 datepicker에 대한 공통 옵션 설정
       	$.datepicker.setDefaults({
           	dateFormat: 'yy-mm-dd' //Input Display Format 변경
           	,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
           	,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
			,changeYear: true //콤보박스에서 년 선택 가능
			,changeMonth: true //콤보박스에서 월 선택 가능                
		//	,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
		//	,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
		//	,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
		//	,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
           	,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
           	,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
           	,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
           	,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
           	,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
		//	,minDate: "-1M" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
       	   	,maxDate: "today" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)                    
       	});

       	//input을 datepicker로 선언
       	$("input#startDate").datepicker({
    	   	maxDate: 0,
    	   	onClose : function(selectedDate){   		   
    		   	$("input#toDate").datepicker("option", "minDate", selectedDate);
    	   	}
       	}); 
       
       	$("input#lastDate").datepicker({  	
    	   	maxDate: 0,
    	   	onClose : function(selectedDate){   		   
    		   	$("input#fromDate").datepicker("option", "maxDate", selectedDate);
    	   	}
       	});
		
       	// 검색창에서 엔터 클릭 시
		$("input[name=searchWord]").keyup(function(event) {			
			if(event.keyCode == 13)
				search();			
		});
       	
       	// 전체선택 클릭 시
       	$("input#selectAll").click(function() {       		
       		var bool = $(this).prop("checked");       		
       		$("input:checkbox[name=selectdoc]").prop("checked", bool);       		
       	});
       	
       	// 문서체크박스 클릭 시
       	$("input:checkbox[name=selectdoc]").click(function(){
			
			var bool = $(this).prop("checked");
			
			if(bool) { // 클릭한 체크박스가 체크가 되어진 상태이라면
				 
				var flag = false;
			
				$("input:checkbox[name=selectdoc]").each(function(index, item){
										
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
       	
       	// 행 클릭 시
       	$("td.click").click(function() {
       		
       		var seq = $(this).parent().find("p.seq").text();
       		
			location.href="<%=ctxPath%>/account/viewBill.hello2?tabName=" + "${paraMap.tabName}" + "&seq=" + seq;
			
       	});
       	
       	
       	/////// 해야 할 일 ==> 체크박스 선택 후 [승인요청] [국세청전송] [엑셀다운로드] 버튼 클릭 시 이벤트 처리(체크된 것이 없을 경우 alert)
       	/////// 다음에는  ==> 한 행 클릭 시 상세페이지로 이동할 것! (디자인은 기존의 작성페이지 디자인 그대로 하고, 다만 input태그는 활성X) 
       	//         --> 여긴 이벤트 처리할 것 엑셀다운로드, 이메일발송, 승인요청, 국세청전송!(수정이나 삭제가 없음!)
       	
	});
	
	// =============== Function Declaration ===============
	
	function search(){
		
		var frm = document.searchFrm;
		
		var startDate = frm.startDate.value;
		var lastDate = frm.lastDate.value;
		
		if(startDate != ""){
			if(lastDate == ""){
				alert('검색 날짜를 확인하세요');
				return;
			}
		}
		
		if(lastDate != ""){
			if(startDate == ""){
				alert('검색 날짜를 확인하세요');
				return;
			}
		}		
		
		frm.action = "<%=ctxPath%>/account/listBill.hello2";
		
		frm.submit();
		
	}
	
	// 삭제
	function goDelete() {

		var checkCnt = 0;
		var selectSeq = "";
		var flagReturn = false;
		var loginEmpid = "${sessionScope.loginEmp.empid}";
		var ranking = Number("${sessionScope.loginEmp.ranking}");
				
		$(".selectdoc").each(function(index, item) {
			
			if($(this).prop("checked") == true) {
				
				var regEmpid = $(this).parent().parent().find("span.empid").text();
								
				if($(this).parent().parent().find("p.status").text() == "승인요청전" 
						&& (loginEmpid == regEmpid || ranking >= 3)) {
					
					if(checkCnt == 0) 
						selectSeq += $(this).parent().parent().find("p.seq").text().trim();
					
					else
						selectSeq += "," + $(this).parent().parent().find("p.seq").text().trim();
					
					checkCnt++;
				
				}
				
				else if($(this).parent().parent().find("p.status").text() != "승인요청전") {
					alert("이미 승인요청이 완료되었거나 국세청으로 전송된 문서는 삭제가 불가합니다.");
					flagReturn = true;
					return false;
				}
				
				else {
					alert("다른 직원이 작성한 문서는 관리자가 이외에는 삭제가 불가합니다.");
					flagReturn = true;
					return false;
				}
				
			}
						
		});
		
		if(checkCnt == 0 && flagReturn == false) {
			alert("삭제할 문서를 선택해주세요.");
			return;
		}
		
		else if(flagReturn == true)
			return;
		
		else {
			
			$("input[name=seqes]").val(selectSeq);
			
			$("button.btn-del").click();
			
		}
		
	}
	
	// 승인요청
	function goPermission() {
		
		var checkCnt = 0;
		var selectSeq = "";
		var flagReturn = false;
				
		$(".selectdoc").each(function(index, item) {
			
			if($(this).prop("checked") == true) {
				
				if($(this).parent().parent().find("p.status").text() == "승인요청전") {
					
					if(checkCnt == 0) 
						selectSeq += $(this).parent().parent().find("p.seq").text().trim();
					
					else
						selectSeq += "," + $(this).parent().parent().find("p.seq").text().trim();
					
					checkCnt++;
				
				}
				
				else {
					alert("이미 승인요청이 완료되었거나 국세청으로 전송된 문서는 승인요청이 불가합니다.");
					flagReturn = true;
					return false;
				}
				
			}
						
		});
		
		if(checkCnt == 0 && flagReturn == false) {
			alert("승인을 요청할 문서를 선택해주세요.");
			return;
		}
		
		else if(flagReturn == true)
			return;
		
		else {
			var frm = document.submitFrm;
			
			frm.seq.value = selectSeq;
			frm.state.value = "1";
			
			frm.action = "<%=ctxPath%>/account/updateStatus.hello2";
			frm.method = "POST";
			frm.submit();
		}
		
	}
	
	// 국세청전송
	function goHometax() {
		
		var checkCnt = 0;
		var selectSeq = "";
		var flagReturn = false;
		
		$(".selectdoc").each(function(index, item) {
			
			if($(this).prop("checked") == true) {
				
				if($(this).parent().parent().find("p.status").text() != "국세청전송완료") {
					
					if(checkCnt == 0) 
						selectSeq += $(this).parent().parent().find("p.seq").text().trim();
					
					else
						selectSeq += "," + $(this).parent().parent().find("p.seq").text().trim();
					
					checkCnt++;
				
				}
				
				else {
					alert("이미 국세청으로 전송된 문서는 재전송이 불가합니다.");
					flagReturn = true;
					return false;
				}
				
			}
			
		});
		
		if(checkCnt == 0 && flagReturn == false) {
			alert("국세청으로 전송할 문서를 선택해주세요.");
			return;
		}
		
		else if(flagReturn == true)
			return;
		
		else {
			var frm = document.submitFrm;
			
			frm.seq.value = selectSeq;
			frm.state.value = "2";
			
			frm.action = "<%=ctxPath%>/account/updateStatus.hello2";
			frm.method = "POST";
			frm.submit();
		}
		
	}
	
	// 엑셀다운로드
	function downloadExcel() {
		
		var checkCnt = 0;
		var selectSeq = "";
		
		$(".selectdoc").each(function(index, item) {
			
			if($(this).prop("checked") == true) {				
					
				if(checkCnt == 0) 
					selectSeq += $(this).parent().parent().find("p.seq").text().trim();
				
				else
					selectSeq += "," + $(this).parent().parent().find("p.seq").text().trim();
				
				checkCnt++;
			
			}
			
		});
		
		if(checkCnt == 0) {
			alert("엑셀로 다운로드할 문서를 선택해주세요.");
			return;
		}
				
		else {
			var frm = document.submitFrm;
			
			frm.seq.value = selectSeq;
			
			frm.action = "<%=ctxPath%>/account/docExcelDownload.hello2";
			frm.method = "POST";
			frm.submit();
		}
		
	}
	
</script>

<jsp:include page="template_KJH.jsp"/>

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px">
  
   	<!-- Contact Section -->
	<div class="w3-container w3-padding-large" style="margin-bottom: 300px;">
    
		<div class="container" style="margin-top: 70px;">
    	    		
	   		<h4>작성완료 문서</h4>
	   		
	   		<hr>
	   		
	   		<%-- 탭 --%>
			<ul class="nav nav-tabs">
				<li class="nav-item tab" style="margin-right: 0.5px;">
					<a class="nav-link text-secondary" href="<%=ctxPath%>/account/listBill.hello2?tabName=tbl_billtax" id="tbl_billtax">세금계산서</a>
				</li>
				<li class="nav-item tab" style="margin-right: 0.5px;">
					<a class="nav-link text-secondary" href="<%=ctxPath%>/account/listBill.hello2?tabName=tbl_billnotax" id="tbl_billnotax">계산서</a>
				</li>
				<li class="nav-item tab" style="margin-right: 0.5px;">
					<a class="nav-link text-secondary" href="<%=ctxPath%>/account/listBill.hello2?tabName=tbl_transaction" id="tbl_transaction">거래명세서</a>
				</li>
			</ul>
			
			<%-- 승인, 국세청, 엑셀을 위한 히든 폼 --%>
			<form id="submitFrm" name="submitFrm">
   				<input type="hidden" name="seq"/>
   				<input type="hidden" name="state"/>
   				<input type="hidden" name="tabName2" value="${paraMap.tabName}"/>
   			</form>
			
			<form id="searchFrm" name="searchFrm">
		
				<%-- 날짜선택 --%>
				<div class="pt-2 pb-2">	
					<div class="card">
						<div class="card-body">					
							<span style="color: gray;">기간검색 : </span>	
							<input type="text" id="startDate" name="startDate" style="height: 25px; width: 105px; font-size: 11pt;" autocomplete='off'>&ensp;~
							<input type="text" id="lastDate" name="lastDate" style="height: 25px; width: 105px; font-size: 11pt;" autocomplete='off'>	         
							&ensp; 
							<button type="button" onclick="search()" style="border: solid 1px gray; background-color: #ebf0fa; border-radius: 5px; font-size: 10pt; padding: 3px 8px;">조회</button>	
							<input type="hidden" name="tabName" value="${paraMap.tabName}"/>				
						</div>
					</div>
				</div>
			
				<%-- 버튼자리(체크박스 선택시 나타나게!) : 승인요청, 국세청전송, 엑셀다운로드 --%>
				<p class="text-right w-100">
					<button type="button" class="btn btn-danger btn-sm" onclick="goDelete()">삭제</button>
		   			<button type="button" class="btn btn-secondary btn-sm" onclick="goPermission()">승인요청</button>
		   			<c:if test="${paraMap.tabName ne 'tbl_transaction'}">
		   				<button type="button" class="btn btn-dark btn-sm" onclick="goHometax()">국세청전송</button>
		   			</c:if>
		   			<button type="button" class="btn btn-primary btn-sm" onclick="downloadExcel()">엑셀다운로드</button>		   			
		   		</p>
		   						
		   		<div class="mx-auto px-auto" style="width: 100%;">   			
		   			
		   			<%-- 리스트 --%>   			
		   			<table class="w-100" style="font-size: 10pt; font-weight: normal;">
		   				
		   				<thead style="background-color: #ebf0fa; height: 50px; color: gray;">
					
							<tr>
								<th style="width: 10%; text-align: center; font-weight: normal; border-right: solid 1px white;">일련번호</th>
								<th style="width: 10%; text-align: center; font-weight: normal; border-right: solid 1px white;">작성일자</th>
								<th style="width: 15%; text-align: center; font-weight: normal; border-right: solid 1px white;">사업자등록번호</th>
								<th style="width: 15%; text-align: center; font-weight: normal; border-right: solid 1px white;">업체명(상호)</th>
								<th style="width: 10%; text-align: center; font-weight: normal; border-right: solid 1px white;">대표자명</th>
								<th style="width: 10%; text-align: center; font-weight: normal; border-right: solid 1px white;">담당자</th>
								<th style="width: 15%; text-align: center; font-weight: normal; border-right: solid 1px white;">합계금액</th>
								<th style="width: 10%; text-align: center; font-weight: normal; border-right: solid 1px white;">진행현황</th>
								<th style="width: 5%; text-align: center; font-weight: normal; font-size: 8pt;"><label for="selectAll">전체선택</label><input type="checkbox" name="selectAll" id="selectAll"/></th>
							</tr>
					
						</thead>
						
						<tbody>
							
							<c:forEach var="doc" items="${docList}" varStatus="status">
								
								<tr valign="middle" class="doc" style="border: solid 1px #e6e6e6; border-top: none;">
								
									<c:if test="${doc.edit eq '1'}">
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 seq tblue">${doc.seq}</p></td>
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 regdate tblue">${doc.regdate}</p></td>
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 customer_id tblue">${doc.customer_id}</p></td>
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 customer_comp tblue">${doc.customer_comp}</p></td>
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 customer_name tblue">${doc.customer_name}</p></td>
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 empname tblue">${doc.empname}<span class="empid" style="display: none;">${doc.empid}</span></p></td>
										
										<fmt:parseNumber var="totalprice" value="${doc.totalprice}" integerOnly="true" />
										<td class="click" style="text-align: left; border-right: solid 1px #e6e6e6;"><p class="my-2 totalprice pl-4 tblue"><fmt:formatNumber value="${totalprice}" type="currency"/>&nbsp;<span class="tblue" style="background-color: #ffff99;">[수정]</span></p></td>
										
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;">
											<c:choose>
												<c:when test="${doc.status == 0}"><p class="my-2 status tblue">승인요청전</p></c:when>
												<c:when test="${doc.status == 1}"><p class="my-2 status tblue">승인요청완료</p></c:when>
												<c:otherwise><p class="my-2 status tblue">국세청전송완료</p></c:otherwise>
											</c:choose>
										</td>
										<td style="text-align: center;"><input type="checkbox" name="selectdoc" class="selectdoc"/></td>
									</c:if>
									
									<c:if test="${doc.edit ne '1'}">
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 seq">${doc.seq}</p></td>
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 regdate">${doc.regdate}</p></td>
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 customer_id">${doc.customer_id}</p></td>
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 customer_comp">${doc.customer_comp}</p></td>
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 customer_name">${doc.customer_name}</p></td>
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;"><p class="my-2 empname">${doc.empname}<span class="empid" style="display: none;">${doc.empid}</span></p></td>
										
										<fmt:parseNumber var="totalprice" value="${doc.totalprice}" integerOnly="true" />
										<td class="click" style="text-align: left; border-right: solid 1px #e6e6e6;"><p class="my-2 totalprice pl-4"><fmt:formatNumber value="${totalprice}" type="currency"/></p></td>
										
										<td class="click" style="text-align: center; border-right: solid 1px #e6e6e6;">
											<c:choose>
												<c:when test="${doc.status == 0}"><p class="my-2 status">승인요청전</p></c:when>
												<c:when test="${doc.status == 1}"><p class="my-2 status">승인요청완료</p></c:when>
												<c:otherwise><p class="my-2 status">국세청전송완료</p></c:otherwise>
											</c:choose>
										</td>
										<td style="text-align: center;"><input type="checkbox" name="selectdoc" class="selectdoc"/></td>
									</c:if>
									
								</tr>
								
							</c:forEach>
							
						</tbody>
		   				
		   			</table>
		   			
				</div>
			
				<%-- 페이징바 --%>
				<nav class="nav-light">
					<ul class="pagination justify-content-center" style="margin:20px 0;">
						${pageBar}
					</ul>
				</nav>
			
				<%-- 검색창 --%>			
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
   			
   			<button type="button" class="btn btn-del" data-toggle="modal" data-target="#deleteModal" data-backdrop="static" style="display: none;"></button>	   
   			
   			<%-- 삭제모달 --%>
	        <div class="modal fade" id="deleteModal">
				  <div class="modal-dialog modal-dialog-scrollable modal modal-dialog-centered">
				  
				    <div class="modal-content">			      
				      	
				      	<!-- Modal header -->
				      <div class="modal-header text-center">
				        	<h6 class="modal-title text-center mx-auto px-auto">선택하신 문서를 정말로 삭제하시겠습니까?</h6>
				      </div>
				      	
				     <!-- Modal body -->
				      <div class="modal-body text-center">
		
					      	<form name="deleteFrm" method="POST" action="<%=ctxPath%>/account/deleteDoc.hello2">
					      		<input type="hidden" value="" name="seqes" required>
					      		<input type="hidden" value="${paraMap.tabName}" name="tabName3">				      		
					      		<button type="submit" class="btn btn-sm btn-danger deletethis mx-1">삭제</button>
					      		<button type="button" class="btn btn-sm btn-success thisclose mx-1" data-dismiss="modal">취소</button>
							</form>
													
						</div>
				     				      
				    </div>
				  </div>
			</div>
   			   			
		</div>
   		    			    	
	</div>
    
</div>

<!-- !!!!!PAGE CONTENT END!!!!!! -->