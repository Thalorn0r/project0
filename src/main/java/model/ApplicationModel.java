package model;

public class ApplicationModel {
	public int id;
	public String userA;
	public String userB;
	public String status;
	
	public ApplicationModel() {
		
	}

	public ApplicationModel(int ID, String userA, String userB, String status) {
		this.id = ID;
		this.userA = userA;
		this.userB = userB;
		this.status = status;
	}
	
}
