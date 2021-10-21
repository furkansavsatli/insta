package com.insta.model;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
    ROLE_CLIENT;

    public String getAuthority() {
        return name();
    }

}
