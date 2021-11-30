package com.spring.mail.service;

import java.util.List;
import java.util.Map;

import com.spring.helloworks.model.EmpVO_KJH;
import com.spring.helloworks.model.MailVO_JDH;

public interface InterMailService_JDH {

	// 메일 갯수 알아오기
	int getTotalCount(Map<String, String> paraMap);

	// 메일 페이징 목록 가져오기
	List<MailVO_JDH> mailListSearchWithPaging(Map<String, String> paraMap);

	// 메일 보내기(파일X)
	int send(MailVO_JDH mailvo);
	
	// 메일 보내기(파일O)
	int send_withFile(MailVO_JDH mailvo);

	// 자동 검색기 만들기
	List<Map<String,String>> recidSearchShow(Map<String, String> paraMap);

	// 메일 조회
	MailVO_JDH getViewMail(Map<String, String> paraMap);

	// 메일 읽음 상태 변경
	int updateReadStatus(MailVO_JDH mailvo);

	// 보낸메일 페이징 목록 가져오기
	List<MailVO_JDH> sendmailListSearchWithPaging(Map<String, String> paraMap);

	// 메일 삭제
	int delete(Map<String, Object> paraMap);
	
	// 휴지통
	List<MailVO_JDH> trashmailListSearchWithPaging(Map<String, String> paraMap);

	// 휴지통에서 메일 복구
	int revive(Map<String, Object> paraMap);

	// 휴지통 메일 삭제
	int trashdelete(Map<String, Object> paraMap);

	// 보낸 메일 삭제
	int deleteSendMail(Map<String, Object> paraMap);

	// Spring Scheduler로  서로에게 삭제된 메일데이터 진짜로 삭제 ===	   
	void realMailDelete();

	// 직원 정보 불러오기
	Map<String, String> searchEmp(String empid);

	
	

	

}
