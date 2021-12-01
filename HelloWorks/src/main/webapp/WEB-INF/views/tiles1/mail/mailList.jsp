<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

	th {background-color: #b3c6ff;}
	
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
		
		// 메일 선택삭제(휴지통)
		$("button#deleteTrash").click(function(){
			
			var arrMailseq = [];
			
			$("input:checkbox[name=check]").each(function(index,item){ // each 하나하나 알아본다
				var bool = $(this).is(":checked"); // 체크박스의 체크유무 검사
				if(bool == true) {
					// 체크박스에 체크가 되었으면
					arrMailseq.push($(this).val()); // 배열에 넣는다.
					// alert("확인1" + arrMailseq[0]);
				}
			});
			
			var sMailseq = arrMailseq.join();
			// alert("확인2" + sMailseq);
			
			
			var frm = document.trashFrm;
			frm.sMailseq.value = sMailseq;
			
			// alert("확인3" + sMailseq);
			
			frm.method = "POST";
			frm.action = "delete.hello2";
			frm.submit();
			
		});
		
		// 체크박스
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
		
	});// end of $(document).ready(function(){})----------------------
	
	
	// Function Declaration
	function goView(mailseq) {
		
		// === #124. 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후 
		//           사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해 
		//           현재 페이지 주소를 뷰단을 넘겨준다. 
		var frm = document.goViewFrm;   
		frm.mailseq.value = mailseq;
		frm.gobackURL.value = "${requestScope.gobackURL}";
		frm.searchType.value = "${requestScope.paraMap.searchType}";
		frm.searchWord.value = "${requestScope.paraMap.searchWord}";
		
		frm.method = "GET";
		frm.action = "<%= ctxPath%>/getView.hello2";
		frm.submit();
		
	}// end of function goView(seq) {}--------------------------------

	
	function goSearch() {
		var frm = document.searchFrm;
		frm.method = "GET";
		frm.action = "<%= request.getContextPath()%>/mailList.hello2";
		frm.submit();
	}// end of function goSearch() {}---------------------------------
	
</script>

<jsp:include page="template_JDH.jsp"/>

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px" >

   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 300px;">
    
    <div class="container" style="margin-top: 70px;">

	<h2 style="margin-bottom: 30px;">전체메일목록</h2>
	
	<div style="width: 100%;">
		<button type="button" class="btn btn-secondary btn-sm" id="deleteTrash" style="float: left;">삭제</button>
		<%-- 메일검색 폼 추가하기 : 메일제목, 보낸이로 검색을 하도록 한다. --%>
		<form name="searchFrm" style="margin-bottom: 20px; width: 90%; float: left; padding-left: 13% ">
			<select name="searchType" id="searchType" style="height: 26px;">
				<option value="mailsubject">메일제목</option>
				<option value="sendid">보낸이</option>
			</select>
			<input type="text" name="searchWord" id="searchWord" size="50" autocomplete="off" />
			<input type="text" style="display: none;"/> <%-- form 태그내에 input 태그가 오로지 1개 뿐일경우에는 엔터를 했을 경우 검색이 되어지므로 이것을 방지하고자 만든것이다. --%>
			<button type="button" class="btn btn-secondary btn-sm" onclick="goSearch()">검색</button>
		</form>
	</div>
	
	<table style="width: 900px;" class="table table-bordered">
		<thead>
		<tr>
			<th style="width: 5%;"><input type="checkbox" name="allCheck" id="statusAll" value="${mailseq}"  /></th>
			<th style="width: 10%; text-align: center;">발신ID</th>
			<th style="width: 10%; text-align: center;">발신자</th>
			<th style="width: 35%;text-align: center;">제목</th>
			<th style="width: 30%; text-align: center;">날짜</th>
			<th style="width: 10%; text-align: center;">읽음</th>
		</tr>	
		</thead>
		
		<tbody>
		<c:forEach var="mailvo" items="${requestScope.mailList}">
			<tr>
				<td style="width: 30px; height: 20px;">
					<input type="checkbox" name="check" id="statusEach" value="${mailvo.mailseq}"/>
				</td>
				<td align="center">${mailvo.sendid}</td>
				<td align="center">${mailvo.sendname}</td>
					<!-- <input type="hidden" name="sMailseq" /> -->
			<%-- === 메일리스트 시작 === --%>
			<%-- 첨부파일이 없는 경우 시작 --%>	
			<c:if test="${empty mailvo.mailfilename}">
			<td>
				<%-- 답변메일이 아닌 원메일인 경우 --%>
				<c:if test="${mailvo.fk_mailseq == '-9999'}">
					<span class="subject" onclick="goView('${mailvo.mailseq}')">${mailvo.mailsubject}</span> 
				</c:if>
				
				<%-- 답변메일인 경우 --%>
				<c:if test="${mailvo.fk_mailseq != '-9999'}">
					  <span class="subject" onclick="goView('${mailvo.mailseq}')"><span style="color: red; font-style: italic; padding-left: 10px;">└Re&nbsp;</span>${mailvo.mailsubject}</span> 
				</c:if>
			</td>	
			</c:if>				
			<%-- 첨부파일이 없는 경우 끝 --%>	
			
			<!-- 첨부파일이 있는 경우 시작 -->	
			
			<c:if test="${not empty mailvo.mailfilename}">
			<td>	
				<!-- 답변메일이 아닌 원 메일인 경우 -->
				<c:if test="${mailvo.fk_mailseq == '-9999'}">
					<span class="subject" onclick="goView('${mailvo.mailseq}')">${mailvo.mailsubject}</span> &nbsp;<i class="far fa-save"></i>
				</c:if>
				
				<!-- 답변메일인 경우 -->
				<c:if test="${mailvo.fk_mailseq != '-9999'}">
					  <span class="subject" onclick="goView('${mailvo.mailseq}')"><span style="color: red; font-style: italic; padding-left: 10px;">└Re&nbsp;</span>${mailvo.mailsubject}</span> &nbsp;<i class="far fa-save"></i> 
				</c:if>
			</td>	
			</c:if>	
			<!-- 첨부파일이 있는 경우 끝 -->
				
				<td align="center">${mailvo.mailregdate}</td>
				
				<!-- 메일 조회가 없으면 -->
				<c:if test="${mailvo.mailreadstatus == '0' }">
					<td align="center"><i class="fas fa-envelope"></i></td>
				</c:if>
				<c:if test="${mailvo.mailreadstatus == '1' }">
					<td align="center"><i class="fas fa-envelope-open-text"></i></td>
				</c:if>
				
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	
	<%-- 페이지바 보여주기 --%>
	<div align="center" style="width: 70%; border: solid 0px gray; margin: 20px auto;">
		${requestScope.pageBar}
	</div>
	
	<form name="goViewFrm">
		<input type="hidden" name="mailseq" />
		<input type="hidden" name="gobackURL" />
		<input type="hidden" name="searchType" />
		<input type="hidden" name="searchWord" />
	</form>
	
	<form name="trashFrm">
		
		<input type="hidden" name="sMailseq">
		<input type="hidden" name="arrMailseq">
	</form>	  
	
</div>
</div>	
	
	
	
	
	
	
	
	
	
	
	
	
    