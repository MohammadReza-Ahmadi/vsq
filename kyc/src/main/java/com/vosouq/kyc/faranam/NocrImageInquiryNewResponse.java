
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
 *         &lt;element name="NocrImageInquiryNewResult" type="{http://tempuri.org/}NocrModel" minOccurs="0"/&gt;
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
    "nocrImageInquiryNewResult"
})
@XmlRootElement(name = "NocrImageInquiryNewResponse")
public class NocrImageInquiryNewResponse {

    @XmlElement(name = "NocrImageInquiryNewResult")
    protected NocrModel nocrImageInquiryNewResult;

    /**
     * Gets the value of the nocrImageInquiryNewResult property.
     * 
     * @return
     *     possible object is
     *     {@link NocrModel }
     *     
     */
    public NocrModel getNocrImageInquiryNewResult() {
        return nocrImageInquiryNewResult;
    }

    /**
     * Sets the value of the nocrImageInquiryNewResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link NocrModel }
     *     
     */
    public void setNocrImageInquiryNewResult(NocrModel value) {
        this.nocrImageInquiryNewResult = value;
    }

}
