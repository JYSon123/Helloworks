package com.spring.addbook.model;

public class AddbookVO {

	private String name;
	private String email;
	private String phonenum;
	private String tag;
	private String company;
	private String department;
	private String rank;
	private String address;
	private String homepage;
	private String birthday;
	private String memo;
	private String pbl;
	
	public AddbookVO() {}
	
	public AddbookVO(String name, String email, String phonenum, String tag, String company,
			 String pbl) {
		this.name = name;
		this.email = email;
		this.phonenum = phonenum;
		this.tag = tag;
		this.company = company;
		this.pbl = pbl;
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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
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
