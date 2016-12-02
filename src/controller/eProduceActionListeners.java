package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.Feedback;
import model.Listing;
import model.Ticket;
import model.User;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import view.AdminMainPageView;
import view.MainPageView;
import view.MyListingsView;
import view.MyMeetingsView;
import view.SignUpView;
import view.StartView;
import view.TicketView;
/*
 * This class handles all ActionListener and ListSelectionListener creation to be passed back to the respective views. 
 * 
 */
public class eProduceActionListeners {
	static ActionListener logout, myAccount, home, myListings, myMeetings, myTickets, createTicket, createListing, allUsers, sysTickets, cancel, submit, signup, login, search;
	static ListSelectionListener ticket, listing, feedback;
	static MouseAdapter searchMouse;
	
	public static ActionListener createLogoutActionListener(JFrame frame, User currentUser)
	{
		logout = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				StartView start = new StartView(currentUser);
				start.frame.setVisible(true);
				
			}
		};
		return logout;
	}
	
	public static MouseAdapter createSearchMouseListener(JFrame frame, JTextField search,  JButton defaultButton)
	{
		searchMouse = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				search.setText("");
				frame.getRootPane().setDefaultButton(defaultButton);
			}
		};
		return searchMouse;
	}
	public static ActionListener createMyAccountActionListener(JFrame frame, User currentUser) 
	{
		myAccount = new ActionListener() {
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
		return myAccount;
	}
	
	public static ActionListener createHomeActionListener(JFrame frame, User currentUser)
	{
		home = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainPageView mpv = new MainPageView(currentUser);
				mpv.frame.setVisible(true);
				
			}
		};
		return home;
	}
	
	public static ActionListener createMyListingsActionListener(JFrame frame, User currentUser)
	{
		myListings = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyListingsView mlv = new MyListingsView(currentUser);
				mlv.frame.setVisible(true);
				
			}
		};
		return myListings;
	}
	
	public static ActionListener createMyMeetingsActionListener(JFrame frame, User currentUser)
	{
		myMeetings = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyMeetingsView mlv = new MyMeetingsView(currentUser);
				mlv.frame.setVisible(true);
				
			}
		};
		return myMeetings;
	}
	
	public static ActionListener createMyTicketsActionListener(JFrame frame, User currentUser) {
		myTickets = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			frame.dispose();
			TicketView tix = new TicketView(currentUser);
			tix.frame.setVisible(true);
			}
		};
		return myTickets;
	}
	
	public static ActionListener createCreateTicketActionListener(JFrame frame, User currentUser)
	{
		createTicket = new ActionListener(){
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
		return createTicket;
	}
	
	public static ActionListener createListingActionListener(JFrame frame, User currentUser)
	{
		createListing = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel lstPanel = new JPanel();
				JPanel top = new JPanel();
				JPanel bottom = new JPanel();
				JLabel title = new JLabel();
				JLabel tags = new JLabel();
				JLabel des = new JLabel();
				JTextField titletxt = new JTextField();
				JTextField tagstxt = new JTextField();
				JTextArea destxt = new JTextArea(5,10);
				Border border = BorderFactory.createLineBorder(Color.BLACK);
				lstPanel.setLayout(new BorderLayout());
				top.setLayout(new GridLayout(0,1));
				bottom.setLayout(new BorderLayout());
				
				title.setText("Title: ");
				tags.setText("Tags: (Separate each tag with a comma \',\')");
				des.setText("Description:");
				destxt.setLineWrap(true);
				destxt.setBorder(border);
				JScrollPane sp = new JScrollPane(destxt);
				top.add(title);
				top.add(titletxt);
				top.add(tags);
				top.add(tagstxt);
				bottom.add(des,BorderLayout.NORTH);
				bottom.add(sp,BorderLayout.SOUTH);
				lstPanel.add(top,BorderLayout.NORTH);
				lstPanel.add(bottom,BorderLayout.SOUTH);
			
				
				int result = JOptionPane.showConfirmDialog(null, lstPanel, "Create Listing Info", JOptionPane.OK_CANCEL_OPTION);
				if(result == JOptionPane.OK_OPTION)
				{
					if(!(titletxt.getText().equals("")) && !(tagstxt.getText().equals("")) && !destxt.getText().equals("") )	
					{
						if(eProduceDatabase.createListing(currentUser.getEmail(), titletxt.getText(), destxt.getText(), tagstxt.getText()))
						{
							String msg = "Listing created!";
							JOptionPane.showMessageDialog(frame, msg);
							frame.dispose();
							MyListingsView mlv = new MyListingsView(currentUser);
							mlv.frame.setVisible(true);
						}
						else
						{
							String msg = "Unable to create listing. Database error.";
							JOptionPane.showMessageDialog(frame, msg);
						}
					}
					else
					{
						String emptyFieldMsg = "Unable to create listing. The following fields are empty: \n";
						if(titletxt.getText().equals("")) emptyFieldMsg += "      Title\n";
						if(tagstxt.getText().equals("")) emptyFieldMsg += "      Tags\n";
						if(destxt.getText().equals("")) emptyFieldMsg += "      Description\n";
						JOptionPane.showMessageDialog(frame, emptyFieldMsg);
						
					}
					
				}
				
			}
		};
		return createListing;
	}
	
	public static ActionListener createAllUsersActionListener(JFrame frame, User currentUser)
	{
		allUsers = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				AdminMainPageView ampv = new AdminMainPageView(currentUser);
				ampv.frame.setVisible(true);
				
			}
		};
		return allUsers;
	}
	
	public static ActionListener createSysTicketsActionListener(JFrame frame, User currentUser) {
		sysTickets = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				TicketView atv = new TicketView(currentUser);
				atv.frame.setVisible(true);
			}
		};
		return sysTickets;
	}
	
	public static ActionListener createCancelActionListener(JFrame frame, User currentUser)
	{
		cancel = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				StartView sv = new StartView(currentUser);
				sv.frame.setVisible(true);
			}	
		};
		return cancel;
	}
	
	public static ActionListener createSubmitActionListener(JFrame frame, JTextField firstNameTF, JTextField lastNameTF, JTextField emailTF, JPasswordField passwordPF, JPasswordField rePasswordPF, User currentUser)
	{
		submit = new ActionListener()
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
		return submit;
	}
	
	public static ActionListener createSignupActionListener(JFrame frame, User currentUser)
	{
		signup = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				SignUpView signupview = new SignUpView(currentUser);
				signupview.frame.setVisible(true);
			}
		};
		return signup;
	}
		
	public static ActionListener createLoginActionListener(JFrame frame, JPasswordField passwordPF, JTextField emailTF, User currentUser)
	{
		login = new ActionListener() {
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
		return login;
	}

	public static ListSelectionListener createTicketTableListener(JFrame frame, JTable table, ArrayList<Ticket> myTickets, User currentUser)
	{
		ticket = new ListSelectionListener() {
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
		return ticket;
	}
	public static ListSelectionListener createAllListingsTableListener(JFrame frame, String[][] listingData, JTable table, ArrayList<Listing> list, User currentUser)
	{
		listing = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if(table.getSelectedRow() < 0)
					return;
				JPanel listPanel = new JPanel();	
				JPanel top = new JPanel();
				JPanel bottom = new JPanel();
				JPanel ratingPanel = new JPanel();
				JPanel userPanel = new JPanel();
				JLabel title = new JLabel();
				JLabel owner = new JLabel();
				JLabel ownerRating = new JLabel();
				JLabel des = new JLabel();
				
				ArrayList<Feedback> feedback ;
				if(table.getSelectedRow()>=0)
					feedback = eProduceDatabase.getFeedbackForListing(list.get(table.getSelectedRow()).getListingNum());
				else
					feedback = new ArrayList<Feedback>();
				JTable feedbacktbl = eProduceController.createFeedbackTable(frame, feedback, list, new String[] {"Feedback #","Creator","Description"}, currentUser);	
				
				JTextField titletxt = new JTextField();
				JTextField ownertxt = new JTextField();
				JButton reportButton = new JButton("Report");
				JTextField rating = new JTextField();
				JTextArea destxt = new JTextArea(5,10);
				Object[] options1 = { "Schedule Meetup", "Contact Owner", "Create Feedback", "Exit" };
				 	
				
				listPanel.setLayout(new GridLayout(0,1));
				ratingPanel.setLayout(new GridLayout(0,4));
				userPanel.setLayout(new GridLayout(0,3));
				top.setLayout(new GridLayout(0,1));
				bottom.setLayout(new BorderLayout());
				//Add SQL statement after text below
				title.setText("Title: ");
				titletxt.setEditable(false);
				titletxt.setText(list.get(table.getSelectedRow()).getTitle());
				owner.setText("Owner: ");
				ownertxt.setEditable(false);
				ownertxt.setText(list.get(table.getSelectedRow()).getOwner().getEmail());
				ownerRating.setText("Owner Rating: ");
				rating.setEditable(false);
				rating.setText(Integer.toString(list.get(table.getSelectedRow()).getOwner().getCurrentRating()));
				rating.setHorizontalAlignment(JTextField.CENTER);
				des.setText("Description:");
				destxt.setEditable(false);
				destxt.setText(list.get(table.getSelectedRow()).getContent());
				destxt.setLineWrap(true);

				try {
					ImageIcon plusImg = new ImageIcon(ImageIO.read(new File("res/plus.png")));
					ImageIcon minusImg = new ImageIcon(ImageIO.read(new File("res/minus.png")));
					ImageIcon plusImgGrey = new ImageIcon(ImageIO.read(new File("res/plus-grey.png")));
					ImageIcon minusImgGrey = new ImageIcon(ImageIO.read(new File("res/minus-grey.png")));
					final JButton plus = new JButton(plusImgGrey);
					final JButton minus = new JButton(minusImgGrey);
					
					plus.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(list.get(table.getSelectedRow()).getOwner().getEmail().equals(currentUser.getEmail()))
								; //don't do anything if you're viewing your own listing, that's cheating.
							else if(plus.getIcon().equals(plusImg)) //de-pressing plus
							{	
								int newRating = Integer.parseInt(rating.getText())-1;
								rating.setText(Integer.toString(newRating));
								list.get(table.getSelectedRow()).getOwner().setCurrentRating(newRating);
								eProduceDatabase.decreaseUserRating(ownertxt.getText());									
								plus.setIcon(plusImgGrey);
							}
							else // pressing plus
							{
								int newRating = Integer.parseInt(rating.getText())+1;
								if(minus.getIcon().equals(minusImg)) //if minus is already pressed.
								{
									minus.setIcon(minusImgGrey);
									newRating++;
								}
								rating.setText(Integer.toString(newRating));
								list.get(table.getSelectedRow()).getOwner().setCurrentRating(newRating);
								eProduceDatabase.increaseUserRating(ownertxt.getText());
								plus.setIcon(plusImg);
							}
						}
					});
					
					minus.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(list.get(table.getSelectedRow()).getOwner().getEmail().equals(currentUser.getEmail()))
								; //don't do anything if you're viewing your own listing, that's cheating.
							else if(minus.getIcon().equals(minusImg)) //they're de-pressing minus
							{
								int newRating = Integer.parseInt(rating.getText())+1;
								rating.setText(Integer.toString(newRating));
								list.get(table.getSelectedRow()).getOwner().setCurrentRating(newRating);
								eProduceDatabase.increaseUserRating(ownertxt.getText());
								minus.setIcon(minusImgGrey);
							}
							else //pressing minus
							{	
								int newRating = Integer.parseInt(rating.getText())-1;
								if(plus.getIcon().equals(plusImg)) //if plus is already pressed.
								{
									plus.setIcon(plusImgGrey);
									newRating--;
								}
								rating.setText(Integer.toString(newRating));
								list.get(table.getSelectedRow()).getOwner().setCurrentRating(newRating);
								eProduceDatabase.decreaseUserRating(ownertxt.getText());									
								minus.setIcon(minusImg);
						}
						}
					});
					reportButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(list.get(table.getSelectedRow()).getOwner().getEmail().equals(currentUser.getEmail()))
									; //don't do anything if you're viewing your own listing, that's cheating.
								eProduceDatabase.reportUser(list.get(table.getSelectedRow()).getOwner().getEmail());
								JOptionPane.showMessageDialog(frame, "User has been reported. Thanks!");
							}
						});
					ratingPanel.add(ownerRating);
					ratingPanel.add(minus);
					ratingPanel.add(rating);
					ratingPanel.add(plus);
					userPanel.add(owner);
					userPanel.add(ownertxt);
					userPanel.add(reportButton);
					top.add(title);
					top.add(titletxt);
					top.add(userPanel);
					top.add(ratingPanel);
					bottom.add(des,BorderLayout.NORTH);
					bottom.add(destxt,BorderLayout.CENTER);
					if(feedback.size()>0)
						bottom.add(new JScrollPane(feedbacktbl),BorderLayout.SOUTH);
					listPanel.add(top);
					listPanel.add(bottom);
					
					} catch (IOException e) {
						System.out.println("Image file not found!");
					}
				
				int result = JOptionPane.showOptionDialog(null, listPanel, "Viewing #" + list.get(table.getSelectedRow()).getListingNum(),
			                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
			                null, options1, null);
				
				if(result == 0){
					JPanel meetingPanel = new JPanel();
					JLabel participantsLabel = new JLabel();
					JLabel whenLabel = new JLabel();
					JLabel timeLabel = new JLabel();
					JLabel locationLabel = new JLabel();
					JTextField whenTF = new JTextField(10);
					JPanel metPanel = new JPanel();
					JLabel participantslbl = new JLabel();
					JLabel whenlbl = new JLabel();
					JLabel timelbl = new JLabel();
					JLabel loclbl = new JLabel();
					JTextField participantstxt = new JTextField(10);
					JTextField loctxt = new JTextField(10);
					SpinnerDateModel model2 = new SpinnerDateModel();
					model2.setCalendarField(Calendar.MINUTE);
					JSpinner spinner= new JSpinner();
					spinner.setModel(model2);
					spinner.setEditor(new JSpinner.DateEditor(spinner, "hh:mm a"));
					
					spinner.setSize(10,10);
					meetingPanel.setLayout(new GridLayout(0,1));
					
					
					participantsLabel.setText("Participants: ");
					whenLabel.setText("When: ");
					timeLabel.setText("Time");
					locationLabel.setText("Location:");
					participantslbl.setText("Participants: ");
					whenlbl.setText("When: ");
					timelbl.setText("Time");
					loclbl.setText("Location:");
					participantstxt.setText(currentUser.getEmail() + ", " + list.get(table.getSelectedRow()).getOwner().getEmail());
					UtilDateModel model = new UtilDateModel();
					JDatePanelImpl datePanel = new JDatePanelImpl(model);
					JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
					
				
					meetingPanel.add(participantsLabel);
					meetingPanel.add(participantstxt);
					meetingPanel.add(whenLabel);
					meetingPanel.add(datePicker);
					meetingPanel.add(timeLabel);
					meetingPanel.add(spinner);
					meetingPanel.add(locationLabel);
					meetingPanel.add(loctxt);
					
				
					
					int result2 = JOptionPane.showConfirmDialog(null, meetingPanel, "Create Meeting Info", JOptionPane.OK_CANCEL_OPTION);
					if(result2 == JOptionPane.OK_OPTION)
					{
						if(!(participantstxt.getText().equals("")) && !(datePicker.getModel().getValue().toString().equals("")) && !loctxt.getText().equals("") )	
						{
							java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String foo = sdf.format(new Date(model.getYear()-1900, model.getMonth(), model.getDay(), model2.getDate().getHours(), model2.getDate().getMinutes()));
							System.out.println(foo);
							boolean ret = eProduceDatabase.createMeetup(currentUser.getEmail(),participantstxt.getText(),loctxt.getText(),  model, model2);
							if(ret)
							{
								JOptionPane.showMessageDialog(frame, "Meetup has been successfully created!");
								frame.dispose();
								MainPageView mpv = new MainPageView(currentUser);
								mpv.frame.setVisible(true);
							}
						}
						else
						{
							String emptyFieldMsg = "Unable to create meetup. The following fields are empty: \n";
							if(participantstxt.getText().equals("")) emptyFieldMsg += "      Participants\n";
							if(datePicker.getModel().getValue().toString().equals("")) emptyFieldMsg += "      When\n";
							if(loctxt.getText().equals("")) emptyFieldMsg += "      Location\n";
							JOptionPane.showMessageDialog(frame, emptyFieldMsg);
						}
					}
				}
				if(result == 1){
					JPanel contactPanel = new JPanel();
					JPanel north = new JPanel();
					JPanel south = new JPanel();
					JLabel toLabel = new JLabel("To:");
					JLabel fromLabel = new JLabel("From:");
					JLabel subjectLabel = new JLabel("Subject:");
					JLabel contentLabel = new JLabel("Email Content:");
					JTextField toTF = new JTextField(20);
					JTextField fromTF = new JTextField(20);
					JTextField subjectTF = new JTextField(20);
					JTextArea contentArea = new JTextArea(8,30);
					
					
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					contentArea.setLineWrap(true);
					contentArea.setBorder(border);
					JScrollPane sp = new JScrollPane(contentArea);
					
					contactPanel.setLayout(new BorderLayout());
					north.setLayout(new GridLayout(0,1));
					south.setLayout(new BorderLayout());
					toTF.setText(list.get(table.getSelectedRow()).getOwner().getEmail());
					fromTF.setText(currentUser.getEmail());
					subjectTF.setText("Another eProduce user would like to contact you!");
					toTF.setEditable(false);
					fromTF.setEditable(false);
					subjectTF.setEditable(false);
					
					north.add(toLabel);
					north.add(toTF);
					north.add(fromLabel);
					north.add(fromTF);
					north.add(subjectLabel);
					north.add(subjectTF);
					south.add(contentLabel, BorderLayout.NORTH);
					south.add(sp, BorderLayout.SOUTH);
					
					
					contactPanel.add(north, BorderLayout.NORTH);
					contactPanel.add(south, BorderLayout.SOUTH);
					
					int result2 = JOptionPane.showConfirmDialog(null, contactPanel, "Contact Owner", JOptionPane.OK_CANCEL_OPTION);
					if(result2 == JOptionPane.OK_OPTION)
					{
						if(!contentArea.getText().isEmpty()){
							String emailMsg = "Hello eProduce User!\nAccording to our system, there is a user that would like to contact you in regards to your listing. "
									+ "The following is a message from " + fromTF.getText() +":\n"
											+ "\n" + contentArea.getText() +"\n"
											+ "\n If you would like to contact this user, you may contact him using the following email address: " + fromTF.getText();
						}
						else{
							JOptionPane.showMessageDialog(null, "Email failed to send. Cannot have an empty body.");
						}
					}
				}
				if(result == 2){
					JPanel lstPanel = new JPanel();
					JPanel top1 = new JPanel();
					JPanel bottom1 = new JPanel();
					JLabel title1 = new JLabel();
					JLabel des1 = new JLabel();
					JTextArea destxt1 = new JTextArea(5,10);
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					lstPanel.setLayout(new BorderLayout());
					top1.setLayout(new GridLayout(0,1));
					bottom1.setLayout(new BorderLayout());
					
					des1.setText("Description:");
					destxt1.setLineWrap(true);
					destxt1.setBorder(border);
					JScrollPane sp = new JScrollPane(destxt1);
					bottom1.add(des1,BorderLayout.NORTH);
					bottom1.add(sp,BorderLayout.SOUTH);
					lstPanel.add(top1,BorderLayout.NORTH);
					lstPanel.add(bottom1,BorderLayout.SOUTH);
				
					
					int result3 = JOptionPane.showConfirmDialog(null, lstPanel, "Create Feedback Info", JOptionPane.OK_CANCEL_OPTION);
					if(result3 == JOptionPane.OK_OPTION)
					{
						if(eProduceDatabase.createFeedback(currentUser.getEmail(), destxt1.getText(), list.get(table.getSelectedRow()).getListingNum()))
							JOptionPane.showMessageDialog(null, "Feedback created!");
						else
							JOptionPane.showMessageDialog(null, "Uh oh. Your feedback couldn't be created.");

						
					}
				}
			}
		};
		return listing;
	}

	public static ListSelectionListener createFeedbackTableListener(JFrame frame, String[][] feedbackData, JTable feedbacktbl, ArrayList<Feedback> list, User currentUser) 
	{		
		if(list.size() > 0)
		{
			feedback = new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) 
					{				
							Feedback currentFeedback;
							if(feedbacktbl.getSelectedRow()>=0)
								currentFeedback = list.get(feedbacktbl.getSelectedRow());
							else
								return;
							if(currentFeedback.getOwner().equals(currentUser.getEmail()))
							{
								JPanel panel = new JPanel();
								JTextArea contentArea = new JTextArea(currentFeedback.getContent(), 10, 20);
								contentArea.setLineWrap(true);
								JScrollPane sp = new JScrollPane(contentArea);
								panel.add(sp);
								int result = JOptionPane.showOptionDialog(null, panel, "Edit Feedback", JOptionPane.YES_NO_CANCEL_OPTION, 
											 JOptionPane.PLAIN_MESSAGE, null, new String[] {"Save Changes", "Delete Feedback", "Cancel"}, null);
								
								if(result == JOptionPane.YES_OPTION) //save
								{
									if(contentArea.getText().equals(currentFeedback.getContent()))
										JOptionPane.showMessageDialog(null, "No changes were made.");
									else if(eProduceDatabase.editFeedback(currentFeedback.getFeedbackNum(), contentArea.getText()))
									{
										JOptionPane.showMessageDialog(null, "Changes updated.");
										feedbacktbl.setValueAt((Object)contentArea.getText(),feedbacktbl.getSelectedRow(), 2);
										currentFeedback.setContent(contentArea.getText());
									}
									else
									{
										JOptionPane.showMessageDialog(null, "Something went wrong saving your feedback changes.");
									}
								}
								else if(result == JOptionPane.NO_OPTION) //delete
								{
									if(eProduceDatabase.deleteFeedback(currentFeedback.getFeedbackNum()))
									{
										JOptionPane.showMessageDialog(null, "Feedback deleted.");
										((DefaultTableModel)feedbacktbl.getModel()).removeRow(feedbacktbl.getSelectedRow());
									}
									else
										JOptionPane.showMessageDialog(null, "Oops something went wrong deleting your feedback.");
								}
							}
					}
				};
			return feedback;
		}
		else
			return null;
		
	}

	public static ActionListener createSearchActionListener(JFrame frame, User currentUser, String[][] listingData, ArrayList<Listing> listings, JTextField searchJTF, JTable table, String[] columnHeadings, JButton searchButton) {
		search = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(searchJTF.getText().isEmpty())
					return;
				eProduceController.searchListings(frame, listings, listingData, table, searchJTF.getText(), columnHeadings, currentUser);
				((DefaultTableModel)table.getModel()).fireTableDataChanged();
			}
		};
		return search;
	}
}
