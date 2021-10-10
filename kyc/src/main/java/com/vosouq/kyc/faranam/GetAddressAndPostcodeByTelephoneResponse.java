
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
 *         &lt;element name="GetAddressAndPostcodeByTelephoneResult" type="{http://tempuri.org/}IranStandardAddress" minOccurs="0"/&gt;
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
    "getAddressAndPostcodeByTelephoneResult"
})
@XmlRootElement(name = "GetAddressAndPostcodeByTelephoneResponse")
public class GetAddressAndPostcodeByTelephoneResponse {

    @XmlElement(name = "GetAddressAndPostcodeByTelephoneResult")
    protected IranStandardAddress getAddressAndPostcodeByTelephoneResult;

    /**
     * Gets the value of the getAddressAndPostcodeByTelephoneResult property.
     * 
     * @return
     *     possible object is
     *     {@link IranStandardAddress }
     *     
     */
    public IranStandardAddress getGetAddressAndPostcodeByTelephoneResult() {
        return getAddressAndPostcodeByTelephoneResult;
    }

    /**
     * Sets the value of the getAddressAndPostcodeByTelephoneResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IranStandardAddress }
     *     
     */
    public void setGetAddressAndPostcodeByTelephoneResult(IranStandardAddress value) {
        this.getAddressAndPostcodeByTelephoneResult = value;
    }

}
