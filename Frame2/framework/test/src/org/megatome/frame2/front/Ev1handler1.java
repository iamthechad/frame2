package org.megatome.frame2.front;

import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

/**
* 
*/
public class Ev1handler1 implements EventHandler {

	/**
	 * Constructor for Ev1handler1.
	 */
	public Ev1handler1() {
		super();
	}

	public String handle(Event event,Context context) throws Frame2Exception {
      context.setRequestAttribute("ev1parm1",context.getInitParameter("parm1"));
      return null;
	}
}
