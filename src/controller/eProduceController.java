package controller;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.JTextField;

import model.Listing;
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
		String host = "smtp.gmail.com";
		//username and password of gmail acc created for this application
		String username = "eProduceSystem@gmail.com";
		String password = "AndrewAntonioNickSean";
		
		//Set up Gmail services (with authentication) to work with java mail
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.user", username);
		properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
		Session session = Session.getDefaultInstance(properties, 
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication(){
						return new PasswordAuthentication(username, password);
					}
		});
		
		//If authentication works
		try{
			MimeMessage msg = new MimeMessage(session);
			//adds who is sending this email
			msg.setFrom(new InternetAddress(from));
			//adds the who this email is going to
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			//adds the subject to email
			msg.setSubject(subject);
			//adds body to email
			msg.setText(content);
			
			//Send email
			Transport.send(msg);
			System.out.println("Email sent successfully!");
		}
		//authentication did not work
		catch (MessagingException e){
			JOptionPane.showMessageDialog(null, "Invalid email. Email not sent.");
		}
	}
	
	public void searchListings(ArrayList<Listing> listings, String search){
		ArrayList<Listing> temp = new ArrayList<Listing>();
		for(int i=0; i<listings.size(); i++){
			String tags = listings.get(i).getTags();
			StringTokenizer st = new StringTokenizer(tags, ", ");
			String token = "";
			while(st.hasMoreTokens()){
				token = st.nextToken();
				if(search.contains(token)){
					temp.add(listings.get(i));
					break;
				}
			}
		}
	}
	
}
