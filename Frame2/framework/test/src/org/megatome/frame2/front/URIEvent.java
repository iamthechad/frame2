package org.megatome.frame2.front;

import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.event.CommonsValidatorEvent;

public class URIEvent extends CommonsValidatorEvent {
	@Override
	public boolean validate(Errors errors) {
		boolean valid = super.validate(errors);
		if (!"1234".equals(this.item)) { //$NON-NLS-1$
			valid = false;
		}
		
		return valid;
	}

	private String item;
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public String getItem() {
		return this.item;
	}
}
