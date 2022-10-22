package create;

import java.util.HashMap;
import java.util.Scanner;

import exceptions.CustomException;
import user.User;

public class UserManagement{
	
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_RED= "\u001B[31m";
	private static final String ANSI_RESET = "\u001B[0m";
	private static Scanner sc;
	
	private <T> T enter(String input)
	{
		System.out.print("Enter " + input + ": ");
		return (T) sc.nextLine();
	}
	
	private void checkEmail(String email) throws CustomException
	{
		if(userDb.containsKey(email)) throw new CustomException("Email already exists");
	}
	
	public User createUser() throws Exception
	{
		sc = new Scanner(System.in);
		String name="", address="", phoneNumber="", email, password = "";
		
		try {
			
			name = enter("Name");
			email = enter("Email");
			password = enter("Password");
			address = enter("Address");
			phoneNumber = enter("Phone Number");
			
			// checkEmail: temp. here
			checkEmail(email);
			
			User u = new User(name, email, password, address, phoneNumber);
			
			userDb.put(u.getEmail(), u);
			
			return u;
		}
		catch(Exception e)
		{
			CustomException c = new CustomException("Enter valid attributes");
			c.initCause(e);
			
			throw c;
		}
		finally {
			sc.close();
		}
	}
	
	public User createAdmin() throws Exception
	{
		User u = createUser();
		u.setAdmin(true);
		return u;
	}
	
	public User createUserAuto(String name, String email, String password, String address, String phoneNumber) throws Exception {
		
		try {
			
			// checkEmail: temp. here
			checkEmail(email);
						
			User u = new User(name,email,password, address, phoneNumber);
			userDb.put(u.getEmail(), u);
			
			return u;
		}
		catch(Exception e)
		{
			CustomException c = new CustomException("Enter valid attributes");
			c.initCause(e);
			
			throw c;
		}
		
	}
	
	public User createAdminAuto(String name, String email, String password, String address, String phoneNumber) throws Exception {
		
		User u = createUserAuto(name, email, password, address, phoneNumber);
		u.setAdmin(true);
		return u;
		
	}
	
	public void signUp()
	{
		try {
			currentUser = createUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void signIn(String email, String password) throws Exception
	{
		
		if(!userDb.containsKey(email)) throw new CustomException("Incorrect Email/Password");
		
		User user = userDb.get(email);
		
//		System.out.println(user.toString());
		
		if(!user.checkPassword(password)) throw new CustomException("Incorrect Email/Password");
		
		currentUser = user;
		
		System.out.println(ANSI_GREEN + "SignedIn successfully" + ANSI_RESET);
		
		
	}
	
	public void signOut()
	{
		currentUser = null;
		System.out.println(ANSI_RED + "SignedOut successfully" + ANSI_RESET);
	}

	public void deleteUser() throws Exception 
	{
		if(currentUser == null) throw new CustomException("Invalid Request");
		
		userDb.remove(currentUser.getEmail());
		
		currentUser = null;
		
	}
	
	public void deleteUser(String email)  throws Exception 
	{
		if(currentUser == null) throw new CustomException("Invalid Request");
		
		if(currentUser.getEmail() == email) deleteUser();
		else
		{
			if(!currentUser.isAdmin()) throw new CustomException("Unauthorized");
			
			if(!userDb.containsKey(email)) throw new CustomException("Invalid Request");
			User user = userDb.get(email);
			
			userDb.remove(user.getEmail());
			
		}
		
	}

	
	public void grantAdminAccess(String email) throws CustomException
	{
		if(currentUser == null || !currentUser.isAdmin()) throw new CustomException("Unauthorized");
		
		if(!userDb.containsKey(email)) throw new CustomException("Invalid Request");
		User user = userDb.get(email);
		
		user.setAdmin(true);
	}
	
	public void accessUsers() throws CustomException
	{
		if(currentUser == null || !currentUser.isAdmin()) throw new CustomException("Unauthorized");
		for(String key : userDb.keySet())
		{
			System.out.println(userDb.get(key).toString());
		}
		
	}
	
	private void update(User user, String newName, String newEmail,String newAddress, String newPhoneNumber) throws CustomException
	{
		
		user.setName(newName);
		
		user.setEmail(newEmail);
		
		user.setAddress(newAddress);
		
		user.setPhoneNumber(newPhoneNumber);
		
	}
	
	public void updateUserDetails(String userEmail, String newName, String newEmail,String newAddress, String newPhoneNumber) throws CustomException
	{
		if(currentUser.getEmail() == userEmail) update(currentUser, newName, newEmail, newAddress, newPhoneNumber);
		else
		{
			if(!currentUser.isAdmin()) throw new CustomException("Unauthorized");
			
			
			if(!userDb.containsKey(userEmail)) throw new CustomException("Invalid Request");
			User user = userDb.get(userEmail);
			
			update(user, newName, newEmail, newAddress, newPhoneNumber);
		}
	}
	
	private void updatePassword(User user, String oldPassword, String newPassword) throws CustomException
	{
		user.changePassword(oldPassword, newPassword);
		
	}
	
	public void updateUserPassword(String email, String oldPassword, String newPassword) throws CustomException
	{
		if(currentUser.getEmail() == email) updatePassword(currentUser, oldPassword, newPassword);
		else throw new CustomException("Unauthorized");
		
		System.out.println("Changed Password");
		
	}
	
	public User currentUser;
	public HashMap<String, User> userDb;
	
	
	public UserManagement() {
		userDb = new HashMap<>();
		currentUser = null;
	}

}
