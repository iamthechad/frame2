package org.megatome.example;

import java.util.List;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

public class DisplayUsersHandler implements EventHandler {

	public String handle(Event event, Context context) throws Exception {
		DisplayUsers myEvent = (DisplayUsers)event;
		
		List users = UserStorage.getUsers();
		myEvent.setUsers(users);
		return null;
	}
}
