package org.megatome.frame2.front;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

/**
 * 
 */
public class Ev12handler implements EventHandler {

	/**
	 * Constructor for Ev1handler2.
	 */
	public Ev12handler() {
		super();
	}

	public String handle(Event event,Context context) {
		return "ev12forward";
	}

}
