package org.exemplar.service.foundation.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class ServiceSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private AuthenticationProvider authenticationProvider;
    private String secureUrlRegex;

    public ServiceSecurityConfigurer(AuthenticationProvider authenticationProvider, String secureUrlRegex) {
        this.authenticationProvider = authenticationProvider;
        this.secureUrlRegex = secureUrlRegex;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.regexMatcher(secureUrlRegex).addFilterAfter(
                getTokenAuthFilter(), BasicAuthenticationFilter.class).csrf().disable();
    }

    protected AbstractAuthenticationProcessingFilter getTokenAuthFilter() throws Exception {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
