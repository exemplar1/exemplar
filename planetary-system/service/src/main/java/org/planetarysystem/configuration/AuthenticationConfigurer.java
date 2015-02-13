package org.planetarysystem.configuration;

import org.exemplar.service.foundation.security.AuthenticationService;
import org.exemplar.service.foundation.security.AuthorisationService;
import org.exemplar.service.foundation.security.DefaultAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

@Configuration
public class AuthenticationConfigurer {

    @Bean
    @Autowired
    public AuthenticationProvider authenticationProvider(
            AuthenticationService authenticationService, AuthorisationService authorisationService) {
        return new DefaultAuthenticationProvider(authenticationService, authorisationService);
    }
}
