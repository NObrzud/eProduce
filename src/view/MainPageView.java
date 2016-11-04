package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import controller.eProduceController;
import controller.eProduceDatabase;
import model.Listing;
import model.User;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class MainPageView {

	public JFrame frame = new JFrame("eProduce");
	public JPanel sidePanel = new JPanel();
	public JPanel middlePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private User currentUser;
	private eProduceController controller = new eProduceController();
	private eProduceDatabase db = new eProduceDatabase();

	/**
	 * Set the main page up in this method each method called is a panel.
	 */
	public MainPageView(User user) {		
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
	/*
	 * This a method to hold all of the top panel information
	 */
	
	public void topPanel(){
		JLabel titleLabel = new JLabel("eProduce - Home");
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
				currentUser = null;
				frame.dispose();
				StartView start = new StartView(currentUser);
				start.frame.setVisible(true);
				
			}
		});
		
		/*
		 * My Account action button listener
		 */
		
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
		JButton myTickets = new JButton();
	
		myTickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				TicketView tix = new TicketView(currentUser);
				tix.frame.setVisible(true);
				
			}
		});
		
		myLists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyListingsView mlv = new MyListingsView(currentUser);
				mlv.frame.setVisible(true);
				
			}
		});
		
		myMeetings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyMeetingsView mlv = new MyMeetingsView(currentUser);
				mlv.frame.setVisible(true);
				
			}
		});
		
		myLists.setText("MyLists");
		myMeetings.setText("MyMeetings");
		myTickets.setText("MyTickets");
		
		sidePanel.setLayout(new BoxLayout(sidePanel,BoxLayout.Y_AXIS));
	
		sidePanel.add(myLists);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(myMeetings);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(myTickets);
	}
	/*
	 * This a method to hold all of the middle panel information
	 */
	public void middlePanel(){
		JTextField search = new JTextField();
		JComboBox sort;
		JTextField[] listings = new JTextField[10];
		for(int i = 0; i < listings.length; i++){
			listings[i] = new JTextField("Listing"+i);
		}
		JButton[] listbtn = new JButton[10];
		for(int i = 0; i < listbtn.length; i++)
		{
			listbtn[i] = new JButton("View");
		}
		ArrayList<Listing> list = new ArrayList<Listing>();
		db.getAllListings(list);
		for(int i = 0; i < list.size(); i++)
		{
			Listing currListing = list.get(i);
			listings[i].setText(currListing.getOwner() + "\t\t" + currListing.getContent() + "\t\t" + currListing.getTags());
		}
		JPanel leftSide = new JPanel();
		JPanel listing = new JPanel();
		JPanel rightSide = new JPanel();
		
		for(int i = 0; i < listbtn.length; i++)
		{
			listbtn[i].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				/*frame.dispose();
				MyAccountView start = new MyAccountView();
				start.frame.setVisible(true);*/
				JPanel listPanel = new JPanel();
				JPanel top = new JPanel();
				JPanel bottom = new JPanel();
				JLabel title = new JLabel();
				JLabel owner = new JLabel();
				JLabel des = new JLabel();
				JTextField titletxt = new JTextField();
				JTextField ownertxt = new JTextField();
				JTextArea destxt = new JTextArea(5,10);
				 Object[] options1 = { "Schedule MeetUp", "Okay",
	                "Cancel" };
				 	
				 	listPanel.setLayout(new BorderLayout());
					top.setLayout(new GridLayout(0,1));
					bottom.setLayout(new BorderLayout());
					//Add SQL statement after text below
					title.setText("Title: ");
					titletxt.setEnabled(false);
					owner.setText("Owner: ");
					ownertxt.setEnabled(false);
					des.setText("Description:");
					destxt.setEnabled(false);
					destxt.setLineWrap(true);
					top.add(title);
					top.add(titletxt);
					top.add(owner);
					top.add(ownertxt);
					bottom.add(des,BorderLayout.NORTH);
					bottom.add(destxt,BorderLayout.SOUTH);
					listPanel.add(top,BorderLayout.NORTH);
					listPanel.add(bottom,BorderLayout.SOUTH);
				
				
				  int result = JOptionPane.showOptionDialog(null, listPanel, "Viewing " + currentUser.getFirstName()+ "'s" +" Listing",
			                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
			                null, options1, null);
				
				if(result == JOptionPane.YES_OPTION){
					JPanel metPanel = new JPanel();
					JLabel participantslbl = new JLabel();
					JLabel whenlbl = new JLabel();
					JLabel timelbl = new JLabel();
					JLabel loclbl = new JLabel();
					JTextField participantstxt = new JTextField(10);
					JTextField whentxt = new JTextField(10);;
					JTextField loctxt = new JTextField(10);
					SpinnerDateModel model2 = new SpinnerDateModel();
					model2.setCalendarField(Calendar.MINUTE);
					JSpinner spinner= new JSpinner();
					spinner.setModel(model2);
					spinner.setEditor(new JSpinner.DateEditor(spinner, "h:mm a"));
					spinner.setSize(10,10);
					metPanel.setLayout(new GridLayout(0,1));
					
					
					participantslbl.setText("Participants: ");
					whenlbl.setText("When: ");
					timelbl.setText("Time");
					loclbl.setText("Location:");
					UtilDateModel model = new UtilDateModel();
					JDatePanelImpl datePanel = new JDatePanelImpl(model);
					JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
					
				
					metPanel.add(participantslbl);
					metPanel.add(participantstxt);
					metPanel.add(whenlbl);
					metPanel.add(datePicker);
					metPanel.add(timelbl);
					metPanel.add(spinner);
					metPanel.add(loclbl);
					metPanel.add(loctxt);
					
				
					
					int result2 = JOptionPane.showConfirmDialog(null, metPanel, "Create Meeting Info", JOptionPane.OK_CANCEL_OPTION);
					if(result2 == JOptionPane.OK_OPTION)
					{
						if(!(participantstxt.getText().equals("")) && !(whentxt.getText().equals("")) && !loctxt.getText().equals("") )	
						{
							
						}
						else
						{
							String emptyFieldMsg = "Unable to create account. The following fields are empty: \n";
							if(participantstxt.getText().equals("")) emptyFieldMsg += "      Participants\n";
							if(whentxt.getText().equals("")) emptyFieldMsg += "      When\n";
							if(loctxt.getText().equals("")) emptyFieldMsg += "      Location\n";
							JOptionPane.showMessageDialog(frame, emptyFieldMsg);
							
							
						}
						
					}
				}
				
			}
		});
		}
		
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
		
		for(int i = 0; i < listings.length; i++)
		{
			listings[i].setEditable(false);
		}
		
		
		listing.setLayout(new BoxLayout(listing,BoxLayout.Y_AXIS));
		for(int i = 0; i < listings.length; i++)
		{
			listing.add(listings[i]);
		}

		rightSide.setLayout(new GridLayout(10,1));
		for(int i = 0; i < listings.length; i++)
		{
			rightSide.add(listbtn[i]);
		}
		
		
		
		
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		
		
		leftSide.add(search);
		leftSide.add(sort);
		middlePanel.add(leftSide,BorderLayout.NORTH);
		middlePanel.add(listing,BorderLayout.CENTER);
		middlePanel.add(rightSide,BorderLayout.EAST);
	}
}
