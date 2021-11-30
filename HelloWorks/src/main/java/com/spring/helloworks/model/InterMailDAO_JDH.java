package com.spring.helloworks.model;

import java.util.List;
import java.util.Map;

public interface InterMailDAO_JDH {

	// 메일 갯수 알아오기
	int getTotalCount(Map<String, String> paraMap);

	// 메일 페이징 목록 가져오기
	List<MailVO_JDH> mailListSearchWithPaging(Map<String, String> paraMap);

	// 메일 쓰기 (파일 X)
	int send(MailVO_JDH mailvo);

	// 메일쓰기(파일O)
	int send_withFile(MailVO_JDH mailvo);

	// 자동검색기 만들기
	List<Map<String,String>> recidSearchShow(Map<String, String> paraMap);

	// 메일 조회
	MailVO_JDH getViewMail(Map<String, String> paraMap);

	// 메일 읽음상태 변경
	int updateReadStatus(MailVO_JDH mailvo);

	// 보낸 메일 페이징 목록 가져오기
	List<MailVO_JDH> sendmailListSearchWithPaging(Map<String, String> paraMap);

	// 메일삭제
	int delete(Map<String, Object> paraMap);

	// 휴지통
	List<MailVO_JDH> trashmailListSearchWithPaging(Map<String, String> paraMap);

	// 휴지통에서 메일 복구
	int revive(Map<String, Object> paraMap);

	// 휴지통 메일 삭제
	int trashdelete(Map<String, Object> paraMap);

	// 보낸 메일 삭제
	int deleteSendMail(Map<String, Object> paraMap);

	// 스케줄러를 이용한 메일 데이터 삭제
	int realMailDelete();

	// 직원 정보 구하기
	Map<String, String> searchEmp(String empid);

	

	
}
