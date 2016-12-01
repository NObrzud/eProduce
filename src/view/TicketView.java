package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.eProduceActionListeners;
import controller.eProduceDatabase;
import controller.eProducePanels;
import model.Ticket;
import model.User;

public class TicketView {

	public JFrame frame = new JFrame("eProduce");
	public JPanel sidePanel = new JPanel();
	public JPanel middlePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private User currentUser;
	private int i = 0;
	/**
	 * Set the Listings up in this method each method called is a panel.
	 */
	public TicketView(User user) {		
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
		String titleLabel = "eProduce - MyTickets";
		topPanel = eProducePanels.topPanel(frame, titleLabel, true, true, topPanel, currentUser);
	}
	/*
	 * This a method to hold all of the side panel information
	 */
	public void sidePanel(){
		if(currentUser.getAdmin() == 1)
			sidePanel = eProducePanels.adminSidePanel(frame, true, false, sidePanel, currentUser);
		else
			sidePanel = eProducePanels.sidePanel(frame, true, true, false, false, true, true, sidePanel, currentUser);
	}
	/*
	 * This a method to hold all of the middle panel information
	 */
	public void middlePanel(){
		ArrayList<Ticket> myTickets = new ArrayList<Ticket>();
		if(currentUser.getAdmin() == 1)
			eProduceDatabase.getAllTickets(myTickets);
		else
			eProduceDatabase.getMyTickets(currentUser.getEmail(),myTickets);
		
		JTextField[][] tickets = new JTextField[myTickets.size()][4];
		String[][] ticketData = new String[myTickets.size()][4];
		for(int i = 0; i < tickets.length; i++){
			tickets[i][0] = new JTextField("Ticket Number"+i);
			tickets[i][1] = new JTextField("Ticket Description" + i);
			tickets[i][2] = new JTextField("Ticket Owner"+ i);
			tickets[i][3] = new JTextField("Ticket Followup"+ i);

		}
		
		JPanel leftSide = new JPanel();
		JPanel ticketPanel = new JPanel();
		
		for(int i = 0; i < myTickets.size(); i++)
		{
			Ticket currTicket = myTickets.get(i);
			if(currTicket != null)
			{
				tickets[i][0].setText(currTicket.getTicketNum());
				tickets[i][1].setText(currTicket.getDescription());
				tickets[i][2].setText(currTicket.getResponse());
				tickets[i][3].setText(currTicket.getOwner().getEmail());

			}
		}
		for(int i = 0; i < ticketData.length; i++)
		{
			for(int j = 0; j < ticketData[i].length; j++)
			{
				ticketData[i][j] = tickets[i][j].getText();
			}
		}

		for(i = 0; i < tickets.length; i++)
		{
			for(int j = 0; j < tickets[i].length; j++)
				tickets[i][j].setEditable(false);
		}
		

		ticketPanel.setLayout(new BoxLayout(ticketPanel,BoxLayout.Y_AXIS));
		String[] columnNames = new String[] {"Ticket #", "Ticket Description", "Ticket Response", "Ticket Owner"};
		DefaultTableModel model = new DefaultTableModel(ticketData, columnNames);
		JTable table = new JTable(model);
		ListSelectionListener ticketTableListener = eProduceActionListeners.createTicketTableListener(frame, table, myTickets, currentUser);
		if(currentUser.getAdmin() == 1)
			model.setColumnCount(4);
		else
			model.setColumnCount(2);
		table.setBackground(frame.getBackground()); //sets background color of each cell to the frame's background.
		table.setShowVerticalLines(false); //doesn't show vertical gridlines
		table.setGridColor(Color.black); //changes the gridline's colors to black
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setFont(new Font("Serif", Font.PLAIN, 24)); //changes font to be larger
		table.setBorder(new MatteBorder(1, 1, 1, 1, Color.black)); //Gives a black border around the table
		table.setRowHeight(30); //number of rows to have in the table.
		table.setDefaultEditor(Object.class, null); //disables "double-click to edit" functionality
		
		table.getSelectionModel().addListSelectionListener(ticketTableListener);

		for(i = 0; i < table.getColumnCount(); i++)
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
		ticketPanel.add(new JScrollPane(table));
		
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		
		middlePanel.add(ticketPanel);
		
		middlePanel.add(leftSide,BorderLayout.NORTH);
		
	}
}
