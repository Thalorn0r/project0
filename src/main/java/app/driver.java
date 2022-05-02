package app;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.Controller;
import dao.AdminDAO;
import dao.CustomerDAO;
import io.javalin.Javalin;

public class driver {
	
	private static final Logger logger = LogManager.getLogger(driver.class);

	public static void main(String[] args) {
		
		Javalin app = Javalin.create().start(7070);
		Controller userController = new Controller(app);
		
		boolean online = false;
		boolean worker = false;
		String username;
		String password;
		
		Scanner reader = new Scanner(System.in);
		String response;
		int responseNum;
		final String yes = "Y";
		final String no = "N";
		
		
		
		
		while (online==false) {
			

			
			//check if the user is an employee
			System.out.println("Are you an employee?");
			System.out.println("Y/N");
			response= reader.next();
			while (response.equals(yes)==false && response.equals(no)==false){
				/*System.out.println(response);
				System.out.println(yes);
				System.out.println(no); */ //logic gates gave me issues
				System.out.println("Try again.");
				response = reader.next();
			}
			if (response.equals(no)) {
				worker = false;
			}
			else if (response.equals(yes)) {
				worker = true;
			} //"Are you an employee? \n Y/N"
			
			//enter a username and password
			System.out.println("Username:");
			username = reader.next();
			System.out.println("Password:");
			password = reader.next();
			logger.info("Login attempted.");
			
			// if they aren't an employee, login user as a customer
			// if they are an employee, log them in as as an employee
			if (worker==false) {
				CustomerDAO Customer = new CustomerDAO();
				online = Customer.login(username, password);
				
				//customer menu
				while (online == true && worker==false) {
					System.out.println("1. Logout");
					System.out.println("2. Account Status");
					System.out.println("3. Apply for new account");
					System.out.println("4. Apply for joint account");
					System.out.println("5. Withdraw");
					System.out.println("6. Deposit");
					System.out.println("7. Transfer");
					responseNum = reader.nextInt();
					if (responseNum == 1) {
						logger.info("Logout attempted.");
						online = Customer.logout();
					}
					else if (responseNum == 2) {
						logger.info("Customer looking up accounts");
						Customer.getAccounts();
					}
					else if (responseNum == 3) {
						logger.info("Customer applied for an account");
						Customer.apply();
					}
					else if (responseNum == 4) {
						logger.info("Customer applied for a joint account");
						System.out.println("Apply for joint account with:");
						String joint = reader.next();
						Customer.apply(joint);
					}
					else if (responseNum == 5) {
						logger.info("Customer initiated withdrawal");
						System.out.println("Withdraw from account #:");
						int account = reader.nextInt();
						System.out.println("Withdrawal amount:");
						float amount = reader.nextFloat();
						Customer.withdraw(amount, account);
					}
					else if (responseNum == 6) {
						logger.info("Customer initiated deposit");
						System.out.println("Deposit to account #:");
						int account = reader.nextInt();
						System.out.println("Deposit amount:");
						float amount = reader.nextFloat();
						Customer.deposit(amount, account);
					}
					else if (responseNum == 7) {
						logger.info("Customer initiated transfer");
						System.out.println("Transfer from account #:");
						int accounta = reader.nextInt();
						System.out.println("Transfer to account #:");
						int accountb = reader.nextInt();
						System.out.println("Transfer amount:");
						float amount = reader.nextFloat();
						Customer.transfer(amount, accounta, accountb);
					}
					logger.info("Customer returned to menu");
				} // while customer is logged in
			}
			else if (worker==true) {
				AdminDAO Employee = new AdminDAO();
				online = Employee.login(username, password);

				//employee menu
				while (online == true && worker==true) {
					System.out.println("1. Logout");
					System.out.println("2. View all customers");
					System.out.println("3. Look up customer");
					System.out.println("4. View applications");
					if (Employee.isAdmin()==true) {
						System.out.println("5. Withdraw");
						System.out.println("6. Deposit");
						System.out.println("7. Transfer");
						System.out.println("8. View all accounts");
						System.out.println("9. Cancel account");
					}
					responseNum = reader.nextInt();
					if (responseNum == 1) {
						logger.info("Employee attempted logout");
						online = Employee.logout();
					}
					else if (responseNum == 2) {
						logger.info("Employee looked up customer directory");
						Employee.getAllCustomers();
					}
					else if (responseNum == 3) {
						logger.info("Employee looked up customer");
						System.out.println("1. Look up customer by username");
						System.out.println("2. Look up customer by id");
						responseNum = reader.nextInt();
						if (responseNum == 1) {
							System.out.println("Enter customer username:");
							response = reader.next();
							Employee.getCustomer(response);
							Employee.getAllAccounts(response);
						}
						else if (responseNum == 2) {
							System.out.println("Enter customer ID:");
							responseNum = reader.nextInt();
							Employee.getCustomer(responseNum);
							Employee.getAllAccounts(responseNum);
						}
					}
					else if (responseNum == 4) {
						logger.info("Employee checked applications");
						Employee.getApplications();
						boolean exit=false;
						while (exit==false) {
							System.out.println("1. Approve application");
							System.out.println("2. Deny application");
							System.out.println("3. Exit");
							responseNum = reader.nextInt();
							if (responseNum == 1) {
								System.out.println("Enter application ID:");
								logger.info("Employee approved an application");
								responseNum = reader.nextInt();
								Employee.approve(responseNum);
							}
							else if (responseNum == 2) {
								System.out.println("Enter application ID:");
								responseNum = reader.nextInt();
								logger.info("Employee denied an application");
								Employee.deny(responseNum);
							}
							else exit=true;
						}
					}
					else if (responseNum == 5 && Employee.isAdmin()==true) {
						logger.info("Admin initiated withdrawal");
						System.out.println("Withdraw from account #:");
						int account = reader.nextInt();
						System.out.println("Withdrawal amount #:");
						float amount = reader.nextFloat();
						Employee.withdraw(amount, account);
					}
					else if (responseNum == 6 && Employee.isAdmin()==true) {
						logger.info("Admin initiated deposit");
						System.out.println("Deposit to account #:");
						int account = reader.nextInt();
						System.out.println("Deposit amount #:");
						float amount = reader.nextFloat();
						Employee.deposit(amount, account);						
					}
					else if (responseNum == 7 && Employee.isAdmin()==true) {
						logger.info("Admin initiated transfer");
						System.out.println("Transfer from account #:");
						int accounta = reader.nextInt();
						System.out.println("Transfer to account #:");
						int accountb = reader.nextInt();
						System.out.println("Transfer amount:");
						float amount = reader.nextFloat();
						Employee.transfer(amount, accounta, accountb);						
					}
					else if (responseNum == 8) {
						logger.info("Employee looked up all accounts");
						Employee.getAllAccounts();
					}
					else if (responseNum == 9 && Employee.isAdmin()==true) {
						logger.info("Admin cancelled account");
						System.out.println("Cancel account #:");
						int account = reader.nextInt();
						Employee.cancel(account);
					}
					logger.info("Employee returned to menu");
				} // while employee is logged in

			}
		} //while (online==false)
		logger.warn("Return to start");
	}

}
