package SistemFacturi;
import java.io.Serializable;


public class User implements Serializable {
	private String username;
	private String password;
	private Boolean isAdmin;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		isAdmin = false;
	}
	
	public void makeAdmin() {
		this.isAdmin = true;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean equals(Object obj) {
		User u = (User) obj;
		return username.equals(u.getUsername()) && password.equals(u.getPassword());
	}
	
	public String toString() {
		return this.username + (this.isAdmin.booleanValue() ? " (Admin)" : "");
	}
}
