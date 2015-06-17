package com.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMAil {
	
	public static void mailcall(String Jid, String Aid, String Token, String Status) {
		
		Properties properties=new Properties();
		try {
			properties.load(new FileInputStream(new File("path.ini")));
		} catch (IOException e) {
			System.out.println("Exception in reading ini file : " + e);			
		}	

	final String username = properties.getProperty("frommailid");
	final String password = properties.getProperty("emailpass");
	final String toreceipent = properties.getProperty("Torecepientmail");
	final String hostname = properties.getProperty("Hostname");

	//	final String username = "transhrm@gmail.com";
	//	final String password = "Technet1";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 //mahe@transforma.in
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("transhrm@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toreceipent));
			message.setSubject("Online Proofing center");
			
			if(Status == "success") {
			message.setText("Dear Client,"
				+ "\n\n Article generated Successfully for the article.\n["+ Jid+"/"+ Aid 
				+"]\n http://"+ hostname +"/ETAPS/Home.xhtml?token="+ Token);
			}
			
			if(Status == "already-generated") {
				message.setText("Dear Client,"
						+ "\n\n Article alreay generated for the article.\n"+ Token);
			}
 			
			Transport.send(message);
 
			System.out.println("Mail Sent......");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
