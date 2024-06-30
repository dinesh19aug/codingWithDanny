package com.javahabit.springsecurity.controller;

import com.javahabit.springsecurity.jwt.JwtUtils;
import com.javahabit.springsecurity.vo.LoginRequest;
import com.javahabit.springsecurity.vo.LoginResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class JwtController {
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    @PostMapping(value = "/authenticate" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createJwtToken(@RequestBody LoginRequest loginRequest) throws Exception {
        //Create Authenticate object (setup the username/pwd)
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        }catch (DisabledException e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("msg", "Account is diabled");
            errorMap.put("status", false);
            return new ResponseEntity<Object>(errorMap, HttpStatus.LOCKED);

        } catch (BadCredentialsException e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("msg", "Invalid credentials");
            errorMap.put("status", false);
            return new ResponseEntity<Object>(errorMap, HttpStatus.UNAUTHORIZED);

        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //Authenticate the user name and pwd in the database
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //If username is found for the given password then create jwt token
        final String jwtToken = jwtUtils.generateToken(userDetails);
        final List<String> roles = authentication.getAuthorities()
                .stream()
                .map(role-> role.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new LoginResponse(jwtToken, userDetails.getUsername(), roles));
    }
}
