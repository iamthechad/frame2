package org.megatome.frame2.front;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

/**
 * 
 */
public class HandlerThrowingException implements EventHandler {

	/**
	 * Constructor for HandlerThrowingException.
	 */
	public HandlerThrowingException() {
		super();
	}

	public String handle(Event event,Context context) throws Exception {
      if(context.getRequestAttribute("exception").equals("throwAnException")) {
         throw new HandlerExceptionTest("This is a Test");
      }
		return "ev12forward";
	}

}
