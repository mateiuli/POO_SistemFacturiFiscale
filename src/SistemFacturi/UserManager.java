package SistemFacturi;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UserManager {
	private static UserManager instance = null;
	
	private String userFile = "users.dat";
	private ArrayList<User> users;
	
	public UserManager() {
		this.users = new ArrayList<>();
		
		this.readUsers();
//		users.add(new User("iulian.matesica", "1234"));
//		User u = new User("admin", "1234");
//		u.makeAdmin();
//		users.add(u);
//		this.writeUsers();
	}
	
	public void readUsers() {
		ObjectInputStream ois;
		try {
			if(!new File(userFile).exists())
				return;
			
			ois = new ObjectInputStream(new FileInputStream(userFile));
			users = (ArrayList<User>) ois.readObject();
			ois.close();
		} catch (IOException e) {
			System.err.println("user.dat does not exist.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void writeUsers() {
		try {
			ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(userFile, false));
			ois.writeObject(users);
			ois.close();
		} catch (IOException e) {
			System.err.println("user.dat could not be written.");
		}
	}
	
	public boolean userExists(User user) {
		for(User u : users) 
			if(u.equals(user))
				return true;
		
		return false;
	}
	
	public static UserManager getInstance() {
		if(instance == null)
			instance = new UserManager();
		
		return instance;
	}
}
