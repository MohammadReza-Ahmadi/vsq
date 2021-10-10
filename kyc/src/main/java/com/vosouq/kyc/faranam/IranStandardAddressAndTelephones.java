
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IranStandardAddressAndTelephones complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IranStandardAddressAndTelephones"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://tempuri.org/}IranStandardAddress"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TelephoneNumbers" type="{http://tempuri.org/}ArrayOfTelephoneNumber" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IranStandardAddressAndTelephones", propOrder = {
    "telephoneNumbers"
})
public class IranStandardAddressAndTelephones
    extends IranStandardAddress
{

    @XmlElement(name = "TelephoneNumbers")
    protected ArrayOfTelephoneNumber telephoneNumbers;

    /**
     * Gets the value of the telephoneNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTelephoneNumber }
     *     
     */
    public ArrayOfTelephoneNumber getTelephoneNumbers() {
        return telephoneNumbers;
    }

    /**
     * Sets the value of the telephoneNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTelephoneNumber }
     *     
     */
    public void setTelephoneNumbers(ArrayOfTelephoneNumber value) {
        this.telephoneNumbers = value;
    }

}
