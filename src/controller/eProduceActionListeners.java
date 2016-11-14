package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Ticket;
import model.User;
import view.AdminMainPageView;
import view.MainPageView;
import view.MyListingsView;
import view.MyMeetingsView;
import view.SignUpView;
import view.StartView;
import view.TicketView;
/*
 * This class handles all ActionListener and ListSelectionListener creation to be passed back to the respective views. 
 * Take note of grouping based on class the method will be utilized in.
 */
public class eProduceActionListeners {
	
	//*********************************START OF GENERAL ACTIONLISTENERS**********************************************
	public static ActionListener createLogoutActionListener(JFrame frame, User currentUser)
	{
		ActionListener logoutActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				StartView start = new StartView(currentUser);
				start.frame.setVisible(true);
				
			}
		};
		return logoutActionListener;
	}
	
	public static ActionListener createMyAccountActionListener(JFrame frame, User currentUser) 
	{
		ActionListener myAccountActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel accPanel = new JPanel();
				accPanel.setLayout(new GridLayout(0,1));
				JTextField fname = new JTextField(currentUser.getFirstName(),20);
				JTextField lname = new JTextField(currentUser.getLastName(),20);
				JTextField email = new JTextField(currentUser.getEmail(),20);
				JPasswordField password = new JPasswordField(currentUser.getPassword(),20);
				password.setEchoChar('*');
				JPasswordField passConfirm = new JPasswordField(currentUser.getPassword(),20);
				passConfirm.setEchoChar('*');
				accPanel.add(new JLabel("First Name:"));
				accPanel.add(fname);
				accPanel.add(new JLabel("Last Name:"));
				accPanel.add(lname);
				accPanel.add(new JLabel("Email:"));
				accPanel.add(email);
				accPanel.add(new JLabel("Password:"));
				accPanel.add(password);
				accPanel.add(new JLabel("Confirm Password:"));
				accPanel.add(passConfirm);
				
				int result = JOptionPane.showConfirmDialog(null, accPanel, "Edit Account Info", JOptionPane.OK_CANCEL_OPTION);
				if(result == JOptionPane.OK_OPTION)
				{

					if(!(fname.getText().equals("")) && !(lname.getText().equals("")) && !email.getText().equals("")  //if firstname, lastname, email, password, confirm pass 
							   && !(password.getText().equals("")) && password.getText().equals(passConfirm.getText()))			   //are not null, and password and confirm pass are equal...
						{
							// if no changes are made by user to account info, nothing happens
							if(currentUser.getFirstName().equals(fname.getText()) &&
									currentUser.getLastName().equals(lname.getText()) &&
									currentUser.getEmail().equals(email.getText()) &&
									currentUser.getPassword().equals(password.getText())){
								JOptionPane.showMessageDialog(frame, "No changes were made.");
								
							}
							else if(eProduceController.updateAccount(fname.getText(), lname.getText(), email.getText(), password.getText(), passConfirm.getText()))
							{
								JOptionPane.showMessageDialog(frame, "Account has been successfully updated! Please login again.");
								frame.dispose();
								StartView sv = new StartView(currentUser);					
								sv.frame.setVisible(true);
							}
							else
							{
								JOptionPane.showMessageDialog(frame, "Could not connect to database. Please check internet access"); //temporary way to handle db-side account failing
							}
						}
					else
					{
						String emptyFieldMsg = "Unable to create account. The following fields are empty: \n";
						if(fname.getText().equals("")) emptyFieldMsg += "      First Name\n";
						if(lname.getText().equals("")) emptyFieldMsg += "      Last Name\n";
						if(email.getText().equals("")) emptyFieldMsg += "      Email\n";
						if(password.getText().equals("")) emptyFieldMsg += "      Password\n";
						if(passConfirm.getText().equals("")) emptyFieldMsg += "      Confirm Password\n";
						if(!password.getText().equals(passConfirm.getText())) emptyFieldMsg = "Unable to create account. Passwords do not match.";
						JOptionPane.showMessageDialog(frame, emptyFieldMsg);
					}
				}
				
			}
		};
		return myAccountActionListener;
	}
	
	public static ActionListener createHomeActionListener(JFrame frame, User currentUser)
	{
		ActionListener homeActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainPageView mpv = new MainPageView(currentUser);
				mpv.frame.setVisible(true);
				
			}
		};
		return homeActionListener;
	}
	
	public static ActionListener createMyMeetingsActionListener(JFrame frame, User currentUser)
	{
		ActionListener myMeetingsActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyMeetingsView mlv = new MyMeetingsView(currentUser);
				mlv.frame.setVisible(true);
				
			}
		};
		return myMeetingsActionListener;
	}
	
	public static ActionListener createMyListingsActionListener(JFrame frame, User currentUser)
	{
		ActionListener myListingsActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyListingsView mlv = new MyListingsView(currentUser);
				mlv.frame.setVisible(true);
				
			}
		};
		return myListingsActionListener;
	}
	
	public static ActionListener createCreateTicketActionListener(JFrame frame, User currentUser)
	{
		ActionListener createTicketActionListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JPanel lstPanel = new JPanel();
				JPanel top = new JPanel();
				JPanel bottom = new JPanel();
				JLabel des = new JLabel();
				JTextArea destxt = new JTextArea(5,10);
				Border border = BorderFactory.createLineBorder(Color.BLACK);
				lstPanel.setLayout(new BorderLayout());
				top.setLayout(new GridLayout(0,1));
				bottom.setLayout(new BorderLayout());
				
				des.setText("Description of Issue:");
				destxt.setLineWrap(true);
				destxt.setBorder(border);
				JScrollPane sp = new JScrollPane(destxt);
				bottom.add(des,BorderLayout.NORTH);
				bottom.add(sp,BorderLayout.SOUTH);
				lstPanel.add(top,BorderLayout.NORTH);
				lstPanel.add(bottom,BorderLayout.SOUTH);
			
				
				int result = JOptionPane.showConfirmDialog(null, lstPanel, "Create Ticket Info", JOptionPane.OK_CANCEL_OPTION);
				if(result == JOptionPane.OK_OPTION)
				{
					if(!destxt.getText().equals(""))	
					{
						if(eProduceDatabase.createTicket(currentUser.getEmail(), destxt.getText()))
						{
							String msg = "Ticket created!";
							JOptionPane.showMessageDialog(frame, msg);
							frame.dispose();
							TicketView tv = new TicketView(currentUser);
							tv.frame.setVisible(true);
						}
						else
						{
							String msg = "Unable to create ticket. Database error.";
							JOptionPane.showMessageDialog(frame, msg);
						}
					}
					else
					{
						String emptyFieldMsg = "Unable to create ticket. The following fields are empty: \n";
						if(destxt.getText().equals("")) emptyFieldMsg += "      Description\n";
						JOptionPane.showMessageDialog(frame, emptyFieldMsg);
						
					}
					
				}
				
			}
		};
		return createTicketActionListener;
	}
	//*********************************END OF GENERAL ACTIONLISTENERS************************************************
	
	//*********************************START OF AdminMainPageView.java ACTIONLISTENERS*******************************
	
	//*********************************END OF AdminMainPageView.java ACTIONLISTENERS*********************************
	
	//*********************************START OF MainPageView.java ACTIONLISTENERS************************************
	
	//*********************************END OF MainPageView.java ACTIONLISTENERS**************************************

	//*********************************START OF MyListingsView.java ACTIONLISTENERS**********************************
	
	//*********************************END OF MyListingsView.java ACTIONLISTENERS************************************

	//*********************************START OF MyMeetingsView.java ACTIONLISTENERS**********************************
	
	//*********************************END OF MyMeetingsView.java ACTIONLISTENERS************************************

	//*********************************START OF SignUpView.java ACTIONLISTENERS**************************************

	public static ActionListener createCancelActionListener(JFrame frame, User currentUser)
	{
		ActionListener cancelActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				StartView sv = new StartView(currentUser);
				sv.frame.setVisible(true);
			}	
		};
		return cancelActionListener;
	}
	
	public static ActionListener createSubmitActionListener(JFrame frame, JTextField firstNameTF, JTextField lastNameTF, JTextField emailTF, JPasswordField passwordPF, JPasswordField rePasswordPF, User currentUser)
	{
		ActionListener submitActionListener = new ActionListener()
		{
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e)
			{
				if(!(firstNameTF.getText().equals("")) && !(lastNameTF.getText().equals("")) && !emailTF.getText().equals("")  //if firstname, lastname, email, password, confirm pass 
						   && !(passwordPF.getText().equals("")) && passwordPF.getText().equals(rePasswordPF.getText()))			   //are not null, and password and confirm pass are equal...
				{
					if(eProduceController.validateEmail(emailTF.getText()))
					{
						if(eProduceController.createNewAccount(firstNameTF, lastNameTF, emailTF, passwordPF, rePasswordPF))
						{
							JOptionPane.showMessageDialog(frame, "Account has been successfully created!");
							frame.dispose();
							StartView sv = new StartView(currentUser);
							sv.frame.setVisible(true);
						}
					}
					else if(!eProduceController.validateEmail(emailTF.getText()))
					{
						JOptionPane.showMessageDialog(frame, "Error invalid email address"); 
					}
					else
					{
						JOptionPane.showMessageDialog(frame, "Could not connect to database. Please check internet access"); 
					}
				}
				else
				{
					String emptyFieldMsg = "Unable to create account. The following fields are empty: \n";
					if(firstNameTF.getText().equals("")) emptyFieldMsg += "      First Name\n";
					if(lastNameTF.getText().equals("")) emptyFieldMsg += "      Last Name\n";
					if(emailTF.getText().equals("")) emptyFieldMsg += "      Email\n";
					if(passwordPF.getText().equals("")) emptyFieldMsg += "      Password\n";
					if(rePasswordPF.getText().equals("")) emptyFieldMsg += "      Confirm Password\n";
					if(!passwordPF.getText().equals(rePasswordPF.getText())) emptyFieldMsg = "Unable to create account. Passwords do not match.";
					JOptionPane.showMessageDialog(frame, emptyFieldMsg);
				}
			}
		};
		return submitActionListener;
	}
	//*********************************END OF SignUpView.java ACTIONLISTENERS****************************************
	
	//*********************************START OF StartView.java ACTIONLISTENERS***************************************
	
	public static ActionListener createSignupActionListener(JFrame frame, User currentUser)
	{
		ActionListener signupActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				SignUpView signupview = new SignUpView(currentUser);
				signupview.frame.setVisible(true);
			}
		};
		return signupActionListener;
	}
		
	public static ActionListener createLoginActionListener(JFrame frame, JPasswordField passwordPF, JTextField emailTF, User currentUser)
	{
		ActionListener loginActionListener = new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				   if(!(passwordPF.getText().equals("")) && eProduceController.validateLogin(currentUser, emailTF, passwordPF) && currentUser != null)
		            {
		               if(currentUser.getBlocked() == 1)
		               {
		            	   JOptionPane.showMessageDialog(frame, "Sorry you're blocked from eProduce :(");
		               }
		               else if(currentUser.getAdmin() == 1)
					   {
		            	   frame.dispose();
						   AdminMainPageView amp = new AdminMainPageView(currentUser);
						   amp.frame.setVisible(true);
					   }
					   else
		               {
						   frame.dispose();
						   MainPageView mp = new MainPageView(currentUser);
						   mp.frame.setVisible(true);
		               }
		            }
		            else
		            {
		            	  JOptionPane.showMessageDialog(frame, "Incorrect login. Please try again.");
		            }
				
			}
		};
		return loginActionListener;
	}
		
	//*********************************END OF StartView.java ACTIONLISTENERS*****************************************
		
	//*********************************START OF TicketView.java ACTIONLISTENERS**************************************
	
	public static ListSelectionListener createTicketTableListener(JFrame frame, JTable table, ArrayList<Ticket> myTickets, User currentUser)
	{
		ListSelectionListener ticketTableListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				JPanel main = new JPanel();
				JPanel top = new JPanel();
				JPanel bottom = new JPanel();
				JLabel des = new JLabel();
				JTextArea destxt = new JTextArea(5,30);
				JLabel res = new JLabel();
				JTextArea restxt = new JTextArea(5,30);
				Object[] options1 = { "Save Changes","Delete Ticket", "Cancel" };
				if(currentUser.getAdmin() == 1)
					options1[1] = "Close ticket";
				destxt.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
				
				restxt.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
				main.setLayout(new GridLayout(2,1));
				//Add SQL statement after text below
				des.setText("Description:");
				res.setText("   Response:");
				res.setSize(des.getSize());
				destxt.setLineWrap(true);
				restxt.setLineWrap(true);
				if(currentUser.getAdmin() == 1)
					destxt.setEditable(false);
				else
					restxt.setEditable(false);
					
				destxt.setText(myTickets.get(table.getSelectedRow()).getDescription());
				restxt.setText(myTickets.get(table.getSelectedRow()).getResponse());
				top.add(des);
				top.add(destxt);
				bottom.add(res);
				bottom.add(restxt);
				
				main.add(top);
				main.add(bottom);
	
				int result = JOptionPane.showOptionDialog(null, main, "Edit Ticket",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, options1, null);
	
				if(result == JOptionPane.YES_OPTION){ //saved button is clicked
					String message = "Saved";
					
					boolean created = eProduceDatabase.updateTicket(destxt.getText(), restxt.getText(), myTickets.get(table.getSelectedRow()).getTicketNum());
					
					JOptionPane.showMessageDialog(frame,message );
					frame.dispose();
					TicketView tv = new TicketView(currentUser);
					tv.frame.setVisible(true);
				}else if(result == JOptionPane.NO_OPTION){ //deleted button is clicked
					String message = "Deleted";
					if(currentUser.getAdmin() == 1)
						message = "Closed";
					eProduceDatabase.deleteTicket(myTickets.get(table.getSelectedRow()).getTicketNum());
					JOptionPane.showMessageDialog(frame,message);
					frame.dispose();
					TicketView tv = new TicketView(currentUser);
					tv.frame.setVisible(true);
				}
			}
		};
		return ticketTableListener;
	}
	//*********************************END OF TicketView.java ACTIONLISTENERS****************************************

}
