package com.spring.schedule.controller;

import java.util.*;

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
import com.spring.schedule.model.ScheduleVO_HJE;
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
   public String requiredLogin_calendar(HttpServletRequest request, HttpServletResponse response) {
	   
	   HttpSession session = request.getSession();
	   EmpVO_KJH loginEmp =  (EmpVO_KJH) session.getAttribute("loginEmp");
	   String empid = loginEmp.getEmpid();
	   
	   if( empid == null) {
		   
		   String message = "로그인 후 이용가능합니다.";
		   String loc = request.getContextPath() + "/login.hello2";
		   
	   }
	   
	   List<CalendarVO_HJE> calList = service.showCalendarList(empid);
	   request.setAttribute("calList", calList);
	   
	   List<Map<String,String>> schList = service.showSchedule(empid);
	   
	   request.setAttribute("schList", schList);
	   
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
	   
	   List<CalendarVO_HJE> calList = service.showCalendarList(empid);
	   
	   JSONArray jsonArr = new JSONArray();
	   
	   if( calList.size() > 0 ) {
		   
		   for( CalendarVO_HJE cvo : calList ) {
			   
			   JSONObject jsonObj = new JSONObject();
			   
			   jsonObj.put("calno", cvo.getCalno());
			   jsonObj.put("calname", cvo.getCalname());
			   jsonObj.put("color", cvo.getColor());
			   jsonObj.put("fk_cno", cvo.getFk_cno());
			   jsonObj.put("shareemp", cvo.getShareEmp());
			   
			   jsonArr.put(jsonObj);
			   
		   }
		   
	   }
	   
	   return jsonArr.toString();
   }
   
   // ajax를 사용한 일정 추가
   @RequestMapping(value="/addSchedule.hello2", method= {RequestMethod.POST})
   public String addSchedule(HttpServletRequest request) {
	   
	   HttpSession session = request.getSession();
	   EmpVO_KJH loginEmp =  (EmpVO_KJH) session.getAttribute("loginEmp");
	   String empid = loginEmp.getEmpid();
	   
	   String fk_calno = request.getParameter("fk_calno");
	   String title = request.getParameter("title");
	   String location = request.getParameter("location");
	   String content = request.getParameter("content");
	   
	   String startDay = request.getParameter("startDay");
	   String startTime = request.getParameter("startTime");

	   String endDay = request.getParameter("endDay");
	   String endTime = request.getParameter("endTime");
	   
	   // 시간 : 하루종일
	   String allDay = request.getParameter("allDay");
	   if("true".equals(allDay)) {
		   startTime = "00:00";
		   endTime = "24:00";
	   }
	   
	   // 시작일시(날짜+시간)
	   String startDate = startDay + "T" + startTime+":00";
	   
	   // 마감일시(날짜+시간)
	   String endDate = endDay + "T" + endTime+":00";
	   
	   // 알림관련
	   String notice = request.getParameter("notice");
	   String mnoticeTime = request.getParameter("mnoticeTime");
	   String enoticeTime = request.getParameter("enoticeTime");
	   
	   
	   Map<String,String> paraMap = new HashMap<>();
	   
	   paraMap.put("fk_calno", fk_calno);	   
	   paraMap.put("title", title);	   
	   paraMap.put("location", location);	   
	   paraMap.put("content", content);	   
	   paraMap.put("startDate", startDate);	   
	   paraMap.put("endDate", endDate);	   
	   paraMap.put("empid", empid);	   
	   
	   service.addSchedule(paraMap);
	   	   
	   return "redirect:/schedule.hello2";
   }
   
   // 개인 캘린더 수정 및 삭제
   @RequestMapping(value = "/changePersonal.hello2", method= {RequestMethod.POST})
   public String changePersonal(HttpServletRequest request, HttpServletResponse response) {
	   
	   String calno = request.getParameter("calno");
	   String calname = request.getParameter("calname");
	   String color = request.getParameter("color");
	   String changeOption = request.getParameter("changeOption");
	   
	   Map<String,String> paraMap = new HashMap<>();
	   
	   paraMap.put("calno", calno);
	   paraMap.put("calname", calname);
	   paraMap.put("color", color);
	   
	   if( "1".equals(changeOption) ) {	// 수정
		   
		   service.updatePersonal(paraMap);
	   }
	   if ( "2".equals(changeOption) ) { // 삭제
		   
		   service.deletePersonal(paraMap);
	   }

	   return "redirect:/schedule.hello2";
   }
   
   
   // 공유 캘린더 수정 및 삭제
   @RequestMapping(value = "/changeShare.hello2", method= {RequestMethod.POST})
   public String changeShare(HttpServletRequest request, HttpServletResponse response) {
	   
	   String calno = request.getParameter("calno");
	   String calname = request.getParameter("calname");
	   String color = request.getParameter("color");
	   String shareEmp = request.getParameter("shareEmp");
	   String changeOption = request.getParameter("changeOption");
	   	   
	   Map<String,String> paraMap = new HashMap<>();
	   
	   paraMap.put("calno", calno);
	   paraMap.put("calname", calname);
	   paraMap.put("color", color);
	   paraMap.put("shareEmp", shareEmp);
	   
	   if( "1".equals(changeOption) ) {	// 수정
		   
		   service.updateShare(paraMap);
	   }
	   if( "2".equals(changeOption) ) { // 삭제
		   
		   service.deleteShare(paraMap);
	   }
	   
	   return "redirect:/schedule.hello2";
   }
   
   
   
}