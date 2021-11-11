<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %> 


<style type="text/css">
   
   .subjectStyle {font-weight: bold;
               color: navy;
               cursor: pointer; }
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
	      frm.action = "<%= request.getContextPath()%>/documentlist.hello2";
	      frm.submit();
	      
	   }// end of function change(){}-------------------
   
	   
	function goView(seq){
		   
      <%--   
         location.href="<%= ctxPath%>/view.action?seq="+seq;
      --%>   
      
      // === #124. 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후
      //            사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해
      //           현재 페이지 주소를 뷰단으로 넘겨준다.
      var frm = document.goViewFrm;  // goViewFrm 밑에 있는 폼
      frm.seq.value = seq;
      frm.gobackURL.value = "${requestScope.gobackURL}"; // 자바스크립트라서 ""
      frm.searchType.value = "${requestScope.paraMap.searchType}";
      frm.searchWord.value = "${requestScope.paraMap.searchWord}";
      
      frm.method = "GET";
      frm.action = "<%= ctxPath%>/viewDocument.hello2";
  //    frm.submit();
      
   }// end of function goView(seq){}------------------
  
</script>   





<!-- 좌측 고정 상세메뉴 시작 -->
<nav class="w3-sidebar w3-collapse w3-white " style="margin-top:50px; z-index:0; width:300px;background-color:#f5f5f5; overflow: hidden" id="mySidebar"><br>
  <div class="w3-container" style="background-color:#f5f5f5; margin-top:10px" >
    <a href="#" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu">
      <i class="fa fa-remove"></i>
    </a>
	<br><br>
    <span style="font-size:20pt; margin:100px 0 30px 40px ; color:gray"><b>DOCUMENT</b></span>
  </div>
  <div class="w3-bar-block" style="background-color:#f5f5f5; height: 100%">
	<div style="margin-left:42px; font-size: 13pt">
	  <br>
	  <a href="<%= ctxPath %>/write.hello2"  class="w3-bar-item w3-button" >기안하기</a>
	  <a href="<%= ctxPath %>/documentlist.hello2"   class="w3-bar-item w3-button">문서목록보기</a>
	  <a href="#채팅"    class="w3-bar-item w3-button">결재하기</a>
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

   <h2 style="width: 1050px; margin-top:120px; margin-bottom: 20px;">문서목록</h2>
   <div style="height: 800px;">
   <table style="width: 1250px;" class="table table-bordered">
      <thead>
      <tr>
         <th style="width: 30px; text-align: center;">문서번호</th>
         <th style="width: 200px; text-align: center;">문서제목</th>
         <th style="width: 60px; text-align: center;">작성자</th>
         <th style="width: 80px; text-align: center;">문서종류</th>
         <th style="width: 150px; text-align: center;">기안시간</th>
         <th style="width: 30px; text-align: center;">상태</th>
      </tr>   
      </thead>
      
      
      
      <tbody>
      <c:forEach var="documentList" items="${requestScope.documentList}">
         <tr>
            <td align="center">${documentList.doument_seq}</td>
            <td align="left">
            <%-- === 댓글쓰기 및  답변형 및 파일첨부가 있는 게시판 시작 === --%>
            
            <%-- === 첨부파일이 없는 경우 시작 === --%>
            <c:if test="${empty documentList.fileName}">
		               <span class="subject" onclick="goView('${documentList.doument_seq}')">${documentList.subject}</span>   
            </c:if>
            <%-- === 첨부파일이 없는 경우 끝 === --%>
            
            
            <%-- === 첨부파일이 있는 경우 시작 === --%>
            <c:if test="${not empty documentList.fileName}">
		        <span class="subject" onclick="goView('${documentList.doument_seq}')">${documentList.subject}</span> &nbsp;<img src="<%= request.getContextPath()%>/resources/images/disk.gif " />
	        </c:if>
            <%-- === 첨부파일이 있는 경우 끝 === --%>
       
            </td>
            <td align="center">${documentList.name}</td>
            <td align="center">
	            <c:if test="${documentList.status == 1}"> 
	                 <span>연차</span>
	            </c:if>
	            <c:if test="${documentList.status == 2}"> 
	                 <span>지출결의서</span>
	            </c:if>
	            <c:if test="${documentList.status == 3}"> 
	                 <span>품의서</span>
	            </c:if>
	            <c:if test="${documentList.status == 4}"> 
	                 <span>업무협조요청</span>
	            </c:if>
            </td>
            <td align="center">${documentList.regDate}</td>
            <td align="center">
	            <c:if test="${documentList.result == 0}"> 
	                 <span>대기</span>
	            </c:if>
	            <c:if test="${documentList.result == 1}"> 
	                 <span>승인</span>
	            </c:if>
	            <c:if test="${documentList.result == 2}"> 
	                 <span>반려</span>
	            </c:if>
            </td>
         </tr>
      </c:forEach>
      </tbody>
   </table>
   
     <form name="searchFrm" style="margin-top: 30px; margin-left: 10px;" ">
     <span style="font-size: 18px;"> 문서종류 : </span>
     <select name="searchType" id= "searchType" style="width: 12%; height: 30px; margin-bottom: 20px;">
     	<option value="">전체</option>
     	<option value="1">연차</option>
     	<option value="2">지출결의서</option>
     	<option value="3">품의서</option>
     	<option value="4">업무협조요청</option>
     </select>
     
     <span style="font-size: 18px; margin-left: 10px;" > 상태 : </span>
     <select name="searchWord" id= "searchWord" style="width: 12%; height: 30px; margin-bottom: 20px;">
     	<option value="">전체</option>
     	<option value="0">대기</option>
     	<option value="1">승인</option>
     	<option value="2">반려</option>
     </select>
     <button onclick="button()" class="btn btn-info btn-sm" style="margin-left: 10px; margin-bottom: 3px;">검색</button>
     
      <input type="hidden" name="searchWord" id="searchWord" size="106" autocomplete="off"/>
      <input type="hidden" style="display: none;"/> <%-- form 태그 내에 input태그가 오로지 1개뿐일 경우에는 엔터를 했을 경우 검색이 되어지므로 이것을 방지하고자 만든 것이다. --%>
   </form>
   
   <%-- === #122. 페이지바 보여주기  === --%>
   <div align="center" style="width:70%; border: solid 0px gray; margin: 20px auto;">
      ${requestScope.pageBar}
   </div>
   

   
   
   
   <%-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후
        사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해
        현재 페이지 주소를 뷰단으로 넘겨준다. --%>
   <form name="goViewFrm">
      <input type="hidden" name="seq" />
      <input type="hidden" name="gobackURL" />
	  <input type="hidden" name="searchType" />
	  <input type="hidden" name="searchWord" />
   </form>
   </div>
</div>
</div>
</div>
</div>
    