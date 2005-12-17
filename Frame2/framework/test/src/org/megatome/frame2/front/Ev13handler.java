package org.megatome.frame2.front;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

/**
 * 
 */
public class Ev13handler implements EventHandler {

	/**
	 * Constructor for Ev13handler.
	 */
	public Ev13handler() {
		super();
	}

	public String handle(Event event,Context context) {
		return "ev13forward";
	}

}
