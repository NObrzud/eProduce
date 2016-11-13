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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
import javax.swing.border.Border;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

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
		myLists.setMinimumSize(new Dimension(110, 26));
		myLists.setMaximumSize(new Dimension(110 ,26));
		JButton myMeetings = new JButton();
		myMeetings.setMinimumSize(new Dimension(110, 26));
		myMeetings.setMaximumSize(new Dimension(110,26));
		JButton myTickets = new JButton();
		myTickets.setMinimumSize(new Dimension(110, 26));
		myTickets.setMaximumSize(new Dimension(110,26));
	
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
		
		myLists.setText("MyListings");
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
		ArrayList<Listing> list = new ArrayList<Listing>();
		db.getAllListings(list);
		JTextField search = new JTextField();
		JComboBox sort;
		JTextField[][] listings = new JTextField[list.size()][3];
		String[][] listingData = new String[list.size()][3];
		for(int i = 0; i < listings.length; i++){
			listings[i][0] = new JTextField("Listing Num " + i);
			listings[i][1] = new JTextField("Listing Owner " + i);
			listings[i][2] = new JTextField("Listing Title " + i);
		}

		for(int i = 0; i < listingData.length; i++)
		{
			Listing currListing = list.get(i);
			listings[i][0].setText(Integer.toString(currListing.getListingNum()));
			listings[i][1].setText(currListing.getOwner());
			listings[i][2].setText(currListing.getTitle());
		}
		
		for(int i = 0; i < listingData.length; i++)
		{
			for(int j = 0; j < listingData[i].length; j++)
			{
				listingData[i][j] = listings[i][j].getText();
			}
		}
		JPanel leftSide = new JPanel();
		JPanel listing = new JPanel();
		
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
			for(int j = 0; j < listings[i].length; j++)
				listings[i][j].setEditable(false);
		}
		
		
		listing.setLayout(new BoxLayout(listing,BoxLayout.Y_AXIS));
		JTable table = new JTable(listingData, new String[] {"Listing #","Creator","Title"});
		table.setBackground(frame.getBackground());
		
		table.setShowVerticalLines(false);
		table.setGridColor(Color.black);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setFont(new Font("Serif", Font.PLAIN, 24));
		table.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
		table.setRowHeight(30);
		table.setDefaultEditor(Object.class, null);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				JPanel listPanel = new JPanel();
				JPanel top = new JPanel();
				JPanel bottom = new JPanel();
				JLabel title = new JLabel();
				JLabel owner = new JLabel();
				JLabel des = new JLabel();
								
				JTextField titletxt = new JTextField();
				JTextField ownertxt = new JTextField();
				JTextArea destxt = new JTextArea(5,10);
				Object[] options1 = { "Schedule Meetup", "Contact Owner", "Exit" };
				 	
				
				 	listPanel.setLayout(new GridLayout(0,1));
					top.setLayout(new GridLayout(0,1));
					bottom.setLayout(new BorderLayout());
					//Add SQL statement after text below
					title.setText("Title: ");
					titletxt.setEditable(false);
					titletxt.setText(list.get(table.getSelectedRow()).getTitle());
					owner.setText("Owner: ");
					ownertxt.setEditable(false);
					ownertxt.setText(list.get(table.getSelectedRow()).getOwner());
					des.setText("Description:");
					destxt.setEditable(false);
					destxt.setText(list.get(table.getSelectedRow()).getContent());
					destxt.setLineWrap(true);
					
					
					JButton plus = new JButton();
					JButton minus= new JButton();
					
					try {
						BufferedImage plusImg = ImageIO.read(new File("res/plus.png"));
						BufferedImage minusImg = ImageIO.read(new File("res/minus.png"));
						plus = new JButton(new ImageIcon(plusImg));
						minus = new JButton(new ImageIcon(minusImg));
						plus.setBorder(BorderFactory.createEmptyBorder());
						plus.setContentAreaFilled(false);
						minus.setBorder(BorderFactory.createEmptyBorder());
						minus.setContentAreaFilled(false);
						top.add(plus);
						top.add(minus);
						
					} catch (IOException e) {
						System.out.println("Image file not found!");
					}
					
					
					
					top.add(title);
					top.add(titletxt);
					top.add(owner);
					top.add(ownertxt);
					bottom.add(des,BorderLayout.NORTH);
					bottom.add(destxt,BorderLayout.SOUTH);
					listPanel.add(top,BorderLayout.NORTH);
					listPanel.add(bottom,BorderLayout.SOUTH);
					
				
					
					
					
					
				
				  int result = JOptionPane.showOptionDialog(null, listPanel, "Viewing #" + list.get(table.getSelectedRow()).getListingNum(),
			                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
			                null, options1, null);
				
				if(result == JOptionPane.YES_OPTION){
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
					participantstxt.setText(currentUser.getEmail() + ", " + list.get(table.getSelectedRow()).getOwner());
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
							boolean ret = db.createMeetup(currentUser.getEmail(),participantstxt.getText(),loctxt.getText(),  model, model2);
							if(ret)
							{
								JOptionPane.showMessageDialog(frame, "Meetup has been successfully created!");
								frame.dispose();
								MainPageView mpv = new MainPageView(currentUser);
								frame = mpv.frame;
								frame.setVisible(true);
							}
						}
						else
						{
							String emptyFieldMsg = "Unable to create account. The following fields are empty: \n";
							if(participantstxt.getText().equals("")) emptyFieldMsg += "      Participants\n";
							if(datePicker.getModel().getValue().toString().equals("")) emptyFieldMsg += "      When\n";
							if(loctxt.getText().equals("")) emptyFieldMsg += "      Location\n";
							if(participantstxt.getText().equals("")) emptyFieldMsg += "      Participants\n";
							if(datePicker.getModel().getValue().toString().equals("")) emptyFieldMsg += "      When\n";
							if(loctxt.getText().equals("")) emptyFieldMsg += "      Location\n";
							JOptionPane.showMessageDialog(frame, emptyFieldMsg);
							
							
						}
						
					}
				}
				if(result == JOptionPane.NO_OPTION){
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
					toTF.setText(list.get(table.getSelectedRow()).getOwner());
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
							controller.sendEmail(toTF.getText(), fromTF.getText(), subjectTF.getText(), emailMsg);
						}
						else{
							JOptionPane.showMessageDialog(null, "Email failed to send. Cannot have an empty body.");
						}
					}
				}
				
			}
		});
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
		listing.add(new JScrollPane(table));
		
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		leftSide.add(search);
		middlePanel.add(listing);
		leftSide.add(sort);
		middlePanel.add(leftSide,BorderLayout.NORTH);
	}
}
