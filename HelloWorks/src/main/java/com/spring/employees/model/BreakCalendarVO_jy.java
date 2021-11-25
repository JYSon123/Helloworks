package com.spring.employees.model;

public class BreakCalendarVO_jy {
		
	
	
	private String fk_empno;   // 사원번호
    private String title;      // 휴가종류
    private String start1;     // 시작날짜
    private String end1;       // 끝나는 날짜
	
    
    public BreakCalendarVO_jy(String fk_empno, String title, String start1, String end1) {
		this.fk_empno = fk_empno;
		this.title = title;
		this.start1 = start1;
		this.end1 = end1;
	}
    
    public BreakCalendarVO_jy() {}
    
	public String getFk_empno() {
		return fk_empno;
	}

	public void setFk_empno(String fk_empno) {
		this.fk_empno = fk_empno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart1() {
		return start1;
	}

	public void setStart1(String start1) {
		this.start1 = start1;
	}

	public String getEnd1() {
		return end1;
	}

	public void setEnd1(String end1) {
		this.end1 = end1;
	}
	
    
	 
	 
	 
}
