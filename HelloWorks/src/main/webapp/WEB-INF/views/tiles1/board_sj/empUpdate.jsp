<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% String ctxPath = request.getContextPath(); %>

   
<style type="text/css">
	body,h1,h2,h3,h4,h5,h6,span {font-family: Verdana, sans-serif;}
	
	form{
		font-family: Verdana, sans-serif;
	
	}
	
	.tbldata {
		height: 65px
	}

	input {
		color:#595959;
		border:none;
		height: 35px;
		
	}
	
	.notice {
		color:#595959;
		font-size: 13pt;
	}
	
	.star{
		color:#0070C0;
	}
</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		
		
		// 직위 선택사항을 input창에 넣기.
		var $selRank = $('.selRank');	     // select
		var $inputRank = $('input#ranking'); // input
		
		$selRank.change(function(){
			
			val = $selRank.val();
			
			if(val == -1){
				alert("직위는 필수 선택사항입니다!");
			}
			else{
				$inputRank.val(val);
			}
	
		});

			
		// 조직 선택사항을 input창에 넣기.
		var $selDept = $('.selDept');	       // select
		var $inputDept = $('input#fk_deptnum'); // input
		
		$selDept.change(function(){
			
			val = $selDept.val();
			
			if(val == -1){
				alert("소속조직은 필수 선택사항입니다!");
			}
			else{
				$inputDept.val(val);
			}	
		});
	   
   
		// 완료 버튼
		$("button#empUpdate").click(function(){
				
			// 직위를 선택하지 않은 경우
		    var selectList = document.getElementById("selRank") 		
			if(selectList.options[selectList.selectedIndex].value == "-1"){ 
				alert("직위는 필수 선택사항입니다!");
				return;
			}
			
			// 소속조직을 선택하지 않은 경우
			var selectList = document.getElementById("selDept") 		
			if(selectList.options[selectList.selectedIndex].value == "-1"){ 
				alert("소속조직은 필수 선택사항입니다!");
				return;
			} 
			
			
			// 폼(form)을 전송(submit)
			var frm = document.empUpdateFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath%>/empUpdateEnd.hello2";
			frm.submit(); 

		}); // end of $("button#btnUpdate").click(function(){})-------------

	}); // end of $(document).ready(function(){})---------------

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


<div style="display: flex; margin-top:30px">
<div style="margin:80px 0 800px 248px; padding-left: 3%; width:1000px">
	
	<!-- 상단 고정 -->
	<div class="w3-top" style="z-index:0;  border-bottom: 1px solid #e6e6e6; margin-top:88px; background-color:white; height: 60px">		
		<h3 style="background-color: white; color:#0070C0; margin-left: 30px"><b>사용자 정보</b></h3>
	</div>
	
	
	<form name="empUpdateFrm" style="margin:50px 0 0 30px">
	  <span class="notice">- * 는 필수 선택사항입니다.</span><br>
	  <span class="notice">- 인사팀은 직원들의 직위, 소속조직, 재직상태에 대한 변경이 가능합니다.</span>
	   
	   <table id="tblMemberRegister" style="font-size: 14pt; margin:45px 0 0 20px; color:#595959; width: 50%">
	      <tbody>
	      <tr>
	         <td class="tbldata" style="width: 20%;">성명&nbsp;<span class="star"></span></td>
	         <td class="tbldata" style="width: 80%;">
	             <input type="text" name="empname" id="empname" size="22" value="${requestScope.empvo.empname}" readonly/> 
	         </td>
	      </tr>
	      <tr>
	         <td class="tbldata" style="width: 20%;">아이디</td>
	         <td class="tbldata" style="width: 80%;">
	             <input type="text" name="empid" id="empid" size="22" value="${requestScope.empvo.empid}" readonly/>
	         </td> 
	      </tr>
	      
	       <tr>
	         <td class="tbldata" style="width: 20%;">개인메일</td>
	         <td class="tbldata" style="width: 80%;">
	             <input type="text" name="email" id="email" size="22" value="${requestScope.empvo.noticeemail}" readonly/>
	         </td> 
	      </tr>
	      
	      <tr>
	         <td class="tbldata" style="width: 20%;">사내메일</td>
	         <td class="tbldata" style="width: 80%;">
	             <input type="text" name="email" id="email" size="22"  value="${requestScope.empvo.email}" readonly/>
	         </td> 
	      </tr>
	      
	      <tr>
	         <td class="tbldata" style="width: 20%;">사번</td>
	         <td class="tbldata" style="width: 80%;">
	             <input type="text" name="empno" id="empno" size="22" value="${requestScope.empvo.empno}" readonly/>
	         </td> 
	      </tr>
	      
	      <tr>
	         <td class="tbldata" style="width: 20%;">입사일자&nbsp;<span class="star"></span></td>
	         <td class="tbldata" style="width: 80%;">
	             <input type="text" name="hiredate" id="hiredate" size="22"  value="${requestScope.empvo.hiredate}" readonly/>
	         </td> 
	      </tr>
	      
	      
	       <tr>
	         <td class="tbldata" style="width: 20%;">직위&nbsp;<span class="star">*</span></td>
	         <td class="tbldata">
				<label class="bmax v2 Mr0">
					<input type="hidden" id="ranking" name="ranking" value="${requestScope.empvo.ranking}"/>
						<select id="selRank"  name="selRank" class="selRank" style="border-color: gray">
							    <option value="-1">직위선택</option>
								<option value="5" <c:if test="${requestScope.empvo.ranking eq '5'}">selected</c:if>>사장</option>					
								<option value="4" <c:if test="${requestScope.empvo.ranking eq '4'}">selected</c:if>>팀장</option>
								<option value="3" <c:if test="${requestScope.empvo.ranking eq '3'}">selected</c:if>>부장</option>
								<option value="2" <c:if test="${requestScope.empvo.ranking eq '2'}">selected</c:if>>대리</option>						
								<option value="1" <c:if test="${requestScope.empvo.ranking eq '1'}">selected</c:if>>사원</option>
						</select>
				</label>
			</td>
	      </tr>
	      
	      
	      <tr>
	         <td class="tbldata" style="width: 40%;">소속조직&nbsp;<span class="star">*</span></td>
	         <td class="tbldata">
				<label class="bmax v2 Mr0">
					<input type="hidden" id="fk_deptnum" name="fk_deptnum" value="${requestScope.empvo.fk_deptnum}"/>
					<select id="selDept" name="selDept" class="selDept" style="border-color: gray">
						<option value="-1">조직선택</option>
						<option value="10" <c:if test="${requestScope.empvo.fk_deptnum eq '10'}">selected</c:if>>인사팀</option>
						<option value="20" <c:if test="${requestScope.empvo.fk_deptnum eq '20'}">selected</c:if>>회계팀</option>
						<option value="30" <c:if test="${requestScope.empvo.fk_deptnum eq '30'}">selected</c:if>>총무팀</option>
						<option value="40" <c:if test="${requestScope.empvo.fk_deptnum eq '40'}">selected</c:if>>마케팅팀</option>
						<option value="50" <c:if test="${requestScope.empvo.fk_deptnum eq '50'}">selected</c:if>>영업팀</option>
						<option value="00" <c:if test="${requestScope.empvo.fk_deptnum eq '00'}">selected</c:if>>대표</option>
					</select>
				</label>
			</td>
	      </tr>
	      
	      <tr>
	         <td class="tbldata" style="width: 40%;">재직상태&nbsp;<span class="star">*</span></td>	
	         <td class="tbldata" id="add_status" style="vertical-align: middle; text-align: left">
	            <input type="radio" id="work"   onclick='getStatus(event)' name="empstatus" value="1" <c:if test="${requestScope.empvo.empstatus eq '1'}">checked</c:if>/><label for="work" style="width: 20%" class="ml-3">재직</label>
	            <input type="radio" id="retire" onclick='getStatus(event)' name="empstatus" value="0"  <c:if test="${requestScope.empvo.empstatus eq '0'}">checked</c:if>/><label for="retire" style="width: 20%" class="ml-3">퇴사</label>
	         	<input type="radio" id="break"  onclick='getStatus(event)' name="empstatus" value="2"  <c:if test="${requestScope.empvo.empstatus eq '2'}">checked</c:if>/><label for="break" style="width: 20%" class="ml-3">휴직</label>
	         </td>
	      </tr>
	      </tbody>
	   </table>
	   
	   <div style="margin:40px 0 0 20px">
		   <button type="button"  class="btn" id="empUpdate" style="background-color:#0070C0; margin-left:14px; font-size:21px; width: 130px; height:50px; color:white">
					<b>저장</b>
		   </button>
		   <button type="button"  onclick="location.href='<%= ctxPath %>/emp/viewEmployee.hello2'" class="btn" id="btn1" style="background-color:white; margin-left:60px; font-size:21px; width: 130px; height:50px; color:#595959; border-color:#e6e6e6">
					<b>취소</b>
		   </button>
	   </div>
	   </form>
		
		

</div>
</div>	


