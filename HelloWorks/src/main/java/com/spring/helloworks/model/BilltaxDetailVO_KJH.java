package com.spring.helloworks.model;

public class BilltaxDetailVO_KJH {
	
	private String billtax_detail_seq;
	private String fk_billtax_seq;
	private String selldate;
	private String sellprod;
	private String sellamount;
	private String selloneprice;
	private String selltotalprice;
	private String selltax;
	
	public BilltaxDetailVO_KJH() {}
	
	public BilltaxDetailVO_KJH(String billtax_detail_seq, String fk_billtax_seq, String selldate, String sellprod,
			String sellamount, String selloneprice, String selltotalprice, String selltax) {
		super();
		this.billtax_detail_seq = billtax_detail_seq;
		this.fk_billtax_seq = fk_billtax_seq;
		this.selldate = selldate;
		this.sellprod = sellprod;
		this.sellamount = sellamount;
		this.selloneprice = selloneprice;
		this.selltotalprice = selltotalprice;
		this.selltax = selltax;
	}

	public String getBilltax_detail_seq() {
		return billtax_detail_seq;
	}

	public void setBilltax_detail_seq(String billtax_detail_seq) {
		this.billtax_detail_seq = billtax_detail_seq;
	}

	public String getFk_billtax_seq() {
		return fk_billtax_seq;
	}

	public void setFk_billtax_seq(String fk_billtax_seq) {
		this.fk_billtax_seq = fk_billtax_seq;
	}

	public String getSelldate() {
		return selldate;
	}

	public void setSelldate(String selldate) {
		this.selldate = selldate;
	}

	public String getSellprod() {
		return sellprod;
	}

	public void setSellprod(String sellprod) {
		this.sellprod = sellprod;
	}

	public String getSellamount() {
		return sellamount;
	}

	public void setSellamount(String sellamount) {
		this.sellamount = sellamount;
	}

	public String getSelloneprice() {
		return selloneprice;
	}

	public void setSelloneprice(String selloneprice) {
		this.selloneprice = selloneprice;
	}

	public String getSelltotalprice() {
		return selltotalprice;
	}

	public void setSelltotalprice(String selltotalprice) {
		this.selltotalprice = selltotalprice;
	}

	public String getSelltax() {
		return selltax;
	}

	public void setSelltax(String selltax) {
		this.selltax = selltax;
	}
	
	
	
	
}
