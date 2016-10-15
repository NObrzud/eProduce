package model;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private int blocked;
	private int admin;
	
	public User(String firstName, String lastName, String email, String password, int blocked, int admin){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this. password = password;
		this.blocked = blocked;
		this.admin = admin;
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
	
}
