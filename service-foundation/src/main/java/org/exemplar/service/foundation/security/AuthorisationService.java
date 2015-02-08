package org.exemplar.service.foundation.security;

public interface AuthorisationService {

    /**
     * Returns an array of roles for a given username and validated token
     *
     * @param username user  name
     * @param token    security token
     * @return list of roles for a valid token, otherwise an empty array
     */
    String[] authorise(String username, String token);
}
