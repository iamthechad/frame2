/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004 Megatome Technologies.  All rights
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
 package org.megatome.frame2.front;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.megatome.frame2.front.config.RequestProcessorDef;
import org.w3c.dom.Element;

/**
 * The RequestProcessorFactory provides singleton instances of RequestProcessors. The
 * implementation is determined by the request type (HTML client, SOAP, etc.).
 */
final public class RequestProcessorFactory {
   public static final String RP_TYPE =
      "org.megatome.frame2.front.RequestProcessor";
   private RequestProcessorFactory() {
   }

   private static Class createClass(String className) {
      Class c = null;
      try {
         c = Class.forName(className);
      } catch (ClassNotFoundException e) {
         // Eat exception, return null.
         c = null;
      } /*finally {
         return c;
      }*/
      
      return c;
   }
   
   private static Class[] generateParamTypes( String[] classNames) {
      List paramTypes = new ArrayList();
      
      try {
         for (int i = 0; i < classNames.length; i++) {
            paramTypes.add(Class.forName(classNames[i]));
      
         }
      } catch (ClassNotFoundException e) {
         return null;
      }
      
      Class[] classParamTypes =
         (Class[]) paramTypes.toArray(new Class[paramTypes.size()]);
      return classParamTypes;
   }

   private static RequestProcessor buildRequestProcessor(
      Class c,
      Object[] objects,
      Class[] paramTypes) {
      RequestProcessor reqProc = null;
      Constructor constructor;
      try {
         constructor = c.getDeclaredConstructor(paramTypes);
      } catch (NoSuchMethodException e) {
         return null;

      }

      try {
         reqProc = (RequestProcessor) constructor.newInstance(objects);

         if (!Class.forName(RP_TYPE).isInstance(reqProc)) {
            return null;
         }
      } catch (InstantiationException e) {
         return null;

      } catch (IllegalAccessException e) {
         return null;

      } catch (InvocationTargetException e) {
         return null;

      } catch (ClassNotFoundException e) {
         return null;
      }

      return reqProc;
   }

   /**
    * Return an instance of RequestProcessor.  The instance is not  assumed to be thread safe and
    * should not be shared between threads.  The client is responsible for calling the instance's
    * <code>release()</code> method when done with it.
    *
    * @param config
    * @param context
    * @param request
    * @param response
    *
    * @return RequestProcessor
    */
   static public RequestProcessor instance(
      Configuration config,
      ServletContext context,
      HttpServletRequest request,
      HttpServletResponse response) {

      RequestProcessorDef requestProcessorDef =
         config.getHttpRequestProcessor();
      if (requestProcessorDef != null) {

         Class c = createClass(requestProcessorDef.getType());
         if (c == null) {
            return null;
         }

         Object[] objects = { config, context, request, response };
        
         String[] classNames =
            {
               "org.megatome.frame2.front.Configuration",
               "javax.servlet.ServletContext",
               "javax.servlet.http.HttpServletRequest",
               "javax.servlet.http.HttpServletResponse" };
               
         Class[] classParamTypes = generateParamTypes(classNames);
         
         if (classParamTypes == null) {
            return null;
         }
                  
         return buildRequestProcessor(c, objects, classParamTypes);

      } else
         return new HttpRequestProcessor(config, context, request, response);
   }

   static public RequestProcessor instance(
      Configuration config,
      Element[] elements,
      String eventPkg) {

      RequestProcessorDef requestProcessorDef = config.getSoapRequestProcessor();
      if (requestProcessorDef != null) {

         Class c = createClass(requestProcessorDef.getType());
         if (c == null) {
            return null;
         }

         Object[] objects = { config, elements, eventPkg};
         String[] classNames =
         {
            "org.megatome.frame2.front.Configuration",            
            "[Lorg.w3c.dom.Element;",  // This of course is intuitive, but... This is the way to specify an array of org.w3c.dom.Element.   Look at javadoc 
            "java.lang.String"};
                          
         Class[] classParamTypes = generateParamTypes(classNames);
         
         if (classParamTypes == null) {
            return null;
         }
         
         return buildRequestProcessor(c, objects, classParamTypes);

      } else
         return new SoapRequestProcessor(config, elements, eventPkg);
   }

   
}
