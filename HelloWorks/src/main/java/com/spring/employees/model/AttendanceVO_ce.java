package com.spring.employees.model;

public class AttendanceVO_ce {

	private String nowdate;
	private String fk_empno;
	private String intime;
	private String outtime;
	private int totaltime;
	private int status_latein;
	private int status_earlyout;
	
	// === 부서별 근태관리(월별)에 사용 === //
	private String empname;
	private String deptnum;
	private String deptname;
	
	private int sumtotal; // 총 근무 시간
	private int sumlate;  // 총 지각 일수	
	private int sumearly; // 총 조기퇴근 일수
	private int sumworkday; // 총 근무일수
	
	
	
	public String getNowdate() {
		return nowdate;
	}
	public void setNowdate(String nowdate) {
		this.nowdate = nowdate;
	}
	public String getFk_empno() {
		return fk_empno;
	}
	public void setFk_empno(String fk_empno) {
		this.fk_empno = fk_empno;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getOuttime() {
		return outtime;
	}
	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}
	public int getTotaltime() {
		return totaltime;
	}
	public void setTotaltime(int totaltime) {
		this.totaltime = totaltime;
	}
	public int getStatus_latein() {
		return status_latein;
	}
	public void setStatus_latein(int status_latein) {
		this.status_latein = status_latein;
	}
	public int getStatus_earlyout() {
		return status_earlyout;
	}
	public void setStatus_earlyout(int status_earlyout) {
		this.status_earlyout = status_earlyout;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getDeptnum() {
		return deptnum;
	}
	public void setDeptnum(String deptnum) {
		this.deptnum = deptnum;
	}
	public int getSumtotal() {
		return sumtotal;
	}
	public void setSumtotal(int sumtotal) {
		this.sumtotal = sumtotal;
	}
	public int getSumlate() {
		return sumlate;
	}
	public void setSumlate(int sumlate) {
		this.sumlate = sumlate;
	}
	public int getSumearly() {
		return sumearly;
	}
	public void setSumearly(int sumearly) {
		this.sumearly = sumearly;
	}
	public int getSumworkday() {
		return sumworkday;
	}
	public void setSumworkday(int sumworkday) {
		this.sumworkday = sumworkday;
	}
	
	
	
	
}
