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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.eProduceController;
import controller.eProduceDatabase;
import model.Ticket;
import model.User;
//TODO: FIX ALL THIS SHIT
public class TicketView {

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
	public TicketView(User user) {		
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
		JButton myListings = new JButton();
		JButton createTicket = new JButton();
		
		home.setMinimumSize(new Dimension(110, 26));
		home.setMaximumSize(new Dimension(110,26));
		myMeetings.setMinimumSize(new Dimension(110, 26));
		myMeetings.setMaximumSize(new Dimension(110,26));
		myListings.setMinimumSize(new Dimension(110, 26));
		myListings.setMaximumSize(new Dimension(110,26));
		createTicket.setMinimumSize(new Dimension(110, 26));
		createTicket.setMaximumSize(new Dimension(110,26));
		
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
		myMeetings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyMeetingsView mlv = new MyMeetingsView(currentUser);
				mlv.frame.setVisible(true);
				
			}
		});
		
		home.setText("Home");
		myMeetings.setText("MyMeetings");
		myListings.setText("MyListings");
		createTicket.setText("New Ticket");
		sidePanel.setLayout(new BoxLayout(sidePanel,BoxLayout.Y_AXIS));
	
		sidePanel.add(home);
		
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(createTicket);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(myListings);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(myMeetings);
		
		/*
		 * create ticket action button listener
		 */
		
		createTicket.addActionListener(new ActionListener(){
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
						if(db.createTicket(currentUser.getEmail(), destxt.getText()))
						{
							String msg = "Ticket created!";
							JOptionPane.showMessageDialog(frame, msg);
							frame.dispose();
							TicketView tv = new TicketView(currentUser);
							frame = tv.frame;
							frame.setVisible(true);
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
		});
	}
	/*
	 * This a method to hold all of the middle panel information
	 */
	public void middlePanel(){
		ArrayList<Ticket> myTickets = new ArrayList<Ticket>();
		if(currentUser.getAdmin() == 1)
			db.getAllTickets(myTickets);
		else
			db.getMyTickets(currentUser.getEmail(),myTickets);
		
		JTextField search = new JTextField();
		JComboBox sort;
		JTextField[][] tickets = new JTextField[myTickets.size()][4];
		String[][] ticketData = new String[myTickets.size()][4];
		for(int i = 0; i < tickets.length; i++){
			tickets[i][0] = new JTextField("Ticket Number"+i);
			tickets[i][1] = new JTextField("Ticket Description" + i);
			tickets[i][2] = new JTextField("Ticket Owner"+ i);
			tickets[i][3] = new JTextField("Ticket Followup"+ i);

		}
		
		JPanel leftSide = new JPanel();
		JPanel ticketPanel = new JPanel();
		
		for(int i = 0; i < myTickets.size(); i++)
		{
			Ticket currTicket = myTickets.get(i);
			if(currTicket != null)
			{
				tickets[i][0].setText(currTicket.getTicketNum());
				tickets[i][1].setText(currTicket.getDescription());
				tickets[i][2].setText(currTicket.getResponse());
				tickets[i][3].setText(currTicket.getOwner().getEmail());

			}
		}
		for(int i = 0; i < ticketData.length; i++)
		{
			for(int j = 0; j < ticketData[i].length; j++)
			{
				ticketData[i][j] = tickets[i][j].getText();
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
		for(i = 0; i < tickets.length; i++)
		{
			for(int j = 0; j < tickets[i].length; j++)
				tickets[i][j].setEditable(false);
		}
		

		ticketPanel.setLayout(new BoxLayout(ticketPanel,BoxLayout.Y_AXIS));
		String[] columnNames = new String[] {"Ticket #", "Ticket Description", "Ticket Response", "Ticket Owner"};
		DefaultTableModel model = new DefaultTableModel(ticketData, columnNames);
		if(currentUser.getAdmin() == 1)
			model.setColumnCount(4);
		else
			model.setColumnCount(2);
		JTable table = new JTable(model);
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
					
					boolean created = db.updateTicket(destxt.getText(), restxt.getText(), myTickets.get(table.getSelectedRow()).getTicketNum());
					
					JOptionPane.showMessageDialog(frame,message );
					frame.dispose();
					TicketView tv = new TicketView(currentUser);
					frame = tv.frame;
					frame.setVisible(true);
				}else if(result == JOptionPane.NO_OPTION){ //deleted button is clicked
					String message = "Deleted";
					if(currentUser.getAdmin() == 1)
						message = "Closed";
					db.deleteTicket(myTickets.get(table.getSelectedRow()).getTicketNum());
					JOptionPane.showMessageDialog(frame,message);
					frame.dispose();
					TicketView tv = new TicketView(currentUser);
					frame = tv.frame;
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
			if(i == 0)
			{
				column.setMinWidth(75);
				column.setPreferredWidth(75);
				column.setMaxWidth(100);
			}
		}
		ticketPanel.add(new JScrollPane(table));
		
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		
		middlePanel.add(ticketPanel);
		
		leftSide.add(search);
		leftSide.add(sort);
		middlePanel.add(leftSide,BorderLayout.NORTH);
		
	}
}
