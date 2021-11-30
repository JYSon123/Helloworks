package com.spring.addbook.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.addbook.model.*;
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
	   public ModelAndView requiredLogin_addbookList_private(Map<String, String> paramap, AddbookVO addbookvo ,HttpServletRequest request ,  HttpServletResponse response, ModelAndView mav) { 
		 		 
		 String fk_tag = request.getParameter("fk_tag");
		 
		 if(fk_tag == null) {
			 fk_tag = "";
		 }
		 
		 paramap.put("fk_tag", fk_tag);
		 
		 List<AddbookVO> addbookList_private = service.addbookList_private(paramap);
		 List<Addbook_tagVO> addbooktag_private = service.addbooktag_private();
		 List<Addbook_tagVO> addbooktag_public = service.addbooktag_public();
		 
		 
		 mav.addObject("addbookList_private",addbookList_private);
		 mav.addObject("addbooktag_private", addbooktag_private);
		 mav.addObject("addbooktag_public", addbooktag_public);
		 mav.setViewName("reservation/addbookList_private.tiles1");
		 
		 return mav;
	      //  /webapp/WEB-INF/views/tiles1/reservation/reservationlist.jsp 파일을 생성한다.
	   }
	 
	 // 공용주소록
	 @RequestMapping(value="/addbookList_public.hello2" , method = RequestMethod.GET)
	   public ModelAndView requiredLogin_addbookList_public(Map<String, String> paramap ,HttpServletRequest request ,  HttpServletResponse response, ModelAndView mav) { 
		 
		 String fk_tag = request.getParameter("fk_tag");
		 
		 if(fk_tag == null) {
			 fk_tag = "";
		 }
		 
		 paramap.put("fk_tag", fk_tag);
		 
		 List<AddbookVO> addbookList_public = service.addbookList_public(paramap); 
		 List<Addbook_tagVO> addbooktag_private = service.addbooktag_private();
		 List<Addbook_tagVO> addbooktag_public = service.addbooktag_public();
		 
		 mav.addObject("addbookList_public",addbookList_public);
		 mav.addObject("addbooktag_private", addbooktag_private);
		 mav.addObject("addbooktag_public", addbooktag_public);
		 
		 mav.setViewName("reservation/addbookList_public.tiles1");
		 
		 return mav;
	      //  /webapp/WEB-INF/views/tiles1/reservation/reservationlist.jsp 파일을 생성한다.
	   }
	 
	 // 개인 주소록 추가
	 @RequestMapping(value="/addbook_privateadd.hello2" , method = RequestMethod.GET)
	   public ModelAndView requiredLogin_addbook_privateadd(Map<String, String> paramap ,HttpServletRequest request ,  HttpServletResponse response, ModelAndView mav) { 
		 
		 String fk_empid = request.getParameter("fk_empid");
		 String name = request.getParameter("name");
		 String email = request.getParameter("email");
		 String phonenum = request.getParameter("phonenum");
		 String fk_tag = request.getParameter("fk_tag");
		 String company = request.getParameter("company");
		 String department = request.getParameter("department");
		 String rank = request.getParameter("rank");
		 String address = request.getParameter("address");
		 String homepage = request.getParameter("homepage");
		 String birthday = request.getParameter("birthday");
		 String memo = request.getParameter("memo");
		 String pbl = request.getParameter("pbl");
		 
		 
		 mav.addObject("fk_empid", fk_empid);
		 mav.addObject("name", name);
		 mav.addObject("email", email);
		 mav.addObject("phonenum", phonenum);
		 mav.addObject("fk_tag",fk_tag);
		 mav.addObject("company",company);
		 mav.addObject("department", department);
		 mav.addObject("rank", rank);
		 mav.addObject("address", address);
		 mav.addObject("homepage", homepage);
		 mav.addObject("birthday", birthday);
		 mav.addObject("memo", memo);
		 mav.addObject("pbl", pbl);
		 
		 
		 
		 mav.setViewName("reservation/addbook_privateadd.tiles1");
		 
		 return mav;
	 }
	
}
