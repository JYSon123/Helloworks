package com.spring.addbook.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.addbook.model.AddbookVO;
import com.spring.addbook.model.Addbook_tagVO;
import com.spring.addbook.model.InterAddbookDAO;





@Service
public class AddbookService implements InterAddbookService {
	
	
	
	// === #34. 의존객체 주입하기(DI: Dependency Injection) ===
		@Autowired
		private InterAddbookDAO dao;
		// Type 에 따라 Spring 컨테이너가 알아서 bean 으로 등록된 com.spring.model.AddbookDAO 의 bean 을  dao 에 주입시켜준다. 
	    // 그러므로 dao 는 null 이 아니다.
	

	// 개인 주소록 //
		@Override
		public List<AddbookVO> addbookList_private(Map<String,String>paramap) {
			// System.out.println("서비스단입니다");
			List<AddbookVO> addbookList_private = dao.addbookList_private(paramap);
			return addbookList_private;
		}
		
		// 공용주소록 // 
		@Override
		public List<AddbookVO> addbookList_public(Map<String,String>paramap) {
			
			List<AddbookVO> addbookList_public = dao.addbookList_public(paramap);
			return  addbookList_public;
		}

		@Override
		public List<Addbook_tagVO> addbooktag_private() {
			List<Addbook_tagVO> addbooktag_private = dao.addbooktag_private();
			return addbooktag_private;
		}

		@Override
		public List<Addbook_tagVO> addbooktag_public() {
			List<Addbook_tagVO> addbooktag_public = dao.addbooktag_public();
			return addbooktag_public;
		}

		
		
		
}
