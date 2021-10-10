package com.vosouq.gateway.model;

import org.springframework.security.core.GrantedAuthority;

public enum Authorities implements GrantedAuthority {

    ROLE_USER,
    ROLE_LIMITED,
    ROLE_NOT_LOGGED_IN;

    @Override
    public String getAuthority() {
        return name();
    }

}
