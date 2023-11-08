package com.ingress.bookstore.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    AUTHOR,
    STUDENT;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
