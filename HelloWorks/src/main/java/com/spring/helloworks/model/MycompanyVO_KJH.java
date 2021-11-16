package com.spring.helloworks.model;

public class MycompanyVO_KJH {

	private String mycompany_id;
	private String mycompany_comp;
	private String mycompany_name;
	private String mycompany_addr;
	private String mycompany_sort;
	
	public MycompanyVO_KJH() {}
	
	public MycompanyVO_KJH(String mycompany_id, String mycompany_comp, String mycompany_name, String mycompany_addr,
			String mycompany_sort) {
		super();
		this.mycompany_id = mycompany_id;
		this.mycompany_comp = mycompany_comp;
		this.mycompany_name = mycompany_name;
		this.mycompany_addr = mycompany_addr;
		this.mycompany_sort = mycompany_sort;
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

	public String getMycompany_sort() {
		return mycompany_sort;
	}

	public void setMycompany_sort(String mycompany_sort) {
		this.mycompany_sort = mycompany_sort;
	}
		
}
