package com.spring.schedule.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.helloworks.model.EmpVO_KJH;
import com.spring.schedule.model.CalendarVO_HJE;
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
   public String calendar(HttpServletRequest request) {
	   
	   
	   
	   return "schedule/calendar.tiles1";
   }
      
   // 캘린더 추가
   @RequestMapping(value = "/addCalendar.hello2", method= {RequestMethod.POST})
   public ModelAndView addCalendar(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
	   
	   String calname = request.getParameter("calname");
	   String color = request.getParameter("color");
	   String shareEmp = request.getParameter("shareEmp");
	   
	   HttpSession session = request.getSession();
	   EmpVO_KJH loginEmp =  (EmpVO_KJH) session.getAttribute("loginEmp");
	   String empid = loginEmp.getEmpid();
	   
	   String fk_cno = "";
	   
	   if(shareEmp == null) {
		   shareEmp = empid;
		   fk_cno = "1";
	   } 
	   else {
		   shareEmp = empid + "," + shareEmp;
		   fk_cno = "2";
	   }
	   
	   // 일정을 등록한 직원(로그인한 직원)이 공유인원에 자동으로 추가되도록
	   
	   Map<String,String> paraMap = new HashMap<>();
	   paraMap.put("calname", calname);
	   paraMap.put("color", color);
	   paraMap.put("shareEmp", shareEmp);
	   paraMap.put("fk_cno", fk_cno);
	   
	   int n = service.addCalendar(paraMap);
	   
	   mav.setViewName("redirect:/schedule.hello2");
	   
	   return mav;
   }
   
   // 전체 캘린더 리스트 ajax로 받아오기
   @ResponseBody
   @RequestMapping(value = "/showCalendarList.hello2", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
   public String showCalendarList(HttpServletRequest request) {
	   
	   HttpSession session = request.getSession();
	   EmpVO_KJH loginEmp =  (EmpVO_KJH) session.getAttribute("loginEmp");
	   String empid = loginEmp.getEmpid();
	   
	   List<CalendarVO_HJE> personalList = service.showCalendarList(empid);
	   
	   JSONArray jsonArr = new JSONArray();
	   
	   if( personalList.size() > 0 ) {
		   
		   for( CalendarVO_HJE cvo : personalList ) {
			   
			   JSONObject jsonObj = new JSONObject();
			   
			   jsonObj.put("calname", cvo.getCalname());
			   jsonObj.put("color", cvo.getColor());
			   jsonObj.put("fk_cno", cvo.getFk_cno());
			   
			   jsonArr.put(jsonObj);
			   
		   }
		   
	   }
	   
	   return jsonArr.toString();
   }
   
   // 일정 Submit
   @RequestMapping(value="/addSchedule.hello2")
//   public ModelAndView addSchedule (ModelAndView mav, HttpServletRequest request) {
   public void addSchedule (HttpServletRequest request) {
	   
	   String calname = request.getParameter("calname");
	   String title = request.getParameter("title");
	   String startDay = request.getParameter("startDay");
	   String startTime = request.getParameter("startTime");
	   String endDay = request.getParameter("endDay");
	   String endTime = request.getParameter("endTime");
	   String allDay = request.getParameter("allDay");
	   String location = request.getParameter("location");
	   String notice = request.getParameter("notice");
	   String content = request.getParameter("content");
	   String mnoticeTime = request.getParameter("mnoticeTime");
	   String enoticeTime = request.getParameter("enoticeTime");
	   
	   System.out.println("calname : " + calname);
	   System.out.println("title : " + title);
	   System.out.println("startDay : " + startDay);
	   System.out.println("startTime : " + startTime);
	   System.out.println("endDay : " + endDay);
	   System.out.println("endTime : " + endTime);
	   System.out.println("allDay : " + allDay);
	   System.out.println("location : " + location);
	   System.out.println("content : " + content);
	   System.out.println("notice : " + notice);
	   System.out.println("mnoticeTime : " + mnoticeTime);
	   System.out.println("enoticeTime : " + enoticeTime);
	   
	   
	   
//	   return mav;
   }
   
   
   
   
   
}