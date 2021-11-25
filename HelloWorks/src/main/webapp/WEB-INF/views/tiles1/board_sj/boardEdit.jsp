<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

   
<style type="text/css">
	
	form{
		font-family: Verdana, sans-serif;
	
	}
	
	th {
		color:#595959;
		width: 18%;
		font-weight: normal;
	}
	
	table,tr,td{
		border:none;
		font-size: 15pt;
	}
	
</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		<%-- === #167. 스마트 에디터 구현 시작 === --%>
	       //전역변수
	       var obj = [];
	       
	       //스마트에디터 프레임생성
	       nhn.husky.EZCreator.createInIFrame({
	           oAppRef: obj,
	           elPlaceHolder: "content",
	           sSkinURI: "<%= request.getContextPath() %>/resources/smarteditor/SmartEditor2Skin.html",
	           htParams : {
	               // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
	               bUseToolbar : true,            
	               // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
	               bUseVerticalResizer : true,    
	               // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	               bUseModeChanger : true,
	           }
	       });
	       <%-- === 스마트 에디터 구현 끝 === --%>
		
	       
	       $("span.error").hide();
			
			// 정규표현식을 이용한 유효성  검사
			 $("button#pwCheck").click(function(){
				 
				// b_flagpwCheckClick = true; // 암호확인을 클릭했는지 안 했는지 알아보는 용도
				
				var regExp = /^\d{4}$/i;			
				
				var pwVal = $("input#pw").val().trim();	
				
				var bool = regExp.test(pwVal);
				
				if(!bool){
					$("span.error").show();
				}
				else{
					$("span.error").hide();
				}
				
			 }); 
			

	       
		// 완료 버튼
		$("button#btnUpdate").click(function(){
			
			<%-- === 스마트 에디터 구현 시작 === --%>
            //id가 content인 textarea에 에디터에서 대입
            obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
            <%-- === 스마트 에디터 구현 끝 === --%>
			
			// 글제목 유효성 검사
			var subjectVal = $("input#subject").val().trim();
			if(subjectVal == null){
				alert("글제목을 입력하세요!");
				return;
			}
			
			// 글암호 유효성 검사
			var pwVal = $("input#pw").val().trim();
			if(pwVal == ""){
				alert("글암호를 입력하세요!");
				return;
			}
			
			
			<%-- === 스마트에디터 구현 시작 === --%>
	        // 스마트에디터 사용시 무의미하게 생기는 p태그 제거
	         var contentval = $("textarea#content").val();
	                
	          // === 확인용 ===
	          // alert(contentval); // content에 내용을 아무것도 입력치 않고 쓰기할 경우 알아보는것.
	          // "<p>&nbsp;</p>" 이라고 나온다.
	          
	          // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기전에 먼저 유효성 검사를 하도록 한다.
	          // 글내용 유효성 검사 
	            if(contentval == "" || contentval == "<p>&nbsp;</p>") {
	                alert("글내용을 입력하세요!!");
	                return;
	            }
	             
	          // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기
	             contentval = $("textarea#content").val().replace(/<p><br><\/p>/gi, "<br>"); //<p><br></p> -> <br>로 변환
	         /*    
	                     대상문자열.replace(/찾을 문자열/gi, "변경할 문자열");
	             ==> 여기서 꼭 알아야 될 점은 나누기(/)표시안에 넣는 찾을 문자열의 따옴표는 없어야 한다는 점입니다. 
	                          그리고 뒤의 gi는 다음을 의미합니다.

	                g : 전체 모든 문자열을 변경 global
	                i : 영문 대소문자를 무시, 모두 일치하는 패턴 검색 ignore
	         */    
	             contentval = contentval.replace(/<\/p><p>/gi, "<br>"); //</p><p> -> <br>로 변환  
	             contentval = contentval.replace(/(<\/p><br>|<p><br>)/gi, "<br><br>"); //</p><br>, <p><br> -> <br><br>로 변환
	             contentval = contentval.replace(/(<p>|<\/p>)/gi, ""); //<p> 또는 </p> 모두 제거시
	         
	             $("textarea#content").val(contentval);
	          
	             // alert(contentval);
	        <%-- === 스마트에디터 구현 끝 === --%>
	        
    
			
			// 폼(form)을 전송(submit)
			var frm = document.editFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath%>/boardEditEnd.hello2";
			frm.submit();
	
		}); // end of $("button#btnUpdate").click(function(){})--------------------

	}); // end of $(document).ready(function(){})--------------------------

</script>

<!-- 좌측 고정 상세메뉴 시작 -->
<nav class="w3-sidebar w3-collapse w3-white " style="margin-top:20px; z-index:0; width:300px;background-color:#f5f5f5; overflow: hidden" id="mySidebar"><br>
  <div class="w3-container" style="background-color:#f5f5f5; margin-top:10px" >
    <a href="#" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu">
      <i class="fa fa-remove"></i>
    </a>
	<br><br>
	
	<button type="button" onclick="location.href='<%= ctxPath %>/boardAdd.hello2'" class="btn" id="btn1" style="background-color:#0070C0; margin-left:14px; font-size:21px; width: 240px; height:63px; color:white">
				<b>글쓰기</b></button>
	<br>
  </div>
  <div class="w3-bar-block" style="background-color:#f5f5f5; height: 100%">
	<div style="margin-left:42px; font-size: 16pt; color:#595959">
	  <br>
	  <a href="<%= ctxPath %>/list.hello2" class="w3-bar-item w3-button">사내공지</a>
  	</div>
  </div>  
</nav>
<!-- 좌측 고정 상세메뉴 끝 -->



<div style="display: flex; margin-top:30px">
<div style="margin:80px 0 800px 300px; padding-left: 3%;">
	
	<form name="editFrm">
	
	<div style="margin: 20px 0 ">
			<button type="button" class="btn btn-lg mr-3 " id="btnUpdate" style="background-color: white; color:#0070C0; font-size: 18pt">수정완료</button>
			<button type="button" class="btn btn-lg" onclick="javascript:history.back()" style="background-color: white; color:#0070C0; font-size: 18pt">취소</button> 
		</div>
	
	
	
	<table style="width: 1300px; margin-left:6px" class="table">
			<tr>
				<th>제목</th>
				<td colspan="3">
						<input type="text" name="subject" id="subject" size="90" value="${requestScope.boardvo.subject}"/> 					
				</td>		
			</tr>
			<tr>
				<th>성명</th>
				<td style="width: 40%">
					<input type="hidden" name="seq" value="${requestScope.boardvo.seq}" />
					${requestScope.boardvo.name}
				</td>
			</tr>
			
			<tr>
				<th style="vertical-align: middle; color:#595959">내용</th>
				<td colspan="3"> 
					<textarea style="width: 100%; height: 612px;" name="content" id="content" >${requestScope.boardvo.content}</textarea> 
				</td>
			</tr>
		
			
			
			<tr>
				<th>글암호</th>
				<td colspan="3">
					<input type="password" name="pw" id="pw" /> 
					<button type="button" class="btn btn-light" id="pwCheck" style=" font-size:16px; width: 90px; height:38px">
					암호확인</button>
					<span class="error" style="color:#0070C0; font-size:17px">암호는 숫자 네자리로 입력해주세요.</span>
				</td>
			</tr>
		</table>
	
	</form>

	
</div>
</div>	


