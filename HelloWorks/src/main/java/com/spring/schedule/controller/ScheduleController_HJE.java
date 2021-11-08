package com.spring.schedule.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.schedule.service.InterScheduleService_HJE;


@Component
@Controller
public class ScheduleController_HJE {
   
   
   @Autowired
   private InterScheduleService_HJE service;
   
 
   // DB 연결 확인용 메소드
   @RequestMapping(value="/test.hello2")
   public String hje(HttpServletRequest request) {
      
      List<String> nameList = service.getName();
      
      request.setAttribute("nameList", nameList);
      
      return "tiles1/schedule/test_HJE";
      
   }
   
   // 디자인처리 후 로그인 처리 만들기
   
   // 일정 페이지
   @RequestMapping(value="/schedule.hello2")
   public String schedule(HttpServletRequest request) {
	   
	  
	   
	   return "schedule/main.tiles1";
	   
   }
   
   
   
}