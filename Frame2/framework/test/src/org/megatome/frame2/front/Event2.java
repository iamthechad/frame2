package org.megatome.frame2.front;

import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.event.CommonsValidatorEvent;


/**
 * 
 */
public class Event2 extends CommonsValidatorEvent {

	/**
	 * Constructor for Event1.
	 */
	public Event2() {
		super();
	}

	public boolean validate(Errors errors) {
		return true;
	}
}
