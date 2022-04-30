package dao;

public interface UserInterface {
	
	public boolean login(String username, String password);
	public boolean logout();
	
	/*
	public String getUsername(int ID);
	public int getID(String Username);
	*/

}
