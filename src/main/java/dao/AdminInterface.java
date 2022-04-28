package dao;

public interface AdminInterface {
	public void withdraw(int withdrawal, int account);
	public void	deposit(int deposit, int account);
	public void transfer(int amount, int accountA, int accountB);
	
	/*
	public void getStaff();
	public void hire(String employee);
	public void fire(String employee);
	public void promote(String employee);
	*/
}
