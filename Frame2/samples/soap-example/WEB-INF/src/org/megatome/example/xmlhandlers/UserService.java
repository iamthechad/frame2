package org.megatome.example.xmlhandlers;

import org.megatome.frame2.front.SoapFrontController;
import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.util.dom.DOMStreamConverter;
import org.w3c.dom.Element;


/**
 * Example of a SOAP message service to add users.
 */
public class UserService extends SoapFrontController {
   private static Logger LOGGER = LoggerFactory.instance(UserService.class.getName());

   public Element[] addUser(Element[] elems) {
      Element[] result = null;

      try {
         LOGGER.debug(DOMStreamConverter.toOutputStream(elems[0]).toString());
         result = processEvent(elems);
      } catch (Exception e) {
         LOGGER.severe("Unable to process element");
      }

      return result;
   }
   
   public Element[] getUsers(Element[] elems) {
   	 return null;
   }

   /**
    *  point the package specifier to where jaxb
    *  generated the event classes
    */
   public String getEventPackageSpecifier() {
      return "org.megatome.example.jaxbgen";
   }
}
