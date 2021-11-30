package com.spring.helloworks.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MailDAO_JDH implements InterMailDAO_JDH {
	
	@Resource
	private SqlSessionTemplate sqlsession2;

	// 메일 갯수 알아오기
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		
		int n = sqlsession2.selectOne("mail.getTotalCount", paraMap);
		
		return n;
	}

	// 받은메일함 가져오기
	@Override
	public List<MailVO_JDH> mailListSearchWithPaging(Map<String, String> paraMap) {
		List<MailVO_JDH> mailList = sqlsession2.selectList("mail.mailListSearchWithPaging", paraMap);
		return mailList;
	}

	// 메일쓰기(파일 X)
	@Override
	public int send(MailVO_JDH mailvo) {

		int n = sqlsession2.insert("mail.send", mailvo);
		
		return n;
	}
	
	// 메일쓰기(파일O)
	@Override
	public int send_withFile(MailVO_JDH mailvo) {

		int n = sqlsession2.insert("mail.send_withFile", mailvo);
		return n;
	}

	// 자동검색기 만들기
	@Override
	public List<Map<String,String>> recidSearchShow(Map<String, String> paraMap) {
		
		List<Map<String,String>> recidList = sqlsession2.selectList("mail.recidSearchShow", paraMap);
		
		return recidList;
	}

	// 메일 조회
	@Override
	public MailVO_JDH getViewMail(Map<String, String> paraMap) {
		
		MailVO_JDH mailvo = sqlsession2.selectOne("mail.getViewMail",paraMap);
		
		return mailvo;
	}

	// 메일 읽음상태 변경
	@Override
	public int updateReadStatus(MailVO_JDH mailvo) {
		
		int n = sqlsession2.update("mail.updateReadStatus",mailvo);
		
		return 0;
	}

	// 보낸메일함
	@Override
	public List<MailVO_JDH> sendmailListSearchWithPaging(Map<String, String> paraMap) {
		List<MailVO_JDH> sendmailList = sqlsession2.selectList("mail.sendmailListSearchWithPaging", paraMap);
		return sendmailList;
	}

	// 메일 삭제
	@Override
	public int delete(Map<String, Object> paraMap) {
		
		int n = sqlsession2.update("mail.delete", paraMap);
		
		return n;
	}

	// 휴지통
	@Override
	public List<MailVO_JDH> trashmailListSearchWithPaging(Map<String, String> paraMap) {
		List<MailVO_JDH> trashmailList = sqlsession2.selectList("mail.trashmailListSearchWithPaging", paraMap);
		return trashmailList;		
	}

	// 휴지통에서 메일 복구
	@Override
	public int revive(Map<String, Object> paraMap) {
		int n = sqlsession2.update("mail.revive", paraMap);
		return n;
	}

	// 휴지통에서 삭제
	@Override
	public int trashdelete(Map<String, Object> paraMap) {
		int n = sqlsession2.update("mail.trashdelete", paraMap);
		return n;
	}

	// 보낸메일 삭제
	@Override
	public int deleteSendMail(Map<String, Object> paraMap) {
		int n = sqlsession2.update("mail.deleteSendMail", paraMap);
		return n;
	}

	@Override
	public int realMailDelete() {
		int n = sqlsession2.delete("mail.realMailDelete");
		return n;
	}

	// 직원 정보 구하기
	@Override
	public Map<String, String> searchEmp(String empid) {
		Map<String, String> searchEmp = sqlsession2.selectOne("mail.searchEmp", empid);
		return searchEmp;
	}

	
	
	
	
}
