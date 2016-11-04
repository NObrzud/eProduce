package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import controller.eProduceController;
import model.User;


public class MyAccountView {
	public JFrame frame= new JFrame("eProduce - MyAccount");
	private JPanel panel = new JPanel();
	private User currentUser = new User();
	
	public eProduceController controller = new eProduceController();

	private JTextField firstNameTF = new JTextField();
	private JTextField lastNameTF = new JTextField();
	private JTextField emailTF = new JTextField();	
	private JPasswordField passwordPF = new JPasswordField();

	
	private JLabel firstNameLabel = new JLabel("First Name:");
	private JLabel lastNameLabel = new JLabel("Last Name:");
	private JLabel emailLabel = new JLabel("Email:");
	private JLabel passwordLabel = new JLabel("Password:");

	private JButton submitButton = new JButton("Save Changes");
	private JButton cancelButton = new JButton("Cancel");
	
	public MyAccountView(User user)
	{
		currentUser = user;
		frame.setVisible(true);
		frame.setSize(300, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); 
		frame.add(panel);
		
		panel.setLayout(null);
		
		
		firstNameLabel.setBounds(0, 10, 80, 25);
		panel.add(firstNameLabel);
		
		firstNameTF.setBounds(0, 30, 160, 25);
		panel.add(firstNameTF);
		
		lastNameLabel.setBounds(0, 50, 80, 25);
		panel.add(lastNameLabel);
		
		lastNameTF.setBounds(0, 70, 160, 25);
		panel.add(lastNameTF);
		
		emailLabel.setBounds(0, 90, 80, 25);
		panel.add(emailLabel);
		
		emailTF.setBounds(0, 110, 160, 25);
		panel.add(emailTF);
		
		passwordLabel.setBounds(0, 130, 80, 25);
		panel.add(passwordLabel);
		
		passwordPF.setBounds(0, 150, 160, 25);
		passwordPF.setEchoChar('*');
		panel.add(passwordPF);
		
		cancelButton.setBounds(0, 180, 75, 20);
		panel.add(cancelButton);
		
		submitButton.setBounds(100, 180, 115, 20);
		panel.add(submitButton);
		
		/**
		 * Cancels sign up and moves back to login screen
		 */
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainPageView sv = new MainPageView(currentUser);
				frame = sv.frame;
				frame.setVisible(true);
			}	
		});
	
	}
}

