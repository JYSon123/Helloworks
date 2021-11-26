package com.spring.employees.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.spring.helloworks.model.EmpVO_KJH;

@Repository
public class EmpDAO_ce implements InterEmpDAO_ce {

	@Resource
	private SqlSessionTemplate sqlsession2;

	// 당일기록조회해오기 
	@Override
	public int getRecordExist(Map<String, String> paraMap) {
		int isRecordExist= sqlsession2.selectOne("leece.getRecordExist",paraMap);
		return isRecordExist;
	}
	
	//아이디로 사원번호 가져오기 
	@Override
	public EmpVO_KJH getEmpVO_KJH(String fk_empno) {
		EmpVO_KJH emp = sqlsession2.selectOne("leece.getEmpVO_KJH", fk_empno);
		return emp;
	}

	// 출퇴근 테이블에서 레코드 가져오기
	@Override
	public AttendanceVO_ce getattendancevo(String fk_empno) {
		AttendanceVO_ce attendancevo = sqlsession2.selectOne("leece.getattendancevo", fk_empno);
		return attendancevo;
	}

	// 지각유무확인
	@Override
	public int checklate(Map<String, String> paraMap) {
		int n = sqlsession2.selectOne("leece.checklate", paraMap);
		return n;
	}

	// 출퇴근테이블에 insert
	@Override
	public int intimeInsert(Map<String, String> paraMap) {
		int n = sqlsession2.insert("leece.intimeInsert", paraMap);
		return n;
	}

	// 출퇴근테이블에 update
	@Override
	public int outtimeUpdate(Map<String, String> paraMap) {
		int n = sqlsession2.update("leece.outtimeUpdate", paraMap);
		return n;
	}

	// 야근테이블 insert
	@Override
	public void nightshiftInsert(Map<String, String> paraMap) {
		sqlsession2.insert("leece.nightshiftInsert", paraMap);
	}

	//해당월의 출근한 횟수 구해오기
		@Override
		public int getAttendanceCnt(String fk_empno) {
			int attendaceCnt = sqlsession2.selectOne("leece.getAttendanceCnt", fk_empno);
			return attendaceCnt;
	}
	
	// 해당월의 평일수 구해오기 
	@Override
	public int getWeekday(Map<String, String> paraMap) {
		int weekdayCnt = sqlsession2.selectOne("leece.getWeekday", paraMap);
		return weekdayCnt;
	}

	// 해당월의 지각횟수 구해오기(조기퇴근이랑 합치면되는데..)
	@Override
	public int getLateStatus(String fk_empno) {
		int lateCnt = sqlsession2.selectOne("leece.getLateStatus", fk_empno);
		return lateCnt;
	}

	// 해당월의 조기퇴근횟수 구해오기
	@Override
	public int getEarlyStaus(String fk_empno) {
		int earlyCnt = sqlsession2.selectOne("leece.getEarlyStaus", fk_empno);
		return earlyCnt;
	}

	// 해당월의 퇴근미체크 횟수 가져오기
	@Override
	public int getouttimeNull(String fk_empno) {
		int outtimeNullCnt = sqlsession2.selectOne("leece.getouttimeNull", fk_empno);
		return outtimeNullCnt;
	}

	// 캘린더에 표시할 근태정보(한달치) 받아오기 
	@Override
	public List<AttendanceVO_ce> getAttendanceList(String fk_empno) {
		List<AttendanceVO_ce> attendanceList = sqlsession2.selectList("leece.getAttendanceList",fk_empno);
		return attendanceList;
	}

	// 부서정보 가져오기 
	@Override
	public List<DepartmentVO_ce> getDepartmentList() {
		
		List<DepartmentVO_ce> departmentList = sqlsession2.selectList("leece.getDepartmentList");
		return departmentList;
	}

	// 캘린더에 표시할 부서별 근태정보 받아오기 
	@Override
	public List<AttendanceVO_ce> getDepattendanceList(String deptnum) {
		List<AttendanceVO_ce> attendanceList = sqlsession2.selectList("leece.getDepattendanceList",deptnum);
		return attendanceList;
	}

	// 부서별 사원번호 가져오기
	@Override
	public List<EmpVO_KJH> getDepEmpList(String deptnum) {
		List<EmpVO_KJH> depEmpList = sqlsession2.selectList("leece.getDepEmpList",deptnum);
		return depEmpList;
	}

	// 부서별 사원근태정보 가져오기
	@Override
	public AttendanceVO_ce getDepEmpAttendReport(Map<String, String> paraMap) {
		AttendanceVO_ce attvo = sqlsession2.selectOne("leece.getDepEmpAttendReport",paraMap);
		return attvo;
	}

	// 해당월의 평일수 구해오기 
	@Override
	public int getWeekday_month(Map<String, String> paraMap) {
		int weekdayCnt = sqlsession2.selectOne("leece.getWeekdaymonth", paraMap);
		return weekdayCnt;
	}

	// 해당월의 퇴근미체크 횟수 가져오기
	@Override
	public int getouttimeNull_month(Map<String, String> paraMap) {
		int outtimeNullCnt = sqlsession2.selectOne("leece.getouttimeNull_month", paraMap);
		return outtimeNullCnt;
	}

	// 부서별 사원들 근태현황 엑셀다운받기
	@Override
	public List<AttendanceVO_ce> excelattendancevo(String fk_empno) {
		List<AttendanceVO_ce> attendanceList = sqlsession2.selectList("leece.excelattendancevo",fk_empno);
		return attendanceList;
	}

	// 사원정보 건수 조회
	@Override
	public int getTotalCount_ce(Map<String, Object> paraMap) {
		int totalCount = sqlsession2.selectOne("leece.getTotalCount_ce", paraMap);
		return totalCount;
	}

	// 검색, 페이징처리한 사원정보 조회
	@Override
	public List<EmpVO_LCE> empListSearchWithPaging_ce(Map<String, Object> paraMap) {
		List<EmpVO_LCE> empList = sqlsession2.selectList("leece.empListSearchWithPaging_ce",paraMap);
		return empList;
	}

	
	
	
	
}
