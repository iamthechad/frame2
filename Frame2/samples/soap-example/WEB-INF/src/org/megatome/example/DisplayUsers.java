package org.megatome.example;

import java.util.List;

import org.megatome.frame2.event.CommonsValidatorEvent;

public class DisplayUsers extends CommonsValidatorEvent {
	private List users;
	
	/**
	 * @return Returns the users.
	 */
	public List getUsers() {
		return users;
	}
	
	/**
	 * @param users The users to set.
	 */
	public void setUsers(List users) {
		this.users = users;
	}
}
