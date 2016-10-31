package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.User;

public class AdminMainPageView {

	public JFrame frame = new JFrame("eProduce-Admin");
	public JPanel middlePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private User currentUser;
	/*
	 * The Main frame that holds everything
	 */
	public AdminMainPageView(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = (int)(((int) tk.getScreenSize().getWidth())*.75);
		int ySize = (int)(((int) tk.getScreenSize().getHeight())*.75);
		frame.setSize(xSize, ySize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); 
		frame.setLayout(new BorderLayout());
		
		topPanel();		
		middlePanel();

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(middlePanel, BorderLayout.CENTER);
		frame.setVisible(true);

	}
	public AdminMainPageView(User user)
	{
		this(); //default constructor
		currentUser = user;
	}
	/*
	 * The top panel that holds the title and the log out button
	 */
	public void topPanel(){
		JLabel titleLabel = new JLabel("eProduce-Admin");
		JButton logout = new JButton();
		JPanel rigthSide = new JPanel();
		JPanel leftSide = new JPanel();
		
		topPanel.setLayout(new BorderLayout());
		rigthSide.setLayout(new FlowLayout(FlowLayout.RIGHT));
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		logout.setText("Log Out");
		titleLabel.setBounds(150, 10, 150, 150);
		titleLabel.setFont(titleLabel.getFont().deriveFont(30f));
		
		/*
		 * Log outs action button listener logs the user out
		 */
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentUser = null;
				frame.dispose();
				StartView start = new StartView();
				start.frame.setVisible(true);
				
			}
		});
		
		leftSide.add(titleLabel);
		rigthSide.add(logout);
		
	
		topPanel.add(leftSide,BorderLayout.WEST);
		topPanel.add(rigthSide,BorderLayout.EAST);
	}
	/*
	 * The middle panel that holds the manage user button and system tickets button
	 */
	public void middlePanel(){
		JButton manageUser = new JButton();
		JButton systemTickets = new JButton();
		
		middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.Y_AXIS));
		manageUser.setText("Manage Users");
		
		systemTickets.setText("System Tickets");
		
		middlePanel.add(manageUser);
		middlePanel.add(Box.createRigidArea(new Dimension(5,5)));
		middlePanel.add(systemTickets);
	}
}
