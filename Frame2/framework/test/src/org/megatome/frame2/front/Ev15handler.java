package org.megatome.frame2.front;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

/**
 * 
 */
public class Ev15handler implements EventHandler {

	/**
	 * Constructor for Ev15handler.
	 */
	public Ev15handler() {
		super();
	}

	public String handle(Event event,Context context) {
		return "listResponder";
	}

}
