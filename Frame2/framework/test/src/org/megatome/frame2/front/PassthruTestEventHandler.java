package org.megatome.frame2.front;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;
import org.megatome.frame2.event.xml.PassthruEvent;

/**
 * 
 */
public class PassthruTestEventHandler implements EventHandler {

	public String handle(Event event, Context context) throws Exception {
      PassthruEvent ptevent = (PassthruEvent) event;
      context.setRequestAttribute("passthruResponse",ptevent.getPassthruData().getFirstChild());
      return null;
	}

}
