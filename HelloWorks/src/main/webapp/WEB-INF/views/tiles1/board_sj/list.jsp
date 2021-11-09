<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

   
<style type="text/css">
	body,h1,h2,h3,h4,h5,h6 {font-family: Verdana, sans-serif;}
	
	th{
		color:#595959;
		font-weight: normal;
		font-size: 13pt;
		text-align: center;
	}
</style>

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
	
	<button type="button" class="btn" id="btn1" style="background-color:#0070C0; margin-left:14px; font-size:21px; width: 240px; height:63px; color:white">
				<b>글쓰기</b></button>
	<br>
  </div>
  <div class="w3-bar-block" style="background-color:#f5f5f5; height: 100%;">
	<div style="margin-left:42px; font-size: 16pt; color:#595959">
	  <br>
	  <a href="#메일"    class="w3-bar-item w3-button">자유게시판</a>
  	</div>
  </div>  
</nav>
<!-- 좌측 고정 상세메뉴 끝 -->


<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin-top:30px">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 800px;">
    <h4 id="contact" style="color: gray; margin-top: 50px"><b>자유게시판</b></h4>
    
    <div style="display: flex;">
	<div style="margin: auto; padding-left: 3%;">

	<span style="color:gray" class="h2"><b>자유게시판</b></span>
	
	<table style="width: 1024px; margin-top: 30px" class="table table-bordered">
		<thead>
		<tr>
			<th style="width: 70px;">글번호</th>
			<th style="width: 200px;">제목</th>
			<th style="width: 130px;">성명</th>
			<th style="width: 100px;">날짜</th>
			<th style="width: 70px;">조회수</th>
		</tr>	
		</thead>
		
		<tbody>
			<tr>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
			</tr>

		</tbody>
	</table>


	

</div>
</div>
  </div>
  

<!-- !!!!!PAGE CONTENT END!!!!!! -->
</div>

