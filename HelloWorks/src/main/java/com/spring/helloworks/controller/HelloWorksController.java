package com.spring.helloworks.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.helloworks.service.InterHelloWorksService;


@Component
@Controller
public class HelloWorksController {
   
   @RequestMapping(value="/test1.hello2")
   public String test1(HttpServletRequest request) {
      
      String name = "이순신";
      
      request.setAttribute("name", name);
      
      return "test1";
      
      // 테스트 완료! 정상출력
      
   }
   

   
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
   
   
   // DB 연결 확인용 메소드
      @RequestMapping(value="/template.hello2")
      public String index2(HttpServletRequest request) {
         

         return "template/template.tiles1";
         
         
      }

   
}