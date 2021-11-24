package com.spring.schedule.service;

import java.util.List;
import java.util.Map;

import com.spring.schedule.model.CalendarVO_HJE;
import com.spring.schedule.model.ScheduleVO_HJE;

public interface InterScheduleService_HJE {

	// DB연결 테스트용 (이순신 select)
	List<String> getName();

	// 캘린더 추가하기
	int addCalendar(Map<String, String> paraMap);

	// 캘린더 리스트 받아오기
	List<CalendarVO_HJE> showCalendarList(String empid);

	// 일정 출력하기
	List<Map<String, String>> showSchedule(String empid);

	// 일정 추가하기
	void addSchedule(Map<String, String> paraMap);

	// 개인 캘린더 수정하기
	void updatePersonal(Map<String, String> paraMap);

	// 개인 캘린더 삭제하기
	void deletePersonal(Map<String, String> paraMap);

	// 공유 캘린더 수정하기
	void updateShare(Map<String, String> paraMap);

	// 공유 캘린더 삭제하기
	void deleteShare(Map<String, String> paraMap);

	// 일정 검색
	List<Map<String, String>> searchSchedule(Map<String, String> paraMap);

	// 자동완성
	List<String> autoSearchWord(Map<String, String> paraMap);

	// 일정 수정하기
	void updateSchedule(Map<String, String> paraMap);

	// 일정 삭제하기
	void deleteSchedule(Map<String, String> paraMap);

	// 대상인원에 해당하는 직원찾기
	List<Map<String, String>> searchShareEmp(String employee);

	// 검색결과에 대한 총 일정 건수
	int getTotalCount(Map<String, String> paraMap);

	// 페이징 처리한 일정
	List<Map<String, String>> searchPagingSchedule(Map<String, String> paraMap);
	
	// 스프링스케줄러로 알림메일 보내기
	void reservationEmailSending() throws Exception;

	// 캘린더명 중복체크
	int calnameDuplicateCheck(Map<String, String> paraMap);

	


	
}
