package org.planetarysystem.configuration;

import org.exemplar.service.foundation.security.ServiceSecurityConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class PlanetarySystemSecurityConfigurer extends ServiceSecurityConfigurer {

    private static final String SECURE_ENDPOINT_PATTERN = "^/secure/planetary.system/.*";

    @Autowired
    public PlanetarySystemSecurityConfigurer(AuthenticationProvider authenticationProvider) {
        super(authenticationProvider, SECURE_ENDPOINT_PATTERN);
    }
}
