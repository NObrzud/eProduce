package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	public boolean validateLogin(String user, String pass)
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
	
	
}
