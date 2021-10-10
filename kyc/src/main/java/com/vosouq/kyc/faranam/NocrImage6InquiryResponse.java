
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
 *         &lt;element name="NocrImage6InquiryResult" type="{http://tempuri.org/}NocrModel" minOccurs="0"/&gt;
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
    "nocrImage6InquiryResult"
})
@XmlRootElement(name = "NocrImage6InquiryResponse")
public class NocrImage6InquiryResponse {

    @XmlElement(name = "NocrImage6InquiryResult")
    protected NocrModel nocrImage6InquiryResult;

    /**
     * Gets the value of the nocrImage6InquiryResult property.
     * 
     * @return
     *     possible object is
     *     {@link NocrModel }
     *     
     */
    public NocrModel getNocrImage6InquiryResult() {
        return nocrImage6InquiryResult;
    }

    /**
     * Sets the value of the nocrImage6InquiryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link NocrModel }
     *     
     */
    public void setNocrImage6InquiryResult(NocrModel value) {
        this.nocrImage6InquiryResult = value;
    }

}
