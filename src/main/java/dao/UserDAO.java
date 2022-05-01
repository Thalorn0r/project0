package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO implements UserInterface{
	
	private Connection connect = ConnectionManager.getConnection();
	
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean logout() {
		// TODO Auto-generated method stub
		return false;
	}
	
	// get all accounts for one customer by username
	public void getAccount(int accountID) {
		try {
			String query ="SELECT * FROM account WHERE id= ?";
			PreparedStatement pstmt = connect.prepareStatement(query);
			pstmt.setInt(1, accountID);
			
			ResultSet rs = pstmt.executeQuery();
			//ArrayList<AccountModel> accounts = new ArrayList<AccountModel>();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				float balance = rs.getFloat("balance");
				int userA = rs.getInt("ownerA");
				int userB = rs.getInt("ownerB");

				System.out.println("Account Number:  "+id+" Balance: $ "+balance+" Owner 1: "+userA+" Owner 2: "+userB);

				//accounts.add(new AccountModel(id, balance, ownerA, ownerB));
			}
			
			/*
			// print results
			for (AccountModel i: accounts) {
				System.out.println(i);
			} */
			
			return;
		} catch (Exception e) {
			e.printStackTrace();
		} return;		
	}

}
