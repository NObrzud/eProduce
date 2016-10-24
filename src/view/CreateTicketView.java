package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateTicketView {

	public JFrame frame= new JFrame("eProduce - Signup");
	private JPanel panel = new JPanel();
	
	private JTextField firstNameTF = new JTextField();
	private JTextField lastNameTF = new JTextField();
	private JTextField ticketReasonTF = new JTextField();	
	private JTextField otherCommentsTF = new JTextField();

	
	private JLabel firstNameLabel = new JLabel("First Name:");
	private JLabel lastNameLabel = new JLabel("Last Name:");
	private JLabel ticketReasonLabel = new JLabel("Ticket Reason:");
	private JLabel otherCommentsLabel = new JLabel("other Comments:");


	private JButton submitButton = new JButton("Submit");
	private JButton backButton = new JButton("Back");
	
	
	public CreateTicketView(){
		frame.setVisible(true);
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.add(panel);
		
		panel.setLayout(null);
		
		firstNameLabel.setBounds(130, 10, 80, 25);
		panel.add(firstNameLabel);
		
		firstNameTF.setBounds(130, 30, 160, 25);
		panel.add(firstNameTF);
		
		lastNameLabel.setBounds(130, 50, 80, 25);
		panel.add(lastNameLabel);
		
		lastNameTF.setBounds(130, 70, 160, 25);
		panel.add(lastNameTF);
		
		ticketReasonLabel.setBounds(130, 90, 80, 25);
		panel.add(ticketReasonLabel);
		
		ticketReasonTF.setBounds(130, 110, 160, 25);
		panel.add(ticketReasonTF);
		
		otherCommentsLabel.setBounds(130, 130, 80, 25);
		panel.add(otherCommentsLabel);
		
		otherCommentsTF.setBounds(130, 110, 160, 25);
		panel.add(otherCommentsTF);
		
		
		backButton.setBounds(120, 230, 75, 20);
		panel.add(backButton);
		
		submitButton.setBounds(220, 230, 75, 20);
		panel.add(submitButton);
		
		/**
		 * Cancels sign up and moves back to login screen
		 */
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainPageView mainPageView = new MainPageView();
				frame = mainPageView.frame;
				frame.setVisible(true);
				
			}
		});
	}

}
