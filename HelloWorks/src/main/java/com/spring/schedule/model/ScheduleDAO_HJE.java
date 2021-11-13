package com.spring.schedule.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDAO_HJE implements InterScheduleDAO_HJE {
	
	@Resource
	private SqlSessionTemplate sqlsession2;
	
	
	// DB연결 테스트용 (이순신, 엄정화 select)
	@Override
	public List<String> getName() {
		
		List<String> nameList =  sqlsession2.selectList("hje.getName");
		
		return nameList;
	}


	// 캘린더 추가하기
	@Override
	public int addCalendar(Map<String, String> paraMap) {
		
		int n = sqlsession2.insert("hje.addCalendar",paraMap);
		return n;
	}


	// 개인 캘린더 리스트 받아오기
	@Override
	public List<CalendarVO_HJE> showCalendarList(String empid) {
		List<CalendarVO_HJE> personalList = sqlsession2.selectList("hje.showCalendarList",empid);
		return personalList;
	}

	
	
}
