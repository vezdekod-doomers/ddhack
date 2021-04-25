package com.alesharik.hack.security.authority;

import org.springframework.security.core.GrantedAuthority;

public class UserGrantedAuthority implements GrantedAuthority {
    private static final GrantedAuthority authority = new UserGrantedAuthority();

    public static GrantedAuthority getInstance() {
        return authority;
    }

    private UserGrantedAuthority() {}

    @Override
    public String getAuthority() {
        return "ROLE_USER";
    }
}
