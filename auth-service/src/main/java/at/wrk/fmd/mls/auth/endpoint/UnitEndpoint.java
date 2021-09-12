package at.wrk.fmd.mls.auth.endpoint;

import static java.util.Objects.requireNonNull;

import at.wrk.fmd.mls.auth.dto.unit.UnitTokenDto;
import at.wrk.fmd.mls.auth.entity.Concern;
import at.wrk.fmd.mls.auth.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collection;

/**
 * This class is the REST endpoint that handles units
 */
@RestController
@RequestMapping("/concerns/{concern}/units")
public class UnitEndpoint {

    private final UnitService unitService;

    @Autowired
    public UnitEndpoint(UnitService unitService) {
        this.unitService = requireNonNull(unitService, "UnitService must not be null");
    }

    @GetMapping("/tokens")
    @PreAuthorize("hasAuthority('ROOT')")
    @Operation(summary = "Gets all units for a concern with tokens")
    public Collection<UnitTokenDto> getTokens(@PathVariable Concern concern, @RequestParam(required = false) Instant expiration) {
        ParamValidator.validate(concern);
        return unitService.getUnitTokens(concern, expiration);
    }
}
