package com.spring.mail.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.spring.helloworks.model.EmpVO_KJH;
import com.spring.helloworks.model.InterMailDAO_JDH;
import com.spring.helloworks.model.MailVO_JDH;
import com.spring.helloworks.util.FileManager;

@Service
public class MailService_JDH implements InterMailService_JDH {

	@Autowired
	private InterMailDAO_JDH mdao;
	
	@Autowired // Type에 따라 알아서 Bean 을 주입해준다.
	private FileManager fileManager;

	// 메일 갯수 알아오기
	@Override
	public int getTotalCount(Map<String, String> paraMap) {

		int n = mdao.getTotalCount(paraMap);
		
		return n;
	}

	// 메일 페이징 목록 가져오기
	@Override
	public List<MailVO_JDH> mailListSearchWithPaging(Map<String, String> paraMap) {
		
		List<MailVO_JDH> mailList = mdao.mailListSearchWithPaging(paraMap);
		
		return mailList;
	}

	// 메일 보내기 (파일 X)
	@Override
	public int send(MailVO_JDH mailvo) {
		
		int n = mdao.send(mailvo);
		
		return n;
	}

	// 메일 보내기 (파일O)
	@Override
	public int send_withFile(MailVO_JDH mailvo) {
		
		int n = mdao.send_withFile(mailvo);
		
		return n;
	}
	
	// 검색 보여주기
	@Override
	public List<Map<String,String>> recidSearchShow(Map<String, String> paraMap) {
		
		List<Map<String,String>> recidList = mdao.recidSearchShow(paraMap);
		return recidList;
	}

	// 메일 조회
	@Override
	public MailVO_JDH getViewMail(Map<String, String> paraMap) {

		MailVO_JDH mailvo = mdao.getViewMail(paraMap); 
		
		return mailvo;
	}

	// 메일 읽음상태 변경
	@Override
	public int updateReadStatus(MailVO_JDH mailvo) {
		
		int n  = mdao.updateReadStatus(mailvo);
		return n;
	}

	// 보낸 메일함
	@Override
	public List<MailVO_JDH> sendmailListSearchWithPaging(Map<String, String> paraMap) {
		
		List<MailVO_JDH> sendmailListSearchWithPaging = mdao.sendmailListSearchWithPaging(paraMap);
		
		return sendmailListSearchWithPaging;
	}

	// 메일 삭제
	@Override
	public int delete(Map<String, Object> paraMap) {
		
		int n = mdao.delete(paraMap);
		
		return n;
	}

	// 휴지통
	@Override
	public List<MailVO_JDH> trashmailListSearchWithPaging(Map<String, String> paraMap) {
		List<MailVO_JDH> trashmailListSearchWithPaging = mdao.trashmailListSearchWithPaging(paraMap);
		
		return trashmailListSearchWithPaging;
	}

	// 메일 복구
	@Override
	public int revive(Map<String, Object> paraMap) {
		int n = mdao.revive(paraMap);
				
		return n;
	}

	// 휴지통 메일 삭제
	@Override
	public int trashdelete(Map<String, Object> paraMap) {
		
		int n = mdao.trashdelete(paraMap);
		
		return n;
	}

	// 보낸 메일 삭제
	@Override
	public int deleteSendMail(Map<String, Object> paraMap) {
		int n = mdao.deleteSendMail(paraMap);
		
		return n;
	}

	// 스케줄러 사용한 진짜 메일데이터 삭제(매일 9시마다)
	@Override
	@Scheduled(cron="0 0 9 * * *")
	public void realMailDelete() {
		int n = mdao.realMailDelete();		
	}

	// 직원 정보 구하기
	@Override
	public Map<String, String> searchEmp(String empid) {
		Map<String, String> searchEmp = mdao.searchEmp(empid);
		return searchEmp;
	}

	

	
	
		
	
	
}
