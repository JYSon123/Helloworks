package com.spring.chat.websockethandler;

import com.google.gson.Gson;

public class MessageVO_HJE {
	
	private String message;
	private String type;   // all 이면 전체에게 채팅메시지를 보냄 
	private String to;     // 특정 클라이언트 IP Address
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	
	public static MessageVO_HJE converMessage(String source) {
		
		MessageVO_HJE messagevo = new MessageVO_HJE();
		
		Gson gson = new Gson();
		
		messagevo = gson.fromJson(source, MessageVO_HJE.class);
							   // source는 JSON 형태로 되어진 문자열
		// gson.fromJson(source, MessageVO.class) 은
		// JSON 형태로 되어진 문자열을 실제 MessageVO 객체로 변환해준다.
		
		return messagevo;
	}
	

}
