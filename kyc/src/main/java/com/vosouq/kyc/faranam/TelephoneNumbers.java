
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TelephoneNumbers complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TelephoneNumbers"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://tempuri.org/}Result"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Telephones" type="{http://tempuri.org/}ArrayOfTelephoneNumber" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TelephoneNumbers", propOrder = {
    "telephones"
})
public class TelephoneNumbers
    extends Result
{

    @XmlElement(name = "Telephones")
    protected ArrayOfTelephoneNumber telephones;

    /**
     * Gets the value of the telephones property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTelephoneNumber }
     *     
     */
    public ArrayOfTelephoneNumber getTelephones() {
        return telephones;
    }

    /**
     * Sets the value of the telephones property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTelephoneNumber }
     *     
     */
    public void setTelephones(ArrayOfTelephoneNumber value) {
        this.telephones = value;
    }

}
