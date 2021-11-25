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

	.subject:visited {
	  color: inherit;
	}
	
	.subject:hover {
	  font-weight: normal;
	  cursor: pointer;
	}
	
	#hovertbl:hover tbody tr:hover td {
   	  background: #f1f6fb;
	}
	
	
	#displayList { 
		-ms-overflow-style: none; 
	} 
	
	#displayList::-webkit-scrollbar { 
		display:none; 
	}

	.result:hover{
		 background: #f5f5f5;
	}
</style>

<script type="text/javascript">

	$(document).ready(function(){
	

		$("input#searchWord").keyup(function(event){
			if(event.keyCode == 13){
				// 엔터를 했을 경우
				goSearch();
			}
		});
		
		// 검색시 검색조건 및 검색어 값 유지시키기
		if( ${not empty requestScope.paraMap} ){
			$("select#searchType").val("${requestScope.paraMap.searchType}");
			$("input#searchWord").val("${requestScope.paraMap.searchWord}");	
		}
		
		
		// === 검색어 입력시 자동글 완성하기 === // 
		$("#displayList").hide();
		
		$("input#searchWord").keyup(function(){
			
			var wordLength = $(this).val().trim().length;
			// 검색어의 길이를 알아온다.
			
			if(wordLength == 0){
				$("div#displayList").hide();
				// 검색어가 공백이거나 검색어 입력 후 백스페이스키를 눌러서 검색어를 모두 지우면 검색된 내용이 안 나오도록 해야 한다.				
			}
			else{
				$.ajax({
					url:"<%= request.getContextPath()%>/wordSearchShow.hello2",
					type:"GET",
					data:{"searchType":$("select#searchType").val()
						 ,"searchWord":$("input#searchWord").val()},
					dataType:"JSON",
					success:function(json){
						
						
					<%-- 검색어 입력시 자동글 완성하기 --%>
					if(json.length > 0){
						//검색된 데이터가 있는 경우
						
						var html = "";
						
						$.each(json, function(index, item){
							var word = item.word;
							
							var index = word.toLowerCase().indexOf($("input#searchWord").val().toLowerCase());
							
							var len = $("input#searchWord").val().length;
							
							var result = word.substring(0, index) + "<span style='font-weight:bold; font-size:14pt; color:#595959'>"+word.substr(index,len)+"</span>" + word.substr(index+len);
							
							html += "<span style='cursor:pointer; margin-top:10px; font-size:14pt; color:#595959;' class='result'>"+result+"</span><br>";
								
						});
						
						var input_width = $("input#searchWord").css("width"); // 검색어 input태그 width알아오기
						
						$("div#displayList").css({"width":input_width});   // 검색결과 div의 width크기를 검색어 input 태그 width와 일치시키기
						
						$("div#displayList").html(html);
						$("div#displayList").show();			
						
						}	
						
					}, 
					error: function(request, status, error){
		                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	            	}	
					
				});
				
			}

		}); // end of $("input#searchWord").keyup(function(){})----------------------

		
		
		<%-- 검색어 입력시 자동글 완성하기 --%>
		$(document).on("click", ".result", function(){
			var word = $(this).text();
			$("input#searchWord").val(word); // 텍스트박스에 검색된 결과의 문자열을 입력해준다.
			$("div#displayList").hide();
			goSearch();		
		});

	});// end of $(document).ready(function(){})-------------------------

	// Function Declaration
	function goView(seq){
		var frm = document.goViewFrm;
		frm.seq.value = seq;
		frm.gobackURL.value = "${requestScope.gobackURL}" // 자바스크립트라서 ""
		frm.searchType.value = "${requestScope.paraMap.searchType}";
		frm.searchWord.value = "${requestScope.paraMap.searchWord}";
		
		frm.method = "GET";
		frm.action = "<%= ctxPath%>/view.hello2";
		frm.submit();	
	}
	
	 function goSearch() {
		var frm = document.searchFrm;
		frm.method = "GET";
		frm.action = "<%= request.getContextPath()%>/list.hello2";
		frm.submit(); 
	}// end of function goSearch(){}--------------------------
	
	
	

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
  <div class="w3-bar-block" style="background-color:#f5f5f5; height: 100%;">
	<div style="margin-left:42px; font-size: 16pt; color:#595959">
	  <br>
	  <a href="<%= ctxPath %>/list.hello2"    class="w3-bar-item w3-button">사내공지</a>
  	</div>
  </div>  
</nav>
<!-- 좌측 고정 상세메뉴 끝 -->


<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin-top:30px">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large w3-animate-opacity" style="margin-bottom: 800px">
    
    <div style="display: flex;">
	<div style="margin: 80px 0 0 260px; padding-left: 3%; display: relative; width: 1600px" >
	

		<span style="color:#0070C0" class="h2"><b>사내공지</b></span>
		
		<%-- === 글검색 폼 추가하기 : 글제목, 글쓴이로 검색한다. === --%>
		<form name="searchFrm" style="margin:0 0 0 860px; font-size: 13pt">
			<select name="searchType" id="searchType" style="height: 33px; border:none; color:#595959">
				<option value="subject">글제목</option>
				<option value="name">글쓴이</option>
			</select>
			<input type="text" name="searchWord" id="searchWord" size="40" autocomplete="off" style="height: 50px;font-size:14pt;  border-radius: 50px; border: 1px solid #595959" placeholder="&ensp;게시글 검색"/>
			<input type="text" style="display: none;"/> <%-- form 태그 내에 input태그가 오로지 1개뿐일 경우에는 엔터를 했을 경우 검색이 되어지므로 이것을 방지하고자 만든 것이다. --%>
			<button class="btn btn-outline-light border ml-1" style=" width:50px; height:50px;  border-radius: 30px; color:black; width:47px;" type="button" onClick="goSearch()">
		    <i class="fa fa-search"></i> </button>
		</form>
		
		<%-- 검색어 입력시 자동글 완성하기--%>
		<div id="displayList" style="z-index:10; position:absolute; background-color:white; border:solid 1px gray; height: 150px; overflow: auto; margin-left: 952px; margin-top: ; border-top: 0px;">
		</div> 
		

	<table  style="width:95%; margin-top:30px" class="table table-hover" id="hovertbl">
		 <thead>
		<tr>
			<th style="width: 7%;">글번호</th>
			<th style="width: 49%;">제목</th>
			<th style="width: 6%;"></th>
			<th style="width: 6%;">성명</th>
			<th style="width: 16%;">날짜</th>
			<th style="width: 8%;">조회수</th>
		</tr>	
		</thead>
		
		<tbody >
		<c:forEach var="boardvo" items="${requestScope.boardList}">
			<tr>
				<td align="center">${boardvo.seq}</td>
				<td align="left">
					<!-- 댓글이 있는 경우 댓글개수 표시하기-->
					<c:if test="${boardvo.commentCount > 0}"> 
					  	<span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}&nbsp;(<span style="color: #0070C0; font-size: 12pt; font-weight: bold;">${boardvo.commentCount}</span>)</span>
					</c:if>			
						
					<c:if test="${boardvo.commentCount == 0}">
						<span class="subject" id="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}</span>
					</c:if>			
				</td>
				<td style="text-align: right">
					<!-- 첨부파일이 있는 경우 아이콘 표시하기 -->
					<c:if test="${not empty boardvo.fileName}">
						<span style="color:gray"><i class="fas fa-paperclip"></i></span>
					</c:if>
				
				</td>
				<td align="center">${boardvo.name}</td>
				<td align="center">${boardvo.regDate}</td>
				<td align="center">${boardvo.readCount}</td>
			</tr>					
	    </c:forEach>	
		</tbody>
	</table>
	
	<%-- === 페이지바 보여주기 === --%>
	<div class="table table-hover" id="hovertbl" align="center" style="width:70%; border: solid 0px gray; margin: 20px auto;">
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

