package com.spring.helloworks.model;

public class EmpVO_KJH {

	private String empno;
	private String empname;
	private String empid;
	private String emppw;
	private String email;
	private int ranking;
	private String fk_deptnum;
	private int empstatus;
	private int empsalary;
	private String hiredate;
	private String otpkey;
	
	private String lastlogingap;
	private boolean requiredPwdChange = false;
	private boolean requiredGetOTPkey = false;
	
	private String otpurl;
	
	private String noticeemail;
	
	public EmpVO_KJH() {}
	
	public EmpVO_KJH(String empno, String empname, String empid, String emppw, String email, int ranking, String fk_deptnum,
			int empStatus, int empSalary, String hiredate, String otpkey, String otpurl, String noticeemail) {
		super();
		this.empno = empno;
		this.empname = empname;
		this.empid = empid;
		this.emppw = emppw;
		this.email = email;
		this.ranking = ranking;
		this.fk_deptnum = fk_deptnum;
		this.empstatus = empStatus;
		this.empsalary = empSalary;
		this.hiredate = hiredate;
		this.otpkey = otpkey;
		this.otpurl = otpurl;
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

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getFk_deptnum() {
		return fk_deptnum;
	}

	public void setFk_deptnum(String fk_deptnum) {
		this.fk_deptnum = fk_deptnum;
	}

	public int getEmpstatus() {
		return empstatus;
	}

	public void setEmpstatus(int empstatus) {
		this.empstatus = empstatus;
	}

	public int getEmpsalary() {
		return empsalary;
	}

	public void setEmpsalary(int empsalary) {
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
