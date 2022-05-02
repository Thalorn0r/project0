package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.AccountModel;
//import model.ApplicationModel;
import model.CustomerModel;
import model.EmployeeModel;

public class EmployeeDAO extends UserDAO implements EmployeeInterface {

	private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);
	private Connection connect = ConnectionManager.getConnection();
	EmployeeModel user = new EmployeeModel();

	
	public boolean login(String username, String password) {
		try {
			PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM employee WHERE username = ?");
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) { //there should only be one user of that username
				if (password.equals( rs.getString("password"))){
					user.id = rs.getInt("id");
					user.username = rs.getString("username");
					user.password = rs.getString("password");
					user.HireStatus = rs.getBoolean("employed");
					user.AdminStatus = rs.getBoolean("administrator");
					logger.info("Employee logged in successfully");
					if (user.HireStatus==false) { //if the employee was fired, log out
						System.out.println("Congratulations, you have been promoted to customer.");
						logger.warn("Ex-employee logged in");
						return this.logout();
					} else return true;
				}
				else {
					System.out.println("Password incorrect.");
					logger.warn("Employee entered invalid password");
					return false;
				}
			}
			else {
				System.out.println("User not found.");
				logger.warn("Employee entered invalid username");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Login failed.");
			return false;
		}
	}
	
	//clear all fields and return to start
	public boolean logout() {
		user.id = 0;
		user.username = null;
		user.password = null;
		user.HireStatus = false;
		user.AdminStatus = false;
		logger.info("Employee logged out.");
		
		return false;
	}
	
	// used to enable Admin powers
	public boolean isAdmin() {
		return user.AdminStatus;
	}
	
	// get account table from db
	public void getAllAccounts() {
		try {
			Statement statement = connect.createStatement();
			
			//TODO return usernames for ownerA and ownerB
			ResultSet rs = statement.executeQuery("SELECT * FROM account");
			//ArrayList<AccountModel> accounts = new ArrayList<AccountModel>();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				float balance = rs.getFloat("balance");
				int userA = rs.getInt("ownerA");
				int userB = rs.getInt("ownerB");

				System.out.println("Account Number:  "+id+" Balance: $ "+balance+" Owner 1: "+userA+" Owner 2: "+userB);
				

				//accounts.add(new AccountModel(id, balance, ownerA, ownerB));
			}
			
			logger.info("Accounts found.");
			
			/*
			// print results
			for (AccountModel i: accounts) {
				System.out.println(i);
			} */
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Account lookup failed.");
		} return;		
	}

	// get all accounts for one customer by username
	public void getAllAccounts(String customer) {
		try {
			String query ="SELECT account.* FROM customer LEFT JOIN account ON account.ownerA=customer.id OR account.ownerB=customer.id WHERE customer.username= ?";
			PreparedStatement pstmt = connect.prepareStatement(query);
			pstmt.setString(1, customer);
			
			ResultSet rs = pstmt.executeQuery();
			//ArrayList<AccountModel> accounts = new ArrayList<AccountModel>();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				float balance = rs.getFloat("balance");
				int userA = rs.getInt("ownerA");
				int userB = rs.getInt("ownerB");

				System.out.println("Account Number:  "+id+" Balance: $ "+balance+" Owner 1: "+userA+" Owner 2: "+userB);

				//accounts.add(new AccountModel(id, balance, ownerA, ownerB));
			}
			
			logger.info("Accounts found.");
			/*
			// print results
			for (AccountModel i: accounts) {
				System.out.println(i);
			} */
			
			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Account lookup failed.");
		} return;		
	}
	

	// get all accounts for one customer by ID
		public void getAllAccounts(int customerID) {
			try {
				String query ="SELECT account.* FROM customer LEFT JOIN account ON account.ownerA=customer.id OR account.ownerB=customer.id WHERE customer.id= ?";
				PreparedStatement pstmt = connect.prepareStatement(query);
				pstmt.setInt(1, customerID);
				
				ResultSet rs = pstmt.executeQuery();
				//ArrayList<AccountModel> accounts = new ArrayList<AccountModel>();
				
				while (rs.next()) {
					int id = rs.getInt("id");
					float balance = rs.getFloat("balance");
					int userA = rs.getInt("ownerA");
					int userB = rs.getInt("ownerB");

					System.out.println("Account Number:  "+id+" Balance: $ "+balance+" Owner 1: "+userA+" Owner 2: "+userB);

					//accounts.add(new AccountModel(id, balance, ownerA, ownerB));
				}
				
				logger.info("Accounts found.");
				/*
				// print results
				for (AccountModel i: accounts) {
					System.out.println(i);
				} */
				
				return;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Account lookup failed.");
			} return;		
		}

	// view all customers
	public void getAllCustomers() {
		try {
			Statement statement = connect.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT * FROM customer");
			//ArrayList<CustomerModel> customers = new ArrayList<CustomerModel>();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				//String password = rs.getString("password");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");
				
				System.out.println("Customer #"+id+" Username: "+username+" Name: "+firstName+" "+lastName+" Email: "+email);
				//customers.add(new CustomerModel(id, username, password, firstName, lastName, email));
			}
			
			logger.info("Accounts found.");
			/*
			// print results
			for (CustomerModel i: customers) {
				System.out.println(i);
			} */
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Account lookup failed.");
		} return;		
	}

	// look up specific customer by name
	public void getCustomer(String customername) {
		try {
			String query ="SELECT * FROM customer WHERE username= ?";
			PreparedStatement pstmt = connect.prepareStatement(query);
			pstmt.setString(1, customername);
			
			ResultSet rs = pstmt.executeQuery();
			CustomerModel customer = new CustomerModel();
			
			if (rs.next()) {
				customer.id = rs.getInt("id");
				customer.username = rs.getString("username");
				customer.password = rs.getString("password");
				customer.firstname = rs.getString("firstName");
				customer.lastname = rs.getString("lastName");
				customer.email = rs.getString("email");	
			}
			
			logger.info("Account found.");
			System.out.println("Customer #"+customer.id+" Username: "+customer.username+" Name: "+customer.firstname+" "+customer.lastname+" Email: "+customer.email);

			
		} catch (Exception e) {
			System.out.println("Customer not found");
			logger.error("Account lookup failed.");
			e.printStackTrace();
		} return;
		
	}
	
	//look up customer by id
	public void getCustomer(int customerID) {
		try {
			String query ="SELECT * FROM customer WHERE id= ?";
			PreparedStatement pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, customerID);
			
			ResultSet rs = pstmt.executeQuery();
			CustomerModel customer = new CustomerModel();
			
			if (rs.next()) {
				customer.id = rs.getInt("id");
				customer.username = rs.getString("username");
				customer.password = rs.getString("password");
				customer.firstname = rs.getString("firstName");
				customer.lastname = rs.getString("lastName");
				customer.email = rs.getString("email");	
			}
			
			logger.info("Account found.");
			System.out.println("Customer #"+customer.id+" Username: "+customer.username+" Name: "+customer.firstname+" "+customer.lastname+" Email: "+customer.email);
			
		} catch (Exception e) {
			System.out.println("Customer not found");
			logger.error("Account lookup failed.");
			e.printStackTrace();
		} return;
		
	}

	// retrieve application table
	public void getApplications() {
		try {
			Statement statement = connect.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT * FROM application ORDER BY id");
			//ArrayList<ApplicationModel> applications = new ArrayList<ApplicationModel>();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				int userA = rs.getInt("ownerA");
				int userB = rs.getInt("ownerB");
				String status = rs.getString("status");
				

				System.out.println("#"+id+" Customer 1: "+userA+" Customer 2: "+userB+" Status: "+status);
				//applications.add(new ApplicationModel(id, userA, userB, status));
			}
			
			logger.info("Applications found.");

			/*
			// print results
			for (ApplicationModel i: applications) {
				System.out.println(i);
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Application lookup failed.");
		}
		
	}

	// change status of application from "pending" to "approved" and create new 
	public void approve(int AppID) {
		try {
			//change status
			String query = "UPDATE application SET status = 'approved' WHERE id =?";
			PreparedStatement pstmtA = connect.prepareStatement(query);
			
			pstmtA.setInt(1, AppID);
			pstmtA.execute();
			logger.info("Application approved");

			
			// get new account owners from application
			AccountModel newAccount = new AccountModel();
			query = "Select ownerA, ownerB From application WHERE id = ?";
			PreparedStatement pstmtB = connect.prepareStatement(query);
			pstmtB.setInt(1, AppID);
			ResultSet rs = pstmtB.executeQuery();
			if (rs.next()) {
				newAccount.userA = rs.getInt("ownerA");
				newAccount.userB = rs.getInt("ownerB");
				logger.info("Customers identified.");
			}
			
			// create new account with owners from application, balance=0
			query = "INSERT INTO account (balance, ownerA, ownerB) VALUES (0, ?, ?)";
			PreparedStatement pstmtC = connect.prepareStatement(query);
			pstmtC.setInt(1, newAccount.userA);
			pstmtC.setInt(2, newAccount.userB);
			pstmtC.execute();

			logger.info("New account created.");
			System.out.println("Application #"+AppID+" approved.");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Approval failed.");
		}
		
		
	}

	//change status of application to "deny"
	public void deny(int AppID) {
		try {
			String query = "UPDATE application SET status = 'denied' WHERE id =?";
			PreparedStatement pstmt = connect.prepareStatement(query);
			
			pstmt.setInt(1, AppID);
			pstmt.execute();

			System.out.println("Application #"+AppID+" denied.");
			logger.info("Application denied.");


		} catch (Exception e) {
			logger.error("Denial failed.");
			e.printStackTrace();
		}
	}

}
