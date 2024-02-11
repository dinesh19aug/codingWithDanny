package com.javahabit.d2ccommerceapp.config;

import org.ff4j.FF4j;
import org.ff4j.springjdbc.store.EventRepositorySpringJdbc;
import org.ff4j.springjdbc.store.FeatureStoreSpringJdbc;
import org.ff4j.springjdbc.store.PropertyStoreSpringJdbc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FF4jConfig {
    private final DataSource dataSource;

    public FF4jConfig(@Qualifier("ff4j-ds") DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Bean
    public FF4j getFF4j() {
        FF4j ff4j = new FF4j();


        //ff4j.setFeatureStore(new InMemoryFeatureStore());
        ff4j.setFeatureStore(new FeatureStoreSpringJdbc(dataSource));
        //ff4j.setPropertiesStore(new InMemoryPropertyStore());
        ff4j.setPropertiesStore(new PropertyStoreSpringJdbc(dataSource));
        //ff4j.setEventRepository(new InMemoryEventRepository());
        ff4j.setEventRepository(new EventRepositorySpringJdbc(dataSource));

        // Enabling audit and monitoring, default value is false
        ff4j.audit(true);

        // When evaluting not existing features, ff4j will create then but disabled
        ff4j.autoCreate(true);

        // To define RBAC access, the application must have a logged user
        //ff4j.setAuthManager(SpringSecurityAuthorisationManager.class.getName());

        // To define a cacher layer to relax the DB, multiple implementations
        //ff4j.cache([a cache Manager]);

        return ff4j;
    }
}
