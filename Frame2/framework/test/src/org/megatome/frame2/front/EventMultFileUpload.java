package org.megatome.frame2.front;

import org.apache.commons.fileupload.FileItem;
import org.megatome.frame2.event.CommonsValidatorEvent;


public class EventMultFileUpload extends CommonsValidatorEvent {

	private String parm1;
	private FileItem[] fileparm;
   /**
    * 
    */
   public EventMultFileUpload() {
      super();
   }

   /**
    * @return
    */
   public FileItem[] getFileparm() {
      return fileparm;
   }

   /**
    * @return
    */
   public String getParm1() {
      return parm1;
   }

   /**
    * @param item
    */
   public void setFileparm(FileItem[] item) {
      fileparm = item;
   }

   /**
    * @param string
    */
   public void setParm1(String string) {
      parm1 = string;
   }
}
