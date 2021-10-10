package com.vosouq.paymentgateway.mellat.webservice.ipg.config;

import com.vosouq.paymentgateway.mellat.webservice.ipg.IPGSoapConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class Config {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        /* this is the package name specified in the <generatePackage> specified in pom.xml */
        marshaller.setContextPath("com.vosouq.paymentgateway.mellat.webservice.ipg.gen");
        return marshaller;
    }

    @Bean
    public IPGSoapConnector soapConnector(Jaxb2Marshaller marshaller) {
        IPGSoapConnector client = new IPGSoapConnector();
        client.setDefaultUri("https://bpm.shaparak.ir/pgwchannel/services/pgw");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}