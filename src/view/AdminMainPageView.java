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
import java.util.ArrayList;

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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import controller.eProduceController;
import controller.eProduceDatabase;
import model.Listing;
import model.User;

public class AdminMainPageView{

	public JFrame frame = new JFrame("eProduce");
	public JPanel sidePanel = new JPanel();
	public JPanel middlePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private User currentUser;
	private eProduceController controller = new eProduceController();
	private eProduceDatabase db = new eProduceDatabase();
	private int i = 0;
	/**
	 * Set the Listings up in this method each method called is a panel.
	 */
	public AdminMainPageView(User user) {		
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
		JLabel titleLabel = new JLabel("eProduce - Admin Home");
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
		JButton home = new JButton();
		JButton myMeetings = new JButton();
		JButton myTickets = new JButton();
		JButton createListing = new JButton();
		
		home.setMinimumSize(new Dimension(110, 26));
		home.setMaximumSize(new Dimension(110,26));
		myMeetings.setMinimumSize(new Dimension(110, 26));
		myMeetings.setMaximumSize(new Dimension(110,26));
		myTickets.setMinimumSize(new Dimension(110, 26));
		myTickets.setMaximumSize(new Dimension(110,26));
		createListing.setMinimumSize(new Dimension(110, 26));
		createListing.setMaximumSize(new Dimension(110,26));
		
		myTickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				TicketView tix = new TicketView(currentUser);
				tix.frame.setVisible(true);
				
			}
		});
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				AdminMainPageView ampv = new AdminMainPageView(currentUser);
				ampv.frame.setVisible(true);
				
			}
		});
		myMeetings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyMeetingsView mlv = new MyMeetingsView(currentUser);
				mlv.frame.setVisible(true);
				
			}
		});
		
	
		home.setText("Home");
		myMeetings.setText("MyMeetings");
		myTickets.setText("MyTickets");
		createListing.setText("New Listing");
		sidePanel.setLayout(new BoxLayout(sidePanel,BoxLayout.Y_AXIS));
	
		sidePanel.add(home);
		
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(createListing);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(myMeetings);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(myTickets);
		
		/*
		 * create ticket action button listener
		 */
		
		createListing.addActionListener(new ActionListener(){
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
						if(db.createListing(currentUser.getEmail(), titletxt.getText(), destxt.getText(), tagstxt.getText()))
						{
							String msg = "Listing created!";
							JOptionPane.showMessageDialog(frame, msg);
							frame.dispose();
							MyListingsView mlv = new MyListingsView(currentUser);
							frame = mlv.frame;
							frame.setVisible(true);
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
		});
	}
	/*
	 * This a method to hold all of the middle panel information
	 */
	public void middlePanel(){
		ArrayList<User> allUsers = new ArrayList<User>();
		db.getAllUsers(allUsers);
		
		JTextField search = new JTextField();
		JComboBox sort;
		JTextField[][] usersTF = new JTextField[allUsers.size()][7];
		String[][] userData = new String[allUsers.size()][7];
		for(int i = 0; i < usersTF.length; i++){
			usersTF[i][0] = new JTextField("User Email: "+i);
			usersTF[i][1] = new JTextField("First Name: " + i);
			usersTF[i][2] = new JTextField("Last Name: " + i);
			usersTF[i][3] = new JTextField("User rating: " + i);
			usersTF[i][4] = new JTextField("Number of reports: " + i);
			usersTF[i][5] = new JTextField("Is Admin?: " + i);
			usersTF[i][6] = new JTextField("Is Banned?: " + i);
		}
		
		JPanel leftSide = new JPanel();
		JPanel listing = new JPanel();
		
		for(int i = 0; i < allUsers.size(); i++)
		{
			User currUser = allUsers.get(i);
			if(currUser != null)
			{
				usersTF[i][0].setText(currUser.getEmail());
				usersTF[i][1].setText(currUser.getFirstName());
				usersTF[i][2].setText(currUser.getLastName());
				usersTF[i][3].setText(Integer.toString(currUser.getCurrentRating()));
				usersTF[i][4].setText(Integer.toString(currUser.getNumReports()));
				usersTF[i][5].setText(Integer.toString(currUser.getAdmin()));
				usersTF[i][6].setText(Integer.toString(currUser.getBlocked()));
			}
		}
		for(int i = 0; i < userData.length; i++)
		{
			for(int j = 0; j < userData[i].length; j++)
			{
				userData[i][j] = usersTF[i][j].getText();
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
		for(i = 0; i < usersTF.length; i++)
		{
			for(int j = 0; j < usersTF[i].length; j++)
				usersTF[i][j].setEditable(false);
		}
		

		listing.setLayout(new BoxLayout(listing,BoxLayout.Y_AXIS));
		JTable table = new JTable(userData, new String[] {"User Email","First name","Last name","Rating", "Number of Reports", "Is Admin?", "Is Banned?"});
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
				JPanel listPanel = new JPanel();
				JPanel top = new JPanel();
				JLabel email = new JLabel();
				JLabel admin = new JLabel();
				JLabel banned = new JLabel();
				JLabel emailtxt = new JLabel();
				JLabel admintxt = new JLabel();
				JLabel bannedtxt = new JLabel();
				Object[] options1 = { "Ban User", "Make Admin","Cancel" };

				listPanel.setLayout(new GridLayout(0,1));
				top.setLayout(new GridLayout(0,1));
				//Add SQL statement after text below
				email.setText("User Email: ");
				admin.setText("Is Admin?: ");
				banned.setText("Is Banned?:");
				emailtxt.setText(allUsers.get(table.getSelectedRow()).getEmail());
				admintxt.setText(Integer.toString(allUsers.get(table.getSelectedRow()).getAdmin()));
				bannedtxt.setText(Integer.toString(allUsers.get(table.getSelectedRow()).getBlocked()));
				
				if(Integer.parseInt(bannedtxt.getText()) == 1)
				{
					options1[0] = "Unban User";
				}
				if(Integer.parseInt(admintxt.getText()) == 1)
				{
					options1[1] = "Revoke Admin";
				}
				top.add(email);
				top.add(emailtxt);
				top.add(admin);
				top.add(admintxt);
				top.add(banned);
				top.add(bannedtxt);
				top.setSize(new Dimension(30, 30));
				listPanel.add(top);


				int result = JOptionPane.showOptionDialog(null, listPanel, "Edit Listing",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, options1, null);

				if(result == JOptionPane.YES_OPTION){ //ban user button is clicked
					String message = "Done!";
					db.setUserBanStatus(emailtxt.getText(), Integer.parseInt(bannedtxt.getText()) == 1 ? 0 : 1);
					
					JOptionPane.showMessageDialog(frame,message );
					frame.dispose();
					AdminMainPageView ampv = new AdminMainPageView(currentUser);
					frame = ampv.frame;
					frame.setVisible(true);
				}else if(result == JOptionPane.NO_OPTION){ //deleted button is clicked
					String message = "Done!";
					db.setUserAdminStatus(emailtxt.getText(), Integer.parseInt(admintxt.getText()) == 1 ? 0 : 1);
					JOptionPane.showMessageDialog(frame,message );
					frame.dispose();
					AdminMainPageView ampv = new AdminMainPageView(currentUser);
					frame = ampv.frame;
					frame.setVisible(true);
				}
			}
		});

		for(i = 0; i < table.getColumnCount(); i++)
		{
			DefaultTableCellRenderer center = new DefaultTableCellRenderer();
			center.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setCellRenderer(center);
		}
		listing.add(new JScrollPane(table));
		
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		
		middlePanel.add(listing);
		
		leftSide.add(search);
		leftSide.add(sort);
		middlePanel.add(leftSide,BorderLayout.NORTH);
		
	}
}
