package com.javahabit.testcontainertodo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javahabit.testcontainertodo.entities.Todo;
import com.javahabit.testcontainertodo.repository.TodoRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)

//@WebMvcTest(TodoController.class)
//@ComponentScan(basePackages = "com.javahabit.testcontainertodo")
@AutoConfigureMockMvc
class TodoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepository repository;

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
        todo.setTitle("Test title 1");
        todo.setDescription("Test description");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/todos")
                        .contentType("application/json")
                        .content(createJsonString(todo))
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("Test title 1")));


    }

}