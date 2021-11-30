package com.spring.chat.websockethandler;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.spring.chat.service.InterChatService_HJE;
import com.spring.helloworks.model.EmpVO_KJH;

@Component
@Controller
public class WebsocketEchoHandler extends TextWebSocketHandler {
	
	@Autowired
	private InterChatService_HJE service;
	
	// === 웹소켓서버에 연결한 클라이언트 사용자들을 저장하는 리스트 === //
	private List<WebSocketSession> connectedUsers = new ArrayList<>(); 
	
	// init-method
	public void init() throws Exception { }
	
	
	@Override
    public void afterConnectionEstablished(WebSocketSession wsession) 
       throws Exception {
		
		// System.out.println("====> 웹채팅확인용 : " + wsession.getId() + "님이 접속했습니다.");
		// ====> 웹채팅확인용 : 5님이 접속했습니다.
		// ====> 웹채팅확인용 : 6님이 접속했습니다.
		
		// System.out.println("====> 웹채팅확인용 : " + "연결 컴퓨터명 : " + wsession.getRemoteAddress().getHostName());
		// ====> 웹채팅확인용 : 연결 컴퓨터명 : DESKTOP-UQOAH10
		// ====> 웹채팅확인용 : 연결 컴퓨터명 : 192.168.35.90
		
		// System.out.println("====> 웹채팅확인용 : " + "연결 컴퓨터명 : " + wsession.getRemoteAddress().getAddress().getHostName());
		// ====> 웹채팅확인용 : 연결 컴퓨터명 : DESKTOP-UQOAH10
		// ====> 웹채팅확인용 : 연결 컴퓨터명 : 192.168.35.90
		
		// System.out.println("====> 웹채팅확인용 : " + "연결 IP : " + wsession.getRemoteAddress().getAddress().getHostAddress()); 
		// ====> 웹채팅확인용 : 연결 IP : 192.168.35.99
		// ====> 웹채팅확인용 : 연결 IP : 192.168.35.90
		
		
		connectedUsers.add(wsession);
		
		/// 웹소켓 서버에 접속시 접속자명단을 알려주기 위한 것
        // Spring에서 WebSocket 사용시 먼저 HttpSession에 저장된 값들을 읽어와서 사용하기
		/*
			먼저 /webapp/WEB-INF/spring/config/websocketContext.xml 파일에서
			websocket:handlers 태그안에 websocket:handshake-interceptors에
            HttpSessionHandshakeInterceptor를 추가하면 WebSocketHandler 클래스를 사용하기 전에 
			먼저 HttpSession에 저장되어진 값들을 읽어 들여 WebSocketHandler 클래스에서 사용할 수 있도록 처리해준다. 
		 */
		
		String connectingUserName = "「";
		String enterMsg = "";
		for ( WebSocketSession webSocketSession : connectedUsers ) {
			
			Map<String, Object> map = webSocketSession.getAttributes();
			/*
	            webSocketSession.getAttributes(); 은 
	            HttpSession에 setAttribute("키",오브젝트); 되어 저장되어진 값들을 읽어오는 것으로써,
				리턴값은  "키",오브젝트로 이루어진 Map<String, Object> 으로 받아온다.
	        */
			
			EmpVO_KJH loginEmp = (EmpVO_KJH) map.get("loginEmp");
			// "loginEmp" 은 HttpSession에 저장된 키 값으로 로그인 되어진 사용자이다.
			
			
			// 접속한 직원 값 넣어두기
			connectingUserName += "<span class='ip' style='display: none;'>"+ wsession.getRemoteAddress().getAddress().getHostAddress() +"</span><span style='cursor:pointer;' class='loginuserName'>"+loginEmp.getEmpname() +"</span> "+ getRankingName(loginEmp) +" ("+ getDeptName(loginEmp)+ ")<br>" ;
			enterMsg = "<span style='font-weight:bold;'>" +loginEmp.getEmpname()+ "</span>&nbsp;<span>( "+ loginEmp.getEmpid() +" / "+ getDeptName(loginEmp)+" )</span>" + "님이 <span style='color: red;'>입장</span>했습니다.<br>";
		}
		connectingUserName += "」";
		
//		System.out.println("확인용 connectingUserName : " + connectingUserName);
		
		for(WebSocketSession webSocketSession : connectedUsers) {
				webSocketSession.sendMessage(new TextMessage(connectingUserName)); 
				webSocketSession.sendMessage(new TextMessage(enterMsg)); 
		}
		// 웹소켓 서버에 접속시 접속자명단을 알려주기 위한 것
		
	}
	
	
	// === 클라이언트가 웹소켓 서버로 메시지를 보냈을때의 Send 이벤트를 처리하기 ===
    /*
       handleTextMessage(WebSocketSession wsession, TextMessage message) 메소드는 
                 클라이언트가 웹소켓서버로 메시지를 전송했을 때 자동으로 호출되는(실행되는) 메소드이다.
                 첫번째 파라미터  WebSocketSession 은  메시지를 보낸 클라이언트임.
               두번째 파라미터  TextMessage 은  메시지의 내용임.
     */
	@Override
    protected void handleTextMessage(WebSocketSession wsession, TextMessage message) 
       throws Exception {
		
		// >>> 파라미터 WebSocketSession wsession은  웹소켓서버에 접속한 클라이언트임. <<<
        // >>> 파라미터 TextMessage message 은  클라이언트 사용자가 웹소켓서버로 보낸 웹소켓 메시지임. <<<
        
        // Spring에서 WebSocket 사용시 먼저 HttpSession에 저장된 값들을 읽어와서 사용하기
        /*
                            먼저 /webapp/WEB-INF/spring/config/websocketContext.xml 파일에서
             websocket:handlers 태그안에 websocket:handshake-interceptors에
             HttpSessionHandshakeInterceptor를 추가해주면 
             WebSocketHandler 클래스를 사용하기 전에, 
                            먼저 HttpSession에 저장되어진 값들을 읽어 들여, WebSocketHandler 클래스에서 사용할 수 있도록 처리해준다. 
        */ 
		
		Map<String, Object> map = wsession.getAttributes();
		/*
            webSocketSession.getAttributes(); 은 
            HttpSession에 setAttribute("키",오브젝트); 되어 저장되어진 값들을 읽어오는 것으로써,
			리턴값은  "키",오브젝트로 이루어진 Map<String, Object> 으로 받아온다.
        */
		
		EmpVO_KJH loginEmp = (EmpVO_KJH) map.get("loginEmp");
		// "loginEmp" 은 HttpSession에 저장된 키 값으로 로그인 되어진 사용자이다.
		
		// System.out.println("===> 웹채팅확인용 : 로그인 ID => " + loginEmp.getEmpid());
		// ===> 웹채팅확인용 : 로그인 ID => leess
		// ===> 웹채팅확인용 : 로그인 ID => hje0121

		MessageVO_HJE messageVO = MessageVO_HJE.converMessage(message.getPayload());
		
		/* 
		       파라미터 message 는  클라이언트 사용자가 웹소켓서버로 보낸 웹소켓 메시지임
		   message.getPayload() 은  클라이언트 사용자가 보낸 웹소켓 메시지를 String 타입으로 바꾸어주는 것이다.
		   /Board/src/main/webapp/WEB-INF/views/tiles1/chatting/multichat.jsp 파일에서 
		       클라이언트가 보내준 메시지는 JSON 형태를 뛴 문자열(String) 이므로 이 문자열을 Gson을 사용하여 MessageVO 형태의 객체로 변환시켜서 가져온다.
		*/
		
		// System.out.println("~~~ 확인용 messageVO.getMessage() => " + messageVO.getMessage());
		//~~~ 확인용 messageVO.getMessage() => 채팅방에 <span style='color: red;'>입장</span>했습니다.

		// System.out.println("~~~ 확인용 messageVO.getType() => " + messageVO.getType());
		// ~~~ 확인용 messageVO.getType() => all
		
		// System.out.println("~~~ 확인용 messageVO.getTo() => " + messageVO.getTo());
		// ~~~ 확인용 messageVO.getTo() => all
		
		String hostAddress = "";
		
		Date now = new Date();	// 현재시각
		String currentTime = String.format("%tp %tl:%tM",now,now,now); 
        // %tp              오전, 오후를 출력
        // %tl              시간을 1~12 으로 출력
		// %tM              분을 00~59 으로 출력
		
		for( WebSocketSession webSocketSession : connectedUsers ) {
			if("all".equals(messageVO.getType())) {
				// 채팅할 대상이 "전체"일 경우
				// 메세지를 자기자신을 뺀 나머지 모든 사용자들에게 메세지를 보냄
				
				if(wsession.getId() != webSocketSession.getId()) {
					// wsession 은 메시지를 보낸 클라이언트임.
                    // webSocketSession 은 웹소켓서버에 연결된 모든 클라이언트중 하나임.
                    // wsession.getId() 와  webSocketSession.getId() 는 자동증가되는 고유한 값으로 나옴
					
					
					webSocketSession.sendMessage(new TextMessage("<span class='ip' style='display: none;'>"+ wsession.getRemoteAddress().getAddress().getHostAddress() +"</span><span style='font-weight:bold; cursor:pointer;' class='loginuserName' >"+ loginEmp.getEmpname() +"</span> &nbsp; <span>( "+ loginEmp.getEmpid() +" / "+ getDeptName(loginEmp)+" )</span><br><div style='background-color: #2f86c4; color: white; display: inline-block; max-width: 60%; padding: 7px; border-radius: 15px; word-break: break-all;'>"+messageVO.getMessage() +"</div><div style='display: inline-block; padding: 20px 0 0 5px; font-size: 7pt;'>"+currentTime+"</div> <div>&nbsp;</div>" ));
//					webSocketSession.sendMessage(new TextMessage("<span style='display: none;'>"+ wsession.getRemoteAddress().getAddress().getHostAddress() +"</span><span style='font-weight:bold; cursor:pointer;' class='loginuserName' >"+ loginEmp.getEmpname() +"</span> &nbsp; <span>( "+ loginEmp.getEmpid() +"/"+ loginEmp.getFk_deptnum()+" )</span><br><div style='background-color: white; display: inline-block; max-width: 60%; padding: 7px; border-radius: 15px; word-break: break-all;'>"+messageVO.getMessage() +"</div><div style='display: inline-block; padding: 20px 0 0 5px; font-size: 7pt;'>"+currentTime+"</div> <div>&nbsp;</div>" ));
					/* word-break: break-all; 은 공백없이 영어로만 되어질 경우 해당구역을 빠져나가므로 이것을 막기위해서 사용한다. */
					// 파이널때는 IP대신 부서명과 직급출력
				}
				
			}
			
			else {
				// 채팅할 대상이 "전체"가 아닌 특정대상(지금은 귓속말대상 IP address 임) 일 경우
				
				hostAddress = webSocketSession.getRemoteAddress().getAddress().getHostAddress(); 
                // webSocketSession 은 웹소켓서버에 연결한 모든 클라이언트중 하나이며, 그 클라이언트의 IP address를 알아오는 것임.  
      
			     if (messageVO.getTo().equals(hostAddress)) { 
			         // messageVO.getTo() 는 클라이언트가 보내온 귓속말대상 IP address 임.
			          webSocketSession.sendMessage(
			                  new TextMessage("<i class='fas fa-lock' style='float: left;'></i>&nbsp;<span class='ip' style='display: none;'>"+ wsession.getRemoteAddress().getAddress().getHostAddress() +"</span><span style='font-weight:bold; cursor:pointer;' class='loginuserName'>" +loginEmp.getEmpname()+ "</span> &nbsp; <span>( "+ loginEmp.getEmpid() +" / "+ getDeptName(loginEmp)+" )</span><br><div style='background-color: #2f86c4; color: white; display: inline-block; max-width: 60%; padding: 7px; border-radius: 15px; word-break: break-all; color: red;'>" + messageVO.getMessage() +"</div> <div style='display: inline-block; padding: 20px 0 0 5px; font-size: 7pt;'>"+currentTime+"</div> <div>&nbsp;</div>" ));  
			                                                                                                                                                                                                                                                                                                                                            /* word-break: break-all; 은 공백없이 영어로만 되어질 경우 해당구역을 빠져나가므로 이것을 막기위해서 사용한다. */
			          break; // 지금의 특정대상(지금은 귓속말대상 IP address 임)은 1개이므로 
			                 // 특정대상(지금은 귓속말대상 IP address 임)에게만 메시지를 보내고  break;를 한다.
			     }
				
			}
			
		}// end of for
		
	}

	
	// === 클라이언트가 웹소켓서버와의 연결을 끊을때 작업 처리하기 ===
	/*
	       afterConnectionClosed(WebSocketSession session, CloseStatus status) 메소드는 
	                클라이언트가 연결을 끊었을 때 
	                즉, WebSocket 연결이 닫혔을 때(채팅페이지가 닫히거나 채팅페이지에서 다른 페이지로 이동되는 경우) 자동으로 호출되어지는(실행되어지는) 메소드이다.
	 */
	@Override
    public void afterConnectionClosed(WebSocketSession wsession, CloseStatus status) 
       throws Exception {
		
		// 파라미터 WebSocketSession wsession 은 연결을 끊은 웹소켓 클라이언트임.
        // 파라미터 CloseStatus 은 웹소켓 클라이언트의 연결 상태.
		
		Map<String, Object> map = wsession.getAttributes();
		EmpVO_KJH loginEmp = (EmpVO_KJH) map.get("loginEmp");
		
		connectedUsers.remove(wsession);
		// 웹소켓 서버에 연결되어진 클라이언트 목록에서 연결은 끊은 클라이언트는 삭제시킨다.
		
		for( WebSocketSession webSocketSession : connectedUsers ) {
			
			// 퇴장했다라는 메시지를 자기자신을 뺀 나머지 모든 사용자들에게 메시지를 보내도록 한다.
			if(wsession.getId() != webSocketSession.getId()) {
				// wsession 은 메시지를 보낸 클라이언트임.
                // webSocketSession 은 웹소켓서버에 연결된 모든 클라이언트중 하나임.
                // wsession.getId() 와  webSocketSession.getId() 는 자동증가되는 고유한 값으로 나옴

				webSocketSession.sendMessage(new TextMessage("<span style='font-weight:bold;'>" +loginEmp.getEmpname()+ "</span>&nbsp;<span>( "+ loginEmp.getEmpid() +" / "+ getDeptName(loginEmp)+" )</span>" + "님이 <span style='color: red;'>퇴장</span>했습니다.<br>"));
				/* word-break: break-all; 은 공백없이 영어로만 되어질 경우 해당구역을 빠져나가므로 이것을 막기위해서 사용한다. */
				// 파이널때는 IP대신 부서명과 직급출력
			}
				
		}// end of for
		
		
		///// ===== 접속을 끊을시 접속자명단을 알려주기 위한 것 시작 ===== /////
        String connectingUserName = "「";
        
        for (WebSocketSession webSocketSession : connectedUsers) {
            Map<String, Object> map2 = webSocketSession.getAttributes();
            EmpVO_KJH loginEmp2 = (EmpVO_KJH)map2.get("loginEmp");  
            // "loginEmp" 은 HttpSession에 저장된 키 값으로 로그인 되어진 사용자이다.
   
            connectingUserName += loginEmp2.getEmpname()+" "; 
        }// end of for------------------------------------------
        
        connectingUserName += "」";
        
        for (WebSocketSession webSocketSession : connectedUsers) {
            webSocketSession.sendMessage(new TextMessage(connectingUserName));
        }// end of for------------------------------------------
        ///// ===== 접속을 끊을시 접속자명단을 알려주기 위한 것  끝 ===== /////
		
		
	}
	
	///////////////////////////////////////////////////////////////////
	// 채팅에 접속한 직원의 부서명 구하기
	public String getDeptName(EmpVO_KJH loginEmp) {
	
		String empid = loginEmp.getEmpid();
		
		String deptname = service.getDeptName(empid);
		
		return deptname;
	}
	
	// 직원의 직급 구하기 
	public String getRankingName(EmpVO_KJH loginEmp) {
		
		String ranking = "";
		
		switch (loginEmp.getRanking()) {
		case 1:
			ranking = "사원";
			break;
		case 2:
			ranking = "대리";
			break;
		case 3:
			ranking = "부장";
			break;
		case 4:
			ranking = "팀장";
			break;
		case 5:
			ranking = "사장";
			break;
		}
		
		return ranking;
		
	}
	

}