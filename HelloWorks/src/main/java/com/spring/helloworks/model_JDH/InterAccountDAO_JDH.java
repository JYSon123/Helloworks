package com.spring.helloworks.model_JDH;

import java.util.List;
import java.util.Map;

import com.spring.helloworks.model.EmpVO_KJH;

public interface InterAccountDAO_JDH {

	// 직원 계좌 갯수 알아오기
	int getAccountCount(Map<String, String> paraMap);

	// 직원 계좌 페이징 목록 가져오기
	List<AccountingVO_JDH> accountingListWithPaging(Map<String, String> paraMap);

	//계좌 삭제
	int deleteAccount(Map<String, Object> paraMap);

	// 계좌 추가
	int addAccount(Map<String, String> paraMap);

	// 직원리스트
	List<Map<String, String>> empList();

	// 계좌 수정
	int editAccount(Map<String, String> paraMap);

	// 카드 리스트
	List<CardVO_JDH> cardList(Map<String, String> paraMap);

	// 카드 등록
	int addCard(Map<String, String> paraMap);

	// 카드 리스트 갯수 알아오기
	int getCardCount(Map<String, String> paraMap);

	// 카드 수정
	int editCard(Map<String, String> paraMap);

	// 카드 삭제
	int deleteCard(Map<String, Object> paraMap);

	// 카드 정보 가져오기
	List<Map<String, String>> empCardList();

	
	
}
