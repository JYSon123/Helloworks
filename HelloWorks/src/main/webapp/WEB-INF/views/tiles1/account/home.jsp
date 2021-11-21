<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	
	$(document).ready(function() {
		
		var now = new Date(); // 현재날짜와 시각
		
		var year = now.getFullYear();
		var date = now.getDate();
		var month = "";
		var d_month = "";
		var curr_month = now.getMonth() + 1;
		var d_year = "";
				
		if(date > 10) {
			month = now.getMonth() + 1;
			
			if(month == 12) {
				d_month = 1;
				d_year = year + 1;
			}
			
			else {
				d_month = month + 1;
				d_year = year;
			}
		}
			
		else {
			month = now.getMonth() + 1;
			
			if(month == 12) {
				d_month = 1;
				d_year = year + 1;
			}
			
			else {
				d_month = month;
				d_year = year;
			}
		}
		
		var d_day = new Date(d_year, d_month - 1, 10);
		
		var n_day = new Date(year, curr_month - 1, date);
		
		var remain = ((d_day - n_day)/24/3600/1000);
		
		var str_month = d_month + "";
		
		if(str_month.length == 1)
			str_month = "0" + str_month;
		
		$("span#month").text(month);
		$("span#d_year").text(year);		
		$("span#d_month").text(str_month);		
		$("span#remain_date").text(remain);
		
		if(remain <= 3) {
			$("div.re-alert").removeClass("alert-warning");
			$("div.re-alert").addClass("alert-danger");
			
			$("i.fa-exclamation-circle").css("color","red");
		}
		
		else
			$("i.fa-exclamation-circle").css("color","#ff9900");
		
				
	})
	
</script>

<jsp:include page="template_KJH.jsp"/>

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 800px;">
    
    <div class="container" style="margin-top: 70px;">
    	
    	<div class="alert alert-warning alert-dismissible fade show re-alert">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<strong><i class="fas fa-exclamation-circle" style="font-size: 16pt;"></i></strong>
			&nbsp;<span id="month"></span>월 세금계산서 발급 마감일은 <span id="d_year"></span>-<span id="d_month"></span>-10 (<span id="remain_date"></span>일 전) 입니다
		</div>
		
		<div class="my-3">
						
    		<div style='width: 80%; padding: 0px auto;'>
        	
        	<img src='<%= request.getContextPath() %>/resources/images/maillogo.png' style='width: 100%; border: solid 2px #003399; border-radius: 20px 20px 0 0; margin-bottom: 0px;'>
        	
        	<div style='width: 100%; background-color: #e6eeff; border: solid 2px #003399; border-top: none; padding: 20px 0; border-radius: 0 0 20px 20px; margin-top: 0px;'>
        	<p style='width: 100%; text-align: center;'>와라라라라라라라</p>
        	
        	<p style='width: 100%; text-align: center; color: #002b80; font-size: 15pt;'><strong>helloworks</strong>와 함께해주셔서 감사합니다.<br>행복한 하루 되세요.</p>
        	</div>
        	    	
        	</div>
			
		</div>
		    	
    </div>
    
  </div>
  

</div>
<!-- !!!!!PAGE CONTENT END!!!!!! -->