<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

 
<style type="text/css">
	body,h1,h2,h3,h4,h5,h6 {font-family: Verdana, sans-serif;}
	.borderbottom {  border-bottom : solid 1px; }
	.borderright { border-right : solid 1px #d9d9d9; }
	
	label{ width: auto;
		   margin: 0 0 0 5px;}
	
	
	table, td, th{ border: 1px solid #d9d9d9; 
					font-weight:normal;
	}
	
	th.topcategory { background-color: #eff4fc; height:50px;}
	th.category{ height:35px; }
	td { height:50px; }
	
	div#formbox { padding: 20px 30px 50px 15px; 
				border: 1px solid #d9d9d9; }
	
	input#empno{ 
	border:0 solid black;
	border-bottom: solid 1px #d9d9d9;}
	
</style>

<script>
	//var now = ""; 지워도되는지 확인하고 지우기 
	//var strNow = "";
	//var onlytime = "";
	//var isRecExist = ${requestScope.isRecordExist};
	//var empno = ${requestScope.empno};
	
	$(document).ready(function() {
	  
		//==== 날짜 select박스 만들기 시작====//
		var now = new Date(); 
		var year = now.getFullYear(); 
		
		
		//년도 selectbox만들기 
		for(var i = 2000 ; i <= year ; i++) 
		{ $('#hireyear').append('<option value="' + i + '">' + i + '년</option>'); } 
		
		$('#hireyear').append('<option selected value="0">년도선택</option>'); 
		
		
		//$('#hireyear > option[value="0"]').attr("selected", "true"); 
		//==== 날짜 select박스 만들기 끝 ====//
		
		
		// === 부서선택 체크박스 유지시키기 시작 === //
		var deptnoList = "${requestScope.deptnoList}"; // 컨트롤러에서넘어옴
		console.log("~~~확인용 deptnoList => " + deptnoList);
		
		if(deptnoList != ""){
			var arrDeptno = deptnoList.split(",");
			//["-9999","50","110"]
			
			$("input:checkbox[name=deptno]").each(function(){
				for(var i=0; i<arrDeptno.length; i++){
					if($(this).val() == arrDeptno[i]){
						$(this).prop("checked", true);
						break;
					}
				}// end of for-----------------
			});
		}
		// === 부서선택 체크박스 유지시키기 끝 === //
		
		
		// === 직급선택 체크박스 유지시키기 시작 === //
		var rankingList = "${requestScope.rankingList}"; // 컨트롤러에서넘어옴
		console.log("~~~확인용 rankingList => " + rankingList);
		
		if(rankingList != ""){
			var arrRanking = rankingList.split(",");
			//["-9999","50","110"]
			
			$("input:checkbox[name=ranking]").each(function(){
				for(var i=0; i<arrRanking.length; i++){
					if($(this).val() == arrRanking[i]){
						$(this).prop("checked", true);
						break;
					}
				}// end of for-----------------
			});
		}
		// === 직급선택 체크박스 유지시키기 끝 === //
		
		
		// === 입사년도 유지시키기 시작 === //
		var hireyear = "${requestScope.hireyear}";
		if(hireyear != ""){
			$("select#hireyear").val(hireyear);
		}
		// === 입사년도 유지시키기 끝=== //

		
		// === 휴직상태 유지시키기 시작 === //
		var empstatus = "${requestScope.empstatus}";
		if(empstatus != ""){
			$("select#empstatus").val(empstatus);
		}
		// === 휴직상태 유지시키기 끝=== //
		
		
		// === 검색시 검색조건 및 검색어 값 유지시키기 === //
		var empno = "${requestScope.empno}";
		if(empno != ""){
			$("input#empno").val(empno);
		}
		// === 검색시 검색조건 및 검색어 값 유지시키기 끝=== //
		
		
		
		
	}); // end of ready(); ---------------------------------
	
	
	// 검색버튼 눌렀을때 
	function goSearch(){

		var frm = document.searchFrm;
		
		// === 부서선택 넘기기 === //
		var arrDeptno = new Array();
		
		$("input:checkbox[name=deptno]").each(function(){
			var bool = $(this).is(":checked"); //체크박스의 체크유무검사
			if(bool == true){
				//체크박스에 체크가 되어있으면
				arrDeptno.push($(this).val());
			}
		});
		
		var deptnoList = arrDeptno.join();
		console.log("~~~~ 확인용 deptnoList => " + deptnoList);
		
		frm.deptnoList.value = deptnoList;
		// === 부서선택 넘기기 끝 === //
		
		
		// === 직급선택 넘기기 === // 
		var arrRanking = new Array();
		
		$("input:checkbox[name=ranking]").each(function(){
			var bool = $(this).is(":checked"); //체크박스의 체크유무검사
			if(bool == true){
				//체크박스에 체크가 되어있으면
				arrRanking.push($(this).val());
			}
		});
		
		var rankingList = arrRanking.join();
		console.log("~~~~ 확인용 rankingList => " + rankingList);
		
		frm.rankingList.value = rankingList;
		// === 직급선택 넘기기 끝=== // 
		
		// 나머지는 select태그이고 name 달아줘서 알아서 넘어가지 않을까?
		
		frm.method="GET";
		frm.action="<%= request.getContextPath()%>/emp/viewEmployee.hello2";
		frm.submit();
		
	}// end of function goSearch(){}---------------------------
	
	
	// 전체 검색버튼 눌렀을때 
	function goAllSearch(){

		var frm = document.searchFrm;
		
		// === 부서선택 넘기기 === //
		
		var deptnoList = "";
		console.log("~~~~ 확인용 deptnoList => " + deptnoList);
		
		frm.deptnoList.value = deptnoList;
		// === 부서선택 넘기기 끝 === //
		
		
		// === 직급선택 넘기기 === // 
		var rankingList = "";
		console.log("~~~~ 확인용 rankingList => " + rankingList);
		
		frm.rankingList.value = rankingList;
		// === 직급선택 넘기기 끝=== // 
		
		var hireyear = "0";
		frm.hireyear.value = hireyear;
		
		var empstatus = "3";
		frm.empstatus.value = empstatus;
		
		var empno = "";
		frm.empno.value = empno;
		
		
		frm.method="GET";
		frm.action="<%= request.getContextPath()%>/emp/viewEmployee.hello2";
		frm.submit();
		
	}// end of function goSearch(){}---------------------------
	
	
	
	// 엑셀파일 다운로드 눌렀을때 
	function godownloadExcel(){

		var frm = document.searchFrm;
		
		// === 부서선택 넘기기 === //
		var arrDeptno = new Array();
		
		$("input:checkbox[name=deptno]").each(function(){
			var bool = $(this).is(":checked"); //체크박스의 체크유무검사
			if(bool == true){
				//체크박스에 체크가 되어있으면
				arrDeptno.push($(this).val());
			}
		});
		
		var deptnoList = arrDeptno.join();
		console.log("~~~~ 확인용 deptnoList => " + deptnoList);
		
		frm.deptnoList.value = deptnoList;
		// === 부서선택 넘기기 끝 === //
		
		
		// === 직급선택 넘기기 === // 
		var arrRanking = new Array();
		
		$("input:checkbox[name=ranking]").each(function(){
			var bool = $(this).is(":checked"); //체크박스의 체크유무검사
			if(bool == true){
				//체크박스에 체크가 되어있으면
				arrRanking.push($(this).val());
			}
		});
		
		var rankingList = arrRanking.join();
		console.log("~~~~ 확인용 rankingList => " + rankingList);
		
		frm.rankingList.value = rankingList;
		// === 직급선택 넘기기 끝=== // 
		
		
		frm.method="POST";
		frm.action="<%= request.getContextPath()%>/emp/downloadEmpInfoExcelFile.hello2";
		frm.submit();
		
	}// end of function godownloadExcel(){}---------------------------
	
	
	
	
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
  		<%-- 회원목록보기_타이틀 --%>
		<span style="margin:50px 0 25px 50px; display:block; font-size:25pt; font-weight:600; ">사원목록보기</span>
		<hr style="background-color:#80bfff; margin-bottom:70px;">
		
		<%-- 부서별 근무현황 타이틀 --%>
			<span style="margin:50px 0 30px 50px; font-size:17pt; font-weight: 600; background-color:#eff4fc">검색조건</span>
		
		<%-- 회원목록보기 영역 --%>
			<div style="min-height: 400px; margin-top: 25px; margin-bottom: 150px; align:center">
				
			 	<%-- 회원목록보기_셀렉트박스--%>
			  	<div id="formbox" style="margin:3px 100px 70px 50px;">	
			  	
			  		<form name="searchFrm">
			  		
				  		<%-- 소속부서종류(다중선택)--%>
						<span style="display:inline-block; margin-bottom:10px; width:150px; font-weight:bold;">부서</span>
						<c:forEach var="deptno" items="${requestScope.departmentList}" varStatus="status">
							<label for="${status.index}">
								${deptno.deptname}
							</label>
							<input type="checkbox" name="deptno" id="${status.index}"value="${deptno.deptnum}" />&nbsp;&nbsp;&nbsp;
						</c:forEach>	
						<input type="hidden" name="deptnoList" />
						<br/>
				
						<%-- 직급종류(다중선택) 사원(1), 대리(2), 부장(3), 팀장(4),  사장(5)--%>
						<span style="display:inline-block; margin-bottom:10px; width:150px; font-weight:bold;">직급</span>
						<label for="1">사원</label>
						<input type="checkbox" name="ranking" id="1" value="1" />&nbsp;&nbsp;&nbsp;
						<label for="2">대리</label>
						<input type="checkbox" name="ranking" id="2" value="2" />&nbsp;&nbsp;&nbsp;
						<label for="3">부장</label>
						<input type="checkbox" name="ranking" id="3" value="3" />&nbsp;&nbsp;&nbsp;
						<label for="4">팀장</label>
						<input type="checkbox" name="ranking" id="4" value="4" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<label for="5">대표</label>
						<input type="checkbox" name="ranking" id="5" value="5" />&nbsp;&nbsp;&nbsp;
						<input type="hidden" name="rankingList" />
					    <br/>
						
						<%-- 입사년도(단일선택)--%>
						<span style="display:inline-block; margin-bottom:10px; width:150px; font-weight:bold;">입사년도</span>
						<select name="hireyear" id="hireyear" style="width: 100px;"></select>
						<br/>
						
						<%-- 휴직상태 --%>
						<span style="display:inline-block; margin-bottom:10px; width:150px; font-weight:bold;">휴직유무</span>
						<select name="empstatus" id="empstatus" style="width: 100px; font-size:15px;">
	            				<option selected value="3" style="width: 350px;">재직유무</option>
	            			   	<option value="1" >재직</option>
	            			   	<option value="2" >휴직</option>
	            			   	<option value="0" >퇴직</option>
	        			</select>
	        			<br/>
	        			
	        			<%-- 검색창 --%>
						<span style="display:inline-block; margin-bottom:10px; width:150px; font-weight:bold;">사원번호</span>
						<input type="text" name="empno" id="empno" size="20" autocomplete="off" />
						<%-- form태그 내에 input태그가 하나만 존재시 엔터가 바로 먹기때문에 아래와 같이 해야함, hidden으로 하면 먹히므로 display를 사용 --%>
						<%-- <input type="text" style="display:none;"/>--%> <%-- form태그 내에 input태그가 오로지 1개 뿐일경우에는 엔터를 했을 경우 검색이 되어지므로 이것을 방지하고자 만든것이다. --%>
						</br>
					
						<div style="float:right;">
	    	    			<button type="button" style="border:0px; width:150px; height: 30px; font-weight:500; border-radius: 5%; background-color: #eff4fc;" id="btnSearchEmp" onclick="godownloadExcel()">Excel파일 다운로드</button>
	    	    			<button type="button" style="margin-left:5px;border:0px; width:120px; height: 30px; font-weight:500; border-radius: 5%; background-color: #eff4fc;" id="btnSearchEmp" onclick="goAllSearch()">전체사원보기</button>
							<button type="button" style="margin-left:5px; border:0px; width:50px; height: 30px; font-weight:500; border-radius: 5%; background-color: #e6eeff;" id="btnSearchEmp" onclick="goSearch()">검색</button>
        				</div>
					</form>
		     	</div>	
			  	</br>
		    	<%-- 셀렉트박스 끝--%>
			  	
			  	<span style="margin:50px 0 30px 50px; font-size:17pt; font-weight: 600; background-color:#eff4fc">사원목록</span>
			  	
				<%-- 사원목록보기_리스트영역--%> 
				<div id='showemptable' style="margin:20px 50px 0 50px;">
					<div style="text-align:left; margin:20px 0 0 0;">
				        총: <span id="totalcnt">${requestScope.totalCount}</span>명
				    </div>
					<div id="checkhere" style="text-align:left; margin:5px 50px 5px 0px;">
			      		 <table style='width:100%; text-align:center;'>
				  	  		<colgroup> 
					  			<col style='width:10%'>
					  			<col style='width:6%'>
					  			<col style='width:9%'>
					  			<col style='width:20%'>
					  			<col style='width:6%'>
					  			<col style='width:6%'>
					  			<col style='width:6.5%'>
					  			<col style='width:10.5%'>
					  			<col style='width:10%'>
					  			<col style='width:21%'>
				  			</colgroup>
				  		  	<tr>
				  	  			<th class ='topcategory' >사원번호</th>
				  	  			<th class ='topcategory' >사원명</th>
				  	  			<th class ='topcategory' >사원ID</th>
				  	  			<th class ='topcategory' >이메일</th>
				  	  			<th class ='topcategory' >직급</th>
				  	  			<th class ='topcategory' >부서명</th>
				  	  			<th class ='topcategory' >재직유무</th>
				  	  			<th class ='topcategory' >연봉(단위:만원)</th>
				  	  			<th class ='topcategory' >입사일</th>
				  	  			<th class ='topcategory' >개인이메일</th>
				  	  		</tr>
				  	  		
				  	  		<c:if test="${not empty requestScope.empList}">
						  	  	<c:forEach var="emp" items="${requestScope.empList}" varStatus="status">
						  			<tr>
							  			<td class ='category' style="cursor:pointer;" onclick="location.href='<%= ctxPath %>/empUpdate.hello2?empno=${emp.empno}'" >${emp.empno}</td>
							  			<td class ='category' >${emp.empname}</td>
							  			<td class ='category' >${emp.empid}</td>
							  			<td class ='category' >${emp.email}</td>
							  			<td class ='category' >${emp.ranking}</td>
							  			<td class ='category' >${emp.fk_deptnum}</td>
							  			<td class ='category' >${emp.empstatus}</td>
							  			<td class ='category' >
							  				<fmt:formatNumber value="${emp.empsalary}" pattern="#,###" />
							  			</td>
							  			<td class ='category' >${emp.hiredate}</td>
							  			<td class ='category' >${emp.noticeemail}</td>
									</tr>
								</c:forEach>	
							</c:if>
							<c:if test="${empty requestScope.empList }">
								<tr>
									<td class ='category' colspan='9' style='height:50px;'>검색하신 회원이 존재하지 않습니다.</td>
								</tr>
							</c:if>
				  	  	</table>
			      		
			      		<%-- <div id="comment" style="align:center;"><span>부서를 선택해주십시오.</span></div> --%>	
			     	</div>
				</div>
				<%-- 회원목록보기_리스트영역 끝--%>

				<%-- 회원목록보기_페이지바영역--%> 
				<div align="center" style="width: 70%;  border: solid 0px; margin:20px auto;">
					${requestScope.pageBar}
				</div>
				<%-- 회원목록보기_페이지바영역끝--%>
		   
			</div>
			<%--  회원목록보기 영역  끝--%>
  	
  	
  	
    </div>
  
 
 </div>
<!-- !!!!!PAGE CONTENT END!!!!!! -->
</div>

