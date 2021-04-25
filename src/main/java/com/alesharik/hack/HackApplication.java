package com.alesharik.hack;

import com.alesharik.hack.security.DescriptiveAccessDeniedHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@SpringBootApplication
public class HackApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(HackApplication.class, args);
        ctx.getBean(UserAdder.class).addAdmin();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new DescriptiveAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
