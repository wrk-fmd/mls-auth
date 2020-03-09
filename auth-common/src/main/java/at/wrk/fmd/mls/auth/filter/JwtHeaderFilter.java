package at.wrk.fmd.mls.auth.filter;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
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
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
@Component
public class JwtHeaderFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final PublicKey signingKey;

    @Autowired
    public JwtHeaderFilter(JwtVerificationProperties properties, ObjectMapper objectMapper)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.objectMapper = requireNonNull(objectMapper, "ObjectMapper must not be null");

        byte[] keyContent = properties.getSigningKey().getInputStream().readAllBytes();
        KeySpec keySpec = new X509EncodedKeySpec(keyContent);
        signingKey = KeyFactory.getInstance("EC").generatePublic(keySpec);
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
            log.warn("Received invalid Authorization token header: {}", tokenHeader);
            return;
        }

        String token = tokenHeader.substring(7);
        try {
            final Claims claims = Jwts.parserBuilder()
                    .deserializeJsonWith(new JacksonDeserializer<>(objectMapper))
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Authentication authentication = new JwtAuthenticationToken(claims, new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            log.info("Received invalid Authorization token: {}", token);
        }
    }
}
