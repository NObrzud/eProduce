package controller;

import javax.swing.JPasswordField;
<<<<<<< HEAD
import javax.swing.JTextField;

import model.User;
=======

>>>>>>> 9539d4bdea3eb198a21ceca74cdb12bb4c083a2c

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
	@SuppressWarnings("deprecation")
	public boolean validateLogin(JTextField user, JPasswordField pass)
	{
		return (db.validateLogin(user.getText(), pass.getText()));
	}
	@SuppressWarnings("deprecation")
	public  boolean createNewAccount(JTextField firstNameTF, JTextField lastNameTF, JTextField emailTF,
									   JPasswordField passwordPF, JPasswordField rePasswordPF) 
	{
		return db.createAccount(firstNameTF.getText(), lastNameTF.getText(), emailTF.getText(), passwordPF.getText());
	}
}
