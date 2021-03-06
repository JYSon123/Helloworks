<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
   
<style type="text/css">
	body,h1,h2,h3,h4,h5,h6 {font-family: Verdana, sans-serif;}
	
	#billtitle:hover {cursor: pointer;}
</style>

<script>
// Script to open and close sidebar
function w3_open() {
    document.getElementById("mySidebar").style.display = "block";
    document.getElementById("myOverlay").style.display = "block";
}
 
function w3_close() {
    document.getElementById("mySidebar").style.display = "none";
    document.getElementById("myOverlay").style.display = "none";
}

	$(document).ready(function() {
		
		$("input#total").bind("keyup", function() {
			
			var totalVal = $(this).val();
			
			if(totalVal != "") {
				
				var price = Math.round(totalVal/1.1);
				
				$("input#price").val(price);
				
				$("input#tax").val(totalVal - price);
			
			}
			
			else {
				
				$("input#price").val("");
				
				$("input#tax").val("");
				
			}
			
		});
		
	})

</script>

<!-- 좌측 고정 상세메뉴 시작 -->
<nav class="w3-sidebar w3-collapse w3-white " style="margin-top:20px; z-index:0; width:300px; background-color:#f5f5f5; overflow:hidden;" id="mySidebar"><br>
  <div class="w3-container" style="background-color:#f5f5f5; margin-top:10px" >
    <a href="#" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu">
      <i class="fa fa-remove"></i>
    </a>
	<br><br>
    <span style="font-size:20pt; margin:100px 0 30px 40px ; color:gray" id="billtitle"><b onclick="location.href='<%=ctxPath %>/account/home.hello2'">전자세금계산서</b></span><%-- 메인 --%>
  </div>
  <div class="w3-bar-block" style="background-color:#f5f5f5; height: 100%">
	<div style="margin-left:42px; font-size: 13pt">
	  <br>
	  <a href="<%=ctxPath %>/account/writeBillTax.hello2" class="w3-bar-item w3-button">세금계산서 작성</a>
	  <a href="<%=ctxPath %>/account/writeBillNotax.hello2" class="w3-bar-item w3-button">계산서 작성</a>
	  <a href="<%=ctxPath %>/account/writeTransaction.hello2" class="w3-bar-item w3-button">거래명세서 작성</a>
	  <a href="<%=ctxPath %>/account/listBill.hello2" class="w3-bar-item w3-button">작성완료 문서</a> <%-- 작성문서의 수정, 삭제기능 / 승인요청(엑셀만들어서 메일로) / 국세청전송 --%>
	  <a href="<%=ctxPath %>/account/manageCustomer.hello2" class="w3-bar-item w3-button">거래처 등록/관리</a>	  
	  
	  <c:if test="${sessionScope.loginEmp.ranking >= 3}">
	  
	  	<a href="<%=ctxPath %>/account/editCompany.hello2" class="w3-bar-item w3-button">사업자 정보 관리</a>
	  
	  </c:if>
	  
	  <%-- 부가세 계산기 --%>	  
	  <div style="background-color: #456bd3; margin-right: 10%; height: 230px; border-radius: 20px;" class="w-80 my-3 text-center py-3">
	  	
	  	<h5 class="text-center" style="color: white;"><i class="fas fa-calculator"></i>&nbsp;부가세 계산기</h5>
	  	<hr>
	  	
	  	<div class="mx-3">
			<form>
				<div class="input-group mb-3 input-group-sm">
					<div class="input-group-prepend">
						<span class="input-group-text">합계금액</span>
					</div>
					<input type="number" class="form-control" id="total" autocomplete="off">
				</div>
				
				<div class="input-group mb-3 input-group-sm">
					<div class="input-group-prepend">
						<span class="input-group-text">공급가액</span>
					</div>
					<input type="number" class="form-control" id="price" readonly placeholder="합계금액/1.1">
				</div>
				
				<div class="input-group mb-3 input-group-sm">
					<div class="input-group-prepend">
						<span class="input-group-text">부가세액</span>
					</div>
					<input type="number" class="form-control" id="tax" readonly>
				</div>
			</form>
	  	</div>
	  	
	  </div>
	  
	  <br><br>
	  
  	</div>
  </div>  
</nav>
<!-- 좌측 고정 상세메뉴 끝 -->


