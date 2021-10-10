
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChqResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChqResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ReturnValue" type="{http://portal.pbn.net}CustomerDTO" minOccurs="0"/&gt;
 *         &lt;element name="HasError" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ErrorMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChqResponse", namespace = "http://portal.pbn.net", propOrder = {
    "returnValue",
    "hasError",
    "errorCode",
    "errorMsg"
})
public class ChqResponse {

    @XmlElement(name = "ReturnValue")
    protected CustomerDTO returnValue;
    @XmlElement(name = "HasError")
    protected boolean hasError;
    @XmlElement(name = "ErrorCode")
    protected int errorCode;
    @XmlElement(name = "ErrorMsg")
    protected String errorMsg;

    /**
     * Gets the value of the returnValue property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDTO }
     *     
     */
    public CustomerDTO getReturnValue() {
        return returnValue;
    }

    /**
     * Sets the value of the returnValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDTO }
     *     
     */
    public void setReturnValue(CustomerDTO value) {
        this.returnValue = value;
    }

    /**
     * Gets the value of the hasError property.
     * 
     */
    public boolean isHasError() {
        return hasError;
    }

    /**
     * Sets the value of the hasError property.
     * 
     */
    public void setHasError(boolean value) {
        this.hasError = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     */
    public void setErrorCode(int value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the errorMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Sets the value of the errorMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMsg(String value) {
        this.errorMsg = value;
    }

}
