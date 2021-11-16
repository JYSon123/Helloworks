package com.spring.employees.model;

public class DocumentVO_jy {

	
	private String doument_seq;  // 문서번호
    private String fk_empno;     // 사원번호
    private String fk_deptnum;   // 부서번호 
    private String name;         // 글쓴이
    private String subject;      // 문서제목 
    private String content;      // 문서내용
    private String regDate;      // 문서기안시간
    private String status;       // 문서종류      1:연차,  2:지출결의서, 3:품의서, 4:업무협조요청  
    private String result;       // 결과            0:대기,  1:승인, 3:반려   

    private String fileName;     // WAS(톰캣)에 저장될 파일명(2021110809271535243254235235234.png)
    private String orgFilename;  // 진짜 파일명(강아지.png)
    private String fileSize;     // 파일크기  
    
    private String breakkind;
    private String breakstart;
    private String breakend;
    

    public DocumentVO_jy() {}
 
    
    
	public DocumentVO_jy(String doument_seq, String fk_empno, String fk_deptnum, String name, String subject,
			String content, String regDate, String status, String result, String fileName, String orgFilename,
			String fileSize, String breakstart, String  breakend , String breakkind) {

		this.doument_seq = doument_seq;
		this.fk_empno = fk_empno;
		this.fk_deptnum = fk_deptnum;
		this.name = name;
		this.subject = subject;
		this.content = content;
		this.regDate = regDate;
		this.status = status;
		this.result = result;
		this.fileName = fileName;
		this.orgFilename = orgFilename;
		this.fileSize = fileSize;
	}


	public String getDoument_seq() {
		return doument_seq;
	}


	public void setDoument_seq(String doument_seq) {
		this.doument_seq = doument_seq;
	}


	public String getFk_empno() {
		return fk_empno;
	}


	public void setFk_empno(String fk_empno) {
		this.fk_empno = fk_empno;
	}


	public String getFk_deptnum() {
		return fk_deptnum;
	}


	public void setFk_deptnum(String fk_deptnum) {
		this.fk_deptnum = fk_deptnum;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getRegDate() {
		return regDate;
	}


	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getOrgFilename() {
		return orgFilename;
	}


	public void setOrgFilename(String orgFilename) {
		this.orgFilename = orgFilename;
	}


	public String getFileSize() {
		return fileSize;
	}


	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}




	public String getBreakkind() {
		return breakkind;
	}




	public void setBreakkind(String breakkind) {
		this.breakkind = breakkind;
	}




	public String getBreakstart() {
		return breakstart;
	}




	public void setBreakstart(String breakstart) {
		this.breakstart = breakstart;
	}




	public String getBreakend() {
		return breakend;
	}




	public void setBreakend(String breakend) {
		this.breakend = breakend;
	}
	
	
	
    

	
	
}
