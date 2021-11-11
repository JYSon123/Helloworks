<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

   
<style type="text/css">
	body,h1,h2,h3,h4,h5,h6 {font-family: Verdana, sans-serif;}
	
	th{
		color:#595959;
		font-weight: normal;
		font-size: 14pt;
		text-align: center;
	}
	
	td{
		color:#595959;
		font-size: 14pt;
		
	}
	
	.subjectStyle {
		font-weight: bold;
		color: navy;
		cursor: pointer;
	}
</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		$("span.subject").bind("mouseover", function(event){
			var $target = $(event.target);
			$target.addClass("subejctStyle");		
		});
		
		$("span.subject").bind("mouseout",function(event){
			var $target = $(event.target);
			$target.removeClass("subjectStyle");
		})
		
		$("input#searchWord").keyup(function(event){
			if(event.keyCode == 13){
				// 엔터를 했을 경우
				goSearch();
			}
		});
		
		
	});


</script>

<script>
function w3_open() {
    document.getElementById("mySidebar").style.display = "block";
    document.getElementById("myOverlay").style.display = "block";
}
 
function w3_close() {
    document.getElementById("mySidebar").style.display = "none";
    document.getElementById("myOverlay").style.display = "none";
}
</script>

<!-- 좌측 고정 상세메뉴 시작 -->
<nav class="w3-sidebar w3-collapse w3-white " style="margin-top:20px; z-index:0; width:300px;background-color:#f5f5f5; overflow: hidden" id="mySidebar"><br>
  <div class="w3-container" style="background-color:#f5f5f5; margin-top:10px" >
    <a href="#" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu">
      <i class="fa fa-remove"></i>
    </a>
	<br><br>
	
	<button type="button" onclick="location.href='<%= ctxPath %>/add.hello2'" class="btn" id="btn1" style="background-color:#0070C0; margin-left:14px; font-size:21px; width: 240px; height:63px; color:white">
				<b>글쓰기</b></button>
	<br>
  </div>
  <div class="w3-bar-block" style="background-color:#f5f5f5; height: 100%;">
	<div style="margin-left:42px; font-size: 16pt; color:#595959">
	  <br>
	  <a href="<%= ctxPath %>/list.hello2"    class="w3-bar-item w3-button">자유게시판</a>
  	</div>
  </div>  
</nav>
<!-- 좌측 고정 상세메뉴 끝 -->


<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin-top:30px">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large w3-animate-opacity" style="margin-bottom: 800px;">
    
    <div style="display: flex;">
	<div style="margin: 70px 0 0 270px; padding-left: 3%">
	

		<span style="color:#0070C0" class="h3"><b>자유게시판</b></span>
		
		<%-- === 글검색 폼 추가하기 : 글제목, 글쓴이로 검색한다. === --%>
		<form name="searchFrom" style="margin:0 0 0 870px; font-size: 13pt">
			<select name="searchType" id="searchType" style="height: 33px; border:none; color:#595959">
				<option value="subject">글제목</option>
				<option value="name">글쓴이</option>
			</select>
			<input type="text" name="searchWord" id="searchWord" size="40" autocomplete="off" style="height: 50px; border-radius: 50px; border: 1px solid #595959"/>
			<input type="text" style="display: none;"/> <%-- form 태그 내에 input태그가 오로지 1개뿐일 경우에는 엔터를 했을 경우 검색이 되어지므로 이것을 방지하고자 만든 것이다. --%>
			<button class="btn btn-outline-light border ml-1" style=" width:50px; height:50px;  border-radius: 30px; color:black; width:47px" type="button" onClick="goSearch();">
		    	  <i class="fa fa-search"></i>
		     </button>
		</form>
		
		<%-- 검색어 입력시 자동글 완성하기 --%>
		<div id="displayList" style="border:solid 1px gray; height: 100px; overflow: auto; margin-left: 85px; margin-top: -1px; border-top: 0px;">
		</div>
		

	<table style="max-width: 1400px; margin-top:30px" class="table">
		 <thead>
		<tr>
			<th style="width: 7%;">글번호</th>
			<th style="width: 49%;">제목</th>
			<th style="width: 12%;">성명</th>
			<th style="width: 16%;">날짜</th>
			<th style="width: 8%;">조회수</th>
		</tr>	
		</thead>
		
		<tbody >
		<c:forEach var="boardvo" items="${requestScope.boardList}">
			<tr>
				<td align="center">${boardvo.seq}</td>
				<td align="left">
					<span class="subject">${boardvo.subject}</span>
				</td>
				<td align="center">${boardvo.name}</td>
				<td align="center">${boardvo.regDate}</td>
				<td align="center">${boardvo.readCount}</td>
			</tr>					
	    </c:forEach>	
		</tbody>
	</table>
	
	<%-- === 페이지바 보여주기 === --%>
	<div aligin="center" style="width:70%; border: solid 0px gray; margin: 20px auto;">
		${requestScope.pageBar}
	</div>
	
	<%-- 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후 
	         사용자가 목록보기 버튼을 클릭했을 때 돌아갈 페이지를 알려주기 위해 
	         현재 페이지 주소를 뷰단으로 넘겨준다. --%>
	 <form name="goViewFrm">
	 	<input type="hidden" name="seq"/>
	 	<input type="hidden" name="gobackURL"/>
	 	<input type="hidden" name="searchType"/>
	 	<input type="hidden" name="searchWord"/> 
	 </form>

</div>
</div>
</div>
  

<!-- !!!!!PAGE CONTENT END!!!!!! -->
</div>

