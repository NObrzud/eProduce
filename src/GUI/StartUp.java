package GUI;
import javax.swing.*;
import java.awt.*;

public class StartUp  {
	private static JTextField emailTF = new JTextField();
	private static JPasswordField passwordPF = new JPasswordField();
	private static JLabel titleLabel = new JLabel("eProduce");
	private static JLabel emailLabel = new JLabel("Email");
	private static JLabel passwordLabel = new JLabel("Password");
	private static JButton loginButton = new JButton("login");
	private static JButton signUpButton = new JButton("Sign Up");
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("eProduce");
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
	 * Created Labels
	 */
		titleLabel.setBounds(150,10,150,150);
		titleLabel.setFont(titleLabel.getFont().deriveFont(30f));
		panel.add(titleLabel);
		
		emailLabel.setBounds(10, 10, 80, 25);
		panel.add(emailLabel);

	
		emailTF.setBounds(100, 10, 160, 25);
		panel.add(emailTF);

		
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

	
		passwordPF.setBounds(100, 40, 160, 25);
		panel.add(passwordPF);

		
		loginButton.setBounds(10, 80, 80, 25);
		panel.add(loginButton);
		
		
		signUpButton.setBounds(180, 80, 80, 25);
		panel.add(signUpButton);
	}
	
}
