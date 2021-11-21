<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@page import="java.util.*"%>
    
<% 
	String ctxPath = request.getContextPath();
	List<Map<String,String>> schList = (ArrayList<Map<String,String>>) request.getAttribute("schList");
	List<Map<String,String>> searchSchList = (ArrayList<Map<String,String>>) request.getAttribute("searchSchList");
%>  
  

<link href='<%=request.getContextPath() %>/resources/lib/main.css' rel='stylesheet' />
<style>
	body {
		margin: 40px 10px;
		padding: 0;
		font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
		font-size: 14px;
	}
	
	#calendar {
		max-width: 1100px;
		margin: 0 auto;
		z-index: 1;	/* 수직정렬: 어느 요소가 앞으로 올지 (1번째로 올라옴) */ 
	}
	
	h1, h2, h3, h4, h5, h6 {
		font-family: Verdana, sans-serif;
	}
	
	button#addSch {
		display: block;
		margin: auto;
		width: 80%;
		height: 50px;
		font-size: 15pt;
		font-weight: bold;
		color: #fff;
		background-color: #0070C1;
		cursor:pointer;
	}
	
	ul {
		list-style-type: none;
		margin: 0;
		padding: 0;
	}
	
	#catg {
		font-size: 15pt;
		font-weight: bold;
		color: gray;
		margin-bottom: 20px;
	}	
	
	ul #subcatg{
		padding: 10px 10px;
		border: solid 3px #e6e6e6;
		border-collapse: collapse;
		background-color: #fff;
		border-radius: 10px; 
	}
	
	#subcatg li {
		margin-bottom: 5px;
	}
	
	.modal {
		position:fixed;
		top:50%; 
		left:50%;
		transform: translate(-50%,-50%);
	}
	
	i {
		color: #999999;
	}
	
	#title {
		font-weight: bold;
		vertical-align: top; 
		padding-top: 7px;
	} 
	table {
		border-collapse: separate;
  		border-spacing: 0 10px;
	}
	.star {
		color: #b4c5e4;
		font-weight: bold;
		font-size: 13pt;
	}
	
	div#displayList {
		position: absolute;
		background-color: #fff;
		z-index: 2;	/* 수직정렬: 어느 요소가 앞으로 올지 (2번째로 올라옴) */ 
		border: solid 1px gray;
		border-bottom-left-radius: 10px;
		border-bottom-right-radius: 10px;
		width: 991px;
		height: 150px; 
		overflow: auto; 
		margin-left: 75px; 
		margin-top: -15px; 
		border-top: 0px;
		padding: 20px;
	}
	
	#tblSearch {
		width: 96%;
		margin: 50px auto; 
		text-align: center;
		border-collapse: collapse;
		border-radius: 10px;
		border-style: hidden;
		box-shadow: 0 0 0 2px #e6e6e6;
	}
	
	#tblSearch th, #tblSearch td {
		border: solid 1px #e6e6e6;
		padding: 7px 0;
	}
	
	a:hover { 
		text-decoration:none;
		cursor: pointer;		
	}
	
</style>

<script src='<%=request.getContextPath() %>/resources/lib/main.js'></script>
<script>
  
  var scheduleList ="";	
  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
      width: '100%',
      headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
      },
//       initialDate: '2020-09-12',
      navLinks: true, // can click day/week names to navigate views
      businessHours: true, // display business hours
      editable: true,
      selectable: true,
      locale: "ko",
      events: [
    	  <% 
    	  if(schList != null) {
    	  		for( Map<String,String> map : schList) {
    	  		
    	  %>
	    	  		{
	    	  			title: "<%= map.get("title") %>",
	    	  			start: "<%= map.get("startdate") %>",
	    	  			end: "<%= map.get("enddate") %>",
	    	  			color: "<%= map.get("color") %>",
	    	  		},
    	  
    	  <% 
	    	  	}
    	  	}
    	  %>
    	  <% 
    	  	if(searchSchList != null) {
    	  		for( Map<String,String> map : searchSchList) {
    	  		
    	  %>
	    	  		{
	    	  			title: "<%= map.get("title") %>",
	    	  			start: "<%= map.get("startDate") %>",
	    	  			end: "<%= map.get("endDate") %>",
	    	  			color: "<%= map.get("color") %>",
	    	  		},
    	  
    	  <% 
    	  		}
    	  	}
    	  %>
    	  {
    		  title: "default",
    		  start: "1998-01-21",
    		  end: "1998-01-21"
    	  }
    	  
      ]
    });

    calendar.render();
  });
	
  
  	$(document).ready(function(){

  		// 검색타입이 날짜로 변경되었을 때 input타입 변경
  		$("select#searchType").change(function(){
	  		if ( $("select#searchType option:selected").val() == "term" ) {
	  			$("input#searchWord").prop("type", "hidden");
	  			$("input#startDate").prop("type", "date");
	  			$("input#endDate").prop("type", "date");
	  		}
	  		else {
	  			$("input#searchWord").prop("type", "text");
	  			$("input#startDate").prop("type", "hidden");
	  			$("input#endDate").prop("type", "hidden");
	  		}
  		});
  		
  		// 일정 카테고리 접었다 펴기
  		$("span#upNdown").click(function(){
            if($(this).parent().find("#subcatg").is(":visible")){
                $(this).parent().find("#subcatg").slideUp();
                $(this).parent().children().first().removeClass("fa-caret-down");
                $(this).parent().children().first().addClass("fa-caret-right");
                
            }
            else{
                $(this).parent().find("#subcatg").slideDown();
                $(this).parent().children().first().removeClass("fa-caret-right");
                $(this).parent().children().first().addClass("fa-caret-down");
            }
        });
  		 	
  		// 엔터 입력 시
  		$("input#p_calname").bind("keyup", function(event) {
			if(event.keyCode == 13) { 
				addPersonal();
			}
		});
  		$("input#s_calname").bind("keyup", function(event) {
			if(event.keyCode == 13) { 
				addShare();
			}
		});
  		
  		// 전체 캘린더 리스트 받아오기
  		showCalendarList();
  		
  		// 알림 체크박스 체크 시 select 태그 보이게
  		$("input[name=noticeChk]").change(function(){
  	        if($(this).is(":checked")){
  	          	$(this).parents().siblings('select').css("visibility","visible");
  	        }
  	        else{
  	          	$(this).parents().siblings('select').css("visibility","hidden");
  	        }
  	    });
  		
  		// 하루종일 선택 시, 시간 선택 못하게
  		$("input:checkbox[name='allDay']").change(function(){
  	        if($(this).is(":checked")){
  	        	$("input[type=time]").attr('disabled', true); 
  	        	$("input[type=time]").val(""); 
  	        }
  	        else{
  	        	$("input[type=time]").attr('disabled', false); 
  	        }
  		});
  		
  		// 검색 시, 검색조건 및 검색어 값 유지시키기
		if( ${not empty requestScope.paraMap.searchType} ) {
			$("select#searchType").val("${requestScope.paraMap.searchType}");
			$("input#searchWord").val("${requestScope.paraMap.searchWord}");
			$("input#startDate").val("${requestScope.paraMap.startDate}");
			$("input#endDate").val("${requestScope.paraMap.endDate}");
		}
  		
  		// 검색내용에 일치하는 내용이 없을 경우 alert 띄워주기
  		<%
  		if( searchSchList != null && searchSchList.size() == 0 ) {
  		%>
  			alert("검색어에 해당하는 일정이 존재하지 않습니다.");
  		<%
  		}
  		%>
  		
  		// 엔터 입력시 검색하기
  		$("input#searchWord").keyup(function(){
  			if(event.keyCode == 13) { 
				goSearch();
			}
  		});
  		$("input#endDate").keyup(function(){
  			if(event.keyCode == 13) { 
  				goSearch();
			}
  		});
  		
  		
  		$("div#displayList").hide();	// 처음엔 감추고, searchWord에 keyup일 경우 보이기

		$("input#searchWord").keyup(function(){
			
			var wordLength = $(this).val().trim().length;
			// 검색어 길이를 알아온다.
			
			if(wordLength == 0) {
				$("div#displayList").hide();
				// 검색어가 공백이거나 검색어 입력후 백스페이스키를 눌러서 검색어를 모두 지우면 검색된 내용이 안 나오도록 해야 한다.
			}
			else {
				
				$.ajax({
					url:"<%=request.getContextPath()%>/autoSearchWord.hello2",
					type:"GET",
					data:{"searchType":$("select#searchType").val()
						 ,"searchWord":$("input#searchWord").val()
						 ,"empid":"${requestScope.paraMap.empid}"},
					dataType:"JSON",
					success:function(json){
						
						if(json.length > 0) {
							//검색된 데이터가 있는 경우
							
							var html = "";
							
							$.each(json, function(index, item){
								var word = item.word;
								// word ==> JAVA script를 배워요
								
								var index = word.toLowerCase().indexOf($("input#searchWord").val().toLowerCase())
								// word ==> java script를 배워요		java
								// 검색어(jAvA)가 나오는 index ==> 1
								
								var len = $("input#searchWord").val().length;
								// 검색어(jAvA)의 길이 len ==> 4
								
								var result = word.substr(0, index) + "<span style='color:blue; font-weight: bold;'>"+word.substr(index,len)+"</span>"+ word.substr(index+len);
								
								html += "<span style='cursor: pointer;' class='result' >"+result+"</span><br>";
								
							});
							
							var input_width = $("input#searchWord").css("width");	// 검색어 input태그 width 알아오기
							
							$("div#displayList").css({"width":input_width});	// 검색결과 div의 width 크기를 검색어 input 태그 width와 일치시키기
							
							$("div#displayList").html(html);
							$("div#displayList").show();
						}
						
					},
					error: function(request, status, error){
		                  alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		            }
				});
			}
			
		});
  		
		$(document).on("click",".result",function(){
			var word = $(this).text();
			$("input#searchWord").val(word);	// 텍스트박스에 검색된 결과의 문자열을 입력해준다.
			$("div#displayList").hide();
			goSearch();
		});
		
		
  		
  		
  	}); // end of $(document).ready(function(){}
  	
	// Function Declaration
	function w3_open() {
		document.getElementById("mySidebar").style.display = "block";
		document.getElementById("myOverlay").style.display = "block";
	}

	function w3_close() {
		document.getElementById("mySidebar").style.display = "none";
		document.getElementById("myOverlay").style.display = "none";
	}
	
	
	// 내 캘린더 추가하기
	function addPersonal() {
		
		// 캘린더 이름 유효성 검사
		if( $("input#p_calname").val().trim() == "" ) {
			
			alert("캘린더 이름을 입력해주세요!");
			return;
		}
		
		var frm = document.personalFrm;
		
		frm.action ="<%=ctxPath%>/addCalendar.hello2";
		frm.method = "POST";
		frm.submit();
		
	}
	
	// 공유 캘린더 추가하기
	function addShare() {
		
		// 캘린더 이름 유효성 검사
		if( $("input#s_calname").val().trim() == "" ) {
			
			alert("캘린더 이름을 입력해주세요!");
			return;
		}
		
		// 공유인원 유효성 검사
		if( $("input#s_shareEmp").val().trim() == "" ) {
			
			alert("캘린더를 공유할 인원을 입력해주세요!");
			return;
		}
		
		var frm = document.shareFrm;
		
		frm.action ="<%=ctxPath%>/addCalendar.hello2";
		frm.method = "POST";
		frm.submit();
		
	}

	// 개인 캘린더 수정 및 삭제 모달창에 값 보내주기
	function gochangepersonalmodal(obj) {
		var index = $("button.my_btn").index(obj);
		//alert(index);
		
		var calno =  $("input.my_calno").eq(index).val();
		var calname =  $("input.my_calname").eq(index).val();
		var color =  $("input.my_color").eq(index).val();
		
 		//alert("calname : " + calname + ", color : " + color);
		
		$("input#calno").val(calno); 
		$("input#updateCalname").val(calname); 
		$("input#updateColor").val(color); 
		
		$("#changePersonalModal").modal();
	}

	// 공유 캘린더 수정 및 삭제 모달창에 값 보내주기
	function gochangesharemodal(obj) {
		var index = $("button.our_btn").index(obj);
		//alert(index);
		
		var calno =  $("input.our_calno").eq(index).val();
		var calname =  $("input.our_calname").eq(index).val();
		var color =  $("input.our_color").eq(index).val();
		var shareemp =  $("input.our_shareemp").eq(index).val();
		
 		// alert("shareemp : " + shareemp );
		
		$("input#calno").val(calno); 
		$("input#updateCalname").val(calname); 
		$("input#updateColor").val(color); 
		$("input#updateShareEmp").val(shareemp); 
		
		$("#changeShareModal").modal();
	}
	
	// Ajax를 사용한 전체 캘린더 리스트 받아오기
	function showCalendarList(){
		
		$.ajax({
			url: "<%=ctxPath %>/showCalendarList.hello2",
			type: "POST",
			dataType: "JSON",
			success:function(json){
				
				var phtml = "";
				var shtml = "";
				
				$.each(json, function(index, item){

					if (item.fk_cno == 1) {
					
						phtml += "<li><span style='color:"+item.color+";'>■ </span>" +
								 "<input type='hidden' class='my_calno' name='calno' value='"+item.calno+"' />"+
								 "<input type='hidden' class='my_calname' name='calname' value='"+item.calname+"' />"+
								 "<input type='hidden' class='my_color' name='color' value='"+item.color+"' />"+
// 								 "<button type='button' class='btn' data-toggle='modal' data-target='#changePersonalModal' data-dismiss='modal'>"+item.calname+"</button>"+
								 "<button type='button' class='my_btn btn' onclick='gochangepersonalmodal(this)'>"+item.calname+"</button>"+
								 "</li>";
					}
					else if (item.fk_cno == 2) {
						
						shtml += "<li><span style='color:"+item.color+";'>■ </span>" +
								 "<input type='hidden' class='our_calno' name='calno' value='"+item.calno+"' />"+
								 "<input type='hidden' class='our_calname' name='calname' value='"+item.calname+"' />"+
								 "<input type='hidden' class='our_color' name='color' value='"+item.color+"' />"+
								 "<input type='hidden' class='our_shareemp' name='shareemp' value='"+item.shareemp+"' />"+
		//						 "<button type='button' class='btn' data-toggle='modal' data-target='#changePersonalModal' data-dismiss='modal'>"+item.calname+"</button>"+
								 "<button type='button' class='our_btn btn' onclick='gochangesharemodal(this)'>"+item.calname+"</button>"+
						 		 "</li>";
					}
					
				});
				
				$("ul.personalList").html(phtml);
				$("ul.shareList").html(shtml);
				
			},
			error: function(request, status, error){
	            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	        }	
		});
		
	}// end of function showCalendarList(){}
	
	function addSchedule() {
		
		var obj = $("[name=notice]");
        var chkArray = new Array();
 		
        // notice(message, email) 배열로 넘겨주기
        $("input:checkbox[name=noticeChk]:checked").each(function() { 
            chkArray.push(this.value);
        });
        $('#notice').val(chkArray);
        
        var boolFlag = false;

  		// 유효성 검사 하기
  		$("input.requiredInfo").each(function(index,item){
			 var data = $(this).val().trim();
			 if(data == "") {
				alert("* 표시된 필수입력사항을 모두 입력해주세요.");
				boolFlag = true;
				return false;
			 }
		});
  		
		if( ($("#startTime").val()=="" || $("#endTime").val()=="") && !$("input:checkbox[name='allDay']").is(":checked") ) {
			alert("시간은 필수입력사항입니다.");
			boolFlag = true;
			return false;
		}
  		

		if(boolFlag) {
			return;
		}
				
		var frm = document.addSchFrm;
		
		frm.action ="<%=ctxPath%>/addSchedule.hello2";
		frm.method = "POST";
		frm.submit();
		
	}
	
	// 개인캘린더 모달창 수정 및 삭제
	function changePersonal(i){
		if (i==1) {	// 수정일 경우 changeOption의 value값을 1로 준다.
			$("input#changePOption").val("1"); 
		}
		if (i==2) { // 삭제일 경우 changeOption의 value값을 2로 준다.
			
			var result = confirm("공유 캘린더를 정말로 삭제하시겠습니까?");
			if(result == true) {
				$("input#changePOption").val("2");
			}
			else {
				$("#changePersonalModal").modal('hide');
			}
		}
		
		var frm = document.changePersonalFrm;
		
		frm.action ="<%=ctxPath%>/changePersonal.hello2";
		frm.method = "POST";
		frm.submit();
	}
	
	// 공유캘린더 모달창 수정 및 삭제
	function changeShare(i){
				
		if (i==1) {	// 수정일 경우 changeOption의 value값을 1로 준다.
			$("input#changeSOption").val("1"); 
		}
		if (i==2) { // 삭제일 경우 changeOption의 value값을 2로 준다.
			
			var result = confirm("공유 캘린더를 정말로 삭제하시겠습니까?");
			if(result == true) {
				$("input#changeSOption").val("2"); 
			}
			else {
				$("#changeShareModal").modal('hide');
			}
			 
		}
		
		var frm = document.changeShareFrm;
		
		frm.action ="<%=ctxPath%>/changeShare.hello2";
		frm.method = "POST";
		frm.submit();
	}
	
	// 검색
	function goSearch(){
		var frm = document.searchFrm;
		frm.action="<%=request.getContextPath()%>/schedule.hello2";
		frm.method="GET";
		frm.submit();
	}
	
	// 검색결과 테이블 클릭
	function changeScheduleModal(index){
		
		var sno = $("td.searchSno").eq(index).text();
		var title = $("td.searchTitle").eq(index).text();
		var content = $("td.searchContent").eq(index).text();
		var location = $("td.searchLocation").eq(index).text();
		var startDay = $("td.searchStartDate").eq(index).text().substring(0,10);
		var startTime = $("td.searchStartDate").eq(index).text().substring(11);
		var endDay = $("td.searchEndDate").eq(index).text().substring(0,10);
		var endTime = $("td.searchEndDate").eq(index).text().substring(11);
		var status = $("td.searchStatus").eq(index).text();
		
		$("input#c_sno").val(sno);
		$("input#c_title").val(title);
		$("input#c_content").val(content);
		$("input#c_location").val(location);
		$("input#c_startDay").val(startDay);
		$("input#c_startTime").val(startTime);
		$("input#c_endDay").val(endDay);
		$("input#c_endTime").val(endTime);
		$("input#c_status").val(status); 
		
		$("#changeSchModal").modal();
	}
	
	// 일정 수정 및 삭제
	function changeSch(i){
		if (i==1) {	// 수정일 경우 changeOption의 value값을 1로 준다.
			$("input#changeSchOption").val("1"); 
		}
		if (i==2) { // 삭제일 경우 changeOption의 value값을 2로 준다.
			var result = confirm("일정을 정말로 삭제하시겠습니까?");
			if(result == true) {
				$("input#changeSchOption").val("2"); 
			}
			else {
				$("#changeSchModal").modal('hide');
			}
		}
		
		var obj = $("[name=notice]");
        var chkArray = new Array();
 		
        // notice(message, email) 배열로 넘겨주기
        $("input:checkbox[name=noticeChk]:checked").each(function() { 
            chkArray.push(this.value);
        });
        $('#c_notice').val(chkArray);
		
		var frm = document.changeSchFrm;
		
		frm.action ="<%=ctxPath%>/changeSchedule.hello2";
		frm.method = "POST";
		frm.submit();
	}
	
</script>


<div>
	<%-- 사이드바 시작 --%>
	<nav class="w3-sidebar w3-collapse w3-white " style="margin-top: 20px; z-index: 0; width: 300px; background-color: #f5f5f5; overflow: auto; height: 100%;" id="mySidebar">
		<br>
		<div class="w3-container" style="background-color: #f5f5f5; margin-top: 10px">
			<a href="#" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu"><i class="fa fa-remove"></i></a> <br><br>
			<span style="font-size: 30pt; margin: 100px 0 30px 40px; color: gray"><b><a href="<%= ctxPath %>/schedule.hello2">일정</a></b></span>
		</div>
		<div class="w3-bar-block" style="background-color: #f5f5f5; height: 100%"><br>
			
			<%-- 일정추가 모달창 버튼 --%>
			<button type="button" id="addSch" class="btn" data-toggle="modal" data-target="#addSchModal" data-dismiss="modal" >일정 추가</button>
			
			<%-- 캘린더 카테고리--%>
			<div style="width: 80%; margin: 30px auto;">
				<ul>
					<li id="catg">
						<i class="fas fa-caret-right" ></i> &nbsp; <span id="upNdown" style="cursor:pointer;">개인 캘린더</span>
						<button type="button" style="float: right;" class="btn"  data-toggle="modal" data-target="#addPersonalModal" data-dismiss="modal" ><i class="fas fa-plus"></i></button>
						<ul id="subcatg" class="personalList" style="display:none">
							
						</ul>
						
					</li>
					<li id="catg">
						<i class="fas fa-caret-right"></i> &nbsp; <span id="upNdown"  style="cursor:pointer;">공유 캘린더</span>
						<button type="button" style="float: right;" class="btn" data-toggle="modal" data-target="#addShareModal" data-dismiss="modal" ><i class="fas fa-plus"></i></button>
						<ul id="subcatg" class="shareList" style="display:none">
							
						</ul>
					</li>
				</ul>
			</div>
			
		</div>
	</nav>
	<%-- 사이드바 끝 --%>

	<%-- 메인 컨텐츠 시작 --%>
	<div class="w3-container w3-padding-large" style="margin: 30px 0 50px 300px">
		<div style="margin: 100px auto 70px auto; width: 96%;">
			
			<%-- 검색 --%>
			<form name="searchFrm">
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<select class="form-control" id="searchType" name="searchType">
							<option value="title" selected>제목</option>
							<option value="content">내용</option>
							<option value="term">기간</option>
						</select>
					</div>
					<input type="text" style="display:none" />
					<input type="text" id="searchWord" name="searchWord" class="form-control" />
					<input type="hidden" id="startDate" name="startDate" class="form-control" />
					<input type="hidden" id="endDate" name="endDate" class="form-control" />
					<div class="input-group-append">
						<button type="button" class="btn btn-outline-secondary" onclick="goSearch()">
							<i class="fas fa-search"></i>
						</button>
					</div>
				</div>
				
				<%-- 자동 완성 --%>
				<div id="displayList"></div>

			</form>
		</div>
		
		<%-- 캘린더 --%>
		<div id='calendar' style="width: 100%"></div>
		
		<%-- 검색 결과 테이블로 출력 --%>
		<%  if(searchSchList != null) { %>
			<table id="tblSearch">
				<thead >
					<tr>
						<th width="20%">일정명</th>
						<th width="33%">내용</th>
						<th width="10%">장소</th>
						<th width="15%">시작일</th>
						<th width="15%">종료일</th>
						<th width="7%">진행상황(%)</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="sch" items="${requestScope.searchSchList}" varStatus="status">
						<tr class="tblSearchList" onclick="changeScheduleModal(${status.index})">
							<td class="searchSno" style="display: none;">${sch.sno }</td>
							<td class="searchTitle">${sch.title}</td>
							<td class="searchContent">${sch.content}</td>
							<td class="searchLocation">${sch.location}</td>
							<td class="searchStartDate">${fn:substring(sch.startDate,0,10)} ${fn:substring(sch.startDate,11,16)}</td>
							<td class="searchEndDate">${fn:substring(sch.endDate,0,10)} ${fn:substring(sch.endDate,11,16)}</td>
							<td class="searchStatus">${sch.status}</td>
						</tr>
					
					</c:forEach>
				</tbody>
			</table>
		<%  } %>
		
		
	</div>
	<%-- 메인 컨텐츠 끝 --%>
	
	<%-- 일정추가 모달창 시작 --%>
	<div class="modal fade" id="addSchModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">

				<%-- Modal header --%>
				<div class="modal-header" style="background-color: #0070C1;">
					<h4 class="modal-title" style="color: #fff; font-weight: bold;">일정추가</h4>
					<button type="button" class="close myclose" data-dismiss="modal" style="color: #fff;">&times;</button>
				</div>

				<%-- Modal body --%>
				<div class="modal-body">
					<div id="addSch">
						<form name="addSchFrm">
							<table id= "tblAddSchedule" class="w-90 mx-auto">
								<tbody>
									<tr>
										<td style="width: 20%;" id="title">캘린더&nbsp;<span class="star">*</span></td>
										<td style="width: 80%; text-align: left;">
										<select id="fk_calno" name="fk_calno" style="width: 100%" class="form-control requiredInfo">
											<c:forEach var="cal" items="${requestScope.calList}">
								               <option value="${cal.calno}">${cal.calname}</option>
								            </c:forEach>
										</select>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">일정 제목&nbsp;<span class="star">*</span></td>
										<td style="width: 80%;">
											<input type="text" id="title" name="title" class="form-control requiredInfo" />
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">시작&nbsp;<span class="star">*</span></td>
										<td style="width: 99%; margin-left: 1px;" class="row">
											<input type="date" id="startDay" name="startDay" class="form-control col-7 requiredInfo" />
											<input type="time" id="startTime" name="startTime" class="form-control col-5" />
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">종료&nbsp;<span class="star">*</span></td>
										<td style="width: 99%; margin-left: 1px;" class="row">
											<input type="date" id="endDay" name="endDay" class="form-control col-7 requiredInfo" />
											<input type="time" id="endTime" name="endTime" class="form-control col-5" />
										</td>
									</tr>
									<tr>
										<td style="width: 100%; padding-left: 68%;" colspan="2">
											<label class="mt-2" for="message"><input type="checkbox" name="allDay" value="true"/>하루종일</label>
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">장소&nbsp;</td>
										<td style="width: 80%;">
											<input type="text" id="location" name="location" class="form-control" />
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">내용&nbsp;</td>
										<td style="width: 80%;"><textarea id="content" name="content" rows="4" cols="80"  class="form-control"> </textarea></td>
									</tr>
									<tr>
										<td rowspan="2" style="width: 20%;" id="title" >알림&nbsp;</td>
										<td style="width: 80%; margin-left: 1px;" class="row">
											<label class="mt-2 col-5" for="message"><input type="checkbox" id="message" name="noticeChk" value="message"/> 문자</label>
											<select id="mnoticeTime" name="mnoticeTime" class="form-control col-7" style="visibility: hidden;">
<!-- 												<option value="0" selected>== 시간선택 ==</option> -->
												<option value="1" selected>30분 전</option>
												<option value="2">1시간 전</option>
												<option value="3">하루 전</option>
											</select>
										</td>
									</tr>
									<tr>					
										<td style="width: 80%; margin-left: 1px;" class="row">
											<label class="mt-2 col-5" for="email"><input type="checkbox" id="email" name="noticeChk" value="email" /> 이메일</label>
											<input type="hidden" name="notice" id="notice" value=""/>
											<select id="enoticeTime" name="enoticeTime" class="form-control col-7" style="visibility: hidden;">
<!-- 												<option value="0" selected>== 시간선택 ==</option> -->
												<option value="1" selected>30분 전</option>
												<option value="2">1시간 전</option>
												<option value="3">하루 전</option>
											</select>
										</td>
									</tr>
					
								</tbody>
							</table>
						</form>
					</div>
				</div>

				<%-- Modal footer --%>
				<div class="modal-footer">
					<button type="button" class="btn myclose" data-dismiss="modal" style="background-color: #c10000; color: #fff;">Close</button>
					<button type="button" class="btn" style="background-color: #0070C1; color: #fff;" onclick="addSchedule()">Add</button>
				</div>
			</div>

		</div>
	</div>
	<%-- 일정추가 모달창 끝 --%>
	
	<%-- 일정 수정 및 삭제 모달창 시작 --%>
	<div class="modal fade" id="changeSchModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">

				<%-- Modal header --%>
				<div class="modal-header" style="background-color: #0070C1;">
					<h4 class="modal-title" style="color: #fff; font-weight: bold;">일정 수정 및 삭제</h4>
					<button type="button" class="close myclose" data-dismiss="modal" style="color: #fff;">&times;</button>
				</div>

				<%-- Modal body --%>
				<div class="modal-body">
					<div id="changeSch">
						<form name="changeSchFrm"  oninput="x.value=parseInt(c_status.value)" >
							<table id= "tblChangeSchedule" class="w-90 mx-auto">
								<tbody>
									<tr>
										<td style="width: 20%;" id="title">일정 제목&nbsp;<span class="star">*</span></td>
										<td style="width: 80%;">
											<input type="hidden" id="c_sno" name="sno" class="form-control" value="" />
											<input type="text" id="c_title" name="title" class="form-control" value="" />
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">시작&nbsp;<span class="star">*</span></td>
										<td style="width: 99%; margin-left: 1px;" class="row">
											<input type="date" id="c_startDay" name="startDay" class="form-control col-7" value="" />
											<input type="time" id="c_startTime" name="startTime" class="form-control col-5" value="" />
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">종료&nbsp;<span class="star">*</span></td>
										<td style="width: 99%; margin-left: 1px;" class="row">
											<input type="date" id="c_endDay" name="endDay" class="form-control col-7" value="" />
											<input type="time" id="c_endTime" name="endTime" class="form-control col-5" value="" />
										</td>
									</tr>
									<tr>
										<td style="width: 100%; padding-left: 68%;" colspan="2">
											<label class="mt-2" for="message"><input type="checkbox" name="allDay" value="true"/>하루종일</label>
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">장소&nbsp;</td>
										<td style="width: 80%;">
											<input type="text" id="c_location" name="location" class="form-control" value="" />
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">진행상황&nbsp;</td>
										<td style="width: 99%; margin-left: 1px;" class="row">
											<input type="hidden" id="changeSchOption" name="changeOption" class="form-control" value="" />
											<input type="range" id="c_status" name="status" style="width: 85%;" min="0" max="100" step="5" value=""/>
											<output name="x" for="c_status" style="font-weight: bold; margin-left: 28px;" class="w-15 pt-2"></output>
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">내용&nbsp;</td>
										<td style="width: 80%;"><textarea id="c_content" name="content" rows="4" cols="80"  class="form-control" value=""> </textarea></td>
									</tr>
									<tr>
										<td rowspan="2" style="width: 20%;" id="title" >알림&nbsp;</td>
										<td style="width: 80%; margin-left: 1px;" class="row">
											<label class="mt-2 col-5" for="message"><input type="checkbox" id="c_message" name="noticeChk" value="message"/> 문자</label>
											<select id="c_mnoticeTime" name="mnoticeTime" class="form-control col-7" style="visibility: hidden;">
<!-- 												<option value="0" selected>== 시간선택 ==</option> -->
												<option value="1" selected>30분 전</option>
												<option value="2">1시간 전</option>
												<option value="3">하루 전</option>
											</select>
										</td>
									</tr>
									<tr>					
										<td style="width: 80%; margin-left: 1px;" class="row">
											<label class="mt-2 col-5" for="email"><input type="checkbox" id="c_email" name="noticeChk" value="email" /> 이메일</label>
											<input type="hidden" name="notice" id="c_notice" value=""/>
											<select id="c_enoticeTime" name="enoticeTime" class="form-control col-7" style="visibility: hidden;">
<!-- 												<option value="0" selected>== 시간선택 ==</option> -->
												<option value="1" selected>30분 전</option>
												<option value="2">1시간 전</option>
												<option value="3">하루 전</option>
											</select>
										</td>
									</tr>
					
								</tbody>
							</table>
						</form>
					</div>
				</div>

				<%-- Modal footer --%>
				<div class="modal-footer">
					<button type="button" class="btn" style="background-color: #0070C1; color: #fff;"  onclick="changeSch(1)">수정</button>
					<button type="button" class="btn" style="background-color: #c10000; color: #fff;"  onclick="changeSch(2)">삭제</button>
					<button type="button" class="btn btn-outline-secondary myclose" data-dismiss="modal" >닫기</button>
				</div>
			</div>

		</div>
	</div>
	<%-- 일정 수정 및 삭제 모달창 끝 --%>
	
	<%-- 개인캘린더 모달창 시작 --%>
	<div class="modal fade" id="addPersonalModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">

				<%-- Modal header --%>
				<div class="modal-header" style="background-color: #0070C1;">
					<h4 class="modal-title" style="color: #fff; font-weight: bold;">개인 캘린더 추가</h4>
					<button type="button" class="close myclose" data-dismiss="modal" style="color: #fff;">&times;</button>
				</div>

				<%-- Modal body --%>
				<div class="modal-body">
					<div id="addPersonal">
						<form name="personalFrm">
							<table id= "tblCalendar" class="w-90 mx-auto">
								<tbody>
									<tr>
										<td style="width: 20%;" id="title">캘린더 이름&nbsp;</td>
										<td style="width: 80%; text-align: left;">
											<%-- enter 이벤트 막기 --%>
											<input type="text" name="none" style="display:none" />
											<input type="text" id="p_calname" name="calname" class="form-control" />
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">색상&nbsp;</td>
										<td style="width: 80%;">
											<input type="color" id="p_color" name="color" class="form-control form-control-color col-2" value="#e0f2ff" />
										</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>

				<%-- Modal footer --%>
				<div class="modal-footer">
					<button type="button" class="btn myclose" data-dismiss="modal" style="background-color: #c10000; color: #fff;">Close</button>
					<button type="button" class="btn" style="background-color: #0070C1; color: #fff;"  onclick="addPersonal()">Add</button>
				</div>
			</div>

		</div>
	</div>
	<%-- 개인캘린더 모달창 끝 --%>
	
	<%-- 개인캘린더 수정 및 삭제 모달창 시작 --%>
	<div class="modal fade" id="changePersonalModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">

				<%-- Modal header --%>
				<div class="modal-header" style="background-color: #0070C1;">
					<h4 class="modal-title" style="color: #fff; font-weight: bold;">개인캘린더 수정 및 삭제</h4>
					<button type="button" class="close myclose" data-dismiss="modal" style="color: #fff;">&times;</button>
				</div>

				<%-- Modal body --%>
				<div class="modal-body">
					<div id="changePersonal">
						<form name="changePersonalFrm">
							<table id= "tblCalendar" class="w-90 mx-auto">
								<tbody>
									<tr>
										<td style="width: 20%;" id="title">캘린더 이름&nbsp;</td>
										<td style="width: 80%; text-align: left;">
											<input type="hidden" id="calno" name="calno" value="" />
											<input type="text" id="updateCalname" name="calname" class="form-control" value=""/>
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">색상&nbsp;</td>
										<td style="width: 80%;">
											<input type="color" id="updateColor" name="color" class="form-control form-control-color col-2" value="" />
											<input type="hidden" id="changePOption" name="changeOption" value="" />
										</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>

				<%-- Modal footer --%>
				<div class="modal-footer">
					<button type="button" class="btn" style="background-color: #0070C1; color: #fff;"  onclick="changePersonal(1)">수정</button>
					<button type="button" class="btn" style="background-color: #c10000; color: #fff;"  onclick="changePersonal(2)">삭제</button>
					<button type="button" class="btn btn-outline-secondary myclose" data-dismiss="modal" >닫기</button>
				</div>
			</div>
		</div>
	</div>
	<%-- 개인캘린더 수정 및 삭제 모달창 끝 --%>
	
	<%-- 공유캘린더 모달창 시작 --%>
	<div class="modal fade" id="addShareModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">

				<%-- Modal header --%>
				<div class="modal-header" style="background-color: #0070C1;">
					<h4 class="modal-title" style="color: #fff; font-weight: bold;">공유 캘린더 추가</h4>
					<button type="button" class="close myclose" data-dismiss="modal" style="color: #fff;">&times;</button>
				</div>

				<%-- Modal body --%>
				<div class="modal-body">
					<div id="addShare">
						<form name="shareFrm">
							<table id= "tblCalendar" class="w-90 mx-auto">
								<tbody>
									<tr>
										<td style="width: 20%;" id="title">캘린더 이름&nbsp;</td>
										<td style="width: 80%; text-align: left;">
											<input type="text" id="s_calname" name="calname" class="form-control" />
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">색상&nbsp;</td>
										<td style="width: 80%;">
											<input type="color" id="s_color" name="color" class="form-control form-control-color col-2" value="#e0f2ff" />
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">공유대상&nbsp;</td>
										<td style="width: 80%;">
											<input type="text" id="s_shareEmp" name="shareEmp" class="form-control form-control-color" />
											<input type="hidden" id="loginuserid" name="loginuserid" value="${sessionScope.loginuser.empid }" />
										</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>

				<%-- Modal footer --%>
				<div class="modal-footer">
					<button type="button" class="btn myclose" data-dismiss="modal" style="background-color: #c10000; color: #fff;">Close</button>
					<button type="button" class="btn" style="background-color: #0070C1; color: #fff;" onclick="addShare()" >Add</button>
				</div>
			</div>

		</div>
	</div>
	<%-- 공유캘린더 모달창 끝 --%>
	
	<%-- 공유캘린더 수정 및 삭제 모달창 시작 --%>
	<div class="modal fade" id="changeShareModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">

				<%-- Modal header --%>
				<div class="modal-header" style="background-color: #0070C1;">
					<h4 class="modal-title" style="color: #fff; font-weight: bold;">공유캘린더 수정 및 삭제</h4>
					<button type="button" class="close myclose" data-dismiss="modal" style="color: #fff;">&times;</button>
				</div>

				<%-- Modal body --%>
				<div class="modal-body">
					<div id="changeShare">
						<form name="changeShareFrm">
							<table id= "tblCalendar" class="w-90 mx-auto">
								<tbody>
									<tr>
										<td style="width: 20%;" id="title">캘린더 이름&nbsp;</td>
										<td style="width: 80%; text-align: left;">
											<input type="hidden" id="calno" name="calno" value="" />
											<input type="text" id="updateCalname" name="calname" class="form-control" value=""/>
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">색상&nbsp;</td>
										<td style="width: 80%;">
											<input type="color" id="updateColor" name="color" class="form-control form-control-color col-2" value="" />
										</td>
									</tr>
									<tr>
										<td style="width: 20%;" id="title">공유대상&nbsp;</td>
										<td style="width: 80%;">
											<input type="text" id="updateShareEmp" name="shareEmp" class="form-control form-control-color" value="" />
											<input type="hidden" id="changeSOption" name="changeOption" value="" />
										</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>

				<%-- Modal footer --%>
				<div class="modal-footer">
					<button type="button" class="btn" style="background-color: #0070C1; color: #fff;"  onclick="changeShare(1)">수정</button>
					<button type="button" class="btn" style="background-color: #c10000; color: #fff;"  onclick="changeShare(2)">삭제</button>
					<button type="button" class="btn btn-outline-secondary myclose" data-dismiss="modal" >닫기</button>
				</div>
			</div>
		</div>
	</div>
	<%-- 공유캘린더 수정 및 삭제 모달창 끝 --%>
	
</div>


