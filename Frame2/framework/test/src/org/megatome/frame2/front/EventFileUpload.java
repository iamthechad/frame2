package org.megatome.frame2.front;

import org.apache.commons.fileupload.FileItem;
import org.megatome.frame2.event.CommonsValidatorEvent;


public class EventFileUpload extends CommonsValidatorEvent {

	private String parm1;
	private FileItem fileparm;
	private FileItem fileparm2;
   /**
    * 
    */
   public EventFileUpload() {
      super();
   }

   /**
    * @return
    */
   public FileItem getFileparm() {
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
   public void setFileparm(FileItem item) {
      fileparm = item;
   }

   /**
    * @param string
    */
   public void setParm1(String string) {
      parm1 = string;
   }

   /**
    * @return
    */
   public FileItem getFileparm2() {
      return fileparm2;
   }

   /**
    * @param item
    */
   public void setFileparm2(FileItem item) {
      fileparm2 = item;
   }

}
