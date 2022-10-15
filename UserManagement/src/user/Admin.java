package user;

public class Admin extends User{
	
	public Admin(String name, String email, String password ,String address, String phoneNumber) throws Exception{
		
		super(name, email, password, address, phoneNumber);
		isAdmin = true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
