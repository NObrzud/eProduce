package controller;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


import javax.swing.JPasswordField;

import javax.swing.JTextField;

import model.User;


public class eProduceController {
	private User userModel;
	private eProduceDatabase db;
	
	public eProduceController()
	{
		db = new eProduceDatabase();
	}
	public eProduceController(User model)
	{
		userModel = model;
	}
	/*
	 * Tests the entered username and password against the database. 
	 * NOTE: username and password are both case-sensitive, e.g. "Test" != "test"
	 */
	@SuppressWarnings("deprecation")
	public boolean validateLogin(User model, JTextField user, JPasswordField pass)
	{
		return (db.validateLogin(model, user.getText(), pass.getText()));
	}
	@SuppressWarnings("deprecation")
	public  boolean createNewAccount(JTextField firstNameTF, JTextField lastNameTF, JTextField emailTF,
									   JPasswordField passwordPF, JPasswordField rePasswordPF) 
	{
		return db.createAccount(firstNameTF.getText(), lastNameTF.getText(), emailTF.getText(), passwordPF.getText());
	}
	public boolean updateAccount(String first, String last, String email, String pass, String rePass) {
		
		return db.updateAccount(first, last, email, pass, rePass);
	}
	public static boolean validateEmail(String emailAddress)
	{
		
		//check to ensure . symbol is not the first or last character of emailAddress
		int addressLength = emailAddress.length();
		if((emailAddress.charAt(0) == '.') || (emailAddress.charAt(addressLength-1) == '.'))
			return false;
		
		
		//check for one and only one @ sign and ensure it is not at beginning or end of emailAddress
		if((emailAddress.charAt(0) == '@') || (emailAddress.charAt(addressLength-1) == '@'))
			return false;

		int at_symbol_count_position=0;
		int at_symbol_count=0;
		for(int i=0; i<addressLength; i++)
		{
			if(emailAddress.charAt(i) == '@')
			{
				at_symbol_count_position=i;
				if(emailAddress.charAt(i+1) == '.')// the . symbol can not be the next char after @ symbol
					return false;
				at_symbol_count++;
			}
				
		}
		if(at_symbol_count != 1)//there can only be one @ symbol
			return false;
		
		
		//check to make sure at least one . symbol occurs after the @ symbol
		int periodCount=0;
		for(int j=at_symbol_count_position; j<addressLength; j++)
		{
			if(emailAddress.charAt(j) == '.')
				periodCount++;
		}
		if(periodCount!=0)
			return true;
		else
			return false;
		
	}
	
	public void sendEmail(String recipient, String sender, String subject, String content){
		String to = recipient;
		String from = sender;
		String host = "localhost";
		String username = "eProduceSystem@gmail.com";
		String password = "AndrewAntonioNickSean";
		String emailMsg = "Hello eProduce User! According to our system, there is a user that would like to contact you in regards to your listing. "
				+ "The following is a message from " + "**enter name here" +":\n"
						+ "\n" + content +"\n"
						+ "\n If you would like to contact **enter name here, you may contact him using the following email address: " + sender;
		
		//getting session object 
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
		Session session = Session.getDefaultInstance(properties, 
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication(){
						return new PasswordAuthentication(username, password);
					}
		});
		
		try{
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(subject);
			msg.setText(emailMsg);
			
			//Send email
			Transport.send(msg);
			System.out.println("Email sent successfully!");
		}
		catch (MessagingException e){
			e.printStackTrace();
		}
	}
}
