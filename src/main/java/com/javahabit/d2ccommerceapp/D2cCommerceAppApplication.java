package com.javahabit.d2ccommerceapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.javahabit")
public class D2cCommerceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(D2cCommerceAppApplication.class, args);
    }

}
