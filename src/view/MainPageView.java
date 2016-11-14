package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import controller.eProduceController;
import controller.eProduceDatabase;
import controller.eProducePanels;
import model.Listing;
import model.User;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

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
		JButton myLists = new JButton();
		myLists.setMinimumSize(new Dimension(110, 26));
		myLists.setMaximumSize(new Dimension(110 ,26));
		JButton myMeetings = new JButton();
		myMeetings.setMinimumSize(new Dimension(110, 26));
		myMeetings.setMaximumSize(new Dimension(110,26));
		JButton myTickets = new JButton();
		myTickets.setMinimumSize(new Dimension(110, 26));
		myTickets.setMaximumSize(new Dimension(110,26));
	
		myTickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				TicketView tix = new TicketView(currentUser);
				tix.frame.setVisible(true);
				
			}
		});
		
		myLists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyListingsView mlv = new MyListingsView(currentUser);
				mlv.frame.setVisible(true);
				
			}
		});
		
		//myMeetings.addActionListener();
		
		myLists.setText("MyListings");
		myMeetings.setText("MyMeetings");
		myTickets.setText("MyTickets");
		
		sidePanel.setLayout(new BoxLayout(sidePanel,BoxLayout.Y_AXIS));
	
		sidePanel.add(myLists);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(myMeetings);
		sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		sidePanel.add(myTickets);
	}
	/*
	 * This a method to hold all of the middle panel information
	 */
	public void middlePanel(){
		ArrayList<Listing> list = new ArrayList<Listing>();
		eProduceDatabase.getAllListings(list);
		JTextField search = new JTextField();
		JComboBox sort;
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
			listings[i][0].setText(Integer.toString(currListing.getListingNum()));
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
		search.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				search.setText("");
			}
		});
		
		String [] comboBoxInputs = {"Sort By","Date - Newest", "Date - Oldest"};
		sort = new JComboBox(comboBoxInputs);
		
		for(int i = 0; i < listings.length; i++)
		{
			for(int j = 0; j < listings[i].length; j++)
				listings[i][j].setEditable(false);
		}
		
		
		listing.setLayout(new BoxLayout(listing,BoxLayout.Y_AXIS));
		JTable table = new JTable(listingData, new String[] {"Listing #","Creator","Title"});
		table.setBackground(frame.getBackground());
		
		table.setShowVerticalLines(false);
		table.setGridColor(Color.black);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setFont(new Font("Serif", Font.PLAIN, 24));
		table.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
		table.setRowHeight(30);
		table.setDefaultEditor(Object.class, null);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				JPanel listPanel = new JPanel();
				JPanel top = new JPanel();
				JPanel bottom = new JPanel();
				JPanel ratingPanel = new JPanel();
				JPanel userPanel = new JPanel();
				JLabel title = new JLabel();
				JLabel owner = new JLabel();
				JLabel ownerRating = new JLabel();
				JLabel des = new JLabel();
								
				JTextField titletxt = new JTextField();
				JTextField ownertxt = new JTextField();
				JButton reportButton = new JButton("Report");
				JTextField rating = new JTextField();
				JTextArea destxt = new JTextArea(5,10);
				Object[] options1 = { "Schedule Meetup", "Contact Owner", "Exit" };
				 	
				
				 	listPanel.setLayout(new GridLayout(0,1));
				 	ratingPanel.setLayout(new GridLayout(0,4));
				 	userPanel.setLayout(new GridLayout(0,3));
					top.setLayout(new GridLayout(0,1));
					bottom.setLayout(new BorderLayout());
					//Add SQL statement after text below
					title.setText("Title: ");
					titletxt.setEditable(false);
					titletxt.setText(list.get(table.getSelectedRow()).getTitle());
					owner.setText("Owner: ");
					ownertxt.setEditable(false);
					ownertxt.setText(list.get(table.getSelectedRow()).getOwner().getEmail());
					ownerRating.setText("Owner Rating: ");
					rating.setEditable(false);
					rating.setText(Integer.toString(list.get(table.getSelectedRow()).getOwner().getCurrentRating()));
					rating.setHorizontalAlignment(JTextField.CENTER);
					des.setText("Description:");
					destxt.setEditable(false);
					destxt.setText(list.get(table.getSelectedRow()).getContent());
					destxt.setLineWrap(true);
					
					try {
						ImageIcon plusImg = new ImageIcon(ImageIO.read(new File("res/plus.png")));
						ImageIcon minusImg = new ImageIcon(ImageIO.read(new File("res/minus.png")));
						ImageIcon plusImgGrey = new ImageIcon(ImageIO.read(new File("res/plus-grey.png")));
						ImageIcon minusImgGrey = new ImageIcon(ImageIO.read(new File("res/minus-grey.png")));
						final JButton plus = new JButton(plusImgGrey);
						final JButton minus = new JButton(minusImgGrey);
						
						plus.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(list.get(table.getSelectedRow()).getOwner().getEmail().equals(currentUser.getEmail()))
									; //don't do anything if you're viewing your own listing, that's cheating.
								else if(plus.getIcon().equals(plusImg)) //de-pressing plus
								{	
									int newRating = Integer.parseInt(rating.getText())-1;
									rating.setText(Integer.toString(newRating));
									list.get(table.getSelectedRow()).getOwner().setCurrentRating(newRating);
									eProduceDatabase.decreaseUserRating(ownertxt.getText());									
									plus.setIcon(plusImgGrey);
								}
								else // pressing plus
								{
									int newRating = Integer.parseInt(rating.getText())+1;
									if(minus.getIcon().equals(minusImg)) //if minus is already pressed.
									{
										minus.setIcon(minusImgGrey);
										newRating++;
									}
									rating.setText(Integer.toString(newRating));
									list.get(table.getSelectedRow()).getOwner().setCurrentRating(newRating);
									eProduceDatabase.increaseUserRating(ownertxt.getText());
									plus.setIcon(plusImg);
								}
							}
						});
						
						minus.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(list.get(table.getSelectedRow()).getOwner().getEmail().equals(currentUser.getEmail()))
									; //don't do anything if you're viewing your own listing, that's cheating.
								else if(minus.getIcon().equals(minusImg)) //they're de-pressing minus
								{
									int newRating = Integer.parseInt(rating.getText())+1;
									rating.setText(Integer.toString(newRating));
									list.get(table.getSelectedRow()).getOwner().setCurrentRating(newRating);
									eProduceDatabase.increaseUserRating(ownertxt.getText());
									minus.setIcon(minusImgGrey);
								}
								else //pressing minus
								{	
									int newRating = Integer.parseInt(rating.getText())-1;
									if(plus.getIcon().equals(plusImg)) //if plus is already pressed.
									{
										plus.setIcon(plusImgGrey);
										newRating--;
									}
									rating.setText(Integer.toString(newRating));
									list.get(table.getSelectedRow()).getOwner().setCurrentRating(newRating);
									eProduceDatabase.decreaseUserRating(ownertxt.getText());									
									minus.setIcon(minusImg);
								}
							}
						});
						
						reportButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if(list.get(table.getSelectedRow()).getOwner().getEmail().equals(currentUser.getEmail()))
									; //don't do anything if you're viewing your own listing, that's cheating.
								eProduceDatabase.reportUser(list.get(table.getSelectedRow()).getOwner().getEmail());
								JOptionPane.showMessageDialog(frame, "User has been reported. Thanks!");
							}
						});
					ratingPanel.add(ownerRating);
					ratingPanel.add(minus);
					ratingPanel.add(rating);
					ratingPanel.add(plus);
					userPanel.add(owner);
					userPanel.add(ownertxt);
					userPanel.add(reportButton);
					top.add(title);
					top.add(titletxt);
					top.add(userPanel);
					top.add(ratingPanel);
					bottom.add(des,BorderLayout.NORTH);
					bottom.add(destxt,BorderLayout.SOUTH);
					listPanel.add(top,BorderLayout.NORTH);
					listPanel.add(bottom,BorderLayout.SOUTH);
					
					} catch (IOException e) {
						System.out.println("Image file not found!");
					}
					
					
					
					
					
				
				  int result = JOptionPane.showOptionDialog(null, listPanel, "Viewing #" + list.get(table.getSelectedRow()).getListingNum(),
			                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
			                null, options1, null);
				
				if(result == JOptionPane.YES_OPTION){
					JPanel meetingPanel = new JPanel();
					JLabel participantsLabel = new JLabel();
					JLabel whenLabel = new JLabel();
					JLabel timeLabel = new JLabel();
					JLabel locationLabel = new JLabel();
					JTextField whenTF = new JTextField(10);
					JPanel metPanel = new JPanel();
					JLabel participantslbl = new JLabel();
					JLabel whenlbl = new JLabel();
					JLabel timelbl = new JLabel();
					JLabel loclbl = new JLabel();
					JTextField participantstxt = new JTextField(10);
					JTextField loctxt = new JTextField(10);
					SpinnerDateModel model2 = new SpinnerDateModel();
					model2.setCalendarField(Calendar.MINUTE);
					JSpinner spinner= new JSpinner();
					spinner.setModel(model2);
					spinner.setEditor(new JSpinner.DateEditor(spinner, "hh:mm a"));
					
					spinner.setSize(10,10);
					meetingPanel.setLayout(new GridLayout(0,1));
					
					
					participantsLabel.setText("Participants: ");
					whenLabel.setText("When: ");
					timeLabel.setText("Time");
					locationLabel.setText("Location:");
					participantslbl.setText("Participants: ");
					whenlbl.setText("When: ");
					timelbl.setText("Time");
					loclbl.setText("Location:");
					participantstxt.setText(currentUser.getEmail() + ", " + list.get(table.getSelectedRow()).getOwner());
					UtilDateModel model = new UtilDateModel();
					JDatePanelImpl datePanel = new JDatePanelImpl(model);
					JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
					
				
					meetingPanel.add(participantsLabel);
					meetingPanel.add(participantstxt);
					meetingPanel.add(whenLabel);
					meetingPanel.add(datePicker);
					meetingPanel.add(timeLabel);
					meetingPanel.add(spinner);
					meetingPanel.add(locationLabel);
					meetingPanel.add(loctxt);
					
				
					
					int result2 = JOptionPane.showConfirmDialog(null, meetingPanel, "Create Meeting Info", JOptionPane.OK_CANCEL_OPTION);
					if(result2 == JOptionPane.OK_OPTION)
					{
						if(!(participantstxt.getText().equals("")) && !(datePicker.getModel().getValue().toString().equals("")) && !loctxt.getText().equals("") )	
						{
							java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String foo = sdf.format(new Date(model.getYear()-1900, model.getMonth(), model.getDay(), model2.getDate().getHours(), model2.getDate().getMinutes()));
							System.out.println(foo);
							boolean ret = eProduceDatabase.createMeetup(currentUser.getEmail(),participantstxt.getText(),loctxt.getText(),  model, model2);
							if(ret)
							{
								JOptionPane.showMessageDialog(frame, "Meetup has been successfully created!");
								frame.dispose();
								MainPageView mpv = new MainPageView(currentUser);
								frame = mpv.frame;
								frame.setVisible(true);
							}
						}
						else
						{
							String emptyFieldMsg = "Unable to create meetup. The following fields are empty: \n";
							if(participantstxt.getText().equals("")) emptyFieldMsg += "      Participants\n";
							if(datePicker.getModel().getValue().toString().equals("")) emptyFieldMsg += "      When\n";
							if(loctxt.getText().equals("")) emptyFieldMsg += "      Location\n";
							if(participantstxt.getText().equals("")) emptyFieldMsg += "      Participants\n";
							if(datePicker.getModel().getValue().toString().equals("")) emptyFieldMsg += "      When\n";
							if(loctxt.getText().equals("")) emptyFieldMsg += "      Location\n";
							JOptionPane.showMessageDialog(frame, emptyFieldMsg);
							
							
						}
						
					}
				}
				if(result == JOptionPane.NO_OPTION){
					JPanel contactPanel = new JPanel();
					JPanel north = new JPanel();
					JPanel south = new JPanel();
					JLabel toLabel = new JLabel("To:");
					JLabel fromLabel = new JLabel("From:");
					JLabel subjectLabel = new JLabel("Subject:");
					JLabel contentLabel = new JLabel("Email Content:");
					JTextField toTF = new JTextField(20);
					JTextField fromTF = new JTextField(20);
					JTextField subjectTF = new JTextField(20);
					JTextArea contentArea = new JTextArea(8,30);
					
					
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					contentArea.setLineWrap(true);
					contentArea.setBorder(border);
					JScrollPane sp = new JScrollPane(contentArea);
					
					contactPanel.setLayout(new BorderLayout());
					north.setLayout(new GridLayout(0,1));
					south.setLayout(new BorderLayout());
					toTF.setText(list.get(table.getSelectedRow()).getOwner().getEmail());
					fromTF.setText(currentUser.getEmail());
					subjectTF.setText("Another eProduce user would like to contact you!");
					toTF.setEditable(false);
					fromTF.setEditable(false);
					subjectTF.setEditable(false);
					
					north.add(toLabel);
					north.add(toTF);
					north.add(fromLabel);
					north.add(fromTF);
					north.add(subjectLabel);
					north.add(subjectTF);
					south.add(contentLabel, BorderLayout.NORTH);
					south.add(sp, BorderLayout.SOUTH);
					
					
					contactPanel.add(north, BorderLayout.NORTH);
					contactPanel.add(south, BorderLayout.SOUTH);
					
					int result2 = JOptionPane.showConfirmDialog(null, contactPanel, "Contact Owner", JOptionPane.OK_CANCEL_OPTION);
					if(result2 == JOptionPane.OK_OPTION)
					{
						if(!contentArea.getText().isEmpty()){
							String emailMsg = "Hello eProduce User!\nAccording to our system, there is a user that would like to contact you in regards to your listing. "
									+ "The following is a message from " + fromTF.getText() +":\n"
											+ "\n" + contentArea.getText() +"\n"
											+ "\n If you would like to contact this user, you may contact him using the following email address: " + fromTF.getText();
							eProduceController.sendEmail(toTF.getText(), fromTF.getText(), subjectTF.getText(), emailMsg);
						}
						else{
							JOptionPane.showMessageDialog(null, "Email failed to send. Cannot have an empty body.");
						}
					}
				}
				
			}
		});
		for(int i = 0; i < table.getColumnCount(); i++)
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
			if(i == 1)
			{
				column.setMaxWidth(300);
			}
			column.setPreferredWidth(348);
		}
		listing.add(new JScrollPane(table));
		
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		leftSide.add(search);
		middlePanel.add(listing);
		leftSide.add(sort);
		middlePanel.add(leftSide,BorderLayout.NORTH);
	}
}
