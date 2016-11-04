package view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.eProduceController;
import model.User;

public class StartView {
	eProduceController controller = new eProduceController();
	
	public JFrame frame = new JFrame("eProduce");

	private JTextField emailTF = new JTextField();
	private JPasswordField passwordPF = new JPasswordField();
	private JLabel emailLabel = new JLabel("Email");
	private JLabel passwordLabel = new JLabel("Password");
	private JButton loginButton = new JButton("Login");
	private JButton signUpButton = new JButton("Sign Up");
	private User currentUser;
	
	public StartView(User user)
	{
		if(user == null)
			currentUser = new User();
		else
			currentUser = user;
		frame.setSize(500, 375);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		frame.setLocationRelativeTo(null); 
		JPanel panel = new JPanel();
		frame.add(panel);		
		
		panel.setLayout(null);

		/**
		 * Create Labels
		 */
		
		// eProduce Logo code 
		ImageIcon pic = new ImageIcon("res/eProduceLogo.png");
		JLabel imgLabel = new JLabel(pic);
		imgLabel.setBounds(73, 5, 334, 179);
		panel.add(imgLabel);
		

		emailLabel.setBounds(130, 210, 80, 25);
		panel.add(emailLabel);

		emailTF.setBounds(175, 210, 160, 25);
		panel.add(emailTF);

		passwordLabel.setBounds(110, 240, 80, 25);
		panel.add(passwordLabel);

		passwordPF.setBounds(175, 240, 160, 25);
		panel.add(passwordPF);

		loginButton.setBounds(170, 300, 80, 25);
		panel.add(loginButton);

		signUpButton.setBounds(260, 300, 80, 25);
		panel.add(signUpButton);
		
		
		/**
		 * Sends you to Signup Page
		 */
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				SignUpView signupview = new SignUpView(currentUser);
				frame = signupview.frame;
				
			}
		});
		
		/**
		 * Sends you to Main Page
		 */
		loginButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				   if(!(passwordPF.getText().equals("")) && controller.validateLogin(currentUser, emailTF, passwordPF))
		            {
		            	frame.dispose();
					   if(currentUser.getAdmin() == 1)
					   {
						   AdminMainPageView amp = new AdminMainPageView(currentUser);
						   frame = amp.frame;
					   }
					   else
		               {
						   MainPageView mp = new MainPageView(currentUser);
						   frame = mp.frame;
		               }
		            }
		            else
		            {
		            	  JOptionPane.showMessageDialog(frame, "Incorrect login. Please try again."); //temporary handling, TODO: discuss how to handle and implement
		            }
				
			}
		});
	}
}
