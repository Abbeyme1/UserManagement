package test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import create.UserManagement;
import exceptions.CustomException;

public class UserManagementTest {
	
	@Test
	public void testCreateUserInvalidName()
	{
		UserManagement um = new UserManagement();
//		um.createUserAuto("Abhay","a@a.com","password","address","phoneNumber");
		
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto(" A    ","a@a.com","password123","hnsdf, sjf m sjf ","7410258963");
		});
		
		String expectedMessage = "Enter valid name";
		String actualMessage = e.getCause().getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void testCreateUserInvalidNameMaxLength()
	{
		UserManagement um = new UserManagement();
		
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("AaaaaAaaaaAaaaaAaaaaAaaaaAaaaaAaaaaAaaaaAaaaaAaaaaAaaaaAaaaa","a@a.com","password123","hnsdf, sjf m sjf ","7410258963");
		});
		
		String expectedMessage = "Enter valid name";
		String actualMessage = e.getCause().getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void testCreateUserInvalidEmail()
	{
		UserManagement um = new UserManagement();
		
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
	public void testCreateUserInvalidPassword()
	{
		UserManagement um = new UserManagement();
		
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("Abhay","b@a.com","1234","hnsdf, sjf m sjf ","7410258963");
		});
		
		String expectedMessage = "Enter valid Password";
		String actualMessage = e.getCause().getMessage();
		
		assertTrue(actualMessage.equals(expectedMessage));
	}
	
	@Test
	public void testCreateUserInvalidAddress()
	{
		UserManagement um = new UserManagement();
		
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("Abhay","b@a.com","password123","hn        ","7410258963");
		});
		
		String expectedMessage = "Enter valid Address";
		String actualMessage = e.getCause().getMessage();
		
		assertTrue(actualMessage.equals(expectedMessage));
	}
	
	@Test
	public void testCreateUserInvalidAddressMaxLength()
	{
		UserManagement um = new UserManagement();
		
		
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
	public void testCreateUserInvalidPhoneNumber()
	{
		UserManagement um = new UserManagement();
		
		Exception e = assertThrows(CustomException.class, () -> {
			
			um.createUserAuto("Abhay","b@a.com","password123","skdfksdflksdfskf","917340865994");
		});
		
		String expectedMessage = "Enter valid Phone Number";
		String actualMessage = e.getCause().getMessage();

		assertTrue(actualMessage.equals(expectedMessage));
	}
	
	
	
	
	
	
	
	

}
