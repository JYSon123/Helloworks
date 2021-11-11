<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<% String ctxPath = request.getContextPath(); %>

<style type="text/css">
   th {background-color: #DDD;}
   
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
      
      // 검색시 검색조건 및 검색어 값 유지시키기
      if( ${not empty requestScope.paraMap} ) {
         $("select#searchType").val("${requestScope.paraMap.searchType}");
         $("input#searchWord").val("${requestScope.paraMap.searchWord}");         
      }
      
      
      <%-- === #107. 검색어 입력시 자동글 완성하기 2 === --%>
      $("div#displayList").hide();
      
      $("input#searchWord").keyup(function(){
         
         var wordLength = $(this).val().trim().length;
         // 검색어의 길이를 알아온다.
         
         if(wordLength == 0){
            $("div#displayList").hide();
            // 검색어가 공백이거나 검색어 입력 후 백스페이스키를 눌러서 검색어를 모두 지우면 검색된 내용이 안 나오도록 해야 한다.
         }
         else{
            $.ajax({
               url:"<%= request.getContextPath()%>/wordSearchShow.action",
               type:"GET",
               data:{"searchType":$("select#searchType").val()
                   ,"searchWord":$("input#searchWord").val()},
               dataType:"JSON",
               success:function(json){
                  
               <%-- === #112. 검색어 입력시 자동글 완성하기 6 === --%>
               if(json.length > 0){
                  // 검색된 데이터가 있는 경우
                  
                  var html = "";
                  
                  $.each(json, function(index, item){
                     var word = item.word;
                     // word ==> 프로그램은 jaVA를 공부합니다.
                     
                     var index = word.toLowerCase().indexOf( $("input#searchWord").val().toLowerCase()  );
                     //             프로그램은 java를 공부합니다.               java
                     // 검색어(jAvA)가 나오는 index ==> 6
                     
                     var len = $("input#searchWord").val().length;
                     // 검색어(jAvA)의 길이 len ==> 4
                     
                     var result = word.substring(0, index) + "<span style='color:blue;'>"+word.substr(index,len)+"</span>" + word.substr(index+len);
                     
                     html += "<span style='cursor:pointer' class='result'>"+result+"</span><br>";
                     
                  });
                  
                  var input_width = $("input#searchWord").css("width"); // 검색어 input태그 width 알아오기
                  
                  $("div#displayList").css({"width":input_width}); // 검색결과 div의 width 크기를 검색어 input 태그 width와 일치시키기
                  
                  $("div#displayList").html(html);
                  $("div#displayList").show();
                  }

               },
               error: function(request, status, error){
                      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
                  }
            });
                     
         }
         
      }); // end of $("input#searchWord").keyup()----------------------
   
   
      <%-- === #113. 검색어 입력시 자동글 완성하기 6 === --%>
      $(document).on("click", ".result", function(){
         var word = $(this).text();
         $("input#searchWord").val(word); // 텍스트박스에 검색된 결과의 문자열을 입력해준다.
         $("div#displayList").hide();
         goSearch();
      });
   
});// end of $(document).ready(function(){})-------------------------

   // Function Declaration 
   function goView(seq){
   
      <%--   
         location.href="<%= ctxPath%>/view.action?seq="+seq;
      --%>   
      
      // === #124. 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후
      //            사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해
      //           현재 페이지 주소를 뷰단으로 넘겨준다.
      var frm = document.goViewFrm; // goViewFrm 밑에 있는 폼
      frm.seq.value = seq;
      frm.gobackURL.value = "${requestScope.gobackURL}"; // 자바스크립트라서 ""
      frm.searchType.value = "${requestScope.paraMap.searchType}";
      frm.searchWord.value = "${requestScope.paraMap.searchWord}";
      
      frm.method = "GET";
      frm.action = "<%= ctxPath%>/view.action";
      frm.submit();
      
   }// end of function goView(seq){}------------------
   
   function goSearch(){
      var frm = document.searchFrm;
      frm.method = "GET";
      frm.action = "<%= request.getContextPath()%>/list.action";
      frm.submit();
   }// end of function goSearch(){}-------------------
</script>

<div style="display: flex;">
<div style="margin: auto; padding-left: 3%;">

   <h2 style="margin-bottom: 30px;">글목록</h2>
   
   <table style="width: 1024px" class="table table-bordered">
      <thead>
      <tr>
         <th style="width: 60px; text-align: center;">글번호</th>
         <th style="width: 200px; text-align: center;">제목</th>
         <th style="width: 60px; text-align: center;">성명</th>
         <th style="width: 100px; text-align: center;">날짜</th>
         <th style="width: 60px; text-align: center;">조회수</th>
      </tr>   
      </thead>
      
      <tbody>
      <c:forEach var="boardvo" items="${requestScope.boardList}">
         <tr>
            <td align="center">${boardvo.seq}</td>
            <td align="left">
            
             <%-- === 댓글쓰기가 없는 게시판 === 
                 <span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}</span>
             --%>
             <%-- === 댓글쓰기가 있는 게시판 시작 === --%>
            <%-- <c:if test="${boardvo.commentCount > 0}"> 
                 <span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${boardvo.commentCount}</span>]</span>
            </c:if>         
               
            <c:if test="${boardvo.commentCount == 0}">
               <span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}</span> 
            </c:if>   --%> 
             <%-- === 댓글쓰기가 있는 게시판 끝 === --%>
            
            
            <%-- === 댓글쓰기 및  답변형 게시판 시작 === --%>
            <!-- 답변글이 아닌 원글인 경우 -->
             <!-- 
            <c:if test="${boardvo.depthno == 0}">
	            <c:if test="${boardvo.commentCount > 0}"> 
	                 <span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${boardvo.commentCount}</span>]</span>
	            </c:if>         
	               
	            <c:if test="${boardvo.commentCount == 0}">
	               <span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}</span> 
	            </c:if>   
            </c:if>
            
            <%-- 답변글인 경우 --%>
            <c:if test="${boardvo.depthno > 0}">
               <c:if test="${boardvo.commentCount > 0}"> 
                    <span class="subject" onclick="goView('${boardvo.seq}')"><span style="color:red; font-style: italic; padding-left: ${boardvo.depthno * 20}px;">└Re&nbsp;</span>${boardvo.subject}[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${boardvo.commentCount}</span>]</span>
               </c:if>         
                  
               <c:if test="${boardvo.commentCount == 0}">
                  <span class="subject" onclick="goView('${boardvo.seq}')"><span style="color:red; font-style: italic; padding-left: ${boardvo.depthno * 20}px;">└Re&nbsp;</span>${boardvo.subject}</span> 
               </c:if>   
            </c:if>
            <%-- === 댓글쓰기 및  답변형 게시판 끝 === --%>
            -->
            
            
            <%-- === 댓글쓰기 및  답변형 및 파일첨부가 있는 게시판 시작 === --%>
            <%-- === 첨부파일이 없는 경우 시작 === --%>
            <c:if test="${empty boardvo.fileName}">
	            <!-- 답변글이 아닌 원글인 경우 -->
	            <c:if test="${boardvo.depthno == 0}">
		            <c:if test="${boardvo.commentCount > 0}"> 
		                 <span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${boardvo.commentCount}</span>]</span>
		            </c:if>         
		               
		            <c:if test="${boardvo.commentCount == 0}">
		               <span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}</span> 
		            </c:if>   
	            </c:if>
	            
	            <%-- 답변글인 경우 --%>
	            <c:if test="${boardvo.depthno > 0}">
	               <c:if test="${boardvo.commentCount > 0}"> 
	                    <span class="subject" onclick="goView('${boardvo.seq}')"><span style="color:red; font-style: italic; padding-left: ${boardvo.depthno * 20}px;">└Re&nbsp;</span>${boardvo.subject}[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${boardvo.commentCount}</span>]</span>
	               </c:if>         
	                  
	               <c:if test="${boardvo.commentCount == 0}">
	                  <span class="subject" onclick="goView('${boardvo.seq}')"><span style="color:red; font-style: italic; padding-left: ${boardvo.depthno * 20}px;">└Re&nbsp;</span>${boardvo.subject}</span> 
	               </c:if>   
	            </c:if>
            </c:if>
            <%-- === 첨부파일이 없는 경우 끝 === --%>
            
            
            <%-- === 첨부파일이 있는 경우 시작 === --%>
            <c:if test="${not empty boardvo.fileName}">
	            <!-- 답변글이 아닌 원글인 경우 -->
	            <c:if test="${boardvo.depthno == 0}">
		            <c:if test="${boardvo.commentCount > 0}"> 
		                 <span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${boardvo.commentCount}</span>]</span> &nbsp;<img src="<%= request.getContextPath()%>/resources/images/disk.gif " />
		            </c:if>         
		               
		            <c:if test="${boardvo.commentCount == 0}">
		               <span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}</span> &nbsp;<img src="<%= request.getContextPath()%>/resources/images/disk.gif " />
		            </c:if>   
	            </c:if>
	            
	            <%-- 답변글인 경우 --%>
	            <c:if test="${boardvo.depthno > 0}">
	               <c:if test="${boardvo.commentCount > 0}"> 
	                    <span class="subject" onclick="goView('${boardvo.seq}')"><span style="color:red; font-style: italic; padding-left: ${boardvo.depthno * 20}px;">└Re&nbsp;</span>${boardvo.subject}[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${boardvo.commentCount}</span>]</span> &nbsp;<img src="<%= request.getContextPath()%>/resources/images/disk.gif " />
	               </c:if>         
	                  
	               <c:if test="${boardvo.commentCount == 0}">
	                  <span class="subject" onclick="goView('${boardvo.seq}')"><span style="color:red; font-style: italic; padding-left: ${boardvo.depthno * 20}px;">└Re&nbsp;</span>${boardvo.subject}</span> &nbsp;<img src="<%= request.getContextPath()%>/resources/images/disk.gif " /> 
	               </c:if>   
	            </c:if>
            </c:if>
            <%-- === 첨부파일이 있는 경우 끝 === --%>
            
            
            
            <%-- === 댓글쓰기 및  답변형 및 파일첨부가 있는 게시판 끝 === --%>
            
            
            
            </td>
            <td align="center">${boardvo.name}</td>
            <td align="center">${boardvo.regDate}</td>
            <td align="center">${boardvo.readCount}</td>
         </tr>
      </c:forEach>
      </tbody>
   </table>
   
   <%-- === #122. 페이지바 보여주기  === --%>
   <div align="center" style="width:70%; border: solid 0px gray; margin: 20px auto;">
      ${requestScope.pageBar}
   </div>
   
   <%-- === #101. 글검색 폼 추가하기 : 글제목,글쓴이로 검색을 하도록 한다. === --%>
   <form name="searchFrm" style="margin-top: 20px;">
      <select name="searchType" id="searchType" style="height: 26px">
         <option value="subject">글제목</option>
         <option value="name">글쓴이</option>
      </select>
      <input type="text" name="searchWord" id="searchWord" size="106" autocomplete="off"/>
      <input type="text" style="display: none;"/> <%-- form 태그 내에 input태그가 오로지 1개뿐일 경우에는 엔터를 했을 경우 검색이 되어지므로 이것을 방지하고자 만든 것이다. --%>
      <button type="button" class="btn btn-secondary btn-sm" onclick="goSearch()">검색</button>
   </form>
   
   
   <%-- === #106. 검색어 입력시 자동글 완성하기 1 === --%>
   <div id="displayList" style="border: solid 1px gray; height: 100px; overflow: auto; margin-left: 85px; margin-top: -1px; border-top: 0px;">
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