package com.javahabit.testcontainertodo.controller;

import com.javahabit.testcontainertodo.entities.Post;
import com.javahabit.testcontainertodo.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Post[] getPosts(){
        return postService.getPosts();
    }
}
