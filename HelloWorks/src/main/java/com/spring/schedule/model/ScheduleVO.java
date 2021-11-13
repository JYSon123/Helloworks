package com.spring.schedule.model;

public class ScheduleVO {
	
	private String sno;			// 일정일련번호
	private String fk_calno;	// 캘린더번호
	private String title;		// 일정 제목
	private String content;		// 내용
	private String location;	// 장소
	private String startDate;	// 시작날짜
	private String endDate;		// 마감날짜
	private String status;		// 상태
	
	public ScheduleVO() {};
	
	public ScheduleVO(String sno, String fk_calno, String title, String content, String location, String startDate,
			String endDate, String status) {
		super();
		this.sno = sno;
		this.fk_calno = fk_calno;
		this.title = title;
		this.content = content;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getFk_calno() {
		return fk_calno;
	}

	public void setFk_calno(String fk_calno) {
		this.fk_calno = fk_calno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
