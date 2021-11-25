package com.spring.employees.model;


public class CommentVO_sj {

	private String seq;           // 글번호
    private String fk_empno;      // 사원번호
    private String name;          // 글쓴이 
    private String content;       // 글내용 
    private String regDate;       // 작성일자
    private String parentSeq;     // 원게시물 글번호
    private String status;        // 글삭제여부   1:사용가능한 글,  0:삭제된글
	
    
    public CommentVO_sj() {} // 기본생성자
    
    
    public CommentVO_sj(String seq, String fk_empno, String name,
    					String content, String regDate, String parentSeq, 
    					String status) {
    	this.seq = seq;
    	this.fk_empno = fk_empno;
    	this.name = name;
    	this.content = content;
    	this.regDate = regDate;
    	this.parentSeq = parentSeq;
    	this.status = status;   	
    }
    
    
    
    
    public String getSeq() {
		return seq;
	}
	
    public void setSeq(String seq) {
		this.seq = seq;
	}
	
    public String getFk_empno() {
		return fk_empno;
	}
	
    public void setFk_empno(String fk_empno) {
		this.fk_empno = fk_empno;
	}
	
    public String getName() {
		return name;
	}
	
    public void setName(String name) {
		this.name = name;
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


	public String getParentSeq() {
		return parentSeq;
	}


	public void setParentSeq(String parentSeq) {
		this.parentSeq = parentSeq;
	}
   
    
    
    
    
	
}
