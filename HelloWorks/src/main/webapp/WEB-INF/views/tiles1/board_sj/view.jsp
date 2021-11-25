<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% String ctxPath = request.getContextPath(); %>

   
<style type="text/css">
	body,h1,h2,h3,h4,h5,h6 {font-family: Verdana, sans-serif;}
	
	form{
		font-family: Verdana, sans-serif;
	
	}
	
	th{
		color:#595959;
		width: 18%;
		font-weight: normal;
	}
	
	table,tr,td{
		border:none;
		font-size: 14pt;
		color:#595959;
	}
	
</style>

<script type="text/javascript">

	$(document).ready(function(){
	
		goReadComment(); // 페이징처리 안 한 댓글 읽어오기
		
	}); // end of $(document).ready(function(){})---------------

	
	// Function Declartion
	// === 댓글 쓰기 === //
	function goAddWrite() {
		
		var contentVal = $("input#commentContent").val().trim();
		if(contentVal == ""){
			alert="댓글 내용을 입력하세요!";
			return; // 종료
		}
		else{
			// 첨부파일이 있는 댓글쓰기
			goAddWrite_noAttach();	
		}
	}// end of function goAddWrite() {}---------------------------
	
	
	// 첨부파일이 없는 댓글 쓰기
	function goAddWrite_noAttach(){
		
		$.ajax({
			url:"<%= request.getContextPath()%>/addComment.hello2",
			data:{"fk_empno":$("input#fk_empno").val()
				 ,"name":$("input#name").val()
				 ,"content":$("input#commentContent").val()
				 ,"parentSeq":$("input#parentSeq").val()},
		    type:"POST",
		    dataType:"JSON",
		    success:function(json){
		    	
		    	var n = json.n;
		    	
		    	if(n==1){	
		    		goReadComment(); // 페이징처리 안 한 댓글 읽어오기
		    	}
		    	
		    	$("input#commentContent").val("");
		    },
		    error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});

	} // end of function goAddWrite_noAttach(){})--------------------
	
	
	
	// === 페이징처리 안 한 댓글 읽어오기 === //
	function goReadComment() {
		
		$.ajax({
			url:"<%= request.getContextPath()%>/readComment.hello2",
			data:{"parentSeq":"${boardvo.seq}"},
			dataType:"JSON",
			success:function(json){
				var html = "";
				if(json.length > 0){
					$.each(json, function(index, item){
						html += "<tr>";
							html += "<td class='comment' style='width:4%; color:#0070C0; height:20px'><b>"+(index+1)+"</b></td>";
							html += "<td class='comment' style='font-weight:bold; width:10%; height:20px'>"+item.name+"</td>";
							html += "<td class='comment' style='color:#a6a6a6; height:20px' >"+item.regDate+"</td>";
						html += "</tr>";
						html += "<tr>";
							html += "<td colspan='3' style='border-top: none; height:20px'>&emsp;&emsp;"+item.content+"</td>";	
						html += "</tr>";
					});
				}
				else{
					html += "<tr>";
					html += "<td colspan='4' class='comment'>댓글이 없습니다.</td>";
					html += "</tr>";
				}
							
				$("tbody#commentDisplay").html(html);		
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}			
		});
		
		
	}// end of function goReadComment() {})-----------------
	
	
	
</script>

<!-- 좌측 고정 상세메뉴 시작 -->
<nav class="w3-sidebar w3-collapse w3-white " style="margin-top:20px; z-index:0; width:300px;background-color:#f5f5f5; overflow: hidden" id="mySidebar"><br>
  <div class="w3-container" style="background-color:#f5f5f5; margin-top:10px" >
    <a href="#" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu">
      <i class="fa fa-remove"></i>
    </a>
	<br><br>
	
	<button type="button" onclick="location.href='<%= ctxPath %>/boardAdd.hello2'" class="btn" id="btn1" style="background-color:#0070C0; margin-left:14px; font-size:21px; width: 240px; height:63px; color:white">
				<b>글쓰기</b></button>
	<br>
  </div>
  <div class="w3-bar-block" style="background-color:#f5f5f5; height: 100%">
	<div style="margin-left:42px; font-size: 16pt; color:#595959">
	  <br>
	  <a href="<%= ctxPath %>/list.hello2" class="w3-bar-item w3-button">사내공지</a>
  	</div>
  </div>  
</nav>
<!-- 좌측 고정 상세메뉴 끝 -->


<div style="display: flex; margin-top:30px">
<div style="margin:80px 0 800px 243px; padding-left: 3%;">
	
	<c:if test="${not empty requestScope.boardvo}">
		
		<button type="button" class="btn btn-lg mr-3 " onclick="javascript:location.href='<%= request.getContextPath()%>/list.hello2'" style="background-color: white; color:#0070C0; font-size: 18pt; margin-left: 30px">전체목록</button>
		<button type="button" class="btn btn-lg mr-3 " onclick="javascript:location.href='<%= request.getContextPath()%>${requestScope.gobackURL}'" style="background-color: white; color:#0070C0; font-size: 18pt">검색목록</button>
		<button type="button" class="btn btn-lg mr-3 " onclick="javascript:location.href='<%= request.getContextPath()%>/boardEdit.hello2?seq=${requestScope.boardvo.seq}'" style="background-color: white; color:#0070C0; font-size: 18pt">수정</button>
		<button type="button" class="btn btn-lg mr-3 " onclick="javascript:location.href='<%= request.getContextPath()%>/boardDel.hello2?seq=${requestScope.boardvo.seq}'" style="background-color: white; color:#0070C0; font-size: 18pt">삭제</button>

		
		<%-- === #141. 어떤글에 대한 답변글쓰기는 로그인 되어진 회원의 gradelevel 컬럼의 값이 10인 직원들만 답변글쓰기가 가능하다. --%>
		<%-- 
		<br>
		   <span>groupno : ${requestScope.boardvo.groupno}</span>
		<br>
		   <span>depthno : ${requestScope.boardvo.depthno}</span>
		--%>
		<c:if test="${sessionScope.loginuser.gradelevel == 10}">
			<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= request.getContextPath()%>/add.action?fk_seq=${requestScope.boardvo.seq}&groupno=${requestScope.boardvo.groupno}&depthno=${requestScope.boardvo.depthno}&subject=${requestScope.boardvo.subject}'">답변글쓰기</button>
		</c:if>
		
		
		<table style="width: 1100px; margin:20px 0 0 40px" class="table">
			<tr>	
				<td>${requestScope.boardvo.seq}</td>
				<td colspan="4" style="font-size: 18pt; height:37px;">${requestScope.boardvo.subject}</td>
			</tr>
			<tr>	
				<td style="width:11%">작성자</td>
				<td style="width:25%">${requestScope.boardvo.name}</td>
				<td style="width:11%">읽은 사람</td>
				<td style="width:13%">${requestScope.boardvo.readCount}</td>
				<td style="width:42%">${requestScope.boardvo.regDate}</td>
			</tr>	
			<c:if test="${requestScope.boardvo.fileName != null}">
				<tr id="file">	
					<td>첨부파일</td>
					<td colspan="4">
						
							<a href="<%= request.getContextPath()%>/boarddownload.hello2?seq=${requestScope.boardvo.seq}">${requestScope.boardvo.orgFilename}</a>
							(<fmt:formatNumber value="${requestScope.boardvo.fileSize}" pattern="#,###"/>byte)
					</td>
				</tr>
			</c:if>
			<tr>	
				<td colspan="5" style="height:400px; font-size:16pt">
					<br>
				  <p style="word-break: break-all;">${requestScope.boardvo.content}</p>
				  <%-- 
				      style="word-break: break-all; 은 공백없는 긴영문일 경우 width 크기를 뚫고 나오는 것을 막는 것임. 
				           그런데 style="word-break: break-all; 나 style="word-wrap: break-word; 은
				           테이블태그의 <td>태그에는 안되고 <p> 나 <div> 태그안에서 적용되어지므로 <td>태그에서 적용하려면
				      <table>태그속에 style="word-wrap: break-word; table-layout: fixed;" 을 주면 된다.
				 --%>
				</td>
			</tr>
				</table>
		
		
		
		<%-- ==== #94. 댓글 내용 보여주기 ==== --%>
		<div class="board_comment" style="background-color: #f6f8f9"> 
		<table class="table" style="width: 1024px; margin-left:40px">
			<tbody id="commentDisplay" style="text-align:left;"></tbody>
		</table>
		
		
		
			<%-- === #83. 댓글쓰기 폼 추가 === --%>
			<form name="addWriteFrm" id="addWriteFrm" style="margin: 20px 0 0 40px"> 
				<table class="table" style="width: 1500px; background-color: #f6f8f9" >
					<tr style="height: 35px">
					   <td colspan="2"><i class="fa fa-user" style="font-size: 18pt; color:gray"></i>
					   	  <input type="hidden" name="fk_empno" id="fk_empno" value="${sessionScope.loginEmp.empno}" />
					   	  <input type="text" name="name" id="name" style="border:none; background-color: #f6f8f9" value="&nbsp;${sessionScope.loginEmp.empname}" readonly />
					   </td>
					</tr>
					<tr style="height: 35px">
					   <td style="border-top: none; width:77%">
					   	  <input type="text"   name="content" id="commentContent" style="margin-left:37px; border:none; height: 40px;" size="100" placeholder="&nbsp;댓글을 입력하세요." />
					   	  <input type="hidden" name="parentSeq" id="parentSeq" value="${requestScope.boardvo.seq}" readonly />
					   </td>
					   <td style="border-top: none;">
					   	  <button type="button" class="btn btn-lg mr-3 " onclick="goAddWrite()" style="background-color: white; color:#0070C0; font-size: 14pt;">등록</button> 			   	 
					   	  <button type="reset" class="btn btn-lg mr-3 " style="background-color: white; color:#0070C0; font-size: 14pt;">취소</button>
					 </td>
					</tr>
				</table>
			</form>
			</div>
	
		
		
		<br/>
		<div style="margin:10px 0 5px 20px; font-size: 13pt">이전글제목&nbsp;:&nbsp;<span class="move" onclick="javascript:location.href='view.action?seq=${requestScope.boardvo.previousseq}&searchType=${requestScope.searchType}&searchWord=${requestScope.searchWord}&gobackURL=${v_gobackURL}'">${requestScope.boardvo.previoussubject}</span></div>
		<div style="margin:0px 0 5px 20px; font-size: 13pt">다음글제목&nbsp;:&nbsp;<span class="move" onclick="javascript:location.href='view.action?seq=${requestScope.boardvo.nextseq}&searchType=${requestScope.searchType}&searchWord=${requestScope.searchWord}&gobackURL=${v_gobackURL}'">${requestScope.boardvo.nextsubject}</span></div>
	
		
		
		
	</c:if>
	
	<c:if test="${empty requestScope.boardvo}">
		<div style="padding: 50px 0; font-size: 16pt; color: black;">존재하지 않습니다</div>
	</c:if>
	
</div>
</div>	


