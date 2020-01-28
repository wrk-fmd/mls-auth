package at.wrk.fmd.mls.auth.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROOT;

    @Override
    public String getAuthority() {
        return name();
    }
}
