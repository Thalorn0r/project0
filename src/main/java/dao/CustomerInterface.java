package dao;

import model.CustomerModel;

public interface CustomerInterface {
	
	//create
	public void createCustomer(String username, String password);
	
	//retrieve
	public void getAccounts();
	//public ArrayList<ApplicationModel> getAppStatus(); //stretch goal
	
	//update
	public boolean withdraw(float withdrawal, int account);
	public void	deposit(float deposit, int account);
	public void transfer(float amount, int accountA, int accountB);
	
	public void apply();
	public void apply(String applicantB);
	
	//delete N/A
	
	//now do it again, but for Javalin
	//create
	public void create(CustomerModel element);
	
	//retrieve
	public CustomerModel get(String username);
	
	//update
	public void update(CustomerModel element);
	
	

}
