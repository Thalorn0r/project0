package dao;

public interface AdminInterface {
	public boolean withdraw(float withdrawal, int account);
	public void	deposit(float deposit, int account);
	public void transfer(float amount, int accountA, int accountB);
	
	public void cancel(int accountID);
	
	/*
	public void getStaff();
	public void hire(String employee);
	public void fire(String employee);
	public void promote(String employee);
	*/
}
