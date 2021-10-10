
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
 *         &lt;element name="GetBlockAccountReportResult" type="{http://tempuri.org/}BARres" minOccurs="0"/&gt;
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
    "getBlockAccountReportResult"
})
@XmlRootElement(name = "GetBlockAccountReportResponse")
public class GetBlockAccountReportResponse {

    @XmlElement(name = "GetBlockAccountReportResult")
    protected BARres getBlockAccountReportResult;

    /**
     * Gets the value of the getBlockAccountReportResult property.
     * 
     * @return
     *     possible object is
     *     {@link BARres }
     *     
     */
    public BARres getGetBlockAccountReportResult() {
        return getBlockAccountReportResult;
    }

    /**
     * Sets the value of the getBlockAccountReportResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link BARres }
     *     
     */
    public void setGetBlockAccountReportResult(BARres value) {
        this.getBlockAccountReportResult = value;
    }

}
