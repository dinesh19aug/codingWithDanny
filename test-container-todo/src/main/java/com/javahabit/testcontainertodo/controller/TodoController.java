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
        List<Todo> todoList = service.getAllTodos();
        return todoList;
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id) {
        Todo todo = service.getTodoById(id);
        return todo;
    }

    @GetMapping("/q")
    public List<Todo> getTodoByTitle(@RequestParam String title) {
        List<Todo> todoByTitle = service.getTodoByTitle(title);
        return todoByTitle;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public Todo createTodo(@RequestBody Todo todo) {
        Todo result = service.createTodo(todo);
        return result;
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        Todo updatedTodo = service.updateTodo(id, todo);
        return updatedTodo;
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        service.deleteTodo(id);
    }
}
