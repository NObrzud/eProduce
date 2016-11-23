package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.User;
import view.AdminMainPageView;
import view.TicketView;

/*
 * This class handles all panel creation to be passed back to the respective views. 
 */
public class eProducePanels {
	public static JPanel topPanel(JFrame frame, String title, boolean showMyAccount, boolean showLogout, JPanel topPanel, User currentUser)
	{
		JLabel titleLabel = new JLabel(title);
		JButton myAccount = new JButton();
		JButton logout = new JButton();
		JPanel rightSide = new JPanel();
		JPanel leftSide = new JPanel();
		
		topPanel.setLayout(new BorderLayout());
		rightSide.setLayout(new FlowLayout(FlowLayout.RIGHT));
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		myAccount.setText("MyAccount");
		logout.setText("Log Out");
		titleLabel.setBounds(150, 10, 150, 150);
		titleLabel.setFont(titleLabel.getFont().deriveFont(30f));
		
		leftSide.add(titleLabel);
		if(showMyAccount) rightSide.add(myAccount);
		if(showLogout) rightSide.add(logout);
		
		ActionListener logoutActionListener = eProduceActionListeners.createLogoutActionListener(frame, currentUser);
		ActionListener myAccountActionListener = eProduceActionListeners.createMyAccountActionListener(frame, currentUser);
		
		// Log outs action button listener logs the user out
		logout.addActionListener(logoutActionListener);
		
		// Opens window to edit account details.
		myAccount.addActionListener(myAccountActionListener);
		
		topPanel.add(leftSide,BorderLayout.WEST);
		topPanel.add(rightSide,BorderLayout.EAST);
		
		return topPanel;
	}
	public static JPanel sidePanel(JFrame frame, boolean showHome, boolean showCreateTicket, boolean showCreateListings, boolean showTickets, boolean showMeetings, boolean showListings, JPanel sidePanel, User currentUser)
	{
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		if(showHome)
		{
			JButton home = new JButton("Home");
			ActionListener homeActionListener = eProduceActionListeners.createHomeActionListener(frame, currentUser);
			home.setMinimumSize(new Dimension(110, 26));
			home.setMaximumSize(new Dimension(110, 26));
			home.addActionListener(homeActionListener);
			sidePanel.add(home);
		}
		if(showCreateTicket)
		{
			sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
			JButton createTicket = new JButton("New Ticket");
			ActionListener createTicketActionListener = eProduceActionListeners.createCreateTicketActionListener(frame, currentUser);
			createTicket.setMinimumSize(new Dimension(110, 26));
			createTicket.setMaximumSize(new Dimension(110, 26));
			createTicket.addActionListener(createTicketActionListener);
			sidePanel.add(createTicket);
		}	
		if(showCreateListings)
		{
			sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
			JButton createListing = new JButton("New Listing");
			ActionListener createListingActionListener = eProduceActionListeners.createListingActionListener(frame, currentUser);
			createListing.setMinimumSize(new Dimension(110, 26));
			createListing.setMaximumSize(new Dimension(110, 26));
			createListing.addActionListener(createListingActionListener);
			sidePanel.add(createListing);
		}
		if(showTickets)
		{
			sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
			JButton myTickets = new JButton("MyTickets");
			ActionListener myTicketsActionListener = eProduceActionListeners.createMyTicketsActionListener(frame, currentUser);
			myTickets.setMinimumSize(new Dimension(110, 26));
			myTickets.setMaximumSize(new Dimension(110, 26));
			myTickets.addActionListener(myTicketsActionListener);
			sidePanel.add(myTickets);
		}
		if(showMeetings)
		{
			sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
			JButton myMeetings = new JButton("MyMeetings");
			ActionListener myMeetingsActionListener = eProduceActionListeners.createMyMeetingsActionListener(frame, currentUser);
			myMeetings.setMinimumSize(new Dimension(110, 26));
			myMeetings.setMaximumSize(new Dimension(110, 26));
			myMeetings.addActionListener(myMeetingsActionListener);
			sidePanel.add(myMeetings);
		}
		if(showListings)
		{
			sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
			JButton myListings = new JButton("MyListings");
			ActionListener myListingsActionListener = eProduceActionListeners.createMyListingsActionListener(frame, currentUser);
			myListings.setMinimumSize(new Dimension(110, 26));
			myListings.setMaximumSize(new Dimension(110, 26));
			myListings.addActionListener(myListingsActionListener);
			sidePanel.add(myListings);
		}
		return sidePanel;
	}
	public static JPanel adminSidePanel(JFrame frame, boolean showHome, boolean showTickets, JPanel sidePanel, User currentUser)
	{
		sidePanel.setLayout(new BoxLayout(sidePanel,BoxLayout.Y_AXIS));
		
		if(showHome)
		{
			JButton allUsers = new JButton();
			ActionListener allUsersActionListener = eProduceActionListeners.createAllUsersActionListener(frame, currentUser);
			allUsers.setText("Manage Users");
			allUsers.setMinimumSize(new Dimension(130, 26));
			allUsers.setMaximumSize(new Dimension(130,26));
			allUsers.addActionListener(allUsersActionListener);
			sidePanel.add(allUsers);
			sidePanel.add(Box.createRigidArea(new Dimension(5,5)));
		}
		if(showTickets)
		{
			JButton sysTickets = new JButton();
			ActionListener sysTicketsActionListener = eProduceActionListeners.createSysTicketsActionListener(frame, currentUser);
			sysTickets.setText("System Tickets");
			sysTickets.setMinimumSize(new Dimension(130, 26));
			sysTickets.setMaximumSize(new Dimension(130,26));
			sysTickets.addActionListener(sysTicketsActionListener);
			sidePanel.add(sysTickets);
		}
		return sidePanel;
	}
}
