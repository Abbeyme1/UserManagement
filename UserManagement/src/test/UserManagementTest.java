package test;

import static org.junit.Assert.*;
//import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;

import create.UserManagement;
import exceptions.CustomException;
import user.User;

public class UserManagementTest {

	
	UserManagement um;
	
	@Before
	public void setup() {
        um = new UserManagement();
    }
	
	@Test
	public void testCreateAutoUserInvalidName()
	{
		Exception e = assertThrows(CustomException.class, () -> {
			// NUll
			
			um.createUserAuto(" A    ","a@a.com","password123","hnsdf, sjf m sjf ","7410258963");
		});
		
		String expectedMessage = "Enter valid name";
		String actualMessage = e.getCause().getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
		
	}
	
	
	@Test
	public void testCreateUserAutoInvalidNameMaxLength()
	{
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("AaaaaAaaaaAaaaaAaaaaAaaaaAaaaaAaaaaAaaaaAaaaaAaaaaAaaaaAaaaa","a@a.com","password123","hnsdf, sjf m sjf ","7410258963");
		});
		
		String expectedMessage = "Enter valid name";
		String actualMessage = e.getCause().getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void testCreateUserAutoInvalidEmail()
	{
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("Abhay","a@a123","password123","hnsdf, sjf m sjf ","7410258963");
		});
		
		String expectedMessage = "Enter valid Email";
		String actualMessage = e.getCause().getMessage();
		
		assertTrue(actualMessage.equals(expectedMessage));
		
		e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("Abhay","a@a.comm","password123","hnsdf, sjf m sjf ","7410258963");
		});
		
		actualMessage = e.getCause().getMessage();
		
		assertTrue(actualMessage.equals(expectedMessage));
	}
	
	@Test
	public void testCreateUserAutoInvalidPassword()
	{
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("Abhay","b@a.com","1234","hnsdf, sjf m sjf ","7410258963");
		});
		
		String expectedMessage = "Enter valid Password";
		String actualMessage = e.getCause().getMessage();
		
		assertTrue(actualMessage.equals(expectedMessage));
	}
	
	@Test
	public void testCreateUserAutoInvalidPasswordWithSpaces()
	{
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("Abhay","b@a.com","               ","hnsdf, sjf m sjf ","7410258963");
		});
		
		String expectedMessage = "Enter valid Password";
		String actualMessage = e.getCause().getMessage();
		
		assertTrue(actualMessage.equals(expectedMessage));
	}
	
	@Test
	public void testCreateUserAutoInvalidAddress()
	{
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("Abhay","b@a.com","password123","hn        ","7410258963");
		});
		
		String expectedMessage = "Enter valid Address";
		String actualMessage = e.getCause().getMessage();
		
		assertTrue(actualMessage.equals(expectedMessage));
	}
	
	@Test
	public void testCreateUserAutoInvalidAddressMaxLength()
	{
		char[] arr = new char[600];
	    for(int i=0;i<600;i++) arr[i] = '-';
		
		String address = new String(arr);

		
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("Abhay","b@a.com","password123",address,"7410258963");
		});
		
		String expectedMessage = "Enter valid Address";
		String actualMessage = e.getCause().getMessage();
		
		assertTrue(actualMessage.equals(expectedMessage));
	}
	
	
	@Test
	public void testCreateUserAutoInvalidPhoneNumberWithoutPlus()
	{
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("Abhay","b@a.com","password123","skdfksdflksdfskf","917340865994");
		});
		
		String expectedMessage = "Enter valid Phone Number";
		String actualMessage = e.getCause().getMessage();

		assertTrue(actualMessage.equals(expectedMessage));
	}
	
	@Test
	public void testCreateUserAutoInvalidPhoneNumberWithPlus()
	{
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("Abhay","b@a.com","password123","skdfksdflksdfskf","+7340865994");
		});
		
		String expectedMessage = "Enter valid Phone Number";
		String actualMessage = e.getCause().getMessage();

		assertTrue(actualMessage.equals(expectedMessage));
	}
	
	@Test
	public void testCreateUserAutoAuto()
	{
		try {
			User u = um.createUserAuto("Abhay"," b@a.com  ","password123","skdfksdflksdfskf","+917340865994");
			
			assertEquals(u.getName(), "Abhay");
			assertEquals(u.getEmail(), "b@a.com");
			assertEquals(u.getAddress(), "skdfksdflksdfskf");
			assertEquals(u.getPhoneNumber(), "+917340865994");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateUser()
	{
		signUp();
	}
	
	@Test
	public void testCreateAdmin()
	{
		User u;
		try {
			String name = "Abhay";
			String email = "a@a.com";
			String password = "password123";
			String address = "jsdfskfjsfsfjskjfsjfj";
			String phoneNumber = "7418529630";
			
			String input = name+"\n"+email+"\n"+password+"\n"+address+"\n"+phoneNumber;
			InputStream in = new ByteArrayInputStream(input.getBytes());
			System.setIn(in);
			
			u = um.createAdmin();
			assertTrue(u.isAdmin());
			
			
		} catch (Exception e) {
			
			System.out.println("Message: " + e.getCause().getMessage());
		}
	
	}
	
	@Test
	public void testCreateUserMissingInputs()
	{
		assertThrows(CustomException.class, () -> {
			
			String name = "Abhay";
			String input = name+"\n";
			InputStream in = new ByteArrayInputStream(input.getBytes());
			System.setIn(in);
			
			um.createUser();
			
		});
		
	}

	
	@Test
	public void testCreateUserAutoToStringFunctionDoesNotContainPassword()
	{
		try {
			User u = um.createUserAuto("Abhay"," b@a.com  ","password123","skdfksdflksdfskf","+917340865994");
			assertFalse(u.toString().contains("password123"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testCreateUserAutoIsAdmin()
	{
		try {
			User u = um.createUserAuto("Abhay"," b@a.com  ","password123","skdfksdflksdfskf","+917340865994");
			
			assertFalse(u.isAdmin());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateAdminAutoIsAdmin()
	{
		try {
			User u = um.createUserAuto("Abhay"," b@a.com  ","password123","skdfksdflksdfskf","+917340865994");
			u.setAdmin(true);
			
			assertTrue(u.isAdmin());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void signUp()
	{
		
		User u;
		try {
			String name = "Abhay";
			String email = "a@a.com";
			String password = "password123";
			String address = "jsdfskfjsfsfjskjfsjfj";
			String phoneNumber = "7418529630";
			
			String input = name+"\n"+email+"\n"+password+"\n"+address+"\n"+phoneNumber;
			InputStream in = new ByteArrayInputStream(input.getBytes());
			System.setIn(in);
			
			um.signUp();
			u = um.currentUser;
			assertTrue(um.userDb.containsKey(u.getEmail()));
			assertEquals(u.getName(), name);
			assertEquals(u.getEmail(), email);
			assertEquals(u.getAddress(), address);
			assertEquals(u.getPhoneNumber(), phoneNumber);
			assertFalse(u.isAdmin());
			
			
		} catch (Exception e) {
			
			System.out.println("Message: " + e.getCause().getMessage());
		}
		
	}
	
	@Test
	public void signIn()
	{
		String email = "a@a.com";
		String password = "password123";
		signUp();
		
		try {
			um.signIn(email,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void signInWrongCredentials()
	{	
		signUp();
		
		String email = "aassdf@v.com";
		String password = "122fsfsfs1122";
		
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.signIn(email, password);
		});
		
		String expectedMessage = "Incorrect Email/Password";
		
		String actualMessage = e.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
		
		
		String emailAddress = "a@a.com";
		String pass = "password1243";
		
		e = assertThrows(CustomException.class, () -> {
			
			um.signIn(emailAddress, pass);
		});
		
		expectedMessage = "Incorrect Email/Password";
		
		actualMessage = e.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
		

		
	}

	@Test
	public void signOut()
	{
		um.signOut();
		
		assertTrue(um.currentUser == null);

	}
	
	@Test
	public void deleteUserSelfNoCurrentUser()
	{
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.deleteUser();
		});
		
		String expectedMessage = "Invalid Request";
		
		String actualMessage = e.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	
	@Test
	public void deleteUserSelf()
	{
		signUp();
		
		assertTrue(um.userDb.containsKey("a@a.com"));
		
		User u = um.userDb.get("a@a.com");
		
		
	}
	

}
