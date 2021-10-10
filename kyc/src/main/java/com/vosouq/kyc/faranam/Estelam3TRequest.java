
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Estelam3TRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Estelam3TRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="birthDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="bookNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="bookRow" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="cardSerial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="dateHasPostfix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="family" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="familyHasPerfix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="familyHasPostfix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="fatherName" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="nameHasPerfix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="nameHasPostfix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="nin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="officeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="shenasnameIssueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="shenasnameNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="shenasnameSeri" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="shenasnameSerial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Estelam3TRequest", namespace = "http://est", propOrder = {
    "birthDate",
    "bookNo",
    "bookRow",
    "cardSerial",
    "dateHasPostfix",
    "family",
    "familyHasPerfix",
    "familyHasPostfix",
    "fatherName",
    "gender",
    "name",
    "nameHasPerfix",
    "nameHasPostfix",
    "nin",
    "officeCode",
    "shenasnameIssueDate",
    "shenasnameNo",
    "shenasnameSeri",
    "shenasnameSerial"
})
public class Estelam3TRequest {

    @XmlElement(namespace = "")
    protected String birthDate;
    @XmlElement(namespace = "")
    protected String bookNo;
    @XmlElement(namespace = "")
    protected String bookRow;
    @XmlElement(namespace = "")
    protected String cardSerial;
    @XmlElement(namespace = "")
    protected String dateHasPostfix;
    @XmlElement(namespace = "")
    protected byte[] family;
    @XmlElement(namespace = "")
    protected String familyHasPerfix;
    @XmlElement(namespace = "")
    protected String familyHasPostfix;
    @XmlElement(namespace = "")
    protected byte[] fatherName;
    @XmlElement(namespace = "")
    protected String gender;
    @XmlElement(namespace = "")
    protected byte[] name;
    @XmlElement(namespace = "")
    protected String nameHasPerfix;
    @XmlElement(namespace = "")
    protected String nameHasPostfix;
    @XmlElement(namespace = "")
    protected String nin;
    @XmlElement(namespace = "")
    protected String officeCode;
    @XmlElement(namespace = "")
    protected String shenasnameIssueDate;
    @XmlElement(namespace = "")
    protected String shenasnameNo;
    @XmlElement(namespace = "")
    protected byte[] shenasnameSeri;
    @XmlElement(namespace = "")
    protected String shenasnameSerial;

    /**
     * Gets the value of the birthDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthDate(String value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the bookNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookNo() {
        return bookNo;
    }

    /**
     * Sets the value of the bookNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookNo(String value) {
        this.bookNo = value;
    }

    /**
     * Gets the value of the bookRow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookRow() {
        return bookRow;
    }

    /**
     * Sets the value of the bookRow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookRow(String value) {
        this.bookRow = value;
    }

    /**
     * Gets the value of the cardSerial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardSerial() {
        return cardSerial;
    }

    /**
     * Sets the value of the cardSerial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardSerial(String value) {
        this.cardSerial = value;
    }

    /**
     * Gets the value of the dateHasPostfix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateHasPostfix() {
        return dateHasPostfix;
    }

    /**
     * Sets the value of the dateHasPostfix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateHasPostfix(String value) {
        this.dateHasPostfix = value;
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
     * Gets the value of the familyHasPerfix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamilyHasPerfix() {
        return familyHasPerfix;
    }

    /**
     * Sets the value of the familyHasPerfix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamilyHasPerfix(String value) {
        this.familyHasPerfix = value;
    }

    /**
     * Gets the value of the familyHasPostfix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamilyHasPostfix() {
        return familyHasPostfix;
    }

    /**
     * Sets the value of the familyHasPostfix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamilyHasPostfix(String value) {
        this.familyHasPostfix = value;
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
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
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
     * Gets the value of the nameHasPerfix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameHasPerfix() {
        return nameHasPerfix;
    }

    /**
     * Sets the value of the nameHasPerfix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameHasPerfix(String value) {
        this.nameHasPerfix = value;
    }

    /**
     * Gets the value of the nameHasPostfix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameHasPostfix() {
        return nameHasPostfix;
    }

    /**
     * Sets the value of the nameHasPostfix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameHasPostfix(String value) {
        this.nameHasPostfix = value;
    }

    /**
     * Gets the value of the nin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNin() {
        return nin;
    }

    /**
     * Sets the value of the nin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNin(String value) {
        this.nin = value;
    }

    /**
     * Gets the value of the officeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficeCode() {
        return officeCode;
    }

    /**
     * Sets the value of the officeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficeCode(String value) {
        this.officeCode = value;
    }

    /**
     * Gets the value of the shenasnameIssueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShenasnameIssueDate() {
        return shenasnameIssueDate;
    }

    /**
     * Sets the value of the shenasnameIssueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShenasnameIssueDate(String value) {
        this.shenasnameIssueDate = value;
    }

    /**
     * Gets the value of the shenasnameNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShenasnameNo() {
        return shenasnameNo;
    }

    /**
     * Sets the value of the shenasnameNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShenasnameNo(String value) {
        this.shenasnameNo = value;
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
     * Gets the value of the shenasnameSerial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShenasnameSerial() {
        return shenasnameSerial;
    }

    /**
     * Sets the value of the shenasnameSerial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShenasnameSerial(String value) {
        this.shenasnameSerial = value;
    }

}
