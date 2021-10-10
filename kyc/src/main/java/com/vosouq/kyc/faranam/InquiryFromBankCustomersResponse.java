
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
 *         &lt;element name="InquiryFromBankCustomersResult" type="{http://tempuri.org/}BankCustomerModel" minOccurs="0"/&gt;
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
    "inquiryFromBankCustomersResult"
})
@XmlRootElement(name = "InquiryFromBankCustomersResponse")
public class InquiryFromBankCustomersResponse {

    @XmlElement(name = "InquiryFromBankCustomersResult")
    protected BankCustomerModel inquiryFromBankCustomersResult;

    /**
     * Gets the value of the inquiryFromBankCustomersResult property.
     * 
     * @return
     *     possible object is
     *     {@link BankCustomerModel }
     *     
     */
    public BankCustomerModel getInquiryFromBankCustomersResult() {
        return inquiryFromBankCustomersResult;
    }

    /**
     * Sets the value of the inquiryFromBankCustomersResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankCustomerModel }
     *     
     */
    public void setInquiryFromBankCustomersResult(BankCustomerModel value) {
        this.inquiryFromBankCustomersResult = value;
    }

}
