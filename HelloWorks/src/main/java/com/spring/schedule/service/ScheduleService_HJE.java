package com.spring.schedule.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.schedule.model.CalendarVO_HJE;
import com.spring.schedule.model.InterScheduleDAO_HJE;

// 트랜잭션 처리 담당, DB담당
@Service
public class ScheduleService_HJE implements InterScheduleService_HJE {
	
	@Autowired
	private InterScheduleDAO_HJE dao; // 의존객체 주입
	
	
	// DB연결 테스트용 (이순신 select)
	@Override
	public List<String> getName() {
		
		List<String> nameList = dao.getName();
		
		return nameList;
	}


	// 캘린더 추가하기
	@Override
	public int addCalendar(Map<String, String> paraMap) {
		int n = dao.addCalendar(paraMap);
		return n;
	}


	// 개인 캘린더 리스트 받아오기
	@Override
	public List<CalendarVO_HJE> showCalendarList(String empid) {
		List<CalendarVO_HJE> personalList = dao.showCalendarList(empid);
		return personalList;
	}

	
	
}
