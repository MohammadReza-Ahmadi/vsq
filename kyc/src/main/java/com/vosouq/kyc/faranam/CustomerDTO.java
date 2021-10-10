
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IDNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NOSbt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DTSbt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CDSbt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ChequeItem" type="{http://portal.pbn.net}ArrayOfChequeDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerDTO", namespace = "http://portal.pbn.net", propOrder = {
    "idno",
    "name",
    "noSbt",
    "dtSbt",
    "cdSbt",
    "chequeItem"
})
public class CustomerDTO {

    @XmlElement(name = "IDNO")
    protected String idno;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "NOSbt")
    protected String noSbt;
    @XmlElement(name = "DTSbt")
    protected String dtSbt;
    @XmlElement(name = "CDSbt")
    protected String cdSbt;
    @XmlElement(name = "ChequeItem")
    protected ArrayOfChequeDTO chequeItem;

    /**
     * Gets the value of the idno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDNO() {
        return idno;
    }

    /**
     * Sets the value of the idno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDNO(String value) {
        this.idno = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the noSbt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOSbt() {
        return noSbt;
    }

    /**
     * Sets the value of the noSbt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOSbt(String value) {
        this.noSbt = value;
    }

    /**
     * Gets the value of the dtSbt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDTSbt() {
        return dtSbt;
    }

    /**
     * Sets the value of the dtSbt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDTSbt(String value) {
        this.dtSbt = value;
    }

    /**
     * Gets the value of the cdSbt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCDSbt() {
        return cdSbt;
    }

    /**
     * Sets the value of the cdSbt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCDSbt(String value) {
        this.cdSbt = value;
    }

    /**
     * Gets the value of the chequeItem property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChequeDTO }
     *     
     */
    public ArrayOfChequeDTO getChequeItem() {
        return chequeItem;
    }

    /**
     * Sets the value of the chequeItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChequeDTO }
     *     
     */
    public void setChequeItem(ArrayOfChequeDTO value) {
        this.chequeItem = value;
    }

}
