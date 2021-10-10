
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
 *         &lt;element name="NationalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NoSbt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DTSbt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CDSbt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "nationalId",
    "noSbt",
    "dtSbt",
    "cdSbt",
    "userName",
    "password"
})
@XmlRootElement(name = "EstlmEtbLegal")
public class EstlmEtbLegal {

    @XmlElement(name = "NationalId")
    protected String nationalId;
    @XmlElement(name = "NoSbt")
    protected String noSbt;
    @XmlElement(name = "DTSbt")
    protected String dtSbt;
    @XmlElement(name = "CDSbt")
    protected String cdSbt;
    protected String userName;
    protected String password;

    /**
     * Gets the value of the nationalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationalId() {
        return nationalId;
    }

    /**
     * Sets the value of the nationalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationalId(String value) {
        this.nationalId = value;
    }

    /**
     * Gets the value of the noSbt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoSbt() {
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
    public void setNoSbt(String value) {
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
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

}
