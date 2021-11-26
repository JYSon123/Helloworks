package com.spring.addbook.service;

import java.util.List;

import com.spring.addbook.model.AddbookVO;



public interface InterAddbookService {

	List<AddbookVO> addbookList_private();
	
	List<AddbookVO> addbookList_public();
	
}
