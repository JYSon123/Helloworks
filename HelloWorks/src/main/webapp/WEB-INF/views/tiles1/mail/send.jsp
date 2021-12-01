<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>

<style type="text/css">
	
	tr td.title {
		text-align: center;
		width: 20%;
	}
	
</style>

<script type="text/javascript">
	
	$(document).ready(function(){
		
		//전역변수
	    var obj = [];
	    
	    //스마트에디터 프레임생성
	    nhn.husky.EZCreator.createInIFrame({
	        oAppRef: obj,
	        elPlaceHolder: "mailcontent",
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
	    
		
		$("div#recidList").hide();
		
		$("input.recid").keyup(function(){
			
			var wordLength = $(this).val().trim().length;
			
			if(wordLength == 0) {
				$("div#recidList").hide();
			}
			
			else {
								
				var searchWord = $(this).val();
				
				var searchArr = $(this).val().trim().split(",");
				
				
				
				if(event.keyCode == 188 || searchArr[searchArr.length - 1] == "") {
					return;
				} 
				
				if(searchArr.length > 1 ) {
					searchWord = searchArr[searchArr.length - 1];
				}
								
				$.ajax({
					url:"<%= request.getContextPath()%>/recidSearchShow.hello2",
					type:"GET",
					data:{"searchWord":searchWord},
					dataType:"JSON",
					success:function(json){
						if(json.length > 0) {
							var html = "";
							
							$.each(json, function(index, item) {
																
								// alert(item.sendname);	
								var empname = item.empname;
								var deptname = item.deptname;
								var empid = item.empid;
								// alert(item.empid);
								
								var word = "<"+deptname+">" + " " + empid + " " + empname;
								
								
								var index = word.toLowerCase().indexOf(searchWord.toLowerCase() );
								
								var len = searchWord.length;
								
								var result = word.substr(0, index) + "<span style='color:blue;'>"+word.substr(index,len)+"</span>" + word.substr(index+len); 
								
								html += "<span style='cursor:pointer' class='result'>"+result+"</span><br>";
							});
							
							var input_width = $("input.recid").css("width"); // 검색어 input 태그 width 알아오기
							
							$("div#recidList").css({"width":input_width}); // 검색결과 div의 width 크기를 검색어 input 태그 width 와 일치시키기 
							
							$("div#recidList").html(html);
							$("div#recidList").show();
						}
						else {
							$("div#recidList").empty();
							$("div#recidList").hide();
						}
					},
					error: function(request, mailstatus, error) {
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					}
				});
			}
		
		}); // end of $("input#searchWord").keyup(function(){
			
		$(document).on("click",".result",function(){
			/* var word = $(this).text();
			
			// alert("확인");
			
			var arrEmpid = $(this).text().split(" ");
			var empid = arrEmpid[arrEmpid.length-1];
			
			 alert(empid);
			
			$("input#searchWord").text(word);
			$("div#recidList").empty();
			$("div#recidList").hide();
			
		//	$("input#showempid").val(word);
			$("input[name=empid]").val(empid);
			
			
			
			var addEmpid = $("input#showempid").val();
			addEmpid = $(this).text() + "," ;
			$("input#showempid").val(addEmpid); */
			
			var arrEmpidinput = $("input.recid").val().split(",");
			
			arrEmpidinput[arrEmpidinput.length-1] = $(this).text();
			
			var empidjoin = arrEmpidinput.join(",");
			
			$("input.recid").val(empidjoin + ",");
			
			$("div#recidList").empty();
			$("div#recidList").hide();
			// alert($("input.recid").val());
			var arrEmpidSelectList = $("input.recid").val().split(","); 
			var lastone = arrEmpidSelectList[arrEmpidSelectList.length-2];
			
			// lastone은 <인사> test 테스트니깐 공백으로 쪼개서 아이디만 찾아서 input#empid에 넣어준다.
			var arrRealRecid = lastone.split(" ");
			
			if(arrEmpidSelectList.length == 2) {
				$("input#recid").val(arrRealRecid[1]);
			}
			else{
				$("input#recid").val($("input#recid").val() + "," + arrRealRecid[1]);
			}
			
		});
		
		$("button#btnWrite").click(function(){
			
			obj.getById["mailcontent"].exec("UPDATE_CONTENTS_FIELD", []);
			
			// 메일제목 유효성 검사
			var mailsubjectVal = $("input#mailsubject").val().trim();
			if(mailsubjectVal == "") {
				alert("글제목을 입력하세요.");
				return;
			}
						
			contentval = $("textarea#mailcontent").val().replace(/<p><br><\/p>/gi, "<br>");
			
			contentval = contentval.replace(/<\/p><p>/gi, "<br>"); //</p><p> -> <br>로 변환  
	        contentval = contentval.replace(/(<\/p><br>|<p><br>)/gi, "<br><br>"); //</p><br>, <p><br> -> <br><br>로 변환
	        contentval = contentval.replace(/(<p>|<\/p>)/gi, ""); //<p> 또는 </p> 모두 제거시
			
	        $("textarea#mailcontent").val(contentval);
	        
	        
	        var sendid = $("input#sendid").val();
	        
	        
	         //alert(mailsubjectVal);
	         // alert(contentval);
	        
			var frm = document.sendFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath%>/sendEnd.hello2";
			frm.submit();
		});
		
			
	});//end of $(document).ready(function(){ -------------------------------
	
	// Function Declaration
	
	
		
</script>    

<jsp:include page="template_JDH.jsp"/>


<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 300px;">
    
    <div class="container" style="margin-top: 70px;">
    	
		<c:if test="${requestScope.fk_mailseq != '-9999'}">
			<h2 style="margin-bottom: 30px;">메일쓰기</h2>
		</c:if>
		
		<c:if test="${requestScope.fk_mailseq == '-9999'}">
			<h2 style="margin-bottom: 30px;">답변메일쓰기</h2>
		</c:if>
		
		<div class="mx-auto px-auto" style="width: 100%;">
		
		<!-- <form name="addFrm"> -->
		<!-- 파일 첨부하기 -->
		<form name="sendFrm" enctype="multipart/form-data">
			<table class="w-100 table table-bordered" style="font-size: 10pt; font-weight: normal;">
				<tr>
					<td style="background-color: #DDD;" class="title">받는사람</td>
					<td>
						<%-- <input type="hidden" name="sendid" value="${sessionScope.loginuser.userid}"  /> --%>
						<c:if test="${empMap == null}">
						<input type="text" id="showempid"  class="recid" autocomplete="off" style="width: 100%;"/>
						<input type="hidden" name="recid" id="recid" />
						<input type="hidden" id="sendid" name="sendid" value="${sessionScope.loginEmp.empid }" />
						<div id="recidList" style="border:solid 2px black; height:100px; overflow:auto; margin-top: -1px; border-top: 0px; ">
						
						</div>
						</c:if>
						<c:if test="${empMap != null}">
						<input type="text" id="showempid"  class="recid" autocomplete="off" style="width: 100%;" value="&lt;${empMap.deptname}&gt; ${empMap.empid} ${empMap.empname}" readonly/>
						<input type="hidden" name="recid" id="recid" value="${empMap.empid}" readonly/>
						<input type="hidden" id="sendid" name="sendid" value="${sessionScope.loginEmp.empid }" />
						<div id="recidList" style="border:solid 2px black; height:100px; overflow:auto; margin-top: -1px; border-top: 0px; ">
						
						</div>
						</c:if>
					</td>
				</tr>
				<tr>
					<td style="background-color: #DDD" class="title">제목</td>
					<td>
						<!-- 원메일쓰기인 경우 -->
						<c:if test="${requestScope.fk_mailseq == ''}"> 
							<input type="text" name="mailsubject" id="mailsubject"  style="width: 100%;" />
						</c:if>
						<!-- 답변메일쓰기인 경우 -->
						<c:if test="${requestScope.fk_mailseq != ''}">
							<input type="text" name="mailsubject" id="mailsubject"  style="width: 100%;" value="${requestScope.mailsubject}에 대한 답변글입니다." />
						</c:if> 
					
					</td>
				</tr>
				<tr>
					<td style="background-color: #DDD; vertical-align: middle;" class="title">내용</td>
					<td>
						<textarea style="width:100%; height: 320px;" name="mailcontent" id="mailcontent"></textarea>
					</td>
				</tr>
				<!-- === #150. 파일첨부 타입 추가하기 === -->
				<tr>
					<td style="background-color: #DDD" class="title">파일첨부</td>
					<td>
						<input type="file" name="attach" />
					</td>
				</tr>
				
			</table>
			
			<%-- 답변메일쓰기가 추가된 경우 시작 --%>
			<input type="hidden" name="fk_mailseq" value="${requestScope.fk_mailseq}"/>
			
			<%-- 답변메일쓰기가 추가된 경우 끝 --%>
			
			<div style="margin: 20px;">
				<button type="button" class="btn btn-secondary btn-lg mr-3" id="btnWrite">메일쓰기</button>
				<button type="button" class="btn btn-secondary btn-lg" onclick="javascript:history.back()">취소</button>
			</div>
		</form>
    	</div>    	
    </div>
    
  </div>
  

</div>
<!-- !!!!!PAGE CONTENT END!!!!!! -->
