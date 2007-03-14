/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2007 Megatome Technologies.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by
 *        Megatome Technologies."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Frame2 Project", and "Frame2", 
 *    must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact iamthechad@sourceforge.net.
 *
 * 5. Products derived from this software may not be called "Frame2"
 *    nor may "Frame2" appear in their names without prior written
 *    permission of Megatome Technologies.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL MEGATOME TECHNOLOGIES OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.megatome.frame2.util.dom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


/**
 * The DOMStreamConverter provides services for working with DOM nodes and converting them to input
 * and output streams.
 */
final public class DOMStreamConverter {
   /**
    * Hidden constructor for DOMStreamConverter.
    */
   private DOMStreamConverter() {
	   // not public
   }

   /**
    * Takes a DOM node and converts its contents such that they can be read as an input stream.
    *
    * @param node
    *
    * @return InputStream
    *
    * @throws DocumentException
    */
   static public InputStream toInputStream(Node node) throws DocumentException {
      InputStream result = null;

      try {
         result = new ByteArrayInputStream(toByteArrayOutputStream(node).toByteArray());
      } catch (Exception e) {
         throw new DocumentException("Unable to convert node to input stream", e); //$NON-NLS-1$
      }

      return result;
   }

   /**
    * Takes a DOM node and converts its contents such that they can be written or processed as an
    * output stream.
    *
    * @param node
    *
    * @return OutputStream
    *
    * @throws DocumentException
    */
   public static OutputStream toOutputStream(Node node)
      throws DocumentException {
      return toByteArrayOutputStream(node);
   }

   private static ByteArrayOutputStream toByteArrayOutputStream(Node node)
      throws DocumentException {
      ByteArrayOutputStream result = null;

      try {
         result = new ByteArrayOutputStream();
         TransformerFactory.newInstance().newTransformer().transform(new DOMSource(node),
            new StreamResult(result));
      } catch (Exception e) {
         throw new DocumentException("Unable to convert node to output stream", e); //$NON-NLS-1$
      }

      return result;
   }

   /**
    * Convert a string representation of an XML node to a DOM node.
    *
    * @param xml
    *
    * @return Node
    *
    * @throws DocumentException
    */
   public static Node fromString(String xml) throws DocumentException {
      return fromString(xml, false);
   }

   /**
    * Convert a string representation of an XML node to a DOM node, but taking namespaces
    * into account.
    *
    * @param xml 
    * @param nameSpaceAware true if namespaces sgould be accounted for.
    *
    * @return Node
    *
    * @throws DocumentException
    */
   public static Node fromString(String xml, boolean nameSpaceAware)
      throws DocumentException {
      try {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

         factory.setNamespaceAware(nameSpaceAware);

         DocumentBuilder builder = factory.newDocumentBuilder();

         Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));

         return doc.getFirstChild();
      } catch (Exception e) {
         throw new DocumentException("Unable to convert string to node", e); //$NON-NLS-1$
      }
   }

   /**
    * Encode a string to escape with XML entity references.
    *
    * @param data
    *
    * @return String The encoded string
    */
   public static String encode(String data) {
      String result = null;

      if (data != null) {
         StringBuffer buffer = new StringBuffer(data.length() + 64);

         for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);

            switch (c) {
            case '&':
               buffer.append("&amp;"); //$NON-NLS-1$

               break;

            case '<':
               buffer.append("&lt;"); //$NON-NLS-1$

               break;

            case '>':
               buffer.append("&gt;"); //$NON-NLS-1$

               break;

            case '\"':
               buffer.append("&quot;"); //$NON-NLS-1$

               break;

            case '\'':
               buffer.append("&apos;"); //$NON-NLS-1$

               break;

            default:
               buffer.append(c);

               break;
            }
         }

         result = buffer.toString();
      }

      return result;
   }
}
