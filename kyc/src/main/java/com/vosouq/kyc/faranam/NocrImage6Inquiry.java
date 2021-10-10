
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nationalCode" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="numericPartSeri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="alphaPartSeri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="serialId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "username",
    "password",
    "nationalCode",
    "numericPartSeri",
    "alphaPartSeri",
    "serialId"
})
@XmlRootElement(name = "NocrImage6Inquiry")
public class NocrImage6Inquiry {

    protected String username;
    protected String password;
    protected long nationalCode;
    protected String numericPartSeri;
    protected String alphaPartSeri;
    protected String serialId;

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the nationalCode property.
     * 
     */
    public long getNationalCode() {
        return nationalCode;
    }

    /**
     * Sets the value of the nationalCode property.
     * 
     */
    public void setNationalCode(long value) {
        this.nationalCode = value;
    }

    /**
     * Gets the value of the numericPartSeri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumericPartSeri() {
        return numericPartSeri;
    }

    /**
     * Sets the value of the numericPartSeri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumericPartSeri(String value) {
        this.numericPartSeri = value;
    }

    /**
     * Gets the value of the alphaPartSeri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlphaPartSeri() {
        return alphaPartSeri;
    }

    /**
     * Sets the value of the alphaPartSeri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlphaPartSeri(String value) {
        this.alphaPartSeri = value;
    }

    /**
     * Gets the value of the serialId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialId() {
        return serialId;
    }

    /**
     * Sets the value of the serialId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialId(String value) {
        this.serialId = value;
    }

}
