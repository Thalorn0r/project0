package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import model.AccountModel;
import model.ApplicationModel;
import model.CustomerModel;

public class CustomerDAO extends UserDAO implements CustomerInterface{

	Scanner reader = new Scanner(System.in);
	String response;
	private Connection connect = ConnectionManager.getConnection();
	
	CustomerModel user = new CustomerModel(); 
	
	public boolean login(String username, String password) {
		try {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM customer WHERE username = '"+username+"'");
			
			if (rs.next()) { //there should only be one user of that username
				if (password.equals(rs.getString("password"))){
					user.id = rs.getInt("id");
					user.username = rs.getString("username");
					user.password = rs.getString("password");
					user.firstname = rs.getString("firstName");
					user.lastname = rs.getString("lastName");
					user.email = rs.getString("email");
					return true;
				}
				else {
					System.out.println("Password incorrect.");
					response = reader.next();
					
					return false;
				}
			}
			else {
				System.out.println("User not found. \n Would you like to create a new account? Y/N");
				if (response =="Y") {
						this.createCustomer();
				}
				
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
		user.firstname = null;
		user.lastname = null;
		user.email = null;
		
		return false;
	}
	
	//make a new user after a failed login attempt
	public void createCustomer() {
		System.out.println("First name:");
		user.firstname = reader.next();
		System.out.println("Last name:");
		user.lastname = reader.next();
		System.out.println("Email address:");
		user.email = reader.next();
		
		try {
			String query = "INSERT into customer (username, password, firstname, lastname, email) values (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = connect.prepareStatement(query);
			
			pstmt.setString(1, user.username);
			pstmt.setString(2, user.password);
			pstmt.setString(3, user.firstname);
			pstmt.setString(4, user.lastname);
			pstmt.setString(5, user.email);
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//create new application with null user2
	public void apply() {
		try {
			String query = "INSERT into application(ownerA, status) values (?, 'pending')";
			PreparedStatement pstmt = connect.prepareStatement(query);

			pstmt.setString(1, user.username);
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	//create new application with joint owner
	//warning: currently only allows for two 
	//alternative: account table doesn't list owners, owners list multiple accounts
	public void apply(String applicantB) {
		
		// TODO check if joint owner exists
		
		if (applicantB==user.username) { //if joint owner is same as user, don't bother
			this.apply();
		} else {
			try {
				String query = "INSERT into application(ownerA, ownerB, status) values (?, ?, 'pending')";
				PreparedStatement pstmt = connect.prepareStatement(query);

				pstmt.setString(1, user.username);
				pstmt.setString(2, applicantB);
				pstmt.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public ArrayList<AccountModel> getAccounts() {
		try {
			Statement statement = connect.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT * FROM account WHERE username="+user.username);
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

	/* stretch goal
	public ArrayList<ApplicationModel> getAppStatus() {
		// TODO Auto-generated method stub
		
	}
	*/

	public void withdraw(float withdrawal, int account) {
		// TODO Auto-generated method stub
		
	}

	public void deposit(float deposit, int account) {
		// TODO Auto-generated method stub
		
	}

	public void transfer(float amount, int accountA, int accountB) {
		// TODO Auto-generated method stub
		
	}

	

}
