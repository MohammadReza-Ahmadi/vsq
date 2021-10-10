
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
 *         &lt;element name="NocrGetEstelam3TResult" type="{http://tempuri.org/}estelamResult3TModel" minOccurs="0"/&gt;
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
    "nocrGetEstelam3TResult"
})
@XmlRootElement(name = "NocrGetEstelam3TResponse")
public class NocrGetEstelam3TResponse {

    @XmlElement(name = "NocrGetEstelam3TResult")
    protected EstelamResult3TModel nocrGetEstelam3TResult;

    /**
     * Gets the value of the nocrGetEstelam3TResult property.
     * 
     * @return
     *     possible object is
     *     {@link EstelamResult3TModel }
     *     
     */
    public EstelamResult3TModel getNocrGetEstelam3TResult() {
        return nocrGetEstelam3TResult;
    }

    /**
     * Sets the value of the nocrGetEstelam3TResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstelamResult3TModel }
     *     
     */
    public void setNocrGetEstelam3TResult(EstelamResult3TModel value) {
        this.nocrGetEstelam3TResult = value;
    }

}
