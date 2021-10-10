
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
 *         &lt;element name="AddCheqeTamResult" type="{http://tempuri.org/}AddTamChequeModel" minOccurs="0"/&gt;
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
    "addCheqeTamResult"
})
@XmlRootElement(name = "AddCheqeTamResponse")
public class AddCheqeTamResponse {

    @XmlElement(name = "AddCheqeTamResult")
    protected AddTamChequeModel addCheqeTamResult;

    /**
     * Gets the value of the addCheqeTamResult property.
     * 
     * @return
     *     possible object is
     *     {@link AddTamChequeModel }
     *     
     */
    public AddTamChequeModel getAddCheqeTamResult() {
        return addCheqeTamResult;
    }

    /**
     * Sets the value of the addCheqeTamResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddTamChequeModel }
     *     
     */
    public void setAddCheqeTamResult(AddTamChequeModel value) {
        this.addCheqeTamResult = value;
    }

}
