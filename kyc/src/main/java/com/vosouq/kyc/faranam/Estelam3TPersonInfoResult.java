
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for estelam3TPersonInfoResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="estelam3TPersonInfoResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nin" type="{http://www.w3.org/2001/XMLSchema}long" form="unqualified"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="family" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="fatherName" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="shenasnameSeri" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="shenasnameNo" type="{http://www.w3.org/2001/XMLSchema}int" form="unqualified"/&gt;
 *         &lt;element name="shenasnameSerial" type="{http://www.w3.org/2001/XMLSchema}int" form="unqualified"/&gt;
 *         &lt;element name="birthDate" type="{http://www.w3.org/2001/XMLSchema}int" form="unqualified"/&gt;
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}int" form="unqualified"/&gt;
 *         &lt;element name="deathStatus" type="{http://www.w3.org/2001/XMLSchema}int" form="unqualified"/&gt;
 *         &lt;element name="officeCode" type="{http://www.w3.org/2001/XMLSchema}int" form="unqualified"/&gt;
 *         &lt;element name="officeName" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="bookNo" type="{http://www.w3.org/2001/XMLSchema}int" form="unqualified"/&gt;
 *         &lt;element name="bookRow" type="{http://www.w3.org/2001/XMLSchema}int" form="unqualified"/&gt;
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="exceptionMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="deathDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estelam3TPersonInfoResult", namespace = "http://est", propOrder = {
    "nin",
    "name",
    "family",
    "fatherName",
    "shenasnameSeri",
    "shenasnameNo",
    "shenasnameSerial",
    "birthDate",
    "gender",
    "deathStatus",
    "officeCode",
    "officeName",
    "bookNo",
    "bookRow",
    "message",
    "exceptionMessage",
    "deathDate"
})
public class Estelam3TPersonInfoResult {

    @XmlElement(namespace = "")
    protected long nin;
    @XmlElement(namespace = "")
    protected byte[] name;
    @XmlElement(namespace = "")
    protected byte[] family;
    @XmlElement(namespace = "")
    protected byte[] fatherName;
    @XmlElement(namespace = "")
    protected byte[] shenasnameSeri;
    @XmlElement(namespace = "")
    protected int shenasnameNo;
    @XmlElement(namespace = "")
    protected int shenasnameSerial;
    @XmlElement(namespace = "")
    protected int birthDate;
    @XmlElement(namespace = "")
    protected int gender;
    @XmlElement(namespace = "")
    protected int deathStatus;
    @XmlElement(namespace = "")
    protected int officeCode;
    @XmlElement(namespace = "")
    protected byte[] officeName;
    @XmlElement(namespace = "")
    protected int bookNo;
    @XmlElement(namespace = "")
    protected int bookRow;
    @XmlElement(namespace = "")
    protected String message;
    @XmlElement(namespace = "")
    protected String exceptionMessage;
    @XmlElement(namespace = "")
    protected String deathDate;

    /**
     * Gets the value of the nin property.
     * 
     */
    public long getNin() {
        return nin;
    }

    /**
     * Sets the value of the nin property.
     * 
     */
    public void setNin(long value) {
        this.nin = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setName(byte[] value) {
        this.name = value;
    }

    /**
     * Gets the value of the family property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFamily() {
        return family;
    }

    /**
     * Sets the value of the family property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFamily(byte[] value) {
        this.family = value;
    }

    /**
     * Gets the value of the fatherName property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFatherName() {
        return fatherName;
    }

    /**
     * Sets the value of the fatherName property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFatherName(byte[] value) {
        this.fatherName = value;
    }

    /**
     * Gets the value of the shenasnameSeri property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getShenasnameSeri() {
        return shenasnameSeri;
    }

    /**
     * Sets the value of the shenasnameSeri property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setShenasnameSeri(byte[] value) {
        this.shenasnameSeri = value;
    }

    /**
     * Gets the value of the shenasnameNo property.
     * 
     */
    public int getShenasnameNo() {
        return shenasnameNo;
    }

    /**
     * Sets the value of the shenasnameNo property.
     * 
     */
    public void setShenasnameNo(int value) {
        this.shenasnameNo = value;
    }

    /**
     * Gets the value of the shenasnameSerial property.
     * 
     */
    public int getShenasnameSerial() {
        return shenasnameSerial;
    }

    /**
     * Sets the value of the shenasnameSerial property.
     * 
     */
    public void setShenasnameSerial(int value) {
        this.shenasnameSerial = value;
    }

    /**
     * Gets the value of the birthDate property.
     * 
     */
    public int getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     */
    public void setBirthDate(int value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     */
    public int getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     */
    public void setGender(int value) {
        this.gender = value;
    }

    /**
     * Gets the value of the deathStatus property.
     * 
     */
    public int getDeathStatus() {
        return deathStatus;
    }

    /**
     * Sets the value of the deathStatus property.
     * 
     */
    public void setDeathStatus(int value) {
        this.deathStatus = value;
    }

    /**
     * Gets the value of the officeCode property.
     * 
     */
    public int getOfficeCode() {
        return officeCode;
    }

    /**
     * Sets the value of the officeCode property.
     * 
     */
    public void setOfficeCode(int value) {
        this.officeCode = value;
    }

    /**
     * Gets the value of the officeName property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getOfficeName() {
        return officeName;
    }

    /**
     * Sets the value of the officeName property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setOfficeName(byte[] value) {
        this.officeName = value;
    }

    /**
     * Gets the value of the bookNo property.
     * 
     */
    public int getBookNo() {
        return bookNo;
    }

    /**
     * Sets the value of the bookNo property.
     * 
     */
    public void setBookNo(int value) {
        this.bookNo = value;
    }

    /**
     * Gets the value of the bookRow property.
     * 
     */
    public int getBookRow() {
        return bookRow;
    }

    /**
     * Sets the value of the bookRow property.
     * 
     */
    public void setBookRow(int value) {
        this.bookRow = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the exceptionMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**
     * Sets the value of the exceptionMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExceptionMessage(String value) {
        this.exceptionMessage = value;
    }

    /**
     * Gets the value of the deathDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeathDate() {
        return deathDate;
    }

    /**
     * Sets the value of the deathDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeathDate(String value) {
        this.deathDate = value;
    }

}
