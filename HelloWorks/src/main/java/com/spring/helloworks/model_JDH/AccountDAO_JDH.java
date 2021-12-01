package com.spring.helloworks.model_JDH;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.spring.helloworks.model.EmpVO_KJH;

@Repository
public class AccountDAO_JDH implements InterAccountDAO_JDH {
	
	@Resource
	private SqlSessionTemplate sqlsession2;

	// 직원 계좌 갯수 알아오기
	@Override
	public int getAccountCount(Map<String, String> paraMap) {
		int n = sqlsession2.selectOne("final_dh_account.getAccountCount", paraMap);
		return n;
	}

	// 직원 계좌 페이징 목록 가져오기
	@Override
	public List<AccountingVO_JDH> accountingListWithPaging(Map<String, String> paraMap) {
		List<AccountingVO_JDH> accountingList = sqlsession2.selectList("final_dh_account.accountingListWithPaging", paraMap);
		// System.out.println("어카운트사이즈 DAO" + accountingList.size());
		return accountingList;
	}

	// 계좌 삭제
	@Override
	public int deleteAccount(Map<String, Object> paraMap) {
		int n = sqlsession2.delete("final_dh_account.deleteAccount", paraMap);
		return n;
	}

	// 계좌 추가
	@Override
	public int addAccount(Map<String, String> paraMap) {
		int n = sqlsession2.insert("final_dh_account.addAccount", paraMap);
		return n;
	}

	
	// 직원리스트
	@Override
	public List<Map<String, String>> empList() {
		List<Map<String, String>> empList = sqlsession2.selectList("final_dh_account.empList");
		return empList;
	}

	// 계좌 수정
	@Override
	public int editAccount(Map<String, String> paraMap) {
		int n = sqlsession2.update("final_dh_account.editAccount", paraMap);
		return n;
	}

	// 카드 리스트
	@Override
	public List<CardVO_JDH> cardList(Map<String, String> paraMap) {
		List<CardVO_JDH> cardList = sqlsession2.selectList("final_dh_account.cardList", paraMap);
		return cardList;
	}

	// 카드 등록
	@Override
	public int addCard(Map<String, String> paraMap) {
		int n = sqlsession2.insert("final_dh_account.addCard", paraMap);
		return n;
	}

	// 카드 리스트 갯수
	@Override
	public int getCardCount(Map<String, String> paraMap) {
		int n = sqlsession2.selectOne("final_dh_account.getCardCount", paraMap);
		return n;
	}

	// 카드 수정
	@Override
	public int editCard(Map<String, String> paraMap) {
		int n = sqlsession2.update("final_dh_account.editCard",paraMap);
		return n;
	}

	// 카드삭제
	@Override
	public int deleteCard(Map<String, Object> paraMap) {
		int n = sqlsession2.delete("final_dh_account.deleteCard", paraMap);
		return n;
	}

	// 직원리스트
	@Override
	public List<Map<String, String>> empCardList() {
		List<Map<String, String>> empCardList = sqlsession2.selectList("final_dh_account.empCardList");
		return empCardList;
	}

	
	
	
}
