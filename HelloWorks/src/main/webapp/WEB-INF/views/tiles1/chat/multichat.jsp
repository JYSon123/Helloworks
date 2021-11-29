<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/bootstrap-4.6.0-dist/css/bootstrap.min.css" >

<style type="text/css">
	a:hover { 
		text-decoration:none;
		cursor: pointer;		
	}
</style>

<script type="text/javascript">

	var weatherTimejugi = 0; // 단위는 밀리초
	
	$(document).ready(function(){
		
		var url = window.location.host; // 웹 브라우저의 주소창의 포트까지 가져옴
		// console.log("url : " + url);
		// url : 192.168.35.99:9090
		
		var pathname = window.location.pathname;	// 포트 번호 뒤 '/'부터 오른쪽에 있는 모든 경로 
		// console.log("pathname : " + pathname);
		// pathname : /board/chatting/multichat.hello2
		
		var appCtx = pathname.substring(0, pathname.lastIndexOf("/") );	// "전체 문자열".lastIndexOf("검사할 문자");
		// console.log("appCtx : " + appCtx);
		// appCtx : /board/chatting
		
		var root = url + appCtx
		// console.log("root : " + root);
		// root : 192.168.35.99:9090/board/chatting
		
		var wsUrl ="ws://"+root+"/multichatstart.hello2";
		// console.log("wsUrl : " + wsUrl);
		// wsUrl : ws://192.168.35.99:9090/board/chatting/multicahtstart.hello2
		// 웹소켓통신을 하기위해서는 http:// 을 사용하는 것이 아니라 ws:// 을 사용해야 한다.
		
		var websocket = new WebSocket(wsUrl);
	
		var messageObj = {};	// 자바스크립트 객체 생성함
		
		// 웹소켓에 최초로 연결이 되었을 경우에 toast 띄우기
		websocket.onopen = function(){
			
			$('.toast').toast('show');
		};
      
		// === 메세지 수신지 콜백함수 정의하기 === //
		websocket.onmessage = function(event){
								// 자음 ㄴ임
			// event.data는 「이순신 황정은」이다. 							
			if(event.data.substr(0,1)=="「" && event.data.substr(event.data.length-1)=="」") {
				$("div#connectingUserList").html(event.data.substr(1,event.data.length-2));
	       }
	       else {
	    	   
	    	   // event.data는 수신받은 채팅 문자이다.
	    	   $("div#chatMessage").append(event.data);
	    	   $("div#chatMessage").append("<br>");
	    	   $("div#chatMessage").scrollTop(99999999);
	       }		
		   
		};
		
		
		// == 메세지 입력 후 엔터하기 ===
		$("input#message").keydown(function(event){ 
			if(event.keyCode == 13) {
				$("button#btnSendMessage").click();
			}
		});
		
		// == 메세지 보내기 == //
		var isOnlyOneDialog = false; // 귀속말 여부. true 이면 귀속말, false 이면 모두에게 공개되는 말
		
		$("button#btnSendMessage").click(function(){
			
			if( $("input#message").val() != "" ) {
				
				// ==== 자바스크립트에서 replace를 replaceAll 처럼 사용하기 ====
                // 자바스크립트에서 replaceAll 은 없다.
                // 정규식을 이용하여 대상 문자열에서 모든 부분을 수정해 줄 수 있다.
                // 수정할 부분의 앞뒤에 슬래시를 하고 뒤에 gi 를 붙이면 replaceAll 과 같은 결과를 볼 수 있다.
                var messageVal = $("input#message").val();
                messageVal = messageVal.replace(/<script/gi, "&lt;script"); 
                // 스크립트 공격을 막기.
                
                <%-- 
                messageObj = { message: messageVal
					  ,type: "all"
					  ,to: "all" }; // 자바스크립트에서 객체의 데이터값 초기화
                --%>
					  
				// 또는	  
                messageObj = {};
                messageObj.message = messageVal;
                messageObj.type = "all";
                messageObj.to = "all";
                
                var to = $("input#to").val();
                if ( to != "" ) {
                    messageObj.type = "one";
                    messageObj.to = to;
                }
                
                websocket.send(JSON.stringify(messageObj));
    			// 위에서 자신이 보낸 메시지를 웹소켓을 보낸 다음에 자신이 보낸 메시지 내용을 웹페이지에 보여지도록 한다.
                
                var now = new Date();
                var ampm = "오전 ";
                var hours = now.getHours();
                
                if(hours > 12) {
                   hours = hours - 12;
                   ampm = "오후 ";
                }
                
                if(hours == 0) {
                   hours = 12;
                }
                if(hours == 12) {
                   ampm = "오후 ";
                }
                
                var minutes = now.getMinutes();
				if(minutes < 10) {
				   minutes = "0"+minutes;
				}
              
                var currentTime = ampm + hours + ":" + minutes;
                
                if(isOnlyOneDialog == false) {	// 귀속말이 아닌 경우
	                $("div#chatMessage").append("<div style='background-color: #ffff69; display: inline-block; max-width: 60%; float: right; padding: 7px; border-radius: 15px; word-break: break-all;'>" + messageVal + "</div> <div style='display: inline-block; float: right; padding: 20px 5px 0 0; font-size: 7pt;'>"+currentTime+"</div> <div style='clear: both;'>&nbsp;</div>");
                }
                else {	// 귓속말인 경우. 글자색을 빨간색으로 함
                	$("div#chatMessage").append("<i class='fas fa-lock' style='float: right;'></i><br><div style='background-color: #ffff69; display: inline-block; max-width: 60%; float: right; padding: 7px; border-radius: 15px; word-break: break-all; color:red;'>" + messageVal + "</div> <div style='display: inline-block; float: right; padding: 20px 5px 0 0; font-size: 7pt;'>"+currentTime+"</div> <div style='clear: both;'>&nbsp;</div>");
                }
                
                $("div#chatMessage").scrollTop(99999999);
                
                $("input#message").val("");
                $("input#message").focus();
                
			}
			
			
		});
		/////////////////////////////////////////////////////////////
		
		// 귀속말대화끊기 버튼은 처음에는 보이지 않도록 한다.
        $("button#btnAllDialog").hide();
		
     	// 아래는 귓속말을 위해서 대화를 나누는 상대방의 이름을 클릭하면 상대방IP주소를 귓속말대상IP주소에 입력하도록 하는 것.
        $(document).on("click",".loginuserName",function(){

        	var ip = $(this).prev().text();
            //   alert(ip);
                $("input#to").val(ip); 
                
                $("span#privateWho").text($(this).text());
                $("button#btnAllDialog").show();
                
                isOnlyOneDialog = true; // 귀속말 대화임을 지정.
         });
		
     	// 귀속말대화끊기 버튼을 클릭한 경우는 전체대상으로 채팅하겠다는 말이다. 
        $("button#btnAllDialog").click(function(){
              $("input#to").val("");
              $("span#privateWho").text("");
              $(this).hide();
              
              isOnlyOneDialog = false; // 귀속말 대화가 아닌 모두에게 공개되는 대화임을 지정.
        });
     	
     	////////////////////////////////////////////////////////////////
     	loopshowNowTime();
			
		// 시간이 대략 매 30분 0초가 되면 기상청 날씨정보를 자동 갱신해서 가져오려고 함.
		// (매 정시마다 변경되어지는 날씨정보를 정시에 보내주지 않고 대략 30분이 지난다음에 보내주므로)
		
		var now = new Date();
		var minute = now.getMinutes(); // 현재시각중 분을 읽어온다.
		
		if(minute < 30) { // 현재시각중 분이 0~29분 이라면
			weatherTimejugi = (30-minute)*60*1000; // 현재시각의 분이 0분이라면 weatherTimejugi에 30분을 넣어준다. 
			                                       // 현재시각의 분이 5분이라면 weatherTimejugi에 25분을 넣어준다. 
			                                       // 현재시각의 분이 29분이라면 weatherTimejugi에 1분을 넣어준다. 
		}
		
		else if(minute == 30) {
			weatherTimejugi = 1000; // 현재시각의 분이 30분이라면 weatherTimejugi에 1초 넣어준다.
		}
		
		else {  // 현재시각중 분이 31~59분 이라면
			
			weatherTimejugi = ( (60-minute)+30 )*60*1000; // 현재시각의 분이 31분이라면 weatherTimejugi에 (29+30)분을 넣어준다. 
			                                              // 현재시각의 분이 40분이라면 weatherTimejugi에 (20+30)분을 넣어준다. 
			                                              // 현재시각의 분이 59분이라면 weatherTimejugi에 (1+30)분을 넣어준다. 
		}
		
		startshowWeather(); // 기상청 날씨정보 공공API XML데이터 호출 및 매 1시간마다 주기적으로 기상청 날씨정보 공공API XML데이터 호출하기
				
	}); // end of $(document).ready()
	
	// Function Declaration
	function showNowTime() {
		
		var now = new Date();
	
		var month = now.getMonth() + 1;
		if(month < 10) {
			month = "0"+month;
		}
		
		var date = now.getDate();
		if(date < 10) {
			date = "0"+date;
		}
		
		var strNow = now.getFullYear() + "-" + month + "-" + date;
		
		var hour = "";
	    if(now.getHours() < 10) {
	    	 hour = "0"+now.getHours();
	    } 
	    else {
	    	hour = now.getHours();
	    }
		
	    
		var minute = "";
		if(now.getMinutes() < 10) {
			minute = "0"+now.getMinutes();
		} else {
			minute = now.getMinutes();
		}
		
		var second = "";
		if(now.getSeconds() < 10) {
			second = "0"+now.getSeconds();
		} else {
			second = now.getSeconds();
		}
		
		strNow += " "+hour + ":" + minute + ":" + second;
		
		$("span#clock").html(strNow);
	
	}// end of function showNowTime()


	function loopshowNowTime() {
		showNowTime();
		
		var timejugi = 1000;   // 시간을 1초 마다 자동 갱신하려고.
		
		setTimeout(function() {
						loopshowNowTime();	
					}, timejugi);
		
	}// end of loopshowNowTime()

	
	// ------ 기상청 날씨정보 공공API XML데이터 호출하기 -------- //
	function showWeather() {
		
		$.ajax({
			url:"<%= request.getContextPath()%>/opendata/weatherXML.hello2",
			type:"GET",
			dataType:"XML",
			success:function(xml) {
				var rootElement = $(xml).find(":root");
				
				var weather = $(rootElement).find("weather");
			    var updateTime = $(weather).attr("year")+"년 "+$(weather).attr("month")+"월 "+$(weather).attr("day")+"일 "+$(weather).attr("hour")+"시";             
				
			    var localArr = $(rootElement).find("local");
				
				var html = "<span style='font-weight:bold;'>"+updateTime+"</span>&nbsp;";
			        html += "<span style='color: gray; cursor:pointer; font-size:9pt;' onClick='javascript:showWeather();'><i class='fas fa-redo-alt'></i></span><br>";
			        html += "<div class='carousel-inner' style='margin-top: 10px;'>";
				
				for(var i=0; i<localArr.length; i++) {
					var local = $(localArr).eq(i);
					var icon = $(local).attr("icon");
					if(icon == "") {
						icon = "없음";
					}

					if(i==0) {
						html += "<div class='carousel-item active'>";
					}
					else {
						html += "<div class='carousel-item'>";
					}
					
// 					html += "<div style='background-color:#fff; width: 100%; padding: 2px 0;' >"+$(local).text()+"&nbsp;<img src='/helloworks/resources/images/weather/"+icon+".png' />&nbsp;"+ $(local).attr("desc") +"&nbsp;"+ $(local).attr("ta")+"℃</div>";
					html += "<div style='background-color:#fff; height: 60px; width: 100%; padding: 5px 0;' ><div style='background-color:#fff; width: 65%; margin: 0 auto;'><img src='/helloworks/resources/images/weather/"+icon+".png' style='margin-left: 10px; width: 25%; float: left;' /><small style='font-weight: bold; float: right; margin-right: 10px;'>&lt;"+$(local).text()+"&gt;</small><strong style='float: right; margin-right: 10px;'>"+ $(local).attr("desc") +"&nbsp;&nbsp;"+ $(local).attr("ta")+"℃</strong></div></div>"
					console.log(i+". "+icon +$(local).text()+$(local).attr("desc") + $(local).attr("ta"));
					
					html += "</div>";
				}// end of for--------------------------
				
				html += "<a class='carousel-control-prev' href='#displayWeather' role='button' data-slide='prev'>";
				html += "<span class='carousel-control-prev-icon' aria-hidden='true'></span>";
				html += "<span class='sr-only'>Previous</span>";
				html += "</a>";
				html += "<a class='carousel-control-next' href='#displayWeather' role='button' data-slide='next'>";
				html += "<span class='carousel-control-next-icon' aria-hidden='true'></span>";
				html += "<span class='sr-only'>Next</span>";
				html += "</a>";
				html += "</div>";
				
				$("div#displayWeather").html(html);
				
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
		
	}
	
	
	function startshowWeather() {
	      loopshowWeather();
	      
	      setTimeout(function(){
	         showWeather();
	      }, weatherTimejugi); // 현재시각의 분이 5분이라면 weatherTimejugi가 25분이므로 25분후인 30분에 showWeather();를 실행한다.
	  
	}// end of function startshowWeather()

	function loopshowWeather() {
		showWeather();

		setTimeout(function() {
			loopshowWeather();
		}, weatherTimejugi + (60 * 60 * 1000)); // 현재시각의 분이 5분이라면 weatherTimejugi가 25분이므로 25분후인 30분에 1시간을 더한후에 showWeather();를 실행한다.
	}// end of function loopshowWeather()
		
</script>

<div>
	<nav class="w3-sidebar w3-collapse" style="margin-top: 20px; z-index: 0; width: 300px; background-color: #f5f5f5; overflow: auto; height: 100%;" id="mySidebar">
		<br>
		<div class="w3-container" style="background-color: #f5f5f5; margin-top: 10px; ">
			<a href="#" onclick="w3_close()" class="w3-hide-large w3-right w3-jumbo w3-padding w3-hover-grey" title="close menu"><i class="fa fa-remove"></i></a> <br><br>
			<span style="font-size: 30pt; margin: 150px 0 30px 40px; color: gray;"><b><a style="color: gray;" href="<%= request.getContextPath() %>/chat/multichat.hello2">채팅</a></b></span>
		</div>
		<div class="w3-bar-block" style="background-color: #f5f5f5; min-height: 470px;"><br>
			
				
			<%-- 기상청 공공정보 API --%>
			<div id="displayWeather" style="text-align:center; width: 100%; margin-bottom: 40px;" class="carousel slide" data-ride="carousel"></div>
			
			<%-- 접속자 보여주기 --%>
 		    <input type="hidden" id="to" placeholder="귓속말대상IP주소"/>
			<div style="margin: 0 auto; padding: 3px 7px; width: 90%; background-color: #0070C1; color: #fff; font-size: 16pt; text-align: left;">&nbsp;접속자</div>
			<div id="connectingUserList" style="text-align: left; border: solid 1px #ccc; font-size: 12pt; font-weight: bold;  padding: 5px 10px; width: 90%; margin: 0 auto; max-height: 100px; overFlow: auto;"></div>
			
		           
		</div>
		
		<div style="vertical-align: bottom;  text-align: center; padding-bottom: 150px;">
			<input type="button" class="btn btn-outline-danger" style="display: inline-block; width: 90%;" onClick="javascript:location.href='<%=request.getContextPath() %>/index.hello2'" value="나가기" />
		</div>
	</nav>
	<%-- 사이드바 끝 --%>
</div>


<%-- 메인 컨텐츠 시작 --%>
<div class="w3-container w3-padding-large" style="margin: 30px 0 0 300px; min-height: 100%;">
	<div style="margin: 100px auto 70px auto; width: 96%;" class="row">
		<div class="col-md-10 offset-md-1">
		
			<%-- 채팅 들어왔을 때 알림 띄워주기 --%>
			<div role="alert" aria-live="assertive" aria-atomic="true" class="toast" data-autohide="false" style="position: absolute; top: 0; right: 0; margin-right: 10px;">
				<div class="toast-header">
					<img src="<%=request.getContextPath()%>/resources/images/favicon.ico" class="rounded mr-2" alt="..."> <strong class="mr-auto">채팅 알리미</strong>
					<small>방금</small>
					<button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="toast-body">WELCOME TO HELLOWORKS CHAT!!</div>
			</div>
			
			<%-- 1:1 채팅 --%>
			<i class='fas fa-lock'></i> 1:1 채팅 대상 : &nbsp;<span id="privateWho" style="font-weight: bold; top: 2;"></span>
			<button type="button" id="btnAllDialog" class="btn btn-secondary btn-sm" style="float: right;">1:1 채팅 끊기</button>
			 
			<%-- 채팅창 --%>
			<div id="chatMessage" style="width: 100%; min-height: 400px; max-height: 500px; overFlow: auto; margin: 20px 0;" data-delay="100000"></div>

			<%-- 메세지 보내기 --%>
			<div class="input-group">
				<input type="text" id="message" class="form-control" placeholder="메시지 내용" style="display: inline-block;" />
				<div class="input-group-append">
					<button type="button" id="btnSendMessage" class="btn btn-secondary" style="display: inline-block;">
						<i class="far fa-paper-plane"></i>
					</button>
				</div>
			</div>
			
		</div>

	</div>
</div>

