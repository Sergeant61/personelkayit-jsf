package rcp.entity;

public class UserEntity {
	
	private String userName;
	private String password;

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getUserName() {
		return userName;
	}
	
}
