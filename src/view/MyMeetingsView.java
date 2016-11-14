package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import controller.eProduceController;
import controller.eProduceDatabase;
import model.Meetup;
import model.User;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class MyMeetingsView {
	public JFrame frame = new JFrame("eProduce");
	public JPanel sidePanel = new JPanel();
	public JPanel middlePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private User currentUser;

	public MyMeetingsView(User user) {		
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
		JLabel titleLabel = new JLabel("eProduce - MyMeetings");
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
							else if(eProduceController.updateAccount(fname.getText(), lname.getText(), email.getText(), password.getText(), passConfirm.getText()))
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
		JButton home = new JButton();
		JButton myListings = new JButton();
		JButton myTickets = new JButton();
		home.setMinimumSize(new Dimension(110, 26));
		home.setMaximumSize(new Dimension(110,26));
		myListings.setMinimumSize(new Dimension(110, 26));
		myListings.setMaximumSize(new Dimension(110,26));
		myTickets.setMinimumSize(new Dimension(110, 26));
		myTickets.setMaximumSize(new Dimension(110,26));
	
		myTickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				TicketView tix = new TicketView(currentUser);
				tix.frame.setVisible(true);
				
			}
		});

		myListings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyListingsView mlv = new MyListingsView(currentUser);
				mlv.frame.setVisible(true);
				
			}
		});
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainPageView mpv = new MainPageView(currentUser);
				mpv.frame.setVisible(true);
				
			}
		});
		
		home.setText("Home");
		myListings.setText("MyListings");
		myTickets.setText("MyTickets");
		
		sidePanel.setLayout(new BoxLayout(sidePanel,BoxLayout.Y_AXIS));
	
		sidePanel.add(home);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(myListings);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(myTickets);
	}
	/*
	 * This a method to hold all of the middle panel information
	 */
	public void middlePanel(){
		ArrayList<Meetup> myMeetups = new ArrayList<Meetup>();
		eProduceDatabase.getMyMeetups(currentUser.getEmail(),myMeetups);
		
		JTextField search = new JTextField();
		JComboBox sort;
		JTextField[][] meetups = new JTextField[myMeetups.size()][6];
		String[][] meetupData = new String[myMeetups.size()][6];
		for(int i = 0; i < meetups.length; i++){
			meetups[i][0] = new JTextField("Meetup Number "+i);
			meetups[i][1] = new JTextField("Meetup Date " + i);
			meetups[i][2] = new JTextField("Meetup Time " + i);
			meetups[i][3] = new JTextField("Meetup Location " + i);
			meetups[i][4] = new JTextField("Meetup Participants " + i);
			meetups[i][5] = new JTextField("Meetup Owner " + i);
		}
		
		JPanel leftSide = new JPanel();
		JPanel meetup = new JPanel();
		
		for(int i = 0; i < myMeetups.size(); i++)
		{
			Meetup currMeetup = myMeetups.get(i);
			if(currMeetup != null)
			{
				Date date = currMeetup.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
				meetups[i][0].setText(currMeetup.getMeetupNum());
				meetups[i][1].setText(currMeetup.getDate().toString());
				meetups[i][2].setText(sdf.format(currMeetup.getTime()));
				meetups[i][3].setText(currMeetup.getLocation().toString());
				meetups[i][4].setText(currMeetup.getParticipants().toString());
				meetups[i][5].setText(currMeetup.getOwner().toString());
			}
		}
		
		for(int i = 0; i < meetupData.length; i++)
		{
			for(int j = 0; j < meetupData[i].length; j++)
			{
				meetupData[i][j] = meetups[i][j].getText();
			}
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
		meetup.setLayout(new BoxLayout(meetup, BoxLayout.Y_AXIS));
		JTable table = new JTable(meetupData, new String[] {"Meeting #", "Date", "Time", "Location","Participants"});
		
		table.setBackground(frame.getBackground()); //sets background color of each cell to the frame's background.
		table.setShowVerticalLines(false); //doesn't show vertical gridlines
		table.setGridColor(Color.black); //changes the gridline's colors to black
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setFont(new Font("Serif", Font.PLAIN, 24)); //changes font to be larger
		table.setBorder(new MatteBorder(1, 1, 1, 1, Color.black)); //Gives a black border around the table
		table.setRowHeight(30); //number of rows to have in the table.
		table.setDefaultEditor(Object.class, null); //disables "double-click to edit" functionality
		
		
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				JPanel meetingPanel = new JPanel();
				JLabel participantsLabel = new JLabel();
				JLabel whenLabel = new JLabel();
				JLabel timeLabel = new JLabel();
				JLabel locationLabel = new JLabel();
				JLabel meetupNumLabel = new JLabel();
				JLabel meetupNumLabel2 = new JLabel();
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
				Date date = myMeetups.get(table.getSelectedRow()).getTime();
				spinner.setValue(date);
				
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
				meetupNumLabel.setText("Meetup #: ");
				meetupNumLabel2.setText(myMeetups.get(table.getSelectedRow()).getMeetupNum());
				participantstxt.setText(myMeetups.get(table.getSelectedRow()).getParticipants());
				loctxt.setText(myMeetups.get(table.getSelectedRow()).getLocation());
				UtilDateModel model = new UtilDateModel(myMeetups.get(table.getSelectedRow()).getDate());
				JDatePanelImpl datePanel = new JDatePanelImpl(model);
				JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
				
				meetingPanel.add(meetupNumLabel);
				meetingPanel.add(meetupNumLabel2);
				meetingPanel.add(participantsLabel);
				meetingPanel.add(participantstxt);
				meetingPanel.add(whenLabel);
				meetingPanel.add(datePicker);
				meetingPanel.add(timeLabel);
				meetingPanel.add(spinner);
				meetingPanel.add(locationLabel);
				meetingPanel.add(loctxt);
				
			
				String[] options = new String[] {"Save Changes", "Delete Meeting", "Cancel"};
				int result = JOptionPane.showOptionDialog(null, meetingPanel, "Edit Meeting Info", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
				if(result == JOptionPane.YES_OPTION)
				{
					if(!(participantstxt.getText().equals("")) && !(datePicker.getModel().getValue().toString().equals("")) && !loctxt.getText().equals("") )	
					{
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String foo = sdf.format(new Date(model.getYear()-1900, model.getMonth(), model.getDay(), model2.getDate().getHours(), model2.getDate().getMinutes()));
						System.out.println(foo);
						boolean ret = eProduceDatabase.editMeetup(meetupNumLabel2.getText(),participantstxt.getText(),loctxt.getText(),  model, model2);
						if(ret)
						{
							JOptionPane.showMessageDialog(frame, "Meetup has been successfully edited!");
							frame.dispose();
							MyMeetingsView mmv = new MyMeetingsView(currentUser);
							frame = mmv.frame;
							frame.setVisible(true);
						}
					}
					else
					{
						String emptyFieldMsg = "Unable to edit meeting. The following fields are empty: \n";
						if(participantstxt.getText().equals("")) emptyFieldMsg += "      Participants\n";
						if(datePicker.getModel().getValue().toString().equals("")) emptyFieldMsg += "      When\n";
						if(loctxt.getText().equals("")) emptyFieldMsg += "      Location\n";
						if(participantstxt.getText().equals("")) emptyFieldMsg += "      Participants\n";
						if(datePicker.getModel().getValue().toString().equals("")) emptyFieldMsg += "      When\n";
						if(loctxt.getText().equals("")) emptyFieldMsg += "      Location\n";
						JOptionPane.showMessageDialog(frame, emptyFieldMsg);
					}
					
				}
				if(result == JOptionPane.NO_OPTION)
				{
					eProduceDatabase.deleteMeetup(myMeetups.get(table.getSelectedRow()).getMeetupNum());
					JOptionPane.showMessageDialog(frame, "Meetup has been successfully deleted!");
					frame.dispose();
					MyMeetingsView mmv = new MyMeetingsView(currentUser);
					frame = mmv.frame;
					frame.setVisible(true);
				}
			}
		});
		
		for(int i = 0; i < table.getColumnCount(); i++){
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
				column.setMinWidth(75);
				column.setPreferredWidth(150);
				column.setMaxWidth(200);
			}
			else if(i == 2)
			{
				column.setMinWidth(100);
				column.setPreferredWidth(150);
				column.setMaxWidth(250);
			}
		}
		meetup.add(new JScrollPane(table));
		
		
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		middlePanel.add(meetup);
		
		leftSide.add(search);
		leftSide.add(sort);
		middlePanel.add(leftSide,BorderLayout.NORTH);
	}
}
