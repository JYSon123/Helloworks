package com.spring.schedule.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.helloworks.common.MyUtil_HJE;
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
   public String requiredLogin_calendar(HttpServletRequest request, HttpServletResponse response) {

	   getCurrentURL(request);
	   
	   Map<String,String> paraMap = new HashMap<>();
	   
	   HttpSession session = request.getSession();
	   EmpVO_KJH loginEmp =  (EmpVO_KJH) session.getAttribute("loginEmp");
	   
	   if (loginEmp == null) {

			String message = "로그인 후 이용가능합니다.";
			String loc = request.getContextPath() + "/login.hello2";

			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			return "tiles1/schedule/msg";
		}
	   else {
		   String empid = loginEmp.getEmpid();
		   paraMap.put("empid", empid);
		   
		   // 캘린더 리스트
		   List<CalendarVO_HJE> calList = service.showCalendarList(empid);
		   request.setAttribute("calList", calList);
		   
		   
		   String searchType = request.getParameter("searchType");
		   paraMap.put("searchType", searchType);
		   String startDate = "";
		   String endDate = "";
		   String searchWord = "";
		   
		   if( "term".equals(searchType) ) {
			   startDate = request.getParameter("startDate");
			   endDate = request.getParameter("endDate");
		   }
		   else {
			   searchWord = request.getParameter("searchWord");
		   }
		   paraMap.put("startDate", startDate);
		   paraMap.put("endDate", endDate);
		   paraMap.put("searchWord", searchWord);
		   
		   request.setAttribute("paraMap", paraMap);
		   ///////////////////////////////////////////////////////////////////
		   // 검색이 없을 때 (전체 일정 출력)
		   if (searchType == null ) {
			   List<Map<String,String>> schList = service.showSchedule(empid);
			   request.setAttribute("schList", schList);
		   }
		   //////////////////////////////////////////////////////////////////
		   // 페이징처리
		   else {
			    
			    // fullCalendar 출력, 엑셀다운을 위한 페이징처리하지 않은 검색결과
			    List<Map<String,String>> searchSchList = service.searchSchedule(paraMap); 
			    request.setAttribute("searchSchList", searchSchList);
			   
			    String str_currentShowPageNo = request.getParameter("currentShowPageNo");
			   
				// 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
				// 총 게시물 건수(totalCount)는 검색조건이 있을 때와 없을 때로 나뉘어진다.
				int totalCount = 0;			// 총 게시물 건수
				int sizePerPage = 5;		// 한 페이징 당 보여줄 게시물 건수
				int currentShowPageNo = 0; 	// 현재 보여주는 페이지 번호로서, 초기치로는 1페이지
				int totalPage = 0;			// 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)
				
				int startRno = 0;			// 시작 행번호
				int endRno = 0;				// 끝 행번호
				
				// 총 게시물건수 구해오기(totalCount)
				totalCount = service.getTotalCount(paraMap);
				
				// 만약에 총 페이지 게시물 건수(totalCount)가 127개 이라면
				// 총 페이지수 (totalPage)는 13개가 되어야 한다.
				totalPage = (int) Math.ceil((double) totalCount/sizePerPage);	// (double)127/10 ==> 12.7 ==> Math.ceil(12.7) ==> 13.0 ==> (int)13.0	==> 13
				
				if(str_currentShowPageNo == null) {
					// 게시판에 보여주는 초기화면
					currentShowPageNo = 1;
				}
				else {
					try {
						currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
						
						if(currentShowPageNo <1 || currentShowPageNo > totalPage) {
							currentShowPageNo = 1;
						}
					} catch (NumberFormatException e) {
						currentShowPageNo = 1;
					}
				}
						
				startRno = ((currentShowPageNo - 1)*sizePerPage) +1;
				endRno = startRno + sizePerPage - 1;
				
				paraMap.put("startRno", String.valueOf(startRno) );
				paraMap.put("endRno", String.valueOf(endRno));
							
				///////////////////////////////////////////////////////////////////
				// 검색이 있을 때 (검색 내용 출력)
				if (searchType != null) {
					List<Map<String,String>> pagingSchList = service.searchPagingSchedule(paraMap); 
					request.setAttribute("pagingSchList", pagingSchList);
				
				}
				//////////////////////////////////////////////////////////////////
				
				int blockSize = 5;
				// blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 개수 이다.
				
				int loop = 1;
			    //  loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수[ 지금은 10개(== blockSize) ] 까지만 증가하는 용도이다.
			    
				int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
							
				String pageBar = "<ul style='list-style: none;' class='pagination justify-content-center'>";
				String url = "schedule.hello2";
				// /schedule.hello2?searchType=title&searchWord=안녕&startDate=&endDate=
				
				// === [맨처음][이전] 만들기  === //
				if(pageNo != 1) {
					pageBar += "<li class='page-item' style='display:inline-block; '><a class='page-link' href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&startDate="+startDate+"&endDate="+endDate+"&currentShowPageNo=1'>&laquo;&laquo;</a></li>";
					pageBar += "<li class='page-item' style='display:inline-block;'><a class='page-link' href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&startDate="+startDate+"&endDate="+endDate+"&currentShowPageNo="+(pageNo-1)+"'>&laquo;</a></li>";
				}
				
				while( !(loop > blockSize || pageNo > totalPage) ) {
					// loop는 10번(blockSize) 반복이므로 loop가 10보다 크면 빠져나간다.
					// pageNo가 총페이지 수보다 크면 빠져나간다.
					
					if(pageNo == currentShowPageNo) {
						// 내가 클릭한 페이지(currentShowPageNo)가 현재 있는 pageNo와 같다면 페이지 이동이 필요 없음
			            pageBar += "<li class='page-item active' style='display:inline-block; '><a class='page-link' href='#'>"+pageNo+"</a></li>";
			         }
	
					else {
						pageBar += "<li class='page-item' style='display:inline-block;'><a class='page-link' href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&startDate="+startDate+"&endDate="+endDate+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
					}
					loop ++;
					pageNo ++ ;
				} // end of while
				
				// === [다음][마지막] 만들기 ===
				if (pageNo <= totalPage) {
					pageBar += "<li class='page-item'><a class='page-link' href='"+url+"?searchType="+ searchType + "&searchWord=" + searchWord +"&startDate="+startDate+"&endDate="+endDate+ "&currentShowPageNo=" + pageNo + "'>&raquo;</a></li>";
					pageBar += "<li class='page-item'><a class='page-link' href='"+url+"?searchType="+ searchType + "&searchWord=" + searchWord +"&startDate="+startDate+"&endDate="+endDate+ "&currentShowPageNo=" + totalPage + "'>&raquo;&raquo;</a></li>";
				}
	
				pageBar += "</ul>";
				
				request.setAttribute("pageBar", pageBar);
		   }
		   //////////////////////////////////////////////////////////////////
		   
		   return "schedule/calendar.tiles1";
	   }
   }
      
   // 캘린더 추가
   @RequestMapping(value = "/addCalendar.hello2", method= {RequestMethod.POST})
   public ModelAndView requiredLogin_addCalendar(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	   
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
		   shareEmp = empid + shareEmp;
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
   public String requiredLogin_showCalendarList(HttpServletRequest request, HttpServletResponse response) {
	   
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
   public String requiredLogin_addSchedule(HttpServletRequest request, HttpServletResponse response) {
	   
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
		   endTime = "23:59";
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
	   paraMap.put("notice", notice);	   
	   paraMap.put("empid", empid);	
	   
	   service.addSchedule(paraMap);
	   	   
	   return "redirect:/schedule.hello2";
   }
   
   // 개인 캘린더 수정 및 삭제
   @RequestMapping(value = "/changePersonal.hello2", method= {RequestMethod.POST})
   public String requiredLogin_changePersonal(HttpServletRequest request, HttpServletResponse response) {
	   
	   HttpSession session = request.getSession();
	   String goBackURL = (String) session.getAttribute("goBackURL");
	   
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

//	   return "redirect:/schedule.hello2";
	   return "redirect:"+goBackURL;
   }
   
   
   // 공유 캘린더 수정 및 삭제
   @RequestMapping(value = "/changeShare.hello2", method= {RequestMethod.POST})
   public String requiredLogin_changeShare(HttpServletRequest request, HttpServletResponse response) {
	   
	   HttpSession session = request.getSession();
	   EmpVO_KJH loginEmp = (EmpVO_KJH) session.getAttribute("loginEmp");
	   
	   String goBackURL = (String) session.getAttribute("goBackURL");
	   
	   if( loginEmp.getRanking() <= 2) {
			// 대리(2)와 사원(1)은 공유캘린더 삭제 불가능
		   
			String message = "일정 수정 및 삭제 권한이 존재하지 않습니다.";
			String loc = request.getContextPath()+goBackURL;
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			return "tiles1/schedule/msg";
	   }
	   
	   else {
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
		   
		   return "redirect:"+goBackURL;
	   }
   }
   
   // 자동완성
   @ResponseBody
   @RequestMapping(value = "/autoSearchWord.hello2", method = {RequestMethod.GET}, produces = "text/plain;charset=UTF-8")
	public String autoSearchWord(HttpServletRequest request) {
   	
   	String searchType = request.getParameter("searchType");
   	String searchWord = request.getParameter("searchWord");
   	String empid = request.getParameter("empid");
   	
   	Map<String,String> paraMap = new HashMap<>();
   	paraMap.put("searchType", searchType);
   	paraMap.put("searchWord", searchWord);
   	paraMap.put("empid", empid);
   	
   	List<String> wordList = service.autoSearchWord(paraMap);
   	
   	JSONArray jsonArr = new JSONArray();	
   	
   	if (wordList != null) {
   		
   		for( String word : wordList ) {
   			JSONObject jsonObj = new JSONObject();
   			jsonObj.put("word", word);
   			
   			jsonArr.put(jsonObj);
   		}
   	}
   	
   	return jsonArr.toString();
   }
	   
	   
   
   // 일정 수정 및 삭제
   @RequestMapping(value = "/changeSchedule.hello2", method= {RequestMethod.POST})
   public String requiredLogin_changeSchedule(HttpServletRequest request, HttpServletResponse response) {
	   
	   HttpSession session = request.getSession();
	   EmpVO_KJH loginEmp = (EmpVO_KJH) session.getAttribute("loginEmp");
	   
	   String goBackURL = (String) session.getAttribute("goBackURL");
	   
	   if( "2".equals(request.getParameter("fk_cno")) && loginEmp.getRanking() <= 2) {
			// 공유캘린더의 경우 대리(2)와 사원(1)은 일정변경 불가능
		   
			String message = "일정 수정 및 삭제 권한이 존재하지 않습니다.";
			String loc = request.getContextPath()+goBackURL;
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			return "tiles1/schedule/msg";
	   }
	   
	   else {
		   // 개인캘린더이거나 직급이 부장 이상인 경우
		   
		   String sno = request.getParameter("sno");
		   String title = request.getParameter("title");
		   String location = request.getParameter("location");
		   String content = request.getParameter("content");
		   String status = request.getParameter("status");
		   
		   String startDay = request.getParameter("startDay");
		   String startTime = request.getParameter("startTime");
		   
		   String endDay = request.getParameter("endDay");
		   String endTime = request.getParameter("endTime");
		   
		   // 시간 : 하루종일
		   String allDay = request.getParameter("allDay");
		   if("true".equals(allDay)) {
			   startTime = "00:00";
			   endTime = "23:59";
		   }
		   
		   // 시작일시(날짜+시간)
		   String startDate = startDay + "T" + startTime+":00";
		   
		   // 마감일시(날짜+시간)
		   String endDate = endDay + "T" + endTime+":00";
		   
		   // 알림관련
		   String notice = request.getParameter("notice");
		   
		   String changeOption = request.getParameter("changeOption");
		   
		   Map<String,String> paraMap = new HashMap<>();
		   
		   paraMap.put("sno", sno);	   
		   paraMap.put("title", title);	   
		   paraMap.put("location", location);	   
		   paraMap.put("content", content);	   
		   paraMap.put("status", status);	
		   paraMap.put("startDate", startDate);	   
		   paraMap.put("endDate", endDate);	
		   paraMap.put("notice", notice);	
		   
		   
		   if( "1".equals(changeOption) ) {	// 수정
			   
			   service.updateSchedule(paraMap);
		   }
		   if( "2".equals(changeOption) ) { // 삭제
			   
			   service.deleteSchedule(paraMap);
		   }
		   
		   return "redirect:"+goBackURL;
	   }
	   
    }

   
	// 검색결과 엑셀파일로 다운
	@RequestMapping(value = "/excel/downloadExcelFile.hello2", method = { RequestMethod.POST })
	public String downloadExcelFile(HttpServletRequest request, Model model) {

		Map<String, String> paraMap = new HashMap<>();

		HttpSession session = request.getSession();
		EmpVO_KJH loginEmp = (EmpVO_KJH) session.getAttribute("loginEmp");

		if (loginEmp == null) {

			String message = "로그인 후 이용가능합니다.";
			String loc = request.getContextPath() + "/login.hello2";

			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			return "tiles1/schedule/msg";
		} 
		else {
			String empid = loginEmp.getEmpid();
			paraMap.put("empid", empid);
			String empname = loginEmp.getEmpname();

			String searchType = request.getParameter("searchType");
			paraMap.put("searchType", searchType);

			if ("term".equals(searchType)) {
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				paraMap.put("startDate", startDate);
				paraMap.put("endDate", endDate);
			} 
			else {

				String searchWord = request.getParameter("searchWord");
				paraMap.put("searchWord", searchWord);
			}

			request.setAttribute("paraMap", paraMap);
			

//			int totalCount = service.getTotalCount(paraMap);
//			paraMap.put("startRno", "1" );
//			paraMap.put("endRno", String.valueOf(totalCount));
			
			List<Map<String, String>> searchSchList = service.searchSchedule(paraMap);
		
			//////////////////////////////////////////////////////////////////
			
			// 시트를 생성하고, 행을 생성하고, 셀을 생성하고, 셀안에 내용을 넣어주면 된다.
			SXSSFWorkbook workbook = new SXSSFWorkbook();
	
			// 시트생성
			SXSSFSheet sheet = workbook.createSheet("일정검색결과");
	
			// 시트 열 너비 설정
			sheet.setColumnWidth(0, 6000);	// 일정명
			sheet.setColumnWidth(1, 6000);	// 시작일
			sheet.setColumnWidth(2, 6000);	// 종료일
			sheet.setColumnWidth(3, 7000);	// 내용
			sheet.setColumnWidth(4, 5000);	// 장소
			sheet.setColumnWidth(5, 3500);	// 진행상황
			sheet.setColumnWidth(6, 6000);	// 캘린더 명
		    sheet.setColumnWidth(7, 7000);	// 공유인원
	
			// 행의 위치를 나타내는 변수
			int rowLocation = 0;
	
			////////////////////////////////////////////////////////////////////////////////////////
			// CellStyle 정렬하기(Alignment)
			// CellStyle 객체를 생성하여 Alignment 세팅하는 메소드를 호출해서 인자값을 넣어준다.
			// 아래는 HorizontalAlignment(가로)와 VerticalAlignment(세로)를 모두 가운데 정렬 시켰다.
			CellStyle mergeRowStyle = workbook.createCellStyle();
			mergeRowStyle.setAlignment(HorizontalAlignment.CENTER);
			mergeRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// import org.apache.poi.ss.usermodel.VerticalAlignment 으로 해야함.
	
			CellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setAlignment(HorizontalAlignment.CENTER);
			headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	
			// CellStyle 배경색(ForegroundColor)만들기
			// setFillForegroundColor 메소드에 IndexedColors Enum인자를 사용한다.
			// setFillPattern은 해당 색을 어떤 패턴으로 입힐지를 정한다.
			mergeRowStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex()); 
																						
			mergeRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	
			headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex()); 
																						
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	
			// Cell 폰트(Font) 설정하기
			// 폰트 적용을 위해 POI 라이브러리의 Font 객체를 생성해준다.
			// 해당 객체의 세터를 사용해 폰트를 설정해준다. 대표적으로 글씨체, 크기, 색상, 굵기만 설정한다.
			// 이후 CellStyle의 setFont 메소드를 사용해 인자로 폰트를 넣어준다.
			Font mergeRowFont = workbook.createFont(); // import org.apache.poi.ss.usermodel.Font; 으로 한다.
			mergeRowFont.setFontName("나눔고딕");
			mergeRowFont.setFontHeight((short) 500);
			mergeRowFont.setColor(IndexedColors.WHITE.getIndex());
			mergeRowFont.setBold(true);
	
			mergeRowStyle.setFont(mergeRowFont);
	
			// CellStyle 테두리 Border
			// 테두리는 각 셀마다 상하좌우 모두 설정해준다.
			// setBorderTop, Bottom, Left, Right 메소드와 인자로 POI라이브러리의 BorderStyle 인자를 넣어서
			// 적용한다.
			headerStyle.setBorderTop(BorderStyle.THICK);
			headerStyle.setBorderBottom(BorderStyle.THICK);
			headerStyle.setBorderLeft(BorderStyle.THIN);
			headerStyle.setBorderRight(BorderStyle.THIN);
	
			// Cell Merge 셀 병합시키기
			/*
			 * 셀병합은 시트의 addMergeRegion 메소드에 CellRangeAddress 객체를 인자로 하여 병합시킨다.
			 * CellRangeAddress 생성자의 인자로(시작 행, 끝 행, 시작 열, 끝 열) 순서대로 넣어서 병합시킬 범위를 정한다. 배열처럼
			 * 시작은 0부터이다.
			 */
			// 병합할 행 만들기
			Row mergeRow = sheet.createRow(rowLocation); // 엑셀에서 행의 시작은 0부터 시작한다.
	
			// 병합할 행에 "우리회사 사원정보" 로 셀을 만들어 셀에 스타일을 주기
			for (int i = 0; i < 8; i++) {
	
				Cell cell = mergeRow.createCell(i);
				cell.setCellStyle(mergeRowStyle);
				cell.setCellValue(empname+"님의 일정 검색 결과");
			}
	
			// 셀 병합하기
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0, 7));
	
			// CellStyle 천단위 쉼표, 금액
			CellStyle moneyStyle = workbook.createCellStyle();
			moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			////////////////////////////////////////////////////////////////////////////////////////////////
	
			// 헤더 행 생성
			Row headerRow = sheet.createRow(++rowLocation); // 엑셀에서 행의 시작은 0 부터 시작한다.
			// ++rowLocation는 전위연산자임.
	
			// 해당 행의 첫번째 열 셀 생성
			Cell headerCell = headerRow.createCell(0); // 엑셀에서 열의 시작은 0부터 시작한다.
			headerCell.setCellValue("일정명");
			headerCell.setCellStyle(headerStyle);
	
			// 해당 행의 두번째 열 셀 생성
			headerCell = headerRow.createCell(1);
			headerCell.setCellValue("시작일");
			headerCell.setCellStyle(headerStyle);
	
			// 해당 행의 세번째 열 셀 생성
			headerCell = headerRow.createCell(2);
			headerCell.setCellValue("종료일");
			headerCell.setCellStyle(headerStyle);
	
			// 해당 행의 네번째 열 셀 생성
			headerCell = headerRow.createCell(3);
			headerCell.setCellValue("내용");
			headerCell.setCellStyle(headerStyle);
	
			// 해당 행의 다섯번째 열 셀 생성
			headerCell = headerRow.createCell(4);
			headerCell.setCellValue("장소");
			headerCell.setCellStyle(headerStyle);
	
			// 해당 행의 여섯째 열 셀 생성
			headerCell = headerRow.createCell(5);
			headerCell.setCellValue("진행상황(%)");
			headerCell.setCellStyle(headerStyle);
	
			// 해당 행의 일곱번째 열 셀 생성
			headerCell = headerRow.createCell(6);
			headerCell.setCellValue("캘린더명");
			headerCell.setCellStyle(headerStyle);
	
			// 해당 행의 여덟번째 열 셀 생성
			headerCell = headerRow.createCell(7);
			headerCell.setCellValue("공유인원");
			headerCell.setCellStyle(headerStyle);
	
			// === HR 사원정보 내용에 해당하는 행 및 셀 생성하기 === //
			Row bodyRow = null;
			Cell bodyCell = null;
	
			for (int i = 0; i < searchSchList.size(); i++) {
	
				Map<String, String> schMap = searchSchList.get(i);
	
				// 행 생성
				bodyRow = sheet.createRow(i + (rowLocation + 1));
	
				// 데이터 일정명 표시
				bodyCell = bodyRow.createCell(0);
				bodyCell.setCellValue(schMap.get("title"));
	
				// 데이터 시작일 표시
				bodyCell = bodyRow.createCell(1);
				bodyCell.setCellValue(schMap.get("startDate").substring(0, 10)+" "+schMap.get("startDate").substring(11,16));
	
				// 데이터 종료일 표시
				bodyCell = bodyRow.createCell(2);
				bodyCell.setCellValue(schMap.get("endDate").substring(0, 10)+" "+schMap.get("endDate").substring(11,16));
	
				// 데이터 내용 표시
				bodyCell = bodyRow.createCell(3);
				bodyCell.setCellValue(schMap.get("content"));
	
				// 데이터 장소 표시
				bodyCell = bodyRow.createCell(4);
				bodyCell.setCellValue(schMap.get("location"));
	
				// 데이터 진행상황 표시 
				bodyCell = bodyRow.createCell(5);
				bodyCell.setCellValue(Integer.parseInt(schMap.get("status")));
	
				// 데이터 캘린더명 표시
				bodyCell = bodyRow.createCell(6);
				bodyCell.setCellValue(schMap.get("calName"));
	
				// 데이터 공유인원 표시 
				bodyCell = bodyRow.createCell(7);
				bodyCell.setCellValue(schMap.get("shareEmp"));
	
			} // end of for
	
			model.addAttribute("locale", Locale.KOREA);
			model.addAttribute("workbook", workbook);
			model.addAttribute("workbookName", "일정검색결과"); // 저장되는 파일명
	
			return "excelDownloadView";
			// "excelDownloadView"은 /WEB-INF/spring/appServlet/servlet-context.xml 파일에서
			// viewResolver 0순위로 기술된 bean의 id값이다.
		}
	}
	
	// 공유인원찾기
	@ResponseBody
	@RequestMapping(value = "/searchShareEmp.hello2", method = {RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
	public String searchShareEmp(HttpServletRequest request) {

		String employee = request.getParameter("employee"); 

		List<Map<String,String>> empList = service.searchShareEmp(employee);

		JSONArray jsonArr = new JSONArray();

		if (empList.size() > 0) {

			for (Map<String,String> map : empList) {

				JSONObject jsonObj = new JSONObject();

				jsonObj.put("empname", map.get("empname"));
				jsonObj.put("empid", map.get("empid"));
				jsonObj.put("deptname", map.get("deptname"));

				jsonArr.put(jsonObj);

			}

		}

		return jsonArr.toString();
	}
	
	
	// 공유캘린더 추가( iframe을 사용한 모달창 )
	@RequestMapping(value = "/modal/addShareSchedule.hello2") 
	public String addShareSchedule () {
		
		return "tiles1/schedule/addShareCalendar";
	}
	
	// 캘린더명 중복검사 확인
	@ResponseBody
	@RequestMapping(value = "/calnameDuplicateCheck.hello2", method = {RequestMethod.POST})
	public String requiredLogin_calnameDuplicateCheck (HttpServletRequest request, HttpServletResponse response) {
			
		HttpSession session = request.getSession();
		EmpVO_KJH loginEmp = (EmpVO_KJH) session.getAttribute("loginEmp");
		String empid = loginEmp.getEmpid();
		
		String calname = request.getParameter("calname");
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("empid", empid);
		paraMap.put("calname", calname);

		int count = service.calnameDuplicateCheck(paraMap);
		
		Boolean isExists = true;
		if(count>0) {
			isExists = true;
		}
		else {
			isExists = false;
		}
		
		JSONObject jsonObj = new JSONObject();	//{}
		jsonObj.put("isExists", isExists);		//{"isExists":true} 또는 {"isExists":false}

		return jsonObj.toString();
	}
	
	// 선택된 카테고리에 해당하는 일정만 보여주기 
	@RequestMapping("/showChkCalList.hello2")
	public String requiredLogin_showChkCalList(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,Object> paraMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		EmpVO_KJH loginEmp = (EmpVO_KJH) session.getAttribute("loginEmp");
		String empid = loginEmp.getEmpid();
		paraMap.put("empid", empid);
		
		// 캘린더 리스트
		List<CalendarVO_HJE> calList = service.showCalendarList(empid);
		request.setAttribute("calList", calList);
		
		String sCalNo = request.getParameter("sCalNo");
		
		// 아무것도 선택하지 않았을 시 일정메인페이지로 이동
		if ("".equals(sCalNo) ) {
			return "redirect:/schedule.hello2";
		}
		
		
		if(sCalNo != null && !"".equals(sCalNo)) {
			String[] calNoArr = sCalNo.split(",");
			paraMap.put("calNoArr", calNoArr);
			
			request.setAttribute("sCalNo", sCalNo);
		}
		
		List<Map<String,String>> chkCalList = service.showChkCalList(paraMap);
		
		request.setAttribute("chkCalList", chkCalList);
		
		return "schedule/calendar.tiles1";
	}
	
   
	////////////////////////////////////////////////////////////////////////////////
	// === 로그인 또는 로그아웃을 했을 때 현재 보이던 그 페이지로 그대로 돌아가기 위한 메소드 생성 ===
	public void getCurrentURL(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("goBackURL", MyUtil_HJE.getCurrentURL(request));
	}
	////////////////////////////////////////////////////////////////////////////////
   
}