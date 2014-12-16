package org.exemplar.service.foundation.configuration;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

public class ServicePropertyConfigurer extends EncryptedPropertyPlaceholderConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicePropertyConfigurer.class);

    public ServicePropertyConfigurer(String serviceName) {
        this(serviceName, getEnvironment());
    }

    public ServicePropertyConfigurer(String serviceName, String environment) {
        this(getFileLocations(serviceName, environment));
    }

    public ServicePropertyConfigurer(List<String> locations) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        List<Resource> resources = Lists.newArrayList();
        for(String location : locations) {
            Resource resource = resourceLoader.getResource(location);
            resources.add(resource);
        }
        setLocations(resources.toArray(new Resource[resources.size()]));
        setIgnoreResourceNotFound(true);
    }

    private static List<String> getFileLocations(String serviceName, String environment) {
        List<String> fileLocation = Lists.newArrayList();
        fileLocation.add("classpath:config/default.properties");
        fileLocation.add(String.format("classpath:config/global.%s.properties", environment));
        fileLocation.add(String.format("classpath:config/%s.default.properties", serviceName));
        fileLocation.add(String.format("classpath:config/%s.%s.properties", serviceName, environment));
        LOGGER.info("Loading properties from {}", fileLocation);
        return fileLocation;
    }

    private static String getEnvironment() {
        String environment = System.getProperty("env");
        if(Strings.isNullOrEmpty(environment)) {
            environment = "local";
            System.setProperty("env", environment);
        }
        return environment;
    }
}
