package app;

import java.util.Scanner;

import dao.AdminDAO;
import dao.CustomerDAO;

public class driver {

	public static void main(String[] args) {
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
			response= reader.nextLine();
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
			username = reader.nextLine();
			System.out.println("Password:");
			password = reader.nextLine();
			
			
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
						online = Customer.logout();
					}
					else if (responseNum == 2) {
						Customer.getAccounts();						
					}
					else if (responseNum == 3) {						
						Customer.apply();
					}
					else if (responseNum == 4) {
						System.out.println("Apply for joint account with:");
						String joint = reader.next();
						Customer.apply(joint);
					}
					else if (responseNum == 5) {
						System.out.println("Withdraw from account #:");
						int account = reader.nextInt();
						System.out.println("Withdrawal amount:");
						float amount = reader.nextFloat();
						Customer.withdraw(amount, account);
					}
					else if (responseNum == 6) {
						System.out.println("Deposit to account #:");
						int account = reader.nextInt();
						System.out.println("Deposit amount:");
						float amount = reader.nextFloat();
						Customer.deposit(amount, account);
					}
					else if (responseNum == 7) {
						System.out.println("Transfer from account #:");
						int accounta = reader.nextInt();
						System.out.println("Transfer to account #:");
						int accountb = reader.nextInt();
						System.out.println("Transfer amount:");
						float amount = reader.nextFloat();
						Customer.transfer(amount, accounta, accountb);
					}
				} // while customer is logged in
			}
			else if (worker==true) {
				AdminDAO Employee = new AdminDAO();
				online = Employee.login(username, password);

				//employee menu
				while (online == true && worker==true) {
					System.out.println("");
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
						online = Employee.logout();
					}
					else if (responseNum == 2) {
						Employee.getAllCustomers();
					}
					else if (responseNum == 3) {
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
						Employee.getApplications();
						boolean exit=false;
						while (exit==false) {
							System.out.println("1. Approve application");
							System.out.println("2. Deny application");
							System.out.println("3. Exit");
							responseNum = reader.nextInt();
							if (responseNum == 1) {
								System.out.println("Enter application ID:");
								responseNum = reader.nextInt();
								Employee.approve(responseNum);
							}
							else if (responseNum == 2) {
								System.out.println("Enter application ID:");
								responseNum = reader.nextInt();
								Employee.deny(responseNum);
							}
							else exit=true;
						}
					}
					else if (responseNum == 5 && Employee.isAdmin()==true) {
						System.out.println("Withdraw from account #:");
						int account = reader.nextInt();
						System.out.println("Withdrawal amount #:");
						float amount = reader.nextFloat();
						Employee.withdraw(amount, account);
					}
					else if (responseNum == 6 && Employee.isAdmin()==true) {
						System.out.println("Deposit to account #:");
						int account = reader.nextInt();
						System.out.println("Deposit amount #:");
						float amount = reader.nextFloat();
						Employee.deposit(amount, account);						
					}
					else if (responseNum == 7 && Employee.isAdmin()==true) {
						System.out.println("Transfer from account #:");
						int accounta = reader.nextInt();
						System.out.println("Transfer to account #:");
						int accountb = reader.nextInt();
						System.out.println("Transfer amount:");
						float amount = reader.nextFloat();
						Employee.transfer(amount, accounta, accountb);						
					}
					else if (responseNum == 8 && Employee.isAdmin()==true) {
						Employee.getAllAccounts();
					}
					else if (responseNum == 9 && Employee.isAdmin()==true) {
						System.out.println("Cancel account #:");
						int account = reader.nextInt();
						Employee.cancel(account);
					}
				} // while employee is logged in

			}
		} //while (online==false)
		reader.close();
	}

}
