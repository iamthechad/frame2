package org.megatome.frame2.front;

import org.megatome.frame2.front.Configuration;
import org.megatome.frame2.front.SoapRequestProcessor;
import org.w3c.dom.Element;

/**
 * @author hmilligan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SoapRequestProcessorCustom extends SoapRequestProcessor {

   /**
    * Constructor for SoapRequestProcessorCustom.
    * @param config
    * @param elements
    * @param eventPkg
    */
   public SoapRequestProcessorCustom(
      Configuration config,
      Element[] elements,
      String eventPkg) {
      super(config, elements, eventPkg);
   }

}
