package dao;

public interface EmployeeInterface {
	
	public void getAllAccounts();
	public void getCustomer(String customer);
	public void getApplications();
	
	public void approve(int Application);
	public void deny(int Application);

}
