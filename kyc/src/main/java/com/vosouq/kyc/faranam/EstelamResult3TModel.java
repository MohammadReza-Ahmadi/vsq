
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for estelamResult3TModel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="estelamResult3TModel"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="estelamResult3T" type="{http://est}estelamResult3T" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estelamResult3TModel", propOrder = {
    "estelamResult3T",
    "status",
    "message"
})
public class EstelamResult3TModel {

    protected EstelamResult3T estelamResult3T;
    @XmlElement(name = "Status")
    protected int status;
    @XmlElement(name = "Message")
    protected String message;

    /**
     * Gets the value of the estelamResult3T property.
     * 
     * @return
     *     possible object is
     *     {@link EstelamResult3T }
     *     
     */
    public EstelamResult3T getEstelamResult3T() {
        return estelamResult3T;
    }

    /**
     * Sets the value of the estelamResult3T property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstelamResult3T }
     *     
     */
    public void setEstelamResult3T(EstelamResult3T value) {
        this.estelamResult3T = value;
    }

    /**
     * Gets the value of the status property.
     * 
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     */
    public void setStatus(int value) {
        this.status = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

}
