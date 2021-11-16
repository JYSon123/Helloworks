<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>    

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
		font-weight: normal;
		font-size: 13pt;
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
	
</style>

<script src='<%=request.getContextPath() %>/resources/lib/main.js'></script>
<script>

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
        {
          title: 'Business Lunch',
          start: '2020-09-03T13:00:00',
          constraint: 'businessHours'
        },
        {
          title: 'Meeting',
          start: '2020-09-13T11:00:00',
          constraint: 'availableForMeeting', // defined below
          color: '#257e4a'
        },
        {
          title: 'Conference',
          start: '2020-09-18',
          end: '2020-09-20'
        },
        {
          title: 'Party',
          start: '2020-09-29T20:00:00'
        },

        // areas where "Meeting" must be dropped
        {
          groupId: 'availableForMeeting',
          start: '2020-09-11T10:00:00',
          end: '2020-09-11T16:00:00',
          display: 'background'
        },
        {
          groupId: 'availableForMeeting',
          start: '2020-09-13T10:00:00',
          end: '2020-09-13T16:00:00',
          display: 'background'
        },

        // red areas where no events can be dropped
        {
          start: '2020-09-24',
          end: '2020-09-28',
          overlap: false,
          display: 'background',
          color: '#ff9f89'
        },
        {
          start: '2020-09-06',
          end: '2020-09-08',
          overlap: false,
          display: 'background',
          color: '#ff9f89'
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
  		$("li#catg").click(function(){
            if($(this).find("#subcatg").is(":visible")){
                $(this).find("#subcatg").slideUp();
                $(this).children().first().removeClass("fa-caret-down");
                $(this).children().first().addClass("fa-caret-right");
                
            }
            else{
                $(this).find("#subcatg").slideDown();
                $(this).children().first().removeClass("fa-caret-right");
                $(this).children().first().addClass("fa-caret-down");
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
  		$("input#message").change(function(){
  	        if($(this).is(":checked")){
  	          	$("select#mnoticeTime").css("visibility","visible");
  	        }
  	        else{
  	          	$("select#mnoticeTime").css("visibility","hidden");
  	        }
  	    });
  		$("input#email").change(function(){
  	        if($(this).is(":checked")){
  	          	$("select#enoticeTime").css("visibility","visible");
  	        }
  	        else{
  	          	$("select#enoticeTime").css("visibility","hidden");
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
								 "<span>"+item.calname+"</span></li>";
					}
					else if (item.fk_cno == 2) {
						
						shtml += "<li><span style='color:"+item.color+";'>■ </span>" +
						 		 "<span>"+item.calname+"</span></li>";
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
		
		var form_data = $("form[name=addSchFrm]").serialize();
		
		$.ajax({
			url:"<%=ctxPath%>/addSchedule.hello2",
			data:form_data,
			type:"POST",
			dataType:"JSON",
			success:function(json){  
				
				opener.parent.location.reload();
				window.close();
				
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		 	}
		});	
		
		
		var frm = document.addSchFrm;
		
		frm.action ="<%=ctxPath%>/addSchedule.hello2";
		frm.method = "GET";
		frm.submit();
		
	}
	
	
	
</script>


<div>
	<%-- 사이드바 시작 --%>
	<nav class="w3-sidebar w3-collapse w3-white " style="margin-top: 20px; z-index: 0; width: 300px; background-color: #f5f5f5; overflow: hidden; height: 100%;" id="mySidebar">
		<br>
		<div class="w3-container" style="background-color: #f5f5f5; margin-top: 10px">
			<a href="#" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu"><i class="fa fa-remove"></i></a> <br><br>
			<span style="font-size: 30pt; margin: 100px 0 30px 40px; color: gray"><b>일정</b></span>
		</div>
		<div class="w3-bar-block" style="background-color: #f5f5f5; height: 100%"><br>
			
			<%-- 일정추가 모달창 버튼 --%>
			<button type="button" id="addSch" class="btn" data-toggle="modal" data-target="#addSchModal" data-dismiss="modal" >일정 추가</button>
			
			<%-- 캘린더 카테고리--%>
			<div style="width: 80%; margin: 30px auto;">
				<ul>
					<li id="catg">
						<i class="fas fa-caret-right" ></i> &nbsp; 개인 캘린더
						<button type="button" style="float: right;" class="btn"  data-toggle="modal" data-target="#addPersonalModal" data-dismiss="modal" ><i class="fas fa-plus"></i></button>
						<ul id="subcatg" class="personalList" style="display:none">
							
						</ul>
						
					</li>
					<li id="catg">
						<i class="fas fa-caret-right"></i> &nbsp; 공유 캘린더
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
							<option selected value="subject">제목</option>
							<option value="content">내용</option>
							<option value="term">기간</option>
						</select>
					</div>
					<input type="text" id="searchWord" name="searchWord" class="form-control" />
					<input type="hidden" id="startDate" name="startDate" class="form-control" />
					<input type="hidden" id="endDate" name="endDate" class="form-control" />
					<div class="input-group-append">
						<button type="button" class="btn btn-outline-secondary" onclick="goSearch()">
							<i class="fas fa-search"></i>
						</button>
					</div>
				</div>

			</form>
		</div>
		
		<%-- 캘린더 --%>
		<div id='calendar' style="width: 100%"></div>
		
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
										<select id="calname" name="calname" style="width: 100%" class="form-control requiredInfo">
											<option>캘린더1</option>
											<option>캘린더2</option>
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
	
	<%-- 개인캘린더 모달창 시작 --%>
	<div class="modal fade" id="addPersonalModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">

				<%-- Modal header --%>
				<div class="modal-header" style="background-color: #0070C1;">
					<h4 class="modal-title" style="color: #fff; font-weight: bold;">개인 캘린더</h4>
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
	
	<%-- 공유캘린더 모달창 시작 --%>
	<div class="modal fade" id="addShareModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">

				<%-- Modal header --%>
				<div class="modal-header" style="background-color: #0070C1;">
					<h4 class="modal-title" style="color: #fff; font-weight: bold;">공유 캘린더</h4>
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
	<%-- 개인캘린더 모달창 끝 --%>
	
</div>

