
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
 *         &lt;element name="NocrImageInquiry_testResult" type="{http://tempuri.org/}NocrModel" minOccurs="0"/&gt;
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
    "nocrImageInquiryTestResult"
})
@XmlRootElement(name = "NocrImageInquiry_testResponse")
public class NocrImageInquiryTestResponse {

    @XmlElement(name = "NocrImageInquiry_testResult")
    protected NocrModel nocrImageInquiryTestResult;

    /**
     * Gets the value of the nocrImageInquiryTestResult property.
     * 
     * @return
     *     possible object is
     *     {@link NocrModel }
     *     
     */
    public NocrModel getNocrImageInquiryTestResult() {
        return nocrImageInquiryTestResult;
    }

    /**
     * Sets the value of the nocrImageInquiryTestResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link NocrModel }
     *     
     */
    public void setNocrImageInquiryTestResult(NocrModel value) {
        this.nocrImageInquiryTestResult = value;
    }

}
