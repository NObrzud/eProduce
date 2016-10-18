package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminMainPageView {

	public JFrame frame = new JFrame("eProduce-Admin");
	public JPanel middlePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	/*
	 * The Main frame that holds everything
	 */
	public AdminMainPageView(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		frame.setSize(xSize, ySize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		
		topPanel();		
		middlePanel();

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(middlePanel, BorderLayout.CENTER);
		frame.setVisible(true);

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
