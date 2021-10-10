
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="GetPostcodeByTelephoneResult" type="{http://tempuri.org/}Postcode" minOccurs="0"/&gt;
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
    "getPostcodeByTelephoneResult"
})
@XmlRootElement(name = "GetPostcodeByTelephoneResponse")
public class GetPostcodeByTelephoneResponse {

    @XmlElement(name = "GetPostcodeByTelephoneResult")
    protected Postcode getPostcodeByTelephoneResult;

    /**
     * Gets the value of the getPostcodeByTelephoneResult property.
     * 
     * @return
     *     possible object is
     *     {@link Postcode }
     *     
     */
    public Postcode getGetPostcodeByTelephoneResult() {
        return getPostcodeByTelephoneResult;
    }

    /**
     * Sets the value of the getPostcodeByTelephoneResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Postcode }
     *     
     */
    public void setGetPostcodeByTelephoneResult(Postcode value) {
        this.getPostcodeByTelephoneResult = value;
    }

}
