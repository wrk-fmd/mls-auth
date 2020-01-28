package at.wrk.fmd.mls.auth.service.impl;

import at.wrk.fmd.mls.auth.dto.auth.AuthRequestDto;
import at.wrk.fmd.mls.auth.filter.JwtAuthenticationToken;
import at.wrk.fmd.mls.auth.service.AuthenticationService;
import at.wrk.fmd.mls.auth.service.TokenService;
import at.wrk.fmd.mls.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenService tokenService;
    private final UserDetailsService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(TokenService tokenService, UserService userService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String authenticate(AuthRequestDto request) {
        log.info("User {} requested login", request.getUsername());

        // Authenticate with Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Generate a token for the authenticated user (only valid for retrieving request tokens, so no specific authorities are set)
        Object principal = authentication.getPrincipal();
        Assert.isInstanceOf(UserDetails.class, principal, "Authentication principal has wrong type");
        return tokenService.generateUserRenewalToken(((UserDetails) principal).getUsername());
    }

    @Override
    public String getRequestToken(JwtAuthenticationToken authentication) {
        if (authentication == null) {
            throw new BadCredentialsException("Token renewal requires an active JWT authentication");
        }

        String username = authentication.getPrincipal();
        if (username == null || username.isBlank()) {
            // Unit based authentication
            return tokenService.generateUnitRequestToken(authentication.getUnits());
        }

        // User based authentication
        // TODO Check if account is still enabled and so on - maybe try to use Spring Security classes like on initial authentication?
        UserDetails user = userService.loadUserByUsername(username);
        Collection<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return tokenService.generateUserRequestToken(user.getUsername(), authorities);
    }
}
