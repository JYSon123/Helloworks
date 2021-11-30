<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

	th {background-color: #DDD;}
	
	.subjectStyle {font-weight: bold;
	               color: navy;
	               cursor: pointer;}
	               
	.changeCSSname {color: black;
                   background-color: white;
   	}               

</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		$("span.subject").bind("mouseover", function(event){
			var $target = $(event.target);
			$target.addClass("subjectStyle");
		});
				
		$("span.subject").bind("mouseout", function(event){
			var $target = $(event.target);
			$target.removeClass("subjectStyle");
		});
		
		$("input#searchWord").keyup(function(event){
			if(event.keyCode == 13) {
				// 엔터를 했을 경우
				goSearch();
			}
		});
		
		// 검색시 검색조건 및 검색어 값 유지시키기
		if( ${not empty requestScope.paraMap} ) {
			$("select#searchType").val("${requestScope.paraMap.searchType}");
			$("input#searchWord").val("${requestScope.paraMap.searchWord}");
		}
		
		
		// 자동 검색어 입력
		$(document).on("click",".result",function(){
			var word = $(this).text();
			$("input#searchWord").val(word); // 텍스트박스에 검색된 결과의 문자열을 입력해준다. 
			$("div#displayList").hide();
			goSearch();
		});
		
		// 계좌 삭제
		$("button#deleteAccount").click(function(){
			
			var arrAccountseq = [];
			
			$("input:checkbox[name=check]").each(function(index,item){ // each 하나하나 알아본다
				var bool = $(this).is(":checked"); // 체크박스의 체크유무 검사
				if(bool == true) {
					// 체크박스에 체크가 되었으면
					arrAccountseq.push($(this).val()); // 배열에 넣는다.
					// alert("확인1" + arrAccountseq[0]);
				}
			});
			
			var sAccountseq = arrAccountseq.join();
			 // alert("확인2" + sAccountseq);
			
			
			var frm = document.selectFrm;
			frm.sAccountseq.value = sAccountseq;
			
			 // alert("확인3" + sAccountseq);
			
			frm.method = "POST";
			frm.action = "deleteAccount.hello2";
			frm.submit();
			
		});
		
		// 전체 선택
		$("input:checkbox[id=statusAll]").click(function(){
			var bool = $(this).prop("checked");
			
			$("input:checkbox[name=check]").prop("checked", bool);
			
			if(bool){
        		// 전체선택/전체해제 체크박스에 체크를 한 경우
        		$("input#statusEach").addClass("changeCSSname");
        		// 아이디가 firstDiv 내에 존재하는 모든 label 태그에 CSS changeCSSname 클래스를 적용시킨다.
        	}
        	else {
        		// 전체선택/전체해제 체크박스에 체크를 해제한 경우
        		$("input#statusEach").removeClass("changeCSSname");
        		// 아이디가 firstDiv 내에 존재하는 모든 label 태그에 CSS changeCSSname 클래스를 적용해제시킨다.
        	}
			
		});
		
		$("tr.selectemp").on("click",function(){
			
			// 변수에 해당 tr에 있는 아이디값, 이름 담기 ==> 아이디랑 이름에 클래스를 줬어야 함 $(this).find(".클래스명").text()
			// 직원 목록 모달을 끈다 버튼.click()
			// 변수에 값들을 계좌등록 모달에 있는 input태그의 val()에 넣어준다
			// 계좌등록 모달을 켜는 버튼.click()
			
			var deptname = $(this).find(".deptname").text();
			var empid = $(this).find(".empid").text();
			var empname = $(this).find(".empname").text();
			
			$("button.thisclose").click();
			
			$("input#deptname").val(deptname);
			$("input#empid").val(empid);
			$("input#empname").val(empname);
			
			$("button#btn_addAccountModal2").click();
			
		});
		
		$("button.btn_editAccountModal").on("click",function(){
			// alert("확인용");
			var editaccountempname = $(this).parent().parent().find(".accountempname").text();
			var editaccountbank = $(this).parent().parent().find(".accountbank").text();
			var editaccountnumber = $(this).parent().parent().find(".accountnumber").text();
			var editaccountempid = $(this).parent().parent().find(".accountempid").text();
			
			
			$("input#editaccountempname").val(editaccountempname);
			$("select#editaccountbank").val(editaccountbank);
			$("input#editaccountnumber").val(editaccountnumber);
			$("input#editaccountempid").val(editaccountempid);
			
			$("button#btn_editAccountModal2").click();
			
		});
		
		
		
	});// end of $(document).ready(function(){})----------------------
	
	
	// Function Declaration
	

	
	function goSearch() {
		var frm = document.searchFrm;
		frm.method = "GET";
		frm.action = "<%= request.getContextPath()%>/empAccountList.hello2";
		frm.submit();
		
	}// end of function goSearch() {}---------------------------------
	
	function addAccount() {
		
		var frm = document.accountAddModalFrm;
		
		if(frm.accountnumber.value.trim() == "") {
			alert("계좌번호를 입력하세요");
			return;
		}
		if(typeof(Number(frm.accountnumber.value.split("-").join(""))) == NaN) {
			alert("숫자만 입력하세요");
			frm.accountnumber.value = ""
			return;
		}
		
		frm.action = "<%= request.getContextPath()%>/addAccount.hello2";
		frm.method = "POST";
		frm.submit();
		
	}
	
	function editAccount() {
		
		var frm = document.accountEditModalFrm;
		
		if(frm.editaccountnumber.value.trim() == "") {
			alert("계좌번호를 입력하세요");
			return;
		}
		if(typeof(Number(frm.editaccountnumber.value.split("-").join(""))) == NaN) {
			alert("숫자만 입력하세요");
			frm.editaccountnumber.value = ""
			return;
		}
		
		frm.action = "<%= request.getContextPath()%>/editAccount.hello2";
		frm.method = "POST";
		frm.submit();
	
	}
	
	
</script>

<jsp:include page="templateAccount_JDH.jsp"/>

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px" >

   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 300px;">
    
    <div class="container" style="margin-top: 70px; margin-left: 0px;">

	<h2 style="margin-bottom: 30px;">직원계좌</h2>
	
	<div>
	<div class="mb-2 w-100">
	<button type="button" class="btn btn-secondary btn-sm mr-2" id="deleteAccount" >삭제</button>
	
	<button type="button" class="btn btn-secondary btn-sm mr-2" id="btn_addAccountModal" data-toggle="modal" data-target="#addAccountModal" data-backdrop="static" >계좌추가</button>
	
	
	
	<div style="width: 31%; float: right;">
	<%-- 아이디, 이름으로 검색 --%>
	<form name="searchFrm">
		<select name="searchType" id="searchType" style="height: 26px;">
			<option value="accountempid">아이디</option>
			<option value="empname">이름</option>
		</select>
		<input type="text" name="searchWord" id="searchWord" size="12%;" autocomplete="off" />
		<input type="text" style="display: none;"/> <%-- form 태그내에 input 태그가 오로지 1개 뿐일경우에는 엔터를 했을 경우 검색이 되어지므로 이것을 방지하고자 만든것이다. --%>
		<button type="button" class="btn btn-secondary btn-sm" onclick="goSearch()">검색</button>
	</form></div>
	</div>
		<table style="width: 100%;" class="table table-bordered">
		<thead>
		<tr>
			<th style="width: 3%;"><input type="checkbox" name="allCheck" id="statusAll" value="${accountseq}"  /></th>
			<th style="width: 10%; text-align: center;">이 름</th>
			<th style="width: 10%; text-align: center;">아이디</th>
			<th style="width: 12%;text-align: center;">은행명</th>
			<th style="width: 25%; text-align: center;">계좌번호</th>
			<th style="width: 30%; text-align: center;">등록일</th>
			<th style="width: 10%; text-align: center;"></th>
		</tr>	
		</thead>
		
		<tbody>
		<c:forEach var="AccountingVO_JDH" items="${requestScope.accountingList}">
			<tr>
				<td style="width: 30px;">
					<input type="checkbox" name="check" id="statusEach" value="${AccountingVO_JDH.accountseq}"/>
				</td>
				<td align="center" class="accountempid">${AccountingVO_JDH.accountempid}</td>
				<td align="center" class="accountempname">${AccountingVO_JDH.empname}</td>
					<!-- <input type="hidden" name="sMailseq" /> -->
				<td align="center" class="accountbank">${AccountingVO_JDH.accountbank}</td>
				<td align="center" class="accountnumber">${AccountingVO_JDH.accountnumber}</td>
				<td align="center" class="accountregdate">${AccountingVO_JDH.accountregdate}</td>
				<td style="text-align: center;"><button type="button" class="btn btn-secondary btn-sm btn_editAccountModal">수정</button></td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	
		<%-- 페이지바 보여주기 --%>
		<div align="center" style="width: 70%; border: solid 0px gray; margin: 20px auto;">
			${requestScope.pageBar}
		</div>
	</div>
	
	<!-- 모달 시작 -->
	<!-- 계정 추가 -->
	<div class="modal fade" id="addAccountModal">
		<div class="modal-dialog modal-dialog-scrollable modal modal-dialog-centered">
					  
			<div class="modal-content">			      
		      	
		      	<!-- Modal header -->
		      <div class="modal-header">			        	
		        	<h5 class="modal-title">계좌등록</h5>
		        	<button type="button" class="close thisclose" data-dismiss="modal">&times;</button>
		      </div>
		      	
		     <!-- Modal body -->
		      <div class="modal-body text-center px-auto">
		      		<table style="width: 100%; font-size: 8.5pt; " class="table table-bordered mx-auto table-hover" >
				<thead>
				<tr>
					<th style="width: 15%; text-align: center;">부서명</th>
					<th style="width: 15%; text-align: center;">아이디</th>
					<th style="width: 15%; text-align: center;">이름</th>
					<th style="width: 17%; text-align: center;">은행</th>
					<th style="text-align: center;">계좌번호</th>
				</tr>	
				</thead>
				
				<tbody>
				<c:forEach var="empMap" items="${requestScope.empList}">
					<tr class="selectemp">
						<td id="deptname" align="center" class="deptname">${empMap.deptname}</td>
						<td id="empid" align="center" class="empid">${empMap.empid}</td>
						<td id="empname" align="center" class="empname">${empMap.empname}</td>
						<td id="accountbank" align="center">${empMap.accountbank}</td>
						<td id="accountnumber" align="center">${empMap.accountnumber}</td>
					</tr>
				</c:forEach>	
				</tbody>
			</table>
		    </div>							
			</div>
		</div>
	</div>
	<!-- 계좌 추가 모달 2  -->
	<div class="modal fade" id="addAccountModal2">
		<div class="modal-dialog modal-dialog-scrollable modal modal-dialog-centered">
					  
			<div class="modal-content">			      
		      	
		      	<!-- Modal header -->
		      <div class="modal-header">			        	
		        	<h5 class="modal-title">계좌 추가</h5>
		        	<button type="button" class="close thisclose" data-dismiss="modal">&times;</button>
		      </div>
		      	
		     <!-- Modal body -->
		      <form name="accountAddModalFrm">		      		
						<label class="col-4">부서</label>
						<input type="text" name="" id="deptname" readonly="readonly">
						<label class="col-4">아이디</label>
						<input type="text" name="accountempid" id="empid" readonly="readonly">
						<label class="col-4">이름</label>
						<input type="text" name="" id="empname" readonly="readonly">
						<br/>
						<label class="col-4">은행명</label>
						<select name="accountbank">
							<option>국민은행</option>
							<option>신한은행</option>
							<option>하나은행</option>
							<option>IBK은행</option>
							<option>농협은행</option>
							<option>우체국</option>
							<option>SC제일은행</option>
							<option>새마을은행</option>
							<option>카카오뱅크</option>
						</select>						
						<br/>
						<label class="col-4">계좌번호</label>
						<input type="text" name="accountnumber" required="required">
						
						
						<hr>					
			      	
			      		<button type="button" class="btn btn-sm btn-success update mx-1" onclick="addAccount()">저장</button>
			      		<button type="button" class="btn btn-sm btn-success thisclose mx-1" data-dismiss="modal">취소</button>
					</form>				
				</div>							
			</div>
		</div>
	</div>		
	
	<!-- 수정모달 -->
	<div class="modal fade" id="editAccountModal2">
		<div class="modal-dialog modal-dialog-scrollable modal modal-dialog-centered">
					  
			<div class="modal-content">			      
		      	
		      	<!-- Modal header -->
		      <div class="modal-header">			        	
		        	<h5 class="modal-title">계좌수정</h5>
		        	<button type="button" class="close thisclose" data-dismiss="modal">&times;</button>
		      </div>
		      	
		     <!-- Modal body -->
		      		<form name="accountEditModalFrm">
						<label class="col-4">이름</label>
						<input type="text" name="editaccountempname" id="editaccountempname" readonly="readonly">
						<input type="hidden" name="editaccountempid" id="editaccountempid" />
						<br/>
						<label class="col-4">은행명</label>
						<select name="editaccountbank">
							<option>국민은행</option>
							<option>신한은행</option>
							<option>하나은행</option>
							<option>IBK은행</option>
							<option>농협은행</option>
							<option>우체국</option>
							<option>SC제일은행</option>
							<option>새마을은행</option>
							<option>카카오뱅크</option>
						</select>						
						<br/>
						<label class="col-4">계좌번호</label>
						<input type="text" name="editaccountnumber"  id="editaccountnumber" required="required">
						
						<br>
				      	<button type="button" class="btn btn-sm btn-success update mx-1" onclick="editAccount()">수정</button>
				      	<button type="button" class="btn btn-sm btn-success thisclose mx-1" data-dismiss="modal">취소</button>
				      	
					</form>
										
			</div>
		</div>
	</div>		


	
	
	<span style="display: none;" ><button type="button" id="btn_addAccountModal2" data-toggle="modal" data-target="#addAccountModal2" data-backdrop="static">계좌추가2</button></span>
	<span style="display: none;" ><button type="button" id="btn_editAccountModal2" data-toggle="modal" data-target="#editAccountModal2" data-backdrop="static">계좌수정2</button></span>

	
	<form name="goViewFrm">
		<input type="hidden" name="accountseq" />
		<input type="hidden" name="gobackURL" />
		<input type="hidden" name="searchType" />
		<input type="hidden" name="searchWord" />
	</form>
	
	<form name="selectFrm">
		
		<input type="hidden" name="sAccountseq">
		<input type="hidden" name="arrAccountseq">
	</form>	  
	
</div>
</div>	
	
	
	
	
	
	
	
	
	
	
	
	
    