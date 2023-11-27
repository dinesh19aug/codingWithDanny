package com.javahabit.testcontainertodo.service;

import com.javahabit.testcontainertodo.entities.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {

    @Value("${post.url}")
    String postUri;
    public Post[] getPosts(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(postUri.concat("/typicode/demo/posts"), Post[].class);
    }


}
