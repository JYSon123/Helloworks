<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">
	body,h1,h2,h3,h4,h5,h6 {font-family: Verdana, sans-serif;}
	
	button#add_sch {
		display: block;
		width: 80%;
		margin: auto;
		height: 50px;
		font-size: 15pt;
		font-weight: bold;
		color: #fff;
		background-color: #0070C0;
		cursor:pointer;
	}
	
	ul li{
		list-style:none;
	}
	
	a{
		text-decoration: none;
	}
	
	#menu > ul > li > ul {
		width:130px;
		display:none;
		position: absolute;
		font-size:14px;
	}
	
</style>

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

<!-- 좌측 고정 상세메뉴 시작 -->
<nav class="w3-sidebar w3-collapse w3-white " style="margin-top:20px; z-index:0; width:300px;background-color:#f5f5f5; overflow: hidden" id="mySidebar"><br>
  <div class="w3-container" style="background-color:#f5f5f5; margin-top:10px" >
    <a href="#" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu">
      <i class="fa fa-remove"></i>
    </a>
	<br><br>
    <span style="font-size:30pt; margin:100px 0 30px 40px ; color:gray"><b>일정</b></span>
  </div>
  <div class="w3-bar-block" style="background-color:#f5f5f5; height: 100%">
  	<br/>
	  <button type="button" id="add_sch">일정 추가</button>
	  <div id="menu" >
		  <ul>
		  	<li><a href="#">내 캘린더</a>
				<ul>
					<li><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
				</ul>
			</li>
			<li><a href="#">공유 캘린더</a>
				<ul>
					<li><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
				</ul>
			</li>
		</ul>
	</div>
  </div>  
</nav>
<!-- 좌측 고정 상세메뉴 끝 -->


<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px">

  
   
  

<!-- !!!!!PAGE CONTENT END!!!!!! -->
</div>

    
    