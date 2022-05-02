package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// EmployeeDAO with the powers of a Customer!

public class AdminDAO extends EmployeeDAO implements AdminInterface {

	private Connection connect = ConnectionManager.getConnection();
	private static final Logger logger = LogManager.getLogger(AdminDAO.class);

	public boolean withdraw(float withdrawal, int account) {
		if (withdrawal<=0) {
			System.out.println("Amount must be greater than 0.");
			logger.warn("Negative amount withdraw attempted");
			return false;		
		}
		else {
			try {
				String query = "SELECT balance from account WHERE id = ?";
				PreparedStatement pstmtB = connect.prepareStatement(query);
				pstmtB.setInt(1, account);
				ResultSet rs = pstmtB.executeQuery();
				float balance;
				if (rs.next())
					balance = rs.getFloat(1);
				else {
					logger.warn("Account not found");
					return false;
				}
				
				if (withdrawal>balance) {
					System.out.println("Insufficient funds;");
					logger.warn("Account overdrawn");

					return false;
				}
				else {
					query = "UPDATE account SET balance = balance - ? WHERE id = ?";
					PreparedStatement pstmt = connect.prepareStatement(query);
								
					pstmt.setFloat(1, withdrawal);
					pstmt.setInt(2, account);
					pstmt.execute();
					
					logger.info("Withdrawal successful");
	
					this.getAccount(account);
					
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Withdrawal failed.");	
			} return false;
		}
		
	}

	public void deposit(float deposit, int account) {
		if (deposit<=0) {
			System.out.println("Amount must be greater than 0.");
			logger.warn("Negative amount attempted");
		} else {
			try {
				String query = "UPDATE account SET balance = balance + ? WHERE id = ?";
				PreparedStatement pstmt = connect.prepareStatement(query);
				
				
				pstmt.setFloat(1, deposit);
				pstmt.setInt(2, account);
				pstmt.execute();
				logger.info("Deposit successful.");	

				this.getAccount(account);
	
			} catch (Exception e) {
				logger.error("Deposit failed.");	
				e.printStackTrace();
			}
		}
	}

	public void transfer(float amount, int accountA, int accountB) {
		if (this.withdraw(amount, accountA)==true) {
			this.deposit(amount, accountB);
			logger.info("Transfer successful.");	
		}
		else {System.out.println("Transaction failed.");}
		logger.error("Transfer failed.");	

	}

	public void cancel(int accountID) {
		try {
			String query = "DELETE FROM account WHERE id =?";
			PreparedStatement pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, accountID);
			pstmt.execute();
			
			logger.info("Account terminated.");	
			System.out.println("Account #"+accountID+" deleted.");
		} catch (Exception e) {
			logger.error("Failed to cancel account.");	
			e.printStackTrace();
		}		
	}
	
	public void cancel(String deadname) { //destroys all accounts by user
		try {
			String query = "DELETE FROM account WHERE ownerA =?";
			PreparedStatement pstmt = connect.prepareStatement(query);
			pstmt.setString(1, deadname);
			pstmt.execute();
			
			System.out.println("Smiting "+deadname);
			logger.info("Accounts terminated.");	

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("DELETE failed.");	
		}					
	}

}
