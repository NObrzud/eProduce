package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Listing;
import model.User;

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
		}	}
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
	
	
}
