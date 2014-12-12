package org.exemplar.foundation.service.configuration;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
public class ServicePropertyConfigurerTest {

    @Value("${global.test.property}")
    private String property1;

    @Value("${test.default.property}")
    private String property2;

    @Value("${test.local.property}")
    private String property3;

    @Value("${encrypted.property}")
    private String property4;

    @Test
    public void testProperties() {
        assertEquals("global1", property1);
        assertEquals("default1", property2);
        assertEquals("local1", property3);
        assertEquals("password", property4);
    }

}