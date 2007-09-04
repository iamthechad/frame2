package org.megatome.frame2.front;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

public class URIEventHandler implements EventHandler {

	@Override
	public String handle(Event event, @SuppressWarnings("unused")
	Context context) throws Exception {
		URIEvent ue = (URIEvent)event;
		if ("1234".equals(ue.getItem())) { //$NON-NLS-1$
			return null;
		}
		
		return "failForward"; //$NON-NLS-1$
	}

}
