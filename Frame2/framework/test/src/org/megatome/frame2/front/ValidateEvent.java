package org.megatome.frame2.front;

import org.megatome.frame2.event.CommonsValidatorEvent;

/**
 * 
 */
public class ValidateEvent extends CommonsValidatorEvent {

   private String _parm1;
   private String _parm2;
   private int    _parm3;

	/**
	 * Constructor for Event1.
	 */
	public ValidateEvent() {
		super();
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

	/**
	 * Returns the parm3.
	 * @return int
	 */
	public int getParm3() {
		return _parm3;
	}

	/**
	 * Sets the parm3.
	 * @param parm3 The parm3 to set
	 */
	public void setParm3(int parm3) {
		_parm3 = parm3;
	}

}
