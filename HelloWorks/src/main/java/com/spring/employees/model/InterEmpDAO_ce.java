package com.spring.employees.model;

import java.util.List;
import java.util.Map;

import com.spring.helloworks.model.EmpVO_KJH;

public interface InterEmpDAO_ce {

	// 당일기록조회해오기 
	int getRecordExist(Map<String, String> paraMap);

	//아이디로 사원번호 가져오기 
	//EmployeeVO_ce getEmployeeVO_ce(String loginuser);
	//아이디로 사원정보 가져오기 
	EmpVO_KJH getEmpVO_KJH(String fk_empno);

	// 출퇴근 테이블에서 레코드 가져오기
	AttendanceVO_ce getattendancevo(String fk_empno);

	// 지각유무확인
	int checklate(Map<String, String> paraMap);

	// 출퇴근테이블에 insert
	int intimeInsert(Map<String, String> paraMap);

	// 출퇴근테이블에 update
	int outtimeUpdate(Map<String, String> paraMap);

	// 야근테이블 insert
	void nightshiftInsert(Map<String, String> paraMap);

	//해당연도의 출근한 횟수 구해오기
	int getAttendanceCnt(String fk_empno);

	// 해당연도의 평일수 구해오기 
	int getWeekday(Map<String, String> paraMap);

	// 해당연도의 지각횟수 구해오기
	int getLateStatus(String fk_empno);

	// 해당연도의 조기퇴근 횟수 가져오기
	int getEarlyStaus(String fk_empno);

	// 해당연도의 퇴근미체크 횟수 가져오기
	int getouttimeNull(String fk_empno);

	// 캘린더에 표시할 근태정보(한달치) 받아오기 
	List<AttendanceVO_ce> getAttendanceList(String fk_empno);

	// 부서정보 가져오기 
	List<DepartmentVO_ce> getDepartmentList();

	// 캘린더에 표시할 부서별 근태정보 받아오기 
	List<AttendanceVO_ce> getDepattendanceList(String deptnum);

	// 부서별 사원번호 가져오기
	List<EmpVO_KJH> getDepEmpList(String deptnum);

	// 부서별 근태정보 가져오기
	AttendanceVO_ce getDepEmpAttendReport(Map<String, String> paraMap);

	// 해당월의 평일수 구해오기 
	int getWeekday_month(Map<String, String> paraMap);

	// 해당월의 퇴근미체크 횟수 가져오기
	int getouttimeNull_month(Map<String, String> paraMap);

	// 부서별 사원들 근태현황 엑셀다운받기
	List<AttendanceVO_ce> excelattendancevo(String fk_empno);

	// 사원정보 건수 조회
	int getTotalCount_ce(Map<String, Object> paraMap);

	// 검색, 페이징처리한 사원정보 조회
	List<EmpVO_LCE> empListSearchWithPaging_ce(Map<String, Object> paraMap);

	


}
