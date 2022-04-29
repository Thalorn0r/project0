package model;

public class CustomerModel extends UserModel {
	public String firstname;
	public String lastname;
	public String email;
	
	public CustomerModel(int id, String username, String password, String firstName, String lastName, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstName;
		this.lastname = lastName;
		this.email = email;
	}

	public CustomerModel() {
	}
		
}
