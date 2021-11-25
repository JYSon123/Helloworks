package com.spring.employees.model;

import org.springframework.web.multipart.MultipartFile;

public class BoardVO {

	private String seq;           // 글번호
    private String fk_empno;      // 사원번호
    private String name;          // 글쓴이 
    private String subject;       // 글제목
    private String content;       // 글내용   -- clob (최대 4GB까지 허용) 
    private String pw;            // 글암호
    private String readCount;     // 글조회수
    private String regDate;       // 글쓴시간
    private String status;        // 글삭제여부   1:사용가능한 글,  0:삭제된글
   
    // 아래는 select용. insert용이 아님 생성자 안에 파라미터로 넣을 필요 없다
    private String previousseq;      // 이전글번호
    private String previoussubject;  // 이전글제목
    private String nextseq;          // 다음글번호
    private String nextsubject;      // 다음글제목
    
    // 댓글쓰기
    private String commentCount;  // 댓글의 개수

    // 답변글쓰기
    private String groupno;       // 답변글쓰기에 있어서 그룹번호 	                                                
    private String fk_seq;        // fk_seq 컬럼은 절대로 foreign key가 아니다 원글(부모글)이 누구인지에 대한 정보값이다.
    private String depthno;       // 답변글쓰기에 있어서 답변글 이라면 얼만큼 들여쓰기를 할 것인지
    
    // 파일첨부
    private MultipartFile attach; // jsp에서 name을 attach로 줘서 여기도 attach/ form태그에서 파일을 받아서 저장되는 필드이다
    private String fileName;      // WAS(톰캣)에 저장될 파일명(2021110809271535243254235235234.png)                                       
    private String orgFilename;   // 원본 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
    private String fileSize;      // 파일크기  
	
	
	public BoardVO() {} // 매개변수가 없는 기본생성자
	
	public BoardVO(String seq, String fk_empno, String name, String subject, String content, String pw,
			  String readCount, String regDate, String status,
			  String groupno, String fk_seq, String depthno,
			  String fileName, String orgFilename, String fileSize) {
		
		this.seq = seq;
		this.fk_empno = fk_empno;
		this.name = name;
		this.subject = subject;
		this.content = content;
		this.pw = pw;
		this.readCount = readCount;
		this.regDate = regDate;
		this.status = status;
		this.groupno = groupno;
		this.fk_seq = fk_seq;
		this.depthno = depthno;
		this.fileName = fileName;
		this.orgFilename = orgFilename;
		this.fileSize = fileSize;
		
		
	}
	public  String getSeq() {
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
	
	public String getPw() {
		return pw;
	}
	
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public String getReadCount() {
		return readCount;
	}
	
	public void setReadCount(String readCount) {
		this.readCount = readCount;
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
	
	// 댓글쓰기
	public String getCommentCount() {
		return commentCount;
	}
	
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	
	// 답변글쓰기
	public String getGroupno() {
		return groupno;
	}
	
	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}
	
	public String getFk_seq() {
		return fk_seq;
	}
	
	public void setFk_seq(String fk_seq) {
		this.fk_seq = fk_seq;
	}
	
	public String getDepthno() {
		return depthno;
	}
	
	public void setDepthno(String depthno) {
		this.depthno = depthno;
	}
	
	// 파일첨부
	public MultipartFile getAttach() {
		return attach;
	}
	
	public void setAttach(MultipartFile attach) {
		this.attach = attach;
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
	
	
    
    
    
    
	
}
