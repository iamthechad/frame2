package servletunit.frame2;

import junit.framework.TestCase;

import org.megatome.frame2.log.LoggerException;
import org.megatome.frame2.log.LoggerFactory;

public class Frame2TestCase extends TestCase {

   /**
    * Constructor for Frame2TestCase.
    */
   public Frame2TestCase() {
      super();
      setupLogger();
   }

   /**
    * Constructor for Frame2TestCase.
    * @param name
    */
   public Frame2TestCase(String name) {
      super(name);
      setupLogger();
   }
   
   protected void setupLogger(){
      try {
         LoggerFactory.setType("org.megatome.frame2.log.impl.Log4jLogger", getClass().getClassLoader());
      } catch (LoggerException e) {
         System.out.println("Unable to initialize looger for TestCase");
      }
   }
}
