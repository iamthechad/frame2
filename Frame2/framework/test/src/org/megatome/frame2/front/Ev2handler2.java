package org.megatome.frame2.front;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

/**
 * 
 */
public class Ev2handler2 implements EventHandler {

	/**
	 * Constructor for Ev2handler2.
	 */
	public Ev2handler2() {
		super();
	}

	public String handle(Event event,Context context) {
		return "ev2forward2";
	}

}
