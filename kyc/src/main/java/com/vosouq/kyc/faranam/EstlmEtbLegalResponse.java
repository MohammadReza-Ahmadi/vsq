
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
 *         &lt;element name="EstlmEtbLegalResult" type="{http://portal.pbn.net}TasResponse" minOccurs="0"/&gt;
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
    "estlmEtbLegalResult"
})
@XmlRootElement(name = "EstlmEtbLegalResponse")
public class EstlmEtbLegalResponse {

    @XmlElement(name = "EstlmEtbLegalResult")
    protected TasResponse estlmEtbLegalResult;

    /**
     * Gets the value of the estlmEtbLegalResult property.
     * 
     * @return
     *     possible object is
     *     {@link TasResponse }
     *     
     */
    public TasResponse getEstlmEtbLegalResult() {
        return estlmEtbLegalResult;
    }

    /**
     * Sets the value of the estlmEtbLegalResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link TasResponse }
     *     
     */
    public void setEstlmEtbLegalResult(TasResponse value) {
        this.estlmEtbLegalResult = value;
    }

}
