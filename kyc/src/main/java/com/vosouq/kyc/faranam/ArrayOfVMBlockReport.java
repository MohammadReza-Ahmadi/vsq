
package com.vosouq.kyc.faranam;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVMBlockReport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVMBlockReport"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="VMBlockReport" type="{http://tempuri.org/}VMBlockReport" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVMBlockReport", propOrder = {
    "vmBlockReport"
})
public class ArrayOfVMBlockReport {

    @XmlElement(name = "VMBlockReport", nillable = true)
    protected List<VMBlockReport> vmBlockReport;

    /**
     * Gets the value of the vmBlockReport property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vmBlockReport property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVMBlockReport().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VMBlockReport }
     * 
     * 
     */
    public List<VMBlockReport> getVMBlockReport() {
        if (vmBlockReport == null) {
            vmBlockReport = new ArrayList<VMBlockReport>();
        }
        return this.vmBlockReport;
    }

}
