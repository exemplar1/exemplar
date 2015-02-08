package org.exemplar.service.foundation.security;

public interface AuthenticationService {

    /**
     * Authenticates username and password and if successful returns a valid security token.
     * Otherwise returns null.
     *
     * @param username username
     * @param password user password
     * @return valid security token or null if not successfully authenticated
     */
    String authenticate(String username, String password);

    /**
     * Validates a security token. Returns true if successfully validated, or otherwise false.
     *
     * @param token security token
     * @return true is valid, false otherwise
     */
    boolean validate(String token);

    /**
     * Retrieves a <code>TokenInfo</code> for a specified token.
     *
     * @param token validated security token
     * @return user token information
     */
    TokenInfo getSecurityToken(String token);

}
