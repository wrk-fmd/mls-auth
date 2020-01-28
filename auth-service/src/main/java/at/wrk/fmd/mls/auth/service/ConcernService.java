package at.wrk.fmd.mls.auth.service;

import at.wrk.fmd.mls.auth.dto.concern.ConcernDto;

import java.util.Collection;

/**
 * This service handles concerns
 */
public interface ConcernService {

    /**
     * Get all concerns
     *
     * @return A list of concerns
     */
    Collection<ConcernDto> getConcerns();
}
