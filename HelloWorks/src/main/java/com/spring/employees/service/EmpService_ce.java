package com.spring.employees.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.employees.model.AttendanceVO_ce;
import com.spring.employees.model.DepartmentVO_ce;
import com.spring.employees.model.EmployeeVO_ce;
import com.spring.employees.model.InterEmpDAO_ce;
import com.spring.helloworks.model.EmpVO_KJH;

//트랜잭션처리담당

@Service
public class EmpService_ce implements InterEmpService_ce {

	@Autowired
	private InterEmpDAO_ce dao;

	// 당일기록조회해오기 
	@Override
	public int getRecordExist(Map<String, String> paraMap) {
	
		// 트랜잭션일지는 모르지만 먼저 아이디로 전체 employeeVO가져옴
		// 거기서 사원번호 추출
		// 그 사원번호로 당일기록조회 또는 당월근태관련횟수조회해오기 (두개는 service단 따로 만들어야하나 그러면 employeeVO가져오는 것도 따로해야함)
		
		int isRecordExist = dao.getRecordExist(paraMap);
		return isRecordExist;
	}
	
	// 아이디로 사원번호 가져오기 
	/*
	@Override
	public EmployeeVO_ce getEmployeeVO_ce(String loginuser) {
		EmployeeVO_ce emp = dao.getEmployeeVO_ce(loginuser);
		return emp;
	}
	*/

	// 출퇴근 테이블에서 레코드 가져오기
	@Override
	public AttendanceVO_ce getattendancevo(String fk_empno) {
		AttendanceVO_ce attendancevo = dao.getattendancevo(fk_empno);
		return attendancevo;
	}

	// 출근버튼 클릭 : 지각처리 -> 출퇴근테이블에 insert -> 출근시간 가져오기
	@Override
	public String clickCheckin(Map<String, String> paraMap) {
		
		String status_latein; // 지각유무
		String fk_empno;
		String intime = ""; // 출근시간 
		
		int n = dao.checklate(paraMap); // 지각유무 확인
		
		if( n > 0 ) { // 지각
			status_latein = "1";
		}
		else {  // 정상출근
			status_latein = "0";
		}
		paraMap.put("status_latein", status_latein);

		n = dao.intimeInsert(paraMap); // 출퇴근테이블에 insert

		fk_empno = paraMap.get("fk_empno");
		
		if(n == 1) {
			intime = dao.getattendancevo(fk_empno).getIntime();
		}
		
		return intime;
		
	}

	
	// 트랜잭션 처리 필요
	// 퇴근버튼 클릭 : 시간알아오기 -> 출퇴근테이블에 update(야근테이블에 insert) -> 퇴근시간 가져오기
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public AttendanceVO_ce clickCheckout(Map<String, String> paraMap) {
		
		
		String onlytime = paraMap.get("onlytime");
		
		// 조기퇴근, 야근유무 확인 
		String status_earlyout; // 조기퇴근유무확인
		int n = 0;
		
		SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm:ss");
		
		 try {
			Date standardTime = sdformat.parse("18:00:00"); // 조기퇴근 기준시간
			Date standard_overTime = sdformat.parse("18:30:00"); // 야근 기준시간
			Date nowTime = sdformat.parse(onlytime); // 퇴근시간
			
			if( !nowTime.after(standardTime) ) { // 조기퇴근O				
				status_earlyout = "1";
				paraMap.put("status_earlyout", status_earlyout);
				
				// 출퇴근테이블update
				n = dao.outtimeUpdate(paraMap);
			}
			else { // 조기퇴근X 
				
				status_earlyout = "0";
				paraMap.put("status_earlyout", status_earlyout);
				
				// 출퇴근테이블update
				n = dao.outtimeUpdate(paraMap);
				
				if( nowTime.after(standard_overTime) ) { // 야근 후 퇴근
					if( n == 1) {
						// 야근테이블 insert	
						long addTime = (nowTime.getTime() - standard_overTime.getTime()) / ( 60*1000);
						paraMap.put("addTime", String.valueOf(addTime));
						
						dao.nightshiftInsert(paraMap);
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 
		String fk_empno = paraMap.get("fk_empno");
		 
		// 퇴근시간알아오기 (vo전체로 받아오기)
		AttendanceVO_ce attendancevo = dao.getattendancevo(fk_empno);
		
		return attendancevo;
	}

	// 근태관련값 가져오기 
	@Override
	public Map<String, String> getAttendStatus(String fk_empno) {
		
		Map<String,String> paraMap = new HashMap<>();
		String sameYear = "";
		String hiredateYear = "";
		
		// 현재날짜와 시간 
		Calendar currentDate = Calendar.getInstance(); 
		int year = currentDate.get(Calendar.YEAR);
		
		
		// 해당사원의 입사년도 가져오기 
		EmpVO_KJH emp = dao.getEmpVO_KJH(fk_empno); // 나중에 지희님 VO로 바꾸기 
		
		String hiredate = emp.getHiredate();
		//System.out.println("확인용 hiredate : " + hiredate);
		SimpleDateFormat sdf = new SimpleDateFormat("yy/mm/dd");
		
		
		try {
			Date hiredateDate = sdf.parse(hiredate);
			hiredateYear = String.format("%tY",hiredateDate);
			//System.out.println("확인용 hiredateYear:" + hiredateYear); //확인용 hiredateYear:2021
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 입사년도와 이번년도가 일치하는지 확인 
		if( String.valueOf(year).equals(hiredateYear)) {
			sameYear = "true";
		}
		else {
			sameYear = "false";
		}
		//System.out.println("확인용 sameYear : " + sameYear);
		
		paraMap.put("sameYear",sameYear); // 입사년도 같은지
		paraMap.put("hiredate",hiredate); // 입사년도
		
		//해당연도의 출근한 횟수 구해오기 
		int attendanceCnt = dao.getAttendanceCnt(fk_empno);
		
		// 해당연도의 평일수 구해오기 
		int weekdayCnt = dao.getWeekday(paraMap);
		//System.out.println("확인용 weekdayCnt" + weekdayCnt);
		
		// 해당연도의 지각횟수 구해오기
		int lateCnt = dao.getLateStatus(fk_empno);
		paraMap.put("status_latein", String.valueOf(lateCnt));
		
		// 해당연도의 조기퇴근 횟수 가져오기
		int earlyCnt = dao.getEarlyStaus(fk_empno);
		paraMap.put("status_earlyout", String.valueOf(earlyCnt));
		
		// 해당연도의 퇴근미체크 횟수 가져오기
		int outtimeNullCnt = dao.getouttimeNull(fk_empno);
		paraMap.put("outtimeNullCnt", String.valueOf(outtimeNullCnt));
		
		// 해당연도의 결근횟수 
		int absenceCnt = weekdayCnt - attendanceCnt;
		paraMap.put("absenceCnt", String.valueOf(absenceCnt));
		
		return paraMap;
	}

	
	// 캘린더에 표시할 근태정보(한달치) 받아오기 
	@Override
	public List<AttendanceVO_ce> getAttendanceList(String fk_empno) {
		
		// 간단하게 받아오기 
		List<AttendanceVO_ce> attendanceList = dao.getAttendanceList(fk_empno);
		
		return attendanceList;
	}

	// 부서정보 가져오기 
	@Override
	public List<DepartmentVO_ce> getDepartmentList() {
		
		List<DepartmentVO_ce> departmentList = dao.getDepartmentList();
		return departmentList;
	}

	// 캘린더에 표시할 부서별 근태정보 받아오기 
	@Override
	public List<AttendanceVO_ce> getDepattendanceList(String deptnum) {
		List<AttendanceVO_ce> attendanceList = dao.getDepattendanceList(deptnum);
		return attendanceList;
	}

	// 부서별 사원들 사원번호 가져오기
	@Override
	public List<EmpVO_KJH> getDepEmpList(String deptnum) {
		List<EmpVO_KJH> depEmpList = dao.getDepEmpList(deptnum);
		return depEmpList;
	}

	// 부서별 사원근태정보 가져오기
	@Override
	public AttendanceVO_ce getDepEmpAttendReport(Map<String, String> paraMap) {
		AttendanceVO_ce attvo = dao.getDepEmpAttendReport(paraMap);
		return attvo;
	}

	// 퇴근미체크, 결근 횟수 가져오기 
	public Map<String, Object> getDepEmpReport(Map<String,String> paraMap) {
		
		Map<String, Object> empMap = new HashMap<>();
		
		String fk_empno = paraMap.get("fk_empno");
		String hiredate = paraMap.get("hiredate");
		String searchDay = paraMap.get("searchDate").replace("-","") + "01"; //yyyymm01형태로 보정 
		
		paraMap.put("searchDay", searchDay); // 보정된 날짜 넣기
		
		/*
		// 현재날짜와 시간 
		Calendar currentDate = Calendar.getInstance(); 
		
		int year = currentDate.get(Calendar.YEAR);
		int month = currentDate.get(Calendar.MONTH)+1; // 1 ~ 12
	    String strMonth = (month<10)?"0"+month:String.valueOf(month);
		
		String yearandMonth =   String.valueOf(year) + "/" + strMonth;
		//System.out.println("yearandMonth =>" + yearandMonth);
		
		String hireYearMonth = hiredate.substring( 0, hiredate.lastIndexOf("/") );
		//System.out.println("hireYearMonth =>" + hireYearMonth);
	   */
		
		String yearandMonth = paraMap.get("searchDate").replace("-","/");  //2021/11 형태
		//System.out.println("yearandMonth =>" + yearandMonth);
		
		String hireYearMonth = hiredate.substring( 0, hiredate.lastIndexOf("/") );
		//System.out.println("hireYearMonth =>" + hireYearMonth);
		
		
		// 이번달에 입사했는지 확인 
	    String same = "";
		if( yearandMonth.equals(hireYearMonth)) {
			same = "true";
		}
		else {
			same = "false";
		}
		//System.out.println("확인용 same: " + same);
		paraMap.put("same",same); // 입사년도 같은지
		
		// 해당월의 평일수 구해오기 
		int weekdayCnt = dao.getWeekday_month(paraMap); // 조회할 월도 같이 들어가야함
		//System.out.println("확인용 weekdayCnt" + weekdayCnt);
		
		// 해당월의 퇴근미체크 횟수 가져오기
		int outtimeNullCnt = dao.getouttimeNull_month(paraMap); // 조회할 월도 같이 들어가야함  
		empMap.put("outtimeNullCnt", String.valueOf(outtimeNullCnt));
		
		// 해당월의 결근횟수 
		int absenceCnt = weekdayCnt - Integer.parseInt(paraMap.get("workday"));
		empMap.put("absenceCnt", String.valueOf(absenceCnt));
		//System.out.println("확인용 absenceCnt" + absenceCnt);
		
		return empMap;
	}

	// 부서별 사원들 근태현황 엑셀다운받기
	@Override
	public List<AttendanceVO_ce> excelattendancevo(String fk_empno) {
		
		List<AttendanceVO_ce> attendanceList = dao.excelattendancevo(fk_empno);
		
		return attendanceList;
	}
	
	
}
