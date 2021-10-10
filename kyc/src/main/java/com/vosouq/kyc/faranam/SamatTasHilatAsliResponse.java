
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
 *         &lt;element name="SamatTasHilatAsliResult" type="{http://tempuri.org/}EstelamAsliResponse" minOccurs="0"/&gt;
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
    "samatTasHilatAsliResult"
})
@XmlRootElement(name = "SamatTasHilatAsliResponse")
public class SamatTasHilatAsliResponse {

    @XmlElement(name = "SamatTasHilatAsliResult")
    protected EstelamAsliResponse samatTasHilatAsliResult;

    /**
     * Gets the value of the samatTasHilatAsliResult property.
     * 
     * @return
     *     possible object is
     *     {@link EstelamAsliResponse }
     *     
     */
    public EstelamAsliResponse getSamatTasHilatAsliResult() {
        return samatTasHilatAsliResult;
    }

    /**
     * Sets the value of the samatTasHilatAsliResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstelamAsliResponse }
     *     
     */
    public void setSamatTasHilatAsliResult(EstelamAsliResponse value) {
        this.samatTasHilatAsliResult = value;
    }

}
