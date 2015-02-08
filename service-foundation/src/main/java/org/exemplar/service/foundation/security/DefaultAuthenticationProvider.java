package org.exemplar.service.foundation.security;

import com.google.common.collect.Lists;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

import static com.google.common.base.Strings.isNullOrEmpty;

public class DefaultAuthenticationProvider implements AuthenticationProvider {

    private AuthenticationService authenticationService;

    private AuthorisationService authorisationService;

    public DefaultAuthenticationProvider(AuthenticationService authenticationService, AuthorisationService authorisationService) {
        this.authenticationService = authenticationService;
        this.authorisationService = authorisationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication existingAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if(existingAuthentication == null || !existingAuthentication.isAuthenticated()) {
            return doAuthenticate(authentication);
        }
        return existingAuthentication;
    }

    private Authentication doAuthenticate(Authentication authentication) {
        String username = authentication.getName();
        String credentials = (String) authentication.getCredentials();

        if(isNullOrEmpty(credentials)) {
            throw new AuthenticationCredentialsNotFoundException(
                    "Authorisation failed. Neither password nor security token provided");
        }

        String token = authenticate(username, credentials);
        if(isNullOrEmpty(username)) {
            TokenInfo tokenInfo = authenticationService.getSecurityToken(token);
            username = tokenInfo.getUsername();
        }

        String[] roles = authorise(username, token);

        return new UsernamePasswordAuthenticationToken(username, token, toGrantedAuthority(roles));
    }

    private String authenticate(String username, String credentials) {
        if(isNullOrEmpty(username)) {
            return authenticationService.validate(credentials) ? credentials : null;
        }
        return authenticationService.authenticate(username, credentials);
    }

    private String[] authorise(String username, String token) {
        try {
            return authorisationService.authorise(username, token);
        }
        catch(Exception e) {
            throw new AuthorizationServiceException("Failed to authorise the user", e);
        }
    }

    private Collection<SimpleGrantedAuthority> toGrantedAuthority(String[] roles) {
        Collection<SimpleGrantedAuthority> result = Lists.newArrayList();
        for(String permission : roles) {
            result.add(new SimpleGrantedAuthority(permission));
        }
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
