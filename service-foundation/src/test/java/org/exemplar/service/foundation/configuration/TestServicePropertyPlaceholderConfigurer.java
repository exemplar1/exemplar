package org.exemplar.service.foundation.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TestServicePropertyPlaceholderConfigurer extends ServicePropertyConfigurer {

    public TestServicePropertyPlaceholderConfigurer() {
        super("test");
    }
}
