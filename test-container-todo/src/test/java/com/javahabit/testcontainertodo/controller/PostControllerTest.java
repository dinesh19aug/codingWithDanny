package com.javahabit.testcontainertodo.controller;

import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Testcontainers
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Container
    static MockServerContainer mockServerContainer =
            new MockServerContainer(DockerImageName.parse("mockserver/mockserver:5.15.0"));

    static MockServerClient mockServerClient;


    @BeforeAll
    static void beforeAll(){
        mockServerContainer.start();
    }

    @AfterAll
    static void afterAll(){
        mockServerContainer.stop();
    }
    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        mockServerClient = new MockServerClient(
                mockServerContainer.getHost(),
                mockServerContainer.getServerPort()
        );


        //registry.add("post.url", ()->mockServerContainer.getEndpoint().concat("/typicode/demo/posts") );
        registry.add("post.url", ()->mockServerContainer.getEndpoint() );
    }


    @BeforeEach
    void setUp() throws IOException {
        byte[] responseJson = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("response.json"));
        String jsonValue = """
                [
                  {
                    "id": 1,
                    "title": "Post 1"
                  },
                  {
                    "id": 2,
                    "title": "Post 2"
                  },
                  {
                    "id": 3,
                    "title": "Post 3"
                  }
                ]
                """;
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/typicode/demo/posts"))
                .respond(response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(jsonValue));
    }

    @Test
    void getPosts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/posts")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", CoreMatchers.is("Post 1")));
    }
}