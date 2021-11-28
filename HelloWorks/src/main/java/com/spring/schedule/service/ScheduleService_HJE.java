package com.spring.schedule.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.spring.helloworks.common.GoogleMail_HJE;
import com.spring.helloworks.model.EmpVO_HJE;
import com.spring.schedule.model.CalendarVO_HJE;
import com.spring.schedule.model.InterScheduleDAO_HJE;

// 트랜잭션 처리 담당, DB담당
@Service
public class ScheduleService_HJE implements InterScheduleService_HJE {
	
	@Autowired
	private InterScheduleDAO_HJE dao; // 의존객체 주입
	
	@Autowired
    private GoogleMail_HJE mail;
	
	
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


	// 검색결과에 대한 총 일정 건수
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int count = dao.getTotalCount(paraMap);
		return count;
	}

	// 페이징 처리한 일정
	@Override
	public List<Map<String, String>> searchPagingSchedule(Map<String, String> paraMap) {
		List<Map<String, String>> pagingSchList = dao.searchPagingSchedule(paraMap);
		return pagingSchList;
	}

	
	// 
	@Override
//	@Scheduled(cron="0 * * * * *")
	@Scheduled(cron="0 0 12 * * *")
	public void reservationEmailSending() throws Exception {
		// !!! <주의> !!!
		// 스케줄러로 사용되어지는 메소드는 반드시 파라미터는 없어야 한다.!!!!!

		// == 현재 시각을 나타내기 ==
		Calendar currentDate = Calendar.getInstance(); // 현재날짜와 시간을 얻어온다.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = dateFormat.format(currentDate.getTime());

		System.out.println("현재시각 => " + currentTime);
		
		// ==== e메일을 발송할 회원 대상 알아오기
		List<Map<String,String>> emailSchList = dao.getEmailSchList();
		
		// *** e 메일 발송하기 *** //
		if( emailSchList != null && emailSchList.size() > 0 ) {
			
			for(int i =0; i<emailSchList.size(); i++) {
				
				// 공유대상에 포함된 직원의 이메일 알아오기
				
				String shareemp = emailSchList.get(i).get("shareemp");
				
//				if( shareemp != null && !"".equals(shareemp) ) {}
				String[] shareempArr = shareemp.split(",");
				
				Map<String, String[]> paraMap = new HashMap<>();
				paraMap.put("shareempArr", shareempArr);
				
				List<EmpVO_HJE> shareEmpEmailList = dao.getShareEmpEmail(paraMap);
				
				for(EmpVO_HJE evo : shareEmpEmailList) {
					
					if(evo != null && !"empty".equals(evo.getNoticeemail())) {
						String emailContents =  "<div style='width: 500px; height: 500px; border: solid 20px #a1c0d6; border-radius: 10px ;'>" + 
												"		<h2 style='text-align: center; background-color:#0070C1; color: #fff; width: 85%; margin: 40px auto; padding: 7px; border-radius: 10px;'>HelloWorks에 등록된 일정 정보</h2>" + 
												"		" + 
												"		<table style='margin: 0 auto; width:85%; border-collapse: collapse;'>" + 
												"			<tbody>" + 
												"				<tr style='border: solid 1px #e6e6e6; height: 40px;'>" + 
												"					<td style='width: 30%; border: solid 1px #e6e6e6; text-align: center;'>일정제목&nbsp;</td>" + 
												"					<td style='width: 70%; border: solid 1px #e6e6e6; padding-left: 10px;'>"+emailSchList.get(i).get("title")+"</td>" + 
												"				</tr>" + 
												"				<tr style='border: solid 1px #e6e6e6; height: 40px;'>" + 
												"					<td style='width: 30%; border: solid 1px #e6e6e6; text-align: center;'>시작&nbsp;</td>" + 
												"					<td style='width: 70%; border: solid 1px #e6e6e6; padding-left: 10px;'>"+emailSchList.get(i).get("startdate").substring(0,10)+"&nbsp;"+emailSchList.get(i).get("startdate").substring(11)+"</td>" + 
												"				</tr>" + 
												"				<tr style='border: solid 1px #e6e6e6; height: 40px;'>" + 
												"					<td style='width: 30%; border: solid 1px #e6e6e6; text-align: center;'>종료&nbsp;</td>" + 
												"					<td style='width: 70%; border: solid 1px #e6e6e6; padding-left: 10px;'>"+emailSchList.get(i).get("enddate").substring(0,10)+"&nbsp;"+emailSchList.get(i).get("enddate").substring(11)+"</td>" + 
												"				</tr>" + 
												"				<tr style='border: solid 1px #e6e6e6; height: 40px;'>" + 
												"					<td style='width: 30%; border: solid 1px #e6e6e6; text-align: center;'>장소&nbsp;</td>" + 
												"					<td style='width: 70%; border: solid 1px #e6e6e6; padding-left: 10px;'>"+emailSchList.get(i).get("location")+"</td>" + 
												"				</tr>" + 
												"				<tr style='border: solid 1px #e6e6e6; height: 40px;'>" + 
												"					<td style='width: 30%; border: solid 1px #e6e6e6; text-align: center;'>내용&nbsp;</td>" + 
												"					<td style='width: 70%; border: solid 1px #e6e6e6; padding-left: 10px;'>"+emailSchList.get(i).get("content")+"</td>" + 
												"				</tr>" + 
												"			</tbody>" + 
												"		</table>" + 
												"		<img src='http://localhost:9090/helloworks/resources/images/logo.jpg' width='70%' style='float: right; margin-top: 50px;'/>" + 
												"	</div>";
						mail.sendmail_Reservation(evo.getNoticeemail(), emailContents);
					}
				}
				
				
//				String emailContents = "사용자ID: " + reservationList.get(i).get("USERID")+"<br/> 예약자명: "+reservationList.get(i).get("NAME") + "님의 방문 예약일은 <span style='color: red;'>"+reservationList.get(i).get("RESERVATIONDATE")+"</span>";
				
			} // end of for
			
		}
		
		
	}

	// 캘린더명 중복체크
	@Override
	public int calnameDuplicateCheck(Map<String, String> paraMap) {
		int count = dao.calnameDuplicateCheck(paraMap);
		return count;
	}

	// 선택된 카테고리에 해당하는 일정만 보여주기 
	@Override
	public List<Map<String, String>> showChkCalList(Map<String, Object> paraMap) {
		List<Map<String, String>> chkCalList = dao.showChkCalList(paraMap);
		return chkCalList;
	}
	
	
}
