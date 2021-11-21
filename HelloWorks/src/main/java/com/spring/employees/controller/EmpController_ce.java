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
			 mav.setViewName("attendance/attendanceOnlyHR.tiles1"); 
		 }
		 // 인사부인 경우 페이지 정상 넘김
		 return mav;
	 }
	 */
	 
	 //기능테스트용 
	 // 출퇴근관리페이지 보여주기 (인사부만 접근가능)
	 @RequestMapping(value="/emp/viewAttendOnlyHR.hello2")
	 public ModelAndView viewAttendOnlyHR(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		 List<DepartmentVO_ce> departmentList = service.getDepartmentList();
		 
		 mav.addObject("departmentList", departmentList);
		 mav.setViewName("attendance/attendanceOnlyHR.tiles1"); 
		 
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
	 @ResponseBody
	 @RequestMapping(value="/emp/downloadEmpExcelFile.hello2", produces="text/plain;charset=UTF-8")
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
		String subject = "" + empname + "사원의 근태정보";
		SXSSFSheet sheet = workbook.createSheet(subject);
		
		// 시트 열 너비 설정 (7개의 열)
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 2000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 2000);
		sheet.setColumnWidth(6, 1500);
		
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
	    mergeRowStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex()); // IndexedColors.DARK_BLUE.getIndex() 는 색상(남색)의 인덱스값을 리턴시켜준다.
	    mergeRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex()); // IndexedColors.LIGHT_YELLOW.getIndex() 는 연한노랑의 인덱스값을 리턴시켜준다.
	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    // Cell 폰트(Font) 설정하기
        // 폰트 적용을 위해 POI 라이브러리의 Font 객체를 생성해준다.
        // 해당 객체의 세터를 사용해 폰트를 설정해준다. 대표적으로 글씨체, 크기, 색상, 굵기만 설정한다.
        // 이후 CellStyle의 setFont 메소드를 사용해 인자로 폰트를 넣어준다.
	    Font mergeRowFont = workbook.createFont(); // import org.apache.poi.ss.usermodel.Font; 으로 한다.
	    mergeRowFont.setFontName("나눔고딕");
	    mergeRowFont.setFontHeight((short)500);
	    mergeRowFont.setColor(IndexedColors.WHITE.getIndex());
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
	    for(int i=0; i<8; i++) {
	    	Cell cell = mergeRow.createCell(i);
	    	cell.setCellStyle(mergeRowStyle);
	    	cell.setCellValue(subject);
	    }
	    
	    // 셀 병합하기 
	    sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0 , 7));
	    
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
	 
	 
}
