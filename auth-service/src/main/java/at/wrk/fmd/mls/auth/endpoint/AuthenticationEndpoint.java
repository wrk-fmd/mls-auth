package at.wrk.fmd.mls.auth.endpoint;

import static java.util.Objects.requireNonNull;

import at.wrk.fmd.mls.auth.dto.auth.AuthRequestDto;
import at.wrk.fmd.mls.auth.dto.auth.AuthResponseDto;
import at.wrk.fmd.mls.auth.filter.JwtAuthenticationToken;
import at.wrk.fmd.mls.auth.service.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * This class is the REST endpoint that handles authentication requests
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationEndpoint {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationEndpoint(AuthenticationService authenticationService) {
        this.authenticationService = requireNonNull(authenticationService, "AuthenticationService must not be null");
    }

    @PostMapping
    @ApiOperation("Authenticates a user with username and password as credentials")
    public AuthResponseDto authenticate(@Valid @RequestBody AuthRequestDto authRequest) {
        String token = authenticationService.authenticate(authRequest);
        return new AuthResponseDto(token);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('RENEWAL')")
    @ApiOperation("Returns an authentication token for normal requests based on a given renewal token")
    public AuthResponseDto getRequestToken(@ApiIgnore JwtAuthenticationToken authentication) {
        String token = authenticationService.getRequestToken(authentication);
        return new AuthResponseDto(token);
    }
}
