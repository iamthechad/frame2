//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2003.05.09 at 09:13:58 MDT 
//


package org.megatome.app.jaxbgen;


/**
 * Java content class for user element declaration.
 *  <p>The following schema fragment specifies the expected content contained within this java content object.
 * <p>
 * <pre>
 * &lt;element name="user">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element ref="{}comment" minOccurs="0"/>
 *           &lt;element name="email" type="{}EmailType"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface User
    extends javax.xml.bind.Element, org.megatome.app.jaxbgen.UserType
{


}