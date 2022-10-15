package user;

import exceptions.CustomException;

public class User {

	private final String userId;
	protected String name;
	protected String email;
	protected String password;
	protected String address;
	protected String phoneNumber;
	protected boolean isAdmin;
	
	private static int userIdCounter = 1;
	
	
	public User(String name, String email, String password ,String address, String phoneNumber) throws Exception{
		
		setName(name);
		setEmail(email);
		setPassword(password);
		setAddress(address);
		setPhoneNumber(phoneNumber);
		setAdmin(false);
		userId =  "u" + (userIdCounter++);
	}

	public String getUserId() {
		return userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) throws CustomException{
		
		name = name.strip();
		
		if(name.length() < 3 || name.length() > 50) throw new CustomException("Enter valid name");
		
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws CustomException {
		
		/// check if email already exists 
		
		String regex = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}";
		
		if(!email.matches(regex)) throw new CustomException("Enter valid Email");
		this.email = email;
	}


	protected String getPassword() {
		return password;
	}

	public boolean changePassword(String oldPassword, String newPassword) throws CustomException
	{
		if(!checkPassword(oldPassword)) throw new CustomException("Invalid Password");
		setPassword(newPassword);
		return true;
	}
	
	public boolean checkPassword(String password) {
		
		if(password.length() != getPassword().length()) return false;
		
		return getPassword().equals(password);
		
	}

	private void setPassword(String password) throws CustomException{
		
		if(password.length() < 7) throw new CustomException("Enter valid Password");
		this.password = password;
	}

	public String getAddress() {
		
		return address;
	}


	public void setAddress(String address) throws CustomException {
		
		address = address.strip();
		
		if(address.length() < 10 || address.length() > 500) throw new CustomException("Enter valid Address");
		
		this.address = address;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) throws CustomException {
		
		
		if(phoneNumber.charAt(0) == '+' && phoneNumber.length() != 13)  throw new CustomException("Enter valid Phone Number");
		else if(phoneNumber.charAt(0) != '+' && phoneNumber.length() != 10) throw new CustomException("Enter valid Phone Number");

		this.phoneNumber = phoneNumber;
	}


	public boolean isAdmin() {
		return isAdmin;
	}


	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
	public String toString() {
		
		String s = "UserId: "+ userId +", Name: " + getName()+ ", Email: "+ email +", Address: "+ getAddress() + ", PhoneNumber: "+ getPhoneNumber()+", isAdmin: "+isAdmin();
		return s;
	}

}




