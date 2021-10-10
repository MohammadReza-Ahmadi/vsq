
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VMBlockReport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VMBlockReport"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Row" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IdentityNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BirthPlaceCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Count" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="BankCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="BirthDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FatherName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BlockType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NationalNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UnBlockDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FileNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nat_UpdateUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nat_Updatedate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VMBlockReport", propOrder = {
    "row",
    "id",
    "name",
    "lastName",
    "identityNo",
    "birthPlaceCode",
    "count",
    "bankCode",
    "birthDate",
    "fatherName",
    "blockType",
    "nationalNo",
    "unBlockDate",
    "fileNo",
    "natUpdateUser",
    "natUpdatedate"
})
public class VMBlockReport {

    @XmlElement(name = "Row")
    protected long row;
    @XmlElement(name = "Id")
    protected long id;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "LastName")
    protected String lastName;
    @XmlElement(name = "IdentityNo")
    protected String identityNo;
    @XmlElement(name = "BirthPlaceCode", required = true, type = Integer.class, nillable = true)
    protected Integer birthPlaceCode;
    @XmlElement(name = "Count", required = true, type = Integer.class, nillable = true)
    protected Integer count;
    @XmlElement(name = "BankCode", required = true, type = Integer.class, nillable = true)
    protected Integer bankCode;
    @XmlElement(name = "BirthDate")
    protected String birthDate;
    @XmlElement(name = "FatherName")
    protected String fatherName;
    @XmlElement(name = "BlockType")
    protected String blockType;
    @XmlElement(name = "NationalNo")
    protected String nationalNo;
    @XmlElement(name = "UnBlockDate")
    protected String unBlockDate;
    @XmlElement(name = "FileNo")
    protected String fileNo;
    @XmlElement(name = "nat_UpdateUser")
    protected String natUpdateUser;
    @XmlElement(name = "nat_Updatedate")
    protected String natUpdatedate;

    /**
     * Gets the value of the row property.
     * 
     */
    public long getRow() {
        return row;
    }

    /**
     * Sets the value of the row property.
     * 
     */
    public void setRow(long value) {
        this.row = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
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
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the identityNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentityNo() {
        return identityNo;
    }

    /**
     * Sets the value of the identityNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentityNo(String value) {
        this.identityNo = value;
    }

    /**
     * Gets the value of the birthPlaceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBirthPlaceCode() {
        return birthPlaceCode;
    }

    /**
     * Sets the value of the birthPlaceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBirthPlaceCode(Integer value) {
        this.birthPlaceCode = value;
    }

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    /**
     * Gets the value of the bankCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBankCode() {
        return bankCode;
    }

    /**
     * Sets the value of the bankCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBankCode(Integer value) {
        this.bankCode = value;
    }

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
     * Gets the value of the fatherName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFatherName() {
        return fatherName;
    }

    /**
     * Sets the value of the fatherName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFatherName(String value) {
        this.fatherName = value;
    }

    /**
     * Gets the value of the blockType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlockType() {
        return blockType;
    }

    /**
     * Sets the value of the blockType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlockType(String value) {
        this.blockType = value;
    }

    /**
     * Gets the value of the nationalNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationalNo() {
        return nationalNo;
    }

    /**
     * Sets the value of the nationalNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationalNo(String value) {
        this.nationalNo = value;
    }

    /**
     * Gets the value of the unBlockDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnBlockDate() {
        return unBlockDate;
    }

    /**
     * Sets the value of the unBlockDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnBlockDate(String value) {
        this.unBlockDate = value;
    }

    /**
     * Gets the value of the fileNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileNo() {
        return fileNo;
    }

    /**
     * Sets the value of the fileNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileNo(String value) {
        this.fileNo = value;
    }

    /**
     * Gets the value of the natUpdateUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNatUpdateUser() {
        return natUpdateUser;
    }

    /**
     * Sets the value of the natUpdateUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNatUpdateUser(String value) {
        this.natUpdateUser = value;
    }

    /**
     * Gets the value of the natUpdatedate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNatUpdatedate() {
        return natUpdatedate;
    }

    /**
     * Sets the value of the natUpdatedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNatUpdatedate(String value) {
        this.natUpdatedate = value;
    }

}
