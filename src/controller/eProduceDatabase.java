package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import model.Feedback;
import model.Listing;
import model.Meetup;
import model.Ticket;
import model.User;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
 //this class handles all database calls
public class eProduceDatabase {
	static String myDB;
	static Connection DBConn;
	static String dbLogin = "itkstu";
	static String dbPass = "student"; 
	public  eProduceDatabase()
	{
		try
		{
			dbLogin = "app"; //test environment, comment out when production
			dbPass = "app"; //test environment, comment out when production
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			myDB = "jdbc:derby://localhost:1527/sample"; //test environment
			//myDB = "jdbc:derby://gfish.it.ilstu.edu:1527/IT326eProduce"; //production
		}
		catch(ClassNotFoundException e)
		{
			System.err.println(e.getMessage());
		}
	}
	/*
	 * Looks for the user in db, compares password, if it matches returns true. if not returns false.
	 */
	public static boolean validateLogin(User model, String user, String pass)
	{
		try {
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.users where username = \'" + user + "\'"; // single quotes protect against SQL injection
			returnValues = stmt.executeQuery(selectString);
			while(returnValues.next())
			{
				if(returnValues.getString("password").equals(pass))// && returnValues.getInt("isbanned") == 0) //password matches, and unbanned. let them in
				{	
					setModelValuesAfterLogin(model, returnValues);
					DBConn.close();
					return true;
				}
				else //password failed or banned, don't let them in.
				{
					DBConn.close();
					return false;
				}
			}
			return false;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			return false;
		}
	}
	//sets all the user variables. simple private helper method
	private static void setModelValuesAfterLogin(User user, ResultSet returnValues) 
	{
		try {
			user.setAdmin(returnValues.getInt("isAdmin"));
			user.setBlocked(returnValues.getInt("isBlocked"));
			user.setCurrentRating(returnValues.getInt("currentRating"));
			user.setEmail(returnValues.getString("username"));
			user.setPassword(returnValues.getString("password"));
			user.setFirstName(returnValues.getString("firstName"));
			user.setLastName(returnValues.getString("lastName"));
		} catch (SQLException e) {
			System.err.println(e.getMessage());			
		}
		
		
	}
	// creates an account using the given firstName, lastName, email, and password
	public static boolean createAccount(String first, String last, String email, String pass) 
	{
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from eproduce.users where username = \'" + email + "'");
			while(rs.next())
			{//if the account already exists. don't make the account
				return false;
			}
			Statement stmt2 = DBConn.createStatement();
			
			
			updateString = "insert into eProduce.users (firstname, lastname, username, password) values (\'"+first+"\',\'"+last+"\',\'"+email+"\',"+"\'"+pass+"\')"; // single quotes protect against SQL injection
			returnVal = stmt2.executeUpdate(updateString);
			if(returnVal == 1) // 1 new account was created
			{
				return true;
			}
			else
				return false;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			return false;
		}
	}
	public static boolean updateAccount(String first, String last, String email, String pass, String rePass) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			updateString = "update eProduce.users set firstname=\'"+first+"\',lastname=\'"+last+"\',username=\'"+email+"\',password=\'"+pass+"\' where username=\'"+email+"\'";
			returnVal = stmt.executeUpdate(updateString);
			return true;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			return false;
		}
	}
	//creates a listing by @param=email, titled title with the content text and tags tags
	public static boolean createListing(String email, String title, String text, String tags) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String insertString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			insertString = "insert into eproduce.listings (owner, title, content, tags ) values (\'"+email+"\',\'"+title+"\',\'"+text+"\',\'"+tags+", "+email+"\')";
			returnVal = stmt.executeUpdate(insertString);
			if(returnVal == 1) // 1 new account was created
			{
				return true;
			}
			else
				return false;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			return false;
		}	
	}
	//gets all the listings created by the email given in parameter, stores in the myListings ArrayList
	public static void getMyListings(String email, ArrayList<Listing> myListings) {
		try {
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.listings where owner = \'" + email + "\'"; // single quotes protect against SQL injection
			
			returnValues = stmt.executeQuery(selectString);
			while(returnValues.next())
			{
				myListings.add(new Listing(returnValues.getString("owner"), returnValues.getString("title"), returnValues.getString("content"), returnValues.getString("tags"), Integer.toString(returnValues.getInt("listingnum"))));
			}
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
		
	}
	public static void getAllUsers(ArrayList<User> myUsers) {
		try {
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.users"; // single quotes protect against SQL injection
			returnValues = stmt.executeQuery(selectString);
			while(returnValues.next())
			{
				myUsers.add(new User(returnValues.getString("firstname"), returnValues.getString("lastname"), returnValues.getString("username"), 
						returnValues.getString("password"), returnValues.getInt("isblocked"), returnValues.getInt("isadmin"),returnValues.getInt("currentrating"), returnValues.getInt("numReports")));
			}
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
		
	}
	public static void getAllListings(ArrayList<Listing> myListings) {
		try {
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.listings"; // single quotes protect against SQL injection
			
			returnValues = stmt.executeQuery(selectString);
			while(returnValues.next())
			{
				myListings.add(new Listing(returnValues.getString("owner"), returnValues.getString("title"), returnValues.getString("content"), returnValues.getString("tags"), Integer.toString(returnValues.getInt("listingnum"))));
			}
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
		
		
	}
	//updates the listing figured out by listingNum to have the new title, description, and tags passed in
	public static boolean updateListing(String title, String des, String listingNum, String tags) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			updateString = "update eProduce.listings set title=\'"+title+"\',content=\'"+des+"\',tags=\'"+tags+"\' where listingnum = "+listingNum;
			returnVal = stmt.executeUpdate(updateString);
			//return true;
			if(returnVal == 0) // listing was updated
			{
				return true;
			}
			else
				return false;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			return false;
		}
	}
	public static boolean createMeetup(String owner, String participants, String location, UtilDateModel model, SpinnerDateModel model2) {
		//model = correct year, model2 = correct time
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String insertString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			//converts Java's date/time to MySQL's date/time formats
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
			String time = sdf.format(model2.getValue());
			sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(model.getValue());
			
			
			insertString = "insert into eproduce.meetups (owner, participants, meettime, meetdate, meetlocation) values (\'"+owner+"\',\'"+participants+"\',\'"+time+"\',\'"+date+"\',\'"+location+"\')";
			returnVal = stmt.executeUpdate(insertString);
			if(returnVal == 1) // 1 new meetup was created
			{
				return true;
			}
			else
				return false;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			return false;
		}	
		
	}
	//get all meetups where the owner is the parameter "email" and puts them in the ArrayList myMeetups
	public static void getMyMeetups(String email, ArrayList<Meetup> myMeetups) {
		try {
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.meetups where owner = \'" + email + "\'"; // single quotes protect against SQL injection
			
			returnValues = stmt.executeQuery(selectString);
			while(returnValues.next())
			{
				ArrayList<User> participants = new ArrayList<User>();
				StringTokenizer st = new StringTokenizer(returnValues.getString("participants"), ", ");
				String token = "";
				while(st.hasMoreTokens()){
					token = st.nextToken();
					participants.add(getOwnerDetails(token));
				}
				myMeetups.add(new Meetup(returnValues.getDate("meetDate"), returnValues.getTime("meetTime"), returnValues.getString("owner"), participants, returnValues.getString("meetLocation"), Integer.toString(returnValues.getInt("meetupNum"))));
			}
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
	}
	//deletes the listing with listing number as the parameter
	public static void deleteListing(String listingNum) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "delete from eProduce.listings where listingNum = " + listingNum;
			
			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
		
	}
	//gives all the owner's details (except password) with the username given by the parameter
	public static User getOwnerDetails(String username) {
		try {
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.users where username = \'" + username + "\'"; // single quotes protect against SQL injection
			returnValues = stmt.executeQuery(selectString);
			
			User result = new User();
			while(returnValues.next())
			{
				result.setFirstName(returnValues.getString("firstname"));
				result.setLastName(returnValues.getString("lastname"));
				result.setEmail(returnValues.getString("username"));
				result.setAdmin(returnValues.getInt("isadmin"));
				result.setBlocked(returnValues.getInt("isblocked"));
				result.setCurrentRating(returnValues.getInt("currentrating"));
				result.setNumReports(returnValues.getInt("numReports"));
			}
			return result;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			return null;
		}
	}
	public static void increaseUserRating(String username) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "update eProduce.users set currentrating=currentrating + 1 where username = \'" +username+ "\'";
			
			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
	}
	public static void decreaseUserRating(String username) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "update eProduce.users set currentrating=currentrating - 1 where username = \'" +username+ "\'";
			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
		
	}
	public static void setUserBanStatus(String username, int isBanned) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "update eProduce.users set isBlocked = "+isBanned+" where username = \'" +username+ "\'";
			
			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
				
	}
	public static void setUserAdminStatus(String username, int isAdmin) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "update eProduce.users set isadmin = "+isAdmin + " where username = \'" +username+ "\'";

			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
				
	}
	public static boolean createTicket(String email, String description) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String insertString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			insertString = "insert into eproduce.tickets (owner, description) values (\'"+email+"\',\'"+description+"\')";
			returnVal = stmt.executeUpdate(insertString);
			if(returnVal == 1) // 1 new account was created
			{
				return true;
			}
			else
				return false;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			return false;
		}	
	}
	
	//gets  all the tickets made by the account with the email given by the parameter, places into the ArrayList given by the parameter
	public static void getMyTickets(String email, ArrayList<Ticket> myTickets) {
		try {
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.tickets where owner = \'" + email + "\'"; // single quotes protect against SQL injection
			returnValues = stmt.executeQuery(selectString);
			while(returnValues.next())
			{
				myTickets.add(new Ticket(returnValues.getString("owner"), returnValues.getString("description"), returnValues.getString("response"), Integer.toString(returnValues.getInt("ticketnum"))));
			}
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
		
	}
	public static void deleteTicket(String ticketNum) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "delete from eProduce.tickets where ticketNum = " + ticketNum;
			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}		
	}
	//updates the ticket with ticket number given by the 3rd parameter with the description and response given by the 1st and second parameters, respectively.
	public static boolean updateTicket(String description, String response, String ticketNum) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			updateString = "update eProduce.tickets set description=\'"+description+"\', response=\'"+response+"\' where ticketnum = "+ticketNum;
			returnVal = stmt.executeUpdate(updateString);
			if(returnVal == 0) // 1 new account was created
			{
				return true;
			}
			else
				return false;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			return false;
		}
	}
	public static void reportUser(String username) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "update eProduce.users set numreports= numreports + 1 where username = \'" +username+ "\'";
			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
		
	}
	//get all the tickets in the db and add to the array given here
	public static void getAllTickets(ArrayList<Ticket> tickets) {
		try {
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.tickets";
			
			returnValues = stmt.executeQuery(selectString);
			while(returnValues.next())
			{
				tickets.add(new Ticket(returnValues.getString("owner"), returnValues.getString("description"), returnValues.getString("response"), Integer.toString(returnValues.getInt("ticketnum"))));
			}
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	//edits the meetup using the updated models passed in through the parameters
	public static boolean editMeetup(String meetupNum, String participants, String location, UtilDateModel model, SpinnerDateModel model2) {
		//model = correct year, model2 = correct time
				try 
				{
					DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
					String insertString;
					int returnVal;
					Statement stmt = DBConn.createStatement();
					stmt = DBConn.createStatement();
					
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
					String time = sdf.format(model2.getValue());
					sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(model.getValue());
					
					
					insertString = "update eproduce.meetups set participants = \'"+participants+"\', meettime = \'"+time+"\', meetdate = \'"+date+"\', meetlocation = \'"+location+"\'"
								   + "where meetupNum = " + meetupNum;
					returnVal = stmt.executeUpdate(insertString);
					if(returnVal == 1) // 1 new meetup was created
					{
						return true;
					}
					else
						return false;
				}
				catch(SQLException e)
				{
					System.err.println(e.getMessage());
					return false;
				}	
	}
	public static void deleteMeetup(String meetupNum) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "delete from eProduce.meetups where meetupNum = " + meetupNum;
			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}				
	}
	public static ArrayList<Feedback> getFeedbackForListing(String listingNum) {
		try {
			ArrayList<Feedback> feedback = new ArrayList<Feedback>();
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.listingfeedback where listingNum = " + listingNum;			returnValues = stmt.executeQuery(selectString);
			while(returnValues.next())
			{
				feedback.add(new Feedback(getOwnerDetails(returnValues.getString("owner")), returnValues.getString("content"), Integer.toString(returnValues.getInt("feedbackNum"))));
			}
			return feedback;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			System.exit(-1);
			return null;
		}
	}
	public static boolean editFeedback(String feedbackNum, String content ) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String insertString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			insertString = "update eproduce.listingfeedback set content = \'"+ content+"\' where feedbackNum = " + feedbackNum;
			
			returnVal = stmt.executeUpdate(insertString);
			if(returnVal == 1) // 1 new meetup was created
			{
				return true;
			}
			else
				return false;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			return false;
		}	
	}
	public static boolean createFeedback(String email, String content, String listingNum) {
		try 
		{
			if(content.equals(""))
				return false;
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String insertString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			insertString = "insert into eproduce.listingfeedback (owner, content, listingNum) values (\'"+email+"\',\'"+content+"\', " + listingNum + ")";
			returnVal = stmt.executeUpdate(insertString);
			if(returnVal == 1) // 1 new account was created
			{
				return true;
			}
			else
				return false;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			return false;
		}	
	}
	public static boolean deleteFeedback(String feedbackNum) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, dbLogin, dbPass);
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "delete from eProduce.listingfeedback where feedbackNum = " + feedbackNum;
			returnVal = stmt.executeUpdate(updateString);
			if(returnVal == 1)
				return true;
			else 
				return false;
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}				
		return false;
	}
}
