package org.megatome.frame2.front;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

/**
 * 
 */
public class Ev1handler2 implements EventHandler {

	/**
	 * Constructor for Ev1handler2.
	 */
	public Ev1handler2() {
		super();
	}

	public String handle(Event event,Context context) {
      context.setRequestAttribute("Ev1handler2",new Boolean(true));
		return "view1";
	}

}
