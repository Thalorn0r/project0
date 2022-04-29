package model;

public class AccountModel {

	int acctnum;
	int balance;
	String userA;
	String userB;
	
	public AccountModel() {
	}

	public AccountModel(int id, int balance2, String ownerA, String ownerB) {
		this.acctnum = id;
		this.balance = balance2;
		this.userA = ownerA;
		this.userB = ownerB;
	}
}
