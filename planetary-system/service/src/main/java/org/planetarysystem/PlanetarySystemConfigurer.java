package org.planetarysystem;

import com.google.common.collect.Maps;
import org.exemplar.service.foundation.configuration.EncryptablePropertySourcesPlaceholderConfigurerFactory;
import org.jasypt.spring31.properties.EncryptablePropertySourcesPlaceholderConfigurer;
import org.planetarysystem.contract.Planet;
import org.planetarysystem.contract.Planets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import java.util.Map;

@Configuration
public class PlanetarySystemConfigurer {

    @Bean
    public EncryptablePropertySourcesPlaceholderConfigurer encryptablePropertySourcesPlaceholderConfigurer() {
        return EncryptablePropertySourcesPlaceholderConfigurerFactory.create("planetary-system");
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Planets.class, Planet.class);
        marshaller.setSchemas(new ClassPathResource("xsd/planetary-system.xsd"));

        Map<String, Object> properties = Maps.newHashMap();
        properties.put(Marshaller.JAXB_ENCODING, "UTF-8");
        properties.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setMarshallerProperties(properties);
        return marshaller;
    }

    @Bean
    @Autowired
    public HttpMessageConverters httpMessageConverters(Jaxb2Marshaller jaxb2Marshaller) {
        return new HttpMessageConverters(new MarshallingHttpMessageConverter(jaxb2Marshaller, jaxb2Marshaller));
    }
}
