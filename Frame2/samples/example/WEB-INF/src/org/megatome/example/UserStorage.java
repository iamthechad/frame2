package org.megatome.example;

import java.util.ArrayList;
import java.util.List;

public class UserStorage {
	private static List users = new ArrayList();
	
	public static void addUser(User user) {
		users.add(user);
	}
	
	public static List getUsers() {
		return users;
	}
}
