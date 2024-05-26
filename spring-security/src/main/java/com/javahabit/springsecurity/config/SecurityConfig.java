package com.javahabit.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // types of filters
        /**
         * CsrfFilter Added by HttpSecurity#csrf
         * UsernamePasswordAuthenticationFilter Added by HttpSecurity#formLogin
         * BasicAuthenticationFilter Added by HttpSecurity#httpBasic
         * AuthorizationFilter Added by HttpSecurity#authorizationHttpRequests
         */

        http
                //Not required for REST API or machine to machine transaction. Use it for Web based apps
                .csrf(csrf -> csrf.disable())
                //.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/hello").permitAll()
                        .requestMatchers(HttpMethod.GET,"/employee").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/employee/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST,"/employee").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT,"/employee").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE,"/employee/**").hasRole("ADMIN")
                        .anyRequest().authenticated()).csrf(csrf-> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint()))

        ;

        return http.build();


    }

    @Bean
    public UserDetailsService userDetailsService(){
        /*UserDetails mary = User.builder()
                .username("mary")
                //{noop} is prefix used in password configuration to indicate that the password is stored in plain text and no encoding or hashing should be applied. This is typically used for testing or demonstration purposes and should not be used in production environments due to security risks.
                .password("{noop}mary")
                .roles("employee", "manager", "admin")
                .build();

        UserDetails john = User.builder()
                .username("john")
                .password("{noop}john")
                .roles("employee")
                .build();

        UserDetails larry = User.builder()
                .username("larry")
                .password("{noop}larry")
                .roles("employee", "manager")
                .build();

        return new InMemoryUserDetailsManager(mary,john,larry);*/
        //return new JdbcUserDetailsManager(dataSource);
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
        manager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
        return manager;
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

