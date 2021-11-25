<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- fullcalender -->
<link href='<%= ctxPath%>/resources/fullcalendar-5.10.1/lib/main.css' rel='stylesheet' />
<script src='<%= ctxPath%>/resources/fullcalendar-5.10.1/lib/main.js'></script>
<script>

	document.addEventListener('DOMContentLoaded', function() {
	    var calendarEl = document.getElementById('calendar');
	    var calendar = new FullCalendar.Calendar(calendarEl, {
	      initialView: 'dayGridMonth'
	    });
	    calendar.render();
	  });

</script>   




   
<style type="text/css">
	body,h1,h2,h3,h4,h5,h6 {font-family: Verdana, sans-serif;}
	.borderbottom {  border-bottom : solid 1px; }
	.borderright { border-right : solid 1px #d9d9d9; }
	
</style>

<script>
	var now = "";
	var strNow = "";
	var onlytime = "";
	var isRecExist = ${requestScope.isRecordExist};
	var empno = ${requestScope.empno};
	
	$(document).ready(function() {
	  	
		// 타이머 진행
		loopshowNowTime();
	  	
		// 출퇴근버튼 비활성화(경우에따라 활성화)
		$("#inTime").attr("disabled", true);
		$("#outTime").attr("disabled", true);
		
		// 당일기록 존재여부에 따라 달라짐 
		if( isRecExist == 1){ // 기록존재(출근함)
			
			// 출근컬럼값, 퇴근컬럼값 가져오기(퇴근하면 존재, 퇴근안하면 null)
			$.ajax({
		         url:"<%= request.getContextPath()%>/emp/getouttime.hello2",
		         data:{"fk_empno":empno},
		         dataType:"JSON",
		         success:function(json){ 
		              
		        	 var intime = json.intime;
		        	 var outtime = json.outtime;
		        	
		        	 $("span#clock5").html(intime); //출근시간찍기
		        	 
		        	 if(outtime == null){ // 퇴근안함
		        		 $("#outTime").attr("disabled", false); //퇴근버튼비활성화
		        	 }
		        	 else{ 				  // 퇴근함
		        		 $("span#clock6").html(outtime); // 퇴근시간찍기
		        	 }
		            
		         },
		         error: function(request, status, error){
		            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		          }
		      });
		
		}
		else if( isRecExist == 0){ // 기록없음(출근안함)
			// 시간조회해오기(18시이전: 출근버튼만 활성화 /18시이후: 출퇴근버튼비활성화)
			knowTime();
		}
		
		showAttendStatus();
		showCalendar();
		
	}); // end of ready(); ---------------------------------
	
	
	// === 출퇴근관리 === //
	
	// 타이머진행1
	function showNowTime() {
	  
	  now = new Date();
	
	  var month = now.getMonth() + 1;
	  if(month < 10) {
	     month = "0"+month;
	  }
	  
	  var date = now.getDate();
	  if(date < 10) {
	     date = "0"+date;
	  }
	  
	 // strNow = now.getFullYear() + "-" + month + "-" + date;
	  
	  var hour = "";
	   if(now.getHours() < 10) {
	       hour = "0"+now.getHours();
	   } 
	   else {
	      hour = now.getHours();
	   }
	  
	   
	  var minute = "";
	  if(now.getMinutes() < 10) {
	     minute = "0"+now.getMinutes();
	  } else {
	     minute = now.getMinutes();
	  }
	  
	  var second = "";
	  if(now.getSeconds() < 10) {
	     second = "0"+now.getSeconds();
	  } else {
	     second = now.getSeconds();
	  }
	  
	  //onlytime부분 넘겨서 확인하면 될 것 같음
	  // 안넘겨도될것같음.. 출근버튼 클릭시 sql에서 sysdate로 찍으면 되니깐 => 일단 보류(11월 8일)
	 // strNow = "" + hour + ":" + minute + ":" + second;
	  onlytime = "" + hour + ":" + minute + ":" + second;
	  
	  $("span#clock").html(onlytime); // 타이머 보여주기(이것필요함)
	  
	}// end of function showNowTime() -----------------------------
	
	// 타이머진행2
	function loopshowNowTime() {
	  showNowTime();
	  
	  var timejugi = 1000;   // 시간을 1초 마다 자동 갱신하려고.
	  
	  setTimeout(function() {
	              loopshowNowTime();   
	           }, timejugi);
	  
	}// end of loopshowNowTime() --------------------------
	
	
	// 시간찍기   
	function extTime(){
	   $("span#clock2").html(strNow);
	   $("span#clock3").html(onlytime); // 시분초만 뽑아오기 (이것 필요함)
	   
	}// end of function extTime(){}
	
	
	// 시간조회해오기(18시이전:버튼활성화 /18시이후:버튼비활성화)
	function knowTime(){
		
	   var now2 = new Date(); 
	   
	   var hour2 = now2.getHours();
	   var minute2 = now2.getMinutes();
	   var second2 = now2.getSeconds();
	  // console.log("확인용 hour: " + hour2); //확인용 hour: 23
	  // console.log("확인용 minute: " + minute2); //확인용 minute: 36
	  // console.log("확인용 second: " + second2); //확인용 second: 19
	
	  var compTime = hour2 * 60 * 60 + minute2 * 60 + second2 ; // 현재시간
	  var standTime = 18 * 60 * 60; // 기준시간(18시);
	  //console.log("확인용:compTime " + compTime); //확인용:compTime 85108
	  //console.log("확인용:standTime " + standTime); //확인용:compTime 85108
	  
	  if( compTime < standTime){ // 18시 이전인 경우에는 출근버튼만 활성화 시켜줌 
		$("#inTime").attr("disabled", false);
	  }
	  
	   $("span#clock4").html(now2);// 없애도됨
		
	}// end of function knowTime(){}
	
	
	// 출근버튼클릭시
	function checkin(){
		
		 $("span#clock3").html(onlytime);
		
		// service단에서 지각확인 -> 출퇴근테이블 insert -> 출근시간 가져오기
		//console.log("확인용 fk_empno : " + empno);
		//console.log("확인용 onlytime : " + onlytime);
		
		$.ajax({
	         url:"<%= request.getContextPath()%>/emp/clickCheckin.hello2",
	         data:{"fk_empno":empno,
	        	   "onlytime":onlytime},
	         type:"POST",
	         dataType:"JSON",
	         success:function(json){ 

	        	 $("#inTime").attr("disabled", true); // 출근비활성화
	        	 $("#outTime").attr("disabled", false); // 퇴근활성화 
	        	 $("span#clock5").html(json.intime); //출근시간찍기
	        	 
	        	 showAttendStatus();
	        	 showCalendar();
	         },
	         error: function(request, status, error){
	            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	          }
	      });
		
		
		
	}// end of function checkin(){}
	
	
	// 퇴근버튼클릭시
	function checkout(){
	  
		$("span#clock3").html(onlytime);
		
		// service단에서 시간알아오기 -> 출퇴근테이블 update -> 퇴근시간 가져오기
		$.ajax({
	         url:"<%= request.getContextPath()%>/emp/clickCheckout.hello2",
	         data:{"fk_empno":empno,
	        	   "onlytime":onlytime},
	         type:"POST",
	         dataType:"JSON",
	         success:function(json){ 
	        	 
	        	 $("#outTime").attr("disabled", true); // 퇴근활성화 
	        	 $("span#clock5").html(json.intime); //출근시간찍기
	        	 $("span#clock6").html(json.outtime); //출근시간찍기
	        	 
	        	 showAttendStatus();
	        	 showCalendar();
	        	 
	         },
	         error: function(request, status, error){
	            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	          }
	      });
		
		
		
	}// end of function checkout(){}
	
	
	// 해당월의 평일날짜수 보여주기 
	function showAttendStatus(){
		
		$.ajax({
	         url:"<%= request.getContextPath()%>/emp/getAttendStatus.hello2",
	         data:{"fk_empno":empno},
	         dataType:"JSON",
	         success:function(json){ 
	        	 
	        	 $("span#clock7").html(json.status_latein); //당월지각횟수
	        	 $("span#clock8").html(json.status_earlyout); //당월조기퇴근횟수 
	        	 $("span#clock9").html(json.outtimeNullCnt); //당월퇴근미체크횟수 
	        	 $("span#clock10").html(json.absenceCnt); //당월결근횟수
	        	 
	         },
	         error: function(request, status, error){
	            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	          }
	      });
		
		
		
	}
	
	
	// 캘린더에 출근, 퇴근시간 찍어오기
	function showCalendar(){
		
		$.ajax({
	         url:"<%= request.getContextPath()%>/emp/showAttendCalendar.hello2",
	         data:{"fk_empno":empno},
	         dataType:"JSON",
	         success:function(json){ 
	        	 
	        	 var events = []; // 객체 담을 리스트
	        	
	        	 if(json.length > 0) {
	        		 
						$.each(json, function(index, item){
							
							// 출근시간 객체 만들기 
							var eventSrc_intime = {}; // 빈객체 생성
							eventSrc_intime.title = "출근"; // title : "출근"
							eventSrc_intime.start = item.nowdate + "T" + item.intime; // start : "2021-11-08T08:30:00"
							events.push(eventSrc_intime); // 리스트에 객체 넣기 
						
							//퇴근시간  객체 만들기 
							var eventSrc_outtime = {}; // 빈객체 생성
							if( item.hasOwnProperty("outtime")){
								eventSrc_outtime.title = "퇴근"; // title : "퇴근"
								eventSrc_outtime.start = item.nowdate + "T" + item.outtime; // start : "2021-11-08T18:00:00"
								eventSrc_outtime.end = item.nowdate + "T" + "24:00:00";
								eventSrc_outtime.color = "red"; // color : "red" 색깔 빨강으로 나옴 
							}
							
							// 퇴근시간이 있는 경우만 출력
							if(eventSrc_outtime.length !== 0 ){
								events.push(eventSrc_outtime);
							}
						
						});
					}
	        	 
	        	// 캘린더에 출력 
	        	var calendarEl = document.getElementById('calendar');
	        	var calendar = new FullCalendar.Calendar(calendarEl, {
					events : events,
					eventTimeFormat: { // like '14:30:00'
					    hour: '2-digit',
					    minute: '2-digit',
					    hour12: false
					  }
				});
	        	
	        	calendar.render();
	      
	         },
	         error: function(request, status, error){
	            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	          }
	      });
		
	}
	
	
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

<!-- 좌측 상세메뉴 시작 -->
<nav class="w3-sidebar w3-collapse w3-white " style="margin-top:20px; z-index:0; width:300px;background-color:#f5f5f5; overflow: hidden" id="mySidebar"><br>
  <div class="w3-container" style="background-color:#eff4fc; margin-top:10px; color:#595959" >   
   <button type="button" onclick="location.href='<%= ctxPath %>/register.hello2'" class="btn" id="btn1" style="background-color:#0070C0; margin:35px 0 0 14px; font-size:21px; width: 240px; height:63px; color:white">
            <i class="fas fa-plus" style="font-size: 14pt"></i>&nbsp;<b>임직원 등록</b>
   </button> <br>
  </div>
  
  <div class="w3-bar-block" style="background-color:#eff4fc; height: 100% ">   
   <div style="margin-left:50px; font-size: 15pt; color:#595959">
     <br>
     <a href="<%= ctxPath %>/emp/viewEmployee.hello2" class="w3-bar-item w3-button" style="margin: 5px 0 20px 0">
        <i class="fas fa-address-book" style="font-size: 14pt; color:gray"></i>&nbsp;임직원 목록    
     </a>
   </div>
   
   <hr style="color: #d7dde8; height: 1px; background-color:#d7dde8 ">
   
   <div style="margin-left:50px; font-size: 15pt; color:#595959">             
     <h4 style="color:#6d88a4"><i class="fas fa-angle-down"></i>&ensp;근무 관리</h4>
      
      <a href="<%= ctxPath %>/emp/viewAttend.hello2" class="w3-bar-item w3-button">
        <i class="fas fa-calendar-alt" style="font-size: 14pt; color:gray"></i>&nbsp;근무 현황
      </a>
     
      <a href="<%= ctxPath %>/emp/viewAttendOnlyHR.hello2" class="w3-bar-item w3-button">
        <i class="fas fa-calendar-week" style="font-size: 14pt; color:gray"></i>&nbsp;부서 근무 현황
      </a>
     </div>
 </div>
  
</nav>
<!-- 좌측 상세메뉴 끝 -->


<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 50px">
    <h4 id="contact" style="color: gray"><b>자유게시판</b></h4>
   	
    <div style="margin-top:30px">
    
	   	<%-- 나의 근무현황_타이틀 --%>
		<span style="margin:50px 0 25px 50px; display:block; font-size:25pt; font-weight:600; ">[${requestScope.empname}]님의 근태관리</span>
		<hr style="background-color:#80bfff; margin-bottom:70px;">
		
	   	<%-- 나의 근무현황_타이틀 --%>
		<span style="margin:50px 0 30px 50px; font-size:17pt; font-weight: 600; background-color:#eff4fc">나의 근무현황</span>
		
		<%-- 나의 근무현황_영역 --%>
		<div style="min-height: 400px; margin-top: 25px; margin-bottom: 50px; align:center">
			
			<%-- 근무체크  시작--%> 
			<div class="mt-4 ml-5" style="width:700px; height:240px;float:left;">
			   
			   <div class="mb-4" style="text-align:left; width:100%">
			    	<span style="font-size:17px; font-weight: bold;"><i class="far fa-check-square"/></i>&nbsp;&nbsp;근무체크</span>
			   </div>
			    
			   <div class="py-4 px-5" id="attendCheck" style="border:solid 1px #d9d9d9; width:100%; height:250px; border-radius:1%; align:center; text-align:center; font-size: 16pt;">
				   
				    <div class="my-3" style="width: 100%; height:100%">
					    
					    <div style=" vertical-align:middle">
					    	<span id="clock" style="font-size:45px;"></span>
					  	</div>
			
				   		<%--*** 18시 전후따라 출퇴근버튼 활성,비활성화 ***--%>
					    <div style="margin-top:20px;text-align:center; align:center">
					    	 <table style=" width:100%; height: 80%;">
					    	 	<colgroup>
									<col style="width:50%">
									<col style="width:50%">
								</colgroup>
								<tr>	
									<th class="borderright">
										<button id="inTime" style="border:0px; width:100px; height: 40px; border-radius: 10%; background-color: #d9d9d9;" onclick="checkin()"><i class="fas fa-sign-in-alt"></i>&nbsp;출근</button>
									</th>
									<th>
										<button id="outTime" style="border:0px; width:100px; height: 40px; border-radius: 10%; background-color: #d9d9d9;" onclick="checkout()"><i class="fas fa-sign-out-alt"></i>&nbsp;퇴근</button>
									</th>
								</tr>
								<tr>
									<th class="borderright" style="height:46px;">
										&nbsp;<span id="clock5">00:00:00</span>
									</th>
									<th>
										&nbsp;<span id="clock6">00:00:00</span>
									</th>
								</tr>
							</table>
				   	    </div>
	
				  </div>
			   </div>
			   
			</div>
	    	<%-- 근무체크  끝--%>
		  	
		   <%-- 근태현황 시작--%> 
		   <div class="mt-4 mr-5" style="width:700px; height:240px; float:right; veritcal-align: center; ">
		   
			   	<div class="mb-4" style="text-align:left; ">
			    	<span style="font-size:17px; font-weight: bold;"><i class="fas fa-list"></i>&nbsp;&nbsp;근태현황</span>
			    </div>
			    
				<div class="py-4 px-5" id="attendStatus" style="border:solid 1px #d9d9d9; width:100%; height:250px; border-radius:1%; align:center; text-align:center; font-size: 16pt;">  
				    <div class="my-4" style="width: 100%; height:100%; align:center">
					   	 <table style="width:100%; height: 80%; margin">
							<colgroup>
									<col style="width:19%">
									<col style="width:28%">
									<col style="width:34%">
									<col style="width:19%">
						    </colgroup>
							<tr>	
								<th class="borderright" >지각</th>
								<th class="borderright" >조기퇴근</th>
								<th class="borderright" >퇴근미체크</th>
								<th>결근</th>
							</tr>
							<tr>
								<th class="borderright"><span id="clock7" style="font-weight: normal"></span></th>
								<th class="borderright"><span id="clock8" style="font-weight: normal"></span></th>
								<th class="borderright"><span id="clock9" style="font-weight: normal"></span></th>
								<th><span id="clock10" style="font-weight: normal"></span></th>
							</tr>
						</table>
					</div>	
			    </div>
			    
			</div>
			<%-- 근태현황 끝--%>
	   
	</div>
	<%-- 나의 근무현황_영역 끝--%>

	<%-- 이번달 근무현황_타이틀--%>
	  <span style="margin-left:50px; font-size:17pt; font-weight:600; background-color:#eff4fc;"> 이번 달 근무현황</span>
	  
	<%-- 이번달 근무현황_캘린더 영역--%>	
	  <div id='calendar' style="margin:30px 50px;"></div>
	
  </div>
  
  
  
 
 </div>
<!-- !!!!!PAGE CONTENT END!!!!!! -->
</div>

