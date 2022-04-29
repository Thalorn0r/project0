package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.AccountModel;
import model.ApplicationModel;
import model.CustomerModel;
import model.EmployeeModel;

public class EmployeeDAO extends UserDAO implements EmployeeInterface {

	private Connection connect = ConnectionManager.getConnection();
	EmployeeModel user = new EmployeeModel();

	
	public boolean login(String username, String password) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM employee WHERE username = '"+username+"'");
			
			if (rs.next()) { //there should only be one user of that username
				if (password.equals( rs.getString("password"))){
					user.id = rs.getInt("id");
					user.username = rs.getString("username");
					user.password = rs.getString("password");
					user.HireStatus = rs.getBoolean("employed");
					user.AdminStatus = rs.getBoolean("administrator");
					if (user.HireStatus==false) { //if the employee was fired, log out
						System.out.println("Congratulations, you have been promoted to customer.");
						return this.logout();
					} else return true;
				}
				else {
					System.out.println("Password incorrect.");
					return false;
				}
			}
			else {
				System.out.println("User not found.");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//clear all fields and return to start
	public boolean logout() {
		user.id = -1;
		user.username = null;
		user.password = null;
		user.HireStatus = false;
		user.AdminStatus = false;
		
		return false;
	}
	
	// get account table from db
	public ArrayList<AccountModel> getAllAccounts() {
		try {
			Statement statement = connect.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT * FROM account");
			ArrayList<AccountModel> accounts = new ArrayList<AccountModel>();
			
			//this loop runs so long as there is another row in rs
			while (rs.next()) {
				//datatype name = rs.getDatatype ("databaseColumnName");
				int id = rs.getInt("id");
				int balance = rs.getInt("balance");
				String ownerA = rs.getString("ownerA");
				String ownerB = rs.getString("ownerB");

				
				//this adds each new student to our student list(array)
				accounts.add(new AccountModel(id, balance, ownerA, ownerB));
			} return accounts;
		} catch (Exception e) {
			e.printStackTrace();
		} return null;		
	}

	public ArrayList<AccountModel> getAllAccounts(String customer) {
		try {
			Statement statement = connect.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT * FROM account WHERE username="+customer);
			ArrayList<AccountModel> accounts = new ArrayList<AccountModel>();
			
			//this loop runs so long as there is another row in rs
			while (rs.next()) {
				//datatype name = rs.getDatatype ("databaseColumnName");
				int id = rs.getInt("id");
				int balance = rs.getInt("balance");
				String ownerA = rs.getString("ownerA");
				String ownerB = rs.getString("ownerB");

				
				//this adds each new student to our student list(array)
				accounts.add(new AccountModel(id, balance, ownerA, ownerB));
			} return accounts;
		} catch (Exception e) {
			e.printStackTrace();
		} return null;		
	}

	public ArrayList<CustomerModel> getAllCustomers() {
		try {
			Statement statement = connect.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT * FROM account");
			ArrayList<CustomerModel> customers = new ArrayList<CustomerModel>();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");
				
				customers.add(new CustomerModel(id, username, password, firstName, lastName, email));
			} return customers;
		} catch (Exception e) {
			e.printStackTrace();
		} return null;		
	}

	public CustomerModel getCustomer(String customer) {
		return null;
		// TODO Auto-generated method stub
		
	}

	public ArrayList<ApplicationModel> getApplications() {
		return null;
		// TODO Auto-generated method stub
		
	}

	public void approve(int AppID) {
		// TODO Auto-generated method stub
		
	}

	public void deny(int AppID) {
		// TODO Auto-generated method stub
		
	}

}
