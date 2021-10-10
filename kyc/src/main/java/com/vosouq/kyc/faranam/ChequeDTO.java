
package com.vosouq.kyc.faranam;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChequeDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChequeDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IDCHQ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CDBNK" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CDSHB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ACCNTNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NOCHQ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AMCHQ" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="DTCHQ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BCKDTCHQ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CDARZ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CONVRATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChequeDTO", namespace = "http://portal.pbn.net", propOrder = {
    "idchq",
    "cdbnk",
    "cdshb",
    "desc",
    "accntno",
    "nochq",
    "amchq",
    "dtchq",
    "bckdtchq",
    "cdarz",
    "convrate"
})
public class ChequeDTO {

    @XmlElement(name = "IDCHQ")
    protected String idchq;
    @XmlElement(name = "CDBNK")
    protected String cdbnk;
    @XmlElement(name = "CDSHB")
    protected String cdshb;
    @XmlElement(name = "DESC")
    protected String desc;
    @XmlElement(name = "ACCNTNO")
    protected String accntno;
    @XmlElement(name = "NOCHQ")
    protected String nochq;
    @XmlElement(name = "AMCHQ", required = true)
    protected BigDecimal amchq;
    @XmlElement(name = "DTCHQ")
    protected String dtchq;
    @XmlElement(name = "BCKDTCHQ")
    protected String bckdtchq;
    @XmlElement(name = "CDARZ")
    protected String cdarz;
    @XmlElement(name = "CONVRATE")
    protected String convrate;

    /**
     * Gets the value of the idchq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCHQ() {
        return idchq;
    }

    /**
     * Sets the value of the idchq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCHQ(String value) {
        this.idchq = value;
    }

    /**
     * Gets the value of the cdbnk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCDBNK() {
        return cdbnk;
    }

    /**
     * Sets the value of the cdbnk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCDBNK(String value) {
        this.cdbnk = value;
    }

    /**
     * Gets the value of the cdshb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCDSHB() {
        return cdshb;
    }

    /**
     * Sets the value of the cdshb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCDSHB(String value) {
        this.cdshb = value;
    }

    /**
     * Gets the value of the desc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESC() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESC(String value) {
        this.desc = value;
    }

    /**
     * Gets the value of the accntno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACCNTNO() {
        return accntno;
    }

    /**
     * Sets the value of the accntno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACCNTNO(String value) {
        this.accntno = value;
    }

    /**
     * Gets the value of the nochq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOCHQ() {
        return nochq;
    }

    /**
     * Sets the value of the nochq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOCHQ(String value) {
        this.nochq = value;
    }

    /**
     * Gets the value of the amchq property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAMCHQ() {
        return amchq;
    }

    /**
     * Sets the value of the amchq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAMCHQ(BigDecimal value) {
        this.amchq = value;
    }

    /**
     * Gets the value of the dtchq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDTCHQ() {
        return dtchq;
    }

    /**
     * Sets the value of the dtchq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDTCHQ(String value) {
        this.dtchq = value;
    }

    /**
     * Gets the value of the bckdtchq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBCKDTCHQ() {
        return bckdtchq;
    }

    /**
     * Sets the value of the bckdtchq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBCKDTCHQ(String value) {
        this.bckdtchq = value;
    }

    /**
     * Gets the value of the cdarz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCDARZ() {
        return cdarz;
    }

    /**
     * Sets the value of the cdarz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCDARZ(String value) {
        this.cdarz = value;
    }

    /**
     * Gets the value of the convrate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONVRATE() {
        return convrate;
    }

    /**
     * Sets the value of the convrate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONVRATE(String value) {
        this.convrate = value;
    }

}
