package com.spring.employees.model;

public class EmpVO_sj {

	private String empno;
	private String empname;
	private String empid;
	private String emppw;
	private String email;
	private String ranking;
	private String fk_deptnum;
	private String empstatus;
	private String empsalary;
	private String hiredate;
	private String otpkey;
	private String noticeemail;
	
	private String lastlogingap;
	private boolean requiredPwdChange = false;
	private boolean requiredGetOTPkey = false;
	
	private String otpurl;
	
	public EmpVO_sj() {}
	
	public EmpVO_sj(String empname, String empid, String email,  String empno, 
			        String hiredate, String ranking, String fk_deptnum, String empstatus, String noticeemail) { // 정보수정을 위한 생성자
		this.empname = empname;
		this.empid = empid;
		this.email = email;
		this.empno = empno;
		this.hiredate = hiredate;
		this.ranking = ranking;
		this.fk_deptnum = fk_deptnum;
		this.empstatus = empstatus;	
		this.noticeemail = noticeemail;
	}


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

	public String getLastlogingap() {
		return lastlogingap;
	}

	public void setLastlogingap(String lastlogingap) {
		this.lastlogingap = lastlogingap;
	}

	public boolean isRequiredPwdChange() {
		return requiredPwdChange;
	}

	public void setRequiredPwdChange(boolean requiredPwdChange) {
		this.requiredPwdChange = requiredPwdChange;
	}

	public boolean isRequiredGetOTPkey() {
		return requiredGetOTPkey;
	}

	public void setRequiredGetOTPkey(boolean requiredGetOTPkey) {
		this.requiredGetOTPkey = requiredGetOTPkey;
	}

	public String getOtpurl() {
		return otpurl;
	}

	public void setOtpurl(String otpurl) {
		this.otpurl = otpurl;
	}

	public String getNoticeemail() {
		return noticeemail;
	}

	public void setNoticeemail(String noticeemail) {
		this.noticeemail = noticeemail;
	}
	
	
	
	

	
	
	
	
}
