package org.megatome.example.xmlhandlers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Responder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class AckResponder implements Responder {

	public Object respond(Context context) {
      Document doc = null;

      try {
         DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

         doc = builder.newDocument();

         Node elem = doc.createElement("ack");

         doc.appendChild(elem);
      } catch (ParserConfigurationException e) {
      } catch (FactoryConfigurationError e) {
      }

      return doc.getDocumentElement();
   }
}
