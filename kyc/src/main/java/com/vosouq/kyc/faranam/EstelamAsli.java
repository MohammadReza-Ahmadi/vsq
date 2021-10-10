
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EstelamAsli complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EstelamAsli"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TotalAmTashilat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TotalAmBedehi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TotalAmTahodTS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TotalAmSarResid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TotalAmMoavagh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TotalAmMashkook" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ExtensionData" type="{http://tempuri.org/}ExtensionDataObject" minOccurs="0"/&gt;
 *         &lt;element name="BadHesabiDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CustomerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DateEstlm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EstelamAsliRows" type="{http://tempuri.org/}ArrayOfEstelamAsliRow" minOccurs="0"/&gt;
 *         &lt;element name="FName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LegalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NationalCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ShenaseEstlm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ShenaseRes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstelamAsli", propOrder = {
    "totalAmTashilat",
    "totalAmBedehi",
    "totalAmTahodTS",
    "totalAmSarResid",
    "totalAmMoavagh",
    "totalAmMashkook",
    "status",
    "extensionData",
    "badHesabiDate",
    "country",
    "customerType",
    "dateEstlm",
    "estelamAsliRows",
    "fName",
    "lName",
    "legalId",
    "nationalCd",
    "shenaseEstlm",
    "shenaseRes"
})
public class EstelamAsli {

    @XmlElement(name = "TotalAmTashilat")
    protected String totalAmTashilat;
    @XmlElement(name = "TotalAmBedehi")
    protected String totalAmBedehi;
    @XmlElement(name = "TotalAmTahodTS")
    protected String totalAmTahodTS;
    @XmlElement(name = "TotalAmSarResid")
    protected String totalAmSarResid;
    @XmlElement(name = "TotalAmMoavagh")
    protected String totalAmMoavagh;
    @XmlElement(name = "TotalAmMashkook")
    protected String totalAmMashkook;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "ExtensionData")
    protected ExtensionDataObject extensionData;
    @XmlElement(name = "BadHesabiDate")
    protected String badHesabiDate;
    @XmlElement(name = "Country")
    protected String country;
    @XmlElement(name = "CustomerType")
    protected String customerType;
    @XmlElement(name = "DateEstlm")
    protected String dateEstlm;
    @XmlElement(name = "EstelamAsliRows")
    protected ArrayOfEstelamAsliRow estelamAsliRows;
    @XmlElement(name = "FName")
    protected String fName;
    @XmlElement(name = "LName")
    protected String lName;
    @XmlElement(name = "LegalId")
    protected String legalId;
    @XmlElement(name = "NationalCd")
    protected String nationalCd;
    @XmlElement(name = "ShenaseEstlm")
    protected String shenaseEstlm;
    @XmlElement(name = "ShenaseRes")
    protected String shenaseRes;

    /**
     * Gets the value of the totalAmTashilat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalAmTashilat() {
        return totalAmTashilat;
    }

    /**
     * Sets the value of the totalAmTashilat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalAmTashilat(String value) {
        this.totalAmTashilat = value;
    }

    /**
     * Gets the value of the totalAmBedehi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalAmBedehi() {
        return totalAmBedehi;
    }

    /**
     * Sets the value of the totalAmBedehi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalAmBedehi(String value) {
        this.totalAmBedehi = value;
    }

    /**
     * Gets the value of the totalAmTahodTS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalAmTahodTS() {
        return totalAmTahodTS;
    }

    /**
     * Sets the value of the totalAmTahodTS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalAmTahodTS(String value) {
        this.totalAmTahodTS = value;
    }

    /**
     * Gets the value of the totalAmSarResid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalAmSarResid() {
        return totalAmSarResid;
    }

    /**
     * Sets the value of the totalAmSarResid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalAmSarResid(String value) {
        this.totalAmSarResid = value;
    }

    /**
     * Gets the value of the totalAmMoavagh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalAmMoavagh() {
        return totalAmMoavagh;
    }

    /**
     * Sets the value of the totalAmMoavagh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalAmMoavagh(String value) {
        this.totalAmMoavagh = value;
    }

    /**
     * Gets the value of the totalAmMashkook property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalAmMashkook() {
        return totalAmMashkook;
    }

    /**
     * Sets the value of the totalAmMashkook property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalAmMashkook(String value) {
        this.totalAmMashkook = value;
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

    /**
     * Gets the value of the extensionData property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionDataObject }
     *     
     */
    public ExtensionDataObject getExtensionData() {
        return extensionData;
    }

    /**
     * Sets the value of the extensionData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionDataObject }
     *     
     */
    public void setExtensionData(ExtensionDataObject value) {
        this.extensionData = value;
    }

    /**
     * Gets the value of the badHesabiDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBadHesabiDate() {
        return badHesabiDate;
    }

    /**
     * Sets the value of the badHesabiDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBadHesabiDate(String value) {
        this.badHesabiDate = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the customerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * Sets the value of the customerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerType(String value) {
        this.customerType = value;
    }

    /**
     * Gets the value of the dateEstlm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateEstlm() {
        return dateEstlm;
    }

    /**
     * Sets the value of the dateEstlm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateEstlm(String value) {
        this.dateEstlm = value;
    }

    /**
     * Gets the value of the estelamAsliRows property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEstelamAsliRow }
     *     
     */
    public ArrayOfEstelamAsliRow getEstelamAsliRows() {
        return estelamAsliRows;
    }

    /**
     * Sets the value of the estelamAsliRows property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEstelamAsliRow }
     *     
     */
    public void setEstelamAsliRows(ArrayOfEstelamAsliRow value) {
        this.estelamAsliRows = value;
    }

    /**
     * Gets the value of the fName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFName() {
        return fName;
    }

    /**
     * Sets the value of the fName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFName(String value) {
        this.fName = value;
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
     * Gets the value of the legalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalId() {
        return legalId;
    }

    /**
     * Sets the value of the legalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalId(String value) {
        this.legalId = value;
    }

    /**
     * Gets the value of the nationalCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationalCd() {
        return nationalCd;
    }

    /**
     * Sets the value of the nationalCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationalCd(String value) {
        this.nationalCd = value;
    }

    /**
     * Gets the value of the shenaseEstlm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShenaseEstlm() {
        return shenaseEstlm;
    }

    /**
     * Sets the value of the shenaseEstlm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShenaseEstlm(String value) {
        this.shenaseEstlm = value;
    }

    /**
     * Gets the value of the shenaseRes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShenaseRes() {
        return shenaseRes;
    }

    /**
     * Sets the value of the shenaseRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShenaseRes(String value) {
        this.shenaseRes = value;
    }

}
