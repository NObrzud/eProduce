package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.User;



public class TicketView {
	public JFrame frame = new JFrame("eProduce");
	public JPanel sidePanel = new JPanel();
	public JPanel middlePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private User currentUser;
	
	public TicketView(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = (int)(((int) tk.getScreenSize().getWidth())*.75);
		int ySize = (int)(((int) tk.getScreenSize().getHeight())*.75);
		frame.setSize(xSize, ySize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		
		topPanel();
		sidePanel();
		middlePanel();

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(sidePanel, BorderLayout.WEST);
		frame.add(middlePanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	public TicketView(User user)
	{
		this();//default constructor
		currentUser = user;
	}
	
	public void topPanel(){
		JLabel titleLabel = new JLabel("eProduce - MyTickets");
		JButton myAccount = new JButton();
		JButton logout = new JButton();
		JPanel rigthSide = new JPanel();
		JPanel leftSide = new JPanel();
		
		topPanel.setLayout(new BorderLayout());
		rigthSide.setLayout(new FlowLayout(FlowLayout.RIGHT));
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		myAccount.setText("MyAccount");
		logout.setText("Log Out");
		titleLabel.setBounds(150, 10, 150, 150);
		titleLabel.setFont(titleLabel.getFont().deriveFont(30f));
		
		leftSide.add(titleLabel);
		rigthSide.add(myAccount);
		rigthSide.add(logout);
		
		/*
		 * Log outs action button listener logs the user out
		 */
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				StartView start = new StartView();
				start.frame.setVisible(true);
				
			}
		});
		
		topPanel.add(leftSide,BorderLayout.WEST);
		topPanel.add(rigthSide,BorderLayout.EAST);
		
	
	}
	/*
	 * This a method to hold all of the side panel information
	 */
	public void sidePanel(){
		JButton myLists = new JButton();
		JButton myMeetings = new JButton();
		JButton homeButton = new JButton();
		JButton createButton= new JButton();
	
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainPageView main = new MainPageView();
				main.frame.setVisible(true);
				
			}
		});
		
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				CreateTicketView main = new CreateTicketView();
				main.frame.setVisible(true);
				
			}
		});
		
		
		
		myLists.setText("MyLists");
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
		JList<String> listings;
		JPanel leftSide = new JPanel();
		
		
		  DefaultListModel<String> listModel = new DefaultListModel<>();
	        listModel.addElement("USA");
	        listModel.addElement("India");
	        listModel.addElement("Vietnam");
	        listModel.addElement("Canada");
	        listModel.addElement("Denmark");
	        listModel.addElement("France");
	        listModel.addElement("Great Britain");
	        listModel.addElement("Japan");
	        listings = new JList<>(listModel);
	  
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		
		
		middlePanel.add(leftSide,BorderLayout.NORTH);
		middlePanel.add(listings,BorderLayout.CENTER);
		
	}

}
