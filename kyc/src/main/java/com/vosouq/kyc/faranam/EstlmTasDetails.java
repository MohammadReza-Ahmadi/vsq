
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="cstmrTp" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *         &lt;element name="idNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dtSbt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="noSbt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cdSbt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "cstmrTp",
    "idNo",
    "dtSbt",
    "noSbt",
    "cdSbt",
    "userName",
    "password"
})
@XmlRootElement(name = "EstlmTasDetails")
public class EstlmTasDetails {

    @XmlSchemaType(name = "unsignedByte")
    protected short cstmrTp;
    protected String idNo;
    protected String dtSbt;
    protected String noSbt;
    protected String cdSbt;
    protected String userName;
    protected String password;

    /**
     * Gets the value of the cstmrTp property.
     * 
     */
    public short getCstmrTp() {
        return cstmrTp;
    }

    /**
     * Sets the value of the cstmrTp property.
     * 
     */
    public void setCstmrTp(short value) {
        this.cstmrTp = value;
    }

    /**
     * Gets the value of the idNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * Sets the value of the idNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdNo(String value) {
        this.idNo = value;
    }

    /**
     * Gets the value of the dtSbt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtSbt() {
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
    public void setDtSbt(String value) {
        this.dtSbt = value;
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
     * Gets the value of the cdSbt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCdSbt() {
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
    public void setCdSbt(String value) {
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
