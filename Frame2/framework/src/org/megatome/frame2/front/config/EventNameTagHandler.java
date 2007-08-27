package org.megatome.frame2.front.config;

import java.util.ArrayList;
import java.util.List;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.util.sax.ParserException;

public class EventNameTagHandler extends ConfigElementHandler {
	private static Logger LOGGER = LoggerFactory.instance(EventNameTagHandler.class
            .getName());
	private List<String> eventNames = new ArrayList<String>();

	@Override
	public void clear() {
		this.eventNames.clear();
	}

	@SuppressWarnings("unused")
	@Override
	public void characters(char[] ch, int start, int length)
			throws ParserException {
		char[] value = new char[length];
		System.arraycopy(ch, start, value, 0, length);
		String tempName = String.valueOf(value);
		if (tempName.isEmpty()) {
			LOGGER.severe("Event name is empty: cannot add to schema mapping"); //$NON-NLS-1$
		}
		if (this.eventNames.contains(tempName)) {
			LOGGER.warn("Event name " + tempName + " is already mapped to this schema"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			this.eventNames.add(tempName);
		}
	}
	
	public List<String> getEventNames() {
		return this.eventNames;
	}
}
