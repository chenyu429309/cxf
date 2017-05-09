package com.pegatron.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtils {
	public static void SendMail(String TO,String From,String Subject,String Text) throws MessagingException{
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
		  
        // 设定mail server  
        senderImpl.setHost("mail.sh.pegatroncorp.com");  
  
        // 建立邮件消息,发送简单邮件和html邮件的区别  
        MimeMessage mailMessage = senderImpl.createMimeMessage();  
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);  
  
        // 设置收件人，寄件人  
        messageHelper.setTo(TO);  //"Felix5_Wang@intra.pegatroncorp.com"
        messageHelper.setFrom(From); //"Felix5_Wang@intra.pegatroncorp.com"
        messageHelper.setSubject(Subject);  //"测试HTML邮件！"
        // true 表示启动HTML格式的邮件  
        messageHelper  
                .setText(  
                       Text ,  
                        true);  //"<html><head></head><body><h1>hello!!spring html Mail</h1></body></html>"
  
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
	public static void SendMangMail(String[] TO,String[] Cc,String From,String Subject,String Text) throws MessagingException, UnsupportedEncodingException{
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
		  
        // 设定mail server  
        senderImpl.setHost("mail.sh.pegatroncorp.com");  
  
        // 建立邮件消息,发送简单邮件和html邮件的区别  
        MimeMessage mailMessage = senderImpl.createMimeMessage();  
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"GBK");  
  
        // 设置收件人，寄件人  
        messageHelper.setTo(TO);  //"Felix5_Wang@intra.pegatroncorp.com"
        messageHelper.setCc(Cc);
        messageHelper.setFrom(From);  //"Felix5_Wang@intra.pegatroncorp.com"
        messageHelper.setSubject(Subject);  //"测试HTML邮件！"
        // true 表示启动HTML格式的邮件  
        messageHelper  
                .setText(  
                	      Text,  
                        true);  //  "<html><head></head><body><h1>hello!!spring html Mail</h1></body></html>"
  
        senderImpl.setUsername("Felix5_Wang"); // 根据自己的情况,设置username  
        senderImpl.setPassword("xiao!429"); // 根据自己的情况, 设置password  
        Properties prop = new Properties();  
        prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确  
        prop.put("mail.smtp.timeout", "250000000");  
        senderImpl.setJavaMailProperties(prop);  
        // 发送邮件  
        senderImpl.send(mailMessage);  
  
        System.out.println("邮件发送成功..");  
	}	
}
