package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import controller.eProduceDatabase;
import model.Listing;
import model.User;

class RadioButtonRenderer implements TableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value == null)
			return null;
		return (Component) value;
	}
}

@SuppressWarnings("serial")
class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
	private JRadioButton button;

	public RadioButtonEditor(JCheckBox checkBox) {
		super(checkBox);
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value == null)
			return null;
		button = (JRadioButton) value;
		button.addItemListener(this);
		return (Component) value;
	}

	public Object getCellEditorValue() {
		return button.isSelected();
	}

	public void itemStateChanged(ItemEvent e) {
		System.out.println(e.getItem() + " selected");
		System.out.println("Press Press");
		super.fireEditingStopped();
	}
}

class ButtonRenderer implements TableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value == null)
			return null;
		return (Component) value;
	}
}

class ButtonColumn extends AbstractCellEditor
implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener
{
private JTable table;
private Action action;
private int mnemonic;
private Border originalBorder;
private Border focusBorder;

private JButton renderButton;
private JButton editButton;
private Object editorValue;
private boolean isButtonColumnEditor;

/**
 *  Create the ButtonColumn to be used as a renderer and editor. The
 *  renderer and editor will automatically be installed on the TableColumn
 *  of the specified column.
 *
 *  @param table the table containing the button renderer/editor
 *  @param action the Action to be invoked when the button is invoked
 *  @param column the column to which the button renderer/editor is added
 */
public ButtonColumn(JTable table, Action action, int column)
{
	this.table = table;
	this.action = action;

	renderButton = new JButton();
	editButton = new JButton();
	editButton.setFocusPainted( false );
	editButton.addActionListener( this );
	originalBorder = editButton.getBorder();
	setFocusBorder( new LineBorder(Color.BLUE) );

	TableColumnModel columnModel = table.getColumnModel();
	columnModel.getColumn(column).setCellRenderer( this );
	columnModel.getColumn(column).setCellEditor( this );
	table.addMouseListener( this );
}


/**
 *  Get foreground color of the button when the cell has focus
 *
 *  @return the foreground color
 */
public Border getFocusBorder()
{
	return focusBorder;
}

/**
 *  The foreground color of the button when the cell has focus
 *
 *  @param focusBorder the foreground color
 */
public void setFocusBorder(Border focusBorder)
{
	this.focusBorder = focusBorder;
	editButton.setBorder( focusBorder );
}

public int getMnemonic()
{
	return mnemonic;
}

/**
 *  The mnemonic to activate the button when the cell has focus
 *
 *  @param mnemonic the mnemonic
 */
public void setMnemonic(int mnemonic)
{
	this.mnemonic = mnemonic;
	renderButton.setMnemonic(mnemonic);
	editButton.setMnemonic(mnemonic);
}

@Override
public Component getTableCellEditorComponent(
	JTable table, Object value, boolean isSelected, int row, int column)
{
	if (value == null)
	{
		editButton.setText( "" );
		editButton.setIcon( null );
	}
	else if (value instanceof Icon)
	{
		editButton.setText( "" );
		editButton.setIcon( (Icon)value );
	}
	else
	{
		editButton.setText( value.toString() );
		editButton.setIcon( null );
	}

	this.editorValue = value;
	return editButton;
}

@Override
public Object getCellEditorValue()
{
	return editorValue;
}

//
//Implement TableCellRenderer interface
//
public Component getTableCellRendererComponent(
	JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
{
	if (isSelected)
	{
		renderButton.setForeground(table.getSelectionForeground());
 		renderButton.setBackground(table.getSelectionBackground());
	}
	else
	{
		renderButton.setForeground(table.getForeground());
		renderButton.setBackground(UIManager.getColor("Button.background"));
	}

	if (hasFocus)
	{
		renderButton.setBorder( focusBorder );
	}
	else
	{
		renderButton.setBorder( originalBorder );
	}

//	renderButton.setText( (value == null) ? "" : value.toString() );
	if (value == null)
	{
		renderButton.setText( "" );
		renderButton.setIcon( null );
	}
	else if (value instanceof Icon)
	{
		renderButton.setText( "" );
		renderButton.setIcon( (Icon)value );
	}
	else
	{
		renderButton.setText( value.toString() );
		renderButton.setIcon( null );
	}

	return renderButton;
}

//
//Implement ActionListener interface
//
/*
 *	The button has been pressed. Stop editing and invoke the custom Action
 */
public void actionPerformed(ActionEvent e)
{
	int row = table.convertRowIndexToModel( table.getEditingRow() );
	fireEditingStopped();

	//  Invoke the Action

	ActionEvent event = new ActionEvent(
		table,
		ActionEvent.ACTION_PERFORMED,
		"" + row);
	action.actionPerformed(event);
}

//
//Implement MouseListener interface
//
/*
 *  When the mouse is pressed the editor is invoked. If you then then drag
 *  the mouse to another cell before releasing it, the editor is still
 *  active. Make sure editing is stopped when the mouse is released.
 */
public void mousePressed(MouseEvent e)
{
	if (table.isEditing()
	&&  table.getCellEditor() == this)
		isButtonColumnEditor = true;
}

public void mouseReleased(MouseEvent e)
{
	if (isButtonColumnEditor
	&&  table.isEditing())
		table.getCellEditor().stopCellEditing();

	isButtonColumnEditor = false;
}

public void mouseClicked(MouseEvent e) {}
public void mouseEntered(MouseEvent e) {}
public void mouseExited(MouseEvent e) {}
}
public class AdminMainPageView {

	public JFrame frame = new JFrame("eProduce-Admin");
	public JPanel middlePanel = new JPanel();
	public JPanel sidePanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private User currentUser;
	private String mpView = "";
	private eProduceDatabase db = new eProduceDatabase();

	/*
	 * The Main frame that holds everything
	 */
	public AdminMainPageView(User user) {
		currentUser = user;
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = (int) (((int) tk.getScreenSize().getWidth()) * .75);
		int ySize = (int) (((int) tk.getScreenSize().getHeight()) * .75);
		frame.setSize(xSize, ySize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());

		topPanel();
		sidePanel();
		middlePanel(mpView);

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(sidePanel, BorderLayout.WEST);
		frame.add(middlePanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	/*
	 * The top panel that holds the title and the log out button
	 */
	public void topPanel() {
		JLabel titleLabel = new JLabel("eProduce-Admin");
		JButton logout = new JButton();
		JPanel rightSide = new JPanel();
		JPanel leftSide = new JPanel();

		topPanel.setLayout(new BorderLayout());
		rightSide.setLayout(new FlowLayout(FlowLayout.RIGHT));
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));

		logout.setText("Log Out");
		titleLabel.setBounds(150, 10, 150, 150);
		titleLabel.setFont(titleLabel.getFont().deriveFont(30f));

		/*
		 * Log outs action button listener logs the user out
		 */
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentUser = null;
				frame.dispose();
				StartView start = new StartView(currentUser);
				start.frame.setVisible(true);

			}
		});

		leftSide.add(titleLabel);
		rightSide.add(logout);

		topPanel.add(leftSide, BorderLayout.WEST);
		topPanel.add(rightSide, BorderLayout.EAST);
	}

	/*
	 * The middle panel that holds the manage user button and system tickets
	 * button
	 */
	public void middlePanel(String a) {
		middlePanel.removeAll();
		UIDefaults ui = UIManager.getLookAndFeel().getDefaults();
		UIManager.put("RadioButton.focus", ui.getColor("control"));
		ArrayList<User> users = new ArrayList<User>();
		db.getAllUsers(users);
		
		if (a.equals("user")) {
			DefaultTableModel dm = new DefaultTableModel();
			Object[][] datavector = new Object[users.size()][8];
			for(int i = 0; i < datavector.length; i++)
			{
				datavector[i][0] = users.get(i).getEmail();
				datavector[i][1] = users.get(i).getPassword();
				datavector[i][2] = users.get(i).getFirstName();
				datavector[i][3] = users.get(i).getLastName();
				datavector[i][4] = users.get(i).getAdmin() == 0 ? new JRadioButton("", false) : new JRadioButton("", true);
				datavector[i][5] = users.get(i).getBlocked() == 0 ? new JRadioButton("", false) : new JRadioButton("", true);
				datavector[i][6] = users.get(i).getCurrentRating();
				datavector[i][7] = users.get(i).getNumReports();
			}
			JRadioButton[] listbtnB = new JRadioButton[4];
			JRadioButton[] listbtnA = new JRadioButton[4];
			for (int b = 0; b <= 3; b++) {
				listbtnB[b] = new JRadioButton();
				listbtnA[b] = new JRadioButton();
			}

			dm.setDataVector( datavector,
					new Object[] { "Username", "Password", "First Name", "Last Name", "Admin", "Blocked", "Current Rating", "# Reports"});

			listbtnB[0].setSelected(true);
			JTable table = new JTable(dm){
			    @Override
			    public boolean isCellEditable(int row, int column) {
			    	   switch (column) {
			           case 4:
			        	   return true;
			           case 5:
			               return true;
			           default:
			               return false;
			        }
			    }
			   };
			
			table.getColumn("Blocked").setCellRenderer(new RadioButtonRenderer());
			table.getColumn("Blocked").setCellEditor(new RadioButtonEditor(new JCheckBox()));
			table.getColumn("Admin").setCellRenderer(new RadioButtonRenderer());
			table.getColumn("Admin").setCellEditor(new RadioButtonEditor(new JCheckBox()));
			JScrollPane scroll = new JScrollPane(table);
			middlePanel.setLayout(new BorderLayout());
			middlePanel.add(scroll, BorderLayout.CENTER);
		} else if (a.equals("tickets")) {
			DefaultTableModel dm = new DefaultTableModel();
			Object[][] datavector = new Object[3][5];
			JButton[] listbtnB = new JButton[4];
			JButton[] listbtnA = new JButton[4];
			for (int b = 0; b <= 3; b++) {
				listbtnB[b] = new JButton("View");
				listbtnA[b] = new JButton("Cancel");
			}
			String[] columnNames = {"First Name", "Last Name", "email", "Description", "", ""};
			
			Object[][] data ={
							{ "Homer", "Simpson", "homer@gmail.com", "Good Work", listbtnB[0].getText(), listbtnA[0].getText() },
							{ "Madge", "Simpson", "madge@gmail.com", "Cool", listbtnB[1].getText(), listbtnA[1].getText() },
							{ "Bart", "Simpson", "bart@gmail.com", "Yes", listbtnB[2].getText(), listbtnA[2].getText() },
							{ "Lisa", "Simpson", "lisa@gmail.com", "Whoooo", listbtnB[3].getText(), listbtnA[3].getText() },
							};

			listbtnB[0].setSelected(true);
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			JTable table = new JTable( model );
			Action delete = new AbstractAction()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        JTable table = (JTable)e.getSource();
			        int modelRow = Integer.valueOf( e.getActionCommand() );
			        ((DefaultTableModel)table.getModel()).removeRow(modelRow);
			    }
			};
			Action respond = new AbstractAction()
			{
			    @SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e)
			    {
			    	JPanel lstPanel = new JPanel();
					JPanel top = new JPanel();
					JPanel middle = new JPanel();
					JPanel bottom = new JPanel();
					JLabel title = new JLabel();
					JLabel ticketNum = new JLabel();
					JLabel name = new JLabel();
					JLabel des = new JLabel();
					JLabel respond = new JLabel();
					JTextArea respondtxt = new JTextArea(5,10);
					Object[] options1 = { "Respond", "Cancel" };
					JTextField titletxt = new JTextField();
					JTextField ticketNumtxt = new JTextField();
					JTextField nametxt = new JTextField();
					JTextArea destxt = new JTextArea(5,10);
					JScrollPane desScroll = new JScrollPane(destxt);
					JScrollPane respondScroll = new JScrollPane(respondtxt);	
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					
					lstPanel.setLayout(new BorderLayout());
					top.setLayout(new GridLayout(0,1));
					middle.setLayout(new BorderLayout());
					bottom.setLayout(new BorderLayout());
					
					name.setText("Name:");
					ticketNum.setText("Ticket Number:");
					title.setText("Ticket Subject: ");
					des.setText("Description:");
					respond.setText("Respond:");
					
					respondtxt.setLineWrap(true);
					respondtxt.setBorder(border);
					destxt.setLineWrap(true);
					
					nametxt.setEditable(false);
					ticketNumtxt.setEditable(false);
					titletxt.setEditable(false);
					destxt.setEditable(false);
					
					top.add(title);
					top.add(titletxt);
					top.add(ticketNum);
					top.add(ticketNumtxt);
					top.add(name);
					top.add(nametxt);
					bottom.add(respond,BorderLayout.NORTH);
					bottom.add(respondScroll,BorderLayout.SOUTH);
					middle.add(des,BorderLayout.NORTH);
					middle.add(desScroll,BorderLayout.SOUTH);
					
					lstPanel.add(top,BorderLayout.NORTH);
					lstPanel.add(middle,BorderLayout.CENTER);
					lstPanel.add(bottom,BorderLayout.SOUTH);
					

					
					int result = JOptionPane.showOptionDialog(null, lstPanel, "Respond to Ticket", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,
							null, options1, null);
					if(result == JOptionPane.OK_OPTION)
					{
							
						
					}
					
				}
			
			    
			};
			
			ButtonColumn buttonColumn = new ButtonColumn(table, delete, 5);
			ButtonColumn buttonColumn2 = new ButtonColumn(table, respond, 4);
			buttonColumn.setMnemonic(KeyEvent.VK_D);
			
		
			JScrollPane scroll = new JScrollPane(table);
			middlePanel.setLayout(new BorderLayout());
			middlePanel.add(scroll, BorderLayout.CENTER);
		}

	}

	public void sidePanel() {
		JButton manageUser = new JButton();
		JButton systemTickets = new JButton();

		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		manageUser.setText("Manage Users");

		systemTickets.setText("System Tickets");
		manageUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mpView = "user";
				middlePanel(mpView);

				frame.revalidate();

			}
		});

		systemTickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mpView = "tickets";
				middlePanel(mpView);

				frame.revalidate();

			}
		});
		sidePanel.add(manageUser);
		sidePanel.add(Box.createRigidArea(new Dimension(5, 5)));
		sidePanel.add(systemTickets);
	}
}