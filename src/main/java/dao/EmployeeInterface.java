package dao;

import java.util.ArrayList;

import model.AccountModel;
import model.ApplicationModel;
import model.CustomerModel;

public interface EmployeeInterface {
	
	//create N/A
	
	//retrieve
	public ArrayList<AccountModel> getAllAccounts();
	public ArrayList<AccountModel> getAllAccounts(String customer);
	public CustomerModel getCustomer(String customer);
	public ArrayList<CustomerModel> getAllCustomers();
	public ArrayList<ApplicationModel> getApplications();
	
	//update
	public void approve(int AppID);
	public void deny(int AppID);
	
	//delete N/A

}
