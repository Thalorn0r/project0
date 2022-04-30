package dao;

public interface EmployeeInterface {
	
	//create N/A
	
	//retrieve
	public void getAllAccounts();
	public void getAllAccounts(String customer);
	public void getCustomer(String customername);
	public void getCustomer(int customerID);
	public void getAllCustomers();
	public void getApplications();
	
	public boolean isAdmin();
	
	//update
	public void approve(int AppID);
	public void deny(int AppID);
	
	//delete N/A

}
