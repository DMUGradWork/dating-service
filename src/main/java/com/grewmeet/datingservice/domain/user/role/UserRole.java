package com.grewmeet.datingservice.domain.user.role;

import static com.grewmeet.datingservice.domain.user.role.Entitlement.*;

import java.util.Set;

public enum UserRole {

    GUEST(Set.of(CAN_PARTICIPATE)),
    HOST(Set.of(CAN_PARTICIPATE, CAN_CREATE, CAN_CANCEL));

    private final Set<Entitlement> capabilities;

    UserRole(Set<Entitlement> capabilities) {
        this.capabilities = capabilities;
    }

    public boolean can(Entitlement e) {
        return capabilities.contains(e);
    }
}

