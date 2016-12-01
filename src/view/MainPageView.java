package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.eProduceActionListeners;
import controller.eProduceController;
import controller.eProduceDatabase;
import controller.eProducePanels;
import model.Listing;
import model.User;


public class MainPageView {

	public JFrame frame = new JFrame("eProduce");
	public JPanel sidePanel = new JPanel();
	public JPanel middlePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private User currentUser;

	/**
	 * Set the main page up in this method each method called is a panel.
	 */
	public MainPageView(User user) {		
		currentUser = user;
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = (int)(((int) tk.getScreenSize().getWidth())*.75);
		int ySize = (int)(((int) tk.getScreenSize().getHeight())*.75);
		frame.setSize(xSize, ySize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); 
		frame.setLayout(new BorderLayout());
		
		topPanel();
		sidePanel();
		middlePanel();

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(sidePanel, BorderLayout.WEST);
		frame.add(middlePanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	/*
	 * This a method to hold all of the top panel information
	 */
	
	public void topPanel(){
		String titleLabel = "eProduce - Home";
		topPanel = eProducePanels.topPanel(frame, titleLabel, true, true, topPanel, currentUser);
	}
	/*
	 * This a method to hold all of the side panel information
	 */
	public void sidePanel(){
		sidePanel = eProducePanels.sidePanel(frame, true, false, false, true, true, true, sidePanel, currentUser);
	}
	/*
	 * This a method to hold all of the middle panel information
	 */
	public void middlePanel(){
		JButton searchButton = new JButton("Search");
		
		ArrayList<Listing> list = new ArrayList<Listing>();
		eProduceDatabase.getAllListings(list);
		JTextField search = new JTextField();
		JTextField[][] listings = new JTextField[list.size()][3];
		String[][] listingData = new String[list.size()][3];
		for(int i = 0; i < listings.length; i++){
			listings[i][0] = new JTextField("Listing Num " + i);
			listings[i][1] = new JTextField("Listing Owner " + i);
			listings[i][2] = new JTextField("Listing Title " + i);
		}

		for(int i = 0; i < listingData.length; i++)
		{
			Listing currListing = list.get(i);
			listings[i][0].setText(currListing.getListingNum());
			listings[i][1].setText(currListing.getOwner().getEmail());
			listings[i][2].setText(currListing.getTitle());
		}
		
		for(int i = 0; i < listingData.length; i++)
		{
			for(int j = 0; j < listingData[i].length; j++)
			{
				listingData[i][j] = listings[i][j].getText();
			}
		}
		
		JPanel leftSide = new JPanel();
		JPanel listing = new JPanel();
		
		search.setText("Search.....");
		search.setColumns(50);
		search.addMouseListener(eProduceActionListeners.createSearchMouseListener(frame,search,searchButton));
				
		
		for(int i = 0; i < listings.length; i++)
		{
			for(int j = 0; j < listings[i].length; j++)
				listings[i][j].setEditable(false);
		}
		
		
		listing.setLayout(new BoxLayout(listing,BoxLayout.Y_AXIS));
		String[] columnHeadings = new String[] {"Listing #", "Creator", "Title"};
		JTable table = eProduceController.createListingTable(frame, list, listingData, columnHeadings, currentUser);
		
		listing.add(new JScrollPane(table));
		
		searchButton.addActionListener(eProduceActionListeners.createSearchActionListener(frame, currentUser, listingData, list, search, table, columnHeadings, searchButton));

		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		leftSide.add(search);
		leftSide.add(searchButton);
		middlePanel.add(listing);
		middlePanel.add(leftSide,BorderLayout.NORTH);
	}
}
