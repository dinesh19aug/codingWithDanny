package com.javahabit.testcontainertodo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javahabit.testcontainertodo.entities.Todo;
import com.javahabit.testcontainertodo.repository.TodoRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class TodoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepository repository;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    public static String createJsonString(final Object json){
        try{
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonStringContent = objectMapper.writeValueAsString(json);
            return jsonStringContent;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }


    @Test
    void createTodo() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test title 2");
        todo.setDescription("Test description");
        //CREATE A TODO TASK
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/todos")
                        .contentType("application/json")
                        .content(createJsonString(todo))
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("Test title 2")));

        //VERIFY A TODO TASK "TEST Title 1 is inserted
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/todos/q")
                        .queryParam("title", "Test title 2")
                        .contentType("application/json")
                        .content(createJsonString(todo))
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", CoreMatchers.is("Test title 2")));
    }



}