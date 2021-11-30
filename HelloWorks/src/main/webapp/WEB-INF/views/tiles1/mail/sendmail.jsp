<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

	th {background-color: #DDD;}
	
	.subjectStyle {font-weight: bold;
	               color: navy;
	               cursor: pointer;}

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
		
		
		<%-- === #113. 검색어 입력시 자동글 완성하기 8 === --%>
		$(document).on("click",".result",function(){
			var word = $(this).text();
			$("input#searchWord").val(word); // 텍스트박스에 검색된 결과의 문자열을 입력해준다. 
			$("div#displayList").hide();
			goSearch();
		});
		
		
	});// end of $(document).ready(function(){})----------------------
	
	
	// Function Declaration
	function goView(mailseq) {
		
		// === #124. 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후 
		//           사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해 
		//           현재 페이지 주소를 뷰단을 넘겨준다. 
		var frm = document.goViewFrm;   
		frm.seq.value = seq;
		frm.gobackURL.value = "${requestScope.gobackURL}";
		frm.searchType.value = "${requestScope.paraMap.searchType}";
		frm.searchWord.value = "${requestScope.paraMap.searchWord}";
		
		frm.method = "GET";
		frm.action = "<%= ctxPath%>/mailView.hello2";
		frm.submit();
		
	}// end of function goView(seq) {}--------------------------------

	
	function goSearch() {
		var frm = document.searchFrm;
		frm.method = "GET";
		frm.action = "<%= request.getContextPath()%>/sendmailList.hello2";
		frm.submit();
	}// end of function goSearch() {}---------------------------------
	
</script>

<div style="display: flex;">
<div style="margin: auto; padding-left: 3%;">

	<h2 style="margin-bottom: 30px;">전체메일목록</h2>
	
	<table style="width: 1024px" class="table table-bordered">
		<thead>
		<tr>
			<th style="width: 70px; text-align: center;">발신인</th>
			<th style="width: 360px;text-align: center;">제목</th>
			<th style="width: 150px; text-align: center;">날짜</th>
			<th style="width: 70px; text-align: center;">용량</th>
		</tr>	
		</thead>
		
		<tbody>
		<c:forEach var="mailvo" items="${requestScope.mailList}">
			<tr>
				<td align="center">${mailvo.mailseq}</td>
				<td align="left">
			
				<%-- === 메일리스트 시작 === --%>
			<%-- 첨부파일이 없는 경우 시작 --%>	
			<c:if test="${empty mailvo.mailfilename}">
				<%-- 답변메일이 아닌 원메일인 경우 --%>
				<c:if test="${mailvo.fk_mailseq == '-9999'}">
					  <span class="subject" onclick="goView('${mailvo.mailseq}')">${mailvo.mailsubject}</span> 
				</c:if>
				
				<%-- 답변메일인 경우 --%>
				<c:if test="${mailvo.fk_mailseq != '-9999'}">
					  <span class="subject" onclick="goView('${mailvo.mailseq}')"><span style="color: red; font-style: italic; padding-left: 10px;">└Re&nbsp;</span>${mailvo.mailsubject}</span> 
				</c:if>	
			</c:if>	
			<%-- 첨부파일이 없는 경우 끝 --%>	
			
			<%-- 첨부파일이 있는 경우 시작 --%>	
			<c:if test="${not empty mailvo.mailfilename}">
				<%-- 답변메일이 아닌 원 메일인 경우 --%>
				<c:if test="${mailvo.fk_mailseq == '-9999'}">
					<span class="subject" onclick="goView('${mailvo.mailseq}')">${mailvo.mailsubject}</span> &nbsp;<i class="far fa-save"></i> 
				</c:if>
				
				<%-- 답변메일인 경우 --%>
				<c:if test="${mailvo.fk_mailseq != '-9999'}">
					  <span class="subject" onclick="goView('${mailvo.mailseq}')"><span style="color: red; font-style: italic; padding-left: 10px;">└Re&nbsp;</span>${mailvo.mailsubject}</span> &nbsp;<i class="far fa-save"></i> 
				</c:if>	
			</c:if>	
			<%-- 첨부파일이 있는 경우 끝 --%>
				
				<%-- === 파일첨부가 있는 메일리스트 끝 === --%>   
				   
				</td>
				<td align="center">${mailvo.sendid}</td>
				<td align="center">${mailvo.regdate}</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	
	<%-- 페이지바 보여주기 --%>
	<div align="center" style="width: 70%; border: solid 0px gray; margin: 20px auto;">
		${requestScope.pageBar}
	</div>
	
	<%-- 메일검색 폼 추가하기 : 메일제목, 보낸이로 검색을 하도록 한다. --%>
	<form name="searchFrm" style="margin-top: 20px;">
		<select name="searchType" id="searchType" style="height: 26px;">
			<option value="mailsubject">메일제목</option>
			<option value="sendid">보낸이</option>
		</select>
		<input type="text" name="searchWord" id="searchWord" size="110" autocomplete="off" />
		<input type="text" style="display: none;"/> <%-- form 태그내에 input 태그가 오로지 1개 뿐일경우에는 엔터를 했을 경우 검색이 되어지므로 이것을 방지하고자 만든것이다. --%>
		<button type="button" class="btn btn-secondary btn-sm" onclick="goSearch()">검색</button>
	</form>
	
	
	<form name="goViewFrm">
		<input type="hidden" name="mailseq" />
		<input type="hidden" name="gobackURL" />
		<input type="hidden" name="searchType" />
		<input type="hidden" name="searchWord" />
	</form>	  
	
</div>
</div>	
	
	
	
	
	
	
	
	
	
	
	
	
    