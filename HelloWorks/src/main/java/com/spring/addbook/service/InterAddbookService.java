package com.spring.addbook.service;

import java.util.List;
import java.util.Map;

import com.spring.addbook.model.*;




public interface InterAddbookService {

	List<AddbookVO> addbookList_private(Map<String,String> paramap);
	
	List<AddbookVO> addbookList_public(Map<String,String> paramap);

	List<Addbook_tagVO> addbooktag_private();

	List<Addbook_tagVO> addbooktag_public();
	

}
