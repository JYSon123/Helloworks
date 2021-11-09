<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

   
<style type="text/css">
	body,h1,h2,h3,h4,h5,h6 {font-family: Verdana, sans-serif;}
</style>

<script>

	$(document).ready(function(){
	
	
		
	});// end of $(document).ready(function(){})------------------------------

</script>

<!-- 좌측 고정 상세메뉴 시작 -->
<nav class="w3-sidebar w3-collapse w3-white " style="margin-top:20px; z-index:0; width:300px;background-color:#f5f5f5; overflow: hidden" id="mySidebar"><br>
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

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px">

   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 800px">
    <h4 id="contact" style="color: gray"><b>자유게시판</b></h4>
    
    <hr class="w3-opacity">
    <form action="/action_page.php" target="_blank">
      <div class="w3-section">
       
      <c:forEach var ="name" items="${requestScope.nameList}" varStatus="status">
			${name}
	  </c:forEach>
       
      	<button type="submit" class="w3-button w3-black w3-margin-bottom"><i class="fa fa-paper-plane w3-margin-right"></i>Send Message</button>
      </div>
    </form>
  </div>

<!-- !!!!!PAGE CONTENT END!!!!!! -->
</div>

<script>

	// Script to open and close sidebar
	function w3_open() {
	    document.getElementById("mySidebar").style.display = "block";
	    document.getElementById("myOverlay").style.display = "block";
	}
	 
	function w3_close() {
	    document.getElementById("mySidebar").style.display = "none";
	    document.getElementById("myOverlay").style.display = "none";
	}


</script>
