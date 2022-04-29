package dao;

import java.util.ArrayList;

import model.AccountModel;
import model.ApplicationModel;

public interface CustomerInterface {
	
	//create
	public void createCustomer();
	
	//retrieve
	public ArrayList<AccountModel> getAccounts();
	//public ArrayList<ApplicationModel> getAppStatus(); //stretch goal
	
	//update
	public void withdraw(float withdrawal, int account);
	public void	deposit(float deposit, int account);
	public void transfer(float amount, int accountA, int accountB);
	
	public void apply();
	public void apply(String applicantB);
	
	//delete N/A

}
