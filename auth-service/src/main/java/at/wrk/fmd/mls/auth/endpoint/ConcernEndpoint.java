package at.wrk.fmd.mls.auth.endpoint;

import static java.util.Objects.requireNonNull;

import at.wrk.fmd.mls.auth.dto.concern.ConcernDto;
import at.wrk.fmd.mls.auth.service.ConcernService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * This class is the REST endpoint that handles concerns
 */
@RestController
@RequestMapping("/concerns")
public class ConcernEndpoint {

    private final ConcernService concernService;

    @Autowired
    public ConcernEndpoint(ConcernService concernService) {
        this.concernService = requireNonNull(concernService, "ConcernService must not be null");
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROOT')")
    @ApiOperation("Get all concerns")
    public Collection<ConcernDto> getConcerns() {
        return concernService.getConcerns();
    }
}
