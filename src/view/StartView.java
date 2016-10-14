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

public class StartView {
	eProduceController controller = new eProduceController();
	
	public JFrame frame = new JFrame("eProduce");

	private JTextField emailTF = new JTextField();
	private JPasswordField passwordPF = new JPasswordField();
	private JLabel titleLabel = new JLabel("eProduce");
	private JLabel emailLabel = new JLabel("Email");
	private JLabel passwordLabel = new JLabel("Password");
	private JButton loginButton = new JButton("Login");
	private JButton signUpButton = new JButton("Sign Up");

	public StartView() {
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		JPanel panel = new JPanel();
		frame.add(panel);

		
		panel.setLayout(null);

		/**
		 * Create Labels
		 */

		titleLabel.setBounds(150, 10, 150, 150);
		titleLabel.setFont(titleLabel.getFont().deriveFont(30f));
		panel.add(titleLabel);

		emailLabel.setBounds(100, 110, 80, 25);
		panel.add(emailLabel);

		emailTF.setBounds(145, 110, 160, 25);
		panel.add(emailTF);

		passwordLabel.setBounds(80, 140, 80, 25);
		panel.add(passwordLabel);

		passwordPF.setBounds(145, 140, 160, 25);
		panel.add(passwordPF);

		loginButton.setBounds(140, 170, 80, 25);
		panel.add(loginButton);

		signUpButton.setBounds(230, 170, 80, 25);
		panel.add(signUpButton);
		
		
		/**
		 * Sends you to Signup Page
		 */
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				SignUpView signupview = new SignUpView();
				frame = signupview.frame;
				
			}
		});
	}
}
