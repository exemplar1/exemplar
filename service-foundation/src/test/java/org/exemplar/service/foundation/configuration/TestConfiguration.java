package org.exemplar.service.foundation.configuration;

import org.jasypt.spring31.properties.EncryptablePropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.exemplar.service")
public class TestConfiguration {

    @Bean
    public EncryptablePropertyPlaceholderConfigurer encryptablePropertyPlaceholderConfigurer() {
        return EncryptablePropertyPlaceholderConfigurerFactory.create("test");
    }
}
