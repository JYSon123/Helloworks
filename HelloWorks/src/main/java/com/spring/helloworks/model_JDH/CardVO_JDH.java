package com.spring.helloworks.model_JDH;

public class CardVO_JDH {
	
	private String cardcompany;
	private String cardseq;
	private String cardnumber;
	private String cardregdate;
	private String cardempid;
	private String empname;
	
	public CardVO_JDH() {}


	public CardVO_JDH(String cardcompany, String cardseq, String cardnumber, String cardregdate,
			String cardempid, String empname) {
		super();
		this.cardcompany = cardcompany;
		this.cardseq = cardseq;
		this.cardnumber = cardnumber;
		this.cardregdate = cardregdate;
		this.cardempid = cardempid;
		this.empname = empname;
	}



	public String getcardcompany() {
		return cardcompany;
	}

	public void setcardcompany(String cardcompany) {
		this.cardcompany = cardcompany;
	}

	public String getcardseq() {
		return cardseq;
	}

	public void setcardseq(String cardseq) {
		this.cardseq = cardseq;
	}

	public String getcardnumber() {
		return cardnumber;
	}

	public void setcardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getcardregdate() {
		return cardregdate;
	}

	public void setcardregdate(String cardregdate) {
		this.cardregdate = cardregdate;
	}



	public String getcardempid() {
		return cardempid;
	}



	public void setcardempid(String cardempid) {
		this.cardempid = cardempid;
	}



	public String getEmpname() {
		return empname;
	}



	public void setEmpname(String empname) {
		this.empname = empname;
	}
	
	
	
	
	
}
