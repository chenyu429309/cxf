package com.pegatron.utils;
	  
	import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;  
	import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;  
	  
	/** 
	 * 本类测试简单邮件 直接用邮件发送 
	 *  
	 * @author Administrator 
	 *  
	 */  
	public class SingleMailSend  
	{  
//	    public static void main(String args[])  
//	    {  
//	        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
//	        // 设定mail server  
//	        senderImpl.setHost(" stmp.pegatroncorp.com ");  
//	        // 建立邮件消息  
//	        SimpleMailMessage mailMessage = new SimpleMailMessage();  
//	        // 设置收件人，寄件人 用数组发送多个邮件  
//	        // String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};  
//	        // mailMessage.setTo(array);  
//	        mailMessage.setTo(" Felix5_Wang@intra.pegatroncorp.com ");  
//	        mailMessage.setFrom(" Felix5_Wang@intra.pegatroncorp.com ");  
//	        mailMessage.setSubject(" 测试简单文本邮件发送！ ");  
//	        mailMessage.setText(" 测试我的简单邮件发送机制！！ ");  
//	  
//	        senderImpl.setUsername(" Felix5_Wang "); // 根据自己的情况,设置username  
//	        senderImpl.setPassword(" xiao!429 "); // 根据自己的情况, 设置password  
//	  
//	        Properties prop = new Properties();  
//	        prop.put(" mail.smtp.auth ", " true "); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确  
//	        prop.put(" mail.smtp.timeout ", " 25000 ");  
//	        senderImpl.setJavaMailProperties(prop);  
//	        // 发送邮件  
//	        senderImpl.send(mailMessage);  
//	  
//	        System.out.println(" 邮件发送成功.. ");  
//	    }  
		
		public static void main(String[] args) throws Exception  
	    {  
	        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
	  
	        // 设定mail server  
	        senderImpl.setHost("mail.sh.pegatroncorp.com");  
	  
	        // 建立邮件消息,发送简单邮件和html邮件的区别  
	        MimeMessage mailMessage = senderImpl.createMimeMessage();  
	        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);  
	  
	        // 设置收件人，寄件人  
	        String[] ss={"Felix5_Wang@intra.pegatroncorp.com","Felix5_Wang@intra.pegatroncorp.com"};
	       
	        messageHelper.setTo(ss); 
	        messageHelper.setCc(ss);
	        messageHelper.setFrom("Felix5_Wang@intra.pegatroncorp.com");  
	        messageHelper.setSubject("测试HTML邮件！");  
	        // true 表示启动HTML格式的邮件  
	        messageHelper  
	                .setText(  
	                        "<html><head></head><body><h1>hello!!spring html Mail</h1></body></html>",  
	                        true);  
	  
	        senderImpl.setUsername("Felix5_Wang"); // 根据自己的情况,设置username  
	        senderImpl.setPassword("xiao!429"); // 根据自己的情况, 设置password  
	        Properties prop = new Properties();  
	        prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确  
	        prop.put("mail.smtp.timeout", "25000");  
	        senderImpl.setJavaMailProperties(prop);  
	        // 发送邮件  
	        senderImpl.send(mailMessage);  
	  
	        System.out.println("邮件发送成功..");  
	    }  
	}  
	  



