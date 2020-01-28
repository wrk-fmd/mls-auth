package at.wrk.fmd.mls.auth.service;

import at.wrk.fmd.mls.auth.dto.auth.AuthRequestDto;
import at.wrk.fmd.mls.auth.filter.JwtAuthenticationToken;

/**
 * This service handles authentication requests
 */
public interface AuthenticationService {

    /**
     * Authenticate a user based on username and password
     *
     * @param request The DTO containing the username and password
     * @return The renewal token which can be used to obtain a request token
     */
    String authenticate(AuthRequestDto request);

    /**
     * Retrieve a new request token for the user authenticated through a renewal token
     *
     * @param authentication The current authentication
     * @return The new request token
     */
    String getRequestToken(JwtAuthenticationToken authentication);
}
