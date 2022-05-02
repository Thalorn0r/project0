package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import model.AccountModel;
import model.CustomerModel;

public class CustomerDAO extends UserDAO implements CustomerInterface{

	private static final Logger logger = LogManager.getLogger(CustomerDAO.class);

	Scanner reader = new Scanner(System.in);
	String response;
	String yes="Y";
	String no="N";
	private Connection connect = ConnectionManager.getConnection();
	
	CustomerModel user = new CustomerModel(); 
	
	public boolean login(String username, String password) {
		try {
			PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM customer WHERE username = ?");
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) { //there should only be one user of that username
				if (password.equals(rs.getString("password"))){
					user.id = rs.getInt("id");
					user.username = rs.getString("username");
					user.password = rs.getString("password");
					user.firstname = rs.getString("firstName");
					user.lastname = rs.getString("lastName");
					user.email = rs.getString("email");
					logger.info("Customer logged in successfully");
					return true;
				}
				else {
					System.out.println("Password incorrect.");
					response = reader.next();
					logger.warn("Customer entered invalid password");
					
					return false;
				}
			}
			else {
				logger.warn("Customer entered invalid username");
				System.out.println("User not found. Would you like to create a new account? Y/N");
				response=reader.next();
				if (response.equals(yes)) {
						logger.info("Customer attempted new account creation");
						this.createCustomer(username, password);
				}
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Customer login failed.");
			return false;
		}
	}
	
	//clear all fields and return to start
	public boolean logout() {
		user.id = 0;
		user.username = null;
		user.password = null;
		user.firstname = null;
		user.lastname = null;
		user.email = null;
		System.out.println("Thank you for banking with us today!");
		logger.info("Customer logged out successfully");
		
		return false;
	}
	
	//make a new user after a failed login attempt
	public void createCustomer(String username, String password) {
		user.username = username;
		user.password = password;
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
			logger.info("Customer created new account.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Account creation failed.");
		}
		this.logout();
	}

	//create new application with null user2
	public void apply() {
		try {
			String query = "INSERT into application(ownerA, status) values (?, 'pending')";
			PreparedStatement pstmt = connect.prepareStatement(query);

			pstmt.setInt(1, user.id);
			pstmt.execute();
			System.out.println("Application submitted");
			logger.info("Application succeeded.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Application failed.");
		}		
	}

	//create new application with joint owner
	//warning: currently only allows for two 
	//alternative: account table doesn't list owners, owners list multiple accounts
	public void apply(String applicantB) {
		int jointID;
		if (applicantB==user.username) { //if joint owner is same as user, don't bother
			this.apply();
		} else {
			// check if joint owner exists
			try {
				PreparedStatement pstmtB = connect.prepareStatement("SELECT id FROM customer WHERE username =?");
				pstmtB.setString(1, applicantB);
				ResultSet rs = pstmtB.executeQuery();
				if (rs.next()) {
					jointID=rs.getInt("id");
					String query = "INSERT into application(ownerA, ownerB, status) values (?, ?, 'pending')";
					PreparedStatement pstmt = connect.prepareStatement(query);
	
					pstmt.setInt(1, user.id);
					pstmt.setInt(2, jointID);
					pstmt.execute();
					System.out.println("Application submitted");
					logger.info("Application succeeded.");
				} else {
					System.out.println("Joint user not found.");
					logger.warn("Joint user lookup failed.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Application failed.");
			}
		}
		
	}

	public void getAccounts() {
		try {
			String query ="SELECT id, balance FROM account WHERE ownerA = ? OR ownerB = ? ORDER BY id";
			PreparedStatement pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, user.id);
			pstmt.setInt(2, user.id);
			//ArrayList<AccountModel> accounts = new ArrayList<AccountModel>();	
						
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				float balance = rs.getFloat("balance");
				//int userA = rs.getInt("ownerA"); //not used
				//int userB = rs.getInt("ownerB"); //not used
			
				//accounts.add(new AccountModel(id, balance, userA, userB));
				System.out.println("Account Number:  "+id+" || Balance: $ "+balance);
			}
			logger.info("Customer accounts found.");
			/* why not just print while rs.next?
			for (AccountModel i: accounts) {
				System.out.println(i);
			}*/
			
		} catch (Exception e) {
			System.out.println("You have no bank accounts.");
			logger.error("Customer account lookup failed");
			e.printStackTrace();
		}
	}

	/* stretch goal
	public ArrayList<ApplicationModel> getAppStatus() {
		// TODO Auto-generated method stub
		
	}
	*/

	public boolean withdraw(float withdrawal, int account) {
		//check if customer owns account

		try {
			String query = "SELECT ownerA, ownerB from account WHERE id = ?";
			PreparedStatement pstmtB = connect.prepareStatement(query);
			pstmtB.setInt(1, account);
			ResultSet rs = pstmtB.executeQuery();
			if (rs.next()) {
				int userA = rs.getInt("ownerA");
				int userB = rs.getInt("ownerB");
				
				if (userA != user.id && userB != user.id) {
					System.out.println("That is not your account.");
					logger.warn("Customer attempted wrong account");
					return false;
				}
			} else {
				System.out.println("Account not found");
				logger.warn("Customer account not found");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Withdrawal failed.");	
			return false;
		}
		
		// validate withdrawal amount is positive
		if (withdrawal<=0) {
			System.out.println("Amount must be greater than 0.");
			logger.warn("Negative amount withdraw attempted");
			return false;		
		}
		
		else {
			try {
				String query = "SELECT balance from account WHERE id = ?";
				PreparedStatement pstmtB = connect.prepareStatement(query);
				pstmtB.setInt(1, account);
				ResultSet rs = pstmtB.executeQuery();
				float balance;
				if (rs.next())
					balance = rs.getFloat(1);
				else
					return false;
				
				// validate overdrawn
				if (withdrawal>balance) {
					System.out.println("Insufficient funds;");
					logger.warn("Customer account overdrawn");
					return false;
				}
				else {
					query = "UPDATE account SET balance = balance - ? WHERE id = ?";
					PreparedStatement pstmt = connect.prepareStatement(query);
								
					pstmt.setFloat(1, withdrawal);
					pstmt.setInt(2, account);
					pstmt.execute();
					
					logger.info("Withdrawal successful.");	
					this.getAccount(account);
										
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Withdrawal failed.");	

				return false;
			}
		}
		
	}

	public void deposit(float deposit, int account) {
		
		try {
			String query = "SELECT ownerA, ownerB from account WHERE id = ?";
			PreparedStatement pstmtB = connect.prepareStatement(query);
			pstmtB.setInt(1, account);
			ResultSet rs = pstmtB.executeQuery();
			if (rs.next()) {
				int userA = rs.getInt("ownerA");
				int userB = rs.getInt("ownerB");
				
				if (userA != user.id && userB != user.id) {
					System.out.println("That is not your account.");
					logger.warn("Customer attempted wrong account");
					return;
				}
				// else continues to deposit amount validation
			} else {
				System.out.println("Account not found");
				logger.warn("Customer account not found");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Deposit failed.");	
			return;
		}
		
		if (deposit<=0) {
			System.out.println("Amount must be greater than 0.");
			logger.warn("Negative amount attempted");
		}else {
			try {
				String query = "UPDATE account SET balance = balance + ? WHERE id = ?";
				PreparedStatement pstmt = connect.prepareStatement(query);
				
				
				pstmt.setFloat(1, deposit);
				pstmt.setInt(2, account);
				pstmt.execute();
				
				this.getAccount(account);
				logger.info("Deposit successful.");	
	
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Deposit failed.");	
				return;
			}
		} return;
	}

	public void transfer(float amount, int accountA, int accountB) {
		if (this.withdraw(amount, accountA)==true) {
			this.deposit(amount, accountB);
			logger.info("Transfer successful.");	
		}
		else {
			System.out.println("Transaction failed.");
			logger.error("Transfer failed.");	
		}
	}

	public void create(CustomerModel element) {
		try {
			PreparedStatement pstmt = connect.prepareStatement("INSERT INTO account (balance, ownerA) values (?, ?)");
			pstmt.setFloat(1, (float) 3.50);
			pstmt.setInt(2, element.id);
			pstmt.execute();
			logger.info("CREATE successful.");	

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CREATE failed.");	
		}
				
	}

	public CustomerModel get(String username) {
		
		try {
			PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM customer WHERE username = ?");
			
			pstmt.setString(1, username);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				CustomerModel guest = new CustomerModel();
				guest.id = rs.getInt("id");
				guest.username = rs.getString("username");
				guest.password = rs.getString("password");
				guest.firstname = rs.getString("firstname");
				guest.lastname = rs.getString("lastname");
				
				logger.info("GET successful.");	

				return guest;
			}
					
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("GET failed.");	
		}
		
		return null;
	}



	public void update(CustomerModel element) {
		// TODO Auto-generated method stub
		
	}

	

}
