package com.javahabit.testcontainertodo.controller;

import com.javahabit.testcontainertodo.entities.Todo;
import com.javahabit.testcontainertodo.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        todo.setId(id);
        return todoRepository.save(todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}
//docker run -p 5432:5432 -e POSTGRES_DB=codingwithdanny -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -d postgres
