package org.exemplar.service.foundation.security;

import org.joda.time.DateTime;

public class TokenInfo {

    private final String token;
    private final String username;
    private final DateTime expiry;

    public TokenInfo(String token, String username, DateTime expiry) {
        this.token = token;
        this.username = username;
        this.expiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public DateTime getExpiry() {
        return expiry;
    }
}
