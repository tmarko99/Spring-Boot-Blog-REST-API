package com.springboot.blog.service;

import com.springboot.blog.entity.Todo;
import com.springboot.blog.security.UserPrincipal;

import java.util.List;

public interface TodoService {
    List<Todo> getAllTodos(UserPrincipal currentUser);
    Todo getTodo(Long id, UserPrincipal currentUser);
    String completeTodo(Long id, UserPrincipal currentUser);
    String unCompleteTodo(Long id, UserPrincipal currentUser);
    Todo addTodo(Todo todo, UserPrincipal currentUser);
    Todo updateTodo(Long id, Todo todo, UserPrincipal currentUser);
    String deleteTodo(Long id, UserPrincipal currentUser);
}
