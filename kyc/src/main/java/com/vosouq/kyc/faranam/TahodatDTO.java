
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TahodatDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TahodatDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BnkCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ShbCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BnkShb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RequstNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RequstType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ArzCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ArzRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmTahodST" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmBedehkar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmMashkuk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmBedehiKol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TahodatDTO", namespace = "http://portal.pbn.net", propOrder = {
    "bnkCD",
    "shbCD",
    "bnkShb",
    "requstNO",
    "requstType",
    "arzCD",
    "arzRate",
    "amTahodST",
    "amBedehkar",
    "amMashkuk",
    "amBedehiKol",
    "type",
    "status"
})
public class TahodatDTO {

    @XmlElement(name = "BnkCD")
    protected String bnkCD;
    @XmlElement(name = "ShbCD")
    protected String shbCD;
    @XmlElement(name = "BnkShb")
    protected String bnkShb;
    @XmlElement(name = "RequstNO")
    protected String requstNO;
    @XmlElement(name = "RequstType")
    protected String requstType;
    @XmlElement(name = "ArzCD")
    protected String arzCD;
    @XmlElement(name = "ArzRate")
    protected String arzRate;
    @XmlElement(name = "AmTahodST")
    protected String amTahodST;
    @XmlElement(name = "AmBedehkar")
    protected String amBedehkar;
    @XmlElement(name = "AmMashkuk")
    protected String amMashkuk;
    @XmlElement(name = "AmBedehiKol")
    protected String amBedehiKol;
    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "Status")
    protected String status;

    /**
     * Gets the value of the bnkCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBnkCD() {
        return bnkCD;
    }

    /**
     * Sets the value of the bnkCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBnkCD(String value) {
        this.bnkCD = value;
    }

    /**
     * Gets the value of the shbCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShbCD() {
        return shbCD;
    }

    /**
     * Sets the value of the shbCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShbCD(String value) {
        this.shbCD = value;
    }

    /**
     * Gets the value of the bnkShb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBnkShb() {
        return bnkShb;
    }

    /**
     * Sets the value of the bnkShb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBnkShb(String value) {
        this.bnkShb = value;
    }

    /**
     * Gets the value of the requstNO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequstNO() {
        return requstNO;
    }

    /**
     * Sets the value of the requstNO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequstNO(String value) {
        this.requstNO = value;
    }

    /**
     * Gets the value of the requstType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequstType() {
        return requstType;
    }

    /**
     * Sets the value of the requstType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequstType(String value) {
        this.requstType = value;
    }

    /**
     * Gets the value of the arzCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArzCD() {
        return arzCD;
    }

    /**
     * Sets the value of the arzCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArzCD(String value) {
        this.arzCD = value;
    }

    /**
     * Gets the value of the arzRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArzRate() {
        return arzRate;
    }

    /**
     * Sets the value of the arzRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArzRate(String value) {
        this.arzRate = value;
    }

    /**
     * Gets the value of the amTahodST property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmTahodST() {
        return amTahodST;
    }

    /**
     * Sets the value of the amTahodST property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmTahodST(String value) {
        this.amTahodST = value;
    }

    /**
     * Gets the value of the amBedehkar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmBedehkar() {
        return amBedehkar;
    }

    /**
     * Sets the value of the amBedehkar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmBedehkar(String value) {
        this.amBedehkar = value;
    }

    /**
     * Gets the value of the amMashkuk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmMashkuk() {
        return amMashkuk;
    }

    /**
     * Sets the value of the amMashkuk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmMashkuk(String value) {
        this.amMashkuk = value;
    }

    /**
     * Gets the value of the amBedehiKol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmBedehiKol() {
        return amBedehiKol;
    }

    /**
     * Sets the value of the amBedehiKol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmBedehiKol(String value) {
        this.amBedehiKol = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
