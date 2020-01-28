package at.wrk.fmd.mls.auth.service;

import at.wrk.fmd.mls.auth.entity.Unit;

import java.time.Instant;
import java.util.Collection;

/**
 * This service generates tokens
 */
public interface TokenService {

    /**
     * Generate a user-based renewal token
     *
     * @param username The username for which the token will be valid
     * @return A renewal JWT for the user
     */
    String generateUserRenewalToken(String username);

    /**
     * Generate a unit-based renewal token
     *
     * @param unit The unit for which the token will be valid
     * @param expiration The optional expiration timestamp for the token (defaults to duration specified in application config)
     * @return A renewal JWT for the unit
     */
    String generateUnitRenewalToken(Unit unit, Instant expiration);

    /**
     * Generate a user-based request token
     *
     * @param username The username for which the token will be valid
     * @param authorities The list of authorities associated with the user
     * @return A request JWT for the user
     */
    String generateUserRequestToken(String username, Collection<String> authorities);

    /**
     * Generate a unit-based request token
     *
     * @param units The list of units for which the token will be valid
     * @return A request JWT for the units
     */
    String generateUnitRequestToken(Collection<String> units);
}
