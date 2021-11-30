package com.spring.employees.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

// === FileManager 클래스 생성하기 === 
@Component
public class FileManager_sj {

	// === 파일 업로드 하기 첫번째 방법 ===
	// byte[] bytes : 파일의 내용물
	// String originalFilename : 첨부된 파일의 원래 이름
	// String path : 업로드할 파일의 저장경로
	// 리턴값 : 서버에 저장된 새로운 파일명
	public String doFileUpload(byte[] bytes, String originalFilename, String path) throws Exception {
		
		String newFilename = null;
		
		if(bytes == null) { // 내용물이 없다면
			return null;
		}
		
		if("".equals(originalFilename)) { // 원본파일이 없다면
			return null;
		}
		
		String fileExt = originalFilename.substring(originalFilename.lastIndexOf("."));
		if(fileExt == null || "".equals(newFilename)) {
			return null;
		}
		
		
		// 서버에 저장할 새로운 파일명을 만든다.
		// 서버에 저장할 새로운 파일명이 동일한 파일명이 되지 않고 고유한 파일명이 되도록 하기 위해
		// 현재의 년월일시분초에다가 현재 나노세컨즈nanoseconds 값을 결합하여 확장자를 붙여서 만든다.
		newFilename = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		newFilename += System.nanoTime();
		newFilename += fileExt;
		
		// 업로드할 경로가 존재하지 않는 경우 폴더를 생성한다.
		File dir = new File(path);
		// 파리미터로 입력받은 문자열인 path(파일저장 경로)를 실제 폴더로 만든다.
		// 자바에서는 File 클래스를 사용하여 폴더 또는 파일을 생성 및 관리하게 된다.
		
		if(!dir.exists() ) {
			// 파일을 저장할 실제 폴더가 존재하지 않는다면
			
			dir.mkdir(); // 파일을 저장할 경로의 폴더를 생성한다.
		}
		
		
		String pathname = path + File.separator + newFilename;
						      // File.separator는 운영체제에서 사용하는 파일경로의 구분자이다.
		
		FileOutputStream fos = new FileOutputStream(pathname);
		// FileOutputStream은 해당 경로 파일명(pathname)에 실제로 데이터 내용(byte[] bytes)을 기록해주는 클래스이다.
		// 이러한 일을 하는 FileOutputStream 객체 fos 생성
		
		fos.write(bytes);
		// 이 메소드가 해당 경로 파일명(pathname)에 실제로 데이터 내용을 기록해주는 일을 하는 것이다.
		
		fos.close();
		// 생성된 FileOutputStream 객체 fos가 더이상 사용되지 않도록 소멸시킨다.
		
		
		return newFilename;
	
	
	}// end of public String doFileUpload(byte[] bytes, String originalFilename, String path)-----------------------
	
	
	
	
	// === 파일 다운로드 하기 === // 
	// saveFilename : 서버에 저장된 파일명(현재의 년월일시분초에다가 현재 나노세컨즈 값을 결합하여 확장자를 붙여서 만든 것)
	// originalFilename : 클라이언트가 업로드한 파일명(파일명이 영어로 되어진 경우도 있지만 한글로 되어진 경우도 있다는 것에 유의하자)
	// path : 서버에 저장된 경로
	public boolean doFileDownload(String saveFileName, String originalFilename, String path, HttpServletResponse response) {
		
		String pathname = path + File.separator + saveFileName;
		// File.seperator는 운영체제에서 사용하는 파일경로의 구분자이다.
		// 해당경로에 \를 더하고 파일명을 더한 경로까지 나타내준 파일명(문자열)을 만든다.
	
		try {
			
			if(originalFilename == null || "".equals(originalFilename) ) {
				originalFilename = saveFileName;	
			}
			
			originalFilename = new String(originalFilename.getBytes("UTF-8"), "8859_1");
			// originalFilename.getBytes("UTF-8")은 UTF-8 형태로 되어진 문자열 originalFilename 을 byte 형태로 변경한 후
			// byte 형태로 되어진 것을 표준인 ISO-Latin1(혹은 Latin1 또는 8859_1) 형태로 인코딩한 문자열로 만든다.
		} catch (UnsupportedEncodingException e) {}
		
		
		try {
			
			File file = new File(pathname);
			// 다운로드 할 파일명(pathname)을 가지고 File 객체를 생성한다.
			
			if(file.exists()) { // 실제로 다운로드할 해당 파일이 존재한다면
				
				response.setContentType("application/octet-stream");
				// 다운로드할 파일의 종류에 따라 Content-Type을 지정해줘야 한다.
				// 기타 인코딩된 모든 파일들은 "application/octet-stream"으로 지정해줘야 한다.
				
				response.setHeader("Content-disposition",
						           "attachment; filename=" + originalFilename);
				
				byte[] readByte = new byte[4096];
				// 다운로드할 파일의 내용을 읽어오는 단위크기를 4096 byte로 하는 byte배열 readByte를 생성한다.
				
				BufferedInputStream bfin = new BufferedInputStream(new FileInputStream(file));
				
				ServletOutputStream souts = response.getOutputStream();
				// ServletOutputStream은 다운로드 되어질 파일을 클라이언트로 보내어주는 출력 스트림용
				
				int length = 0;
				
				while( (length = bfin.read(readByte, 0, 4096)) != -1 ) {
					
					souts.write(readByte, 0, length);
					/*
					 	readByte에 저장된 내용을 처음부터 읽어온 크기인 length만큼
					 	ServletOutputStream souts에 기록(저장)해둔다.
					 */				
				}// end of while----------------------
				
				souts.flush(); // ServletOutputStream souts에 기록(저장)해둔 내용을 클라이언트로 내보낸다.
				
				souts.close(); // ServletOutputStream souts 객체를 소멸시킨다.
				bfin.close();  // BufferedInputStream bfin 객체를 소멸시킨다.
				
				return true;   // 다운로드 해줄 파일이 존재하고 Exception이 발생하지 않으면 true를 리턴시킨다.
								
			}// end of if---------------------
		
		} catch(Exception e) {}
			
		return false;
			
	}
		
	

	// === 파일 삭제하기 === //
	public void doFileDelete(String saveFileName, String path) throws Exception {

		String pathname = path + File.separator + saveFileName;
		
		File file = new File(pathname);
		
		if(file.exists()) { // 파일이 존재한다면 삭제해라
			file.delete();
		}
	}

	

}
