package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import controller.eProduceDatabase;
import controller.eProducePanels;
import model.User;

public class AdminMainPageView{

	public JFrame frame = new JFrame("eProduce");
	public JPanel sidePanel = new JPanel();
	public JPanel middlePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private User currentUser;
	private int i = 0;
	/**
	 * Set the Listings up in this method each method called is a panel.
	 */
	public AdminMainPageView(User user) {		
		currentUser = user;
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = (int)(((int) tk.getScreenSize().getWidth())*.75);
		int ySize = (int)(((int) tk.getScreenSize().getHeight())*.75);
		frame.setSize(xSize, ySize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); 
		frame.setLayout(new BorderLayout());
		
		topPanel(); //topPanel refers to the account/logout button portion
		sidePanel(); //sidePanel refers to the home/system tickets portion
		middlePanel(); //middlePanel refers to the JTable of users

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(sidePanel, BorderLayout.WEST);
		frame.add(middlePanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	/*
	 * This a method to hold all of the top panel information
	 */
	public void topPanel(){
		String titleLabel = "eProduce - Admin Home";
		topPanel = eProducePanels.topPanel(frame, titleLabel, false, true, topPanel, currentUser);
	}
	/*
	 * This a method to hold all of the side panel information
	 */
	public void sidePanel(){
		sidePanel = eProducePanels.adminSidePanel(frame, false, true, sidePanel, currentUser);				
	}
	/*
	 * This a method to hold all of the middle panel information
	 */
	public void middlePanel(){
		ArrayList<User> allUsers = new ArrayList<User>();
		eProduceDatabase.getAllUsers(allUsers); //fill the allUsers ArrayList with Users
		
		JTextField[][] usersTF = new JTextField[allUsers.size()][7];
		String[][] userData = new String[allUsers.size()][7];
		for(int i = 0; i < usersTF.length; i++){
			usersTF[i][0] = new JTextField("User Email: "+i);
			usersTF[i][1] = new JTextField("First Name: " + i);
			usersTF[i][2] = new JTextField("Last Name: " + i);
			usersTF[i][3] = new JTextField("User rating: " + i);
			usersTF[i][4] = new JTextField("Number of reports: " + i);
			usersTF[i][5] = new JTextField("Is Admin?: " + i);
			usersTF[i][6] = new JTextField("Is Banned?: " + i);
		}
		
		JPanel leftSide = new JPanel();
		JPanel listing = new JPanel();
		
		for(int i = 0; i < allUsers.size(); i++)
		{
			User currUser = allUsers.get(i);
			if(currUser != null)
			{
				usersTF[i][0].setText(currUser.getEmail());
				usersTF[i][1].setText(currUser.getFirstName());
				usersTF[i][2].setText(currUser.getLastName());
				usersTF[i][3].setText(Integer.toString(currUser.getCurrentRating()));
				usersTF[i][4].setText(Integer.toString(currUser.getNumReports()));
				usersTF[i][5].setText(Integer.toString(currUser.getAdmin()));
				usersTF[i][6].setText(Integer.toString(currUser.getBlocked()));
			}
		}
		for(int i = 0; i < userData.length; i++)
		{
			for(int j = 0; j < userData[i].length; j++)
			{
				userData[i][j] = usersTF[i][j].getText(); //fill the userData array with all the user data
			}
		}
		
		for(i = 0; i < usersTF.length; i++)
		{
			for(int j = 0; j < usersTF[i].length; j++)
				usersTF[i][j].setEditable(false);
		}
		

		listing.setLayout(new BoxLayout(listing,BoxLayout.Y_AXIS));
		JTable table = new JTable(userData, new String[] {"User Email","First name","Last name","Rating", "Number of Reports", "Is Admin?", "Is Banned?"});
		table.setBackground(frame.getBackground()); //sets background color of each cell to the frame's background.
		table.setShowVerticalLines(false); //doesn't show vertical gridlines
		table.setGridColor(Color.black); //changes the gridline's colors to black
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setFont(new Font("Serif", Font.PLAIN, 24)); //changes font to be larger
		table.setBorder(new MatteBorder(1, 1, 1, 1, Color.black)); //Gives a black border around the table
		table.setRowHeight(30); //number of rows to have in the table.
		table.setDefaultEditor(Object.class, null); //disables "double-click to edit" functionality
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) { //this will happen when you double click on one of the JTable cells
				JPanel listPanel = new JPanel();
				JPanel top = new JPanel();
				JLabel email = new JLabel();
				JLabel admin = new JLabel();
				JLabel banned = new JLabel();
				JLabel emailtxt = new JLabel();
				JLabel admintxt = new JLabel();
				JLabel bannedtxt = new JLabel();
				Object[] options1 = { "Ban User", "Make Admin","Cancel" };//button texts

				listPanel.setLayout(new GridLayout(0,1));
				top.setLayout(new GridLayout(0,1));
				//Add SQL statement after text below
				email.setText("User Email: ");
				admin.setText("Is Admin?: ");
				banned.setText("Is Banned?:");
				
				//pre-populate data
				emailtxt.setText(allUsers.get(table.getSelectedRow()).getEmail()); 							
				admintxt.setText(Integer.toString(allUsers.get(table.getSelectedRow()).getAdmin()));
				bannedtxt.setText(Integer.toString(allUsers.get(table.getSelectedRow()).getBlocked()));
				//if user is banned, show "unban user" instead of "ban user" , if user is admin show "revoke admin" insetad of "Make admin"
				if(Integer.parseInt(bannedtxt.getText()) == 1)
				{
					options1[0] = "Unban User";
				}
				if(Integer.parseInt(admintxt.getText()) == 1)
				{
					options1[1] = "Revoke Admin";
				}
				top.add(email);
				top.add(emailtxt);
				top.add(admin);
				top.add(admintxt);
				top.add(banned);
				top.add(bannedtxt);
				top.setSize(new Dimension(30, 30));
				listPanel.add(top);

				//actually display listPanel, which contains the buttons to ban/unban user as well as general user info
				int result = JOptionPane.showOptionDialog(null, listPanel, "Manage User Permissions",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, options1, null);

				if(result == JOptionPane.YES_OPTION){ //ban user button is clicked
					String message = "Change user's block status!";
					eProduceDatabase.setUserBanStatus(emailtxt.getText(), Integer.parseInt(bannedtxt.getText()) == 1 ? 0 : 1);
					
					JOptionPane.showMessageDialog(frame,message );
					frame.dispose();
					AdminMainPageView ampv = new AdminMainPageView(currentUser);
					frame = ampv.frame;
					frame.setVisible(true);
				}else if(result == JOptionPane.NO_OPTION){ //deleted button is clicked
					String message = "Changed user's admin status!";
					eProduceDatabase.setUserAdminStatus(emailtxt.getText(), Integer.parseInt(admintxt.getText()) == 1 ? 0 : 1);
					JOptionPane.showMessageDialog(frame,message );
					frame.dispose();
					AdminMainPageView ampv = new AdminMainPageView(currentUser);
					frame = ampv.frame;
					frame.setVisible(true);
				}
			}
		});

		for(i = 0; i < table.getColumnCount(); i++) //this loop sets the text to be center aligned.
		{
			DefaultTableCellRenderer center = new DefaultTableCellRenderer();
			center.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setCellRenderer(center);
		}
		listing.add(new JScrollPane(table)); //allows scrollability
		
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //10 pixels of padding

		
		middlePanel.add(listing);
		
		middlePanel.add(leftSide,BorderLayout.NORTH);
		
	}
}
