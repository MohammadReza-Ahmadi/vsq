
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="estlmId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cstmrType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cstmrShenase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cstmrName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cstmrLName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "userName",
    "password",
    "estlmId",
    "cstmrType",
    "cstmrShenase",
    "cstmrName",
    "cstmrLName"
})
@XmlRootElement(name = "SamatTasHilatAsli")
public class SamatTasHilatAsli {

    protected String userName;
    protected String password;
    protected String estlmId;
    protected String cstmrType;
    protected String cstmrShenase;
    protected String cstmrName;
    protected String cstmrLName;

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

    /**
     * Gets the value of the estlmId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstlmId() {
        return estlmId;
    }

    /**
     * Sets the value of the estlmId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstlmId(String value) {
        this.estlmId = value;
    }

    /**
     * Gets the value of the cstmrType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCstmrType() {
        return cstmrType;
    }

    /**
     * Sets the value of the cstmrType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCstmrType(String value) {
        this.cstmrType = value;
    }

    /**
     * Gets the value of the cstmrShenase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCstmrShenase() {
        return cstmrShenase;
    }

    /**
     * Sets the value of the cstmrShenase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCstmrShenase(String value) {
        this.cstmrShenase = value;
    }

    /**
     * Gets the value of the cstmrName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCstmrName() {
        return cstmrName;
    }

    /**
     * Sets the value of the cstmrName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCstmrName(String value) {
        this.cstmrName = value;
    }

    /**
     * Gets the value of the cstmrLName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCstmrLName() {
        return cstmrLName;
    }

    /**
     * Sets the value of the cstmrLName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCstmrLName(String value) {
        this.cstmrLName = value;
    }

}
