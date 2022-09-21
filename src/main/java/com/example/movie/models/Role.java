package com.example.movie.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CUSTOMER, ADMIN, EMPLOYEE;

    @Override
    public String getAuthority() {
        return name();
    }
}
