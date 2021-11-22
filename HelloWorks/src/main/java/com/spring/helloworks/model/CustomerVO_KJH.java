package com.spring.helloworks.model;

public class CustomerVO_KJH {

	private String customer_id;
	private String customer_comp;
	private String customer_name;
	private String customer_addr;
	private String customer_email;
	
	public CustomerVO_KJH() {}
	
	public CustomerVO_KJH(String customer_id, String customer_comp, String customer_name, String customer_addr,
			String customer_email) {
		super();
		this.customer_id = customer_id;
		this.customer_comp = customer_comp;
		this.customer_name = customer_name;
		this.customer_addr = customer_addr;
		this.customer_email = customer_email;
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

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	
	
	
}
