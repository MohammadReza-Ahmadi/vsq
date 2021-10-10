
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
 *         &lt;element name="InquiryFromILENCByNationalIdResult" type="{http://tempuri.org/}ILENCPersonModel" minOccurs="0"/&gt;
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
    "inquiryFromILENCByNationalIdResult"
})
@XmlRootElement(name = "InquiryFromILENCByNationalIdResponse")
public class InquiryFromILENCByNationalIdResponse {

    @XmlElement(name = "InquiryFromILENCByNationalIdResult")
    protected ILENCPersonModel inquiryFromILENCByNationalIdResult;

    /**
     * Gets the value of the inquiryFromILENCByNationalIdResult property.
     * 
     * @return
     *     possible object is
     *     {@link ILENCPersonModel }
     *     
     */
    public ILENCPersonModel getInquiryFromILENCByNationalIdResult() {
        return inquiryFromILENCByNationalIdResult;
    }

    /**
     * Sets the value of the inquiryFromILENCByNationalIdResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ILENCPersonModel }
     *     
     */
    public void setInquiryFromILENCByNationalIdResult(ILENCPersonModel value) {
        this.inquiryFromILENCByNationalIdResult = value;
    }

}
