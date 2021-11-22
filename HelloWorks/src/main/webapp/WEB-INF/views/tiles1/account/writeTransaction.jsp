<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
	
	table.mytbl td {
		border: solid 1px blue;
		padding: 10px;
		
	}
	
	table.mytbl {
		color: blue;
		border: solid 1px blue; 
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
	
	.checkedPayment {
		border: solid 1px black;
		border-radius: 20px;
	}
	
</style>

<script type="text/javascript">
	
	$(document).ready(function() {
		
		var searchFlag = false;
		
		$("input[name=payment]").bind('click', function() {
			$(this).parent().find('label').removeClass('checkedPayment');
			$(this).next().addClass('checkedPayment');
		});
		
		$("button#search").click(function() {
			
			if(searchFlag == false) {
				
				$.ajax({url:"<%=request.getContextPath()%>/account/searchCustomer.hello2",
					dataType:"JSON",
					success:function(json) {
						
						var cvoList = json.cvoList;
						
						if(cvoList.length == 0) {
							alert("등록된 거래처가 존재하지 않습니다.");
							return;
						}
						
						else {
							
							var html = "<table class='table table-bordered mx-auto px-auto table-hover'>";
							
							html += "<thead class='thead-dark'><tr style='font-size: 9pt; text-align: center;'>";
							
							html +=		"<th>사업자등록번호</th>";
							html +=		"<th>업체명(상호)</th>";
							html +=		"<th>성명</th>";
							html +=		"<th>사업장주소</th>";
							
							html += "</tr></thead><tbody>";
							
							
							for(var i=0; i<cvoList.length; i++) {
								
								html += "<tr style='font-size: 9pt; text-align: center;' class='customer'>";
								
								html +=		"<td>" + cvoList[i].customer_id + "</td>"
								html +=		"<td>" + cvoList[i].customer_comp + "</td>"
								html +=		"<td>" + cvoList[i].customer_name + "</td>"
								html +=		"<td>" + cvoList[i].customer_addr + "</td>"								
								
								html += "</tr>";
								
							}
							
							html += "</tbody></table>";
							
							$("div#searchBody").html(html);
							
							searchFlag = true;
							
							$("button#btn_searchModal").trigger('click'); // 모달켜주기
							
						}
												
					},
					error: function(request, status, error){
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					}
				});
				
			}
			
			else {
				$("button#btn_searchModal").trigger('click'); // 모달켜주기
			}
			
		});
		
		/////////////////////////////////////////////////////////////////////////////
		
		$("input[name=customer_id]").blur(function() {
			
			$(this).val($(this).val().trim());
			
			if($(this).val() != "") {
				
				var regExp = /^\d{3}-\d{2}-\d{5}$/; // 000-00-00000의 형식
				 
				var compid = regExp.test($(this).val());
				 
				if(!compid) {
					alert("형식에 맞지 않는 사업자 등록번호이거나, 존재하지 않는 사업자 등록번호입니다.");
					$(this).val("");
					$(this).focus();
					return;
				}
				
			}
			
		});
		
		/////////////////////////////////////////////////////////////////////////////
		
		$(document).on('click','tr.customer', function() {
			
			var tdArr = $(this).find('td');
			
			var dataArr = []
			
			tdArr.each(function(index, item) {				
				dataArr.push($(this).text().trim());				
			});
			
			$("button.thisclose").click();
			
			$("input[name=customer_id]").val(dataArr[0]);
			$("input[name=customer_comp]").val(dataArr[1]);
			$("input[name=customer_name]").val(dataArr[2]);
			$("input[name=customer_addr]").val(dataArr[3]);
			
		});
		
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
		
		// 단가입력
		$("input.selloneprice").blur(function() {			
				
			var amount = Number($(this).parent().prev().find('input').val());
			
			if($(this).val() != "") {
				// 수량입력란이 비어있으면 넣어주기
				if(amount == "") {
					$(this).parent().prev().find('input').val(1);
					amount = 1;
				}
			
			}
			
			else {
				$(this).parent().prev().find('input').val(0);
			}
			
			var n_price = Number($(this).val());
			
			var total_price = n_price * amount;
			
			$(this).parent().next().find('input').val(total_price);
							
			getTotal(); // 총 공급가액 계산해서 넣어야 함
										
		});
		
		/////////////////////////////////////////////////////////////////////////////
		
		// 수량입력
		$("input.sellamount").blur(function() {
			
			if($(this).parent().next().find('input').val() != "") {
				
				if($(this).val().trim() == "")
					$(this).val(1);
				
				var total_price = Number($(this).val()) * Number($(this).parent().next().find('input').val());
				
				$(this).parent().next().next().find('input').val(total_price);
				
				getTotal(); // 총 공급가액 계산해서 넣어야 함
				
			}
						
		});
					
	})//--------------------------------------------------------------------------------------------
	
	// Function Declaration
	
	// 합계금액 계산
	function getTotal() {
		
		var total = 0;
		
		var arr_selltotalprice = $("input.selltotalprice");
		
		arr_selltotalprice.each(function(index, item) {
									
			var sellprice = Number($(item).val());
			
			if(sellprice != "") 
				total += sellprice
			
		});
				
		$("input[name=totalprice]").val(total);
				
	}
	
	// 유효성검사 및 제출
	function goSubmit() {
		
		var flag = false;
		
		var frm = document.billFrm;
		
		// 총 공급가액이 모두 없으면 아무것도 입력하지 않은 것!
		if(frm.totalprice.value.trim() == "" || frm.totalprice.value == "0") {
			alert("내용을 입력하세요.");
			return;
		}
		
		// 공급가액과 세액이 모두 0일 경우 해당 행의 내용물 비워주기
		$("input.selltotalprice").each(function(index, item) {
			
			if($(this).val() == "0" || $(this).val() == "") {					
				$(this).parent().parent().find('input').val("");				
			}	
			
		});
		
		// 1. 공급받는자		
		frm.customer_id.value = frm.customer_id.value.trim();
		
		if(frm.customer_id.value == "") {
			alert("공급받는자의 사업자등록번호를 입력하세요.");
			frm.customer_id.focus();
			return;
		}
		
		// 2. 작성일
		frm.regdate.value = frm.regdate.value.trim();
		
		if(frm.regdate.value == "") {
			alert("작성일을 입력하세요.");
			frm.regdate.focus();
			return;
		}
		
		// 3. 공급일자		
		var arrSelldate = [];
		
		$("input.selltotalprice").each(function(index, item) {
			
			if($(this).val() != "") {				
				var date = $(this).parent().parent().find(".selldate").val().trim();
				
				if(date == "") {
					alert("공급일자를 입력하세요.");
					flag = true;
					return false;
				}
				
				else {
					arrSelldate.push(date); 
				}				
			}	
			
		});
		
		if(flag == true)
			return;
		
		frm.selldate.value = arrSelldate.join(",");
		
		// 4. 품목명
		var arrSellprod = [];
		
		$("input.selltotalprice").each(function(index, item) {
			
			if($(this).val() != "") {				
				var sellprod = $(this).parent().parent().find(".sellprod").val().trim();
				
				if(sellprod == "") {
					alert("품목명을 입력하세요.");
					flag = true;
					return false;
				}
				
				else {
					arrSellprod.push(sellprod); 
				}				
			}	
			
		});

		if(flag == true)
			return;
		
		frm.sellprod.value = arrSellprod.join(",");
		
		// 5. 수량(입력안해도 된다)
		var arrSellamount = [];
		
		$("input.selltotalprice").each(function(index, item) {
			
			if($(this).val() != "") {				
				var sellamount = $(this).parent().parent().find(".sellamount").val().trim();
				
				if(sellamount == "")
					sellamount = "1";
				
				arrSellamount.push(sellamount);				
			}	
			
		});
		
		frm.sellamount.value = arrSellamount.join(",");
		
		// 6. 단가
		var arrSelloneprice = [];

		$("input.selltotalprice").each(function(index, item) {
			
			if($(this).val() != "") {				
				var selloneprice = $(this).parent().parent().find(".selloneprice").val().trim();
				
				if(selloneprice == "") {
					alert("단가를 입력하세요.");
					flag = true;
					return false;
				}
				
				else {
					arrSelloneprice.push(selloneprice);
				}
				 							
			}	
			
		});

		if(flag == true)
			return;
		
		frm.selloneprice.value = arrSelloneprice.join(",");
		
		// 8. 공급가액
		var arrSelltotalprice = [];

		$("input.selltotalprice").each(function(index, item) {
			
			if($(this).val() != "") {				
				
				var selltotalprice = $(this).parent().parent().find(".selltotalprice").val().trim();
				
				if(selltotalprice == "")
					selltotalprice = "0";	
				
				arrSelltotalprice.push(selltotalprice);
			}
						
		});
		
		frm.selltotalprice.value = arrSelltotalprice.join(",");
				
		frm.action = "<%=request.getContextPath()%>/account/insertTransaction.hello2";
		frm.method = "POST";		
		frm.submit();
		
	}
	
</script>

<jsp:include page="template_KJH.jsp"/>

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 300px;">
    
    <div class="container" style="margin-top: 70px;">
    	    		
   		<h4>거래명세서 작성</h4>
   		
   		<hr>
   		
   		<form style="width: 100%; font-size: 10pt; border: solid 1px blue; padding: 1px;" name="billFrm">
   		
			<table class="mytbl">
				
				<tr>					
					<td style="width: 18%;">작성일자</td>
					<td rowspan="2" style="width: 53.1%;"><h2>거래명세표</h2><span>(공급자보관용)</span></td>					
				</tr>
				<tr>
					<td><input type="text" class="datepicker" name="regdate" class="form-control" style="width:80%; margin-right: 2%;"></td>
				</tr>
			
			</table>
			
			<table class="mytbl">
				
				<tr>
					<td rowspan="6" style="width: 3%;">공급자</td>
					<td colspan="1" style="width: 15%;">등록번호</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="mycompany_id" class="form-control" value="${sessionScope.comp.mycompany_id}" readonly/></td>
					<td rowspan="6" style="width: 3%;">공급받는자</td>
					<td colspan="1" style="width: 15%;">등록번호</td>
					<td colspan="2" style="width: 32%;" class="non-title">
						<div class="input-group mx-0 px-0">
							<div class="input-group-prepend" style="width: 80%; z-index: 0;">
								<input type="text" class="form-control" name="customer_id" placeholder="반드시 -을 포함하여 입력하세요" autocomplete="off">
							</div>				
							<div class="input-group-append px-0 mx-0" style="width: 20%; z-index: 1;">
								<button class="btn btn-outline-primary btn-sm mx-0 w-100" type="button" id="search"><i class="fas fa-search-plus"></i>검색</button>
							</div>
						</div>
					</td>				
				</tr>
				
				<tr>
					<td colspan="1" style="width: 15%;">상호<br>(업체명)</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="mycompany_comp" class="form-control" value="${sessionScope.comp.mycompany_comp}" readonly/></td>
					<td colspan="1" style="width: 15%;">상호<br>(업체명)</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="customer_comp" class="form-control" autocomplete="off" /></td>				
				</tr>
				
				<tr>
					<td colspan="1" style="width: 15%;">성명</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="mycompany_name" class="form-control" value="${sessionScope.comp.mycompany_name}" readonly/></td>
					<td colspan="1" style="width: 15%;">성명</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="customer_name" class="form-control" autocomplete="off" /></td>				
				</tr>
				
				<tr>
					<td colspan="1" style="width: 15%;">사업장<br>주소</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="mycompany_addr" class="form-control" value="${sessionScope.comp.mycompany_addr}" readonly/></td>
					<td colspan="1" style="width: 15%;">사업장<br>주소</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="customer_addr" class="form-control" autocomplete="off"/></td>				
				</tr>	
				
				<tr>
					<td colspan="1" style="width: 15%;">업종</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" name="mycompany_sort" class="form-control" value="${sessionScope.comp.mycompany_sort}" readonly/></td>
					<td colspan="1" style="width: 15%;">업종</td>
					<td colspan="2" style="width: 32%;" class="non-title"><input type="text" class="form-control" readonly/></td>				
				</tr>					
				
			</table>
			
			<table class="mytbl">
				
				<tr>
					<td style="width: 15.35%;">공급일자</td>
					<td style="width: 21.65%;">품목</td>
					<td style="width: 5%;">규격</td>
					<td style="width: 8%;">수량</td>
					<td style="width: 10%;">단가</td>
					<td style="width: 30%;">금액</td>
					<td>비고</td>
				</tr>
				
				<tr>
					<td style="width: 15.35%;"><input type="text" class="datepicker selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 21.65%;"><input type="text" class="sellprod form-control"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 8%;"><input type="text" class="sellamount onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/></td>
					<td style="width: 10%;"><input type="text" class="selloneprice onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/></td>
					<td style="width: 30%;"><input type="text" class="selltotalprice onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" readonly/></td>
					<td></td>
				</tr>
				
				<tr>
					<td style="width: 15.35%;"><input type="text" class="datepicker selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 21.65%;"><input type="text" class="sellprod form-control"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 8%;"><input type="text" class="sellamount onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/></td>
					<td style="width: 10%;"><input type="text" class="selloneprice onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/></td>
					<td style="width: 30%;"><input type="text" class="selltotalprice onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" readonly/></td>
					<td></td>
				</tr>
				
				<tr>
					<td style="width: 15.35%;"><input type="text" class="datepicker selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 21.65%;"><input type="text" class="sellprod form-control"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 8%;"><input type="text" class="sellamount onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/></td>
					<td style="width: 10%;"><input type="text" class="selloneprice onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/></td>
					<td style="width: 30%;"><input type="text" class="selltotalprice onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" readonly/></td>
					<td></td>
				</tr>
				
				<tr>
					<td style="width: 15.35%;"><input type="text" class="datepicker selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 21.65%;"><input type="text" class="sellprod form-control"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 8%;"><input type="text" class="sellamount onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/></td>
					<td style="width: 10%;"><input type="text" class="selloneprice onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/></td>
					<td style="width: 30%;"><input type="text" class="selltotalprice onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" readonly/></td>
					<td></td>
				</tr>
				
				<tr>
					<td style="width: 15.35%;"><input type="text" class="datepicker selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 21.65%;"><input type="text" class="sellprod form-control"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 8%;"><input type="text" class="sellamount onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/></td>
					<td style="width: 10%;"><input type="text" class="selloneprice onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/></td>
					<td style="width: 30%;"><input type="text" class="selltotalprice onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" readonly/></td>
					<td></td>
				</tr>
				
				<tr>
					<td style="width: 15.35%;"><input type="text" class="datepicker selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 21.65%;"><input type="text" class="sellprod form-control"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 8%;"><input type="text" class="sellamount onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/></td>
					<td style="width: 10%;"><input type="text" class="selloneprice onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/></td>
					<td style="width: 30%;"><input type="text" class="selltotalprice onlyNum form-control" autocomplete="off" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" readonly/></td>
					<td></td>
				</tr>
				
				<tr style="display: none;">
					<td style="width: 15.35%;"><input type="text" name="selldate" style="width:80%; margin-right: 2%;"></td>
					<td style="width: 21.65%;"><input type="text" name="sellprod"/></td>
					<td style="width: 5%;"></td>
					<td style="width: 8%;"><input type="text" name="sellamount"/></td>
					<td style="width: 10%;"><input type="text" name="selloneprice"/></td>
					<td style="width: 30%;"><input type="text" name="selltotalprice"/></td>
					<td></td>
				</tr>
								
			</table>
			
			<table class="mytbl">
			
				<tr>
					<td style="width: 10%;">합계금액</td>
					<td style="width: 40%;"><input type="text" name="totalprice" class="form-control" readonly/></td>
					<td style="width: 10%;">세금계산서<br>발행</td>
					<td>
						<input type="radio" name="billtax_yn" id="yes" value="발행" checked/>&nbsp;<label for="yes" class="checkedPayment" style="width:20%">발행</label>
						&nbsp;<input type="radio" name="billtax_yn" id="no" value="미발행"/>&nbsp;<label for="no" style="width:20%">미발행</label>
					</td>
				</tr>
				
			</table>
					
		</form>
		
		<div class="my-2 text-center">	
			<button type="button" id="btn_submit" class="btn btn-primary" onclick="goSubmit();">저장</button>
			<button type="button" id="" class="btn btn-danger" onclick="location.reload(true);">초기화</button>
    	</div>
    	
    	<button type="button" id="btn_searchModal" data-toggle="modal" data-target="#searchModal" style="display: none;"></button>
    	
    	<%-- 찾기모달 --%>
      	<div class="modal fade" id="searchModal">
	  		
	  		<div class="modal-dialog modal-lg modal-dialog-scrollable modal modal-dialog-centered">
	  
	    		<div class="modal-content">			      
	      	
	      			<!-- Modal header -->
	      			<div class="modal-header">
	      				<h5 class="modal-title">거래처 목록</h5>
	      				<button type="button" class="close thisclose" data-dismiss="modal">&times;</button>	        				        			
	      			</div>
	      	
	     			<!-- Modal body -->
	      			<div class="modal-body text-center px-auto w-90 justify-content-center" id="searchBody">
	      																
					</div>
	     				      
	    		</div>
	    
	  		</div>
	  		
		</div>
    	
    </div>
    
  </div>
  

</div>
<!-- !!!!!PAGE CONTENT END!!!!!! -->