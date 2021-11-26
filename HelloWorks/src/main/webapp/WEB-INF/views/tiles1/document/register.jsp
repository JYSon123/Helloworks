<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %> 

<style>

  .rest { display: none; }
  
     table#tblMemberRegister {
          width: 93%;
          
          /* 선을 숨기는 것 */
          border: hidden;
          
          margin: 10px;
   }  
   
   table#tblMemberRegister #th {
         height: 40px;
         text-align: center;
         background-color: silver;
         font-size: 14pt;
   }
   
   table#tblMemberRegister td {
         /* border: solid 1px gray;  */
         line-height: 30px;
         padding-top: 18px;
         padding-bottom: 8px;
   }
   
   .star { color: grey;
           font-weight: bold;
           font-size: 11pt;
   }
   
   div#container {
   font-family: Arial
   }
  
  

</style>

<script type="text/javascript">

   var b_flagIdDuplicateClick = false;

   $(document).ready(function(){
	   
	   $("span.error").hide();
		
	   $("input#name").focus(); // 첫번째 input인 name에 focus
				
	  /* 
		$("input#name").blur(function(){ // focus가 있다가 잃었을 때
			
			var name = $(this).val().trim();
			if(name == ""){ // 입력하지 않거나 공백만 입력한 경우
				$("table#tblMemberRegister :input").prop("disabled", true); // 우선 다 막아뒀다가
				$(this).prop("disabled", false); // 성명칸만 활성화시키자
				// .prop는 속성값을 가져오거나 추가한다.
				
				$(this).parent().find(".error").show(); // error 메시지 보이게
				$(this).focus(); // focus를 name으로
			}
			else { // 공백이 아닌 글자를 입력했을 경우
				
				$("table#tblMemberRegister :input").prop("disabled", false);
			
				$(this).parent().find(".error").hide();
				
			}

		}); // end of $("input#name").blur(function(){})-----------------
		
		
		
		
		$("input#userid").blur(function(){
			
			var userid = $(this).val().trim();
			if(userid == ""){
				$("table#tblMemberRegister :input").prop("disabled", true); // 우선 다 막아뒀다가
				$(this).prop("disabled", false); // 성명칸만 활성화시키자
				
				$(this).parent().find(".error").show();
				$(this).focus();
			}
			else{
				$("table#tblMemberRegister :input").prop("disabled", false);
				$(this).parent().find(".error").hide();
			}
	
		}); // end of $("input#userid").blur(function(){})-------------
		
			
		
		$("input#pwdcheck").blur(function(){
			
			var pwd = $("input#pwd").val();
			var pwdcheck = $("input#pwdcheck").val();
			
			if(pwd != pwdcheck){
				
				$("table#tblMemberRegister :input").prop("disabled", true);
				$(this).prop("disabled", false);
				$("input#pwd").prop("disabled", true);
				
				$(this).parent().find(".error").show();
				$("input#pwd").focus();
			}
			else {
				$("table#tblMemberRegister :input").prop("disabled", false);
				$(this).parent().find(".error").hide();		
			}
			
		}); // end of $("input#pwdcheck").blur(function(){})------------------ */
		
	
		
		// =================== 아이디 중복검사하기  ===================
		$("img#idcheck").click(function(){
			
			b_flagIdDuplicateClick = true;
			// 가입하기 버튼을 클릭시 "아이디중복확인"을 클릭했는지 안했는지 알아보기 위한 용도임
			
			var empid = $("input#empid").val();
			
			if(empid == "") {
				$("span#idcheckResult").html("아이디를 입력해주세요").css("color", "red");
				return;
			}		

			$.ajax({
				url:"<%= ctxPath%>/idDuplicateCheck.hello2",
				type:"post",
				data:{"empid":$("input#empid").val()},
				dataType:"json",
				success:function(json){

					if(json.n == 1){
						// 입력한 userid가 이미 사용 중이라면
					
						$("span#idcheckResult").html($("input#empid").val()+" 은 이미 사용 중이므로 사용불가합니다.").css("color", "red");
						$("input#userid").val("");
					}
					else{
						// 입력한 userid가 DB 테이블에 존재하지 않는 경우라면
						
						$("span#idcheckResult").html($("input#empid").val()+" 은 사용가능합니다.").css("color", "green");
					}
				},
				error: function(request, status, error){
					alert("code: " +request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					
				}
			}); // 
			
		});// end of $("img#idcheck").click(function(){})----------------
		
		
		// 아이디값이 변경되면 가입하기 버튼을 클릭시 "아이디 중복확인"을 클릭했는지 안 했는지 알아보기 위한 용도
		$("input#userid").bind("change", function(){
			b_flagIdDuplicateClick = false; // 다시 초기화
		});
		
	
		
		$("input#noticeemail").blur(function(){
			
		    var regExp =/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
			// 이메일 정규표현식 객체 생성
			
			var email = $(this).val(); /* 입력을 해온 값 */
			
			var bool = regExp.test(email);
			
			if(!bool) {
				// 이메일이 정규 표현식에 위배된 경우
				$("table#tblMemberRegister :input").prop("disabled",true); 
				$(this).prop("disabled",false);  
		
			    $(this).parent().find(".error").show();   // this에서 한칸 올라가서 클래스가 error인것을 찾아서 보여라
				$(this).focus();
			}
			else {
				// 이메일이 정규표현식에 맞는 경우 
				$("table#tblMemberRegister :input").prop("disabled",false); // 나머지 막힌것들을 다시 활성화
				
				 $(this).parent().find(".error").hide();
			
			}	
			
		});// 아이디가 email 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
		
		
	});// end of $(document).ready(function(){})----------------
	
	
<%--          
         // 폼(form)을 전송(submit)
         var frm = document.addFrm;
         frm.method = "POST";
         frm.action = "<%= ctxPath%>/addEndjy.hello2";
         frm.submit();


	 --%>


	// 가입하기
	function goRegister(){
		
		// *** 필수입력사항에 모두 입력이 되었는지 검사한다. *** //
		var boolFlag = false; 
		
		$("input.requiredInfo").each(function(){
			var data = $(this).val().trim();
			if(data == ""){ // data가 공백이라면
				alert("*표시된 필수입력사항은 모두 입력하셔야 합니다.");
				boolFlag = true;
				return false; 				
			}
		});
		
		if(boolFlag){
			return; // 종료
		}
		
		
		if(!b_flagIdDuplicateClick){ // ID중복검사를 클릭하지 않았다면
			alert("아이디중복확인 클릭하여 ID중복검사를 하세요.");
			return; // 종료
		}

		var frm = document.registerFrm;
		frm.action = "<%= ctxPath%>/registerEnd.hello2";
		frm.method = "post";
		frm.submit();
		
		
	}// end of function goRegister(){}---------------
   
	
	

   
</script>   


<!-- 좌측 상세메뉴 시작 -->
<nav class="w3-sidebar w3-collapse w3-white " style="margin-top:50px; z-index:0; width:300px;background-color:#f5f5f5; overflow: hidden" id="mySidebar"><br>
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

<div style="display: flex; padding-top: 50px; ">
<div style="margin: auto; padding-left: 3%;">

    <h2 style="margin-bottom: 30px; margin-top:100px;">사용자 개별 등록</h2>
   
   
    <hr>
    
    
    <div style="color: gray; font-size: 13px;">- 여기서 설정한 비밀번호는 임시비밀번호로 사용자가 직접 1회 비밀번호를 변경한 후 오피스 사용이 가능합니다.</div>
    <div style="color: gray; font-size: 13px;">- 사원번호, 사내이메일은 자동으로 부여됩니다.</div>


   <div class="row" id="divRegisterFrm" style="margin-top: 20px;">
	   <div class="col-md-12" >
	   <form name="registerFrm">
	   <table id="tblMemberRegister" style="font-size: 10pt">
	      <tbody>
	      <tr>
	         <td style="width: 20%; font-weight: bold;">성명&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	             <input type="text" name="empname" id="empname" class="requiredInfo" /> 
	            <span class="error">성명은 필수입력 사항입니다.</span>
	         </td>
	      </tr>
	      <tr>
	         <td style="width: 20%; font-weight: bold;">아이디&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	             <input type="text" name="empid" id="empid" class="requiredInfo" />&nbsp;&nbsp;
	             <!-- 아이디중복체크 -->
	             <img id="idcheck" src="<%= ctxPath%>/resources/images/b_id_check.gif" style="vertical-align: middle; cursor: pointer;" />
	             <span id="idcheckResult"></span>
	             <span class="error">아이디는 필수입력 사항입니다.</span>
	         </td> 
	      </tr>
	      <tr>
	         <td style="width: 20%; font-weight: bold;">비밀번호&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;"><input type="password" name="emppw" id="emppw" class="requiredInfo" />
	            <span class="error">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.</span>
	         </td>
	      </tr>
	      <tr>
	         <td style="width: 20%; font-weight: bold;">비밀번호확인&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;"><input type="password" id="pwdcheck" class="requiredInfo" /> 
	            <span class="error">암호가 일치하지 않습니다.</span>
	         </td>
	      </tr>
 			
 			<tr>
	 		    <td style="width: 20%; font-weight: bold;">개인이메일&nbsp;</td>
	 			<td>
	 			<input type="text" name="noticeemail" id="noticeemail"  style="width: 190px;"/>
	 			 <span class="error" style="color: red;">이메일 형식에 맞지 않습니다.</span>
			    </td>
	 		</tr>

	 		<tr>
	 		    <td style="width: 20%; font-weight: bold;">소속부서&nbsp;</td>
	 			<td>
	 			 <select  name="fk_deptnum" id="fk_deptnum"  style="width: 15%; height: 30px;">
			     	<option value="10">인사</option>
			     	<option value="20">회계</option>
			     	<option value="30">총무</option>
			     	<option value="40">마케팅</option>
			     	<option value="50">영업</option>
				 </select>  
			    </td>
	 		</tr>	
	 		<tr>
	 		    <td style="width: 20%; font-weight: bold;">직급&nbsp;</td>
	 			<td>
	 			 <select  name="ranking" id="ranking"  style="width: 15%; height: 30px;">
			     	<option value="1">사원</option>
			     	<option value="2">대리</option>
			     	<option value="3">부장</option>
			     	<option value="4">팀장</option>
			     	<option value="5">사장</option>
				 </select>  
			    </td>
	 		</tr>	
	 		<tr>
	 		    <td style="width: 20%; font-weight: bold;">계약연봉&nbsp;</td>
	 			<td>
	 			<input type="text" name="empsalary" id="empsalary"  style="width: 100px;"/> <span>만원</span>
			    </td>
	 		</tr>
	 		<tr>
	 		    <td style="width: 20%; font-weight: bold;">입사일자&nbsp;</td>
	 			<td>
	 			<input type="date" name="hiredate" id="hiredate" />
			    </td>
	 		</tr>
	      <tr>
	         <td colspan="2" style="line-height: 90px;" class="text-center">
	            <button type="button" id="btnRegister" class="btn btn-primary  float-left " onClick="goRegister()" style="border-radius: 3px; margin: 10px 0 60px 0; background-color: #0070C0; color: white;"><h6>등록하기</h6></button> 
	         
	         </td>
	      </tr>
	      </tbody>
	   </table>
	   </form>
	   </div>
	</div>
</div>
		
	<!-- row 끝 -->
	<!-- 중앙 컨텐츠 끝 -->

	<hr>


 <!-- .container 끝 -->


<!-- 사이드바 function -->    
	
</body>
</html>
	
</div>   


    