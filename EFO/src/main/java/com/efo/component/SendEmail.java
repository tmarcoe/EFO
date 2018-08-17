package com.efo.component;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {

	@Autowired
	private JavaMailSender sender;

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
		

		helper.setTo(to);
		message.setContent(content, "text/html; charset=utf-8" );

		helper.setSubject(subject);
		helper.setFrom(from, name);

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
