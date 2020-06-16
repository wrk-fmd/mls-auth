package at.wrk.fmd.mls.auth.filter;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
@Component
public class JwtAuthenticationParser {

    private final ObjectMapper objectMapper;
    private final PublicKey signingKey;

    @Autowired
    public JwtAuthenticationParser(JwtVerificationProperties properties, ObjectMapper objectMapper)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.objectMapper = requireNonNull(objectMapper, "ObjectMapper must not be null");

        byte[] keyContent = properties.getSigningKey().getInputStream().readAllBytes();
        KeySpec keySpec = new X509EncodedKeySpec(keyContent);
        signingKey = KeyFactory.getInstance("EC").generatePublic(keySpec);
    }

    public JwtAuthenticationToken getAuthentication(String token, Object details) {
        if (token == null || token.isBlank()) {
            log.info("Received empty JWT");
            return null;
        }

        try {
            final Claims claims = Jwts.parserBuilder()
                    .deserializeJsonWith(new JacksonDeserializer<>(objectMapper))
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return new JwtAuthenticationToken(claims, details);
        } catch (JwtException e) {
            log.info("Received invalid Authorization token: {}", token);
            return null;
        }
    }
}
