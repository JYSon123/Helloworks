package com.spring.helloworks.common;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

//=== #184. Spring Scheduler(스프링스케줄러6) === 
//=== Spring Scheduler(스프링 스케줄러)를 사용한 email 발송하기 === 
//=== Google메일서버를 사용할 수 있도록 Google email 계정 및 암호 입력하기 ===
public class MySMTPAuthenticator_HJE extends Authenticator {

	@Override
	public PasswordAuthentication getPasswordAuthentication() {

		// Gmail 의 경우 @gmail.com 을 제외한 아이디만 입력한다.
//		return new PasswordAuthentication("cclass1124", "tclklzbgjkbxifvd");
		return new PasswordAuthentication("helloworks3", "qwer1234$");
	}

}
