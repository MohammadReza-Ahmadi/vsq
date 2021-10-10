
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
 *         &lt;element name="InquiryFromNOCRmellatEstelamDBResult" type="{http://tempuri.org/}NOCRPersonModel" minOccurs="0"/&gt;
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
    "inquiryFromNOCRmellatEstelamDBResult"
})
@XmlRootElement(name = "InquiryFromNOCRmellatEstelamDBResponse")
public class InquiryFromNOCRmellatEstelamDBResponse {

    @XmlElement(name = "InquiryFromNOCRmellatEstelamDBResult")
    protected NOCRPersonModel inquiryFromNOCRmellatEstelamDBResult;

    /**
     * Gets the value of the inquiryFromNOCRmellatEstelamDBResult property.
     * 
     * @return
     *     possible object is
     *     {@link NOCRPersonModel }
     *     
     */
    public NOCRPersonModel getInquiryFromNOCRmellatEstelamDBResult() {
        return inquiryFromNOCRmellatEstelamDBResult;
    }

    /**
     * Sets the value of the inquiryFromNOCRmellatEstelamDBResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link NOCRPersonModel }
     *     
     */
    public void setInquiryFromNOCRmellatEstelamDBResult(NOCRPersonModel value) {
        this.inquiryFromNOCRmellatEstelamDBResult = value;
    }

}
