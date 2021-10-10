
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
 *         &lt;element name="GetAddressAndTelephoneByPostcodeResult" type="{http://tempuri.org/}IranStandardAddressAndTelephones" minOccurs="0"/&gt;
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
    "getAddressAndTelephoneByPostcodeResult"
})
@XmlRootElement(name = "GetAddressAndTelephoneByPostcodeResponse")
public class GetAddressAndTelephoneByPostcodeResponse {

    @XmlElement(name = "GetAddressAndTelephoneByPostcodeResult")
    protected IranStandardAddressAndTelephones getAddressAndTelephoneByPostcodeResult;

    /**
     * Gets the value of the getAddressAndTelephoneByPostcodeResult property.
     * 
     * @return
     *     possible object is
     *     {@link IranStandardAddressAndTelephones }
     *     
     */
    public IranStandardAddressAndTelephones getGetAddressAndTelephoneByPostcodeResult() {
        return getAddressAndTelephoneByPostcodeResult;
    }

    /**
     * Sets the value of the getAddressAndTelephoneByPostcodeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IranStandardAddressAndTelephones }
     *     
     */
    public void setGetAddressAndTelephoneByPostcodeResult(IranStandardAddressAndTelephones value) {
        this.getAddressAndTelephoneByPostcodeResult = value;
    }

}
