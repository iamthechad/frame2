package org.megatome.example;

import org.megatome.frame2.event.CommonsValidatorEvent;

public class User extends CommonsValidatorEvent /*implements org.megatome.example.jaxbgen.User*/ {

	private String userName;
	private String email;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
