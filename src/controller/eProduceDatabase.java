package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import model.Listing;
import model.Meetup;
import model.Ticket;
import model.User;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class eProduceDatabase {
	String myDB;
	Connection DBConn;
	public eProduceDatabase()
	{
		try
		{
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			myDB = "jdbc:derby://gfish.it.ilstu.edu:1527/IT326eProduce";
		}
		catch(ClassNotFoundException e)
		{
			System.err.println(e.getMessage());
			System.exit(0);
		}
	}
	/*
	 * Looks for the user in db, compares password, if it matches returns true. if not returns false.
	 */
	public boolean validateLogin(User model, String user, String pass)
	{
		try {
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.users where username = \'" + user + "\'"; // single quotes protect against SQL injection
			System.out.println(selectString);
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
			System.exit(-1);
			return false;
		}
	}
	private void setModelValuesAfterLogin(User user, ResultSet returnValues) 
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
			System.exit(-1);
			
		}
		
		
	}
	public boolean createAccount(String first, String last, String email, String pass) 
	{
		try 
		{
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			updateString = "insert into eProduce.users (firstname, lastname, username, password) values (\'"+first+"\',\'"+last+"\',\'"+email+"\',"+"\'"+pass+"\')"; // single quotes protect against SQL injection
			System.out.println(updateString);
			returnVal = stmt.executeUpdate(updateString);
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
			//System.exit(-1);
			return false;
		}
	}
	public boolean updateAccount(String first, String last, String email, String pass, String rePass) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			updateString = "update eProduce.users set firstname=\'"+first+"\',lastname=\'"+last+"\',username=\'"+email+"\',password=\'"+pass+"\' where username=\'"+email+"\'";
			System.out.println(updateString);
			returnVal = stmt.executeUpdate(updateString);
			return true;
			/*if(returnVal == 0) // 1 new account was created
			{
				return true;
			}
			else
				return false;*/
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			//System.exit(-1);
			return false;
		}
	}
	public boolean createListing(String email, String title, String text, String tags) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String insertString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			insertString = "insert into eproduce.listings (owner, title, content, tags ) values (\'"+email+"\',\'"+title+"\',\'"+text+"\',\'"+tags+", "+email+"\')";
			System.out.println(insertString);
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
			//System.exit(-1);
			return false;
		}	
	}
	public void getMyListings(String email, ArrayList<Listing> myListings) {
		try {
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.listings where owner = \'" + email + "\'"; // single quotes protect against SQL injection
			System.out.println(selectString);
			returnValues = stmt.executeQuery(selectString);
			while(returnValues.next())
			{
				myListings.add(new Listing(returnValues.getString("owner"), returnValues.getString("title"), returnValues.getString("content"), returnValues.getString("tags"), returnValues.getInt("listingnum")));
			}
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
	}
	public void getAllUsers(ArrayList<User> myUsers) {
		try {
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.users"; // single quotes protect against SQL injection
			System.out.println(selectString);
			returnValues = stmt.executeQuery(selectString);
			while(returnValues.next())
			{
				myUsers.add(new User(returnValues.getString("firstname"), returnValues.getString("lastname"), returnValues.getString("username"), 
						returnValues.getString("password"), returnValues.getInt("isblocked"), returnValues.getInt("isadmin"), returnValues.getInt("numReports")));
			}
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
	}
	public void getAllListings(ArrayList<Listing> myListings) {
		try {
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.listings"; // single quotes protect against SQL injection
			System.out.println(selectString);
			returnValues = stmt.executeQuery(selectString);
			while(returnValues.next())
			{
				myListings.add(new Listing(returnValues.getString("owner"), returnValues.getString("title"), returnValues.getString("content"), returnValues.getString("tags"), returnValues.getInt("listingnum")));
			}
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		
	}
	public boolean updateListing(String title, String des, int listingNum, String tags) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			updateString = "update eProduce.listings set title=\'"+title+"\',content=\'"+des+"\',tags=\'"+tags+"\' where listingnum = "+listingNum;
			System.out.println(updateString);
			returnVal = stmt.executeUpdate(updateString);
			//return true;
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
			//System.exit(-1);
			return false;
		}
	}
	public boolean createMeetup(String owner, String participants, String location, UtilDateModel model, SpinnerDateModel model2) {
		//model = correct year, model2 = correct time
		try 
		{
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String insertString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
			String time = sdf.format(model2.getValue());
			sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(model.getValue());
			
			
			insertString = "insert into eproduce.meetups (owner, participants, meettime, meetdate, meetlocation) values (\'"+owner+"\',\'"+participants+"\',\'"+time+"\',\'"+date+"\',\'"+location+"\')";
			System.out.println(insertString);
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
			//System.exit(-1);
			return false;
		}	
		
	}
	public void getMyMeetups(String email, ArrayList<Meetup> myMeetups) {
		try {
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.meetups where owner = \'" + email + "\'"; // single quotes protect against SQL injection
			System.out.println(selectString);
			returnValues = stmt.executeQuery(selectString);
			while(returnValues.next())
			{
				
				myMeetups.add(new Meetup(returnValues.getDate("meetDate"), returnValues.getTime("meetTime"), returnValues.getString("owner"), returnValues.getString("participants"), returnValues.getString("meetLocation"), returnValues.getInt("meetupNum")));
			}
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
	}
	public void deleteListing(int listingNum) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "delete from eProduce.listings where listingNum = " + listingNum;
			System.out.println(updateString);
			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			//System.exit(-1);
		}
		
	}
	public User getOwnerDetails(String username) {
		try {
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String selectString;
			ResultSet returnValues;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			
			selectString = "select * from eproduce.users where username = \'" + username + "\'"; // single quotes protect against SQL injection
			System.out.println(selectString);
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
	public void increaseUserRating(String username) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "update eProduce.users set currentrating=currentrating + 1 where username = \'" +username+ "\'";
			System.out.println(updateString);
			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
	}
	public void decreaseUserRating(String username) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "update eProduce.users set currentrating=currentrating - 1 where username = \'" +username+ "\'";
			System.out.println(updateString);
			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
		
	}
	public void setUserBanStatus(String username, int isBanned) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "update eProduce.users set isBlocked = "+isBanned+" where username = \'" +username+ "\'";
			System.out.println(updateString);
			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
				
	}
	public void setUserAdminStatus(String username, int isAdmin) {
		try 
		{
			DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
			String updateString;
			int returnVal;
			Statement stmt = DBConn.createStatement();
			stmt = DBConn.createStatement();
			updateString = "update eProduce.users set isadmin = "+isAdmin + " where username = \'" +username+ "\'";
			System.out.println(updateString);
			returnVal = stmt.executeUpdate(updateString);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
				
	}
	public boolean createTicket(String email, String text) {
		// TODO Auto-generated method stub
		return false;
	}
	public void getMyTickets(String email, ArrayList<Ticket> myTickets) {
		// TODO Auto-generated method stub
		
	}
	public void deleteTicket(String ticketNum) {
		// TODO Auto-generated method stub
		
	}
	public boolean updateTicket(String email, String ticketNum) {
		// TODO Auto-generated method stub
		return false;
	}
}
