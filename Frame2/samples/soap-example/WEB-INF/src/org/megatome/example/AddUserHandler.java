package org.megatome.example;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

public class AddUserHandler implements EventHandler {

	public String handle(Event event, Context context) throws Exception {
		User user = null;
		if (event instanceof org.megatome.example.jaxbgen.User) {
			user = new User();
			org.megatome.example.jaxbgen.User jaxbUser = (org.megatome.example.jaxbgen.User)event;
			user.setUserName(jaxbUser.getUserName());
			user.setEmail(jaxbUser.getEmail());
		} else {
			user = (User)event;
		}
		
		UserStorage.addUser(user);
		return null;
	}
}
