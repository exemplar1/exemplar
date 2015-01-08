package org.exemplar.service.foundation.configuration;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.spring31.properties.EncryptablePropertySourcesPlaceholderConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

public class EncryptablePropertySourcesPlaceholderConfigurerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptablePropertySourcesPlaceholderConfigurerFactory.class);
    private static final String DEFAULT_ENVIRONMENT = "local";
    private static final String DEFAULT_ENCRYPT_PASSWORD = "t0Ps3cr3t";

    public static EncryptablePropertySourcesPlaceholderConfigurer create(String serviceName) {
        EncryptablePropertySourcesPlaceholderConfigurer configurer = new EncryptablePropertySourcesPlaceholderConfigurer(encryptor());
        configurer.setLocations(getResourceLocations(serviceName, getEnvironment()));
        configurer.setIgnoreResourceNotFound(true);
        return configurer;
    }

    public static StandardPBEStringEncryptor encryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        String encryptPassword = System.getProperty("enc.password");
        encryptor.setPassword(
                isNullOrEmpty(encryptPassword) ? DEFAULT_ENCRYPT_PASSWORD : encryptPassword
        );
        return encryptor;
    }

    private static Resource[] getResourceLocations(String serviceName, String environment) {
        List<String> locations = getFileLocations(serviceName, environment);

        List<Resource> resources = Lists.transform(locations, new Function<String, Resource>() {
            @Override
            public Resource apply(String location) {
                return new ClassPathResource(location);
            }
        });

        return resources.toArray(new Resource[resources.size()]);
    }

    private static List<String> getFileLocations(String serviceName, String environment) {
        List<String> fileLocation = Lists.newArrayList();
        fileLocation.add("config/default.properties");
        fileLocation.add(String.format("config/global.%s.properties", environment));
        fileLocation.add(String.format("config/%s.default.properties", serviceName));
        fileLocation.add(String.format("config/%s.%s.properties", serviceName, environment));
        LOGGER.info("Loading properties from {}", fileLocation);
        return fileLocation;
    }

    private static String getEnvironment() {
        String environment = System.getProperty("env");
        if(Strings.isNullOrEmpty(environment)) {
            environment = DEFAULT_ENVIRONMENT;
            System.setProperty("env", environment);
        }
        return environment;
    }
}
