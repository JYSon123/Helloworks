package com.spring.employees.model;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

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
	}


}
