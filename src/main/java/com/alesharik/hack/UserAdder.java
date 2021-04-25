package com.alesharik.hack;

import com.alesharik.hack.data.LocalUser;
import com.alesharik.hack.data.LocalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAdder {
    public final LocalUserRepository localUserRepository;
    public final PasswordEncoder passwordEncoder;

    public void addAdmin() {
        if (localUserRepository.findByLogin("admin").isPresent())
            return;
        var localUser = new LocalUser();
        localUser.setAdmin(true);
        localUser.setLogin("admin");
        localUser.setPassword(passwordEncoder.encode("admin"));
        localUserRepository.save(localUser);
    }
}
