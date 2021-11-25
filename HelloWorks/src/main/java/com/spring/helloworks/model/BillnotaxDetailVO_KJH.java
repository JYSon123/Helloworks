package com.spring.helloworks.model;

public class BillnotaxDetailVO_KJH {
	
	private String billnotax_detail_seq;
	private String fk_billnotax_seq;
	private String selldate;
	private String sellprod;
	private String sellamount;
	private String selloneprice;
	private String selltotalprice;
	
	public BillnotaxDetailVO_KJH() {}

	public BillnotaxDetailVO_KJH(String billnotax_detail_seq, String fk_billnotax_seq, String selldate, String sellprod,
			String sellamount, String selloneprice, String selltotalprice) {
		super();
		this.billnotax_detail_seq = billnotax_detail_seq;
		this.fk_billnotax_seq = fk_billnotax_seq;
		this.selldate = selldate;
		this.sellprod = sellprod;
		this.sellamount = sellamount;
		this.selloneprice = selloneprice;
		this.selltotalprice = selltotalprice;
	}

	public String getBillnotax_detail_seq() {
		return billnotax_detail_seq;
	}

	public void setBillnotax_detail_seq(String billnotax_detail_seq) {
		this.billnotax_detail_seq = billnotax_detail_seq;
	}

	public String getFk_billnotax_seq() {
		return fk_billnotax_seq;
	}

	public void setFk_billnotax_seq(String fk_billnotax_seq) {
		this.fk_billnotax_seq = fk_billnotax_seq;
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
	
	
		
}
