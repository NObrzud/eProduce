package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.eProduceController;
import model.User;



public class SignUpView {
	public JFrame frame= new JFrame("eProduce - Signup");
	private JPanel panel = new JPanel();
	
	private User currentUser = new User();
	
	private JTextField firstNameTF = new JTextField();
	private JTextField lastNameTF = new JTextField();
	private JTextField emailTF = new JTextField();	
	private JPasswordField passwordPF = new JPasswordField();
	private JPasswordField rePasswordPF = new JPasswordField();
	
	private JLabel firstNameLabel = new JLabel("First Name:");
	private JLabel lastNameLabel = new JLabel("Last Name:");
	private JLabel emailLabel = new JLabel("Email:");
	private JLabel passwordLabel = new JLabel("Password:");
	private JLabel rePasswordLabel = new JLabel("Confirm Password:");

	private JButton submitButton = new JButton("Submit");
	private JButton cancelButton = new JButton("Cancel");
	

	public SignUpView(User user)
	{
		currentUser = user;
		frame.setVisible(true);
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); 
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
		
		emailLabel.setBounds(130, 90, 80, 25);
		panel.add(emailLabel);
		
		emailTF.setBounds(130, 110, 160, 25);
		panel.add(emailTF);
		
		passwordLabel.setBounds(130, 130, 80, 25);
		panel.add(passwordLabel);
		
		passwordPF.setBounds(130, 150, 160, 25);
		panel.add(passwordPF);
		
		rePasswordLabel.setBounds(130, 170, 130, 25);
		panel.add(rePasswordLabel);
		
		rePasswordPF.setBounds(130, 190, 160, 25);
		panel.add(rePasswordPF);
		
		cancelButton.setBounds(120, 230, 75, 20);
		panel.add(cancelButton);
		
		submitButton.setBounds(220, 230, 75, 20);
		panel.add(submitButton);
		
		/**
		 * Cancels sign up and moves back to login screen
		 */
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				StartView sv = new StartView(currentUser);
				frame = sv.frame;
				frame.setVisible(true);
			}	
		});
		submitButton.addActionListener(new ActionListener()
				{
					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent e)
					{
						if(!(firstNameTF.getText().equals("")) && !(lastNameTF.getText().equals("")) && !emailTF.getText().equals("")  //if firstname, lastname, email, password, confirm pass 
								   && !(passwordPF.getText().equals("")) && passwordPF.getText().equals(rePasswordPF.getText()))			   //are not null, and password and confirm pass are equal...
						{
							if(eProduceController.validateEmail(emailTF.getText()))
							{
								if(eProduceController.createNewAccount(firstNameTF, lastNameTF, emailTF, passwordPF, rePasswordPF))
								{
									JOptionPane.showMessageDialog(frame, "Account has been successfully created!");
									frame.dispose();
									StartView sv = new StartView(currentUser);
									frame = sv.frame;
									frame.setVisible(true);
								}
							}
							else if(!eProduceController.validateEmail(emailTF.getText()))
							{
								JOptionPane.showMessageDialog(frame, "Error invalid email address"); 
							}
							else
							{
								JOptionPane.showMessageDialog(frame, "Could not connect to database. Please check internet access"); 
							}
						}
						else
						{
							String emptyFieldMsg = "Unable to create account. The following fields are empty: \n";
							if(firstNameTF.getText().equals("")) emptyFieldMsg += "      First Name\n";
							if(lastNameTF.getText().equals("")) emptyFieldMsg += "      Last Name\n";
							if(emailTF.getText().equals("")) emptyFieldMsg += "      Email\n";
							if(passwordPF.getText().equals("")) emptyFieldMsg += "      Password\n";
							if(rePasswordPF.getText().equals("")) emptyFieldMsg += "      Confirm Password\n";
							if(!passwordPF.getText().equals(rePasswordPF.getText())) emptyFieldMsg = "Unable to create account. Passwords do not match.";
							JOptionPane.showMessageDialog(frame, emptyFieldMsg);
						}
					}
				});
	}
}
