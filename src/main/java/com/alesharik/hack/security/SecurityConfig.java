package com.alesharik.hack.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final LocalUserAuthenticationProvider localUserAuthenticationProvider;
    private final AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/login").permitAll().and()
                .authorizeRequests().antMatchers("/static/**").permitAll().and()
                .authorizeRequests().antMatchers("/asset-manifest.json").permitAll().and()
                .authorizeRequests().antMatchers("/favicon.ico").permitAll().and()
                .authorizeRequests().antMatchers("/index.html").permitAll().and()
                .authorizeRequests().antMatchers("/manifest.json").permitAll().and()
                .authorizeRequests().antMatchers("/*.js").permitAll().and()
                .authorizeRequests().antMatchers("/perform_login").permitAll().and()
                .authorizeRequests().antMatchers("/api/admin/**").hasRole("ADMIN").and()
                .authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").and()
                .authorizeRequests().antMatchers("/api/ticket/add").permitAll().and()
                .authorizeRequests().antMatchers("/v3/api-docs.yaml").permitAll().and()
                .authorizeRequests().anyRequest().hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                .cors().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(localUserAuthenticationProvider);
    }

    @Bean("AuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
