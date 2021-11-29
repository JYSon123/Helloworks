package com.spring.chat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Component
@Controller
public class ChatController_HJE {

	// 채팅
	@RequestMapping(value = "/chat/multichat.hello2")
    public String requiredLogin_multichat(HttpServletRequest request, HttpServletResponse response) {
//	public String multichat(HttpServletRequest request, HttpServletResponse response) {
    	
    	return "chat/multichat.tiles1";
    }
	
	// 기상청 공공데이터를 활용한 날씨정보 보여주기
	@RequestMapping(value="/opendata/weatherXML.hello2", method = {RequestMethod.GET})
	public String weatherXML() {
		
		return "opendata/weatherXML";
	}
	
	
}
