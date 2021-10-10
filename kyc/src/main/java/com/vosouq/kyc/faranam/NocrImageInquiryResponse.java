
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
 *         &lt;element name="NocrImageInquiryResult" type="{http://tempuri.org/}NocrModel" minOccurs="0"/&gt;
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
    "nocrImageInquiryResult"
})
@XmlRootElement(name = "NocrImageInquiryResponse")
public class NocrImageInquiryResponse {

    @XmlElement(name = "NocrImageInquiryResult")
    protected NocrModel nocrImageInquiryResult;

    /**
     * Gets the value of the nocrImageInquiryResult property.
     * 
     * @return
     *     possible object is
     *     {@link NocrModel }
     *     
     */
    public NocrModel getNocrImageInquiryResult() {
        return nocrImageInquiryResult;
    }

    /**
     * Sets the value of the nocrImageInquiryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link NocrModel }
     *     
     */
    public void setNocrImageInquiryResult(NocrModel value) {
        this.nocrImageInquiryResult = value;
    }

}
