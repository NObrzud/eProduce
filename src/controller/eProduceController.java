package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.Feedback;
import model.Listing;
import model.User;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import view.AdminMainPageView;
import view.MainPageView;
import view.SignUpView;
import view.StartView;


public class eProduceController {
	private User userModel;
	
	public eProduceController()
	{
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
	public static boolean validateLogin(User model, JTextField user, JPasswordField pass)
	{
		return (eProduceDatabase.validateLogin(model, user.getText(), pass.getText()));
	}
	@SuppressWarnings("deprecation")
	public static  boolean createNewAccount(JTextField firstNameTF, JTextField lastNameTF, JTextField emailTF,
									   JPasswordField passwordPF, JPasswordField rePasswordPF) 
	{
		return eProduceDatabase.createAccount(firstNameTF.getText(), lastNameTF.getText(), emailTF.getText(), passwordPF.getText());
	}
	public static boolean updateAccount(String first, String last, String email, String pass, String rePass) {
		
		return eProduceDatabase.updateAccount(first, last, email, pass, rePass);
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
	
	public static void sendEmail(String recipient, String sender, String subject, String content){
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
	
	public static void searchListings(JFrame frame, ArrayList<Listing> listings, String[][] listingData, JTable table, String search, String[] columnHeadings, User currentUser){
		ArrayList<Listing> temp = new ArrayList<Listing>();
		for(int i=0; i<listings.size(); i++){
			String tags = listings.get(i).getTags();
			StringTokenizer st = new StringTokenizer(tags, ", ");
			String token = "";
			boolean contains = false;
			while(st.hasMoreTokens()){
				contains = false;
				token = st.nextToken();
				System.out.print(token + " == " + search + "?");
				if(search.contains(token)){
					System.out.println(" yes"); contains = true;
					temp.add(listings.get(i));
					System.out.println("Listing #: " + listings.get(i).getListingNum());
					break;
				}
				else {
					System.out.println(" no");
				}
			}
			if(!contains)
			{	
				if(table.getRowCount() > 0)
				((DefaultTableModel)table.getModel()).removeRow(i);

			}
		}
		String[][] tempData = new String[temp.size()][3]; 
		for(int i = 0; i < tempData.length; i++)
		{
			Listing currListing = temp.get(i);
			tempData[i][0] = currListing.getListingNum();
			tempData[i][1] = currListing.getOwner().getEmail();
			tempData[i][2] = currListing.getTitle();
		}
		listings = temp;
		listingData = tempData;
		table.getSelectionModel().addListSelectionListener(eProduceActionListeners.createAllListingsTableListener(frame, listingData, table, listings, currentUser));
	}
	/*
	 * To be used in MainPageView. Creates the Table of listings to be shown in the middle panel
	 */
	public static JTable createListingTable(JFrame frame, ArrayList<Listing> list, String[][] listingData, String[] columnHeadings, User currentUser) {
		DefaultTableModel model = new DefaultTableModel(listingData, columnHeadings);
		JTable table = new JTable(model);
		table.setBackground(frame.getBackground());
		table.setShowVerticalLines(false);
		table.setGridColor(Color.black);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setFont(new Font("Serif", Font.PLAIN, 24));
		table.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
		table.setRowHeight(30);
		table.setDefaultEditor(Object.class, null);
		
		ListSelectionListener tableListener = eProduceActionListeners.createAllListingsTableListener(frame,listingData, table, list, currentUser);
		
		for(int i = 0; i < table.getColumnCount(); i++)
		{
			DefaultTableCellRenderer center = new DefaultTableCellRenderer();
			center.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setCellRenderer(center);
			if(i == 0)
			{
				column.setMinWidth(75);
				column.setPreferredWidth(75);
				column.setMaxWidth(100);
			}
			if(i == 1)
			{
				column.setMaxWidth(300);
			}
			column.setPreferredWidth(348);
		}
		
		table.getSelectionModel().addListSelectionListener(tableListener);
		return table;
	}
	public static JTable createFeedbackTable(JFrame frame, ArrayList<Feedback> feedback, ArrayList<Listing> list, String[] columnHeadings, User currentUser) {
		String[][] feedbackData;
		if(feedback.size()>0)
			feedbackData = new String[feedback.size()][3];
		else
			feedbackData = new String[0][0];
		
		for(int i = 0; i < feedbackData.length; i++)
		{
			feedbackData[i][0] = feedback.get(i).getFeedbackNum();
			feedbackData[i][1] = feedback.get(i).getOwner();
			feedbackData[i][2] = feedback.get(i).getContent();
		}
		DefaultTableModel model = new DefaultTableModel(feedbackData, columnHeadings);
		JTable feedbacktbl = new JTable(model);
		feedbacktbl.setShowVerticalLines(false);
		feedbacktbl.setGridColor(Color.black);
		feedbacktbl.setIntercellSpacing(new Dimension(0, 0));
		feedbacktbl.setFont(new Font("Serif", Font.PLAIN, 15));
		feedbacktbl.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
		feedbacktbl.setRowHeight(25); 
		feedbacktbl.setFillsViewportHeight(true);
		feedbacktbl.setPreferredScrollableViewportSize(feedbacktbl.getPreferredSize());
		feedbacktbl.setDefaultEditor(Object.class, null);
		
		ListSelectionListener feedbackListener = eProduceActionListeners.createFeedbackTableListener(frame, feedbackData,  feedbacktbl, feedback, currentUser);
		feedbacktbl.getSelectionModel().addListSelectionListener(feedbackListener);
		
		return feedbacktbl;
	}
}
