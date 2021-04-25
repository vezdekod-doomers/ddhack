package com.alesharik.hack.security.token;

import com.alesharik.hack.data.LocalUser;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class LocalUserAuthenticationToken extends UsernamePasswordAuthenticationToken  {
    private final LocalUser user;

    public LocalUserAuthenticationToken(LocalUser user) {
        super(user.getLogin(), user.getPassword());
        this.user = user;
    }

    public LocalUserAuthenticationToken(LocalUser user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLogin(), user.getPassword(), authorities);
        this.user = user;
    }
}
