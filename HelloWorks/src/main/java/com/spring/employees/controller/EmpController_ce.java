package com.spring.employees.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.employees.model.AttendanceVO_ce;
import com.spring.employees.model.DepartmentVO_ce;
import com.spring.employees.model.EmpVO_LCE;
import com.spring.employees.model.EmployeeVO_ce;
import com.spring.employees.service.InterEmpService_ce;
import com.spring.helloworks.model.EmpVO_KJH;

@Controller
public class EmpController_ce {
	
	@Autowired
	private InterEmpService_ce service;
	
	// 출퇴근관리페이지 보여주기 (모든사원 접근가능)
	// 앞에 requiredlogin_붙여야함, 파라미터 순서랑 종류 제대로 알아와야함, mav말고 request로 바꾸기
	@RequestMapping(value="/emp/viewAttend.hello2")
	public ModelAndView requiredLogin_viewAttend(HttpServletRequest request, HttpServletResponse response,ModelAndView mav) {
	   
		// 사원번호 가져오기
		HttpSession session = request.getSession();
		EmpVO_KJH loginEmp = (EmpVO_KJH) session.getAttribute("loginEmp");
		String empno = loginEmp.getEmpno();
		String empid = loginEmp.getEmpid();
		String empname = loginEmp.getEmpname();
		//System.out.println("확인용 empno: " + empno);
		// 확인용 empno: 202111101005
		
		
		// 당일기록조회해오기 
	 	Map<String,String> paraMap = new HashMap<>();
	 	paraMap.put("empno", empno); //확인용
		
		int isRecordExist = 0;
		isRecordExist = service.getRecordExist(paraMap);
		// System.out.println("확인용 isRecordExist : " + isRecordExist);
		// 확인용 isRecordExist : 1
		
		
		mav.addObject("empno", empno); // 사원번호
		mav.addObject("empid", empid); // 사원아이디
		mav.addObject("empname", empname); // 사원아이디
		mav.addObject("isRecordExist",isRecordExist); // 당일기록존재여부
		mav.setViewName("attendance/attendance.tiles1");   
		
	    return mav;
	 }
	
	
	// 출퇴근 테이블에서 레코드 가져오기(ajax로 처리)
	 @ResponseBody
	 @RequestMapping(value="/emp/getouttime.hello2", produces="text/plain;charset=UTF-8")
	 public String getouttime(HttpServletRequest request) {
	
		 String fk_empno = request.getParameter("fk_empno");
		 
		 // 출퇴근 테이블에서 레코드 가져오기 (nowdate를 굳이 넣어줄필요있나? 항상 오늘날짜만 조회할거니가,, 필요없을것같음)
		 AttendanceVO_ce attendancevo = service.getattendancevo(fk_empno);
		 
		 //System.out.println("확인용 출근시간 : " + attendancevo.getIntime());
		 // 확인용 출근시간 : 23:35:50
		 //System.out.println("확인용 퇴근시간 : " + attendancevo.getOuttime());
		 // 확인용 퇴근시간 : null
		 
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("intime",attendancevo.getIntime());
		 jsonObj.put("outtime",attendancevo.getOuttime());
		 
		 return jsonObj.toString();
		 
	 }
	 
	 
	 // 출퇴근 테이블에 출근시간 insert(ajax로 처리)
	 @ResponseBody
	 @RequestMapping(value="/emp/clickCheckin.hello2", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	 public String clickCheckin(HttpServletRequest request) {
		 
		 String fk_empno = request.getParameter("fk_empno");
		 String onlytime = request.getParameter("onlytime");
		 
		 Map<String,String> paraMap = new HashMap<>();
		 paraMap.put("fk_empno", fk_empno);
		 paraMap.put("onlytime", onlytime);
		 
		// 지각처리 -> 출퇴근테이블에 insert -> 출근시간 가져오기 ( 어차피 출근시간만 가져오면 되니깐 String으로 쓰겠음)
		// AttendanceVO_ce attendancevo = service.clickCheckin(fk_empno);
		 String intime = service.clickCheckin(paraMap);
		 
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("intime",intime);
		 
		 return jsonObj.toString();
		 
	 }
	 
	 
	// 출퇴근 테이블에 퇴근시간 update(ajax로 처리)
	 @ResponseBody
	 @RequestMapping(value="/emp/clickCheckout.hello2", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	 public String clickCheckout(HttpServletRequest request) {
		 
		 String fk_empno = request.getParameter("fk_empno");
		 String onlytime = request.getParameter("onlytime");
		 
		// System.out.println("확인용 onlytime:" + onlytime);
		 
		 Map<String,String> paraMap = new HashMap<>();
		 paraMap.put("fk_empno", fk_empno);
		 paraMap.put("onlytime", onlytime);
		 
		// 야근처리 -> 출퇴근테이블에 update -> 퇴근시간 가져오기 (어차피 퇴근시간만 가져오면 되니깐 String으로 쓰겠음)
		// AttendanceVO_ce attendancevo = service.clickCheckin(fk_empno);
		// String outtime = service.clickCheckout(paraMap);
		
		 AttendanceVO_ce attendancevo = service.clickCheckout(paraMap);
		 
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("outtime",attendancevo.getOuttime());
		 jsonObj.put("intime",attendancevo.getIntime());
		 
		 return jsonObj.toString();
	 }
	 
	 
	// 해당월의 평일날짜수(주말만 제외, 공휴일제외는 나중에api로 해보기)
	 @ResponseBody
	 @RequestMapping(value="/emp/getAttendStatus.hello2", produces="text/plain;charset=UTF-8")
	 public String getAttendStatus(HttpServletRequest request) {
	 
		 // 여기서 로그인유저 받아서 서비스단에 가는 방법 혹은 view단에서 받아온 사원번호로 vo 가져오는 방법있음 
		 //HttpSession session = request.getSession();
		 //String loginuser = (String) session.getAttribute("loginuser");
		 //String fk_empno = request.getParameter("fk_empno");
		 
		 // 근태관련 값들 paraMap에 담아오기
		 // 사원번호 가져오기
		 HttpSession session = request.getSession();
		 EmpVO_KJH loginEmp = (EmpVO_KJH) session.getAttribute("loginEmp");
		 String fk_empno = loginEmp.getEmpno();
		 
		 //Map<String,String> paraMap = service.getAttendStatus(loginuser); //사원번호는 넣어도되고 안넣어도됨 
		 Map<String,String> paraMap = service.getAttendStatus(fk_empno);
		 
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("status_latein",paraMap.get("status_latein")); // 지각횟수
		 jsonObj.put("status_earlyout",paraMap.get("status_earlyout")); // 조기퇴근횟수
		 jsonObj.put("outtimeNullCnt",paraMap.get("outtimeNullCnt")); // 퇴근미체크
		 jsonObj.put("absenceCnt",paraMap.get("absenceCnt")); // 결근횟수
		 
		 return jsonObj.toString();
		 
	 }
	 
	 
	 // 캘린더에 표시할 근태정보(한달치) 받아오기 (ajax)
	 @ResponseBody
	 @RequestMapping(value="/emp/showAttendCalendar.hello2", produces="text/plain;charset=UTF-8")
	 public String showAttendCalendar(HttpServletRequest request) {
		 
		//System.out.println("근태컨트롤로 넘어옴");
		 
		String fk_empno = request.getParameter("fk_empno");
		 //String fk_empno = "202111081004";
		 
		 List<AttendanceVO_ce> attendanceList = service.getAttendanceList(fk_empno);
		 
		 JSONArray jsonArr = new JSONArray(); // []
	       
	       if(attendanceList != null) {
	    	   for(AttendanceVO_ce attvo : attendanceList) {
	    		   JSONObject jsonObj = new JSONObject();
	    		   jsonObj.put("nowdate", attvo.getNowdate());
	    		   jsonObj.put("intime", attvo.getIntime());
	    		   jsonObj.put("outtime", attvo.getOuttime());
	    		   jsonObj.put("totaltime", attvo.getTotaltime());
	    		   
	    		   jsonArr.put(jsonObj);
	    	   }
	       }
		 
		 
		 return jsonArr.toString();
	 }
	 
	 
	// 로그인한 유저의 소속부서를 확인하는 메소드
	   private boolean checkDepartment(HttpServletRequest request, String deptnum) {
	      
	      boolean checkDepartment = false;
	      
	      HttpSession session = request.getSession();
	      
	      EmpVO_KJH loginEmp = (EmpVO_KJH)session.getAttribute("loginEmp");
	      //System.out.println("확인용 부서번호 : " + loginEmp.getFk_deptnum());
	      
	      
	      if(loginEmp != null && (deptnum.equals(loginEmp.getFk_deptnum()) || "00".equals(loginEmp.getFk_deptnum()))) {
	         checkDepartment = true;
	      }
	      
	      return checkDepartment;
	      
	   }
	   
	 /* 로그인필요
	 // 출퇴근관리페이지 보여주기 (인사부만 접근가능)
	 @RequestMapping(value="/emp/viewAttendOnlyHR.hello2")
	 public ModelAndView requiredLogin_viewAttendOnlyHR(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		 //인사부 확인 
		 if(!checkDepartment(request,"10")) { // 인사부가 아닌경우
				String message = "권한이 없습니다.";
				String loc = request.getContextPath() + "/emp/viewAttend.hello2";
				
				mav.addObject("message", message); 
				mav.addObject("loc", loc);
				mav.setViewName("msg_LCE"); 
			}
		 else {
			  List<DepartmentVO_ce> departmentList = service.getDepartmentList();
		 
			 mav.addObject("departmentList", departmentList);
			 mav.setViewName("attendance/attendanceOnlyHR.tiles1"); 
		 
		 }
		 // 인사부인 경우 페이지 정상 넘김
		 return mav;
	 }
	 */
	 
	 /*
	 //기능테스트용 
	 // 출퇴근관리페이지 보여주기 (인사부만 접근가능)
	 @RequestMapping(value="/emp/viewAttendOnlyHR.hello2")
	 public ModelAndView viewAttendOnlyHR(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		 List<DepartmentVO_ce> departmentList = service.getDepartmentList();
		 
		 mav.addObject("departmentList", departmentList);
		 mav.setViewName("attendance/attendanceOnlyHR.tiles1"); 
		 
		 return mav;
	 }
	 */
	   
     // 기능테스트용 
	 // 출퇴근관리페이지 보여주기 (인사부만 접근가능 => ok, 로그인연결 => )
	 @RequestMapping(value="/emp/viewAttendOnlyHR.hello2")
	 public ModelAndView requiredLogin_viewAttendOnlyHR(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		 if(!checkDepartment(request,"10")) { // 인사부가 아닌경우
				String message = "권한이 없습니다.";
				String loc = request.getContextPath() + "/emp/viewAttend.hello2";
				
				mav.addObject("message", message); 
				mav.addObject("loc", loc);
				mav.setViewName("msg_LCE"); 
			}
		 else {
			  List<DepartmentVO_ce> departmentList = service.getDepartmentList();
		 
			 mav.addObject("departmentList", departmentList);
			 mav.setViewName("attendance/attendanceOnlyHR.tiles1"); 
		 
		 }
		 return mav;

	 }
	   
	   
	// 캘린더에 표시할 부서별 근태정보 받아오기 (ajax)
	 @ResponseBody
	 @RequestMapping(value="/emp/showDepCalendar.hello2", produces="text/plain;charset=UTF-8")
	 public String showDepCalendar(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		 String fk_empno = request.getParameter("fk_empno"); // 이것도 필요가 있나?
		 String deptnum = request.getParameter("deptnum");
		 String nowdate = request.getParameter("nowdate"); // 2021-11-01 형태 이거는 캘린더에서는 굳이 필요없는 것 같음 
		 
		// Map<String,String> paraMap = new HashMap<>();
		// paraMap.put("deptnum", deptnum);
	
		 
		 List<AttendanceVO_ce> attendanceList = service.getDepattendanceList(deptnum);
		 
		 JSONArray jsonArr = new JSONArray(); // []
	       
		 // 
	      if(attendanceList != null) {
	    	   for(AttendanceVO_ce attvo : attendanceList) {
	    		   JSONObject jsonObj = new JSONObject();
	    		   jsonObj.put("fk_empno", attvo.getFk_empno() );
	    		   jsonObj.put("empname", attvo.getEmpname());
	    		   jsonObj.put("deptnum", attvo.getDeptnum());
	    		   jsonObj.put("nowdate", attvo.getNowdate());
	    		   jsonObj.put("intime", attvo.getIntime());
	    		   jsonObj.put("outtime", attvo.getOuttime());
	    		   jsonObj.put("totaltime", attvo.getTotaltime());
	    		   jsonObj.put("status_latein", attvo.getStatus_latein());
	    		   jsonObj.put("status_earlyout", attvo.getStatus_earlyout());
	    		   
	    		   jsonArr.put(jsonObj);
	    	   }
	       }
		 
		 
		 return jsonArr.toString();
	 }
	 
	 
	 
	// 테이블에 표시할 부서별 사원 정보 받아오기 (ajax)
	 @ResponseBody
	 @RequestMapping(value="/emp/showDepTable.hello2", produces="text/plain;charset=UTF-8")
	 public String showDepTable(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	 
		 String deptnum = request.getParameter("deptnum");
		 String year = request.getParameter("year");
		 String month = request.getParameter("month");
		 
		 String searchDate = year +"-"+ month ; //yyyy-mm 형태로 보정 
		 
		 // 부서별  사원번호 가져오기
		 List<EmpVO_KJH> depEmpList = service.getDepEmpList(deptnum);
		 
		 JSONArray jsonArr = new JSONArray(); // []
	     
	      if(depEmpList != null) {
	    	   for(EmpVO_KJH empvo : depEmpList) {
	    		   
	    		   Map<String,String> paraMap = new HashMap<>();
	    		   
	    		   // 부서별 사원근태정보 가져오기
	    		   String fk_empno = empvo.getEmpno();
	    		   paraMap.put("fk_empno",fk_empno);
	    		   paraMap.put("searchDate", searchDate);
	    		   AttendanceVO_ce attvo = service.getDepEmpAttendReport(paraMap); // 날짜도 들어와야함 ㅇㅋ

	    		   // 퇴근미체크, 결근 횟수 가져오기 
	    		   String hiredate = empvo.getHiredate().substring(0, 10).replace("-", "/");
	    		   //System.out.println("컨트롤러에서 hiredate : " + hiredate);
	    		   paraMap.put("hiredate",hiredate);
	    		   paraMap.put("workday", String.valueOf(attvo.getSumworkday()));
	    		   
	    		   Map<String, Object> empMap = new HashMap<>();
	    		   empMap = service.getDepEmpReport(paraMap); // 조회할 월도 같이 들어가야함 ㅇㅋ
	    		  
	    		   
	    		   JSONObject jsonObj = new JSONObject();
	    		   jsonObj.put("empname", empvo.getEmpname());
	    		   jsonObj.put("empid", empvo.getEmpid());
	    		   jsonObj.put("fk_deptnum", empvo.getFk_deptnum());
	    		   jsonObj.put("ranking", empvo.getRanking());

	    		   if(empvo.getEmpstatus() == 1) {
	    			   jsonObj.put("empstatus", "재직");
	    			   jsonObj.put("hiredate", hiredate);
	    			   jsonObj.put("sumlate", attvo.getSumlate()); 		//지각
	    			   jsonObj.put("sumearly", attvo.getSumearly()); 	//조퇴
	    			   jsonObj.put("sumtotal", attvo.getSumtotal());	//총근무시간
	    			   jsonObj.put("sumworkday", attvo.getSumworkday());//총근무일자
	    			   jsonObj.put("outtimeNullCnt", empMap.get("outtimeNullCnt")); //퇴근미체크
	    			   jsonObj.put("absenceCnt", empMap.get("absenceCnt")); //결근
	    		   }
	    		   else {
	    			   jsonObj.put("empstatus", "휴직");
	    		   }
	    		   
	    			   
	    		   jsonArr.put(jsonObj);
	    	   }
	       }
		 
		 
		 return jsonArr.toString();
	 }
	 
	 
	 
	// select에 표시할 부서별 사원 정보 받아오기 (ajax)
	 @ResponseBody
	 @RequestMapping(value="/emp/showDepSelect.hello2", produces="text/plain;charset=UTF-8")
	 public String showDepSelect(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		 
		String deptnum = request.getParameter("deptnum");
		
		// 부서별  사원번호 가져오기
		List<EmpVO_KJH> depEmpList = service.getDepEmpList(deptnum);
		 
		JSONArray jsonArr = new JSONArray(); // []
	     
	    if(depEmpList != null) {
		 
		    for(EmpVO_KJH empvo : depEmpList) {
    		   
    		   Map<String,String> paraMap = new HashMap<>();
    		   JSONObject jsonObj = new JSONObject();
    		   jsonObj.put("empname", empvo.getEmpname());
    		   jsonObj.put("empno", empvo.getEmpno());
    		   jsonObj.put("empid", empvo.getEmpid());
    		   jsonObj.put("fk_deptnum", empvo.getFk_deptnum());
    		   jsonObj.put("ranking", empvo.getRanking());
    		  
    		   jsonArr.put(jsonObj);
		    }
	    	
	    }	
	    
	    return jsonArr.toString();
	    	
	 }
	 
	 
	 // 부서별 사원들 근태현황 엑셀다운받기
	 @RequestMapping(value="/emp/downloadEmpExcelFile.hello2", method = {RequestMethod.POST})
	 public String downloadEmpExcelFile(HttpServletRequest request, Model model) {
	 
		String fk_empno = request.getParameter("empno");
		System.out.println("확인용 empno => " + fk_empno);
	 
		String empname = request.getParameter("empname");
		System.out.println("확인용 empname => " + empname);
		
		List<AttendanceVO_ce> attendanceList = service.excelattendancevo(fk_empno);
		
		// === 조회결과물인 empList 를 가지고 엑셀 시트 생성하기 ===
	    // 시트를 생성하고, 행을 생성하고, 셀을 생성하고, 셀안에 내용을 넣어주면 된다.
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		// 시트생성
		String subject = "" + empname + "("+ fk_empno + ")" +"사원의 근태정보";
		SXSSFSheet sheet = workbook.createSheet(subject);
		
		// 시트 열 너비 설정 (7개의 열)
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(5, 2000);
		sheet.setColumnWidth(6, 2000);
		
		// 행의 위치를 나타내는 변수
		int rowLocation = 0;
		
		
		////////////////////////////////////////////////////////////////////////////////////////
		// CellStyle 정렬하기(Alignment)
		// CellStyle 객체를 생성하여 Alignment 세팅하는 메소드를 호출해서 인자값을 넣어준다.
		// 아래는 HorizontalAlignment(가로)와 VerticalAlignment(세로)를 모두 가운데 정렬 시켰다.
		CellStyle mergeRowStyle = workbook.createCellStyle(); // 사원 근태정보
	    mergeRowStyle.setAlignment(HorizontalAlignment.CENTER);
	    mergeRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    // import org.apache.poi.ss.usermodel.VerticalAlignment 으로 해야함.
	    
	    CellStyle headerStyle = workbook.createCellStyle(); // 날짜, 출근시간...
	    headerStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    
	    
	    // CellStyle 배경색(ForegroundColor)만들기
        // setFillForegroundColor 메소드에 IndexedColors Enum인자를 사용한다.
        // setFillPattern은 해당 색을 어떤 패턴으로 입힐지를 정한다
	    mergeRowStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex()); // IndexedColors.DARK_BLUE.getIndex() 는 색상(남색)의 인덱스값을 리턴시켜준다.
	    mergeRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    headerStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex()); // IndexedColors.LIGHT_YELLOW.getIndex() 는 연한노랑의 인덱스값을 리턴시켜준다.
	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    // Cell 폰트(Font) 설정하기
        // 폰트 적용을 위해 POI 라이브러리의 Font 객체를 생성해준다.
        // 해당 객체의 세터를 사용해 폰트를 설정해준다. 대표적으로 글씨체, 크기, 색상, 굵기만 설정한다.
        // 이후 CellStyle의 setFont 메소드를 사용해 인자로 폰트를 넣어준다.
	    Font mergeRowFont = workbook.createFont(); // import org.apache.poi.ss.usermodel.Font; 으로 한다.
	    mergeRowFont.setFontName("나눔고딕");
	    mergeRowFont.setFontHeight((short)500);
	    mergeRowFont.setColor(IndexedColors.BLACK.getIndex());
	    mergeRowFont.setBold(true);
	    
	    mergeRowStyle.setFont(mergeRowFont);
	    
	    
	    // CellStyle 테두리 Border
        // 테두리는 각 셀마다 상하좌우 모두 설정해준다.
        // setBorderTop, Bottom, Left, Right 메소드와 인자로 POI라이브러리의 BorderStyle 인자를 넣어서 적용한다.
	    headerStyle.setBorderTop(BorderStyle.THICK);
	    headerStyle.setBorderBottom(BorderStyle.THICK);
	    headerStyle.setBorderLeft(BorderStyle.THIN);
	    headerStyle.setBorderRight(BorderStyle.THIN);
	    
	    // Cell Merge 셀 병합시키기
        /* 셀병합은 시트의 addMergeRegion 메소드에 CellRangeAddress 객체를 인자로 하여 병합시킨다.
           CellRangeAddress 생성자의 인자로(시작 행, 끝 행, 시작 열, 끝 열) 순서대로 넣어서 병합시킬 범위를 정한다. 배열처럼 시작은 0부터이다.  
        */
        // 병합할 행 만들기
	    Row mergeRow = sheet.createRow(rowLocation); //엑셀에서 행의 시작은 0부터 시작된다.
	    
	    //병합할 행에 "우리회사 사원정보" 로 셀을 만들어 셀에 스타일을 주기  
	    for(int i=0; i<7; i++) {
	    	Cell cell = mergeRow.createCell(i);
	    	cell.setCellStyle(mergeRowStyle);
	    	cell.setCellValue(subject);
	    }
	    
	    // 셀 병합하기 
	    sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0 , 6));
	    
	    // CellStyle 천단위 쉼표, 금액
        CellStyle moneyStyle = workbook.createCellStyle();
        moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
        ////////////////////////////////////////////////////////////////////////////////////////////////
	    
        // 헤더 행 생성 
        Row headerRow = sheet.createRow(++rowLocation); // 엑셀에서 행의 시작은 0 부터 시작한다.
        												// ++rowLocation는 전위연산자임. 
        
        // 해당 행의 첫번째 열 셀 생성
        Cell headerCell = headerRow.createCell(0); // 엑셀에서 열의 시작은 0부터 시작한다.
        headerCell.setCellValue("날짜");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 두번째 열 셀 생성
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("사원번호");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 세번째 열 셀 생성
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("출근시간");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 네번째 열 셀 생성
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("퇴근시간");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 다섯번째 열 셀 생성
        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("당일근무시간");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 여섯번째 열 셀 생성
        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("지각유무");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 일곱번째 열 셀 생성
        headerCell = headerRow.createCell(6);
        headerCell.setCellValue("조퇴유무");
        headerCell.setCellStyle(headerStyle);
        
  
        
        // ==== HR사원정보 내용에 해당하는 행 및 셀 생성하기 ==== //
        Row bodyRow = null; 
        Cell bodyCell = null;
        
        for(int i=0; i<attendanceList.size(); i++ ) {
        	
        	AttendanceVO_ce attvo = attendanceList.get(i);
        	
        	// 행생성
        	bodyRow = sheet.createRow(i + (rowLocation + 1));
        	
        	// 데이터 날짜 표시
        	bodyCell = bodyRow.createCell(0);
        	bodyCell.setCellValue(attvo.getNowdate());
        	
        	// 데이터 사원번호 표시
        	bodyCell = bodyRow.createCell(1);
        	bodyCell.setCellValue(attvo.getFk_empno());
        	
        	// 데이터 출근시간 표시
        	bodyCell = bodyRow.createCell(2);
        	bodyCell.setCellValue(attvo.getIntime());
        	
        	// 데이터 퇴근시간 표시
        	bodyCell = bodyRow.createCell(3);
        	bodyCell.setCellValue(attvo.getOuttime());
        
        	// 데이터 당일총근무시간 표시
        	bodyCell = bodyRow.createCell(4);
        	bodyCell.setCellValue(attvo.getTotaltime());
        	
        	// 데이터 지각유무 표시
        	bodyCell = bodyRow.createCell(5);
        	bodyCell.setCellValue(attvo.getStatus_latein());
        	
        	// 데이터 조퇴유무 표시
        	bodyCell = bodyRow.createCell(6);
        	bodyCell.setCellValue(attvo.getStatus_earlyout());
        	
        	
        }// end of for------------------------------
        
        model.addAttribute("locale", Locale.KOREA);
        model.addAttribute("workbook", workbook);
        model.addAttribute("workbookName", subject);
        
        
		return "excelDownloadView";
		
	 }
	 
	 
	 // 11월 21일 : 출퇴근관리 개인페이지에 account로그인이 안됨, 주말 출근시 계산 
	 
	 /*
	 // 테스트용 
	 @RequestMapping(value="/emp/viewEmployee.hello2")
	 public ModelAndView viewEmployee(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	 
		List<DepartmentVO_ce> departmentList = service.getDepartmentList();
		 
		 mav.addObject("departmentList", departmentList);
		 mav.setViewName("employee_LCE/viewEmployee.tiles1"); 
		 
		 return mav;
	 }
	 */
	 
	 // 테스트용, 부서확인, 페이지 돌아가기 넣어야함 
	 @RequestMapping(value="/emp/viewEmployee.hello2")
	 public ModelAndView requiredLogin_viewEmployee(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	 
 		//getCurrentURL(request); //로그인 또는 로그아웃을 했을 때 현재 보이던 그 페이지로 그대로 돌아가기 위한 메소드 호출
		
		 if(!checkDepartment(request,"10")) { // 인사부가 아닌경우
				String message = "권한이 없습니다.";
				String loc = request.getContextPath() + "/emp/viewAttend.hello2";
				
				mav.addObject("message", message); 
				mav.addObject("loc", loc);
				mav.setViewName("msg_LCE"); 
			}
		 else {
		 
		 
	 		List<EmpVO_LCE> empList = null;
	 
		    //부서번호 가져오기 
			List<DepartmentVO_ce> departmentList = service.getDepartmentList();
			
			// 부서번호 가져오기(다중선택가능)
			String deptnoList = request.getParameter("deptnoList");
			System.out.println("~~~ 확인용 deptnoList => " + deptnoList);
			
			// 직위종류 가져오기 (다중선택가능) 
			String rankingList = request.getParameter("rankingList");
			System.out.println("~~~ 확인용 rankingList => " + rankingList);
	
			// 입사년도 가져오기 (선택하나만 가능)
			String hireyear = request.getParameter("hireyear");
			System.out.println("~~~ 확인용 hireyear => " + hireyear);
			
			// 휴직상태 가져오기 (선택하나만 가능)
			String empstatus = request.getParameter("empstatus");
			System.out.println("~~~ 확인용 empstatus => " + empstatus);
			
			// 검색한 사원번호 가져오기 
			String empno = request.getParameter("empno");
			System.out.println("~~~ 확인용 empno => " + empno);
			
			// 현재페이지 가져오기 
		    String str_currentShowPageNo = request.getParameter("currentShowPageNo");
			
			
			Map<String, Object> paraMap = new HashMap<>();
			
			// 부서선택
			if( deptnoList != null && !"".equals(deptnoList) ) {
				
				String[] deptnoArr = deptnoList.split(",");
				paraMap.put("deptnoArr", deptnoArr);
				
				request.setAttribute("deptnoList", deptnoList);
				//뷰단에서 체크되어진 값을 유지시키기 위한 것이다. 
			}
			
			// 직급선택
			if( rankingList != null && !"".equals(rankingList) ) {
				
				String[] rankArr = rankingList.split(",");
				paraMap.put("rankArr", rankArr);
				
				request.setAttribute("rankingList", rankingList);
				//뷰단에서 체크되어진 값을 유지시키기 위한 것이다. 
			}
			
			// 입사년도 선택
			if( hireyear != null && !"".equals(hireyear) && !"0".equals(hireyear)) {
				paraMap.put("hireyear", hireyear);
				request.setAttribute("hireyear", hireyear);
				//뷰단에서 선택한 값을 유지시키기 위한 것이다. 
			}
			
			
			// 휴직상태 선택
			if(  empstatus != null && !"".equals(empstatus) && !"3".equals(empstatus)) {
				paraMap.put("empstatus", empstatus);
				request.setAttribute("empstatus", empstatus);
				//뷰단에서 선택한 값을 유지시키기 위한 것이다. 
			}
			
			// 사원번호 검색 받아오기 
			if( empno != null && !"".equals(empno) && ! empno.trim().isEmpty() ) {
				paraMap.put("empno", empno);
				request.setAttribute("empno", empno);
				//뷰단에서 선택한 값을 유지시키기 위한 것이다. 
			}
			
			// 먼저 총 사원정보 건수(totalCount)를 구해와야 한다. 
			// 총 사원정보 건수(totalCount)는 검색조건이 있을때와 없을때로 나뉘어진다. 
			
			int totalCount = 0; 	// 총 게시물건수(totalCount)
			int sizePerPage = 10;	// 한 페이지다 보여줄 게시물 건수
			int currentShowPageNo = 0;	// 현재 보여주는 페이지 번호로서, 초기치로는 1
			int totalPage= 0; 			// 총 페이지수(웹브라우저상에서 보여줄 총 페이지)
			
			int startRno = 0;			// 시작 행 번호
			int endRno = 0;				// 끝 행번호
	
			//총 사원정보 건수(totalCount)
			// 사원정보 건수 조회
			totalCount = service.getTotalCount_ce(paraMap); 
			mav.addObject("totalCount", totalCount);
			//System.out.println("~~~확인용 totalCount:" + totalCount);
			
			//만약에 총 게시물 건수(totalCount)가 127개이라면
			//총 페이지수(totalPage)는 13개가 되어야 한다.
		
			totalPage = (int)Math.ceil( (double)totalCount/sizePerPage ); // (double)127/10 ==> 12.7 ==> Math.ceil(12.7) ==> 13.0 ==> (int)13 
		
			if(str_currentShowPageNo == null) { // 현재페이지 설정 안한경우 즉 맨 처음 들어온 경우 
				//게시판에 보여지는 초기화면
				currentShowPageNo = 1;
			}
			else {
				try {
					currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
					if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
						currentShowPageNo = 1;
					}
				}catch(NumberFormatException e) {
					currentShowPageNo = 1;
				}
			}
			
			
			startRno = ( (currentShowPageNo -1) * sizePerPage )	+ 1;	// 시작 행 번호
			endRno = startRno + sizePerPage - 1;
			
			paraMap.put("startRno", String.valueOf(startRno));
			paraMap.put("endRno", String.valueOf(endRno));
			
			// 사원정보 조회해올려면 이것 사용해야함 아직수정전
			empList = service.empListSearchWithPaging_ce(paraMap);
			// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한 것)
			
			
			// === 페이지바 만들기 === //
		   int blockSize = 10;
		
		   int loop = 1;
			
		   int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
	
		    // *** !! 공식이다. !! *** //
		    //   1  2  3  4  5  6  7  8  9  10  -- 첫번째 블럭의 페이지번호 시작값(pageNo)은 1 이다.
		    //   11 12 13 14 15 16 17 18 19 20  -- 두번째 블럭의 페이지번호 시작값(pageNo)은 11 이다.
		    //   21 22 23 24 25 26 27 28 29 30  -- 세번째 블럭의 페이지번호 시작값(pageNo)은 21 이다.
		    
		    String pageBar = "<ul style='list-style: none;'>";
			String url = "emp/viewEmployee.hello2";
	
			// === [맨처음][이전] 만들기 ===
			if( pageNo != 1) {
				pageBar += "<li style='display:inline-block; width:70px; font-size:12pt; '><a href='"+url+"?deptnoList="+deptnoList+"&rankingList="+rankingList+"&hireyear="+hireyear+"&empstatus="+empstatus+"&empno="+empno+"&currentShowPageNo=1'>[맨처음]</a></li>";
				pageBar += "<li style='display:inline-block; width:50px; font-size:12pt; '><a href='"+url+"?deptnoList="+deptnoList+"&rankingList="+rankingList+"&hireyear="+hireyear+"&empstatus="+empstatus+"&empno="+empno+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
			}
			
			// 
			while( !(loop > blockSize || pageNo > totalPage ) ) {
				if(pageNo == currentShowPageNo) {
					pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</a></li>";
				}
				else {
					pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; '><a href='"+url+"?deptnoList="+deptnoList+"&rankingList="+rankingList+"&hireyear="+hireyear+"&empstatus="+empstatus+"&empno="+empno+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
				}
				
				loop++;
				pageNo++;
				
			}// end of while
		    
		    // === [다음][마지막] 만들기 ===
			
			if( pageNo <= totalPage) {
				pageBar += "<li style='display:inline-block; width:50px; font-size:12pt; '><a href='"+url+"?deptnoList="+deptnoList+"&rankingList="+rankingList+"&hireyear="+hireyear+"&empstatus="+empstatus+"&empno="+empno+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
				pageBar += "<li style='display:inline-block; width:70px; font-size:12pt; '><a href='"+url+"?deptnoList="+deptnoList+"&rankingList="+rankingList+"&hireyear="+hireyear+"&empstatus="+empstatus+"&empno="+empno+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
			}
			
			pageBar += "</ul>";
			
			mav.addObject("pageBar", pageBar);
		    
			
			// 사원정보조회해오기 아직 수정전 이거 안써도 되는 것 같음 
			//List<Map<String,String>> empList = service.empList(paraMap);
			
			mav.addObject("departmentList", departmentList);
			mav.addObject("empList", empList); 
			mav.setViewName("employee_LCE/viewEmployee.tiles1");
			// /WEB-INF/views/tiles2/emp/empList.jsp 파일을 생성한다. 
		 }
		 
		return mav;
	 }
	 
	 
	 // 부서별 사원정보 엑셀다운받기
	 @RequestMapping(value="/emp/downloadEmpInfoExcelFile.hello2", method = {RequestMethod.POST})
	 public String downloadSearchedEmpInfoExcelFile(HttpServletRequest request, Model model) {
	 
	    // 부서번호 가져오기(다중선택가능)
		String deptnoList = request.getParameter("deptnoList");
		System.out.println("~~~ 확인용 deptnoList => " + deptnoList);
		
		// 직위종류 가져오기 (다중선택가능) 
		String rankingList = request.getParameter("rankingList");
		System.out.println("~~~ 확인용 rankingList => " + rankingList);

		// 입사년도 가져오기 (선택하나만 가능)
		String hireyear = request.getParameter("hireyear");
		System.out.println("~~~ 확인용 hireyear => " + hireyear);
		
		// 휴직상태 가져오기 (선택하나만 가능)
		String empstatus = request.getParameter("empstatus");
		System.out.println("~~~ 확인용 empstatus => " + empstatus);
		
		// 검색한 사원번호 가져오기 
		String empno = request.getParameter("empno");
		System.out.println("~~~ 확인용 empno => " + empno);
		
		// 현재페이지 가져오기 
	    String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		
		Map<String, Object> paraMap = new HashMap<>();
		
		// 부서선택
		if( deptnoList != null && !"".equals(deptnoList) ) {
			
			String[] deptnoArr = deptnoList.split(",");
			paraMap.put("deptnoArr", deptnoArr);
			
			request.setAttribute("deptnoList", deptnoList);
			//뷰단에서 체크되어진 값을 유지시키기 위한 것이다. 
		}
		
		// 직급선택
		if( rankingList != null && !"".equals(rankingList) ) {
			
			String[] rankArr = rankingList.split(",");
			paraMap.put("rankArr", rankArr);
			
			request.setAttribute("rankingList", rankingList);
			//뷰단에서 체크되어진 값을 유지시키기 위한 것이다. 
		}
		
		// 입사년도 선택
		if( hireyear != null && !"".equals(hireyear) && !"0".equals(hireyear)) {
			paraMap.put("hireyear", hireyear);
			request.setAttribute("hireyear", hireyear);
			//뷰단에서 선택한 값을 유지시키기 위한 것이다. 
		}
		
		// 휴직상태 선택
		if(  empstatus != null && !"".equals(empstatus) && !"3".equals(empstatus)) {
			paraMap.put("empstatus", empstatus);
			request.setAttribute("empstatus", empstatus);
			//뷰단에서 선택한 값을 유지시키기 위한 것이다. 
		}
		
		// 사원번호 검색 받아오기 
		if( empno != null && !"".equals(empno) && ! empno.trim().isEmpty() ) {
			paraMap.put("empno", empno);
			request.setAttribute("empno", empno);
			//뷰단에서 선택한 값을 유지시키기 위한 것이다. 
		}
		
		 
		List<EmpVO_LCE> empList = service.empListSearchWithPaging_ce(paraMap);
		 
		
		// === 조회결과물인 empList 를 가지고 엑셀 시트 생성하기 ===
	    // 시트를 생성하고, 행을 생성하고, 셀을 생성하고, 셀안에 내용을 넣어주면 된다.
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		// 시트생성
		String subject = "사원정보";
		SXSSFSheet sheet = workbook.createSheet(subject);
		
		// 시트 열 너비 설정 (10개의 열)
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(5, 2000);
		sheet.setColumnWidth(6, 2000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 3000);
		sheet.setColumnWidth(9, 7000);
		
		// 행의 위치를 나타내는 변수
		int rowLocation = 0;
		
		
		////////////////////////////////////////////////////////////////////////////////////////
		// CellStyle 정렬하기(Alignment)
		// CellStyle 객체를 생성하여 Alignment 세팅하는 메소드를 호출해서 인자값을 넣어준다.
		// 아래는 HorizontalAlignment(가로)와 VerticalAlignment(세로)를 모두 가운데 정렬 시켰다.
		CellStyle mergeRowStyle = workbook.createCellStyle(); // 사원 근태정보
	    mergeRowStyle.setAlignment(HorizontalAlignment.CENTER);
	    mergeRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    // import org.apache.poi.ss.usermodel.VerticalAlignment 으로 해야함.
	    
	    CellStyle headerStyle = workbook.createCellStyle(); // 날짜, 출근시간...
	    headerStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    
	    
	    // CellStyle 배경색(ForegroundColor)만들기
        // setFillForegroundColor 메소드에 IndexedColors Enum인자를 사용한다.
        // setFillPattern은 해당 색을 어떤 패턴으로 입힐지를 정한다
	    mergeRowStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex()); // IndexedColors.DARK_BLUE.getIndex() 는 색상(남색)의 인덱스값을 리턴시켜준다.
	    mergeRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    headerStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex()); // IndexedColors.LIGHT_YELLOW.getIndex() 는 연한노랑의 인덱스값을 리턴시켜준다.
	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    // Cell 폰트(Font) 설정하기
        // 폰트 적용을 위해 POI 라이브러리의 Font 객체를 생성해준다.
        // 해당 객체의 세터를 사용해 폰트를 설정해준다. 대표적으로 글씨체, 크기, 색상, 굵기만 설정한다.
        // 이후 CellStyle의 setFont 메소드를 사용해 인자로 폰트를 넣어준다.
	    Font mergeRowFont = workbook.createFont(); // import org.apache.poi.ss.usermodel.Font; 으로 한다.
	    mergeRowFont.setFontName("나눔고딕");
	    mergeRowFont.setFontHeight((short)500);
	    mergeRowFont.setColor(IndexedColors.BLACK.getIndex());
	    mergeRowFont.setBold(true);
	    
	    mergeRowStyle.setFont(mergeRowFont);
	    
	    
	    // CellStyle 테두리 Border
        // 테두리는 각 셀마다 상하좌우 모두 설정해준다.
        // setBorderTop, Bottom, Left, Right 메소드와 인자로 POI라이브러리의 BorderStyle 인자를 넣어서 적용한다.
	    headerStyle.setBorderTop(BorderStyle.THICK);
	    headerStyle.setBorderBottom(BorderStyle.THICK);
	    headerStyle.setBorderLeft(BorderStyle.THIN);
	    headerStyle.setBorderRight(BorderStyle.THIN);
	    
	    // Cell Merge 셀 병합시키기
        /* 셀병합은 시트의 addMergeRegion 메소드에 CellRangeAddress 객체를 인자로 하여 병합시킨다.
           CellRangeAddress 생성자의 인자로(시작 행, 끝 행, 시작 열, 끝 열) 순서대로 넣어서 병합시킬 범위를 정한다. 배열처럼 시작은 0부터이다.  
        */
        // 병합할 행 만들기
	    Row mergeRow = sheet.createRow(rowLocation); //엑셀에서 행의 시작은 0부터 시작된다.
	    
	    //병합할 행에 "우리회사 사원정보" 로 셀을 만들어 셀에 스타일을 주기  
	    for(int i=0; i<10; i++) {
	    	Cell cell = mergeRow.createCell(i);
	    	cell.setCellStyle(mergeRowStyle);
	    	cell.setCellValue(subject);
	    }
	    
	    // 셀 병합하기 
	    sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0 , 9));
	    
	    // CellStyle 천단위 쉼표, 금액
        CellStyle moneyStyle = workbook.createCellStyle();
        moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
        ////////////////////////////////////////////////////////////////////////////////////////////////
	    
        // 헤더 행 생성 
        Row headerRow = sheet.createRow(++rowLocation); // 엑셀에서 행의 시작은 0 부터 시작한다.
        												// ++rowLocation는 전위연산자임. 
        
        // 해당 행의 첫번째 열 셀 생성
        Cell headerCell = headerRow.createCell(0); // 엑셀에서 열의 시작은 0부터 시작한다.
        headerCell.setCellValue("사원번호");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 두번째 열 셀 생성
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("사원명");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 세번째 열 셀 생성
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("아이디");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 네번째 열 셀 생성
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("사내이메일");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 다섯번째 열 셀 생성
        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("직급");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 여섯번째 열 셀 생성
        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("부서");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 일곱번째 열 셀 생성
        headerCell = headerRow.createCell(6);
        headerCell.setCellValue("재직상태");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 여덟곱번째 열 셀 생성
        headerCell = headerRow.createCell(7);
        headerCell.setCellValue("연봉(단위:만원)");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 아홉번째 열 셀 생성
        headerCell = headerRow.createCell(8);
        headerCell.setCellValue("입사일");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 열본째 열 셀 생성
        headerCell = headerRow.createCell(9);
        headerCell.setCellValue("개인이메일");
        headerCell.setCellStyle(headerStyle);
        
  
        
        // ==== HR사원정보 내용에 해당하는 행 및 셀 생성하기 ==== //
        Row bodyRow = null; 
        Cell bodyCell = null;
        
        for(int i=0; i<empList.size(); i++ ) {
        	
        	EmpVO_LCE empvo = empList.get(i);
        	
        	// 행생성
        	bodyRow = sheet.createRow(i + (rowLocation + 1));
        	
        	// 데이터 사원번호 표시
        	bodyCell = bodyRow.createCell(0);
        	bodyCell.setCellValue(empvo.getEmpno());
        	
        	// 데이터 사원명 표시
        	bodyCell = bodyRow.createCell(1);
        	bodyCell.setCellValue(empvo.getEmpname());
        	
        	// 데이터 아이디 표시
        	bodyCell = bodyRow.createCell(2);
        	bodyCell.setCellValue(empvo.getEmpid());
        	
        	// 데이터 이메일 표시
        	bodyCell = bodyRow.createCell(3);
        	bodyCell.setCellValue(empvo.getEmail());
        
        	// 데이터 직급 표시
        	bodyCell = bodyRow.createCell(4);
        	bodyCell.setCellValue(empvo.getRanking());
        	
        	// 데이터 부서 표시
        	bodyCell = bodyRow.createCell(5);
        	bodyCell.setCellValue(empvo.getFk_deptnum());
        	
        	// 데이터 재직상태 표시
        	bodyCell = bodyRow.createCell(6);
        	bodyCell.setCellValue(empvo.getEmpstatus());

        	// 데이터 연봉 표시
        	bodyCell = bodyRow.createCell(7);
        	bodyCell.setCellStyle(moneyStyle);
        	bodyCell.setCellValue(empvo.getEmpsalary());
        	
        	// 데이터 입사일 표시
        	bodyCell = bodyRow.createCell(8);
        	bodyCell.setCellValue(empvo.getHiredate());

        	// 데이터 개인 이메일 표시 
        	bodyCell = bodyRow.createCell(8);
        	bodyCell.setCellValue(empvo.getNoticeemail());
        	
        	
        }// end of for------------------------------
        
        model.addAttribute("locale", Locale.KOREA);
        model.addAttribute("workbook", workbook);
        model.addAttribute("workbookName", subject);
        
        
		return "excelDownloadView";
		
	 }
	 
	 
	 
	 
	 
	 
	 
}
