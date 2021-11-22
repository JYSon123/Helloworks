package com.spring.schedule.model;

public class CalendarVO_HJE {
	
	public String calno;	// 캘린더번호
	public String fk_cno;	// 카테고리 번호
	public String calname;	// 캘린더 명
	public String color;	// 색상
	public String shareEmp;	// 공유인원
	
	public CalendarVO_HJE() {};
	
	public CalendarVO_HJE(String calno, String fk_cno, String calname, String color, String shareEmp) {
		super();
		this.calno = calno;
		this.fk_cno = fk_cno;
		this.calname = calname;
		this.color = color;
		this.shareEmp = shareEmp;
	}

	public String getCalno() {
		return calno;
	}

	public void setCalno(String calno) {
		this.calno = calno;
	}

	public String getFk_cno() {
		return fk_cno;
	}

	public void setFk_cno(String fk_cno) {
		this.fk_cno = fk_cno;
	}

	public String getCalname() {
		return calname;
	}

	public void setCalname(String calname) {
		this.calname = calname;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getShareEmp() {
		return shareEmp;
	}

	public void setShareEmp(String shareEmp) {
		this.shareEmp = shareEmp;
	}
	

}
