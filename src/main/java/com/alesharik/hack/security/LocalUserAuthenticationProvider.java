package com.alesharik.hack.security;

import com.alesharik.hack.data.LocalUserRepository;
import com.alesharik.hack.security.authority.AdminGrantedAuthority;
import com.alesharik.hack.security.authority.UserGrantedAuthority;
import com.alesharik.hack.security.token.LocalUserAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class LocalUserAuthenticationProvider implements AuthenticationProvider {
    private final LocalUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var login = authentication.getName();
        var password = authentication.getCredentials().toString();
        var user = repository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User " + login + " not found"));
        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("Password did not match");
        var authorities = user.isAdmin() ? Arrays.asList(AdminGrantedAuthority.getInstance(), UserGrantedAuthority.getInstance()) : Collections.singletonList(UserGrantedAuthority.getInstance());
        return new LocalUserAuthenticationToken(user, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
