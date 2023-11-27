package com.javahabit.testcontainertodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EnableJpaRepositories
public class TestContainerTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestContainerTodoApplication.class, args);
	}

}
