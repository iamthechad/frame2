package org.megatome.frame2.front;

import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.event.CommonsValidatorEvent;


/**
 * 
 */
public class Event4 extends CommonsValidatorEvent {

   private String _parm1;
   private String _parm2;

	/**
	 * Constructor for Event1.
	 */
	public Event4() {
		super();
	}

	public boolean validate(Errors errors) {
      boolean result = true;

      if ( _parm1 == null || _parm1.indexOf('1') == -1 ) {
         result = false;
         if ( errors != null ) {
            errors.add("Event4.parm1",_parm1);
         }
      }

      if ( _parm2 == null || _parm2.indexOf('2') == -1 ) {
         result = false;
         if ( errors != null ) {
            errors.add("Event4.parm2",_parm2);
         }
      }

		return result;
	}

	/**
	 * Method getParm1.
	 * @return Object
	 */
	public String getParm1() {
		return _parm1;
	}

	/**
	 * Returns the parm2.
	 * @return String
	 */
	public String getParm2() {
		return _parm2;
	}

	/**
	 * Sets the parm1.
	 * @param parm1 The parm1 to set
	 */
	public void setParm1(String parm1) {
		_parm1 = parm1;
	}

	/**
	 * Sets the parm2.
	 * @param parm2 The parm2 to set
	 */
	public void setParm2(String parm2) {
		_parm2 = parm2;
	}

}
