package com.spring.addbook.model;

import java.util.List;



public interface InterAddbookDAO {
	
	
	
	List<AddbookVO> addbookList_private();
	
	List<AddbookVO> addbookList_public();
	
}
