package servletunit.frame2;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;

import servletunit.HttpServletRequestSimulator;


public class Frame2HttpServletRequestSimulator
   extends HttpServletRequestSimulator {

	RequestStream is = null;
	int contentLength = -1;
   /**
    * @param arg0
    */
   public Frame2HttpServletRequestSimulator(ServletContext arg0) {
      super(arg0);
   }
   
   

   /* (non-Javadoc)
    * @see javax.servlet.ServletRequest#getInputStream()
    */
   public ServletInputStream getInputStream() throws IOException {
      return is;
   }
   
   public void setDataInputStream(int contentLength, InputStream is) {
   	this.is = new RequestStream(contentLength, is);
   	this.contentLength = contentLength;
   }
   
   

   /* (non-Javadoc)
    * @see javax.servlet.ServletRequest#getContentLength()
    */
   public int getContentLength() {
      return contentLength;
   }

}
