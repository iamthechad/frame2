package org.megatome.frame2.front;

import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.event.CommonsValidatorEvent;

public class FailsValidationEvent extends CommonsValidatorEvent {


    String _stillThere;
   /**
    * Constructor for FailsValidationEvent.
    */
   public FailsValidationEvent() {
      super();
   }
   
   
   
   public boolean validate(Errors errors) {
      return false;
   }

   /**
    * Returns the stillThere.
    * @return String
    */
   public String getStillThere() {
      return _stillThere;
   }

   /**
    * Sets the stillThere.
    * @param stillThere The stillThere to set
    */
   public void setStillThere(String stillThere) {
      _stillThere = stillThere;
   }

}
