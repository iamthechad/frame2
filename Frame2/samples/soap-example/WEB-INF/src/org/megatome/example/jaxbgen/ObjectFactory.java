//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.2-b15-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.05.06 at 12:44:58 MDT 
//


package org.megatome.example.jaxbgen;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.megatome.example.jaxbgen package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
public class ObjectFactory
    extends org.megatome.example.jaxbgen.impl.runtime.DefaultJAXBContextImpl
{

    private static java.util.HashMap defaultImplementations = new java.util.HashMap();
    private static java.util.HashMap rootTagMap = new java.util.HashMap();
    public final static org.megatome.example.jaxbgen.impl.runtime.GrammarInfo grammarInfo = new org.megatome.example.jaxbgen.impl.runtime.GrammarInfoImpl(rootTagMap, defaultImplementations, (org.megatome.example.jaxbgen.ObjectFactory.class));
    public final static java.lang.Class version = (org.megatome.example.jaxbgen.impl.JAXBVersion.class);

    static {
        defaultImplementations.put((org.megatome.example.jaxbgen.User.class), "org.megatome.example.jaxbgen.impl.UserImpl");
        defaultImplementations.put((org.megatome.example.jaxbgen.NACK.class), "org.megatome.example.jaxbgen.impl.NACKImpl");
        defaultImplementations.put((org.megatome.example.jaxbgen.UserType.class), "org.megatome.example.jaxbgen.impl.UserTypeImpl");
        defaultImplementations.put((org.megatome.example.jaxbgen.ACK.class), "org.megatome.example.jaxbgen.impl.ACKImpl");
        rootTagMap.put(new javax.xml.namespace.QName("", "NACK"), (org.megatome.example.jaxbgen.NACK.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "user"), (org.megatome.example.jaxbgen.User.class));
        rootTagMap.put(new javax.xml.namespace.QName("", "ACK"), (org.megatome.example.jaxbgen.ACK.class));
    }

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.megatome.example.jaxbgen
     * 
     */
    public ObjectFactory() {
        super(grammarInfo);
    }

    /**
     * Create an instance of the specified Java content interface.
     * 
     * @param javaContentInterface
     *     the Class object of the javacontent interface to instantiate
     * @return
     *     a new instance
     * @throws JAXBException
     *     if an error occurs
     */
    public java.lang.Object newInstance(java.lang.Class javaContentInterface)
        throws javax.xml.bind.JAXBException
    {
        return super.newInstance(javaContentInterface);
    }

    /**
     * Get the specified property. This method can only be
     * used to get provider specific properties.
     * Attempting to get an undefined property will result
     * in a PropertyException being thrown.
     * 
     * @param name
     *     the name of the property to retrieve
     * @return
     *     the value of the requested property
     * @throws PropertyException
     *     when there is an error retrieving the given property or value
     */
    public java.lang.Object getProperty(java.lang.String name)
        throws javax.xml.bind.PropertyException
    {
        return super.getProperty(name);
    }

    /**
     * Set the specified property. This method can only be
     * used to set provider specific properties.
     * Attempting to set an undefined property will result
     * in a PropertyException being thrown.
     * 
     * @param value
     *     the value of the property to be set
     * @param name
     *     the name of the property to retrieve
     * @throws PropertyException
     *     when there is an error processing the given property or value
     */
    public void setProperty(java.lang.String name, java.lang.Object value)
        throws javax.xml.bind.PropertyException
    {
        super.setProperty(name, value);
    }

    /**
     * Create an instance of User
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public org.megatome.example.jaxbgen.User createUser()
        throws javax.xml.bind.JAXBException
    {
        return new org.megatome.example.jaxbgen.impl.UserImpl();
    }

    /**
     * Create an instance of NACK
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public org.megatome.example.jaxbgen.NACK createNACK()
        throws javax.xml.bind.JAXBException
    {
        return new org.megatome.example.jaxbgen.impl.NACKImpl();
    }

    /**
     * Create an instance of NACK
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public org.megatome.example.jaxbgen.NACK createNACK(java.lang.String value)
        throws javax.xml.bind.JAXBException
    {
        return new org.megatome.example.jaxbgen.impl.NACKImpl(value);
    }

    /**
     * Create an instance of UserType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public org.megatome.example.jaxbgen.UserType createUserType()
        throws javax.xml.bind.JAXBException
    {
        return new org.megatome.example.jaxbgen.impl.UserTypeImpl();
    }

    /**
     * Create an instance of ACK
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public org.megatome.example.jaxbgen.ACK createACK()
        throws javax.xml.bind.JAXBException
    {
        return new org.megatome.example.jaxbgen.impl.ACKImpl();
    }

    /**
     * Create an instance of ACK
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public org.megatome.example.jaxbgen.ACK createACK(java.lang.String value)
        throws javax.xml.bind.JAXBException
    {
        return new org.megatome.example.jaxbgen.impl.ACKImpl(value);
    }

}