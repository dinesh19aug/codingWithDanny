package com.javahabit.testcontainertodo.service;

import com.javahabit.testcontainertodo.entities.Todo;
import com.javahabit.testcontainertodo.messageQueue.RabbitMqProducer;
import com.javahabit.testcontainertodo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService implements Iservice{
    private TodoRepository todoRepository;
    private RabbitMqProducer producer;
    TodoService(TodoRepository repository, RabbitMqProducer producer){
        this.todoRepository = repository;
        this.producer = producer;
    }
    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Todo> getTodoByTitle(String title) {
        return todoRepository.findTodoByTitle(title);
    }

    @Override
    public Todo createTodo(Todo todo) {
        producer.sendMessage("Creating Todo task: " + todo.getTitle());
        return todoRepository.save(todo);
    }

    @Override
    public Todo updateTodo(Long id, Todo todo) {
        todo.setId(id);
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
