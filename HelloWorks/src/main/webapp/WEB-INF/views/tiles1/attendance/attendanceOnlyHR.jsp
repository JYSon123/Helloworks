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
	
	table, td, th{ border: 1px solid #d9d9d9; 
					font-weight:normal;
	}
	
	th.topcategory { background-color: #eff4fc; height:50px;}
	th.category{ height:35px; }
	td { height:50px; }
</style>

<script>
	var now = "";
	var strNow = "";
	var onlytime = "";
	//var isRecExist = ${requestScope.isRecordExist};
	//var empno = ${requestScope.empno};
	
	$(document).ready(function() {
	  
		//==== 날짜 select박스 만들기 시작====//
		var now = new Date(); 
		var year = now.getFullYear(); 
		var mon = (now.getMonth() + 1) > 9 ? ''+(now.getMonth() + 1) : '0'+(now.getMonth() + 1); 
		var day = (now.getDate()) > 9 ? ''+(now.getDate()) : '0'+(now.getDate()); 
		
		//년도 selectbox만들기 
		for(var i = 2000 ; i <= year ; i++) 
		{ $('#year').append('<option value="' + i + '">' + i + '년</option>'); } 
		
		// 월별 selectbox 만들기 
		for(var i=1; i <= 12; i++) 
		{ var mm = i > 9 ? i : "0"+i ; $('#month').append('<option value="' + mm + '">' + mm + '월</option>'); } 
		
		$("#year > option[value="+year+"]").attr("selected", "true"); 
		$("#month > option[value="+mon+"]").attr("selected", "true"); 
	
		//==== 날짜 select박스 만들기 끝 ====//
		
		
		
		
	}); // end of ready(); ---------------------------------
	
	
	// 부서명 select 한후에 캘린더 가져오기 
	function putCalInfo(deptnum){
		
		//console.log("확인용 deptnum : " + deptnum); 
		// 확인용 deptnum : 40(마케팅)
		// 확인용 deptnum : 0(부서선택)
		
		//if( deptnum != 0 ){ if처리 안해도 오류 안떨어져서 그냥 그대로 하는 게 나을듯 
		
			$.ajax({
		         url:"<%= request.getContextPath()%>/emp/showDepCalendar.hello2",
		         dataType:"JSON",
		         data:{"deptnum":deptnum},
		         success:function(json){ 
		        	
		        	 var events = [];
			        	
		        	 if(json.length > 0) {
		        		 
							$.each(json, function(index, item){
								
								// 출근시간 배열 만들기 
								var eventSrc_intime = {};
								eventSrc_intime.title = "출근 [ " + item.empname + " ]";
								eventSrc_intime.start = item.nowdate + "T" + item.intime;
								events.push(eventSrc_intime);
							
								
								//퇴근시간 배열만들기 
								var eventSrc_outtime = {};
								
								if( item.hasOwnProperty("outtime")){
									eventSrc_outtime.title = "퇴근 [ " + item.empname + " ]";
									eventSrc_outtime.start = item.nowdate + "T" + item.outtime;
									eventSrc_outtime.end = item.nowdate + "T" + "24:00:00";
									eventSrc_outtime.color = "red";
								}
								
								// 퇴근시간이 있는 경우만 출력
								if(eventSrc_outtime.length !== 0 ){
									events.push(eventSrc_outtime);
								}
							
							});
						}
		        	 
		        	
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
		
		//}
		
	}
	
	// 부서명 select 한후에 부서원들 근태통계 보여주기
	function putTableInfo(){
		
		var deptnum = $("select[name=deptnum]").val();
		console.log("확인용 deptnum : " + deptnum); 
		
		var year = $("select[name=year]").val();
		//console.log("확인용 year : " + year); 
		
		var month = $("select[name=month]").val();
		//console.log("확인용 month : " + month); 

		var deptname = $("#deptselect2 > option:selected").attr("value2"); 
		//console.log("확인용 deptname : " + deptname); 
		
		var cnt = 0;
		
		$.ajax({
	         url:"<%= request.getContextPath()%>/emp/showDepTable.hello2",
	         dataType:"JSON",
	         data:{"deptnum":deptnum
	        	 	, "year"  : year
	        	 	, "month" :month },
	         success:function(json){ 
	        
	        	 var html = "<table style='width:100%; text-align:center'>";
			  	  html += "<colgroup>" +  
				  			"<col style='width:25%'>"+
				  			"<col style='width:7.5%'>"+
				  			"<col style='width:10%'>"+
				  			"<col style='width:7.5%'>"+
				  			"<col style='width:7.5%'>"+
				  			"<col style='width:7.5%'>"+
				  			"<col style='width:15%'>"+
				  			"<col style='width:10%'>"+
				  			"<col style='width:10%'>"+
			  			"</colgroup>" +
			  		  	"<tr >" +
			  	  			"<th class ='topcategory' colspan='3'>이름/소속</th>" +
			  	  			"<th class ='topcategory' colspan='4'>근태</th>" +
			  	  			"<th class ='topcategory' colspan='2'>근무</th>" +
			  	  		  "<tr>" +
				  	  	  "<tr>" +
			  	  			"<th class ='category'>사원명[id]</th>" +
			  	  			"<th class ='category'>소속</th>" +
			  	  			"<th class ='category'>휴직상태</th>" +
			  	  			"<th class ='category'>지각</th>" +
			  	  			"<th class ='category'>조퇴</th>" +
			  	  			"<th class ='category'>결근</th>" +
			  	  			"<th class ='category'>퇴근미체크</th>" +
			  	  			"<th class ='category'>근무일수</th>" +
			  	  			"<th class ='category'>근무시간</th>" +
			  	  		  "<tr>";
	        	 
	        	 if(deptnum != -1 && json.length > 0) { // 부서 올바르게 선택, 부서에 사원이 있는 경우 
	        		 
	        		 $.each(json, function(index, item){
	        		 
	        			 
				  		// === 조회한 달과 입사일자 비교하기 === //
				  		 console.log("확인용 입사일자: " + item.hiredate); //확인용 입사일자: 2021/11/08
	        			 
	        			 var compdate = year + "/" + month + "/" + "31" ;
	        			 
	        			 var date1 = new Date(compdate);
	        			 var date2 = new Date(item.hiredate);

	        			 if(date1 < date2) { // 입사전 날짜 조회
	        				 console.log("입사 전 입니다.");
	        			 }
	        			 else{
	        				 cnt = cnt + 1;
	        				 console.log("입사 후 입니다.");
	        				 html +="<tr>" +
					  				"<td>"+ item.empname+" ["+item.empid+"] </td>"+
					  				"<td>"+ deptname +"</td>"+
					  				"<td>"+ item.empstatus +"</td>"+
					  				"<td>"+ item.sumlate +"</td>"+ 
					  				"<td>"+ item.sumearly +"</td>"+ 
					  				"<td>"+ item.absenceCnt +"</td>"+ 
					  				"<td>"+ item.outtimeNullCnt +"</td>"+ 
					  				"<td>"+ item.sumworkday +"</td>"+ 
					  				"<td>"+ item.sumtotal +"</td>"+ 
		  						"</tr>";
	        			 }
	        			
	        		 });
	        	 }
	        	 else if(deptnum != -1 && json.length == 0) { //부서 올바르게 선택, 부서에 사원이 없는 경우
	        		 html += "<tr>" +
				  				"<td colspan='9' style='height:50px;'> 해당 부서에 사원이 존재하지 않습니다. </td>"+
							"</tr>";
	        	 }
	        	 else if(deptnum == -1 ){ //'부서선택'을 선택한 경우 
	        		 
	        		 html += "<tr>" +
		  				"<td colspan='9' style='height:50px;'> 부서를 선택해주십시오. </td>"+
					"</tr>";
	        		 
	        	 }
	        	 
	        	 $("div#checkhere").html(html);
	        	 $("span#totalcnt").html(cnt);
	        	 
	        	 
	         },
	         error: function(request, status, error){
	            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	          }
	      });
		
	}
	
	
	// select 태그에 사원정보 넣기 
	function putEmpInfo(deptnum){
		
		$.ajax({
	         url:"<%= request.getContextPath()%>/emp/showDepSelect.hello2",
	         dataType:"JSON",
	         data:{"deptnum":deptnum},
	         success:function(json){ 
	        
	        	  var html = " <option selected value='0' style='width: 350px;'>사원 선택</option>";
	        	  
	        	  if(json.length > 0) {
	        		  
		        	  $.each(json, function(index, item){
		        		html += "<option value=" + item.empno + " value2= "+ item.empname+">" + item.empname + "[" + item.empid + "]" + "</option>";
		        	  });
	        	  }
	        
   				  $("select[name=employee]").html(html);
	        	 
	         },
	         error: function(request, status, error){
	            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	          }
	      });
		
	}
	
	
	//엑셀파일 다운로드하기 
	function downloadExcel(){
	
		var empno = $("select[name=employee]").val();
		console.log("엑셀버튼 확인용 empno => " + empno);
		
		
		var empname = $("#deptselect3 > option:selected").attr("value2"); 
		console.log("엑셀버튼 확인용 empname=> " + empname); 
		
		
		if( empno == 0){
			
			alert("조회할 대상을 정확히 선택해주십시오.");
			
		}
		else{
			
			var frm = document.searchFrm;
			frm.empno.value = empno;
			frm.empname.value= empname;
			
			frm.method = "POST";
			frm.action = "<%= request.getContextPath()%>/emp/downloadEmpExcelFile.hello2";
			frm.submit();
			
		}
		
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
			<span style="margin:50px 0 25px 50px; display:block; font-size:25pt; font-weight:600; ">부서 근태관리</span>
			<hr style="background-color:#80bfff; margin-bottom:70px;">
			
		   	<%-- 부서별 근무현황 타이틀 --%>
			<span style="margin:50px 0 30px 50px; font-size:17pt; font-weight: 600; background-color:#eff4fc">부서별 근무현황</span>
			
			<%-- 부서별 근무현황 영역 --%>
			<div style="min-height: 400px; margin-top: 25px; margin-bottom: 150px; align:center">
				
			 	<%-- 부서별 근무현황_셀렉트박스--%>
			  	<div style="margin:3px 50px;">	
					<select id="deptselect1" style="width: auto; font-size:15px;" onchange="putCalInfo(this.value)">
		            				<option selected value="0" style="width: 350px;">부서 선택</option>
		           			 	<c:forEach var="department" items="${requestScope.departmentList}">
		            			   		<option value="${department.deptnum}" value2="${department.deptname}">${department.deptname}</option>
		           			 	</c:forEach>
		        			</select>
		     	 </div>	
		    	<%-- 셀렉트박스 끝--%>
			  	
				<%-- 부서별 근무현황_캘린더--%> 
				<div id='calendar' style="margin:20px 50px 0 50px;"></div>
				<%-- 부서별 근무현황_캘린더 끝--%>
		   
			</div>
			<%--  부서별 근무현황 영역 끝--%>
	
			<%-- 부서별 근무통계_타이틀--%>
		  	<span style="margin-top:50px; margin-left:50px; font-size:17pt; font-weight:600; background-color:#eff4fc;">부서별 근무통계</span>
		    
			<%-- 부서별 근무통계_영역--%>	
		 	<div style="min-height: 150px;margin-top: 25px; margin-bottom: 150px; align:center;">
			   
				<%-- 부서별 근무통계_셀렉트박스--%>
				 <div style="margin:3px 50px;">	
						<select name="deptnum" id="deptselect2" style="width: auto; font-size:15px;">
			           			 <option selected value="-1" style="width: 350px;">부서 선택</option>
			            			<c:forEach var="department" items="${requestScope.departmentList}">
			               			<option value="${department.deptnum}" value2="${department.deptname}">${department.deptname}</option>
			            			</c:forEach>
			        	</select> 부
						<select name="year" id="year"></select> 년 
						<select name="month" id="month"></select> 월
					<button type="button" style="border:0px; width:80px; height: 40px; font-weight:500; border-radius: 5%; background-color: #eff4fc;" id="btnSearch" onclick="putTableInfo()">검색하기</button>
			    </div>
				<%-- 부서별 근무통계_셀렉트박스끝--%>
				
				
				<%-- 부서별 근무통계_표--%>
				<div style="text-align:left; margin:20px 0 0 50px;">
			        총: <span id="totalcnt">0</span>명
			    </div>
				<div id="checkhere" style="text-align:left; margin:5px 50px;">
		      		 <table style='width:100%; text-align:center;'>
			  	  		<colgroup> 
				  			<col style='width:25%'>
				  			<col style='width:7.5%'>
				  			<col style='width:10%'>
				  			<col style='width:7.5%'>
				  			<col style='width:7.5%'>
				  			<col style='width:7.5%'>
				  			<col style='width:15%'>
				  			<col style='width:10%'>
				  			<col style='width:10%'>
			  			</colgroup>
			  		  	<tr>
			  	  			<th class ='topcategory' colspan='3'>이름/소속</th>
			  	  			<th class ='topcategory' colspan='4'>근태</th>
			  	  			<th class ='topcategory' colspan='2'>근무</th>
			  	  		</tr>
				  	  	<tr>
			  	  			<th class ='category'>사원명[id]</th>
			  	  			<th class ='category'>소속</th>
			  	  			<th class ='category'>휴직상태</th>
			  	  			<th class ='category'>지각</th>
			  	  			<th class ='category'>조퇴</th>
			  	  			<th class ='category'>결근</th>
			  	  			<th class ='category'>퇴근미체크</th>
			  	  			<th class ='category'>근무일수</th>
			  	  			<th class ='category'>근무시간</th>
			  	  		 </tr>
			  	  		 <tr>
				  			<td colspan='9' style='height:50px;'> 부서를 선택해주십시오. </td>
						</tr>
			  	  	</table>
		      		
		      		<%-- <div id="comment" style="align:center;"><span>부서를 선택해주십시오.</span></div> --%>	
		     	</div>
				<%-- 부서별 근무통계_표끝--%>
			</div>
			<%-- 부서별 근무통계_영역_끝--%>	
			
			
			<%-- 사원 근무내역_엑셀다운 타이틀--%>
		  	<span style="margin-top:50px; margin-left:50px; font-size:17pt; font-weight:600; background-color:#eff4fc;">사원 근무내역_Excel다운로드</span>
		    
			<%-- 사원 근무내역_엑셀다운 영역--%>	
		 	<div style="min-height: 400px; margin-top: 25px; margin-bottom: 50px; align:center">
			   
			   	<%-- 사원 근무내역_엑셀다운_셀렉트박스--%>
				 <div style="margin:3px 50px;">	
				 	<form name="searchFrm">
						<select name="empDeptnum" id="deptselect2" style="width: auto; font-size:15px;" onchange="putEmpInfo(this.value)">
			           			 <option selected value="0" style="width: 350px;">부서 선택</option>
			            			<c:forEach var="department" items="${requestScope.departmentList}">
			               			<option value="${department.deptnum}" value2="${department.deptname}">${department.deptname}</option>
			            			</c:forEach>
			        	</select> 부 &nbsp;&nbsp;/&nbsp;&nbsp; 
						<select name="employee" id="deptselect3" style="width: 180px; font-size:15px;">
			           			 <option selected value="0" >사원 선택</option>
			        	</select> 사원
			        	<input type="hidden" name="empno" />
			        	<input type="hidden" name="empname" />
						<button type="button" style="margin-left:15px; border:0px; width:150px; height: 40px; font-weight:500; border-radius: 5%; background-color: #eff4fc;" id="btnSearchEmp" onclick="downloadExcel()">Excel파일 다운로드</button>
					</form>
			    </div>
				<%-- 사원 근무내역_엑셀다운_셀렉트박스끝--%>
					
			</div>
			<%-- 사원 근무내역_엑셀다운 영역 끝--%>	
			
	  	</div>
	  
	 </div>  
<!-- !!!!!PAGE CONTENT END!!!!!! -->
</div>