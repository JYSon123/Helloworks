<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %> 

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
         
         // 폼(form)을 전송(submit)
         var frm = document.addFrm;
         frm.method = "POST";
         frm.action = "<%= ctxPath%>/addEnd.hello2";
         frm.submit();
      });
      
   });// end of $(document).ready(function(){})--------------------------

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
	  <a href="#게시판"   class="w3-bar-item w3-button">문서목록보기</a>
	  <a href="#채팅"    class="w3-bar-item w3-button">결재하기</a>
  	</div>
  </div>  
</nav>
<!-- 좌측 고정 상세메뉴 끝 -->

<div style="display: flex; padding-top: 50px; ">
<div style="margin: auto; padding-left: 3%;">

    <h2 style="margin-bottom: 30px; margin-top:100px;">기안하기</h2>
   
<!-- <form name="addFrm"> -->
<!-- === #149. 파일첨부하기 === 
		  먼저 위의 <form name="addFrm"> 을 주석처리 한 이후에 아래와 같이 해야 한다.
		 enctype="multipart/form-data" 를 해주어야만 파일첨부가 되어진다.
-->

     <form name="addFrm" enctype="multipart/form-data">
      <table style="width: 1050px; margin-top:30px; " class="table table-bordered">
         <tr>
            <th style="width: 15%; background-color: #DDDDDD">성명</th>
            <td>
               <input type="hidden" name="fk_empno" value="${sessionScope.loginuser.fk_empno}" />
               <input type="hidden" name="fk_deptnum" value="${sessionScope.loginuser.fk_deptnum}" />
               <input type="hidden" name="fk_userid" value="${sessionScope.loginuser.userid}" />
               <input type="text" name="name" value="${sessionScope.loginuser.name}" readonly /> 
            </td>
         </tr>
         <tr>
            <th style="width: 15%; background-color: #DDDDDD " >문서종류</th>
            <td>
			     <select name="documentKind" style="width: 15%; height: 30px;">
			     	<option>연차</option>
			     	<option>지출결의서</option>
			     	<option>품의서</option>
			     	<option>업무협조요청</option>
			     </select>
            </td>
         </tr>
         <tr>
            <th style="width: 15%; background-color: #DDDDDD" >제목</th>
            <td>
               <%-- 원글쓰기인 경우 --%>
			     <input type="text" name="subject" id="subject" size="100"/>
            </td>
         </tr>
         <tr>
            <th style="width: 15%; background-color: #DDDDDD; vertical-align: middle;">내용</th>
            <td>
               <textarea style="width: 100%; height: 412px;" name="content" id="content"></textarea> 
            </td>
         </tr>
         
        <%-- === # 150. 파일첨부 타입 추가하기 === --%> 
         <tr>
            <th style="width: 25%; background-color: #DDDDDD">증빙첨부</th>
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
         <button type="button" class="btn btn-secondary  mr-3" id="btnWrite">기안하기</button>
         <button type="button" class="btn btn-secondary " onclick="javascript:history.back()">취소</button> 
      </div>
      
      <div style="margin-bottom: 150px;"></div>
   </form>
	
</div>   
</div>

    