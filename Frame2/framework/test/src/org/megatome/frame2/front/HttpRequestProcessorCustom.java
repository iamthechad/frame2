package org.megatome.frame2.front;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.megatome.frame2.front.Configuration;
import org.megatome.frame2.front.RequestProcessor;

/**
 * @author hmilligan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 * 
 * 
 * Used for Testing.
 */
public class HttpRequestProcessorCustom implements RequestProcessor {

   HttpRequestProcessorCustom(
      Configuration config,
      ServletContext context,
      HttpServletRequest request,
      HttpServletResponse response) {
      
   }


   public Object processRequest() throws Throwable {
      return null;
   }

   public void release() {
   }
   
   public void preProcess() {
   }
   
   public void postProcess() {
   }
   
   

}
