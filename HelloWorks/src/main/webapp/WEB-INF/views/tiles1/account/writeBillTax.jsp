<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
	
	table.mytbl td {
		border: solid 1px red;
		padding: 10px;
		
	}
	
	table.mytbl {
		color: red;
		border: solid 1px red; 
		text-align: center; 
		vertical-align: middle; 
		width: 100%;
		margin: 0px;
	}
	
	td.non-title {
		text-align: left;
	}
	
	input[type=text] {
		width: 100%;
	}
	
</style>

<script type="text/javascript">
	
	$(document).ready(function() {
		
		var now = new Date();
		
		var year = now.getFullYear();
		
		var min = year + "-01-01";
		
		// === jQuery UI 의 datepicker === //
      	$("input.datepicker").datepicker({
                 dateFormat: 'yy-mm-dd'  //Input Display Format 변경
                ,showOtherMonths: true   //빈 공간에 현재월의 앞뒤월의 날짜를 표시
                ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
                ,changeYear: true        //콤보박스에서 년 선택 가능
                ,changeMonth: true       //콤보박스에서 월 선택 가능                
                ,showOn: "both"          //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
                ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
                ,buttonImageOnly: true   //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
                ,buttonText: "선택"       //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
                ,yearSuffix: "년"         //달력의 년도 부분 뒤에 붙는 텍스트
                ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
                ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
                ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
                ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
              	,minDate: min //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
                ,maxDate: "today" //최대 선택일자(+1D:하루후, +1M:한달후, +1Y:일년후)                
        });                    
            
        //초기값을 오늘 날짜로 설정
        $('.datepicker').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후) 
      	$('.selldate').datepicker('setDate', '');
        
		///////////////////////////////////////////////////////////////////////////
		
		$(".onlyNum").keyup(function() {
			
			// 숫자만 입력하도록 이벤트 작성하기!!!
			
		});
		
	})
	
	// Function Declaration
		
	
</script>

<jsp:include page="template_KJH.jsp"/>

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 300px;">
    
    <div class="container" style="margin-top: 70px;">
    	    		
   		<h4>세금계산서 작성</h4>
   		
   		<hr>
   		
   		<form style="width: 100%; font-size: 10pt;" name="compFrm">
   		
			<table class="mytbl">
				
				<tr>
					<td rowspan="2" colspan="9" style="width: 53.4%;"><h2>전자세금계산서</h2><span>(공급자보관용)</span></td>
					<td colspan="1" style="width: 20%;">책번호</td>
					<td colspan="1" style="width: 20%; text-align: right;">권</td>
					<td colspan="2" style="text-align: right;">호</td>					
				</tr>
				<tr>
					<td colspan="1">일련번호</td>
					<td colspan="3"></td>
				</tr>
			
			</table>
			
			<table class="mytbl">
				
				<tr>
					<td rowspan="6" style="width: 3%;">공급자</td>
					<td colspan="1" style="width: 15%;">등록번호</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="mycompany_id" value="${sessionScope.comp.mycompany_id}" readonly/></td>
					<td rowspan="6" style="width: 3%;">공급받는자</td>
					<td colspan="1" style="width: 15%;">등록번호<button type="button" class="btn btn-sm btn-outline-danger ml-1">찾기</button></td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="customer_id"/></td>				
				</tr>
				
				<tr>
					<td colspan="1" style="width: 15%;">상호<br>(업체명)</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="mycompany_comp" value="${sessionScope.comp.mycompany_comp}" readonly/></td>
					<td colspan="1" style="width: 15%;">상호<br>(업체명)</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="customer_comp"/></td>				
				</tr>
				
				<tr>
					<td colspan="1" style="width: 15%;">성명</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="mycompany_name" value="${sessionScope.comp.mycompany_name}" readonly/></td>
					<td colspan="1" style="width: 15%;">성명</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="customer_name"/></td>				
				</tr>
				
				<tr>
					<td colspan="1" style="width: 15%;">사업장<br>주소</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="mycompany_addr" value="${sessionScope.comp.mycompany_addr}" readonly/></td>
					<td colspan="1" style="width: 15%;">사업장<br>주소</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="customer_addr"/></td>				
				</tr>	
				
				<tr>
					<td colspan="1" style="width: 15%;">업종</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="mycompany_sort" value="${sessionScope.comp.mycompany_sort}" readonly/></td>
					<td colspan="1" style="width: 15%;">업종</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="customer_sort"/></td>				
				</tr>					
				
			</table>
			
			<table class="mytbl">
				
				<tr>
					<td style="width: 15.35%;">작성일</td>
					<td style="width: 34.65%;">공급가액</td>
					<td style="width: 34.65%;">세액</td>
					<td>비고</td>
				</tr>
				
				<tr>
					<td style="width: 15.35%;"><input type="text" class="datepicker" name="regdate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 34.65%;"><input type="text" name="totalprice" readonly/></td>
					<td style="width: 34.65%;"><input type="text" name="taxprice" readonly/></td>
					<td></td>
				</tr>
				
			</table>
			
			<table class="mytbl">
				
				<tr>
					<td style="width: 15.35%;">공급일자</td>
					<td style="width: 14.65%;">품목</td>
					<td style="width: 5%;">규격</td>
					<td style="width: 5%;">수량</td>
					<td style="width: 10%;">단가</td>
					<td style="width: 20%;">공급가액</td>
					<td style="width: 14.65%;">세액</td>
					<td>비고</td>
				</tr>
				
				<tr>
					<td style="width: 15.35%;"><input type="text" class="datepicker selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 14.65%;"><input type="text" class="sellprod"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 5%;"><input type="text" class="sellamount onlyNum"/></td>
					<td style="width: 10%;"><input type="text" class="selloneprice onlyNum"/></td>
					<td style="width: 20%;"><input type="text" class="selltotalprice onlyNum"/></td>
					<td style="width: 14.65%;"><input type="text" class="selltax onlyNum"/></td>
					<td></td>
				</tr>
				
				<tr>
					<td style="width: 15.35%;"><input type="text" class="datepicker selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 14.65%;"><input type="text" class="sellprod"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 5%;"><input type="text" class="sellamount onlyNum"/></td>
					<td style="width: 10%;"><input type="text" class="selloneprice onlyNum"/></td>
					<td style="width: 20%;"><input type="text" class="selltotalprice onlyNum"/></td>
					<td style="width: 14.65%;"><input type="text" class="selltax onlyNum"/></td>
					<td></td>
				</tr>
				
				<tr>
					<td style="width: 15.35%;"><input type="text" class="datepicker selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 14.65%;"><input type="text" class="sellprod"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 5%;"><input type="text" class="sellamount onlyNum"/></td>
					<td style="width: 10%;"><input type="text" class="selloneprice onlyNum"/></td>
					<td style="width: 20%;"><input type="text" class="selltotalprice onlyNum"/></td>
					<td style="width: 14.65%;"><input type="text" class="selltax onlyNum"/></td>
					<td></td>
				</tr>
				
				<tr>
					<td style="width: 15.35%;"><input type="text" class="datepicker selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 14.65%;"><input type="text" class="sellprod"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 5%;"><input type="text" class="sellamount onlyNum"/></td>
					<td style="width: 10%;"><input type="text" class="selloneprice onlyNum"/></td>
					<td style="width: 20%;"><input type="text" class="selltotalprice onlyNum"/></td>
					<td style="width: 14.65%;"><input type="text" class="selltax onlyNum"/></td>
					<td></td>
				</tr>
				
				<tr>
					<td style="width: 15.35%;"><input type="text" class="datepicker selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 14.65%;"><input type="text" class="sellprod"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 5%;"><input type="text" class="sellamount onlyNum"/></td>
					<td style="width: 10%;"><input type="text" class="selloneprice onlyNum"/></td>
					<td style="width: 20%;"><input type="text" class="selltotalprice onlyNum"/></td>
					<td style="width: 14.65%;"><input type="text" class="selltax onlyNum"/></td>
					<td></td>
				</tr>
				
				<tr style="display: none;">
					<td style="width: 15.35%;"><input type="text" name="selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 14.65%;"><input type="text" name="sellprod"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 5%;"><input type="text" name="sellamount"/></td>
					<td style="width: 10%;"><input type="text" name="selloneprice"/></td>
					<td style="width: 20%;"><input type="text" name="selltotalprice"/></td>
					<td style="width: 14.65%;"><input type="text" name="selltax"/></td>
					<td></td>
				</tr>
				
			</table>
			
			<table class="mytbl">
				
				<tr>
					<td style="width: 30%;">합계금액</td>
					<td style="width: 10%;">현금</td>
					<td style="width: 10%;">수표</td>
					<td style="width: 10%;">어음</td>
					<td style="width: 10%;">외상미수금</td>
					<td rowspan="2">위 금액을 영수 <span style="border: solid 1px black; border-radius: 20px;">청구</span> 함.</td>
				</tr>
				
				<tr>
					<td style="width: 30%;"><input type="text" id="total" readonly/></td>
					<td style="width: 10%;"></td>
					<td style="width: 10%;"></td>
					<td style="width: 10%;"></td>
					<td style="width: 10%;"></td>
				</tr>
								
			</table>
			
		</form>
    		
    </div>
    
  </div>
  

</div>
<!-- !!!!!PAGE CONTENT END!!!!!! -->