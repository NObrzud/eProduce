package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import controller.eProduceDatabase;
import controller.eProducePanels;
import model.Listing;
import model.User;

public class MyListingsView {

	public JFrame frame = new JFrame("eProduce");
	public JPanel sidePanel = new JPanel();
	public JPanel middlePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private User currentUser;
	private int i = 0;
	/**
	 * Set the Listings up in this method each method called is a panel.
	 */
	public MyListingsView(User user) {		
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
		String titleLabel = "eProduce - MyListings";
		topPanel = eProducePanels.topPanel(frame, titleLabel, true, true, topPanel, currentUser);
	}
	/*
	 * This a method to hold all of the side panel information
	 */
	public void sidePanel(){
		sidePanel = eProducePanels.sidePanel(frame, true, false, true, true, true, false, sidePanel, currentUser);
	}
	/*
	 * This a method to hold all of the middle panel information
	 */
	public void middlePanel(){
		ArrayList<Listing> myListings = new ArrayList<Listing>();
		eProduceDatabase.getMyListings(currentUser.getEmail(),myListings); //get all of the listings of the current user and put into myListings ArrayList
		
		JTextField[][] listings = new JTextField[myListings.size()][4];
		String[][] listingData = new String[myListings.size()][4];
		for(int i = 0; i < listings.length; i++){
			listings[i][0] = new JTextField("Listing Title "+i);
			listings[i][1] = new JTextField("Listing Content " + i);
			listings[i][2] = new JTextField("Listing Tags " + i);
			listings[i][3] = new JTextField("Listing Num " + i);
		}
		
		JPanel leftSide = new JPanel();
		JPanel listing = new JPanel();
		
		for(int i = 0; i < myListings.size(); i++)
		{
			Listing currListing = myListings.get(i);
			if(currListing != null)
			{
				listings[i][0].setText(currListing.getListingNum());
				listings[i][1].setText(currListing.getTitle());
				listings[i][2].setText(currListing.getContent());
				listings[i][3].setText(currListing.getTags());
			}
		}
		for(int i = 0; i < listingData.length; i++)
		{
			for(int j = 0; j < listingData[i].length; j++)
			{
				listingData[i][j] = listings[i][j].getText();
			}
		}
		
		for(i = 0; i < listings.length; i++)
		{
			for(int j = 0; j < listings[i].length; j++)
				listings[i][j].setEditable(false);
		}
		

		listing.setLayout(new BoxLayout(listing,BoxLayout.Y_AXIS));
		JTable table = new JTable(listingData, new String[] {"Listing #","Title","Content","Tags"});
		table.setBackground(frame.getBackground()); //sets background color of each cell to the frame's background.
		table.setShowVerticalLines(false); //doesn't show vertical gridlines
		table.setGridColor(Color.black); //changes the gridline's colors to black
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setFont(new Font("Serif", Font.PLAIN, 24)); //changes font to be larger
		table.setBorder(new MatteBorder(1, 1, 1, 1, Color.black)); //Gives a black border around the table
		table.setRowHeight(30); //number of rows to have in the table.
		table.setDefaultEditor(Object.class, null); //disables "double-click to edit" functionality
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				JPanel listPanel = new JPanel();
				JPanel top = new JPanel();
				JPanel bottom = new JPanel();
				JLabel title = new JLabel();
				JLabel tags = new JLabel();
				JLabel des = new JLabel();
				JTextField titletxt = new JTextField(10);
				JTextField tagstxt = new JTextField(10);
				JTextArea destxt = new JTextArea(5,10);
				Object[] options1 = { "Save Changes","Delete Listing", "Cancel" };

				listPanel.setLayout(new GridLayout(0,1));
				top.setLayout(new GridLayout(0,1));
				bottom.setLayout(new BorderLayout());
				//Add SQL statement after text below
				title.setText("Title: ");
				tags.setText("Tags: ");
				des.setText("Description:");
				destxt.setLineWrap(true);
				tagstxt.setText(myListings.get(table.getSelectedRow()).getTags());
				titletxt.setText(myListings.get(table.getSelectedRow()).getTitle());
				destxt.setText(myListings.get(table.getSelectedRow()).getContent());
				top.add(title);
				top.add(titletxt);
				top.add(tags);
				top.add(tagstxt);
				top.setSize(new Dimension(30, 30));
				bottom.add(des,BorderLayout.NORTH);
				bottom.add(destxt,BorderLayout.SOUTH);
				listPanel.add(top);
				listPanel.add(bottom,BorderLayout.SOUTH);


				int result = JOptionPane.showOptionDialog(null, listPanel, "Edit Listing",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, options1, null);

				if(result == JOptionPane.YES_OPTION){ //saved button is clicked
					String message = "Saved";
					boolean created = eProduceDatabase.updateListing(titletxt.getText(), destxt.getText(),myListings.get(table.getSelectedRow()).getListingNum(), tagstxt.getText());
					
					JOptionPane.showMessageDialog(frame,message );
					frame.dispose();
					MyListingsView mlv = new MyListingsView(currentUser);
					frame = mlv.frame;
					frame.setVisible(true);
				}else if(result == JOptionPane.NO_OPTION){ //deleted button is clicked
					String message = "Deleted";
					eProduceDatabase.deleteListing(myListings.get(table.getSelectedRow()).getListingNum());
					JOptionPane.showMessageDialog(frame,message );
					frame.dispose();
					MyListingsView mlv = new MyListingsView(currentUser);
					frame = mlv.frame;
					frame.setVisible(true);
				}
			}
		});

		for(i = 0; i < table.getColumnCount(); i++) //this loop simply sets the text to display in the center
		{
			DefaultTableCellRenderer center = new DefaultTableCellRenderer();
			center.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setCellRenderer(center);
			if(i == 0)
			{
				column.setMinWidth(75);
				column.setPreferredWidth(75);
				column.setMaxWidth(100);
			}
		}
		listing.add(new JScrollPane(table));
		
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		
		middlePanel.add(listing);
		
		middlePanel.add(leftSide,BorderLayout.NORTH);
		
	}
}
