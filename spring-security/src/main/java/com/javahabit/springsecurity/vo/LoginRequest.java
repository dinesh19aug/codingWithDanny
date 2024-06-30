package com.javahabit.springsecurity.vo;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
}
