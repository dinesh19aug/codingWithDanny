package com.javahabit.testcontainertodo.controller;

import com.javahabit.testcontainertodo.entities.Todo;
import com.javahabit.testcontainertodo.service.Iservice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final Iservice service;

    public TodoController( Iservice service) {
        this.service = service;

    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return service.getAllTodos();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id) {
        return service.getTodoById(id);
    }

    @GetMapping("/q")
    public List<Todo> getTodoByTitle(@RequestParam String title) {
        return service.getTodoByTitle(title);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public Todo createTodo(@RequestBody Todo todo) {
        return service.createTodo(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        return service.updateTodo(id, todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        service.deleteTodo(id);
    }
}
