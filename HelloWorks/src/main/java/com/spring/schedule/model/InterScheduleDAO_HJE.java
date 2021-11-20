package com.spring.schedule.model;

import java.util.*;

public interface InterScheduleDAO_HJE {
	
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

	
}
