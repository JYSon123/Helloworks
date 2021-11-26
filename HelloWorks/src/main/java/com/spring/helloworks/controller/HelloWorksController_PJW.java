package com.spring.helloworks.controller;

import java.util.List;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.helloworks.service.InterHelloWorksService;


@Component
@Controller
public class HelloWorksController_PJW {
   
  
   

   
   ////////////////////////////////////////////////////////////////////////////////////////////
   
   @Autowired
   private InterHelloWorksService service;
   
   /*
   // DB 연결 확인용 메소드
   @RequestMapping(value="/index.hello2")
   public String index(HttpServletRequest request) {
      
      List<String> nameList = service.getName();
      
      request.setAttribute("nameList", nameList);
      
      return "main/index.tiles1";
      
      
   }
   */
   
   @RequestMapping(value="/index.hello2")
      public String index(HttpServletRequest request) {
         
         return "main/index.tiles1";
         // /WEB-INF/views/tiles1/main/index.jsp 파일을 생성한다.
      }
   
		
		/*
		 * @RequestMapping(value="/reservationlist.hello2") public String
		 * requiredLogin_reservation(HttpServletRequest request , HttpServletResponse
		 * response) {
		 * 
		 * return "reservation/reservationlist.tiles1"; }
		 */
		  //WEB-INF/views/tiles1/reservation/index.jsp 파일을 생성한다. 
		
   

   // DB 연결 확인용 메소드
      @RequestMapping(value="/template.hello2")
      public String index2(HttpServletRequest request) {
         

         return "template/template.tiles1";
         
         
      }

   
}