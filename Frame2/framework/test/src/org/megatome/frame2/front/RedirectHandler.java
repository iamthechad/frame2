package org.megatome.frame2.front;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

/**
* 
*/
public class RedirectHandler implements EventHandler {

	/**
	 * Constructor for RedirectHandler.
	 */
	public RedirectHandler() {
		super();
	}

	public String handle(Event event,Context context) throws Exception {
      context.setRequestAttribute("firstName","Barney",true);
      context.setRequestAttribute("middle","Jeff",true);
      context.setRequestAttribute("nonRedirectAttr1","attr1",false);
      context.setRequestAttribute("lastName","Jones",true);
      context.setRequestAttribute("nonRedirectAttr2","attr2");
      return "redirect";
	}
}
