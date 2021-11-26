<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %> 

<style>

  .rest { display: none; }



</style>

<script type="text/javascript">

   $(document).ready(function(){
      
	   
      // 쓰기버튼
      $("button#btnWrite").click(function(){
         
         // 글제목 유효성 검사
         var subjectVal = $("input#subject").val().trim();
         if(subjectVal == "") {
            alert("글제목을 입력하세요!!");
            return;
         }
         
         // 글내용 유효성 검사(스마트에디터 사용 안 할시)
         var contentVal = $("textarea#content").val().trim();
         if(contentVal == "") {
            alert("글내용을 입력하세요!!");
            return;
         }
         
         var documentKind = $("select#documentKind").val();
         
         if(documentKind == "0") {
            alert("문서 종류를 설정해주세요!");
            return;
         }
         
         var documentKind = $("select#documentKind").val();
         if(documentKind == "1" ) {
            
        	 var breakstart = $("input#breakstart").val();
        	 var breakend = $("input#breakend").val();
        	 
        	 if(breakstart == ""){
        		 alert("시작 날짜를 설정해주세요!");
        		 return;
        	 }
        	 
        	 if(breakend == ""){
        		 alert("끝나는 날짜를 설정해주세요!");
        		 return;
        	 }
           
         }
         
         
         // 폼(form)을 전송(submit)
         var frm = document.addFrm;
         frm.method = "POST";
         frm.action = "<%= ctxPath%>/addEndjy.hello2";
         frm.submit();
      });
      
 
      
      
   });// end of $(document).ready(function(){})--------------------------

   
   function form1(value){
	   
	   var state = value;
	   
	   console.log(state);
	   
	   if(state == 1) {
		   $("span#rest").removeClass("rest");   
	   }
	   else {
		   $("span#rest").addClass("rest");
		   
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
    <span style="font-size:20pt; margin:100px 0 30px 40px ; color:#0070C0"><b>DOCUMENT</b></span>
  </div>
  <div class="w3-bar-block" style="background-color:#f5f5f5; height: 100%">
	<div style="margin-left:42px; font-size: 13pt">
	  <br>
	  <%-- <a href="<%= ctxPath %>/write.hello2"  class="w3-bar-item w3-button" >기안하기</a> --%>
	  <button type="button"  class="w3-button w3-margin-bottom" style="width: 180px; height: 50px; background-color : #0070C0; color:white; margin-left: 13px;"onclick="javascript:location.href='<%= request.getContextPath()%>/write.hello2'"><i class="fa fa-paper-plane w3-margin-right"></i>기안하기</button>
	  <a href="<%= ctxPath %>/myDocumentlist.hello2"    class="w3-bar-item w3-button">나의 기안목록</a>
	  <a href="<%= ctxPath %>/viewBreak.hello2"    class="w3-bar-item w3-button">나의 휴가캘린더</a> <!-- (남은연차개수, 연차내역조회, 연차내기) -->
	  <a href="<%= ctxPath %>/documentlist.hello2"   class="w3-bar-item w3-button">전체문서목록(결재)<br></a>
  	</div>
  </div>  
</nav>
<!-- 좌측 고정 상세메뉴 끝 -->

<div style="display: flex; padding-top: 50px; ">
<div style="margin: auto; padding-left: 3%;">

    <h2 style="margin-bottom: 30px; margin-top:100px;" ><b>기안하기</b></h2>
   
<!-- <form name="addFrm"> -->
<!-- === #149. 파일첨부하기 === 
		  먼저 위의 <form name="addFrm"> 을 주석처리 한 이후에 아래와 같이 해야 한다.
		 enctype="multipart/form-data" 를 해주어야만 파일첨부가 되어진다.
-->

     <form name="addFrm" enctype="multipart/form-data">
      <table style="width: 1050px; margin-top:30px;" class="table" >
         <tr>
            <th style="width: 15%;" >성명</th>
            <td>
               <input type="hidden" name="fk_empno" value="${sessionScope.loginEmp.empno}" />
               <input type="hidden" name="fk_deptnum" value="${sessionScope.loginEmp.fk_deptnum}" />
               <input type="hidden" name="fk_userid" value="${sessionScope.loginEmp.empid}" />
               <input type="text" name="empname" value="${sessionScope.loginEmp.empname}" readonly style="border: none;" /> 
            </td>
         </tr>
         <tr>
            <th style="width: 15%; " >문서종류</th>
            <td>
			     <select onchange="form1(value)" name="documentKind" id="documentKind"  style="width: 15%; height: 30px;">
			     	<option value="0">선택</option>
			     	<option value="1">연차</option>
			     	<option value="2">지출결의서</option>
			     	<option value="3">품의서</option>
			     	<option value="4">업무협조요청</option>
			     </select>
			     
			     <span id="rest" class="rest" >
			     <span style="margin-left: 14px;">종류 : </span>
			     <select name="breakkind" style="width: 13%; height: 30px;">
			     	<option value="1">종일</option>
			     	<option value="2">반차(오전)</option>
			     	<option value="3">반차(오후)</option>
			     	<option value="4">기타</option>
			     </select>
			     
			     <span style="margin-left: 14px;">사용날짜 : </span>
			     <input name="breakstart" id="breakstart" style="height: 30px; " type="date" />
			     
			     <span style="margin-left: 3px; font-weight: bolder;">-</span>
			     
			     <input name="breakend" id="breakend" style="height: 30px; " type="date" />
			     
			     </span>
            </td>
         </tr>
         <tr>
            <th style="width: 15%; " >제목</th>
            <td>
               <%-- 원글쓰기인 경우 --%>
			     <input type="text" name="subject" id="subject" size="100" style="border: none;"/>
            </td>
         </tr>
         <tr>
            <th style="width: 15%; vertical-align: middle; border-right: none; ">내용</th>
            <td>
               <textarea style="width: 100%; height: 412px; border: none;" name="content" id="content"></textarea> 
            </td>
         </tr>
         
        <%-- === # 150. 파일첨부 타입 추가하기 === --%> 
         <tr>
            <th style="width: 25%;">증빙첨부</th>
            <td>
               <input type="file" name="attach" /> 
            </td>
         </tr>

      </table>
      
      <%-- === #143. 답변글쓰기가 추가된 경우 시작 ===  --%>
      <input type="hidden" name="fk_seq" value="${requestScope.fk_seq}" />
      <input type="hidden" name="groupno" value="${requestScope.groupno}" />
      <input type="hidden" name="depthno" value="${requestScope.depthno}" />
      <%-- === 답변글쓰기가 추가된 경우 끝 ===  --%>
      
      <div style=" margin-top: 30px;">
         <button type="button" class="btn mr-3" id="btnWrite" style="background-color: #0070C0; color: white;">기안하기</button>
         <button type="button" class="btn" style="background-color: #0070C0; color: white;" onclick="javascript:history.back()">취소</button> 
      </div>
      
      <div style="margin-bottom: 150px;"></div>
   </form>
	
</div>   
</div>

    