package org.megatome.frame2.front.config;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.util.sax.ParserException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SchemaMappingTagHandler extends ConfigElementHandler {
	private static Logger LOGGER = LoggerFactory.instance(SchemaMappingTagHandler.class
            .getName());
	private EventNameTagHandler eventNameTagHandler;
	private static final String SCHEMA_LOCATION = "schemaLocation"; //$NON-NLS-1$
	private String schemaLocation;

	private Map<String, Schema> schemaMappings = new HashMap<String, Schema>();
	private Map<String, Schema> loadedSchema = new HashMap<String, Schema>();

	private final SchemaFactory sf;

	public SchemaMappingTagHandler(EventNameTagHandler eventNameTagHandler) {
		this.eventNameTagHandler = eventNameTagHandler;
		this.sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused")
	@Override
	public void endElement(String uri, String localName, String name)
			throws ParserException {
		for (String eventName : this.eventNameTagHandler.getEventNames()) {
			if (this.schemaMappings.containsKey(eventName)) {
				LOGGER.severe("The event \"" + eventName + "\" is already mapped to a schema. Cannot map to another schema."); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				Schema s = loadSchema(this.schemaLocation);
				if (s != null) {
					this.schemaMappings.put(eventName, s);
				}
			}
		}
		this.eventNameTagHandler.clear();
	}

	@SuppressWarnings("unused")
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws ParserException {
		this.schemaLocation = attributes.getValue(SCHEMA_LOCATION);
	}

	private Schema loadSchema(final String location) {
		Schema s = this.loadedSchema.get(location);
		if (s == null) {
			try {
				URL schemaURL = getClass().getResource(location);
				if (schemaURL != null) {
					s = this.sf.newSchema(schemaURL);
					this.loadedSchema.put(location, s);
				} else {
					LOGGER.severe("Failed to load schema at location: " + location); //$NON-NLS-1$
				}
			} catch (SAXException e) {
				LOGGER.severe("Failed to load schema at location: " + location, e); //$NON-NLS-1$
			}
		}

		return s;
	}

	@Override
	public void clear() {
		this.schemaLocation = null;
		this.eventNameTagHandler.clear();
		this.schemaMappings.clear();
	}

	public Map<String, Schema> getSchemaMappings() {
		return Collections.unmodifiableMap(this.schemaMappings);
	}

}
