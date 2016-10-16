package controller;

import model.User;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class eProduceController {
	private User userModel;
	private eProduceDatabase db;
	
	public eProduceController()
	{
		db = new eProduceDatabase();
	}
	public eProduceController(User model)
	{
		userModel = model;
	}
	/*
	 * Tests the entered username and password against the database. 
	 * NOTE: username and password are both case-sensitive, e.g. "Test" != "test"
	 */
	public boolean validateLogin(JTextField user, JPasswordField pass)
	{
		return (db.validateLogin(user.getText(), pass.getText()));
	}
	public  boolean createNewAccount(JTextField firstNameTF, JTextField lastNameTF, JTextField emailTF,
									   JPasswordField passwordPF, JPasswordField rePasswordPF) 
	{
		return db.createAccount(firstNameTF.getText(), lastNameTF.getText(), emailTF.getText(), passwordPF.getText());
	}
}
