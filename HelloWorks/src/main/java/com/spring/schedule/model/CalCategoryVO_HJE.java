package com.spring.schedule.model;

public class CalCategoryVO_HJE {
	private String cno;		// 카테고리번호 ( 1, 2 )
	private String cname;	// 카테고리명  ( personal, share )
	
	public CalCategoryVO_HJE() {}

	public CalCategoryVO_HJE(String cno, String cname) {
		super();
		this.cno = cno;
		this.cname = cname;
	}

	public String getCno() {
		return cno;
	}

	public void setCno(String cno) {
		this.cno = cno;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	};
	
	
}
