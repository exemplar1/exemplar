package org.planetarysystem.security;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.exemplar.service.foundation.security.AuthenticationService;
import org.exemplar.service.foundation.security.AuthorisationService;
import org.exemplar.service.foundation.security.TokenInfo;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
public class PlanetarySystemAuthenticationService implements AuthenticationService, AuthorisationService {

    private List<TokenInfo> authorisedUsers = Lists.newArrayList();

    @Override
    public String authenticate(String username, String password) {
        String token = null;

        if(!(isNullOrEmpty(username) || isNullOrEmpty(password))) {
            if(username.equals("ironman") && password.equals("triathlete")) {
                token = UUID.randomUUID().toString();
                authorisedUsers.add(new TokenInfo(token, username, new DateTime("2999-12-31T00:00:00.000-00:00")));
            }
        }
        return token;
    }

    @Override
    public boolean validate(final String token) {
        Optional<TokenInfo> tokenInfo = FluentIterable.from(authorisedUsers).firstMatch(new Predicate<TokenInfo>() {
            @Override
            public boolean apply(TokenInfo input) {
                return input.getToken().equals(token);
            }
        });
        return tokenInfo.isPresent();
    }

    @Override
    public TokenInfo getSecurityToken(final String token) {
        Optional<TokenInfo> tokenInfo = FluentIterable.from(authorisedUsers).firstMatch(new Predicate<TokenInfo>() {
            @Override
            public boolean apply(TokenInfo input) {
                return input.getToken().equals(token);
            }
        });
        return tokenInfo.orNull();
    }

    @Override
    public String[] authorise(final String username, final String token) {
        if(!(isNullOrEmpty(username) || isNullOrEmpty(token))) {
            Optional<TokenInfo> tokenInfo = FluentIterable.from(authorisedUsers).firstMatch(new Predicate<TokenInfo>() {
                @Override
                public boolean apply(TokenInfo input) {
                    return input.getUsername().equals(username) && input.getToken().equals(token);
                }
            });
            if(tokenInfo.isPresent() && tokenInfo.get().getUsername().equals("ironman")) {
                return new String[]{"ADMIN"};
            }
        }
        return new String[0];
    }
}
