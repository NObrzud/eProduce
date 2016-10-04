package GUI;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MainPage {
	
	public static JFrame frame = new JFrame("eProduce");
	public static JPanel sidePanel = new JPanel();
	public static JPanel middlePanel = new JPanel();
	private static JPanel searchPanel = new JPanel();
	private static JPanel listingPanel = new JPanel();
	private static JPanel insidePanel = new JPanel();
	private static JLabel titleLabel = new JLabel("eProduce");
	private static JLabel searchLabel = new JLabel("Search:");
	private static JTextField postings = new JTextField();
	private static JTextField searchTF = new JTextField("Look up Listings");
	/**
	 * Create the main page
	 */
	public void createMain() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		frame.setSize(xSize, ySize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
	
		
	/*
	 * sidePanel is the side panel and insidePanel is the middle part of the side panel
	 */
		sidePanel.setLayout(new BorderLayout());
		
		 insidePanel.setLayout(new BoxLayout(insidePanel,BoxLayout.Y_AXIS));
		 JButton clbtn=new JButton("Create Listings");
		 JButton vclbtn=new JButton("View all My  Listings");
		 JButton ctbtn =new JButton("Create Ticket");
		 JButton vtbtn = new JButton("View Ticktets");
		 JButton rubtn = new JButton("Report User");
		 JButton lobtn = new JButton("Log Out");
		 insidePanel.add(vclbtn);
		 insidePanel.add(ctbtn);
		 insidePanel.add(vtbtn);
		 insidePanel.add(rubtn);
		 
		 sidePanel.add(clbtn,BorderLayout.NORTH);
		 sidePanel.add(insidePanel,BorderLayout.CENTER);
		 sidePanel.add(lobtn,BorderLayout.SOUTH);
	
		
		/*
		 * searchPanel holds the search label and search text field.
		 */
		 searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		 searchPanel.add(searchLabel);
		 searchPanel.add(searchTF);
		 
		 /*
		  * listingPanel holds the all of the listings.
		  */
		 listingPanel.setLayout(new BoxLayout(listingPanel,BoxLayout.Y_AXIS));
		 listingPanel.add(postings);
		
		 
		/*
		 * middlePanel is the middle panel of the frame holds the title,search text, search text field and posting
		 */
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		titleLabel.setBounds(150,10,150,150);
		titleLabel.setFont(titleLabel.getFont().deriveFont(30f));
		postings.setText("Posting");
		postings.setEditable(false);
		 middlePanel.add(titleLabel);
		 middlePanel.add(searchPanel);
		 middlePanel.add(listingPanel);
		  
		 /*
		  * add everthing to the frame.
		  */
		 
		    frame.add(sidePanel,BorderLayout.WEST);  
		    frame.add(middlePanel,BorderLayout.CENTER);        
		  frame.setVisible(true);  
	
	}
}
