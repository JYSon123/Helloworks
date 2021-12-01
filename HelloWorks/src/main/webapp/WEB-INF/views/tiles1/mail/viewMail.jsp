<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
    
<style type="text/css">

	.move {cursor: pointer; color: navy;}
	.moveColor {color: #660029; font-weight: bold; background-color: #ffffe6;}

    td.comment {text-align: center;}
</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		$("span.move").hover(function(){
			                   $(this).addClass("moveColor");
		                     }
		                   , function(){
		                	   $(this).removeClass("moveColor");
		                   });
		
	});// end of $(document).ready(function(){})------------------------------
	
	
	// Function Declration
	
</script>    

<jsp:include page="template_JDH.jsp"/>

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px" >

   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 300px;">
    
    <div class="container" style="margin-top: 70px;">
	<h2 style="margin-bottom: 30px; margin-top: 8%">메일내용보기</h2>
	
	<c:if test="${not empty requestScope.mailvo}">
		<table style="width: 1024px" class="table table-bordered">
			<input type="hidden" value="${requestScope.mailvo.mailseq}"/>
			
			<tr>
				<th style="width: 10%;">보낸이</th>
				<td><span id="sendid">${mailvo.sendid}</span>[${mailvo.sendname}]</td>
			</tr>
			<tr>
				<th style="width: 10%;">받는이</th>
				<td><span id="recid">${mailvo.recid}</span> [${mailvo.recname}] </td>
			</tr>
			<tr>	
				<th style="width: 10%;">제목</th>
				<td>${requestScope.mailvo.mailsubject}</td>
			</tr>
			<tr style="height: 500px;">	
				<th style="width: 10%;">내용</th>
				<td>
				  <p style="word-break: break-all;">${mailvo.mailcontent}</p>
				  <%-- 
				      style="word-break: break-all; 은 공백없는 긴영문일 경우 width 크기를 뚫고 나오는 것을 막는 것임. 
				           그런데 style="word-break: break-all; 나 style="word-wrap: break-word; 은
				           테이블태그의 <td>태그에는 안되고 <p> 나 <div> 태그안에서 적용되어지므로 <td>태그에서 적용하려면
				      <table>태그속에 style="word-wrap: break-word; table-layout: fixed;" 을 주면 된다.
				 --%>
				</td>
			</tr>
			
			<tr>	
				<th style="width: 10%;">날짜</th>
				<td>${requestScope.mailvo.mailregdate}</td>
			</tr>
			
			<!-- === #162. 첨부파일 이름 및 파일크기를 보여주고 첨부파일을 다운로드 되도록 만들기 -->
			<tr>	
				<th style="width: 10%;">첨부파일</th>
				<td>
					<c:if test="${sessionScope.loginEmp != null}">
						<a href="<%= request.getContextPath()%>/download.hello2?mailseq=${requestScope.mailvo.mailseq}">${requestScope.mailvo.mailorgfilename}</a>
					</c:if>
					<c:if test="${sessionScope.loginEmp == null}">
						${requestScope.mailvo.mailorgfilename}
					</c:if>
				</td>				
			</tr>
			<tr>	
				<th style="width: 10%;">파일크기(bytes)</th>
				<td><fmt:formatNumber value="${requestScope.mailvo.mailfilesize}" pattern="#,###"/></td>
			</tr>
		</table>
		
		<br/>
		<%-- <span>searchType : ${requestScope.searchType}</span> 
		<br>
		<span>searchWord : ${requestScope.searchWord}</span> --%>
		
		<br/>
		
		<c:set var="v_gobackURL" value='${ fn:replace(requestScope.gobackURL, "&", " ")}' />
		
		<%-- <span>gobackURL : ${v_gobackURL}</span> --%>
		
		<div style="margin-bottom: 1%;">이전글제목&nbsp;:&nbsp;<span class="move" onclick="javascript:location.href='getView.hello2?mailseq=${requestScope.mailvo.previousseq}&searchType=${requestScope.searchType}&searchWord=${requestScope.searchWord}&gobackURL=${v_gobackURL}'">${requestScope.mailvo.previoussubject}</span></div>
		<div style="margin-bottom: 1%;">다음글제목&nbsp;:&nbsp;<span class="move" onclick="javascript:location.href='getView.hello2?mailseq=${requestScope.mailvo.nextseq}&searchType=${requestScope.searchType}&searchWord=${requestScope.searchWord}&gobackURL=${v_gobackURL}'">${requestScope.mailvo.nextsubject}</span></div>
		
		<br/>
		
		<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= request.getContextPath()%>/mailList.hello2'">전체목록보기</button>
		
		
		<%-- 페이징 처리되어진 후 특정 s글제목을 클릭하여 상세내용을 본 이후 
                            사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해 
                            현재 페이지 주소를 뷰단을 넘겨준다. --%>
		<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= request.getContextPath()%>${requestScope.gobackURL}'">검색된결과목록보기</button>
		
		<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= request.getContextPath()%>/send.hello2?fk_mailseq=${requestScope.mailvo.mailseq}&mailsubject=${mailvo.mailsubject}&sendid=${mailvo.sendid}'">답장 보내기</button>
		<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= request.getContextPath()%>/delete.hello2?seq=${requestScope.mailvo.mailseq}'">메일 삭제하기</button>
		
		
		 
		<%-- 
		<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= request.getContextPath()%>/send.action?fk_seq=${requestScope.mailvo.mailseq}&subject=${requestScope.mailvo.mailsubject} '">답변메일쓰기</button>
		 --%>
		
		
	</c:if>
	
	<c:if test="${empty requestScope.mailvo}">
		<div style="padding: 50px 0; font-size: 16pt; color: red;">존재하지 않습니다</div>
	</c:if>
	
</div>
</div>	



