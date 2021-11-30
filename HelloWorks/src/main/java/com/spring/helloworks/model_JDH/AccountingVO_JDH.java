package com.spring.helloworks.model_JDH;

public class AccountingVO_JDH {
	
	private String accountbank;
	private String accountseq;
	private String accountnumber;
	private String accountregdate;
	private String accountempid;
	private String empname;
	
	public AccountingVO_JDH() {}

	

	public AccountingVO_JDH(String accountbank, String accountseq, String accountnumber, String accountregdate,
			String accountempid, String empname) {
		super();
		this.accountbank = accountbank;
		this.accountseq = accountseq;
		this.accountnumber = accountnumber;
		this.accountregdate = accountregdate;
		this.accountempid = accountempid;
		this.empname = empname;
	}



	public String getAccountbank() {
		return accountbank;
	}

	public void setAccountbank(String accountbank) {
		this.accountbank = accountbank;
	}

	public String getAccountseq() {
		return accountseq;
	}

	public void setAccountseq(String accountseq) {
		this.accountseq = accountseq;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getAccountregdate() {
		return accountregdate;
	}

	public void setAccountregdate(String accountregdate) {
		this.accountregdate = accountregdate;
	}



	public String getAccountempid() {
		return accountempid;
	}



	public void setAccountempid(String accountempid) {
		this.accountempid = accountempid;
	}



	public String getEmpname() {
		return empname;
	}



	public void setEmpname(String empname) {
		this.empname = empname;
	}
	
	
	
	
	
}
