package org.megatome.frame2.front;

import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

/**
* 
*/
public class ChainHandler implements EventHandler {

	/**
	 * Constructor for Ev1handler1.
	 */
	public ChainHandler() {
		super();
	}

	public String handle(Event event,Context context) throws Frame2Exception {
      context.setRequestAttribute("eventChainName",event.getName());
      return null;
	}
}
