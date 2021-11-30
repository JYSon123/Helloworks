package com.spring.addbook.model;

public class AddbookVO {
	
	private String fk_empid; // 유저개인 아이디.
	private String name;  // 이름 
	private String email; // 이메일
	private String phonenum; // 전화번호
	private String fk_tag; // 태그
	private String company; // 회사
	private String department; // 부서
	private String rank; // 직급
	private String address; // 주소
	private String homepage; // 홈페이지
	private String birthday; // 생일
	private String memo; // 메모
	private String pbl; // 공유 개인 유무
	
	public AddbookVO() {}
	
	public AddbookVO(String name, String email, String phonenum, String fk_tag) {
		this.name = name;
		this.email = email;
		this.phonenum = phonenum;
		this.fk_tag = fk_tag;
		
	}
	
	public String getFk_empid() {
		return fk_empid;
	}

	public void setFk_empid(String fk_empid) {
		this.fk_empid = fk_empid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getFk_tag() {
		return fk_tag;
	}

	public void setFk_tag(String fk_tag) {
		this.fk_tag = fk_tag;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPbl() {
		return pbl;
	}

	public void setPbl(String pbl) {
		this.pbl = pbl;
	}
	
	
	
	
	
	
	
}
