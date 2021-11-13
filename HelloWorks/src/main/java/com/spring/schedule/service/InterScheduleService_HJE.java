package com.spring.schedule.service;

import java.util.List;
import java.util.Map;

import com.spring.schedule.model.CalendarVO_HJE;

public interface InterScheduleService_HJE {

	// DB연결 테스트용 (이순신 select)
	List<String> getName();

	// 캘린더 추가하기
	int addCalendar(Map<String, String> paraMap);

	// 개인 캘린더 리스트 받아오기
	List<CalendarVO_HJE> showCalendarList(String empid);

	
}
