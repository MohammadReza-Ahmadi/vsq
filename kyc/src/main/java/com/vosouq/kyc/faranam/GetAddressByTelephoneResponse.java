
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
 *         &lt;element name="GetAddressByTelephoneResult" type="{http://tempuri.org/}IranStandardAddress" minOccurs="0"/&gt;
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
    "getAddressByTelephoneResult"
})
@XmlRootElement(name = "GetAddressByTelephoneResponse")
public class GetAddressByTelephoneResponse {

    @XmlElement(name = "GetAddressByTelephoneResult")
    protected IranStandardAddress getAddressByTelephoneResult;

    /**
     * Gets the value of the getAddressByTelephoneResult property.
     * 
     * @return
     *     possible object is
     *     {@link IranStandardAddress }
     *     
     */
    public IranStandardAddress getGetAddressByTelephoneResult() {
        return getAddressByTelephoneResult;
    }

    /**
     * Sets the value of the getAddressByTelephoneResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IranStandardAddress }
     *     
     */
    public void setGetAddressByTelephoneResult(IranStandardAddress value) {
        this.getAddressByTelephoneResult = value;
    }

}
