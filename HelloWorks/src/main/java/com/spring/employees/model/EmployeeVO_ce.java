package com.spring.employees.model;

public class EmployeeVO_ce {

	private String empno;
	private String empname;
	private String empid;
	private String emppw; //(SHA-256 암호화 대상)
	private String email;
	private String ranking;
	private String fk_deptnum;
	private String empstatus;
	private String empsalary;
	private String hiredate;
	private String otpkey;
	
	
	
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getEmppw() {
		return emppw;
	}
	public void setEmppw(String emppw) {
		this.emppw = emppw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public String getFk_deptnum() {
		return fk_deptnum;
	}
	public void setFk_deptnum(String fk_deptnum) {
		this.fk_deptnum = fk_deptnum;
	}
	public String getEmpstatus() {
		return empstatus;
	}
	public void setEmpstatus(String empstatus) {
		this.empstatus = empstatus;
	}
	public String getEmpsalary() {
		return empsalary;
	}
	public void setEmpsalary(String empsalary) {
		this.empsalary = empsalary;
	}
	public String getHiredate() {
		return hiredate;
	}
	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}
	public String getOtpkey() {
		return otpkey;
	}
	public void setOtpkey(String otpkey) {
		this.otpkey = otpkey;
	}
	
	
	
	
	
}
