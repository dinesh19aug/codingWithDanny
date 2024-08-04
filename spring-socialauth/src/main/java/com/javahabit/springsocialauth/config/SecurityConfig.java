package com.javahabit.springsocialauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/index").permitAll() // Ensure this URL is accessible without authentication
                                .requestMatchers("/logout").permitAll()
                                .requestMatchers("/admin").authenticated() // This URL requires authentication
                                .anyRequest().authenticated() // Other requests require authentication
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/oauth2/authorization/github") // Set the login page
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint.userService(oAuth2UserService())
                                )
                )
                .logout(logout ->
                        logout

                                .logoutUrl("/logout?prompt=consent") // Set the logout URL
                                .logoutSuccessUrl("/index") // Redirect to index after logout
                                .invalidateHttpSession(true) // Invalidate the session
                                .clearAuthentication(true) // Clear authentication
                                .deleteCookies("JSESSIONID") // Delete session cookies
                                .permitAll()
                )


        ;
        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return new DefaultOAuth2UserService();
    }
}
