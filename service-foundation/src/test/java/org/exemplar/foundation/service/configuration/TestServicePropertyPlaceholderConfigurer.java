package org.exemplar.foundation.service.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TestServicePropertyPlaceholderConfigurer extends ServicePropertyConfigurer {

    public TestServicePropertyPlaceholderConfigurer() {
        super("test");
    }
}
