package at.wrk.fmd.mls.auth.filter;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Claims claims;

    public JwtAuthenticationToken(Claims claims, Object details) {
        super(getAuthorities(claims));
        setDetails(details);
        setAuthenticated(true);

        this.claims = claims;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        //noinspection unchecked
        List<String> authorities = claims.get("authorities", List.class);
        return authorities != null ? authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()) : null;
    }

    @Override
    public Claims getCredentials() {
        return claims;
    }

    @Override
    public String getPrincipal() {
        return claims.getSubject();
    }

    public Collection<String> getUnits() {
        //noinspection unchecked
        return claims.get("units", Collection.class);
    }
}
