package com.spring.addbook.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.addbook.model.AddbookVO;
import com.spring.addbook.service.InterAddbookService;

@Component
@Controller
public class AddbookController {
	
	@Autowired
	private InterAddbookService service;
	// Type에 따라 알아서 Bean 을 주입해준다.
	
	// 주소록 시작. // 주소록을 뜻하는 단어 addbook에서 영감을 얻었다.
	
	// 개인주소록
	 @RequestMapping(value="/addbookList_private.hello2" , method = RequestMethod.GET)
	   public ModelAndView requiredLogin_addbookList_private(HttpServletRequest request ,  HttpServletResponse response, ModelAndView mav) { 
		 		 
		 List<AddbookVO> addbookList_private = service.addbookList_private(); 
		 
		 mav.addObject("addbookList_private",addbookList_private);
		 
		 mav.setViewName("reservation/addbookList_private.tiles1");
		 
		 return mav;
	      //  /webapp/WEB-INF/views/tiles1/reservation/reservationlist.jsp 파일을 생성한다.
	   }
	 
	 // 공용주소록
	 @RequestMapping(value="/addbookList_public.hello2" , method = RequestMethod.GET)
	   public ModelAndView requiredLogin_addbookList_public(HttpServletRequest request ,  HttpServletResponse response, ModelAndView mav) { 
		 		 
		 List<AddbookVO> addbookList_public = service.addbookList_public(); 
		 
		 mav.addObject("addbookList_public",addbookList_public);
		 
		 mav.setViewName("reservation/addbookList_public.tiles1");
		 
		 return mav;
	      //  /webapp/WEB-INF/views/tiles1/reservation/reservationlist.jsp 파일을 생성한다.
	   }
	
}
