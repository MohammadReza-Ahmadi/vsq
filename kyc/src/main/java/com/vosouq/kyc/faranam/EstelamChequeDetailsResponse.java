
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
 *         &lt;element name="EstelamChequeDetailsResult" type="{http://portal.pbn.net}ChqResponse" minOccurs="0"/&gt;
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
    "estelamChequeDetailsResult"
})
@XmlRootElement(name = "EstelamChequeDetailsResponse")
public class EstelamChequeDetailsResponse {

    @XmlElement(name = "EstelamChequeDetailsResult")
    protected ChqResponse estelamChequeDetailsResult;

    /**
     * Gets the value of the estelamChequeDetailsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ChqResponse }
     *     
     */
    public ChqResponse getEstelamChequeDetailsResult() {
        return estelamChequeDetailsResult;
    }

    /**
     * Sets the value of the estelamChequeDetailsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChqResponse }
     *     
     */
    public void setEstelamChequeDetailsResult(ChqResponse value) {
        this.estelamChequeDetailsResult = value;
    }

}
