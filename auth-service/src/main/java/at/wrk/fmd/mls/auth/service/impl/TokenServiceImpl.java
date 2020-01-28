package at.wrk.fmd.mls.auth.service.impl;

import static java.util.Objects.requireNonNull;

import at.wrk.fmd.mls.auth.config.JwtSigningProperties;
import at.wrk.fmd.mls.auth.entity.Unit;
import at.wrk.fmd.mls.auth.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.JacksonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Service
@Transactional
class TokenServiceImpl implements TokenService {

    private static final Collection<String> RENEWAL_AUTHORITIES = Collections.singleton("RENEWAL");
    private static final Collection<String> UNIT_AUTHORITIES = Collections.singleton("UNIT");

    private final JwtSigningProperties properties;
    private final ObjectMapper objectMapper;
    private final PrivateKey signingKey;

    @Autowired
    public TokenServiceImpl(JwtSigningProperties properties, ObjectMapper objectMapper)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.properties = requireNonNull(properties, "JwtSigningProperties must not be null");
        this.objectMapper = requireNonNull(objectMapper, "ObjectMapper must not be null");

        byte[] keyContent = properties.getSigningKey().getInputStream().readAllBytes();
        KeySpec keySpec = new PKCS8EncodedKeySpec(keyContent);
        signingKey = KeyFactory.getInstance("EC").generatePrivate(keySpec);
    }

    @Override
    public String generateUserRenewalToken(String username) {
        Instant expiration = Instant.now().plus(properties.getRenewalValidity());
        return generateToken(username, null, RENEWAL_AUTHORITIES, expiration);
    }

    @Override
    public String generateUnitRenewalToken(Unit unit, Instant expiration) {
        if (expiration == null) {
            expiration = Instant.now().plus(properties.getRenewalValidity());
        }
        return generateToken("", Collections.singleton(unit.getExternalId()), RENEWAL_AUTHORITIES, expiration);
    }

    @Override
    public String generateUserRequestToken(String username, Collection<String> authorities) {
        Instant expiration = Instant.now().plus(properties.getRequestValidity());
        return generateToken(username, null, authorities, expiration);
    }

    @Override
    public String generateUnitRequestToken(Collection<String> units) {
        Instant expiration = Instant.now().plus(properties.getRequestValidity());
        return generateToken("", units, UNIT_AUTHORITIES, expiration);
    }

    private String generateToken(String username, Collection<String> units, Collection<String> authorities, Instant expiration) {
        Instant now = Instant.now();

        String token = Jwts.builder()
                .serializeToJsonWith(new JacksonSerializer<>(objectMapper))
                .setIssuedAt(Date.from(now))
                .setNotBefore(Date.from(now))
                .setExpiration(Date.from(expiration))
                .setSubject(username)
                .claim("units", units)
                .claim("authorities", authorities)
                .signWith(signingKey)
                .compact();

        log.info("Token for user {}: {}", username, token);
        return token;
    }
}
