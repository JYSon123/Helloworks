<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %> 

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
      
	   
	      $("span.subject").bind("mouseover", function(event){ // 마우스가 올라가면
	         var $target = $(event.target);
	         $target.addClass("subjectStyle");
	      });
	      
	      $("span.subject").bind("mouseout", function(){ // 마우스가 빠지면
	         var $target = $(event.target);
	         $target.removeClass("subjectStyle");
	      });
	            
	      
	      $("input#searchWord").keyup(function(event){
	         if(event.keyCode == 13){
	            // 엔터를 했을 경우
	            goSearch();
	         }
	      });
	      
	      
	         $("select#searchType").val("${requestScope.paraMap.searchType}");
	         $("select#searchWord").val("${requestScope.paraMap.searchWord}");         

      	
   });// end of $(document).ready(function(){})--------------------------

   
   function button(){

	      var frm = document.searchFrm;
	      frm.method = "GET";
	      frm.action = "<%= request.getContextPath()%>/myDocumentlist.hello2";
	      frm.submit();
	      
	   }// end of function change(){}-------------------
   
	   
	function goView(doument_seq){
		   
      <%--   
         location.href="<%= ctxPath%>/view.action?seq="+seq;
      --%>   
      
      // === #124. 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후
      //            사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해
      //           현재 페이지 주소를 뷰단으로 넘겨준다.
      var frm = document.goViewFrm;  // goViewFrm 밑에 있는 폼
      frm.doument_seq.value = doument_seq;
      frm.gobackURL.value = "${requestScope.gobackURL}"; // 자바스크립트라서 ""
      frm.searchType.value = "${requestScope.paraMap.searchType}";
      frm.searchWord.value = "${requestScope.paraMap.searchWord}";
      
      frm.method = "GET";
      frm.action = "<%= ctxPath%>/viewMyDocument.hello2";
      frm.submit();
      
   }// end of function goView(seq){}------------------
  
   
   
   function result(){
	   
	   /* $("select#searchType").val(); */
	   
	   
	      var frm = document.changeResult;  // goViewFrm 밑에 있는 폼
	      frm.doument_seq.value = "${documentvo.doument_seq}";
	      frm.change.value = $("select[name=change]").val();
	      frm.status1.value = "${documentvo.status}";
	      frm.breakstart.value= "${documentvo.breakstart}";
	      frm.breakend.value= "${documentvo.breakend}";
	      frm.breakkind.value= "${documentvo.breakkind}";
	      
	      
	      frm.method = "GET";
	      frm.action = "<%= ctxPath%>/changeResult.hello2";
	      frm.submit();
	      
	   }// end of function goView(seq){}------------------
	  
   
  function delete1() {
		   
		   var returnValue = confirm('정말 삭제하시겠습니까?');

		   if(returnValue == true){
			    var frm = document.delFrm;
			    frm.doument_seq.value = "${documentvo.doument_seq}";
				frm.method = "POST";
				frm.action = "<%= ctxPath%>/delDocumentEnd.hello2";
				frm.submit();
			   
		   }
		   
		   
		   
	   }   
	   
</script>   





<!-- 좌측 고정 상세메뉴 시작 -->
<nav class="w3-sidebar w3-collapse w3-white " style="margin-top:50px; z-index:0; width:300px;background-color:#f5f5f5; overflow: hidden" id="mySidebar"><br>
  <div class="w3-container" style="background-color:#f5f5f5; margin-top:10px" >
    <a href="#" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu">
      <i class="fa fa-remove"></i>
    </a>
	<br><br>
    <span style="font-size:20pt; margin:100px 0 30px 40px ; color:#3399ff"><b>DOCUMENT</b></span>
  </div>
  <div class="w3-bar-block" style="background-color:#f5f5f5; height: 100%">
	<div style="margin-left:42px; font-size: 13pt">
	  <br>
	 <%-- <a href="<%= ctxPath %>/write.hello2"  class="w3-bar-item w3-button" >기안하기</a> --%>
	  <button type="button" class="w3-button w3-blue w3-margin-bottom" style="width: 180px; height: 50px; margin-left: 13px;"onclick="javascript:location.href='<%= request.getContextPath()%>/write.hello2'"><i class="fa fa-paper-plane w3-margin-right"></i>기안하기</button>
	  <a href="<%= ctxPath %>/myDocumentlist.hello2"    class="w3-bar-item w3-button">나의 기안목록 (평사원)</a>
	  <a href="<%= ctxPath %>/viewBreak.hello2"    class="w3-bar-item w3-button">나의 휴가캘린더</a> <!-- (남은연차개수, 연차내역조회, 연차내기) -->
	  <a href="<%= ctxPath %>/documentlist.hello2"   class="w3-bar-item w3-button">전체문서목록보기<br>(경지,admin)</a>
  	</div>
  </div>  
</nav>
<!-- 좌측 고정 상세메뉴 끝 -->

<div style="display: flex; padding-top: 50px; ">
<div style="margin: auto; padding-left: 3%;">
   
<!-- <form name="addFrm"> -->
<!-- === #149. 파일첨부하기 === 
		  먼저 위의 <form name="addFrm"> 을 주석처리 한 이후에 아래와 같이 해야 한다.
		 enctype="multipart/form-data" 를 해주어야만 파일첨부가 되어진다.
-->

<div style="display: flex;">
<div style="margin: auto; padding-left: 3%;">

   <h2 style="width: 1050px; margin-top:120px; margin-bottom: 20px;"></h2>
   <div style="height: 1000px;"> <!-- 여기서부터 내용물을 넣으면 된다 -->
<div style="display: flex;">
<div style="margin: auto; padding-left: 3%;">

	<h2 style="margin-bottom: 30px;">기안서</h2>
	
	<%-- <c:if test="${not empty requestScope.boardvo}"> --%>
		<table style="width: 1024px" class="table table-bordered" >
			<tr>
				<th style="width: 15%; background-color:#e6f5ff;">문서번호 </th>
				<th style="width: 70%;">${documentvo.doument_seq} <c:if test="${documentvo.result == 0}"> <button onclick="delete1()" type="button" class="btn btn-outline-danger btn-sm mr-3 ml-1 mb-1" >삭제</button></c:if></th>
				<td align="center" style="font-weight: bolder; font-size: 17px; background-color:#e6f5ff ; ">결과</td>
			</tr>
			
			<tr>
				<th style="background-color:#e6f5ff">문서종류</th>
				<td>
		            <c:if test="${documentvo.status == 1}"> 
		                 <span>연차</span>
		            </c:if>
		            <c:if test="${documentvo.status == 2}"> 
		                 <span>지출결의서</span>
		            </c:if>
		            <c:if test="${documentvo.status == 3}"> 
		                 <span>품의서</span>
		            </c:if>
		            <c:if test="${documentvo.status == 4}"> 
		                 <span>업무협조요청</span>
		            </c:if>
	            </td>
	            <td rowspan="3">
	            	<c:if test="${documentvo.result == 0}"> 
		                <img src="<%= ctxPath %>/resources/images/result_0.PNG"  width="130" height="130"/>
		            </c:if>
		            <c:if test="${documentvo.result == 1}"> 
		                <img src="<%= ctxPath %>/resources/images/result_1.PNG"  width="130" height="130"/>
		            </c:if>
		            <c:if test="${documentvo.result == 2}"> 
		                <img src="<%= ctxPath %>/resources/images/result_2.PNG"  width="130" height="130"/>
		            </c:if>
	            </td>
			</tr>
			
			<c:if test="${documentvo.status == 1}">
			<tr >
				<th style="width: 15%; background-color:#e6f5ff;">연차사용날짜</th>
				<td style="width: 70%;">${documentvo.breakstart} <span >~</span> ${documentvo.breakend},
				
					<c:if test="${documentvo.breakkind == 1}"> 
			                <span style="font-weight: bolder; color: blue;">종일</span>
			         </c:if>
			         <c:if test="${documentvo.breakkind == 2}"> 
			                <span style="font-weight: bolder; color: blue;">반차(오전)</span>
			         </c:if>
			         <c:if test="${documentvo.breakkind == 3}"> 
			                <span style="font-weight: bolder; color: blue;">반차(오후)</span>
			         </c:if>
			         <c:if test="${documentvo.breakkind == 4}"> 
			                <span style="font-weight: bolder; color: blue;">기타</span>
			         </c:if>
	
				</td>
			</tr>
			</c:if>
			
			<tr>	
				<th style="background-color:#e6f5ff">성명</th>
				<td>${documentvo.name}</td>
			</tr>
			<tr>	
				<th style="background-color:#e6f5ff">제목</th>
				<td colspan="2" >${documentvo.subject}</td>
			</tr>
			
			
			<tr style="height: 200px;">	
				<th style="background-color:#e6f5ff; vertical-align: middle;">내용</th>
				<td colspan="2">
				  <p style="word-break: break-all; ">${documentvo.content}</p>
				  <%-- 
				      style="word-break: break-all; 은 공백없는 긴영문일 경우 width 크기를 뚫고 나오는 것을 막는 것임. 
				           그런데 style="word-break: break-all; 나 style="word-wrap: break-word; 은
				           테이블태그의 <td>태그에는 안되고 <p> 나 <div> 태그안에서 적용되어지므로 <td>태그에서 적용하려면
				      <table>태그속에 style="word-wrap: break-word; table-layout: fixed;" 을 주면 된다.
				 --%>
				</td>
			</tr>
			<tr>	
				<th style="background-color:#e6f5ff">기안날짜</th>
				<td colspan="2">${documentvo.regDate}</td>
			</tr>
			
			<%-- === #162. 첨부파일 이름 및 파일크기를 보여주고 첨부파일을 다운로드 되도록 만들기 === --%>
			<tr>	
				<th style="background-color:#e6f5ff">첨부파일</th>
				<td colspan="2">
						<a href="<%= request.getContextPath()%>/download_document.hello2?doument_seq=${requestScope.documentvo.doument_seq}"><span style="color: blue;">${requestScope.documentvo.orgFilename}</span></a>
				</td>
			</tr>
			<tr>	
				<th style="background-color:#e6f5ff">파일크기(bytes)</th>
				<td colspan="2"> <fmt:formatNumber value="${requestScope.documentvo.fileSize}" pattern="#,###" /></td>
			</tr>
			
			<%-- <c:if test="${sessionScope.loginEmp.empno == 202111081004 && documentvo.result == 0}">
				<tr> 	
					<th style="background-color:#e6f5ff; vertical-align: middle"  >결재하기</th>
					<td style="vertical-align: middle">
						<select name="change" id="change" style="width: 12%; height: 30px;">
					     	<option value="1">승인</option>
					     	<option value="2">반려</option>
					     </select>
					     <button onclick="result()" type="button" class="btn btn-info btn-sm mr-3 ml-1 mb-1" >변경</button>
						<button type="button" class="btn btn-info btn-sm mr-3 ml-1 mb-1" onclick="javascript:location.href='<%= request.getContextPath()%>/changeResult.hello2?doument_seq=${documentvo.doument_seq}'">변경</button>
					</td>
				</tr>
			
		</c:if> --%>
			
		</table>
		
		
		
		<br/>
		
		<%-- <c:set var="v_gobackURL" value='${ fn:replace(requestScope.gobackURL, "&", " " ) }' /> --%>

		<br/>
		
		<button type="button" class="btn btn-secondary mr-3" style="margin-left: 500px;" onclick="javascript:history.back();">목록으로</button>
		
		
		<%-- === #126. 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후 
		                              사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해 
		                              현재 페이지 주소를 뷰단을 넘겨준다. --%>

		<%-- === #141. 어떤글에 대한 답변글쓰기는 로그인 되어진 회원의 gradelevel 컬럼의 값이 10인 직원들만 답변글쓰기가 가능하다. --%>
		<%-- 
		<br>
		   <span>groupno : ${requestScope.boardvo.groupno}</span>
		<br>
		   <span>depthno : ${requestScope.boardvo.depthno}</span>
		--%>
		
		<form name="changeResult">
			<input type="hidden" name="change"/>
			<input type="hidden" name="doument_seq"/>
			<input type="hidden" name="status1"/>
			<input type="hidden" name="breakkind"/>
			<input type="hidden" name="breakstart"/>
			<input type="hidden" name="breakend"/>
		</form>
		
		<form name="delFrm">
			<input type="hidden" name="doument_seq" value=""/>
		</form>
		
   </div>  <!-- 여기까지 내용물을 넣으면 된다. -->
</div>
</div>
</div>
</div>
</div>
</div>
    