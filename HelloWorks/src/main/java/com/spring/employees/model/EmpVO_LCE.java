package com.spring.employees.model;

public class EmpVO_LCE {

	private String empno;
	private String empname;
	private String empid;
	private String emppw;
	private String email;
	private String ranking;
	private String fk_deptnum;
	private String empstatus;
	private int empsalary;
	private String hiredate;
	private String otpkey;
	private String noticeemail;
	
	

	private String lastlogingap;
	private boolean requiredPwdChange = false;
	private boolean requiredGetOTPkey = false;
	
	private String otpurl;
	
	public EmpVO_LCE() {}
	
	public EmpVO_LCE(String empno, String empname, String empid, String emppw, String email, String ranking, String fk_deptnum,
			String empStatus, int empSalary, String hiredate, String otpkey, String otpurl) {
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
		
		switch (ranking) {
		case "1": ranking = "사원";
			break;
		case "2": ranking = "대리";
			break;
		case "3": ranking = "부장";
			break;
		case "4": ranking = "팀장";
			break;
		case "5": ranking = "대표";
			break;

		default:
			break;
		}
		
		this.ranking = ranking;
	}

	public String getFk_deptnum() {
		return fk_deptnum;
	}

	public void setFk_deptnum(String fk_deptnum) {
		
		switch (fk_deptnum) {
		case "10": fk_deptnum = "인사";
			break;
		case "20": fk_deptnum = "회계";
			break;
		case "30": fk_deptnum = "총무";
			break;
		case "40": fk_deptnum = "마케팅";
			break;
		case "50": fk_deptnum = "영업";
			break;
		case "00": fk_deptnum = "대표";
		break;

		default:
			break;
		}
		
		this.fk_deptnum = fk_deptnum;
	}

	public String getEmpstatus() {
		return empstatus;
	}

	public void setEmpstatus(String empstatus) {
		
		switch (empstatus) {
		case "0" : empstatus ="퇴사";
			break;
		case "1" : empstatus ="재직";
		break;
		case "2" : empstatus ="휴직";
		break;
		default:
			break;
		}
		
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
		
		hiredate = hiredate.substring(0, 10);
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
