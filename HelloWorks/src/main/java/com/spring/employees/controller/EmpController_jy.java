package com.spring.employees.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.employees.model.FileManager_sj;
import com.spring.employees.service.InterEmpService_jy;

@Controller
public class EmpController_jy {
	
	
	@Autowired  // 의존객체 설정
	private InterEmpService_jy service;
	
	// === 파일업로드 및 다운로드를 해주는 FileManager_sj클래스 의존객체 주입하기 === 
	@Autowired
	private FileManager_sj fileManager;
	
	
	// 보드 jsp 확인용 메소드
	@RequestMapping(value="/documentMain.hello2")
    public String board(HttpServletRequest request) {
      
       return "document/documentMain.tiles1";   
    }
	
	
	// 보드 jsp 확인용 메소드
	@RequestMapping(value="/write.hello2")
    public String write(HttpServletRequest request) {
      
       return "document/add.tiles1";   
    }
	
	
}
