package org.megatome.frame2.front;

import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

/**
* 
*/
public class ServletContextAccessHandler implements EventHandler {

	/**
	 * Constructor for Ev1handler1.
	 */
	public ServletContextAccessHandler() {
		super();
	}

	public String handle(Event event,Context context) throws Frame2Exception {
      context.getServletContext().setAttribute("foo", "bar");
      return null;
	}
}
