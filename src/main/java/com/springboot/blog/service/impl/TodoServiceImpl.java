package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Todo;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.TodoRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.UserPrincipal;
import com.springboot.blog.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<Todo> getAllTodos(UserPrincipal currentUser) {
        List<Todo> byUser_id = todoRepository.findByUser_Id(currentUser.getId());

        return byUser_id;
    }

    @Override
    public Todo getTodo(Long id, UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername());

        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));

        if(todo.getUser().getId().equals(user.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Todo doesn't belong to this user");
        }
        return todo;
    }

    @Override
    public String completeTodo(Long id, UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername());

        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));

        if(todo.getUser().getId().equals(user.getId())){
            todo.setCompleted(Boolean.TRUE);
            todoRepository.save(todo);
            return "Completed successfully set to true";
        }
        else{
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Todo doesn't belong to this user");
        }
    }

    @Override
    public String unCompleteTodo(Long id, UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername());

        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));

        if(todo.getUser().getId().equals(user.getId())){
            todo.setCompleted(Boolean.FALSE);
            todoRepository.save(todo);
            return "Completed successfully set to false";
        }
        else{
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Todo doesn't belong to this user");
        }
    }

    @Override
    public Todo addTodo(Todo todo, UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername());
        todo.setUser(user);
        todoRepository.save(todo);

        return todo;
    }

    @Override
    public Todo updateTodo(Long id, Todo newTodo, UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername());
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));

        if(todo.getUser().getId().equals(user.getId())){
            todo.setTitle(newTodo.getTitle());
            todo.setCompleted(newTodo.getCompleted());

            return todoRepository.save(todo);
        }
        else{
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Todo doesn't belong to this user");
        }
    }

    @Override
    public String deleteTodo(Long id, UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername());
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));

        if(todo.getUser().getId().equals(user.getId())){
            todoRepository.deleteById(id);
            return "Todo is successfully deleted";
        }
        else{
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Todo doesn't belong to this user");
        }
    }
}
