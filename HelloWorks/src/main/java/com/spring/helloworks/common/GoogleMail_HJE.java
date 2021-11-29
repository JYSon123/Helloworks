package com.spring.helloworks.common;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.stereotype.Component;

//=== #183. Spring Scheduler(스프링스케줄러5) === 
//=== Spring Scheduler(스프링 스케줄러)를 사용한 email 발송하기 === 
//=== email을 보내주는 클래스 생성하기 ===
@Component
public class GoogleMail_HJE {

	public void sendmail_Reservation(String recipient, String emailContents) throws Exception {

		// 1. 정보를 담기 위한 객체
		Properties prop = new Properties();

		// 2. SMTP 서버의 계정 설정
		// Google Gmail 과 연결할 경우 Gmail 의 email 주소를 지정
//		prop.put("mail.smtp.user", "cclass1124@gmail.com");
		prop.put("mail.smtp.user", "helloworks3@gmail.com");

		// 3. SMTP 서버 정보 설정
		// Google Gmail 인 경우 smtp.gmail.com
		prop.put("mail.smtp.host", "smtp.gmail.com");

		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.debug", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.socketFactory.fallback", "false");

		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Authenticator smtpAuth = new MySMTPAuthenticator_HJE();
		Session ses = Session.getInstance(prop, smtpAuth);

		// 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
		ses.setDebug(true);

		// 메일의 내용을 담기 위한 객체생성
		MimeMessage msg = new MimeMessage(ses);

		// 제목 설정
		String subject = "HelloWorks에 등록된 일정 알림메일입니다. ";
		msg.setSubject(subject);

		// 보내는 사람의 메일주소
//		String sender = "cclass1124@gmail.com";
		String sender = "helloworks3@gmail.com";
		Address fromAddr = new InternetAddress(sender);
		msg.setFrom(fromAddr);

		// 받는 사람의 메일주소
		Address toAddr = new InternetAddress(recipient);
		msg.addRecipient(Message.RecipientType.TO, toAddr);

		// 메시지 본문의 내용과 형식, 캐릭터 셋 설정
		msg.setContent("<div style='font-size:14pt;'>" + emailContents + "</div>", "text/html;charset=UTF-8");

		// 메일 발송하기
		Transport.send(msg);

	}// end of sendmail_Reservation(String recipient, String
		// emailContents)-------------------
	
	
	
	
	
}
