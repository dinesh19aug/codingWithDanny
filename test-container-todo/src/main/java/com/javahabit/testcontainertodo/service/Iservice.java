package com.javahabit.testcontainertodo.service;

import com.javahabit.testcontainertodo.entities.Todo;

import java.util.List;

public interface Iservice {
    List<Todo> getAllTodos();
    Todo getTodoById(Long id);
    List<Todo> getTodoByTitle(String title);
    Todo createTodo(Todo todo);
    Todo updateTodo(Long id, Todo todo);
    public void deleteTodo(Long id);
}
