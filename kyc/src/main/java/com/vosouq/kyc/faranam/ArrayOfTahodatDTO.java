
package com.vosouq.kyc.faranam;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfTahodatDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfTahodatDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TahodatDTO" type="{http://portal.pbn.net}TahodatDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTahodatDTO", namespace = "http://portal.pbn.net", propOrder = {
    "tahodatDTO"
})
public class ArrayOfTahodatDTO {

    @XmlElement(name = "TahodatDTO", nillable = true)
    protected List<TahodatDTO> tahodatDTO;

    /**
     * Gets the value of the tahodatDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tahodatDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTahodatDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TahodatDTO }
     * 
     * 
     */
    public List<TahodatDTO> getTahodatDTO() {
        if (tahodatDTO == null) {
            tahodatDTO = new ArrayList<TahodatDTO>();
        }
        return this.tahodatDTO;
    }

}
