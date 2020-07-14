package sap.practice.mobilesystem.utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private String username;
	private String password;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	
}
