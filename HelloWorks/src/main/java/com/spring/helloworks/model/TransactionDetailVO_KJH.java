package com.spring.helloworks.model;

public class TransactionDetailVO_KJH {
	
	private String transaction_detail_seq;
	private String fk_transaction_seq;
	private String selldate;
	private String sellprod;
	private String sellamount;
	private String selloneprice;
	private String selltotalprice;
	
	public TransactionDetailVO_KJH() {}

	public TransactionDetailVO_KJH(String transaction_detail_seq, String fk_transaction_seq, String selldate,
			String sellprod, String sellamount, String selloneprice, String selltotalprice) {
		super();
		this.transaction_detail_seq = transaction_detail_seq;
		this.fk_transaction_seq = fk_transaction_seq;
		this.selldate = selldate;
		this.sellprod = sellprod;
		this.sellamount = sellamount;
		this.selloneprice = selloneprice;
		this.selltotalprice = selltotalprice;
	}

	public String getTransaction_detail_seq() {
		return transaction_detail_seq;
	}

	public void setTransaction_detail_seq(String transaction_detail_seq) {
		this.transaction_detail_seq = transaction_detail_seq;
	}

	public String getFk_transaction_seq() {
		return fk_transaction_seq;
	}

	public void setFk_transaction_seq(String fk_transaction_seq) {
		this.fk_transaction_seq = fk_transaction_seq;
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
