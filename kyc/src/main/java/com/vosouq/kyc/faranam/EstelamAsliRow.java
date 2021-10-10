
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EstelamAsliRow complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EstelamAsliRow"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ExtensionData" type="{http://tempuri.org/}ExtensionDataObject" minOccurs="0"/&gt;
 *         &lt;element name="AdamSabtSanadEntezami" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmBedehiKol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmBenefit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmDirkard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmEltezam" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmMashkuk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmMoavagh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmOriginal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmSarResid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmSukht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AmTahod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ArzCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DasteBandi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DateEstehal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DateSarResid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Estehal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="HadafAzDaryaft" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MainIdNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MainLName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MainLgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MainName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PlaceCdMasraf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RequestNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RequstType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RsrcTamin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ShobeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ShobeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstelamAsliRow", propOrder = {
    "extensionData",
    "adamSabtSanadEntezami",
    "amBedehiKol",
    "amBenefit",
    "amDirkard",
    "amEltezam",
    "amMashkuk",
    "amMoavagh",
    "amOriginal",
    "amSarResid",
    "amSukht",
    "amTahod",
    "arzCode",
    "bankCode",
    "dasteBandi",
    "date",
    "dateEstehal",
    "dateSarResid",
    "estehal",
    "hadafAzDaryaft",
    "mainIdNo",
    "mainLName",
    "mainLgId",
    "mainName",
    "placeCdMasraf",
    "requestNum",
    "requstType",
    "rsrcTamin",
    "shobeCode",
    "shobeName",
    "type"
})
public class EstelamAsliRow {

    @XmlElement(name = "ExtensionData")
    protected ExtensionDataObject extensionData;
    @XmlElement(name = "AdamSabtSanadEntezami")
    protected String adamSabtSanadEntezami;
    @XmlElement(name = "AmBedehiKol")
    protected String amBedehiKol;
    @XmlElement(name = "AmBenefit")
    protected String amBenefit;
    @XmlElement(name = "AmDirkard")
    protected String amDirkard;
    @XmlElement(name = "AmEltezam")
    protected String amEltezam;
    @XmlElement(name = "AmMashkuk")
    protected String amMashkuk;
    @XmlElement(name = "AmMoavagh")
    protected String amMoavagh;
    @XmlElement(name = "AmOriginal")
    protected String amOriginal;
    @XmlElement(name = "AmSarResid")
    protected String amSarResid;
    @XmlElement(name = "AmSukht")
    protected String amSukht;
    @XmlElement(name = "AmTahod")
    protected String amTahod;
    @XmlElement(name = "ArzCode")
    protected String arzCode;
    @XmlElement(name = "BankCode")
    protected String bankCode;
    @XmlElement(name = "DasteBandi")
    protected String dasteBandi;
    @XmlElement(name = "Date")
    protected String date;
    @XmlElement(name = "DateEstehal")
    protected String dateEstehal;
    @XmlElement(name = "DateSarResid")
    protected String dateSarResid;
    @XmlElement(name = "Estehal")
    protected String estehal;
    @XmlElement(name = "HadafAzDaryaft")
    protected String hadafAzDaryaft;
    @XmlElement(name = "MainIdNo")
    protected String mainIdNo;
    @XmlElement(name = "MainLName")
    protected String mainLName;
    @XmlElement(name = "MainLgId")
    protected String mainLgId;
    @XmlElement(name = "MainName")
    protected String mainName;
    @XmlElement(name = "PlaceCdMasraf")
    protected String placeCdMasraf;
    @XmlElement(name = "RequestNum")
    protected String requestNum;
    @XmlElement(name = "RequstType")
    protected String requstType;
    @XmlElement(name = "RsrcTamin")
    protected String rsrcTamin;
    @XmlElement(name = "ShobeCode")
    protected String shobeCode;
    @XmlElement(name = "ShobeName")
    protected String shobeName;
    @XmlElement(name = "Type")
    protected String type;

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
     * Gets the value of the adamSabtSanadEntezami property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdamSabtSanadEntezami() {
        return adamSabtSanadEntezami;
    }

    /**
     * Sets the value of the adamSabtSanadEntezami property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdamSabtSanadEntezami(String value) {
        this.adamSabtSanadEntezami = value;
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
     * Gets the value of the amBenefit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmBenefit() {
        return amBenefit;
    }

    /**
     * Sets the value of the amBenefit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmBenefit(String value) {
        this.amBenefit = value;
    }

    /**
     * Gets the value of the amDirkard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmDirkard() {
        return amDirkard;
    }

    /**
     * Sets the value of the amDirkard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmDirkard(String value) {
        this.amDirkard = value;
    }

    /**
     * Gets the value of the amEltezam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmEltezam() {
        return amEltezam;
    }

    /**
     * Sets the value of the amEltezam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmEltezam(String value) {
        this.amEltezam = value;
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
     * Gets the value of the amMoavagh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmMoavagh() {
        return amMoavagh;
    }

    /**
     * Sets the value of the amMoavagh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmMoavagh(String value) {
        this.amMoavagh = value;
    }

    /**
     * Gets the value of the amOriginal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmOriginal() {
        return amOriginal;
    }

    /**
     * Sets the value of the amOriginal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmOriginal(String value) {
        this.amOriginal = value;
    }

    /**
     * Gets the value of the amSarResid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmSarResid() {
        return amSarResid;
    }

    /**
     * Sets the value of the amSarResid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmSarResid(String value) {
        this.amSarResid = value;
    }

    /**
     * Gets the value of the amSukht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmSukht() {
        return amSukht;
    }

    /**
     * Sets the value of the amSukht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmSukht(String value) {
        this.amSukht = value;
    }

    /**
     * Gets the value of the amTahod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmTahod() {
        return amTahod;
    }

    /**
     * Sets the value of the amTahod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmTahod(String value) {
        this.amTahod = value;
    }

    /**
     * Gets the value of the arzCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArzCode() {
        return arzCode;
    }

    /**
     * Sets the value of the arzCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArzCode(String value) {
        this.arzCode = value;
    }

    /**
     * Gets the value of the bankCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * Sets the value of the bankCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCode(String value) {
        this.bankCode = value;
    }

    /**
     * Gets the value of the dasteBandi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDasteBandi() {
        return dasteBandi;
    }

    /**
     * Sets the value of the dasteBandi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDasteBandi(String value) {
        this.dasteBandi = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the dateEstehal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateEstehal() {
        return dateEstehal;
    }

    /**
     * Sets the value of the dateEstehal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateEstehal(String value) {
        this.dateEstehal = value;
    }

    /**
     * Gets the value of the dateSarResid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateSarResid() {
        return dateSarResid;
    }

    /**
     * Sets the value of the dateSarResid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateSarResid(String value) {
        this.dateSarResid = value;
    }

    /**
     * Gets the value of the estehal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstehal() {
        return estehal;
    }

    /**
     * Sets the value of the estehal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstehal(String value) {
        this.estehal = value;
    }

    /**
     * Gets the value of the hadafAzDaryaft property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHadafAzDaryaft() {
        return hadafAzDaryaft;
    }

    /**
     * Sets the value of the hadafAzDaryaft property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHadafAzDaryaft(String value) {
        this.hadafAzDaryaft = value;
    }

    /**
     * Gets the value of the mainIdNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainIdNo() {
        return mainIdNo;
    }

    /**
     * Sets the value of the mainIdNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainIdNo(String value) {
        this.mainIdNo = value;
    }

    /**
     * Gets the value of the mainLName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainLName() {
        return mainLName;
    }

    /**
     * Sets the value of the mainLName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainLName(String value) {
        this.mainLName = value;
    }

    /**
     * Gets the value of the mainLgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainLgId() {
        return mainLgId;
    }

    /**
     * Sets the value of the mainLgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainLgId(String value) {
        this.mainLgId = value;
    }

    /**
     * Gets the value of the mainName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainName() {
        return mainName;
    }

    /**
     * Sets the value of the mainName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainName(String value) {
        this.mainName = value;
    }

    /**
     * Gets the value of the placeCdMasraf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlaceCdMasraf() {
        return placeCdMasraf;
    }

    /**
     * Sets the value of the placeCdMasraf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlaceCdMasraf(String value) {
        this.placeCdMasraf = value;
    }

    /**
     * Gets the value of the requestNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestNum() {
        return requestNum;
    }

    /**
     * Sets the value of the requestNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestNum(String value) {
        this.requestNum = value;
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
     * Gets the value of the rsrcTamin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRsrcTamin() {
        return rsrcTamin;
    }

    /**
     * Sets the value of the rsrcTamin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRsrcTamin(String value) {
        this.rsrcTamin = value;
    }

    /**
     * Gets the value of the shobeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShobeCode() {
        return shobeCode;
    }

    /**
     * Sets the value of the shobeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShobeCode(String value) {
        this.shobeCode = value;
    }

    /**
     * Gets the value of the shobeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShobeName() {
        return shobeName;
    }

    /**
     * Sets the value of the shobeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShobeName(String value) {
        this.shobeName = value;
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

}
