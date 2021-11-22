package com.spring.helloworks.model;

public class TransactionVO_KJH {
	
	private String transaction_seq;
	private String customer_id;
	private String customer_comp;
	private String customer_name;
	private String customer_addr;
	private String regdate;
	private int totalprice;
	private String billtax_yn;
	private String empid;
	private String empname;
	private String mycompany_id;
	private String mycompany_comp;
	private String mycompany_name;
	private String mycompany_addr;
	
	public TransactionVO_KJH() {}

	public TransactionVO_KJH(String transaction_seq, String customer_id, String customer_comp, String customer_name,
			String customer_addr, String regdate, int totalprice, String billtax_yn, String payment, String empid,
			String empname, String mycompany_id, String mycompany_comp, String mycompany_name, String mycompany_addr) {
		super();
		this.transaction_seq = transaction_seq;
		this.customer_id = customer_id;
		this.customer_comp = customer_comp;
		this.customer_name = customer_name;
		this.customer_addr = customer_addr;
		this.regdate = regdate;
		this.totalprice = totalprice;
		this.billtax_yn = billtax_yn;
		this.empid = empid;
		this.empname = empname;
		this.mycompany_id = mycompany_id;
		this.mycompany_comp = mycompany_comp;
		this.mycompany_name = mycompany_name;
		this.mycompany_addr = mycompany_addr;
	}

	public String getTransaction_seq() {
		return transaction_seq;
	}

	public void setTransaction_seq(String transaction_seq) {
		this.transaction_seq = transaction_seq;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_comp() {
		return customer_comp;
	}

	public void setCustomer_comp(String customer_comp) {
		this.customer_comp = customer_comp;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_addr() {
		return customer_addr;
	}

	public void setCustomer_addr(String customer_addr) {
		this.customer_addr = customer_addr;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

	public String getBilltax_yn() {
		return billtax_yn;
	}

	public void setBilltax_yn(String billtax_yn) {
		this.billtax_yn = billtax_yn;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getMycompany_id() {
		return mycompany_id;
	}

	public void setMycompany_id(String mycompany_id) {
		this.mycompany_id = mycompany_id;
	}

	public String getMycompany_comp() {
		return mycompany_comp;
	}

	public void setMycompany_comp(String mycompany_comp) {
		this.mycompany_comp = mycompany_comp;
	}

	public String getMycompany_name() {
		return mycompany_name;
	}

	public void setMycompany_name(String mycompany_name) {
		this.mycompany_name = mycompany_name;
	}

	public String getMycompany_addr() {
		return mycompany_addr;
	}

	public void setMycompany_addr(String mycompany_addr) {
		this.mycompany_addr = mycompany_addr;
	}

	
	
	
}
