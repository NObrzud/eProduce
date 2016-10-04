package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class StartUp  {
	private static JTextField emailTF = new JTextField();
	private static JPasswordField passwordPF = new JPasswordField();
	private static JLabel titleLabel = new JLabel("eProduce");
	private static JLabel emailLabel = new JLabel("Email");
	private static JLabel passwordLabel = new JLabel("Password");
	private static JButton loginButton = new JButton("Login");
	private static JButton signUpButton = new JButton("Sign Up");
	public static JFrame frame = new JFrame("eProduce");
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		JPanel panel = new JPanel();
		frame.add(panel);
		placeComponents(panel);

		frame.setVisible(true);
	}
	/**
	 * Create the panel.
	 */
	private static void placeComponents(JPanel panel) {

		panel.setLayout(null);
	
		/**
		 * Create Labels
		 */
	
		titleLabel.setBounds(150,10,150,150);
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
		/**
		 * Sends you to MainPage
		 */
		loginButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            frame.setVisible(false);
	           
	            MainPage mp = new MainPage();
	            mp.createMain();
	         }          
	      });
		
		signUpButton.setBounds(230, 170, 80, 25);
		panel.add(signUpButton);
	}
	
}
