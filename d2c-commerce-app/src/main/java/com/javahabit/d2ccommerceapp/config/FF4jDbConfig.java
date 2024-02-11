package com.javahabit.d2ccommerceapp.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration

public class FF4jDbConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.ff4j")
    public DataSourceProperties ff4jDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "ff4j-ds")
    @ConfigurationProperties("spring.datasource.ff4j.configuration")
    public DataSource ff4jDataSource() {
        return ff4jDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }



}
