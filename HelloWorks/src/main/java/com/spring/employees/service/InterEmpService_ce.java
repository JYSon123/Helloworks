package com.spring.employees.service;

import java.util.List;
import java.util.Map;

import com.spring.employees.model.AttendanceVO_ce;
import com.spring.employees.model.DepartmentVO_ce;
import com.spring.employees.model.EmployeeVO_ce;
import com.spring.helloworks.model.EmpVO_KJH;

public interface InterEmpService_ce {

	// 당일기록조회해오기 
	int getRecordExist(Map<String, String> paraMap);

	// 아이디로 사원번호 가져오기 
	//EmployeeVO_ce getEmployeeVO_ce(String loginuser);

	// 출퇴근 테이블에서 레코드 가져오기
	AttendanceVO_ce getattendancevo(String fk_empno);

	// 출근버튼 클릭 : 지각처리 -> 출퇴근테이블에 insert -> 출근시간 가져오기
	String clickCheckin(Map<String, String> paraMap);

	// 퇴근버튼 클릭 : 시간알아오기 -> 출퇴근테이블에 update(야근테이블에 insert) -> 퇴근시간 가져오기
	AttendanceVO_ce clickCheckout(Map<String, String> paraMap);

	// 근태관련값 가져오기 
	Map<String, String> getAttendStatus(String fk_empno);

	// 캘린더에 표시할 근태정보 받아오기 
	List<AttendanceVO_ce> getAttendanceList(String fk_empno);

	// 부서정보 가져오기 
	List<DepartmentVO_ce> getDepartmentList();

	// 캘린더에 표시할 부서별 근태정보 받아오기 
	List<AttendanceVO_ce> getDepattendanceList(String deptnum);

	// 부서별 사원번호 가져오기
	List<EmpVO_KJH> getDepEmpList(String deptnum);

	// 부서별 사원근태정보 가져오기
	AttendanceVO_ce getDepEmpAttendReport(Map<String, String> paraMap);

	// 퇴근미체크, 결근 횟수 가져오기 
	Map<String, Object> getDepEmpReport(Map<String,String> paraMap);

	// 부서별 사원들 근태현황 엑셀다운받기
	List<AttendanceVO_ce> excelattendancevo(String fk_empno);

	
}
