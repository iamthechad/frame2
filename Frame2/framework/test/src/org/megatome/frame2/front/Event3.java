package org.megatome.frame2.front;

import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.event.CommonsValidatorEvent;


/**
 * 
 */
public class Event3 extends CommonsValidatorEvent {

	/**
	 * Constructor for Event1.
	 */
	public Event3() {
		super();
	}

	public boolean validate(Errors errors) {
		return true;
	}
}
