
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AddTamChequeModel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddTamChequeModel"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="FollowUpCode" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="ChequeSerial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ChequeSeriChar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Account" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Branch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ChequeSeriNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InsertDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ReturnDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Shenaseh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *         &lt;element name="SupplyDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SupplyType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddTamChequeModel", propOrder = {
    "message",
    "status",
    "id",
    "followUpCode",
    "chequeSerial",
    "chequeSeriChar",
    "account",
    "branch",
    "chequeSeriNumber",
    "insertDateTime",
    "returnDate",
    "shenaseh",
    "state",
    "supplyDate",
    "supplyType"
})
public class AddTamChequeModel {

    @XmlElement(name = "Message")
    protected String message;
    @XmlElement(name = "Status")
    protected int status;
    @XmlElement(name = "Id", required = true, type = Long.class, nillable = true)
    protected Long id;
    @XmlElement(name = "FollowUpCode")
    protected long followUpCode;
    @XmlElement(name = "ChequeSerial")
    protected String chequeSerial;
    @XmlElement(name = "ChequeSeriChar")
    protected String chequeSeriChar;
    @XmlElement(name = "Account")
    protected String account;
    @XmlElement(name = "Branch")
    protected String branch;
    @XmlElement(name = "ChequeSeriNumber")
    protected String chequeSeriNumber;
    @XmlElement(name = "InsertDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar insertDateTime;
    @XmlElement(name = "ReturnDate")
    protected String returnDate;
    @XmlElement(name = "Shenaseh")
    protected String shenaseh;
    @XmlElement(name = "State")
    @XmlSchemaType(name = "unsignedByte")
    protected short state;
    @XmlElement(name = "SupplyDate")
    protected String supplyDate;
    @XmlElement(name = "SupplyType")
    protected String supplyType;

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
     * Gets the value of the status property.
     * 
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     */
    public void setStatus(int value) {
        this.status = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the followUpCode property.
     * 
     */
    public long getFollowUpCode() {
        return followUpCode;
    }

    /**
     * Sets the value of the followUpCode property.
     * 
     */
    public void setFollowUpCode(long value) {
        this.followUpCode = value;
    }

    /**
     * Gets the value of the chequeSerial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChequeSerial() {
        return chequeSerial;
    }

    /**
     * Sets the value of the chequeSerial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChequeSerial(String value) {
        this.chequeSerial = value;
    }

    /**
     * Gets the value of the chequeSeriChar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChequeSeriChar() {
        return chequeSeriChar;
    }

    /**
     * Sets the value of the chequeSeriChar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChequeSeriChar(String value) {
        this.chequeSeriChar = value;
    }

    /**
     * Gets the value of the account property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccount() {
        return account;
    }

    /**
     * Sets the value of the account property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccount(String value) {
        this.account = value;
    }

    /**
     * Gets the value of the branch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranch() {
        return branch;
    }

    /**
     * Sets the value of the branch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranch(String value) {
        this.branch = value;
    }

    /**
     * Gets the value of the chequeSeriNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChequeSeriNumber() {
        return chequeSeriNumber;
    }

    /**
     * Sets the value of the chequeSeriNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChequeSeriNumber(String value) {
        this.chequeSeriNumber = value;
    }

    /**
     * Gets the value of the insertDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInsertDateTime() {
        return insertDateTime;
    }

    /**
     * Sets the value of the insertDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInsertDateTime(XMLGregorianCalendar value) {
        this.insertDateTime = value;
    }

    /**
     * Gets the value of the returnDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the value of the returnDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnDate(String value) {
        this.returnDate = value;
    }

    /**
     * Gets the value of the shenaseh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShenaseh() {
        return shenaseh;
    }

    /**
     * Sets the value of the shenaseh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShenaseh(String value) {
        this.shenaseh = value;
    }

    /**
     * Gets the value of the state property.
     * 
     */
    public short getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     */
    public void setState(short value) {
        this.state = value;
    }

    /**
     * Gets the value of the supplyDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplyDate() {
        return supplyDate;
    }

    /**
     * Sets the value of the supplyDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplyDate(String value) {
        this.supplyDate = value;
    }

    /**
     * Gets the value of the supplyType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplyType() {
        return supplyType;
    }

    /**
     * Sets the value of the supplyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplyType(String value) {
        this.supplyType = value;
    }

}
