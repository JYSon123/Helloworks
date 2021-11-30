package com.spring.addbook.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class AddbookDAO implements InterAddbookDAO {

	
	@Resource
	private SqlSessionTemplate sqlsession2; // 원격DB 에 연결
	// Type 에 따라 Spring 컨테이너가 알아서 root-context.xml 에 생성된 org.mybatis.spring.SqlSessionTemplate 의 sqlsession2 bean 을  sqlsession2 에 주입시켜준다. 
    // 그러므로 sqlsession2 는 null 이 아니다.
	
	
	// 주소록 띄워주기
	@Override
	public List<AddbookVO> addbookList_private(Map<String,String> paramap) {
		List<AddbookVO> addbookList_private = sqlsession2.selectList("final_pjw.addbookList_private", paramap);
		return addbookList_private;
	}

	// 공용주소록 띄워주기
	@Override
	public List<AddbookVO> addbookList_public(Map<String, String> paramap) {
		List<AddbookVO> addbookList_public = sqlsession2.selectList("final_pjw.addbookList_public", paramap);
		return addbookList_public;
	}

	@Override
	public List<Addbook_tagVO> addbooktag_private() {
		List<Addbook_tagVO> addbooktag_private = sqlsession2.selectList("final_pjw.addbooktag_private");
		return addbooktag_private;
	}

	@Override
	public List<Addbook_tagVO> addbooktag_public() {
		List<Addbook_tagVO> addbooktag_public = sqlsession2.selectList("final_pjw.addbooktag_public");
		return addbooktag_public;
	}
	
	
	
	
}
