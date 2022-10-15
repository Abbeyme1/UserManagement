package create;

import java.util.HashMap;
import java.util.Scanner;

import exceptions.CustomException;
import user.Admin;
import user.User;

public class Create {
	
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RED= "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
	static Scanner sc;
	
	public static <T> T enter(String input)
	{
		System.out.print("Enter " + input + ": ");
		return (T) sc.nextLine();
	}
	
	
	public static void checkEmail(String email) throws CustomException
	{
		if(emailDb.containsKey(email)) throw new CustomException("Email already exists");
	}
	
	public static User createUser() throws Exception
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
			
			userDb.put(u.getUserId(), u);
			emailDb.put(u.getEmail(), u.getUserId());
			
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
	
	public static Admin createAdmin() throws Exception
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
						
			Admin u = new Admin(name, email, password, address, phoneNumber);
			userDb.put(u.getUserId(), u);
			emailDb.put(u.getEmail(), u.getUserId());
			
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
	
	public static User createUserAuto(String ...vals) throws Exception {
		
		try {
			
			// checkEmail: temp. here
			checkEmail(vals[1]);
						
			User u = new User(vals[0],vals[1],vals[2], vals[3], vals[4]);
			userDb.put(u.getUserId(), u);
			emailDb.put(u.getEmail(), u.getUserId());
			
			return u;
		}
		catch(Exception e)
		{
			CustomException c = new CustomException("Enter valid attributes");
			c.initCause(e);
			
			throw c;
		}
		
	}
	
	public static Admin createAdminAuto(String ...vals) throws Exception {
		
		try {
			// checkEmail: temp. here
			checkEmail(vals[1]);
			Admin u = new Admin(vals[0],vals[1],vals[2], vals[3], vals[4]);
			userDb.put(u.getUserId(), u);
			emailDb.put(u.getEmail(), u.getUserId());
			
			return u;
		}
		catch(Exception e)
		{
			CustomException c = new CustomException("Enter valid attributes");
			c.initCause(e);
			
			throw c;
		}
		
	}
	
	
	public static void signUp()
	{
		try {
			currentUser = createUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void signIn(String email, String password)
	{
		try {
			if(!emailDb.containsKey(email)) throw new CustomException("Incorrect Email/Password");
			
			User user = userDb.get(emailDb.get(email));
			
//			System.out.println(user.toString());
			
			if(!user.checkPassword(password)) throw new CustomException("Incorrect Email/Password");
			
			currentUser = user;
			
			System.out.println(ANSI_GREEN + "SignedIn successfully" + ANSI_RESET);
		}
		catch (Exception e) {
			
//			System.out.println(e.getCause());
			e.printStackTrace();
		}
		
		
	}
	
	public static void signOut()
	{
		currentUser = null;
		System.out.println(ANSI_RED + "SignedOut successfully" + ANSI_RESET);
	}

	
	public static void deleteUser() throws Exception 
	{
		if(currentUser == null) throw new CustomException("Invalid Request");
		
		userDb.remove(currentUser.getUserId());
		emailDb.remove(currentUser.getEmail());
		
		currentUser = null;
		
	}
	
	public static void deleteUser(String email)  throws Exception 
	{
		if(currentUser == null) throw new CustomException("Invalid Request");
		
		if(currentUser.getEmail() == email) deleteUser();
		else
		{
			if(!currentUser.isAdmin()) throw new CustomException("Unauthorized");
			
			String uid = emailDb.get(email);
			
			if(uid == null) throw new CustomException("Invalid Request");
			User user = userDb.get(uid);
			
			userDb.remove(user.getUserId());
			emailDb.remove(user.getEmail());
			
		}
		
	}
	
	
	public static void grantAdminAccess(String email) throws CustomException
	{
		if(currentUser == null || !currentUser.isAdmin()) throw new CustomException("Unauthorized");
		String uid = emailDb.get(email);
		
		if(uid == null) throw new CustomException("Invalid Request");
		User user = userDb.get(uid);
		
		user.setAdmin(true);
	}
	
	public static void accessUsers() throws CustomException
	{
		if(currentUser == null || !currentUser.isAdmin()) throw new CustomException("Unauthorized");
		for(String key : userDb.keySet())
		{
			System.out.println(userDb.get(key).toString());
		}
		
	}
	
	
	private static void update(User user, String newName, String newEmail,String newAddress, String newPhoneNumber) throws CustomException
	{
		
		user.setName(newName);
		
		user.setEmail(newEmail);
		
		user.setAddress(newAddress);
		
		user.setPhoneNumber(newPhoneNumber);
		
	}
	
	public static void updateUserDetails(String userEmail, String newName, String newEmail,String newAddress, String newPhoneNumber) throws CustomException
	{
		if(currentUser.getEmail() == userEmail) update(currentUser, newName, newEmail, newAddress, newPhoneNumber);
		else
		{
			if(!currentUser.isAdmin()) throw new CustomException("Unauthorized");
			
			String uid = emailDb.get(userEmail);
			
			if(uid == null) throw new CustomException("Invalid Request");
			User user = userDb.get(uid);
			
			update(user, newName, newEmail, newAddress, newPhoneNumber);
		}
	}
	
	private static void updatePassword(User user, String oldPassword, String newPassword) throws CustomException
	{
		user.changePassword(oldPassword, newPassword);
		
	}
	
	public static void updateUserPassword(String email, String oldPassword, String newPassword) throws CustomException
	{
		if(currentUser.getEmail() == email) updatePassword(currentUser, oldPassword, newPassword);
		else throw new CustomException("Unauthorized");
		
	}
	
	static User currentUser;
	static HashMap<String, User> userDb;
	static HashMap<String, String> emailDb;
	
	
	public static void main(String[] args) {
		
		userDb = new HashMap<>();
		emailDb = new HashMap<>();
		currentUser = null;

		try {
			
			User u = createUserAuto("abcd", "a@gmail.com", "1225888", "asdfk kasdlf k asldfk ksdf ", "7412589637");
			Admin a = createAdminAuto("abcd", "a@agmail.com", "1225888", "asdfk kasdlf k asldfk ksdf ", "7412589637");
			
			currentUser = a;
			
			
//			deleteUser();
//			deleteUser(u.getEmail());
//			deleteUser(u.getEmail());
			
//			updateUserDetails(currentUser.getEmail(),"Abhay","a@aa.com", "hno 401, 123 amritsar", "+917412589630");
			
//			updateUserDetails(a.getEmail(),"Abhay","a@aa.com", "hno 401, 123 amritsar", "+917412589630");
			
//			updateUserDetails(u.getEmail(),"Abhay","a@aa.com", "hno 401, 123 amritsar", "+917412589630");
			
			
			
//			System.out.println(u.toString());
			
//			updateUserPassword(currentUser.getEmail(),"1225888" , "abhay123");
			
			updateUserPassword(u.getEmail(),"1225888" , "abhay123");
			
			
			
			
			
			
			
			
			signIn("a@gmail.com","abhay123");
//			signOut();
			
//			accessUsers();
			
			
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
		

	}

}
