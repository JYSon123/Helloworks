package com.spring.schedule.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.schedule.model.CalendarVO_HJE;
import com.spring.schedule.model.InterScheduleDAO_HJE;
import com.spring.schedule.model.ScheduleVO_HJE;

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


	// 캘린더 리스트 받아오기
	@Override
	public List<CalendarVO_HJE> showCalendarList(String empid) {
		List<CalendarVO_HJE> calList = dao.showCalendarList(empid);
		return calList;
	}


	// 일정 출력하기
	@Override
	public List<Map<String, String>> showSchedule(String empid) {
		List<Map<String, String>> schList = dao.showSchedule(empid);
		return schList;
	}


	// 일정 추가하기
	@Override
	public void addSchedule(Map<String, String> paraMap) {
		dao.addSchedule(paraMap);
	}

	// 개인 캘린더 수정하기
	@Override
	public void updatePersonal(Map<String, String> paraMap) {
		dao.updatePersonal(paraMap);
	}

	// 개인 캘린더 삭제하기
	@Override
	public void deletePersonal(Map<String, String> paraMap) {
		dao.deletePersonal(paraMap);
	}

	// 공유 캘린더 수정하기
	@Override
	public void updateShare(Map<String, String> paraMap) {
		dao.updateShare(paraMap);
		
	}

	// 공유 캘린더 삭제하기
	@Override
	public void deleteShare(Map<String, String> paraMap) {
		dao.deleteShare(paraMap);
	}


	// 일정 검색
	@Override
	public List<Map<String, String>> searchSchedule(Map<String, String> paraMap) {
		List<Map<String, String>> searchSchList = dao.searchSchedule(paraMap);
		return searchSchList;
	}

	// 자동완성
	@Override
	public List<String> autoSearchWord(Map<String, String> paraMap) {
		List<String> wordList = dao.autoSearchWord(paraMap);
		return wordList;
	}

	// 일정 수정하기
	@Override
	public void updateSchedule(Map<String, String> paraMap) {
		dao.updateSchedule(paraMap);
	}

	// 일정 삭제하기
	@Override
	public void deleteSchedule(Map<String, String> paraMap) {
		dao.deleteSchedule(paraMap);
	}


	@Override
	public List<Map<String, String>> searchShareEmp(String employee) {
		List<Map<String, String>> empList = dao.searchShareEmp(employee);
		return empList;
	}

	

	
	
}
