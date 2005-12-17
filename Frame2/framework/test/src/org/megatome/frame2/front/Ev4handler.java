package org.megatome.frame2.front;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

/**
 * 
 */
public class Ev4handler implements EventHandler {

	/**
	 * Constructor for Ev4handler.
	 */
	public Ev4handler() {
		super();
	}

	public String handle(Event event,Context context) {
		return "invalidView";
	}

}
