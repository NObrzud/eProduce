package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class TicketView {

	public JFrame frame= new JFrame("eProduce - myTickets");
	private JPanel panel = new JPanel();
	
;
	
	private JLabel ticket_1_Label = new JLabel("Ticket 1:");
	private JLabel ticket_2_Label = new JLabel("Ticket 2:");
	private JLabel ticket_3_Label = new JLabel("Ticket 3:");
	private JLabel ticket_4_Label = new JLabel("Ticket 4:");
	private JLabel ticket_5_Label = new JLabel("Ticket 5:");


	private JButton backButton = new JButton("Back");
	
	
	public TicketView(){
		frame.setVisible(true);
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.add(panel);
		
		panel.setLayout(null);
		
		ticket_1_Label.setBounds(130, 10, 80, 25);
		panel.add(ticket_1_Label);
		
		
		ticket_2_Label.setBounds(130, 50, 80, 25);
		panel.add(ticket_2_Label);
		

		
		ticket_3_Label.setBounds(130, 90, 80, 25);
		panel.add(ticket_3_Label);
		
	
		
		ticket_4_Label.setBounds(130, 130, 80, 25);
		panel.add(ticket_4_Label);
		

		ticket_5_Label.setBounds(130, 170, 130, 25);
		panel.add(ticket_5_Label);
		
	
		
		backButton.setBounds(120, 230, 75, 20);
		panel.add(backButton);
		

		
		/**
		 * Cancels TicketView and moves back to MainPageView
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
