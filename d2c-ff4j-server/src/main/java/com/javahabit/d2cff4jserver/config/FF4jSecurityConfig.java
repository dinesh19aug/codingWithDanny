package com.javahabit.d2cff4jserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class FF4jSecurityConfig {
    @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails admin = users
                .username("admin")
                .password("admin")
                .roles("USER", "ADMIN")
                .build();

        UserDetails user = users
                .username("user")
                .password("user")
                .roles("USER", "USER")
                .build();
        return new InMemoryUserDetailsManager(admin,user);
    }
}
