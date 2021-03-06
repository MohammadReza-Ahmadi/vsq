
package com.vosouq.kyc.faranam;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "Inquiry", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://msp.bm/WebServices/Inquiry.asmx?WSDL")
public class Inquiry
    extends Service
{

    private final static URL INQUIRY_WSDL_LOCATION;
    private final static WebServiceException INQUIRY_EXCEPTION;
    private final static QName INQUIRY_QNAME = new QName("http://tempuri.org/", "Inquiry");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://msp.bm/WebServices/Inquiry.asmx?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        INQUIRY_WSDL_LOCATION = url;
        INQUIRY_EXCEPTION = e;
    }

    public Inquiry() {
        super(__getWsdlLocation(), INQUIRY_QNAME);
    }

    public Inquiry(WebServiceFeature... features) {
        super(__getWsdlLocation(), INQUIRY_QNAME, features);
    }

    public Inquiry(URL wsdlLocation) {
        super(wsdlLocation, INQUIRY_QNAME);
    }

    public Inquiry(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, INQUIRY_QNAME, features);
    }

    public Inquiry(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Inquiry(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns InquirySoap
     */
    @WebEndpoint(name = "InquirySoap")
    public InquirySoap getInquirySoap() {
        return super.getPort(new QName("http://tempuri.org/", "InquirySoap"), InquirySoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns InquirySoap
     */
    @WebEndpoint(name = "InquirySoap")
    public InquirySoap getInquirySoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "InquirySoap"), InquirySoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (INQUIRY_EXCEPTION!= null) {
            throw INQUIRY_EXCEPTION;
        }
        return INQUIRY_WSDL_LOCATION;
    }

}
