package at.wrk.fmd.mls.auth.service;

import at.wrk.fmd.mls.auth.dto.unit.UnitTokenDto;
import at.wrk.fmd.mls.auth.entity.Concern;

import java.time.Instant;
import java.util.Collection;

/**
 * This service handles units
 */
public interface UnitService {

    /**
     * Get tokens for all units of one concern
     *
     * @param concern The concern instance
     * @param expiration The nullable expiration time for the tokens
     * @return A list of units
     */
    Collection<UnitTokenDto> getUnitTokens(Concern concern, Instant expiration);
}
