package com.javahabit.springsecurity.config;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // types of filters
        /**
         * CsrfFilter Added by HttpSecurity#csrf
         * UsernamePasswordAuthenticationFilter Added by HttpSecurity#formLogin
         * BasicAuthenticationFilter Added by HttpSecurity#httpBasic
         * AuthorizationFilter Added by HttpSecurity#authorizationHttpRequests
         */

        http.csrf(Customizer.withDefaults())
                //Not required for REST API or machine to machine transaction. Use it for Web based apps
                .csrf(csrf -> csrf.disable())
                //.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/hello").permitAll()
                        .requestMatchers(HttpMethod.GET,"/employee").hasRole("employee")
                        .requestMatchers(HttpMethod.GET,"/employee/**").hasRole("employee")
                        .requestMatchers(HttpMethod.POST,"/employee").hasRole("manager")
                        .requestMatchers(HttpMethod.PUT,"/employee").hasRole("manager")
                        .requestMatchers(HttpMethod.DELETE,"/employee/**").hasRole("admin")
                        .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint()))

        ;

        return http.build();


    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails mary = User.builder()
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

        return new InMemoryUserDetailsManager(mary,john,larry);

    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }
}

