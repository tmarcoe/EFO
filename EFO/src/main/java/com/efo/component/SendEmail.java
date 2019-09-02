package com.efo.component;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {

	@Autowired
	JavaMailSender sender;

	public void sendMail(String from, String to, String name, String subject, String content) throws MessagingException, UnsupportedEncodingException {

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setTo(to);
		helper.setText(content);
		helper.setSubject(subject);
		helper.setFrom(from, name);

		sender.send(message);
	}

	public void sendHtmlMail(String from, String to, String name, String subject, String content) throws MessagingException, UnsupportedEncodingException {

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		message.setContent(content, "text/html; charset=utf-8");

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setFrom(from, name);

		sender.send(message);
	}

	public void sendHtmlMailWithAttachment(String from, String to, String name, String subject, String content, String filePath)
			throws MessagingException, IOException {
		
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);		

		//message.setContent(content, "text/html; charset=utf-8");

		helper.setTo(to);
		helper.setFrom(from, name);
		helper.setSubject(subject);
		helper.setText(content);

		FileSystemResource file = new FileSystemResource(new File(filePath));
		
		helper.addAttachment("salesReceipt.PDF", file);

		sender.send(message);
	}

	public void sendMail(String from, String[] to, String subject, String content) throws MessagingException {

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setTo(to);
		helper.setText(content, "text/html");
		helper.setSubject(subject);
		helper.setFrom(from);

		sender.send(message);
	}

	public void sendMail(String from, String to, String subject, String content, ClassPathResource file, String type) throws MessagingException {

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setTo(to);
		helper.setText(content);
		helper.setSubject(subject);
		helper.setFrom(from);
		helper.addAttachment(type, file);
		sender.send(message);
	}
}
