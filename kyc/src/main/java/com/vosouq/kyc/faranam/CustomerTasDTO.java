
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerTasDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerTasDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IDNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SabtNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SabtDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SabtCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TashilatItem" type="{http://portal.pbn.net}ArrayOfTashilatDTO" minOccurs="0"/&gt;
 *         &lt;element name="TahodatItem" type="{http://portal.pbn.net}ArrayOfTahodatDTO" minOccurs="0"/&gt;
 *         &lt;element name="TotalAmTashilat" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="TotalAmBedehi" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="TotalAmTahodST" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="TotalAmSarResid" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="TotalAmMoavagh" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="TotalAmMashkuk" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="BadHesabiDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerTasDTO", namespace = "http://portal.pbn.net", propOrder = {
    "idno",
    "name",
    "lName",
    "sabtNO",
    "sabtDT",
    "sabtCD",
    "tashilatItem",
    "tahodatItem",
    "totalAmTashilat",
    "totalAmBedehi",
    "totalAmTahodST",
    "totalAmSarResid",
    "totalAmMoavagh",
    "totalAmMashkuk",
    "badHesabiDT"
})
public class CustomerTasDTO {

    @XmlElement(name = "IDNO")
    protected String idno;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "LName")
    protected String lName;
    @XmlElement(name = "SabtNO")
    protected String sabtNO;
    @XmlElement(name = "SabtDT")
    protected String sabtDT;
    @XmlElement(name = "SabtCD")
    protected String sabtCD;
    @XmlElement(name = "TashilatItem")
    protected ArrayOfTashilatDTO tashilatItem;
    @XmlElement(name = "TahodatItem")
    protected ArrayOfTahodatDTO tahodatItem;
    @XmlElement(name = "TotalAmTashilat")
    protected long totalAmTashilat;
    @XmlElement(name = "TotalAmBedehi")
    protected long totalAmBedehi;
    @XmlElement(name = "TotalAmTahodST")
    protected long totalAmTahodST;
    @XmlElement(name = "TotalAmSarResid")
    protected long totalAmSarResid;
    @XmlElement(name = "TotalAmMoavagh")
    protected long totalAmMoavagh;
    @XmlElement(name = "TotalAmMashkuk")
    protected long totalAmMashkuk;
    @XmlElement(name = "BadHesabiDT")
    protected String badHesabiDT;

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
     * Gets the value of the lName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLName() {
        return lName;
    }

    /**
     * Sets the value of the lName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLName(String value) {
        this.lName = value;
    }

    /**
     * Gets the value of the sabtNO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSabtNO() {
        return sabtNO;
    }

    /**
     * Sets the value of the sabtNO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSabtNO(String value) {
        this.sabtNO = value;
    }

    /**
     * Gets the value of the sabtDT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSabtDT() {
        return sabtDT;
    }

    /**
     * Sets the value of the sabtDT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSabtDT(String value) {
        this.sabtDT = value;
    }

    /**
     * Gets the value of the sabtCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSabtCD() {
        return sabtCD;
    }

    /**
     * Sets the value of the sabtCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSabtCD(String value) {
        this.sabtCD = value;
    }

    /**
     * Gets the value of the tashilatItem property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTashilatDTO }
     *     
     */
    public ArrayOfTashilatDTO getTashilatItem() {
        return tashilatItem;
    }

    /**
     * Sets the value of the tashilatItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTashilatDTO }
     *     
     */
    public void setTashilatItem(ArrayOfTashilatDTO value) {
        this.tashilatItem = value;
    }

    /**
     * Gets the value of the tahodatItem property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTahodatDTO }
     *     
     */
    public ArrayOfTahodatDTO getTahodatItem() {
        return tahodatItem;
    }

    /**
     * Sets the value of the tahodatItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTahodatDTO }
     *     
     */
    public void setTahodatItem(ArrayOfTahodatDTO value) {
        this.tahodatItem = value;
    }

    /**
     * Gets the value of the totalAmTashilat property.
     * 
     */
    public long getTotalAmTashilat() {
        return totalAmTashilat;
    }

    /**
     * Sets the value of the totalAmTashilat property.
     * 
     */
    public void setTotalAmTashilat(long value) {
        this.totalAmTashilat = value;
    }

    /**
     * Gets the value of the totalAmBedehi property.
     * 
     */
    public long getTotalAmBedehi() {
        return totalAmBedehi;
    }

    /**
     * Sets the value of the totalAmBedehi property.
     * 
     */
    public void setTotalAmBedehi(long value) {
        this.totalAmBedehi = value;
    }

    /**
     * Gets the value of the totalAmTahodST property.
     * 
     */
    public long getTotalAmTahodST() {
        return totalAmTahodST;
    }

    /**
     * Sets the value of the totalAmTahodST property.
     * 
     */
    public void setTotalAmTahodST(long value) {
        this.totalAmTahodST = value;
    }

    /**
     * Gets the value of the totalAmSarResid property.
     * 
     */
    public long getTotalAmSarResid() {
        return totalAmSarResid;
    }

    /**
     * Sets the value of the totalAmSarResid property.
     * 
     */
    public void setTotalAmSarResid(long value) {
        this.totalAmSarResid = value;
    }

    /**
     * Gets the value of the totalAmMoavagh property.
     * 
     */
    public long getTotalAmMoavagh() {
        return totalAmMoavagh;
    }

    /**
     * Sets the value of the totalAmMoavagh property.
     * 
     */
    public void setTotalAmMoavagh(long value) {
        this.totalAmMoavagh = value;
    }

    /**
     * Gets the value of the totalAmMashkuk property.
     * 
     */
    public long getTotalAmMashkuk() {
        return totalAmMashkuk;
    }

    /**
     * Sets the value of the totalAmMashkuk property.
     * 
     */
    public void setTotalAmMashkuk(long value) {
        this.totalAmMashkuk = value;
    }

    /**
     * Gets the value of the badHesabiDT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBadHesabiDT() {
        return badHesabiDT;
    }

    /**
     * Sets the value of the badHesabiDT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBadHesabiDT(String value) {
        this.badHesabiDT = value;
    }

}
