package at.wrk.fmd.mls.auth.filter;

import static java.util.Objects.requireNonNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO Use AbstractAuthenticationProcessingFilter?
@Slf4j
@Component
public class JwtHeaderFilter extends OncePerRequestFilter {

    private final JwtAuthenticationParser authenticationParser;

    @Autowired
    public JwtHeaderFilter(JwtAuthenticationParser authenticationParser) {
        this.authenticationParser = requireNonNull(authenticationParser, "JwtAuthenticationParser must not be null");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        authenticateWithHeaderToken(request);
        chain.doFilter(request, response);
    }

    private void authenticateWithHeaderToken(HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            // Already authenticated
            log.debug("Skipping JwtHeaderFilter, already authenticated");
            return;
        }

        final String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            // Invalid header
            log.info("Received invalid Authorization token header: {}", tokenHeader);
            return;
        }

        String token = tokenHeader.substring(7);
        Object details = new WebAuthenticationDetailsSource().buildDetails(request);
        Authentication authentication = authenticationParser.getAuthentication(token, details);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
