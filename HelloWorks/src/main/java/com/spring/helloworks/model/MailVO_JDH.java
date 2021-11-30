package com.spring.helloworks.model;

import org.springframework.web.multipart.MultipartFile;

public class MailVO_JDH {

	private String sendid;
	private String sendname;
	private String recname;
	private String mailseq;
	private String fk_mailseq;
	private String recid;
	private String mailsubject;
	private String mailcontent;
	private String mailregdate;
	private String mailreceivestatus;
	private String mailsendstatus;
	private String mailreadstatus;
	
	
	private MultipartFile attach;
	
	private String mailfilename;
	private String mailorgfilename;
	private String mailfilesize;
	
	private String previousseq;      // 이전글번호
	private String previoussubject;  // 이전글제목
	private String nextseq;          // 다음글번호
	private String nextsubject;      // 다음글제목
	
	public MailVO_JDH() {}

	
	public MailVO_JDH(String sendid, String sendname, String recname, String mailseq, String fk_mailseq, String recid,
			String mailsubject, String mailcontent, String mailregdate, String mailreceivestatus, String mailsendstatus,
			String mailreadstatus, MultipartFile attach, String mailfilename, String mailorgfilename,
			String mailfilesize, String previousseq, String previoussubject, String nextseq, String nextsubject,
			MultipartFile filedata, String callback, String callback_func) {
		super();
		this.sendid = sendid;
		this.sendname = sendname;
		this.recname = recname;
		this.mailseq = mailseq;
		this.fk_mailseq = fk_mailseq;
		this.recid = recid;
		this.mailsubject = mailsubject;
		this.mailcontent = mailcontent;
		this.mailregdate = mailregdate;
		this.mailreceivestatus = mailreceivestatus;
		this.mailsendstatus = mailsendstatus;
		this.mailreadstatus = mailreadstatus;
		this.attach = attach;
		this.mailfilename = mailfilename;
		this.mailorgfilename = mailorgfilename;
		this.mailfilesize = mailfilesize;
		this.previousseq = previousseq;
		this.previoussubject = previoussubject;
		this.nextseq = nextseq;
		this.nextsubject = nextsubject;
		Filedata = filedata;
		this.callback = callback;
		this.callback_func = callback_func;
	}








	public String getSendid() {
		return sendid;
	}

	public void setSendid(String sendid) {
		this.sendid = sendid;
	}

	public String getMailseq() {
		return mailseq;
	}

	public void setMailseq(String mailseq) {
		this.mailseq = mailseq;
	}

	public String getFk_mailseq() {
		return fk_mailseq;
	}

	public void setFk_mailseq(String fk_mailseq) {
		this.fk_mailseq = fk_mailseq;
	}

	public String getRecid() {
		return recid;
	}

	public void setRecid(String recid) {
		this.recid = recid;
	}
	
	public String getMailsubject() {
		return mailsubject;
	}

	public void setMailsubject(String mailsubject) {
		this.mailsubject = mailsubject;
	}

	public String getMailcontent() {
		return mailcontent;
	}

	public void setMailcontent(String mailcontent) {
		this.mailcontent = mailcontent;
	}

	public String getMailregdate() {
		return mailregdate;
	}

	public void setMailregdate(String mailregdate) {
		this.mailregdate = mailregdate;
	}

	

	public String getMailreceivestatus() {
		return mailreceivestatus;
	}


	public void setMailreceivestatus(String mailreceivestatus) {
		this.mailreceivestatus = mailreceivestatus;
	}


	public String getMailsendstatus() {
		return mailsendstatus;
	}


	public void setMailsendstatus(String mailsendstatus) {
		this.mailsendstatus = mailsendstatus;
	}


	public MultipartFile getAttach() {
		return attach;
	}

	public void setAttach(MultipartFile attach) {
		this.attach = attach;
	}

	public String getMailfilename() {
		return mailfilename;
	}

	public void setMailfilename(String mailfilename) {
		this.mailfilename = mailfilename;
	}

	public String getMailorgfilename() {
		return mailorgfilename;
	}

	public void setMailorgfilename(String mailorgfilename) {
		this.mailorgfilename = mailorgfilename;
	}

	public String getMailfilesize() {
		return mailfilesize;
	}

	public void setMailfilesize(String mailfilesize) {
		this.mailfilesize = mailfilesize;
	}

	public String getMailreadstatus() {
		return mailreadstatus;
	}

	public void setMailreadstatus(String mailreadstatus) {
		this.mailreadstatus = mailreadstatus;
	}
	
	private MultipartFile Filedata;     
    //photo_uploader.html페이지의 form태그내에 존재하는 file 태그의 name명과 일치시켜줌

	private String callback;
	    //callback URL
	
	private String callback_func;
	    //콜백함수??
	
	public MultipartFile getFiledata() {
	   return Filedata;
	}
	
	public void setFiledata(MultipartFile filedata) {
	   Filedata = filedata;
	}
	
	public String getCallback() {
	   return callback;
	}
	
	public void setCallback(String callback) {
	   this.callback = callback;
	}
	
	public String getCallback_func() {
	   return callback_func;
	}
	
	public void setCallback_func(String callback_func) {
	   this.callback_func = callback_func;
	}

	public String getPreviousseq() {
		return previousseq;
	}

	public void setPreviousseq(String previousseq) {
		this.previousseq = previousseq;
	}

	public String getPrevioussubject() {
		return previoussubject;
	}

	public void setPrevioussubject(String previoussubject) {
		this.previoussubject = previoussubject;
	}

	public String getNextseq() {
		return nextseq;
	}

	public void setNextseq(String nextseq) {
		this.nextseq = nextseq;
	}

	public String getNextsubject() {
		return nextsubject;
	}

	public void setNextsubject(String nextsubject) {
		this.nextsubject = nextsubject;
	}


	public String getSendname() {
		return sendname;
	}


	public void setSendname(String sendname) {
		this.sendname = sendname;
	}


	public String getRecname() {
		return recname;
	}


	public void setRecname(String recname) {
		this.recname = recname;
	}

	
	
}
