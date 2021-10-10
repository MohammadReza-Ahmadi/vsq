
package com.vosouq.kyc.faranam;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for estelamResult3T complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="estelamResult3T"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="estelam3TPersonInfoResult" type="{http://est}estelam3TPersonInfoResult" maxOccurs="unbounded" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="trackingInfo" type="{http://est}TrackingInfo" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="serviceStatus" type="{http://est}serviceStatus" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estelamResult3T", namespace = "http://est", propOrder = {
    "estelam3TPersonInfoResult",
    "trackingInfo",
    "serviceStatus"
})
public class EstelamResult3T {

    @XmlElement(namespace = "")
    protected List<Estelam3TPersonInfoResult> estelam3TPersonInfoResult;
    @XmlElement(namespace = "")
    protected TrackingInfo trackingInfo;
    @XmlElement(namespace = "")
    protected ServiceStatus serviceStatus;

    /**
     * Gets the value of the estelam3TPersonInfoResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the estelam3TPersonInfoResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEstelam3TPersonInfoResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Estelam3TPersonInfoResult }
     * 
     * 
     */
    public List<Estelam3TPersonInfoResult> getEstelam3TPersonInfoResult() {
        if (estelam3TPersonInfoResult == null) {
            estelam3TPersonInfoResult = new ArrayList<Estelam3TPersonInfoResult>();
        }
        return this.estelam3TPersonInfoResult;
    }

    /**
     * Gets the value of the trackingInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TrackingInfo }
     *     
     */
    public TrackingInfo getTrackingInfo() {
        return trackingInfo;
    }

    /**
     * Sets the value of the trackingInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrackingInfo }
     *     
     */
    public void setTrackingInfo(TrackingInfo value) {
        this.trackingInfo = value;
    }

    /**
     * Gets the value of the serviceStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceStatus }
     *     
     */
    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    /**
     * Sets the value of the serviceStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceStatus }
     *     
     */
    public void setServiceStatus(ServiceStatus value) {
        this.serviceStatus = value;
    }

}
