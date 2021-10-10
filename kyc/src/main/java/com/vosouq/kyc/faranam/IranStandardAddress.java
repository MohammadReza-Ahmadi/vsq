
package com.vosouq.kyc.faranam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IranStandardAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IranStandardAddress"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://tempuri.org/}Result"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Province" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TownShip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Zone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Village" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LocalityType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LocalityName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LocalityCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="SubLocality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Street" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Street2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="HouseNumber" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Floor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SideFloor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BuildingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PostCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IranStandardAddress", propOrder = {
    "province",
    "townShip",
    "zone",
    "village",
    "localityType",
    "localityName",
    "localityCode",
    "subLocality",
    "street",
    "street2",
    "houseNumber",
    "floor",
    "sideFloor",
    "buildingName",
    "description",
    "postCode"
})
@XmlSeeAlso({
    IranStandardAddressAndTelephones.class
})
public class IranStandardAddress
    extends Result
{

    @XmlElement(name = "Province")
    protected String province;
    @XmlElement(name = "TownShip")
    protected String townShip;
    @XmlElement(name = "Zone")
    protected String zone;
    @XmlElement(name = "Village")
    protected String village;
    @XmlElement(name = "LocalityType")
    protected String localityType;
    @XmlElement(name = "LocalityName")
    protected String localityName;
    @XmlElement(name = "LocalityCode")
    protected int localityCode;
    @XmlElement(name = "SubLocality")
    protected String subLocality;
    @XmlElement(name = "Street")
    protected String street;
    @XmlElement(name = "Street2")
    protected String street2;
    @XmlElement(name = "HouseNumber")
    protected double houseNumber;
    @XmlElement(name = "Floor")
    protected String floor;
    @XmlElement(name = "SideFloor")
    protected String sideFloor;
    @XmlElement(name = "BuildingName")
    protected String buildingName;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "PostCode")
    protected String postCode;

    /**
     * Gets the value of the province property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets the value of the province property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvince(String value) {
        this.province = value;
    }

    /**
     * Gets the value of the townShip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTownShip() {
        return townShip;
    }

    /**
     * Sets the value of the townShip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTownShip(String value) {
        this.townShip = value;
    }

    /**
     * Gets the value of the zone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZone() {
        return zone;
    }

    /**
     * Sets the value of the zone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZone(String value) {
        this.zone = value;
    }

    /**
     * Gets the value of the village property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVillage() {
        return village;
    }

    /**
     * Sets the value of the village property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVillage(String value) {
        this.village = value;
    }

    /**
     * Gets the value of the localityType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalityType() {
        return localityType;
    }

    /**
     * Sets the value of the localityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalityType(String value) {
        this.localityType = value;
    }

    /**
     * Gets the value of the localityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalityName() {
        return localityName;
    }

    /**
     * Sets the value of the localityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalityName(String value) {
        this.localityName = value;
    }

    /**
     * Gets the value of the localityCode property.
     * 
     */
    public int getLocalityCode() {
        return localityCode;
    }

    /**
     * Sets the value of the localityCode property.
     * 
     */
    public void setLocalityCode(int value) {
        this.localityCode = value;
    }

    /**
     * Gets the value of the subLocality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubLocality() {
        return subLocality;
    }

    /**
     * Sets the value of the subLocality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubLocality(String value) {
        this.subLocality = value;
    }

    /**
     * Gets the value of the street property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the value of the street property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreet(String value) {
        this.street = value;
    }

    /**
     * Gets the value of the street2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreet2() {
        return street2;
    }

    /**
     * Sets the value of the street2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreet2(String value) {
        this.street2 = value;
    }

    /**
     * Gets the value of the houseNumber property.
     * 
     */
    public double getHouseNumber() {
        return houseNumber;
    }

    /**
     * Sets the value of the houseNumber property.
     * 
     */
    public void setHouseNumber(double value) {
        this.houseNumber = value;
    }

    /**
     * Gets the value of the floor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the value of the floor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFloor(String value) {
        this.floor = value;
    }

    /**
     * Gets the value of the sideFloor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSideFloor() {
        return sideFloor;
    }

    /**
     * Sets the value of the sideFloor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSideFloor(String value) {
        this.sideFloor = value;
    }

    /**
     * Gets the value of the buildingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     * Sets the value of the buildingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuildingName(String value) {
        this.buildingName = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the postCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Sets the value of the postCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostCode(String value) {
        this.postCode = value;
    }

}
