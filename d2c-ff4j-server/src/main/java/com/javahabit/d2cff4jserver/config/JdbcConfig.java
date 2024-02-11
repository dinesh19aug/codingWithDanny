package com.javahabit.d2cff4jserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration

public class JdbcConfig {
    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String jdbcUserName;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String jdbcDriver;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource jdbc = new DriverManagerDataSource();
        jdbc.setDriverClassName(jdbcDriver);
        jdbc.setUrl(jdbcUrl);
        jdbc.setPassword(jdbcPassword);
        jdbc.setUsername(jdbcUserName);
        return jdbc;
    }

}
