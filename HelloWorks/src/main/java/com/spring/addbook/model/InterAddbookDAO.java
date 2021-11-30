package com.spring.addbook.model;

import java.util.List;
import java.util.Map;




public interface InterAddbookDAO {
	
	
	
	List<AddbookVO> addbookList_private(Map<String,String> paramap);
	
	List<AddbookVO> addbookList_public(Map<String,String> paramap);

	List<Addbook_tagVO> addbooktag_private();

	List<Addbook_tagVO> addbooktag_public();
	
	
	
}
