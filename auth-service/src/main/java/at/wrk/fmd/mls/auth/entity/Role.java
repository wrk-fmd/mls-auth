package at.wrk.fmd.mls.auth.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum Role implements GrantedAuthority {
    MLS,
    Kdt(MLS),
    ROOT(Kdt);

    private final Set<Role> roles;

    Role(Role... inherited) {
        roles = resolveInheritedRoles(this, Arrays.asList(inherited));
    }

    public Set<Role> getAllGranted() {
        return roles;
    }

    @Override
    public String getAuthority() {
        return name();
    }

    private static Set<Role> resolveInheritedRoles(final Role startingPoint, final Collection<Role> inherited) {
        Set<Role> resolved = new HashSet<>();
        resolved.add(startingPoint);
        inherited.forEach(r -> resolved.addAll(r.getAllGranted()));
        return Collections.unmodifiableSet(resolved);
    }
}
