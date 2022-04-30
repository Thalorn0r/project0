package model;

public class AccountModel {

	public int acctnum;
	public float balance;
	public int userA;
	public int userB;
	
	public AccountModel() {
	}

	public AccountModel(int id, float balance2, int ownerA, int ownerB) {
		this.acctnum = id;
		this.balance = balance2;
		this.userA = ownerA;
		this.userB = ownerB;
	}
}
