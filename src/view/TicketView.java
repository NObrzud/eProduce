package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;

import controller.eProduceController;

import java.awt.color.*;

import model.User;



public class TicketView {
	public JFrame frame = new JFrame("eProduce");
	public JPanel sidePanel = new JPanel();
	public JPanel middlePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	Border border = BorderFactory.createLineBorder(Color.BLACK);
	private User currentUser;
	private eProduceController controller = new eProduceController();

	public TicketView(User user)
	{
		currentUser = user;
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = (int)(((int) tk.getScreenSize().getWidth())*.75);
		int ySize = (int)(((int) tk.getScreenSize().getHeight())*.75);
		frame.setSize(xSize, ySize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); 
		frame.setLayout(new BorderLayout());
		
		topPanel();
		sidePanel();
		middlePanel();

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(sidePanel, BorderLayout.WEST);
		frame.add(middlePanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public void topPanel(){
		JLabel titleLabel = new JLabel("eProduce - MyTickets");
		JButton myAccount = new JButton();
		JButton logout = new JButton();
		JPanel rightSide = new JPanel();
		JPanel leftSide = new JPanel();
		
		topPanel.setLayout(new BorderLayout());
		rightSide.setLayout(new FlowLayout(FlowLayout.RIGHT));
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		myAccount.setText("MyAccount");
		logout.setText("Log Out");
		titleLabel.setBounds(150, 10, 150, 150);
		titleLabel.setFont(titleLabel.getFont().deriveFont(30f));
		
		leftSide.add(titleLabel);
		rightSide.add(myAccount);
		rightSide.add(logout);
		
		/*
		 * Log outs action button listener logs the user out
		 */
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				StartView start = new StartView(currentUser);
				start.frame.setVisible(true);
				
			}
		});
		myAccount.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				/*frame.dispose();
				MyAccountView start = new MyAccountView();
				start.frame.setVisible(true);*/
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
							else if(controller.updateAccount(fname.getText(), lname.getText(), email.getText(), password.getText(), passConfirm.getText()))
							{
								JOptionPane.showMessageDialog(frame, "Account has been successfully updated! Please login again.");
								frame.dispose();
								StartView sv = new StartView(currentUser);
								frame = sv.frame;
								frame.setVisible(true);
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
		});
		
		topPanel.add(leftSide,BorderLayout.WEST);
		topPanel.add(rightSide,BorderLayout.EAST);
		
	
	}
	/*
	 * This a method to hold all of the side panel information
	 */
	public void sidePanel(){
		JButton myLists = new JButton();
		JButton myMeetings = new JButton();
		JButton homeButton = new JButton();
		JButton createButton= new JButton();
	
		myLists.setMinimumSize(new Dimension(110, 26));
		myLists.setMaximumSize(new Dimension(110, 26));
		myMeetings.setMaximumSize(new Dimension(110,26));
		homeButton.setMinimumSize(new Dimension(110, 26));
		homeButton.setMaximumSize(new Dimension(110,26));
		createButton.setMinimumSize(new Dimension(110, 26));
		createButton.setMaximumSize(new Dimension(110,26));
		
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainPageView main = new MainPageView(currentUser);
				main.frame.setVisible(true);
				
			}
		});
		
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel lstPanel = new JPanel();
				JPanel top = new JPanel();
				JPanel bottom = new JPanel();
				JLabel title = new JLabel();
				JLabel name = new JLabel();
				JLabel des = new JLabel();
				JTextField titletxt = new JTextField();
				JTextField nametxt = new JTextField();
				JTextArea destxt = new JTextArea(5,10);
				Border border = BorderFactory.createLineBorder(Color.BLACK);
				lstPanel.setLayout(new BorderLayout());
				top.setLayout(new GridLayout(0,1));
				bottom.setLayout(new BorderLayout());
				
				name.setText("Name:");
				title.setText("Ticket Subject: ");
				des.setText("Description:");
				destxt.setLineWrap(true);
				destxt.setBorder(border);
				
				top.add(title);
				top.add(titletxt);
				top.add(name);
				top.add(nametxt);
				bottom.add(des,BorderLayout.NORTH);
				bottom.add(destxt,BorderLayout.SOUTH);
				lstPanel.add(top,BorderLayout.NORTH);
				lstPanel.add(bottom,BorderLayout.SOUTH);
			
				
				int result = JOptionPane.showConfirmDialog(null, lstPanel, "Create Ticket Info", JOptionPane.OK_CANCEL_OPTION);
				if(result == JOptionPane.OK_OPTION)
				{
					if(!(titletxt.getText().equals(""))&&!nametxt.getText().equals("") && !destxt.getText().equals("") )	
					{
						
					}
					else
					{
						String emptyFieldMsg = "Unable to create account. The following fields are empty: \n";
						if(titletxt.getText().equals("")) emptyFieldMsg += "      Title\n";
						if(nametxt.getText().equals(""))emptyFieldMsg += "      Name\n";
						if(destxt.getText().equals("")) emptyFieldMsg += "      Description\n";
						JOptionPane.showMessageDialog(frame, emptyFieldMsg);
					}
					
				}
				
			}
		});
		myMeetings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyMeetingsView mlv = new MyMeetingsView(currentUser);
				mlv.frame.setVisible(true);
				
			}
		});
		myLists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyListingsView mlv = new MyListingsView(currentUser);
				mlv.frame.setVisible(true);
				
			}
		});
		
		
		myLists.setText("MyListings");
		myMeetings.setText("MyMeetings");
		homeButton.setText("Home");
		createButton.setText("Create Ticket");
		
		sidePanel.setLayout(new BoxLayout(sidePanel,BoxLayout.Y_AXIS));
		
		sidePanel.add(homeButton);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(createButton);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(myLists);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(myMeetings);
	}
	/*
	 * This a method to hold all of the middle panel information
	 */
	public void middlePanel(){
		JTextField search = new JTextField();
		JComboBox sort;
		JTextField listing1 = new JTextField("Listings1");
		JTextField listing2 = new JTextField("Listings2");
		JTextField listing3 = new JTextField("Listings3");
		JTextField listing4 = new JTextField("Listings4");
		JTextField listing5 = new JTextField("Listings5");
		JTextField listing6 = new JTextField("Listings6");
		JTextField listing7 = new JTextField("Listings7");
		JTextField listing8 = new JTextField("Listings8");
		JTextField listing9 = new JTextField("Listings9");
		JTextField listing10 = new JTextField("Listings10");
		JButton listbtn1 = new JButton("View");
		JButton listbtn2 = new JButton("View");
		JButton listbtn3 = new JButton("View");
		JButton listbtn4 = new JButton("View");
		JButton listbtn5 = new JButton("View");
		JButton listbtn6 = new JButton("View");
		JButton listbtn7 = new JButton("View");
		JButton listbtn8 = new JButton("View");
		JButton listbtn9 = new JButton("View");
		JButton listbtn10 = new JButton("View");
		
		JPanel leftSide = new JPanel();
		JPanel listing = new JPanel();
		JPanel rightSide = new JPanel();
		
		search.setText("Search.....");
		search.setColumns(50);
		search.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				search.setText("");
			}
		});
		
		String [] comboBoxInputs = {"Sort By","Date - Newest", "Date - Oldest"};
		sort = new JComboBox(comboBoxInputs);
		
		listing1.setEditable(false);
		listing2.setEditable(false);
		listing3.setEditable(false);
		listing4.setEditable(false);
		listing5.setEditable(false);
		listing6.setEditable(false);
		listing7.setEditable(false);
		listing8.setEditable(false);
		listing9.setEditable(false);
		listing10.setEditable(false);
		
		
		listing.setLayout(new BoxLayout(listing,BoxLayout.Y_AXIS));
		listing.add(listing1);
		listing.add(listing2);
		listing.add(listing3);
		listing.add(listing4);
		listing.add(listing5);
		listing.add(listing6);
		listing.add(listing7);
		listing.add(listing8);
		listing.add(listing9);
		listing.add(listing10);

		rightSide.setLayout(new GridLayout(10,1));
		rightSide.add(listbtn1);
		rightSide.add(listbtn2);
		rightSide.add(listbtn3);
		rightSide.add(listbtn4);
		rightSide.add(listbtn5);
		rightSide.add(listbtn6);
		rightSide.add(listbtn7);
		rightSide.add(listbtn8);
		rightSide.add(listbtn9);
		rightSide.add(listbtn10);
		
		
		
		
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		
		
		leftSide.add(search);
		leftSide.add(sort);
		middlePanel.add(leftSide,BorderLayout.NORTH);
		middlePanel.add(listing,BorderLayout.CENTER);
		middlePanel.add(rightSide,BorderLayout.EAST);
		
	}

}
