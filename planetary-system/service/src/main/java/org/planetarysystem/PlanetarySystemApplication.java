package org.planetarysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;

@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableMBeanExport
@ComponentScan({"org.planetarysystem"})
public class PlanetarySystemApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(PlanetarySystemApplication.class);
        application.addListeners(new ApplicationPidFileWriter("planetary-system.pid"));
        application.run(args);
    }
}
