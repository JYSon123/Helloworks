package com.spring.helloworks.service_JDH;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.helloworks.common_KJH.FileManager;
import com.spring.helloworks.model.EmpVO_KJH;
import com.spring.helloworks.model_JDH.AccountingVO_JDH;
import com.spring.helloworks.model_JDH.CardVO_JDH;
import com.spring.helloworks.model_JDH.InterAccountDAO_JDH;


@Service
public class AccountService_JDH implements InterAccountService_JDH {
	
	@Autowired
	private InterAccountDAO_JDH dao;
	
	@Autowired // Type에 따라 알아서 Bean 을 주입해준다.
	private FileManager fileManager;

	// 직원 계좌 갯수 가져오기
	@Override
	public int getAccountCount(Map<String, String> paraMap) {
		int n = dao.getAccountCount(paraMap);
		return n;
	}

	// 직원 계좌 페이징 목록 가져오기
	@Override
	public List<AccountingVO_JDH> accountingListWithPaging(Map<String, String> paraMap) {
		List<AccountingVO_JDH> accountingList = dao.accountingListWithPaging(paraMap);
		// System.out.println("어카운트사이즈 서비스" + accountingList.size());
		return accountingList;
	}

	// 계좌 삭제
	@Override
	public int deleteAccount(Map<String, Object> paraMap) {
		int n = dao.deleteAccount(paraMap);
		return n;
	}

	// 계좌 추가
	@Override
	public int addAccount(Map<String, String> paraMap) {
		int n = dao.addAccount(paraMap);
		return n;
	}

	
	// 직원리스트
	@Override
	public List<Map<String, String>> empList() {
		List<Map<String, String>> empList = dao.empList();
		return empList;
	}

	// 계좌 수정
	@Override
	public int editAccount(Map<String, String> paraMap) {
		int n = dao.editAccount(paraMap);
		return n;
	}

	// 카드 리스트
	@Override
	public List<CardVO_JDH> cardList(Map<String, String> paraMap) {
		List<CardVO_JDH> cardList = dao.cardList(paraMap);
		return cardList;
	}

	// 카드 등록
	@Override
	public int addCard(Map<String, String> paraMap) {
		int n = dao.addCard(paraMap);
		return n;
	}

	// 카드 수 가져오기
	@Override
	public int getCardCount(Map<String, String> paraMap) {
		int n = dao.getCardCount(paraMap);
		return n;
	}

	// 카드 수정
	@Override
	public int editCard(Map<String, String> paraMap) {
		int n = dao.editCard(paraMap);
		return n;
	}

	// 카드 삭제
	@Override
	public int deleteCard(Map<String, Object> paraMap) {
		int n = dao.deleteCard(paraMap);
		return n;
	}

	@Override
	public List<Map<String, String>> empCardList() {
		List<Map<String, String>> empCardList = dao.empCardList();
		return empCardList;
	}

	
	
	
	
}
