package app;

import java.util.Scanner;

import dao.CustomerDAO;
import dao.EmployeeDAO;

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
				CustomerDAO customer = new CustomerDAO();
				online = customer.login(username, password);
				
				//temporary program break || while customer is logged in
				while (online == true && worker==false) {
					System.out.println("1. Logout");
					responseNum = reader.nextInt();
					if (responseNum == 1) {
						online = customer.logout();
					}
				} // while customer is logged in
			}
			else if (worker==true) {
				EmployeeDAO employee = new EmployeeDAO();
				online = employee.login(username, password);

				//temporary program break || while employee is logged in
				while (online == true && worker==true) {
					System.out.println("1. Logout");
					responseNum = reader.nextInt();
					if (responseNum == 1) {
						online = employee.logout();
					}
				} // while employee is logged in

			}
		} //while (online==false)
		reader.close();
	}

}
