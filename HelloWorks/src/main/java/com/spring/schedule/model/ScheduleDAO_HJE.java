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


	// 캘린더 리스트 받아오기
	@Override
	public List<CalendarVO_HJE> showCalendarList(String empid) {
		List<CalendarVO_HJE> calList = sqlsession2.selectList("hje.showCalendarList",empid);
		return calList;
	}


	// 일정 출력하기
	@Override
	public List<Map<String, String>> showSchedule(String empid) {
		List<Map<String, String>> schList = sqlsession2.selectList("hje.showSchedule", empid);
		return schList;
	}


	// 일정 추가하기
	@Override
	public void addSchedule(Map<String, String> paraMap) {
		sqlsession2.insert("hje.addSchedule",paraMap);
		
	}

	// 개인 캘린더 수정하기
	@Override
	public void updatePersonal(Map<String, String> paraMap) {
		sqlsession2.update("hje.updatePersonal",paraMap);
		
	}

	// 개인 캘린더 삭제하기
	@Override
	public void deletePersonal(Map<String, String> paraMap) {
		sqlsession2.delete("hje.deletePersonal",paraMap);
		
	}

	// 공유 캘린더 수정하기
	@Override
	public void updateShare(Map<String, String> paraMap) {
		sqlsession2.update("hje.updateShare",paraMap);
		
	}

	// 공유 캘린더 삭제하기
	@Override
	public void deleteShare(Map<String, String> paraMap) {
		sqlsession2.delete("hje.deleteShare",paraMap);
		
	}



	
	
}
