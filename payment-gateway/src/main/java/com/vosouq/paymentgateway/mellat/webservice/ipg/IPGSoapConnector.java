package com.vosouq.paymentgateway.mellat.webservice.ipg;

import com.vosouq.paymentgateway.mellat.constant.NumberConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class IPGSoapConnector extends WebServiceGatewaySupport {

    @Value("${ipg.shaparak.ws.url}")
    private String webserviceUrl;

    @Value("${ipg.shaparak.ws.namespace.url}")
    private String namespaceUrl;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <S extends Class,R extends Object> R callWebService(S requestClassType, Object request) {
        String localPart = requestClassType.getSimpleName().replaceFirst(String.valueOf(requestClassType.getSimpleName().charAt(NumberConstants.ZERO)),String.valueOf(requestClassType.getSimpleName().charAt(NumberConstants.ZERO)).toLowerCase());
        QName qName = new QName(namespaceUrl,localPart);
        JAXBElement<S> requestJAXBElement = new JAXBElement<>(qName, requestClassType,request);
        JAXBElement<R> responseJAXBElement = (JAXBElement<R>) getWebServiceTemplate().marshalSendAndReceive(webserviceUrl, requestJAXBElement);
        return responseJAXBElement.getValue();
    }

    /*    public Object callWebService(String url, Object request) {
        return getWebServiceTemplate().marshalSendAndReceive(url, request);
    }*/

}