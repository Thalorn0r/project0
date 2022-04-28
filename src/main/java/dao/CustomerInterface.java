package dao;

public interface CustomerInterface {
	
	public void apply(String applicant);
	public void applyJoint(String applicantA, String applicantB);
	
	public void getAccounts();
	public void getAppStatus();
	
	public void withdraw(float withdrawal, int account);
	public void	deposit(float deposit, int account);
	public void transfer(float amount, int accountA, int accountB);

}
