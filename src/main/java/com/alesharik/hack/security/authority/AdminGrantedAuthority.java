package com.alesharik.hack.security.authority;

import org.springframework.security.core.GrantedAuthority;

public class AdminGrantedAuthority implements GrantedAuthority {
    private static final GrantedAuthority authority = new AdminGrantedAuthority();

    public static GrantedAuthority getInstance() {
        return authority;
    }

    private AdminGrantedAuthority() {}

    @Override
    public String getAuthority() {
        return "ROLE_ADMIN";
    }
}
