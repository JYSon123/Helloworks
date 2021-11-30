package com.spring.addbook.model;

public class Addbook_tagVO {
	private String tag; // 태그명
	private String tag_pbl; // 공유태그와 개인 태그 구분
	private String fk_empid; // 개인 태그일 경우 누구의 개인 태그인지 구분
	
	
	
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTag_pbl() {
		return tag_pbl;
	}
	public void setTag_pbl(String tag_pbl) {
		this.tag_pbl = tag_pbl;
	}
	public String getFk_empid() {
		return fk_empid;
	}
	public void setFk_empid(String fk_empid) {
		this.fk_empid = fk_empid;
	}
	
	
}
