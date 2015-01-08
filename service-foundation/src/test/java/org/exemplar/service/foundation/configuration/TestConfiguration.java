package org.exemplar.service.foundation.configuration;

import org.jasypt.spring31.properties.EncryptablePropertySourcesPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.exemplar.service")
public class TestConfiguration {

    @Bean
    public EncryptablePropertySourcesPlaceholderConfigurer encryptablePropertyPlaceholderConfigurer() {
        return EncryptablePropertySourcesPlaceholderConfigurerFactory.create("test");
    }
}
