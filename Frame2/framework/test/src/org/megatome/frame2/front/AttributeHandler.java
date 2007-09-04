package org.megatome.frame2.front;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

public class AttributeHandler implements EventHandler {

	@Override
	public String handle(Event event, Context context) throws Exception {
		if (event instanceof Event1) {
			context.addResponseURIAttribute("item", Integer.valueOf(1234)); //$NON-NLS-1$
		} else if (event instanceof Event2) {
			context.addResponseURIAttribute("item", "failme"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return null;
	}

}
