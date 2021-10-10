
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
 *         &lt;element name="GetTelephonesByPostcodeResult" type="{http://tempuri.org/}TelephoneNumbers" minOccurs="0"/&gt;
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
    "getTelephonesByPostcodeResult"
})
@XmlRootElement(name = "GetTelephonesByPostcodeResponse")
public class GetTelephonesByPostcodeResponse {

    @XmlElement(name = "GetTelephonesByPostcodeResult")
    protected TelephoneNumbers getTelephonesByPostcodeResult;

    /**
     * Gets the value of the getTelephonesByPostcodeResult property.
     * 
     * @return
     *     possible object is
     *     {@link TelephoneNumbers }
     *     
     */
    public TelephoneNumbers getGetTelephonesByPostcodeResult() {
        return getTelephonesByPostcodeResult;
    }

    /**
     * Sets the value of the getTelephonesByPostcodeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelephoneNumbers }
     *     
     */
    public void setGetTelephonesByPostcodeResult(TelephoneNumbers value) {
        this.getTelephonesByPostcodeResult = value;
    }

}
