package org.megatome.example;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

public class AddUserHandler implements EventHandler {

	public String handle(Event event, Context context) throws Exception {
		User user = (User)event;
		
		UserStorage.addUser(user);
		return null;
	}
}
