<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/data.js"></script>
<script src="https://code.highcharts.com/modules/drilldown.js"></script>
<script src="https://code.highcharts.com/modules/wordcloud.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>

<style type="text/css">
	table {
		border-left: none;
		border-right: none;
	}
</style>
<script type="text/javascript">
	
	$(document).ready(function() {
		
		var now = new Date(); // 현재날짜와 시각
		
		var year = now.getFullYear();
		var date = now.getDate();
		var month = "";
		var d_month = "";
		var curr_month = now.getMonth() + 1;
		var d_year = "";
		
		$("span#showMonth").text("${showMonth}");		
		
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
		
		makingBar(year);
		makingLine(year);
		makingWord();
						
	});
	
	function makingBar(year) {
		
		$.ajax({url:"<%=request.getContextPath()%>/account/totalSalesOfMonth.hello2",
				dataType:"JSON",
				success:function(json1) {
					
					var monthArr = [];
					
					for(var i=1; i<13; i++) {
						
						$.each(json1, function(index, item) {
							
							if(i+"" == item.month) {
								monthArr.push({name: item.month+"월",
									   		   y: Number(item.sales),
									   		   drilldown: i});
							}
							
							else {
								monthArr.push({name: i+"월",
									   		   y: 0,
									           drilldown: i});
							}
															
						});
						
					}
					
					var customerArr = [];
					
					for(var i=1; i<13; i++) {
						
						var str_i = "";
						
						if(i<10)
							str_i = "0" + i;
						
						else
							str_i = "" + i;
						
						$.ajax({url:"<%=request.getContextPath()%>/account/monthOfCustomerCnt.hello2",
							data:{"month":str_i},
							dataType:"JSON",
							method:"POST",
							async: false,
							success:function(json2) {
								
								var subArr = []
								
								if(json2.length == 0) {
									subArr.push(["매출없음", 0]);
								}
								
								else {
									$.each(json2, function(index2, item2) {
										subArr.push([item2.customer, Number(item2.cnt)]);										
									});
								}
								
								customerArr.push({name: i,
									 		 	  id: i,
									 		 	  data: subArr});
								
							},
							error: function(request, status, error){
								alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
							}
							
						});
						
					}
					
					Highcharts.chart('bar_container', {
					    chart: {
					        type: 'column'
					    },
					    title: {
					        text: '월별 매출액, ' + year
					    },
					    accessibility: {
					        announceNewData: {
					            enabled: true
					        }
					    },
					    xAxis: {
					        type: 'category'
					    },
					    yAxis: {
					        title: {
					            text: 'Total sales of Month (￦)'
					        }

					    },
					    legend: {
					        enabled: false
					    },
					    plotOptions: {
					        series: {
					            borderWidth: 0,
					            dataLabels: {
					                enabled: true,
					                format: '{point.y}'
					            }
					        }
					    },

					    tooltip: {
					        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
					        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b><br/>'
					    },

					    series: [
					        {
					            name: "sales",
					            colorByPoint: true,
					            data: monthArr
					        }
					    ],
					    drilldown: {
					        series: customerArr
					    }
					});
					
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
		});
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	
	function makingLine(year) {
		
		$.ajax({url:"<%=request.getContextPath()%>/account/totalSalesOfYear.hello2",
			data:{"year":year},
			dataType:"JSON",
			success:function(json) {
				
				var yearArr = [];
				
				for(var i=year-4; i<=year; i++) {
					
					$.each(json, function(index, item) {
						
						if(i+"" == item.year) {
							yearArr.push(Number(item.sales));
						}
						
						else {
							yearArr.push(0);
						}
														
					});
					
				}
				
				Highcharts.chart('line_container', {
				    chart: {
				        type: 'line'
				    },
				    title: {
				        text: '연간 매출액'
				    },
				    xAxis: {
				        categories: [year-4, year-3, year-2, year-1, year]
				    },
				    yAxis: {
				        title: {
				            text: 'Total sales of Year (￦)'
				        }
				    },
				    plotOptions: {
				        line: {
				            dataLabels: {
				                enabled: true
				            },
				            enableMouseTracking: false
				        }
				    },
				    series: [{
				        name: '매출',
				        data: yearArr
				    }]
				});
				
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
			
		});	
			
	}
	
	////////////////////////////////////////////////////////////////////////
	
	function makingWord() {
		
		$.ajax({url:"<%=request.getContextPath()%>/account/wordcloudOfCustomer.hello2",
			dataType:"JSON",
			success:function(json) {
				
				if(json.length != 0) {
					
					const text = json.customer,
				    lines = text.split(/[,\. ]+/g),
				    data = lines.reduce((arr, word) => {
				        let obj = Highcharts.find(arr, obj => obj.name === word);
				        if (obj) {
				            obj.weight += 1;
				        } else {
				            obj = {
				                name: word,
				                weight: 1
				            };
				            arr.push(obj);
				        }
				        return arr;
				    }, []);

					Highcharts.chart('word_container', {
					    accessibility: {
					        screenReaderSection: {
					            beforeChartFormat: '<h5>{chartTitle}</h5>' +
					                '<div>{chartSubtitle}</div>' +
					                '<div>{chartLongdesc}</div>' +
					                '<div>{viewTableButton}</div>'
					        }
					    },
					    series: [{
					        type: 'wordcloud',
					        data,
					        name: 'Occurrences'
					    }],
					    title: {
					        text: ''
				    	}
					});
				}
				
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
			
		});
		
	}
	
	function nextMonth() {
		
		var now = new Date(); // 현재날짜와 시각
		
		var curr_year = now.getFullYear() + "";
		var curr_month = (now.getMonth() + 1) + "";
		var curr = curr_year + curr_month;
		
		var thisMonth = "${thisMonth}";
		
		var arrMonth = thisMonth.split("-");
		
		if(arrMonth[1] == "12") {
			arrMonth[0] = Number(arrMonth[0]) + 1;
			arrMonth[1] = "01";
		}
		
		else
			arrMonth[1] = Number(arrMonth[1]) + 1;
		
		if(Number(arrMonth[1]) < 10)
			arrMonth[1] = "0" + arrMonth[1];
			
		if(Number(curr) < Number(arrMonth.join("")))		
			return;
				
		else
			location.href = "<%=request.getContextPath()%>/account/home.hello2?thisMonth=" + arrMonth.join("-");
				
	}
	
	function beforeMonth() {

		var now = new Date(); // 현재날짜와 시각
		
		var curr_year = now.getFullYear();
		var curr_month = now.getMonth() + 1;
		var curr = curr_year + curr_month;
		
		var thisMonth = "${thisMonth}";
		
		var arrMonth = thisMonth.split("-");
		
		if(arrMonth[1] == "01") {
			arrMonth[0] = Number(arrMonth[0]) - 1;
			arrMonth[1] = "12";
		}
		
		else
			arrMonth[1] = Number(arrMonth[1]) - 1;
		
		if(Number(arrMonth[1]) < 10)
			arrMonth[1] = "0" + arrMonth[1];
		
		location.href = "<%=request.getContextPath()%>/account/home.hello2?thisMonth=" + arrMonth.join("-");
		
	}
	
</script>

<jsp:include page="template_KJH.jsp"/>

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 800px;">
    
    <div class="container" style="margin-top: 70px;">
    	
    	<div class="alert alert-warning alert-dismissible fade show re-alert">
			<button type="button" class="close" data-dismiss="alert" style="z-index: 1;">&times;</button>
			<strong><i class="fas fa-exclamation-circle" style="font-size: 16pt;"></i></strong>
			&nbsp;<span id="month"></span>월 세금계산서 발급 마감일은 <span id="d_year"></span>-<span id="d_month"></span>-10 (<span id="remain_date"></span>일 전) 입니다
		</div>
		
		<div class="my-3">
						
    		<div style="display: inline-block; float: left; width: 30%;" class="px-0">
    			<span style="float: none; display: none;"></span>
    			<div style="border: solid 1px #f2f2f2; border-radius: 5px; text-align: center; padding-top: 10px;">
    				<button type="button" class="btn btn-sm" onclick="beforeMonth();"><i class="fas fa-chevron-left" style="color:gray; font-size: 18pt;"></i></button>
    				<span class="mx-5" style="color:gray;"><span id="showMonth" style="font-size: 18pt;"></span>월</span>
    				<button type="button" class="btn btn-sm" onclick="nextMonth();"><i class="fas fa-chevron-right" style="color:gray; font-size: 18pt;"></i></button>
    				<input type="hidden"/>
    				<hr style="width: 100%;" class="mt-3">
    				<div style="min-height: 739px;">
    				<table style="width: 90%;" class="table table-bordered mx-auto">
    					<c:if test="${not empty billtaxEditList}">
	    					<c:forEach var="map" items="${billtaxEditList}">
	    						<c:if test="${map.edit eq 0}">
	    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">세금계산서</td><td>${map.cnt}&nbsp;건</td></tr>
	    						</c:if>
	    						<c:if test="${map.edit eq 1}">
	    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">수정세금계산서</td><td>${map.cnt}&nbsp;건</td></tr>
	    						</c:if>
	    					</c:forEach>
	    				</c:if>
	    				<c:if test="${empty billtaxEditList}">				
	    					<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">세금계산서</td><td>0&nbsp;건</td></tr>
	    					<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">수정세금계산서</td><td>0&nbsp;건</td></tr>
	    				</c:if>
	    				<c:if test="${not empty billnotaxEditList}">
	    					<c:forEach var="map" items="${billnotaxEditList}">
	    						<c:if test="${map.edit eq 0}">
	    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">계산서</td><td>${map.cnt}&nbsp;건</td></tr>
	    						</c:if>
	    						<c:if test="${map.edit eq 1}">
	    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">수정계산서</td><td>${map.cnt}&nbsp;건</td></tr>
	    						</c:if>
	    					</c:forEach>
    					</c:if>
    					<c:if test="${empty billnotaxEditList}">				
	    					<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">계산서</td><td>0&nbsp;건</td></tr>
	    					<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">수정계산서</td><td>0&nbsp;건</td></tr>
	    				</c:if>
    				</table>
    				<table style="width: 90%;" class="table table-bordered mx-auto">    					
    					<c:forEach var="map" items="${billtaxStatusList}" varStatus="status">
    						<c:if test="${status.index eq 0}">
    							<tr><td colspan="2" style="background-color: #adc2eb;">세금계산서 현황</td></tr>
    						</c:if>
    						<c:if test="${map.state eq 0}">
    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">승인요청전</td><td>${map.cnt}&nbsp;건</td></tr>
    						</c:if>
    						<c:if test="${map.state eq 1}">
    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">승인요청완료</td><td>${map.cnt}&nbsp;건</td></tr>
    						</c:if>
    						<c:if test="${map.state eq 2}">
    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">국세청전송완료</td><td>${map.cnt}&nbsp;건</td></tr>
    						</c:if>
    					</c:forEach>
    					
    					<c:forEach var="map" items="${billnotaxStatusList}" varStatus="status">
    						<c:if test="${status.index eq 0}">
    							<tr><td colspan="2" style="background-color: #adc2eb;">계산서 현황</td></tr>
    						</c:if>
    						<c:if test="${map.state eq 0}">
    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">승인요청전</td><td>${map.cnt}&nbsp;건</td></tr>
    						</c:if>
    						<c:if test="${map.state eq 1}">
    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">승인요청완료</td><td>${map.cnt}&nbsp;건</td></tr>
    						</c:if>
    						<c:if test="${map.state eq 2}">
    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">국세청전송완료</td><td>${map.cnt}&nbsp;건</td></tr>
    						</c:if>
    					</c:forEach>
    					
    					<c:forEach var="map" items="${transactionStatusList}" varStatus="status">
    						<c:if test="${status.index eq 0}">
    							<tr><td colspan="2" style="background-color: #adc2eb;">거래명세서 현황</td></tr>
    						</c:if>
    						<c:if test="${map.state eq 0}">
    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">승인요청전</td><td>${map.cnt}&nbsp;건</td></tr>
    						</c:if>
    						<c:if test="${map.state eq 1}">
    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">승인요청완료</td><td>${map.cnt}&nbsp;건</td></tr>
    						</c:if>
    						<c:if test="${map.state eq 2}">
    							<tr style="height: 40px;"><td style="text-align: left; width: 50%; padding-left: 10px; background-color: #ebf0fa;">국세청전송완료</td><td>${map.cnt}&nbsp;건</td></tr>
    						</c:if>
    					</c:forEach>   					
    				</table>
    				</div>
    			</div>
    		</div>
    		
    		<div style="display: inline-block; float: left; width: 70%;" class="pl-3">
    			<div id="bar_container" style="width: 100%; border: solid 1px #f2f2f2;"></div>
    		</div>
    		
    		<div style="display: inline-block; float: left; width: 35%;" class="pl-3">
    			<br>
    			<div id="line_container" style="width: 100%; border: solid 1px #f2f2f2;"></div>
    		</div>
    		
    		<div style="display: inline-block; float: left; width: 35%;" class="px-3">
    			<br>
    			<div id="word_container" style="width: 100%; border: solid 1px #f2f2f2;"></div>
    		</div>
    					
		</div>
		    	
    </div>
    
  </div>
  

</div>
<!-- !!!!!PAGE CONTENT END!!!!!! -->