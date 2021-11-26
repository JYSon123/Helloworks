<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<% String ctxPath = request.getContextPath(); %> 


<!-- 좌측 고정 상세메뉴 시작 -->
<nav class="w3-sidebar w3-collapse w3-white " style="margin-top:-55px; z-index:0; width:300px;background-color:#f5f5f5; overflow: hidden" id="mySidebar"><br>
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


<html lang='en'>
  <head>
    <meta charset='utf-8' />
    <link href='<%=ctxPath%>/resources/fullcalendar_jy/lib/main.css' rel='stylesheet' />
    <script src='<%=ctxPath%>/resources/fullcalendar_jy/lib/main.js'></script>
    
    
    <script type="text/javascript">
    	
    $(document).ready(function() {
        showCalendar();
        
     }); // end of ready(); ---------------------------------
    
    </script>
    
    
    <script>

	  document.addEventListener('DOMContentLoaded', function() {
	    var calendarEl = document.getElementById('calendar');
	
	    var calendar = new FullCalendar.Calendar(calendarEl, {
	      headerToolbar: {
	        left: 'prevYear,prev,next,nextYear today',
	        center: 'title',
	        right: 'dayGridMonth,dayGridWeek,dayGridDay'
	      },
	      initialDate: '2021-11-15',
	      navLinks: true, // can click day/week names to navigate views
	      editable: true,
	      dayMaxEvents: true, // allow "more" link when too many events
	     
	    });
	
	    calendar.render();
	  });

	    
	  
	  function showCalendar(){
	      
	      $.ajax({
	    	    url:"<%= request.getContextPath()%>/viewBreak2.hello2",
	            dataType:"JSON",
	            success:function(json){ 
	               
	               var events = []; // 객체 담을 리스트
	              
	               if(json.length > 0) {
	                  
	                  $.each(json, function(index, item){
	                     
	                     // 연차반차 객체넣기
	                     var event_braek = {}; // 빈객체 생성
	                     event_braek.title = item.title; // title : "연차"
	                     event_braek.start = item.start; // start : "2021-11-15"
	                     event_braek.end = item.end;
	                     events.push(event_braek); // 리스트에 객체 넣기 
	                     
	                  }); // end of $.each---------- 
	                  
	               }// end of if(json.length > 0)------
	               
	              // 캘린더에 출력 
	              var calendarEl = document.getElementById('calendar');
	              var calendar = new FullCalendar.Calendar(calendarEl, {
	               events : events
	               
	              });// end of new FullCalendar.Calendar(calendarEl,{});-----------
	              
	              calendar.render();
	         
	            }, // end of success:function(json)------
	            
	            error: function(request, status, error){
	               alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	            
	            } // end of error 
	             
	         }); // end of $.ajax({})-------------------
	      
	   } // end of function showCalendar(){}
    
	    
	    
</script>
<style>

  body {
    margin:  0;
    padding: 0;
    font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
    font-size: 14px;
  }

  #calendar {
    max-width: 1500px;
    margin: 0 0 0 -10px;
    width: 950px;
  }

</style>
</head>
<body>

	<div class="row" id='calendar' style="margin: 110px 30px 0px 600px;"></div>

</body>
</html>