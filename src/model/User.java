package model;

import java.util.ArrayList;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private int blocked;
	private int admin;
	private double currentRating;
	private ArrayList<Double> allRatings;
	
	public User(String firstName, String lastName, String email, String password, int blocked, int admin){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this. password = password;
		this.blocked = blocked;
		this.admin = admin;
	}
	public User()
	{
		
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getBlocked() {
		return blocked;
	}
	public void setBlocked(int blocked) {
		this.blocked = blocked;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public double getCurrentRating() {
		return currentRating;
	}

	public void setCurrentRating(double currentRating) {
		this.currentRating = currentRating;
	}
	
	public void addRating(double value){
		allRatings.add(value);
		double temp = 0;
		for(int i = 0; i<allRatings.size(); i++){
			temp += allRatings.get(i);
		}
		temp = temp / allRatings.size();
		currentRating = temp;
	}
		
}
